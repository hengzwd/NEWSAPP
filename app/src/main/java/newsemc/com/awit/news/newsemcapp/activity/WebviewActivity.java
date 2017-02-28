package newsemc.com.awit.news.newsemcapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import newsemc.com.awit.news.newsemcapp.R;

/**
 * Created by admin on 2016/3/4.
 */
public class WebviewActivity  extends Activity{
    WebView webView;
    private String url = "http://61.237.239.144/sgtsh/excel/image!show.action";
    private String TAG="WebviewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        getWebView(url);
    }

    // 获取webView
    private void getWebView(String url) {
        webView = (WebView)findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        webView.setInitialScale(100);
        settings.setJavaScriptEnabled(true); // 设置WebView支持javascript
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        settings.setSupportZoom(true); // 设置支持缩放
        settings.setBuiltInZoomControls(true);// 显示缩放控件
        settings.setTextSize(WebSettings.TextSize.LARGER);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(url);
    }
}
