package otea.connection.api;

import java.util.List;

import cli.organization.data.Address;
import retrofit2.Call;
import retrofit2.http.*;

public interface AddressesApi {

    // GET all action
    @GET("Addresses/all")
    Call<List<Address>> GetAll();

    // GET by ID AND ORGTYPE action

    @GET("Addresses/get")
    Call<Address> Get(@Query("id") int id);

    // POST action
    @POST("Addresses")
    Call<Address> Create(@Body Address address);

    // PUT action
    @PUT("Addresses")
    Call<Address> Update(@Query("idAddress") int idAddress, @Body Address address);

    // DELETE action
    @DELETE("Addresses")
    Call<Address> Delete(@Query("id") int id);


}
