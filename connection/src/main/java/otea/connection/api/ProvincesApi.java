package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Province;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProvincesApi {

    @GET("Provinces/get")
    Call<Province> GetProvince(@Query("idProvince") int idProvince, @Query("idRegion") int idRegion,@Query("idCountry") String idCountry);

    @GET("Provinces/region")
    Call<List<Province>> GetProvincesByRegion(@Query("idRegion") int idRegion,@Query("idCountry") String idCountry);
    
}
