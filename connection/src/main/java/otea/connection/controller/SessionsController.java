package otea.connection.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.user.Session;
import cli.user.Session;
import otea.connection.ConnectionClient;
import otea.connection.api.SessionsApi;
import retrofit2.Call;
import retrofit2.Response;

public class SessionsController {

    private static SessionsController instance;

    private static SessionsApi api;

    private SessionsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(SessionsApi.class);
    }

    public static SessionsController getInstance(){
        if(instance==null){
            synchronized (SessionsController.class){
                if(instance==null){
                    instance=new SessionsController();
                }
            }
        }
        return instance;
    }
    public static List<Session> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Session>> callable = new Callable<List<Session>>() {
            @Override
            public List<Session> call() throws Exception {
                Call<List<Session>> call=api.GetAll();
                Response<List<Session>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Session>> future = executor.submit(callable);
            List<Session> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session Create(Session session){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Session> callable = new Callable<Session>() {
            @Override
            public Session call() throws Exception {
                Call<Session> call = api.Create(session);
                Response<Session> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Session> future = executor.submit(callable);
            Session result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session Get(String sessionToken){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Session> callable = new Callable<Session>() {
            @Override
            public Session call() throws Exception {
                Call<Session> call = api.Get(sessionToken);
                Response<Session> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Session> future = executor.submit(callable);
            Session result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session Update(String sessionToken, Session session){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Session> callable = new Callable<Session>() {
            @Override
            public Session call() throws Exception {
                Call<Session> call = api.Update(sessionToken,session);
                Response<Session> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Session> future = executor.submit(callable);
            Session result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static Session Delete(String sessionToken){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Session> callable = new Callable<Session>() {
            @Override
            public Session call() throws Exception {
                Call<Session> call = api.Delete(sessionToken);
                Response<Session> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Session> future = executor.submit(callable);
            Session result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
}
