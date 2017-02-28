package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.AppendFileAdapter1;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.NewsDetailEntity;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.Detail2Impl1;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.IntentUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by PC on 2016/3/9.
 */
public class NewsDetailActivity extends Activity implements HttpResultListener, View.OnClickListener {
    private static final String TAG = "NewsDetailActivity";

    /**
     * 新闻详情
     */
    private NewsDetailEntity detailInfoList;
    /**
     * 下载的dialog
     */
    private CustomProgressDialog progressDialog = null;
    /**
     * 下载的文件名
     */
    private String Name;
    private String fileName;
    /**
     * 下载链接
     */
    private String downfileurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_newsdetail);

        //清空特殊登录所保存的数据
        SharedPreferences testspec = getSharedPreferences("testlogin", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = testspec.edit();
        editor.clear();
        editor.commit();
        Log.i("清空特殊登录的数据", "清空中。。。。");
        //正常登录过来的
        SharedPreferences sharedPreferences = getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
        String ssid = sharedPreferences.getString("KEY", "");
        String account = sharedPreferences.getString("ACCOUNT", "");
        Log.i(TAG, "account =" + account + " \n " + "ssid =" + ssid);

        /** 请求数据*/
        Detail2Impl1 detailImpl = new Detail2Impl1(this);
        Intent intent = getIntent();
        KLog.e(intent.getStringExtra("infotype"));
        KLog.e(intent.getStringExtra("infoid"));
        detailImpl.getDetailInfoList(ssid, account, intent.getStringExtra("infotype"), intent.getStringExtra("infoid"));

    }

    private void showView(NewsDetailEntity data) {
        //创建picasso图片框架实例
        Picasso picasso = Picasso.with(this);
        //获取控件
        TextView title = (TextView) this.findViewById(R.id.title); //标题
        TextView unit = (TextView) this.findViewById(R.id.unit);   //来源
        TextView time = (TextView) this.findViewById(R.id.time);   //日期
        TextView read = (TextView) this.findViewById(R.id.read);   //阅读量
        TextView detail = (TextView) this.findViewById(R.id.tv_newsdetail);   //详细
        LinearLayout ll_container = (LinearLayout) findViewById(R.id.ll_container_newsdetail); //容器
        ListView lv_fujian = (ListView) findViewById(R.id.lv_fujian); //附件listview
        Button backq = (Button) findViewById(R.id.backq);
        backq.setOnClickListener(this);

        title.setText(data.getData().getList().getInfoname());
        unit.setText(data.getData().getList().getPublishquarry());
        time.setText(data.getData().getList().getInfodate());
        read.setText(data.getData().getList().getNum() + getResources().getString(R.string.ci));
        detail.setText(Html.fromHtml(data.getData().getList().getInfocontent()));

        List<String> URL = new ArrayList<String>();
        List<String> attachefile = new ArrayList<String>();
        for (int i = 0; i < data.getData().getList().getAttachments().size(); i++) {
            NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity tmp = data.getData().getList().getAttachments().get(i);
            URL.add(tmp.getSavePath());
            attachefile.add(tmp.getFileName());
            Log.d("zhuph attachefile[" + i + "]=", tmp.getSavePath());
        }

        // 16则为图片新闻,其它的则是下载附件
        if (data.getData().getList().getInfotype().equals("16")) {
            List<ImageView> list_iv = new ArrayList<>();

            //根据url的数量动态new出imageview
            for (int i = 0; i < URL.size(); i++) {
                ImageView iv = new ImageView(this);
                //设置图片居中
                iv.setScaleType(ImageView.ScaleType.CENTER);
                iv.setAdjustViewBounds(true);
                //使用picasso 加载图片
                picasso.load(URL.get(i))
                        .placeholder(R.drawable.loadingprogress_1) //设置加载中的占位图
                        .into(iv);

                //将设置好的imageview添加到容器中
                ll_container.addView(iv);
                list_iv.add(iv);
            }
            //动态设置imageview大小
            for (int i = 0; i < list_iv.size(); i++) {
                ImageView iv = list_iv.get(i);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ll_container.getLayoutParams().width,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 20, 0, 0);
                iv.setLayoutParams(layoutParams);
            }

        } else {
            //附件页面
            AppendFileAdapter1 adapter2 = new AppendFileAdapter1(this, attachefile);
            lv_fujian.setAdapter(adapter2);
            ViewGroup.LayoutParams params = lv_fujian.getLayoutParams();
            params.height = adapter2.getCount() * 500;
            lv_fujian.setLayoutParams(params);

            lv_fujian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //下载附件
                    String url = detailInfoList.getData().getList().getAttachments().get(position).getSavePath();
                    Log.e(TAG, "文件下载路径：" + url);
                    //获取文件名
                    Name = detailInfoList.getData().getList().getAttachments().get(position).getFileName();
                    Log.e(TAG, "namenamename==" + Name);
                    Log.e(TAG, "文件的保存路径：" + ValueConfig.DOWNLOAD);

                    fileName = url.substring(url.lastIndexOf('=') + 1) + ".";

                    KLog.e("fileName:" + fileName);
                    //将获取的文件名赋值给downfileurl
                    downfileurl = Name;

                    KLog.e(TAG, "downfileurl：" + downfileurl);

                    //先判断文件是否存在
                    if (!isFileExist()) {
                        downLoadComponyFile(url, fileName);
                    }
                }
            });
        }

    }

    //下载文件
    private void downLoadComponyFile(String url, final String fileName) {

        KLog.e("downLoadComponyFile:url:" + url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                if (progressDialog == null) {
                    progressDialog = CustomProgressDialog.createDialog(NewsDetailActivity.this);
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
                Toast.makeText(NewsDetailActivity.this, getResources().getString(R.string.file_downloading_failure), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String intactFileName = Name;

                KLog.e(TAG, "=====" + intactFileName);
                KLog.e(TAG, "文件下载成功！！！");
                KLog.e(TAG, "文件大小(B)：" + String.valueOf(bytes.length));
                KLog.e(TAG, "文件名称：" + intactFileName);
                KLog.e(TAG, "文件的长度：" + intactFileName.length());
                Toast.makeText(NewsDetailActivity.this, getResources().getString(R.string.file_downloading_success), Toast.LENGTH_SHORT).show();
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
        KLog.e(TAG, "filePostfix==" + filePostfix);
        fileName = ValueConfig.DOWNLOAD + "/" + fileName;
        KLog.e(TAG, "将要打开的文件名：" + fileName);

        switch (filePostfix) {
            case "xls":
            case "xlsx":
                startActivity(IntentUtils.getExcelFileIntent(fileName));
                KLog.e(TAG, "打开xls文件！");
                break;
            case "pdf":
            case "PDF":
                startActivity(IntentUtils.getPdfFileIntent(fileName));
                KLog.e(TAG, "打开PDF文件！");
                break;
            case "doc":
            case "docx":
                startActivity(IntentUtils.getWordFileIntent(fileName));
                KLog.e(TAG, "打开DOC文件！");
                break;
            case "txt":
                startActivity(IntentUtils.getTextFileIntent(fileName, false));
                KLog.e(TAG, "打开文本文件！");
                break;
            case "ppt":
            case "pptx":
                startActivity(IntentUtils.getPptFileIntent(fileName));
                KLog.e(TAG, "打开ppt文件");
                break;
            case "jpg":
            case "JPG":
            case "JPEG":
            case "jpeg":
            case "PNG":
            case "png":
                startActivity(IntentUtils.getImageFileIntent(fileName));
                KLog.e(TAG, "打开图片");
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
            if (fileList.length != 0) {
                String[] fileNameList = new String[fileList.length];

                for (int j = 0; j < fileList.length; j++) {
                    if (fileList[j].getName().length()>3) {
                        fileNameList[j] = fileList[j].getName().substring(0, fileList[j].getName().length() - 3);//文件名小于3，则数组越界
                        KLog.e(TAG, "文件路径：" + fileList[j].getPath() + "/" + fileList[j].getName());
                        if (fileNameList[j].equals(fileName)) {
                            //文件存在
                            KLog.e(TAG, "文件存在");
                            openFile(fileList[j].getName());
                            return true;
                        }
                    }
                }
            }
        }
        KLog.e(TAG, "文件不存在");
        return false;
    }

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

    /**
     * onStartRequest
     * onResult
     * onFinish
     * 以上三个方法皆是网络请求的回调
     */

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
            KLog.e(TAG, detailInfoList.getData().getList().getInfocontent());
            showView(detailInfoList);
        }
    }

    @Override
    public void onFinish() {

    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backq:
                onBackPressed();
                break;

        }
    }
}
