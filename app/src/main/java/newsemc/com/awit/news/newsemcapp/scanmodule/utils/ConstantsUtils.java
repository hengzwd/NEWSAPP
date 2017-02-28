package newsemc.com.awit.news.newsemcapp.scanmodule.utils;

import android.text.TextUtils;

import com.socks.library.KLog;

import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;

public class ConstantsUtils {
    private static final String TAG = ConstantsUtils.class.getSimpleName();

    /**
     * 不允许new
     */
    private ConstantsUtils() {
        throw new Error("Do not need instantiate!");
    }

    //第三方基地址
//  public static final String BaseURL_3 = "http://112.124.5.152/tkywsapp/WSMobile.asmx/";
   public static final String BaseURL_3 = "http://www.r93535.com/picdoc/wsmobile.asmx/";


    //登录地址
    public static final String Login_URL = BaseURL_3 + "Login";

    //取资料信息列表地址
    public static final String ArchiveInfoList_URL = BaseURL_3 + "GetArchiveInfoList";

    //上传图片地址
    public static final String UploadFile_URL = BaseURL_3 + "UploadFile";


    /*************************************************************************************************
     * 分割线：以上为第三方接口，以下为铁科院接口
     ***********************************************************************************************/
    //铁科院基地址
//    private static final String BaseURL_TKY = "http://61.237.239.105:9090/gis/";
   private static final String BaseURL_TKY = "http://api.r93535.com/appscan/";

    //工程概况URL
    public static final String tunnel_survey_url = BaseURL_TKY + "projectProfileControl/findProjectProfileInfo?id=%0&type=%1";

    public static final String first_search_url = BaseURL_TKY + "projectSearchControl/findBuildingInfo";

    public static final String second_search_url = BaseURL_TKY + "projectSearchControl/findsitebridgeInfo";

    public static final String pier_records_url = BaseURL_TKY + "projectProfileControl/findConstructionRecord";

    public static final String specialstructure_records_url = BaseURL_TKY + "projectProfileControl/findSpecialsTructureInfo";

    public static final String tunnel_records_url = BaseURL_TKY + "projectProfileControl/findTunnelSiteInfo";

    public static final String schedule_url = BaseURL_TKY + "projectProfileControl/findTunnelDailyInfo";

//    public  static String getSchedule_url(String type)
//    { String url = schedule_url.replace("%0", NewsEMCAppllication.mQRCodeBean.getId()).replace("%1", type);
//          return url;
//    }
    //获取隧道工程概况数据
    public static String getTunnelSurveyData(String id, String type) {
        String url = tunnel_survey_url.replace("%0", id).replace("%1", type);
        KLog.e(TAG, "工程概况数据 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }
}
