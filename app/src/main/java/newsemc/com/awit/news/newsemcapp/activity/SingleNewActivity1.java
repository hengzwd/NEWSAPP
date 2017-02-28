package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.AppendFileAdapter1;
import newsemc.com.awit.news.newsemcapp.adapter.HttpImgListAdapter1;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.NewsDetailEntity;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.Detail2Impl1;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.IntentUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

public class SingleNewActivity1 extends Activity implements HttpResultListener, View.OnClickListener {

    private static final String TAG = "SingleNewActivity1";
    private TextView title,unit,time,read,textContent;
    private NewsDetailEntity detailInfoList;
    private String ssid;
    private String account;
    private String fileName;
    private WebView wv = null;
    private CustomProgressDialog progressDialog = null;
    private String url="http://www.r93535.com/gateway/base/common/attachment!download.action?id=196143";
    private ListView listView1,listView2,listView3;
    private Button btback;
    private HttpImgListAdapter1 imgAdapter;
    private HttpImgListAdapter1 imgAdapter1;
    private String input="";
    private LinearLayout btnconq;
    private Button backq;
    private  String downfileurl;
    //文件名
    private String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_new1);

        //初始化控件
        title = (TextView) this.findViewById(R.id.title);
        unit = (TextView) this.findViewById(R.id.unit);
        time = (TextView) this.findViewById(R.id.time);
        read = (TextView) this.findViewById(R.id.read);
        textContent = (TextView) this.findViewById(R.id.textContent);
        listView1 = (ListView) this.findViewById(R.id.listView1);
        listView2 = (ListView) this.findViewById(R.id.listView2);
        listView3 = (ListView) this.findViewById(R.id.listView3);
        btback=(Button)findViewById(R.id.back);
        btback.setOnClickListener(this);
        //
        btnconq = (LinearLayout)findViewById(R.id.btnconq);
        backq = (Button)findViewById(R.id.backq);
        backq.setOnClickListener(this);
        //计算高度的方法
        measureListViewheight();

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
        Detail2Impl1 detailImpl=new Detail2Impl1(SingleNewActivity1.this);
        detailImpl.getDetailInfoList(ssid, account, intent.getStringExtra("infotype"), intent.getStringExtra("infoid"));




    }


    //加载显示图片的方法
    public void measureListViewheight(){
        if(listView1 != null)setListViewHeightBasedOnChildren(listView1);

        if (listView2 != null)setListViewHeightBasedOnChildren(listView2);

        if (listView3 != null)setListViewHeightBasedOnChildren(listView3);
        Log.i(TAG, "重新计算高度");
    }

    private int scrWidth = 300;
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
            Log.i(TAG, "文件=====" + tmp.getFileName());
        }
        //加载图片列表
        if(data.getData().getList().getInfotype().equals("16")) {
            imgAdapter1 = new HttpImgListAdapter1(SingleNewActivity1.this, URL, listView1,scrWidth);
            listView1.setAdapter(imgAdapter1);
//            setListViewHeightBasedOnChildren(listView1);
            ViewGroup.LayoutParams params = listView1.getLayoutParams();
            params.height = imgAdapter1.getCount() * 500;
            //Log.d("totalHeight",totalHeight + "");
            listView1.setLayoutParams(params);
        } else {
            listView1.setVisibility(View.GONE);
            Log.d("zhuph attachefile[0]=", attachefile.get(0) + "");
            AppendFileAdapter1 adapter2 = new AppendFileAdapter1(SingleNewActivity1.this,attachefile);
            listView2.setAdapter(adapter2);
//          ScrollWithListView.setListViewHeightBasedOnChildren(listView2);
//            setListViewHeightBasedOnChildren(listView2);
            ViewGroup.LayoutParams params = listView2.getLayoutParams();
            params.height = adapter2.getCount() * 500;
            //Log.d("totalHeight",totalHeight + "");
            listView2.setLayoutParams(params);


            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //下载附件
                    String url = detailInfoList.getData().getList().getAttachments().get(position).getSavePath();
                    //获取文件名
                    Name = detailInfoList.getData().getList().getAttachments().get(position).getFileName();
                    Log.i(TAG,"namenamename==" + Name);
                    Log.i(TAG, "文件的保存路径：" + ValueConfig.DOWNLOAD);
                    Log.i(TAG, "文件下载路径：" + url);
                    fileName = url.substring(url.lastIndexOf('=') + 1) + ".";
                    //将获取的文件名赋值给downfileurl
                    downfileurl = Name;

                    Log.i(TAG,"文件的名称："+downfileurl);

                    //先判断文件是否存在
                    if (!isFileExist()){
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
        AppendFileAdapter1 adapter3 = new AppendFileAdapter1(SingleNewActivity1.this,appdenix);
        listView3.setAdapter(adapter3);
        setListViewHeightBasedOnChildren(listView3);
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
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            //详情返回键按钮
            case  R.id.backq:
                finish();
                break;
        }
    }
    //重新设置一次高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //linearlayout布局可以使用，否则会出现异常
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
            detailInfoList=(NewsDetailEntity)obj;
