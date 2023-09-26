package otea.connection.api;

import java.util.List;

import cli.organization.data.Center;
import retrofit2.Call;
import retrofit2.http.*;

public interface CentersApi {

    @GET("Centers/all")
    Call<List<Center>> GetAll();

    @GET("Centers/org")
    Call<List<Center>> GetAllByOrganization(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness);

    @GET("Centers/get")
    Call<Center> Get(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Query("idCenter") int idCenter);

    @POST("Centers")
    Call<Center> Create(@Body Center center);

    @PUT("Centers")
    Call<Center> Update(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Query("idCenter") int idCenter, @Body Center center);

    @DELETE("Centers")
    Call<Center> Delete(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Query("idCenter") int idCenter);
}
