package newsemc.com.awit.news.newsemcapp.util;

import android.util.Log;

/**
 * Created by sb on 2015/8/11.
 * Log的关闭和打开
 */
public class LogUtils {

    private static boolean openLog = false;

    public static  void i(String tag, String msg){
        if (openLog){
            Log.i(tag, msg);
        }
    }

    public static  void i(Class clazz, String msg){
        if (openLog){
            Log.i(clazz.getSimpleName(), msg);
        }
    }

    public static  void e(String tag, String msg){
        if (openLog){
            Log.e(tag, msg);
        }
    }

    public static  void e(Class clazz, String msg){
        if (openLog){
            Log.e(clazz.getSimpleName(), msg);
        }
    }

}
