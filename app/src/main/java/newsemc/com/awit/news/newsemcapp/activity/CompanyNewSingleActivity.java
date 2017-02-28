package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfo;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.DetailImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;


/**
 * 暂无用到
 * Created by Administrator on 2015/7/11.
 */



public class CompanyNewSingleActivity extends Activity implements HttpResultListener {
    private ImageView img;
    private TextView title,textview,dateview,readview;
    private List<DetailInfo> detailInfoList;
    //特殊账号
    private String Specialssid;
    private String Specialaccount;
    private SpecialAccountInfo specialAccountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.singlelistitem);
//        img=(ImageView)findViewById(R.id.img);
//        title=(TextView)findViewById(R.id.title);
//        textview=(TextView)findViewById(R.id.text);
//        dateview=(TextView)findViewById(R.id.date);
//        readview=(TextView)findViewById(R.id.read);
        Bundle bundle=this.getIntent().getExtras();
//        title.setText(intent.getStringExtra("infoid"));
        Log.i("title", bundle.getString("infoid"));
//        textview.setText(intent.getStringExtra("infotype"));

        //DDDD
        SharedPreferences testspec=getSharedPreferences("testlogin",MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test",null);
        Log.i("special：：：：：：",special);

        if ("test".equals(special)){
            Log.i("进入if","");
            if (special == specialAccountInfo.getAccount()) {
                //获取特殊登录存储的数据
                SharedPreferences SpecialsharedPreferences = getSharedPreferences("SPEC", CompanyNewSingleActivity.MODE_PRIVATE);
                Specialssid = SpecialsharedPreferences.getString("KEY", "");
                Log.i("Specialssid", Specialssid + "");
                Specialaccount = SpecialsharedPreferences.getString("KEY1", "");
                Log.i("SPECIALACCOUNT", Specialaccount + "");
                DetailImpl detailImpl = new DetailImpl(CompanyNewSingleActivity.this);
                detailImpl.getDetailInfoList(Specialssid, Specialaccount, bundle.getString("infotype"), bundle.getString("infoid"));
            } else {

                //清空特殊登录所保存的数据
                SharedPreferences.Editor editor=testspec.edit();
                editor.clear();
                editor.commit();
                Log.i("清空特殊登录的数据","进入else。。。。");
                //正常登录过来的
                SharedPreferences sharedPreferences=getSharedPreferences("SP",MainActivity.MODE_PRIVATE);
                String  ssid=sharedPreferences.getString("KEY","");
                Log.i("ssid", ssid + "");
//                account=sharedPreferences.getString("ACCOUNT","");
//                Log.i("ACCOUNT", account + "");
                //finish();
                DetailImpl detailImpl = new DetailImpl(CompanyNewSingleActivity.this);
                detailImpl.getDetailInfoList(ssid, "liguobin", bundle.getString("infotype"), bundle.getString("infoid"));
            }
        }
    }

    // 获取webView
    private WebView getWebView(String url) {

        WebView wv = (WebView) findViewById(R.id.webview_id);
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        int scalePercent = 100;

        switch (screenDensity) {
            case DisplayMetrics.DENSITY_HIGH:// 800*480
                scalePercent = 75;
                break;
            case DisplayMetrics.DENSITY_XHIGH:// 1280*720
                scalePercent = 100;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:// 1920*1080
                scalePercent = 150;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:// 2560*1440
                scalePercent = 200;
                break;
        }
        wv.setInitialScale(scalePercent);

        WebSettings settings = wv.getSettings();

        settings.setJavaScriptEnabled(true); // 设置WebView支持javascript
        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        settings.setSupportZoom(false); // 设置支持缩放
        settings.setBuiltInZoomControls(false);// 显示缩放控件
        settings.setLoadWithOverviewMode(true);
        wv.requestFocus();
//        file:///android_asset/webview.html
        wv.loadUrl(url);
        return wv;
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
            detailInfoList=(List<DetailInfo>)obj;
            String url=detailInfoList.get(0).getHtmlpath();
            getWebView(url);
        }
    }

    @Override
    public void onFinish() {

    }
}
