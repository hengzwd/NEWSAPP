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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.socks.library.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ArchiveInfoListActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ShowPhotoActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.scheduleActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.adapter.TunnelRecordsFragmentRecyclerViewAdapter;
import newsemc.com.awit.news.newsemcapp.scanmodule.base.BaseFragment;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.TunnelRecordsFragmentBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;

/**
 * Created by leguang on 2016/9/16 0016.
 * 联系邮箱:langmanleguang@qq.com
 * <p/>
 * 施工记录Fragment
 */
public class TunnelRecordsFragment extends BaseFragment {
    private static final String TAG = TunnelRecordsFragment.class.getSimpleName();
    private PtrFrameLayout mPtrFrameLayout;
    private ScrollView mScrollView;
    private ImageView iv_top;
    private TextView tv00_level;
    private TextView tv01_level;
    private TextView tv00_mileage;
    private TextView tv01_mileage;
    private TextView tv00_warning;
    private TextView tv01_warning;
    private TextView tv00_accomplished;
    private TextView tv01_accomplished;

    private TextView tv10_level;
    private TextView tv11_level;
    private TextView tv10_mileage;
    private TextView tv11_mileage;
    private TextView tv10_warning;
    private TextView tv11_warning;
    private TextView tv10_accomplished;
    private TextView tv11_accomplished;

    private TextView tv20_level;
    private TextView tv21_level;
    private TextView tv20_mileage;
    private TextView tv21_mileage;
    private TextView tv20_warning;
    private TextView tv21_warning;
    private TextView tv20_accomplished;
    private TextView tv21_accomplished;
    private RecyclerView mRecyclerView;
    private TunnelRecordsFragmentRecyclerViewAdapter mAdapter;
    private TunnelRecordsFragmentBean itemsData;
    private Gson mGson;
    private TextView tv_jianyanpi;
    private TextView tv_shiyanbaogao;
    private TextView tv_yingxiang;
    private LinearLayout ll_jianyanpi;
    private LinearLayout ll_shiyanbaogao;
    private LinearLayout ll_yingxiang;
    private LinearLayout ll_kaiwajindu;
    private LinearLayout ll_gongyang;
    private LinearLayout ll_erchun;

    private int type;
    private String tittle;

    public static TunnelRecordsFragment newInstance() {
        return new TunnelRecordsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tunnel_records, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //处理view中的控件
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_tunnel_records_fragment);
        mScrollView = (ScrollView) view.findViewById(R.id.nsv_tunnel_records_fragment);
        iv_top = (ImageView) view.findViewById(R.id.iv_top_tunnel_records_fragment);

        tv00_level = (TextView) view.findViewById(R.id.tv00_level_tunnel_records_fragment);
        tv01_level = (TextView) view.findViewById(R.id.tv01_level_tunnel_records_fragment);
        tv00_mileage = (TextView) view.findViewById(R.id.tv00_mileage_tunnel_records_fragment);
        tv01_mileage = (TextView) view.findViewById(R.id.tv01_mileage_tunnel_records_fragment);
        tv00_warning = (TextView) view.findViewById(R.id.tv00_warning_tunnel_records_fragment);
        tv01_warning = (TextView) view.findViewById(R.id.tv01_warning_tunnel_records_fragment);
        tv00_accomplished = (TextView) view.findViewById(R.id.tv00_accomplished_tunnel_records_fragment);
        tv01_accomplished = (TextView) view.findViewById(R.id.tv01_accomplished_tunnel_records_fragment);
        tv10_level = (TextView) view.findViewById(R.id.tv10_level_tunnel_records_fragment);
        tv11_level = (TextView) view.findViewById(R.id.tv11_level_tunnel_records_fragment);
        tv10_mileage = (TextView) view.findViewById(R.id.tv10_mileage_tunnel_records_fragment);
        tv11_mileage = (TextView) view.findViewById(R.id.tv11_mileage_tunnel_records_fragment);
        tv10_warning = (TextView) view.findViewById(R.id.tv10_warning_tunnel_records_fragment);
        tv11_warning = (TextView) view.findViewById(R.id.tv11_warning_tunnel_records_fragment);
        tv10_accomplished = (TextView) view.findViewById(R.id.tv10_accomplished_tunnel_records_fragment);
        tv11_accomplished = (TextView) view.findViewById(R.id.tv11_accomplished_tunnel_records_fragment);
        tv20_level = (TextView) view.findViewById(R.id.tv20_level_tunnel_records_fragment);
        tv21_level = (TextView) view.findViewById(R.id.tv21_level_tunnel_records_fragment);
        tv20_mileage = (TextView) view.findViewById(R.id.tv20_mileage_tunnel_records_fragment);
        tv21_mileage = (TextView) view.findViewById(R.id.tv21_mileage_tunnel_records_fragment);
        tv20_warning = (TextView) view.findViewById(R.id.tv20_warning_tunnel_records_fragment);
        tv21_warning = (TextView) view.findViewById(R.id.tv21_warning_tunnel_records_fragment);
        tv20_accomplished = (TextView) view.findViewById(R.id.tv20_accomplished_tunnel_records_fragment);
        tv21_accomplished = (TextView) view.findViewById(R.id.tv21_accomplished_tunnel_records_fragment);

