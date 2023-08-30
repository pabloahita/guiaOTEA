package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.organization.data.geo.Country;
import cli.organization.data.geo.Country;
import otea.connection.ConnectionClient;
import otea.connection.api.CountriesApi;
import retrofit2.Call;
import retrofit2.Response;

public class CountriesCaller {

    private static CountriesApi api;

    private static CountriesCaller instance;

    private CountriesCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(CountriesApi.class);
    }

    public static CountriesCaller getInstance(){
        if(instance==null){
            synchronized (CountriesCaller.class){
                if(instance==null){
                    instance=new CountriesCaller();
                }
            }
        }
        return instance;
    }

    public static Country obtainCountry(String idCountry){
        Call<Country> call=api.GetCountry(idCountry);

        AsyncTask<Void, Void, Country> asyncTask = new AsyncTask<Void, Void, Country>() {
            Country resultCountry = null;
            @Override
            protected Country doInBackground(Void... voids) {
                try {
                    Response<Country> response = call.execute();
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
            protected void onPostExecute(Country country) {
                resultCountry=country;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<Country> GetAll(String language){
        Call<List<Country>> call=api.GetAll(language);
        AsyncTask<Void, Void, List<Country>> asyncTask = new AsyncTask<Void, Void, List<Country>>() {
            List<Country> resultList = null;
            @Override
            protected List<Country> doInBackground(Void... voids) {
                try {
                    Response<List<Country>> response = call.execute();
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
            protected void onPostExecute(List<Country> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
}
