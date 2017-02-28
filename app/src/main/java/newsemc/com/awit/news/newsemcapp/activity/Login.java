package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.UserLogin;
import newsemc.com.awit.news.newsemcapp.dao.CompanyLogoInfo;
import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.UserLoginImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.service.DanDianLoginPubService;
import newsemc.com.awit.news.newsemcapp.service.IconInfoService;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * Created by Administrator on 15-7-1.
 */
public class Login extends Activity implements HttpResultListener{
    private final String TAG = "Login";
    private EditText name, pass,imeiedit;
    private CheckBox checkpass;
    private Button login;
    private CustomProgressDialog progressDialog = null;
    private static String account,pwd;
    private static  UserLogin userLogin = null;
    //动态获取imei
    private String imei="";
    //学习版登录的用户名和密码
    private String username,mobile;

    private long exitTime = 0;
    private RelativeLayout passlayout,namelayout,imeilayout;
    //保存上次imei
    private String imeiNo;
    private TelephonyManager telephonyManager;
    private String phoneNum;
    private ProgressDialog pdDialog;
    private static String ssid;
    private static String userAccount;
    private static String relateAccount ;
    private static String idcard;
    private static String compid;
    private String firststa="5";//成功登录的状态码
    private String status;
    private String successsta="10",exceptionsta="10",loginsta="10",exsta="10";
    private boolean flag=false;
    private  SharedPreferences success,getsta;
    private Object userInfo;
    //
    private String companyId,companyName;

    //测试人员账号登录
    private TextView testlogin;

    private TextView desrc;

    private String namestr;

    private SharedPreferences NodeID;
    private String id1;
//    private Flag flagobj=new Flag();

    //获取公司LOGO列表定义
    private List<CompanyLogoInfo> companyLogoInfoList;

    private CheckBox remember;
    private CheckBox autologin;


    //数据表的定义
    private ImageInfoService imageInfoService;
    private CompanyInfoService companyInfoService;
    private IconInfoService iconInfoService;
    private DanDianLoginPubService danDianLoginPubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.login);
        name = (EditText) findViewById(R.id.nameinput);
        pass = (EditText) findViewById(R.id.passinput);
        imeiedit=(EditText)findViewById(R.id.imeiinput);
//        checkpass = (CheckBox) findViewById(R.id.ckeckpass);
        passlayout = (RelativeLayout) findViewById(R.id.passlayout);
        namelayout=(RelativeLayout)findViewById(R.id.namelayout);
        imeilayout=(RelativeLayout)findViewById(R.id.imeilayout);
        imeilayout.setVisibility(View.GONE);
        login = (Button) findViewById(R.id.login);
        success=getSharedPreferences("SAVE_SUCSTA", MODE_PRIVATE);
        getsta=getSharedPreferences("SAVE_STA", MODE_PRIVATE);
        //testlogin=(TextView)findViewById(R.id.testlogin);
        desrc=(TextView)findViewById(R.id.decripe);
        remember =(CheckBox)findViewById(R.id.remember);
        autologin = (CheckBox)findViewById(R.id.autologin);

        // 获取imei号
        imeiNo=getImei();
