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

public class TranslatorController {
    private static TranslatorApi api;

    private static TranslatorController instance;

    private TranslatorController(){
        api= ConnectionClient.getInstance().getRetrofit().create(TranslatorApi.class);
    }

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

    public List<String> multiLangTranslate(String text, String origin){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                Call<List<String>> call=api.multiLangTranslate(text,origin);
                Response<List<String>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<String>> future = executor.submit(callable);
            List<String> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
