package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;


/**
 * 暂无用到
 * Created by Administrator on 15-6-9.
 */
public class GridSingleActivity extends Activity {
    private ImageView img;
    private TextView name,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.gridsingleitem);
        img=(ImageView)findViewById(R.id.img);
        name=(TextView)findViewById(R.id.name);
        date=(TextView)findViewById(R.id.date);
        Bundle bundle=this.getIntent().getExtras();
        img.setImageResource(Integer.parseInt(bundle.getString("itemimg")));
        name.setText(bundle.getString("itemname"));
        date.setText(bundle.getString("itemdate"));
    }
}
