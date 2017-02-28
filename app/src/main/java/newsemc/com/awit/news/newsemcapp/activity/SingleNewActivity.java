package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.AppendFileAdapter;
import newsemc.com.awit.news.newsemcapp.adapter.HttpImgListAdapter;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.NewsDetailEntity;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.http.AsyncImageLoader;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.Detail2Impl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.DetailImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.IntentUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;
import newsemc.com.awit.news.newsemcapp.view.ScrollWithListView;

public class SingleNewActivity extends Activity implements HttpResultListener, View.OnClickListener {

    private static final String TAG = "SingleNewActivity";
    private TextView title, unit, time, read, textContent;
    private NewsDetailEntity detailInfoList;
    private String ssid;
    private String account;
    private String fileName;
    private WebView wv = null;
    private CustomProgressDialog progressDialog = null;
    private String url = "http://www.r93535.com/gateway/base/common/attachment!download.action?id=196143";
    private ListView listView1, listView2, listView3;
    private Button btback;
    private ScrollView scrollView1;
    private LinearLayout ImageList;
    private String str = "";
    private LinearLayout btnconq;
    private Button backq;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_new);

        //初始化控件
        title = (TextView) this.findViewById(R.id.title);
        unit = (TextView) this.findViewById(R.id.unit);
        time = (TextView) this.findViewById(R.id.time);
        read = (TextView) this.findViewById(R.id.read);
        textContent = (TextView) this.findViewById(R.id.textContent);
        listView1 = (ListView) this.findViewById(R.id.listView1);
        listView2 = (ListView) this.findViewById(R.id.listView2);
        listView3 = (ListView) this.findViewById(R.id.listView3);
        btback = (Button) findViewById(R.id.back);
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        ImageList = (LinearLayout) findViewById(R.id.ImageList);
        btback.setOnClickListener(this);
        btnconq = (LinearLayout) findViewById(R.id.btnconq);
        backq = (Button) findViewById(R.id.backq);
        backq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("123", "yesyes");
                finish();
            }
        });
        Intent intent = this.getIntent();
        Log.i("title", intent.getStringExtra("infoid") + "");
        //DDDD
        SharedPreferences testspec = getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        String special = testspec.getString("test", null);
        Log.i("special：：：：：：", special + "");

        if ("test".equals(special)) {
            Log.i("进入if", "");
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences = getSharedPreferences("SPEC", MainActivity.MODE_PRIVATE);
            ssid = SpecialsharedPreferences.getString("KEY", "");
            Log.i("Specialssid", ssid);
            account = SpecialsharedPreferences.getString("ACCOUNT", "");
            Log.i("SPECIALACCOUNT", account);

        } else {
            //清空特殊登录所保存的数据
            SharedPreferences.Editor editor = testspec.edit();
            editor.clear();
            editor.commit();
            Log.i("清空特殊登录的数据", "进入else。。。。");
            //正常登录过来的
            SharedPreferences sharedPreferences = getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
            ssid = sharedPreferences.getString("KEY", "");
            Log.i("ssid", ssid + "");
            account = sharedPreferences.getString("ACCOUNT", "");
            Log.i("ACCOUNT", account + "");
        }
        Detail2Impl detailImpl = new Detail2Impl(SingleNewActivity.this);
        detailImpl.getDetailInfoList(ssid, account, intent.getStringExtra("infotype"), intent.getStringExtra("infoid"));
        StringFilter(str);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private int scrWidth = 300;
    //压缩图片
    private Bitmap saveImage(String fileName,Bitmap bit) {

        File file = new File(fileName);
        if (!file.exists()) {
            try
            {
//                file.createNewFile();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            // 70 是压缩率，表示压缩30%; 如果不压缩是100，表示压缩率为0
            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            os.close();
            return bit;
        } catch (Exception e) {
            file = null;
            return null;
        }
    }
    // 获取webView
    private void showView(NewsDetailEntity data) {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        scrWidth = wm.getDefaultDisplay().getWidth();

        title.setText(data.getData().getList().getInfoname());
        unit.setText(data.getData().getList().getPublishquarry());
        time.setText(data.getData().getList().getInfodate());
        read.setText(data.getData().getList().getNum() + getResources().getString(R.string.ci));
        textContent.setText(Html.fromHtml(data.getData().getList().getInfocontent()));
        List<String> URL = new ArrayList<String>();
        List<String> attachefile = new ArrayList<String>();
        List<String> appdenix = new ArrayList<String>();
        for (int i = 0; i < data.getData().getList().getAttachments().size(); i++) {
            NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity tmp = data.getData().getList().getAttachments().get(i);
            URL.add(tmp.getSavePath());
            attachefile.add(tmp.getFileName());
            Log.d("zhuph attachefile[" + i + "]=", tmp.getSavePath());
        }
        //加载图片列表
        if (data.getData().getList().getInfotype().equals("16")) {
            NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity tmp = data.getData().getList().getAttachments().get(0);
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.empty_photo);
            saveImage(tmp.getFileName(),bitmap);
//            comp(bitmap);
            HttpImgListAdapter adapter = new HttpImgListAdapter(SingleNewActivity.this, URL, listView1, scrWidth);
            listView1.setAdapter(adapter);
            //listView1.setMinimumHeight(1000);
            ViewGroup.LayoutParams params = ImageList.getLayoutParams();
            params.height = adapter.getCount() * 500;
            //Log.d("totalHeight",totalHeight + "");
            ImageList.setLayoutParams(params);
            //重新设置焦点
            scrollView1.fullScroll(ScrollView.FOCUS_UP); //滚动到顶部
            title.setFocusable(true);

        } else {
            listView1.setVisibility(View.GONE);
            Log.d("zhuph attachefile[0]=", attachefile.get(0) + "");

            AppendFileAdapter adapter2 = new AppendFileAdapter(SingleNewActivity.this, attachefile);
            listView2.setAdapter(adapter2);
            ScrollWithListView.setListViewHeightBasedOnChildren(listView2);

            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //下载附件
                    String url = detailInfoList.getData().getList().getAttachments().get(position).getSavePath();
                    Log.i(TAG, "文件的保存路径：" + ValueConfig.DOWNLOAD);
                    Log.i(TAG, "文件下载路径：" + url);
                    fileName = url.substring(url.lastIndexOf('=') + 1) + ".";
                    //先判断文件是否存在
                    if (!isFileExist()) {
                        downLoadComponyFile(url, fileName);
                    }
                }
            });
        }


        //加载附件列表
        for (int i = 0; i < data.getData().getList().getAppendix().size(); i++) {
            NewsDetailEntity.DataEntity.ListEntity.AppendixEntity tmp = data.getData().getList().getAppendix().get(i);
            appdenix.add(tmp.getFileName());
        }
        AppendFileAdapter adapter3 = new AppendFileAdapter(SingleNewActivity.this, appdenix);
        listView3.setAdapter(adapter3);
        ScrollWithListView.setListViewHeightBasedOnChildren(listView3);
