package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.indicators.Indicator;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * API for indicators operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface IndicatorsApi {

    /**
     * Updates an indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @param indicator - Indicator
     * */
    @PUT("Indicators")
    Call<Indicator> Update(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType, @Body Indicator indicator,@Header("Authorization") String Authorization);

    /**
     * Creates an indicator
     *
     * @param indicator - Indicator
     * */
    @POST("Indicators")
    Call<Indicator> Create(@Body Indicator indicator,@Header("Authorization") String Authorization);

    /**
     * Gets an indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * */
    @GET("Indicators/get")
    Call<Indicator> Get(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType,@Header("Authorization") String Authorization);

    /**Gets all indicators*/
    @GET("Indicators/all")
    Call<List<JsonObject>> GetAll(@Query("evaluationType") String evaluationType,@Query("language") String language,@Header("Authorization") String Authorization);

    /**
     * Gets all ambits by ambit identifier
     *
     * @param idAmbit - Ambit identifier
     * */
    @GET("Indicators/ambit")
    Call<List<Indicator>> GetAllByIdAmbit(@Query("idAmbit") int idAmbit,@Header("Authorization") String Authorization);

    /**
     * Deletes an indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * */
    @DELETE("Indicators")
    Call<Indicator> Delete(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("idSubSubAmbit") int idSubSubAmbit, @Query("idSubAmbit") int idSubAmbit, @Query("idAmbit") int idAmbit, @Query("indicatorVersion") int indicatorVersion, @Query("evaluationType") String evaluationType,@Header("Authorization") String Authorization);
}
