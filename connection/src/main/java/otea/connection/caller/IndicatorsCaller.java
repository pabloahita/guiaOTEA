package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.Indicator;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class IndicatorsCaller {

    private static IndicatorsApi api;
    private static IndicatorsCaller instance;

    private IndicatorsCaller(){
        api=ConnectionClient.getInstance().getRetrofit().create(IndicatorsApi.class);
    }

    public static IndicatorsCaller getInstance(){
        if(instance==null){
            synchronized (IndicatorsCaller.class){
                if(instance==null){
                    instance=new IndicatorsCaller();
                }
            }
        }
        return instance;
    }


    public static List<Indicator> obtainIndicators(String illness){
        Call<List<Indicator>> call=api.GetAllByType(illness);
        AsyncTask<Void, Void, List<Indicator>> asyncTask = new AsyncTask<Void, Void, List<Indicator>>() {
            List<Indicator> resultList= null;
            @Override
            protected List<Indicator> doInBackground(Void... voids) {
                try {
                    Response<List<Indicator>> response = call.execute();
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
            protected void onPostExecute(List<Indicator> indicatorList) {
                resultList=indicatorList;
            }

        };
        asyncTask.execute();
        try {
            List<Indicator> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }


    public static Indicator Update(int idIndicator, String indicatorType, int indicatorVersion, Indicator indicator){
        Call<Indicator> call=api.Update(idIndicator,indicatorType,indicatorVersion,indicator);
        AsyncTask<Void, Void, Indicator> asyncTask = new AsyncTask<Void, Void, Indicator>() {
            Indicator resultIndicator= null;
            @Override
            protected Indicator doInBackground(Void... voids) {
                try {
                    Response<Indicator> response = call.execute();
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
            protected void onPostExecute(Indicator indicator) {
                resultIndicator=indicator;
            }

        };
        asyncTask.execute();
        try {
            Indicator i=asyncTask.get();
            return new Indicator(i.getIdIndicator(),i.getIndicatorType(),i.getDescriptionEnglish(),i.getDescriptionSpanish(),i.getDescriptionFrench(),i.getPriority(),i.getIndicatorVersion());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Indicator Create(Indicator indicator){
        Call<Indicator> call=api.Create(indicator);
        AsyncTask<Void, Void, Indicator> asyncTask = new AsyncTask<Void, Void, Indicator>() {
            Indicator resultIndicator= null;
            @Override
            protected Indicator doInBackground(Void... voids) {
                try {
                    Response<Indicator> response = call.execute();
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
            protected void onPostExecute(Indicator indicator) {
                resultIndicator=indicator;
            }

        };
        asyncTask.execute();
        try {
            Indicator i=asyncTask.get();
            return new Indicator(i.getIdIndicator(),i.getIndicatorType(),i.getDescriptionEnglish(),i.getDescriptionSpanish(),i.getDescriptionFrench(),i.getPriority(),i.getIndicatorVersion());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Indicator Get(int idIndicator, String indicatorType, int indicatorVersion){
        Call<Indicator> call=api.Get(idIndicator,indicatorType,indicatorVersion);
        AsyncTask<Void, Void, Indicator> asyncTask = new AsyncTask<Void, Void, Indicator>() {
            Indicator resultIndicator= null;
            @Override
            protected Indicator doInBackground(Void... voids) {
                try {
                    Response<Indicator> response = call.execute();
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
            protected void onPostExecute(Indicator indicator) {
                resultIndicator=indicator;
            }

        };
        asyncTask.execute();
        try {
            Indicator i=asyncTask.get();
            return new Indicator(i.getIdIndicator(),i.getIndicatorType(),i.getDescriptionEnglish(),i.getDescriptionSpanish(),i.getDescriptionFrench(),i.getPriority(),i.getIndicatorVersion());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
    public static List<Indicator> GetAll(){
        Call<List<Indicator>> call=api.GetAll();
        AsyncTask<Void, Void, List<Indicator>> asyncTask = new AsyncTask<Void, Void, List<Indicator>>() {
            List<Indicator> resultList= null;
            @Override
            protected List<Indicator> doInBackground(Void... voids) {
                try {
                    Response<List<Indicator>> response = call.execute();
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
            protected void onPostExecute(List<Indicator> indicatorList) {
                resultList=indicatorList;
            }

        };
        asyncTask.execute();
        try {
            List<Indicator> aux=asyncTask.get();
            List<Indicator> list=new LinkedList<>();
            for(Indicator i:aux){//Se hace así debido a que queremos asignar también las evidencias
                list.add(new Indicator(i.getIdIndicator(),i.getIndicatorType(),i.getDescriptionEnglish(),i.getDescriptionSpanish(),i.getDescriptionFrench(),i.getPriority(),i.getIndicatorVersion()));
            }
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
    public static Indicator Delete(int idIndicator, String indicatorType, int indicatorVersion){
        Call<Indicator> call=api.Get(idIndicator,indicatorType,indicatorVersion);
        AsyncTask<Void, Void, Indicator> asyncTask = new AsyncTask<Void, Void, Indicator>() {
            Indicator resultIndicator= null;
            @Override
            protected Indicator doInBackground(Void... voids) {
                try {
                    Response<Indicator> response = call.execute();
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
            protected void onPostExecute(Indicator indicator) {
                resultIndicator=indicator;
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
