package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.ArchiveInfoBean;


public class ArchiveInfoListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ArchiveInfoListRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<ArchiveInfoBean.InfolistBean> itemsData;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }

    public ArchiveInfoListRecyclerViewAdapter(Context mContext, List<ArchiveInfoBean.InfolistBean> itemsData) {
        super();
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.size() > 0) {
            //这里的10是根据分页查询，一页该显示的条数
            if (itemsData.size() >= 20) {
                return itemsData.size() + 1;
            } else {
                return itemsData.size();
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 20 && position + 1 == getItemCount()) {
            return ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

//            Picasso.with(mContext).load(itemsData.get(position).getThumurl())
//                    .into(mItemViewHolder.iv_left);

            Glide.with(mContext).load(itemsData.get(position).getThumurl()).crossFade().error(R.drawable.downloadfailure).
                   // placeholder(R.drawable.downloadfailure).
                    into(mItemViewHolder.iv_left);


            mItemViewHolder.tv_title.setText(itemsData.get(position).getName());
            mItemViewHolder.tv_description.setText(itemsData.get(position).getNo());
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
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_design_info_fragment, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_foot_recyclerview, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_left;
        TextView tv_title;
        TextView tv_description;

        public ItemViewHolder(View view) {
            super(view);
            iv_left = (ImageView) view.findViewById(R.id.iv_left_item_rv_design_info_fragment);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_rv_design_info_fragment);
            tv_description = (TextView) view.findViewById(R.id.tv_date_item_rv_design_info_fragment);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}
