package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountriesApi {
    @GET("Countries/get")
    Call<Country> GetCountry(@Query("idCountry") String idCountry);

    @GET("Countries/all")
    Call<List<Country>> GetAll(@Query("language") String language);

    @GET("Countries/countriesWithPhoneCode")
    Call<List<Country>> GetCountriesWithPhoneCode(@Query("language") String language);
}
