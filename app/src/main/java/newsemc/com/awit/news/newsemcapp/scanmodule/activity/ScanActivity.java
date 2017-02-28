package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.socks.library.KLog;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import me.drakeet.materialdialog.MaterialDialog;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.QRCodeBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ImageUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.SharedPreferencesUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private static final String TAG = ScanActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 0;
    private QRCodeView mQRCodeView;
    private ImageView iv_back;
    private ImageView iv_photo;
    private ImageView iv_flashlight;
    private boolean isFlashlightOpened = false;
    private Gson mGson;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        initView();
        initData();
        if (TextUtils.isEmpty(NewsEMCAppllication.mToken)) {
            NewsEMCAppllication.mToken = (String) SharedPreferencesUtils.get(getApplicationContext(), "scan_login_token", "");
        }
    }

    private void initView() {
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);

        iv_back = (ImageView) findViewById(R.id.iv_back_scan_activity);
        iv_photo = (ImageView) findViewById(R.id.iv_photo_picker_scan_activity);
        iv_flashlight = (ImageView) findViewById(R.id.iv_flashlight_scan_activity);


    }

    private void initData() {
        mGson = new Gson();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);

            }
        });
        iv_flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashlightOpened) {
                    mQRCodeView.closeFlashlight();
                } else {
                    mQRCodeView.openFlashlight();
                }
                isFlashlightOpened = !isFlashlightOpened;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.e("onResume");
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        KLog.e("onStart");
        mQRCodeView.startCamera();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        super.onStop();
        KLog.e("onStop");
        mQRCodeView.stopCamera();
        mQRCodeView.stopSpot();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        KLog.e(TAG, "二维码:" + result);
        vibrate();
        mQRCodeView.startSpot();
        handleQRCode(result);
    }

    private void handleQRCode(String result) {
        QRCodeBean mQRCodeBean = null;
        try {
            mQRCodeBean = mGson.fromJson(result, QRCodeBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToast(this, "非我司二维码，请扫描正确的二维码!");
            return;
        }
        NewsEMCAppllication.mQRCodeBean = mQRCodeBean;

        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("请确认")
                .setMessage(mQRCodeBean.getName())
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Intent intent = new Intent(ScanActivity.this, ScanMainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("QRCodeBean", NewsEMCAppllication.mQRCodeBean);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                });

        mMaterialDialog.show();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 选择系统图片并解析
         */
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                final Uri uri = data.getData();

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        return QRCodeDecoder.syncDecodeQRCode(ImageUtils.getImageAbsolutePath(ScanActivity.this, uri));
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (TextUtils.isEmpty(result)) {
                            Toast.makeText(ScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                        } else {
                            //解析成功后
                            handleQRCode(result);
                        }
                    }
                }.execute();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mQRCodeView.closeFlashlight();
        finish();
    }
}