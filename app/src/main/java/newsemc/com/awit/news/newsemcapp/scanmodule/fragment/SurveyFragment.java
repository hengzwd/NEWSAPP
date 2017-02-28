package newsemc.com.awit.news.newsemcapp.scanmodule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.volley.VolleyError;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.ptr.PtrFrameLayout;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ShowPhotoActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.SurveyFragmentRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.base.BaseFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.SurveyBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

/**
 * Created by leguang on 2016/9/16 0016.
 * 联系邮箱:langmanleguang@qq.com
 * <p>
 * 工程概况Fragment
 */
public class SurveyFragment extends BaseFragment {
    private static final String TAG = SurveyFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PtrFrameLayout mPtrFrameLayout;
    private ScrollView mScrollView;
    private ImageView iv_top;
    private SurveyFragmentRecyclerViewAdapter mAdapter;
    private SurveyBean itemsData;
    private Gson mGson;
    private CustomProgressDialog progressDialog;

    public static SurveyFragment newInstance() {
        return new SurveyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //处理view中的控件
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_survey_fragment);
        mScrollView = (ScrollView) view.findViewById(R.id.nsv_survey_fragment);
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_survey_fragment);
        iv_top = (ImageView) view.findViewById(R.id.iv_top_survey_fragment);
        initData();
    }

    private void initData() {
        mGson = new Gson();
        initPtrFrameLayout(mPtrFrameLayout);
        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsData.getInfolist().size() > 0 && !TextUtils.isEmpty(itemsData.getImageUrl1())) {
                    Intent mIntent = new Intent(getActivity(), ShowPhotoActivity.class);
                    mIntent.putExtra("mainurl", itemsData.getImageUrl1());
                    startActivity(mIntent);
                }
            }
        });
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (mScrollView.getScrollY() == 0) {
            return true;
        }
        return false;
    }

    public void refresh() {

        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getActivity());
            progressDialog.setMessage("刷新中…");
        }
        progressDialog.show();
        String URL;
        KLog.e(NewsEMCAppllication.mToken);

        if (null != NewsEMCAppllication.mQRCodeBean) {
            URL = ConstantsUtils.getTunnelSurveyData(NewsEMCAppllication.mQRCodeBean.getId(), NewsEMCAppllication.mQRCodeBean.getType());
            KLog.e(URL);
        } else {
            ToastUtils.showToast(getActivity(), "参数不正确!");
            return;
        }

        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.getRequest(URL, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.e(response);
                onRefreshSuccess(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.d(error);
                onRefreshFailed(error);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        });
    }

    @Override
    public void onRefreshSuccess(String response) {
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
                    itemsData = mGson.fromJson(response, SurveyBean.class);

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    //ToastUtils.showToast(getActivity(), "数据异常!");
                    return;
                }
                if (null != itemsData) {
                    if (itemsData.getInfolist().size() > 0) {
                        setData4View();
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

    private void setData4View() {

              Picasso.with(getActivity())
                  .load(itemsData.getImageUrl1())
                 //   .placeholder(R.drawable.downloadfailure)
                    .error(R.drawable.downloadfailure)
                  .into(iv_top);

        mAdapter = new SurveyFragmentRecyclerViewAdapter(getActivity(), itemsData.getInfolist());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        ToastUtils.showToast(getActivity(), "网络访问失败!");
    }
}
