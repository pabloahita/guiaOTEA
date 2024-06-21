package otea.connection.api;

import java.util.List;

import cli.indicators.Evidence;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for Evidences operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface EvidencesApi {

    /**Gets all evidences*/
    @GET("Evidences/all")
    Call<List<Evidence>> GetAll(@Query("evaluationType") String evaluationType, @Header("Authorization") String Authorization);

    /**
     * Gets all evidences by indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     *
     * */
    @GET("Evidences/ind")
    Call<List<Evidence>> GetAllByIndicator(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType, @Header("Authorization") String Authorization);

    /**
     * Gets an evidence
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * */
    @GET("Evidences/get")
    Call<Evidence> Get(@Query("idEvidence") int idEvidence, @Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType, @Header("Authorization") String Authorization);

    /**
     * Creates new evidence
     *
     * @param evidence - Evidence
     * */
    @POST("Evidences")
    Call<Evidence> Create(@Body Evidence evidence, @Header("Authorization") String Authorization);

    /**
     * Updates an existant evidence
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @param evidence - Evidence
     * */
    @PUT("Evidences")
    Call<Evidence> Update(@Query("idEvidence") int idEvidence, @Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType, @Body Evidence evidence, @Header("Authorization") String Authorization);

    /**
     * Deletes an evidence
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * */
    @DELETE("Evidences")
    Call<Evidence> Delete(@Query("idEvidence") int idEvidence,@Query("idIndicator") int idIndicator,@Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType, @Header("Authorization") String Authorization);
}
