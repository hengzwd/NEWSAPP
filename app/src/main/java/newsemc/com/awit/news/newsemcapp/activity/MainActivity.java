package newsemc.com.awit.news.newsemcapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.readystatesoftware.viewbadger.BadgeView;
import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.GloBalUrl.GloBalUrl;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.BitmapHelper;
import newsemc.com.awit.news.newsemcapp.adapter.MainNewsAdapter;
import newsemc.com.awit.news.newsemcapp.adapter.MyAdapter;
import newsemc.com.awit.news.newsemcapp.adapter.PopViewAdapter;
import newsemc.com.awit.news.newsemcapp.adapter.PopWindowAdapter;
import newsemc.com.awit.news.newsemcapp.adapter.TreeAdapter;
import newsemc.com.awit.news.newsemcapp.bean.AlterPwd;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.Logout;
import newsemc.com.awit.news.newsemcapp.bean.Match;
import newsemc.com.awit.news.newsemcapp.bean.UnBding;
import newsemc.com.awit.news.newsemcapp.dao.ApkUpdateInfo;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.CompanyLogoInfo;
import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPub;
import newsemc.com.awit.news.newsemcapp.dao.FirstMenuInfo;
import newsemc.com.awit.news.newsemcapp.dao.IconInfo;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;
import newsemc.com.awit.news.newsemcapp.dao.PushInfo;
import newsemc.com.awit.news.newsemcapp.dao.SecondMenuInfo;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.fragment.IntentFragment;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.AlterPwdImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ApkDetailImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.CompanyLogoImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.FirstMenuImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.IconImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ImgInfoListImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.SecondMenuImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.UnBdingImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.UserLogOutImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.FirstSearchActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ScanActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.widget.flipshareview.FlipShareView;
import newsemc.com.awit.news.newsemcapp.scanmodule.widget.flipshareview.ShareItem;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.service.DanDianLoginPubService;
import newsemc.com.awit.news.newsemcapp.service.IconInfoService;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;
import newsemc.com.awit.news.newsemcapp.tree.TreeElement;
import newsemc.com.awit.news.newsemcapp.util.DialogUtils;
import newsemc.com.awit.news.newsemcapp.util.GetApk;
import newsemc.com.awit.news.newsemcapp.util.MyDialog;
import newsemc.com.awit.news.newsemcapp.util.UIUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;
import newsemc.com.awit.news.newsemcapp.view.MyPopupWindow;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;


public class MainActivity extends FragmentActivity implements View.OnClickListener, ZrcListView.OnItemClickListener, HttpResultListener, AdapterView.OnItemClickListener, Toolbar.OnMenuItemClickListener {
    private Bundle bundle;
    private PopupWindow popWindow, setWindow;
    private LayoutInflater inflater;
    private Button set;
    private LinearLayout prodetail, myfocus, projectdynamic, create, daiban;
    private ImageView home;
    private ImageView gwcl;
    private ImageView wylc;
    private View contentView;
    private boolean visibility_Flag = false;
    private List<ImageInfo> main_list_view;
    ZrcListView zrcListView;
    private int pageNo = 1;
    private int num = 0;

    private View view1, view2, view3;
    private ViewPager viewPager;
    private ImageView cuesorView;
    private List<View> lists;
    private MyAdapter myAdapter;
    private MainNewsAdapter mainNewsAdapter;
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix;
    private int bmWidth;
    private Animation animation;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ProgressDialog pdDialog;
    private AlertDialog.Builder builder;
    private String ssid;
    MyPopupWindow window;
    private String account;
    //特殊账号
    private String Specialssid;
    private String Specialaccount;
    //取得特殊账号的ssid，account，imei；
    private SpecialAccountInfo specialAccountInfo;
    private Integer id;
    private String status;
    private String name;
    private final int POPUP_BASE_WIDTH = UIUtils.getDimens(R.dimen.popup_width);
    private final int POPUP_BASE_HEIGHT = UIUtils.getDimens(R.dimen.popup_height);

    private LinearLayout ll_line_indicator;
    private DisplayMetrics dm;

    //
    private String infotype, pageno;

    //icon实体类
//    private List<DanDianLoginPub> danDianLoginPubList;
    private List<DanDianLoginPub> danDianLoginPubList1;
    private List<DanDianLoginPub> danDianLoginPubList2;
    private List<DanDianLoginPub> danDianLoginPubList3;
    private List<DanDianLoginPub> danDianLoginPubList4;
    //    private List<DanDianLoginPub> danDianLoginPubList;
    private List<DanDianLoginPub> brighticon;
    private List<DanDianLoginPub> grayicon;

    //actionname列表
    private static Map<String, String> appType1 = new HashMap<String, String>();

    //packname列表
    private static Map<String, String> appType2 = new HashMap<>();
    //本地apk版本号
    private String sdkvercode;
    //服务器版本号
    private String servercode;

    //apk包名
    private String packname;
    //appId
    private String appID;
    //进度条
    private CustomProgressDialog progressDialog = null;


    //围岩量测数量
    String wylcnum;

    //公文处理数量
    String oanum;

    //主界面标题logo
    private ImageView mainlogo;
    private static final String TAG = "<--MainActivity-->";

    //调用网络图片类定义
    private BitmapUtils bitmapUtils;

    //公司id
    private String compid;
    //
    private CheckBox autologin;
    private boolean auto = true;

    //    private ImageInfoService imageInfoService;
//    private DanDianLoginPubService danDianLoginPubService;
    List<ImageInfo> read_up = new ArrayList<ImageInfo>();
    List<ImageInfo> read_locallist = new ArrayList<ImageInfo>();
    List<ImageInfo> reads = new ArrayList<ImageInfo>();
    private List<ImageInfo> main_list_view1;
    List<DanDianLoginPub> read_first = new ArrayList<DanDianLoginPub>();
    List<DanDianLoginPub> read_first1 = new ArrayList<DanDianLoginPub>();
    List<DanDianLoginPub> read_two = new ArrayList<DanDianLoginPub>();
    List<DanDianLoginPub> read_three = new ArrayList<DanDianLoginPub>();
    List<ImageInfo> list = new ArrayList<ImageInfo>();
    private String Page;
    private int pageid = -1;

    //数据表的定义
    private ImageInfoService imageInfoService;
    private CompanyInfoService companyInfoService;
    private IconInfoService iconInfoService;
    private DanDianLoginPubService danDianLoginPubService;


    //左侧滑菜单
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView listviewdrawer;
    private ArrayAdapter arrayAdapter;
    private LinearLayout drawer_left;
    private String[] mPlanetTitles;
    private ArrayList<TreeElement> mPdfOutlinesCount = new ArrayList<TreeElement>();
    private TreeAdapter treeAdapter;
    private List<String> title = new ArrayList<String>();
    private List<String> company = new ArrayList<String>();
    private List<String> duan = new ArrayList<String>();
    private ActionBar action;
    private FirstMenuImpl firstMenuimpl;
    private SecondMenuImpl secondMenuimpl;
    private String account1;
    private ArrayList<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();
    private ArrayList<CompanyImpl> companyImpl = new ArrayList<CompanyImpl>();
    private Intent intent = new Intent();


    private List<SecondMenuInfo> secondMenuInfoList = new ArrayList<SecondMenuInfo>();
    private String outlineTitle;
    private List<String> code = new ArrayList<String>();

    //
    private Handler zrchandler;
    private int pageId = -1;
    private ArrayList<String> msgs;
    //private myAdapter Adapter;
    //private MainNewsAdapter mainNewsAdapter;
    private Runnable loadItemThread;
    //文件夹的路径
//    private final static String PathString = "/mnt/sdcard/JING_SHENG";

    SlideShowView slideShowView;
    private List<CompanyLogoInfo> companyLogoInfoList;
    //栏目的返回按钮
    private ImageView back;
    //
    private ViewPager viewPager1;
    private ImageButton imageButton;
    private String appidl;
    private ImageView iv_scan;

    //根据app图标名称获取actionname
    static {
        appType1.put("公文处理", "oa.jszhb");
        appType1.put("资料管理", "");
        appType1.put("后台管理", "");
        appType1.put("技术管理", "com.tky.techmanage.openmain");
        appType1.put("规章制度", "");
        appType1.put("部门考核", "");
        appType1.put("诚信体系", "com.tky.Integrity.openmain");
        appType1.put("劳务工工资系统", "query.app");
        appType1.put("施工日志", "creec.ecs.action.MAIN");
        appType1.put("征地拆迁", "android.intent.action.openLand");
//        appType1.put("施工组织", "com.sgzz.tky.openmain");
        appType1.put("施工组织", "shizu.app");
        appType1.put("施工图审核", "construct.app");
        appType1.put("验工计价", "");
        appType1.put("物资设备", "");
        appType1.put("问题库", "");
        appType1.put("在线培训", "elearning.a");
        appType1.put("拌和站", "banhezhan.app");
        appType1.put("试验室", "bjjwsys.action");
        appType1.put("隧道监控", "android.intent.action.MAINLWB");
        appType1.put("沉降观测", "android.intent.action.cjgc.ldmk");
        appType1.put("连续梁", "BSRI.BCMSUI");
//        appType1.put("桥梁静载", "android.zys.qljz");
        appType1.put("桥梁静载", "qljz.app");
        appType1.put("隧道注浆", "");
        appType1.put("连续压实", "");
        appType1.put("自动张拉", "");
        appType1.put("梁场", "");
        appType1.put("智能板场", "");
        appType1.put("检验批", "creec.jyp.action.MAIN");
        appType1.put("通讯录", "com.tkycontacts.default.openmain");
//        appType1.put("会议管理", "hygl.app");
        appType1.put("会议管理", "conference.app");
        appType1.put("车辆管理", "car.app");
        appType1.put("隧道形象化", "com.tkytunnel.default.openmain");
        appType1.put("调度指挥", "dispatch.app");
    }

    //根据app图标名称获取包名
    static {
        appType2.put("公文处理", "com.creec.creeoa");//京沈
//        appType2.put("公文处理", "com.creec.chuannanoa");//川南
        appType2.put("资料管理", "");
        appType2.put("后台管理", "");
        appType2.put("技术管理", "com.tky.techmanage");
        appType2.put("规章制度", "");
        appType2.put("部门考核", "");
        appType2.put("诚信体系", "com.tky.Integrity");
        appType2.put("劳务工工资系统", "com.tky.query");
        appType2.put("施工日志", "com.creec.eclassist");
        appType2.put("征地拆迁", "com.tky.land_requisition");
//        appType2.put("施工组织", "com.sgzz.tky.application");
        appType2.put("施工组织", "com.tky.constructorganiztion");
        appType2.put("施工图审核", "com.tky.construct");
        appType2.put("验工计价", "");
        appType2.put("物资设备", "");
        appType2.put("问题库", "");
        appType2.put("在线培训", "com.luyou.training");
        appType2.put("拌和站", "bhz.awit.com.banhezhanemc");
        appType2.put("试验室", "com.bjjw.sysbuild");
        appType2.put("隧道监控", "com.eagle.xueprj");
        appType2.put("沉降观测", "com.example.webview");
        appType2.put("连续梁", "com.example.bcmsui");
//        appType2.put("桥梁静载", "com.zys.qljz");
        appType2.put("桥梁静载", "com.zys.qljz");
        appType2.put("隧道注浆", "");
        appType2.put("隧道形象化", "tkytunnel.tky.com.tkytunnel");
        appType2.put("连续压实", "");
        appType2.put("自动张拉", "");
        appType2.put("梁场", "");
        appType2.put("智能板场", "");
        appType2.put("检验批", "com.creec.ecljyp");
        appType2.put("会议管理", "com.tky.conference");
        appType2.put("车辆管理", "com.tky.cars");
        appType2.put("通讯录", "com.tky.tkycontacts");//京沈
//        appType2.put("通讯录", "com.tky.cntkycontacts");//川南
        appType2.put("调度指挥", "com.tky.dispatch");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //初始化数据表的业务
        imageInfoService = ImageInfoService.getInstance(MainActivity.this);
        companyInfoService = CompanyInfoService.getInstance(MainActivity.this);
        iconInfoService = IconInfoService.getInstance(MainActivity.this);
        danDianLoginPubService = DanDianLoginPubService.getInstance(MainActivity.this);
        autologin = (CheckBox) findViewById(R.id.autologin);

        bundle = getIntent().getExtras();
//        Log.i(TAG,"compid ="+intent.getStringExtra("compid"));
        Context ctx = MainActivity.this;
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.projectlist);
        //获取旧数据库库之后清空，否则不能自动更新数据库的内容
        danDianLoginPubService = DanDianLoginPubService.getInstance(MainActivity.this);
        //清空首页缓存数据库
        danDianLoginPubService.deleteAllDanDianLoginPub();
        //home弹框方法
        initView();
        set = (Button) findViewById(R.id.set_btn);
        //系统设置
        initSet();
        //树形菜单实现的方法
        initListView();

