package otea.connection.api;

import java.util.List;

import cli.indicators.Evidence;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface EvidencesApi {
    // GET all action
    @GET("Evidences/all")
    Call<List<Evidence>> GetAll();

    // GET all by INDICATOR action
    @GET("Evidences/ind")
    Call<List<Evidence>> GetAllByIndicator(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion);

    // GET by ID AND INDICATOR action
    @GET("Evidences/get")
    Call<List<Evidence>> Get(@Query("idEvidence") int idEvidence, @Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion);

    // POST action
    @POST("Evidences")
    Call<Evidence> Create(@Body Evidence evidence);
    // PUT action
    @PUT("Evidences")
    Call<Evidence> Update(@Query("idEvidence") int idEvidence, @Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Body Evidence evidence);

    // DELETE action
    @DELETE("Evidences")
    Call<Evidence> Delete(@Query("idEvidence") int idEvidence,@Query("idIndicator") int idIndicator,@Query("indicatorType") String indicatorType, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion);
}
