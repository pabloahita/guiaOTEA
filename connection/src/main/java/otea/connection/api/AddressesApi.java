package otea.connection.api;

import com.google.gson.JsonObject;

import java.util.List;

import cli.organization.data.Address;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * API for Address operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface AddressesApi {

    /**Get all Addresses*/
    @GET("Addresses/all")
    Call<List<JsonObject>> GetAll();

    /**
     * Get an address using its identifier
     *
     * @param id - Address identifier
     * */
    @GET("Addresses/get")
    Call<Address> Get(@Query("id") int id);

    /**
     * Create new Address
     *
     * @param address - Address
     * */
    @POST("Addresses")
    Call<Address> Create(@Body Address address);

    /**
     * Update an existant Address
     *
     * @param idAddress - Address identifier
     * @param address - Address
     * */
    @PUT("Addresses")
    Call<Address> Update(@Query("idAddress") int idAddress, @Body Address address);

    /**
     * Delete an address
     *
     * @param id - Address identifier
     * */
    @DELETE("Addresses")
    Call<Address> Delete(@Query("id") int id);


}
