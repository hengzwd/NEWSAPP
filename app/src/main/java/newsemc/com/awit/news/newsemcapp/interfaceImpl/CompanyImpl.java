package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.CompanyNewInter;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by Administrator on 2015/10/17 0017.
 */
public class CompanyImpl implements CompanyNewInter {

    private String TAG = "CompanyImpl";
    private CompanyInfo companyInfo;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;

    public CompanyImpl(HttpResultListener httpResultListener) {
        this.httpResultListener = httpResultListener;
    }



    public void getCompanyInfoList(String ssid, String userId, String infotype,String Infosontype, String pageno, String pagesize, String ifhead) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("userId", userId);
        params.put("infotype", infotype);
        params.put("pageno", pageno);
        params.put("pagesize", pagesize);
        params.put("ifhead", ifhead);
        params.put("ssid",ssid);
        params.put("Infosontype",Infosontype);
        client.setTimeout(ValueConfig.TIME_OUT);
        String url =  ValueConfig.PCAPP_URL + "inter/inter!jsInfoList.action";

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {


                System.out.println("success");
                Log.i(TAG, "succ");
                JSONArray jsonArr;
                JSONObject jsonObject;
                JSONObject failjsonobj;
                String data = null;
                try {
                    String str = new String(bytes, "UTF-8");
                    Log.i(TAG, str);
                    jsonObject= new JSONObject(str);
                    String s = jsonObject.getString("status");
                    Log.i(TAG, s);
                    if (Integer.parseInt(s) >0) {
                        failRequestobj = new FailRequest();
                        failRequestobj.setStatus(jsonObject.getString("status"));
                        failRequestobj.setMsg(jsonObject.getString("msg"));
                        httpResultListener.onResult(failRequestobj);
                    }else{
                        String dataobj=jsonObject.getString("data");
                        Log.i(TAG, dataobj);
                        JSONObject datao=new JSONObject(dataobj);

//                        JSONArray dataarr=datao.getJSONArray(datao.getString("list"));

                        JSONArray dataarr=new JSONArray(datao.getString("list"));

                        Log.i(TAG, dataarr.toString());

                        ArrayList<CompanyInfo> arr=new ArrayList<CompanyInfo>();
                        for (int j = 0; j < dataarr.length(); j++) {
                            data = dataarr.get(j).toString();
                            jsonObject = new JSONObject(data);
                            companyInfo=new CompanyInfo();
                            companyInfo.setInfoid(jsonObject.getString("infoid"));
                            companyInfo.setInfodate(jsonObject.getString("infodate"));
                            companyInfo.setInfotype(jsonObject.getString("infotype"));
                            companyInfo.setInfoname(jsonObject.getString("infoname"));
                            companyInfo.setInfoimg(jsonObject.getString("infoimg"));
                            companyInfo.setInfourl(jsonObject.getString("infourl"));
                            companyInfo.setInfocontent(jsonObject.getString("infocontent"));
                            companyInfo.setPageno(jsonObject.getString("pageno"));
                            companyInfo.setNum(jsonObject.getString("num"));
                            companyInfo.setIsnew(jsonObject.getString("isnew"));
                            companyInfo.setSource(jsonObject.getString("source"));
                            arr.add(companyInfo);
                        }
                        httpResultListener.onResult(arr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("fail");
                try {
                    Log.i(TAG, "onFailure:" + bytes);
                    String str = new String(bytes, "UTF-8");
                    JSONObject jsonObject = new JSONObject(str);
                    Log.i("url", throwable.toString());
                    if ((jsonObject.getString("status").equals("1"))) {
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
