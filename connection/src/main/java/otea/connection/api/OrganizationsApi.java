package otea.connection.api;

import java.util.List;

import cli.organization.*;
import retrofit2.*;
import retrofit2.http.*;

public interface OrganizationsApi {
    // GET all action
    @GET("Organizations/all")
    Call<List<Organization>> GetAll();


    // GET all evaluated organizations action
    @GET("Organizations/allEvaluated")
    Call<List<Organization>> GetAllEvaluatedOrganizations();

    // GET all evaluator organizations action
    @GET("Organizations/allEvaluator")
    Call<List<Organization>> GetAllEvaluatorOrganizations();

    // GET by ID, ORGTYPE AND ILLNESS
    @GET("Organizations/get")
    Call<Organization> Get(@Query("id") int id, @Query("orgType") String orgType, @Query("illness") String illness);

    @GET("Organizations/evaluated")
    Call<Organization> GetEvaluatedOrganizationById(@Query("id") int id, @Query("illness") String illness);

    @GET("Organizations/evaluator")
    Call<Organization> GetEvaluatorOrganizationById(@Query("id") int id, @Query("illness") String illness);

    // POST action
    @POST("Organizations")
    Call<Organization> Create(@Body Organization organization);
    // PUT action
    @PUT("Organizations")
    Call<Organization> Update(@Query("id") int id, @Query("orgType") String orgType, @Query("illness") String illness, @Body Organization organization);
    // DELETE action
    @DELETE("Organizations")
    Call<Organization> Delete(@Query("id") int id, @Query("orgType") String orgType, @Query("illness")  String illness);
}
