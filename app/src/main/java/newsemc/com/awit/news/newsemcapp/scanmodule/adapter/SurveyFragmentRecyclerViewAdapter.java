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
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.SurveyBean;


public class SurveyFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SurveyFragmentRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<SurveyBean.InfolistBean> infolist;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_EMPTY
    }

    public SurveyFragmentRecyclerViewAdapter(Context mContext, List<SurveyBean.InfolistBean> infolist) {
        super();
        this.mContext = mContext;
        this.infolist = infolist;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (null != infolist && infolist.size() > 0) {
            return infolist.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == infolist.get(position)) {
            return ITEM_TYPE.TYPE_EMPTY.ordinal();
        } else {
            return ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            mItemViewHolder.tv_key.setText(infolist.get(position).getKey());
            mItemViewHolder.tv_value.setText(infolist.get(position).getValue());
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

        if (viewType == ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_survey_fragment, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_EMPTY.ordinal()) {
            View mView = new View(mContext);
            mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20));
            return new LineViewHolder(mView);
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_key;
        TextView tv_value;

        public ItemViewHolder(View view) {
            super(view);
            tv_key = (TextView) view.findViewById(R.id.tv0_item_rv_survey_fragment);
            tv_value = (TextView) view.findViewById(R.id.tv1_item_rv_survey_fragment);
        }
    }

    static class LineViewHolder extends RecyclerView.ViewHolder {

        public LineViewHolder(View view) {
            super(view);
        }
    }
}
