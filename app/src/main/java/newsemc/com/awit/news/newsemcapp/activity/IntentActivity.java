package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.WorkAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.FirstMenuInfo;
import newsemc.com.awit.news.newsemcapp.dao.MeasureDetailInfo;
import newsemc.com.awit.news.newsemcapp.dao.SecondMenuInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.fragment.IntentFragment;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.util.UIUtils;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-10-30.
 */
public class IntentActivity extends FragmentActivity implements View.OnClickListener{
    private Button btback;
    private FragmentTransaction ft;
    private TextView textt;
    private String code ;


    public static boolean isFirst = false;
    private ListView listview1;
    private String ssid,account,childcode,infoname,parentCode,id;
    private TextView text;
    ZrcListView zrcListView;
    private int pageNo = 1;
    private int pageNo1 = -1;
    private List<FirstMenuInfo> firstMenuInfoList;
    private List<SecondMenuInfo> secondMenuInfoList;
    private List<MeasureDetailInfo> measureDetailInfoList;
    private int date = 1;
    //private List<MeasureInfo> measureInfoList;
    //数据库里面的数据展示
    private CompanyInfoService companyInfoService;
    List<CompanyInfo> reads_exit = new ArrayList<CompanyInfo>();//读取存在的
    List<CompanyInfo> read_next = new ArrayList<CompanyInfo>();//读取下一条数据
    List<CompanyInfo> exit = new ArrayList<CompanyInfo>();//存
    List<CompanyInfo> list = new ArrayList<CompanyInfo>();
    List<CompanyInfo> read = new ArrayList<CompanyInfo>();//读取详情
    private String Page;
    private Handler handler;
    private WorkAdapter adapter;
    private  static final int DIALOG_DISMISS = 0;
    //列表信息展示
    private List<CompanyInfo> companyInfoList;

    View footer;
    private AlertDialog.Builder builder;
    //进度条
    private CustomProgressDialog progressDialog = null;
    private ProgressDialog pdDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取控件
        setContentView(R.layout.intentactivity);
        btback=(Button)findViewById(R.id.back);
        textt=(TextView)findViewById(R.id.text);
        btback.setOnClickListener(this);
            //接收MainActivity传过来的标题
            Bundle bundle_title1 = getIntent().getExtras();
            Log.i("ififififif","aaaaaa");
            String title1 = bundle_title1.getString("title1");
            Log.i("title1", title1 + "");
            getWindow().setTitle("title1");
           //添加到标题栏上
            textt.setText(title1);
           //使用正则表达式替换标题
           setTitle(title1.replaceAll("\\[([^\\]]*)\\]", "$1"));
        //接受从loginActivity传递过来的参数
        SharedPreferences testspec=this.getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test", null);
        if ("test".equals(special)){
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences=this.getSharedPreferences("SPEC", CompanyNewSingleActivity.MODE_PRIVATE);
            ssid=SpecialsharedPreferences.getString("KEY","");
            Log.i("Specialssid", ssid);
            account=SpecialsharedPreferences.getString("ACCOUNT","");
            Log.i("SPECIALACCOUNT", account);

        }else{
            //清空特殊登录所保存的数据
            SharedPreferences.Editor editor=testspec.edit();
            editor.clear();
            editor.commit();
            //正常登录过来的
            SharedPreferences sharedPreferences=this.getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
            ssid=sharedPreferences.getString("KEY","");
            account=sharedPreferences.getString("ACCOUNT","");
        }
//        //声明变量，当第一次进来的时候为false
//        isFirst = false;
//        startProgressDialog("正在加载数据。。。");
//        showProDialog();
        //接受父级菜单传过来的code
//        SharedPreferences parentsha=this.getSharedPreferences("PARENT", MainActivity.MODE_PRIVATE);
//        parentCode=parentsha.getString("parentCode","");
//        Log.i("parentcodecodecode", parentCode);
//        //清空上一次的数据
//        SharedPreferences.Editor editorr= parentsha.edit();
//        editorr.clear();
//        editorr.commit();
//        //接收从mainactivity传递过来子菜单的列表
//        childcode = parentsha.getString("childcode", "");
//        infoname = parentsha.getString("Infoname", "");
//        id = parentsha.getString("idd","");
//        Log.i("code+++++++", childcode);
//        Log.i("name++++++++",infoname);
//        Log.i("idd++++++++",id);
        Intent intent = getIntent();
        parentCode = intent.getStringExtra("parentCode");
        childcode = intent.getStringExtra("childcode");

        //初始化数据库对象
        companyInfoService= CompanyInfoService.getInstance(this);
        //初始化handler对象
        handler=new Handler();
        //触发的是头部的事件
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        zrcListView = (ZrcListView)findViewById(R.id.zrcListview);
        zrcListView.setHeadable(header);

