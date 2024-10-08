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

import cli.indicators.Ambit;
import otea.connection.ConnectionClient;
import otea.connection.api.AmbitsApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

/**
 * Controller class for ambit operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class AmbitsController {

    /**Ambits api to connect to the server*/
    private static AmbitsApi api;

    /**AmbitsController instance for singleton*/
    private static AmbitsController instance;

    private static int numAttempts=0;

    /**Class constructor*/
    private AmbitsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(AmbitsApi.class);
    }

    /**
     * Synchronized method that obtains the instance
     *
     * @return Instance of ambit
     * */
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


    /**
     * Method that obtains from the database all the ambits
     *
     * @return All ambits
     * */
    public static List<Ambit> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Ambit>> callable = new Callable<List<Ambit>>() {
            @Override
            public List<Ambit> call() throws Exception {
                Call<List<Ambit>> call = api.GetAll(Session.getInstance().getToken());
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
            numAttempts=0;
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
     * Method that obtains from the database a ambit using its identifier
     *
     * @param id - Ambit identifier
     * @return The ambit if exists, null if not.
     * */

    public static Ambit Get(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Get(id,Session.getInstance().getToken());
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

    /**
     * Method that appends a new ambit to the database
     *
     * @param ambit - Ambit to append
     * @return Ambit append, null if not
     * */
    public static Ambit Create(Ambit ambit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Create(ambit,Session.getInstance().getToken());
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

    /**
     * Method that updates the ambit by the given
     *
     * @param idAmbit - Ambit identifier
     * @param ambit - New ambit
     * @return ambit if was updated, null if not
     *
     * */
    public static Ambit Update(int idAmbit, Ambit ambit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Update(idAmbit,ambit,Session.getInstance().getToken());
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

    /**
     * Method that deletes the ambit using its identifier
     *
     * @param id - Ambit identifier
     * @return Deleted ambit if deletion was succesful, null if not
     * */
    public static Ambit Delete(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Ambit> callable = new Callable<Ambit>() {
            @Override
            public Ambit call() throws Exception {
                Call<Ambit> call = api.Delete(id,Session.getInstance().getToken());
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