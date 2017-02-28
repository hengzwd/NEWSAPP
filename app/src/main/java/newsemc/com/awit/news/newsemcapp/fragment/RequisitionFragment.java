package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.MeasureInfo;
import newsemc.com.awit.news.newsemcapp.dao.PlanInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProblemInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.MeasureInfoImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ZhengDiProgressImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ZhengProblemImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-6-30.
 */
public class RequisitionFragment extends Fragment implements HttpResultListener,View.OnClickListener {

    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View view1, view2;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中
    private List<ProblemInfo> problemInfoList;
    ZrcListView zhengchailist;
    Spinner spinner1;
    Spinner spinner2;
    Button button1;
    Button button2;
    private int pageNo = 1;

    private WebView webView;
    private String ssid,account;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //DDDD
        SharedPreferences testspec=getActivity().getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test",null);
        Log.i("special：：：：：：",special+"");

        if ("test".equals(special)){
            Log.i("进入if","");
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences=getActivity().getSharedPreferences("SPEC", CompanyNewSingleActivity.MODE_PRIVATE);
            ssid=SpecialsharedPreferences.getString("KEY","");
            Log.i("Specialssid", ssid);
            account=SpecialsharedPreferences.getString("ACCOUNT","");
            Log.i("SPECIALACCOUNT", account);
            // finish();

        }else{
            //清空特殊登录所保存的数据
            SharedPreferences.Editor editor=testspec.edit();
            editor.clear();
            editor.commit();
            Log.i("清空特殊登录的数据","进入else。。。。");
            //正常登录过来的
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
            ssid=sharedPreferences.getString("KEY","");
            Log.i("ssid", ssid + "");
            account=sharedPreferences.getString("ACCOUNT","");
            Log.i("ACCOUNT", account + "");
            //finish();


        }

