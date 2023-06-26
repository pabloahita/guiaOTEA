package otea.connection.api;

import java.util.List;

import cli.organization.data.Center;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CentersApi {

    @GET("Centers")
    Call<List<Center>> GetAll();

    @GET("Centers/org::idOrganization={idOrganization}:orgType={orgType}:illness={illness}")
    Call<List<Center>> GetAllByOrganization(@Path("idOrganization") int idOrganization, @Path("orgType") String orgType, @Path("illness") String illness);

    @GET("Centers/get::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")
    Call<Center> Get(@Path("idOrganization") int idOrganization, @Path("orgType") String orgType, @Path("illness") String illness, @Path("idCenter") int idCenter);

    @POST("Centers")
    Call<Center> Create(int idOrganization,String orgType,String illness,int idCenter,String centerDescription,int idAddress,long telephone);

    @PUT("Centers/put::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")
    Call<Center> Update(@Path("idOrganization") int idOrganization, @Path("orgType") String orgType, @Path("illness") String illness, @Path("idCenter") int idCenter, @Body Center center);

    @DELETE("Centers/del::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")
    Call<Center> Delete(@Path("idOrganization") int idOrganization, @Path("orgType") String orgType, @Path("illness") String illness, @Path("idCenter") int idCenter);
}
