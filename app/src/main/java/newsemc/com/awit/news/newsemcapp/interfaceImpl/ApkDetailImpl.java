package newsemc.com.awit.news.newsemcapp.interfaceImpl;

import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socks.library.KLog;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.test.AbstractDaoTestLongPk;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.ApkUpdateInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.ApkDetailInter;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 获取apk详情信息接口
 * Created by sb on 2015/8/5.
 */
public class ApkDetailImpl implements ApkDetailInter {
    private AppCallBack callBack;
    private String TAG="ApkDetailImpl";
    private List<ApkUpdateInfo> apkUpdateInfoList;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public ApkDetailImpl(HttpResultListener httpResultListener, AppCallBack callBack){
        this.callBack = callBack;
        this.httpResultListener=httpResultListener;
    }
    public ApkDetailImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getApkDetail(@Nullable String ssid, String appId) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
//        params.put("ssid", ssid);
        params.put("appId",appId);
        KLog.e("接口ssid",ssid);
        KLog.e("接口ssid",appId +"");
//        client.setTimeout(ValueConfig.TIME_OUT);
//        String url = "http://www.r93535.com/gateway/inter/inter!getAppInfo.action";
        String url = ValueConfig.PCAPP_URL+"inter/inter!getAppInfox.action";
        client.post(
//                "http://192.168.0.157:8080/gateway/inter/inter!getAppInfo.action",
                url,
                params,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                        System.out.println("fail");
                        try {
                            KLog.e(TAG, "onFailure:" + arg2);
                            String str = new String(arg2, "UTF-8");
                            JSONObject jsonObject = new JSONObject(str);
                            Log.i("url", arg3.toString());
                            if (!(jsonObject.getString("status").equals("0"))) {
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.getString("status"));
                                failRequestobj.setMsg(jsonObject.getString("msg"));
                                if (callBack != null){
                                    callBack.myResultFailure(failRequestobj);
                                }
                                httpResultListener.onResult(failRequestobj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        System.out.println("success");
                        KLog.e(TAG, "succ");
                        KLog.e(arg1.toString());
                        KLog.e(arg2.toString());

                        apkUpdateInfoList=new ArrayList<ApkUpdateInfo>();
                        JSONArray jsonArr;
                        JSONObject jsonObject;
                        JSONObject failjsonobj;
                        String data = null;
                        try {
                            String str = new String(arg2, "UTF-8");
                            KLog.e(TAG, str);
                            jsonObject= new JSONObject(str);
                            String s = jsonObject.getString("status");
                            KLog.e(TAG, s);
                            if(Integer.parseInt(s) >0){
                                failRequestobj = new FailRequest();
                                failRequestobj.setStatus(jsonObject.getString("status"));
                                failRequestobj.setMsg(jsonObject.getString("msg"));
                                if (callBack != null){
                                    callBack.myResultFailure(failRequestobj);
                                    KLog.e("00000000000");

                                }
                                httpResultListener.onResult(failRequestobj);
                                KLog.e("111111111");
                            }else {
                                String dataobj=jsonObject.getString("data");
                                Log.i(TAG, dataobj);
                                JSONObject datao=new JSONObject(dataobj);
                                JSONArray dataarr=new JSONArray(datao.getString("list"));
                                Log.i(TAG, dataarr.toString());
                                for (int i = 0; i < dataarr.length(); i++) {
                                    data = dataarr.get(i).toString();
                                    jsonObject = new JSONObject(data);
                                ApkUpdateInfo apkUpdateInfobj= new ApkUpdateInfo();
                                apkUpdateInfobj.setId(jsonObject.getString("id"));
                                apkUpdateInfobj.setName(jsonObject.getString("name"));
                                apkUpdateInfobj.setApkName(jsonObject.getString("apkName"));
                                apkUpdateInfobj.setAppId(jsonObject.getString("appId"));
                                apkUpdateInfobj.setAppUrl(jsonObject.getString("appUrl"));
                                apkUpdateInfobj.setDescription(jsonObject.getString("description"));
                                apkUpdateInfobj.setPackName(jsonObject.getString("packName"));
                                apkUpdateInfobj.setUploadTime(jsonObject.getString("uploadTime"));
                                apkUpdateInfobj.setUseFlag(jsonObject.getString("useFlag"));
                                apkUpdateInfobj.setVersionCode(jsonObject.getString("versionCode"));
                                apkUpdateInfobj.setVersionName(jsonObject.getString("versionName"));
                                    apkUpdateInfobj.setApkSize(jsonObject.getString("apkSize"));
                                apkUpdateInfoList.add(apkUpdateInfobj);
                            }
                                if (callBack != null){
                                    callBack.myResult(apkUpdateInfoList);
                                    KLog.e("2222222222");
                                }
                                httpResultListener.onResult(apkUpdateInfoList);
                                KLog.e("333333333");
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

    /**
     * AppResult
     */
    public interface AppCallBack {
        public void myResult (List<ApkUpdateInfo> result);
        public void myResultFailure (FailRequest failRequestobj);
    }
}
