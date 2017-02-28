package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import newsemc.com.awit.news.newsemcapp.R;

/**
 * 设置-关于我们
 * Created by sb on 2015/8/20.
 */






public class AboutUsActivity extends Activity {
    private Button back;
    private TextView versioname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.aboutusactivity);
        back=(Button)findViewById(R.id.back);
        versioname=(TextView)findViewById(R.id.versionname);
        versioname.setText(getVerCode(AboutUsActivity.this,"newsemc.com.awit.news.newsemcapp"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 获得手机端版本号
     */
    public String getVerCode(Context context,String pkg) {
        String verCode = "-1";
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    pkg, 0).versionName;
        } catch (Exception e) {
            System.out.println("版本号获取异常:" + e.getMessage());
        }
        return verCode;
    }
}
