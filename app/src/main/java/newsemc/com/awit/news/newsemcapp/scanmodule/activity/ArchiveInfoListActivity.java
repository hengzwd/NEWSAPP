package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hailstone.util.FileUtils;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.base.BaseActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.ArchiveInfoListRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.ArchiveInfoBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpHelper;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ImageUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by leguang on 2016/9/20 0020.
 * 联系邮箱:langmanleguang@qq.com
 */
public class ArchiveInfoListActivity extends BaseActivity {
    private static final String TAG = ArchiveInfoListActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 0;
    private Toolbar mToolbar;
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private ArchiveInfoListRecyclerViewAdapter mAdapter;
    private Gson mGson;
    private ArchiveInfoBean itemsData;
    private List<ArchiveInfoBean.InfolistBean> listData;
    private boolean isLoading;
    private int lastVisibleItemPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private int intPage = 1;
    private CustomProgressDialog progressDialog;
    private Bitmap mBitmap;
    private String archive_id;
    private String archive_type;
    private String archive_name;
    private String archive_hiecoding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_info_list);
        Intent mIntent = getIntent();
        archive_id = mIntent.getStringExtra("archive_id");
        archive_type = mIntent.getStringExtra("archive_type");
        archive_name = mIntent.getStringExtra("archive_name");
        archive_hiecoding = mIntent.getStringExtra("archive_hiecoding");

        KLog.e("archive_id：" + archive_id);
        KLog.e("archive_type：" + archive_type);
        KLog.e("archive_name：" + archive_name);
        KLog.e("archive_hiecoding：" + archive_hiecoding);

        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbarTitle();
        if (archive_type != null && archive_type.equals("1")) {
            initToolbarMenu(mToolbar);
        }
        initToolbarBackNavigation(mToolbar);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_archive_info_list_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_archive_info_list_activity);
    }

    private void setToolbarTitle() {
        if (null != mToolbar && !TextUtils.isEmpty(archive_name)) {
            mToolbar.setTitle(archive_name);
        }
    }

    private void initToolbarMenu(final Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_uploadfile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_album:
                       // ToastUtils.showToast(getApplication(), "相机");
                        openAlbum();
                        break;
                }
                return true;
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void initData() {

        mGson = new Gson();
        listData = new ArrayList<>();
        mAdapter = new ArchiveInfoListRecyclerViewAdapter(this, listData);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (archive_type.equals("3"))
                {}
                Intent intent = new Intent(ArchiveInfoListActivity.this, ArchiveInfoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailDateList", listData.get(position));
                bundle.putString("archive_type",archive_type);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == mAdapter.getItemCount()
                        && itemsData.getInfolist().size() >= 20) {

                    if (!isLoading) {
                        isLoading = true;
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadMore();
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

        initPtrFrameLayout(mPtrFrameLayout);
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
        if (null != mRecyclerView) {
            if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int position = lm.findFirstVisibleItemPosition();
                if (position >= 0) {
                    if (lm.findViewByPosition(position).getTop() >= 0 && position == 0) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    public String createRefreshULR() {

        return ConstantsUtils.ArchiveInfoList_URL;
    }

    @Override
    public Map<String, String> createRefreshParams() {
        intPage = 1;
        if (null != listData) {
            listData.clear();
        }
        Map<String, String> paramsMap = new HashMap<String, String>();
        //授权Token值
        KLog.e(NewsEMCAppllication.mToken);

        paramsMap.put("token", NewsEMCAppllication.mToken);

        //工程实体唯一主键
        paramsMap.put("ebsdataid", archive_id);
//        paramsMap.put("ebsdataid", "12345");

        //1：影像资料，2：设计资料，3:检验批，4：施工日志，5：试验报告，6:：技术交底；7：标准规范
        paramsMap.put("filetypeid", archive_type);

        // 1为项目、2为构筑物、3为标段、4为工点、5、为ebs工程实体
        paramsMap.put("hiecoding", archive_hiecoding);

        //层级结构对应id
        paramsMap.put("hieid", archive_id);
//        paramsMap.put("hieid", "12345");

        //可以不填
        paramsMap.put("startdate", "");

        //用于以后扩展
        paramsMap.put("key", "");

        //页码
        paramsMap.put("page", intPage + "");

        KLog.e("page:" + paramsMap.get("page"));
        return paramsMap;
    }

    @Override
    public void onRefreshSuccess(String response) {
        KLog.e(response);
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
             //    // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                return;
            }
            if (jsonObject.optInt("Result") == 0) {
                try {
                    response = new String(response.getBytes("iso-8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    // // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                    return;
                }
                itemsData = mGson.fromJson(response, ArchiveInfoBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getInfolist().size() > 0) {
                        listData.addAll(itemsData.getInfolist());
                        if (null != listData) {
                            if (listData.size() > 0) {
                                mRecyclerView.setAdapter(mAdapter);
                            } else {
                                //提示数据为空，展示空状态
                                //ToastUtils.showToast(getApplicationContext(), "暂无数据!");
                            }
                        } else {
                            //提示数据解析异常，展示错误页面
                           //  // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                        }
                    } else {
                        //提示数据为空，展示空状态
                        //ToastUtils.showToast(getApplicationContext(), "暂无数据!");
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                   //  // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                }

            } else {
                //提示数据为空，展示空状态
                // // ToastUtils.showToast(getApplicationContext(), "数据异常!");
            }
        } else {
            //提示返回数据异常，展示错误页面
            // // ToastUtils.showToast(getApplicationContext(), "数据异常!");
        }
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        ToastUtils.showToast(getApplicationContext(), "网络访问失败!");
    }

    @Override
    public String createLoadMoreULR() {
        return ConstantsUtils.ArchiveInfoList_URL;
    }

    @Override
    public Map<String, String> createLoadMoreParams() {
        intPage = intPage + 1;
        KLog.e("intPage" + intPage);
        Map<String, String> paramsMap = new HashMap<String, String>();

        //授权Token值
        paramsMap.put("token", NewsEMCAppllication.mToken);

        //工程实体唯一主键
        paramsMap.put("ebsdataid", archive_id);
//        paramsMap.put("ebsdataid", "");

        //1：影像资料，2：设计资料，3:检验批，4：施工日志，5：试验报告，6:：技术交底；7：标准规范
        paramsMap.put("filetypeid", archive_type);
//        paramsMap.put("filetypeid", "2");

        // 1为项目、2为构筑物、3为标段、4为工点、5、为ebs工程实体
        paramsMap.put("hiecoding", archive_hiecoding);
//        paramsMap.put("hiecoding", "1");

        //层级结构对应id
        paramsMap.put("hieid", archive_id);
//        paramsMap.put("hieid", "12345");

        //可以不填
        paramsMap.put("startdate", "");

        //用于以后扩展,先不管
        paramsMap.put("key", "");

        //页码
        paramsMap.put("page", intPage + "");

        return paramsMap;
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
               //  // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                return;
            }
            if (jsonObject.optInt("Result") == 0) {
                try {
                    response = new String(response.getBytes("iso-8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                     // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                    return;
                }

                itemsData = mGson.fromJson(response, ArchiveInfoBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getInfolist().size() > 0) {
                        if (null != listData) {
                            Boolean isAddAll = listData.addAll(itemsData.getInfolist());
                            if (isAddAll) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showToast(getApplicationContext(), "加载失败!");
                                intPage = intPage - 1;
                                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                            }
                        } else {
                             // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                            intPage = intPage - 1;
                            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        }
                    } else {
                        ToastUtils.showToast(getApplicationContext(), "无更多数据!");
                        intPage = intPage - 1;
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    }
                } else {
                    ToastUtils.showToast(getApplicationContext(), "解析异常!");
                    intPage = intPage - 1;
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                }

            } else {
                //提示数据为空，展示空状态
                 // ToastUtils.showToast(getApplicationContext(), "数据异常!");
            }
        } else {
            //提示返回数据异常，展示错误页面
             // ToastUtils.showToast(getApplicationContext(), "数据异常!");
        }
    }

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        ToastUtils.showToast(getApplicationContext(), "加载失败!");
        intPage = intPage - 1;
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_IMAGE) { // 表示是从相册选择图片返回
            Uri uri = data.getData(); //得到图片 uri

            final String strPath = ImageUtils.getImageAbsolutePath(this, uri);
            String strFileName = new File(strPath).getName();

            KLog.e("strPath::" + strPath);
            KLog.e(strFileName);


            Observable.create(new Observable.OnSubscribe<ResponseBody>() {
                @Override
                public void call(Subscriber<? super ResponseBody> subscriber) {

                    long offset = 0;
                    long length = new File(strPath).length();
                    long count = 1024 * 1024;

                    String strFileName = new File(strPath).getName();


                    KLog.e("length::" + length);


                    while (true) {
                        if (count > (length - offset)) {
                            count = length - offset;
                        }

                        String base64 = Base64.encodeToString(FileUtils.getFileBytes(strPath, (int) offset, (int) count), Base64.DEFAULT);//base64编码上传


                        Call<ResponseBody> call = HttpHelper.getInstance().initService().upload(NewsEMCAppllication.mToken,
                                strFileName,
                                base64,
                                length + "",
                                offset + "",
                                "", archive_type, archive_hiecoding, archive_id, "");

                        Response<ResponseBody> response = null;
                        try {
                            response = call.execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                            KLog.e(e.toString());
                            subscriber.onError(e);
                        }

                        subscriber.onNext(response.body());

                        offset += count;
                        KLog.e("offset::" + offset);

                        if (!response.isSuccessful()) {
                            subscriber.onError(new IOException());
                            break;
                        } else if (offset >= length) {
                            subscriber.onCompleted();
                            break;
                        }

                    }

                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {

                                   @Override
                                   public void onStart() {
                                       super.onStart();
                                       if (progressDialog == null) {
                                           progressDialog = CustomProgressDialog.createDialog(ArchiveInfoListActivity.this);
                                           progressDialog.setMessage("正在上传…");
                                       }
                                       progressDialog.show();
                                   }

                                   @Override
                                   public void onNext(ResponseBody responseBody) {
                                       try {
                                           KLog.e(responseBody.string());
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }
                                   }

                                   @Override
                                   public void onCompleted() {
                                       if (progressDialog != null) {
                                           progressDialog.dismiss();
                                           progressDialog = null;
                                       }
                                       ToastUtils.showToast(getApplicationContext(),"上传成功");
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       if (progressDialog != null) {
                                           progressDialog.dismiss();
                                           progressDialog = null;
                                       }
                                       ToastUtils.showToast(getApplicationContext(),"上传失败");
                                   }

                               }
                    );


        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != Activity.RESULT_OK) {
//            return;
//        }
//
//        if (requestCode == REQUEST_IMAGE) { // 表示是从相册选择图片返回
//            Uri uri = data.getData(); //得到图片 uri
//            ContentResolver resolver = getContentResolver(); //处理器
//            try {
//                mBitmap = MediaStore.Images.Media.getBitmap(resolver, uri); //  将对应 uri 通过处理器转化为 bitmap
//            } catch (Exception e) {
//                e.printStackTrace();
//                ToastUtils.showToast(getApplicationContext(), "获取图片失败！");
//                return;
//            }
//            upload();
//
//        }
//    }
//
//    private void upload() {
//        if (progressDialog == null) {
//            progressDialog = CustomProgressDialog.createDialog(this);
//            progressDialog.setMessage("正在上传…");
//        }
//        progressDialog.show();
//        String URL = ConstantsUtils.UploadFile_URL;
//        Map<String, String> paramsMap = new HashMap<String, String>();
//        //授权Token值
//        String photoName = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//
//        String base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//
//        paramsMap.put("token", NewsEMCAppllication.mToken);
//        paramsMap.put("fileName", photoName);
//        paramsMap.put("strBytes", base64);
//        paramsMap.put("fileLength", baos.toByteArray().length + "");
//        paramsMap.put("offset", "0");
//        paramsMap.put("ebsdataid", "");
//
//        //1代表影像资料，也只有1有上传图片
//        paramsMap.put("filetypeid", archive_type);
//        paramsMap.put("hiecoding", archive_hiecoding);
//        paramsMap.put("hieid", archive_id);
//        //暂时先不管
//        paramsMap.put("jsonParm", "");
//
//
//        //联网获取数据
//        //还没有判断url，用户再判断
//        HttpUtils.postRequest(URL, paramsMap, new HttpUtils.HttpListener() {
//            @Override
//            public void onSuccess(String response) {
//
//                KLog.e(response);
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
//            }
//
//            @Override
//            public void onFailed(VolleyError error) {
//                KLog.d(error);
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
//            }
//        });
//    }


}
