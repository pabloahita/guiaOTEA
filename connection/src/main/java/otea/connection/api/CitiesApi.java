package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CitiesApi {

    @GET("Cities/get")
    Call<City> GetCity(@Query("idCity") int idCity, @Query("idProvince") int idProvince, @Query("idRegion") int idRegion,@Query("idCountry") String idCountry);

    @GET("Cities/allByProvince")
    Call<List<City>> GetCitiesByProvince(@Query("idProvince") int idProvince, @Query("idRegion") int idRegion, @Query("idCountry") String idCountry);
}
