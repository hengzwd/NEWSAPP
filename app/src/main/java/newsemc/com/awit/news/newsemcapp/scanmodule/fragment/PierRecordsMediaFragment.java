package newsemc.com.awit.news.newsemcapp.scanmodule.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hailstone.util.FileUtils;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
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
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ArchiveInfoDetailActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ArchiveInfoListActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.ArchiveInfoListRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.base.BaseFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.ArchiveInfoBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.phoneInfo;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpHelper;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ImageUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;
import newsemc.com.awit.news.newsemcapp.util.DeviceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * Created by gesangdianzi on 2016/10/26.
 */
public class PierRecordsMediaFragment extends BaseFragment {
    private static final String TAG = PierRecordsMediaFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private Button btn_upload;
    private Button iv_camera_select;
    private PtrFrameLayout mPtrFrameLayout;
    private ArchiveInfoListRecyclerViewAdapter mAdapter;
    private Gson mGson;
    private ArchiveInfoBean itemsData;
    private List<ArchiveInfoBean.InfolistBean> listData;
    private boolean isLoading;
    private int lastVisibleItemPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private CustomProgressDialog progressDialog;
    private int intPage = 1;
    public static final int REQUEST_IMAGE = 0;
    public static final int REQUEST_CAMERA = 1;
    private String filename;
    private Bitmap bitmap;
    private Uri uri;
    View layout;
    EditText fileEditText;
    private String phoneinfoString;

    public static PierRecordsMediaFragment newInstance() {
        return new PierRecordsMediaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pierrecordsmedia, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //处理view中的控件
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_pierrecordmedia_fragment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_pierrecordmedia_fragment);
        btn_upload = (Button) view.findViewById(R.id.bt_upload);
        iv_camera_select = (Button) view.findViewById(R.id.iv_camera_select);

