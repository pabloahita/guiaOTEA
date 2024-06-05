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

import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;
import cli.organization.data.geo.Region;
import otea.connection.ConnectionClient;
import otea.connection.api.RegionsApi;
import retrofit2.Call;
import retrofit2.Response;

public class RegionsController {

    /**Regions api to connect to the server*/
    private static RegionsApi api;

    /**Controller instance*/
    private static RegionsController instance;

    /**Number of attempts*/
    private static int numAttempts=0;

    /**Class constructor*/
    private RegionsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(RegionsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static RegionsController getInstance(){
        if(instance==null){
            synchronized (RegionsController.class){
                if(instance==null){
                    instance=new RegionsController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new RegionsController();
    }

    /**
     * Method that obtains a region from the database
     *
     * @param idRegion - Region identifier
     * @param idCountry - Country identifier
     * @return Region if success, null if not
     * */
    public static Region GetRegion(int idRegion, String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Region> callable = new Callable<Region>() {
            @Override
            public Region call() throws Exception {
                Call<Region> call = api.GetRegion(idRegion,idCountry);
                Response<Region> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Region> future = executor.submit(callable);
            Region result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains all regions of a country
     *
     * @param idCountry - Country identifier
     * @return Regions list
     * */
    public static List<Region> GetRegionsByCountry(String idCountry){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call=api.GetRegionsByCountry(idCountry, Locale.getDefault().getLanguage());
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
            List<Region> regions=new ArrayList<>();
            for(JsonObject reg:list){
                int idRegion=reg.getAsJsonPrimitive("idRegion").getAsInt();
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
                regions.add(new Region(idRegion,idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese));
            }
            return regions;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetRegionsByCountry(idCountry);
                }
                else{
                    numAttempts=0;
                    return null;
                }
            }
            else{
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method that obtains from the database all the regions
     * @return Region list
     * */
    public static List<Region> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Region>> callable = new Callable<List<Region>>() {
            @Override
            public List<Region> call() throws Exception {
                Call<List<Region>> call = api.GetAll();
                Response<List<Region>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Region>> future = executor.submit(callable);
            List<Region> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
