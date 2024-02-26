package otea.connection.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.Evidence;
import otea.connection.ConnectionClient;
import otea.connection.api.EvidencesApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Controller class for evidences operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class EvidencesController {

    /**Evidences api to connect to the server*/
    private static EvidencesApi api;

    /**Evidence controller instance*/
    private static EvidencesController instance;

    /**Class controller*/
    private EvidencesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(EvidencesApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static EvidencesController getInstance(){
        if(instance==null){
            synchronized (EvidencesController.class){
                if(instance==null){
                    instance=new EvidencesController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new EvidencesController();
    }

    /**
     * Method that obtains all evidences from the database
     *
     * @return Evidences list
     * */
    public static List<Evidence> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Evidence>> callable = new Callable<List<Evidence>>() {
            @Override
            public List<Evidence> call() throws Exception {
                Call<List<Evidence>> call = api.GetAll();
                Response<List<Evidence>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Evidence>> future = executor.submit(callable);
            List<Evidence> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method that obtains from the database all the evidences of an indicator
     *
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @return Evidences list
     * */
    public static List<Evidence> GetAllByIndicator(int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Evidence>> callable = new Callable<List<Evidence>>() {
            @Override
            public List<Evidence> call() throws Exception {
                Call<List<Evidence>> call = api.GetAllByIndicator(idIndicator, indicatorType, idSubSubAmbit, idSubAmbit, idAmbit, indicatorVersion);
                Response<List<Evidence>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Evidence>> future = executor.submit(callable);
            List<Evidence> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains from the database an evidence
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @return Evidence if success, null if not
     * */
    public static Evidence Get(int idEvidence, int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion){
    ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Evidence> callable = new Callable<Evidence>() {
            @Override
            public Evidence call() throws Exception {
                Call<Evidence> call = api.Get(idEvidence,idIndicator,indicatorType,idSubSubAmbit,idSubAmbit,idAmbit,indicatorVersion);
                Response<Evidence> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Evidence> future = executor.submit(callable);
            Evidence result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a new evidence to the database
     *
     * @param evidence - Evidence
     * @return Evidence if sucess, null if not
     * */
    public static Evidence Create(Evidence evidence){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Evidence> callable = new Callable<Evidence>() {
            @Override
            public Evidence call() throws Exception {
                Call<Evidence> call = api.Create(evidence);
                Response<Evidence> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Evidence> future = executor.submit(callable);
            Evidence result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates an existant evidence
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @param evidence - Evidence
     * @return Updated evidence if success, null if not
     * */
    public static Evidence Update(int idEvidence, int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion, Evidence evidence) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Evidence> callable = new Callable<Evidence>() {
            @Override
            public Evidence call() throws Exception {
                Call<Evidence> call = api.Update(idEvidence,idIndicator,indicatorType,idSubSubAmbit,idSubAmbit,idAmbit,indicatorVersion,evidence);
                Response<Evidence> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Evidence> future = executor.submit(callable);
            Evidence result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes an evidence
     *
     * @param idEvidence - Evidence identifier
     * @param idIndicator - Indicator identifier
     * @param indicatorType - Indicator type
     * @param idSubSubAmbit - Second level division of the ambit
     * @param idSubAmbit  - First level division of the ambit
     * @param idAmbit - Ambit identifier
     * @param indicatorVersion - Indicator version
     * @return Deleted evidence if success, null if not
     * */
    public static Evidence Delete(int idEvidence, int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Evidence> callable = new Callable<Evidence>() {
            @Override
            public Evidence call() throws Exception {
                Call<Evidence> call = api.Delete(idEvidence,idIndicator,indicatorType,idSubSubAmbit,idSubAmbit,idAmbit,indicatorVersion);
                Response<Evidence> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Evidence> future = executor.submit(callable);
            Evidence result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
