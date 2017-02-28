package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.FeedBack;
import newsemc.com.awit.news.newsemcapp.bean.Logout;
import newsemc.com.awit.news.newsemcapp.interfaces.FeedBackInter;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * 消息推送
 * Created by sb on 2015/9/1.
 */
public class FeedBackImpl implements FeedBackInter{
    private String TAG="FeedBackImpl";
    private FeedBack feedBackobj;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public FeedBackImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }

    @Override
    public void uploadAdvice(String ssid,String imei,String appid,String advice,String title,String contact) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ssid", ssid);
        params.put("imei", imei);
        params.put("appid", appid);
        params.put("advice", advice);
        params.put("title", title);
        params.put("contact", contact);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "inter/inter!getFeedBack.action";
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
                        Log.i(TAG, new String(arg2));
                        try {
                            JSONObject jsonObject=new JSONObject(new String(arg2));
                            feedBackobj=new FeedBack();
                            feedBackobj.setStatus(jsonObject.getString("status"));
                            feedBackobj.setMsg(jsonObject.getString("msg"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        httpResultListener.onResult(feedBackobj);
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
                }
        );
    }
}