        tv_jianyanpi = (TextView) view.findViewById(R.id.tv_jianyanpi_tunnel_records_fragment);
        tv_shiyanbaogao = (TextView) view.findViewById(R.id.tv_shiyanbaogao_tunnel_records_fragment);
//        tv_yingxiang = (TextView) view.findViewById(R.id.tv_yingxiang_tunnel_records_fragment);

        ll_jianyanpi = (LinearLayout) view.findViewById(R.id.ll_jianyanpi);
        ll_shiyanbaogao = (LinearLayout) view.findViewById(R.id.ll_shiyanbaogao);
      //  ll_yingxiang = (LinearLayout) view.findViewById(R.id.ll_yingxiang);


        ll_kaiwajindu = (LinearLayout) view.findViewById(R.id.ll_kaiwa_jindu);
        ll_gongyang = (LinearLayout) view.findViewById(R.id.ll_gongyang);
        ll_erchun = (LinearLayout) view.findViewById(R.id.ll_erchun);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tunnel_records_fragment);


        initData();
    }

    private void initData() {
        initPtrFrameLayout(mPtrFrameLayout);
        mGson = new Gson();

        ll_kaiwajindu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 0;
                if (null!=tv01_level.getText()&&!Util.isEmpty(tv01_level.getText().toString())) {
                    startScheduleActivity(type);
                }
            }
        });
        ll_gongyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                if (null!=tv11_level.getText()&&!Util.isEmpty(tv11_level.getText().toString()))
                startScheduleActivity(type);
            }
        });
        ll_erchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                if (null!=tv21_level.getText()&&!Util.isEmpty(tv21_level.getText().toString()))
                startScheduleActivity(type);
            }
        });
        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsData.getSecurity().size() > 0 && !TextUtils.isEmpty(itemsData.getImageUrl1())) {
                    KLog.e("1111111111111111");
                    Intent mIntent = new Intent(getActivity(), ShowPhotoActivity.class);
                    mIntent.putExtra("mainurl", itemsData.getImageUrl1());
                    startActivity(mIntent);
                }
            }
        });

        ll_jianyanpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), ArchiveInfoListActivity.class);
                mIntent.putExtra("archive_id", NewsEMCAppllication.mQRCodeBean.getId());
                mIntent.putExtra("archive_type", itemsData.getQuality().get(0).getId());
                mIntent.putExtra("archive_name", itemsData.getQuality().get(0).getName());
                mIntent.putExtra("archive_hiecoding", itemsData.getQuality().get(0).getType());

                getActivity().startActivity(mIntent);
            }
        });

        ll_shiyanbaogao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), ArchiveInfoListActivity.class);
                mIntent.putExtra("archive_id", NewsEMCAppllication.mQRCodeBean.getId());
                mIntent.putExtra("archive_type", itemsData.getQuality().get(1).getId());
                mIntent.putExtra("archive_name", itemsData.getQuality().get(1).getName());
                mIntent.putExtra("archive_hiecoding", itemsData.getQuality().get(1).getType());

                getActivity().startActivity(mIntent);

            }
        });

