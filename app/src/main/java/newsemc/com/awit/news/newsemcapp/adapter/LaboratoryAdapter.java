package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.dao.LabInfo;


/**
 * Created by Administrator on 15-6-30.
 */
public class LaboratoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<LabInfo> list;
//    private String[] data = {"施工TJ-1标(中国路桥局)","2","2",
//            "2","1","37","0/0","0/0","0/0","0/0","0/0"};
    public LaboratoryAdapter(Context context,List<LabInfo> list){
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    class ViewHolder{
        TextView l_1;
        TextView l_2;
        TextView l_3;
        TextView l_4;
        TextView l_5;
        TextView l_6;
        TextView l_7;
        TextView l_8;
        TextView l_9;
        TextView l_10;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.laboratory_list_item,null);
            holder.l_1 = (TextView)convertView.findViewById(R.id.laboratory_1);
            holder.l_2 = (TextView)convertView.findViewById(R.id.laboratory_2);
            holder.l_3 = (TextView)convertView.findViewById(R.id.laboratory_3);
            holder.l_4 = (TextView)convertView.findViewById(R.id.laboratory_4);
            holder.l_5 = (TextView)convertView.findViewById(R.id.laboratory_5);
            holder.l_6 = (TextView)convertView.findViewById(R.id.laboratory_6);
            holder.l_7 = (TextView)convertView.findViewById(R.id.laboratory_7);
            holder.l_8 = (TextView)convertView.findViewById(R.id.laboratory_8);
            holder.l_9 = (TextView)convertView.findViewById(R.id.laboratory_9);
            holder.l_10 = (TextView)convertView.findViewById(R.id.laboratory_10);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.l_1.setText(list.get(position).getSectionname());
        holder.l_2.setText(list.get(position).getYljtotal());
        holder.l_3.setText(list.get(position).getYljusenum());
        holder.l_4.setText(list.get(position).getWnjtotal());
        holder.l_5.setText(list.get(position).getYljusenum());
        holder.l_6.setText(list.get(position).getReporttotal());
        holder.l_7.setText(list.get(position).getHntinfo());
        holder.l_8.setText(list.get(position).getGjinfo());
        holder.l_9.setText(list.get(position).getGjhjinfo());
        holder.l_10.setText(list.get(position).getGjjxinfo());

        return convertView;
    }
}
