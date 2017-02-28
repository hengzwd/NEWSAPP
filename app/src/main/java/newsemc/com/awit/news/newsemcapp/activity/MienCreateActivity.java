package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.MienAdapter;
import newsemc.com.awit.news.newsemcapp.fragment.MienFragment;
import newsemc.com.awit.news.newsemcapp.fragment.QuanLuFragment;
import newsemc.com.awit.news.newsemcapp.fragment.StoneFragment;


/**
 * 建设风采模块
 * Created by Administrator on 15-6-3.
 */
public class MienCreateActivity extends FragmentActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private TextView projectinfo,companyinfo,prp,stoneinfo;
    private ImageView quanlu,gongsi,tashan;
    private RelativeLayout projectlay,company,stone;
    private Button back;
    private GridView listView;
    private List<Map<String,Object>> list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.mieniteminfo);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.replace_fragment, new MienFragment());
//        ft.commit();

        prp=(TextView) findViewById(R.id.prp);
//        projectlay=(RelativeLayout)findViewById(R.id.projectinfo_layout);
//        company=(RelativeLayout)findViewById(R.id.companyinfo_layout);
//        stone=(RelativeLayout)findViewById(R.id.stone_layout);
//        projectinfo=(TextView)findViewById(R.id.projectinfo_text);
//        companyinfo=(TextView)findViewById(R.id.companyinfo_text);
//        stoneinfo=(TextView)findViewById(R.id.stone_text);
        quanlu=(ImageView)findViewById(R.id.projectinfo_image);
        gongsi=(ImageView)findViewById(R.id.qualityinfo_image);
        tashan=(ImageView)findViewById(R.id.workinfo_image);
        back=(Button)findViewById(R.id.back);
        quanlu.setOnClickListener(this);
        gongsi.setOnClickListener(this);
        tashan.setOnClickListener(this);
//        projectinfo.setOnClickListener(this);
//        companyinfo.setOnClickListener(this);
//        stoneinfo.setOnClickListener(this);
        back.setOnClickListener(this);

        prp.setText("全路风采");
//        projectlay.setBackgroundColor(getResources().getColor(R.color.hui));
//        projectinfo.setTextColor(getResources().getColor(R.color.black));
//
//        company.setBackgroundColor(R.drawable.tab_bg);
//        companyinfo.setTextColor(getResources().getColor(R.color.hui));
//        stone.setBackgroundColor(R.drawable.tab_bg);
//        stoneinfo.setTextColor(getResources().getColor(R.color.hui));
        ft.replace(R.id.replace_fragment, new QuanLuFragment());
        ft.commit();
        //initMainListView();
//        //取得从specialactivity存储的Spec
//        SharedPreferences spec=getActivity().getSharedPreferences("speciallogin", CompanyNewSingleActivity.MODE_PRIVATE);
//        String specialName=spec.getString("special","");
//        Log.i("specialName", "");
//        if (specialName.equals("special")){
//            Log.i("进入projectif","");
//            //获取特殊登录存储的数据
//            SharedPreferences SpecialsharedPreferences=getActivity().getSharedPreferences("SP", CompanyNewSingleActivity.MODE_PRIVATE);
//            ssid=SpecialsharedPreferences.getString("KEY","");
//            Log.i("Specialssid",ssid );
//            account=SpecialsharedPreferences.getString("KEY1","");
//            Log.i("SPECIALACCOUNT",account);
//
//        }else{
//            //正常登录过来的
//            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP", CompanyNewSingleActivity.MODE_PRIVATE);
//            ssid=sharedPreferences.getString("KEY","");
//            Log.i("ssid", ssid + "");
//            account=sharedPreferences.getString("ACCOUNT","");
//            Log.i("ACCOUNT", account + "");
//
//        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.projectinfo_image:
                prp.setText("全路风采");
//                projectlay.setBackgroundColor(getResources().getColor(R.color.hui));
//                projectinfo.setTextColor(getResources().getColor(R.color.black));
//
//                company.setBackgroundColor(R.drawable.tab_bg);
//                companyinfo.setTextColor(getResources().getColor(R.color.hui));
//                stone.setBackgroundColor(R.drawable.tab_bg);
//                stoneinfo.setTextColor(getResources().getColor(R.color.hui));
                ft.replace(R.id.replace_fragment, new QuanLuFragment());
                ft.commit();
                break;
            case R.id.qualityinfo_image:
                prp.setText("公司风采");
//                company.setBackgroundColor(getResources().getColor(R.color.hui));
//                companyinfo.setTextColor(getResources().getColor(R.color.black));
//
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                stone.setBackgroundColor(R.drawable.tab_bg);
//                stoneinfo.setTextColor(getResources().getColor(R.color.hui));
                ft.replace(R.id.replace_fragment, new MienFragment());
                ft.commit();
                break;
            case R.id.workinfo_image:
                prp.setText("他山之石");
//                stone.setBackgroundColor(getResources().getColor(R.color.hui));
//                stoneinfo.setTextColor(getResources().getColor(R.color.black));
//
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                company.setBackgroundColor(R.drawable.tab_bg);
//                companyinfo.setTextColor(getResources().getColor(R.color.hui));
                ft.replace(R.id.replace_fragment, new StoneFragment());
                ft.commit();
                break;
            case R.id.back:
                Log.i("123", "gogogooggo");
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.setClass(MienCreateActivity.this, GridSingleActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("itemimg",list_view.get(position).get("drawer_list_itme_img")+"");
        bundle.putString("itemname",list_view.get(position).get("drawer_list_itme_name")+"");
        bundle.putString("itemdate",list_view.get(position).get("drawer_list_itme_date")+"");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
