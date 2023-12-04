package otea.connection.api;

import java.util.List;

import cli.organization.data.Center;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * API for Centers operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface CentersApi {

    /**Gets all centers*/
    @GET("Centers/all")
    Call<List<Center>> GetAll();

    /**
     * Gets all centers of an organization
     *
     * @param idOrganization - Organization identifier
     * */
    @GET("Centers/org")
    Call<List<Center>> GetAllByOrganization(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness);

    /**
     * Get a center
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * */
    @GET("Centers/get")
    Call<Center> Get(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Query("idCenter") int idCenter);

    /**
     * Create a new center
     *
     * @param center - Center identifier
     */
    @POST("Centers")
    Call<Center> Create(@Body Center center);

    /**
     * Update an existant center
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * @param center - Center identifier
     * */
    @PUT("Centers")
    Call<Center> Update(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Query("idCenter") int idCenter, @Body Center center);

    /**
     * Delete a center
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * */
    @DELETE("Centers")
    Call<Center> Delete(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Query("idCenter") int idCenter);
}
