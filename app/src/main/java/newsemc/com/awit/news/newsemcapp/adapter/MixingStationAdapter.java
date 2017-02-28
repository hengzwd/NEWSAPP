package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.dao.BhzInfo;


/**
 * Created by Administrator on 15-6-30.
 */
public class MixingStationAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BhzInfo> list;
//    private String[] data = {"1","SG-1标(中铁一局)","8",
//            "5","237.6","144","0","0%","0%","0%","0%"};
    public MixingStationAdapter(Context context,List<BhzInfo> list){
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
        TextView m_9;
        TextView m_10;
        TextView m_11;
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
            convertView = mInflater.inflate(R.layout.mixing_list_item,null);
//            holder.m_1 = (TextView)convertView.findViewById(R.id.mixing_0);
            holder.m_2 = (TextView)convertView.findViewById(R.id.mixing_1);
            holder.m_3 = (TextView)convertView.findViewById(R.id.mixing_2);
            holder.m_4 = (TextView)convertView.findViewById(R.id.mixing_3);
            holder.m_5 = (TextView)convertView.findViewById(R.id.mixing_4);
            holder.m_6 = (TextView)convertView.findViewById(R.id.mixing_5);
            holder.m_7 = (TextView)convertView.findViewById(R.id.mixing_6);
            holder.m_8 = (TextView)convertView.findViewById(R.id.mixing_7);
            holder.m_9 = (TextView)convertView.findViewById(R.id.mixing_8);
            holder.m_10 = (TextView)convertView.findViewById(R.id.mixing_9);
            holder.m_11 = (TextView)convertView.findViewById(R.id.mixing_10);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

//        holder.m_1.setText("");
        holder.m_2.setText(list.get(position).getSectionname());
        holder.m_3.setText(list.get(position).getBhjtotal());
        holder.m_4.setText(list.get(position).getBhjusenum());
        holder.m_5.setText(list.get(position).getVolume());
        holder.m_6.setText(list.get(position).getPannum());
        holder.m_7.setText(list.get(position).getMixwarnnum());
        holder.m_8.setText(list.get(position).getMixratio());
        holder.m_9.setText(list.get(position).getMatlwarnnum());
        holder.m_10.setText(list.get(position).getMatlratio());
        holder.m_11.setText(list.get(position).getMaltdisratio());

        return convertView;
    }
}
