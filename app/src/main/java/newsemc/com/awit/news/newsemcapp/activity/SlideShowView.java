package newsemc.com.awit.news.newsemcapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.widget.*;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.BitmapHelper;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;
import zrc.widget.ZrcListView;

import static android.support.v4.view.ViewPager.*;

/**
 * 主界面图片轮播
 * Created by Administrator on 15-6-3.
 */
public class SlideShowView extends FrameLayout implements HttpResultListener {
    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private final String TAG = "SlideShowView";
    //轮播图图片数量
    private final static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;

    //自定义轮播图的资源
    private String[] imageUrls;
    //自定义文字描述
    private String[] textDesc;
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;
    private ViewPager viewPager;
    //当前轮播页
    private int currentItem  = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private Context context;
//图片信息集合
    private List<ImageInfo> imgInfoListList;

    private String[] temp;
    //文字描述数组
    private String[] textarr;

    private TextView text;

    private String ssid;

    private String account;
    private long endTime=0;
    private int i=1;
//图片信息服务
    private ImageInfoService imageInfoService;

    private BitmapUtils bitmapUtils;
    ZrcListView zrcListView;

    //Handler
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
            //设置图片的休眠事件
        }

    };
    public SlideShowView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;

        initImageLoader(context);

        initData();
        if(isAutoPlay){
            Log.i(TAG, "i的值=" + i);
            startPlay();
            i++;
        }

    }
    //
    /**
     * 开始轮播图切换
     */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 4, 4, TimeUnit.SECONDS);
        //在开始轮播切换图片的时候给个时间限制
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("时间限制 ===","popopo");
            }
        },1000000000);
    }
    /**
     * 停止轮播图切换
     */
    private void stopPlay(){
        scheduledExecutorService.shutdown();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("时间限制 ===", "popopo");
            }
        }, 1000000000);
    }
    /**
     * 初始化相关Data
     */
    private void initData(){
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
        SharedPreferences sharedPreferences=context.getSharedPreferences("SP", CompanyNewSingleActivity.MODE_PRIVATE);
        ssid=sharedPreferences.getString("KEY","");
        Log.i("ssid", ssid + "");
        account=sharedPreferences.getString("ACCOUNT","");

        imageInfoService = ImageInfoService.getInstance(context);

        if(imageInfoService.queryImageinfotype_pageno(16+"",1+"").size() == 0){
            ImgInfoListImpl imgInfoListImpl=new ImgInfoListImpl(this);
            imgInfoListImpl.getImgInfoList(ssid,account, "16", "1", "6", "1");
        }else{
            List<ImageInfo> read_localtupian = new ArrayList<ImageInfo>();
            read_localtupian = imageInfoService.queryImageinfotype_pageno(16+"",1+"");
            //图片数组
            int size = read_localtupian.size();
            temp = new String[size];
            textarr=new String[size];
            for(int i=0;i<size;i++){
                temp[i] = read_localtupian.get(i).getInfoimg();
                textarr[i]=read_localtupian.get(i).getInfoname();
                Log.i("temp",temp[i]);
                Log.i("textarr",textarr[i]);
            }
            // 一步任务获取图片
            new GetListTask().execute("");
        }



    }
    /**
     * 初始化Views等UI
     */
    private void initUI(Context context){
        if (bitmapUtils == null) {
            bitmapUtils = BitmapHelper.getBitmapUtils();
            bitmapUtils.configDefaultLoadingImage(R.drawable.empty_photo); // 设置加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.empty_photo); // 设置加载错误图片

        }
        if(imageUrls == null || imageUrls.length == 0)
            return;
        ViewGroup vv= (ViewGroup)findViewById(R.id.replace_fragment);
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);

        Log.i("hahahaha", textDesc[0]);
        text=(TextView)findViewById(R.id.textcontent1);
        text.setText(textarr[0]);
        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view =  new ImageView(context);
            view.setTag(imageUrls[i]);
            if(i==0){//给一个默认图
                bitmapUtils.display(view,imageUrls[0]);
            }
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(view);

            ImageView dotView =  new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.leftMargin = 3;
            params.rightMargin = 3;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);

        }
        System.out.println("++++++++跳出initUI+++++++");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN :
                        Log.i(TAG, "action_down");
//                        stopPlay();
                        break;
                    case MotionEvent.ACTION_SCROLL:
                        Log.i(TAG, "action_scroll");
