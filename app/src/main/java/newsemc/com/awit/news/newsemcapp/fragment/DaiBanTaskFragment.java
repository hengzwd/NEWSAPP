package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.DaiBanTaskActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.activity.SingleActivity;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.OASysInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.OaSysImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by Administrator on 15-11-11.
 */
public class DaiBanTaskFragment extends Fragment implements ZrcListView.OnItemClickListener {
    private String ssid;
    private String account;
    ZrcListView zrcListView;
    private int pageNo=1;
    private List<OASysInfo> oaSysInfoList;
    private List<CompanyInfo> companyInfoList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.daibantaskfragment, null);
        //DDDD
        SharedPreferences testspec=getActivity().getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test", null);
        Log.i("special：：：：：：", special + "");

        if ("test".equals(special)){
            Log.i("进入intentif","");
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

        //下拉刷新
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView = (ZrcListView)view.findViewById(R.id.work);
        zrcListView.setHeadable(header);
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo = pageNo + 1;
                String page = String.valueOf(pageNo);
                CompanyImpl companyimpl=new CompanyImpl(new MyhttpResultListener(1));
                companyimpl.getCompanyInfoList(ssid, account, "34", "0", "1", "10", "2");
                zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                zrcListView.startLoadMore();
            }
        });
        CompanyImpl companyimpl=new CompanyImpl(new MyhttpResultListener(1));
        companyimpl.getCompanyInfoList(ssid, account, "34", "0", "1", "10", "2");
        Log.i("ssid)))))))", ssid);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    //请求数据的类
    class MyhttpResultListener implements HttpResultListener{
        private int index;
        public MyhttpResultListener(int index){
            this.index=index;

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
                switch (index){
                    case 1:
                        companyInfoList=(List<CompanyInfo>) obj;
                        if (companyInfoList.size()>0){
                            zrcListView.setAdapter(new WorkAdapter(getActivity(),companyInfoList));
                            zrcListView.setOnItemClickListener(DaiBanTaskFragment.this);
                        }else if(companyInfoList.size()==0){
                            if (pageNo==1) {
                                Toast.makeText(getActivity(), "接口无数据返回", Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getActivity(),MainActivity.class);
                                startActivity(intent);
                            } else{
                                CompanyImpl company=new CompanyImpl(new MyhttpResultListener(1));
                                company.getCompanyInfoList(ssid, account, "34", "0","1", "10", "2");
                            }
                        }else {

                            Toast.makeText(getActivity(),"网络异常，接口无法连接",Toast.LENGTH_LONG).show();
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
                convertView = mInflater.inflate(R.layout.daiban_list,null);
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
    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(getActivity(), SingleActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("id",companyInfoList.get(position).getInfoid());
        bundle.putString("type", companyInfoList.get(position).getInfotype());
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);


    }

    //浏览量
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(pageNo==1){
            CompanyImpl company=new CompanyImpl(new MyhttpResultListener(1));
            company.getCompanyInfoList(ssid, account, "34", "0","1", "10", "2");
        }else{
            CompanyImpl company=new CompanyImpl(new MyhttpResultListener(1));
            company.getCompanyInfoList(ssid, account, "34", "0", pageNo + "", "10", "2");
        }
    }
}
