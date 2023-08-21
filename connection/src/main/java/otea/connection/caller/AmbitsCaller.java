package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cli.indicators.Ambit;
import cli.organization.data.Center;
import otea.connection.ConnectionClient;
import otea.connection.api.AmbitsApi;
import retrofit2.Call;
import retrofit2.Response;

public class AmbitsCaller {

    private static AmbitsApi api;

    private static AmbitsCaller instance;

    private AmbitsCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(AmbitsApi.class);
    }

    public static AmbitsCaller getInstance(){
        if(instance==null){
            synchronized (AmbitsCaller.class){
                if(instance==null){
                    instance=new AmbitsCaller();
                }
            }
        }
        return instance;
    }

    public static List<Ambit> GetAll(){
        Call<List<Ambit>> call=api.GetAll();
        AsyncTask<Void, Void, List<Ambit>> asyncTask = new AsyncTask<Void, Void, List<Ambit>>() {
            List<Ambit> resultList = null;
            @Override
            protected List<Ambit> doInBackground(Void... voids) {
                try {
                    Response<List<Ambit>> response = call.execute();
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
            protected void onPostExecute(List<Ambit> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<Ambit> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
    
    // GET by ID AND ORGTYPE action

    public static Ambit Get(int id){
        Call<Ambit> call=api.Get(id);
        AsyncTask<Void, Void, Ambit> asyncTask = new AsyncTask<Void, Void, Ambit>() {
            Ambit result = null;
            @Override
            protected Ambit doInBackground(Void... voids) {
                try {
                    Response<Ambit> response = call.execute();
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
            protected void onPostExecute(Ambit ambit) {
                result=ambit;
            }

        };
        asyncTask.execute();
        try {
            Ambit ambit=asyncTask.get();
            return ambit;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    // POST action
    public static Ambit Create(Ambit ambit){
        Call<Ambit> call=api.Create(ambit);
        AsyncTask<Void, Void, Ambit> asyncTask = new AsyncTask<Void, Void, Ambit>() {
            Ambit result = null;
            @Override
            protected Ambit doInBackground(Void... voids) {
                try {
                    Response<Ambit> response = call.execute();
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
            protected void onPostExecute(Ambit ambit) {
                result=ambit;
            }

        };
        asyncTask.execute();
        try {
            Ambit aux=asyncTask.get();
            return aux;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;}
    
    public static Ambit Update(int idAmbit, Ambit ambit){
        Call<Ambit> call=api.Update(idAmbit,ambit);
        AsyncTask<Void, Void, Ambit> asyncTask = new AsyncTask<Void, Void, Ambit>() {
            Ambit result = null;
            @Override
            protected Ambit doInBackground(Void... voids) {
                try {
                    Response<Ambit> response = call.execute();
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
            protected void onPostExecute(Ambit ambit) {
                result=ambit;
            }

        };
        asyncTask.execute();
        try {
            Ambit aux=asyncTask.get();
            return aux;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
    
    
    public static Ambit Delete(int id){
        Call<Ambit> call=api.Delete(id);
        AsyncTask<Void, Void, Ambit> asyncTask = new AsyncTask<Void, Void, Ambit>() {
            Ambit result = null;
            @Override
            protected Ambit doInBackground(Void... voids) {
                try {
                    Response<Ambit> response = call.execute();
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
            protected void onPostExecute(Ambit ambit) {
                result=ambit;
            }

        };
        asyncTask.execute();
        try {
            Ambit ambit=asyncTask.get();
            return ambit;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
}
