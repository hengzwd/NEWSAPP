package newsemc.com.awit.news.newsemcapp.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import newsemc.com.awit.news.newsemcapp.bean.FailRequest;
import newsemc.com.awit.news.newsemcapp.bean.StatusSucc;
import newsemc.com.awit.news.newsemcapp.interfaces.HttpResultListener;


/**
 * Created by sb on 2015/8/13.
 */
public class GetApk {
    private static int count;
    private final static String TAG = "GetApk";

    public static void downloadFile(String url, final String path,final String apkSize,
                                    final Handler handler, final HttpResultListener httpResultListener) {
        // TODO Auto-generated method stub
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                if (arg2 != null && arg2.length > 0) {
                    Log.d(TAG, "onFailure:" + new String(arg2));
                }
                FailRequest failRequest = new FailRequest();
                failRequest.setStatus("-255");
                failRequest.setMsg("接口无法连接，下载应用失败");
                httpResultListener.onResult(failRequest);
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                try {
                    Log.i("file size::::", String.valueOf(arg2.length));
                    File file = new File(ValueConfig.DOWNLOAD, path);
                    File parent = file.getParentFile();
                    if (parent != null && !parent.exists())
                        parent.mkdirs();
                    FileOutputStream fos = new FileOutputStream(file);
                    OutputStream output = new DataOutputStream(fos);
                    output.write(arg2);
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                StatusSucc succ = new StatusSucc();
                succ.setStatus("1");
                httpResultListener.onResult(succ);
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                super.onProgress(bytesWritten, totalSize);
                Log.i("totalSize", totalSize + "");
//                count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                //apk文件大小
                count = (int) ((bytesWritten * 1.0 / Double.parseDouble(apkSize)) * 100);
                Log.i("filesize：：：：",count+"");
                Log.i("---->>", String.valueOf(bytesWritten).length()+"");

                Bundle bundle = new Bundle();
                bundle.putInt("count", count);
                Message msg = new Message();
                msg.what = 0;
                msg.setData(bundle);
                handler.sendMessage(msg);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                httpResultListener.onFinish();
            }

            @Override
            public void onStart() {
                super.onStart();
                httpResultListener.onStartRequest();
            }

        });
    }
}
