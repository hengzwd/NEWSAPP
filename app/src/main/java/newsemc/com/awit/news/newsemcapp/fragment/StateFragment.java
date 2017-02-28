package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.SingleActivity;
import newsemc.com.awit.news.newsemcapp.adapter.CompanyNewsAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.MeasureInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectTtrendsInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyNewImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.MeasureInfoImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ProjectInfoInterImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ProjectTrendsImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.LoginSsidInter;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-10-19.
 */
public class StateFragment extends Fragment implements View.OnClickListener,ZrcListView.OnItemClickListener {

    private final String TAG = "StateFragment";
    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View view1, view2, view3;//view4;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中
    private ListView projectbaselist;
    private ListView sectsitelist;
    private ListView jianlisectlist;
    private List<CompanyInfo> compwnylist,compwnylist1;
    private ListView shejisectlist;
    //二三页的listview显示
    private ListView listView1,listview2;
    Button button1,button2,button3,button4;
    Spinner spinner1,spinner2,spinner3,spinner4;
    private String ssid;
    private String account;
    private ListView worklistview;
    ZrcListView zrcListView,zrcListView2;
    private int pageNo1 = 0;
    private int pageNo2 = 0;
    private TextView prp1,prp2,prp3;
    private Button back1,back2,back3;
    private CompanyInfoService companyInfoService;
    private ImageInfoService imageInfoService;
    List<CompanyInfo> read_cunzai_jianbao = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_next_jianbao= new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_jianbao = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_cunzai = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_next = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_cun_next = new ArrayList<CompanyInfo>();
    List<CompanyInfo> list_TongBao = new ArrayList<CompanyInfo>();
    List<CompanyInfo> list_JianBao = new ArrayList<CompanyInfo>();
    private String Page1 = 0+"";
    private String Page2 = 0+"";
    List<CompanyInfo> read_up1 = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_up2 = new ArrayList<CompanyInfo>();
    private Handler handler;
    //图片新闻请求码
    private static  final int IMGCODE=1;
    //公司要闻请求码
    private static final int NEWCODE=2;
    private WorkAdapter zrclistview_adapter1;
    private WorkAdapter zrclistview_adapter2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //取得从specialactivity存储的Spec
       // worklistview=(ListView)view.findViewById(R.id.worklistview);
        //取得从specialactivity存储的Spec
        SharedPreferences spec=getActivity().getSharedPreferences("speciallogin", MainActivity.MODE_PRIVATE);
        String specialName=spec.getString("special","");
        Log.i("specialName", "");
        if (specialName.equals("special")){
            Log.i("进入if","");
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences=getActivity().getSharedPreferences("SPEC", CompanyNewSingleActivity.MODE_PRIVATE);
            ssid=SpecialsharedPreferences.getString("KEY","");
            Log.i("Specialssid",ssid );
            account=SpecialsharedPreferences.getString("ACCOUNT","");
            Log.i("SPECIALACCOUNT",account);

        }else{
            //正常登录过来的
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP",CompanyNewSingleActivity.MODE_PRIVATE);
            ssid=sharedPreferences.getString("KEY","");
            Log.i("ssid", ssid + "");
            account=sharedPreferences.getString("ACCOUNT","");
            Log.i("ACCOUNT", account + "");

        }

        handler = new Handler();

        companyInfoService = CompanyInfoService.getInstance(getActivity());
       // companyInfoService.deleteAllCompanyInfo();

       //项目基础信息
        MeasureInfoImpl measureInfoImpl = new MeasureInfoImpl(new MyHTTPResultListener(1));
        measureInfoImpl.getProjectInfoList(ssid, account);

        zrclistview_adapter1 = new WorkAdapter(getActivity(),read_up1);
        zrcListView.setAdapter(zrclistview_adapter1);
        zrcListView.refresh();

        zrclistview_adapter2 = new WorkAdapter(getActivity(),read_up2);
        zrcListView2.setAdapter(zrclistview_adapter2);
        zrcListView2.refresh();

