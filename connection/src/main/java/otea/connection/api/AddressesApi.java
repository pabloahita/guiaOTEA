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
    Call<Address> Create(int idAddress, String nameStreet, int numberStreet, int floorApartment, char apartmentLetter, int zipCode, int idCity, int idProvince, int idRegion, String idCountry);

    // PUT action
    @PUT("Addresses/upd::{id}")
    Call<Address> Update(@Path("id") int id, Address address);

    // DELETE action
    @DELETE("Addresses/del::{id}")
    Call<Address> Delete(@Path("id") int id);


}
