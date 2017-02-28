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
import newsemc.com.awit.news.newsemcapp.bean.NewsDetailEntity;
import newsemc.com.awit.news.newsemcapp.dao.DetailInfo;
import newsemc.com.awit.news.newsemcapp.interfaces.DetailInter;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by Administrator on 2016/1/17.
 */
public class Detail2Impl implements DetailInter {
    private String TAG="Detail2Impl";
    private NewsDetailEntity detailInfoList;
    private FailRequest failRequestobj;
    private HttpResultListener httpResultListener;
    public Detail2Impl(HttpResultListener httpResultListener){
        this.httpResultListener=httpResultListener;
    }
    @Override
    public void getDetailInfoList(String ssid,String userId, String infotype, String infoid) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userId", userId);
        params.put("infotype", infotype);
        params.put("infoid", infoid);
        params.put("ssid",ssid);
//        client.setTimeout(ValueConfig.TIME_OUT);
        String url = ValueConfig.PCAPP_URL + "inter/inter!jsInfoDetailx.action";
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
                        detailInfoList= new NewsDetailEntity();
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
                            }else {
                                String dataobj=jsonObject.getString("data");
                                Log.i(TAG + "dataobj = ", dataobj);
                                JSONObject datao = new JSONObject(dataobj);
                                JSONObject dataarr = new JSONObject(datao.getString("list"));
                                Log.i(TAG, dataarr.toString());

                                //新闻内容详情
                                NewsDetailEntity.DataEntity entity1 = new NewsDetailEntity.DataEntity();
                                entity1.setTotal(datao.getString("total"));
                                NewsDetailEntity.DataEntity.ListEntity detailInfobj= new NewsDetailEntity.DataEntity.ListEntity();
                                detailInfobj.setId(dataarr.getString("id"));
                                detailInfobj.setInfotype(dataarr.getString("infotype"));
                                detailInfobj.setNum(dataarr.getString("num"));
                                detailInfobj.setInfoname(dataarr.getString("infoname"));   //信息标题
                                detailInfobj.setInfodate(dataarr.getString("infodate"));   //信息发布时间
                                detailInfobj.setInfocontent(dataarr.getString("infocontent")); //信息发布内容
                                detailInfobj.setPublish(dataarr.getString("publish"));    //发布账号
                                detailInfobj.setPublishusername(dataarr.getString("publishusername"));  //发布人
                                detailInfobj.setPublishquarry(dataarr.getString("publishquarry"));    //发布来源

                                //图片列表
                                List<NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity> attachs = new ArrayList<NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity>();
                                JSONArray attach_jsons = dataarr.getJSONArray("attachments");
                                for(int i=0; i<attach_jsons.length();i++){
                                    NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity attachmentsEntity = new NewsDetailEntity.DataEntity.ListEntity.AttachmentsEntity();
                                    JSONObject jo = attach_jsons.getJSONObject(i);
                                    attachmentsEntity.setSavePath(jo.getString("savePath"));
                                    attachmentsEntity.setFileName(jo.getString("fileName"));
                                    attachs.add(attachmentsEntity);
                                }
                                detailInfobj.setAttachments(attachs);

                                //附件列表
                                List<NewsDetailEntity.DataEntity.ListEntity.AppendixEntity> attachdxs = new ArrayList<NewsDetailEntity.DataEntity.ListEntity.AppendixEntity>();
                                JSONArray attachdx_jsons = dataarr.getJSONArray("appendix");
                                for(int i=0; i <attachdx_jsons.length();i++ ){
                                    NewsDetailEntity.DataEntity.ListEntity.AppendixEntity appendixEntity = new NewsDetailEntity.DataEntity.ListEntity.AppendixEntity();
                                    JSONObject jodx = attachdx_jsons.getJSONObject(i);
                                    appendixEntity.setSavePath(jodx.getString("savePath"));
                                    appendixEntity.setFileName(jodx.getString("fileName"));
                                    attachdxs.add(appendixEntity);
                                }
                                detailInfobj.setAppendix(attachdxs);

                                //设置实体
                                entity1.setList(detailInfobj);
                                detailInfoList.setData(entity1);
                                httpResultListener.onResult(detailInfoList);
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
