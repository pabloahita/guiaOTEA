package otea.connection.api;

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

    @GET("IndicatorsEvaluationRegs/indEval::idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<List<IndicatorsEvaluationReg>> GetAllByIndicatorsEvaluation(@Path("idEvaluatorTeam")int idEvaluatorTeam, @Path("idEvaluatorOrganization") int idEvaluatorOrganization, @Path("orgType") String orgType, @Path("illness") String illness);

    @GET("IndicatorsEvaluationRegs/get::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<IndicatorsEvaluationReg> Get(@Path("emailUser") String emailUser, @Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness);

    @POST("IndicatorsEvaluationRegs")
    Call<IndicatorsEvaluationReg> Create(String emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, String orgType, String illness);

    @PUT("IndicatorsEvaluationRegs/upd::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<IndicatorsEvaluationReg> Update(@Path("emailUser") String emailUser, @Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness, @Body IndicatorsEvaluationReg evaluatorTeamMember);

    @DELETE("IndicatorsEvaluationRegs/del::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<IndicatorsEvaluationReg> Delete(@Path("emailUser") String emailUser, @Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness);

}
