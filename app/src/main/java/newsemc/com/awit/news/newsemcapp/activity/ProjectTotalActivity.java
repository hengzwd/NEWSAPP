package newsemc.com.awit.news.newsemcapp.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.fragment.AnnoucementFragment;
import newsemc.com.awit.news.newsemcapp.fragment.CompanyNewsFragment;
import newsemc.com.awit.news.newsemcapp.fragment.DiaoDuFragment;
import newsemc.com.awit.news.newsemcapp.fragment.JSCompanyNewsFragment;
import newsemc.com.awit.news.newsemcapp.fragment.ZhongYaoFragment;


/**
 * 公司信息模块
 * Created by Administrator on 15-6-2.
 */
public class ProjectTotalActivity extends FragmentActivity implements View.OnClickListener{
    private TextView projectinfo,qualityinfo,workinfo,studyinfo,prp;
    private ImageView info,annouce,diaodu,wenjian;
    private RelativeLayout projectlay,qualitylay,worklay,studylay;
    private Button back;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.projectiteminfo);

//        projectinfo=(TextView)findViewById(R.id.projectinfo_text);
//        qualityinfo=(TextView)findViewById(R.id.qualityinfo_text);
//        workinfo=(TextView)findViewById(R.id.workinfo_text);
//        studyinfo=(TextView)findViewById(R.id.studyinfo_text);
        projectlay=(RelativeLayout)findViewById(R.id.projectinfo_layout);
//        qualitylay=(RelativeLayout)findViewById(R.id.qualityinfo_layout);
//        worklay=(RelativeLayout)findViewById(R.id.workinfo_layout);
//        studylay=(RelativeLayout)findViewById(R.id.studyinfo_layout);
        info=(ImageView)findViewById(R.id.projectinfo_image);
        annouce=(ImageView)findViewById(R.id.qualityinfo_image);
        diaodu=(ImageView)findViewById(R.id.workinfo_image);
        wenjian=(ImageView)findViewById(R.id.studyinfo_image);
        info.setOnClickListener(this);
        annouce.setOnClickListener(this);
        diaodu.setOnClickListener(this);
        wenjian.setOnClickListener(this);
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
        ft.replace(R.id.replace_fragment, new CompanyNewsFragment());
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
            case R.id.projectinfo_image:
//                projectlay.setBackgroundColor(getResources().getColor(R.color.hui));
//                projectinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));


                ft.replace(R.id.replace_fragment, new CompanyNewsFragment());
                ft.commit();
                break;

            case R.id.qualityinfo_image:
//                qualitylay.setBackgroundColor(getResources().getColor(R.color.hui));
//                qualityinfo.setTextColor(getResources().getColor(R.color.black));
//
//                projectlay.setBackgroundResource(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));

                ft.replace(R.id.replace_fragment,new AnnoucementFragment());
                ft.commit();
                break;

            case R.id.workinfo_image:
//                worklay.setBackgroundColor(getResources().getColor(R.color.hui));
//                workinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));
                ft.replace(R.id.replace_fragment, new DiaoDuFragment());
                ft.commit();
                break;

            case R.id.studyinfo_image:
//                studylay.setBackgroundColor(getResources().getColor(R.color.hui));
//                studyinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));

                ft.replace(R.id.replace_fragment, new ZhongYaoFragment());
                ft.commit();
                break;
            case R.id.back:
                Log.i("123","gogogooggo");
                finish();
                break;
        }
    }

}
