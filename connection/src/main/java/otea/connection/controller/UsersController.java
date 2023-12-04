package otea.connection.controller;


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

/**
 * Controller class for users operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class UsersController {

    /**Users api to connect to the server*/
    private static UsersApi api;

    /**Controller instance*/
    private static UsersController instance;

    /**Class controller*/
    private UsersController(){
        api= ConnectionClient.getInstance().getRetrofit().create(UsersApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**
     * Method that obtains the login
     *
     * @param email - User login
     * @param password - User password
     * @return Login if credentials are true, null if not
     * */
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

    /**
     * Method that obtains an user from database
     *
     * @param email - User email
     * @return User if success, null if not
     * */
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

    /**
     * Method that obtains all the users
     *
     * @return User list
     * */
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


    /**
     * Method that obtains all organization users of an organization
     *
     * @param idOrganization - User organization identifier
     * @param orgType - User organization type
     * @param illness - User organization illness or syndrome
     * @return User list
     * */
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

    /**
     * Method that appends an user to the database
     *
     * @param user - User
     * @return User if success, null if not
     * */
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

    /**
     * Method that updates an user
     *
     * @param email - User email
     * @param user - User
     * @return User if success, null if not
     * */
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

    /**
     * Method that deletes an user
     *
     * @param email - User email
     * @return User if success, null if not
     * */
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
