package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.RelateaAccountAdapter;
import newsemc.com.awit.news.newsemcapp.bean.RelateaAccountLoginBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.StringUtils;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.service.DanDianLoginPubService;
import newsemc.com.awit.news.newsemcapp.service.IconInfoService;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by hengzwd on 2017/1/17.
 */
public class RelateaAccountDialogActivity extends Activity {


    private String TAG = RelateaAccountDialogActivity.class.getSimpleName();
    private RecyclerView recycleview;
    private FrameLayout mfragment;
    private String ssid;
    private List<String> list;
    private String relateAccounts;
    private RelateaAccountAdapter madapter;
    private AlertDialog.Builder builder;
    private HashMap map = new HashMap();
    private Gson gson;
    private LinearLayoutManager mLinearLayoutManager;
    private View addHeaderView;
    private LayoutInflater layoutInflater;
    private ImageView imageCancle;
    private TextView tv_noaccount;
    private TextView tv_select;
    private Button btn_cancle;


    //数据表的定义
    private ImageInfoService imageInfoService;
    private CompanyInfoService companyInfoService;
    private IconInfoService iconInfoService;
    private DanDianLoginPubService danDianLoginPubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initview();
        initdata();
    }


    private void initview() {
        recycleview = (RecyclerView) findViewById(R.id.rcl_relateaaccount);
        mfragment = (FrameLayout) findViewById(R.id.fl_container_relateaAccountDialog_activity);
        imageCancle= (ImageView) findViewById(R.id.imgv_cancle);
        tv_noaccount= (TextView) findViewById(R.id.tv_noaccount);
        tv_select= (TextView) findViewById(R.id.tv_select_reoateaaccount_activity);
        btn_cancle= (Button) findViewById(R.id.btn_cancel);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
//        mfragment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private void initdata() {

        //初始化数据表的业务
        imageInfoService = ImageInfoService.getInstance(RelateaAccountDialogActivity.this);
        companyInfoService = CompanyInfoService.getInstance(RelateaAccountDialogActivity.this);
        iconInfoService = IconInfoService.getInstance(RelateaAccountDialogActivity.this);
        danDianLoginPubService = DanDianLoginPubService.getInstance(RelateaAccountDialogActivity.this);

        gson = new Gson();
        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        layoutInflater = getLayoutInflater();
        relateAccounts = (String) bundle.get("relateAccount");
        list = StringUtils.stringsToList(relateAccounts);
        if (list.size()==0||list.get(0).equals("")) {
            recycleview.setVisibility(View.GONE);
//            tv_select.setVisibility(View.GONE);
            tv_noaccount.setVisibility(View.VISIBLE);
            tv_noaccount.setText("没有可以切换的账号");
            btn_cancle.setVisibility(View.VISIBLE);
        }
        ssid = (String) bundle.get("ssid");
        map.put("ssid", ssid);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recycleview.setLayoutManager(mLinearLayoutManager);
        madapter = new RelateaAccountAdapter();
        madapter.setOnItemChildClickListener(new RelateaAccountAdapter.OnItemChildClickListener() {
            @Override
            public void onclick(final int position, String s) {
                builder = new AlertDialog.Builder(RelateaAccountDialogActivity.this);
                builder.setTitle("切换账号")
                        .setMessage("确认切换至账号：" + list.get(position))
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                map.put("account", list.get(position));
                                HttpUtils.postRequest(ValueConfig.PCAPP_URL + "user/login!switchAccount.action", map, new HttpUtils.HttpListener() {
                                    @Override
                                    public void onSuccess(String response) {
                                        KLog.e(TAG, response);
                                        if (!response.isEmpty()) {
                                            RelateaAccountLoginBean data = gson.fromJson(response, RelateaAccountLoginBean.class);
                                            if (data.getStatus().equals("0")) {

                                                Bundle bundle1 = new Bundle();
                                                Intent itent = new Intent(RelateaAccountDialogActivity.this, MainActivity.class);
                                                bundle1.putString("name", data.getData().getList().get(0).getName());
                                                bundle1.putString("duty", data.getData().getList().get(0).getDepartments().get(0).getDuty());
                                                bundle1.putString("contact", data.getData().getList().get(0).getContact());
                                                bundle1.putString("relateAccount", data.getData().getList().get(0).getRelateAccount());
                                                bundle1.putString("compname", data.getData().getList().get(0).getCompname());
                                                bundle1.putString("f_userId", String.valueOf(data.getData().getList().get(0).getUserId()));
                                                bundle1.putString("ssid", data.getData().getList().get(0).getSsid());
                                                itent.putExtras(bundle1);
                                                itent.putExtra("compid", data.getData().getList().get(0).getCompid());
                                                itent.putExtra("ssidd", data.getData().getList().get(0).getSsid());
                                                RelateaAccountDialogActivity.this.setResult(1);
                                                startActivity(itent);

//                                                imageInfoService.deleteAllImageInfo();
//                                                companyInfoService.deleteAllCompanyInfo();
//                                                iconInfoService.deleteAllIconInfo();
//                                                danDianLoginPubService.deleteAllDanDianLoginPub();
                                                SharedPreferences sp = getSharedPreferences(
                                                        "SP", MODE_PRIVATE);
                                                String key = data.getData().getList().get(0).getSsid();
                                                ssid = key;
                                                SharedPreferences.Editor editor = sp.edit();
                                                editor.putString("KEY", key);
                                                editor.putString("ACCOUNT", data.getData().getList().get(0).getAccount());
                                                editor.commit();
                                                Toast.makeText(getApplicationContext(), "切换账号至"+ data.getData().getList().get(0).getAccount(), Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "切换账号失败", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailed(VolleyError error) {
                                        KLog.e(TAG, error.toString());
                                        Toast.makeText(getApplicationContext(), "切换账号失败", Toast.LENGTH_SHORT).show();
                                         finish();
                                    }
                                });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        finish();
                    }
                }).show();

            }
        });
        madapter.setNewData(list);
        recycleview.setAdapter(madapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
