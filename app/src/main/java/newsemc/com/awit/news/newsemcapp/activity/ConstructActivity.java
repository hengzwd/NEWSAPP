package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.fragment.ProjectInfoFragment;


/**
 * 项目动态模块
 * Created by Administrator on 15-6-3.
 */
public class ConstructActivity extends FragmentActivity implements View.OnClickListener{
    private TextView projectinfo,qualityinfo,workinfo,studyinfo,prp;
    private Button back,but1,but2,but3,but4;
    private RelativeLayout projectlay,qualitylay,worklay,studylay;
    //特殊账号
    private String Specialssid;
    private String Specialaccount;
    private TextView prp1,prp2,prp3;

//    private View temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.constructiteminfo);
        //事物替换
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.replace_fragment, new ProjectInfoFragment());
        ft.commit();
//        //取得从specialactivity存储的Spec
//            SharedPreferences spec=getActivity().getSharedPreferences("speciallogin", CompanyNewSingleActivity.MODE_PRIVATE);
//            String specialName=spec.getString("special","");
//            Log.i("specialName", "");
//            if (specialName.equals("special")){
//                Log.i("进入projectif","");
//                //获取特殊登录存储的数据
//                SharedPreferences SpecialsharedPreferences=getActivity().getSharedPreferences("SP", CompanyNewSingleActivity.MODE_PRIVATE);
//                ssid=SpecialsharedPreferences.getString("KEY","");
//                Log.i("Specialssid",ssid );
//                account=SpecialsharedPreferences.getString("KEY1","");
//                Log.i("SPECIALACCOUNT",account);
//
//            }else{
//                //正常登录过来的
//            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("SP", CompanyNewSingleActivity.MODE_PRIVATE);
//            ssid=sharedPreferences.getString("KEY","");
//            Log.i("ssid", ssid + "");
//            account=sharedPreferences.getString("ACCOUNT","");
//            Log.i("ACCOUNT", account + "");
//
//        }

//        prp=(TextView)findViewById(R.id.prp);
//        prp.setText("项目动态");
//        prp1=(TextView)findViewById(R.id.prp);
//        prp1.setText("项目动态-项目基础信息");
//        prp2=(TextView)findViewById(R.id.prp);
//        prp2.setText("项目动态-施工标段");
//        prp3=(TextView)findViewById(R.id.prp);
//        prp3.setText("项目动态-监理标段");
       // back=(Button)findViewById(R.id.back);
//        but1=(Button)findViewById(R.id.but1);
//        but1.setOnClickListener(this);
//        but2=(Button)findViewById(R.id.but2);
//        but2.setOnClickListener(this);
//        but3=(Button)findViewById(R.id.but3);
//        but3.setOnClickListener(this);
//        but4=(Button)findViewById(R.id.but4);
//        but4.setOnClickListener(this);
//        projectlay=(RelativeLayout)findViewById(R.id.projectinfo_layout);
//        qualitylay=(RelativeLayout)findViewById(R.id.qualityinfo_layout);
//        worklay=(RelativeLayout)findViewById(R.id.workinfo_layout);
//        studylay=(RelativeLayout)findViewById(R.id.studyinfo_layout);
//        projectinfo=(TextView)findViewById(R.id.projectinfo_text);
//        qualityinfo=(TextView)findViewById(R.id.qualityinfo_text);
//        workinfo=(TextView)findViewById(R.id.workinfo_text);
//        studyinfo=(TextView)findViewById(R.id.studyinfo_text);
//        projectinfo.setOnClickListener(this);
//        qualityinfo.setOnClickListener(this);
//        workinfo.setOnClickListener(this);
//        studyinfo.setOnClickListener(this);
//        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
//            case R.id.qualityinfo_text:
//                prp.setText("项目动态-征地拆迁");
//                qualitylay.setBackgroundColor(getResources().getColor(R.color.hui));
//                qualityinfo.setTextColor(getResources().getColor(R.color.black));
//
//                projectlay.setBackgroundResource(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));
//                ft.replace(R.id.replace_fragment, new RequisitionFragment());
//                ft.commit();
//                break;
//            case R.id.projectinfo_text:
//                prp.setText("项目动态-进度管理");
//                projectlay.setBackgroundColor(getResources().getColor(R.color.hui));
//                projectinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));
//                ft.replace(R.id.replace_fragment, new PlanFragment());
//                ft.commit();
//                break;
//
//            case R.id.workinfo_text:
//                prp.setText("项目动态-技术资料");
//                worklay.setBackgroundColor(getResources().getColor(R.color.hui));
//                workinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                studylay.setBackgroundColor(R.drawable.tab_bg);
//                studyinfo.setTextColor(getResources().getColor(R.color.hui));
//                ft.replace(R.id.replace_fragment, new DataMaterailFragment());
//                ft.commit();
//                break;
//
//            case R.id.studyinfo_text:
//                prp.setText("项目动态-质安风险");
//                studylay.setBackgroundColor(getResources().getColor(R.color.hui));
//                studyinfo.setTextColor(getResources().getColor(R.color.black));
//
//                qualitylay.setBackgroundColor(R.drawable.tab_bg);
//                qualityinfo.setTextColor(getResources().getColor(R.color.hui));
//                projectlay.setBackgroundColor(R.drawable.tab_bg);
//                projectinfo.setTextColor(getResources().getColor(R.color.hui));
//                worklay.setBackgroundColor(R.drawable.tab_bg);
//                workinfo.setTextColor(getResources().getColor(R.color.hui));
//                ft.replace(R.id.replace_fragment, new QualitySafetyFragment());
//                ft.commit();
//                break;
            case R.id.back:
                Log.i("123", "gogogooggo");
                finish();
                break;
        }
    }

}
