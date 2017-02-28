package newsemc.com.awit.news.newsemcapp.scanmodule.utils;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public interface ApiService {

    @POST("UploadFile")
    @FormUrlEncoded//表单请求
    Call<ResponseBody> upload(@Field("token") String token, @Field("fileName") String fileName,
                              @Field("strBytes") String strBytes, @Field("fileLength") String fileLength,
                              @Field("offset") String offset, @Field("ebsdataid") String ebsdataid,
                              @Field("filetypeid") String filetypeid, @Field("hiecoding") String hiecoding,
                              @Field("hieid") String hieid, @Field("jsonParm") String jsonParm);

}
