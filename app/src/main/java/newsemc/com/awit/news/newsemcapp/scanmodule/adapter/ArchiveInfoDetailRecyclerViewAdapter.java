package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ShowPhotoActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.ArchiveInfoBean;
import newsemc.com.awit.news.newsemcapp.util.IntentUtils;


public class ArchiveInfoDetailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ArchiveInfoDetailRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<ArchiveInfoBean.InfolistBean.BrolistBean> itemsData;

    public ArchiveInfoDetailRecyclerViewAdapter(Context mContext, List<ArchiveInfoBean.InfolistBean.BrolistBean> itemsData) {
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
            return itemsData.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

            KLog.e("Adapter:" + itemsData.get(position).getUrl());
           //Picasso.with(mContext).load(itemsData.get(position).getUrl()).into(mItemViewHolder.iv);

            Glide.with(mContext).load(itemsData.get(position).getUrl()).crossFade().error(R.drawable.downloadfailure).
                    //placeholder(R.drawable.downloadfailure).
                    into(mItemViewHolder.iv);


            mItemViewHolder.tv.setText(itemsData.get(position).getName());
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    Intent mIntent = new Intent(mContext, ShowPhotoActivity.class);
                    mIntent.putExtra("mainurl", itemsData.get(position).getUrl());
                    mContext.startActivity(mIntent);
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_archive_info_detail_activity, parent, false));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv_item_rv_archive_info_detail_activity);
            tv = (TextView) view.findViewById(R.id.tv_item_rv_archive_info_detail_activity);
        }
    }
}
