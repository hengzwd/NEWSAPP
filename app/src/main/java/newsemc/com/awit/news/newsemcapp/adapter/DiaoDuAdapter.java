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
import newsemc.com.awit.news.newsemcapp.dao.DiaoDuInfo;


/**
 * Created by Administrator on 2015/7/2.
 */
public class DiaoDuAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    // private String[] data = {"总","关于开展2015年5月份月度专项检查的通知","05-06","50人已读"};
    private List<DiaoDuInfo> data;

    class ViewHolder{
        TextView type;
        TextView title;
        TextView time;
//        TextView read;
    }

    public DiaoDuAdapter(Context context,List<DiaoDuInfo> data){
        mInflater = LayoutInflater.from(context);
        this.data=data;
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
        ViewHolder holder;
        DiaoDuInfo m=(DiaoDuInfo) this.getItem(position);
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.diaodu_list_item,null);
            holder.type = (TextView)convertView.findViewById(R.id.type);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.time= (TextView)convertView.findViewById(R.id.time);
//            holder.read = (TextView)convertView.findViewById(R.id.read);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.type.setText(m.getUploaddate());
        holder.title.setText(m.getFiletitle());
//        holder.read.setText("0");
        if(m.getNum().equals("")) {
            Log.i("number","hahahaha");
            holder.time.setText("");
        }
        return convertView;
    }
}
