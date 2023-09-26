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

public class AddressesController {

    private static AddressesApi api;

    private static AddressesController instance;

    private AddressesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(AddressesApi.class);
    }

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

    // POST action
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

    // PUT action
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

    // DELETE action
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
