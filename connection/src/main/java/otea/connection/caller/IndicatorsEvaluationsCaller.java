package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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
            List<IndicatorsEvaluation> list= asyncTask.get();
            List<IndicatorsEvaluation> result=asyncTask.get();
            for(IndicatorsEvaluation indicatorsEvaluation:list){
                result.add(new IndicatorsEvaluation(indicatorsEvaluation.getEvaluationDate(),indicatorsEvaluation.getIdEvaluatedOrganization(),indicatorsEvaluation.getOrgTypeEvaluated(),indicatorsEvaluation.getIdEvaluatorTeam(),indicatorsEvaluation.getIdEvaluatorTeam(),indicatorsEvaluation.getOrgTypeEvaluator(),indicatorsEvaluation.getIllness(),indicatorsEvaluation.getTotalScore()));
            }
            return result;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<IndicatorsEvaluation> GetAllByEvaluatorTeam(EvaluatorTeam evaluatorTeam){
        Call<List<IndicatorsEvaluation>> call=api.GetAllByEvaluatorTeam(evaluatorTeam.getIdEvaluatorTeam(),evaluatorTeam.getIdOrganization(),evaluatorTeam.getOrgType(),evaluatorTeam.getIllness());
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

    
    public static IndicatorsEvaluation Get(Timestamp evaluation_date,int idEvaluatedOrganization,String orgTypeEvaluated,String illness){
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
            return new IndicatorsEvaluation(indicatorsEvaluation.getEvaluationDate(),indicatorsEvaluation.getIdEvaluatedOrganization(),indicatorsEvaluation.getOrgTypeEvaluated(),indicatorsEvaluation.getIdEvaluatorTeam(),indicatorsEvaluation.getIdEvaluatorTeam(),indicatorsEvaluation.getOrgTypeEvaluator(),indicatorsEvaluation.getIllness(),indicatorsEvaluation.getTotalScore());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    
    public static IndicatorsEvaluation Create(IndicatorsEvaluation indicatorsEvaluation){
        Call<IndicatorsEvaluation> call=api.Create(indicatorsEvaluation);
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
    
    public static IndicatorsEvaluation Update(Timestamp evaluation_date,int idEvaluatedOrganization,String orgTypeEvaluated,String illness,IndicatorsEvaluation indicatorsEvaluation){
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

    
    public static IndicatorsEvaluation Delete(Timestamp evaluation_date,int idEvaluatedOrganization,String orgTypeEvaluated,String illness){
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
