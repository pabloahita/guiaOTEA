package otea.connection.api;

;
import java.util.List;

import cli.organization.data.EvaluatorTeam;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EvaluatorTeamsApi {

    @GET("EvaluatorTeams/all")
    Call<List<EvaluatorTeam>> GetAll();

    @GET("EvaluatorTeams/allByOrg")
    Call<List<EvaluatorTeam>> GetAllByOrganization(@Query("id") int id, @Query("orgType") String orgType,@Query("illness") String illness);

    @GET("EvaluatorTeams/get")
    Call<EvaluatorTeam> Get(@Query("id") int id,@Query("idEvaluatorOrg") int idEvaluatorOrg,@Query("orgType") String orgType,@Query("illness") String illness);

    // POST action
    @POST("EvaluatorTeams")
    Call<EvaluatorTeam> Create(@Body EvaluatorTeam evaluatorTeam);

    // PUT action
    @PUT("EvaluatorTeams")
    Call<EvaluatorTeam> Update(@Query("id") int id,@Query("idEvaluatorOrg") int idEvaluatorOrg,@Query("orgType") String orgType,@Query("illness") String illness,@Body EvaluatorTeam evaluatorTeam);

    // DELETE action
    @DELETE("EvaluatorTeams")
    Call<EvaluatorTeam> Delete(@Query("id") int id,@Query("idEvaluatorOrg") int idEvaluatorOrg,@Query("orgType") String orgType,@Query("illness") String illness);
}
