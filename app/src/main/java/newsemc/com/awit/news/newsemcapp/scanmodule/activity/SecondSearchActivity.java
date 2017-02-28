package newsemc.com.awit.news.newsemcapp.scanmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.base.BaseActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.HistoryRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.SecondSearchActivityRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.FirstSearchBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.QRCodeBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.SecondSearchBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.SharedPreferencesUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

/**
 * Created by leguang on 2016/9/20 0020.
 * 联系邮箱:langmanleguang@qq.com
 */
public class SecondSearchActivity extends BaseActivity {
    private static final String TAG = SecondSearchActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SecondSearchActivityRecyclerViewAdapter mAdapter;
    private Gson mGson;
    private SecondSearchBean itemsData;
    private EditText et_search;
    private Button bt_search;
    private FirstSearchBean.BuildlistBean searchData;
    private String strSearchName = "";
    private String historyJson;
    private ArrayList historyList;
    private HistoryRecyclerViewAdapter historymAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_search);
        initView();
        initData();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        et_search = (EditText) findViewById(R.id.et_search);
        bt_search = (Button) findViewById(R.id.bt_search);
        et_search.clearFocus();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_second_search_activity);
    }


    private void initData() {
        searchData = (FirstSearchBean.BuildlistBean) getIntent().getSerializableExtra("searchData");
        initToolbarBackNavigation(mToolbar);
        setToolbarTitle();
        initToolbarMenu(mToolbar);

        mGson = new Gson();

        historyJson = (String) SharedPreferencesUtils.get(this, "second_search_history", "");
        KLog.e(historyJson);

        if (!TextUtils.isEmpty(historyJson)) {
            historyList = mGson.fromJson(historyJson, ArrayList.class);
        } else {
            historyList = new ArrayList<String>();
        }

        if (searchData.getBuildtype().equals("578")) {
            refresh();
        } else {
            setHistoryAdapter();
        }

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSearchName = et_search.getText().toString();
                if (!TextUtils.isEmpty(strSearchName)) {

                    if (!historyList.contains(strSearchName)) {
                        historyList.add(strSearchName);
                        if (historyList.size() >= 10) {
                            historyList.remove(0);
                        }
                        KLog.e(mGson.toJson(historyList));
                        SharedPreferencesUtils.put(getApplicationContext(), "second_search_history", mGson.toJson(historyList));
                    }

                    for (int i = 0; i < historyList.size(); i++) {
                        KLog.e("第" + i + ":" + historyList.get(i));
                    }
                    refresh();
                } else {
                    ToastUtils.showToast(getApplicationContext(), "输入不能为空");
                }
            }
        });
    }

    @Override
    public String createRefreshULR() {
        return ConstantsUtils.second_search_url;
    }

    @Override
    public Map<String, String> createRefreshParams() {

        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("bulidid", searchData.getBuildid());

        paramsMap.put("buildtype", searchData.getBuildtype());

        paramsMap.put("projectsectionid", searchData.getProjectsectionid());

        paramsMap.put("name", strSearchName);
        KLog.e("strSearchName:" + strSearchName);

//        paramsMap.put("bulidid", "33813");
//
//        paramsMap.put("buildtype", "571");
//
//        paramsMap.put("projectsectionid", "7789");
//
//        paramsMap.put("name", strSearchName);

        return paramsMap;
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!TextUtils.isEmpty(response)) {
            itemsData = mGson.fromJson(response, SecondSearchBean.class);
            if (null != itemsData && itemsData.getBuildlist().size() > 0) {
                setAdapter();
            } else {
                //提示数据解析异常，展示错误页面
                //ToastUtils.showToast(getApplicationContext(), "暂无数据!");
            }

        } else {
            //提示返回数据异常，展示错误页面
           //  // ToastUtils.showToast(getApplicationContext(), "数据异常!");
        }
    }

    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SecondSearchActivityRecyclerViewAdapter(this, itemsData);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                SecondSearchBean.BuildlistBean temp = itemsData.getBuildlist().get(position);
                QRCodeBean mQRCodeBean = new QRCodeBean();
                mQRCodeBean.setId(temp.getId());
                mQRCodeBean.setType(temp.getType());
                mQRCodeBean.setName(temp.getName());
                NewsEMCAppllication.mQRCodeBean = mQRCodeBean;

                Intent intent = new Intent(SecondSearchActivity.this, ScanMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("QRCodeBean", mQRCodeBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        ToastUtils.showToast(getApplicationContext(), "网络访问失败!");
    }

    private void setToolbarTitle() {
        if (null != mToolbar) {
            mToolbar.setTitle("工程部位搜索");
        }
    }

    private void initToolbarMenu(final Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.menu_delete);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        SharedPreferencesUtils.put(getApplicationContext(), "first_search_history", "");
                        historyList.clear();
                        setHistoryAdapter();
                        KLog.e("清空");
                        break;
                }
                return true;
            }
        });
    }

    private void setHistoryAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historymAdapter = new HistoryRecyclerViewAdapter(this, historyList);
        historymAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    return;
                }
                strSearchName = (String) historyList.get(--position);
                et_search.setText(strSearchName);
                KLog.e(et_search.getText().toString());
                refresh();
            }
        });
        mRecyclerView.setAdapter(historymAdapter);
    }
}
