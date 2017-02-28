package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.util.List;

/**
 * Author：Administrator on 2016/10/10 0010 11:33
 * Email：langmanleguang@qq.com
 */
public class SpecialsTructureRecordsListBean {

    private String finishDate;
    private String id;
    private String name;
    private String partname;
    private int viewtype;
    /**
     * id : 3
     * name : 检验批
     * total : 2
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

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public int getViewtype() {
        return viewtype;
    }

    public void setViewtype(int viewtype) {
        this.viewtype = viewtype;
    }

    public List<ArchiveInfoListBean> getArchiveInfoList() {
        return archiveInfoList;
    }

    public void setArchiveInfoList(List<ArchiveInfoListBean> archiveInfoList) {
        this.archiveInfoList = archiveInfoList;
    }
}