//            String url=detailInfoList.get(0).getHtmlpath();
            Log.i(TAG, detailInfoList.getData().getList().getInfocontent());
            showView(detailInfoList);
            //文字替换
            input = detailInfoList.getData().getList().getInfocontent();
            ToSBC(input);
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
   //下载文件
    private void downLoadComponyFile(String url, final String fileName){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                if (progressDialog == null) {
                    progressDialog = CustomProgressDialog.createDialog(SingleNewActivity1.this);
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
                Toast.makeText(SingleNewActivity1.this, getResources().getString(R.string.file_downloading_failure), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String namePostfix = headers[headers.length - 1].toString();
//                String intactFileName = fileName + namePostfix.substring(namePostfix.lastIndexOf(".") + 1);
                //文件名的格式
//                String intactFileName = downfileurl + detailInfoList.getData().getList().getAttachments()
//                        .get(0).getFileName();
//                String intactFileName =Name +  detailInfoList.getData().getList().getAttachments()
//                        .get(0).getFileName();
                //将获取的文件名赋值给将要打开的文件
                String intactFileName =Name ;

                Log.i(TAG,"=====" + intactFileName);
                Log.i(TAG, "文件下载成功！！！");
                Log.i(TAG, "文件大小(B)：" + String.valueOf(bytes.length));
                Log.i(TAG, "文件名称：" + intactFileName);
                Log.i(TAG,"文件的长度：" + intactFileName.length());
                Toast.makeText(SingleNewActivity1.this, getResources().getString(R.string.file_downloading_success), Toast.LENGTH_SHORT).show();
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

    private void openFile(String fileName){

        //判断文件后缀名，打开文件
//        String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Log.i(TAG,"filePostfix==" + filePostfix);
        fileName = ValueConfig.DOWNLOAD + "/" + fileName;
        Log.i(TAG, "将要打开的文件名：" + fileName);

        switch (filePostfix){
            case "xls":
            case "xlsx":
                startActivity(IntentUtils.getExcelFileIntent(fileName));
                Log.i(TAG, "打开xls文件！");
                break;
            case "pdf":
            case "PDF":
                startActivity(IntentUtils.getPdfFileIntent(fileName));
                Log.i(TAG, "打开PDF文件！");
                break;
            case "doc":
            case "docx":
                startActivity(IntentUtils.getWordFileIntent(fileName));
                Log.i(TAG, "打开DOC文件！");
                break;
            case "txt":
                startActivity(IntentUtils.getTextFileIntent(fileName, false));
                Log.i(TAG, "打开文本文件！");
                break;
            case "ppt":
            case "pptx":
                startActivity(IntentUtils.getPptFileIntent(fileName));
                Log.i(TAG, "打开ppt文件");
                break;
            default:
                break;
        }



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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(imgAdapter != null)imgAdapter1.destoryImg();
//    }

    //文字替换
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

}
