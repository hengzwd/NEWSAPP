package newsemc.com.awit.news.newsemcapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.CompanyNewSingleActivity;
import newsemc.com.awit.news.newsemcapp.activity.MainActivity;
import newsemc.com.awit.news.newsemcapp.adapter.DiaoDuAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.DiaoDuInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.DiaoDuImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Administrator on 15-7-9.
 */
public class DiaoDuFragment extends Fragment implements HttpResultListener,ZrcListView.OnItemClickListener{
    ZrcListView zrcListView;

    private int pageNo = 1;
    private List<DiaoDuInfo> compwnylist;
    private TextView prp;
    private Button back;
    private String ssid;
    private String account;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.diaodufragment, null);
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

        prp=(TextView)view.findViewById(R.id.prp);
        back=(Button)view.findViewById(R.id.back);
        prp.setText("公司信息-调度公告");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        DiaoDuImpl diaoDuImpl = new DiaoDuImpl(DiaoDuFragment.this);
        diaoDuImpl.getDiaoDuInfoList(ssid,account, "14", "1", "10", "1");

        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);

        zrcListView = (ZrcListView)view.findViewById(R.id.work);
        zrcListView.setHeadable(header);
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                pageNo = pageNo+1;
                String page = String.valueOf(pageNo);
                Log.i("pageno",pageNo+"");
                DiaoDuImpl diaoDuImpl = new DiaoDuImpl(DiaoDuFragment.this);
                diaoDuImpl.getDiaoDuInfoList(ssid,account, "14", page, "10", "2");
                zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                zrcListView.startLoadMore();
            }
        });

        return  view;
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
            compwnylist=(List<DiaoDuInfo>)obj;
            if(compwnylist.size()>0){
                zrcListView.setAdapter(new DiaoDuAdapter(getActivity(),compwnylist));
                zrcListView.setOnItemClickListener(this);
            }else{
                DiaoDuImpl diaoDuImpl = new DiaoDuImpl(DiaoDuFragment.this);
                diaoDuImpl.getDiaoDuInfoList(ssid,account, "14",
                        pageNo>1?String.valueOf(pageNo-1):String.valueOf(pageNo), "10", "2");
            }

        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"暂无详情",Toast.LENGTH_SHORT).show();
    }
}
