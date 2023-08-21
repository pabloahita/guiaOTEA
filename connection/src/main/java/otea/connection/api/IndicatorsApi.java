package otea.connection.api;

import java.util.List;

import cli.indicators.Indicator;

import retrofit2.Call;
import retrofit2.http.*;

public interface IndicatorsApi {
    @PUT("Indicators")
    Call<Indicator> Update(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("indicatorVersion") int indicatorVersion, @Body Indicator indicator);
    @POST("Indicators")
    Call<Indicator> Create(@Body Indicator indicator);
    @GET("Indicators/get")
    Call<Indicator> Get(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("indicatorVersion") int indicatorVersion);
    @GET("Indicators/all")
    Call<List<Indicator>> GetAll();
    @GET("Indicators/type")
    Call<List<Indicator>> GetAllByType(@Query("indicatorType") String indicatorType);
    @DELETE("Indicators")
    Call<Indicator> Delete(@Query("idIndicator") int idIndicator, @Query("indicatorType") String indicatorType, @Query("indicatorVersion") int indicatorVersion);
}
