package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CitiesApi {

    @GET("Cities/city::idCity={idCity}:idProvince={idProvince}:idRegion={idRegion}:idCountry={idCountry}")
    Call<City> GetCity(@Path("idCity") int idCity, @Path("idProvince") int idProvince, @Path("idRegion") int idRegion,@Path("idCountry") String idCountry);

    @GET("Cities/province::idProvince={idProvince}:idRegion={idRegion}:idCountry={idCountry}")
    Call<List<City>> GetCitiesByProvince(@Path("idProvince") int idProvince, @Path("idRegion") int idRegion, @Path("idCountry") String idCountry);
}
