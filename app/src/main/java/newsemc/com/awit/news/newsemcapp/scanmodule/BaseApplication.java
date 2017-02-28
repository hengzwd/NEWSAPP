package newsemc.com.awit.news.newsemcapp.scanmodule;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by leguang on 2016/5/20 0031.
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();
    public static BaseApplication application;
    public static Context context;
    public static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
