package newsemc.com.awit.news.newsemcapp.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;


/**
 * Created by Administrator on 2015/6/26.
 */
public class HTTPRequest {
    private HttpResultListener hrl;
    public HTTPRequest(HttpResultListener hrl){
        this.hrl=hrl;
    }
    public void getData(String url,RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler(){

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {

            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

                hrl.onResult(new String(arg2));
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
            }

            @Override
            public void onStart() {
                super.onStart();
                hrl.onStartRequest();
            }

        });
    }
}
