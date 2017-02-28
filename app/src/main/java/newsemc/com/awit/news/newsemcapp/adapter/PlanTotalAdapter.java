package newsemc.com.awit.news.newsemcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;


/**
 * Created by Administrator on 15-7-1.
 */
public class PlanTotalAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public PlanTotalAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder{
        TextView name;
        TextView type_1,type_1_num_1,type_1_num_2,type_1_num_3,type_1_num_4,type_1_num_5;
        TextView type_2,type_2_num_1,type_2_num_2,type_2_num_3,type_2_num_4,type_2_num_5;
        TextView type_3,type_3_num_1,type_3_num_2,type_3_num_3,type_3_num_4,type_3_num_5;
        TextView type_4,type_4_num_1,type_4_num_2,type_4_num_3,type_4_num_4,type_4_num_5;
        TextView type_5,type_5_num_1,type_5_num_2,type_5_num_3,type_5_num_4,type_5_num_5;
        TextView type_6,type_6_num_1,type_6_num_2,type_6_num_3,type_6_num_4,type_6_num_5;
        TextView type_7,type_7_num_1,type_7_num_2,type_7_num_3,type_7_num_4,type_7_num_5;
        TextView type_8,type_8_num_1,type_8_num_2,type_8_num_3,type_8_num_4,type_8_num_5;
        TextView type_9,type_9_num_1,type_9_num_2,type_9_num_3,type_9_num_4,type_9_num_5;
        TextView type_10,type_10_num_1,type_10_num_2,type_10_num_3,type_10_num_4,type_10_num_5;
    }

    @Override
    public int getCount() {
        return 1;
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
            convertView = mInflater.inflate(R.layout.plantotal_list_item,null);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }
}
