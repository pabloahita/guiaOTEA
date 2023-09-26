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

import cli.indicators.Evidence;
import otea.connection.ConnectionClient;
import otea.connection.api.EvidencesApi;
import retrofit2.Call;
import retrofit2.Response;

public class EvidencesController {

    private static EvidencesApi api;

    private static EvidencesController instance;

    private EvidencesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(EvidencesApi.class);
    }

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





    public static List<Evidence> GetAllByIndicator(int idIndicator, String indicatorType,int idAmbit, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Evidence>> callable = new Callable<List<Evidence>>() {
            @Override
            public List<Evidence> call() throws Exception {
                Call<List<Evidence>> call = api.GetAllByIndicator(idIndicator, indicatorType, idAmbit, indicatorVersion);
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

    public static Evidence Update(int idEvidence, int idIndicator, String indicatorType, int idAmbit, int indicatorVersion, Evidence evidence) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Evidence> callable = new Callable<Evidence>() {
            @Override
            public Evidence call() throws Exception {
                Call<Evidence> call = api.Update(idEvidence,idIndicator,indicatorType,idAmbit,indicatorVersion,evidence);
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

    public static Evidence Delete(int idEvidence, int idIndicator, String indicatorType, int idAmbit, int indicatorVersion) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Evidence> callable = new Callable<Evidence>() {
            @Override
            public Evidence call() throws Exception {
                Call<Evidence> call = api.Delete(idEvidence,idIndicator,indicatorType,idAmbit,indicatorVersion);
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
