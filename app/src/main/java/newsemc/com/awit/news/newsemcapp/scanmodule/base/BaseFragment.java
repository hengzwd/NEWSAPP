package newsemc.com.awit.news.newsemcapp.scanmodule.base;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.volley.VolleyError;
import com.socks.library.KLog;

import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.DisplayUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.widget.PageStateLayout;


/**
 * Created by leguang on 16/5/4.
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
   // private CustomProgressDialog progressDialog;


    public void initPageStateLayout(final PageStateLayout mPageStateLayout) {
        if (null == mPageStateLayout) return;

        mPageStateLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    public void initPtrFrameLayout(final PtrFrameLayout mPtrFrameLayout) {
        if (null == mPtrFrameLayout) return;

        // 下拉刷新头部
        final StoreHouseHeader ptrHeader = new StoreHouseHeader(getActivity());
        final String[] mStringList = {".", "."};
        ptrHeader.setTextColor(Color.BLACK);
        ptrHeader.setPadding(0, DisplayUtils.dp2px(15), 0, 0);
        ptrHeader.initWithString(mStringList[0]);
        mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
            private int mLoadTime = 0;

            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                String string = mStringList[mLoadTime % mStringList.length];
                ptrHeader.initWithString(string);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                String string = mStringList[mLoadTime % mStringList.length];
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });
        mPtrFrameLayout.setHeaderView(ptrHeader);
        mPtrFrameLayout.addPtrUIHandler(ptrHeader);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 100);
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return isCanDoRefresh();
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                refresh();
                frame.refreshComplete();
            }
        });
    }

    public void refresh() {
//        if (progressDialog == null) {
//            progressDialog = CustomProgressDialog.createDialog(getActivity());
//            progressDialog.setMessage("刷新中…");
//        }
//        progressDialog.show();
        String URL = createRefreshULR();
        Map<String, String> paramsMap = createRefreshParams();
        KLog.e("URL：" + URL);
        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.postRequest(URL, paramsMap, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.e("response:" + response);
                onRefreshSuccess(response);
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.e(error);
                onRefreshFailed(error);
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
            }
        });

    }

    public String createRefreshULR() {
        return null;
    }

    public Map<String, String> createRefreshParams() {
        return null;
    }

    public void onRefreshSuccess(String response) {
    }

    public void onRefreshFailed(VolleyError error) {
    }

    public void loadMore() {
        String URL = createLoadMoreULR();
        Map<String, String> paramsMap = createLoadMoreParams();
        //联网获取数据
        //还没有判断url，用户再判断
        HttpUtils.postRequest(URL, paramsMap, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                onLoadMoreSuccess(response);
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.d(error);
                onLoadMoreFailed(error);
            }
        });
    }

    public String createLoadMoreULR() {
        return null;
    }

    public Map<String, String> createLoadMoreParams() {
        return null;
    }

    public void onLoadMoreSuccess(String response) {
    }

    public void onLoadMoreFailed(VolleyError error) {
        error.printStackTrace();
    }

    public boolean isCanDoRefresh() {
        return true;
    }
}