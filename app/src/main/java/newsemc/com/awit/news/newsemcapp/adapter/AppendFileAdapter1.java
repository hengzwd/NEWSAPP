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

/**
 * Created by admin on 2016/2/26.
 */
public class AppendFileAdapter1 extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> data;

    class ViewHolder{
        TextView file;
    }

    public AppendFileAdapter1(Context context,List<String> data){
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
        Log.d("this.data.size()=", this.data.size() + " " + position);
        String m = this.data.get(position);
        Log.d("File Name=",this.data.get(position) == null ? "null" : "not null");
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.append_file1,null);
            holder.file = (TextView)convertView.findViewById(R.id.file);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.file.setText(m);
        return convertView;
    }
}

