package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for indicators evaluations operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface IndicatorsEvaluationsApi {

    /**Gets all indicators evaluations*/
    @GET("IndicatorsEvaluations/all")
    Call<List<JsonObject>> GetAll(@Header("Authorization") String Authorization);

    /**Gets all indicators evaluations*/
    @GET("IndicatorsEvaluations/allRegs")
    Call<List<JsonObject>> GetRegsByIndicatorsEvaluation(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("evaluationType") String evaluationType, @Query("language") String language, @Header("Authorization") String Authorization);

    /**
     * Gets all indicators evaluations of an evaluator team
     *
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatorOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatedOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param idCenter - Center identifier
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * */
    @GET("IndicatorsEvaluations/evaluatorTeam")
    Call<List<JsonObject>> GetAllByEvaluatorTeam(@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("idCenter") int idCenter, @Query("illness") String illness,@Query("language") String language,@Header("Authorization") String Authorization);

    /**
     * Get all non finished indicators evaluations by evaluator team
     *
     * @param idEvaluatorTeam - Identifier of the evaluator team
     * @param idEvaluatorOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatedOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier
     * */
    @GET("IndicatorsEvaluations/nonFinished")
    Call<List<JsonObject>> GetNonFinishedByEvaluatorTeam(@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("language") String language, @Header("Authorization") String Authorization);

    /**
     * Gets an indicators evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * */
    @GET("IndicatorsEvaluations/get")
    Call<IndicatorsEvaluation> Get(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("evaluationType") String evaluationType,@Header("Authorization") String Authorization);

    /**
     * Creates a new indicators evaluation
     *
     * @param indicatorsEvaluation - Indicators evaluation
     * */
    @POST("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Create(@Body IndicatorsEvaluation indicatorsEvaluation,@Header("Authorization") String Authorization);

    /**
     * Updates an indicators evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param indicatorsEvaluation - Indicators evaluation
     * */
    @PUT("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Update(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("evaluationType") String evaluatorType, @Body IndicatorsEvaluation indicatorsEvaluation,@Header("Authorization") String Authorization);

    /**
     * Gets the finished indicator evaluation with its results
     *
     *
     * @param indicatorsEvaluation - Indicators evaluation
     * */
    @PUT("IndicatorsEvaluations/result")
    Call<ResponseBody> calculateResults(@Body IndicatorsEvaluation indicatorsEvaluation,@Header("Authorization") String Authorization);

    /**
     * Deletes an indicator evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * */
    @DELETE("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Delete(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("evaluationType") String evaluatorType,@Header("Authorization") String Authorization);


}