        //底部的样式
        SimpleFooter footer=new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        zrcListView.setFootable(footer);
        //设置列表项出现动画
        zrcListView.setItemAnimForBottomIn(R.anim.bottomitem_in);



        //设置下拉刷新的监听事件
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                zrcListView.setRefreshSuccess("刷新成功");
                zrcListView.startLoadMore();
            }
        });
        //设置上拉加载的监听事件
        zrcListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                Log.i("loadloadload","dsadsa");
                LoadMore();
            }
        });
        adapter=new WorkAdapter(this,read);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh();//一进来就自动刷新一次(展示第一页的数据)

//        //请求第一页数据
        CompanyImpl companyimpl=new CompanyImpl(new MyHTTPResultListener(1));
        companyimpl.getCompanyInfoList(ssid, account, parentCode, childcode, "1", "11", "2");
    }




    //上拉加载更多的回调方法
    private void LoadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("loadmoreloadmore...", "");
                pageNo = pageNo + 1;
                String page = String.valueOf(pageNo);
                List<String> num = new ArrayList<String>();
                for (int i = 0; i < read.size(); i++) {
                    num.add(read.get(i).getPageno());
                }
                //从数据库里面查询
                if (companyInfoService.queryCompanyInfotype_pageno(parentCode, page).size() > 0) {
                    for (int i = 0; i < read.size(); i++) {
                        //如果读取到
                        if (read.get(i).getPageno().equals(page) && num.size() > 0) {
                            break;
                        } else {
                            //否则添加到数据库里面
                            list = companyInfoService.queryCompanyInfotype_pageno(parentCode, page);
                            read.addAll(list);
                            WorkAdapter workAdapter = new WorkAdapter(IntentActivity.this, read);
                            zrcListView.setAdapter(workAdapter);
                            break;
                        }
                    }
                    //通知适配器更新数据
                    adapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();//加载成功
                    zrcListView.startLoadMore();//开始加载更多
                    Itemclick(read);

                } else {
                    //调用接口的数据 显示
                    CompanyImpl companyimpl = new CompanyImpl(new MyHTTPResultListener3());
                    companyimpl.getCompanyInfoList(ssid, account, parentCode, childcode, page, "11", "2");
                    zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                    zrcListView.startLoadMore();
                }
//                if (pageNo==0){
//                    Log.i("数据加载完成","");
//                     Toast.makeText(getActivity(),"没有更多数据可以加载",Toast.LENGTH_LONG).show();
//
//                }
            }
        }, 2000);

    }
