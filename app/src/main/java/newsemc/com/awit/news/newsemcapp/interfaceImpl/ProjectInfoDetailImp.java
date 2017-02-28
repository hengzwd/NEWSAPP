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
import newsemc.com.awit.news.newsemcapp.bean.ProjectDetail;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetail;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailMainItem;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPeriod;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPic;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;
import newsemc.com.awit.news.newsemcapp.interfaces.ProjectInfoInter;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;

/**
 * Created by lianghl on 2015/12/16.
 * 项目信息详情接口，在原基础（ProjectInfoInterImpl）上有改动
 */
public class ProjectInfoDetailImp implements ProjectInfoInter {

    private final String TAG = "ProjectInfoDetailImp";
    private List<ProjectDetail> mProjectDetailList;
    private FailRequest mFailRequestObj;
    private HttpResultListener mHttpResultListener;

    public ProjectInfoDetailImp(HttpResultListener httpResultListener){
        mHttpResultListener = httpResultListener;
    }
    @Override
    public void getProjectInfoList(final String ssid, String userId, String projectinfoid) {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        params.put("ssid", ssid);
        params.put("userId", userId);
        params.put("projectinfoid", projectinfoid);
        String url = ValueConfig.PCAPP_URL + "inter/inter!getItemInfo.action";
        Log.i(TAG, "ssid=" + ssid);
        Log.i(TAG, "userId=" + userId);
        Log.i(TAG, "projectinfoid="+projectinfoid);
        Log.i(TAG, "url=" + url);
        client.setTimeout(ValueConfig.TIME_OUT);
        client.post(url, params, new AsyncHttpResponseHandler() {
            //请求失败操作
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i(TAG, "请求失败！！！");
                try {
                    String str = new String(bytes, "UTF-8");//可能产生异常
                    Log.i(TAG, "返回请求失败的信息为：" + str);
                    Log.i(TAG, "请求失败的原因为：" + throwable.toString());
                    JSONObject failData = new JSONObject(str);
                    if (!failData.getString("status").equals("0")){
                        mFailRequestObj = new FailRequest();
                        mFailRequestObj.setStatus(failData.getString("status"));
                        mFailRequestObj.setMsg(failData.getString("msg"));
                        mHttpResultListener.onResult(mFailRequestObj);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //请求成功操作
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i(TAG, "请求成功！！！");
                mProjectDetailList = new ArrayList<ProjectDetail>();
                try {
                    String str = new String(bytes, "UTF-8");
                    Log.i(TAG, "返回成功数据为：" + str);
                    JSONObject successData = new JSONObject(str);
                    //判断接收到的数据是否有效
                    //状态为0时，返回的数据是有效数据，否则返回数据失败
                    if (Integer.parseInt(successData.getString("status")) < 0){
                        //返回失败数据处理
                        mFailRequestObj = new FailRequest();
                        mFailRequestObj.setStatus(successData.getString("status"));
                        mFailRequestObj.setMsg(successData.getString("msg"));
                        mHttpResultListener.onResult(mFailRequestObj);
                    }else {
                        //返回成功数据处理
                        String data = successData.getString("data");
                        Log.i(TAG, "data=" + data);
                        JSONObject dataJSON = new JSONObject(data);
                        JSONArray listJSONArray = new JSONArray(dataJSON.getString("list"));
                        Log.i(TAG, "list=" + listJSONArray);
                        for (int n = 0; n < listJSONArray.length(); n++){
                            //获取list中的Json元素
                            ProjectDetail projectDetail = new ProjectDetail();
                            JSONObject jsonListItem = new JSONObject(listJSONArray.get(n).toString());
                            ProjectInfoDetail projectInfoDetail = new ProjectInfoDetail();
                            projectInfoDetail.setProject_id(jsonListItem.getString("id"));//项目id
                            Log.i(TAG, "Project_id=" + projectInfoDetail.getProject_id());
                            projectInfoDetail.setProjectName(jsonListItem.getString("projectName"));//项目名称
                            Log.i(TAG, "projectName=" + projectInfoDetail.getProjectName());
                            projectInfoDetail.setProjectType(jsonListItem.getString("projectType"));//项目功能类别
                            Log.i(TAG, "projectType=" + projectInfoDetail.getProjectType());
                            projectInfoDetail.setConxName(jsonListItem.getString("conxName"));//建设单位
                            projectInfoDetail.setProjectSource(jsonListItem.getString("projectSource"));//工程来源
                            projectInfoDetail.setProjectCode(jsonListItem.getString("projectCode"));//项目编码
                            projectInfoDetail.setProjectLength(jsonListItem.getString("projectLength"));//正线建筑里程
                            projectInfoDetail.setStartStation(jsonListItem.getString("startStation"));//始发站
                            projectInfoDetail.setStopStation(jsonListItem.getString("stopStation"));//终点站
                            projectInfoDetail.setStartDate(jsonListItem.getString("startDate"));//开工时间
                            projectInfoDetail.setStopDate(jsonListItem.getString("stopDate"));//项目竣工时间
                            projectInfoDetail.setStationNum(jsonListItem.getString("stationNum"));//车站总数
                            projectInfoDetail.setInvest(jsonListItem.getString("invest"));//投资总额
                            projectInfoDetail.setStartMile(jsonListItem.getString("startMile"));//开始里程
                            projectInfoDetail.setStopMile(jsonListItem.getString("stopMile"));//结束里程
                            projectInfoDetail.setDesignCorpName(jsonListItem.getString("designCorpName"));//设计单位
                            projectInfoDetail.setExamineCorpName(jsonListItem.getString("examineCorpName"));//施工图审核单位
                            projectInfoDetail.setProCity(jsonListItem.getString("proCity"));//途径城市
                            projectInfoDetail.setProStation(jsonListItem.getString("proStation"));//所辖车站
                            projectInfoDetail.setDescription(jsonListItem.getString("description"));//项目概况
                            Log.i(TAG, "description=" + projectInfoDetail.getDescription());
                            projectDetail.setProjectInfoDetail(projectInfoDetail);
                            //获取projectPeriod中的信息（主要控制工程）
                            JSONObject jsonMainItem = new JSONObject(jsonListItem.getString("projectMainitem"));//获取projectMainitem对象
                            ProjectInfoDetailMainItem projectInfoDetailMainItem = new ProjectInfoDetailMainItem();
                            projectInfoDetailMainItem.setId(jsonMainItem.getString("id"));//不知道什么id（保留）
                            Log.i(TAG, "InfoDetailMainItem_id=" + projectInfoDetailMainItem.getId());
                            projectInfoDetailMainItem.setProjectInfoId(jsonMainItem.getString("projectInfoId"));//项目id
                            Log.i(TAG, "InfoDetailMainItem_projectInfoId=" + projectInfoDetailMainItem.getProjectInfoId());
                            projectInfoDetailMainItem.setKongzhiSection(jsonMainItem.getString("kongzhiSection"));//主要控制工程
                            Log.i(TAG, "InfoDetailMainItem_kongzhiSection=" + projectInfoDetailMainItem.getKongzhiSection());
                            projectDetail.setProjectInfoDetailMainItem(projectInfoDetailMainItem);
                            //获取projectPeriod的信息(批复文号)
                            JSONObject jsonPeriod = new JSONObject(jsonListItem.getString("projectPeriod"));
                            ProjectInfoDetailPeriod projectInfoDetailPeriod = new ProjectInfoDetailPeriod();
                            projectInfoDetailPeriod.setId(jsonPeriod.getString("id"));//不知道什么id（保留）
                            projectInfoDetailPeriod.setProjectInfoId(jsonPeriod.getString("projectInfoId"));//项目id
                            projectInfoDetailPeriod.setJyWenhao(jsonPeriod.getString("jyWenhao"));//项目建议书
                            projectInfoDetailPeriod.setKyWenhao(jsonPeriod.getString("kyWenhao"));//可研批复
                            projectInfoDetailPeriod.setCsWenhao(jsonPeriod.getString("csWenhao"));//初设批复
                            projectInfoDetailPeriod.setSgWenhao(jsonPeriod.getString("sgWenhao"));//施工图审核
                            projectInfoDetailPeriod.setZdWenhao(jsonPeriod.getString("zdWenhao"));//指导性施组批复和调整
                            projectInfoDetailPeriod.setSgJishu(jsonPeriod.getString("sgJishu"));//初设批复技术标准
                            projectInfoDetailPeriod.setSgTouzi(jsonPeriod.getString("sgTouzi"));//初设批复投资
                            projectInfoDetailPeriod.setSgGongqi(jsonPeriod.getString("sgGongqi"));//初设批复工期
                            projectInfoDetailPeriod.setSzJdGongqi(jsonPeriod.getString("szJdGongqi"));//批准施工组节点工期
                            Log.i(TAG, "csWenhao=" + projectInfoDetailPeriod.getCsWenhao());
                            Log.i(TAG, "sgWenhao=" + projectInfoDetailPeriod.getSgWenhao());
                            Log.i(TAG, "zdWenhao=" + projectInfoDetailPeriod.getZdWenhao());
                            Log.i(TAG, "sgJishu=" + projectInfoDetailPeriod.getSgJishu());
                            Log.i(TAG, "sgTouzi=" + projectInfoDetailPeriod.getSgTouzi());
                            Log.i(TAG, "sgGongqi=" + projectInfoDetailPeriod.getSgGongqi());
                            Log.i(TAG, "szJdGongqi=" + projectInfoDetailPeriod.getSzJdGongqi());
                            projectDetail.setProjectInfoDetailPeriod(projectInfoDetailPeriod);
                            //获取图片
                            List<ProjectInfoDetailPic> picList = new ArrayList<ProjectInfoDetailPic>();
                            JSONArray jsonPicList = new JSONArray(jsonListItem.getString("projectPic"));//建立图片json数组
                            for (int m = 0; m < jsonPicList.length(); m++ ){
                                String picDataStr = jsonPicList.get(m).toString();
                                JSONObject pic = new JSONObject(picDataStr);//获取每一个图片json对象
                                ProjectInfoDetailPic projectInfoDetailPic = new ProjectInfoDetailPic();
                                projectInfoDetailPic.setProjectPicId(pic.getString("id"));//图片id
                                Log.i(TAG, "ProjectPicId=" + projectInfoDetailPic.getProjectPicId());
                                projectInfoDetailPic.setProjectPicName(pic.getString("name"));//图片名称
                                Log.i(TAG, "ProjectPicName=" + projectInfoDetailPic.getProjectPicName());
                                projectInfoDetailPic.setProjectPicUrl(pic.getString("url"));//图片url
                                Log.i(TAG, "ProjectPicUrl=" + projectInfoDetailPic.getProjectPicUrl());
                                picList.add(projectInfoDetailPic);
                            }
                            projectDetail.setProjectInfoDetailPicList(picList);

                            mProjectDetailList.add(projectDetail);
                        }
                        Log.i(TAG, "打印接口获取的数据。。。");
                        Log.i(TAG, mProjectDetailList.get(0).getProjectInfoDetail().getProjectName());
                        Log.i(TAG, mProjectDetailList.get(0).getProjectInfoDetailMainItem().getKongzhiSection());
                        Log.i(TAG, mProjectDetailList.get(0).getProjectInfoDetailPeriod().getCsWenhao());
//                        Log.i(TAG, mProjectDetailList.get(0).getProjectInfoDetailPicList().get(0).getProjectPicName());
                        mHttpResultListener.onResult(mProjectDetailList);
                    }
                }catch (Exception e){
                    Log.i(TAG, "ProjectInfoDetailImp接口返回数据异常。。。");
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                mHttpResultListener.onStartRequest();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mHttpResultListener.onFinish();
            }
        });
    }
}
