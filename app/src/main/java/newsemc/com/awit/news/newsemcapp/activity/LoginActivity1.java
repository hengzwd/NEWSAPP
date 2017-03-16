package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.UserLogin;
import newsemc.com.awit.news.newsemcapp.dao.ApkUpdateInfo;
import newsemc.com.awit.news.newsemcapp.dao.CompanyLogoInfo;
import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfo;
import newsemc.com.awit.news.newsemcapp.dao.PersonInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.ApkDetailImpl;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.UserLoginImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.LoginUserBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ConstantsUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.HttpUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.SharedPreferencesUtils;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.ToastUtils;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.service.DanDianLoginPubService;
import newsemc.com.awit.news.newsemcapp.service.IconInfoService;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;
import newsemc.com.awit.news.newsemcapp.util.GetApk;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by Administrator on 2016-08-18.
 */
public class LoginActivity1 extends Activity implements HttpResultListener {
    private final String TAG = "LoginActivity";
    private EditText name, pass, imeiedit;
    private CheckBox checkpass;
    private Button login;
    private CustomProgressDialog progressDialog = null;
    private static String account, pwd;
    private static UserLogin userLogin = null;
    //动态获取imei
    private String imei = "";
    //学习版登录的用户名和密码
    private String username, mobile;

    private long exitTime = 0;
    private RelativeLayout passlayout, namelayout, imeilayout;
    //保存上次imei
    private String imeiNo;
    private TelephonyManager telephonyManager;
    private String phoneNum;
    private ProgressDialog pdDialog;
    private static String ssid;
    private static String userAccount;
    private static String relateAccount;
    private static String idcard;
    private static String compid;

    private List<JSONObject> switchers;
    private String firststa = "5";//成功登录的状态码
    private String status;
    private String successsta = "10", exceptionsta = "10", loginsta = "10", exsta = "10";
    private boolean flag = false;
    private SharedPreferences success, getsta;
    private Object userInfo;
    //
    private String companyId, companyName;

    //测试人员账号登录
    private TextView testlogin;

    private TextView desrc;

    private String namestr;

    private SharedPreferences NodeID;
    private String id1;

    private SharedPreferences sp;
    //获取公司LOGO列表定义
    private List<CompanyLogoInfo> companyLogoInfoList;

    private CheckBox remember;
    public static CheckBox autologin;

    //本地apk版本号
    private String sdkvercode;
    //服务器版本号
    private String servercode;


    //数据表的定义
    private ImageInfoService imageInfoService;
    private CompanyInfoService companyInfoService;
    private IconInfoService iconInfoService;
    private DanDianLoginPubService danDianLoginPubService;
    public static boolean choseAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        //获取控件的方法
        initview();
        // 获取imei号
        imeiNo = getImei();
        Log.i("动态获取imei号：", imeiNo);
        //保存第一次登录的imei码
        SharedPreferences getLogin = getSharedPreferences("SAVE_LOGIN", MODE_PRIVATE);
        String lastpass = getLogin.getString("saveimei", "");
        String lastname = getLogin.getString("account", "");
        String lastpwd = getLogin.getString("password", "");
        //保存默认用户登陆成功的状态
        SharedPreferences getDeafault = getSharedPreferences("SAVE_DEFAULT", MODE_PRIVATE);
        String defaultsta = getDeafault.getString("deastatus", "");
        namestr = getDeafault.getString("account", "");
        name.setText(namestr);//yanzhiwei的密码
        Log.i("上次保存的用户名：", namestr + "hahahah" + name.getText());
        Log.i("第一次用默认账户的登录状态：", defaultsta);
        Intent intent = getIntent();
        Log.i("第一次的intent对象", intent.getStringExtra("unbding") + "");
        if ("success".equals(intent.getStringExtra("unbding"))) {
            Toast.makeText(LoginActivity1.this, "请绑定新的账户", Toast.LENGTH_SHORT).show();
//            imeilayout.setVisibility(View.VISIBLE);
            namelayout.setVisibility(View.VISIBLE);
            desrc.setVisibility(View.VISIBLE);
        } else if ((lastpass.equals(imeiNo) &&
                ((namestr == null ? "" : namestr.trim()).equals(name == null ? "" : name.getText().toString().trim()))
                && (defaultsta.equals("0")) && (!("fail".equals(intent.getStringExtra("fail")))))) {
            Log.i("屏蔽用户名和imei", "yes");
//            imeilayout.setVisibility(View.GONE);
            namelayout.setVisibility(View.GONE);
            desrc.setVisibility(View.GONE);
            //第一次登录成功之后，直接用密码登录(忘记密码)出现“用户名或密码错误”，显示用户名和密码方便用户登录
            if ("pwderror".equals(intent.getStringExtra("pwderror"))) {
                namelayout.setVisibility(View.VISIBLE);
                desrc.setVisibility(View.GONE);
            }
        } else if ("pwderror".equals(intent.getStringExtra("pwderror"))) {
            Log.i("用户名和密码错误", "yes");
            namelayout.setVisibility(View.VISIBLE);
            desrc.setVisibility(View.GONE);
        } else {
            namelayout.setVisibility(View.VISIBLE);
            desrc.setVisibility(View.GONE);
        }

