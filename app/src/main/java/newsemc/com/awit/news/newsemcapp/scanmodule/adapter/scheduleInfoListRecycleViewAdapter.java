package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socks.library.KLog;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.OnItemClickListener;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.scheduleBean;
import newsemc.com.awit.news.newsemcapp.util.DensityUtils;

/**
 * Created by gesangdianzi on 2016/10/19.
 */
public class scheduleInfoListRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = scheduleInfoListRecycleViewAdapter.class.getSimpleName();
    private Context mContext;
    private List<scheduleBean.DailyListEntity> itemsData;
    private Resources resources;
    private static final int view_type_item = 0;
    private static final int view_type_footer = 1;

    public scheduleInfoListRecycleViewAdapter(Context mContext, List<scheduleBean.DailyListEntity> itemsData) {
        super();
        this.mContext = mContext;
        this.itemsData = itemsData;
        resources = mContext.getResources();
    }

    @Override
    public int getItemCount() {
        if (itemsData != null && itemsData.size() > 0) {
            return itemsData.size() + 1;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;

            if (position == 0) {
                mItemViewHolder.tv_footage.setTextColor(resources.getColor(R.color.black));
                mItemViewHolder.tv_footage.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                mItemViewHolder.tv_design.setTextColor(resources.getColor(R.color.black));
                mItemViewHolder.tv_design.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                mItemViewHolder.tv_finishDate.setTextColor(resources.getColor(R.color.black));
                mItemViewHolder.tv_finishDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                mItemViewHolder.tv_mileStone.setTextColor(resources.getColor(R.color.black));
                mItemViewHolder.tv_mileStone.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
                mItemViewHolder.tv_footage.setText("进尺(m)");
                mItemViewHolder.tv_design.setText("围岩级别");
                mItemViewHolder.tv_finishDate.setText("完成日期");
                mItemViewHolder.tv_mileStone.setText("前端里程");
            } else {
                mItemViewHolder.tv_footage.setTextColor(resources.getColor(R.color.darkgray2));
                mItemViewHolder.tv_footage.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                mItemViewHolder.tv_design.setTextColor(resources.getColor(R.color.darkgray2));
                mItemViewHolder.tv_design.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                mItemViewHolder.tv_finishDate.setTextColor(resources.getColor(R.color.darkgray2));
                mItemViewHolder.tv_finishDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                mItemViewHolder.tv_mileStone.setTextColor(resources.getColor(R.color.darkgray2));
                mItemViewHolder.tv_mileStone.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
                KLog.e("Adapter:" + itemsData.get(position - 1));
                mItemViewHolder.tv_footage.setText(itemsData.get(position - 1).getFootage());
                mItemViewHolder.tv_design.setText(itemsData.get(position - 1).getDesign());
                mItemViewHolder.tv_finishDate.setText(itemsData.get(position - 1).getFinishDate());
                mItemViewHolder.tv_mileStone.setText(itemsData.get(position - 1).getMileStone());
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 20 && position + 1 == getItemCount()) {
            return view_type_footer;
        } else {
            return view_type_item;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == view_type_item) {
            return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.scheduleactivity_recycleview_item, parent, false));
        } else if (viewType == view_type_footer) {
            return new footViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_foot, parent, false));
        }
        return null;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mileStone;
        TextView tv_footage;
        TextView tv_design;
        TextView tv_finishDate;

        public ItemViewHolder(View view) {
            super(view);
            tv_mileStone = (TextView) view.findViewById(R.id.tv_mileStone);
            tv_footage = (TextView) view.findViewById(R.id.tv_footage);
            tv_design = (TextView) view.findViewById(R.id.tv_design);
            tv_finishDate = (TextView) view.findViewById(R.id.tv_finishDate);
        }


    }
    private class footViewHolder extends RecyclerView.ViewHolder {

        public footViewHolder(View itemView) {
            super(itemView);
        }
    }
}