        return  inflater.inflate(R.layout.statefragment, null);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(0xff33bbee);
        zrcListView = (ZrcListView)view2.findViewById(R.id.mainlistview);
        zrcListView.setHeadable(header);
        zrcListView.setFootable(footer);
        zrcListView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        //Company_Tongbao_View_first(8 + "", 1 + "");


        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
//                pageNo1 = pageNo1 + 1;
//                String page = String.valueOf(pageNo1);
//                Company_Tongbao_View_next(8 + "", page);
                refresh_tongbao();
            }
        });
        zrcListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                load1_tongbao();
            }
        });

        zrcListView2 = (ZrcListView)view3.findViewById(R.id.mainlistview);
        zrcListView2.setHeadable(header);
        zrcListView2.setFootable(footer);
        zrcListView2.setItemAnimForBottomIn(R.anim.bottomitem_in);
        Company_Jianbao_View_first(7 + "", 1 + "");

        zrcListView2.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo2 = pageNo2 + 1;
                String page = String.valueOf(pageNo2);
                Company_Jianbao_View_next(7 + "", page);
                //refresh_jianbao();
            }
        });
        zrcListView2.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                load2_jianbao();
            }
        });

    }

    private void refresh_tongbao(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pageNo1 == 0){
                    pageNo1 = 1;
                    String page = String.valueOf(pageNo1);
                    if(companyInfoService.queryCompanyInfotype_pageno(8+"",page).size() == 0){
                        CompanyImpl companyImpl=new CompanyImpl(new MyHTTPResultListener(3));
                        companyImpl.getCompanyInfoList(ssid, account,"0", 8+"", page, "13", "2");
                        zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                        zrcListView.startLoadMore();
                    }else {
                        read_cunzai = companyInfoService.queryCompanyInfotype_pageno(8+"",page);
                        read_up1.addAll(read_cunzai);
                        zrclistview_adapter1.notifyDataSetChanged();
                        zrcListView.setRefreshSuccess("加载成功");
                        Item_click1(read_up1);
                    }
                }else {
                    if(read_up1.size() <= 13){
                        pageNo1 = pageNo1 + 1;
                        String page = String.valueOf(pageNo1);
                        CompanyImpl companyImpl = new CompanyImpl(new MyHTTPResultListenerNext());
                        companyImpl.getCompanyInfoList(ssid, account,"0", 8+"", page, "13", "2");
                        zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                        zrcListView.startLoadMore();
                    }else {
                        zrcListView.setRefreshFail("加载失败");
                    }
                }
            }
        },2*1000);
    }

    private void load1_tongbao(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo1 = pageNo1 + 1;
                String page = String.valueOf(pageNo1);
                List<String> Num1 = new ArrayList<String>();
                for(int i = 0 ; i < read_up1.size() ; i++){
                    if(read_up1.get(i).getPageno().equals(page)){
                        Num1.add(read_up1.get(i).getPageno());
                    }
                }
                if(companyInfoService.queryCompanyInfotype_pageno(8+"",page).size() > 0){
                    for(int i = 0 ;i <read_up1.size() ; i++){
                       if(read_up1.get(i).getPageno().equals(page)&& Num1.size() >0){
                           break;
                       }else {
                           list_TongBao = companyInfoService.queryCompanyInfotype_pageno(8+"",page);
                           read_up1.addAll(list_TongBao);
                           break;
                       }
                    }
//                    WorkAdapter adapter1 = new WorkAdapter(getActivity(), read_up1);
//                    zrcListView.setAdapter(adapter1);
                    zrclistview_adapter1.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Item_click1(read_up1);
                }else {
                    CompanyImpl companyImpl = new CompanyImpl(new MyHTTPResultListenerNext1());
                    companyImpl.getCompanyInfoList(ssid, account,"0", 8+"", page, "13", "2");
                    zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                    zrcListView.startLoadMore();
                }
            }
        },2*1000);
    }

    private void refresh_jianbao(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pageNo2 == 0){
                    pageNo2 = 1;

                }else {
                    zrcListView2.setRefreshFail("加载失败");
                }
            }
        },2*1000);
    }

    private void load2_jianbao(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo2 = pageNo2 + 1;
                String page = String.valueOf(pageNo2);
                List<String> Num = new ArrayList<String>();
                for(int i = 0 ; i < read_up2.size() ; i++){
                    if(read_up2.get(i).getPageno().equals(page)){
                        Num.add(read_up2.get(i).getPageno());
                    }
                }
                if(companyInfoService.queryCompanyInfotype_pageno(7+"",page).size() > 0){
                    for(int i = 0 ; i < read_up2.size() ; i ++){
                        if(read_up2.get(i).getPageno().equals(page) && Num.size() > 0){
                            break;
                        }else {
                            list_JianBao = companyInfoService.queryCompanyInfotype_pageno(7+"",page);
                            read_up2.addAll(list_JianBao);
                            break;
                        }
                    }
                    CompanyNewsAdapter companyNewsAdapter = new CompanyNewsAdapter(getActivity(), read_up2);
                    zrcListView2.setAdapter(companyNewsAdapter);
                    Item_click2(read_up2);
                    companyNewsAdapter.notifyDataSetChanged();
                    zrcListView2.setLoadMoreSuccess();
                }else {
                    CompanyImpl newsjianbao = new CompanyImpl(new CompanyHttpResultListenernext1());
                    newsjianbao.getCompanyInfoList(ssid, account,"0",7+"", page, "13", "2");
                    zrcListView2.setRefreshSuccess("加载成功"); // 通知加载成功
                    zrcListView2.startLoadMore();
                }
            }
        },2*1000);
    }

