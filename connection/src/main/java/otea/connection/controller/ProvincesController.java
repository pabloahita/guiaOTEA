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

import cli.organization.data.geo.Province;
import otea.connection.ConnectionClient;
import otea.connection.api.ProvincesApi;
import retrofit2.Call;
import retrofit2.Response;

public class ProvincesController {

    private static ProvincesApi api;

    private static ProvincesController instance;

    private ProvincesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(ProvincesApi.class);
    }

    public static ProvincesController getInstance(){
        if(instance==null){
            synchronized (ProvincesController.class){
                if(instance==null){
                    instance=new ProvincesController();
                }
            }
        }
        return instance;
    }


    public static Province GetProvince(int idProvince, int idRegion, String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Province> callable = new Callable<Province>() {
            @Override
            public Province call() throws Exception {
                Call<Province> call = api.GetProvince(idProvince,idRegion,idCountry);
                Response<Province> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Province> future = executor.submit(callable);
            Province result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Province> GetProvincesByRegion(int idRegion, String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Province>> callable = new Callable<List<Province>>() {
            @Override
            public List<Province> call() throws Exception {
                Call<List<Province>> call=api.GetProvincesByRegion(idRegion,idCountry);
                Response<List<Province>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Province>> future = executor.submit(callable);
            List<Province> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
