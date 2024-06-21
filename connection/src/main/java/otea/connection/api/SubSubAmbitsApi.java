package otea.connection.api;


import java.util.List;

import cli.indicators.SubSubAmbit;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for SubSubAmbit operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface SubSubAmbitsApi {

    /**Get all subSubAmbits*/
    @GET("SubSubAmbits/all")
    Call<List<SubSubAmbit>> GetAll(@Header("Authorization") String Authorization);

    /**
     * Get all subSubAmbits of an subAmbit
     * @param idSubAmbit - subAmbit identifier
     * @param idAmbit - Ambit identifier
     */
    @GET("SubSubAmbits/allBySubAmbit")
    Call<List<SubSubAmbit>> GetAllBySubAmbit(@Query("idSubAmbit") int idSubAmbit,@Query("idAmbit") int idAmbit,@Header("Authorization") String Authorization);

    /**
     * Get a subSubAmbit
     *
     * @param id - subSubAmbit identifier
     * */
    @GET("SubSubAmbits/get")
    Call<SubSubAmbit> Get(@Query("id") int id,@Header("Authorization") String Authorization);

    /**
     * Create new subSubAmbit
     *
     * @param subSubAmbit - SubSubAmbit to append
     * */
    @POST("SubAmbits")
    Call<SubSubAmbit> Create(@Body SubSubAmbit subSubAmbit,@Header("Authorization") String Authorization);

    /**
     * Update a subSubAmbit
     *
     * @param idSubSubAmbit - SubSubAmbit identifier
     * @param subSubAmbit - new subSubAmbit
     * */
    @PUT("SubSubAmbits")
    Call<SubSubAmbit> Update(@Query("idSubSubAmbit") int idSubSubAmbit, @Body SubSubAmbit subSubAmbit,@Header("Authorization") String Authorization);

    /**
     * Delete a subSubAmbit
     *
     * @param id - subSubAmbit identifier
     * */
    @DELETE("SubSubAmbits")
    Call<SubSubAmbit> Delete(@Query("id") int id,@Header("Authorization") String Authorization);
}