//                        stopPlay();
                        break;
                    case MotionEvent.ACTION_UP :
                        Log.i(TAG, "action_up");
//                        startPlay();
                        break;
                    case MotionEvent.ACTION_CANCEL :
                        Log.i(TAG, "action_cancel");
//                        startPlay();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 填充ViewPager的页面适配器
     *
     */
    private class MyPagerAdapter  extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
//            ((ViewPager)container).removeView(imageViewsList.get(position));
            ((ViewPager)container).removeView((View)object);
        }

        @Override
        public Object instantiateItem(View container, int position) {
//            ImageView imageView = imageViewsList.get(position);
//            imageLoader.displayImage(imageView.getTag() + "", imageView);
//            ImageView image = imageViewsList.get(position);
//            ((ViewPager) container).addView(image);
//            return imageViewsList.get(position);
            //获取图片资源
            ImageView imageView = imageViewsList.get(position);
            //设置图片是可以点击的
            imageView.setClickable(true);
            bitmapUtils.display(imageView, (String)imageView.getTag());
            //
            final ImageInfo imageInfo=imgInfoListList.get(position);
//            //支持新闻图片点击，实现图片的监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                       KLog.e("vvvvvvv", "=========图片轮播=======");
//                    Intent intent = new Intent(getContext(), SingleNewActivity.class);
//                       Intent intent = new Intent(getContext(), SingleNewActivity1.class);
                       Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                       intent.putExtra("infoid", imageInfo.getInfoid());
                       intent.putExtra("infotype", imageInfo.getInfotype());
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       getContext().startActivity(intent);
                }
            });

            ((ViewPager) container).addView(imageView);

            return imageView;
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("时间限制 ===","popopo");
                        }
                    },1000000000);
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("时间限制 ===","popopo");
                        }
                    },1000000000);
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("时间限制 ===", "popopo");
                            }
                        }, 1000000000);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("时间限制 ===", "popopo");
                            }
                        }, 1000000000);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            currentItem = pos;
            text.setText(textDesc[currentItem]);
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_focus);
                }else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_blur);
                }
            }
        }

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN :
//                Log.i(TAG, "action_down");
//                stopPlay();
//                break;
//            case MotionEvent.ACTION_SCROLL:
//                Log.i(TAG, "action_scroll");
//                stopPlay();
//                break;
//            case MotionEvent.ACTION_UP :
//                Log.i(TAG, "action_up");
//                startPlay();
//                break;
//            case MotionEvent.ACTION_CANCEL :
//                Log.i(TAG, "action_cancel");
//                startPlay();
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 销毁ImageView资源，回收内存
     *
     */
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }


    /**
     * 异步任务,获取数据
     *
     */
    class GetListTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            System.out.println("++++++++进入doInBackground+++++++");
            try {
                // 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片

//                imageUrls = new String[]{
//                        "http://www.r93535.com/gateway/file/1435276438693.jpg",
//                        "http://www.r93535.com/gateway/file/1435276565692.jpg",
//                        "http://www.r93535.com/gateway/file/1435276646484.jpg",
//                        "http://www.r93535.com/gateway/file/1435276861253.jpg"
//                };
                imageUrls=temp;
                textDesc=textarr;
                Log.i("img",imageUrls+"");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                initUI(context);
            }
        }
    }

    /**
     * ImageLoader 图片组件初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
                        // for
                        // release
                        // app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onResult(Object obj) {
        System.out.println("++++++++进入onResult+++++++");
        if(obj instanceof FailRequest){
            FailRequest  fail= (FailRequest)obj;
            if(!(fail==null)){
                System.out.println("异常信息："+fail.getMsg());
                System.out.println("状态："+fail.getStatus());
            }
        }else{
            //图片数组
            imgInfoListList= (List<ImageInfo>) obj;
            int size = imgInfoListList.size();
            temp = new String[size];
            textarr=new String[size];

            for(int i=0;i<size;i++){
                temp[i] = imgInfoListList.get(i).getInfoimg();
                textarr[i]=imgInfoListList.get(i).getInfoname();
                Log.i("temp",temp[i]);
                Log.i("textarr",textarr[i]);
            }
            // 一步任务获取图片
            new GetListTask().execute("");
        }
        System.out.println("++++++++跳出onResult+++++++");
    }

    @Override
    public void onFinish() {

    }
}
