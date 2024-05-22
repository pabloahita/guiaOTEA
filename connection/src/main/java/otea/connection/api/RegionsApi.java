package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Region;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for regions operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface RegionsApi {

    /**
     * Gets a region
     *
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * */
    @GET("Regions/get")
    Call<Region> GetRegion(@Query("idRegion") int idRegion, @Query("idCountry") String idCountry);

    /**
     * Gets all regions of a country
     *
     * @param idCountry - Country identifier
     * */
    @GET("Regions/allByCountry")
    Call<List<JsonObject>> GetRegionsByCountry(@Query("idCountry") String idCountry, @Query("language") String language);

    /**
     * Get all regions
     * */
    @GET("Regions/all")
    Call<List<Region>> GetAll();
}
