package otea.connection.api;

;
import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IndicatorsEvaluationsApi {

    @GET("IndicatorsEvaluations/all")
    Call<List<IndicatorsEvaluation>> GetAll();

    @GET("IndicatorsEvaluations/evaluatorTeam")
    Call<List<IndicatorsEvaluation>> GetAllByEvaluatorTeam(@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrg") int idEvaluatorOrg, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrg") int idEvaluatedOrg, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("idCenter") int idCenter, @Query("illness") String illness);

    @GET("IndicatorsEvaluations/nonFinished")
    Call<List<IndicatorsEvaluation>> GetNonFinishedByCenter(@Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter);

    @GET("IndicatorsEvaluations/get")
    Call<IndicatorsEvaluation> Get(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter);

    @POST("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Create(@Body IndicatorsEvaluation indicatorsEvaluation);

    @PUT("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Update(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Body IndicatorsEvaluation indicatorsEvaluation);

    @DELETE("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Delete(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter);


}