//    //在退出实现刷新或者上拉加载的时候程序不崩溃
//    protected void onResume() {
//        super.onResume();
//        if(zrcListView == null) return ;
//        WorkAdapter adapter = new WorkAdapter(IntentActivity.this, list);
//        zrcListView.setAdapter(adapter);
//        zrcListView.setSelection(0);
//    }

    //下拉刷新else的请求数据
    class MyHTTPResultListener2 implements HttpResultListener {
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
            }else {
                companyInfoList = (List<CompanyInfo>)obj;
                if(companyInfoList.size()>0){
                    //将集合中的数据存到数据库里面
                    companyInfoService.saveCompanyInfoLists(companyInfoList);
                    read.addAll(companyInfoList);
                    //排序 1.list集合2.
                    Collections.sort(read, new Comparator<CompanyInfo>() {
                        @Override
                        public int compare(CompanyInfo lhs, CompanyInfo rhs) {
                            return lhs.getPageno().compareTo(rhs.getPageno());
                        }
                    });
                    adapter.notifyDataSetChanged();
                    zrcListView.setRefreshSuccess();
                    Itemclick(read);
                }else{
                    //直接查询已经存在的
                    exit = companyInfoService.queryCompanyInfotype_pageno(parentCode,1+"");
                    WorkAdapter adapter = new WorkAdapter(IntentActivity.this, exit);
                    //添加单适配器里面
                    zrcListView.setAdapter(adapter);
                    Itemclick(exit);
                }

            }
        }

        @Override
        public void onFinish() {

        }
    }
    //

    //上拉加载更多else的请求数据
    class MyHTTPResultListener3 implements HttpResultListener{
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
            }else {
                companyInfoList = (List<CompanyInfo>)obj;
                if(companyInfoList.size()>0){
                    //将数据保存到数据库中
                    companyInfoService.saveCompanyInfoLists(companyInfoList);
                    //将集合中的数据添加到读取的集合里面
                    read.addAll(companyInfoList);
                    //更新数据
                    adapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Itemclick(read);
                }
                else{
//                    //显示已经在数据库存在的数据
//                    exit = companyInfoService.queryCompanyInfotype_pageno(parentCode,1+"");
//                    WorkAdapter adapter = new WorkAdapter(IntentActivity.this, exit);
//                    zrcListView.setAdapter(adapter);
//                    Itemclick(exit);
//                    zrcListView.stopLoadMore();
                    Toast.makeText(IntentActivity.this,"数据加载完成",Toast.LENGTH_LONG).show();
                }

            }
        }


        @Override
        public void onFinish() {
            stopProgressDialog();
        }
    }
    //

    @Override
    protected void onResume() {
        super.onResume();
        //退出之后自动加载一次数据
        zrcListView.refresh();
    }

    //详情的事件
    private void Itemclick(final List<CompanyInfo> click){

        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Log.d("祝鹏辉IntentFragment1", "新闻点击事件");
                Intent intent = new Intent();
                intent.setClass(IntentActivity.this, NewsDetailActivity.class);
//                intent.setClass(IntentActivity.this, SingleNewActivity1.class);
//                intent.setClass(getActivity(), SingleActivity.class);
                intent.putExtra("infoid", click.get(position).getInfoid());
                Log.i("infoid", click.get(position).getInfoid());
                intent.putExtra("infotype", click.get(position).getInfotype());
                Page = click.get(position).getPageno();
                startActivityForResult(intent, 0);
            }
        });
    }



    //下拉刷新的请求
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
                        companyInfoList = (List<CompanyInfo>)obj;
                        if(companyInfoList.size()>0){
                            companyInfoService.saveCompanyInfoLists(companyInfoList);
                            read.addAll(companyInfoList);
//                            zrcListView.setAdapter(new WorkAdapter(IntentActivity.this, companyInfoList));
//                            zrcListView.setOnItemClickListener(this);
                            Collections.sort(read, new Comparator<CompanyInfo>() {
                                @Override
                                public int compare(CompanyInfo lhs, CompanyInfo rhs) {
                                    return lhs.getPageno().compareTo(rhs.getPageno());
                                }
                            });
                            adapter.notifyDataSetChanged();
                            zrcListView.setRefreshSuccess();
                            zrcListView.startLoadMore();
                            Itemclick(read);
                        } else if(companyInfoList.size()==0){
                            if(pageNo==1){
                                Log.i("无数据","");
                                Toast.makeText(IntentActivity.this,"接口无数据返回",Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent();
//                                intent.setClass(IntentActivity.this,MainActivity.class);
//                                startActivity(intent);
                                finish();
                            }
                            else{
                                CompanyImpl companyimpl=new CompanyImpl(new MyHTTPResultListener(1));
                                companyimpl.getCompanyInfoList(ssid, account, parentCode, childcode,"1", "11", "2");
                            }
                        }
                        else{
                            Toast.makeText(IntentActivity.this,"网络异常，接口无法连接",Toast.LENGTH_SHORT).show();
                            CompanyImpl company=new CompanyImpl(new MyHTTPResultListener(1));
                            company.getCompanyInfoList(ssid,account,parentCode,childcode
                                    ,  pageNo > 1 ? String.valueOf(pageNo - 1) : String.valueOf(pageNo), "11", "2");
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
    //加载数据
//    @Override
//    public void onResume() {
//        super.onResume();
//        final ProgressDialog pd = showProDialog();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pd.dismiss();
//            }
//        }, 2000);
//        //调用接口，显示数据
//        CompanyImpl company = new CompanyImpl(new httpResult());
//        company.getCompanyInfoList(ssid,account,parentCode,childcode,"1","11","2");
//
//    }
    //数据请求
    class httpResult implements HttpResultListener{
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
            }else {
                companyInfoList = (List<CompanyInfo>)obj;
                if(companyInfoList.size()>0){
                    //将数据保存到数据库中
                    companyInfoService.saveCompanyInfoLists(companyInfoList);
                    //将集合中的数据添加到读取的集合里面
                    read.addAll(companyInfoList);
                    //更新数据
                    adapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Itemclick(read);
                    startProgressDialog("正在加载数据，请稍后。。");
                }
                else{
//                    //显示已经在数据库存在的数据
                    exit = companyInfoService.queryCompanyInfotype_pageno(parentCode,1+"");
                    WorkAdapter adapter = new WorkAdapter(IntentActivity.this, exit);
                    zrcListView.setAdapter(adapter);
//                    Itemclick(exit);
//                    zrcListView.stopLoadMore();
                    Toast.makeText(IntentActivity.this,"数据加载完成",Toast.LENGTH_LONG).show();
                }

            }
        }

        @Override
        public void onFinish() {
            progressDialog.dismiss();
        }
    }
    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void startProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(IntentActivity.this);
            progressDialog.setMessage(msg);
        }

        progressDialog.show();
    }
    //加载数据
    private ProgressDialog showProDialog() {
        pdDialog = new ProgressDialog(IntentActivity.this);
        pdDialog.setMessage("loading...");
        //控制手机自带的返回键
        pdDialog.setCancelable(false);
        pdDialog.show();
        //销毁image
        onDestroy();

        return pdDialog;
    }
