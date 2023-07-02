package otea.connection.api;

import java.util.List;

import cli.organization.data.EvaluatorTeamMember;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EvaluatorTeamMembersApi {

    @GET("EvaluatorTeamMembers")
    Call<List<EvaluatorTeamMember>> GetAll();

    @GET("EvaluatorTeamMembers/evalTeam::idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<List<EvaluatorTeamMember>> GetAllByEvaluatorTeam(@Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness);

    @GET("EvaluatorTeamMembers/get::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<EvaluatorTeamMember> Get(@Path("emailUser") String emailUser, @Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness);

    @POST("EvaluatorTeamMembers")
    Call<EvaluatorTeamMember> Create(@Body EvaluatorTeamMember evaluatorTeamMember);

    @PUT("EvaluatorTeamMembers/upd::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<EvaluatorTeamMember> Update(@Path("emailUser") String emailUser, @Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness, @Body EvaluatorTeamMember evaluatorTeamMember);

    @DELETE("EvaluatorTeamMembers/del::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")
    Call<EvaluatorTeamMember> Delete(@Path("emailUser") String emailUser, @Path("idEvaluatorTeam")int idEvaluatorTeam,@Path("idEvaluatorOrganization") int idEvaluatorOrganization,@Path("orgType") String orgType,@Path("illness") String illness);
}
