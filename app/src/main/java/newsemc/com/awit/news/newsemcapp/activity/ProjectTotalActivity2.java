package newsemc.com.awit.news.newsemcapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.fragment.AnnoucementFragment;
import newsemc.com.awit.news.newsemcapp.fragment.CompanyInfoFragment;
import newsemc.com.awit.news.newsemcapp.fragment.CompanyNewsFragment;
import newsemc.com.awit.news.newsemcapp.fragment.DiaoDuFragment;
import newsemc.com.awit.news.newsemcapp.fragment.HYJiYaoFragment;
import newsemc.com.awit.news.newsemcapp.fragment.HuiYiJiYaoFragment;
import newsemc.com.awit.news.newsemcapp.fragment.JsTongZhiFragment;
import newsemc.com.awit.news.newsemcapp.fragment.ZhongYaoFragment;

/**
 * Created by Administrator on 15-10-19.
 */
public class ProjectTotalActivity2 extends FragmentActivity implements View.OnClickListener{
    private TextView projectinfo,qualityinfo,workinfo,studyinfo,prp;
    private ImageView zljhuiyijiyao,jstongzhi,huiyijiyao,companyinfo;
    private RelativeLayout projectlay,qualitylay,worklay,studylay;
    private Button back;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.projectiteminfo2);

//        projectinfo=(TextView)findViewById(R.id.projectinfo_text);
//        qualityinfo=(TextView)findViewById(R.id.qualityinfo_text);
//        workinfo=(TextView)findViewById(R.id.workinfo_text);
//        studyinfo=(TextView)findViewById(R.id.studyinfo_text);
        //projectlay=(RelativeLayout)findViewById(R.id.projectinfo_layout);
//        qualitylay=(RelativeLayout)findViewById(R.id.qualityinfo_layout);
//        worklay=(RelativeLayout)findViewById(R.id.workinfo_layout);
//        studylay=(RelativeLayout)findViewById(R.id.studyinfo_layout);
        zljhuiyijiyao=(ImageView)findViewById(R.id.zongjlhuiyijiyao_image);
        jstongzhi=(ImageView)findViewById(R.id.jstongzhi_image);
        huiyijiyao=(ImageView)findViewById(R.id.huiyijiyao_image);
        companyinfo=(ImageView)findViewById(R.id.companyinfo_image);

        zljhuiyijiyao.setOnClickListener(this);
        jstongzhi.setOnClickListener(this);
        huiyijiyao.setOnClickListener(this);
        companyinfo.setOnClickListener(this);
//        projectinfo.setOnClickListener(this);
//        qualityinfo.setOnClickListener(this);
//        workinfo.setOnClickListener(this);
//        studyinfo.setOnClickListener(this);




//        projectlay.setBackgroundColor(getResources().getColor(R.color.hui));
//        projectinfo.setTextColor(getResources().getColor(R.color.black));
//
//        qualitylay.setBackgroundColor(R.drawable.tab_bg);
//        qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//        worklay.setBackgroundColor(R.drawable.tab_bg);
//        workinfo.setTextColor(getResources().getColor(R.color.hui));
//        studylay.setBackgroundColor(R.drawable.tab_bg);
//        studyinfo.setTextColor(getResources().getColor(R.color.hui));
        ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.replace_fragment, new HuiYiJiYaoFragment());
        ft.commit();
//        //取得从specialactivity存储的Spec
//        SharedPreferences spec=getSharedPreferences("speciallogin", MainActivity.MODE_PRIVATE);
//        String specialName=spec.getString("special","");
//        Log.i("specialName", "");
//        if (specialName.equals("special")){
//            Log.i("进入if","");
//            //获取特殊登录存储的数据
//            SharedPreferences SpecialsharedPreferences=getSharedPreferences("SP",MainActivity.MODE_PRIVATE);
//            ssid=SpecialsharedPreferences.getString("KEY","");
//            Log.i("Specialssid",ssid );
//            account=SpecialsharedPreferences.getString("KEY1","");
//            Log.i("SPECIALACCOUNT",account);
//
//        }else{
//            //正常登录过来的
//            SharedPreferences sharedPreferences=getSharedPreferences("SP",MainActivity.MODE_PRIVATE);
//            ssid=sharedPreferences.getString("KEY","");
//            Log.i("ssid", ssid + "");
//            account=sharedPreferences.getString("ACCOUNT","");
//            Log.i("ACCOUNT", account + "");
//
//        }

//        initMainListView();
    }


//    private  void initMainListView(){
//        mlistview=(ListView)findViewById(R.id.mainlistview);
//        main_list_view= ValueConfig.getMainListViewData();
//        mlistview.setAdapter(new MainNewsAdapter(this, main_list_view));
//    }

    @Override
    public void onClick(View v) {
        ft=getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.zongjlhuiyijiyao_image:
//                projectlay.setBackgroundColor(getResources().getColor(R.color.hui));
//                projectinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));


                ft.replace(R.id.replace_fragment, new HuiYiJiYaoFragment());
                ft.commit();
                break;

            case R.id.jstongzhi_image:
//                qualitylay.setBackgroundColor(getResources().getColor(R.color.hui));
//                qualityinfo.setTextColor(getResources().getColor(R.color.black));
//
//                projectlay.setBackgroundResource(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));

                ft.replace(R.id.replace_fragment,new JsTongZhiFragment());
                ft.commit();
                break;

            case R.id.huiyijiyao_image:
                huiyijiyao.setImageResource(R.drawable.hyjyh);
//                worklay.setBackgroundColor(getResources().getColor(R.color.hui));
//                workinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));
                ft.replace(R.id.replace_fragment, new HYJiYaoFragment());
                ft.commit();
                break;

            case R.id.companyinfo_image:
//                studylay.setBackgroundColor(getResources().getColor(R.color.hui));
//                studyinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));

                ft.replace(R.id.replace_fragment, new CompanyInfoFragment());
                ft.commit();
                break;
            case R.id.back:
                Log.i("123", "gogogooggo");
                finish();
                break;
        }
    }

}