//    private class WorkAdapter extends BaseAdapter {
//        private LayoutInflater mInflater;
//        private List<CompanyInfo> data;
//        private Context context;
//        final   class ViewHolder{
//            TextView title;
//            TextView time;
//            TextView read;
//            TextView unit;
//            TextView tag;
//        }
//
//        public WorkAdapter(Context context,List<CompanyInfo> data){
//            this.context=context;
//            this.mInflater = LayoutInflater.from(context);
//            this.data=data;
//        }
//
//
//
//        @Override
//        public int getCount() {
//            return data==null ? 0 :data.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return data.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            CompanyInfo m=(CompanyInfo)this.getItem(position);
//            if(convertView == null){
//                convertView = mInflater.inflate(R.layout.menu_list,null);
//                holder = new ViewHolder();
//                holder.title = (TextView)convertView.findViewById(R.id.title);
//                holder.time= (TextView)convertView.findViewById(R.id.time);
//                holder.read = (TextView)convertView.findViewById(R.id.read);
//                holder.unit = (TextView)convertView.findViewById(R.id.unit);
//                holder.tag = (TextView)convertView.findViewById(R.id.tag);
//                convertView.setTag(holder);
//            }else{
//                holder = (ViewHolder)convertView.getTag();
//            }
//            holder.title.setText(m.getInfoname());
//            holder.time.setText(m.getInfodate());
//            holder.read.setText(m.getNum());
//            holder.unit.setText(m.getSource());
////            holder.tag.setText(m.getIsnew());
//            if(m.getIsnew().equals("1")) {
//                Log.i("新闻新闻新闻", "oooooo");
//                holder.tag.setText("新");
//            }else{
//                holder.tag.setVisibility(View.GONE);
//            }
//
////            else
////            {
////                if(Integer.valueOf(m.getNum()) > 100){
//////                    holder.tag.setText("热");
////                    holder.tag.setText("新");
////                }
//////                else {
//////                    holder.tag.setVisibility(View.GONE);
//////                }
////            }
////            if (m.getIsnew().equals("1")){
////                Log.i("新闻新闻新闻", "oooooo");
////                holder.tag.setText("新");
////            }else if (m.getIsnew().equals("0")){
////                holder.tag.setVisibility(View.GONE);
////            }else {
////                if(Integer.valueOf(m.getNum()) > 100){
////                    holder.tag.setText("热");
////                }
//////                else {
//////                    holder.tag.setVisibility(View.GONE);
//////                }
////            }
//            return convertView;
//        }
//    }
//    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Log.d("祝鹏辉IntentFragment2","新闻点击事件");
        Intent intent=new Intent();
//        intent.setClass(IntentActivity.this, SingleNewActivity1.class);
        intent.setClass(IntentActivity.this, NewsDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("infoid",companyInfoList.get(position - 1).getInfoid());
        bundle.putString("infotype", companyInfoList.get(position -1).getInfotype());
        intent.putExtras(bundle);
        Page=companyInfoList.get(position).getPageno();
        startActivityForResult(intent, 0);

    }
    //浏览量
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<CompanyInfo> list=new ArrayList<CompanyInfo>();
        for(int i = 0 ; i < read.size() ; i++){
            if(read.get(i).getPageno().equals(Page)){
                list.add(read.get(i));
            }
        }
//        //对信息集合进行遍历
        for(CompanyInfo companyInfo : list){
            if(read.contains(companyInfo)){
                read.remove(companyInfo);
            }
        }
        adapter.notifyDataSetChanged();
        if(Page.equals("1")){
            CompanyImpl companyimpl=new CompanyImpl(new MyHTTPResultListener(1));
            companyimpl.getCompanyInfoList(ssid, account,parentCode, childcode, Page, "11", "2");
        }else{
            CompanyImpl companyimpl=new CompanyImpl(new MyHTTPResultListener(1));
            companyimpl.getCompanyInfoList(ssid, account,parentCode, childcode, Page, "11", "2");
        }
    }

    //




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Log.i("123","yesyesyeys");
                finish();
                break;
        }
    }

}
