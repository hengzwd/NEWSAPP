package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.SingleActivity;
import newsemc.com.awit.news.newsemcapp.adapter.CompanyNewsAdapter;
import newsemc.com.awit.news.newsemcapp.adapter.StoneAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyNewImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 2015/6/30.
 */
public class CompanyNewsFragment extends Fragment{
    private ArrayList<View> dots;
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private View /*view1,*/ view2, view3;
    private int oldPosition = 0;// 记录上一次点的位置
    private int currentItem; // 当前页面
    private List<View> viewList = new ArrayList<View>();// 把需要滑动的页卡添加到这个list中
    //    private List<Map<String,ImgInfoList>> main_list_view;
    private List<ImgInfoList> main_list_view;
    //private ListView projectbaselist;
    ZrcListView zlv;
    ZrcListView zlv2;
    private int pageNo1 = 1;
    private int pageNo2 = 1;
    private List<CompanyInfo> compwnylist;
    private TextView prp2;
    private Button back;
    private String ssid;
    private String account;
    //图片新闻请求码
    private static  final int IMGCODE=1;
    //公司要闻请求码
    private static final int NEWCODE=2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.companynewsfragment, null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPager();
        //图片新闻
        //DDDD
        SharedPreferences testspec=getActivity().getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test",null);
        Log.i("special：：：：：：",special);

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
        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHTTPResultListener());
        imgInfoListImpl.getImgInfoList(ssid,account, "10", "1", "10", "1");

        //公司要闻
        CompanyNewImpl companyNewImpl = new CompanyNewImpl(new CompanyHttpResultListener());
        companyNewImpl.getCompanyInfoList(ssid,account,"0", "11", "1", "10", "1");


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IMGCODE:
                if(pageNo1==1){
                    ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHTTPResultListener());
                    imgInfoListImpl.getImgInfoList(ssid, account, "10", pageNo1 + "", "10", "1");
                }else{
                    ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHTTPResultListener());
                    imgInfoListImpl.getImgInfoList(ssid, account, "10", pageNo1 + "", "10", "2");
                }
                break;
            case NEWCODE:
                if(pageNo2==1){
                    CompanyNewImpl companyNewImpl = new CompanyNewImpl(new CompanyHttpResultListener());
                    companyNewImpl.getCompanyInfoList(ssid, account, "0","11",pageNo2+"", "10", "1");
                }else{
                    CompanyNewImpl companyNewImpl = new CompanyNewImpl(new CompanyHttpResultListener());
                    companyNewImpl.getCompanyInfoList(ssid, account, "0","11",pageNo2+"", "10", "2");
                }
                break;
        }
    }
    public void initPager() {

        // 添加当前Acitivity到ancivitylist里面去，为了方便统一退出
        // 显示的点
        dots = new ArrayList<View>();
        // dots.add(getView().findViewById(R.id.dot_1));
        dots.add(getView().findViewById(R.id.dot_2));
        dots.add(getView().findViewById(R.id.dot_3));
        // 得到viewPager的布局
        LayoutInflater lf = LayoutInflater.from(getActivity());
        // view1 = lf.inflate(R.layout.projectbaseinfo, null);
        view2 = lf.inflate(R.layout.mainfragment, null);
        view3 = lf.inflate(R.layout.companyimsfragment, null);
        back=(Button)getView().findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        //viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        // 找到点击进入那个按钮
//        back=(Button)getView(). findViewById(R.id.back);
        prp2=(TextView)getView().findViewById(R.id.prp);

//        prp3=(TextView)view3. findViewById(R.id.prp);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        prp2.setText("公司信息-图片新闻");

//        prp3.setText("公司信息-公司要闻");
        mViewPager = (ViewPager)getView(). findViewById(R.id.vp);

        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        dots.get(0).setBackgroundResource(R.drawable.dot_focus);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                Log.i("position", position + "");
                if(position==1){
                    prp2.setText("公司信息-公司要闻");
                }else{
                    prp2.setText("公司信息-图片新闻");
                }
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

//        CompanyNewImpl companyNewImpl = new CompanyNewImpl(CompanyNewsFragment.this);
//        companyNewImpl.getCompanyInfoList("liguobin", "11", "1", "10", "1");

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        zlv = (ZrcListView) view2.findViewById(R.id.mainlistview);
        zlv.setHeadable(header);
        zlv.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo1 = pageNo1+1;
                String page = String.valueOf(pageNo1);
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new MyHTTPResultListener());
                imgInfoListImpl.getImgInfoList(ssid,account, "10",page, "10", "1");
                zlv.setRefreshSuccess("加载成功"); // 通知加载成功
                zlv.startLoadMore();
            }
        });

        zlv2 = (ZrcListView)view3.findViewById(R.id.work);
        zlv2.setHeadable(header);
        zlv2.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo2 = pageNo2+1;
                String page = String.valueOf(pageNo2);
                //显示公司要闻
                CompanyNewImpl companyNewImpl = new CompanyNewImpl(new CompanyHttpResultListener());
                companyNewImpl.getCompanyInfoList(ssid,account, "0","11", page, "10", "1");
                zlv2.setRefreshSuccess("加载成功"); // 通知加载成功
                zlv2.startLoadMore();

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


    class MyHTTPResultListener implements HttpResultListener,ZrcListView.OnItemClickListener {
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
                main_list_view = (List<ImgInfoList>) obj;
                Log.i("projectinfo", main_list_view + "");
                if (main_list_view.size()>0){
                    StoneAdapter adapter = new StoneAdapter(getActivity(),main_list_view);
                    zlv.setAdapter(adapter);
                    zlv.setOnItemClickListener(this);
                }else{
                    ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
                    imgInfoListImpl.getImgInfoList(ssid,account, "10",
                            pageNo1 > 1 ? String.valueOf(pageNo1 - 1) : String.valueOf(pageNo1), "10", "1");
                }
            }
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {
            Log.i("id",id+"");
            //图片新闻
            Intent intent=new Intent();
            intent.setClass(getActivity(), SingleActivity.class);
            intent.putExtra("infoid", main_list_view.get(position).getInfoid() + "");
            Log.i("infoid", main_list_view.get(position).getInfoid());
            intent.putExtra("infotype", main_list_view.get(position).getInfotype());
            startActivityForResult(intent,IMGCODE);
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
                    CompanyNewsAdapter companyNewsAdapter=new CompanyNewsAdapter(getActivity(), compwnylist);
                    zlv2.setAdapter(companyNewsAdapter);
                    zlv2.setOnItemClickListener(this);
                    Log.i("1","hahahah");
                }else {
                    CompanyNewImpl companyNewImpl = new CompanyNewImpl(this);
                    companyNewImpl.getCompanyInfoList(ssid,account, "11","0",
                            pageNo2 > 1 ? String.valueOf(pageNo2-1):String.valueOf(pageNo2), "11", "1");
                }
            }
        }

        @Override
        public void onFinish() {

        }

        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {
            Log.i("id",id+"");
            //图片新闻
//            Intent intent=new Intent();
//            intent.setClass(getActivity(), SingleActivity.class);
//            intent.putExtra("infoid", main_list_view.get(position).getInfoid() + "");
//            Log.i("infoid", main_list_view.get(position).getInfoid());
//            intent.putExtra("infotype", main_list_view.get(position).getInfotype());
//            startActivity(intent);

            //公司要闻
            Intent intent1=new Intent();
            intent1.setClass(getActivity(), CompanyNewSingleActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("infoid",compwnylist.get(position).getInfoid());
            bundle.putString("infotype", compwnylist.get(position).getInfotype());
            intent1.putExtras(bundle);
            startActivityForResult(intent1,NEWCODE);
        }
    }

}
