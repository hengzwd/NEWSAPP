package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.BitmapHelper;
import newsemc.com.awit.news.newsemcapp.util.MulitPointTouchListener;

/**
 * 全路风采和公司风采详情界面
 * Created by cll on 2015/9/17.
 */
public class MyImageViewActivity extends Activity {
    private ImageView view;
    Matrix matrix = new Matrix();
    Rect rect;
    private ImageButton zoom_in,zoom_out;
    private PointF mid;
    private BitmapUtils bitmapUtils;
    private Intent intent;
    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enlargeimage);
        intent=this.getIntent();
        url=intent.getStringExtra("imageurl");
        Log.i("image：：：：",url);
        if(bitmapUtils == null){
            bitmapUtils = BitmapHelper.getBitmapUtils();
            bitmapUtils.configDefaultLoadingImage(R.drawable.empty_photo); // 设置加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.empty_photo); // 设置加载错误图片
        }
        mid = new PointF();
        findAll();
        setListener();
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

    private void findAll(){
        view = (ImageView) findViewById(R.id.image_View);
        view.setScaleType(ImageView.ScaleType.MATRIX);
        bitmapUtils.display(view,url);
        zoom_in = (ImageButton) findViewById(R.id.ibtn_zoom_in);
        zoom_out = (ImageButton) findViewById(R.id.ibtn_zoom_out);
    }

    private void setListener(){
        view.setOnTouchListener(new MulitPointTouchListener());
        //放大
        zoom_in.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                matrix.set(view.getImageMatrix());
                setMid();//设置放大的中心
                matrix.postScale(1.3f, 1.3f, mid.x,mid.y);
                view.setImageMatrix(matrix);
                view.invalidate();
            }
        });
        //缩小
        zoom_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                matrix.set(view.getImageMatrix());
                setMid();//设置放大的中心
                matrix.postScale(0.8f, 0.8f, mid.x,mid.y);
                view.setImageMatrix(matrix);
                view.invalidate();
            }
        });
    }
    private void setMid(){
        rect = view.getDrawable().getBounds();
        mid.x = view.getDrawable().getBounds().centerX();
        mid.y = view.getDrawable().getBounds().centerY();
    }
}
