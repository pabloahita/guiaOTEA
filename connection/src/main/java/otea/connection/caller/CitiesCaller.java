package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.organization.data.geo.City;
import otea.connection.ConnectionClient;
import otea.connection.api.CitiesApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class CitiesCaller {

    private static CitiesApi api;

    private static CitiesCaller instance;

    private CitiesCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(CitiesApi.class);
    }

    public static CitiesCaller getInstance(){
        if(instance==null){
            synchronized (CitiesCaller.class){
                if(instance==null){
                    instance=new CitiesCaller();
                }
            }
        }
        return instance;
    }

    public static City obtainCity(int idCity, int idProvince, int idRegion, String idCountry){
        Call<City> call=api.GetCity(idCity,idProvince,idRegion,idCountry);
        AsyncTask<Void, Void, City> asyncTask = new AsyncTask<Void, Void, City>() {
            City resultCity = null;
            @Override
            protected City doInBackground(Void... voids) {
                try {
                    Response<City> response = call.execute();
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
            protected void onPostExecute(City city) {
                resultCity=city;
            }

        };
        asyncTask.execute();
        try {
            City city=asyncTask.get();
            //Se devuelve el constructor porque este llama a la obtención de los objetos Province, Region y Country
            return new City(city.getIdCity(),city.getIdProvince(),city.getIdRegion(),city.getIdCountry(),city.getCityName());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<City> GetCitiesByProvince(int idProvince, int idRegion, String idCountry){
        Call<List<City>> call=api.GetCitiesByProvince(idProvince,idRegion,idCountry);
        AsyncTask<Void, Void, List<City>> asyncTask = new AsyncTask<Void, Void, List<City>>() {
            List<City> resultList = null;
            @Override
            protected List<City> doInBackground(Void... voids) {
                try {
                    Response<List<City>> response = call.execute();
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
            protected void onPostExecute(List<City> listCity) {
                resultList=listCity;
            }

        };
        asyncTask.execute();
        try {
            List<City> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

}
