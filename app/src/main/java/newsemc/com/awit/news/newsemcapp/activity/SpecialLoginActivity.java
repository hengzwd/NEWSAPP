package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.UserLogin;
import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfo;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfo;
import newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.SpecialLoginImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.service.CompanyInfoService;
import newsemc.com.awit.news.newsemcapp.service.DanDianLoginPubService;
import newsemc.com.awit.news.newsemcapp.service.IconInfoService;
import newsemc.com.awit.news.newsemcapp.service.ImageInfoService;

/**
 * Created by Administrator on 15-10-10.
 */
public class SpecialLoginActivity extends Activity implements HttpResultListener{
    private final String TAG = "SpecialLoginActivity";
    private EditText name, pass;
    //    private CheckBox checkpass;
    private Button login;
    private CustomProgressDialog progressDialog = null;
    private static String account,pwd,imei;
    //动态获取imei
    // private String imei="";
    //学习版登录的用户名和密码
    private String username,mobile;
    //
    private List<SpecialAccountInfo> specialAccountInfoArrayList;
    private HttpResultListener hrl;
    private long exitTime = 0;
    private RelativeLayout passlayout,namelayout,imeilayout;
    //保存上次imei
    private String imeiNo;
    private TelephonyManager telephonyManager;
    private String phoneNum;
    private ProgressDialog pdDialog;
    private static String ssid;
    private static String userAccount;
    private static String idcard;
    private String firststa="5";//成功登录的状态码
    private String status;
    private String successsta="10",exceptionsta="10",loginsta="10",exsta="10";
    private boolean flag=false;
    private  SharedPreferences success,getsta;
    //
    private String companyid,companyname,accounta,password,imeii=" ";

    //测试人员账号登录
    //private TextView testlogin;

    private TextView desrc;

    private String namestr;


    //数据表的定义
    private ImageInfoService imageInfoService;
    private CompanyInfoService companyInfoService;
    private IconInfoService iconInfoService;
    private DanDianLoginPubService danDianLoginPubService;
    //    private Flag flagobj=new Flag();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.speciallogin);
        name = (EditText) findViewById(R.id.nameinput);
        pass = (EditText) findViewById(R.id.passinput);
//        //     imeiedit=(EditText)findViewById(R.id.imeiinput);
////        checkpass = (CheckBox) findViewById(R.id.ckeckpass);
//        passlayout = (RelativeLayout) findViewById(R.id.passlayout);
        namelayout=(RelativeLayout)findViewById(R.id.namelayout);
//        //    imeilayout=(RelativeLayout)findViewById(R.id.imeilayout);
        login = (Button) findViewById(R.id.login);
//        success=getSharedPreferences("SAVE_SUCSTA", MODE_PRIVATE);
//        getsta=getSharedPreferences("SAVE_STA", MODE_PRIVATE);
        //testlogin=(TextView)findViewById(R.id.testlogin);
      //  desrc=(TextView)findViewById(R.id.decripe);

        // 获取imei号
          imeiNo=getImei();
//        imeiNo="355637051680098";
//        imeiNo="352625061487854";
//        imeiNo="353492045124551";
        Log.i("动态获取imei号：",imeiNo);;

