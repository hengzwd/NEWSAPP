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
import newsemc.com.awit.news.newsemcapp.dao.FirstMenuInfo;
import newsemc.com.awit.news.newsemcapp.dao.SecondMenuInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.SecondMenuInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by cll on 2015/11/5.
 */
public class SecondMenuImpl implements SecondMenuInter {
    private String TAG="SecondMenuImpl";
    private List<SecondMenuInfo> secondMenuInfoList;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public SecondMenuImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getSecondMenu(String pid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("pid", pid);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "inter/inter!getSonMenuList.action";
//        String url = "http://192.168.1.109:8080/gateway/inter/inter!interImage.action";
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
                    //成功时，对返回的json数据进行解析
                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        System.out.println("success");
                        Log.i(TAG, "succ");
                        secondMenuInfoList=new ArrayList<SecondMenuInfo>();
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
                                    SecondMenuInfo secondMenuInfobj = new SecondMenuInfo();
                                    secondMenuInfobj.setId(jsonObject.getString("id"));
                                    secondMenuInfobj.setLevel(jsonObject.getInt("level"));
                                    secondMenuInfobj.setName(jsonObject.getString("name"));
                                    secondMenuInfobj.setPid(jsonObject.getString("pid"));
                                    secondMenuInfobj.setCode(jsonObject.getString("code"));
                                    secondMenuInfobj.setType(jsonObject.getInt("type"));
                                    secondMenuInfoList.add(secondMenuInfobj);
                                }
                                httpResultListener.onResult(secondMenuInfoList);
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
