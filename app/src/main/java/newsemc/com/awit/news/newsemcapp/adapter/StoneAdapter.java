package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;


/**
 * Created by sb on 2015/7/29.
 */
public class StoneAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<ImgInfoList> data;
    private Context context;

    public final class ViewHolder{
        public ImageView imageView;
        public TextView text,read,dateview;
    }

    public StoneAdapter(Context context, List< ImgInfoList> data){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        Log.i("list", data + "");
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size() ;
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
//        if (position == 0){
//            convertView = UIUtils.inflate(R.layout.slideshowview);
//        }else{
            ViewHolder holder=null;
            ImgInfoList m = (ImgInfoList)this.getItem(position);
            if(convertView ==null){
                holder=new ViewHolder();

                convertView=mInflater.inflate(R.layout.main_list_item,null);
//            holder.imageView=(ImageView)convertView.findViewById(R.id.drawer_list_itme_img);
                holder.text=(TextView)convertView.findViewById(R.id.title);
//            holder.title=(TextView)convertView.findViewById(R.id.drawer_list_itme_title);
                holder.dateview=(TextView)convertView.findViewById(R.id.time);
                holder.read=(TextView)convertView.findViewById(R.id.read);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
//        holder.imageView.setImageResource((Integer)data.get(position).get("drawer_list_itme_img"));
        /*holder.title.setText((String) data.get(position).get("drawer_list_itme_title"));
        holder.text.setText((String)data.get(position).get("drawer_list_itme_text"));
        holder.dateview.setText((String)data.get(position).get("drawer_list_itme_date"));
        holder.read.setText((String)data.get(position).get("drawer_list_itme_read"));*/
            Log.i("position",position+"");
//        holder.title.setText(m.getInfoid());
            holder.text.setText(m.getInfoname());
//        holder.dateview.setText(m.getInfotype());
            holder.dateview.setText(m.getInfodate());
            holder.read.setText(m.getNum());
//        }
        return  convertView;

    }
}
