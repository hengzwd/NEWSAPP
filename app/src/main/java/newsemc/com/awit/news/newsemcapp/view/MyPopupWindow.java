package newsemc.com.awit.news.newsemcapp.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.adapter.PopWindowAdapter;


public class MyPopupWindow extends PopupWindow implements OnItemClickListener {

	private List<String> mItems;
	private MyPopupWindow mWindow;
	private PopWindowAdapter.onItemClickListener mListener;
	private Context mContext;
	
	public MyPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}
	
	public MyPopupWindow(Context context, int width, int height, List<String> items,List<Integer> icons) {
		this(context, width, height, items, true,icons);
	}

	public MyPopupWindow(Context context, int width, int height, List<String> items, boolean focusable,List<Integer> icons) {
		mContext = context;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View contentView = inflater.inflate(R.layout.activity_pop, null);
		calculateView(contentView);
		// 设置PopupWindow的View
		this.setContentView(contentView);
		// 设置PopupWindow弹出窗体的宽
		this.setWidth(width);
		// 设置PopupWindow弹出窗体的高
		 this.setHeight(height);
//		this.setHeight(640);
		 // 设置popwindow背景色
		 this.setBackgroundDrawable(context.getResources().getDrawable(
				 R.drawable.shap_ser));
		this.setFocusable(focusable);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		this.mItems = items;
		ListView listView = (ListView) contentView.findViewById(R.id.lv_list);
		mWindow = this;
		PopWindowAdapter adapter = new PopWindowAdapter(mWindow, context,
				mItems,icons);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyPopupWindow.this.dismiss();

	}

	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
	}

	public void close() {
		this.dismiss();
	}

	public int position() {
		return 0;
	}

	public void setOnItemClickListener(PopWindowAdapter.onItemClickListener listener) {
		this.mListener = listener;
	}

	public PopWindowAdapter.onItemClickListener getListener() {
		// 可以通过this的实例来获取设置好的listener
		return mListener;
	}
	
	/**
	 * 估量加载的View的宽高
	 * @param view
	 */
	private void calculateView(View view) {
		int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
	}
}
