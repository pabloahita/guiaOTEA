package otea.connection.controller;


import java.io.IOException;
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

    public static boolean hasSessionToken;

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
        Callable<List<Address>> callable = new Callable<List<Address>>() {
            @Override
            public List<Address> call() throws Exception {
                Call<List<Address>> call = api.GetAll();
                Response<List<Address>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Address>> future = executor.submit(callable);
            List<Address> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
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
