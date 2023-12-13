package otea.connection.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.SubAmbit;
import otea.connection.ConnectionClient;
import otea.connection.api.SubAmbitsApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller class for subAmbit operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class SubAmbitsController {

    /**SubAmbits api to connect to the server*/
    private static SubAmbitsApi api;

    /**SubAmbitsController instance for singleton*/
    private static SubAmbitsController instance;

    /**Class constructor*/
    private SubAmbitsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(SubAmbitsApi.class);
    }

    /**
     * Synchronized method that obtains the instance
     *
     * @return Instance of subAmbit
     * */
    public static SubAmbitsController getInstance(){
        if(instance==null){
            synchronized (AmbitsController.class){
                if(instance==null){
                    instance=new SubAmbitsController();
                }
            }
        }
        return instance;
    }

    /**
     * Method that obtains from the database all the subAmbits
     *
     * @return All subAmbits
     * */
    public static List<SubAmbit> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<SubAmbit>> callable = new Callable<List<SubAmbit>>() {
            @Override
            public List<SubAmbit> call() throws Exception {
                Call<List<SubAmbit>> call = api.GetAll();
                Response<List<SubAmbit>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<SubAmbit>> future = executor.submit(callable);
            List<SubAmbit> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains from the database all the subAmbits of an ambit
     *
     * @param idAmbit - Ambit identifier
     * @return All subAmbits
     * */
    public static List<SubAmbit> GetAllByAmbit(int idAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<SubAmbit>> callable = new Callable<List<SubAmbit>>() {
            @Override
            public List<SubAmbit> call() throws Exception {
                Call<List<SubAmbit>> call = api.GetAllByAmbit(idAmbit);
                Response<List<SubAmbit>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<SubAmbit>> future = executor.submit(callable);
            List<SubAmbit> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains from the database a subAmbit using its identifier
     *
     * @param id - subAmbit identifier
     * @return The subAmbit if exists, null if not.
     * */

    public static SubAmbit Get(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubAmbit> callable = new Callable<SubAmbit>() {
            @Override
            public SubAmbit call() throws Exception {
                Call<SubAmbit> call = api.Get(id);
                Response<SubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubAmbit> future = executor.submit(callable);
            SubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a new subAmbit to the database
     *
     * @param subAmbit - SubAmbit to append
     * @return SubAmbit append, null if not
     * */
    public static SubAmbit Create(SubAmbit subAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubAmbit> callable = new Callable<SubAmbit>() {
            @Override
            public SubAmbit call() throws Exception {
                Call<SubAmbit> call = api.Create(subAmbit);
                Response<SubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubAmbit> future = executor.submit(callable);
            SubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates the subAmbit
     *
     * @param idSubAmbit - SubAmbit identifier
     * @param subAmbit - new subAmbit
     * @return subAmbit if was updated, null if not
     *
     * */
    public static SubAmbit Update(int idSubAmbit, SubAmbit subAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubAmbit> callable = new Callable<SubAmbit>() {
            @Override
            public SubAmbit call() throws Exception {
                Call<SubAmbit> call = api.Update(idSubAmbit,subAmbit);
                Response<SubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubAmbit> future = executor.submit(callable);
            SubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes the subAmbit using its identifier
     *
     * @param id - SubAmbit identifier
     * @return Deleted subAmbit if deletion was successful, null if not
     * */
    public static SubAmbit Delete(int id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<SubAmbit> callable = new Callable<SubAmbit>() {
            @Override
            public SubAmbit call() throws Exception {
                Call<SubAmbit> call = api.Delete(id);
                Response<SubAmbit> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<SubAmbit> future = executor.submit(callable);
            SubAmbit result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

