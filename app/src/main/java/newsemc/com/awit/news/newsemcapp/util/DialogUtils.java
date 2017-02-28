package newsemc.com.awit.news.newsemcapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import newsemc.com.awit.news.newsemcapp.R;


/**
 * Created by sb on 2015/7/31.
 */
public class DialogUtils {
    /**
     * 带输入框的弹出框
     * @param context 上下文环境，不能是getApplicationContext()获取的结果
     * @param title 标题
     * @param msgHint 输入框内提示信息
     * @param callBack 回调函数
     */
    public static void alertEditDialog(Context context, String title, String msgHint, final DialogEditCallBack callBack){
        View view = View.inflate(context, R.layout.alterpwd, null);
        TextView oldpwd = (TextView) view.findViewById(R.id.oldpwd_edit);
        final EditText oldtext = (EditText) view.findViewById(R.id.oldpwd_edit);
        TextView newpwd = (TextView) view.findViewById(R.id.newpwd_text);
        final EditText newtext = (EditText) view.findViewById(R.id.newpwd_text);
        TextView surepwd = (TextView) view.findViewById(R.id.surepwd_text);
        final EditText suretext = (EditText) view.findViewById(R.id.surepwd_text);
        LinearLayout ll_btn = (LinearLayout) view.findViewById(R.id.ll_btn);
        Button btn_sure = (Button) ll_btn.findViewById(R.id.btn_sure);
        Button btn_cancel = (Button) ll_btn.findViewById(R.id.btn_cancel);
        final MyDialog dialog = new MyDialog(context,view,R.style.dialog);
        oldtext.setHint(msgHint);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.positive(dialog, oldtext.getText().toString().trim(),
                        newtext.getText().toString().trim(),
                        suretext.getText().toString().trim());
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.negative(dialog, v);
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * @param context 上下文环境，不能是getApplicationContext()获取的结果
     * @param title 标题
     * @param message 消息内容
     * @param callBack 回调函数
     */
    public static void alertDialog(Context context, String title, String message, final DialogCallBack callBack){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBack.positive();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBack.negative();
            }
        });
        builder.show();
    }


    public interface DialogEditCallBack {
        /**
         * 点击确定按钮的处理事件（需要自己控制dialog.dismiss()）
         * @param dialog
         */
        public void positive(MyDialog dialog, String oldpwd, String newpwd,String surepwd);
        /**
         * 点击取消按钮的处理事件
         * @param v
         * @param dialog
         */
        public void negative(MyDialog dialog, View v);
    }

    public interface DialogCallBack {
        /**
         * 点击确定按钮的处理事件（需要自己控制dialog.dismiss()）
         */
        public void positive();
        /**
         * 点击取消按钮的处理事件
         */
        public void negative();
    }
}