        //保存第一次登录的imei码
        SharedPreferences getLogin=getSharedPreferences("SAVE_LOGIN",MODE_PRIVATE);
        String lastpass=getLogin.getString("saveimei","");
        Log.i("第二次登录imei：", lastpass);
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
            Toast.makeText(SpecialLoginActivity.this,"请绑定新的账户",Toast.LENGTH_SHORT).show();
            // imeilayout.setVisibility(View.VISIBLE);
            namelayout.setVisibility(View.VISIBLE);
           // desrc.setVisibility(View.VISIBLE);
        }else if((lastpass.equals(imeiNo)&&((namestr == null ? "" : namestr.trim()).equals(name == null ? "" : name.getText().toString().trim()))&&(defaultsta.equals("0")) &&(!("fail".equals(intent.getStringExtra("fail")))))){
            Log.i("屏蔽用户名和imei", "yes");
            // imeilayout.setVisibility(View.GONE);
            namelayout.setVisibility(View.GONE);
           // desrc.setVisibility(View.GONE);
        }else if("pwderror".equals(intent.getStringExtra("pwderror"))){
            Log.i("用户名和密码错误", "yes");
            // imeilayout.setVisibility(View.GONE);
           // desrc.setVisibility(View.GONE);
        }else{
//            imeilayout.setVisibility(View.VISIBLE);
            namelayout.setVisibility(View.VISIBLE);
            //desrc.setVisibility(View.GONE);
        }
        //初始化数据表的业务
        imageInfoService=ImageInfoService.getInstance(SpecialLoginActivity.this);
        companyInfoService=CompanyInfoService.getInstance(SpecialLoginActivity.this);
        iconInfoService=IconInfoService.getInstance(SpecialLoginActivity.this);
        danDianLoginPubService=DanDianLoginPubService.getInstance(SpecialLoginActivity.this);
