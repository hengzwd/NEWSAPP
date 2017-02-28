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
import newsemc.com.awit.news.newsemcapp.bean.PersonLoginInfo;
import newsemc.com.awit.news.newsemcapp.dao.DeptsInfo;
import newsemc.com.awit.news.newsemcapp.dao.SsidInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.LoginSsidInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 人员信息接口实现
 * Created by Administrator on 2015/7/3.
 */
public class PersonLoginImpl implements LoginSsidInter {
    private String TAG="PersonLoginImpl";
    private PersonLoginInfo personLoginInfobj;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public PersonLoginImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getSsidInfo(String ssid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ssid", ssid);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "user/logininfopwd!execute.action";
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
                        personLoginInfobj=new PersonLoginInfo();
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
                            if(Integer.parseInt(s) !=0){
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
                                for (int i = 0; i < dataarr.length(); i++) {
                                    data = dataarr.get(i).toString();
                                    jsonObject = new JSONObject(data);
                                    SsidInfo ssidInfobj=new SsidInfo();
                                    ssidInfobj.setUserId(jsonObject.getString("userId"));
                                    ssidInfobj.setAccount(jsonObject.getString("account"));
                                    ssidInfobj.setName(jsonObject.getString("name"));
                                    ssidInfobj.setContact(jsonObject.getString("contact"));
                                    ssidInfobj.setSex(jsonObject.getString("sex"));
                                    ssidInfobj.setSsid(jsonObject.getString("ssid"));
                                    personLoginInfobj.setSsidInfobj(ssidInfobj);
                                    JSONArray departarr=new JSONArray(jsonObject.getString("departments"));
                                    List<DeptsInfo> deptsInfoList=new ArrayList<DeptsInfo>();
                                    for(int j=0;j<departarr.length();j++){
                                        String deptdata=departarr.get(j).toString();
                                        JSONObject deptjson=new JSONObject(deptdata);
                                        DeptsInfo deptsInfobj=new DeptsInfo();
                                        deptsInfobj.setId(deptjson.getString("id"));
                                        deptsInfobj.setName(deptjson.getString("name"));
                                        deptsInfobj.setDuty(deptjson.getString("duty"));
                                        deptsInfoList.add(deptsInfobj);
                                    }
                                    personLoginInfobj.setDeptsInfoList(deptsInfoList);
                                }
                                httpResultListener.onResult(personLoginInfobj);
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
