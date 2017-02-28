package newsemc.com.awit.news.newsemcapp.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by admin on 2016/2/26.
 */
public class ScrollWithListView1 {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) {
        return;
    }

    int totalHeight = 0;
    for (int i = 0; i < listAdapter.getCount(); i++) {
        View listItem = listAdapter.getView(i, null, listView);
        listItem.measure(0, 0);
        totalHeight += listItem.getMeasuredHeight();
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    listView.setLayoutParams(params);
}
}