        //取得从specialactivity存储的Spec
        SharedPreferences spec = getSharedPreferences("speciallogin1", MainActivity.MODE_PRIVATE);
        String specialName = spec.getString("special", "");
        Log.i("specialName", "");
        if (specialName.equals("special")) {
            Log.i("进入if", "");
            //获取特殊登录存储的数据
            SharedPreferences SpecialsharedPreferences = this.getSharedPreferences("SPEC", MainActivity.MODE_PRIVATE);
            ssid = SpecialsharedPreferences.getString("KEY", "");
            Log.i("Specialssid", ssid);
            account = SpecialsharedPreferences.getString("ACCOUNT", "");
            Log.i("SPECIALACCOUNT", account);

        } else {
            //正常登录过来的
            SharedPreferences sharedPreferences = getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
            ssid = sharedPreferences.getString("KEY", "");
            Log.i("ssid", ssid + "");
            account = sharedPreferences.getString("ACCOUNT", "");
            Log.i("ACCOUNT", account + "");

        }
        //获取参数
        SharedPreferences sf = getSharedPreferences("APPID", MODE_PRIVATE);
        appidl = sf.getString("appId", "");
        Log.i("APPPPPID", appidl);

//        //调用方法
        getAppIDServer(account, "2");
        //初始化logo，头标
        mainlogo = (ImageView) this.findViewById(R.id.mianlogo);
        if (bitmapUtils == null) {
            bitmapUtils = BitmapHelper.getBitmapUtils();
            bitmapUtils.configDefaultLoadingImage(R.drawable.empty_photo); // 设置加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.empty_photo); // 设置加载错误图片
        }

        iv_scan = (ImageView) this.findViewById(R.id.iv_scan_main_activity);
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FlipShareView share = new FlipShareView.Builder(MainActivity.this, v)
                        .addItem(new ShareItem("扫一扫", Color.WHITE, BitmapFactory.decodeResource(getResources(), R.drawable.ic_scan)))
                        .addItem(new ShareItem("搜    索", Color.WHITE, BitmapFactory.decodeResource(getResources(), R.drawable.ic_search_blue_500_24dp)))
                        .setItemDuration(0)
                        .setBackgroundColor(0x40000000)
                        .create();


                share.setOnFlipClickListener(new FlipShareView.OnFlipClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(MainActivity.this, ScanActivity.class));
                                break;
                            case 1:
                                startActivity(new Intent(MainActivity.this, FirstSearchActivity.class));
                                break;
                        }
                    }

                    @Override
                    public void dismiss() {

                    }
                });

            }
        });


        //调用接口，展示公司logo图片
        Log.i("logolofo", account);
        CompanyLogoImpl companyLogoImpl = new CompanyLogoImpl(new MyHTTPResultListener(10));
        companyLogoImpl.getCompanyLogo(account);


        //第一级树形菜单
        firstMenuimpl = new FirstMenuImpl(new LoadTreeHttpListener(1));//封装AsyncHttpClient，产生的回调转接HttpResultListener中的空函数，在mainactivity中实现
        firstMenuimpl.getFirstMenu(account);
        Log.i("第一级菜单的account+++++++", account);
        //获取控件
        home = (ImageView) findViewById(R.id.home_icon);
        projectdynamic = (LinearLayout) findViewById(R.id.projectdynamic);
        daiban = (LinearLayout) findViewById(R.id.daiban);

        set.setOnClickListener(this);
        projectdynamic.setOnClickListener(this);
        daiban.setOnClickListener(this);
        //

        //点击项目动态展示左边的树形菜单，显示栏目信息
        projectdynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(drawer_left)) {
                    mDrawerLayout.closeDrawer(drawer_left);
                } else {
                    mDrawerLayout.openDrawer(drawer_left);
                }
            }
        });
        //栏目返回按钮事件
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("123", "yesyes-");
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
               mDrawerLayout.closeDrawer(drawer_left);

            }
        });
        //home键事件
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (visibility_Flag) {
                    close();
                    visibility_Flag = false;
                } else {
                    show();
                    visibility_Flag = true;
                }
            }
        });
        //设置的事件监听
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibility_Flag) {
                    closeset();
                    visibility_Flag = false;
                } else {
                    showset();
                    visibility_Flag = true;
                }
            }
        });

        //获取主界面下拉刷新的控件
        zrcListView = (ZrcListView) this.findViewById(R.id.mainlistview);
        //设置下拉刷新的特性
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView.setHeadable(header);


        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        zrcListView.setFootable(footer);


        zrcListView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        try {
            View headView = View.inflate(this, R.layout.slideshowview, null);
            zrcListView.addHeaderView(headView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageInfoService = ImageInfoService.getInstance(this);
        //下拉刷新
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                zrcListView.setRefreshSuccess("刷新成功"); // 通知加载成功
                zrcListView.startLoadMore();
            }
        });
        //上拉加载更多的监听
        zrcListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        mainNewsAdapter = new MainNewsAdapter(this, read_up);
        zrcListView.setAdapter(mainNewsAdapter);
        Log.i(TAG, "pageNo = " + pageNo);
        Image_View_first(16 + "", 1 + "");
        //更新
        update();
