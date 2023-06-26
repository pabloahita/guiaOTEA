package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.organization.data.geo.Province;
import cli.organization.data.geo.Province;
import otea.connection.ConnectionClient;
import otea.connection.api.ProvincesApi;
import retrofit2.Call;
import retrofit2.Response;

public class ProvincesCaller {

    private static ProvincesApi api;

    private static ProvincesCaller instance;

    private ProvincesCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(ProvincesApi.class);
    }

    public static ProvincesCaller getInstance(){
        if(instance==null){
            synchronized (ProvincesCaller.class){
                if(instance==null){
                    instance=new ProvincesCaller();
                }
            }
        }
        return instance;
    }


    public static Province obtainProvince(int idProvince, int idRegion, String idCountry){
        Call<Province> call=api.GetProvince(idProvince,idRegion,idCountry);
        AsyncTask<Void, Void, Province> asyncTask = new AsyncTask<Void, Void, Province>() {
            Province resultProvince = null;
            @Override
            protected Province doInBackground(Void... voids) {
                try {
                    Response<Province> response = call.execute();
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
            protected void onPostExecute(Province province) {
                resultProvince=province;
            }

        };
        asyncTask.execute();
        try {
            Province province=asyncTask.get();
            //Se devuelve el constructor porque este llama a la obtención de los objetos Region y Country
            return new Province(province.getIdProvince(),province.getIdRegion(),province.getIdCountry(),province.getNameProvince());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<Province> GetProvincesByRegion(int idRegion, String idCountry){
        Call<List<Province>> call=api.GetProvincesByRegion(idRegion,idCountry);
        AsyncTask<Void, Void, List<Province>> asyncTask = new AsyncTask<Void, Void, List<Province>>() {
            List<Province> resultList = null;
            @Override
            protected List<Province> doInBackground(Void... voids) {
                try {
                    Response<List<Province>> response = call.execute();
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
            protected void onPostExecute(List<Province> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<Province> list=asyncTask.get();
            List<Province> result=new LinkedList<Province>();
            for(Province province:list){
                result.add(new Province(province.getIdProvince(),province.getIdRegion(),province.getIdCountry(),province.getNameProvince()));
            }
            return result;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
}
