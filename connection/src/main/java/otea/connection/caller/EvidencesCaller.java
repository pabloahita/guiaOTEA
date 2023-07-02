package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cli.indicators.Evidence;
import otea.connection.ConnectionClient;
import otea.connection.api.EvidencesApi;
import otea.connection.api.EvidencesApi;
import retrofit2.Call;
import retrofit2.Response;

public class EvidencesCaller {

    private static EvidencesApi api;

    private static EvidencesCaller instance;

    private EvidencesCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(EvidencesApi.class);
    }

    public static EvidencesCaller getInstance(){
        if(instance==null){
            synchronized (EvidencesCaller.class){
                if(instance==null){
                    instance=new EvidencesCaller();
                }
            }
        }
        return instance;
    }





    public static List<Evidence> obtainEvidences(int idIndicator, String indicatorType,int indicatorVersion){
        Call<List<Evidence>> call=api.GetAllByIndicator(idIndicator,indicatorType,indicatorVersion);
        AsyncTask<Void, Void, List<Evidence>> asyncTask = new AsyncTask<Void, Void, List<Evidence>>() {
            List<Evidence> resultList = null;
            @Override
            protected List<Evidence> doInBackground(Void... voids) {
                try {
                    Response<List<Evidence>> response = call.execute();
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
            protected void onPostExecute(List<Evidence> list) {
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

    public static Evidence addEvidence(Evidence evidence){
        Call<Evidence> call=api.Create(evidence);
        AsyncTask<Void, Void, Evidence> asyncTask = new AsyncTask<Void, Void, Evidence>() {
            Evidence resultEvidence = null;
            @Override
            protected Evidence doInBackground(Void... voids) {
                try {
                    Response<Evidence> response = call.execute();
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
            protected void onPostExecute(Evidence evidence) {
                resultEvidence=evidence;
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

    public static Evidence updateEvidence(int idEvidence, int idIndicator, String indicatorType, int indicatorVersion, Evidence evidence) {
        Call<Evidence> call=api.Update(idEvidence,idIndicator,indicatorType,indicatorVersion,evidence);
        AsyncTask<Void, Void, Evidence> asyncTask = new AsyncTask<Void, Void, Evidence>() {
            Evidence resultEvidence = null;
            @Override
            protected Evidence doInBackground(Void... voids) {
                try {
                    Response<Evidence> response = call.execute();
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
            protected void onPostExecute(Evidence evidence) {
                resultEvidence=evidence;
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

    public static Evidence deleteEvidence(int idEvidence, int idIndicator, String indicatorType, int indicatorVersion) {
        Call<Evidence> call=api.Delete(idEvidence,idIndicator,indicatorType,indicatorVersion);
        AsyncTask<Void, Void, Evidence> asyncTask = new AsyncTask<Void, Void, Evidence>() {
            Evidence resultEvidence = null;
            @Override
            protected Evidence doInBackground(Void... voids) {
                try {
                    Response<Evidence> response = call.execute();
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
            protected void onPostExecute(Evidence evidence) {
                resultEvidence=evidence;
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
