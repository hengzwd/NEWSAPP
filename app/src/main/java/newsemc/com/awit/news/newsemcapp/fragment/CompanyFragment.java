package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.GridSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;


/**
 * Created by Administrator on 2015/7/6.
 */
public class CompanyFragment extends Fragment implements AdapterView.OnItemClickListener,HttpResultListener {
//    private List<Map<String,ImgInfoList>> list_view;
//    public static CompanyFragment newInstance(){
//        CompanyFragment f = new CompanyFragment();
//        return f;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return  inflater.inflate(R.layout.mienfragment, null);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
//        imgInfoListImpl.getImgInfoList("liguobin", "17", "1", "10", "1");
//    }
//
//    public static ArrayList<Map<String, Object>> getMainListViewData() {
//        ArrayList<Map<String, Object>> grid_list_view = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        map.put("drawer_list_itme_img", R.drawable.u10);
//        grid_list_view.add(map);
//
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u12);
//        grid_list_view.add(map);
//
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u16);
//        map.put("drawer_list_itme_name", "甬台温铁路雁荡山特大桥竣工启用");
//        map.put("drawer_list_itme_date","【2015-03】");
//        grid_list_view.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u21);
//        map.put("drawer_list_itme_name", "徐州铁路枢纽荣获鲁班奖");
//        map.put("drawer_list_itme_date","【2015-04】");
//        grid_list_view.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u26);
//        map.put("drawer_list_itme_name", "XX铁路开展联调联试工作");
//        map.put("drawer_list_itme_date","【2015-03】");
//        grid_list_view.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u28);
//        map.put("drawer_list_itme_name", "XX铁路开展联调联试工作");
//        map.put("drawer_list_itme_date","【2015-03】");
//        grid_list_view.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u32);
//        map.put("drawer_list_itme_name", "XX隧道正式通车");
//        map.put("drawer_list_itme_date","【2015-05】");
//        grid_list_view.add(map);
//
//
//        map = new HashMap<String, Object>();
//        map.put("drawer_list_itme_img", R.drawable.u34);
//        map.put("drawer_list_itme_name", "五府山特大桥竣工验收");
//        map.put("drawer_list_itme_date","【2015-05】");
//        grid_list_view.add(map);
//
//        return grid_list_view;
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent=new Intent();
//        intent.setClass(getActivity(), GridSingleActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putString("itemimg",list_view.get(position).get("drawer_list_itme_img")+"");
//        bundle.putString("itemname",list_view.get(position).get("drawer_list_itme_name")+"");
//        bundle.putString("itemdate",list_view.get(position).get("drawer_list_itme_date")+"");
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onStartRequest() {
//
//    }
//
//    @Override
//    public void onResult(Object obj) {
//        if(obj instanceof FailRequest){
//            FailRequest  fail= (FailRequest)obj;
//            if(!(fail==null)){
//                System.out.println("异常信息："+fail.getMsg());
//                System.out.println("状态："+fail.getStatus());
//            }
//        }else{
//            list_view = (List<Map<String,ImgInfoList>>) obj;
//            GridView lv = (GridView) getView().findViewById(R.id.gridview);
//            lv.setAdapter(new ImageAdapter(getActivity(),list_view));
//        }
//    }
//
//    @Override
//    public void onFinish() {
//
//    }

    private List<ImgInfoList> imageInfoList;
    private String imageUrls[];
    private String title[];
    private SwipeRefreshLayout swipeLayout;
    private int pageNo = 1;
    private String ssid;
    private String account;
    public static CompanyFragment newInstance(){
        CompanyFragment f = new CompanyFragment();
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.mienfragment, null);
        //DDDD
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

        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(CompanyFragment.this);
        imgInfoListImpl.getImgInfoList(ssid,account, "17","1", "10", "1");
//        swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        pageNo = pageNo+1;
//                        swipeLayout.setRefreshing(false);
//                        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(MienFragment.this);
//                        imgInfoListImpl.getImgInfoList("jinzhiyi", "16",String.valueOf(pageNo), "10", "2");
//                    }
//                }, 500);
//            }
//        });
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
//        imgInfoListImpl.getImgInfoList("jinzhiyi", "16", "1", "10", "2");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), GridSingleActivity.class);
        Bundle bundle=new Bundle();
//        bundle.putString("itemimg",list_view.get(position).get("infoimage")+"");
//        bundle.putString("itemname",list_view.get(position).get("infoame")+"");
//        bundle.putString("itemdate",list_view.get(position).get("infodate")+"");
        intent.putExtras(bundle);
        startActivity(intent);
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
            imageInfoList = (List<ImgInfoList>) obj;
//            ListBuddiesLayout listBuddies = (ListBuddiesLayout)getView(). findViewById(R.id.listbuddies);
            int size=imageInfoList.size();
            String temp[]=new String[size];
            title=new String[size];
            for(int i=0;i<imageInfoList.size();i++){
                temp[i] = imageInfoList.get(i).getInfoimg();
                title[i]=imageInfoList.get(i).getInfoname();
                Log.i("temp", temp[i]);
            }
            imageUrls=temp;
//            CircularAdapter adapter = new CircularAdapter(getActivity(),
//                    getResources().getDimensionPixelSize(R.dimen.item_height_small),
//                    Arrays.asList(imageUrls));
//            CircularAdapter adapter2 = new CircularAdapter(getActivity(),
//                    getResources().getDimensionPixelSize(R.dimen.item_height_tall),
//                    Arrays.asList(imageUrls));
//            listBuddies.setAdapters(adapter, adapter2);
//            listBuddies.setOnItemClickListener(new ListBuddiesLayout.OnBuddyItemClickListener() {
//                @Override
//                public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {
//
//                    Toast.makeText(getActivity(), getImage(buddy, position), Toast.LENGTH_SHORT).show();
//                }
//            });

//            if(list_view.size()>0){
//                lv.setAdapter(new ImageAdapter(getActivity(),list_view));
//            }else{
//                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(MienFragment.this);
//                imgInfoListImpl.getImgInfoList("liguobin", "16",
//                        pageNo>1?String.valueOf(pageNo-1):String.valueOf(pageNo), "10", "2");
//            }


        }
    }
    private String getImage(int buddy, int position) {
        return buddy == 0 ? title[position] : title[position];
    }
    @Override
    public void onFinish() {

    }
}
