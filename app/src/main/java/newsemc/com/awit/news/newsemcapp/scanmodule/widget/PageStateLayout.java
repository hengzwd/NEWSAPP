package newsemc.com.awit.news.newsemcapp.scanmodule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import newsemc.com.awit.news.newsemcapp.R;
import newsemc.com.awit.news.newsemcapp.scanmodule.NoMultiClickListener;


/**
 * Created by leguang on 2016/9/16 0016.
 * 联系邮箱:langmanleguang@qq.com
 * <p/>
 * 设计资料Fragment
 */
public class PageStateLayout extends FrameLayout {

    private int errorView, loadingView;
    private OnClickListener onRetryClickListener;
    public boolean isShowError = false, isShowLoading = false, isShowContent = true;

    public PageStateLayout(Context context) {
        this(context, null);
    }

    public PageStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PageStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PageStateLayout, 0, 0);
        try {
            errorView = a.getResourceId(R.styleable.PageStateLayout_errorView, R.layout.page_state_error_view);
            loadingView = a.getResourceId(R.styleable.PageStateLayout_loadingView, R.layout.page_state_loading_view);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(errorView, this, true);
            inflater.inflate(loadingView, this, true);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }

        findViewById(R.id.btn_error_retry).setOnClickListener(new NoMultiClickListener() {
            @Override
            public void onNoMultiClick(View view) {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onClick(view);
                }
            }
        });
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
    }


    public void showError() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowError = true;
        isShowLoading = false;
        isShowContent = false;
    }


    public void showLoading() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowError = false;
        isShowLoading = true;
        isShowContent = false;
    }

    public void showContent() {
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
        isShowError = false;
        isShowLoading = false;
        isShowContent = true;
    }
}
