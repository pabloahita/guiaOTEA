package otea.connection.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.SubSubAmbit;
import otea.connection.ConnectionClient;
import otea.connection.api.SubSubAmbitsApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

/**
 * Controller class for subSubAmbit operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class SubSubAmbitsController {

    /**SubSubAmbits api to connect to the server*/
    private static SubSubAmbitsApi api;

    /**SubSubAmbitsController instance for singleton*/
    private static SubSubAmbitsController instance;

    /**Class constructor*/
    private SubSubAmbitsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(SubSubAmbitsApi.class);
    }

    /**
     * Synchronized method that obtains the instance
     *
     * @return Instance of subSubAmbit
     * */
    public static SubSubAmbitsController getInstance(){
        if(instance==null){
            synchronized (AmbitsController.class){
                if(instance==null){
                    instance=new SubSubAmbitsController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new SubSubAmbitsController();
    }

    /**
     * Method that obtains from the database all the subSubAmbits
     *
     * @return All subSubAmbits
     * */
    public static List<SubSubAmbit> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<SubSubAmbit>> callable = new Callable<List<SubSubAmbit>>() {
            @Override
            public List<SubSubAmbit> call() throws Exception {
                Call<List<SubSubAmbit>> call = api.GetAll(Session.getInstance().getToken());
                Response<List<SubSubAmbit>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<SubSubAmbit>> future = executor.submit(callable);
            List<SubSubAmbit> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains from the database all the subSubAmbits
     *
     * @param idSubAmbit - subAmbit identifier
     * @param idAmbit - Ambit identifier
     * @return All subSubAmbits
     * */
    public static List<SubSubAmbit> GetAllBySubAmbit(int idSubAmbit,int idAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<SubSubAmbit>> callable = new Callable<List<SubSubAmbit>>() {
            @Override
            public List<SubSubAmbit> call() throws Exception {
                Call<List<SubSubAmbit>> call = api.GetAllBySubAmbit(idSubAmbit, idAmbit, Session.getInstance().getToken());
                Response<List<SubSubAmbit>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<SubSubAmbit>> future = executor.submit(callable);
            List<SubSubAmbit> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains from the database a subSubAmbit using its identifier
     *
     * @param id - subSubAmbit identifier
     * @return The subSubAmbit if exists, null if not.
     * */

    public static SubSubAmbit Get(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubSubAmbit> callable = new Callable<SubSubAmbit>() {
            @Override
            public SubSubAmbit call() throws Exception {
                Call<SubSubAmbit> call = api.Get(id,Session.getInstance().getToken());
                Response<SubSubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubSubAmbit> future = executor.submit(callable);
            SubSubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a new subSubAmbit to the database
     *
     * @param subSubAmbit - SubSubAmbit to append
     * @return SubSubAmbit append, null if not
     * */
    public static SubSubAmbit Create(SubSubAmbit subSubAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubSubAmbit> callable = new Callable<SubSubAmbit>() {
            @Override
            public SubSubAmbit call() throws Exception {
                Call<SubSubAmbit> call = api.Create(subSubAmbit,Session.getInstance().getToken());
                Response<SubSubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubSubAmbit> future = executor.submit(callable);
            SubSubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates the subSubAmbit by the given
     *
     * @param idSubSubAmbit - SubSubAmbit identifier
     * @param subSubAmbit - new subSubAmbit
     * @return subSubAmbit if was updated, null if not
     *
     * */
    public static SubSubAmbit Update(int idSubSubAmbit, SubSubAmbit subSubAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubSubAmbit> callable = new Callable<SubSubAmbit>() {
            @Override
            public SubSubAmbit call() throws Exception {
                Call<SubSubAmbit> call = api.Update(idSubSubAmbit,subSubAmbit,Session.getInstance().getToken());
                Response<SubSubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubSubAmbit> future = executor.submit(callable);
            SubSubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes the subSubAmbit using its
     *
     * @param id - SubSubAmbit identifier
     * @return Deleted subSubAmbit if deletion was succesful, null if not
     * */
    public static SubSubAmbit Delete(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubSubAmbit> callable = new Callable<SubSubAmbit>() {
            @Override
            public SubSubAmbit call() throws Exception {
                Call<SubSubAmbit> call = api.Delete(id,Session.getInstance().getToken());
                Response<SubSubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubSubAmbit> future = executor.submit(callable);
            SubSubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
