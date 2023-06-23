package otea.connection.api;

import java.sql.Date;
import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IndicatorsEvaluationsApi {

    @GET("IndicatorsEvaluations")
    Call<List<IndicatorsEvaluation>> GetAll();

    @GET("IndicatorsEvaluations/evalTeam::idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<List<IndicatorsEvaluation>> GetAllByEvaluatorTeam(@Path("idEvaluatorTeam") int idEvaluatorTeam, @Path("idEvaluatorOrganization") int idEvaluatorOrganization, @Path("orgType") String orgType, @Path("illness") String illness);

    @GET("IndicatorsEvaluations/get::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")
    Call<IndicatorsEvaluation> Get(@Path("evaluation_date") Date evaluation_date,@Path("idEvaluatedOrganization") int idEvaluatedOrganization,@Path("orgTypeEvaluated") String orgTypeEvaluated, @Path("illness") String illness);

    @POST("IndicatorsEvaluations")
    Call<IndicatorsEvaluation> Create(Date evaluation_date, int idEvaluatedOrganization,String orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator,String illness);

    @PUT("IndicatorsEvaluations/upd::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")
    Call<IndicatorsEvaluation> Update(@Path("evaluation_date") Date evaluation_date,@Path("idEvaluatedOrganization") int idEvaluatedOrganization,@Path("orgTypeEvaluated") String orgTypeEvaluated, @Path("illness") String illness, @Body IndicatorsEvaluation evaluatorTeamMember);

    @DELETE("IndicatorsEvaluations/del::evaluation_date={evaluation_date}:idEvaluatedOrganization:orgTypeEvaluated={orgTypeEvaluated}:illness={illness}")
    Call<IndicatorsEvaluation> Delete(@Path("evaluation_date") Date evaluation_date,@Path("idEvaluatedOrganization") int idEvaluatedOrganization,@Path("orgTypeEvaluated") String orgTypeEvaluated, @Path("illness") String illness);


}
