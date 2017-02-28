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


/**
 * Created by Administrator on 2015/7/11.
 */
public class CompanyNewsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    // private String[] data = {"总","关于开展2015年5月份月度专项检查的通知","05-06","50人已读"};
    private List<CompanyInfo> data;

    class ViewHolder{
        TextView title;
        TextView time;
        TextView read;
    }

    public CompanyNewsAdapter(Context context,List<CompanyInfo> data){
        mInflater = LayoutInflater.from(context);
        this.data=data;
        Log.i("CompanyNewsAdapter","yesyes");
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
        CompanyInfo m = (CompanyInfo)this.getItem(position);
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.work_list_item,null);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.time= (TextView)convertView.findViewById(R.id.time);
            holder.read = (TextView)convertView.findViewById(R.id.read);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(m.getInfoname());
        holder.time.setText(m.getInfodate());
        holder.read.setText(m.getNum());
        return convertView;
    }
}
