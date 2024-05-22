package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.organization.data.geo.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for Countries operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface CountriesApi {

    /**
     * Get country
     *
     * @param idCountry - Country identifier
     * */
    @GET("Countries/get")
    Call<Country> GetCountry(@Query("idCountry") String idCountry);

    /**
     * Get all countries
     *
     * @param language - App language
     * */
    @GET("Countries/all")
    Call<List<JsonObject>> GetAll(@Query("language") String language);

}
