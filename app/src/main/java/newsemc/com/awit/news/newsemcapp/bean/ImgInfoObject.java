package newsemc.com.awit.news.newsemcapp.bean;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;

/**
 * Created by Administrator on 2015/6/26.
 */
public class ImgInfoObject {
    private List<ImageInfo> imgInfoListList;

    private String total;

    public List<ImageInfo> getImgInfoListList() {
        return imgInfoListList;
    }

    public void setImgInfoListList(List<ImageInfo> imgInfoListList) {
        this.imgInfoListList = imgInfoListList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
