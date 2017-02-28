package newsemc.com.awit.news.newsemcapp.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ZDGC_INFO.
 */
public class ZdgcInfo {

    private String buildname;
    private String designtype;
    private String designnum;
    private String dayfinish;
    private String mothfinish;
    private String kailei;
    private String kaileiratio;
    private String plandate;
    private String delaynum;
    private String delaydays;

    public ZdgcInfo() {
    }

    public ZdgcInfo(String buildname) {
        this.buildname = buildname;
    }

    public ZdgcInfo(String buildname, String designtype, String designnum, String dayfinish, String mothfinish, String kailei, String kaileiratio, String plandate, String delaynum, String delaydays) {
        this.buildname = buildname;
        this.designtype = designtype;
        this.designnum = designnum;
        this.dayfinish = dayfinish;
        this.mothfinish = mothfinish;
        this.kailei = kailei;
        this.kaileiratio = kaileiratio;
        this.plandate = plandate;
        this.delaynum = delaynum;
        this.delaydays = delaydays;
    }

    public String getBuildname() {
        return buildname;
    }

    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    public String getDesigntype() {
        return designtype;
    }

    public void setDesigntype(String designtype) {
        this.designtype = designtype;
    }

    public String getDesignnum() {
        return designnum;
    }

    public void setDesignnum(String designnum) {
        this.designnum = designnum;
    }

    public String getDayfinish() {
        return dayfinish;
    }

    public void setDayfinish(String dayfinish) {
        this.dayfinish = dayfinish;
    }

    public String getMothfinish() {
        return mothfinish;
    }

    public void setMothfinish(String mothfinish) {
        this.mothfinish = mothfinish;
    }

    public String getKailei() {
        return kailei;
    }

    public void setKailei(String kailei) {
        this.kailei = kailei;
    }

    public String getKaileiratio() {
        return kaileiratio;
    }

    public void setKaileiratio(String kaileiratio) {
        this.kaileiratio = kaileiratio;
    }

    public String getPlandate() {
        return plandate;
    }

    public void setPlandate(String plandate) {
        this.plandate = plandate;
    }

    public String getDelaynum() {
        return delaynum;
    }

    public void setDelaynum(String delaynum) {
        this.delaynum = delaynum;
    }

    public String getDelaydays() {
        return delaydays;
    }

    public void setDelaydays(String delaydays) {
        this.delaydays = delaydays;
    }

}
