package otea.connection.controller;


import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
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

/**
 * Controller class for requests operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class RequestsController {

    /**Controller instance*/
    private static RequestsController instance;

    /**Requests api to connect to the server*/
    private static RequestsApi api;

    /**Class constructor*/
    private RequestsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(RequestsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**Refresh API*/
    public static void refreshApi(){
        instance=new RequestsController();
    }

    public interface RequestCallback {
        void onSuccess(JsonObject data);
        void onError(JsonObject errorResponse);
    }

    /**
     * Method that obtains all requests
     *
     * @return Request list
     * */
    public static List<Request> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call=api.GetAll();
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
            List<Request> requests=new ArrayList<>();
            for(JsonObject req:list){
                String email=req.getAsJsonPrimitive("email").getAsString();
                int statusReq=req.getAsJsonPrimitive("statusReq").getAsInt();
                int idOrganization=req.getAsJsonPrimitive("idOrganization").getAsInt();
                String orgType=req.getAsJsonPrimitive("orgType").getAsString();
                String illness=req.getAsJsonPrimitive("illness").getAsString();
                String userType=req.getAsJsonPrimitive("userType").getAsString();
                requests.add(new Request(email, statusReq, "-", idOrganization, orgType, illness, userType));
            }
            return requests;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains a request from the database
     *
     * @param email - Email of the register request
     * @return Request if success, null if not
     * */
    public static Request Get(String email){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<JsonObject> callable = new Callable<JsonObject>() {
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
                    throw new IOException("Error: " + response.code() + " " + response.message()+ " ");
                }
            }
        };
        try {
            Future<JsonObject> future = executor.submit(callable);
            JsonObject result = future.get();
            executor.shutdown();
            int statusReq=result.getAsJsonPrimitive("statusReq").getAsInt();
            int idOrganization=result.getAsJsonPrimitive("idOrganization").getAsInt();
            String orgType=result.getAsJsonPrimitive("orgType").getAsString();
            String illness=result.getAsJsonPrimitive("illness").getAsString();
            String userType=result.getAsJsonPrimitive("userType").getAsString();
            return new Request(email, statusReq, "-", idOrganization, orgType, illness, userType);
        } catch (InterruptedException | ExecutionException e) {
            if(e.getMessage().equals("java.io.IOException: Not found")){
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a request to the database
     *
     * @param request - Request
     * @return Request if success, null if not
     * */
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

    /**
     * Method that updates a request
     *
     * @param email - Email of the register request
     * @param request - Request
     * @return Request if success, null if not
     * */
    public static void goToUserReg(JsonObject credentials, RequestCallback callback){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<JsonObject> callable = new Callable<JsonObject>() {
            @Override
            public JsonObject call() throws Exception {
                Call<JsonObject> call = api.goToUserReg(credentials);
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
                    throw new RuntimeException(e);
                }
                callback.onError(errorResponse);
            } finally {
                executor.shutdown();
            }
        });
    }


    /**
     * Method that updates a request
     *
     * @param email - Email of the register request
     * @param request - Request
     * @return Request if success, null if not
     * */
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

    /**
     * Method that deletes a request
     *
     * @param email - Email of the register request
     * @return Request if success, null if not
     * */
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
