package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.organization.*;
import retrofit2.*;
import retrofit2.http.*;

/**
 * API for organizations operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface OrganizationsApi {

    /**Gets all organizations*/
    @GET("Organizations/all")
    Call<List<JsonObject>> GetAll();


    /**Gets all evaluated organizations*/
    @GET("Organizations/allEvaluated")
    Call<List<JsonObject>> GetAllEvaluatedOrganizations();

    /**Gets all evaluator organizations*/
    @GET("Organizations/allEvaluator")
    Call<List<JsonObject>> GetAllEvaluatorOrganizations();

    /**
     * Gets an organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * */
    @GET("Organizations/get")
    Call<Organization> Get(@Query("id") int id, @Query("orgType") String orgType, @Query("illness") String illness);

    /**
     * Creates an organization
     *
     * @param organization - Organization
     * */
    @POST("Organizations")
    Call<Organization> Create(@Body Organization organization,@Header("Authorization") String Authorization);

    /**
     * Updates an organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param organization - Organization
     * */
    @PUT("Organizations")
    Call<Organization> Update(@Query("id") int id, @Query("orgType") String orgType, @Query("illness") String illness, @Body Organization organization,@Header("Authorization") String Authorization);

    /**
     * Deletes an organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * */
    @DELETE("Organizations")
    Call<Organization> Delete(@Query("id") int id, @Query("orgType") String orgType, @Query("illness")  String illness,@Header("Authorization") String Authorization);
}
