package newsemc.com.awit.news.newsemcapp.scanmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.bean.TunnelRecordsFragmentBean;
import newsemc.com.awit.news.newsemcapp.scanmodule.widget.ItemInItemView;


public class TunnelRecordsFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = TunnelRecordsFragmentRecyclerViewAdapter.class.getSimpleName();
    private Context mContext;
    private TunnelRecordsFragmentBean itemsData;


    public TunnelRecordsFragmentRecyclerViewAdapter(Context mContext, TunnelRecordsFragmentBean itemsData) {
        super();
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    @Override
    public int getItemCount() {
        if (itemsData.getSecurity().size() > 0) {
            return itemsData.getSecurity().size();
        }
        return 0;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder mItemViewHolder = (ItemViewHolder) holder;
            TunnelRecordsFragmentBean.SecurityBean.ProfileInfoListBean temp0 = itemsData.getSecurity().get(position).get(0).getProfileInfoList().get(0);
            mItemViewHolder.tv_title.setText(temp0.getKey() + ":" + temp0.getValue());
            TunnelRecordsFragmentBean.SecurityBean.ProfileInfoListBean temp1 = itemsData.getSecurity().get(position).get(0).getProfileInfoList().get(1);

            mItemViewHolder.tv_date.setText(temp1.getKey() + ":" + temp1.getValue());//

            for (int i = 0; i < itemsData.getSecurity().get(position).size(); i++) {

                ItemInItemView itemInItem = new ItemInItemView(mContext);

                List<TunnelRecordsFragmentBean.SecurityBean.ProfileInfoListBean> tempintemp = itemsData.getSecurity().get(position).get(i).getProfileInfoList();
                itemInItem.setLeft0(tempintemp.get(2).getKey() + ":" + tempintemp.get(2).getValue());
                itemInItem.setRight0(tempintemp.get(3).getKey() + ":");
                itemInItem.setAlarm(0);

                switch (tempintemp.get(3).getValue()) {
                    case "0":
                        itemInItem.setAlarm(R.drawable.ic_alarm_green);

                        break;
                    case "1":
                        itemInItem.setAlarm(R.drawable.ic_alarm_yellow);

                        break;
                    case "2":
                        itemInItem.setAlarm(R.drawable.ic_alarm_red);

                        break;
                }

                itemInItem.setLeft1(tempintemp.get(4).getKey() + ":" + tempintemp.get(4).getValue());
                itemInItem.setRight1(tempintemp.get(5).getKey() + ":" + tempintemp.get(5).getValue());

                itemInItem.setLeft2(tempintemp.get(6).getKey() + ":" + tempintemp.get(6).getValue());
                itemInItem.setRight2(tempintemp.get(7).getKey() + ":" + tempintemp.get(7).getValue());

                mItemViewHolder.ll.addView(itemInItem);
            }


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rv_tunnel_records_fragment, parent, false));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_date;
        LinearLayout ll;

        public ItemViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title_item_rv_tunnel_records_fragment);
            tv_date = (TextView) view.findViewById(R.id.tv_date_item_rv_tunnel_records_fragment);
            ll = (LinearLayout) view.findViewById(R.id.ll_item_rv_tunnel_records_fragment);
        }
    }

}