//    private void Company_Tongbao_View_first(String infotype , String pageno ){
//        if(companyInfoService.queryCompanyInfotype_pageno(infotype,pageno).size() == 0){
//            //情况通报
//            CompanyImpl companyImpl=new CompanyImpl(new MyHTTPResultListener(3));
//            companyImpl.getCompanyInfoList(ssid, account, infotype, pageno, "13", "2");
//        }else {
//            read_cunzai = companyInfoService.queryCompanyInfotype_pageno(infotype,pageno);
//            read_up1.addAll(read_cunzai);
//            WorkAdapter workAdapter = new WorkAdapter(getActivity(), read_cunzai);
//            zrcListView.setAdapter(workAdapter);
//            Item_click1(read_cunzai);
//        }
//    }

//    private void Company_Tongbao_View_next(String infotype , String pageno){
//        if (companyInfoService.queryCompanyInfotype_pageno(infotype, pageno).size() == 0) {
//            CompanyImpl companyImpl = new CompanyImpl(new MyHTTPResultListenerNext());
//            companyImpl.getCompanyInfoList(ssid, account, infotype, pageno, "13", "2");
//            zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
//            zrcListView.startLoadMore();
//        } else {
//            read_next = companyInfoService.queryCompanyInfotype_pageno(infotype, pageno);
//            read_up1.addAll(read_next);
//            WorkAdapter adapter1 = new WorkAdapter(getActivity(), read_next);
//            zrcListView.setAdapter(adapter1);
//            Item_click1(read_next);
//            zrcListView.setRefreshSuccess("展示成功"); // 通知加载成功
//            zrcListView.startLoadMore();
//        }
//    }

    private void Company_Jianbao_View_first(String infotype , String pageno){
        if(companyInfoService.queryCompanyInfotype_pageno(infotype,pageno).size() == 0){
            //信息简报
            CompanyImpl newsjianbao=new CompanyImpl(new CompanyHttpResultListener());
            newsjianbao.getCompanyInfoList(ssid, account,"0", infotype, pageno, "13", "2");
        }else{
            read_cunzai_jianbao = companyInfoService.queryCompanyInfotype_pageno(infotype,pageno);
            read_up2.addAll(read_cunzai_jianbao);
            CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), read_cunzai_jianbao);
            zrcListView2.setAdapter(companyNewsAdapter);
            Item_click2(read_cunzai_jianbao);
        }
    }

    private void Company_Jianbao_View_next( String infotype , String pageno ){
        if (companyInfoService.queryCompanyInfotype_pageno(infotype, pageno).size() == 0) {
            CompanyImpl newsjianbao = new CompanyImpl(new CompanyHttpResultListenernext());
            newsjianbao.getCompanyInfoList(ssid, account,"0",infotype, pageno, "13", "2");
            zrcListView2.setRefreshSuccess("加载成功"); // 通知加载成功
            zrcListView2.startLoadMore();
        } else {
            read_next_jianbao = companyInfoService.queryCompanyInfotype_pageno(infotype, pageno);
            read_up2.addAll(read_next_jianbao);
            CompanyNewsAdapter companyNewsAdapter = new CompanyNewsAdapter(getActivity(), read_next_jianbao);
            zrcListView2.setAdapter(companyNewsAdapter);
            Item_click2(read_next_jianbao);
            zrcListView2.setRefreshSuccess("展示成功"); // 通知加载成功
            zrcListView2.startLoadMore();
        }
    }


    private void Item_click1(final List<CompanyInfo> item_click){
        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SingleActivity.class);
                intent.putExtra("infoid", item_click.get(position).getInfoid());
                Log.i("infoid", item_click.get(position).getInfoid());
                intent.putExtra("infotype", item_click.get(position).getInfotype());
                Page1 = item_click.get(position).getPageno();
                startActivityForResult(intent, 0);
            }
        });
    }
    private void Item_click2(final List<CompanyInfo> item_click){
        zrcListView2.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //公司简报
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), SingleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("infoid", item_click.get(position).getInfoid());
                bundle.putString("infotype", item_click.get(position).getInfotype());
                Page2 = item_click.get(position).getPageno();
                intent1.putExtras(bundle);
                startActivityForResult(intent1, NEWCODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<CompanyInfo> one = new ArrayList<CompanyInfo>();
        for(int i = 0 ; i < read_up1.size() ; i++){
            if(read_up1.get(i).getPageno().equals(Page1)){
                one.add(read_up1.get(i));
            }
        }
        for(CompanyInfo tt1 : one){
            if(read_up1.contains(tt1)){
                read_up1.remove(tt1);
            }
        }
        if (Page1.equals("1")) {
            CompanyImpl companyImpl = new CompanyImpl(new MyHTTPResultListener(3));
            companyImpl.getCompanyInfoList(ssid, account, "0","8", Page1, "13", "2");
        } else {
            CompanyImpl companyImpl = new CompanyImpl(new MyHTTPResultListener(3));
            companyImpl.getCompanyInfoList(ssid, account,"0", "8", Page1, "13", "2");
        }
        List<CompanyInfo> two = new ArrayList<CompanyInfo>();
        for(int j = 0 ; j < read_up2.size() ;j++){
            if(read_up2.get(j).getPageno().equals(Page2)){
                two.add(read_up2.get(j));
            }
        }
        for (CompanyInfo tt2 : two){
            if(read_up2.contains(tt2)){
                read_up2.remove(tt2);
            }
        }
        if(Page2.equals("1")){
            CompanyImpl newsjianbao = new CompanyImpl(new CompanyHttpResultListener());
            newsjianbao.getCompanyInfoList(ssid, account, "0","7",Page2, "13", "2");
        }else{
            CompanyImpl newsjianbao = new CompanyImpl(new CompanyHttpResultListener());
            newsjianbao.getCompanyInfoList(ssid, account,"0", "7", Page2, "13", "2");
        }
    }


    public void initPager() {
        // 得到viewPager的布局
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.projectbaseinfo, null);
        view2 = lf.inflate(R.layout.qingkuangtongbao, null);
        view3 = lf.inflate(R.layout.qingkuangtongbao, null);
