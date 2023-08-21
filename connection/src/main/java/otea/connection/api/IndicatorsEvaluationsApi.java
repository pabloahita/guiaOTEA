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

    @GET("IndicatorsEvaluations/evaluatorOrg")
    Call<List<IndicatorsEvaluation>> GetAllByEvaluatorOrganization(@Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgType") String orgType, @Query("illness") String illness);

    @GET("IndicatorsEvaluations/evaluatedOrg")
    Call<List<IndicatorsEvaluation>> GetAllByEvaluatedOrganization(@Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgType") String orgType, @Query("illness") String illness);

    @GET("IndicatorsEvaluations/get")
    Call<IndicatorsEvaluation> Get(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatedOrganization") int idEvaluatedOrganization,@Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness);

    @POST("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Create(@Body IndicatorsEvaluation indicatorsEvaluation);

    @PUT("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Update(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatedOrganization") int idEvaluatedOrganization,@Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Body IndicatorsEvaluation indicatorsEvaluation);

    @DELETE("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Delete(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatedOrganization") int idEvaluatedOrganization,@Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness);


}
