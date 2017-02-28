package newsemc.com.awit.news.newsemcapp.util;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.activity.LoginActivity;

import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.dao.DanDianLoginPub;


/**
 * Created by Administrator on 15-6-2.
 */
public class ValueConfig {
//    public static final String PCAPP_URL = "http://www.r93535.com/gateway/";   //京沈
    public static final String PCAPP_URL = "http://www.r93535.com/gateway/";   //京沈
//    public static final String PCAPP_URL = "http://10.30.1.18:8070/gateway/";//开发环境
//    public static final String PCAPP_URL = "http://88.1.1.17:8080/gateway/";//测试版
//    public static final String PCAPP_URL = "http://www.r93535.com/gatewaytest/";   //京沈测试
//    public static final String PCAPP_URL = "http://www.r93535.com/chuannan/";   //川南
    public static final String APPSERVICE_URL = "http://10.1.0.100/ws/";
    public static final int TIME_OUT = 20000;
    public static final String DOWNLOAD = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/ems/download";

    //进度条
    public newsemc.com.awit.news.newsemcapp.dialog.CustomProgressDialog progressDialog = null;


    public static boolean validateObject(Object obj, Context context) {
        if (obj == null) {
            Toast.makeText(context, "接口出现问题，没有数据返回", Toast.LENGTH_SHORT).show();
            return false;
        } else if (obj instanceof FailRequest) {
            if (((FailRequest) obj).getStatus().equals("-8") ) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);

            } else {
                FailRequest fail = (FailRequest) obj;
                Toast.makeText(context, fail.getMsg(), Toast.LENGTH_SHORT)
                        .show();
            }
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<Map<String, Object>> getGridListPopViewData(List<DanDianLoginPub> icon) {
        ArrayList<Map<String, Object>> zonghelist = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        String status,name,num;
        for(int i=0;i<icon.size();i++){
            Log.i("name",icon.get(i).getName());
            Log.i("status",icon.get(i).getStatus());
            status=icon.get(i).getStatus();
            name=icon.get(i).getName();
            num=icon.get(i).getNum();
            map = new HashMap<String, Object>();
            if(name.equals("")){
                Log.i("Exception：","接口空指针异常");
            }else{
                if(name.equals("公文处理")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.gwcl);
                    }else{
                        map.put("pop_img",R.drawable.gwclh);
                    }
                    map.put("pop_num",num);
                    map.put("pop_text", "公文处理");
//                }else if(name.equals("农民工系统")){
                }else if(name.equals("劳务工工资系统")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.nmgxt);
                    }else{
                        map.put("pop_img",R.drawable.nmgxth);
                    }
//                    map.put("pop_text", "农民工系统");
                    map.put("pop_text", "劳务工工资系统");
                } else if(name.equals("诚信体系")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.cxtx);
                    }else{
                        map.put("pop_img",R.drawable.cxtxh);
                    }
                    map.put("pop_text", "诚信体系");
                }else if(name.equals("资料管理")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.zlgl);
                    }else{
                        map.put("pop_img",R.drawable.zlglh);
                    }
                    map.put("pop_text", "资料管理");
                }else if(name.equals("技术管理")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.jsgl);
                    }else{
                        map.put("pop_img",R.drawable.jsglh);
                    }
                    map.put("pop_text", "技术管理");
                }else if(name.equals("后台管理")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.htgl);
                    }else{
                        map.put("pop_img",R.drawable.htglh);
                    }
                    map.put("pop_text", "后台管理");
                }else if(name.equals("规章制度")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.gzzd);
                    }else{
                        map.put("pop_img",R.drawable.gzzdh);
                    }
                    map.put("pop_text", "规章制度");
                } else if(name.equals("部门考核")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.bmkh);
                    }else{
                        map.put("pop_img",R.drawable.bmkhh);
                    }
                    map.put("pop_text", "部门考核");
                }else if(name.equals("通讯录")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.txl);
                    }else{
                        map.put("pop_img",R.drawable.txlh);
                    }
                    map.put("pop_text", "通讯录");
                }else if(name.equals("项目信息")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.xmxx);
                    }else{
                        map.put("pop_img",R.drawable.xmxx_h);
                    }
                    map.put("pop_text", "项目信息");
                }else if (name.equals("会议管理")){
                    if (!status.equals("0")){
                        map.put("pop_img",R.drawable.hygl);
                    }else {
                        map.put("pop_img",R.drawable.hygl1);
                    }
                    map.put("pop_text","会议管理");
                }else if (name.equals("车辆管理")){
                    if (!status.equals("0")){
                        map.put("pop_img",R.drawable.clgl);
                    }else {
                        map.put("pop_img",R.drawable.clgl1);
                    }
                    map.put("pop_text","车辆管理");
                }else{
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.xmxx);
                    }else{
                        map.put("pop_img",R.drawable.xmxx_h);
                    }
                    map.put("pop_text", name);
                }
            }
            zonghelist.add(map);
        }
        return zonghelist;
    }

    public static ArrayList<Map<String, Object>> getGridListRealViewData(List<DanDianLoginPub> icon) {
        ArrayList<Map<String, Object>> procedurelist = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        String status,name;
        for(int i=0;i<icon.size();i++){
            Log.i("name",icon.get(i).getName());
            Log.i("status",icon.get(i).getStatus());
            status=icon.get(i).getStatus();
            name=icon.get(i).getName();
            map = new HashMap<String, Object>();
            if(name.equals("")){
                Log.i("Exception：","接口空指针异常");
            }else{
                if(name.equals("调度指挥")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.ddzh);
                    }else{
                        map.put("pop_img",R.drawable.ddzhh);
                    }
                    map.put("pop_text", "调度指挥");
                }else if(name.equals("验工计价")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.ygjj);
                    }else{
                        map.put("pop_img",R.drawable.ygjjh);
                    }
                    map.put("pop_text", "验工计价");
                }else if(name.equals("物资设备")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.wzsb);
                    }else{
                        map.put("pop_img",R.drawable.wzsbh);
                    }
                    map.put("pop_text", "物资设备");
                }else if(name.equals("施工图审核")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.sgtsh);
                    }else{
                        map.put("pop_img",R.drawable.sgtshh);
                    }
                    map.put("pop_text", "施工图审核");
                }else if(name.equals("征地拆迁")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.zdcq);
                    }else{
                        map.put("pop_img",R.drawable.zdcqh);
                    }
                    map.put("pop_text", "征地拆迁");
                }else if(name.equals("后台管理")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.htgl);
                    }else{
                        map.put("pop_img",R.drawable.htglh);
                    }
                    map.put("pop_text", "后台管理");
                }else if(name.equals("施工组织")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.sgzz);
                    }else{
                        map.put("pop_img",R.drawable.sgzzh);
                    }
                    map.put("pop_text", "施工组织");
                }else if(name.equals("施工日志")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.sgrz);
                    }else{
                        map.put("pop_img",R.drawable.sgrzh);
                    }
                    map.put("pop_text", "施工日志");
                }else if(name.equals("问题库")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.wtk);
                    }else{
                        map.put("pop_img",R.drawable.wtkh);
                    }
                    map.put("pop_text", "问题库");
                }else if(name.equals("在线培训")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.peixun);
                    }else{
                        map.put("pop_img",R.drawable.peixun);
                    }
                    map.put("pop_text", "在线培训");
                }else {
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.xmxx);
                    }else{
                        map.put("pop_img",R.drawable.xmxx_h);
                    }
                    map.put("pop_text", name);
                }
            }
            procedurelist.add(map);
        }
        return procedurelist;
    }

    public static ArrayList<Map<String, Object>> getGridListXianViewData(List<DanDianLoginPub> icon) {
        ArrayList<Map<String, Object>> xianchanglist = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        String status,name,num;
        for(int i=0;i<icon.size();i++){
            Log.i("name",icon.get(i).getName());
            Log.i("status",icon.get(i).getStatus());
            status=icon.get(i).getStatus();
            name=icon.get(i).getName();
            num=icon.get(i).getNum();
            map = new HashMap<String, Object>();
            if(name.equals("")){
                map.put("pop_img",R.drawable.bhzh);
                map.put("pop_text", "拌和站");
                Log.i("Exception：", "接口空指针异常");
            }else{
                if(name.equals("拌和站")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.bhz);
                    }else{
                        map.put("pop_img",R.drawable.bhzh);
                    }
                    map.put("pop_text", "拌和站");
                }else if(name.equals("试验室")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.sys);
                    }else{
                        map.put("pop_img",R.drawable.sysh);
                    }
                    map.put("pop_text", "试验室");
                }else if(name.equals("自动张拉")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.zdzl);
                    }else{
                        map.put("pop_img",R.drawable.zdzlh);
                    }
                    map.put("pop_text", "自动张拉");
                }
