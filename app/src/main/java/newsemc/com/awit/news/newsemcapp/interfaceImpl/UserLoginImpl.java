package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socks.library.KLog;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.RelateaAccountLoginBean;
import newsemc.com.awit.news.newsemcapp.bean.UserLogin;
import newsemc.com.awit.news.newsemcapp.dao.DepartmentInfo;
import newsemc.com.awit.news.newsemcapp.dao.PersonInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.UserLoginInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 用户登录接口实现
 * Created by Administrator on 2015/7/3.
 */
public class UserLoginImpl implements UserLoginInter {
    private String TAG = "UserLoginImpl";
    private UserLogin userLoginobj;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;


    public UserLoginImpl(HttpResultListener httpResultListener) {
        this.httpResultListener = httpResultListener;
    }

    private Handler handler = new Handler();
    private Message msg = handler.obtainMessage();

    @Override
    public void login(String account, String mobile, String imei) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("mobile", mobile);
        params.put("imei", imei);
        Log.i(TAG, "imei = " + imei);
        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "user/login!executex.action";
        client.post(
                url,
                params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        System.out.println("fail");
                        try {
                            Log.i(TAG, "onFailure:" + arg2);
                            String str = new String(arg2, "UTF-8");
                            JSONObject jsonObject = new JSONObject(str);
                            Log.i("url", arg3.toString());
                            if (!(jsonObject.optString("status").equals("0"))) {
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.optString("status"));
                                failRequestobj.setMsg(jsonObject.optString("msg"));
                                httpResultListener.onResult(failRequestobj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        System.out.println("success");
                        Log.i(TAG, "succ");
                        userLoginobj = new UserLogin();
                        JSONArray jsonArr;
                        JSONObject jsonObject;
                        JSONObject failjsonobj;
                        String data = null;
                        try {
                            String str = new String(arg2, "UTF-8");
                            KLog.e(TAG, str);
                            jsonObject = new JSONObject(str);
                            String s = jsonObject.optString("status");
                            KLog.e(TAG, s);
                            if (Integer.parseInt(s) < 0) {
                                KLog.e(TAG, "loginFailed");
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.optString("status"));
                                failRequestobj.setMsg(jsonObject.optString("msg"));
                                httpResultListener.onResult(failRequestobj);
                            } else {
                                String dataobj = jsonObject.optString("data");
                                KLog.e(TAG, dataobj);
                                JSONObject datao = new JSONObject(dataobj);
                                JSONArray dataarr = new JSONArray(datao.optString("list"));
                                Log.i(TAG, dataarr.toString());
                                for (int i = 0; i < dataarr.length(); i++) {
                                    data = dataarr.get(i).toString();
                                    jsonObject = new JSONObject(data);
                                    PersonInfo personInfobj = new PersonInfo();
                                    personInfobj.setUserId(jsonObject.optString("userId"));
                                    personInfobj.setSex(jsonObject.optString("sex"));
                                    personInfobj.setSsid(jsonObject.optString("ssid"));
                                    personInfobj.setAccount(jsonObject.optString("account"));
                                    personInfobj.setRelateAccount(jsonObject.optString("relateAccount"));
                                    personInfobj.setName(jsonObject.optString("name"));
                                    personInfobj.setContact(jsonObject.optString("contact"));
                                    personInfobj.setCompname(jsonObject.optString("compname"));
                                    personInfobj.setIdNo(jsonObject.optString("idNo"));
                                    personInfobj.setCompid(jsonObject.optString("compid"));
                                    JSONArray jsonArray = new JSONArray();
                                    jsonArray = jsonObject.optJSONArray("switchers");
//                                    jsonArray=jsonObject.optJSONArray("relateAccount");
                                    Gson gson=new Gson();
                                    ArrayList<RelateaAccountLoginBean.DataEntity.ListEntity.SwitchersEntity> list = new ArrayList<RelateaAccountLoginBean.DataEntity.ListEntity.SwitchersEntity>();

                                    if (jsonArray != null) {
                                        for (int x = 0; x < jsonArray.length(); x++) {
                                            list.add( gson.fromJson(jsonArray.get(x).toString(), RelateaAccountLoginBean.DataEntity.ListEntity.SwitchersEntity.class));

                                        }
                                    }

                                    personInfobj.setSwitchers(list);
                                    userLoginobj.setPersonInfobj(personInfobj);
                                    JSONArray departarr = new JSONArray(jsonObject.optString("departments"));
                                    Log.i(TAG, "shaorc" + departarr);
                                    List<DepartmentInfo> departmentInfoList = new ArrayList<DepartmentInfo>();
                                    for (int j = 0; j < departarr.length(); j++) {
                                        String deptdata = departarr.get(j).toString();
                                        JSONObject deptjson = new JSONObject(deptdata);
                                        DepartmentInfo departmentInfobj = new DepartmentInfo();
                                        departmentInfobj.setId(deptjson.optString("id"));
                                        departmentInfobj.setName(deptjson.optString("name"));
                                        departmentInfobj.setDuty(deptjson.optString("duty"));
                                        departmentInfoList.add(departmentInfobj);
                                    }
                                    userLoginobj.setDepartmentInfoList(departmentInfoList);
                                }
                                NewsEMCAppllication.mUserLogin = userLoginobj;
                                KLog.e("getUserId:" + userLoginobj.getPersonInfobj().getUserId());
                                httpResultListener.onResult(userLoginobj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onStart() {
                        super.onStart();
                        httpResultListener.onStartRequest();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        httpResultListener.onFinish();
                    }
                });
    }
}
