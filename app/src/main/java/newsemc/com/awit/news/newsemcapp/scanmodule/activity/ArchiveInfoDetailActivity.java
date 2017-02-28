package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.socks.library.KLog;

import org.apache.http.Header;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import in.srain.cube.views.ptr.PtrFrameLayout;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.base.BaseActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.ArchiveInfoDetailRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.ArchiveInfoBean;
import newsemc.com.awit.news.newsemcapp.util.IntentUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by leguang on 2016/9/20 0020.
 * 联系邮箱:langmanleguang@qq.com
 */
public class ArchiveInfoDetailActivity extends BaseActivity {
    private static final String TAG = ArchiveInfoDetailActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private ArchiveInfoDetailRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private CustomProgressDialog progressDialog;
    private ArchiveInfoBean.InfolistBean detailDateList;
    private String archive_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_info_detail);
        archive_type = getIntent().getStringExtra("archive_type");
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarTitle();
        if (!"3".equals(archive_type) && !"1".equals(archive_type)) {//判断是否为检验批
            initToolbarMenu(mToolbar);
        }
        initToolbarBackNavigation(mToolbar);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_archive_info_detail_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_archive_info_detail_activity);
    }

    private void setToolbarTitle() {
        mToolbar.setTitle("文件详情");
    }

    private void initToolbarMenu(final Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_download);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_download:
                        down2Show();
                        break;
                }
                return true;
            }
        });
    }

    private void initData() {
        detailDateList = (ArchiveInfoBean.InfolistBean) getIntent().getSerializableExtra("detailDateList");


        KLog.e(detailDateList.getBrolist().size());
        KLog.e(detailDateList.getBrolist().get(0).getUrl());

        mAdapter = new ArchiveInfoDetailRecyclerViewAdapter(this, detailDateList.getBrolist());
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//               String fileName = ValueConfig.DOWNLOAD + "/" + view.;
//                startActivity(IntentUtils.getImageFileIntent(fileName));
//                KLog.e(TAG, "打开图片");
            }
        });
        if ("3".equals(archive_type)) {
            down2Show();
        }
//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (TextUtils.isEmpty(detailDateList.getBrolist().get(position).getUrl())) {
//                    return;
//                }
//                Intent mIntent = new Intent(ArchiveInfoDetailActivity.this, ShowPhotoActivity.class);
//                mIntent.putExtra("mainurl", detailDateList.getBrolist().get(position).getUrl());
//                startActivity(mIntent);
//            }
//        });
    }

    private void down2Show() {
        String mainURL = detailDateList.getMainurl();
        KLog.e(mainURL);
        String filePostfix = mainURL.substring(mainURL.lastIndexOf(".") + 1);
        KLog.e(filePostfix);

        if ("jpg".equals(filePostfix) || "JPG".equals(filePostfix)
                || "jpeg".equals(filePostfix) || "JPEG".equals(filePostfix)
                || "png".equals(filePostfix) || "PNG".equals(filePostfix)) {

            Intent mIntent = new Intent(this, ShowPhotoActivity.class);
            mIntent.putExtra("mainurl", mainURL);
            startActivity(mIntent);
        } else {
            down();
        }
    }

    private void down() {
        String mainURL = detailDateList.getMainurl();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(mainURL).build();
        final Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                KLog.e("下载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                KLog.e(response);
                String intactFileName = detailDateList.getName();

                // Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_downloading_success), Toast.LENGTH_SHORT).show();
                try {
                    File file = new File(ValueConfig.DOWNLOAD, intactFileName);
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists())
                        parent.mkdirs();
                    FileOutputStream fos = new FileOutputStream(file);
                    OutputStream output = new DataOutputStream(fos);
                    byte[] bytes = response.body().bytes();
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
        KLog.e("fileName", "将要打开的文件名：" + fileName);

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
}
