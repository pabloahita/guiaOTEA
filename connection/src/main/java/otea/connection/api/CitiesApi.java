package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.organization.data.geo.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for Cities operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface CitiesApi {

    /**
     * Get a city
     *
     * @param idCity - City identifier
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * */
    @GET("Cities/get")
    Call<City> GetCity(@Query("idCity") int idCity, @Query("idProvince") int idProvince, @Query("idRegion") int idRegion,@Query("idCountry") String idCountry);

    /**
     * Get all cities of a province
     *
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * */
    @GET("Cities/allByProvince")
    Call<List<JsonObject>> GetCitiesByProvince(@Query("idProvince") int idProvince, @Query("idRegion") int idRegion, @Query("idCountry") String idCountry, @Query("language") String language);

    /**
     * Get all cities
     * */
    @GET("Cities/all")
    Call<List<City>> GetAll();

}
