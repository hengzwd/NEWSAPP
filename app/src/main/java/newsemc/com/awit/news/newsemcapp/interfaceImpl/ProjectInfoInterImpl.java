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
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.ProjectInfoInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * 项目动态-基本信息
 * Created by Administrator on 2015/6/29.
 */
public class ProjectInfoInterImpl implements ProjectInfoInter {
    private String TAG="ProjectInfoInterImpl";
    private List<ProjectInfo> projectInfoList;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;

    public ProjectInfoInterImpl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getProjectInfoList(String ssid,String userId,String projectinfoid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("projectinfoid",projectinfoid);
        params.put("ssid",ssid);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "inter/inter!getItembaseMsg.action";
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
                        projectInfoList=new ArrayList<ProjectInfo>();
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
                                ProjectInfo projectInfobj=new ProjectInfo();
                                projectInfobj.setId(jsonObject.getString("id"));
                                projectInfobj.setPname(jsonObject.getString("pname"));
                                projectInfobj.setCode(jsonObject.getString("code"));
                                projectInfobj.setNameabbr(jsonObject.getString("nameabbr"));
                                projectInfobj.setConstructiondepId(jsonObject.getString("constructiondepId"));
                                projectInfobj.setBname(jsonObject.getString("bname"));
                                projectInfobj.setDesignCompanyName(jsonObject.getString("designCompanyName"));
                                projectInfobj.setExamineCompanyName(jsonObject.getString("examineCompanyName"));
                                projectInfobj.setStartdate(jsonObject.getString("startdate"));
                                projectInfobj.setRundate(jsonObject.getString("rundate"));
                                projectInfobj.setDescription(jsonObject.getString("description"));
                                projectInfobj.setUpdatedate(jsonObject.getString("updatedate"));
                                projectInfoList.add(projectInfobj);
                            }
                            httpResultListener.onResult(projectInfoList);
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
