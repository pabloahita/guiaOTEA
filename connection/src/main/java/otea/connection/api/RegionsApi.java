package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.Region;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RegionsApi {
    @GET("Regions/get")
    Call<Region> GetRegion(@Query("idRegion") int idRegion, @Query("idCountry") String idCountry);

    @GET("Regions/country")
    Call<List<Region>> GetRegionsByCountry(@Query("idCountry") String idCountry);
}
