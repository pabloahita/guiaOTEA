package otea.connection;

import java.util.List;

import cli.organization.*;
import retrofit2.*;
import retrofit2.http.*;

public interface OrganizationsApi {
    // GET all action
    @GET("Organizations")
    Call<List<Organization>> GetAll();


    // GET all evaluated organizations action
    @GET("Organizations/evaluated")
    Call<List<Organization>> GetAllEvaluatedOrganizations();

    // GET all evaluator organizations action
    @GET("Organizations/evaluated")
    Call<List<Organization>> GetAllEvaluatorOrganizations();

    // GET by ID, ORGTYPE AND ILLNESS
    @GET("Organizations/id={id}:orgType={orgType}:illness={illness}")
    Call<Organization> Get(@Path("id") int id,@Path("orgType") String orgType,@Path("illness") String illness);

    // POST action
    @POST("Organizations")
    Call<Organization> Create(int id, String orgType, String illness, String name, int idAddress, String email, int telephone, String information, String emailOrgPrincipal, String emailOrgConsultant);
    // PUT action
    @PUT("Organizations/id={id}:orgType={orgType}:illness={illness}")
    Call<Organization> Update(@Path("id") int id,@Path("orgType") String orgType,@Path("illness") String illness,@Body Organization organization);
    // DELETE action
    @DELETE("Organizations/id={id}:orgType={orgType}:illness={illness}")
    Call<Organization> Delete(@Path("id") int id, @Path("orgType") String orgType,@Path("illness")  String illness);
}
