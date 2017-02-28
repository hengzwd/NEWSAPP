package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;


public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = HistoryRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<String> itemsData;


    public HistoryRecyclerViewAdapter(Context mContext, List<String> itemsData) {
        super();
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (itemsData.size() > 0) {
            return itemsData.size() + 1;
        } else {
            return 0;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            if (position == 0) {
                mItemViewHolder.tv.setText("历史搜索");

            } else {
                mItemViewHolder.tv.setText(itemsData.get(--position));
            }
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
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_history_rv_search_activity, parent, false));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item_rv_search_activity);
        }
    }
}
