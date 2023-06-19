package otea.connection;

import java.util.List;

import cli.indicators.Indicator;

import retrofit2.Call;
import retrofit2.http.*;

public interface IndicatorsApi {
    @PUT("Indicators/idIndicator={idIndicator}:indicatorType={indicatorType}")
    Call<Indicator> updateIndicator(@Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType, @Body Indicator indicator);
    @POST("Indicators")
    Call<Indicator> addIndicator(@Body Indicator indicator);
    @GET("Indicators/idIndicator={idIndicator}:indicatorType={indicatorType}")
    Call<Indicator> getIndicator(@Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType);
    @GET("Indicators")
    Call<List<Indicator>> getIndicators();
    @GET("Indicators/indicatorType={indicatorType}")
    Call<List<Indicator>> getIndicatorsByType(@Path("indicatorType") String indicatorType);
    @DELETE("Indicators/idIndicator={idIndicator}:indicatorType={indicatorType}")
    Call<Indicator> deleteIndicator(@Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType);
}
