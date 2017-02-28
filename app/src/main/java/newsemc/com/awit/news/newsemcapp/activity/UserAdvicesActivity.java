package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.net.ssl.HandshakeCompletedListener;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.FeedBack;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.FeedBackImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;

/**
 * 用户反馈界面
 * Created by sb on 2015/8/27.
 */
public class UserAdvicesActivity extends Activity implements HttpResultListener{
    private Button back;
    private Button commit;
    private String ssid;
    private String account;
    private FeedBack feedBackobj;
    private EditText writeadv,contact;
    private String imei;
    private int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.useradviceactivity);
        back=(Button)findViewById(R.id.back);
        commit=(Button)findViewById(R.id.commit);
        writeadv=(EditText)findViewById(R.id.writeadv);
        contact=(EditText)findViewById(R.id.contact);
        //取得从specialactivity存储的Spec
        //DDDD
        SharedPreferences testspec=getSharedPreferences("testlogin",MainActivity.MODE_PRIVATE);
        String special=testspec.getString("test",null);
        Log.i("special：：：：：：",special+"");

        if ("test".equals(special)){
            Log.i("进入if","");
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences=getSharedPreferences("SPEC", MainActivity.MODE_PRIVATE);
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
            SharedPreferences sharedPreferences=getSharedPreferences("SP",MainActivity.MODE_PRIVATE);
            ssid=sharedPreferences.getString("KEY","");
            Log.i("ssid", ssid + "");
            account=sharedPreferences.getString("ACCOUNT","");
            Log.i("ACCOUNT", account + "");
            //finish();


        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("1111111","11111");
                //用户反馈为空校验
                if ("".equals(writeadv.getText().toString())) {
                    Toast.makeText(UserAdvicesActivity.this, "反馈意见不能为空,请填写建议内容", Toast.LENGTH_SHORT).show();
                } else if (writeadv.getText().length() < 10) {
                    Toast.makeText(UserAdvicesActivity.this, "反馈意见文字不能小于10", Toast.LENGTH_SHORT).show();
                } else if (writeadv.getText().length() > 120) {
                    Toast.makeText(UserAdvicesActivity.this, "反馈意见文字不能超过120", Toast.LENGTH_SHORT).show();
                } else {
                    FeedBackImpl feedBackImpl = new FeedBackImpl(UserAdvicesActivity.this);
                    feedBackImpl.uploadAdvice(ssid, imei, "1", writeadv.getText().toString(), "测试用户反馈", contact.getText().toString());
                    Log.i("输入的用户建议是：", writeadv.getText().toString());
                    Log.i("输入的用户联系方式是:", contact.getText().toString());


                }


            }
        });
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
                Toast.makeText(UserAdvicesActivity.this,"接口无法连接,用户反馈提交失败",Toast.LENGTH_SHORT).show();
            }
        }else{
            feedBackobj=(FeedBack)obj;
            if(feedBackobj.getStatus().equals("0")){
                Intent intent=new Intent();
                intent.setClass(UserAdvicesActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(UserAdvicesActivity.this,"您的建议已提交",Toast.LENGTH_SHORT).show();
            } else if(time <= 120){
                Log.i("222222222","2222222222222222");
                Toast.makeText(UserAdvicesActivity.this,"请稍后再提交意见",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFinish() {

    }
}

