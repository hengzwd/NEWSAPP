package newsemc.com.awit.news.newsemcapp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;

import newsemc.com.awit.news.newsemcapp.interfaces.MyRequestCallBack;


public class DownloadUtil {
	public static void main(String[] args) {
		String url = "";
		String savePath = "";
		Context context = null;
		//单线程下载
		downloadFile(context, url, savePath, "test.doc", new MyRequestCallBack<File>() {

			@Override
			public void onSuccess(File result) {//下载完成  result是下载完成并且下载成功后的文件
				//下载完之后可以在此处操作文件，例如打开文件、删除文件等
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {//下载中，totoal不一定准确，如果为0，需要手动给定
				super.onLoading(total, current, isUploading);
				//操作下载进度
			}

			@Override
			public void onFailure(int errCode, String msg) {//各种下载失败，由errorcode和msg决定
				//下载失败，关闭Dialog等
			}
		});
	}

	/**
	 * 单线程下载，已经将下载放到子线程中，下载过程中的操作放到了主线程中
	 * @param context 当前Activity的上下文，不可以是全局上下文（为了保证使下载状态改变后代码能在主线程中运行）
	 * @param url 下载地址
	 * @param savePath 保存路径
	 * @param callBack 回调
	 */
	public static void downloadFile(final Context context, final String url,final String savePath, final String fileName, final MyRequestCallBack<File> callBack) {
		final Activity activity = ((Activity)context);
		new Thread(){
			public void run() {
				final File file = new File(savePath);
				try {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							callBack.onStart();
						}
					});
					URL uri = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					// 设置 HttpURLConnection的接收的文件类型
					conn.setRequestProperty(
							"Accept",
							"image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
									+ "application/x-shockwave-flash, application/xaml+xml, "
									+ "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
									+ "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
					conn.setRequestProperty("Accept-Charset", "UTF-8");

//					conn.connect();
					if (conn.getResponseCode() == 200) {
						InputStream is = conn.getInputStream();
						BufferedInputStream bis;
						BufferedOutputStream bos;
						if (is != null) {
							bis = new BufferedInputStream(is);
							bos = new BufferedOutputStream(new FileOutputStream(file + File.separator + fileName));
							int len = 0;
							byte[] bys = new byte[1024];
							int count = 0;
							while ((len = bis.read(bys)) != -1) {
								count += len;
								callBack.onLoading(conn.getContentLength(), count, false);
								bos.write(bys, 0, len);
								bos.flush();
							}
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									if (file != null && file.exists()) {
										callBack.onSuccess(file);
									}else if(file.length() == 0){
										callBack.onFailure(104, "datazero");//下载的文件大小为0
									}else {
										callBack.onFailure(103, "downfail");//下载失败
									}
								}
							});
						}else {
							activity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									callBack.onFailure(102, "dataerror");//数据错误
								}
							});
						}
					}else {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								callBack.onFailure(103, "downfail");//下载失败
							}
						});
					}
				} catch (Exception e) {
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							callBack.onFailure(101, "exception");//发生异常
						}
					});
				}finally {
					activity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (file != null && file.exists()) {
								callBack.onStopped("success");
							}else {
								callBack.onStopped("fail");
							}
						}
					});
				}
			};
		}.start();

	}
}
