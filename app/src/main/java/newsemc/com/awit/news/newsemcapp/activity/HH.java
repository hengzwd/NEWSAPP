package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.GloBalUrl.GloBalUrl;
import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.Match;
import newsemc.com.awit.news.newsemcapp.interfaceImpl.MatchImpl;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;

/**
 * Created by shaorc on 2016-08-18.
 */
public class HH extends Activity {
    private String userId,appId = "1";
    private ArrayList<Match> matchList;
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo);
        SharedPreferences sharedPreferences = getSharedPreferences("SP", MainActivity.MODE_PRIVATE);
        userId = sharedPreferences.getString("ACCOUNT", "");
        Log.i("account------", userId + "");
//        SharedPreferences preferences = getSharedPreferences("APPID",MODE_PRIVATE);
//        appId = preferences.getString("appId","");
//        Log.i("apppppppid",appId);
        getDtatServer(userId,"1");
//        MatchImpl match = new MatchImpl(this);
//        match.getMatchAppId(userId,appId);
    }
//
//    @Override
//    public void onStartRequest() {
//
//    }
//
//    @Override
//    public void onResult(Object obj) {
//        if (obj instanceof FailRequest) {
//            FailRequest fail = (FailRequest) obj;
//            if (!(fail == null)) {
//                System.out.println("异常信息：" + fail.getMsg());
//                System.out.println("状态：" + fail.getStatus());
//            } else {
//                Log.i("失败消息：", fail.getMsg());
//                Toast.makeText(HH.this, fail.getMsg(), Toast.LENGTH_SHORT).show();
//            }
//        }else {
//            matchList = (List<Match>) obj;
//           if (matchList.size() > 0){
//                String appid = matchList.get(0).getAppid();
//                SharedPreferences sh = getSharedPreferences("APPID", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sh.edit();
//                editor.putString("appid", appid);
//                editor.commit();
//            }
//        }
//        }
//
//
//
//    @Override
//    public void onFinish() {
//
//    }

    private void  getDtatServer(String userId ,String appId){
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("appId", appId);
        Log.i("aaaaaaa",userId);
        Log.i("bbbbb",appId);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.POST, GloBalUrl.match, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.i("shaorc", "数据" + responseInfo.result);
                String result = responseInfo.result;
                Toast.makeText(HH.this, responseInfo.result.toString(), Toast.LENGTH_SHORT).show();
                Match search = new Gson().fromJson(responseInfo.result, Match.class);
//                String appId2 = search.getData().getAppId();
//                SharedPreferences sf = getSharedPreferences("APPID",MODE_PRIVATE);
//                SharedPreferences.Editor ed = sf.edit();
//                ed.putString("appId",appId2);
//                Toast.makeText(HH.this, search.getData().getAppId(), Toast.LENGTH_SHORT).show();
//                ed.commit();
//                Gson gson = new Gson();
//                TypeToken<List<Match>> token = new TypeToken<List<Match>>() {
//                };
//                matchList = gson.fromJson(result, token.getType());
                matchList = new ArrayList<Match>();
                for (int i =0;i<matchList.size();i++){
                    String appId1 = matchList.get(i).getData().getAppId();
                    SharedPreferences sf = getSharedPreferences("APPID",MODE_PRIVATE);
                    SharedPreferences.Editor ed = sf.edit();
                    ed.putString("appId",appId1);
                    ed.commit();

                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });


    }
}
