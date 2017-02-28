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
import newsemc.com.awit.news.newsemcapp.bean.UserLogin;
import newsemc.com.awit.news.newsemcapp.dao.DepartsInfo;
import newsemc.com.awit.news.newsemcapp.dao.IconInfo;
import newsemc.com.awit.news.newsemcapp.dao.LoginInfo;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.SpecialAccountInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * 特殊账号登录接口实现
 * Created by cll on 2015/10/10.
 */
public class SpecialLoginImpl implements SpecialAccountInter{
    private String TAG="SpecialLoginImpl";
    private List<SpecialAccountInfo> specialAccountInfoList;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public SpecialLoginImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }

    @Override
    public void specialLogin(String account, String mobile, String imei) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("mobile", mobile);
        params.put("imei",imei);
//        client.setTimeout(ValueConfig.TIME_OUT);
//        String url = "http://www.r93535.com/gateway/inter/inter!interImage.action";
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
                        specialAccountInfoList=new ArrayList<SpecialAccountInfo>();
                        JSONArray jsonArr;
                        JSONObject jsonObject;
                        String data = null;
                        try {
                            String str = new String(arg2, "UTF-8");
                            Log.i(TAG, str);
                            jsonObject= new JSONObject(str);
                            String s = jsonObject.getString("status");
                            Log.i(TAG,s);
                            if(Integer.parseInt(s) !=0){
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.getString("status"));
                                failRequestobj.setMsg(jsonObject.getString("msg"));
                                httpResultListener.onResult(failRequestobj);
                            }else {
                                String dataobj=jsonObject.getString("data");
                                Log.i(TAG, dataobj);
                                JSONObject datao=new JSONObject(dataobj);
                                JSONArray dataarr=new JSONArray(datao.getString("list"));
                                Log.i(TAG, dataarr.toString());
                                for (int i = 0; i < dataarr.length(); i++) {
                                    data = dataarr.get(i).toString();
                                    jsonObject = new JSONObject(data);
                                    SpecialAccountInfo specialAccountInfobj=new SpecialAccountInfo();
                                    specialAccountInfobj.setCompanyid(jsonObject.getString("companyid"));
                                    specialAccountInfobj.setCompanyname(jsonObject.getString("companyname"));
                                    specialAccountInfobj.setAccount(jsonObject.getString("account"));
                                    specialAccountInfobj.setPassword(jsonObject.getString("password"));
                                    specialAccountInfobj.setImei(jsonObject.getString("imei"));
                                    specialAccountInfoList.add(specialAccountInfobj);
                                }
                                httpResultListener.onResult(specialAccountInfoList);
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
