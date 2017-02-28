package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import newsemc.com.awit.news.newsemcapp.R;

/**
 * 设置-二维码展示界面
 * Created by sb on 2015/8/31.
 */
public class ErWeiMaActivity extends Activity {
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.erweimaactivity);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
