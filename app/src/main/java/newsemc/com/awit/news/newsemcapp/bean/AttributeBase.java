package newsemc.com.awit.news.newsemcapp.bean;
import java.io.Serializable;

/**
 * Created by Administrator on 15-6-29.
 */
public class AttributeBase implements Serializable {

    private static final long serialVersionUID = 1L;
    private String attrName;
    private String attrValue;


    public AttributeBase() {
        super();
    }


    public AttributeBase(String attrName, String attrValue) {
        super();
        this.attrName = attrName;
        this.attrValue = attrValue;
    }


    public String getAttrName() {
        return attrName;
    }


    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }


    public String getAttrValue() {
        return attrValue;
    }


    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
