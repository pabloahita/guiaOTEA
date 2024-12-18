package otea.connection.api;

import java.util.List;

import cli.indicators.Ambit;
import cli.indicators.Ambit;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * API for Ambit operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface AmbitsApi {

    /**Get all Ambits*/
    @GET("Ambits/all")
    Call<List<Ambit>> GetAll(@Header("Authorization") String Authorization);

    /**
     * Get an Ambit
     *
     * @param id - Ambit identifier
     * */
    @GET("Ambits/get")
    Call<Ambit> Get(@Query("id") int id,@Header("Authorization") String Authorization);

    /**
     * Create new Ambit
     *
     * @param ambit - Ambit
     * */
    @POST("Ambits")
    Call<Ambit> Create(@Body Ambit ambit,@Header("Authorization") String Authorization);

    /**
     * Update an Ambit
     *
     * @param idAmbit - Ambit identifier
     * @param ambit - Ambit
     * */
    @PUT("Ambits")
    Call<Ambit> Update(@Query("idAmbit") int idAmbit, @Body Ambit ambit,@Header("Authorization") String Authorization);

    /**
     * Delete an Ambit
     *
     * @param id - Ambit identifier
     * */
    @DELETE("Ambits")
    Call<Ambit> Delete(@Query("id") int id,@Header("Authorization") String Authorization);
}