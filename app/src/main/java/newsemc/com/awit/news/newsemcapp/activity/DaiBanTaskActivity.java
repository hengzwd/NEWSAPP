package newsemc.com.awit.news.newsemcapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.fragment.DaiBanTaskFragment;

/**
 * Created by Administrator on 15-11-11.
 */
public class DaiBanTaskActivity extends FragmentActivity implements View.OnClickListener{
    private Button btnback;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daibantaskactivity);
        btnback=(Button)findViewById(R.id.back);

        btnback.setOnClickListener(this);


        ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.replace_fragment,new DaiBanTaskFragment());
        ft.commit();
        //Toast.makeText(DaiBanTaskActivity.this, "接口无数据返回", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Log.i("123","gogogog");
                finish();
                break;
        }
    }
}