        MeasureInfoImpl measureInfoImpl = new MeasureInfoImpl(new MyHTTPResultListener(2));
        measureInfoImpl.getProjectInfoList(ssid,"sh34");
        View view = (View)inflater.inflate(R.layout.requisitionfragment, null);
        initPager(view);
        initWebView();
        return  view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //征地拆迁-重难点问题
//        ZhengProblemImpl zhengProblemImpl=new ZhengProblemImpl(this);
//        zhengProblemImpl.getZhengProblemList("268","1","10");
    }

    public void initPager(View view) {
        dots = new ArrayList<View>();
        dots.add(view.findViewById(R.id.dot_1));
        dots.add(view.findViewById(R.id.dot_2));
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.requisitionplan, null);
        view2 = lf.inflate(R.layout.superversionfragment, null);
        viewList.add(view1);
        viewList.add(view2);
        mViewPager = (ViewPager)view.findViewById(R.id.vp);

        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        dots.get(0).setBackgroundResource(R.drawable.dot_focus);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub

                dots.get(oldPosition).setBackgroundResource(
                        R.drawable.dot_blur);
                dots.get(position)
                        .setBackgroundResource(R.drawable.dot_focus);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


        spinner1 = (Spinner)view1.findViewById(R.id.spinner);
        spinner2 = (Spinner)view2.findViewById(R.id.select);
        button1 = (Button)view1.findViewById(R.id.button);
        button1.setOnClickListener(this);
        button2 = (Button)view2.findViewById(R.id.but1);
        button2.setOnClickListener(this);

        zhengchailist = (ZrcListView) view2.findViewById(R.id.questions);
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zhengchailist.setHeadable(header);
        zhengchailist.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo = pageNo+1;
                String page = String.valueOf(pageNo);
                ZhengProblemImpl zhengProblemImpl=new ZhengProblemImpl(RequisitionFragment.this);
                zhengProblemImpl.getZhengProblemList(ssid,((MeasureInfo)spinner2.getAdapter().getItem(0)).getId(),page,"10");
                zhengchailist.setRefreshSuccess("加载成功"); // 通知加载成功
                zhengchailist.startLoadMore();
            }
        });
    }

    @JavascriptInterface
    public void initProgressChart(){
        ZhengDiProgressImpl zhengDiProgressImpl=new ZhengDiProgressImpl(new MyHTTPResultListener(1));
        zhengDiProgressImpl.getZhengProgressList(ssid,"sh34");
    }

    // 获取webView
    private void initWebView() {
        webView = (WebView)view1.findViewById(R.id.rp_webview);
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        int scalePercent = 100;

        switch (screenDensity) {
            case DisplayMetrics.DENSITY_HIGH:// 800*480
                scalePercent = 75;
                break;
            case DisplayMetrics.DENSITY_XHIGH:// 1280*720
                scalePercent = 100;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:// 1920*1080
                scalePercent = 150;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:// 2560*1440
                scalePercent = 200;
                break;
        }
        webView.setInitialScale(scalePercent);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true); // 设置WebView支持javascript
        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        settings.setSupportZoom(false); // 设置支持缩放
        settings.setBuiltInZoomControls(false);// 显示缩放控件
        settings.setLoadWithOverviewMode(true);
        webView.requestFocus();
        webView.addJavascriptInterface(this, "client");
        webView.loadUrl("file:///android_asset/rp_webview.html");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                String id1 = ((MeasureInfo)spinner1.getSelectedItem()).getId();
                Toast.makeText(getActivity(), id1 , Toast.LENGTH_LONG).show();
                break;
            case R.id.but1:
                String id2 = ((MeasureInfo)spinner2.getSelectedItem()).getId();
                ZhengProblemImpl zhengProblemImpl=new ZhengProblemImpl(RequisitionFragment.this);
                zhengProblemImpl.getZhengProblemList(ssid,id2,"1","10");
                Toast.makeText(getActivity(), id2 , Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        public ViewPagerAdapter() {
            super();
            // TODO Auto-generated constructor stub
            // 得到viewpager切换的三个布局，并把它们加入到viewList里面,记得view用打气筒生成，否则容易出现空指针异常

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewList.size();
        }

        // 是否是同一张图片
        @Override
        public boolean isViewFromObject(View view, Object object) {
            // TODO Auto-generated method stub
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            ((ViewPager) view).removeView(viewList.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            ((ViewPager) view).addView(viewList.get(position));
            return viewList.get(position);
        }
    }

    //征地拆迁-重难点问题
    private class ZhengchaiAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
       // private String[] data = {"1","2015年3月25日中心协调部与陕西省国土厅、西安市重点办、未央区国土一局同城协调。","督促未央区快速推进。","05-06"};
        private List<ProblemInfo> problemInfoList;
        private Context context;

        class ViewHolder{
            TextView type;
            TextView title;
            TextView time;
            TextView text;
        }

        public ZhengchaiAdapter(Context context,List<ProblemInfo> problemInfoList){
            this.context=context;
            this.problemInfoList=problemInfoList;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            Log.i("counts:", problemInfoList.size() + "");
            return problemInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return problemInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ProblemInfo m = (ProblemInfo)this.getItem(position);
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.super_list_item,null);
                holder.type = (TextView)convertView.findViewById(R.id.type);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.time= (TextView)convertView.findViewById(R.id.time);
                holder.text=(TextView)convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.type.setText(m.getCreateat());
            holder.title.setText(m.getProjectnam());
            holder.time.setText(m.getAdvice());
            holder.text.setText(m.getDetail());
            return convertView;
        }
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onResult(Object obj) {
        if(obj instanceof FailRequest){
            FailRequest  fail= (FailRequest)obj;
            if(!(fail==null)){
                System.out.println("异常信息："+fail.getMsg());
                System.out.println("状态："+fail.getStatus());
            }
        }else{
            problemInfoList = (List<ProblemInfo>) obj;
            if(problemInfoList.size() <= 0) {
                ZhengProblemImpl zhengProblemImpl=new ZhengProblemImpl(RequisitionFragment.this);
                zhengProblemImpl.getZhengProblemList(ssid,((MeasureInfo)spinner2.getAdapter().getItem(0)).getId(),
                        pageNo>1?String.valueOf(pageNo-1):String.valueOf(pageNo), "10");

            }else {
                ZhengchaiAdapter adapter = new ZhengchaiAdapter(getActivity(), problemInfoList);
                zhengchailist.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onFinish() {

    }

    class MyHTTPResultListener implements HttpResultListener{
        private int type;

        public MyHTTPResultListener(int type){
            this.type = type;
        }
        @Override
        public void onStartRequest() {

        }

        @Override
        public void onResult(Object obj) {
            if(obj instanceof FailRequest){
                FailRequest  fail= (FailRequest)obj;
                if(!(fail==null)){
                    System.out.println("异常信息："+fail.getMsg());
                    System.out.println("状态："+fail.getStatus());
                }
            }else{
                switch (type){
                    case 1:
                        List<PlanInfo> list = (List<PlanInfo>)obj;
                        final JSONArray projectnam = new JSONArray();
                        final JSONArray chaikaiper = new JSONArray();;
                        final JSONArray linkaiper = new JSONArray();;
                        final JSONArray yongkaiper = new JSONArray();;
                        for(int i=0;i<list.size();i++){
                            projectnam.put(list.get(i).getProjectnam());
                            chaikaiper.put(list.get(i).getChaikaiper());
                            linkaiper.put(list.get(i).getLinkaiper());
                            yongkaiper.put(list.get(i).getYongkaiper());
                        }
                        Message msg = new Message();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("projectnam",projectnam.toString());
                        map.put("chaikaiper",chaikaiper.toString());
                        map.put("linkaiper",linkaiper.toString());
                        map.put("yongkaiper",yongkaiper.toString());
                        msg.obj = map;
                        handler.sendMessage(msg);
                        break;
                    case 2:
                        List<MeasureInfo> measureInfoList = (List<MeasureInfo>)obj;
                        ArrayAdapter<MeasureInfo> adapter = new ArrayAdapter<MeasureInfo>(getActivity(),
                                android.R.layout.simple_spinner_item, measureInfoList );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        spinner2.setAdapter(adapter);
                        ZhengProblemImpl zhengProblemImpl=new ZhengProblemImpl(RequisitionFragment.this);
                        zhengProblemImpl.getZhengProblemList(ssid,((MeasureInfo)spinner2.getAdapter().getItem(0)).getId(), "1", "10");
                        break;
                    default:
                        break;
                }

            }
        }

        @Override
        public void onFinish() {

        }
    }

    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            Map map = (Map) msg.obj;
            final String projectnam = (String) map.get("projectnam");
            final String chaikaiper = (String) map.get("chaikaiper");
            final String linkaiper = (String) map.get("linkaiper");
            final String yongkaiper = (String) map.get("yongkaiper");
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:displayChart(" +
                            projectnam + "," + chaikaiper + "," + linkaiper + "," + yongkaiper + ")");
                }
            });
        }
    };
}
