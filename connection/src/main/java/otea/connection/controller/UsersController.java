package otea.connection.controller;



import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import otea.connection.ConnectionClient;
import otea.connection.api.UsersApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

import com.google.gson.JsonObject;

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

    /**Number of attempts*/
    private static int numAttempts=0;

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

    /**Refresh API*/
    public static void refreshApi(){
        instance=new UsersController();
    }

    public interface LoginCallback {
        void onSuccess(JsonObject data);
        void onError(JsonObject errorResponse);
    }


    /**
     * Method that obtains the login
     *
     * @param credentials - Credentials
     * @return Login if credentials are true, null if not
     * */
    public static void Login(JsonObject credentials, LoginCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<JsonObject> callable = new Callable<JsonObject>() {
            @Override
            public JsonObject call() throws Exception {
                Call<JsonObject> call = api.Login(credentials);
                Response<JsonObject> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    if(response.code() == 404) {
                        throw new IOException("Not found");
                    } else if(response.code() == 401) {
                        throw new IOException("Unauthorized");
                    }
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };

        executor.submit(() -> {
            try {
                JsonObject result = callable.call();
                callback.onSuccess(result);
            } catch (Exception e) {
                JsonObject errorResponse = new JsonObject();
                if (e.getMessage().equals("Not found")) {
                    errorResponse.addProperty("errorCode", 404);
                } else if (e.getMessage().equals("Unauthorized")) {
                    errorResponse.addProperty("errorCode", 401);
                } else {

                    errorResponse.addProperty("errorCode",408);
                }
                callback.onError(errorResponse);
            } finally {
                executor.shutdown();
            }
        });
    }

    /**
     * Method that obtains an user from database
     *
     * @param email - User email
     * @return User if success, null if not
     * */
    public static User Get(String email){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<JsonObject> callable= getInstance().GetASync(email);
        try {
            Future<JsonObject> future = executor.submit(callable);
            JsonObject jsonUser = future.get();
            executor.shutdown();
            return new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), "", jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString());
        } catch (InterruptedException | ExecutionException e) {
            if(e.getMessage().equals("java.io.IOException: Not found")){
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    public static Callable<JsonObject> GetASync(String email){
        return new Callable<JsonObject>() {
            @Override
            public JsonObject call() throws Exception {
                Call<JsonObject> call = api.Get(email);
                Response<JsonObject> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    if(response.code()==404){
                        throw new IOException("Not found");
                    }
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
    }

    public static Callable<JsonObject> GetDirector(Organization organization){
        return new Callable<JsonObject>() {
            @Override
            public JsonObject call() throws Exception {
                Call<JsonObject> call = api.GetDirector(organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness(),Session.getInstance().getToken());
                Response<JsonObject> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    if(response.code()==404){
                        throw new IOException("Not found");
                    }
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
    }

    public static List<User> GetOrganizationAndEvalTeamStaff(Organization organization, EvaluatorTeam evaluatorTeam){
        ExecutorService executor=Executors.newFixedThreadPool(3);
        List<Future<JsonObject>> futures=new ArrayList<>();
        futures.add(executor.submit(GetDirector(organization)));
        futures.add(executor.submit(GetASync(evaluatorTeam.getEmailResponsible())));
        futures.add(executor.submit(GetASync(evaluatorTeam.getEmailProfessional())));
        List<User> users=new ArrayList<>();
        for(Future<JsonObject> future:futures){
            try {
                JsonObject jsonUser=future.get();
                users.add(new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), "", jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString()));
            } catch (InterruptedException | ExecutionException e) {
                if(e.getMessage().equals("java.io.IOException: Not found")){
                    return null;
                }
                throw new RuntimeException(e);
            }
        }
        return users;
    }

    /**
     * Method that obtains all the users
     *
     * @return User list
     * */
    public static List<User> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAll(Session.getInstance().getToken());
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
            List<JsonObject> jsonList = future.get();
            List<User> list=new ArrayList<>();
            for(JsonObject jsonUser:jsonList){
                list.add(new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), "", jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString()));
            }
            executor.shutdown();
            return list;
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
     * Method that obtains all organization users of an organization
     *
     * @param idOrganization - User organization identifier
     * @param orgType - User organization type
     * @param illness - User organization illness or syndrome
     * @return User list
     * */
    public static List<User> GetAllOrgUsersByOrganization(int idOrganization,String orgType, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAllOrgUsersByOrganization(idOrganization,orgType,illness,Session.getInstance().getToken());
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
            List<JsonObject> jsonList = future.get();
            List<User> list=new ArrayList<>();
            for(JsonObject jsonUser:jsonList){
                list.add(new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), "", jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString()));
            }
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetAllOrgUsersByOrganization(idOrganization,orgType,illness);
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
                Call<User> call = api.Update(email,user,Session.getInstance().getToken());
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
                Call<User> call = api.Delete(email,Session.getInstance().getToken());
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
