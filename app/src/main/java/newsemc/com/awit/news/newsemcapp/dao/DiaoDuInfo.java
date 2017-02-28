package newsemc.com.awit.news.newsemcapp.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DIAO_DU_INFO.
 */
public class DiaoDuInfo {

    private String docCode;
    private String filetitle;
    private String uploaddate;
    private String num;

    public DiaoDuInfo() {
    }

    public DiaoDuInfo(String docCode) {
        this.docCode = docCode;
    }

    public DiaoDuInfo(String docCode, String filetitle, String uploaddate, String num) {
        this.docCode = docCode;
        this.filetitle = filetitle;
        this.uploaddate = uploaddate;
        this.num = num;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getFiletitle() {
        return filetitle;
    }

    public void setFiletitle(String filetitle) {
        this.filetitle = filetitle;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

}
