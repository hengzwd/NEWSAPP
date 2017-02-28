package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.FirstSearchBean;


public class FirstSearchActivityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FirstSearchActivityRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private FirstSearchBean itemsData;


    public FirstSearchActivityRecyclerViewAdapter(Context mContext, FirstSearchBean itemsData) {
        super();
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        return itemsData.getBuildlist().size();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            mItemViewHolder.tv.setText(itemsData.getBuildlist().get(position).getBuildname());
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_search_activity, parent, false));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item_rv_search_activity);
        }
    }
}
