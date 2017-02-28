package newsemc.com.awit.news.newsemcapp.adapter;

import com.lidroid.xutils.BitmapUtils;

import newsemc.com.awit.news.newsemcapp.util.FileUtils;
import newsemc.com.awit.news.newsemcapp.util.UIUtils;


/**
 * Created by Administrator on 2015/7/5.
 */
public class BitmapHelper {
    private BitmapHelper() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
     *
     * @param
     * @return
     */
    public static BitmapUtils getBitmapUtils() {
        if (bitmapUtils == null) {
            //  1 上下文  2 图片缓存的路径 3 最多消耗可以使用ram内存的百分之50 在  0.05- 0.8 之间
            bitmapUtils = new BitmapUtils(UIUtils.getContext(), FileUtils.getIconDir(),0.5f);
        }
        return bitmapUtils;
    }
}
