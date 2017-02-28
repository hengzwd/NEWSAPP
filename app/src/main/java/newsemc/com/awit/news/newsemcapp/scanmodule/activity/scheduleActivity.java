package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.base.BaseActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.scheduleInfoListRecycleViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.scheduleBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

/**
 * Created by hengzwd on 2016/10/19.
 */
public class scheduleActivity  extends BaseActivity {


    private static final String TAG = scheduleActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 0;
    private Toolbar mToolbar;
    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private scheduleInfoListRecycleViewAdapter mAdapter;
    private Gson mGson;
    private scheduleBean itemsData;
    private List<scheduleBean.DailyListEntity> listData;
    private boolean isLoading;
    private int lastVisibleItemPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private int intPage = 1;
    private CustomProgressDialog progressDialog;
    private Bitmap mBitmap;
    private  int type;
    private  String tittle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        Intent mIntent = getIntent();
        type= (int) mIntent.getExtras().get("type");
        tittle=mIntent.getExtras().get("tittle").toString();
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_scheduleactivity);
        setToolbarTitle();

        initToolbarBackNavigation(mToolbar);
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_schedule_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.scheduleactivity_recycleview);
    }

    private void setToolbarTitle() {
        if (null != mToolbar&&null!=tittle ) {
            mToolbar.setTitle(tittle);
        }
    }



    private void initData() {

        mGson = new Gson();
        listData = new ArrayList<>();
        mAdapter = new scheduleInfoListRecycleViewAdapter(this, listData);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(this));

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


        return ConstantsUtils.schedule_url;
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

        paramsMap.put("rgdid", NewsEMCAppllication.mQRCodeBean.getId());

        paramsMap.put("type",String.valueOf(type));

        paramsMap.put("pageSize","20");

        //可以不填
        paramsMap.put("startdate", "");

        //用于以后扩展
        paramsMap.put("key", "");

        //页码
        paramsMap.put("pageNumber",intPage + "");



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
                 // ToastUtils.showToast(getApplicationContext(), "数据异常!");
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
                itemsData = mGson.fromJson(response, scheduleBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getDailyList().size() > 0) {
                        listData.addAll(itemsData.getDailyList());
                        if (null != listData) {
                            if (listData.size() > 0) {
                                mRecyclerView.setAdapter(mAdapter);
                            } else {
                                //提示数据为空，展示空状态
                                //ToastUtils.showToast(getApplicationContext(), "暂无数据!");
                            }
                        } else {
                            //提示数据解析异常，展示错误页面
                             // ToastUtils.showToast(getApplicationContext(), "数据异常!");
                        }
                    } else {
                        //提示数据为空，展示空状态
                        //ToastUtils.showToast(getApplicationContext(), "暂无数据!");
                    }
                } else {
                    //提示数据解析异常，展示错误页面
                     // ToastUtils.showToast(getApplicationContext(), "数据异常!");
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
    public void onRefreshFailed(VolleyError error) {
        ToastUtils.showToast(getApplicationContext(), "网络访问失败!");
    }

    @Override
    public String createLoadMoreULR() {
        return ConstantsUtils.schedule_url;
    }

    @Override
    public Map<String, String> createLoadMoreParams() {
        intPage = intPage + 1;
        KLog.e("intPage" + intPage);
        Map<String, String> paramsMap = new HashMap<String, String>();

        //授权Token值
        paramsMap.put("token", NewsEMCAppllication.mToken);

        paramsMap.put("rgdid", NewsEMCAppllication.mQRCodeBean.getId());

        paramsMap.put("type",String.valueOf(type));

        paramsMap.put("pageSize","20");

        //可以不填
        paramsMap.put("startdate", "");

        //用于以后扩展,先不管
        paramsMap.put("key", "");

        //页码
        paramsMap.put("pageNumber", intPage + "");

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
                 // ToastUtils.showToast(getApplicationContext(), "数据异常!");
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

                itemsData = mGson.fromJson(response, scheduleBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getDailyList().size() > 0) {
                        if (null != listData) {
                            Boolean isAddAll = listData.addAll(itemsData.getDailyList());
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
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
            }
        } else {
            //提示返回数据异常，展示错误页面
             // ToastUtils.showToast(getApplicationContext(), "数据异常!");
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
