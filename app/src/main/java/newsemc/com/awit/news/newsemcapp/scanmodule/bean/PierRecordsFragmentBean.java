package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.util.List;

/**
 * Author：Administrator on 2016/10/10 0010 08:51
 * Email：langmanleguang@qq.com
 */
public class PierRecordsFragmentBean {

    /**
     * Description : Success
     * Result : 0
     * infolist : [{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"","id":"27065","name":"墩台"},null,{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"","id":"48833","name":"承台"},null,{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"2016-06-28","id":"220103","name":"1号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"2016-06-30","id":"220104","name":"2号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"2016-07-06","id":"220097","name":"3号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"2016-07-10","id":"220098","name":"4号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"","id":"220099","name":"5号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"2016-07-12","id":"220100","name":"6号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"2016-07-10","id":"220101","name":"7号桩"},{"archiveInfoList":[{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}],"finishDate":"","id":"220102","name":"8号桩"},null]
     */

    private String Description;
    private int Result;
    /**
     * archiveInfoList : [{"id":"3","name":"检验批","total":"2","type":"5"},{"id":"5","name":"试验报告","total":"1","type":"5"},{"id":"1","name":"影像资料文件","total":"27","type":"5"}]
     * finishDate :
     * id : 27065
     * name : 墩台
     */

    private List<InfolistBean> infolist;

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

    public List<InfolistBean> getInfolist() {
        return infolist;
    }

    public void setInfolist(List<InfolistBean> infolist) {
        this.infolist = infolist;
    }

    public static class InfolistBean {
        private String finishDate;
        private String id;
        private String name;
        /**
         * id : 3
         * name : 检验批
         * total : 2
         * type : 5
         */

        private List<ArchiveInfoListBean> archiveInfoList;

        public String getFinishDate() {
            return finishDate;
        }

        public void setFinishDate(String finishDate) {
            this.finishDate = finishDate;
        }

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

        public List<ArchiveInfoListBean> getArchiveInfoList() {
            return archiveInfoList;
        }

        public void setArchiveInfoList(List<ArchiveInfoListBean> archiveInfoList) {
            this.archiveInfoList = archiveInfoList;
        }

        public static class ArchiveInfoListBean {
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
    }
}
