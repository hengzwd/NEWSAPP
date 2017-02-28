package newsemc.com.awit.news.newsemcapp.scanmodule.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;


/**
 * Created by leguang on 2016/6/1 0021.
 */
public class ItemInItemView extends LinearLayout {

    private TextView tv0_left;
    private TextView tv0_right;
    private TextView tv1_left;
    private TextView tv1_right;
    private TextView tv2_left;
    private TextView tv2_right;
    private ImageView iv_alarm;

    public ItemInItemView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.item_in_item_view, this);
        tv0_left = (TextView) findViewById(R.id.tv0_left_item_in_item);
        tv0_right = (TextView) findViewById(R.id.tv0_right_item_in_item);
        tv1_left = (TextView) findViewById(R.id.tv1_left_item_in_item);
        tv1_right = (TextView) findViewById(R.id.tv1_right_item_in_item);
        tv2_left = (TextView) findViewById(R.id.tv2_left_item_in_item);
        tv2_right = (TextView) findViewById(R.id.tv2_right_item_in_item);
        iv_alarm = (ImageView) findViewById(R.id.iv_alarm_item_in_item);

    }

    public ItemInItemView setLeft0(String s) {
        tv0_left.setText(s);
        return this;
    }

    public ItemInItemView setLeft1(String s) {
        tv1_left.setText(s);
        return this;
    }

    public ItemInItemView setLeft2(String s) {
        tv2_left.setText(s);
        return this;
    }

    public ItemInItemView setRight0(String s) {
        tv0_right.setText(s);
        return this;
    }

    public ItemInItemView setRight1(String s) {
        tv1_right.setText(s);
        return this;
    }

    public ItemInItemView setRight2(String s) {
        tv2_right.setText(s);
        return this;
    }

    public ItemInItemView setAlarm(int i) {
        iv_alarm.setImageResource(i);
        return this;
    }

}
