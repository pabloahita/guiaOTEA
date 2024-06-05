package otea.connection.controller;


import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.data.Address;
import otea.connection.ConnectionClient;
import otea.connection.api.AddressesApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller class for address operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 *
 * */
public class AddressesController {

    /**Addresses API to connect to the server*/
    private static AddressesApi api;

    /**Controller instance*/
    private static AddressesController instance;

    private static int numAttempts=0;

    /**Class constructor*/
    private AddressesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(AddressesApi.class);
    }

    /**
     * Method that obtains the controller instance
     *
     * @return Controller instance
     * */
    public static AddressesController getInstance(){
        if(instance==null){
            synchronized (AddressesController.class){
                if(instance==null){
                    instance=new AddressesController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new AddressesController();
    }

    /**
     * Method that obtains from the database all the addresses
     *
     * @return Addresses list
     * */
    public static List<Address> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAll();
                Response<List<JsonObject>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<JsonObject>> future = executor.submit(callable);
            List<JsonObject> list = future.get();
            executor.shutdown();
            numAttempts=0;
            List<Address> addresses=new ArrayList<>();
            for(JsonObject address:list){
                int idAddress=address.getAsJsonPrimitive("idAddress").getAsInt();
                String addressName=address.getAsJsonPrimitive("addressName").getAsString();
                int idCity=address.getAsJsonPrimitive("idCity").getAsInt();
                int idProvince=address.getAsJsonPrimitive("idProvince").getAsInt();
                int idRegion=address.getAsJsonPrimitive("idRegion").getAsInt();
                String idCountry=address.getAsJsonPrimitive("idCountry").getAsString();
                String nameCity=address.getAsJsonPrimitive("nameCity").getAsString();
                String nameProvince=address.getAsJsonPrimitive("nameProvince").getAsString();
                String nameRegion=address.getAsJsonPrimitive("nameRegion").getAsString();
                addresses.add(new Address(idAddress, addressName, idCity, idProvince, idRegion, idCountry, nameCity, nameProvince, nameRegion));
            }
            return addresses;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetAll();
                }
                else{
                    numAttempts=0;
                    return null;
                }
            }
            else{
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method that obtains from the database the address using its identifier
     *
     * @param idAddress - Address identifier
     * @return Address if is in the database, null if not
     * */
    public static Address Get(int idAddress) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Address> callable = new Callable<Address>() {
            @Override
            public Address call() throws Exception {
                Call<Address> call = api.Get(idAddress);
                Response<Address> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Address> future = executor.submit(callable);
            Address result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends the address to the database
     *
     * @param address - New address
     * @return Address if was append, null if not
     * */
    public static Address Create(Address address) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Address> callable = new Callable<Address>() {
            @Override
            public Address call() throws Exception {
                Call<Address> call = api.Create(address);
                Response<Address> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Address> future = executor.submit(callable);
            Address result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates an address using its identifier
     *
     * @param id - Address identifier
     * @param address - Address
     * @return Address if was updated, null if not
     * */
    public static Address Update(int id, Address address){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Address> callable = new Callable<Address>() {
            @Override
            public Address call() throws Exception {
                Call<Address> call = api.Update(id,address);
                Response<Address> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Address> future = executor.submit(callable);
            Address result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes the address using its identifier
     *
     * @param id - Address identifier
     * @return Address if was deleted, null if not
     *
     * */
    public static Address Delete(int id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Address> callable = new Callable<Address>() {
            @Override
            public Address call() throws Exception {
                Call<Address> call = api.Delete(id);
                Response<Address> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Address> future = executor.submit(callable);
            Address result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
