package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.activity.ArchiveInfoListActivity;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.SpecialsTructureRecordsListBean;


public class SpecialsTructureRecordsFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SpecialsTructureRecordsFragmentRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<SpecialsTructureRecordsListBean> itemsData;

    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_EMPTY
    }

    public SpecialsTructureRecordsFragmentRecyclerViewAdapter(Context mContext, List<SpecialsTructureRecordsListBean> itemsData) {
        super();
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    @Override
    public int getItemCount() {
        if (itemsData.size() > 0) {
            return itemsData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == itemsData.get(position).getViewtype()) {
            return ITEM_TYPE.TYPE_EMPTY.ordinal();
        } else {
            return ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final SpecialsTructureRecordsListBean item = itemsData.get(position);
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

            if (TextUtils.isEmpty(item.getFinishDate())) {
                mItemViewHolder.tv_date.setText("");

            } else {
                mItemViewHolder.tv_date.setText("浇筑日期：" + item.getFinishDate());
            }

            mItemViewHolder.tv_title.setText(item.getName());
            mItemViewHolder.tv_jianyanpi.setText(item.getArchiveInfoList().get(0).getName() + "(" + item.getArchiveInfoList().get(0).getTotal() + ")");
            mItemViewHolder.tv_shiyanbaogao.setText(item.getArchiveInfoList().get(1).getName() + "(" + item.getArchiveInfoList().get(1).getTotal() + ")");
            mItemViewHolder.tv_yingxiang.setText(item.getArchiveInfoList().get(2).getName() + "(" + item.getArchiveInfoList().get(2).getTotal() + ")");


            KLog.e(item.getArchiveInfoList().get(0).getId());
            KLog.e(item.getArchiveInfoList().get(0).getName());
            KLog.e(item.getArchiveInfoList().get(0).getType());

            final Intent mIntent = new Intent(mContext, ArchiveInfoListActivity.class);
            mIntent.putExtra("archive_id", item.getId());

            mItemViewHolder.tv_jianyanpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIntent.putExtra("archive_type", item.getArchiveInfoList().get(0).getId());
                    mIntent.putExtra("archive_name", item.getArchiveInfoList().get(0).getName());
                    mIntent.putExtra("archive_hiecoding", item.getArchiveInfoList().get(0).getType());

                    mContext.startActivity(mIntent);
                }
            });

            mItemViewHolder.tv_shiyanbaogao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIntent.putExtra("archive_type", item.getArchiveInfoList().get(1).getId());
                    mIntent.putExtra("archive_name", item.getArchiveInfoList().get(1).getName());
                    mIntent.putExtra("archive_hiecoding", item.getArchiveInfoList().get(1).getType());

                    mContext.startActivity(mIntent);
                }
            });

            mItemViewHolder.tv_yingxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIntent.putExtra("archive_type", item.getArchiveInfoList().get(2).getId());
                    mIntent.putExtra("archive_name", item.getArchiveInfoList().get(2).getName());
                    mIntent.putExtra("archive_hiecoding", item.getArchiveInfoList().get(2).getType());

                    mContext.startActivity(mIntent);
                }
            });

        } else {
            LineViewHolder mLineViewHolder = (LineViewHolder) holder;
            mLineViewHolder.tv_title.setText(item.getPartname());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_ITEM.ordinal()) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_records_fragment, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_EMPTY.ordinal()) {
            return new LineViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_part_recyclerview, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_date;
        TextView tv_jianyanpi;
        TextView tv_shiyanbaogao;
        TextView tv_yingxiang;

        public ItemViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_rv_records_fragment);
            tv_date = (TextView) view.findViewById(R.id.tv_date_item_rv_survey_fragment);
            tv_jianyanpi = (TextView) view.findViewById(R.id.tv_jianyanpi_item_rv_survey_fragment);
            tv_shiyanbaogao = (TextView) view.findViewById(R.id.tv_shiyanbaogao_item_rv_survey_fragment);
            tv_yingxiang = (TextView) view.findViewById(R.id.tv_yingxiang_item_rv_survey_fragment);
        }
    }

    static class LineViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        public LineViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_item_part_recyclerview);
        }
    }
}
