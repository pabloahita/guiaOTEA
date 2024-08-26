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

    /**Number of attempts*/
    private static int numAttempts=0;

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
            numAttempts=0;
            List<Country> countries=new ArrayList<>();
            for(JsonObject reg:list){
                String idCountry=reg.getAsJsonPrimitive("idCountry").getAsString();
                String name=reg.getAsJsonPrimitive("name").getAsString();
                String nameSpanish = "";
                String nameEnglish = "";
                String nameFrench = "";
                String nameBasque = "";
                String nameCatalan = "";
                String nameDutch = "";
                String nameGalician = "";
                String nameGerman = "";
                String nameItalian = "";
                String namePortuguese = "";
                if(Locale.getDefault().getLanguage().equals("es")){
                    nameSpanish=name;
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    nameFrench=name;
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    nameBasque=name;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    nameCatalan=name;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    nameDutch=name;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    nameGalician=name;
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    nameGerman=name;
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    nameItalian=name;
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    namePortuguese=name;
                }else{
                    nameEnglish=name;
                }
                String phone_code=reg.getAsJsonPrimitive("phone_code").getAsString();
                String flag=reg.getAsJsonPrimitive("flag").getAsString();
                countries.add(new Country(idCountry,nameSpanish,nameEnglish,nameFrench,nameBasque,nameCatalan,nameDutch,nameGalician,nameGerman,nameItalian,namePortuguese,phone_code,flag));
            }
            return countries;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetAll(language);
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

}
