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

import cli.organization.data.geo.Country;
import cli.organization.data.geo.Country;
import otea.connection.ConnectionClient;
import otea.connection.api.CountriesApi;
import retrofit2.Call;
import retrofit2.Response;

public class CountriesController {

    private static CountriesApi api;

    private static CountriesController instance;

    private CountriesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(CountriesApi.class);
    }

    public static CountriesController getInstance(){
        if(instance==null){
            synchronized (CountriesController.class){
                if(instance==null){
                    instance=new CountriesController();
                }
            }
        }
        return instance;
    }

    public static Country GetCountry(String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Country> callable = new Callable<Country>() {
            @Override
            public Country call() throws Exception {
                Call<Country> call = api.GetCountry(idCountry);
                Response<Country> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Country> future = executor.submit(callable);
            Country result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Country> GetAll(String language){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Country>> callable = new Callable<List<Country>>() {
            @Override
            public List<Country> call() throws Exception {
                Call<List<Country>> call = api.GetAll(language);
                Response<List<Country>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Country>> future = executor.submit(callable);
            List<Country> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
