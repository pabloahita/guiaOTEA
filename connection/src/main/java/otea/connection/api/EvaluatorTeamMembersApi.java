package otea.connection.api;

import java.util.List;

import cli.organization.data.EvaluatorTeamMember;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EvaluatorTeamMembersApi {

    @GET("EvaluatorTeamMembers/all")
    Call<List<EvaluatorTeamMember>> GetAll();

    @GET("EvaluatorTeamMembers/evalTeam")
    Call<List<EvaluatorTeamMember>> GetAllByEvaluatorTeam(@Query("idEvaluatorTeam")int idEvaluatorTeam,@Query("idEvaluatorOrganization") int idEvaluatorOrganization,@Query("orgType") String orgType,@Query("illness") String illness);

    @GET("EvaluatorTeamMembers/get")
    Call<EvaluatorTeamMember> Get(@Query("emailUser") String emailUser, @Query("idEvaluatorTeam")int idEvaluatorTeam,@Query("idEvaluatorOrganization") int idEvaluatorOrganization,@Query("orgType") String orgType,@Query("illness") String illness);

    @POST("EvaluatorTeamMembers")
    Call<EvaluatorTeamMember> Create(@Body EvaluatorTeamMember evaluatorTeamMember);

    @PUT("EvaluatorTeamMembers")
    Call<EvaluatorTeamMember> Update(@Query("emailUser") String emailUser, @Query("idEvaluatorTeam")int idEvaluatorTeam,@Query("idEvaluatorOrganization") int idEvaluatorOrganization,@Query("orgType") String orgType,@Query("illness") String illness, @Body EvaluatorTeamMember evaluatorTeamMember);

    @DELETE("EvaluatorTeamMembers")
    Call<EvaluatorTeamMember> Delete(@Query("emailUser") String emailUser, @Query("idEvaluatorTeam")int idEvaluatorTeam,@Query("idEvaluatorOrganization") int idEvaluatorOrganization,@Query("orgType") String orgType,@Query("illness") String illness);
}
