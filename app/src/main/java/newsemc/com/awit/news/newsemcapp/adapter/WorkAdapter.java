package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.dao.CompanyInfo;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;

/**
 * Created by admin on 2016/2/26.
 */
public class WorkAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<CompanyInfo> data;
    private Context context;
    public final  class ViewHolder{
        TextView title;
        TextView time;
        TextView read;
        TextView unit;
        TextView tag;
    }
    public  WorkAdapter(Context context){
        this.context=context;
    }
    public WorkAdapter(Context context,List<CompanyInfo> data){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.data=data;
    }



    @Override
    public int getCount() {
        return data==null ? 0 :data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CompanyInfo m = (CompanyInfo)this.getItem(position);
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.menu_list,null);
            holder = new ViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.time= (TextView)convertView.findViewById(R.id.time);
            holder.read = (TextView)convertView.findViewById(R.id.read);
            holder.unit = (TextView)convertView.findViewById(R.id.unit);
            holder.tag = (TextView)convertView.findViewById(R.id.tag);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(m.getInfoname());
        holder.time.setText(m.getInfodate());
        holder.read.setText(m.getNum());
        holder.unit.setText(m.getSource());
//            holder.tag.setText(m.getIsnew());
        if(m.getIsnew().equals("1")) {
            Log.i("新闻新闻新闻", "oooooo");
            holder.tag.setText("新");
        }else{
            holder.tag.setVisibility(View.GONE);
        }

//            else
//            {
//                if(Integer.valueOf(m.getNum()) > 100){
////                    holder.tag.setText("热");
//                    holder.tag.setText("新");
//                }
////                else {
////                    holder.tag.setVisibility(View.GONE);
////                }
//            }
//            if (m.getIsnew().equals("1")){
//                Log.i("新闻新闻新闻", "oooooo");
//                holder.tag.setText("新");
//            }else if (m.getIsnew().equals("0")){
//                holder.tag.setVisibility(View.GONE);
//            }else {
//                if(Integer.valueOf(m.getNum()) > 100){
//                    holder.tag.setText("热");
//                }
////                else {
////                    holder.tag.setVisibility(View.GONE);
////                }
//            }
        return convertView;
    }
}
