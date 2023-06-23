package otea.connection.api;

import java.util.List;

import cli.indicators.Evidence;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EvidencesApi {
    // GET all action
    @GET("Evidences")
    Call<List<Evidence>> GetAll();

    // GET all by INDICATOR action
    @GET("Evidences/ind::idIndicator={idIndicator}")
    Call<List<Evidence>> GetAllByIndicator(@Path("idIndicator") int idIndicator, @Path("idIndicator") String indicatorType);

    // GET by ID AND INDICATOR action
    @GET("Evidences/get::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}")
    Call<List<Evidence>> Get(int idEvidence, int idIndicator, String indicatorType);

    // POST action
    @POST("Evidences")
    Call<Evidence> Create(int idEvidence, int idIndicator, String indicatorType, String descriptionEnglish,String descriptionSpanish,String descriptionFrench, int evidenceValue);

    // PUT action
    @PUT("Evidences/put::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}")
    Call<Evidence> Update(@Path("idEvidence") int idEvidence, @Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType, @Body Evidence evidence);

    // DELETE action
    @DELETE("Evidences/del::idEvidence={idEvidence}:idIndicator={idIndicator}:indicatorType={indicatorType}")
    Call<Evidence> Delete(@Path("idEvidence") int idEvidence,@Path("idIndicator") int idIndicator,@Path("indicatorType") String indicatorType);
}
