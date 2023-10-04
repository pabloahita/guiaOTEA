package otea.connection.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.user.Request;
import otea.connection.ConnectionClient;
import otea.connection.api.RequestsApi;
import retrofit2.Call;
import retrofit2.Response;

public class RequestsController {

    private static RequestsController instance;

    private static RequestsApi api;

    private RequestsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(RequestsApi.class);
    }

    public static RequestsController getInstance(){
        if(instance==null){
            synchronized (RequestsController.class){
                if(instance==null){
                    instance=new RequestsController();
                }
            }
        }
        return instance;
    }
    public static List<Request> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Request>> callable = new Callable<List<Request>>() {
            @Override
            public List<Request> call() throws Exception {
                Call<List<Request>> call=api.GetAll();
                Response<List<Request>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Request>> future = executor.submit(callable);
            List<Request> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static Request Get(String email){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Request> callable = new Callable<Request>() {
            @Override
            public Request call() throws Exception {
                Call<Request> call = api.Get(email);
                Response<Request> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Request> future = executor.submit(callable);
            Request result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Request Create(Request request){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Request> callable = new Callable<Request>() {
            @Override
            public Request call() throws Exception {
                Call<Request> call = api.Create(request);
                Response<Request> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Request> future = executor.submit(callable);
            Request result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Request Update(String email,Request request){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Request> callable = new Callable<Request>() {
            @Override
            public Request call() throws Exception {
                Call<Request> call = api.Update(email,request);
                Response<Request> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Request> future = executor.submit(callable);
            Request result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static Request Delete(String email){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Request> callable = new Callable<Request>() {
            @Override
            public Request call() throws Exception {
                Call<Request> call = api.Delete(email);
                Response<Request> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Request> future = executor.submit(callable);
            Request result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}