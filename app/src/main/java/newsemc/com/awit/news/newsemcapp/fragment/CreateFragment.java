package newsemc.com.awit.news.newsemcapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import newsemc.com.awit.news.newsemcapp.R;


/**
 * Created by Administrator on 15-6-16.
 */
public class CreateFragment extends Fragment {
    public static CreateFragment newInstance(){
        CreateFragment f = new CreateFragment();
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.webviewfragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getWebView();
    }

    // 获取webView
    private WebView getWebView() {
        WebView wv = (WebView) getActivity().findViewById(R.id.webview_id);
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        int scalePercent = 100;

        switch (screenDensity) {
            case DisplayMetrics.DENSITY_HIGH:// 800*480
                scalePercent = 75;
                break;
            case DisplayMetrics.DENSITY_XHIGH:// 1280*720
                scalePercent = 100;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:// 1920*1080
                scalePercent = 150;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:// 2560*1440
                scalePercent = 200;
                break;
        }
        wv.setInitialScale(scalePercent);

        WebSettings settings = wv.getSettings();

        settings.setJavaScriptEnabled(true); // 设置WebView支持javascript
        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        settings.setSupportZoom(false); // 设置支持缩放
        settings.setBuiltInZoomControls(false);// 显示缩放控件
        settings.setLoadWithOverviewMode(true);
        wv.requestFocus();
        wv.loadUrl("file:///android_asset/webview.html");
        return wv;
    }
}
