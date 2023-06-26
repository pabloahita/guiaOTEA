package otea.connection.api;

import java.util.List;

import cli.indicators.Indicator;

import retrofit2.Call;
import retrofit2.http.*;

public interface IndicatorsApi {
    @PUT("Indicators/upd::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")
    Call<Indicator> Update(@Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType, @Path("indicatorVersion") int indicatorVersion, @Body Indicator indicator);
    @POST("Indicators")
    Call<Indicator> Create(int idIndicator, String indicatorType, String descriptionEnglish,String descriptionSpanish,String descriptionFrench, int indicatorPriority, int indicatorVersion);
    @GET("Indicators/get::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")
    Call<Indicator> Get(@Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType, @Path("indicatorVersion") int indicatorVersion);
    @GET("Indicators")
    Call<List<Indicator>> GetAll();
    @GET("Indicators/all::indicatorType={indicatorType}")
    Call<List<Indicator>> GetAllByType(@Path("indicatorType") String indicatorType);
    @DELETE("Indicators/del::idIndicator={idIndicator}:indicatorType={indicatorType}:indicatorVersion={indicatorVersion}")
    Call<Indicator> Delete(@Path("idIndicator") int idIndicator, @Path("indicatorType") String indicatorType, @Path("indicatorVersion") int indicatorVersion);
}
