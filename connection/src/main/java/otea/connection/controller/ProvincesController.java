package otea.connection.controller;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Province;
import misc.ListCallback;
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

    /**Number of attempts*/
    private static int numAttempts=0;

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


    /**Refresh API*/
    public static void refreshApi(){
        instance=new ProvincesController();
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
     * @param idCountry - Country identifier
     * @return Provinces list
     * */
    public static void GetProvincesByRegion(int idRegion, String idCountry, ListCallback callback){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetProvincesByRegion(idRegion,idCountry, Locale.getDefault().getLanguage());
                Response<List<JsonObject>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };

        executor.submit(()->{
            try {
                List<JsonObject> result = callable.call();
                callback.onSuccess(result);
            } catch (Exception e) {
                callback.onError(e.getCause().toString());
            } finally {
                executor.shutdown();
            }
        });
        /*try {
            Future<List<JsonObject>> future = executor.submit(callable);
            List<JsonObject> result = future.get();
            executor.shutdown();
            List<Province> provinces=new ArrayList<>();
            for(JsonObject reg:result){
                int idProvince=reg.getAsJsonPrimitive("idProvince").getAsInt();
                String nameSpanish=reg.getAsJsonPrimitive("nameSpanish").getAsString();
                String nameEnglish=reg.getAsJsonPrimitive("nameEnglish").getAsString();
                String nameFrench=reg.getAsJsonPrimitive("nameFrench").getAsString();
                String nameBasque=reg.getAsJsonPrimitive("nameBasque").getAsString();
                String nameCatalan=reg.getAsJsonPrimitive("nameCatalan").getAsString();
                String nameDutch=reg.getAsJsonPrimitive("nameDutch").getAsString();
                String nameGalician=reg.getAsJsonPrimitive("nameGalician").getAsString();
                String nameGerman=reg.getAsJsonPrimitive("nameGerman").getAsString();
                String nameItalian=reg.getAsJsonPrimitive("nameItalian").getAsString();
                String namePortuguese=reg.getAsJsonPrimitive("namePortuguese").getAsString();
                provinces.add(new Province(idProvince,idRegion,idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese));
            }
            return provinces;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetProvincesByRegion(idRegion,idCountry);
                }
                else{
                    numAttempts=0;
                    return null;
                }
            }
            else{
                throw new RuntimeException(e);
            }
        }*/

    }
}
