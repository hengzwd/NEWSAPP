package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.adapter.GygfAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.AuditInfo;
import newsemc.com.awit.news.newsemcapp.dao.CraftInfo;
import newsemc.com.awit.news.newsemcapp.dao.PreceptInfo;
import newsemc.com.awit.news.newsemcapp.dao.ScienceInfo;
import newsemc.com.awit.news.newsemcapp.dao.WorkpieceInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.FangAnLZImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.GYGFImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.JSJDImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.SGTSHImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ShouJGCImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 2015/6/30.
 */
public class DataMaterailFragment extends Fragment {
    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View view1, view2, view3,view4,view5;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中
    private ZrcListView shigongtu;
    private ZrcListView jishujiaodi;
    private ZrcListView shouyeproject;
    private ZrcListView fanganlunzheng;
    private ZrcListView gongyigongfa;
    private GridView gridView;
    private List<Map<String,CraftInfo>> craft_view;
    private SwipeRefreshLayout swipeLayout;

    private int pageNo1 = 1;
    private int pageNo2 = 1;
    private int pageNo3 = 1;
    private int pageNo4 = 1;
    private int pageNo5 = 1;
    private String ssid,account;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.datamaterailfragment, null);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();
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

        SGTSHImpl sgtsh = new SGTSHImpl(new MyHTTPResultListener(1));
        sgtsh.getSiGongShenHeList(ssid,"1", "10");

        JSJDImpl jsjd = new JSJDImpl(new MyHTTPResultListener(2));
        jsjd.getJiShuJiaoDiList(ssid,"1", "10");

        ShouJGCImpl shouJGC = new ShouJGCImpl(new MyHTTPResultListener(3));
        shouJGC.getShouProjectList(ssid,"1", "10");

        FangAnLZImpl fangAnLZ = new FangAnLZImpl(new MyHTTPResultListener(4));
        fangAnLZ.getFangAnLZList(ssid,"1","10");

        GYGFImpl gygfImpl=new GYGFImpl(new MyHTTPResultListener(5));
        gygfImpl.getGongYiGongFaList(ssid,"1","10");
    }

    public void initPager() {
        // 添加当前Acitivity到ancivitylist里面去，为了方便统一退出
        // 显示的点
        dots = new ArrayList<View>();
        dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        dots.add(getView().findViewById(R.id.dot_3));
        dots.add(getView().findViewById(R.id.dot_4));
        dots.add(getView().findViewById(R.id.dot_5));
        // 得到viewPager的布局
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.shigongtu, null);
        view2 = lf.inflate(R.layout.jishujiaodi, null);
        view3 = lf.inflate(R.layout.shouyeproject, null);
        view4=lf.inflate(R.layout.fanganlunzheng,null);
        view5=lf.inflate(R.layout.gongyigongfa,null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        // 找到点击进入那个按钮
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

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        //技术资料-施工图及审核
        shigongtu=(ZrcListView)view1.findViewById(R.id.shigongtu);
        shigongtu.setHeadable(header);
        shigongtu.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo1 = pageNo1+1;
                String pageNo = String.valueOf(pageNo1);
                SGTSHImpl sgtsh = new SGTSHImpl(new MyHTTPResultListener(1));
                sgtsh.getSiGongShenHeList(ssid,pageNo,"10");
                shigongtu.setRefreshSuccess("加载成功"); // 通知加载成功
                shigongtu.startLoadMore();
            }
        });


        //技术资料-技术交底
        jishujiaodi=(ZrcListView)view2.findViewById(R.id.jishujiaodi);
        jishujiaodi.setHeadable(header);
        jishujiaodi.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo2 = pageNo2+1;
                String pageNo = String.valueOf(pageNo2);
                JSJDImpl jsjd = new JSJDImpl(new MyHTTPResultListener(2));
                jsjd.getJiShuJiaoDiList(ssid,pageNo, "10");
                jishujiaodi.setRefreshSuccess("加载成功"); // 通知加载成功
                jishujiaodi.startLoadMore();
            }
        });

        //技术资料-首页工程
        shouyeproject =(ZrcListView)view3.findViewById(R.id.shouyeproject);
        shouyeproject.setHeadable(header);
        shouyeproject.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo3 = pageNo3+1;
                String pageNo = String.valueOf(pageNo3);
                ShouJGCImpl shouJGC = new ShouJGCImpl(new MyHTTPResultListener(3));
                shouJGC.getShouProjectList(ssid,pageNo, "10");
                shouyeproject.setRefreshSuccess("加载成功"); // 通知加载成功
                shouyeproject.startLoadMore();
            }
        });


        //技术资料-方案论证
        fanganlunzheng=(ZrcListView)view4.findViewById(R.id.fanganlunzheng);
        fanganlunzheng.setHeadable(header);
        fanganlunzheng.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo4 = pageNo4+1;
                String pageNo = String.valueOf(pageNo4);
                FangAnLZImpl fangAnLZ = new FangAnLZImpl(new MyHTTPResultListener(4));
                fangAnLZ.getFangAnLZList(ssid,pageNo, "10");
                fanganlunzheng.setRefreshSuccess("加载成功"); // 通知加载成功
                fanganlunzheng.startLoadMore();
            }
        });


        //技术资料-工艺工法
        swipeLayout = (SwipeRefreshLayout)view5.findViewById(R.id.swipe_refresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        pageNo5 = pageNo5 + 1;
                        swipeLayout.setRefreshing(false);
                        GYGFImpl gygfImpl=new GYGFImpl(new MyHTTPResultListener(5));
                        gygfImpl.getGongYiGongFaList(ssid,String.valueOf(pageNo5), "10");
                    }
                }, 500);
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


    //技术资料-施工图及审核
    private class ShigongtuAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<AuditInfo> list;

        class ViewHolder{
            TextView num;
            TextView projectname;
            TextView data;
            TextView sum;
        }

        public ShigongtuAdapter(Context context,List<AuditInfo> list){
            mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            AuditInfo auditInfo = (AuditInfo)list.get(position);
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.shigongtu_item_list,null);
                holder.num = (TextView)convertView.findViewById(R.id.num);
                holder.projectname = (TextView)convertView.findViewById(R.id.projectname);
                holder.data= (TextView)convertView.findViewById(R.id.data);
                holder.sum=(TextView)convertView.findViewById(R.id.sum);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.num.setText("");
            holder.projectname.setText(auditInfo.getName());
            holder.data.setText(auditInfo.getZiliao());
            holder.sum.setText(auditInfo.getCounts());
            return convertView;
        }
    }

    //技术资料-技术交底
    private class JishujiaodiAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<ScienceInfo> list;
        class ViewHolder{
            TextView num;
            TextView projectname;
            TextView data;
            TextView sum;
            TextView sheji;
            TextView shigong;
        }

        public JishujiaodiAdapter(Context context,List<ScienceInfo> list) {
            mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.jishujiaodi_list_item,null);
                holder.num = (TextView)convertView.findViewById(R.id.num);
                holder.projectname = (TextView)convertView.findViewById(R.id.projectname);
                holder.data= (TextView)convertView.findViewById(R.id.data);
                holder.sum=(TextView)convertView.findViewById(R.id.sum);
                holder.sheji=(TextView)convertView.findViewById(R.id.sheji);
                holder.shigong=(TextView)convertView.findViewById(R.id.shigong);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.num.setText(list.get(position).getId());
            holder.projectname.setText(list.get(position).getName());
            holder.data.setText(list.get(position).getContent());
            holder.sum.setText(list.get(position).getCtime());
            holder.sheji.setText(list.get(position).getShedanwei());
            holder.shigong.setText(list.get(position).getShidanwei());
            return convertView;
        }
    }

    //技术资料-首页工程
    private class ShouyeprojectAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<WorkpieceInfo> list;
