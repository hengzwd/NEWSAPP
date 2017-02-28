package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.UserList;
import newsemc.com.awit.news.newsemcapp.dao.DepartsInfo;
import newsemc.com.awit.news.newsemcapp.dao.LoginInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.UserListInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 人员列表(暂无用到)
 * Created by Administrator on 2015/7/3.
 */
public class UserListImpl implements UserListInter {
    private String TAG="UserListImpl";
    private UserList userListobj;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public UserListImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getUserList(String ssid, String pageNo, String pageSize) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ssid", ssid);
        params.put("pageNo", pageNo);
        params.put("pageSize",pageSize);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "user/userlist!execute.action";
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
                            if (!(jsonObject.getString("status").equals("0"))) {
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.getString("status"));
                                failRequestobj.setMsg(jsonObject.getString("msg"));
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
                        userListobj=new UserList();
                        JSONArray jsonArr;
                        JSONObject jsonObject;
                        JSONObject failjsonobj;
                        String data = null;
                        try {
                            String str = new String(arg2, "UTF-8");
                            Log.i(TAG, str);
                            jsonObject= new JSONObject(str);
                            String s = jsonObject.getString("status");
                            Log.i(TAG, s);
                            if(Integer.parseInt(s) >0){
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.getString("status"));
                                failRequestobj.setMsg(jsonObject.getString("msg"));
                                httpResultListener.onResult(failRequestobj);
                            }else{
                                String dataobj=jsonObject.getString("data");
                                Log.i(TAG, dataobj);
                                JSONObject datao=new JSONObject(dataobj);
                                JSONArray dataarr=new JSONArray(datao.getString("list"));
                                Log.i(TAG, dataarr.toString());
                                List<LoginInfo> loginInfoList=new ArrayList<LoginInfo>();
                                for (int i = 0; i < dataarr.length(); i++) {
                                    data = dataarr.get(i).toString();
                                    jsonObject = new JSONObject(data);
                                    JSONArray loginarr=new JSONArray(jsonObject.getString("logins"));
                                    for(int k=0;k<loginarr.length();k++){
                                        String logindata=loginarr.get(k).toString();
                                        JSONObject logindataobj=new JSONObject(logindata);
                                        LoginInfo loginInfobj=new LoginInfo();
                                        loginInfobj.setUserId(logindataobj.getInt("userId")+"");
                                        loginInfobj.setAccount(logindataobj.getString("account"));
                                        loginInfobj.setName(logindataobj.getString("name"));
                                        loginInfobj.setContact(logindataobj.getString("contact"));
                                        loginInfoList.add(loginInfobj);
                                        JSONArray departarr=new JSONArray(logindataobj.getString("departments"));
                                        List<DepartsInfo> departsInfoList=new ArrayList<DepartsInfo>();
                                        for(int j=0;j<departarr.length();j++){
                                            String deptdata=departarr.get(j).toString();
                                            JSONObject deptjson=new JSONObject(deptdata);
                                            DepartsInfo departsInfo=new DepartsInfo();
                                            departsInfo.setId(deptjson.getInt("id")+"");
                                            departsInfo.setName(deptjson.getString("name"));
                                            departsInfo.setDuty(deptjson.getString("duty"));
                                            departsInfoList.add(departsInfo);
                                        }
                                        userListobj.setDepartsInfoList(departsInfoList);
                                    }
                                    userListobj.setLoginInfoList(loginInfoList);


                                }
                                httpResultListener.onResult(userListobj);
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
