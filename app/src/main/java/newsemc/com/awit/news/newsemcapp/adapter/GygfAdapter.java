package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.net.URL;
import java.util.List;
import java.util.Map;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.dao.CraftInfo;


/**
 * Created by Administrator on 2015/7/6.
 */
public class GygfAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater mInflater;
    private List<Map<String, CraftInfo>> data;

    private URL myFileUrl = null;
    private Bitmap bitmap = null;
    private BitmapUtils bitmapUtils;
    private String imageUrl = "http://www.r93535.com/gateway/file/1434301474006.jpg";
    public final class ViewHolder{
        public ImageView imageView;
        public TextView name,dateview;
    }

    public GygfAdapter(Context context, List<Map<String, CraftInfo>> data){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.data=data;
        if (bitmapUtils == null) {
            bitmapUtils = BitmapHelper.getBitmapUtils();
            bitmapUtils.configDefaultLoadingImage(R.drawable.empty_photo); // 设置加载中显示的图片
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.empty_photo); // 设置加载错误图片
        }
    }
    @Override
    public int getCount() {
        return data.size();
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
        ViewHolder holder=null;
        CraftInfo m=(CraftInfo)data.get(position);
        if(convertView==null){
            holder=new ViewHolder();

            convertView=mInflater.inflate(R.layout.grid_item_list,null);
            holder.imageView=(ImageView)convertView.findViewById(R.id.img);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.dateview=(TextView)convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
//        new Thread(new ImageShowThread()).start();
        bitmapUtils.display(holder.imageView,m.getInfoimg());
        holder.name.setText("32343214");
        holder.dateview.setText("2011111111");
//        holder.imageView.setImageBitmap(bitmap);
        Log.i("bitmap", m.getInfoimg() + "");
//        holder.name.setText(m.getInfoname());
//        holder.dateview.setText(m.getInfodate());
        return  convertView;
    }
}
