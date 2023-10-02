package otea.connection.api;

import java.util.List;

import cli.user.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RequestsApi {


    @GET("Requests/all")
    Call<List<Request>> GetAll();

    @POST("Requests/get")
    Call<Request> Get(@Query("email") String email);

    @POST("Requests")
    Call<Request> Create(@Body Request request);

    @PUT("Requests")
    Call<Request> Update(@Query("email") String email,@Body Request request);

    @DELETE("Requests")
    Call<Request> Delete(@Query("email") String email);
}