//        imeiNo="355637051680098";
//        imeiNo="352625061487854";
//        imeiNo="353492045124551";
        Log.i("动态获取imei号：",imeiNo);
        //保存第一次登录的imei码
        SharedPreferences getLogin=getSharedPreferences("SAVE_LOGIN",MODE_PRIVATE);
        String lastpass=getLogin.getString("saveimei","");
        String lastname=getLogin.getString("account", "");
        String lastpwd=getLogin.getString("password","");
        Log.i("第二次登录imei：", lastpass);

        //接受传递过来的记住密码和自动登录的值
        boolean choseRemember =getLogin.getBoolean("remember", false);
        boolean choseAutoLogin =getLogin.getBoolean("autologin", false);
        //      Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            name.setText(lastname);
            pass.setText(lastpwd);
            remember.setChecked(true);
        }
        //如果上次选择了自动登录，这次也是自动勾选自动登录
        if (choseAutoLogin){
            autologin.setChecked(true);
        }
        //如果上次登录选了自动登录和记住密码，那进入登录页面也自动勾选自动登录和记住密码病登录
        if(autologin.isChecked() && remember.isChecked() && !"success".equals(getIntent().getStringExtra("unbding")) && !"pwderror".equals(getIntent().getStringExtra("pwderror"))){

            SharedPreferences loginshare=getSharedPreferences("SAVE_LOGIN",MODE_PRIVATE);
            String account = loginshare.getString("account", null);
            String password = loginshare.getString("password", null);
            Login(account,password);
            Intent inte = new Intent(Login.this,MainActivity.class);
            startActivity(inte);

        }
        //保存默认用户登陆成功的状态
        SharedPreferences getDeafault=getSharedPreferences("SAVE_DEFAULT", MODE_PRIVATE);
        String defaultsta=getDeafault.getString("deastatus", "");
        namestr=getDeafault.getString("account","");
        name.setText(namestr);//yanzhiwei的密码
        Log.i("上次保存的用户名：",namestr+"hahahah"+name.getText());
        Log.i("第一次用默认账户的登录状态：",defaultsta);
        Intent intent = getIntent();
        Log.i("第一次的intent对象", intent.getStringExtra("unbding") + "");
        if("success".equals(intent.getStringExtra("unbding"))){
            Toast.makeText(Login.this,"请绑定新的账户",Toast.LENGTH_SHORT).show();
//            imeilayout.setVisibility(View.VISIBLE);
            namelayout.setVisibility(View.VISIBLE);
            desrc.setVisibility(View.VISIBLE);
        }else if((lastpass.equals(imeiNo)&&((namestr == null ? "" : namestr.trim()).equals(name == null ? "" : name.getText().toString().trim()))&&(defaultsta.equals("0")) &&(!("fail".equals(intent.getStringExtra("fail")))))){
            Log.i("屏蔽用户名和imei","yes");
//            imeilayout.setVisibility(View.GONE);
            namelayout.setVisibility(View.GONE);
            desrc.setVisibility(View.GONE);
            //第一次登录成功之后，直接用密码登录(忘记密码)出现“用户名或密码错误”，显示用户名和密码方便用户登录
            if("pwderror".equals(intent.getStringExtra("pwderror"))){
                namelayout.setVisibility(View.VISIBLE);
                desrc.setVisibility(View.GONE);
            }
        }else if("pwderror".equals(intent.getStringExtra("pwderror"))){
            Log.i("用户名和密码错误", "yes");
            namelayout.setVisibility(View.VISIBLE);
            desrc.setVisibility(View.GONE);
        }else{
            namelayout.setVisibility(View.VISIBLE);
            desrc.setVisibility(View.GONE);
        }

        //初始化数据表的业务
        imageInfoService=ImageInfoService.getInstance(Login.this);
        companyInfoService=CompanyInfoService.getInstance(Login.this);
        iconInfoService=IconInfoService.getInstance(Login.this);
        danDianLoginPubService = DanDianLoginPubService.getInstance(Login.this);


        //获取测试登录的标识符
//        Intent intent = getIntent();
//        if (intent != null && "test".equals(intent.getStringExtra("test"))){
//            Log.i("屏蔽imei", "yes");
//            imeilayout.setVisibility(View.GONE);
//            namelayout.setVisibility(View.VISIBLE);
//        }
//        name.setText("donghong");//绑定大手机的用户名
        //        pass.setText("password");
        Log.i("IMEI码：", imeiNo);
//        name.setText("qinzhengyang");

//        pass.setText("111111");
        imeiedit.setText(imeiNo);//imei码
//        imeiedit.setText("352625061487854");

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        // 判断多选框状态
        if (sp.getBoolean("isCheck", false)) {
            // 设置默认状态记住密码
            checkpass.setChecked(true);
            name.setText(sp.getString("USER_NAME", ""));
            pass.setText(sp.getString("USER_PASS", ""));

        }

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                try {
                    Login(null, null);
//                    //存储树形菜单所需要的值
//                    SharedPreferences tree=getSharedPreferences("treeEle",MainActivity.MODE_PRIVATE);
//                    SharedPreferences.Editor treeEdit=tree.edit();
//                    treeEdit.putString("tree",null);
//                    treeEdit.commit();

                    //删除缓存文件
//                    ArrayList<String> pathList = getAllMountedPath();
//                    for (String devicePath : pathList) {
//                        Log.i("aaaaa",devicePath + File.separator+"JING_SHENG"+File.separator
//                                +"biao5");
//                        //sd卡
//                        deleteFile(devicePath, File.separator+"JING_SHENG"+File.separator+"biao5");
//            //
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        });
        //学习版登录
