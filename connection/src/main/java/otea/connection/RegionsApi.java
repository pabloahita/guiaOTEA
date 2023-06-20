package otea.connection;

import java.util.List;

import cli.organization.data.geo.Region;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RegionsApi {
    @GET("Regions/idRegion={idRegion}:idCountry={idCountry}")
    Call<Region> GetRegion(@Path("idRegion") int idRegion, @Path("idCountry") String idCountry);

    @GET("Regions/idCountry={idCountry}")
    Call<List<Region>> GetRegionsByCountry(@Path("idCountry") String idCountry);
}
