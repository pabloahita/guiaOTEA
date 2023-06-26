package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluationReg;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationRegsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class IndicatorsEvaluationRegsCaller {
    private static IndicatorsEvaluationRegsApi api;

    private static IndicatorsEvaluationRegsCaller instance;

    private IndicatorsEvaluationRegsCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationRegsApi.class);
    }

    public static IndicatorsEvaluationRegsCaller getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationRegsCaller.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationRegsCaller();
                }
            }
        }
        return instance;
    }
    
    
    public static List<IndicatorsEvaluationReg> GetAll(){
        Call<List<IndicatorsEvaluationReg>> call=api.GetAll();
        AsyncTask<Void, Void, List<IndicatorsEvaluationReg>> asyncTask = new AsyncTask<Void, Void, List<IndicatorsEvaluationReg>>() {
            List<IndicatorsEvaluationReg> resultList= null;
            @Override
            protected List<IndicatorsEvaluationReg> doInBackground(Void... voids) {
                try {
                    Response<List<IndicatorsEvaluationReg>> response = call.execute();
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
            protected void onPostExecute(List<IndicatorsEvaluationReg> indicatorList) {
                resultList=indicatorList;
            }

        };
        asyncTask.execute();
        try {
            List<IndicatorsEvaluationReg> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<IndicatorsEvaluationReg> GetAllByIndicatorsEvaluation(Timestamp evaluationDate, int idEvaluatorOrganization, String orgType, String illness){

        Call<List<IndicatorsEvaluationReg>> call=api.GetAllByIndicatorsEvaluation(evaluationDate,idEvaluatorOrganization,orgType,illness);
        AsyncTask<Void, Void, List<IndicatorsEvaluationReg>> asyncTask = new AsyncTask<Void, Void, List<IndicatorsEvaluationReg>>() {
            List<IndicatorsEvaluationReg> resultList= null;
            @Override
            protected List<IndicatorsEvaluationReg> doInBackground(Void... voids) {
                try {
                    Response<List<IndicatorsEvaluationReg>> response = call.execute();
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
            protected void onPostExecute(List<IndicatorsEvaluationReg> indicatorList) {
                resultList=indicatorList;
            }

        };
        asyncTask.execute();
        try {
            List<IndicatorsEvaluationReg> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static IndicatorsEvaluationReg Get(Timestamp evaluationDate, int idEvaluatedOrganization, String orgType,String illness, int idIndicator, int idEvidence, int indicatorVersion){
        Call<IndicatorsEvaluationReg> call=api.Get(evaluationDate,idEvaluatedOrganization,orgType,illness,idIndicator,idEvidence,indicatorVersion);
        AsyncTask<Void, Void, IndicatorsEvaluationReg> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluationReg>() {
            IndicatorsEvaluationReg result= null;
            @Override
            protected IndicatorsEvaluationReg doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluationReg> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluationReg indicatorsEvaluationReg) {
                result=indicatorsEvaluationReg;
            }

        };
        asyncTask.execute();
        try {
            IndicatorsEvaluationReg reg=asyncTask.get();
            return reg;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static IndicatorsEvaluationReg Create(Timestamp evaluationDate, int idEvaluatedOrganization,String orgType,String illness,int indicatorId,int idEvidence,int isMarked, int indicatorVersion){
        Call<IndicatorsEvaluationReg> call=api.Create(evaluationDate,idEvaluatedOrganization,orgType,illness,indicatorId,idEvidence,isMarked,indicatorVersion);
        AsyncTask<Void, Void, IndicatorsEvaluationReg> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluationReg>() {
            IndicatorsEvaluationReg result= null;
            @Override
            protected IndicatorsEvaluationReg doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluationReg> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluationReg indicatorsEvaluationReg) {
                result=indicatorsEvaluationReg;
            }

        };
        asyncTask.execute();
        try {
            IndicatorsEvaluationReg reg=asyncTask.get();
            return reg;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static IndicatorsEvaluationReg Update(Timestamp evaluationDate, int idEvaluatedOrganization,String orgType,String illness, int idIndicator, int idEvidence, int indicatorVersion){
        Call<IndicatorsEvaluationReg> call=api.Update(evaluationDate,idEvaluatedOrganization,orgType,illness,idIndicator,idEvidence,indicatorVersion);
        AsyncTask<Void, Void, IndicatorsEvaluationReg> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluationReg>() {
            IndicatorsEvaluationReg result= null;
            @Override
            protected IndicatorsEvaluationReg doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluationReg> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluationReg indicatorsEvaluationReg) {
                result=indicatorsEvaluationReg;
            }

        };
        asyncTask.execute();
        try {
            IndicatorsEvaluationReg reg=asyncTask.get();
            return reg;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static IndicatorsEvaluationReg Delete(Timestamp evaluationDate, int idEvaluatedOrganization, String orgType,String illness, int idIndicator, int idEvidence, int indicatorVersion){
        Call<IndicatorsEvaluationReg> call=api.Delete(evaluationDate,idEvaluatedOrganization,orgType,illness,idIndicator,idEvidence,indicatorVersion);
        AsyncTask<Void, Void, IndicatorsEvaluationReg> asyncTask = new AsyncTask<Void, Void, IndicatorsEvaluationReg>() {
            IndicatorsEvaluationReg result= null;
            @Override
            protected IndicatorsEvaluationReg doInBackground(Void... voids) {
                try {
                    Response<IndicatorsEvaluationReg> response = call.execute();
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
            protected void onPostExecute(IndicatorsEvaluationReg indicatorsEvaluationReg) {
                result=indicatorsEvaluationReg;
            }

        };
        asyncTask.execute();
        try {
            IndicatorsEvaluationReg reg=asyncTask.get();
            return reg;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

}
