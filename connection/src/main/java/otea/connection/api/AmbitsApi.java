package otea.connection.api;

import java.util.List;

import cli.indicators.Ambit;
import retrofit2.Call;
import retrofit2.http.*;

public interface AmbitsApi {

    @GET("Ambits/all")
    Call<List<Ambit>> GetAll();
    // GET by ID AND ORGTYPE action
    @GET("Ambits/get")
    Call<Ambit> Get(@Query("id") int id);

    // POST action
    @POST("Ambits")
    Call<Ambit> Create(@Body Ambit ambit);

    // PUT action
    @PUT("Ambits")
    Call<Ambit> Update(@Query("idAmbit") int idAmbit, @Body Ambit ambit);

    // DELETE action
    @DELETE("Ambits")
    Call<Ambit> Delete(@Query("id") int id);

}
