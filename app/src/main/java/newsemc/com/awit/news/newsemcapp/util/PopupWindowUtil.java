package newsemc.com.awit.news.newsemcapp.util;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.adapter.PopWindowAdapter;
import newsemc.com.awit.news.newsemcapp.view.MyPopupWindow;


/**
 * Created by Administrator on 2015/7/27.
 */
public class PopupWindowUtil {
    /**
     * 弹出PopupWindow
     */
    public static void alertPop(Context context, final List<String> items, View view, int width, int height, final WindowCallBack callBack,List<Integer> icons) {
        MyPopupWindow window = new MyPopupWindow(context,
                width, height, items, false,icons);
        window.showAsDropDown(view, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                callBack.onDismiss();
            }
        });
        window.setOnItemClickListener(new PopWindowAdapter.onItemClickListener() {
            public void click(int position, View view) {
                callBack.click(position, view);
            }
        });
    }

    /**
     * 弹PopupWindow回调
     *
     */
    public interface WindowCallBack {
        public void onDismiss();
        public void click(int position, View view);
    }
}
