package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.bean.ImgInfoList;
import newsemc.com.awit.news.newsemcapp.dao.ImageInfo;
import newsemc.com.awit.news.newsemcapp.util.UIUtils;


/**
 * Created by Administrator on 15-6-5.
 */
public class MainNewsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ImageInfo> data;
    private Context context;

    public final class ViewHolder{
        public ImageView imageView;
        public TextView text,read,dateview,unit,tag;
    }

    public  MainNewsAdapter(Context context){
        this.context=context;
    }
    public MainNewsAdapter(Context context, List<ImageInfo> data){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.data=data;
    }
    public MainNewsAdapter(){
        super();
    }
    @Override
    public int getCount() {
        return data==null ? 0 :data.size() ;
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
        ViewHolder holder;
        ImageInfo m = (ImageInfo)this.getItem(position );
        if(convertView ==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.main_list_item,null);
            holder.text=(TextView)convertView.findViewById(R.id.title);
            holder.dateview=(TextView)convertView.findViewById(R.id.time);
            holder.read=(TextView)convertView.findViewById(R.id.read);
            holder.unit=(TextView)convertView.findViewById(R.id.unit);
            holder.tag=(TextView)convertView.findViewById(R.id.tag);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Log.i("position",position+"");
        Log.i("主adapter===infoname", holder + "");
        holder.text.setText(m.getInfoname());
        holder.dateview.setText(m.getInfodate());
        holder.read.setText(m.getNum());
        holder.unit.setText(m.getSource());
        if(m.getIsnew().equals("1")) {
            holder.tag.setText("新");
        }else {
            holder.tag.setVisibility(View.GONE);
        }
//        else {
//            if(Integer.valueOf(m.getNum().trim()) > 100){
//                holder.tag.setText("热");
//            }else
//                holder.tag.setVisibility(View.GONE);
//        }

//        }
        return  convertView;

    }
}
