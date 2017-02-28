package newsemc.com.awit.news.newsemcapp.scanmodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.RecordsFragmentRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.base.BaseFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.PierRecordsFragmentBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

/**
 * Created by leguang on 2016/9/16 0016.
 * 联系邮箱:langmanleguang@qq.com
 * <p/>
 * 施工记录Fragment
 */
public class PierRecordsFragment extends BaseFragment {
    private static final String TAG = PierRecordsFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PtrFrameLayout mPtrFrameLayout;
    private RecordsFragmentRecyclerViewAdapter mAdapter;
    private PierRecordsFragmentBean itemsData;
    private Gson mGson;

    public static PierRecordsFragment newInstance() {
        return new PierRecordsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_records, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //处理view中的控件
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_records_fragment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_records_fragment);
        initData();
    }

    private void initData() {
        mGson = new Gson();
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
        KLog.e(ConstantsUtils.pier_records_url);
        return ConstantsUtils.pier_records_url;
    }

    @Override
    public Map<String, String> createRefreshParams() {

        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("pierId", NewsEMCAppllication.mQRCodeBean.getId());

        //测试id
//        paramsMap.put("pierId", "49477");

        return paramsMap;
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

                itemsData = mGson.fromJson(response, PierRecordsFragmentBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getInfolist().size() > 0) {

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
        mAdapter = new RecordsFragmentRecyclerViewAdapter(getActivity(), itemsData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        ToastUtils.showToast(getActivity(), "网络访问失败!");
    }

}
