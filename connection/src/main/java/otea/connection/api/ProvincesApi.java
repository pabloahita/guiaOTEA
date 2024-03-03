package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Province;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for Provinces operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface ProvincesApi {

    /**
     * Gets a province
     *
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * */
    @GET("Provinces/get")
    Call<Province> GetProvince(@Query("idProvince") int idProvince, @Query("idRegion") int idRegion,@Query("idCountry") String idCountry);

    /**
     * Gets all provinces of a region
     *
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * */
    @GET("Provinces/region")
    Call<List<Province>> GetProvincesByRegion(@Query("idRegion") int idRegion,@Query("idCountry") String idCountry);

    /**
     * Get all provinces
     * */
    @GET("Provinces/all")
    Call<List<Province>> GetAll();
}