//        private String[] data ={"1","宝兰客运专线陕西段","中铁十九局","无咋轨道施工","2015-3-25","无咋轨道首件施工中，对底座，道床板外形控制标准高，要求严"} ;


        class ViewHolder{
            TextView num;
            TextView projectname;
            TextView data;
            TextView sum;
            TextView sheji;
            TextView shigong;
        }

        public ShouyeprojectAdapter(Context context,List<WorkpieceInfo> list){
            mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.jishujiaodi_list_item,null);
                holder.num = (TextView)convertView.findViewById(R.id.num);
                holder.projectname = (TextView)convertView.findViewById(R.id.projectname);
                holder.data= (TextView)convertView.findViewById(R.id.data);
                holder.sum=(TextView)convertView.findViewById(R.id.sum);
                holder.sheji=(TextView)convertView.findViewById(R.id.sheji);
                holder.shigong=(TextView)convertView.findViewById(R.id.shigong);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.num.setText(list.get(position).getId());
            holder.projectname.setText(list.get(position).getName());
            holder.data.setText(list.get(position).getShidanwei());
            holder.sum.setText(list.get(position).getType());
            holder.sheji.setText(list.get(position).getCtime());
            holder.shigong.setText(list.get(position).getContent());
            return convertView;
        }
    }

    //技术资料-方案论证
    private class FanganyanzhengAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private List<PreceptInfo> list;
