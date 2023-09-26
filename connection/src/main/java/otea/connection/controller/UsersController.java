package otea.connection.controller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.user.User;
import otea.connection.ConnectionClient;
import otea.connection.api.UsersApi;
import retrofit2.Call;
import retrofit2.Response;

public class UsersController {

    private static UsersApi api;

    private static UsersController instance;

    private UsersController(){
        api= ConnectionClient.getInstance().getRetrofit().create(UsersApi.class);
    }

    public static UsersController getInstance(){
        if(instance==null){
            synchronized (UsersController.class){
                if(instance==null){
                    instance=new UsersController();
                }
            }
        }
        return instance;
    }


    public static User GetForLogin(String email, String password){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.GetForLogin(email,password);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static User Get(String email){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.Get(email);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                Call<List<User>> call = api.GetAll();
                Response<List<User>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<User>> future = executor.submit(callable);
            List<User> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    //GET all by user type
    public static List<User> GetAllByType(String userType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                Call<List<User>> call = api.GetAllByType(userType);
                Response<List<User>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<User>> future = executor.submit(callable);
            List<User> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //GET all organization users by organization type
    public static List<User> GetAllOrgUsersByOrganization(int idOrganization,String orgType, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<User>> callable = new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                Call<List<User>> call = api.GetAllOrgUsersByOrganization(idOrganization,orgType,illness);
                Response<List<User>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<User>> future = executor.submit(callable);
            List<User> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static User GetByType(String email, String userType) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.GetByType(email,userType);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // GET by EMAIL and ORGANIZATION action

    public static User GetOrgUserByOrganization(String email,int idOrganization,String orgType, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.GetOrgUserByOrganization(email,idOrganization,orgType,illness);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // POST action

    public static User Create(User user){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.Create(user);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // PUT action
    public static User Update(String email, User user){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.Update(email,user);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE action
    public static User Delete(String email){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<User> callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                Call<User> call = api.Delete(email);
                Response<User> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<User> future = executor.submit(callable);
            User result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
