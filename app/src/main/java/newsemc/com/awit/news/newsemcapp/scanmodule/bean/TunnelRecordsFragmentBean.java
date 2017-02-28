package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.util.List;

/**
 * Author：leguang on 2016/10/13 0013 19:07
 * Email：langmanleguang@qq.com
 */
public class TunnelRecordsFragmentBean {

    /**
     * Description : Success
     * Result : 0
     * imageUrl1 : http://61.237.239.144/tunnel/tn/downloadTunnelImage?rtid=24115
     * progress : [{"profileInfoList":[{"key":"开挖及支护"},{"key":"前端里程","value":"DK502+303.0"},{"key":"当前围岩级别","value":"V"},{"key":"开累完成","value":"18040m"},{"key":"进度预警","value":"2天"}]},{"profileInfoList":[{"key":"仰拱"},{"key":"前端里程","value":"DK502+303.0"},{"key":"当前围岩级别","value":"V"},{"key":"开累完成","value":"16960m"},{"key":"步距预警","value":"90m"}]},{"profileInfoList":[{"key":"二衬"},{"key":"前端里程","value":"DK502+303.0"},{"key":"当前围岩级别","value":"V"},{"key":"开累完成","value":"15880m"},{"key":"步距预警","value":"200m"}]}]
     * quality : [{"id":"3","name":"检验批","total":"2","type":"4"},{"id":"5","name":"试验报告","total":"1","type":"4"},{"id":"1","name":"影像资料文件","total":"27","type":"4"}]
     * security : [[{"profileInfoList":[{"key":"断面","value":"DK123+121"},{"key":"建立日期","value":"2014-09-15 02:32:37"},{"key":"测点","value":"GD00"},{"key":"报警标识","value":"0"},{"key":"围岩级别","value":"5"},{"key":"最近观测时间","value":"2014-09-30 20:28:25"},{"key":"累计变形量","value":"5.1mm"},{"key":"最大变形速率","value":"4.1mm/d"}]},{"profileInfoList":[{"key":"断面","value":"DK123+121"},{"key":"建立日期","value":"2014-09-15 02:32:37"},{"key":"测点","value":"SL01-SL02"},{"key":"报警标识","value":"0"},{"key":"围岩级别","value":"5"},{"key":"最近观测时间","value":"2014-09-18 23:22:17"},{"key":"累计变形量","value":"4.2mm"},{"key":"最大变形速率","value":"4.8mm/d"}]}]]
     */

    private String Description;
    private int Result;
    private String imageUrl1;
    private List<ProgressBean> progress;
    /**
     * id : 3
     * name : 检验批
     * total : 2
     * type : 4
     */

    private List<QualityBean> quality;
    private List<List<SecurityBean>> security;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public List<ProgressBean> getProgress() {
        return progress;
    }

    public void setProgress(List<ProgressBean> progress) {
        this.progress = progress;
    }

    public List<QualityBean> getQuality() {
        return quality;
    }

    public void setQuality(List<QualityBean> quality) {
        this.quality = quality;
    }

    public List<List<SecurityBean>> getSecurity() {
        return security;
    }

    public void setSecurity(List<List<SecurityBean>> security) {
        this.security = security;
    }

    public static class ProgressBean {
        /**
         * key : 开挖及支护
         */

        private List<ProfileInfoListBean> profileInfoList;

        public List<ProfileInfoListBean> getProfileInfoList() {
            return profileInfoList;
        }

        public void setProfileInfoList(List<ProfileInfoListBean> profileInfoList) {
            this.profileInfoList = profileInfoList;
        }

        public static class ProfileInfoListBean {
            private String key;
            private String value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class QualityBean {
        private String id;
        private String name;
        private String total;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class SecurityBean {
        /**
         * key : 断面
         * value : DK123+121
         */

        private List<ProfileInfoListBean> profileInfoList;

        public List<ProfileInfoListBean> getProfileInfoList() {
            return profileInfoList;
        }

        public void setProfileInfoList(List<ProfileInfoListBean> profileInfoList) {
            this.profileInfoList = profileInfoList;
        }

        public static class ProfileInfoListBean {
            private String key;
            private String value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
