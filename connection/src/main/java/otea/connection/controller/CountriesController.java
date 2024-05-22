package otea.connection.controller;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.data.geo.Country;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Region;
import otea.connection.ConnectionClient;
import otea.connection.api.CountriesApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller for countries operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class CountriesController {

    /**Countries api used to connect to the server*/
    private static CountriesApi api;

    /**Controller instance*/
    private static CountriesController instance;

    /**Class constructor*/
    private CountriesController(){
        api= ConnectionClient.getInstance().getRetrofit().create(CountriesApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**Refresh API*/
    public static void refreshApi(){
        instance=new CountriesController();
    }

    /**
     * Method that obtains the country from the database
     *
     * @param idCountry - Country identifier
     * @return Country if exists, null if not
     * */
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

    /**
     * Method that obtains all the countries in language order
     *
     * @param language - App language (english, spanish, french, basque, catalan, dutch, galician, german, italian and portuguese)
     * @return Country list
     * */
    public static List<Country> GetAll(String language){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAll(language);
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
            List<Country> countries=new ArrayList<>();
            for(JsonObject reg:list){
                String idCountry=reg.getAsJsonPrimitive("idCountry").getAsString();
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
                String phone_code=reg.getAsJsonPrimitive("phone_code").getAsString();
                String flag=reg.getAsJsonPrimitive("flag").getAsString();
                countries.add(new Country(idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese,phone_code,flag));
            }
            return countries;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
