package otea.connection.api;

import java.util.List;

import cli.organization.data.Address;
import retrofit2.Call;
import retrofit2.http.*;

public interface AddressesApi {

    // GET all action
    @GET("Addresses")
    Call<List<Address>> GetAll();

    // GET by ID AND ORGTYPE action

    @GET("Addresses/get::{id}")
    Call<Address> Get(@Path("id") int id);

    // POST action
    @POST("Addresses")
    Call<Address> Create(@Body Address address);

    // PUT action
    @PUT("Addresses/upd::{id}")
    Call<Address> Update(@Path("id") int id, @Body Address address);

    // DELETE action
    @DELETE("Addresses/del::{id}")
    Call<Address> Delete(@Path("id") int id);


}