        //初始化数据表的业务
        imageInfoService = ImageInfoService.getInstance(LoginActivity1.this);
        companyInfoService = CompanyInfoService.getInstance(LoginActivity1.this);
        iconInfoService = IconInfoService.getInstance(LoginActivity1.this);
        danDianLoginPubService = DanDianLoginPubService.getInstance(LoginActivity1.this);


        Log.i("IMEI码：", imeiNo);

        imeiedit.setText(imeiNo);//imei码

        //实现登录方法    `
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用登录的方法
                Login(null, null);
            }
        });
        sp = getSharedPreferences("SAVE_LOGIN", MODE_PRIVATE);
        //接受传递过来的记住密码和自动登录的值
//        boolean choseRemember =getLogin.getBoolean("remember", false);
//        choseAutoLogin =getLogin.getBoolean("autologin", false);
//
//// 如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
//        if(choseRemember){
//            name.setText(lastname);
//            pass.setText(lastpwd);
//            remember.setChecked(true);
//        }
//        //如果上次选择了自动登录，这次也是自动勾选自动登录
//        if (choseAutoLogin){
//            autologin.setChecked(true);
//        }
        //自动登录
//        boolean zidong = getIntent().getBooleanExtra("zidong", false);
//        SharedPreferences ss = getSharedPreferences("SAVE_ZIDONG", Activity.MODE_PRIVATE);
//        boolean zidong = ss.getBoolean("zidong", false);

