package newsemc.com.awit.news.newsemcapp.adapter;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.view.MyPopupWindow;


public class PopWindowAdapter extends BaseAdapter{
	
	private int mPosition;
	private List<String> mItems;
	private List<Integer> micons;
	private MyPopupWindow mWindow;
	private Context mContext;	
	private onItemClickListener mListener;
	
	public PopWindowAdapter(MyPopupWindow window,Context context,List<String> items,List<Integer> icons){
		this.mContext= context;
		this.mItems=items;
		this.micons=icons;
		this.mWindow=window;
	}
	
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder { // item的4个控件
		public TextView text;
		public ImageView icon;
	}
			
	public View getView(final int arg0, final View arg1, ViewGroup arg2) {
		//获取设置好的listener
		mListener=mWindow.getListener();
		View view=arg1;
		ViewHolder holder=null;
		if(view==null){
			view=View.inflate(mContext, R.layout.myspinner_list_item, null);
			holder = new ViewHolder();
			holder.text=(TextView) view.findViewById(R.id.tv_text);
			holder.icon = (ImageView) view.findViewById(R.id.icon);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		holder.text.setText(mItems.get(arg0));
		holder.icon.setImageResource(micons.get(arg0));
		view.setOnClickListener(new OnClickListener() {	
			public void onClick(View v) {
				mPosition=arg0;	
				mWindow.close();
				mListener.click(mPosition,arg1);
			}
		});
		return view;
	}
	//定义接口和一个为实现的方法
	public interface onItemClickListener{
		public void click(int position, View view);
	}	
}
