package newsemc.com.awit.news.newsemcapp.interfaces;

public abstract class MyRequestCallBack<T> {
	/**
	 * 程序执行开始
	 * @param t
	 */
	public void onStart(){}
	/**
	 * @param total 总大小
	 * @param current 当前大小
	 * @param isUploading 是否正在上传
	 */
	public void onLoading(long total, long current, boolean isUploading){}
	/**
	 * 成功调用事件
	 * @param t
	 */
	public abstract void onSuccess(T result);
	/**
	 * 失败时调用
	 * @param errCode 错误码
	 * @param msg 错误信息
	 */
	public abstract void onFailure(int errCode, String msg);
	/**
	 * 停止时调用
	 * @param t
	 */
	public void onStopped(String result){}
}
