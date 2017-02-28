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
import android.widget.Toast;

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
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ArchiveInfoDetailActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.ArchiveInfoListRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.base.BaseFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.ArchiveInfoBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

/**
 * Created by leguang on 2016/9/16 0016.
 * 联系邮箱:langmanleguang@qq.com
 * <p>
 * 设计资料Fragment
 */
public class DesignInfoFragment extends BaseFragment {
    private static final String TAG = DesignInfoFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private PtrFrameLayout mPtrFrameLayout;
    private ArchiveInfoListRecyclerViewAdapter mAdapter;
    private Gson mGson;
    private ArchiveInfoBean itemsData;
    private List<ArchiveInfoBean.InfolistBean> listData;
    private boolean isLoading;
    private int lastVisibleItemPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private int intPage = 1;

    public static DesignInfoFragment newInstance() {
        return new DesignInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //处理view中的控件
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_information_fragment);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_information_fragment);
        initData();
    }

    private void initData() {
        mGson = new Gson();
        listData = new ArrayList<>();
        mAdapter = new ArchiveInfoListRecyclerViewAdapter(getActivity(), listData);
        mRecyclerView.setLayoutManager(mLinearLayoutManager = new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
             //   Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArchiveInfoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailDateList", listData.get(position));
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
                }else {
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
        paramsMap.put("filetypeid", "2");

        // 1为项目、2为构筑物、3为标段、4为工点、5、为ebs工程实体
        //墩和特殊结构就是5，隧道工点就是4
        KLog.e(getHieCoding());
        paramsMap.put("hiecoding", getHieCoding());
//        paramsMap.put("hiecoding", "1");

        //层级结构对应id
        KLog.e(NewsEMCAppllication.mQRCodeBean.getId());
        paramsMap.put("hieid", NewsEMCAppllication.mQRCodeBean.getId());
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
        paramsMap.put("filetypeid", "2");

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
        paramsMap.put("page", "1");

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

    @Override
    public void onLoadMoreSuccess(String response) {
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
            }
        } else {
            //提示返回数据异常，展示错误页面
            //ToastUtils.showToast(getActivity(), "数据异常!");
        }
    }

    @Override
    public void onLoadMoreFailed(VolleyError error) {
        super.onLoadMoreFailed(error);
        ToastUtils.showToast(getActivity(), "加载失败!");
        intPage = intPage - 1;
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }
}