//        // 判断多选框状态
//        if (spec.getBoolean("isCheck", false)) {
//            // 设置默认状态记住密码
//            //checkpass.setChecked(true);
//            name.setText(spec.getString("USER_NAME", ""));
//            pass.setText(spec.getString("USER_PASS", ""));
//
//        }

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                try {
                    account = name.getText().toString();
                    pwd=pass.getText().toString();
                    // imei = imeiedit.getText().toString();
                    SharedPreferences loginshare=getSharedPreferences("SAVE_LOGIN",MODE_PRIVATE);
                    SharedPreferences.Editor editor=loginshare.edit();
                    //editor.putString("saveimei",imei);
                    editor.putString("account",account);
                    editor.putString("password", pwd);
                    editor.commit();
                    Log.i("account：：：：：：", account);
                    Log.i("password：：：：：：", pwd);
                    Log.i("imei:::::::",imeii);
                    //Log.i("IMEI::::::",imei);
//                    UserLoginImpl getUserLogin = new UserLoginImpl(
//                            new MyHTTPResultListener(1));
//                    getUserLogin.login(account, pwd, imei);
                    SpecialLoginImpl specialLoginImpl=new SpecialLoginImpl(SpecialLoginActivity.this);
                    specialLoginImpl.specialLogin(account, "", "");


                    //DDDD
                    SharedPreferences testlogin=getSharedPreferences("testlogin",SpecialLoginActivity.MODE_PRIVATE);
                    SharedPreferences.Editor testloginEditor=testlogin.edit();
                    testloginEditor.putString("test","test");
                    testloginEditor.commit();


                    //pwd
                    //点击学习版登录跳转到SpinnerActivity
                    Intent intent1=new Intent(SpecialLoginActivity.this,SpinnerActivity.class);
                    startActivity(intent1);
                    Toast.makeText(SpecialLoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onStartRequest() {

    }


    @Override
    public void onResult(Object obj) {
        // Log.i("zzzzzz","已进入");
        if(obj instanceof FailRequest){
            FailRequest  fail= (FailRequest)obj;
            if (((FailRequest) obj).getStatus().equals("-2")) {
                Toast.makeText(SpecialLoginActivity.this,  ((FailRequest) obj).getMsg(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(SpecialLoginActivity.this, SpecialLoginActivity.class);
                intent.putExtra("fail", "fail");
                startActivity(intent);
                finish();
            }else if(((FailRequest) obj).getStatus().equals("-1")) {
                Log.i("1------异常消息",fail.getMsg());
                Toast.makeText(SpecialLoginActivity.this, ((FailRequest) obj).getMsg(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(SpecialLoginActivity.this, SpecialLoginActivity.class);
                intent.putExtra("pwderror","pwderror");
                startActivity(intent);
                finish();
            }
        }
        else {
            Log.i("SpecialLoginActivity","neirong");
            UserLogin userLogin = (UserLogin) obj;
            userLogin.getPersonInfobj().getAccount();
            Log.i("hahahhaaccount", userLogin.getPersonInfobj().getAccount());
            userLogin.getPersonInfobj().getSsid();
            Log.i("hahahhaaccount", userLogin.getPersonInfobj().getSsid());
            if (!(userLogin == null)) {
                if (!userLogin.getPersonInfobj().getSsid().equals("")
                        && userLogin.getPersonInfobj().getSsid()!= null) {
                    Context ctx = SpecialLoginActivity.this;

                        SharedPreferences spec1 = getSharedPreferences("speciallogin1", MODE_PRIVATE);
                        SharedPreferences.Editor speceditor=spec1.edit();
                        speceditor.putString("special","special");
                        speceditor.commit();
                        //保存指定用户登录成功的状态
//                            SharedPreferences defaultsta =ctx.getSharedPreferences("SPEC",SpinnerActivity.MODE_PRIVATE);
//                            SharedPreferences.Editor editordea=defaultsta.edit();
//                            editordea.putString("deastatus", "0");
//                            editordea.putString("account",userLogin.getPersonInfobj().getAccount());
//                            Log.i("aacountaaaaaa", userLogin.getPersonInfobj().getAccount());
//                            editordea.commit();
                        String name = userLogin.getPersonInfobj().getSsid();
                        String contact = userLogin.getPersonInfobj().getContact();
                        userAccount = userLogin.getPersonInfobj().getAccount();
                        Log.i("userAccount",userAccount);
                        idcard=userLogin.getPersonInfobj().getIdNo();
                        Log.i("idcard",idcard);
                        String compname = userLogin.getPersonInfobj().getCompname();
                        List<DepartmentInfo> department = userLogin
                                .getDepartmentInfoList();
                        String duty = department.get(0).getDuty();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putString("duty", duty);
                        bundle.putString("contact", contact);
                        bundle.putString("compname",compname);
                        Log.i("name",name);

                        Intent intent = new Intent(SpecialLoginActivity.this,
                                MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                        //登录成功后,当上次登录成功的用户名跟本次登录的用户名不相同的情况下，清楚缓存
                        if(!namestr.equals(userAccount)){
                            Log.i("更换用户名了呀。。。。。", "ohohohoh");
                            imageInfoService.deleteAllImageInfo();
                            companyInfoService.deleteAllCompanyInfo();
                            iconInfoService.deleteAllIconInfo();
                            danDianLoginPubService.deleteAllDanDianLoginPub();
                        }
                        Toast.makeText(SpecialLoginActivity.this, "登录成功",
                                Toast.LENGTH_SHORT).show();
                        // 将ssid值存入xml中
                        SharedPreferences spec = ctx.getSharedPreferences(
                                "SPEC", MODE_PRIVATE);
                        String key = userLogin.getPersonInfobj().getSsid();
                        ssid = key;
                        String key1=userLogin.getPersonInfobj().getAccount();
                        account=key1;
                        SharedPreferences.Editor editor = spec.edit();
                        editor.putString("KEY", key);
                        editor.putString("ACCOUNT",key1);
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

            //获取接口的数据
            specialAccountInfoArrayList=(List<SpecialAccountInfo>)obj;
            //---------------------------- 在这直接复制给Application的变量，在你需要使用的地方直接通过这种方式 NewsEMCAppllication.getApplication().specialAccountInfoList 使用
            NewsEMCAppllication.getApplication().specialAccountInfoList=specialAccountInfoArrayList;
            Log.i("公司名称", specialAccountInfoArrayList.get(0).getCompanyname());
        }
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



    // 获取imei号
    private String  getImei() {
        // TODO Auto-generated method stub
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        Log.i("Spinner获取的imei号", imei);
        SharedPreferences sharedPreferences= getSharedPreferences("SAVE_IMEI",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imei", imei);
        editor.commit();
        return imei;
    }
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
        root.add(factory.createAttribute(root,"idcard",idcard));
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
                    eroot.setAttributeValue("idcard",idcard);
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
    //
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
    //
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
}
