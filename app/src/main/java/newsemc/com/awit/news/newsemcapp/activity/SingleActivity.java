package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.Detail2Impl1;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.DetailImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.IntentUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 列表详情界面
 * Created by Administrator on 15-6-9.
 */
public class SingleActivity extends Activity implements HttpResultListener {
    //    private TextView downloadtext;
    private static final String TAG = "SingleActivity";
    private TextView title,textview,dateview,readview;
    private List<DetailInfo> detailInfoList;
    private String ssid;
    private String account;
    private String fileName;
    private WebView wv = null;
    private CustomProgressDialog progressDialog = null;
    private String url="http://www.r93535.com/gateway/base/common/attachment!download.action?id=196143";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.singlelistitem);
//        downloadtext=(TextView)this.findViewById(R.id.downloadurl);
        Intent intent=this.getIntent();
        Log.i("title", intent.getStringExtra("infoid"));
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
        }
        DetailImpl detailImpl=new DetailImpl(SingleActivity.this);
        detailImpl.getDetailInfoList(ssid, account, intent.getStringExtra("infotype"), intent.getStringExtra("infoid"));
    }

    // 获取webView
    private WebView getWebView(String url) {

        wv = (WebView) findViewById(R.id.webview_id);
        wv.loadUrl(url);
        WebSettings settings = wv.getSettings();
        wv.setInitialScale(100);
//        settings.setJavaScriptEnabled(true); // 设置WebView支持javascript
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
//        settings.setSupportZoom(true); // 设置支持缩放
//        settings.setBuiltInZoomControls(true);// 显示缩放控件
//        settings.setTextSize(WebSettings.TextSize.LARGEST);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true); //支持js
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        settings.setLoadWithOverviewMode(true);//自适应屏幕
        settings.setUseWideViewPort(true); //扩大比例的缩放
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //显示缩放按钮
        settings.setTextSize(WebSettings.TextSize.LARGEST); //普通字体大小
        settings.setDefaultFontSize(25); //标题大小

        wv.setWebViewClient(new MyWebViewClient());
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
            Log.i(TAG,"url = " + url);
            getWebView(url);
        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = getIntent();
//            intent.putExtra()
            setResult(0, intent);

        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i(TAG, "文件的保存路径：" + ValueConfig.DOWNLOAD);
            Log.i(TAG, "文件下载路径：" + url);
            fileName = url.substring(url.lastIndexOf('=') + 1) + ".";
//            先判断文件是否存在
            if (!isFileExist()){
                downLoadComponyFile(url, fileName);
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        private boolean isFileExist(){
            File file = new File(ValueConfig.DOWNLOAD);
            if (!file.exists()){
                file.mkdirs();
            }else{
                File[] fileList = file.listFiles();
                String[] fileNameList = new String[fileList.length];

                for (int j = 0; j < fileList.length; j++){
                    fileNameList[j] = fileList[j].getName().substring(0, fileList[j].getName().length() - 3);
//                Log.i(TAG, "文件列表：" + fileNameList[j]);
//                Log.i(TAG, "下载的文件名：" + fileName);
                    Log.i(TAG, "文件路径：" + fileList[j].getPath() + "/" + fileList[j].getName());
                    if(fileNameList[j].equals(fileName)){
                        //文件存在
                        Log.i(TAG, "文件存在");
                        openFile(fileList[j].getName());
                        return true;
                    }
                }
            }
            Log.i(TAG, "文件不存在");
            return false;
        }

        private void openFile(String fileName){
            //判断文件后缀名，打开文件
            String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
            fileName = ValueConfig.DOWNLOAD + "/" + fileName;
            Log.i(TAG, "将要打开的文件名：" + fileName);

            switch (filePostfix){
                case "pdf":
                    startActivity(IntentUtils.getPdfFileIntent(fileName));
                    Log.i(TAG, "打开PDF文件！");
                    break;
                case "doc":
                case "docx":
                    startActivity(IntentUtils.getWordFileIntent(fileName));
                    Log.i(TAG, "打开DOC文件！");
                    break;
                case "xls":
                    startActivity(IntentUtils.getExcelFileIntent(fileName));
                    Log.i(TAG, "打开xls文件！");
                    break;
                case "txt":
                    startActivity(IntentUtils.getTextFileIntent(fileName, false));
                    Log.i(TAG, "打开文本文件！");
                    break;
                default:
                    break;
            }

//            Intent intent = new Intent(SingleActivity.this, OpenFileActivity.class);
//            intent.putExtra("file_path", ValueConfig.DOWNLOAD);
//            intent.putExtra("file_name", fileName);
//            startActivity(intent);
        }

        private void downLoadComponyFile(String url, final String fileName){
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                    if (progressDialog == null) {
                        progressDialog = CustomProgressDialog.createDialog(SingleActivity.this);
                        progressDialog.setMessage("文件下载中...");
                    }
                    progressDialog.show();
                }
                @Override
                public void onFinish() {
                    super.onFinish();
                    //停止进度条
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.i(TAG, "下载文件失败！！！");
                    Toast.makeText(SingleActivity.this, "文件下载失败！", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {

                    String namePostfix = headers[headers.length - 1].toString();
                    String intactFileName = fileName + namePostfix.substring(namePostfix.lastIndexOf(".") + 1);
                    Log.i(TAG, "文件下载成功！！！");
                    Log.i(TAG, "文件大小(B)：" + String.valueOf(bytes.length));
                    Log.i(TAG, "文件名称：" + intactFileName);
                    Toast.makeText(SingleActivity.this, "文件下载成功！", Toast.LENGTH_SHORT).show();
                    try {
                        File file = new File(ValueConfig.DOWNLOAD, intactFileName);
                        File parent = file.getParentFile();
                        if (parent != null && !parent.exists())
                            parent.mkdirs();
                        FileOutputStream fos = new FileOutputStream(file);
                        OutputStream output = new DataOutputStream(fos);
                        output.write(bytes);
                        output.flush();
                        output.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    openFile(intactFileName);
                }
            });
        }

    }
}
