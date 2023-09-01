package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import otea.connection.ConnectionClient;
import otea.connection.api.TranslatorApi;
import retrofit2.Call;
import retrofit2.Response;

public class Translator {
    private static TranslatorApi api;

    private static Translator instance;

    private Translator(){
        api= ConnectionClient.getInstance().getRetrofit().create(TranslatorApi.class);
    }

    public static Translator getInstance() {
        if (instance == null) {
            synchronized (Translator.class) {
                if (instance == null) {
                    instance = new Translator();
                }
            }
        }
        return instance;
    }

    public String translate(String text, String origin, String target){
        Call<String> call=api.translate(text,origin,target);
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            String resultString = null;
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Response<String> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(String translation) {
                resultString=translation;
            }

        };
        asyncTask.execute();
        try {
            String translation=asyncTask.get();
            return translation;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public List<String> multiLangTranslate(String text, String origin){
        Call<List<String>> call=api.multiLangTranslate(text,origin);
        AsyncTask<Void, Void, List<String>> asyncTask = new AsyncTask<Void, Void, List<String>>() {
            List<String> resultList = null;
            @Override
            protected List<String> doInBackground(Void... voids) {
                try {
                    Response<List<String>> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(List<String> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<String> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

}
