package otea.connection.api;

import java.util.List;

import cli.user.Request;
import cli.user.Session;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SessionsApi {

    @GET("Sessions/all")
    Call<List<Session>> GetAll();

    @POST("Sessions/get")
    Call<Session> Get(@Query("sessionToken") String sessionToken);

    @POST("Sessions")
    Call<Session> Create(@Body Session session);

    @PUT("Sessions")
    Call<Session> Update(@Query("sessionToken") String sessionToken, @Body Session session);

    @DELETE("Sessions")
    Call<Session> Delete(@Query("sessionToken") String sessionToken);
}
