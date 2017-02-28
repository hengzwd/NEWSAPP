package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import newsemc.com.awit.news.newsemcapp.bean.AlterPwd;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.interfaces.AlterPwdInter;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 修改密码接口实现
 * Created by sb on 2015/7/30.
 */
public class AlterPwdImpl implements AlterPwdInter {
    private String TAG="AlterPwdImpl";
    private AlterPwd alterPwdobj;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public AlterPwdImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getAlterPwdInfo(String ssid, String userId, String oldpassword, String newpassword) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("ssid", ssid);
        params.put("userId",userId);
        params.put("oldpassword",oldpassword);
        params.put("newpassword",newpassword);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "inter/inter!updatePwd.action";
        client.post(
//                ValueConfig.PCAPP_URL + "inter!getItembaseMsg.action",
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
                            alterPwdobj=new AlterPwd();
                            alterPwdobj.setStatus(jsonObject.getString("status"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        httpResultListener.onResult(alterPwdobj);
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
