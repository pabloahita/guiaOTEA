package otea.connection.api;

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
    @GET("Organizations/evaluator")
    Call<List<Organization>> GetAllEvaluatorOrganizations();

    // GET by ID, ORGTYPE AND ILLNESS
    @GET("Organizations/get::id={id}:orgType={orgType}:illness={illness}")
    Call<Organization> Get(@Path("id") int id, @Path("orgType") String orgType, @Path("illness") String illness);

    @GET("Organizations/evaluated::id={id}:illness={illness}")
    Call<Organization> GetEvaluatedOrganizationById(@Path("id") int id, @Path("illness") String illness);

    @GET("Organizations/evaluator::id={id}:illness={illness}")
    Call<Organization> GetEvaluatorOrganizationById(@Path("id") int id, @Path("illness") String illness);

    // POST action
    @POST("Organizations")
    Call<Organization> Create(@Body Organization organization);
    // PUT action
    @PUT("Organizations/upd::id={id}:orgType={orgType}:illness={illness}")
    Call<Organization> Update(@Path("id") int id, @Path("orgType") String orgType, @Path("illness") String illness, @Body Organization organization);
    // DELETE action
    @DELETE("Organizations/del::id={id}:orgType={orgType}:illness={illness}")
    Call<Organization> Delete(@Path("id") int id, @Path("orgType") String orgType, @Path("illness")  String illness);
}
