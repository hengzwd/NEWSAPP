package newsemc.com.awit.news.newsemcapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17.
 */
public class NewsDetailEntity {

    /**
     * status : 0
     * msg : 成功
     * data : {"list":{"infodate":"2015-11-26","id":"9518","infosontype":"","num":"403","infoname":"卢春房副总经理现场检查京沈客专项目建设。","appendix":[],"publish":"jslnwangjiang","infocontent":"","attachments":[{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515483260.png","id":209330,"fileSize":832008,"fileName":"QQ截图20151126132041.png","contentType":"png","saveFileName":"1448515483260.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484428.png","id":209332,"fileSize":867149,"fileName":"QQ截图20151126131514.png","contentType":"png","saveFileName":"1448515484428.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484694.png","id":209334,"fileSize":767704,"fileName":"QQ截图20151126131319.png","contentType":"png","saveFileName":"1448515484694.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484850.png","id":209336,"fileSize":633877,"fileName":"QQ截图20151126131713.png","contentType":"png","saveFileName":"1448515484850.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515485084.png","id":209338,"fileSize":661319,"fileName":"QQ截图20151126131948.png","contentType":"png","saveFileName":"1448515485084.png","createDate":null}],"infotype":"16","publishquarry":"综合部","publishusername":"王江"},"total":"1"}
     */

    private String status;
    private String msg;
    /**
     * list : {"infodate":"2015-11-26","id":"9518","infosontype":"","num":"403","infoname":"卢春房副总经理现场检查京沈客专项目建设。","appendix":[],"publish":"jslnwangjiang","infocontent":"","attachments":[{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515483260.png","id":209330,"fileSize":832008,"fileName":"QQ截图20151126132041.png","contentType":"png","saveFileName":"1448515483260.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484428.png","id":209332,"fileSize":867149,"fileName":"QQ截图20151126131514.png","contentType":"png","saveFileName":"1448515484428.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484694.png","id":209334,"fileSize":767704,"fileName":"QQ截图20151126131319.png","contentType":"png","saveFileName":"1448515484694.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484850.png","id":209336,"fileSize":633877,"fileName":"QQ截图20151126131713.png","contentType":"png","saveFileName":"1448515484850.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515485084.png","id":209338,"fileSize":661319,"fileName":"QQ截图20151126131948.png","contentType":"png","saveFileName":"1448515485084.png","createDate":null}],"infotype":"16","publishquarry":"综合部","publishusername":"王江"}
     * total : 1
     */

    private DataEntity data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * infodate : 2015-11-26
         * id : 9518
         * infosontype :
         * num : 403
         * infoname : 卢春房副总经理现场检查京沈客专项目建设。
         * appendix : []
         * publish : jslnwangjiang
         * infocontent :
         * attachments : [{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515483260.png","id":209330,"fileSize":832008,"fileName":"QQ截图20151126132041.png","contentType":"png","saveFileName":"1448515483260.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484428.png","id":209332,"fileSize":867149,"fileName":"QQ截图20151126131514.png","contentType":"png","saveFileName":"1448515484428.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484694.png","id":209334,"fileSize":767704,"fileName":"QQ截图20151126131319.png","contentType":"png","saveFileName":"1448515484694.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515484850.png","id":209336,"fileSize":633877,"fileName":"QQ截图20151126131713.png","contentType":"png","saveFileName":"1448515484850.png","createDate":null},{"savePath":"http://www.r93535.com/gateway/file/20151126//16//1448515485084.png","id":209338,"fileSize":661319,"fileName":"QQ截图20151126131948.png","contentType":"png","saveFileName":"1448515485084.png","createDate":null}]
         * infotype : 16
         * publishquarry : 综合部
         * publishusername : 王江
         */

        private ListEntity list;
        private String total;

        public void setList(ListEntity list) {
            this.list = list;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public ListEntity getList() {
            return list;
        }

        public String getTotal() {
            return total;
        }

        public static class ListEntity {
            private String infodate;
            private String id;
            private String infosontype;
            private String num;
            private String infoname;
            private String publish;
            private String infocontent;
            private String infotype;
            private String publishquarry;
            private String publishusername;
            private List<AppendixEntity> appendix;
            /**
             * savePath : http://www.r93535.com/gateway/file/20151126//16//1448515483260.png
             * id : 209330
             * fileSize : 832008
             * fileName : QQ截图20151126132041.png
             * contentType : png
             * saveFileName : 1448515483260.png
             * createDate : null
             */

            private List<AttachmentsEntity> attachments;

            public void setInfodate(String infodate) {
                this.infodate = infodate;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setInfosontype(String infosontype) {
                this.infosontype = infosontype;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public void setInfoname(String infoname) {
                this.infoname = infoname;
            }

            public void setPublish(String publish) {
                this.publish = publish;
            }

            public void setInfocontent(String infocontent) {
                this.infocontent = infocontent;
            }

            public void setInfotype(String infotype) {
                this.infotype = infotype;
            }

            public void setPublishquarry(String publishquarry) {
                this.publishquarry = publishquarry;
            }

            public void setPublishusername(String publishusername) {
                this.publishusername = publishusername;
            }

            public void setAppendix(List<AppendixEntity> appendix) {
                this.appendix = appendix;
            }

            public void setAttachments(List<AttachmentsEntity> attachments) {
                this.attachments = attachments;
            }

            public String getInfodate() {
                return infodate;
            }

            public String getId() {
                return id;
            }

            public String getInfosontype() {
                return infosontype;
            }

            public String getNum() {
                return num;
            }

            public String getInfoname() {
                return infoname;
            }

            public String getPublish() {
                return publish;
            }

            public String getInfocontent() {
                return infocontent;
            }

            public String getInfotype() {
                return infotype;
            }

            public String getPublishquarry() {
                return publishquarry;
            }

            public String getPublishusername() {
                return publishusername;
            }

            public List<AppendixEntity> getAppendix() {
                return appendix;
            }

            public List<AttachmentsEntity> getAttachments() {
                return attachments;
            }

            public static class AttachmentsEntity {
                private String savePath;
                private int id;
                private int fileSize;
                private String fileName;
                private String contentType;
                private String saveFileName;
                private Object createDate;

                public void setSavePath(String savePath) {
                    this.savePath = savePath;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setFileSize(int fileSize) {
                    this.fileSize = fileSize;
                }

                public void setFileName(String fileName) {
                    this.fileName = fileName;
                }

                public void setContentType(String contentType) {
                    this.contentType = contentType;
                }

                public void setSaveFileName(String saveFileName) {
                    this.saveFileName = saveFileName;
                }

                public void setCreateDate(Object createDate) {
                    this.createDate = createDate;
                }

                public String getSavePath() {
                    return savePath;
                }

                public int getId() {
                    return id;
                }

                public int getFileSize() {
                    return fileSize;
                }

                public String getFileName() {
                    return fileName;
                }

                public String getContentType() {
                    return contentType;
                }

                public String getSaveFileName() {
                    return saveFileName;
                }

                public Object getCreateDate() {
                    return createDate;
                }
            }

            public static class AppendixEntity {
                public String getSavePath() {
                    return savePath;
                }

                public String getFileName() {
                    return fileName;
                }

                public void setSavePath(String savePath) {
                    this.savePath = savePath;
                }

                public void setFileName(String fileName) {
                    this.fileName = fileName;
                }

                private String savePath;
                private String fileName;
            }
        }
    }
}
