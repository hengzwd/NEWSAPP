package newsemc.com.awit.news.newsemcapp.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

import newsemc.com.awit.news.newsemcapp.R;

/**
 * Created by gesangdianzi on 2017/1/17.
 */
public class RelateaAccountAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private OnItemChildClickListener onItemChildClickListener;

    public RelateaAccountAdapter() {
        super(R.layout.item_recycleview_relateaaccout, null);
    }


    @Override
    public void setNewData(List<String> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(final BaseViewHolder BaseViewHolder, final String s) {
        BaseViewHolder.setText(R.id.tv_relateaaccount, s)
                .setOnClickListener(R.id.tv_relateaaccount, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemChildClickListener.onclick(BaseViewHolder.getLayoutPosition(), s);
                    }
                });
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public interface OnItemChildClickListener {
        void onclick(int position, String s);
    }

//    public void checkItem(CheckBox checkBox, int position) {
//        for (int i = 0; i < getItemCount(); i++) {
//
//
//        }
//        if (checkBox.isChecked()) {
//            checkBox.setChecked(false);
//        } else {
//            checkBox.setChecked(true);
//        }
//
//    }

//    protected class myViewHolder extends BaseViewHolder {
//
//        public CheckBox checkBox;
//        public TextView textView;
//
//        public myViewHolder(View view) {
//            super(view);
//            checkBox = (CheckBox) view.findViewById(R.id.check_box);
//            textView = (TextView) view.findViewById(R.id.tv_relateaaccount);
//        }
//    }

}