//        Image_View_next(16 + "",1 + "");

    }

    //上拉加载更多的回调
    private void loadMore() {
        loadItemThread = new Runnable() {
            @Override
            public void run() {
                pageNo = pageNo + 1;
                String page = String.valueOf(pageNo);
                List<String> Num = new ArrayList<String>();
                for (int i = 0; i < read_up.size(); i++) {
                    if (read_up.get(i).getPageno().equals(page)) {
                        Num.add(read_up.get(i).getPageno());
                    }
                }
                Log.i(TAG, "page = " + page);
                if (imageInfoService.queryImageinfotype_pageno(16 + "", page).size() > 0) {
                    for (int i = 0; i < read_up.size(); i++) {
                        if (read_up.get(i).getPageno().equals(page) && Num.size() > 0) {
                            break;
                        } else {
                            read_locallist = imageInfoService.queryImageinfotype_pageno(16 + "", page);
                            read_up.addAll(read_locallist);
                            MainNewsAdapter mainNewsAdapter = new MainNewsAdapter(MainActivity.this, read_up);
                            zrcListView.setAdapter(mainNewsAdapter);
                            break;
                        }
                    }
                    mainNewsAdapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    zrcListView.startLoadMore();
                    Item_click(read_up);
                } else {
                    ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new httpnext());
//                    imgInfoListImpl.getImgInfoList(ssid, account, 16 + "", page, "10", "2");
                    imgInfoListImpl.getImgInfoList(ssid, account, 1 + "", page, "20", "1");
                    zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                    zrcListView.startLoadMore();
                }
            }
        };

        handler.postDelayed(loadItemThread, 2000);
    }

    //显示主界面的数据
    private void Image_View_first(String infotype, String pageno) {
        if (imageInfoService.queryImageinfotype_pageno(infotype, pageno).size() == 0) {
            //主界面新闻列表
            ImgInfoListImpl imgInfoList = new ImgInfoListImpl(this);
            imgInfoList.getImgInfoList(ssid, account, "1", pageno, "20", "1");
            zrcListView.startLoadMore();
        } else {
            main_list_view1 = imageInfoService.queryImageinfotype_pageno(infotype, pageno);
            read_up.addAll(main_list_view1);
            mainNewsAdapter.notifyDataSetChanged();
            zrcListView.startLoadMore();
            Item_click(main_list_view1);
        }

    }

    //
    private void Image_View_next(String infotype, String pageno) {
        if (imageInfoService.queryImageinfotype_pageno(infotype, pageno).size() == 0) {
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(new httpnext2());
            imgInfoListImpl.getImgInfoList(ssid, account, infotype, pageno, "10", "2");
            zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
            zrcListView.startLoadMore();
        } else {
            list = imageInfoService.queryImageinfotype_pageno(infotype, pageno);
            for (int i = 0; i < list.size(); i++) {
                read_up.add(list.get(i));
            }

            MainNewsAdapter adapter = new MainNewsAdapter(MainActivity.this, list);
            zrcListView.setAdapter(adapter);

            Item_click(list);
            zrcListView.setRefreshSuccess("展示成功"); // 通知加载成功
            zrcListView.startLoadMore();
        }

    }

    private void Item_click(final List<ImageInfo> item_list) {
        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                KLog.e("00000000000");
                if (position-1>=0) {//放置数组越界
                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, SingleNewActivity1.class);
                intent.setClass(MainActivity.this, NewsDetailActivity.class);
                intent.putExtra("infoid", item_list.get(position - 1).getInfoid() + "");
                intent.putExtra("infotype", item_list.get(position - 1).getInfotype());
                Page = item_list.get(position - 1).getPageno();
                startActivityForResult(intent, 0);
                }else {
                    Toast.makeText(MainActivity.this, "没有数据可展示", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * 初始化home键弹框
     */
    private void initView() {
        //getSystemService()是Android很重要的一个API，它是Activity的一个方法，根据传入的NAME来取得对应的Object，然后转换成相应的服务对象
        inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);//LAYOUT_INFLATER_SERVICE:取得xml里定义的view

        contentView = inflater.inflate(R.layout.popviewpager, null);
        lists = new ArrayList<View>();


        popWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        //设置setOutsideTouchable首先要设置setBackgroundDrawable
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置不popupwindow的样式
        popWindow.setAnimationStyle(R.style.home_popwin_anim_style);

        //获取popupwindow里面的控件
        ll_line_indicator = (LinearLayout) contentView.findViewById(R.id.ll_line_indicator);
        cuesorView = (ImageView) contentView.findViewById(R.id.cursor);
        textView1 = (TextView) contentView.findViewById(R.id.textView1);
        textView2 = (TextView) contentView.findViewById(R.id.textView2);
        textView3 = (TextView) contentView.findViewById(R.id.textView3);

        view1 = inflater.inflate(R.layout.popup_gridview, null);
        view2 = inflater.inflate(R.layout.popup_gridview, null);
        view3 = inflater.inflate(R.layout.popup_gridview, null);
        lists.add(view1);
        lists.add(view2);
        lists.add(view3);

        //光标方法
        initeCursor();
//        ll_line_indicator = (LinearLayout) contentView.findViewById(R.id.ll_line_indicator);
//        calculateIndicateLineWidth();
        textView1.setTextColor(getResources().getColor(R.color.red));
        textView3.setTextColor(getResources().getColor(R.color.darkgray));
        textView2.setTextColor(getResources().getColor(R.color.darkgray));

        danDianLoginPubService = DanDianLoginPubService.getInstance(MainActivity.this);

        myAdapter = new MyAdapter(lists);

        viewPager = (ViewPager) contentView.findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) { // 当滑动式，顶部的cuesorView是通过animation缓慢的滑动
                switch (arg0) {
                    case 0:
                        Log.i("选中第一页", "yes");
                        if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth - (1 / 2) * offSet, 0, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(offSet * 4 + 2
                                    * bmWidth, 0, 0, 0);
                        }
                        textView1.setTextColor(getResources().getColor(R.color.apptheme_color));
                        textView3.setTextColor(getResources().getColor(R.color.black));
                        textView2.setTextColor(getResources().getColor(R.color.black));

                        if (danDianLoginPubService.queryDanDianLoginPubDaopage(1 + "").size() == 0) {
                            IconImpl iconImpl1 = new IconImpl(new MyHTTPResultListener(3));
                            iconImpl1.getIconInfo(ssid, "1", account);
                        } else {
                            read_first = danDianLoginPubService.queryDanDianLoginPubDaopage(1 + "");

                            filter(read_first);

                            GridView gridView1 = (GridView) view1.findViewById(R.id.popgrid);
                            List<Map<String, Object>> grid_view_data1 = ValueConfig.getGridListPopViewData(read_first);
                            PopViewAdapter popViewAdapter1 = new PopViewAdapter(view1.getContext(), grid_view_data1);
                            popViewAdapter1.setOnItemClick(new PopViewAdapter.OnItemClick() {
                                @Override
                                public void itemClick(View v, int i) {
                                    Log.i("mainactivity===position", i + "");
                                    DanDianLoginPub danDianLoginPub = read_first.get(i);
                                    String name = danDianLoginPub.getName();
                                    String status = danDianLoginPub.getStatus();
                                    Log.i("MainActivity", name);
                                    if (name.equals("项目信息")) {
                                        Intent intent = new Intent();
                                        //intent.setClass(MainActivity.this, ProjectStateActivity.class);
                                        intent.setClass(MainActivity.this, ConstructActivity.class);
//                                        intent.setClass(MainActivity.this, ProjectInfoActivity.class);
                                        startActivity(intent);
                                    } else if (status != null && !"0".equals(status)) {
                                        switch (name) {
                                            case "公文处理":
//                                                appID = "2";
                                                appID = appidl;
                                                name = "公文处理";
                                                break;
                                            case "通讯录":
                                                appID = "10";
                                                name = "通讯录";
                                                break;
                                            case "技术管理":
                                                appID = "12";
                                                break;
                                            case "车辆管理":
                                                appID = "32";
                                                break;
                                            case "会议管理":
                                                appID = "33";
                                                break;
                                            case "诚信体系":
                                                appID = "34";
                                                break;
                                            case "劳务工工资系统":
                                                appID = "74";
                                                break;
                                        }
                                        packname = appType2.get(name);
                                        getApk(view1.getContext(), packname, appID, name);
                                    }
                                }
                            });
                            gridView1.setAdapter(popViewAdapter1);
                        }

                        Log.i("account", account);
                        break;
                    case 1:
                        Log.i("选中第2页", "yes");
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, offSet * 2
                                    + bmWidth, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(2 * offSet + 2
                                    * bmWidth, offSet * 2 + bmWidth, 0, 0);
                        }
                        textView2.setTextColor(getResources().getColor(R.color.apptheme_color));
                        textView3.setTextColor(getResources().getColor(R.color.black));
                        textView1.setTextColor(getResources().getColor(R.color.black));

                        if (danDianLoginPubService.queryDanDianLoginPubDaopage(2 + "").size() == 0) {
                            IconImpl iconImpl2 = new IconImpl(new MyHTTPResultListener(4));
                            iconImpl2.getIconInfo(ssid, "2", account);
                        } else {
                            read_two = danDianLoginPubService.queryDanDianLoginPubDaopage(2 + "");
                            GridView gridView2 = (GridView) view2.findViewById(R.id.popgrid);
                            List<Map<String, Object>> grid_view_data2 = ValueConfig.getGridListRealViewData(read_two);
                            PopViewAdapter popViewAdapter2 = new PopViewAdapter(view1.getContext(), grid_view_data2);
                            popViewAdapter2.setOnItemClick(new PopViewAdapter.OnItemClick() {
                                @Override
                                public void itemClick(View v, int i) {
                                    Log.i("mainactivity===position", i + "");
                                    DanDianLoginPub danDianLoginPub = read_two.get(i);
                                    String name = danDianLoginPub.getName();
                                    String status = danDianLoginPub.getStatus();
                                    Log.i("MainActivity", name);
                                    if (status != null && !"0".equals(status)) {
                                        switch (name) {
                                            case "施工日志":
                                                appID = "3";
                                                name = "施工日志";
                                                break;
                                            case "在线培训":
                                                appID = "9";
                                                name = "在线培训";
                                                break;
                                            case "施工组织":
                                                appID = "13";
                                                break;
                                            case "施工图审核":
                                                appID = "39";
                                                break;
                                            case "调度指挥":
                                                appID = "53";
                                                break;
                                            case "征地拆迁":
                                                appID = "38";
                                                break;
                                        }
                                        packname = appType2.get(name);
                                        getApk(view2.getContext(), packname, appID, name);
                                    }
                                }
                            });
                            gridView2.setAdapter(popViewAdapter2);

                        }
                        break;
                    case 2:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, 4 * offSet + 2
                                    * bmWidth, 0, 0);
                        } else if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth,
                                    0, 0);
                        }
                        textView3.setTextColor(getResources().getColor(R.color.apptheme_color));
                        textView1.setTextColor(getResources().getColor(R.color.black));
                        textView2.setTextColor(getResources().getColor(R.color.black));

                        if (danDianLoginPubService.queryDanDianLoginPubDaopage(3 + "").size() == 0) {
                            IconImpl iconImpl3 = new IconImpl(new MyHTTPResultListener(5));
                            iconImpl3.getIconInfo(ssid, "3", account);
                        } else {
                            read_three = danDianLoginPubService.queryDanDianLoginPubDaopage(3 + "");
                            filter(read_three);
                            GridView gridView3 = (GridView) view3.findViewById(R.id.popgrid);
                            List<Map<String, Object>> grid_view_data3 = ValueConfig.getGridListXianViewData(read_three);
                            PopViewAdapter popViewAdapter3 = new PopViewAdapter(view1.getContext(), grid_view_data3);
                            popViewAdapter3.setOnItemClick(new PopViewAdapter.OnItemClick() {
                                @Override
                                public void itemClick(View v, int i) {
                                    Log.i("mainactivity===position", i + "");
                                    DanDianLoginPub danDianLoginPub = read_three.get(i);
                                    String name = danDianLoginPub.getName();
                                    String status = danDianLoginPub.getStatus();
                                    Log.i("MainActivity", name);
                                    if (status != null && !"0".equals(status)) {
                                        switch (name) {
                                            case "拌和站":
                                                appID = "5";
                                                name = "拌和站";
                                                break;
                                            case "试验室":
                                                name = "试验室";
                                                appID = "6";
                                                break;
                                            case "围岩量测":
                                                name = "隧道监控";
                                                appID = "4";
                                                break;
                                            case "沉降观测":
                                                name = "沉降观测";
                                                appID = "7";
                                                break;
                                            case "线形监测":
                                                name = "连续梁";
                                                appID = "8";
                                                break;
                                            case "桥梁静载":
                                                appID = "16";
                                                break;
                                            case "隧道形象化":
                                                appID = "17";
                                                break;
                                            case "检验批":
                                                appID = "35";
                                                break;
//
                                        }
                                        packname = appType2.get(name);
                                        getApk(view3.getContext(), packname, appID, name);
                                    }
                                }
                            });
                            gridView3.setAdapter(popViewAdapter3);

                        }
                        break;
                }
                currentItem = arg0;

                animation.setDuration(500);
                animation.setFillAfter(true);
                cuesorView.startAnimation(animation);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });


        textView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(0);

            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(1);

            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(2);

            }
        });
    }

    private int lineWidth;

    /**
     * 计算指针线宽度
     */
    public void calculateIndicateLineWidth() {
        /*int width = View.MeasureSpec.getMode(View.MeasureSpec.EXACTLY);
        int height = View.MeasureSpec.getMode(View.MeasureSpec.EXACTLY);
        Log.i("width",width+"");
        Log.i("height",height+"");
        ll_line_indicator.measure(width, height);*/
        //Log.i("ll_line_indicator",ll_line_indicator.getWidth()+"");

//        int screenWidth = ll_line_indicator.getLayoutParams().width;
//        Log.i("screenwidth",screenWidth+"");
        lineWidth = dm.widthPixels / lists.size();
        cuesorView.getLayoutParams().width = lineWidth;
        cuesorView.requestLayout();
    }


    //初始化光标
    private void initeCursor() {
        matrix = new Matrix();
        cursor = BitmapFactory
                .decodeResource(getResources(), R.drawable.img_cursor);
        bmWidth = cursor.getWidth();
        int lineWidth = ll_line_indicator.getWidth();

        dm = getResources().getDisplayMetrics();

        offSet = (dm.widthPixels - 3 * bmWidth) / 6;
        matrix.postTranslate(offSet, 0);
        cuesorView.setImageMatrix(matrix); // 需要iamgeView的scaleType为matrix
        currentItem = 0;
    }


    //启动APP
    public void startAppByAction(String actionname, String name) {
        try {
            if (actionname.equals(appType1.get(name))) {

                Log.i(TAG, "意图---" + actionname);
                Intent intent = new Intent();
                //判断是否为在线培训
                if (actionname.equals("elearning.a")) {
                    intent.putExtra("key_elearning_default_login", true);
                }
                intent.setAction(actionname);
                startActivity(intent);
            } else {

                Toast.makeText(MainActivity.this, "应用程序异常", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void startAPP(String pkg){
//        if (haveInstallApp(this, pkg)){
//            //已安装
//            Intent intent = this
//                    .getPackageManager().getLaunchIntentForPackage(
//                            pkg);
//            if (intent==null){
//                intent = new Intent(Intent.ACTION_MAIN);
//                if (pkg.equals("com.bjjw.sysbuild")){//处理图标被隐藏的情况
//                    intent.setComponent(new ComponentName(pkg,"com.bjjw.sysbuild.activity.MainActivity"));
//                }
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//            if(pkg.equals("com.luyou.training")){
//                intent.putExtra("key_elearning_default_login", true);
//                intent.setComponent(new ComponentName(pkg, "com.luyou.training.ui.WelcomeActivity"));
//            }
//            if(pkg.equals("bhz.awit.com.banhezhanemc")){
//                intent.addCategory("android.intent.category.DEFAULT");
//            }
//            startActivity(intent);
//        }else {
//            //未安装
//            Toast.makeText(this, "应用未安装", Toast.LENGTH_SHORT).show();
//        }
//    }

    //关闭设置popupwindow
    protected void closeset() {
        if (window == null) {
            showset();
        } else {
            window.dismiss();
        }
    }

    //展示设置popupwindow
    protected void showset() {
        if (window == null) {
            initSet();
        } else {
            //window.showAsDropDown(set, 0, UIUtils.getDimens(R.dimen.pulldown_y_offset));
            window.showAtLocation(set, Gravity.BOTTOM
                    | Gravity.RIGHT, 0, set.getHeight() + 120);
        }
    }

    //展示home popupwindow
    private void show() {
        if (popWindow == null) {
            initView();
        } else {
            //展示home里面的子APP图片
            if (danDianLoginPubService.queryDanDianLoginPubDaopage(1 + "").size() == 0) {
                IconImpl iconImpl = new IconImpl(new MyHTTPResultListener(3));
                iconImpl.getIconInfo(ssid, "1", account);
            } else {
                read_first1 = danDianLoginPubService.queryDanDianLoginPubDaopage(1 + "");
                filter(read_first1);

                GridView gridView1 = (GridView) view1.findViewById(R.id.popgrid);
                List<Map<String, Object>> grid_view_data1 = ValueConfig.getGridListPopViewData(read_first1);
                PopViewAdapter popViewAdapter1 = new PopViewAdapter(view1.getContext(), grid_view_data1);
                popViewAdapter1.setOnItemClick(new PopViewAdapter.OnItemClick() {
                    @Override
                    public void itemClick(View v, int i) {
                        Log.i("mainactivity===position", i + "");
                        DanDianLoginPub danDianLoginPub = read_first1.get(i);
                        String name = danDianLoginPub.getName();
                        String status = danDianLoginPub.getStatus();
                        Log.i("MainActivity", name);
                        if (name.equals("项目信息")) {
                            Intent intent = new Intent();
                            //intent.setClass(MainActivity.this, ProjectStateActivity.class);
                            intent.setClass(MainActivity.this, ConstructActivity.class);
//                            intent.setClass(MainActivity.this, ProjectInfoActivity.class);
                            startActivity(intent);
                        } else if (status != null && !"0".equals(status)) {
                            switch (name) {
                                case "公文处理":
//                                    appID = "2";
                                    appID = appidl;
                                    name = "公文处理";
                                    break;
                                case "通讯录":
                                    appID = "10";
                                    name = "通讯录";
                                    break;
                                case "技术管理":
                                    appID = "12";
                                    break;
                                case "车辆管理":
                                    appID = "32";
                                    break;
                                case "会议管理":
                                    appID = "33";
                                    break;
                                case "诚信体系":
                                    appID = "34";
                                    break;
                                case "劳务工工资系统":
                                    appID = "74";
                                    break;
                            }
                            packname = appType2.get(name);
                            getApk(view1.getContext(), packname, appID, name);
                        }
                    }
                });
                gridView1.setAdapter(popViewAdapter1);
            }

            popWindow.showAtLocation(contentView, Gravity.BOTTOM
                    | Gravity.CENTER_HORIZONTAL, 0, home.getHeight());
        }
    }

    //关闭home popupwindow
    private void close() {
        if (popWindow == null) {
            show();
        } else {
            if (viewPager != null) {
                viewPager.setCurrentItem(0);
            }
            popWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
//            case R.id.prodetail:
////                intent = new Intent(MainActivity.this,
////                        ProjectTotalActivity.class);
////                startActivity(intent);
//                intent = new Intent(MainActivity.this,
//                        ProjectTotalActivity2.class);
//                startActivity(intent);
//                break;

//            case R.id.myfoucs://建设风采---重要讲话
//                intent = new Intent(MainActivity.this, ImportantSpeakingActivity.class);
//                startActivity(intent);
//                break;
            case R.id.daiban://待办任务  祝鹏辉 设置
//                intent = new Intent(MainActivity.this, DaiBanTaskActivity.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, "功能正在完善中",
//                        Toast.LENGTH_SHORT).show();
                if (visibility_Flag) {
                    closeset();
                    visibility_Flag = false;
                } else {
                    showset();
                    visibility_Flag = true;
                }
                break;
//
//            case R.id.tree://项目动态-参建单位
////                intent = new Intent(MainActivity.this, ConstructActivity.class);
////                startActivity(intent);
////                intent = new Intent(MainActivity.this, ProjectStateActivity.class);
////                startActivity(intent);
//                openOptionsMenu();

            //break;

//            case R.id.create://创先争优
//                intent = new Intent(MainActivity.this,DangWorkingActivity.class);
////                Toast.makeText(MainActivity.this, "功能正在完善中",
////                        Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//                break;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        dismissPopupWindow();
    }


    //判断PopupWindow是不是存在，存在就把它dismiss掉
    private void dismissPopupWindow() {

        if (popWindow != null) {
            popWindow.dismiss();
            popWindow = null;
        }
    }

    //    private int count = 0;
//    private long startTime = 0;
    private long endtTime = 0;

    //
    private void showTips() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("提醒")
                .setMessage("是否退出程序")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                }).setNegativeButton("取消",

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent inte = getIntent();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
//            if (popWindow != null) {
//                popWindow.dismiss();
//            }
//            if (popWindow == null) {
//                if ((System.currentTimeMillis() - endtTime) > 2000) {
//                    Toast.makeText(getApplicationContext(), "再按一次退出",
//                            Toast.LENGTH_SHORT).show();
//                    endtTime = System.currentTimeMillis();
//                } else {
//                    finish();
//                    System.exit(0);
//                }
//            } else if (setWindow == null) {
//                if ((System.currentTimeMillis() - endtTime) > 2000) {
//                    Toast.makeText(getApplicationContext(), "再按一次退出",
//                            Toast.LENGTH_SHORT).show();
//                    endtTime = System.currentTimeMillis();
//                } else {
//                    finish();
//                    System.exit(0);
//                }
//            } else if (popWindow.isShowing()) {
//                popWindow.dismiss();
//            } else if (setWindow.isShowing()) {
//                setWindow.dismiss();
//            } else {
//                if ((System.currentTimeMillis() - endtTime) > 2000) {
//                    Toast.makeText(getApplicationContext(), "再按一次退出",
//                            Toast.LENGTH_SHORT).show();
//                    endtTime = System.currentTimeMillis();
//                } else {
//                    finish();
//                    System.exit(0);
//                }
//            }
//            return true;
            showTips();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
//退出之后销毁activity

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class httpnext2 implements HttpResultListener {

        @Override
        public void onStartRequest() {

        }

        @Override
        public void onResult(Object obj) {
            if (obj instanceof FailRequest) {
                FailRequest fail = (FailRequest) obj;
                if (!(fail == null)) {
                    System.out.println("异常信息：" + fail.getMsg());
                    System.out.println("状态：" + fail.getStatus());
                }
            } else {
                main_list_view1 = (List<ImageInfo>) obj;
                if (main_list_view1.size() > 0) {
                    imageInfoService.saveImageInfoLists(main_list_view1);
                    for (int i = 0; i < main_list_view1.size(); i++) {
                        read_up.add(main_list_view1.get(i));
                    }
                    Collections.sort(read_up, new Comparator<ImageInfo>() {
                        @Override
                        public int compare(ImageInfo lhs, ImageInfo rhs) {
                            return lhs.getPageno().compareTo(rhs.getPageno());
                        }
                    });
//                    MainNewsAdapter adapter = new MainNewsAdapter(MainActivity.this,main_list_view1);
//                    zrcListView.setAdapter(adapter);
                    mainNewsAdapter.notifyDataSetChanged();
                    zrcListView.setRefreshSuccess();
                    Item_click(read_up);
                } else {
//                    reads = imageInfoService.queryImageinfotype_pageno(16 + "", 1 + "", "", "", "", "", "", "", "", "", "", "");
                    reads = imageInfoService.queryImageinfotype_pageno(16 + "", 1 + "");
                    MainNewsAdapter adapter = new MainNewsAdapter(MainActivity.this, reads);
                    zrcListView.setAdapter(adapter);
                    Item_click(reads);
                }
            }
        }

        @Override
        public void onFinish() {
        }
    }

    //上拉加载的回调方法
    class httpnext implements HttpResultListener {

        @Override
        public void onStartRequest() {
        }

        @Override
        public void onResult(Object obj) {
            if (obj instanceof FailRequest) {
                FailRequest fail = (FailRequest) obj;
                if (!(fail == null)) {
                    System.out.println("异常信息：" + fail.getMsg());
                    System.out.println("状态：" + fail.getStatus());
                }
            } else {
                main_list_view1 = (List<ImageInfo>) obj;
                if (main_list_view1.size() > 0) {
                    imageInfoService.saveImageInfoLists(main_list_view1);
                    for (int i = 0; i < main_list_view1.size(); i++) {
                        read_up.add(main_list_view1.get(i));
                    }
                    mainNewsAdapter.notifyDataSetChanged();
                    zrcListView.setLoadMoreSuccess();
                    Item_click(read_up);
                } else {
                    Toast.makeText(MainActivity.this, "数据加载完成", Toast.LENGTH_LONG).show();
                    zrcListView.stopLoadMore();
                }
            }
        }

        @Override
        public void onFinish() {

        }
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onResult(Object obj) {
        if (obj instanceof FailRequest) {
            FailRequest fail = (FailRequest) obj;
            if (!(fail == null)) {
                System.out.println("异常信息：" + fail.getMsg());
                System.out.println("状态：" + fail.getStatus());
            }
        } else {
            main_list_view = (List<ImageInfo>) obj;
            if (main_list_view.size() > 0) {
                imageInfoService.saveImageInfoLists(main_list_view);
                for (int i = 0; i < main_list_view.size(); i++) {
                    read_up.add(main_list_view.get(i));
                }
                Collections.sort(read_up, new Comparator<ImageInfo>() {
                    @Override
                    public int compare(ImageInfo lhs, ImageInfo rhs) {
                        return lhs.getPageno().compareTo(rhs.getPageno());
                    }
                });
//                MainNewsAdapter mainNewsAdapter1 = new MainNewsAdapter(MainActivity.this,read_up);
//                zrcListView.setAdapter(mainNewsAdapter1);
                mainNewsAdapter.notifyDataSetChanged();
                zrcListView.setRefreshSuccess("刷新成功");
                Item_click(read_up);
            } else {
                ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
//                imgInfoListImpl.getImgInfoList(ssid,account, "16",
//                        pageNo>1?String.valueOf(pageNo-1):String.valueOf(pageNo), "10", "1");
                imgInfoListImpl.getImgInfoList(ssid, account, "1",
                        pageNo > 1 ? String.valueOf(pageNo - 1) : String.valueOf(pageNo), "20", "1");
            }
        }
    }

    @Override
    public void onFinish() {

    }

    //主界面的新闻条目点击事件
    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        Log.i("position", position + "");
        KLog.e("1111111111111");
        Intent intent = new Intent();
//        intent.setClass(MainActivity.this, SingleActivity.class);
//        intent.setClass(MainActivity.this, SingleNewActivity1.class);
        intent.setClass(MainActivity.this, NewsDetailActivity.class);
        intent.putExtra("infoid", main_list_view.get(position - 1).getInfoid() + "");
        intent.putExtra("infotype", main_list_view.get(position - 1).getInfotype());
        Page = main_list_view.get(position - 1).getPageno();
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1&&requestCode==1)
        {
            finish();
        }
        List<ImageInfo> one = new ArrayList<>();
        for (int i = 0; i < read_up.size(); i++) {
            if (read_up.get(i).getPageno().equals(Page)) {
                one.add(read_up.get(i));
            }
        }
        for (ImageInfo tt : one) {
            if (read_up.contains(tt)) {
                read_up.remove(tt);
            }
        }
        //刷新一次
        mainNewsAdapter.notifyDataSetChanged();
        //浏览量
        if (Page != null) {
        if (Page.equals("1")) {
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
//            imgInfoListImpl.getImgInfoList(ssid, account, "16", Page, "10", "1");
            imgInfoListImpl.getImgInfoList(ssid, account, "1", Page, "20", "1");
        } else {
            ImgInfoListImpl imgInfoListImpl = new ImgInfoListImpl(this);
//            imgInfoListImpl.getImgInfoList(ssid, account, "16", Page, "10", "2");
            imgInfoListImpl.getImgInfoList(ssid, account, "1", Page, "20", "2");
        }
        }
    }

    //初始化系统设置按钮
    public void initSet() {
        final List<String> items = getPopupMenuData();
        window = new MyPopupWindow(MainActivity.this, POPUP_BASE_WIDTH, 100 + POPUP_BASE_HEIGHT * items.size(),//自定义弹出窗口的大小  包含的图标和标题对应
                items, getSettingIcons());    //祝鹏辉：要注意items和getSettingIcons()的长度保持一致，图片对应文字
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                AnimationUtils.startRotateAnimation(iv_pulldown, 135, 0);
            }
        });
        window.setOnItemClickListener(new PopWindowAdapter.onItemClickListener() {

            @Override
            public void click(int position, View view) {
                Intent intent = new Intent();
                builder = new AlertDialog.Builder(MainActivity.this);
                switch (position) {
                    //用户注销
                    case 0:
                        builder.setTitle("注销");
                        builder.setMessage("确认注销吗 ");
                        builder.setPositiveButton("确认",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        SharedPreferences loginshare = getSharedPreferences("SAVE_LOGIN", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = loginshare.edit();
                                        String account = loginshare.getString("account", "");
                                        String pwd = loginshare.getString("password", "");
                                        String imei = loginshare.getString("saveimei", "");
                                        editor.remove(account);
                                        editor.remove(pwd);
                                        editor.remove(imei);
                                        editor.commit();
                                        LoginActivity1.autologin.setChecked(false);//取消自动登录checkbox的true状态
//                                        LoginActivity1.choseAutoLogin = false;

                                        UserLogOutImpl userLogOutImpl = new UserLogOutImpl(new MyHTTPResultListener(1));
                                        userLogOutImpl.loginOut(ssid);//post登出
                                        finish();
                                    }
                                });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.cancel();
                                    }
                                });
                        builder.create().show();
                        break;
//                    //修改密码
//                    case 1:
//                        DialogUtils.alertEditDialog(MainActivity.this, "修改密码", "请输入密码", new DialogUtils.DialogEditCallBack() {
//                            @Override
//                            public void positive(MyDialog dialog, String oldpwd, String newpwd, String surepwd) {
//                                AlterPwdImpl alterPwdImpl = new AlterPwdImpl(new MyHTTPResultListener(6));
//                                alterPwdImpl.getAlterPwdInfo(ssid, account, oldpwd, newpwd);
//                            }
//
//                            @Override
//                            public void negative(MyDialog dialog, View v) {
//                                dialog.dismiss();
//                            }
//                        });
//                        break;
                    //应用更新
                    case 1:
                        update();
                        break;
                    //用户反馈
                    case 2:
                        Intent useradvice = new Intent();
                        useradvice.setClass(MainActivity.this, UserAdvicesActivity.class);
                        startActivity(useradvice);
                        break;
                    //关注平台二维码展示
                    case 3:
                        intent.setClass(MainActivity.this, ErWeiMaActivity.class);
                        startActivity(intent);
                        break;
                    //关于我们
                    case 4:
                        intent.setClass(MainActivity.this, AboutUsActivity.class);
                        startActivity(intent);
                        break;
                    //imei解绑
                    case 5:
                        builder.setTitle("解除绑定");
                        builder.setMessage("确认解除绑定吗 ");
                        builder.setPositiveButton("确认",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        SharedPreferences loginshare = getSharedPreferences("SAVE_LOGIN", MODE_PRIVATE);
                                        String account = loginshare.getString("account", "");
                                        String pwd = loginshare.getString("password", "");
                                        String imei = loginshare.getString("saveimei", "");
                                        Log.i("account", account);
                                        Log.i("password", pwd);
                                        Log.i("imei", imei);
                                        UnBdingImpl unBdingImpl = new UnBdingImpl(new MyHTTPResultListener(8));
                                        unBdingImpl.unBding(account, pwd, imei);
                                        finish();
                                    }
                                });
                        builder.setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.cancel();
                                    }
                                });
                        builder.create().show();
                        break;
                    case 6:
                        Intent intent1 = new Intent(MainActivity.this, RelateaAccountDialogActivity.class);
                        intent1.putExtras(bundle);
                        startActivityForResult(intent1,1);
                        break;
                }
            }
        });
    }

    //第一级树形菜单请求数据回掉
    class LoadTreeHttpListener implements HttpResultListener {
        private int index;

        public LoadTreeHttpListener(int index) {
            this.index = index;
        }

        @Override
        public void onStartRequest() {
            //startProgressDialog("正在加载数据。。。");
        }

        @Override
        public void onResult(Object obj) {
            Log.i("aaaaaa", "已经进入第一级");
            if (obj instanceof FailRequest) {
                FailRequest fail = (FailRequest) obj;
                if (!(fail == null)) {
                    System.out.println("异常信息：" + fail.getMsg());
                    System.out.println("状态：" + fail.getStatus());
                    if (!(fail.getStatus().equals("0"))) {
                        Toast.makeText(MainActivity.this, "接口异常", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i("失败消息：", fail.getMsg());
                    Toast.makeText(MainActivity.this, fail.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                switch (index) {
                    case 0:
                        break;
                    case 1:

                        //获取第一级的数据
                        final List<FirstMenuInfo> firstMenuInfoList = (List<FirstMenuInfo>) obj;
                        for (int i = 0; i < firstMenuInfoList.size(); i++) {
                            FirstMenuInfo firstMenuInfo = firstMenuInfoList.get(i);
                            KLog.e("公司", firstMenuInfo.getId());
                            String id = firstMenuInfo.getId();

                            String name = firstMenuInfo.getName();
                            company.add(name);
                            String parentCode = firstMenuInfo.getCode();
                            //添加第一级菜单到左边的树形菜单里面
                            //第一个null就是父级菜单的code
                            TreeElement parentTree = new TreeElement("road", id, name, firstMenuInfoList.get(i).getCode(),
                                    null, firstMenuInfoList.get(i).getName(), false, null);

                            //对一级菜单进行判断
                            if (firstMenuInfoList.get(i).getName().equals("总经理会议纪要")) {
                                Log.i("parentcode", firstMenuInfoList.get(i).getCode());
                            }
                            mPdfOutlinesCount.add(parentTree);
                        }

                        //TODO  对公司ID进行判断，显示每日简报 listview 的第一行
                        Intent intent = getIntent();
                        Log.i(TAG, "compid ==" + intent.getStringExtra("compid"));
                        compid = intent.getStringExtra("compid");
                        if (compid.equals("279")) {
                            TreeElement element = new TreeElement("每日简报", "279", "每日简报", "", null, "", false, null);
                            mPdfOutlinesCount.add(0, element);
                        }
                        listviewdrawer.setOnItemClickListener(MainActivity.this);
                        listviewdrawer.setAdapter(treeAdapter);
                        treeAdapter.notifyDataSetChanged();
                        //调用第二级菜单
                        startLoadingSecondMenu();
                        break;
                }

            }


        }

        @Override
        public void onFinish() {
            stopProgressDialog();
        }
    }


    /**
     * 开始调用第二级菜单
     */
    private void startLoadingSecondMenu() {
        //加载第二级菜单的数据
        showProDialog();
        //对第一级菜单下的第二级菜单的数据进行遍历
        for (TreeElement treeElement : mPdfOutlinesCount) {
            //调用第二级菜单的接口
            SecondMenuImpl secondMenu = new SecondMenuImpl(new SecondMenuResponse(treeElement));
            secondMenu.getSecondMenu(treeElement.getId());

        }
        pdDialog.dismiss();
        treeAdapter.notifyDataSetChanged();
    }

    //二级菜单获取数据完成的回调
    private class SecondMenuResponse implements HttpResultListener {
        private TreeElement parent;

        public SecondMenuResponse(TreeElement parent) {
            this.parent = parent;
        }

        @Override
        public void onStartRequest() {

        }

        @Override
        public void onResult(Object obj) {
            if (obj instanceof FailRequest) {
                FailRequest fail = (FailRequest) obj;
                if (!(fail == null)) {
                    System.out.println("异常信息：" + fail.getMsg());
                    System.out.println("状态：" + fail.getStatus());
                    if (fail.getStatus().equals("1")) {
                        Toast.makeText(MainActivity.this, "接口异常", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i("失败消息：", fail.getMsg());
                    Toast.makeText(MainActivity.this, fail.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                //获取第二级菜单并将其添加到第一级的下面
                final List<SecondMenuInfo> secondMenuInfoList = (List<SecondMenuInfo>) obj;
                for (int j = secondMenuInfoList.size() - 1; j >= 0; j--) {
                    TreeElement child = new TreeElement("childs", secondMenuInfoList.get(j).getPid()
                            , secondMenuInfoList.get(j).getName(), parent.getParentCode(), secondMenuInfoList.get(j).getCode()
                            , secondMenuInfoList.get(j).getName(), false, myOnClickListener);
                    //当子菜单的pid和父级菜单相同的时候输出子菜单的信息
                    if (secondMenuInfoList.get(j).getPid().equals("3") && secondMenuInfoList.get(j).getName().equals("总经理办公会议纪要")) {
                        Log.i("childcode", secondMenuInfoList.get(j).getCode());
                        Log.i("childname", secondMenuInfoList.get(j).getName());
                    }
                    parent.addChild(child);
                    listviewdrawer.setOnItemClickListener(MainActivity.this);
                    listviewdrawer.setAdapter(treeAdapter);
                    treeAdapter.notifyDataSetChanged();
                }
            }
        }


        @Override
        public void onFinish() {
        }

    }

    //左侧滑菜单
    private void initListView() {
        //得到左侧滑菜单所需要的控件
        drawer_left = (LinearLayout) findViewById(R.id.drawer_left);
        listviewdrawer = (ListView) findViewById(R.id.navdrawer);
        treeAdapter = new TreeAdapter(this, R.layout.outline, mPdfOutlinesCount);
        listviewdrawer.setAdapter(treeAdapter);
        listviewdrawer.setOnItemClickListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TreeElement treeElement = mPdfOutlinesCount.get(position);
        //没有子菜单的时候
        if (!treeElement.isMhasChild()) {
            if (position == 0 && compid.equals("279")) {
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                startActivity(intent);
                return;
            }
            TextView textView = (TextView) view.findViewById(R.id.text);
            String str = textView.getText().toString();
            treeElement.setExpanded(true);
            //携带标题并进行跳转
            Intent intent = new Intent(this, IntentActivity.class);
            intent.putExtra("parentCode", mPdfOutlinesCount.get(position).getParentCode());
            intent.putExtra("childcode", mPdfOutlinesCount.get(position).getCode());

            Bundle bundle_title1 = new Bundle();
            Log.i("zzzzz", "title is = " + treeElement.getOutlineTitle());
            bundle_title1.putString("title1", treeElement.getOutlineTitle());

            intent.putExtras(bundle_title1);

            startActivity(intent);

            int level = treeElement.getLevel();
            int nextLevel = level + 1;

            for (TreeElement element : treeElement.getChildList()) {
                element.setLevel(nextLevel);
                element.setExpanded(false);
                mPdfOutlinesCount.add(position + 1, element);
            }
            mDrawerLayout.closeDrawer(drawer_left);
        }
        treeAdapter.notifyDataSetChanged();

        if (treeElement.isExpanded()) {
            treeElement.setExpanded(false);
            ArrayList<TreeElement> temp = new ArrayList<TreeElement>();

            for (int i = position + 1; i < mPdfOutlinesCount.size(); i++) {

                if (treeElement.getLevel() >= mPdfOutlinesCount.get(i).getLevel()) {

                    IntentFragment.isFirst = true;
                    break;
                }
                temp.add(mPdfOutlinesCount.get(i));
            }

            mPdfOutlinesCount.removeAll(temp);

            treeAdapter.notifyDataSetChanged();

        } else {
            TreeElement obj = mPdfOutlinesCount.get(position);
            obj.setExpanded(true);
            int level = obj.getLevel();
            int nextLevel = level + 1;


            for (TreeElement element : obj.getChildList()) {
                element.setLevel(nextLevel);
                element.setExpanded(false);
                mPdfOutlinesCount.add(position + 1, element);
            }
        }
    }


    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "--" + v.getTag(),
                    Toast.LENGTH_LONG).show();

        }
    };


    // @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    /**
     * 初始化titleBar下拉菜单条目数据
     */
    protected List<String> getPopupMenuData() {
        List<String> items = new ArrayList<String>();
        items.add("退出登录");
        // items.add("修改密码");
        items.add("检查更新");
        items.add("用户反馈");
        items.add("关注平台");
        items.add("关于我们");
        items.add("解除绑定");
        items.add("切换账号");
        return items;
    }

    /**
     * 设置下拉框的icon
     *
     * @return
     */
    protected List<Integer> getSettingIcons() {
        List<Integer> items = new ArrayList<Integer>();
        items.add(R.drawable.setting_sub1);
        // items.add(R.drawable.setting_sub2);
        items.add(R.drawable.setting_sub3);
        items.add(R.drawable.setting_sub4);
        items.add(R.drawable.setting_sub5);
        items.add(R.drawable.setting_sub6);
        items.add(R.drawable.setting_sub7);
        items.add(R.drawable.setting_sub2);
        return items;
    }

    //回调方法请求
    class MyHTTPResultListener implements HttpResultListener {

        private int index;

        public MyHTTPResultListener(int index) {
            this.index = index;
        }

        @Override
        public void onStartRequest() {

        }

        @Override
        public void onResult(Object obj) {
            if (obj instanceof FailRequest) {
                FailRequest fail = (FailRequest) obj;
                if (!(fail == null)) {
                    System.out.println("异常信息：" + fail.getMsg());
                    System.out.println("状态：" + fail.getStatus());
                    if (fail.getStatus().equals("1")) {
//                        Toast.makeText(MainActivity.this,"接口异常",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.i("失败消息：", fail.getMsg());
                    Toast.makeText(MainActivity.this, fail.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        Logout logout = (Logout) obj;
                        if (logout.getStatus() == 0) {
                            Toast.makeText(MainActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainActivity.this,LoginActivity1.class);
//                            intent.putExtra("zidong",true);
//                            startActivity(intent);
//                            SharedPreferences sharedPreferences= getSharedPreferences("SAVE_ZIDONG",
//                                    Activity.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putBoolean("zidong",true);
//                            editor.commit();
//                            stopProgressDialog();
                            LoginActivity1.autologin.setChecked(false);
                            LoginActivity1.choseAutoLogin = false;
                            stopProgressDialog();
                            pdDialog.cancel();
                            finish();
                        }
                        break;
                    case 2:
                        brighticon = new ArrayList<DanDianLoginPub>();
                        grayicon = new ArrayList<DanDianLoginPub>();
                        List<IconInfo> iconInfoList3 = (List<IconInfo>) obj;
                        danDianLoginPubList1 = new ArrayList<DanDianLoginPub>();
                        for (int i = 0; i < iconInfoList3.size(); i++) {
                            DanDianLoginPub danDianLoginPub = new DanDianLoginPub();
                            id = iconInfoList3.get(i).getId();
                            status = iconInfoList3.get(i).getStatus();
                            name = iconInfoList3.get(i).getName();
                            if (name.equals("")) {
                                Log.i("测试进不进来：", "yesyes");
//                                Toast.makeText(view1.getContext(),"接口异常",Toast.LENGTH_SHORT);
                            } else {
                                if (!status.equals("0")) {
                                    if (name.equals("公文处理")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("项目信息")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("技术管理")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("诚信体系")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("劳务工工资系统")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus("0");
                                        grayicon.add(danDianLoginPub);
                                    }
                                } else {
                                    danDianLoginPub.setName(name);
                                    danDianLoginPub.setStatus(status);
                                    Log.i("dark", name);
                                    grayicon.add(danDianLoginPub);
                                }
                            }

                        }
                        DanDianLoginPub danDianLoginPub1 = new DanDianLoginPub();
                        danDianLoginPub1.setName("通讯录");
                        danDianLoginPub1.setStatus("1");
                        DanDianLoginPub danDianLoginPub2 = new DanDianLoginPub();
                        danDianLoginPub2.setName("会议管理");
                        danDianLoginPub2.setStatus("1");
                        DanDianLoginPub danDianLoginPub3 = new DanDianLoginPub();
                        danDianLoginPub3.setName("车辆管理");
                        danDianLoginPub3.setStatus("1");
                        brighticon.add(danDianLoginPub1);
                        brighticon.add(danDianLoginPub2);
                        brighticon.add(danDianLoginPub3);
                        danDianLoginPubList1.addAll(brighticon);
                        danDianLoginPubList1.addAll(grayicon);

                        GridView gridView = (GridView) view1.findViewById(R.id.popgrid);

                        List<Map<String, Object>> grid_view_data = ValueConfig.getGridListPopViewData(danDianLoginPubList1);
                        PopViewAdapter popViewAdapter = new PopViewAdapter(view1.getContext(), grid_view_data);
                        popViewAdapter.setOnItemClick(new PopViewAdapter.OnItemClick() {
                            @Override
                            public void itemClick(View v, int i) {
                                Log.i("MainActivity", "这是MainActivity");
                                DanDianLoginPub danDianLoginPub = danDianLoginPubList1.get(i);
                                String name = danDianLoginPub.getName();
                                String status = danDianLoginPub.getStatus();
                                if (name.equals("项目信息")) {
                                    Intent intent = new Intent();
                                    //intent.setClass(MainActivity.this,ProjectStateActivity.class);
                                    intent.setClass(MainActivity.this, ConstructActivity.class);
//                                    intent.setClass(MainActivity.this,ProjectInfoActivity.class);
                                    startActivity(intent);
                                } else if (status != null && !"0".equals(status)) {
                                    switch (name) {
                                        case "公文处理":
//                                            appID = "2";
                                            appID = appidl;
                                            name = "公文处理";
                                            break;
                                        case "通讯录":
                                            appID = "10";
                                            name = "通讯录";
                                            break;
                                        case "技术管理":
                                            appID = "12";
                                            break;
                                        case "车辆管理":
                                            appID = "32";
                                            break;
                                        case "会议管理":
                                            appID = "33";
                                            break;
                                        case "诚信体系":
                                            appID = "34";
                                            break;
                                        case "劳务工工资系统":
                                            appID = "74";
                                            break;
                                    }
                                    packname = appType2.get(name);
                                    getApk(view1.getContext(), packname, appID, name);
                                }
                            }
                        });
                        gridView.setAdapter(popViewAdapter);
                        break;
                    case 3:
                        brighticon = new ArrayList<DanDianLoginPub>();//点亮的集合
                        grayicon = new ArrayList<DanDianLoginPub>();//暗的集合
                        danDianLoginPubList2 = new ArrayList<DanDianLoginPub>();//总集合
                        List<IconInfo> iconInfoList = (List<IconInfo>) obj;
                        for (int i = 0; i < iconInfoList.size(); i++) {
                            DanDianLoginPub danDianLoginPub = new DanDianLoginPub();
                            id = iconInfoList.get(i).getId();
                            name = iconInfoList.get(i).getName();
                            status = iconInfoList.get(i).getStatus();
                            if (name.equals("")) {
                                Log.i("测试进不进来：", "yesyes");
//                                Toast.makeText(view1.getContext(),"接口异常",Toast.LENGTH_SHORT);
                            } else {
                                if (!status.equals("0")) {
                                    if (name.equals("公文处理")) {
//                                        Log.i("公文处理数量传进去：",oanum);
                                        danDianLoginPub.setNum(oanum);
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("项目信息")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("技术管理")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("诚信体系")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("劳务工工资系统")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus("0");
                                        grayicon.add(danDianLoginPub);
                                    }
                                } else {
                                    danDianLoginPub.setName(name);
                                    danDianLoginPub.setStatus(status);
                                    grayicon.add(danDianLoginPub);
                                    Log.i("dark2", name);
                                }
                            }
                        }
                        DanDianLoginPub danDianLoginPub4 = new DanDianLoginPub();
                        danDianLoginPub4.setName("通讯录");
                        danDianLoginPub4.setStatus("1");
                        DanDianLoginPub danDianLoginPub5 = new DanDianLoginPub();
                        danDianLoginPub5.setName("会议管理");
                        danDianLoginPub5.setStatus("1");
                        DanDianLoginPub danDianLoginPub6 = new DanDianLoginPub();
                        danDianLoginPub6.setName("车辆管理");
                        danDianLoginPub6.setStatus("1");
                        brighticon.add(danDianLoginPub4);
                        brighticon.add(danDianLoginPub5);
                        brighticon.add(danDianLoginPub6);
                        danDianLoginPubList2.addAll(brighticon);
                        danDianLoginPubList2.addAll(grayicon);

                        GridView gridView1 = (GridView) view1.findViewById(R.id.popgrid);

                        filter(danDianLoginPubList2);

                        List<Map<String, Object>> grid_view_data1 = ValueConfig.getGridListPopViewData(danDianLoginPubList2);
                        PopViewAdapter popViewAdapter1 = new PopViewAdapter(view1.getContext(), grid_view_data1);
                        popViewAdapter1.setOnItemClick(new PopViewAdapter.OnItemClick() {
                            @Override
                            public void itemClick(View v, int i) {
                                Log.i("mainactivity===position", i + "");
                                DanDianLoginPub danDianLoginPub = danDianLoginPubList2.get(i);
                                String name = danDianLoginPub.getName();
                                String status = danDianLoginPub.getStatus();
                                Log.i("MainActivity", name);
                                if (name.equals("项目信息")) {
                                    Intent intent = new Intent();
                                    //  intent.setClass(MainActivity.this, ProjectStateActivity.class);
                                    intent.setClass(MainActivity.this, ConstructActivity.class);
//                                    intent.setClass(MainActivity.this,ProjectInfoActivity.class);
                                    startActivity(intent);
                                } else if (status != null && !"0".equals(status)) {
                                    switch (name) {
                                        case "公文处理":
//                                            appID = "2";
                                            appID = appidl;
                                            name = "公文处理";
                                            break;
                                        case "通讯录":
                                            appID = "10";
                                            name = "通讯录";
                                            break;
                                        case "技术管理":
                                            appID = "12";
                                            break;
                                        case "车辆管理":
                                            appID = "32";
                                            break;
                                        case "会议管理":
                                            appID = "33";
                                            break;
                                        case "诚信体系":
                                            appID = "34";
                                            break;
                                        case "劳务工工资系统":
                                            appID = "74";
                                            break;
                                    }
                                    packname = appType2.get(name);
                                    getApk(view1.getContext(), packname, appID, name);
                                }
                            }
                        });

                        danDianLoginPubService.saveDanDianLoginPubLists(danDianLoginPubList2);
                        for (int j = 0; j < danDianLoginPubList2.size(); j++) {
                            DanDianLoginPub siteInfo = danDianLoginPubList2.get(j);
                            siteInfo.setPageno("1");
                            danDianLoginPubService.saveDanDianLoginPubDao(siteInfo);
                        }
                        gridView1.setAdapter(popViewAdapter1);
                        break;
                    case 4:
                        brighticon = new ArrayList<DanDianLoginPub>();
                        grayicon = new ArrayList<DanDianLoginPub>();
                        danDianLoginPubList3 = new ArrayList<DanDianLoginPub>();
                        List<IconInfo> iconInfoList2 = (List<IconInfo>) obj;
                        for (int i = 0; i < iconInfoList2.size(); i++) {
                            DanDianLoginPub danDianLoginPub = new DanDianLoginPub();
                            id = iconInfoList2.get(i).getId();
                            status = iconInfoList2.get(i).getStatus();
                            name = iconInfoList2.get(i).getName();
                            if (name.equals("")) {
//                                Log.i("测试进不进来：","yesyes");
//                                Toast.makeText(view1.getContext(),"接口异常",Toast.LENGTH_SHORT);
                            } else {
                                if (!status.equals("0")) {
                                    if (name.equals("施工日志")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("施工组织")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("施工图审核")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("调度指挥")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("征地拆迁")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus("0");
                                        grayicon.add(danDianLoginPub);
                                    }
                                } else {
                                    danDianLoginPub.setName(name);
                                    danDianLoginPub.setStatus(status);
                                    grayicon.add(danDianLoginPub);
                                }
                            }
                        }
                        DanDianLoginPub danDianLoginPub7 = new DanDianLoginPub();
                        danDianLoginPub7.setName("在线培训");
                        danDianLoginPub7.setStatus("1");
                        brighticon.add(danDianLoginPub7);
                        danDianLoginPubList3.addAll(brighticon);
                        danDianLoginPubList3.addAll(grayicon);

                        GridView gridView2 = (GridView) view2.findViewById(R.id.popgrid);
                        List<Map<String, Object>> grid_view_data2 = ValueConfig.getGridListRealViewData(danDianLoginPubList3);
                        PopViewAdapter popViewAdapter2 = new PopViewAdapter(view1.getContext(), grid_view_data2);
                        popViewAdapter2.setOnItemClick(new PopViewAdapter.OnItemClick() {
                            @Override
                            public void itemClick(View v, int i) {
                                Log.i("mainactivity===position", i + "");
                                DanDianLoginPub danDianLoginPub = danDianLoginPubList3.get(i);
                                String name = danDianLoginPub.getName();
                                String status = danDianLoginPub.getStatus();
                                Log.i("MainActivity", name);
                                if (status != null && !"0".equals(status)) {
                                    switch (name) {
                                        case "施工日志":
                                            appID = "3";
                                            name = "施工日志";
                                            break;
                                        case "在线培训":
                                            appID = "9";
                                            name = "在线培训";
                                            break;
                                        case "施工组织":
                                            appID = "13";
                                            break;
                                        case "施工图审核":
                                            appID = "39";
                                            break;
                                        case "调度指挥":
                                            appID = "53";
                                            break;
                                        case "征地拆迁":
                                            appID = "38";
                                            break;
                                    }
                                    packname = appType2.get(name);
                                    getApk(view2.getContext(), packname, appID, name);
                                }
                            }
                        });

                        for (int j = 0; j < danDianLoginPubList3.size(); j++) {
                            DanDianLoginPub siteInfo = danDianLoginPubList3.get(j);
                            siteInfo.setPageno("2");
                            danDianLoginPubService.saveDanDianLoginPubDao(siteInfo);
                        }
                        danDianLoginPubService.saveDanDianLoginPubLists(danDianLoginPubList3);
                        Log.d("dadadada", danDianLoginPubService.queryDanDianLoginPubDaopage(2 + "").size() + "");
                        gridView2.setAdapter(popViewAdapter2);
                        //                        gridView2.setAdapter(new PopViewAdapter(view2.getContext(), grid_view_data2));
//                        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Log.i("MainActivity", "这是MainActivity22");
//                                DanDianLoginPub danDianLoginPub = danDianLoginPubList3.get(i);
//                                String name = danDianLoginPub.getName();
//                                String status = danDianLoginPub.getStatus();
//                                if (status != null && !"0".equals(status)) {
//                                    switch (name) {
//                                        case "施工日志":
//                                            appID = "3";
//                                            name = "施工日志";
//                                            break;
//                                        case "在线培训":
//                                            appID = "9";
//                                            name = "在线培训";
//                                            break;
//                                    }
//                                    packname = appType2.get(name);
//                                    getApk(view2.getContext(), packname, appID, name);
//                                }
//                            }
//                        });
                        break;
                    case 5:
                        brighticon = new ArrayList<DanDianLoginPub>();
                        grayicon = new ArrayList<DanDianLoginPub>();
                        danDianLoginPubList4 = new ArrayList<DanDianLoginPub>();
                        List<IconInfo> iconInfoList4 = (List<IconInfo>) obj;
                        for (int i = 0; i < iconInfoList4.size(); i++) {
                            DanDianLoginPub danDianLoginPub = new DanDianLoginPub();
                            id = iconInfoList4.get(i).getId();
                            status = iconInfoList4.get(i).getStatus();
                            name = iconInfoList4.get(i).getName();
                            if (name.equals("")) {
//                                Log.i("测试进不进来：","yesyes");
//                                Toast.makeText(view1.getContext(),"接口异常",Toast.LENGTH_SHORT);
                            } else {
                                if (!status.equals("0")) {
                                    if (name.equals("拌和站")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("试验室")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("围岩量测")) {
                                        danDianLoginPub.setNum(wylcnum);
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("沉降观测")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("线形监测")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("桥梁静载")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("隧道形象化")) {
                                        Log.i("隧道形象化。。。", "00000");
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else if (name.equals("检验批")) {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus(status);
                                        brighticon.add(danDianLoginPub);
                                    } else {
                                        danDianLoginPub.setName(name);
                                        danDianLoginPub.setStatus("0");
                                        grayicon.add(danDianLoginPub);
                                    }
                                } else {
                                    danDianLoginPub.setName(name);
                                    danDianLoginPub.setStatus(status);
                                    grayicon.add(danDianLoginPub);
                                }
                            }
                        }
                        danDianLoginPubList4.addAll(brighticon);
                        danDianLoginPubList4.addAll(grayicon);

                        GridView gridView3 = (GridView) view3.findViewById(R.id.popgrid);
                        List<Map<String, Object>> grid_view_data3 = ValueConfig.getGridListXianViewData(danDianLoginPubList4);
                        PopViewAdapter popViewAdapter3 = new PopViewAdapter(view1.getContext(), grid_view_data3);
                        popViewAdapter3.setOnItemClick(new PopViewAdapter.OnItemClick() {
                            @Override
                            public void itemClick(View v, int i) {
                                Log.i("mainactivity===position", i + "");
                                DanDianLoginPub danDianLoginPub = danDianLoginPubList4.get(i);
                                String name = danDianLoginPub.getName();
                                String status = danDianLoginPub.getStatus();
                                Log.i("MainActivity", name);
                                if (status != null && !"0".equals(status)) {
                                    switch (name) {
                                        case "拌和站":
                                            appID = "5";
                                            name = "拌和站";
                                            break;
                                        case "试验室":
                                            name = "试验室";
                                            appID = "6";
                                            break;
                                        case "围岩量测":
                                            name = "隧道监控";
                                            appID = "4";
                                            break;
                                        case "沉降观测":
                                            name = "沉降观测";
                                            appID = "7";
                                            break;
                                        case "线形监测":
                                            name = "连续梁";
                                            appID = "8";
                                            break;
                                        case "桥梁静载":
                                            appID = "16";
                                            break;
                                        case "隧道形象化":
                                            appID = "17";
                                            break;
                                        case "检验批":
                                            appID = "35";
                                            break;
//
                                    }
                                    packname = appType2.get(name);
                                    getApk(view3.getContext(), packname, appID, name);
                                }
                            }
                        });

                        for (int j = 0; j < danDianLoginPubList4.size(); j++) {
                            DanDianLoginPub siteInfo = danDianLoginPubList4.get(j);
                            siteInfo.setPageno("3");
                            danDianLoginPubService.saveDanDianLoginPubDao(siteInfo);
                        }
                        danDianLoginPubService.saveDanDianLoginPubLists(danDianLoginPubList4);
                        gridView3.setAdapter(popViewAdapter3);
                        break;
                    case 6:
                        AlterPwd alterPwd = (AlterPwd) obj;
                        if (alterPwd.getStatus().equals("0")) {
                            Toast.makeText(MainActivity.this, "修改成功",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,
                                    LoginActivity1.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "修改失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 7:
                        PushInfo pushInfo = (PushInfo) obj;
                        String sum = pushInfo.getSum();
                        badgeview(home, sum);
                        wylcnum = pushInfo.getWylcNum();
                        if (wylcnum == null) {
                            wylcnum = "";
                        }
                        Log.i("围岩量测数量", wylcnum);
                        oanum = pushInfo.getOaNum();
                        if (oanum == null) {
                            oanum = "";
                        }
                        Log.i("公文处理数量", oanum);
                        break;
                    case 8:
                        UnBding unBdingobj = (UnBding) obj;
                        if (unBdingobj.getStatus().equals("0")) {
                            Toast.makeText(MainActivity.this, "解绑成功",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,
                                    LoginActivity1.class);
                            intent.putExtra("unbding", "success");
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case 9:
                        break;
                    case 10:
                        companyLogoInfoList = (List<CompanyLogoInfo>) obj;
                        if (companyLogoInfoList.size() > 0) {
                            Log.i("公司logo", companyLogoInfoList.get(0).getLogopath());
                            bitmapUtils.display(mainlogo, companyLogoInfoList.get(0).getLogopath());
                        } else {
                            Toast.makeText(MainActivity.this, "接口无图标返回", Toast.LENGTH_SHORT);
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void onFinish() {

        }

    }

    //消息推送(根据围岩量测和公文处理)
    public void badgeview(View target, String num) {
        BadgeView badgeView = new BadgeView(MainActivity.this, target);
        if (!(num.equals("0"))) {
            badgeView.setText(num);
            if (target == wylc) {
                badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            } else if (target == gwcl) {
                badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            } else {
                badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
            badgeView.show();
        } else {
            badgeView.setText("");
        }
    }


    //根据apk接口返回url下载apk
    class getDownloadAddrHttpResultListener implements HttpResultListener {

        @Override
        public void onStartRequest() {
            // TODO Auto-generated method stub
            startProgressDialog("正在下载应用……");
        }

        @Override
        public void onResult(Object obj) {
            // TODO Auto-generated method stub
            if (obj instanceof FailRequest) {
                if (((FailRequest) obj).getStatus().equals("")) {
                    Intent intent = new Intent(MainActivity.this,
                            LoginActivity1.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this,
                            ((FailRequest) obj).getMsg(),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                List<ApkUpdateInfo> apkUpdateInfoList = (List<ApkUpdateInfo>) obj;
                if (apkUpdateInfoList.size() != 0) {
                    for (int i = 0; i < apkUpdateInfoList.size(); i++) {
                        String str = apkUpdateInfoList.get(i).getAppUrl();
                        String apkname = apkUpdateInfoList.get(i).getApkName();
                        String apkSize = apkUpdateInfoList.get(i).getApkSize();
                        Log.i("文件大小：", apkSize);
                        GetApk.downloadFile(str, apkname, apkSize, handler,
                                new GetDownloadApkHttpResultListener(apkname));
                    }

                } else {
                    String str = apkUpdateInfoList.get(0).getAppUrl();
                    String apkname = apkUpdateInfoList.get(0).getApkName();
                    String apkSize = apkUpdateInfoList.get(0).getApkSize();
                    Log.i("文件大小：", apkSize);
                    GetApk.downloadFile(str, apkname, apkSize, handler,
                            new GetDownloadApkHttpResultListener(apkname));
                }
            }
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            stopProgressDialog();
        }

    }


    //下载APK
    class GetDownloadApkHttpResultListener implements HttpResultListener {

        String type;

        public GetDownloadApkHttpResultListener(String type) {
            super();
            this.type = type;
        }

        @Override
        public void onStartRequest() {
            // TODO Auto-generated method stub
            pdDialog = new ProgressDialog(MainActivity.this);
            pdDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pdDialog.setTitle("正在下载应用……");
            pdDialog.setIndeterminate(false);
            pdDialog.setCanceledOnTouchOutside(false);
            pdDialog.show();
        }

        @Override
        public void onResult(Object obj) {
            if (ValueConfig.validateObject(obj, MainActivity.this)) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(
                        Uri.fromFile(new File(ValueConfig.DOWNLOAD, type)),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            pdDialog.cancel();
//            Uri packageURI = Uri.parse("package:newsemc.com.awit.news.newsemcapp");
//            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//            uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            MainActivity.this.startActivity(uninstallIntent);
        }

    }


    //根据apk接口返回url下载apk
    class getDownloadAddrHttpResultListener1 implements HttpResultListener {

        @Override
        public void onStartRequest() {
            // TODO Auto-generated method stub
            startProgressDialog("正在下载应用……");
        }

        @Override
        public void onResult(Object obj) {
            // TODO Auto-generated method stub
            if (obj instanceof FailRequest) {
                if (((FailRequest) obj).getStatus().equals("")) {
                    Intent intent = new Intent(MainActivity.this,
                            LoginActivity1.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this,
                            ((FailRequest) obj).getMsg(),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                List<ApkUpdateInfo> apkUpdateInfoList = (List<ApkUpdateInfo>) obj;

                String str = apkUpdateInfoList.get(0).getAppUrl();
                String apkname = apkUpdateInfoList.get(0).getApkName();
                String apkSize = apkUpdateInfoList.get(0).getApkSize();
                Log.i("文件大小：", apkSize);
                GetApk.downloadFile(str, apkname, apkSize, handler,
                        new GetDownloadApkHttpResultListener(apkname));

            }
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            stopProgressDialog();
        }

    }

    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void startProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage(msg);
        }

        progressDialog.show();
    }

    // 更新进度UI
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();
                    int i = bundle.getInt("count");
                    Log.i("接收数据进度", i + "");
                    pdDialog.setProgress(i);
                    break;
            }
        }

    };

    /**
     * 获得手机端版本号
     */
    public String getVerCode(Context context, String pkg) {
        String verCode = "-1";
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    pkg, 0).versionName;
        } catch (Exception e) {
            System.out.println("版本号获取异常:" + e.getMessage());
        }
        return verCode;
    }

    /**
     * 公有方法（apk下载）
     */
    public void getApk(final Context context, final String packagename, final String appId, final String name) {
        Log.i("传进来的包名：", packagename + "");
        Log.i("传进来的actionname：", name + "");
        Log.i("传进来的appid：", appId + "");
        if (!(isAppInstalled(context, packagename == null ? "" : packagename.trim()))) {
            DialogUtils.alertDialog(context, "下载", "确认下载吗", new DialogUtils.DialogCallBack() {
                @Override
                public void positive() {
                    ApkDetailImpl apkDetailImpl = new ApkDetailImpl(new getDownloadAddrHttpResultListener(), new ApkDetailImpl.AppCallBack() {
                        @Override
                        public void myResult(List<ApkUpdateInfo> result) {

                        }

                        @Override
                        public void myResultFailure(FailRequest failRequestobj) {

                        }
                    });
                    Log.i("共有方法ssid", ssid);
                    apkDetailImpl.getApkDetail(ssid, appId);
                }

                @Override
                public void negative() {

                }
            });

        } else {
            Log.i("name", name);
            String s1 = appType1.get(name);
            Log.i("s1", s1 + "");
            startAppByAction(s1, name);
        }
    }

    /**
     * 检查某个应用是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (packageName == null || "".equals(packageName)) {
            Log.i(TAG, "找不到包名！！！");
            Log.i(TAG, context.getPackageManager() + "");
            return true;
        }
        if (packageManager == null) {
            packageManager = UIUtils.getContext().getPackageManager();
        }
        try {
            packageManager.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.i(TAG, "应用正确安装！！！");
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.i(TAG, "没有安装此应用！！！");
            return false;
        }
    }

    private void update() {
        ApkDetailImpl apkDetailImpl = new ApkDetailImpl(new MyHTTPResultListener(9), new ApkDetailImpl.AppCallBack() {
            @Override
            public void myResult(List<ApkUpdateInfo> result) {
                List<ApkUpdateInfo> apkUpdateInfoList = (List<ApkUpdateInfo>) result;
                sdkvercode = getVerCode(MainActivity.this, "newsemc.com.awit.news.newsemcapp");
                servercode = apkUpdateInfoList.get(0).getVersionName();
                Log.i("mmm", apkUpdateInfoList.get(0).getDescription());
                Log.i("服务器版本号：", servercode);
                Log.i("本地版本号：", sdkvercode + "");
                Log.i("是否更新", isNeedsUpgrade(sdkvercode, servercode) + "");
                if (isNeedsUpgrade(sdkvercode, servercode)) {
//                    Toast.makeText(MainActivity.this, "hahahahahahhaha", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            MainActivity.this);
                    builder.setTitle("更新")
//                            .setMessage("添加用户反馈为空校验，解决项目基础信息界面两个top问题")
//                            .setMessage("界面整体优化,实现首页图片新闻点击进入详情,根据附件类型显示不同的附件图标,增加新闻发布部门显示")
                            .setMessage(apkUpdateInfoList.get(0).getDescription())
                            .setPositiveButton("更新",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface arg0, int arg1) {
                                            ApkDetailImpl updateApk = new ApkDetailImpl(new getDownloadAddrHttpResultListener1());
                                            updateApk.getApkDetail(ssid, "1");
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface arg0, int arg1) {
                                            arg0.dismiss();
                                        }
                                    }).show();
                } else {
                    Log.i("版本一致", "对的对的");
                    Toast.makeText(MainActivity.this, "已是最新版本", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void myResultFailure(FailRequest failRequestobj) {

            }
        });
        apkDetailImpl.getApkDetail(ssid, "1");
    }

    /**
     * 是否需要升级
     *
     * @param oldVersionName
     * @param newVersionName
     * @return
     */
    private static boolean isNeedsUpgrade(String oldVersionName, String newVersionName) {
        String[] oldStr = oldVersionName.split("\\.");//本地
        String[] newStr = newVersionName.split("\\.");//服务器
        boolean flag = false;
        Log.i("相同长度：", "yesyes");
//        for (int i = 0;i < (newStr.length <= oldStr.length ? newStr.length : oldStr.length); i++) {
//            if (Integer.parseInt(newStr[i]) > Integer.parseInt(oldStr[i])) {
//                flag = true;
//                break;
//            } else {
//                flag = false;
//            }
//        }
        for (int i = 0; i < (newStr.length <= oldStr.length ? newStr.length : oldStr.length); i++) {
            Log.i("123", "old" + Integer.parseInt(oldStr[i]) +
                    "new" + Integer.parseInt(newStr[i]));
            if (Integer.parseInt(newStr[i]) > Integer.parseInt(oldStr[i])) {
//                return true;
                flag = true;
                break;
            } else {
//                return false;
                flag = false;
            }
        }
        return flag;
//        return false;
    }


    //二级树形菜单的加载
    private void showProDialog() {
        pdDialog = new ProgressDialog(this);
        pdDialog.setMessage("loading...");
        pdDialog.show();
    }

    //查看apk的签名是否一致
    public void qianming() {
        if ("".equals("android.keystore")) {
            Log.i("签名一致，对的对的", "yesyesyes");
        } else {
            Toast.makeText(MainActivity.this, "签名不一致，请卸载原版本", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 卸载程序时清理掉缓存
     */
    class PackageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                String packageName = intent.getDataString();
                Log.i("Test", "---------------" + packageName);
            }


            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                String packageName = intent.getDataString();
                Log.i("Test", "---------------" + "PACKAGE_REMOVED" + packageName);
            }
        }
    }

    //删除文件
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//                    Toast.makeText(getApplicationContext(), "文件或文件夹不存在", Toast.LENGTH_LONG).show();
                    break;
                case 1:
//                    Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_LONG).show();
                    break;
                case 2:
//                    Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_LONG).show();
                    break;
                case 3:
//                    Toast.makeText(getApplicationContext(), "删除成功！", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };


    //删除文件
    private void deleteFile(String path, String fileName) {
        File df = new File(path + File.separator + fileName);
        Log.i("papapapa", fileName);
        if (df.exists()) {
            df.delete();
//            Log.i("booloooo", str + "");
            Log.i(TAG, fileName);
            //删除文件
            Toast.makeText(this, "文件删除成功" + path + File.separator + fileName, Toast.LENGTH_SHORT).show();

        } else {
            Log.i("TAG", "文件不存在");
        }
    }

    //
    public ArrayList<String> getAllMountedPath() {// get all mounted dir
        ArrayList<String> allMountedPath = new ArrayList<String>();
        allMountedPath.add(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        Log.d(TAG, "外部存储路径："
                + Environment.getExternalStorageDirectory().getAbsolutePath());
        File file = new File("/system/etc/vold.fstab");
        if (file.exists()) {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(file);
                if (fr != null) {
                    br = new BufferedReader(fr);
                    String s = br.readLine();
                    while (s != null) {
                        if (s.startsWith("dev_mount")) {
                            /* "\s"转义符匹配的内容有：半/全角空格 */
                            String[] tokens = s.split("\\s");
                            String path = tokens[2]; // mount_point
                            if (new File(path).exists()) {
                                allMountedPath.add(path);
                                Log.d(TAG, "系统挂载路径：" + path);
                            }
                        }
                        s = br.readLine();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fr != null)
                    try {
                        fr.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                if (br != null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        }
        return allMountedPath;
    }

    /**
     * 过滤poupwindow中一些不需要的图标
     *
     * @param list
     */
    public void filter(List<DanDianLoginPub> list) {
        if (list == null) return;

        String[] strArr = {"现场监视", "人员机构管理", "信息发布"};
        for (int i = 0; i < strArr.length; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (strArr[i].equals(list.get(j).getName().trim())) {
                    list.remove(j);
                    j--;
                    break;
                }
            }
        }
    }

    //从服务器获取appid
    public void getAppIDServer(String userid, String appid) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", userid);
        params.addBodyParameter("appId", appid);
        Log.i("aaaaaaa", userid + "");
        Log.i("bbbbb", appid);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.POST, GloBalUrl.match, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("shaorc", "数据" + responseInfo.result);
                String result = responseInfo.result;
//                Toast.makeText(MainActivity.this, responseInfo.result.toString(), Toast.LENGTH_SHORT).show();

                try {
                    Match search = new Gson().fromJson(responseInfo.result, Match.class);
                    String appId2 = search.getData().getAppId();
                    SharedPreferences sf = getSharedPreferences("APPID", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sf.edit();
                    ed.putString("appId", appId2);
                    Log.i("shapppp", appId2);
                    ed.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Gson gson = new Gson();
//                try {
//                    TypeToken<List<Match>> token = new TypeToken<List<Match>>() {
//                    };
//                    matchList = gson.fromJson(result, token.getType());
////                matchList = new ArrayList<Match>();
//                    for (int i =0;i<matchList.size();i++){
//                        String appId1 = matchList.get(i).getData().getAppId();
//                        SharedPreferences sf = getSharedPreferences("APPID",MODE_PRIVATE);
//                        SharedPreferences.Editor ed = sf.edit();
//                        ed.putString("appId",appId1);
//                        Toast.makeText(MainActivity.this, appId1, Toast.LENGTH_SHORT).show();
//                        ed.commit();
//
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }
}