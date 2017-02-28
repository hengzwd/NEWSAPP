package newsemc.com.awit.news.newsemcapp.bean;

/**
 * Created by Administrator on 2016-08-21.
 */
public class Match {
    /**
     * status : 0
     * msg : 成功
     * data : {"appId":"2"}
     */

    private String status;
    private String msg;
    /**
     * appId : 2
     */

    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Match{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        private String appId;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "appId='" + appId + '\'' +
                    '}';
        }
    }

//    private String status;
//    private String msg;
//    private String  appid;
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getAppid() {
//        return appid;
//    }
//
//    public void setAppid(String appid) {
//        this.appid = appid;
//    }



}
