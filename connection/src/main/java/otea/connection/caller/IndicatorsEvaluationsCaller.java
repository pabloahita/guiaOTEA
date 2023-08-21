package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.data.EvaluatorTeam;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationsApi;
import retrofit2.Call;
import retrofit2.Response;

public class IndicatorsEvaluationsCaller {

    private static IndicatorsEvaluationsApi api;

    private static IndicatorsEvaluationsCaller instance;

    private IndicatorsEvaluationsCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationsApi.class);
    }

    public static IndicatorsEvaluationsCaller getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationsCaller.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationsCaller();
                }
            }
        }
        return instance;
    }

    
    public static List<IndicatorsEvaluation> GetAll(){
        Call<List<IndicatorsEvaluation>> call=api.GetAll();
        AsyncTask<Void, Void, List<IndicatorsEvaluation>> asyncTask = new AsyncTask<Void, Void, List<IndicatorsEvaluation>>() {
            List<IndicatorsEvaluation> resultList = null;
            @Override
            protected List<IndicatorsEvaluation> doInBackground(Void... voids) {
                try {
                    Response<List<IndicatorsEvaluation>> response = call.execute();
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
            protected void onPostExecute(List<IndicatorsEvaluation> list) {
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

    public static List<IndicatorsEvaluation> GetAllByEvaluatedOrganization(int idOrganization, String orgType, String illness){
        AsyncTask<Void, Void, List<IndicatorsEvaluation>> asyncTask = new AsyncTask<Void, Void, List<IndicatorsEvaluation>>() {
            List<IndicatorsEvaluation> resultList = null;
            @Override
            protected List<IndicatorsEvaluation> doInBackground(Void... voids) {
                try {
                    Response<List<IndicatorsEvaluation>> response = api.GetAllByEvaluatedOrganization(idOrganization,orgType,illness).execute();
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
            protected void onPostExecute(List<IndicatorsEvaluation> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<IndicatorsEvaluation> GetAllByEvaluatorOrganization(int idOrganization, String orgType, String illness){
        AsyncTask<Void, Void, List<IndicatorsEvaluation>> asyncTask = new AsyncTask<Void, Void, List<IndicatorsEvaluation>>() {
            List<IndicatorsEvaluation> resultList = null;
            @Override
            protected List<IndicatorsEvaluation> doInBackground(Void... voids) {
                try {
                    Response<List<IndicatorsEvaluation>> response = api.GetAllByEvaluatorOrganization(idOrganization,orgType,illness).execute();
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
            protected void onPostExecute(List<IndicatorsEvaluation> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static IndicatorsEvaluation Get(long evaluation_date,int idEvaluatedOrganization,String orgTypeEvaluated,String illness){
        Call<IndicatorsEvaluation> call=api.Get(evaluation_date,idEvaluatedOrganization,orgTypeEvaluated,illness);
        AsyncTask<Void, Void, IndicatorsEvaluation> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluation>() {
            IndicatorsEvaluation resultIndicatorsEvaluation = null;
            @Override
            protected IndicatorsEvaluation doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluation> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluation indicatorsEvaluation) {
                resultIndicatorsEvaluation=indicatorsEvaluation;
            }

        };
        asyncTask.execute();
        try {
            IndicatorsEvaluation indicatorsEvaluation= asyncTask.get();
            return indicatorsEvaluation;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    
    public static IndicatorsEvaluation Create(IndicatorsEvaluation indicatorsEvaluation){
        AsyncTask<Void, Void, IndicatorsEvaluation> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluation>() {
            IndicatorsEvaluation resultIndicatorsEvaluation = null;
            @Override
            protected IndicatorsEvaluation doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluation> response = api.Create(indicatorsEvaluation).execute();
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
            protected void onPostExecute(IndicatorsEvaluation indicatorsEvaluation) {
                resultIndicatorsEvaluation=indicatorsEvaluation;
            }

        };
        asyncTask.execute();
        try {
            IndicatorsEvaluation evaluation=asyncTask.get();
            return evaluation;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static IndicatorsEvaluation Update(long evaluation_date,int idEvaluatedOrganization,String orgTypeEvaluated,String illness,IndicatorsEvaluation indicatorsEvaluation){
        Call<IndicatorsEvaluation> call=api.Update(evaluation_date,idEvaluatedOrganization,orgTypeEvaluated,illness,indicatorsEvaluation);
        AsyncTask<Void, Void, IndicatorsEvaluation> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluation>() {
            IndicatorsEvaluation resultIndicatorsEvaluation = null;
            @Override
            protected IndicatorsEvaluation doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluation> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluation indicatorsEvaluation) {
                resultIndicatorsEvaluation=indicatorsEvaluation;
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

    
    public static IndicatorsEvaluation Delete(long evaluation_date,int idEvaluatedOrganization,String orgTypeEvaluated,String illness){
        Call<IndicatorsEvaluation> call=api.Delete(evaluation_date,idEvaluatedOrganization,orgTypeEvaluated,illness);
        AsyncTask<Void, Void, IndicatorsEvaluation> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluation>() {
            IndicatorsEvaluation resultIndicatorsEvaluation = null;
            @Override
            protected IndicatorsEvaluation doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluation> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluation indicatorsEvaluation) {
                resultIndicatorsEvaluation=indicatorsEvaluation;
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
