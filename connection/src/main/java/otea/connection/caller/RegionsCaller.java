package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.organization.data.geo.Region;
import cli.organization.data.geo.Region;
import otea.connection.ConnectionClient;
import otea.connection.api.RegionsApi;
import retrofit2.Call;
import retrofit2.Response;

public class RegionsCaller {

    private static RegionsApi api;

    private static RegionsCaller instance;

    private RegionsCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(RegionsApi.class);
    }

    public static RegionsCaller getInstance(){
        if(instance==null){
            synchronized (RegionsCaller.class){
                if(instance==null){
                    instance=new RegionsCaller();
                }
            }
        }
        return instance;
    }

    public static Region obtainRegion(int idRegion, String idCountry){
        Call<Region> call=api.GetRegion(idRegion,idCountry);
        AsyncTask<Void, Void, Region> asyncTask = new AsyncTask<Void, Void, Region>() {
            Region resultRegion = null;
            @Override
            protected Region doInBackground(Void... voids) {
                try {
                    Response<Region> response = call.execute();
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
            protected void onPostExecute(Region region) {
                resultRegion=region;
            }

        };
        asyncTask.execute();
        try {
            Region region=asyncTask.get();
            return region;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<Region> GetRegionsByCountry(String idCountry){
        Call<List<Region>> call=api.GetRegionsByCountry(idCountry);
        AsyncTask<Void, Void, List<Region>> asyncTask = new AsyncTask<Void, Void, List<Region>>() {
            List<Region> resultList = null;
            @Override
            protected List<Region> doInBackground(Void... voids) {
                try {
                    Response<List<Region>> response = call.execute();
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
            protected void onPostExecute(List<Region> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<Region> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
}