        initData();
    }

    private void initData() {
        mGson = new Gson();
        NewsEMCAppllication.phoneInfo.set手机名称("");
        NewsEMCAppllication.phoneInfo.set手机标识(DeviceUtils.getIMEI());
        NewsEMCAppllication.phoneInfo.set手机品牌(DeviceUtils.getPhoneBrand());
        NewsEMCAppllication.phoneInfo.set手机型号(DeviceUtils.getPhoneType());
        NewsEMCAppllication.phoneInfo.set系统版本(DeviceUtils.getOSVersionName());
        phoneinfoString = mGson.toJson(NewsEMCAppllication.phoneInfo);
        KLog.e(phoneinfoString);
        listData = new ArrayList<>();
        mAdapter = new ArchiveInfoListRecyclerViewAdapter(getActivity(), listData);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //  Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArchiveInfoDetailActivity.class);
                Bundle bundle = new Bundle();
                if (0 != listData.size()) {
                    bundle.putSerializable("detailDateList", listData.get(position));
                    intent.putExtras(bundle);
                    intent.putExtra("archive_type", "1");
                    startActivity(intent);
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //还有一个不完美的地方就是当规定的item个数时，最后一个item在屏幕外滑到可见时，其底部没有footview，这点以后再解决。
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition + 1 == mAdapter.getItemCount()
                        && listData.size() >= 20) {

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
                //     KLog.e("lastVisibleItemPosition"+lastVisibleItemPosition);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });

        iv_camera_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis() + ".jpg");
                uri = Uri.fromFile(file);
                // intent.setDataAndType(Uri.fromFile(file), "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        initPtrFrameLayout(mPtrFrameLayout);
        progressDialog = CustomProgressDialog.createDialog(getActivity());
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        //判断RecyclerView是否在在顶部，在顶部则允许滑动下拉刷新
        if (null != mRecyclerView) {
            if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int position = lm.findFirstVisibleItemPosition();
                if (position >= 0) {
                    if (lm.findViewByPosition(position).getTop() >= 0 && position == 0) {
                        return true;
                    }
                } else {
                    return true;
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
            mAdapter.notifyDataSetChanged();
        }
        Map<String, String> paramsMap = new HashMap<String, String>();
        //授权Token值
        KLog.e(NewsEMCAppllication.mToken);
        paramsMap.put("token", NewsEMCAppllication.mToken);

        //工程实体唯一主键
        KLog.e(NewsEMCAppllication.mQRCodeBean.getId());
        paramsMap.put("ebsdataid", NewsEMCAppllication.mQRCodeBean.getId());
//        paramsMap.put("ebsdataid", "");

        //1：影像资料，2：设计资料，3:检验批，4：施工日志，5：试验报告，6:：技术交底；7：标准规范
        paramsMap.put("filetypeid", "1");

        // 1为项目、2为构筑物、3为标段、4为工点、5、为ebs工程实体
        //墩和特殊结构就是5，隧道工点就是4
        KLog.e(getHieCoding());
        paramsMap.put("hiecoding", getHieCoding());
//        paramsMap.put("hiecoding", "1");

        //层级结构对应id
        KLog.e(NewsEMCAppllication.mQRCodeBean.getId());
        paramsMap.put("hieid", NewsEMCAppllication.mQRCodeBean.getId());
        KLog.e(NewsEMCAppllication.mQRCodeBean.getId());
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
                //ToastUtils.showToast(getActivity(), "数据异常!");
                return;
            }
            if (jsonObject.optInt("Result") == 0) {
                try {
                    response = new String(response.getBytes("iso-8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    //ToastUtils.showToast(getActivity(), "数据异常!");
                    return;
                }
                itemsData = mGson.fromJson(response, ArchiveInfoBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getInfolist().size() > 0) {
                        listData.addAll(itemsData.getInfolist());
                        if (null != listData) {
                            if (listData.size() > 0) {
                                // ToastUtils.showToast(getActivity(), String.valueOf(listData.size()));
                                mRecyclerView.setAdapter(mAdapter);
                            } else {
                                //提示数据为空，展示空状态
                                //ToastUtils.showToast(getActivity((), "暂无数据!");
                            }
                        } else {
                            //提示数据解析异常，展示错误页面
                            //ToastUtils.showToast(getActivity(), "数据异常!");
                        }
                    } else {
                        //提示数据为空，展示空状态
                        //ToastUtils.showToast(getActivity((), "暂无数据!");
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                    //ToastUtils.showToast(getActivity(), "数据异常!");
                }

            } else {
                //提示数据为空，展示空状态
                //ToastUtils.showToast(getActivity(), "数据异常!");
            }
        } else {
            //提示返回数据异常，展示错误页面
            //ToastUtils.showToast(getActivity(), "数据异常!");
        }
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        KLog.e("response:" + "111111111111");

        ToastUtils.showToast(getActivity(), "网络访问失败!");
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
        paramsMap.put("ebsdataid", NewsEMCAppllication.mQRCodeBean.getId());
//        paramsMap.put("ebsdataid", "");

        //1：影像资料，2：设计资料，3:检验批，4：施工日志，5：试验报告，6:：技术交底；7：标准规范
        paramsMap.put("filetypeid", "1");

        // 1为项目、2为构筑物、3为标段、4为工点、5、为ebs工程实体
        //墩和特殊结构就是5，隧道工点就是4
        paramsMap.put("hiecoding", getHieCoding());
//        paramsMap.put("hiecoding", "1");

        //层级结构对应id
//        paramsMap.put("hieid", "12345");
        paramsMap.put("hieid", NewsEMCAppllication.mQRCodeBean.getId());

        //可以不填
        paramsMap.put("startdate", "");

        //用于以后扩展
        paramsMap.put("key", "");

        //页码
        paramsMap.put("page", String.valueOf(intPage));

        return paramsMap;
    }

    private String getHieCoding() {

        if (NewsEMCAppllication.mQRCodeBean.getType().equals("2")) {
            //隧道hiecoding是4  其他是5              墩和特殊结构就是5，隧道工点就是4
            return "4";

        } else {
            return "5";
        }
    }

    /**
     * 压缩单张图片 Listener 方式
     */
    private void compressWithLs(File file, String filename) {
        Luban.get(getActivity())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .setFilename(filename)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  Toast.makeText(getActivity(), "I'm start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        KLog.e("path", file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("onError", "压缩失败");
                    }
                }).launch();
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
        if (!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                //ToastUtils.showToast(getActivity(), "数据异常!");
                intPage = intPage - 1;
                return;
            }
            if (jsonObject.optInt("Result") == 0) {
                try {
                    response = new String(response.getBytes("iso-8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    //ToastUtils.showToast(getActivity(), "数据异常!");
                    intPage = intPage - 1;
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
                                ToastUtils.showToast(getActivity(), "加载失败!");
                                intPage = intPage - 1;
                                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                            }
                        } else {
                            //ToastUtils.showToast(getActivity(), "数据异常!");
                            intPage = intPage - 1;
                            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        }
                    } else {
                        ToastUtils.showToast(getActivity(), "无更多数据!");
                        intPage = intPage - 1;
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    }
                } else {
                    ToastUtils.showToast(getActivity(), "解析异常!");
                    intPage = intPage - 1;
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                }

            } else {
                //提示数据为空，展示空状态
                //ToastUtils.showToast(getActivity(), "数据异常!");
                intPage = intPage - 1;
            }
        } else {
            //提示返回数据异常，展示错误页面
            //ToastUtils.showToast(getActivity(), "数据异常!");
            intPage = intPage - 1;
        }
    }

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        ToastUtils.showToast(getActivity(), "加载失败!");
        intPage = intPage - 1;
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        KLog.e(resultCode);
        KLog.e(requestCode);
        KLog.e(data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CAMERA) {
            if (null == data) {
                final String filepath = ImageUtils.getImageAbsolutePath(getActivity(), uri);
                KLog.e("length:" + new File(filepath).length());
                // 判断内存卡是否可用
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    KLog.e("SD卡不可用");
                    ToastUtils.showToast(getActivity(), "SD卡不可用");
                    return;
                }
//                Bundle bundle = data.getExtras();
//            final Bitmap bitmap = (Bitmap) bundle.get("data");
//            final String filepath=(String)bundle.get(MediaStore.EXTRA_OUTPUT);
//
//            compressWithLs(file);
                //自定义一个dialog
                Luban.get(getActivity())
                        .load(new File(filepath))
                        .putGear(Luban.THIRD_GEAR)
                        .asObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                            @Override
                            public Observable<? extends File> call(Throwable throwable) {
                                return Observable.empty();
                            }
                        })
                        .subscribe(new Subscriber<File>() {
                            @Override
                            public void onCompleted() {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.dismiss();
                                ToastUtils.showToast(getActivity(), "压缩失败");
                            }

                            @Override
                            public void onNext(final File file) {

                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                layout = inflater.inflate(R.layout.fileeditdialog, null);
                                fileEditText = (EditText) layout.findViewById(R.id.etname);
                                new AlertDialog.Builder(getActivity()).
                                        setTitle("编辑文件名").
                                        setView(layout).
                                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                if (null != progressDialog) {
                                                    progressDialog.dismiss();
                                                }
                                                filename = fileEditText.getText().toString();
                                                final String photoName = filename + ".jpg";

                                                KLog.e("文件名：" + photoName);
//
//                            FileOutputStream b = null;
//                            File file = new File("/sdcard/NewsEMC/");
//                            file.mkdirs();
//                            final String fileName = "/sdcard/NewSEMC/" + photoName;

                                                try {
//                                b = new FileOutputStream(fileName);
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);//存储照片

                                                    Observable.create(new Observable.OnSubscribe<ResponseBody>() {
                                                        @Override
                                                        public void call(Subscriber<? super ResponseBody> subscriber) {
                                                            long offset = 0;
                                                            long length = file.length();
                                                            long count = 1024 * 1024;
                                                            KLog.e("length::" + length);
                                                            while (true) {
                                                                if (count > (length - offset)) {
                                                                    count = length - offset;
                                                                }
                                                                String base64 = Base64.encodeToString(FileUtils.getFileBytes(file.getAbsolutePath(), (int) offset, (int) count), Base64.DEFAULT);
                                                                Call<ResponseBody> call = HttpHelper.getInstance().initService().upload(NewsEMCAppllication.mToken, photoName, base64,
                                                                        length + "",
                                                                        offset + "",
                                                                        NewsEMCAppllication.mQRCodeBean.getId(), "1", getHieCoding(), NewsEMCAppllication.mQRCodeBean.getId(), phoneinfoString);

                                                                Response<ResponseBody> response = null;
                                                                try {
                                                                    response = call.execute();//同步线程

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
                                                                               progressDialog.setMessage("正在上传…");
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
                                                                               ToastUtils.showToast(getActivity(), "上传成功");
                                                                           }

                                                                           @Override
                                                                           public void onError(Throwable e) {
                                                                               if (progressDialog != null) {
                                                                                   progressDialog.dismiss();
                                                                               }
                                                                               ToastUtils.showToast(getActivity(), "上传失败");
                                                                           }

                                                                       }
                                                            );
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).setNegativeButton("取消", null).show();
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                progressDialog = CustomProgressDialog.createDialog(getActivity());
                                progressDialog.setMessage("正在压缩......");
                                progressDialog.show();
                            }
                        });
            }
        }
        if (requestCode == REQUEST_IMAGE) { // 表示是从相册选择图片返回
            if (null == data) {
                return;
            }
            Uri uri = data.getData(); //得到图片 uri

            final String strPath = ImageUtils.getImageAbsolutePath(getActivity(), uri);
            KLog.e("length:" + new File(strPath).length());
            final String strFileName = new File(strPath).getName();
            Luban.get(getActivity())
                    .load(new File(strPath))
                    .putGear(Luban.THIRD_GEAR)
                    .asObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends File>>() {
                        @Override
                        public Observable<? extends File> call(Throwable throwable) {
                            return Observable.empty();
                        }
                    }).subscribe(new Subscriber<File>() {
                @Override
                public void onCompleted() {

                    progressDialog.dismiss();
                }

                @Override
                public void onError(Throwable e) {

                    progressDialog.dismiss();
                    ToastUtils.showToast(getActivity(), "压缩失败");
                }

                @Override
                public void onStart() {
                    super.onStart();
                    progressDialog = CustomProgressDialog.createDialog(getActivity());
                    progressDialog.setMessage("正在压缩....");
                    progressDialog.show();
                }

                @Override
                public void onNext(final File file) {

                    KLog.e("strPath::" + strPath);
                    KLog.e(strFileName);

                    //自定义一个dialog
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    layout = inflater.inflate(R.layout.fileeditdialog, null);
                    fileEditText = (EditText) layout.findViewById(R.id.etname);
                    new AlertDialog.Builder(getActivity()).
                            setTitle("编辑文件名").
                            setView(layout).
                            setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ToastUtils.showToast(getActivity(), "确定");
                                    String name = strFileName.substring(strFileName.lastIndexOf("."));
                                    KLog.e("name" + name);
                                    filename = fileEditText.getText() + name;
                                    ToastUtils.showToast(getActivity(), "文件名" + filename);
                                    //Rxjava+retrofit同步上传
                                    Observable.create(new Observable.OnSubscribe<ResponseBody>() {
                                        @Override
                                        public void call(Subscriber<? super ResponseBody> subscriber) {
                                            long offset = 0;
                                            long length = file.length();
                                            long count = 1024 * 1024;
                                            KLog.e("length::" + length);
                                            KLog.e("path:" + file.getAbsolutePath());
                                            while (true) {
                                                if (count > (length - offset)) {
                                                    count = length - offset;
                                                }
                                                String base64 = Base64.encodeToString(FileUtils.getFileBytes(file.getAbsolutePath(), (int) offset, (int) count), Base64.DEFAULT);
                                                KLog.e("base64:" + base64);
                                                KLog.e("NewsEMCAppllication.mToken:" + NewsEMCAppllication.mToken);
                                                KLog.e("filename:" + filename);
                                                KLog.e("length:" + String.valueOf(length));
                                                KLog.e("offset:" + offset + "");
                                                KLog.e("ebsdataid:" + NewsEMCAppllication.mQRCodeBean.getId());
                                                KLog.e("filetype:" + "1");
                                                KLog.e("getHieCoding():" + getHieCoding());
                                                KLog.e("hieid:" + NewsEMCAppllication.mQRCodeBean.getId());
                                                KLog.e("空" + "");
                                                Call<ResponseBody> call = HttpHelper.getInstance().initService().upload(NewsEMCAppllication.mToken, filename, base64,
                                                        length + "",
                                                        offset + "",
                                                        NewsEMCAppllication.mQRCodeBean.getId(), "1", getHieCoding(), NewsEMCAppllication.mQRCodeBean.getId(), phoneinfoString);

                                                Response<ResponseBody> response = null;
                                                try {
                                                    response = call.execute();//同步线程

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
                                                               progressDialog.setMessage("正在上传…");
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
                                                               ToastUtils.showToast(getActivity(), "上传成功");
                                                           }

                                                           @Override
                                                           public void onError(Throwable e) {
                                                               if (progressDialog != null) {
                                                                   progressDialog.dismiss();
                                                                   progressDialog = null;
                                                               }
                                                               ToastUtils.showToast(getActivity(), "上传失败");
                                                           }

                                                       }
                                            );
                                }
                            }).
                            setNegativeButton("取消", null).show();

                }
            });

        }

    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }


}
