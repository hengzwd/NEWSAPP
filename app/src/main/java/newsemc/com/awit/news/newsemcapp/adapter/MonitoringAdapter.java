package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.dao.MeasureDetailInfo;


/**
 * Created by Administrator on 15-6-30.
 */
public class MonitoringAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MeasureDetailInfo> list;
//    private String[] data = {"1","武九客专瑞九段/RJZQ-1标/吴家铺隧道出口","DK+188+655/GD00",
//            "22.3","5.7","2015-05-16 08:26","中级","未处置"};

    public MonitoringAdapter(Context context,List<MeasureDetailInfo> list){
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    class ViewHolder{
        TextView m_1;
        TextView m_2;
        TextView m_3;
        TextView m_4;
        TextView m_5;
        TextView m_6;
        TextView m_7;
        TextView m_8;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.monitoring_list_item,null);
            holder.m_1 = (TextView)convertView.findViewById(R.id.monitoring_1);
            holder.m_2 = (TextView)convertView.findViewById(R.id.monitoring_2);
            holder.m_3 = (TextView)convertView.findViewById(R.id.monitoring_3);
            holder.m_4 = (TextView)convertView.findViewById(R.id.monitoring_4);
            holder.m_5 = (TextView)convertView.findViewById(R.id.monitoring_5);
            holder.m_6 = (TextView)convertView.findViewById(R.id.monitoring_6);
            holder.m_7 = (TextView)convertView.findViewById(R.id.monitoring_7);
            holder.m_8 = (TextView)convertView.findViewById(R.id.monitoring_8);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.m_1.setText(list.get(position).getId());
        holder.m_2.setText(list.get(position).getName());
        holder.m_3.setText(list.get(position).getMonitorobjectname());
        holder.m_4.setText(list.get(position).getPeak());
        holder.m_5.setText(list.get(position).getSpeed());
        holder.m_6.setText(list.get(position).getWarntime());
        holder.m_7.setText(list.get(position).getWarnlevel());
        holder.m_8.setText(list.get(position).getDealflag());

        return convertView;
    }
}