//        ll_yingxiang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(getActivity(), ArchiveInfoListActivity.class);
//                mIntent.putExtra("archive_id", NewsEMCAppllication.mQRCodeBean.getId());
//                mIntent.putExtra("archive_type", itemsData.getQuality().get(2).getId());
//                mIntent.putExtra("archive_name", "影像资料");
//                mIntent.putExtra("archive_hiecoding", itemsData.getQuality().get(2).getType());
//
//                getActivity().startActivity(mIntent);
//
//            }
//        });

    }


    private void startScheduleActivity(int type) {
        switch (type) {
            case 0:
                tittle = getResources().getString(R.string.kaiwajindu);
                break;
            case 1 :
                tittle = getResources().getString(R.string.yanggong);
                break;
            case 2 :
                tittle = getResources().getString(R.string.erchun);
                break;

        }
        Intent mIntent = new Intent(getActivity(), scheduleActivity.class);
        mIntent.putExtra("tittle", tittle);
        mIntent.putExtra("type",type);
        getActivity().startActivity(mIntent);
    }

    @Override
    public boolean isCanDoRefresh() {
        //判断是哪种状态的页面，都让其可下拉
        if (mScrollView.getScrollY() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String createRefreshULR() {
        KLog.e(ConstantsUtils.tunnel_records_url);
        return ConstantsUtils.tunnel_records_url;
    }

    @Override
    public Map<String, String> createRefreshParams() {

        Map<String, String> paramsMap = new HashMap<String, String>();

        paramsMap.put("rgdid", NewsEMCAppllication.mQRCodeBean.getId());

        //测试id
//        paramsMap.put("rgdid", "5054");

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

                itemsData = mGson.fromJson(response, TunnelRecordsFragmentBean.class);
                if (null != itemsData) {
                    if (itemsData.getResult() == 0 && itemsData.getSecurity().size() > 0) {

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
              //  .placeholder(R.drawable.downloadfailure)
                .error(R.drawable.downloadfailure)
                .into(iv_top);

        List<TunnelRecordsFragmentBean.ProgressBean.ProfileInfoListBean> temp0 = itemsData.getProgress().get(0).getProfileInfoList();

        tv00_level.setText(temp0.get(2).getKey() + ":");
        tv01_level.setText(temp0.get(2).getValue());


        tv00_mileage.setText(temp0.get(1).getKey() + ":");
        tv01_mileage.setText(temp0.get(1).getValue());
        tv00_warning.setText(temp0.get(4).getKey() + ":");
        tv01_warning.setText(temp0.get(4).getValue());
        tv00_accomplished.setText(temp0.get(3).getKey() + ":");
        tv01_accomplished.setText(temp0.get(3).getValue());

        List<TunnelRecordsFragmentBean.ProgressBean.ProfileInfoListBean> temp1 = itemsData.getProgress().get(1).getProfileInfoList();
        tv10_level.setText(temp1.get(2).getKey() + ":");
        tv11_level.setText(temp1.get(2).getValue());
        tv10_mileage.setText(temp1.get(1).getKey() + ":");
        tv11_mileage.setText(temp1.get(1).getValue());
        tv10_warning.setText(temp1.get(4).getKey() + ":");
        tv11_warning.setText(temp1.get(4).getValue());
        tv10_accomplished.setText(temp1.get(3).getKey() + ":");
        tv11_accomplished.setText(temp1.get(3).getValue());

        List<TunnelRecordsFragmentBean.ProgressBean.ProfileInfoListBean> temp2 = itemsData.getProgress().get(2).getProfileInfoList();

        tv20_level.setText(temp2.get(2).getKey() + ":");
        tv21_level.setText(temp2.get(2).getValue());
        tv20_mileage.setText(temp2.get(1).getKey() + ":");
        tv21_mileage.setText(temp2.get(1).getValue());
        tv20_warning.setText(temp2.get(4).getKey() + ":");
        tv21_warning.setText(temp2.get(4).getValue());
        tv20_accomplished.setText(temp2.get(3).getKey() + ":");
        tv21_accomplished.setText(temp2.get(3).getValue());

        if (itemsData.getQuality().size() > 0) {

            tv_jianyanpi.setText(itemsData.getQuality().get(0).getName() + "(" + itemsData.getQuality().get(0).getTotal() + ")");
            tv_shiyanbaogao.setText(itemsData.getQuality().get(1).getName() + "(" + itemsData.getQuality().get(1).getTotal() + ")");
           // tv_yingxiang.setText("影像资料" + "(" + itemsData.getQuality().get(2).getTotal() + ")");
        } else {
            tv_jianyanpi.setText("无数据");
            tv_shiyanbaogao.setText("无数据");
            //tv_yingxiang.setText("无数据");
        }

        mAdapter = new TunnelRecordsFragmentRecyclerViewAdapter(getActivity(), itemsData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefreshFailed(VolleyError error) {
        ToastUtils.showToast(getActivity(), "网络访问失败!");
    }

}
