package otea.connection.api;

import java.sql.Timestamp;
import java.util.List;

import cli.organization.data.EvaluatorTeam;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EvaluatorTeamsApi {

    @GET("EvaluatorTeams")
    Call<List<EvaluatorTeam>> GetAll();

    @GET("EvaluatorTeams/org::id={id}:orgType={orgType}:illness={illness}")
    Call<List<EvaluatorTeam>> GetAllByOrganization(@Path("id") int id, @Path("orgType") String orgType,@Path("illness") String illness);

    @GET("EvaluatorTeams/get::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")
    Call<EvaluatorTeam> Get(@Path("id") int id,@Path("idEvaluatorOrg") int idEvaluatorOrg,@Path("orgType") String orgType,@Path("illness") String illness);

    // POST action
    @POST("EvaluatorTeams")
    Call<EvaluatorTeam> Create(int id, Timestamp creation_date, int idOrganization, String orgType, String illness, String emailConsultant, String emailProfessional, String emailResponsible, String patient_name, String relative_name);

    // PUT action
    @PUT("EvaluatorTeams/upd::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")
    Call<EvaluatorTeam> Update(@Path("id") int id,@Path("idEvaluatorOrg") int idEvaluatorOrg,@Path("orgType") String orgType,@Path("illness") String illness,@Body EvaluatorTeam evaluatorTeam);

    // DELETE action
    @DELETE("EvaluatorTeams/del::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")
    Call<EvaluatorTeam> Delete(@Path("id") int id,@Path("idEvaluatorOrg") int idEvaluatorOrg,@Path("orgType") String orgType,@Path("illness") String illness);
}
