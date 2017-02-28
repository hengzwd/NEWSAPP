package newsemc.com.awit.news.newsemcapp.scanmodule.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static Toast mToast;

    public static void showToast(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
