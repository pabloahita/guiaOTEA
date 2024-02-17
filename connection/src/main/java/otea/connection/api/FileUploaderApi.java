package otea.connection.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FileUploaderApi {
    @Multipart
    @POST("Uploader/upload")
    Call<ResponseBody> uploadFile(
            @Part MultipartBody.Part file,
            @Part("containerName") RequestBody containerName
    );
}
