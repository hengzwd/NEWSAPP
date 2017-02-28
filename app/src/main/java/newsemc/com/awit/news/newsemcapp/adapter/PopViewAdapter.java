package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.viewbadger.BadgeView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import newsemc.com.awit.news.newsemcapp.R;


/**
 * Created by sb on 2015/7/31.
 */
public class PopViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> data;
    private LayoutInflater mInflater;
    private OnItemClick onItemClick;
    private String oanum,wylcnum;
    public PopViewAdapter(Context context,List<Map<String,Object>> data) {
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.data=data;
    }

    public final class ViewHolder{
        public ImageView imageView;
        public TextView nameview;
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();

            convertView=mInflater.inflate(R.layout.pop_grid_item,null);
            holder.imageView=(ImageView)convertView.findViewById(R.id.pop_img);
            holder.nameview=(TextView)convertView.findViewById(R.id.pop_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        if(data != null && data.size() >= position + 1 && data.get(position).get("pop_text").equals("公文处理")){
            Log.i("公文处理",data.get(position).get("pop_num")+"");
            Log.i("data===========", data + "");
            if (data.get(position).get("pop_num")==null){
//                Toast.makeText(context,"暂无未读消息",Toast.LENGTH_SHORT).show();
            }else{
                oanum=data.get(position).get("pop_num").toString();
                Log.i("oanum",oanum);
                BadgeView badgeView = new BadgeView(context, holder.imageView);
                badgeView.setText(oanum);
                badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                badgeView.show();
            }
        }else if(data != null && data.size() >= position + 1 && data.get(position).get("pop_text").equals("隧道监控")){
            Log.i("隧道监控", "lalalalal");
            Log.i("data===========",data+"");
            if(data.get(position).get("pop_num")==null){
//                Toast.makeText(context,"暂无未读消息",Toast.LENGTH_SHORT).show();
            }else{
                wylcnum=data.get(position).get("pop_num").toString();
                Log.i("wylcnum",wylcnum);
                BadgeView badgeView = new BadgeView(context, holder.imageView);
                badgeView.setText(wylcnum);
                badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                badgeView.show();
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    Log.i("adapter===position",position+"");
                    onItemClick.itemClick(v, position);
                }
            }
        });
//        Log.i("pop_text_name",data.get(position).get("pop_text")+"");
//        Log.i("pop_img_resid",(Integer) data.get(position).get("pop_img")+"");
        holder.imageView.setImageResource((Integer) data.get(position).get("pop_img"));
        holder.nameview.setText((String)data.get(position).get("pop_text"));
        return  convertView;
    }

    public interface OnItemClick {
        public void itemClick(View v,int i);
    }

    /**
     * 设置回调
     */
    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
}
