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
import newsemc.com.awit.news.newsemcapp.dao.ZdgcInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.ZdgcInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 *已关闭
 * Created by Administrator on 2015/7/4.
 */
public class ZdgcImpl implements ZdgcInter {
    private String TAG="ZdgcImpl";
    private List<ZdgcInfo> zdgcInfoList;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public ZdgcImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }

    @Override
    public void getZdgcList(String ssid,String projectinfoid, String pageno, String pagesize) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("projectinfoid", projectinfoid);
        params.put("pageno", pageno);
        params.put("pagesize", pagesize);
        params.put("ssid",ssid);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "inter/inter!getZdgc.action";
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
                        zdgcInfoList=new ArrayList<ZdgcInfo>();
                        JSONArray jsonArr;
                        JSONObject jsonObject;
                        JSONObject failjsonobj;
                        String data = null;
                        try {
                            String str = new String(arg2, "UTF-8");
                            Log.i(TAG, str);
                            jsonObject= new JSONObject(str);
                            String s = jsonObject.getString("status");
                            Log.i(TAG,s);
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
                                for (int i = 0; i < dataarr.length(); i++) {
                                    data = dataarr.get(i).toString();
                                    jsonObject = new JSONObject(data);
                                    ZdgcInfo zdgcInfobj=new ZdgcInfo();
                                    zdgcInfobj.setBuildname(jsonObject.getString("buildname"));
                                    zdgcInfobj.setDesigntype(jsonObject.getString("designtype"));
                                    zdgcInfobj.setDesignnum(jsonObject.getString("designnum"));
                                    zdgcInfobj.setDayfinish(jsonObject.getString("dayfinish"));
                                    zdgcInfobj.setMothfinish(jsonObject.getString("mothfinish"));
                                    zdgcInfobj.setKailei(jsonObject.getString("kailei"));
                                    zdgcInfobj.setKaileiratio(jsonObject.getString("kaileiratio"));
                                    zdgcInfobj.setPlandate(jsonObject.getString("plandate"));
                                    zdgcInfobj.setDelaynum(jsonObject.getString("delaynum"));
                                    zdgcInfobj.setDelaydays(jsonObject.getString("delaydays"));
                                    zdgcInfoList.add(zdgcInfobj);
                                }
                                httpResultListener.onResult(zdgcInfoList);
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
