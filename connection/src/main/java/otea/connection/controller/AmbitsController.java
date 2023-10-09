package otea.connection.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.Ambit;
import otea.connection.ConnectionClient;
import otea.connection.api.AmbitsApi;
import retrofit2.Call;
import retrofit2.Response;

public class AmbitsController {

    private static AmbitsApi api;

    private static AmbitsController instance;

    private AmbitsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(AmbitsApi.class);
    }

    public static AmbitsController getInstance(){
        if(instance==null){
            synchronized (AmbitsController.class){
                if(instance==null){
                    instance=new AmbitsController();
                }
            }
        }
        return instance;
    }

    public static List<Ambit> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Ambit>> callable = new Callable<List<Ambit>>() {
            @Override
            public List<Ambit> call() throws Exception {
                Call<List<Ambit>> call = api.GetAll();
                Response<List<Ambit>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Ambit>> future = executor.submit(callable);
            List<Ambit> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    // GET by ID AND ORGTYPE action

    public static Ambit Get(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Get(id);
                Response<Ambit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Ambit> future = executor.submit(callable);
            Ambit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // POST action
    public static Ambit Create(Ambit ambit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Create(ambit);
                Response<Ambit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Ambit> future = executor.submit(callable);
            Ambit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Ambit Update(int idAmbit, Ambit ambit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Update(idAmbit,ambit);
                Response<Ambit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Ambit> future = executor.submit(callable);
            Ambit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static Ambit Delete(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Delete(id);
                Response<Ambit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Ambit> future = executor.submit(callable);
            Ambit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
