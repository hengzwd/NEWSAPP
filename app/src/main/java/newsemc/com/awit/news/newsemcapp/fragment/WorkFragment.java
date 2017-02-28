package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.adapter.DiaoDuAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.dao.DiaoDuInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.DiaoDuImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-6-29.
 */
public class WorkFragment extends Fragment{
    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View view1, view2;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中

    ZrcListView zhongyao;
    ZrcListView diaodu;
    private int pageNo1 = 1;
    private int pageNo2 =1;
    private  String ssid,account;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.requisitionfragment, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();
    }

    public void initPager() {
        dots = new ArrayList<View>();
        dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.zhongyaowenjianfragment, null);
        view2 = lf.inflate(R.layout.workfragment, null);
        viewList.add(view1);
        viewList.add(view2);
        mViewPager = (ViewPager)getView(). findViewById(R.id.vp);

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
        //getWebView(view1);
        //工作动态-重要文件
        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHttpResultListener(1));
        imgInfoListImpl.getImgInfoList(ssid,"liguobin", "13", "1", "10", "1");
        //工作动态-调度公告
        DiaoDuImpl diaoDuImpl = new DiaoDuImpl(new MyHttpResultListener(2));
        diaoDuImpl.getDiaoDuInfoList(ssid,"liguobin", "14", "1", "10", "1");

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        zhongyao = (ZrcListView) view1.findViewById(R.id.work);
        zhongyao.setHeadable(header);
        zhongyao.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo1 = pageNo1+1;
                String page = String.valueOf(pageNo1);
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHttpResultListener(1));
                imgInfoListImpl.getImgInfoList(ssid,"liguobin", "13", page, "10", "1");
                zhongyao.setRefreshSuccess("加载成功"); // 通知加载成功
                zhongyao.startLoadMore();
            }
        });
        diaodu = (ZrcListView)view2.findViewById(R.id.work);
        diaodu.setHeadable(header);
        diaodu.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo2 = pageNo2+1;
                String page = String.valueOf(pageNo2);
                DiaoDuImpl diaoDuImpl = new DiaoDuImpl(new MyHttpResultListener(2));
                diaoDuImpl.getDiaoDuInfoList(ssid,"liguobin", "14", page, "10", "1");
                diaodu.setRefreshSuccess("加载成功"); // 通知加载成功
                diaodu.startLoadMore();
            }
        });
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
    private class WorkAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
       // private String[] data = {"总","关于开展2015年5月份月度专项检查的通知","05-06","50人已读"};
        private List<ImgInfoList> data;

       class ViewHolder{
            TextView type;
            TextView title;
            TextView time;
//            TextView read;
        }

        public WorkAdapter(Context context,List<ImgInfoList> data){
            mInflater = LayoutInflater.from(context);
            this.data=data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ImgInfoList m=(ImgInfoList) this.getItem(position);
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.work_list_item,null);
                holder.type = (TextView)convertView.findViewById(R.id.type);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.time= (TextView)convertView.findViewById(R.id.time);
//                holder.read = (TextView)convertView.findViewById(R.id.read);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.type.setText(m.getInfoid());
            holder.title.setText(m.getInfoname());
            holder.time.setText(m.getInfodate());
//            holder.read.setText(m.getInfoimg());
            return convertView;
        }
    }
    class MyHttpResultListener implements HttpResultListener {
        private int type;

        public MyHttpResultListener(int type){
            this.type = type;
        }
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
                switch (type) {
                    case 1:
                        //重要文件
                        List<ImgInfoList> imps = (List<ImgInfoList>) obj;
                        Log.i("projectinfo", imps + "");
                        if(imps.size()>0){
                            WorkAdapter adapter = new WorkAdapter(getActivity(), imps);
                            zhongyao.setAdapter(adapter);
                        }else{
                            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHttpResultListener(1));
                            imgInfoListImpl.getImgInfoList(ssid,"liguobin", "13",
                                    pageNo1>1?String.valueOf(pageNo1-1):String.valueOf(pageNo1), "10", "1");

                        }

                        break;
                    case 2:
                        //调度公告
                        List<DiaoDuInfo> compwnylist = (List<DiaoDuInfo>) obj;
                        if(compwnylist.size()>0){
                            diaodu.setAdapter(new DiaoDuAdapter(getActivity(), compwnylist));
                        }else {
                            DiaoDuImpl diaoDuImpl = new DiaoDuImpl(new MyHttpResultListener(2));
                            diaoDuImpl.getDiaoDuInfoList(ssid,"liguobin", "14",
                                    pageNo2>1?String.valueOf(pageNo2-1):String.valueOf(pageNo2), "10", "1");
                        }

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
}
