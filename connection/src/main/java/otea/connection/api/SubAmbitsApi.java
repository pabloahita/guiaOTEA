package otea.connection.api;

import java.util.List;

import cli.indicators.SubAmbit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for SubAmbit operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface SubAmbitsApi {

    /**Get all subAmbits*/
    @GET("SubAmbits/all")
    Call<List<SubAmbit>> GetAll();

    /**Get all subAmbits of an ambit
     *
     * @param idAmbit - Ambit identifier
     * */
    @GET("SubAmbits/allByAmbit")
    Call<List<SubAmbit>> GetAllByAmbit(@Query("idAmbit") int idAmbit);

    /**
     * Get a subAmbit
     *
     * @param id - subAmbit identifier
     * */
    @GET("SubAmbits/get")
    Call<SubAmbit> Get(@Query("id") int id);

    /**
     * Method that appends a new subAmbit to the database
     *
     * @param subAmbit - SubAmbit to append
     * */
    @POST("SubAmbits")
    Call<SubAmbit> Create(@Body SubAmbit subAmbit);

    /**
     * Update a subAmbit
     *
     * @param idSubAmbit - SubAmbit identifier
     * @param subAmbit - new subAmbit
     * */
    @PUT("SubAmbits")
    Call<SubAmbit> Update(@Query("idSubAmbit") int idSubAmbit, @Body SubAmbit subAmbit);

    /**
     * Delete a subAmbit
     *
     * @param id - subAmbit identifier
     * */
    @DELETE("SubAmbits")
    Call<SubAmbit> Delete(@Query("id") int id);
}