//        view4 = lf.inflate(R.layout.shejisectfragment, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        // 显示的点
        dots = new ArrayList<View>();
        dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        dots.add(getView().findViewById(R.id.dot_3));
//        viewList.add(view4);

        //初始化标题
        prp1=(TextView)view1.findViewById(R.id.prp);
        prp1.setText("项目基础信息");

        prp2=(TextView)view2.findViewById(R.id.prp);
        prp2.setText("情况通报");

        prp3=(TextView)view3.findViewById(R.id.prp);
        prp3.setText("信息简报");

        //初始化返回按钮
        back1=(Button)view1.findViewById(R.id.back);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        back2=(Button)view2.findViewById(R.id.back);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        back3=(Button)view3.findViewById(R.id.back);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        // 找到点击进入那个按钮
        mViewPager = (ViewPager)getView(). findViewById(R.id.vp);

        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        dots.get(0).setBackgroundResource(R.drawable.dot_focus);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
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

        /**
         * 显示项目基础信息
         */
        projectbaselist = (ListView)view1.findViewById(R.id.projectbaselist);

    }




    @Override
    public void onClick(View v) {
        String id;
        ProjectTrendsImpl projectTrends;
        switch (v.getId()){
            default:
                break;
        }
    }

    private class WorkAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        //private String[] data = {"总","关于开展2015年5月份月度专项检查的通知","05-06","50人已读"};
        private List<CompanyInfo> data;

        class ViewHolder{
            TextView title;
            TextView time;
            TextView read;
        }

        public WorkAdapter(Context context,List<CompanyInfo> data){
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
            CompanyInfo m = (CompanyInfo)this.getItem(position);
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.work_list_item,null);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.time= (TextView)convertView.findViewById(R.id.time);
                holder.read = (TextView)convertView.findViewById(R.id.read);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.title.setText(m.getInfoname());
            holder.time.setText(m.getInfodate());
            holder.read.setText(m.getNum());
            return convertView;
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

    private class AttributeBaseAdapter extends BaseAdapter {

        public AttributeBaseAdapter(Context context, List<ProjectInfo> list) {
            datas = list;
            mInflater = LayoutInflater.from(context);
        }

        class ViewHolder{
            TextView projectName;
            TextView projectType;
            TextView conxName;
            TextView projectSource;
            TextView projectCode;
            TextView projectLength;
            TextView startStation;
            TextView stopStation;
            TextView startDate;
            TextView stopDate;
            TextView stationNum;
            TextView invest;
            TextView startMile;
            TextView stopMile;
            TextView designCorpName;
            TextView examineCorpName;
            TextView proCity;
            TextView proStation;
            TextView description;
            TextView jyWenhao;
            TextView kyWenhao;
            TextView csWenhao;
            TextView sgWenhao;
            TextView zdWenhao;
            TextView sgJishu;
            TextView sgTouzi;
            TextView sgGongqi;
            TextView szJdGongqi;
            TextView kongzhiSection;
        }

        private List<ProjectInfo> datas;
        private LayoutInflater mInflater;

        public int getCount() {
            return datas.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.attributeitembase, null);
                holder.projectName = (TextView)convertView.findViewById(R.id.project_info_projectName);
                holder.projectType = (TextView)convertView.findViewById(R.id.project_info_projectType);
                holder.conxName = (TextView)convertView.findViewById(R.id.project_info_conxName);
                holder.projectSource = (TextView)convertView.findViewById(R.id.project_info_projectSource);
                holder.projectCode = (TextView)convertView.findViewById(R.id.project_info_projectCode);
                holder.projectLength = (TextView)convertView.findViewById(R.id.project_info_projectLength);
                holder.startStation = (TextView)convertView.findViewById(R.id.project_info_startStation);
                holder.stopStation = (TextView)convertView.findViewById(R.id.project_info_stopStation);
                holder.startDate = (TextView)convertView.findViewById(R.id.project_info_startDate);
                holder.stopDate = (TextView)convertView.findViewById(R.id.project_info_stopDate);
                holder.stationNum = (TextView)convertView.findViewById(R.id.project_info_stationNum);
                holder.invest = (TextView)convertView.findViewById(R.id.project_info_invest);
                holder.startMile = (TextView)convertView.findViewById(R.id.project_info_startMile);
                holder.stopMile = (TextView)convertView.findViewById(R.id.project_info_stopMile);
                holder.designCorpName = (TextView)convertView.findViewById(R.id.project_info_designCorpName);
                holder.examineCorpName = (TextView)convertView.findViewById(R.id.project_info_examineCorpName);
                holder.proCity = (TextView)convertView.findViewById(R.id.project_info_proCity);
                holder.proStation = (TextView)convertView.findViewById(R.id.project_info_proStation);
                holder.description = (TextView)convertView.findViewById(R.id.project_info_description);
                holder.jyWenhao = (TextView)convertView.findViewById(R.id.project_info_jyWenhao);
                holder.kyWenhao = (TextView)convertView.findViewById(R.id.project_info_kyWenhao);
                holder.csWenhao = (TextView)convertView.findViewById(R.id.project_info_csWenhao);
                holder.sgWenhao = (TextView)convertView.findViewById(R.id.project_info_sgWenhao);
                holder.zdWenhao = (TextView)convertView.findViewById(R.id.project_info_zdWenhao);
                holder.sgJishu = (TextView)convertView.findViewById(R.id.project_info_sgJishu);
                holder.sgTouzi = (TextView)convertView.findViewById(R.id.project_info_sgTouzi);
                holder.sgGongqi = (TextView)convertView.findViewById(R.id.project_info_sgGongqi);
                holder.szJdGongqi = (TextView)convertView.findViewById(R.id.project_info_szJdGongqi);
                holder.kongzhiSection = (TextView)convertView.findViewById(R.id.project_info_kongzhiSection);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            Log.i(TAG, "建设单位名称：" + datas.get(position).getDesignCompanyName());
            holder.projectName.setText(datas.get(position).getPname());
            holder.projectType.setText(datas.get(position).getBname());
            holder.designCorpName.setText(datas.get(position).getDesignCompanyName());
            holder.examineCorpName.setText(datas.get(position).getExamineCompanyName());
            holder.startDate.setText(datas.get(position).getStartdate());
            holder.stopDate.setText(datas.get(position).getRundate());
            holder.description.setText(datas.get(position).getDescription());
            holder.projectCode.setText(datas.get(position).getCode());

            return convertView;
        }

    }

    private class ProjectBaseAdapter extends BaseAdapter{
        private List<ProjectTtrendsInfo> datas;
        private LayoutInflater mInflater;

        public ProjectBaseAdapter(Context context, List<ProjectTtrendsInfo> list){
            datas = list;
            mInflater = LayoutInflater.from(context);
        }

        class ViewHolder{
            TextView attrvalue1;
            TextView attrvalue2;
            TextView attrvalue3;
            TextView attrvalue4;
            TextView attrvalue5;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
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
                convertView = mInflater.inflate(R.layout.sect_list_item, null);
                holder.attrvalue1 = (TextView)convertView.findViewById(R.id.value_1);
                holder.attrvalue2 = (TextView)convertView.findViewById(R.id.value_2);
                holder.attrvalue3 = (TextView)convertView.findViewById(R.id.value_3);
                holder.attrvalue4 = (TextView)convertView.findViewById(R.id.value_4);
                holder.attrvalue5 = (TextView)convertView.findViewById(R.id.value_5);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            if(datas.get(position).getName().equals("null")){
                holder.attrvalue1.setText("");
            }else{
                holder.attrvalue1.setText(datas.get(position).getName());
            }
            if(datas.get(position).getConstractamount().equals("null")){
                holder.attrvalue2.setText("");
            }else{
                holder.attrvalue2.setText(datas.get(position).getConstractamount());
            }
            if(datas.get(position).getTenders().equals("null-null")){
                holder.attrvalue3.setText("");
            }else{

                holder.attrvalue3.setText(datas.get(position).getTenders());
            }
            if(datas.get(position).getTotallength().equals("null")){
                holder.attrvalue4.setText("");
            }else{
                holder.attrvalue4.setText(datas.get(position).getTotallength());
            }
            if(datas.get(position).getConxname().equals("null")){
                holder.attrvalue5.setText("");
            }else{
                holder.attrvalue5.setText(datas.get(position).getConxname());
            }

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
                        final List<MeasureInfo> measureInfoList = (List<MeasureInfo>)obj;
                        ArrayAdapter<MeasureInfo> adapter = new ArrayAdapter<MeasureInfo>(getActivity(),
                                android.R.layout.simple_spinner_item, measureInfoList );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                ProjectInfoInterImpl projectInfoInterImpl = new ProjectInfoInterImpl(new MyHTTPResultListener(2));
                                projectInfoInterImpl.getProjectInfoList(ssid, account, ((MeasureInfo) measureInfoList.get(position)).getId());

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        ProjectInfoInterImpl projectInfoInterImpl = new ProjectInfoInterImpl(new MyHTTPResultListener(2));
                        projectInfoInterImpl.getProjectInfoList(ssid, account, ((MeasureInfo) spinner1.getAdapter().getItem(0)).getId());
                        break;
                    case 2:
                        List<ProjectInfo> list = (List<ProjectInfo>)obj;
                        Log.i("项目基础信息：",list.get(0).getId());
                        projectbaselist.setAdapter(new AttributeBaseAdapter(getActivity(), list));
                        break;
                    case 3:
                        compwnylist = (List<CompanyInfo>)obj;
                        if(compwnylist.size()>0){
                            companyInfoService.saveCompanyInfoLists(compwnylist);
                            read_up1.addAll(compwnylist);
                            Collections.sort(read_up1, new Comparator<CompanyInfo>() {
                                @Override
                                public int compare(CompanyInfo lhs, CompanyInfo rhs) {
                                    return lhs.getPageno().compareTo(rhs.getPageno());
                                }
                            });
//                            WorkAdapter adapter1 = new WorkAdapter(getActivity(), compwnylist);
//                            zrcListView.setAdapter(adapter1);
                            zrclistview_adapter1.notifyDataSetChanged();
                            zrcListView.setRefreshSuccess();
                            Item_click1(read_up1);
//                            zrcListView.setOnItemClickListener(StateFragment.this);
                        }else{
                            CompanyImpl company = new CompanyImpl(new MyHTTPResultListener(2));
                            company.getCompanyInfoList(ssid, account, "0","8",
                                    pageNo1 > 1 ? String.valueOf(pageNo1 - 1) : String.valueOf(pageNo1), "13", "2");
                        }
                        Log.i("hahah项目动态：", compwnylist.get(0).getInfoname());
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
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), SingleActivity.class);
        intent.putExtra("infoid", compwnylist.get(position).getInfoid());
        Log.i("infoid", compwnylist.get(position).getInfoid());
        intent.putExtra("infotype", compwnylist.get(position).getInfotype());
        Page1 = compwnylist.get(position).getPageno();
        startActivityForResult(intent, 0);
    }

    class MyHTTPResultListenerNext1 implements HttpResultListener{
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
                compwnylist = (List<CompanyInfo>)obj;
                if(compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    read_up1.addAll(compwnylist);
//                    WorkAdapter adapter1 = new WorkAdapter(getActivity(), read_up1);
//                    zrcListView.setAdapter(adapter1);
                    zrclistview_adapter1.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Item_click1(read_up1);

                }else{
                    read_cun_next = companyInfoService.queryCompanyInfotype_pageno(8+"",1+"");
                    WorkAdapter adapter1 = new WorkAdapter(getActivity(), read_cun_next);
                    zrcListView.setAdapter(adapter1);
                    Item_click1(read_cun_next);
                    zrcListView.stopLoadMore();
                }
                Log.i("hahah项目动态111：", compwnylist.get(0).getInfoname());
            }
        }

        @Override
        public void onFinish() {

        }
    }
    class MyHTTPResultListenerNext implements HttpResultListener{
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
                compwnylist = (List<CompanyInfo>)obj;
                if(compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    read_up1.addAll(compwnylist);
                    Collections.sort(read_up1, new Comparator<CompanyInfo>() {
                        @Override
                        public int compare(CompanyInfo lhs, CompanyInfo rhs) {
                            return lhs.getPageno().compareTo(rhs.getPageno());
                        }
                    });
                    zrclistview_adapter1.notifyDataSetChanged();
                    zrcListView.setRefreshSuccess();
//                    WorkAdapter adapter1 = new WorkAdapter(getActivity(), compwnylist);
//                    zrcListView.setAdapter(adapter1);
                    Item_click1(read_up1);
                }else{
                    read_cun_next = companyInfoService.queryCompanyInfotype_pageno(8+"",1+"");
                    WorkAdapter adapter1 = new WorkAdapter(getActivity(), read_cun_next);
                    zrcListView.setAdapter(adapter1);
                    Item_click1(read_cun_next);
                }
                Log.i("hahah项目动态111：", compwnylist.get(0).getInfoname());
            }
        }

        @Override
        public void onFinish() {

        }
    }

    class CompanyHttpResultListenernext1 implements HttpResultListener{
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
                compwnylist=(List<CompanyInfo>)obj;
                if (compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    read_up2.addAll(compwnylist);
                    CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), read_up2);
                    zrcListView2.setAdapter(companyNewsAdapter);
                    Item_click2(read_up2);
                }else {
                    read_jianbao = companyInfoService.queryCompanyInfotype_pageno(7+"",1+"");
                    CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), read_jianbao);
                    zrcListView2.setAdapter(companyNewsAdapter);
                    Item_click2(read_jianbao);
                    zrcListView2.stopLoadMore();
                }
            }
        }

        @Override
        public void onFinish() {

        }
    }
    class CompanyHttpResultListenernext implements HttpResultListener{

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
                compwnylist=(List<CompanyInfo>)obj;
                if (compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    read_up2.addAll(compwnylist);
                    CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), compwnylist);
                    zrcListView2.setAdapter(companyNewsAdapter);
                    Item_click2(compwnylist);
                }else {
                    read_jianbao = companyInfoService.queryCompanyInfotype_pageno(7+"",1+"");
                    CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), read_jianbao);
                    zrcListView2.setAdapter(companyNewsAdapter);
                    Item_click2(read_jianbao);
                }
            }
        }

        @Override
        public void onFinish() {

        }
    }

    class CompanyHttpResultListener implements HttpResultListener,ZrcListView.OnItemClickListener {
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
                compwnylist=(List<CompanyInfo>)obj;
                if (compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    read_up2.addAll(compwnylist);
                    CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), compwnylist);
                    zrcListView2.setAdapter(companyNewsAdapter);
                    zrcListView2.setOnItemClickListener(this);
                }else {
                    CompanyImpl newsjianbao = new CompanyImpl(this);
                    newsjianbao.getCompanyInfoList(ssid, account, "0","7",
                            pageNo2 > 1 ? String.valueOf(pageNo2-1):String.valueOf(pageNo2), "13", "2");
                }
            }
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {

            //公司要闻
            Intent intent1=new Intent();
            intent1.setClass(getActivity(), SingleActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("infoid",compwnylist.get(position).getInfoid());
            bundle.putString("infotype", compwnylist.get(position).getInfotype());
           Page2 = compwnylist.get(position).getPageno();
            intent1.putExtras(bundle);
            startActivityForResult(intent1,NEWCODE);
        }
    }

}