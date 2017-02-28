package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.base.BaseActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by leguang on 2016/9/20 0020.
 * 联系邮箱:langmanleguang@qq.com
 */
public class ShowPhotoActivity extends BaseActivity {
    private String mainurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        mainurl = getIntent().getStringExtra("mainurl");
        KLog.e(mainurl);
        initView();
    }

    private void initView() {
        PhotoView photoView = (PhotoView) findViewById(R.id.pv_show_photo_activity);
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        if (null != mainurl) {
//            Picasso.with(this)
//                    .load(mainurl)
//                    .into(photoView, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            attacher.update();
//                        }
//
//                        @Override
//                        public void onError() {
//                            ToastUtils.showToast(getApplicationContext(), "网络访问失败！");
//                            onBackPressed();
//                        }
//                    });
            Glide.with(this).load(mainurl).crossFade().error(R.drawable.downloadfailure).
                   // placeholder(R.drawable.downloadfailure).
                    into(photoView);
            attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                }

                @Override
                public void onOutsidePhotoTap() {
                    onBackPressed();
                }
            });
        } else {

            onBackPressed();
            return;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);//activity切换的动画效果
        finish();
    }
}
