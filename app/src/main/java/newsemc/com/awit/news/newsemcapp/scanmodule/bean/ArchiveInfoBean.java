package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leguang on 2016/9/22 0022.
 * 联系邮箱:langmanleguang@qq.com
 */
public class ArchiveInfoBean implements Serializable{

    /**
     * DT : 2016-10-06 14:29:00
     * Description : Success
     * Result : 0
     * count : 2
     * infolist : [{"brolist":[{"id":"20010","name":"检验批.pdf","type":"1","url":"http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf"}],"browurl":"http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf","date":"2016/9/21","id":"20010","mainurl":"http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf","name":"检验批.pdf","no":"","remark":"","thumurl":"http://112.124.5.152/tkywsapp/Images/App/item-elc.png"},{"brolist":[],"browurl":"","date":"2016/9/1","id":"3515","mainurl":"","name":"京方台-56#墩防护墙-[防护墙、电缆槽竖墙、接触网支柱基础]混凝土(原材料、配合比、拌和、浇筑)检验批质量验收记录表(Ⅰ).pdf","no":"","remark":"","thumurl":"http://112.124.5.152/tkywsapp/Images/App/item-elc.png"}]
     */

    private String DT;
    private String Description;
    private int Result;
    private int count;
    /**
     * brolist : [{"id":"20010","name":"检验批.pdf","type":"1","url":"http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf"}]
     * browurl : http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf
     * date : 2016/9/21
     * id : 20010
     * mainurl : http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf
     * name : 检验批.pdf
     * no :
     * remark :
     * thumurl : http://112.124.5.152/tkywsapp/Images/App/item-elc.png
     */

    private List<InfolistBean> infolist;

    public String getDT() {
        return DT;
    }

    public void setDT(String DT) {
        this.DT = DT;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<InfolistBean> getInfolist() {
        return infolist;
    }

    public void setInfolist(List<InfolistBean> infolist) {
        this.infolist = infolist;
    }

    public static class InfolistBean implements Serializable {
        private String browurl;
        private String date;
        private String id;
        private String mainurl;
        private String name;
        private String no;
        private String remark;
        private String thumurl;
        /**
         * id : 20010
         * name : 检验批.pdf
         * type : 1
         * url : http://112.124.5.152/tkywsapp/datapath/3/10/20010/检验批.pdf
         */

        private List<BrolistBean> brolist;

        public String getBrowurl() {
            return browurl;
        }

        public void setBrowurl(String browurl) {
            this.browurl = browurl;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMainurl() {
            return mainurl;
        }

        public void setMainurl(String mainurl) {
            this.mainurl = mainurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getThumurl() {
            return thumurl;
        }

        public void setThumurl(String thumurl) {
            this.thumurl = thumurl;
        }

        public List<BrolistBean> getBrolist() {
            return brolist;
        }

        public void setBrolist(List<BrolistBean> brolist) {
            this.brolist = brolist;
        }

        public static class BrolistBean implements Serializable {
            private String id;
            private String name;
            private String type;
            private String url;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
