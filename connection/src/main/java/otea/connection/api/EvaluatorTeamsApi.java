package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.organization.data.EvaluatorTeam;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for Evaluator team operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface EvaluatorTeamsApi {

    /**Get all evaluator teams*/
    @GET("EvaluatorTeams/all")
    Call<List<JsonObject>> GetAll(@Header("Authorization") String Authorization);

    /**
     * Get all evaluator teams of a center
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param idCenter - Organization center
     * @param illness - Organization illness or syndrome
     * */
    @GET("EvaluatorTeams/allByCenter")
    Call<List<JsonObject>> GetAllByCenter(@Query("id") int id, @Query("orgType") String orgType, @Query("idCenter") int idCenter, @Query("illness") String illness, @Query("language") String language, @Header("Authorization") String Authorization);

    /**
     * Get all evaluator teams of an organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     */
    @GET("EvaluatorTeams/allByOrganization")
    Call<List<JsonObject>> GetAllByOrganization(@Query("id") int id, @Query("orgType") String orgType, @Query("illness") String illness, @Query("language") String language, @Header("Authorization") String Authorization);

    /**
     * Get an evaluator team
     *
     * @param id - Evaluator team identifier
     * @param idEvaluatorOrg - Evaluator organization identifier (who does the indicators evaluations)
     * @param orgTypeEvaluator - Evaluator organization type (who does the indicators evaluations)
     * @param idEvaluatedOrg - Evaluated organization identifier (who receives the indicators evaluations)
     * @param orgTypeEvaluated - Evaluated organization type (who receives the indicators evaluations)
     * @param idCenter - Evaluated organization center (who receives the indicators evaluations)
     * @param illness - Both organizations illness or syndrome
     * */
    @GET("EvaluatorTeams/get")
    Call<EvaluatorTeam> Get(@Query("id") int id, @Query("idEvaluatorOrg") int idEvaluatorOrg, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrg") int idEvaluatedOrg, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("idCenter") int idCenter, @Query("illness") String illness, @Header("Authorization") String Authorization);

    /**
     * Create a new evaluator team
     *
     * @param evaluatorTeam - Evaluator team
     * */
    @POST("EvaluatorTeams")
    Call<EvaluatorTeam> Create(@Body EvaluatorTeam evaluatorTeam, @Header("Authorization") String Authorization);

    /**
     * Update an existant evaluator team
     *
     * @param id - Evaluator team identifier
     * @param idEvaluatorOrg - Evaluator organization identifier (who does the indicators evaluations)
     * @param orgTypeEvaluator - Evaluator organization type (who does the indicators evaluations)
     * @param idEvaluatedOrg - Evaluated organization identifier (who receives the indicators evaluations)
     * @param orgTypeEvaluated - Evaluated organization type (who receives the indicators evaluations)
     * @param idCenter - Evaluated organization center (who receives the indicators evaluations)
     * @param illness - Both organizations illness or syndrome
     * @param evaluatorTeam - Evaluator team
     * */
    @PUT("EvaluatorTeams")
    Call<EvaluatorTeam> Update(@Query("id") int id, @Query("idEvaluatorOrg") int idEvaluatorOrg, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrg") int idEvaluatedOrg, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("idCenter") int idCenter, @Query("illness") String illness,@Body EvaluatorTeam evaluatorTeam, @Header("Authorization") String Authorization);

    /**
     * Delete an evaluator team
     *
     * @param id - Evaluator team identifier
     * @param idEvaluatorOrg - Evaluator organization identifier (who does the indicators evaluations)
     * @param orgTypeEvaluator - Evaluator organization type (who does the indicators evaluations)
     * @param idEvaluatedOrg - Evaluated organization identifier (who receives the indicators evaluations)
     * @param orgTypeEvaluated - Evaluated organization type (who receives the indicators evaluations)
     * @param idCenter - Evaluated organization center (who receives the indicators evaluations)
     * @param illness - Both organizations illness or syndrome
     * */
    @DELETE("EvaluatorTeams")
    Call<EvaluatorTeam> Delete(@Query("id") int id, @Query("idEvaluatorOrg") int idEvaluatorOrg, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrg") int idEvaluatedOrg, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("idCenter") int idCenter, @Query("illness") String illness, @Header("Authorization") String Authorization);
}
