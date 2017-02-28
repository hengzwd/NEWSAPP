package newsemc.com.awit.news.newsemcapp.tree;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 15-10-29.
 */
public class TreeElement implements Serializable {
    private String id;
    private String nodeType;
    private String typeId;
    private String value;
    private String code;
    private String infoname;
    private String parentCode;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getInfoname() {
        return infoname;
    }

    public void setInfoname(String infoname) {
        this.infoname = infoname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String outlineTitle;
    public boolean mhasParent;
    private boolean mhasChild;
    private TreeElement parent;//该节点的父节点
    private boolean isLastSlibing;
    private int level;
    private View.OnClickListener TitleOnClickListener;//节点文本点解事件
    private ArrayList<TreeElement> childList = new ArrayList<TreeElement>();
    // private OutlineElement outlineElement;
    private boolean expanded;

    public void addChild(TreeElement c) {
        this.childList.add(c);
        this.mhasParent = false;
        this.mhasChild = true;
        c.parent = this;
        c.level = this.level + 1;

    }

    public TreeElement() {
        super();
    }

    public TreeElement(String id,String title){
        super();
        this.id = id;
        this.outlineTitle = title;
        this.level = 0;
        this.mhasParent = true;
        this.mhasChild = false;
        this.parent = null;
    }
    public TreeElement(String id,String title,boolean hasChild){
        super();
        this.id = id;
        this.outlineTitle = title;
        this.level = 0;
        this.mhasParent = true;
        this.mhasChild = hasChild;
        this.parent = null;
    }

    public TreeElement(String value,String id,String title,boolean hasChild){
        super();
        this.id = id;
        this.outlineTitle = title;
        this.level = 0;
        this.mhasParent = true;
        this.mhasChild = hasChild;
        this.parent = null;
        this.value = value;
    }
    //OnClickListener TitleOnClickListener
    public TreeElement(String value,String id,String title,String parentCode,String code,String Infoname,boolean hasChild,View.OnClickListener TitleOnClickListener){
        super();
        this.id = id;
        this.outlineTitle = title;
        this.level = 0;//在listview中的位置
        this.mhasParent = true;
        this.mhasChild = hasChild;
        this.parent = null;
        this.value = value;
        this.expanded = false;
        this.parentCode=parentCode;
        this.code=code;
        this.infoname=Infoname;
        this.setCaptionOnClickListener(TitleOnClickListener);
        if(mhasChild){
            this.childList = new ArrayList<TreeElement>();
        }
        this.isLastSlibing = false;

    }
    public View.OnClickListener getCaptionOnClickListener() {
        return TitleOnClickListener;
    }

    private void setCaptionOnClickListener(View.OnClickListener TitleOnClickListener) {
        this.TitleOnClickListener= TitleOnClickListener;
    }

    public TreeElement(String id,String title,boolean hasChild,String typeId){
        super();
        this.id = id;
        this.outlineTitle = title;
        this.level = 0;
        this.mhasParent = true;
        this.mhasChild = hasChild;
        this.parent = null;
        this.typeId = typeId;
    }
//    //父级菜单的code
//    public  TreeElement(String value,String id,String title,String parentCode,boolean hasChild,View.OnClickListener TitleOnClickListener){
//        super();
//        this.id = id;
//        this.outlineTitle = title;
//        this.level = 0;//在listview中的位置
//        this.mhasParent = true;
//        this.mhasChild = hasChild;
//        this.parent = null;
//        this.value = value;
//        this.expanded = false;
//        this.parentCode=parentCode;
//
//    }

    public TreeElement(String id, String title,String nodeType,String typeId) {
        super();
        this.id = id;
        this.outlineTitle = title;
        this.level = 0;
        this.mhasParent = true;
        this.mhasChild = false;
        this.parent = null;
        this.nodeType = nodeType;
        this.typeId = typeId;
    }

    public TreeElement(String id, String outlineTitle, boolean mhasParent,
                       boolean mhasChild, TreeElement parent, int level, boolean expanded) {
        super();
        this.id = id;
        this.outlineTitle = outlineTitle;
        this.mhasParent = mhasParent;
        this.mhasChild = mhasChild;
        this.parent = parent;
        if (parent != null) {
            this.parent.getChildList().add(this);
        }
        this.level = level;
        this.expanded = expanded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String typeId) {
        this.nodeType = typeId;
    }

    public void setChildList(ArrayList<TreeElement> childList) {
        this.childList = childList;
    }

    public String getOutlineTitle() {
        return outlineTitle;
    }

    public void setOutlineTitle(String outlineTitle) {
        this.outlineTitle = outlineTitle;
    }

    public boolean isMhasParent() {
        return mhasParent;
    }

    public void setMhasParent(boolean mhasParent) {
        this.mhasParent = mhasParent;
    }

    public boolean isMhasChild() {
        return mhasChild;
    }

    public void setMhasChild(boolean mhasChild) {
        this.mhasChild = mhasChild;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public ArrayList<TreeElement> getChildList() {
        return childList;
    }

    public boolean isLastSlibing(){
        return isLastSlibing;
    }
    public void setLastSibling(boolean isLastSibling) {
        this.isLastSlibing = isLastSibling;
    }
    public TreeElement getParent() {
        return parent;
    }

    public void setParent(TreeElement parent) {
        this.parent = parent;
    }

}