//                else if(name.equals("智能梁场")){
                else if(name.equals("梁场")){
                    if(!status.equals("0")){
//                        map.put("pop_img", R.drawable.znlc);
                        map.put("pop_img", R.drawable.lc);
                    }else{
//                        map.put("pop_img",R.drawable.znlch);
                        map.put("pop_img",R.drawable.lc_f);
                    }
//                    map.put("pop_text", "智能梁场");
                    map.put("pop_text", "梁场");
                }
//                else if(name.equals("智能板场")){
                    else if(name.equals("板场")){
                    if(!status.equals("0")){
//                        map.put("pop_img", R.drawable.znbc);
                        map.put("pop_img", R.drawable.bc);
                    }else{
//                        map.put("pop_img",R.drawable.znbch);
                        map.put("pop_img",R.drawable.bc_f);
                    }
//                    map.put("pop_text", "智能板场");
                    map.put("pop_text", "板场");
                }else if(name.equals("隧道注浆")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.sdzj);
                    }else{
                        map.put("pop_img",R.drawable.sdzjh);
                    }
                    map.put("pop_text", "隧道注浆");
                }else if(name.equals("围岩量测")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.wylc);
                    }else{
                        map.put("pop_img",R.drawable.wylch);
                    }
                    map.put("pop_num",num);
                    map.put("pop_text", "隧道监控");
                }else if(name.equals("沉降观测")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.cjgc);
                    }else{
                        map.put("pop_img",R.drawable.cjgch);
                    }
                    map.put("pop_text", "沉降观测");
                }else if(name.equals("现场监视")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.xcjs);
                    }else{
                        map.put("pop_img",R.drawable.xcjsh);
                    }
                    map.put("pop_text", "现场监视");
                }else if(name.equals("线形监测")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.xxjc);
                    }else{
                        map.put("pop_img",R.drawable.xxjch);
                    }
                    map.put("pop_text", "连续梁");
                }else if(name.equals("检验批")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.jyp);
                    }else{
                        map.put("pop_img",R.drawable.jyph);
                    }
                    map.put("pop_text", "检验批");
                }else if(name.equals("桥梁静载")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.qljz);
                    }else{
                        map.put("pop_img",R.drawable.qljzh);
                    }
                    map.put("pop_text", "桥梁静载");
                }else if(name.equals("线形监视")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.qljz);
                    }else{
                        map.put("pop_img",R.drawable.qljzh);
                    }
                    map.put("pop_text", "线形监视");
                }else if(name.equals("连续压实")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.lxys);
                    }else{
                        map.put("pop_img",R.drawable.lxysh);
                    }
                    map.put("pop_text", "连续压实");
                }else if(name.equals("隧道形象化")){
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.sdjxhgl);
                    }else{
                        map.put("pop_img",R.drawable.sdjxhgl_f);
                    }
                    map.put("pop_text", "隧道形象化");
                }
                else{
                    if(!status.equals("0")){
                        map.put("pop_img", R.drawable.xmxx);
                    }else{
                        map.put("pop_img",R.drawable.xmxx_h);
                    }
                    map.put("pop_text", name);
                }
            }
            xianchanglist.add(map);
            }
            Log.i("xianchanglist",xianchanglist.size()+"");
        return xianchanglist;
    }
    public static final String DB_JING= Environment.getExternalStorageDirectory().getPath()
            + File.separator+"JING_SHENG"+File.separator+"biao5";//数据库路径


}
