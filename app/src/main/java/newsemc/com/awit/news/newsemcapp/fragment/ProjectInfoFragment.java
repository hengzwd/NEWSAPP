package newsemc.com.awit.news.newsemcapp.fragment;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.ProjectShowView;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ProjectDetail;
import newsemc.com.awit.news.newsemcapp.dao.MeasureInfo;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetail;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailMainItem;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPeriod;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPic;
import newsemc.com.awit.news.newsemcapp.dao.ProjectTtrendsInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.MeasureInfoImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ProjectInfoDetailImp;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ProjectInfoInterImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ProjectTrendsImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;

/**
 * Created by Administrator on 15-6-16.
 */
public class ProjectInfoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProjectInfoFragment";
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
    Spinner spinner1,spinner2,spinner3;
    private String ssid;
    private String account;
    private TextView prp1,prp2,prp3;
    private Button btnback1,btnback2,btnback3;
    private String test;
    private int roadNum;

    @Override
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
            Log.i(TAG, "ssid=" + ssid);
            account=sharedPreferences.getString("ACCOUNT","");
            Log.i(TAG, "account=" + account);
            //finish();
        }

        MeasureInfoImpl measureInfoImpl = new MeasureInfoImpl(new MyHTTPResultListener(1));
        measureInfoImpl.getProjectInfoList(ssid, account);
        return  inflater.inflate(R.layout.projectfragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();
    }

    public void initPager() {
        // 添加当前Acitivity到ancivitylist里面去，为了方便统一退出
        // 显示的点
        dots = new ArrayList<View>();
        dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        dots.add(getView().findViewById(R.id.dot_3));

        // 得到viewPager的布局
        LayoutInflater lf = LayoutInflater.from(getActivity());
        view1 = lf.inflate(R.layout.projectbaseinfo, null);
        view2 = lf.inflate(R.layout.detailfragment, null);
        view3 = lf.inflate(R.layout.jianlisectfragment, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        prp1=(TextView)view1.findViewById(R.id.prp);
        prp1.setText("项目基础信息");
        prp2=(TextView)view2.findViewById(R.id.prp);
        prp2.setText("施工标段");
        prp3=(TextView)view3.findViewById(R.id.prp);
        prp3.setText("监理标段");
        btnback1=(Button)view1.findViewById(R.id.back);
        btnback2=(Button)view2.findViewById(R.id.back);
        btnback3=(Button)view3.findViewById(R.id.back);
        btnback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        btnback3.setOnClickListener(new View.OnClickListener() {
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
        spinner3 = (Spinner)view3.findViewById(R.id.spinner3);
        /**
         * 显示项目基础信息
         */
        projectbaselist = (ListView)view1.findViewById(R.id.projectbaselist);
        sectsitelist = (ListView)view2.findViewById(R.id.sectsite_lv);
        jianlisectlist =(ListView)view3.findViewById(R.id.jianlisect_lv);

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

        class ViewHolder{
            ProjectShowView projectDetailPicShowView;
            SmartTabLayout smartTabLayoutPic;
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

        private List<ProjectDetail> datas;
        private LayoutInflater mInflater;
        private ProjectInfoDetail projectInfoDetail;//项目基本信息
        private ProjectInfoDetailPeriod projectInfoDetailPeriod;//批复文号
        private ProjectInfoDetailMainItem projectInfoDetailMainItem;//主要控制工程
        private List<ProjectInfoDetailPic> projectInfoDetailPicList;//图片列表
        private String picUrls;
        private String picNames;

        public AttributeBaseAdapter(Context context, List<ProjectDetail> list) {
            datas = list;
            mInflater = LayoutInflater.from(context);
        }

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
            final ViewHolder holder;
            projectInfoDetail = datas.get(position).getProjectInfoDetail();//项目基本信息
            projectInfoDetailPeriod = datas.get(position).getProjectInfoDetailPeriod();//批复文号
            projectInfoDetailMainItem = datas.get(position).getProjectInfoDetailMainItem();//主要控制工程
            projectInfoDetailPicList = datas.get(position).getProjectInfoDetailPicList();//图片列表

            for (int i = 0; i < projectInfoDetailPicList.size(); i++){
                picUrls = picUrls + projectInfoDetailPicList.get(i).getProjectPicUrl() + ";";
                picNames = picNames + projectInfoDetailPicList.get(i).getProjectPicName() + ";";
                Log.i(TAG, "图片的url=" + projectInfoDetailPicList.get(i).getProjectPicUrl());
                Log.i(TAG, "图片的name=" + projectInfoDetailPicList.get(i).getProjectPicName());
                Log.i(TAG, "图片的id=" + projectInfoDetailPicList.get(i).getProjectPicId());
            }
            //讲所有的url和name全部一一个字符串的形式储存起来
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("PROJECT_PIC_INFO", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pic_urls", picUrls);
            editor.putString("pic_names", picNames);
            editor.commit();

            if(convertView == null){
                Log.i(TAG,"nullnullnull");
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.attributeitembase, null);
                holder.projectDetailPicShowView = (ProjectShowView)convertView.findViewById(R.id.project_info_project_pic);
                //对项目动态的图片进行判断，检测接口图片的URL数组是否为空，为空时设置不可见，否则可见
                if (projectInfoDetailPicList.size() == 0 || projectInfoDetailPicList
                        .get(position).getProjectPicUrl().equals("null")){
                    Log.i(TAG,"ifififif===");
                    //holder.projectDetailPicShowView.setVisibility(View.INVISIBLE);
                    holder.projectDetailPicShowView.setVisibility(View.GONE);

                }else{
                    Log.i(TAG,"elseeslll===");
                    holder.projectDetailPicShowView.setVisibility(View.VISIBLE);
                }

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
            Log.i(TAG, "图片List的长度=" + projectInfoDetailPicList.size());
            Log.i(TAG, "项目名称：" + datas.get(position).getProjectInfoDetail().getProjectName());

            holder.projectName.setText(handleEmptyString(projectInfoDetail.getProjectName()));//项目名称
            holder.projectType.setText(handleEmptyString(projectInfoDetail.getProjectType()));//项目功能类别
            holder.conxName.setText(handleEmptyString(projectInfoDetail.getConxName()));//建设单位
            holder.projectSource.setText(handleEmptyString(projectInfoDetail.getProjectSource()));//工程来源
            holder.projectCode.setText(handleEmptyString(projectInfoDetail.getProjectCode()));//项目编码
            holder.projectLength.setText(handleEmptyString(projectInfoDetail.getProjectLength()));//正线建筑里程
            holder.startStation.setText(handleEmptyString(projectInfoDetail.getStartStation()));//始发站
            holder.stopStation.setText(handleEmptyString(projectInfoDetail.getStopStation()));//终点站
            holder.startDate.setText(handleEmptyString(projectInfoDetail.getStartDate()));//开工时间
            holder.stopDate.setText(handleEmptyString(projectInfoDetail.getStopDate()));//竣工时间
            holder.stationNum.setText(handleEmptyString(projectInfoDetail.getStationNum()));//车站总数
            holder.invest.setText(handleEmptyString(projectInfoDetail.getInvest()));//投资总额
            holder.startMile.setText(handleEmptyString(projectInfoDetail.getStartMile()));//开始里程
            holder.stopMile.setText(handleEmptyString(projectInfoDetail.getStopMile()));//结束里程
            holder.designCorpName.setText(handleEmptyString(projectInfoDetail.getDesignCorpName()));//设计单位
            holder.examineCorpName.setText(handleEmptyString(projectInfoDetail.getExamineCorpName()));//施工图审核单位
            holder.proCity.setText(handleEmptyString(projectInfoDetail.getProCity()));//途径城市
            holder.proStation.setText(handleEmptyString(projectInfoDetail.getProStation()));//所辖车站
            holder.description.setText(handleEmptyString(projectInfoDetail.getDescription()));//工程概述
            holder.jyWenhao.setText(handleEmptyString(projectInfoDetailPeriod.getJyWenhao()));//项目建议书
            holder.kyWenhao.setText(handleEmptyString(projectInfoDetailPeriod.getKyWenhao()));//可研批复
            holder.csWenhao.setText(handleEmptyString(projectInfoDetailPeriod.getCsWenhao()));//初设批复
            holder.sgWenhao.setText(handleEmptyString(projectInfoDetailPeriod.getSgWenhao()));//施工图审核
            holder.zdWenhao.setText(handleEmptyString(projectInfoDetailPeriod.getZdWenhao()));//指导性施组批复和调整
            holder.sgJishu.setText(handleEmptyString(projectInfoDetailPeriod.getSgJishu()));//初设批复技术标准
            holder.sgTouzi.setText(handleEmptyString(projectInfoDetailPeriod.getSgTouzi()));//初设批复投资
            holder.sgGongqi.setText(handleEmptyString(projectInfoDetailPeriod.getSgGongqi()));//初设批复工期
            holder.szJdGongqi.setText(handleEmptyString(projectInfoDetailPeriod.getSzJdGongqi()));//批准施组节点工期
            holder.kongzhiSection.setText(handleEmptyString(projectInfoDetailMainItem.getKongzhiSection()));//主要控制工程

            return convertView;
        }

        //处理空字符串，若字符串为null，则返回“”;
        public String handleEmptyString(String string){
            if (string.equals("null")){
                string = "";
            }
            return string;
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
//                        Log.i("选择项目的数量：",measureInfoList.size()+"");
//                        Log.i("项目名称",measureInfoList.get(0).getName());
                        ArrayAdapter<MeasureInfo> adapter = new ArrayAdapter<MeasureInfo>(getActivity(),
                                android.R.layout.simple_spinner_item, measureInfoList );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner1.setAdapter(adapter);
                        spinner2.setAdapter(adapter);
                        spinner3.setAdapter(adapter);

                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                test = ((MeasureInfo) measureInfoList.get(position)).getId();

                                Log.d(TAG,"test1111=" + test);
//                                ProjectInfoInterImpl projectInfoInterImpl = new ProjectInfoInterImpl(new MyHTTPResultListener(2));
//                                projectInfoInterImpl.getProjectInfoList(ssid, account, test);
                                ProjectInfoDetailImp projectInfoDetailImp = new ProjectInfoDetailImp(new MyHTTPResultListener(2));
                                projectInfoDetailImp.getProjectInfoList(ssid, account, test);
                                Log.d(TAG, "test2222=" + test);

                                roadNum = (int)id;
                                spinner2.setSelection(roadNum);
                                spinner3.setSelection(roadNum);

                                ProjectTrendsImpl projectTrends = new ProjectTrendsImpl(new MyHTTPResultListener(3));
                                projectTrends.getProjectTrendList(ssid, account, test, "1");

                                ProjectTrendsImpl projectTrend = new ProjectTrendsImpl(new MyHTTPResultListener(4));
                                projectTrend.getProjectTrendList(ssid, account, test, "3");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Log.d("test3333", test);
                                test = ((MeasureInfo) measureInfoList.get(position)).getId();
                                Log.d("test4444", test);


                                ProjectTrendsImpl projectTrends = new ProjectTrendsImpl(new MyHTTPResultListener(3));
                                projectTrends.getProjectTrendList(ssid, account,
                                        test, "1");

                                roadNum = (int) id;
                                spinner1.setSelection(roadNum);
                                spinner3.setSelection(roadNum);

                                ProjectInfoInterImpl projectInfoInterImpl = new ProjectInfoInterImpl(new MyHTTPResultListener(2));
                                projectInfoInterImpl.getProjectInfoList(ssid, account, test);
//                                ProjectInfoDetailImp projectInfoDetailImp = new ProjectInfoDetailImp(new MyHTTPResultListener(2));
//                                projectInfoDetailImp.getProjectInfoList(ssid, account, test);

                                ProjectTrendsImpl projectTrend = new ProjectTrendsImpl(new MyHTTPResultListener(4));
                                projectTrend.getProjectTrendList(ssid, account,
                                        test, "3");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                test = ((MeasureInfo) measureInfoList.get(position)).getId();

                                ProjectTrendsImpl projectTrend = new ProjectTrendsImpl(new MyHTTPResultListener(4));
                                projectTrend.getProjectTrendList(ssid, account,
                                        test, "3");

                                roadNum = (int)id;

                                spinner2.setSelection(roadNum);
                                spinner1.setSelection(roadNum);

                                ProjectTrendsImpl projectTrends = new ProjectTrendsImpl(new MyHTTPResultListener(3));
                                projectTrends.getProjectTrendList(ssid, account,
                                        test, "1");

//                                ProjectInfoInterImpl projectInfoInterImpl = new ProjectInfoInterImpl(new MyHTTPResultListener(2));
//                                projectInfoInterImpl.getProjectInfoList(ssid, account, test);
                                ProjectInfoDetailImp projectInfoDetailImp = new ProjectInfoDetailImp(new MyHTTPResultListener(2));
                                projectInfoDetailImp.getProjectInfoList(ssid, account, test);


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        //基础项目信息
//                        ProjectInfoInterImpl projectInfoInterImpl = new ProjectInfoInterImpl(new MyHTTPResultListener(2));
//                        projectInfoInterImpl.getProjectInfoList(ssid,account,((MeasureInfo)spinner1.getAdapter().getItem(0)).getId());
                        ProjectInfoDetailImp projectInfoDetailImp = new ProjectInfoDetailImp(new MyHTTPResultListener(2));
                        projectInfoDetailImp.getProjectInfoList(ssid, account, ((MeasureInfo)spinner1.getAdapter().getItem(0)).getId());
                        //施工标段
                        ProjectTrendsImpl projectTrends = new ProjectTrendsImpl(new MyHTTPResultListener(3));
                        projectTrends.getProjectTrendList(ssid,account,((MeasureInfo)spinner2.getAdapter().getItem(0)).getId(), "1");
                        //监理标段
                        ProjectTrendsImpl projectTrends2 = new ProjectTrendsImpl(new MyHTTPResultListener(4));
                        projectTrends2.getProjectTrendList(ssid,account, ((MeasureInfo)spinner3.getAdapter().getItem(0)).getId(), "3");

//                        ProjectTrendsImpl projectTrends3 = new ProjectTrendsImpl(new MyHTTPResultListener(5));
//                        projectTrends3.getProjectTrendList("sh34", ((MeasureInfo) spinner4.getAdapter().getItem(0)).getId(), "2");
                        break;
                    case 2:
//                        List<ProjectInfo> list = (List<ProjectInfo>)obj;
                        List<ProjectDetail> list = (List<ProjectDetail>)obj;
//                        Log.i("项目基础信息：",list.get(0).getId());
                        Log.i(TAG, "项目基础信息（项目名称）：" + list.get(0).getProjectInfoDetail().getProjectName());
                        Log.i(TAG, "输出接口获取到的数据1111");
                        //加载项目基础信息列表
                        projectbaselist.setAdapter(new AttributeBaseAdapter(getActivity(), list));
                        Log.i(TAG, "输出接口获取到的数据2222");
                        break;
                    case 3:
                        List<ProjectTtrendsInfo> projectTtrendsInfoList = (List<ProjectTtrendsInfo>)obj;
                        sectsitelist.setAdapter(new ProjectBaseAdapter(getActivity(),projectTtrendsInfoList));
                        break;
                    case 4:
                        List<ProjectTtrendsInfo> jianliList = (List<ProjectTtrendsInfo>)obj;
                        jianlisectlist.setAdapter(new ProjectBaseAdapter(getActivity(),jianliList));
                        break;
//                    case 5:
//                        List<ProjectTtrendsInfo> shejiList = (List<ProjectTtrendsInfo>)obj;
//                        shejisectlist.setAdapter(new ProjectBaseAdapter(getActivity(), shejiList));
//                        break;
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