//        if ( !"success".equals(getIntent().getStringExtra("unbding")) && !"pwderror".equals(getIntent().getStringExtra("pwderror")) ) {
//                SharedPreferences loginn = getSharedPreferences("SAVE_LOGIN", MODE_PRIVATE);
//                String account = loginn.getString("account", LoginActivity1.account);
//                String password = loginn.getString("password", pwd);
//                Login(account, password);
//
//            }
//        else if (zidong ) {
//                choseAutoLogin = false;
//                autologin.setChecked(false);
//                stopProgressDialog();
//            }

        update();

        Log.i("oncreate", "update");
        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false) && !"success".equals(getIntent().getStringExtra("unbding")) && !"pwderror".equals(getIntent().getStringExtra("pwderror"))) {
            //设置默认是记录密码状态
            remember.setChecked(true);
            name.setText(sp.getString("account", account));
            pass.setText(sp.getString("password", pwd));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                autologin.setChecked(true);
                Login(null, null);

            }
        }
        //
        //监听记住密码多选框按钮事件
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences sp = getSharedPreferences("REm",MODE_PRIVATE);
                if (remember.isChecked()) {

                    System.out.println("记住密码已选中");

                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    System.out.println("记住密码没有选中");

                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences sp = getSharedPreferences("AUTO",MODE_PRIVATE);
                if (autologin.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }


    private void initview() {
        name = (EditText) findViewById(R.id.nameinput);
        pass = (EditText) findViewById(R.id.passinput);
        imeiedit = (EditText) findViewById(R.id.imeiinput);
        passlayout = (RelativeLayout) findViewById(R.id.passlayout);
        namelayout = (RelativeLayout) findViewById(R.id.namelayout);
        imeilayout = (RelativeLayout) findViewById(R.id.imeilayout);
        imeilayout.setVisibility(View.GONE);
        login = (Button) findViewById(R.id.login);
        success = getSharedPreferences("SAVE_SUCSTA", MODE_PRIVATE);
        getsta = getSharedPreferences("SAVE_STA", MODE_PRIVATE);
        desrc = (TextView) findViewById(R.id.decripe);
        remember = (CheckBox) findViewById(R.id.remember);
        autologin = (CheckBox) findViewById(R.id.autologin);
    }

    // 获取imei号,把imei号通过sharedPreferences存储在本地
    private String getImei() {
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();//获取唯一设备id
        SharedPreferences sharedPreferences = getSharedPreferences("SAVE_IMEI",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imei", imei);
        editor.commit();
        return imei;
    }

    //登录方法
    private void Login(String username, String userpwd) {
        account = name.getText().toString();
        pwd = pass.getText().toString();
        imei = imeiedit.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(userpwd)) {
            account = username;
            pwd = userpwd;
        }
        if (remember.isChecked()) {
            remember.setChecked(true);
            //存储所需要的用户名和密码
            SharedPreferences loginshare = getSharedPreferences("SAVE_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor editor = loginshare.edit();
            editor.putString("saveimei", imei);
            editor.putString("account", account);
            editor.putString("password", pwd);
            //
            editor.putString("login", "login");

            //是否记住密码
//        if(remember.isChecked()){
//            editor.putBoolean("remember", true);
//
//        }else{
//            editor.putBoolean("remember", false);
//        }
//        //是否自动登录
//        if(autologin.isChecked()){
//            editor.putBoolean("autologin", true);
//        }else{
//            editor.putBoolean("autologin", false);
//        }
            editor.commit();
        }
        //调用接口，实现登录
        UserLoginImpl getUserLogin = new UserLoginImpl(
                new MyHTTPResultListener(1));
        getUserLogin.login(account, pwd, imei);
        //获取扫描模块的授权Token值
        scanLogin();
    }

    private void scanLogin() {
        KLog.e("11111111111111111111111111111");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("uid", "da");
        paramsMap.put("pwd", "");

        HttpUtils.postRequest(ConstantsUtils.Login_URL, paramsMap, new HttpUtils.HttpListener() {
            @Override
            public void onSuccess(String response) {
                KLog.json(response);
                LoginUserBean data = new Gson().fromJson(response, LoginUserBean.class);
                if (null != data && 0 == data.getResult()) {

                    NewsEMCAppllication.mToken = data.getToken();
                    if (!TextUtils.isEmpty(NewsEMCAppllication.mToken)) {

                        KLog.e(NewsEMCAppllication.mToken);
                    }
                    SharedPreferencesUtils.put(getApplicationContext(), "scan_login_token", data.getToken());
                } else {
                    //提示数据为空，展示空状态
                    ToastUtils.showToast(getApplicationContext(), "授权失败");
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                KLog.e("3333333");
                error.printStackTrace();

            }
        });
    }

    //回调函数方法
    class MyHTTPResultListener implements HttpResultListener {
        private int index;

        public MyHTTPResultListener(int index) {
            this.index = index;
//            onResult();
        }

        @Override
        public void onStartRequest() {
            if (index != 9) {  //更新检测网络请求
                startProgressDialog("正在加载……");
            }
        }

        @Override
        public void onResult(Object obj) {
            if (obj instanceof FailRequest) {
                if (((FailRequest) obj).getStatus().equals("-2")) {
                    Toast.makeText(LoginActivity1.this, ((FailRequest) obj).getMsg(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity1.this, LoginActivity.class);
                    intent.putExtra("fail", "fail");
                    startActivity(intent);
                    finish();
                } else if (((FailRequest) obj).getStatus().equals("-1")) {
                    Toast.makeText(LoginActivity1.this,
                            ((FailRequest) obj).getMsg() + ",请重新登录",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity1.this, Login.class);
                    intent.putExtra("pwderror", "pwderror");
                    startActivity(intent);
                    finish();
                } else if (((FailRequest) obj).getStatus().equals("-3")) {
                    Toast.makeText(LoginActivity1.this, ((FailRequest) obj).getMsg(), Toast.LENGTH_SHORT).show();
                } else if (((FailRequest) obj).getStatus().equals("-4")) {
                    Toast.makeText(LoginActivity1.this, ((FailRequest) obj).getMsg()
                            , Toast.LENGTH_LONG).show();
                }
            } else {
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        userLogin = (UserLogin) obj;
                        if (!(userLogin == null)) {
                            if (!userLogin.getPersonInfobj().getSsid().equals("")
                                    && userLogin.getPersonInfobj().getSsid() != null) {
                                Context ctx = LoginActivity1.this;
                                // 保存手机号
                                saveMobileNo();
                                KLog.e(userLogin.getPersonInfobj());
                                NewsEMCAppllication.phoneInfo.set电话号码(userLogin.getPersonInfobj().getContact());
                                //保存指定用户登录成功的状态
                                SharedPreferences defaultsta = ctx.getSharedPreferences("SAVE_DEFAULT", MODE_PRIVATE);
                                SharedPreferences.Editor editordea = defaultsta.edit();
                                editordea.putString("deastatus", "0");
                                editordea.putString("account", account);
                                compid = userLogin.getPersonInfobj().getCompid();
//                                Toast.makeText(LoginActivity.this, "11", Toast.LENGTH_SHORT).show();
                                boolean isJingshen = false;
                                if (compid.equals("279")) isJingshen = true;
                                editordea.putBoolean("isJingshen", false);

                                editordea.commit();
                                String name = userLogin.getPersonInfobj().getSsid();
                                Log.i(TAG, "SSId===" + name);
                                String contact = userLogin.getPersonInfobj().getContact();
                                userAccount = userLogin.getPersonInfobj().getAccount();
                                Log.i(TAG, "userAccount = " + userAccount);
                                relateAccount = userLogin.getPersonInfobj().getRelateAccount();
                                Log.i(TAG, "relateAccount = " + relateAccount);
                                idcard = userLogin.getPersonInfobj().getIdNo();
                                Log.i(TAG, "idcard = " + idcard);
                                Log.i(TAG, "compid = " + compid);
                                String compname = userLogin.getPersonInfobj().getCompname();
                                List<DepartmentInfo> department = userLogin
                                        .getDepartmentInfoList();
                                String duty = department.get(0).getDuty();
                                String f_userId = department.get(0).getF_userId();
                                Bundle bundle = new Bundle();
                                bundle.putString("name", name);
                                bundle.putString("duty", duty);
                                bundle.putString("contact", contact);
                                bundle.putString("relateAccount", relateAccount);
                                bundle.putString("compname", compname);
                                NewsEMCAppllication.switchers= userLogin.getPersonInfobj().getSwitchers();
                            //    bundle.putSerializable("PersonInfo",personInfo );
                                bundle.putString("f_userId", f_userId);
//                                bundle.putStringArrayList("list",((UserLogin) obj).getPersonInfobj().getAccountlist());
                                bundle.putString("ssid", name);
                                Log.e(TAG, "f_userId====" + f_userId);
                                Intent intent = new Intent(LoginActivity1.this,
                                        MainActivity.class);
                                intent.putExtras(bundle);
                                intent.putExtra("compid", compid);
                                intent.putExtra("ssidd", name);
                                startActivity(intent);
                                finish();
                                KLog.e("finish");
                                //登录成功后,当上次登录成功的用户名跟本次登录的用户名不相同的情况下，清楚缓存
                                if (!namestr.equals(userAccount)) {
                                    Log.i("更换用户名了呀", "ohohohoh");
                                    imageInfoService.deleteAllImageInfo();
                                    companyInfoService.deleteAllCompanyInfo();
                                    iconInfoService.deleteAllIconInfo();
                                    danDianLoginPubService.deleteAllDanDianLoginPub();
                                }
                                Toast.makeText(LoginActivity1.this, "登录成功",
                                        Toast.LENGTH_SHORT).show();
                                // 将ssid值存入xml中
                                SharedPreferences sp = ctx.getSharedPreferences(
                                        "SP", MODE_PRIVATE);
                                String key = userLogin.getPersonInfobj().getSsid();
                                ssid = key;
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("KEY", key);
                                editor.putString("ACCOUNT", userAccount);
                                editor.commit();
                                // 判断sp中xml路径的值
//                                SharedPreferences shared = ctx
//                                        .getSharedPreferences("XMLPATH",
//                                                MODE_PRIVATE);
                                String path = "/NewEMCAPP";
                                SharedPreferences sharedPath = getSharedPreferences("PATH", LoginActivity.MODE_PRIVATE);
                                SharedPreferences.Editor editorpath = sharedPath.edit();
                                editorpath.putString("path", path);
                                editorpath.commit();
                                Log.i("获取存入的xml路径", path + "");
                                // 判断emcTemp.xml文件的存在
                                try {
                                    ArrayList<String> pathList = getAllMountedPath();
                                    for (String devicePath : pathList) {
                                        createXML(devicePath + path, "emcTemp.xml");
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                        break;

                }

            }
        }

        @Override
        public void onFinish() {
            stopProgressDialog();
        }
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

        }
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onFinish() {

    }

    // 自定义对话框
    private void startProgressDialog(String msg) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setMessage(msg);
        }

        progressDialog.show();
    }

    /**
     * 创建xml文件(关于子APP单点登录需要的信息)
     *
     * @param path
     * @param filename
     */
    private void createXML(String path, String filename) {// create or update
        // xmlfile

        File file = new File(path);
        Log.i("调用xml方法", path);
        String xmlpath = path + File.separator + filename;
        Log.i(TAG, "当前处理文件路径：" + xmlpath);
        DocumentFactory factory = DocumentFactory.getInstance();// singleton
        Element root = factory.createElement("msc");
        root.add(factory.createAttribute(root, "ssid", ssid));
        root.add(factory.createAttribute(root, "mobile", account));
        root.add(factory.createAttribute(root, "userAccount", userAccount));
        root.add(factory.createAttribute(root, "relateAccount", relateAccount));
        root.add(factory.createAttribute(root, "idcard", idcard));
        root.add(factory.createAttribute(root, "compid", compid));
        Document d = factory.createDocument(root);
        if (!file.exists()) {
            Log.i(TAG, "文件不存在，正在创建！");
            try {
                if (file.mkdirs()) {
                    // 建立文件
                    FileWriter fw;
                    fw = new FileWriter(path + file.separator + filename);
                    XMLWriter output = new XMLWriter(fw);
                    output.write(d);
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
//            Log.i("删除文件路径：",path);
//            ArrayList<String> pathList = getAllMountedPath();
//            for (String devicePath : pathList) {
//                deleteXmlFile(devicePath + path, "emcTemp.xml");
//            }
            Log.i(TAG, "文件夹存在");
            File ef = new File(path + file.separator + filename);
            if (ef.exists()) {
                Log.i(TAG, "文件已存在，正在更新！");
                try {
                    Document doc = getConfig(path + file.separator + filename);
                    // 获取root节点
                    Element eroot = doc.getRootElement();
                    eroot.setAttributeValue("ssid", ssid);
                    eroot.setAttributeValue("mobile", account);
                    eroot.setAttributeValue("userAccount", userAccount);
                    eroot.setAttributeValue("relateAccount", relateAccount);
                    eroot.setAttributeValue("idcard", idcard);
                    eroot.setAttributeValue("compid", compid);
                    // 下面是保存修改过后的文件，直接创建一个xml文件，名字与读取的文件名字一样，直接覆盖
                    XMLWriter out = new XMLWriter(new FileWriter(path
                            + file.separator + filename));
                    // 将设置过后的doc保存到本地
                    out.write(doc);
                    // 关闭IO流
                    out.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                try {
                    Log.i(TAG, "文件夹存在，建立新文件！");
                    FileWriter fw;
                    fw = new FileWriter(path + file.separator + filename);
                    XMLWriter output = new XMLWriter(fw);
                    output.write(d);
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建一个文档根据文件路径
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    private static Document getConfig(String filePath) throws Exception {
        File configFile = new File(filePath);
        // 初始化一个解析器
        SAXReader reader = new SAXReader();
        Document doc = null;
        doc = reader.read(configFile);
        return doc;

    }

    // get all mounted dir
    public ArrayList<String> getAllMountedPath() {
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
     * 保存手机号(暂无用到)
     */
    public void saveMobileNo() {
        Context ctx = LoginActivity1.this;
        SharedPreferences sp = ctx
                .getSharedPreferences("LOGINNO", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("mobileNo", account);
        Log.i("存入手机号", account);
        ed.commit();
    }

    private void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    // 再按一次退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 10000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                // 退出时删除xml文件
                SharedPreferences shared = getSharedPreferences("PATH", LoginActivity.MODE_PRIVATE);
                String path = shared.getString("path", "");
                Log.i("删除文件路径：", path);
                ArrayList<String> pathList = getAllMountedPath();
                for (String devicePath : pathList) {
                    deleteXmlFile(devicePath + path, "emcTemp.xml");
                }
                System.exit(0);
                finish();
            }
//            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 删除登陆生成的xml文件方法
     *
     * @param path
     * @param fileName
     */
    private void deleteXmlFile(String path, String fileName) {
        File xmlfile = new File(path);
        Log.i(TAG, "删除当前文件路径：" + xmlfile);
        if (xmlfile.exists()) {
            File df = new File(xmlfile + xmlfile.separator + fileName);
            df.delete();
        } else {
            Log.i("TAG", "文件不存在");
        }
    }

    @Override
    public void onResume() {
//        name.setText("");
        super.onResume();
//        MobclickAgent.onResume(this);
        update();

    }

    public void onPause() {
        super.onPause();
    }


    private void update() {
        ApkDetailImpl apkDetailImpl = new ApkDetailImpl(new MyHTTPResultListener(9), new ApkDetailImpl.AppCallBack() {
            @Override
            public void myResult(List<ApkUpdateInfo> result) {
                List<ApkUpdateInfo> apkUpdateInfoList = (List<ApkUpdateInfo>) result;
                sdkvercode = getVerCode(LoginActivity1.this, "newsemc.com.awit.news.newsemcapp");
                servercode = apkUpdateInfoList.get(0).getVersionName();
                Log.i("mmm", apkUpdateInfoList.get(0).getDescription());
                Log.i("服务器版本号：", servercode);
                Log.i("本地版本号：", sdkvercode + "");
                Log.i("是否更新", isNeedsUpgrade(sdkvercode, servercode) + "");
                if (isNeedsUpgrade(sdkvercode, servercode)) {
//                    Toast.makeText(MainActivity.this, "hahahahahahhaha", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            LoginActivity1.this);
                    builder.setTitle("更新")
//                            .setMessage("添加用户反馈为空校验，解决项目基础信息界面两个top问题")
//                            .setMessage("界面整体优化,实现首页图片新闻点击进入详情,根据附件类型显示不同的附件图标,增加新闻发布部门显示")
                            .setMessage(apkUpdateInfoList.get(0).getDescription()).setCancelable(false)
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
                                            // System.exit(0);
                                            Process.killProcess(Process.myPid());
                                        }
                                    }).show();
                } else {
                    Log.i("版本一致", "对的对的");
                    Toast.makeText(LoginActivity1.this, "已是最新版本", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void myResultFailure(FailRequest failRequestobj) {

            }
        });
        apkDetailImpl.getApkDetail(ssid, "1");
    }


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


    //根据apk接口返回url下载apk
    class getDownloadAddrHttpResultListener1 implements HttpResultListener {

        @Override
        public void onStartRequest() {
            // TODO Auto-generated method stub
            // startProgressDialog("正在下载应用……");
        }

        @Override
        public void onResult(Object obj) {
            // TODO Auto-generated method stub
            if (obj instanceof FailRequest) {
                if (((FailRequest) obj).getStatus().equals("")) {
                    Intent intent = new Intent(LoginActivity1.this,
                            LoginActivity1.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity1.this,
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
            pdDialog = new ProgressDialog(LoginActivity1.this);
            pdDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pdDialog.setCanceledOnTouchOutside(false);
            pdDialog.setTitle("正在下载应用……");
            pdDialog.setIndeterminate(false);
            pdDialog.setCanceledOnTouchOutside(false);
            pdDialog.show();
        }

        @Override
        public void onResult(Object obj) {
            if (ValueConfig.validateObject(obj, LoginActivity1.this)) {
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


}


