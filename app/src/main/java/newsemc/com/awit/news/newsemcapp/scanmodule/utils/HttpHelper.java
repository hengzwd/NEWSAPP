package newsemc.com.awit.news.newsemcapp.scanmodule.utils;

import java.util.concurrent.TimeUnit;

import newsemc.com.awit.news.newsemcapp.BuildConfig;
import newsemc.com.awit.news.newsemcapp.scanmodule.common.Configuration;
import newsemc.com.awit.news.newsemcapp.scanmodule.common.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class HttpHelper {

    private static final String TAG = HttpHelper.class.getSimpleName();
    public static final String BASE_URL = ConstantsUtils.BaseURL_3;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit mRetrofit;
    private volatile static HttpHelper INSTANCE;

    //构造方法私有
    private HttpHelper() {
        initRetrofit();
    }

    //获取单例
    public static HttpHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpHelper();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化OkHttp
     */
    private void initOkHttpClient() {
        if (null == mOkHttpClient) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (builder.interceptors() != null) {
                builder.interceptors().clear();
            }


            //设置缓存
//            builder.cache(cache);
            //设置超时
            builder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            //DEBUG模式下配Log拦截器
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

//            builder.addNetworkInterceptor();

            if (Configuration.isShowNetworkParams()) {
                builder.addInterceptor(new LoggingInterceptor());
            }
            mOkHttpClient = builder.build();
        }
    }

    private void initRetrofit() {
        if (null == mRetrofit) {
            initOkHttpClient();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
    }

    public ApiService initService() {
        return mRetrofit.create(ApiService.class);
    }
}
