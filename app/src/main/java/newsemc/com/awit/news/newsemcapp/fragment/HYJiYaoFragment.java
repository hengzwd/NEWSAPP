package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.SingleActivity;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-10-19.
 */
public class HYJiYaoFragment extends Fragment implements HttpResultListener,ZrcListView.OnItemClickListener {

    ZrcListView zrcListView;
    private int pageNo = 0;
    private List<CompanyInfo> compwnylist;
    private TextView prp;
    private Button back;
    private String ssid;
    private String account;
    private CompanyInfoService companyInfoService;
    List<CompanyInfo> read_cunzai = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_next = new ArrayList<CompanyInfo>();
    List<CompanyInfo> reads = new ArrayList<CompanyInfo>();
    List<CompanyInfo> list = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read_up = new ArrayList<CompanyInfo>();
    private String Page;
    private Handler handler;
    private WorkAdapter adapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.hyjiyaofragment, null);
        prp=(TextView)view.findViewById(R.id.prp);
        back=(Button)view.findViewById(R.id.back);
        prp.setText("公司信息-会议纪要");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
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
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(0xff33bbee);

        handler = new Handler();
        zrcListView = (ZrcListView)view.findViewById(R.id.work);
        zrcListView.setHeadable(header);
        zrcListView.setFootable(footer);
        zrcListView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        companyInfoService = CompanyInfoService.getInstance(getActivity());
        //companyInfoService.deleteAllCompanyInfo();

        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh_HY();
            }
        });

        zrcListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                load_HuiYi();
            }
        });

        adapter = new WorkAdapter(getActivity(),read_up);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh();

        return  view;
    }

    private void refresh_HY(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pageNo == 0){
                    pageNo = 1;
                    String page = String.valueOf(pageNo);
                    if(companyInfoService.queryCompanyInfotype_pageno(6+"",page).size() == 0){
                        CompanyImpl companyImpl = new CompanyImpl(HYJiYaoFragment.this);
                        companyImpl.getCompanyInfoList(ssid, account,"0", "6", page, "13", "2");
                        zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                        zrcListView.startLoadMore();
                    }else {
                        read_cunzai = companyInfoService.queryCompanyInfotype_pageno(6+"",page);
                        read_up.addAll(read_cunzai);
                        adapter.notifyDataSetChanged();
                        zrcListView.setRefreshSuccess("加载成功");
                        Item_click(read_up);
                    }
                }else {
                    if(read_up.size() <=13){
                        pageNo = pageNo + 1;
                        String page = String.valueOf(pageNo);
                        CompanyImpl companyImpl = new CompanyImpl(new HYJiYaoFragmentnext());
                        companyImpl.getCompanyInfoList(ssid, account, "0","6",page, "13", "2");
                        zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                        zrcListView.startLoadMore();
                    }else {
                        zrcListView.setRefreshFail("加载失败");
                    }
                }
            }
        },2*1000);
    }

    private void load_HuiYi(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo = pageNo+1;
                String page = String.valueOf(pageNo);
                List<String> Num = new ArrayList<String>();
                for(int i = 0 ; i < read_up.size() ; i++){
                    Num.add( read_up.get(i).getPageno());
                }
                if(companyInfoService.queryCompanyInfotype_pageno(6+"",page).size() > 0){
                    for(int i = 0 ; i < read_up.size() ; i++){
                        if(read_up.get(i).getPageno().equals(page) && Num.size() > 0){
                            break;
                        }else {
                            list = companyInfoService.queryCompanyInfotype_pageno(6+"",page);
                            for(int j = 0 ; j < list.size() ; j++ ){
                                read_up.add(list.get(j));
                            }
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Item_click(read_up);
                }else {
                    CompanyImpl companyImpl = new CompanyImpl(new HYJiYaoFragmentnext1());
                    companyImpl.getCompanyInfoList(ssid, account,"0", "6",page, "13", "2");
                    zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                    zrcListView.startLoadMore();
                }
            }
        },2*1000);
    }

    private void Item_click(final List<CompanyInfo> item_list){
        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), SingleActivity.class);
                intent.putExtra("infoid", item_list.get(position).getInfoid() + "");
                Log.i("infoid", item_list.get(position).getInfoid());
                intent.putExtra("infotype", item_list.get(position).getInfotype());
                Page = item_list.get(position).getPageno();
                startActivityForResult(intent, 0);
            }
        });
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

    class HYJiYaoFragmentnext1 implements HttpResultListener{
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
                //通知公告
                compwnylist=(List<CompanyInfo>)obj;
                if(compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    for (int i = 0 ; i < compwnylist.size() ; i++){
                        read_up.add(compwnylist.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Item_click(read_up);
                }else{
                    reads = companyInfoService.queryCompanyInfotype_pageno(6+"",1+"");
                    adapter = new WorkAdapter(getActivity(),reads);
                    zrcListView.setAdapter(adapter);
                    Item_click(reads);
                    zrcListView.stopLoadMore();
                }
            }
        }

        @Override
        public void onFinish() {

        }
    }

    class HYJiYaoFragmentnext implements HttpResultListener{


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
                //通知公告
                compwnylist=(List<CompanyInfo>)obj;
                if(compwnylist.size()>0){
                    companyInfoService.saveCompanyInfoLists(compwnylist);
                    for (int i = 0 ; i < compwnylist.size() ; i++){
                        read_up.add(compwnylist.get(i));
                    }
                    Collections.sort(read_up, new Comparator<CompanyInfo>() {
                        @Override
                        public int compare(CompanyInfo lhs, CompanyInfo rhs) {
                            return lhs.getPageno().compareTo(rhs.getPageno());
                        }
                    });
                    adapter.notifyDataSetChanged();
                    zrcListView.setRefreshSuccess();
                    Item_click(read_up);
                }else{
                    reads = companyInfoService.queryCompanyInfotype_pageno(6+"",1+"");
                    adapter = new WorkAdapter(getActivity(),reads);
                    zrcListView.setAdapter(adapter);
                    Item_click(reads);
                }
            }
        }
        @Override
        public void onFinish() {

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
            //通知公告
            compwnylist=(List<CompanyInfo>)obj;
            if(compwnylist.size()>0){
                companyInfoService.saveCompanyInfoLists(compwnylist);
                for (int i = 0 ; i < compwnylist.size() ; i++){
                    read_up.add(compwnylist.get(i));
                }
                Collections.sort(read_up, new Comparator<CompanyInfo>() {
                    @Override
                    public int compare(CompanyInfo lhs, CompanyInfo rhs) {
                        return lhs.getPageno().compareTo(rhs.getPageno());
                    }
                });
                adapter.notifyDataSetChanged();
                zrcListView.setRefreshSuccess();
                Item_click(read_up);
            }else{
                CompanyImpl companyImpl = new CompanyImpl(HYJiYaoFragment.this);
                companyImpl.getCompanyInfoList(ssid, account,"0", "6",
                        pageNo > 1 ? String.valueOf(pageNo - 1) : String.valueOf(pageNo), "13", "2");
            }

        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), SingleActivity.class);
        intent.putExtra("infoid", compwnylist.get(position).getInfoid() + "");
        Log.i("infoid", compwnylist.get(position).getInfoid());
        intent.putExtra("infotype", compwnylist.get(position).getInfotype());
        Page = compwnylist.get(position).getPageno();
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<CompanyInfo> one = new ArrayList<CompanyInfo>();
        for(int i = 0 ; i < read_up.size() ; i++){
            if(read_up.get(i).getPageno().equals(Page)){
                one.add(read_up.get(i));
            }
        }
        for(CompanyInfo tt : one){
            if (read_up.contains(tt)){
                read_up.remove(tt);
            }
        }
        if(Page.equals("1")){
            CompanyImpl companyImpl = new CompanyImpl(HYJiYaoFragment.this);
            companyImpl.getCompanyInfoList(ssid, account,"0", "6", Page, "13", "2");
        }else{
            CompanyImpl companyImpl = new CompanyImpl(HYJiYaoFragment.this);
            companyImpl.getCompanyInfoList(ssid, account, "0","6", Page, "13", "2");
        }
    }
}


