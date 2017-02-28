package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import org.w3c.dom.Text;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.BitmapHelper;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.DetailImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;

/**
 * 全路风采和公司风采详情界面图片轮播展示activity(暂无需要)
 * Created by cll on 2015/9/14.
 */
public class ImageDetailActivity extends Activity {
    private List<DetailInfo> detailInfoList;
    private String ssid;
    private String account;
    private String[] imageIds;
    private String[] titles;
    private ViewPager viewpager;
    private int currentItem=0;
    private BitmapUtils bitmapUtils;
    private TextView textView;
    private Button back;
    private TextView prp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.imagedetail);
        viewpager = (ViewPager) findViewById(R.id.vp);
        textView=(TextView)findViewById(R.id.textcontent1);
        back=(Button)findViewById(R.id.back);
        prp=(TextView)findViewById(R.id.prp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=this.getIntent();
        Log.i("prp", intent.getStringExtra("prp"));
        prp.setText(intent.getStringExtra("prp"));
//        SharedPreferences sharedPreferences=getSharedPreferences("SP", CompanyNewSingleActivity.MODE_PRIVATE);
//        ssid=sharedPreferences.getString("KEY","");
//        Log.i("ssid", ssid + "");
//        account=sharedPreferences.getString("ACCOUNT","");
//        DetailImpl detailImpl=new DetailImpl(ImageDetailActivity.this);
//        detailImpl.getDetailInfoList(ssid, account, intent.getStringExtra("infotype"), intent.getStringExtra("infoid"));
        //图片ID
        imageIds = new String[]{
                "http://www.r93535.com/gateway/file/1435310700819.jpg",
                "http://www.r93535.com/gateway/file/1434301193338.jpg",
                "http://www.r93535.com/gateway/file/1434301117663.jpg",
                "http://www.r93535.com/gateway/file/1434301354510.jpg",
        };
        titles = new String[]{
                "为白血病儿童献爱心",
                "始发站五环段加装声屏障效果图",
                "美化环境",
                "领导调研"
        };
        //这个是我自定义的ViewPager的适配器
        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        viewpager.setAdapter(adapter);
        //因为考虑到左右都可以用手指滑动，所以给他一个比较大的数，这样往左往右都可以滑动，
        //要乘以imageIds.length考虑到滑动要从第一张图片开始滑动，下面的adapter中的
        //getCount()给了一个特别大的数Integer.MAX_VALUE，保证永远划不到头，会不挺的滑动下去
        viewpager.setCurrentItem(5000*imageIds.length);
        //ViewPager的触摸事件，保证触摸时停止滑动，抬起时继续滑动
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://手指按下时，停止轮播
                        isFlag = false;
                        break;
                    case MotionEvent.ACTION_CANCEL://手指从图片上划出去了，图片的轮播会停止，跟手指抬起一样要重新进行轮播
                    case MotionEvent.ACTION_UP://手指抬起，此时继续轮播
                        isFlag = true;//isFlag=true保证图片可以继续轮播
                        handler.sendEmptyMessageDelayed(0, 1000);
                        break;
                }
                return false;//此处必须返回false，否则事件被消费掉了（不是ViewPager消费掉的）,无法进行轮播
            }
        });
        autoChange();//做第一次图片的滚动，然后ViewPager会自动轮播

    }


    /**
     * 控制是否继续滑动
     */
    private boolean isFlag = true;
    /**
     * 图片切换的方法
     */
    public void autoChange(){
        if (isFlag) {//判断是否要继续图片轮播
            //取消消息，否则，虽然不运行autoChange()方法了，但是还会继续发送消息，下次isFlag=true时会接着上次轮播继续，相当于开了两个消息，导致轮播速度加快
            handler.removeMessages(0);
            //取出当前条目并加 1
            currentItem = viewpager.getCurrentItem();
            currentItem++;
            //设置为+1后的当前条目
            viewpager.setCurrentItem(currentItem);
            //发送指令图片要进行轮播
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    /**
     * ViewPager的适配器
     *
     */
    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //Integer.MAX_VALUE的值为2147483647，保证永远滑动不完
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            //固定写法，这个好像是废物，但是必须这样写
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这里是View，可以自己定义一个布局文件，然后,
			/*View view = View.inflate(SplashActivity.this, R.layout.xxxx, null);
			ImageView imageView = (ImageView)view.findViewById(R.id.xxx);
			int index = position % imageIds.length;
			imageView.setBackgroundResource(imageIds[index]);*/
            //ViewPager的适配器对内存的管理非常好，不像ListView需要自己去管理内存（复用convertView、写ViewHolder之类的都是自己手动的在维护内存）

            ImageView imageView = new ImageView(ImageDetailActivity.this);
            //position模除imageIds的长度使index保证在imageIds的角标范围内，呼应了Integer.MAX_VALUE
            int index = position % imageIds.length;
            textView.setText(titles[index]);
            if (bitmapUtils == null) {
                bitmapUtils = BitmapHelper.getBitmapUtils();
                bitmapUtils.configDefaultLoadingImage(R.drawable.empty_photo); // 设置加载中显示的图片
                bitmapUtils.configDefaultLoadFailedImage(R.drawable.empty_photo); // 设置加载错误图片
            }
            bitmapUtils.display(imageView,imageIds[index]);
            //这一步相当于ListView适配器中的getView()方法
            container.addView(imageView);
            //这一步返回必须有，相当于ListView适配器的getItem()方法
            //上面的return arg0 == arg1;中第一个参数跟这个是对应的
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //把一开始的super.destroyItem(....去掉，不能带着那个，这个写法基本也是固定的
            container.removeView((View)object);
        }
    }

    /**
     * 图片轮播的handler
     */
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            //自动轮播
            autoChange();
        };
    };
}
