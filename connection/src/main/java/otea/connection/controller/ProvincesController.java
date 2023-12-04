package otea.connection.controller;

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

/**
 * Controller class for provinces operation
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class ProvincesController {

    /**Provinces api to connect to the server*/
    private static ProvincesApi api;

    /**Controller instance*/
    private static ProvincesController instance;

    /**Class constructor*/
    private ProvincesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(ProvincesApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**
     * Method that obtains a province from the database
     *
     * @param idProvince - Province identifier
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @return Province if success, null if not
     * */
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

    /**
     * Method that obtains all provinces of a region
     *
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @return Provinces list
     * */
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
