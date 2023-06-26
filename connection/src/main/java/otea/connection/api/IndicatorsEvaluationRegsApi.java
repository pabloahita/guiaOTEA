package otea.connection.api;

import java.sql.Timestamp;
import java.util.List;

import cli.indicators.IndicatorsEvaluationReg;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IndicatorsEvaluationRegsApi {
    @GET("IndicatorsEvaluationRegs")
    Call<List<IndicatorsEvaluationReg>> GetAll();

    @GET("IndicatorsEvaluationRegs/indEval::evaluationDate={evaluationDate}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<List<IndicatorsEvaluationReg>> GetAllByIndicatorsEvaluation(@Path("evaluationDate") Timestamp evaluationDate,@Path("idEvaluatedOrganization") int idEvaluatedOrganization, @Path("orgType") String orgType, @Path("illness") String illness);

    @GET("IndicatorsEvaluationRegs/get::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")
    Call<IndicatorsEvaluationReg> Get(@Path("evaluationDate")Timestamp evaluationDate, @Path("idEvaluatedOrganization") int idEvaluatedOrganization, @Path("orgType") String orgType,@Path("illness") String illness,@Path("indicatorId") int indicatorId,@Path("idEvidence") int idEvidence,@Path("indicatorVersion") int indicatorVersion);

    @POST("IndicatorsEvaluationRegs")
    Call<IndicatorsEvaluationReg> Create(Timestamp evaluationDate, int idEvaluatedOrganization,String orgType,String illness,int indicatorId,int idEvidence,int isMarked, int indicatorVersion);

    @PUT("IndicatorsEvaluationRegs/upd::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")
    Call<IndicatorsEvaluationReg> Update(@Path("evaluationDate")Timestamp evaluationDate, @Path("idEvaluatedOrganization") int idEvaluatedOrganization, @Path("orgType") String orgType,@Path("illness") String illness,@Path("indicatorId") int indicatorId,@Path("idEvidence") int idEvidence,@Path("indicatorVersion") int indicatorVersion);


    @DELETE("IndicatorsEvaluationRegs/del::evaluationDate={evaluationDate}:idEvaluatedOrganization={idEvaluatedOrganization}:orgType={orgType}:illness={illness}:indicatorId={indicatorId}:idEvidence={idEvidence}:indicatorVersion={indicatorVersion}")
    Call<IndicatorsEvaluationReg> Delete(@Path("evaluationDate")Timestamp evaluationDate, @Path("idEvaluatedOrganization") int idEvaluatedOrganization, @Path("orgType") String orgType,@Path("illness") String illness,@Path("indicatorId") int indicatorId,@Path("idEvidence") int idEvidence,@Path("indicatorVersion") int indicatorVersion);

}
