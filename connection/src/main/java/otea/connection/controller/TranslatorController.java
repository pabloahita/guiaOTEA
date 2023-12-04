package otea.connection.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.ResponseBody;
import otea.connection.ConnectionClient;
import otea.connection.api.TranslatorApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller class for translation operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class TranslatorController {

    /**Translation api to connect to the server*/
    private static TranslatorApi api;

    /**Controller instance*/
    private static TranslatorController instance;

    /**Class controller*/
    private TranslatorController(){
        api= ConnectionClient.getInstance().getRetrofit().create(TranslatorApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static TranslatorController getInstance() {
        if (instance == null) {
            synchronized (TranslatorController.class) {
                if (instance == null) {
                    instance = new TranslatorController();
                }
            }
        }
        return instance;
    }

    /**
     * Method that translates a text from an origin language to a target language
     *
     * @param text - Text to translate
     * @param origin - Origin language
     * @param target - Target language
     * @return Translated text
     * */
    public String translate(String text, String origin, String target){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Call<ResponseBody> call = api.translate(text, origin, target);
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<String> future = executor.submit(callable);
            String result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
