package newsemc.com.awit.news.newsemcapp.util;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by sb on 2015/7/31.
 */
public class MyDialog extends Dialog {
    private static int default_width = 360; //默认宽度
    private static int default_height = 320;//默认高度

    public MyDialog(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }

    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = widthPixels - 140;
        window.setAttributes(params);
    }
}
