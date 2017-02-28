package newsemc.com.awit.news.newsemcapp.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.socks.library.KLog;

import java.io.File;
import java.util.List;

import newsemc.com.awit.news.newsemcapp.bean.UserLogin;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.DaoMaster;
import newsemc.com.awit.news.newsemcapp.dao.DaoSession;
import newsemc.com.awit.news.newsemcapp.dao.SpecialAccountInfo;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.QRCodeBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.phoneInfo;
import newsemc.com.awit.news.newsemcapp.scanmodule.exception.AppExceptionHandler;
import newsemc.com.awit.news.newsemcapp.scanmodule.utils.SharedPreferencesUtils;
import newsemc.com.awit.news.newsemcapp.util.ValueConfig;


/**
 * Created by Administrator on 15-6-2.
 */
public class NewsEMCAppllication extends Application {
    private final String TAG = "NewsEMCAppllication";
    public static Context context;
    public static NewsEMCAppllication application;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static int myTid;
    private static Handler handler;
    private static int mainThreadId;
    private static Thread mainThread;
    public List<SpecialAccountInfo> specialAccountInfoList;
    public List<CompanyInfo> companyList;
    public static RequestQueue mRequestQueue;
    public static String mToken;
    public static QRCodeBean mQRCodeBean;
    public static UserLogin mUserLogin;
    public static phoneInfo phoneInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        //获取扫描Token值
        mToken = (String) SharedPreferencesUtils.get(this, "scan_login_token", "");

        phoneInfo=new phoneInfo();
        //Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler.getInstance(this));
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "emc/Cache");
        Log.e(TAG, cacheDir.getPath());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new WeakMemoryCache())
                .threadPoolSize(1)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(500)
                .build();
        ImageLoader.getInstance().init(config);
        application = this;
        myTid = android.os.Process.myTid();
        //Context
        context = getApplicationContext();
        //mainThreadId
        mainThreadId = android.os.Process.myTid();
        //Thread-->object
        mainThread = Thread.currentThread();
        handler = new Handler();
        KLog.init(true, "SHTW");
    }

    // ���判断
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, ValueConfig.DB_JING, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    public static NewsEMCAppllication getApplication() {
        return application;
    }

    public static int getMyTid() {
        return myTid;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Thread getMainThread() {
        return mainThread;
    }
}
