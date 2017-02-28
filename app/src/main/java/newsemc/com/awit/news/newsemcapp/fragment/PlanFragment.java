package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.adapter.PlanTotalAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.MeasureInfo;
import newsemc.com.awit.news.newsemcapp.dao.ZdgcInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.MeasureInfoImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ZdgcImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-6-29.
 */
public class PlanFragment extends Fragment implements View.OnClickListener{
    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View view1, view2;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中

    private ZrcListView zrcListView1;
    private ZrcListView zrcListView2;

    Button button1,button2;
    Spinner spinner1,spinner2;
    private int pageNo1 = 1;
    private int pageNo2 = 1;
    private String ssid;
    private String account;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.jinduguanlifragment, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        initPager();
        MeasureInfoImpl measureInfoImpl = new MeasureInfoImpl(new MyHTTPResultListener(3));
        measureInfoImpl.getProjectInfoList(ssid,account);

    }

    public void initPager() {
        dots = new ArrayList<View>();
        dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.planfragment, null);
        view2 = lf.inflate(R.layout.plantotalfragment, null);
        viewList.add(view1);
        viewList.add(view2);
        mViewPager = (ViewPager) getView().findViewById(R.id.vp);

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

        spinner1 = (Spinner)view1.findViewById(R.id.spinner1);
        spinner2 = (Spinner)view2.findViewById(R.id.spinner2);
        button1 = (Button)view1.findViewById(R.id.but1);
        button1.setOnClickListener(this);
        button2 = (Button)view2.findViewById(R.id.but2);
        button2.setOnClickListener(this);

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        //进度管理-重点工程
        zrcListView1 = (ZrcListView)view1.findViewById(R.id.jindu_tab_lv);
        zrcListView1.setHeadable(header);
        zrcListView1.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo1 = pageNo1 + 1;
                String pageNo = String.valueOf(pageNo1);
                ZdgcImpl zdgcImpl = new ZdgcImpl(new MyHTTPResultListener(1));
                zdgcImpl.getZdgcList(ssid,((MeasureInfo)spinner1.getAdapter().getItem(0)).getId(), "1", "10");
                zrcListView1.setRefreshSuccess("加载成功"); // 通知加载成功
                zrcListView1.startLoadMore();
            }
        });

        zrcListView2 = (ZrcListView)view2.findViewById(R.id.total_lv);
        zrcListView2.setHeadable(header);
        zrcListView2.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo2 = pageNo2 + 1;
                String pageNo = String.valueOf(pageNo2);

                zrcListView2.setRefreshSuccess("加载成功"); // 通知加载成功
                zrcListView2.startLoadMore();
            }
        });
        zrcListView2.setAdapter(new PlanTotalAdapter(getActivity()));
    }

    @Override
    public void onClick(View v) {
        String id;
        switch (v.getId()){
            case R.id.but1:
                id = ((MeasureInfo)spinner1.getSelectedItem()).getId();
                ZdgcImpl zdgcImpl = new ZdgcImpl(new MyHTTPResultListener(1));
                zdgcImpl.getZdgcList(ssid,id, "1", "10");
                Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();
                break;
            case R.id.but2:
                id = ((MeasureInfo)spinner2.getSelectedItem()).getId();
                Toast.makeText(getActivity(), id, Toast.LENGTH_LONG).show();
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

    private class JinduAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private List<ZdgcInfo> list;
        class ViewHolder{
            TextView j_1;
            TextView j_2;
            TextView j_3;
            TextView j_4;
            TextView j_5;
            TextView j_6;
            TextView j_7;
            TextView j_8;
            TextView j_9;
            TextView j_10;
//            TextView j_11;
//            TextView j_12;
//            TextView j_13;
//            TextView j_14;
//            TextView j_15;
//            TextView j_16;
//            TextView j_17;
//            TextView j_18;
//            TextView j_19;
        }

        public JinduAdapter(Context context,List<ZdgcInfo> list){
            mInflater = LayoutInflater.from(context);
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.plan_list_item,null);
                holder.j_1 = (TextView)convertView.findViewById(R.id.j_1);
                holder.j_2 = (TextView)convertView.findViewById(R.id.j_2);
                holder.j_3 = (TextView)convertView.findViewById(R.id.j_3);
                holder.j_4 = (TextView)convertView.findViewById(R.id.j_4);
                holder.j_5 = (TextView)convertView.findViewById(R.id.j_5);
                holder.j_6 = (TextView)convertView.findViewById(R.id.j_6);
                holder.j_7 = (TextView)convertView.findViewById(R.id.j_7);
                holder.j_8 = (TextView)convertView.findViewById(R.id.j_8);
                holder.j_9 = (TextView)convertView.findViewById(R.id.j_9);
                holder.j_10 = (TextView)convertView.findViewById(R.id.j_10);
//                holder.j_11 = (TextView)convertView.findViewById(R.id.j_11);
//                holder.j_12 = (TextView)convertView.findViewById(R.id.j_12);
//                holder.j_13 = (TextView)convertView.findViewById(R.id.j_13);
//                holder.j_14 = (TextView)convertView.findViewById(R.id.j_14);
//                holder.j_15 = (TextView)convertView.findViewById(R.id.j_15);
//                holder.j_16 = (TextView)convertView.findViewById(R.id.j_16);
//                holder.j_17 = (TextView)convertView.findViewById(R.id.j_17);
//                holder.j_18 = (TextView)convertView.findViewById(R.id.j_18);
//                holder.j_19 = (TextView)convertView.findViewById(R.id.j_19);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.j_1.setText(list.get(position).getBuildname());
            holder.j_2.setText(list.get(position).getDesigntype());
            holder.j_3.setText(list.get(position).getDesignnum());
            holder.j_4.setText(list.get(position).getDayfinish());
            holder.j_5.setText(list.get(position).getMothfinish());
            holder.j_6.setText(list.get(position).getKailei());
            holder.j_7.setText(list.get(position).getKaileiratio());
            holder.j_8.setText(list.get(position).getPlandate());
            holder.j_9.setText(list.get(position).getDelaynum());
            holder.j_10.setText(list.get(position).getDelaydays());
//            holder.j_11.setText("123");
//            holder.j_12.setText("123");
//            holder.j_13.setText("123");
//            holder.j_14.setText("123");
//            holder.j_15.setText("123");
//            holder.j_16.setText("123");
//            holder.j_17.setText("123");
//            holder.j_18.setText("123");
//            holder.j_19.setText("123");
            return convertView;
        }
    }

    class MyHTTPResultListener implements HttpResultListener {
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
                        List<ZdgcInfo> zdgcInfoList = (List<ZdgcInfo>)obj;
                        if(zdgcInfoList.size()<=0) {
                            ZdgcImpl zdgcImpl = new ZdgcImpl(new MyHTTPResultListener(1));
                            zdgcImpl.getZdgcList(ssid,((MeasureInfo)spinner1.getAdapter().getItem(0)).getId(),
                                    pageNo1>1?String.valueOf(pageNo1-1):String.valueOf(pageNo1), "10");

                        }else {
                            zrcListView1.setAdapter(new JinduAdapter(getActivity(), zdgcInfoList));
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        List<MeasureInfo> measureInfoList = (List<MeasureInfo>)obj;
                        ArrayAdapter<MeasureInfo> adapter = new ArrayAdapter<MeasureInfo>(getActivity(),
                                android.R.layout.simple_spinner_item, measureInfoList );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        spinner2.setAdapter(adapter);
                        ZdgcImpl zdgcImpl = new ZdgcImpl(new MyHTTPResultListener(1));
                        zdgcImpl.getZdgcList(ssid,((MeasureInfo)spinner2.getAdapter().getItem(0)).getId(), "1", "10");
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
