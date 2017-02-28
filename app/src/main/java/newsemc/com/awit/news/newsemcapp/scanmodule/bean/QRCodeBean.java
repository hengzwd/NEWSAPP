package newsemc.com.awit.news.newsemcapp.scanmodule.bean;

import java.io.Serializable;

/**
 * Created by leguang on 2016/9/23 0023.
 * 联系邮箱:langmanleguang@qq.com
 */
public class QRCodeBean implements Serializable{

    /**
     * id : 44468
     * type : 2
     * name : 某某隧道
     */

    private String id;
    private String type;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
