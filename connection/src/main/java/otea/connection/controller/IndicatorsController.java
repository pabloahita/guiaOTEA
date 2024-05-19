package otea.connection.controller;


import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.Indicator;
import cli.indicators.Indicator;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Class controller for indicators operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class IndicatorsController {

    /**Indicators api to connect to the class*/
    private static IndicatorsApi api;

    /**Controller instance*/
    private static IndicatorsController instance;

    /**Class controller*/
    private IndicatorsController(){
        api=ConnectionClient.getInstance().getRetrofit().create(IndicatorsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static IndicatorsController getInstance(){
        if(instance==null){
            synchronized (IndicatorsController.class){
                if(instance==null){
                    instance=new IndicatorsController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new IndicatorsController();
    }

    /**
     * Method that obtains all the indicators of an ambit
     *
     * @param idAmbit - Ambit identifier
     * @return Indicators list
     *
     * */
    public static List<Indicator> GetAllByIdAmbit(int idAmbit){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Indicator>> callable = new Callable<List<Indicator>>() {
            @Override
            public List<Indicator> call() throws Exception {
                Call<List<Indicator>> call = api.GetAllByIdAmbit(idAmbit);
                Response<List<Indicator>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Indicator>> future = executor.submit(callable);
            List<Indicator> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method that updates an indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @param indicator - Indicator
     * @return Updated indicator if success, null if not
     * */
    public static Indicator Update(int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion, String evaluationType, Indicator indicator){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Update(idIndicator,indicatorType,idSubSubAmbit,idSubAmbit,idAmbit,indicatorVersion,evaluationType,indicator);
                Response<Indicator> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Indicator> future = executor.submit(callable);
            Indicator result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that creates an indicator
     *
     * @param indicator - Indicator
     * @return Indicator if success, null if not
     * */
    public static Indicator Create(Indicator indicator){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Create(indicator);
                Response<Indicator> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Indicator> future = executor.submit(callable);
            Indicator result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains an indicator from the database
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @return Indicator if success, null if not
     * */
    public static Indicator Get(int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Get(idIndicator,indicatorType,idSubSubAmbit,idSubAmbit,idAmbit,indicatorVersion,evaluationType);
                Response<Indicator> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Indicator> future = executor.submit(callable);
            Indicator result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains all the indicators
     *
     * @return Indicators list
     * */
    public static List<JsonObject> GetAll(String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAll(evaluationType);
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
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes an indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @return Indicator if success, null if not
     * */
    public static Indicator Delete(int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Get(idIndicator,indicatorType,idSubSubAmbit,idSubAmbit,idAmbit,indicatorVersion,evaluationType);
                Response<Indicator> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Indicator> future = executor.submit(callable);
            Indicator result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