//        private String[] data ={"1","2015-3-15","西安至宝鸡客运专线路基沉降观测技术研讨会","西成陕西客专公司、铁一院、铁科院","《西安至宝鸡客运专线路基沉降观测技术》"} ;


        class ViewHolder{
            TextView num;
            TextView projectname;
            TextView data;
            TextView sum;
            TextView sheji;
        }

        public FanganyanzhengAdapter(Context context,List<PreceptInfo> list){
            mInflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.fanganlunzheng_item_list,null);
                holder.num = (TextView)convertView.findViewById(R.id.num);
                holder.projectname = (TextView)convertView.findViewById(R.id.projectname);
                holder.data= (TextView)convertView.findViewById(R.id.data);
                holder.sum=(TextView)convertView.findViewById(R.id.sum);
                holder.sheji=(TextView)convertView.findViewById(R.id.sheji);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.num.setText(list.get(position).getId());
            holder.projectname.setText(list.get(position).getHuiyitime());
            holder.data.setText(list.get(position).getHuiyiname());
            holder.sum.setText(list.get(position).getDanwei());
            holder.sheji.setText(list.get(position).getJiyao());
            return convertView;
        }
    }


    class MyHTTPResultListener implements HttpResultListener {
        private int type;
        public  MyHTTPResultListener(int type){
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
                        List<AuditInfo> auditInfoList = (List<AuditInfo>)obj;
                        if(auditInfoList.size()>0){
                            shigongtu.setAdapter(new ShigongtuAdapter(getActivity(),auditInfoList));
                        }else {
                            SGTSHImpl sgtsh = new SGTSHImpl(new MyHTTPResultListener(1));
                            sgtsh.getSiGongShenHeList(ssid,pageNo1>1?String.valueOf(pageNo1-1):String.valueOf(pageNo1), "10");
                        }
                        break;
                    case 2:
                        List<ScienceInfo> scienceInfoList = (List<ScienceInfo>)obj;
                        if(scienceInfoList.size()>0){
                            jishujiaodi.setAdapter(new JishujiaodiAdapter(getActivity(),scienceInfoList));
                        }else {
                            JSJDImpl jsjd = new JSJDImpl(new MyHTTPResultListener(2));
                            jsjd.getJiShuJiaoDiList(ssid,pageNo2>1?String.valueOf(pageNo2-1):String.valueOf(pageNo2), "10");
                        }

                        break;
                    case 3:
                        List<WorkpieceInfo> workpieceInfoList = (List<WorkpieceInfo>)obj;
                        if(workpieceInfoList.size()>0){
                            shouyeproject.setAdapter(new ShouyeprojectAdapter(getActivity(),workpieceInfoList));
                        }else {
                            ShouJGCImpl shouJGC = new ShouJGCImpl(new MyHTTPResultListener(3));
                            shouJGC.getShouProjectList(ssid,pageNo3>1?String.valueOf(pageNo3-1):String.valueOf(pageNo3), "10");
                        }

                        break;
                    case 4:
                        List<PreceptInfo> preceptInfoList = (List<PreceptInfo>)obj;
                        if(preceptInfoList.size()>0){
                            fanganlunzheng.setAdapter(new FanganyanzhengAdapter(getActivity(),preceptInfoList));
                        }else{
                            FangAnLZImpl fangAnLZ = new FangAnLZImpl(new MyHTTPResultListener(4));
                            fangAnLZ.getFangAnLZList(ssid,pageNo4>1?String.valueOf(pageNo4-1):String.valueOf(pageNo4), "10");
                        }

                        break;
                    case 5:
                        craft_view=(List<Map<String,CraftInfo>>)obj;
                        gridView=(GridView)view5.findViewById(R.id.gridview);
                        if(craft_view.size()>0){
                            gridView.setAdapter(new GygfAdapter(getActivity(),craft_view));
                        }else {
                            GYGFImpl gygfImpl=new GYGFImpl(new MyHTTPResultListener(5));
                            gygfImpl.getGongYiGongFaList(ssid,pageNo5>1?String.valueOf(pageNo5-1):String.valueOf(pageNo5), "10");
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
