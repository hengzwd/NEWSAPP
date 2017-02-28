package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leguang on 2016/10/9 0009.
 */
public class FirstSearchBean implements Serializable {

    /**
     * buildid : 6623
     * buildname : 路基22
     * buildtype : 568
     * projectinfoid : 86
     * projectinfoname : 新建北京至沈阳铁路客运专线辽宁段（不含代建）
     * projectsectionid :
     * projectsectionname :
     */

    private List<BuildlistBean> buildlist;

    public List<BuildlistBean> getBuildlist() {
        return buildlist;
    }

    public void setBuildlist(List<BuildlistBean> buildlist) {
        this.buildlist = buildlist;
    }

    public static class BuildlistBean implements Serializable {
        private String buildid;
        private String buildname;
        private String buildtype;
        private String projectinfoid;
        private String projectinfoname;
        private String projectsectionid;
        private String projectsectionname;

        public String getBuildid() {
            return buildid;
        }

        public void setBuildid(String buildid) {
            this.buildid = buildid;
        }

        public String getBuildname() {
            return buildname;
        }

        public void setBuildname(String buildname) {
            this.buildname = buildname;
        }

        public String getBuildtype() {
            return buildtype;
        }

        public void setBuildtype(String buildtype) {
            this.buildtype = buildtype;
        }

        public String getProjectinfoid() {
            return projectinfoid;
        }

        public void setProjectinfoid(String projectinfoid) {
            this.projectinfoid = projectinfoid;
        }

        public String getProjectinfoname() {
            return projectinfoname;
        }

        public void setProjectinfoname(String projectinfoname) {
            this.projectinfoname = projectinfoname;
        }

        public String getProjectsectionid() {
            return projectsectionid;
        }

        public void setProjectsectionid(String projectsectionid) {
            this.projectsectionid = projectsectionid;
        }

        public String getProjectsectionname() {
            return projectsectionname;
        }

        public void setProjectsectionname(String projectsectionname) {
            this.projectsectionname = projectsectionname;
        }
    }
}
