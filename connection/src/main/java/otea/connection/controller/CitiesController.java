package otea.connection.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.data.geo.City;
import otea.connection.ConnectionClient;
import otea.connection.api.CitiesApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller for Cities operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class CitiesController {

    /**Cities api to connect to the server*/
    private static CitiesApi api;

    /**Controller instance*/
    private static CitiesController instance;

    /**Class constructor*/
    private CitiesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(CitiesApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static CitiesController getInstance(){
        if(instance==null){
            synchronized (CitiesController.class){
                if(instance==null){
                    instance=new CitiesController();
                }
            }
        }
        return instance;
    }
    /**Refresh API*/
    public static void refreshApi(){
        instance=new CitiesController();
    }



    /**
     * Method that obtains a city from the database
     *
     * @param idCity - City identifier
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @return City if success, null if not
     * */
    public static City GetCity(int idCity, int idProvince, int idRegion, String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<City> callable = new Callable<City>() {
            @Override
            public City call() throws Exception {
                Call<City> call = api.GetCity(idCity,idProvince,idRegion,idCountry);
                Response<City> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<City> future = executor.submit(callable);
            City result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains from the database all the cities of a province
     *
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @return Cities of the province
     * */
    public static List<City> GetCitiesByProvince(int idProvince, int idRegion, String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<City>> callable = new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                Call<List<City>> call = api.GetCitiesByProvince(idProvince,idRegion,idCountry);
                Response<List<City>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<City>> future = executor.submit(callable);
            List<City> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
