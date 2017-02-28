package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leguang on 2016/10/9 0009.
 */
public class SecondSearchBean implements Serializable {

    /**
     * id : 21477208
     * name : 312号墩
     * type : 1
     */

    private List<BuildlistBean> buildlist;

    public List<BuildlistBean> getBuildlist() {
        return buildlist;
    }

    public void setBuildlist(List<BuildlistBean> buildlist) {
        this.buildlist = buildlist;
    }

    public static class BuildlistBean implements Serializable {
        private String id;
        private String name;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
