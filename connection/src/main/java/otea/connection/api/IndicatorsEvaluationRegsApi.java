package otea.connection.api;

;
import java.util.List;

import cli.indicators.IndicatorsEvaluationReg;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IndicatorsEvaluationRegsApi {
    @GET("IndicatorsEvaluationsRegs/all")
    Call<List<IndicatorsEvaluationReg>> GetAll();

    @GET("IndicatorsEvaluationsRegs/indEval")
    Call<List<IndicatorsEvaluationReg>> GetAllByIndicatorsEvaluation(@Query("evaluationDate") long evaluationDate,@Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter);
    @GET("IndicatorsEvaluationsRegs/get")
    Call<IndicatorsEvaluationReg> Get(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("idEvidence") int idEvidence, @Query("indicatorValue") int indicatorVersion);

    @POST("IndicatorsEvaluationsRegs")
    Call<IndicatorsEvaluationReg> Create(@Body IndicatorsEvaluationReg indicatorsEvaluationReg);

    @PUT("IndicatorsEvaluationsRegs")
    Call<IndicatorsEvaluationReg> Update(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("idEvidence") int idEvidence, @Query("indicatorValue") int indicatorVersion, @Body IndicatorsEvaluationReg indicatorsEvaluationReg);


    @DELETE("IndicatorsEvaluationsRegs")
    Call<IndicatorsEvaluationReg> Delete(@Query("evaluationDate") long evaluationDate, @Query("idEvaluatorTeam") int idEvaluatorTeam, @Query("idEvaluatorOrganization") int idEvaluatorOrganization, @Query("orgTypeEvaluator") String orgTypeEvaluator, @Query("idEvaluatedOrganization") int idEvaluatedOrganization, @Query("orgTypeEvaluated") String orgTypeEvaluated, @Query("illness") String illness, @Query("idCenter") int idCenter, @Query("idAmbit") int idAmbit, @Query("idIndicator") int idIndicator, @Query("idEvidence") int idEvidence, @Query("indicatorValue") int indicatorVersion);

}
