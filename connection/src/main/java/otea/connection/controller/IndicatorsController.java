package otea.connection.controller;


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

public class IndicatorsController {

    private static IndicatorsApi api;
    private static IndicatorsController instance;

    private IndicatorsController(){
        api=ConnectionClient.getInstance().getRetrofit().create(IndicatorsApi.class);
    }

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


    public static Indicator Update(int idIndicator, String indicatorType, int idAmbit, int indicatorVersion, Indicator indicator){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Update(idIndicator,indicatorType,idAmbit,indicatorVersion,indicator);
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

    public static Indicator Get(int idIndicator, String indicatorType, int idAmbit, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Get(idIndicator,indicatorType,idAmbit,indicatorVersion);
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
    public static List<Indicator> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Indicator>> callable = new Callable<List<Indicator>>() {
            @Override
            public List<Indicator> call() throws Exception {
                Call<List<Indicator>> call = api.GetAll();
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
    public static Indicator Delete(int idIndicator, String indicatorType, int idAmbit, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Indicator> callable = new Callable<Indicator>() {
            @Override
            public Indicator call() throws Exception {
                Call<Indicator> call = api.Get(idIndicator,indicatorType,idAmbit,indicatorVersion);
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
