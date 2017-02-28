package newsemc.com.awit.news.newsemcapp.interfaces;

/**
 * Created by Administrator on 2015/6/26.
 */
public interface HttpResultListener {
    public void onStartRequest();
    public void onResult(Object obj);
    public void onFinish();
}