//        testlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //传一个公共的值过去到每个所需要的特殊登录的页面进行判断
//               // Toast.makeText(LoginActivity.this,"该功能正在完善中",Toast.LENGTH_LONG).show();
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this, SpecialLoginActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    private void Login(String username, String password) {

        account = name.getText().toString();
        pwd=pass.getText().toString();
        imei = imeiedit.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            account = username;
            pwd=password;
        }


        SharedPreferences loginshare=getSharedPreferences("SAVE_LOGIN",MODE_PRIVATE);
        SharedPreferences.Editor editor=loginshare.edit();
        editor.putString("saveimei",imei);
        editor.putString("account", Login.account);
        editor.putString("password",pwd);
        //
        editor.putString("login","login");

        //是否记住密码
        if(remember.isChecked()){
            editor.putBoolean("remember", true);
            editor.putString("account", Login.account);
            editor.putString("pwd",pwd);
            editor.putString("imei",imei);


        }else{
            editor.putBoolean("remember", false);
        }
        //是否自动登录
        if(autologin.isChecked()){
            editor.putBoolean("autologin", true);
        }else{
            editor.putBoolean("autologin", false);
        }
        editor.commit();
        UserLoginImpl getUserLogin = new UserLoginImpl(
                new MyHTTPResultListener(1));
        getUserLogin.login(account, pwd, imei);

        //DDDD
        SharedPreferences testlogin=getSharedPreferences("testlogin",MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor testloginEditor=testlogin.edit();
        testloginEditor.putString("test",null);
        testloginEditor.commit();

    }

    // 获取imei号
    private String  getImei() {
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        SharedPreferences sharedPreferences= getSharedPreferences("SAVE_IMEI",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imei", imei);
        editor.commit();
        return imei;
    }
    class MyHTTPResultListener implements HttpResultListener {
        private int index;

        public MyHTTPResultListener(int index) {
            this.index = index;
//            onResult();
        }

        @Override
        public void onStartRequest() {
            startProgressDialog("正在加载……");
        }

        @Override
        public void onResult(Object obj) {
//            Toast.makeText(LoginActivity.this, "onResult", Toast.LENGTH_SHORT).show();
            if (obj instanceof FailRequest) {
                if (((FailRequest) obj).getStatus().equals("-2")) {
                    Toast.makeText(Login.this,  ((FailRequest) obj).getMsg(),
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(Login.this, LoginActivity.class);
                    intent.putExtra("fail", "fail");
                    startActivity(intent);
                    finish();
                }else if(((FailRequest) obj).getStatus().equals("-1")) {
                    Toast.makeText(Login.this,
                            ((FailRequest) obj).getMsg()+",请重新登录",
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(Login.this, Login.class);
                    intent.putExtra("pwderror","pwderror");
                    startActivity(intent);
                    finish();
                }else if(((FailRequest) obj).getStatus().equals("-3")){
                    Toast.makeText(Login.this,((FailRequest) obj).getMsg(),Toast.LENGTH_SHORT).show();
                }else if(((FailRequest) obj).getStatus().equals("-4")){
                    Toast.makeText(Login.this,((FailRequest) obj).getMsg()
                            ,Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(LoginActivity.this, "else", Toast.LENGTH_SHORT).show();
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        userLogin = (UserLogin) obj;
//                        Toast.makeText(LoginActivity.this, "00", Toast.LENGTH_SHORT).show();
                        if (!(userLogin == null)) {
                            if (!userLogin.getPersonInfobj().getSsid().equals("")
                                    && userLogin.getPersonInfobj().getSsid()!= null) {
                                Context ctx = Login.this;
                                // 保存手机号
                                saveMobileNo();
                                //保存指定用户登录成功的状态
                                SharedPreferences defaultsta =ctx.getSharedPreferences("SAVE_DEFAULT",MODE_PRIVATE);
                                SharedPreferences.Editor editordea=defaultsta.edit();
                                editordea.putString("deastatus", "0");
                                editordea.putString("account",account);
                                compid = userLogin.getPersonInfobj().getCompid();
//                                Toast.makeText(LoginActivity.this, "11", Toast.LENGTH_SHORT).show();
                                boolean isJingshen=false;
                                if(compid.equals("279")) isJingshen=true;
                                editordea.putBoolean("isJingshen",false);

                                editordea.commit();
                                String name = userLogin.getPersonInfobj().getSsid();
                                Log.i(TAG,"SSId==="+name);
                                String contact = userLogin.getPersonInfobj().getContact();
                                userAccount = userLogin.getPersonInfobj().getAccount();
                                Log.i(TAG, "userAccount = " + userAccount);
                                relateAccount = userLogin.getPersonInfobj().getRelateAccount();
                                Log.i(TAG, "relateAccount = " + relateAccount);
                                idcard=userLogin.getPersonInfobj().getIdNo();
                                Log.i(TAG, "idcard = " + idcard);
//                                Toast.makeText(LoginActivity.this, "22", Toast.LENGTH_SHORT).show();
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
                                bundle.putString("compname", compname);
                                bundle.putString("relateAccount",relateAccount);
                                bundle.putString("f_userId",f_userId);
                                bundle.putString("ssid",name);
                                Log.i(TAG,"f_userId===="+f_userId);
//                                Toast.makeText(LoginActivity.this, "33", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this,
                                        MainActivity.class);
//                                Toast.makeText(LoginActivity.this, "44", Toast.LENGTH_SHORT).show();
                                intent.putExtras(bundle);
                                intent.putExtra("compid",compid);
                                intent.putExtra("ssidd",name);
                                startActivity(intent);

                                finish();
                                //登录成功后,当上次登录成功的用户名跟本次登录的用户名不相同的情况下，清楚缓存
                                if(!namestr.equals(userAccount)){
                                    Log.i("更换用户名了呀","ohohohoh");
                                    imageInfoService.deleteAllImageInfo();
                                    companyInfoService.deleteAllCompanyInfo();
                                    iconInfoService.deleteAllIconInfo();
                                    danDianLoginPubService.deleteAllDanDianLoginPub();
                                }
                                Toast.makeText(Login.this, "登录成功",
                                        Toast.LENGTH_SHORT).show();
                                // 将ssid值存入xml中
                                SharedPreferences sp = ctx.getSharedPreferences(
                                        "SP", MODE_PRIVATE);
                                String key = userLogin.getPersonInfobj().getSsid();
                                ssid = key;
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("KEY", key);
                                editor.putString("ACCOUNT",userAccount);
                                editor.commit();
                                // 判断sp中xml路径的值
//                                SharedPreferences shared = ctx
//                                        .getSharedPreferences("XMLPATH",
//                                                MODE_PRIVATE);
                                String path = "/NewEMCAPP";
                                SharedPreferences sharedPath=getSharedPreferences("PATH",LoginActivity.MODE_PRIVATE);
                                SharedPreferences.Editor editorpath=sharedPath.edit();
                                editorpath.putString("path",path);
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
        if(obj instanceof FailRequest){
            FailRequest  fail= (FailRequest)obj;
            if(!(fail==null)){
                System.out.println("异常信息："+fail.getMsg());
                System.out.println("状态："+fail.getStatus());
            }
        }else{

        }
    }

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onFinish() {

    }

    /**
     * 下载APK事件监听
     */
    class GetDownloadApkHttpResultListener implements HttpResultListener {

        String type;

        public GetDownloadApkHttpResultListener(String type) {
            super();
            this.type = type;
        }

        @Override
        public void onStartRequest() {
            pdDialog = new ProgressDialog(Login.this);
            pdDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pdDialog.setTitle("正在下载应用……");
            pdDialog.setIndeterminate(false);
            pdDialog.show();
        }

        @Override
        public void onResult(Object obj) {
            if (ValueConfig.validateObject(obj, Login.this)) {
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
            pdDialog.cancel();
        }

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
     * @param path
     * @param filename
     */
    private void createXML(String path, String filename) {// create or update
        // xmlfile

        File file = new File(path);
        Log.i("调用xml方法",path);
        String xmlpath=path + File.separator + filename;
        Log.i(TAG, "当前处理文件路径：" + xmlpath);
        DocumentFactory factory = DocumentFactory.getInstance();// singleton
        Element root = factory.createElement("msc");
        root.add(factory.createAttribute(root, "ssid", ssid));
        root.add(factory.createAttribute(root, "mobile", account));
        root.add(factory.createAttribute(root, "userAccount", userAccount));
        root.add(factory.createAttribute(root, "relateAccount", relateAccount));
        root.add(factory.createAttribute(root,"idcard",idcard));
        root.add(factory.createAttribute(root,"compid",compid));
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
                    eroot.setAttributeValue("idcard",idcard);
                    eroot.setAttributeValue("compid",compid);
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
        Context ctx = Login.this;
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
                SharedPreferences shared = getSharedPreferences("PATH", LoginActivity. MODE_PRIVATE);
                String path = shared.getString("path", "");
                Log.i("删除文件路径：",path);
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

    public void onResume() {
//        name.setText("");
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
    }
    //    //删除文件
    private void deleteFile(String path, String fileName) {
        File df = new File(path + File.separator + fileName);
        Log.i("papapapa", fileName);
        if (df.exists()) {
            df.delete();
//            Log.i("booloooo", str + "");
            Log.i(TAG,fileName);
            //删除文件
            Toast.makeText(this,"文件删除成功" +path + File.separator + fileName
                    ,Toast.LENGTH_SHORT).show();

        } else {
            Log.i("TAG", "文件不存在");
        }
    }

}