//        wv = (WebView) findViewById(R.id.webview_id);
//        wv.loadUrl(url);
//        WebSettings settings = wv.getSettings();
//        wv.setInitialScale(100);
//        settings.setJavaScriptEnabled(true); // 设置WebView支持javascript
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
//        settings.setSupportZoom(true); // 设置支持缩放
//        settings.setBuiltInZoomControls(true);// 显示缩放控件
//        settings.setTextSize(WebSettings.TextSize.LARGEST);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        wv.setWebViewClient(new MyWebViewClient());
//        return wv;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onResult(Object obj) {
        if (obj instanceof FailRequest) {
            FailRequest fail = (FailRequest) obj;
            if (!(fail == null)) {
                System.out.println("异常信息：" + fail.getMsg());
                System.out.println("状态：" + fail.getStatus());
            }
        } else {
            detailInfoList = (NewsDetailEntity) obj;
//            String url=detailInfoList.get(0).getHtmlpath();
            Log.i(TAG, detailInfoList.getData().getList().getInfocontent());
            showView(detailInfoList);
        }
    }

    @Override
    public void onFinish() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Intent intent = getIntent();
//            intent.putExtra()
            setResult(0, intent);

        }
        return super.onKeyDown(keyCode, event);
    }

    private void downLoadComponyFile(String url, final String fileName) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                if (progressDialog == null) {
                    progressDialog = CustomProgressDialog.createDialog(SingleNewActivity.this);
                    progressDialog.setMessage(getResources().getString(R.string.file_in_downloading));
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
                Toast.makeText(SingleNewActivity.this, getResources().getString(R.string.file_downloading_failure), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String namePostfix = headers[headers.length - 1].toString();
                String intactFileName = fileName + namePostfix.substring(namePostfix.lastIndexOf(".") + 1);
                Log.i(TAG, "文件下载成功！！！");
                Log.i(TAG, "文件大小(B)：" + String.valueOf(bytes.length));
                Log.i(TAG, "文件名称：" + intactFileName);
                Toast.makeText(SingleNewActivity.this, getResources().getString(R.string.file_downloading_success), Toast.LENGTH_SHORT).show();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                openFile(intactFileName);
            }
        });
    }

    private void openFile(String fileName) {
        //判断文件后缀名，打开文件
        String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
        fileName = ValueConfig.DOWNLOAD + "/" + fileName;
        Log.i(TAG, "将要打开的文件名：" + fileName);

        switch (filePostfix) {
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
    }

    private boolean isFileExist() {
        File file = new File(ValueConfig.DOWNLOAD);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            File[] fileList = file.listFiles();
            String[] fileNameList = new String[fileList.length];

            for (int j = 0; j < fileList.length; j++) {
                fileNameList[j] = fileList[j].getName().substring(0, fileList[j].getName().length() - 3);
//                Log.i(TAG, "文件列表：" + fileNameList[j]);
//                Log.i(TAG, "下载的文件名：" + fileName);
                Log.i(TAG, "文件路径：" + fileList[j].getPath() + "/" + fileList[j].getName());
                if (fileNameList[j].equals(fileName)) {
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

    //解决字体自动换行的问题
    // 替换、过滤特殊字符
    public static String StringFilter(String str) throws PatternSyntaxException {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");//替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

//质量压缩法
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

//按比例大小压缩（bitmap）
    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
}
