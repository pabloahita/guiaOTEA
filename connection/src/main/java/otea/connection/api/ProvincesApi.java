package otea.connection.api;

import java.util.List;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Province;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProvincesApi {

    @GET("Provinces/get::idProvince={idProvince}:idRegion={idRegion}:idCountry={idCountry}")
    Call<Province> GetProvince(@Path("idProvince") int idProvince, @Path("idRegion") int idRegion,@Path("idCountry") String idCountry);

    @GET("Provinces/region::idRegion={idRegion}:idCountry={idCountry}")
    Call<List<Province>> GetProvincesByRegion(@Path("idRegion") int idRegion,@Path("idCountry") String idCountry);
    
}
