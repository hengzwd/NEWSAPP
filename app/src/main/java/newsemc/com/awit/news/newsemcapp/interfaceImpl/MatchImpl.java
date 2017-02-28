package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.bean.FailRequest;

import newsemc.com.awit.news.newsemcapp.bean.Match;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.MatchInter;

/**
 * Created by shaorc on 2016-08-18.
 */
public class MatchImpl implements MatchInter {
    private AppCallBack callBack;
    private String TAG="MatchImpl";
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    private Match matchobj ;
    private String s;
//    public MatchImpl(HttpResultListener httpResultListener, AppCallBack callBack){
//        this.callBack = callBack;
//        this.httpResultListener=httpResultListener;
//    }
    public MatchImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getMatchAppId(String userId, String appId) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ssid", userId);
        params.put("appId",appId);
        Log.i("接口ssid",userId);
        Log.i("接口appid-------",appId +"");
        String url = "http://www.r93535.com/gatewaytest/inter/inter!getMatchAppId.action?userId=jslnqinzhengyang&appId=2";
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                System.out.println("success");
                Log.i(TAG, "succ");
                JSONObject jsonObject;
                try {
                    String str = new String(bytes, "UTF-8");
                    Log.i(TAG, str);
                    jsonObject= new JSONObject(str);
                    String s = jsonObject.getString("status");
                    Log.i(TAG, s);
                    if(Integer.parseInt(s) >0){
                        failRequestobj = new FailRequest();
                        failRequestobj.setStatus(jsonObject.getString("status"));
                        failRequestobj.setMsg(jsonObject.getString("msg"));
                        httpResultListener.onResult(failRequestobj);
                    }else {
                    matchobj=new Match();
//                    matchobj.setAppid(jsonObject.optString("appId"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try {
//                    JSONObject jsonObject=new JSONObject(new String(bytes));
//                    matchobj=new Match();
//                    matchobj.setAppid(jsonObject.optString("appId"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                httpResultListener.onResult(matchobj);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("fail");
                try {
                    Log.i(TAG, "onFailure:" + bytes);
                    String str = new String(bytes, "UTF-8");
                    JSONObject jsonObject = new JSONObject(str);
                    Log.i("url", throwable.toString());
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
        });
    }
    /**
     * AppResult
     */
    public interface AppCallBack {
        public void myResult (List<Match> result);
        public void myResultFailure (FailRequest failRequestobj);
    }
}
