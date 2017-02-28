package newsemc.com.awit.news.newsemcapp.bean;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetail;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailMainItem;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPeriod;
import newsemc.com.awit.news.newsemcapp.dao.ProjectInfoDetailPic;

/**
 * Created by lianghl on 2015/12/16.
 * 项目信息详情改动版（实体类）
 */
public class ProjectDetail {
    private ProjectInfoDetail mProjectInfoDetail;
    private List<ProjectInfoDetailPic> mProjectInfoDetailPicList;
    private ProjectInfoDetailPeriod mProjectInfoDetailPeriod;
    private ProjectInfoDetailMainItem mProjectInfoDetailMainItem;

    public void setProjectInfoDetail(ProjectInfoDetail projectInfoDetail){
        mProjectInfoDetail = projectInfoDetail;
    }

    public ProjectInfoDetail getProjectInfoDetail(){
        return mProjectInfoDetail;
    }

    public void setProjectInfoDetailPicList(List<ProjectInfoDetailPic> projectInfoDetailPicList){
        mProjectInfoDetailPicList = projectInfoDetailPicList;
    }

    public List<ProjectInfoDetailPic> getProjectInfoDetailPicList(){
        return mProjectInfoDetailPicList;
    }

    public void setProjectInfoDetailPeriod(ProjectInfoDetailPeriod projectInfoDetailPeriod){
        mProjectInfoDetailPeriod = projectInfoDetailPeriod;
    }

    public ProjectInfoDetailPeriod getProjectInfoDetailPeriod(){
        return mProjectInfoDetailPeriod;
    }

    public void setProjectInfoDetailMainItem(ProjectInfoDetailMainItem projectInfoDetailMainItem) {
        mProjectInfoDetailMainItem = projectInfoDetailMainItem;
    }

    public ProjectInfoDetailMainItem getProjectInfoDetailMainItem(){
        return mProjectInfoDetailMainItem;
    }
}
