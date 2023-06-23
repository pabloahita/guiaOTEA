package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountriesApi {
    @GET("Countries/idCountry={idCountry}")
    Call<Country> GetCountry(@Path("idCountry") String idCountry);

    @GET("Countries")
    Call<List<Country>> GetAll();
}
