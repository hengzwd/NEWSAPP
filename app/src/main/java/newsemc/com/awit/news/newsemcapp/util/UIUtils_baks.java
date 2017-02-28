package newsemc.com.awit.news.newsemcapp.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import newsemc.com.awit.news.newsemcapp.application.NewsEMCAppllication;


/**
 * Created by Administrator on 2015/7/5.
 */
public class UIUtils_baks {
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** pxz转换dip */

    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static String[] getStringArray(int id) {
        return getResource().getStringArray(id);
    }

    public static Resources getResource() {
        return getContext().getResources();
    }

    public static NewsEMCAppllication getContext() {
        return NewsEMCAppllication.getApplication();
    }

    public static int getColor(int id) {
        return getResource().getColor(id);
    }

    public static void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int str_id) {
        Toast.makeText(getContext(), str_id,Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建view对象
     *
     * @param layout_id
     *            布局id
     * @return
     */
    public static View inflate(int layout_id) {
        return View.inflate(getContext(), layout_id, null);
    }

    /**
     * 保证代码在主线程中运行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isOnMainThread()) {
            runnable.run();
        } else {
            execute(runnable);
        }
    }

    /**
     * 获取主线程中的Handler
     *
     * @return
     */
    private static Handler getMainHandler() {
        return NewsEMCAppllication.getHandler();
    }

    public static void execute(Runnable runnable) {
        getMainHandler().post(runnable);
    }

    public static void executeDelay(Runnable runnable, long delayTime) {
        getMainHandler().postDelayed(runnable, delayTime);
    }

    public static void cancelRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }

    /**
     * 判断程序是否在主线程运行
     *
     * @return
     */
    private static boolean isOnMainThread() {
        // 首先获取到主线程的tid == 再判断当前线程的tid
        return NewsEMCAppllication.getMyTid() == android.os.Process.myTid();
    }

    public static Drawable getDrawable(int id) {
        return getResource().getDrawable(id);
    }

//    public static void startActivity(Intent intent) {
//        if (BaseActivity.activity != null) {
//            // 因为activity 内部管理一个任务栈 ,通过activity打开的activity不需要设置
//            // 不需要设置intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            BaseActivity.activity.startActivity(intent);
//        } else {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getContext().startActivity(intent);
//        }
//    }

}
