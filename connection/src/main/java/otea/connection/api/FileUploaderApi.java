package otea.connection.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FileUploaderApi {
    @POST("Uploader/upload")
    Call<ResponseBody> uploadFile(
            @Body byte[] data,
            @Query("containerName") String containerName,
            @Query("fileName") String fileName
    );
}
