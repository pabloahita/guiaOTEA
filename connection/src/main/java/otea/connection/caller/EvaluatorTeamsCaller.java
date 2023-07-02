package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import cli.organization.data.EvaluatorTeam;
import otea.connection.ConnectionClient;
import otea.connection.api.EvaluatorTeamsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class EvaluatorTeamsCaller {

    private static EvaluatorTeamsApi api;

    private static EvaluatorTeamsCaller instance;

    private EvaluatorTeamsCaller() {
        api = ConnectionClient.getInstance().getRetrofit().create(EvaluatorTeamsApi.class);
    }

    public static EvaluatorTeamsCaller getInstance(){
        if(instance==null){
            synchronized (EvaluatorTeamsCaller.class){
                if(instance==null){
                    instance=new EvaluatorTeamsCaller();
                }
            }
        }
        return instance;
    }



    public static EvaluatorTeam obtainEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness){
        Call<EvaluatorTeam> call=api.Get(idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,illness);
        AsyncTask<Void, Void, EvaluatorTeam> asyncTask = new AsyncTask<Void, Void, EvaluatorTeam>() {
            EvaluatorTeam resultEvaluatorTeam = null;
            @Override
            protected EvaluatorTeam doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeam> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeam evaluatorTeam) {
                resultEvaluatorTeam=evaluatorTeam;
            }

        };
        asyncTask.execute();
        try {
            EvaluatorTeam evaluatorTeam=asyncTask.get();
            return evaluatorTeam;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<EvaluatorTeam> GetAll(){
        Call<List<EvaluatorTeam>> call=api.GetAll();
        AsyncTask<Void, Void, List<EvaluatorTeam>> asyncTask = new AsyncTask<Void, Void, List<EvaluatorTeam>>() {
            List<EvaluatorTeam> resultList = null;
            @Override
            protected List<EvaluatorTeam> doInBackground(Void... voids) {
                try {
                    Response<List<EvaluatorTeam>> response = call.execute();
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
            protected void onPostExecute(List<EvaluatorTeam> evaluatorTeam) {
                resultList=evaluatorTeam;
            }

        };
        asyncTask.execute();
        try {
            List<EvaluatorTeam> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<EvaluatorTeam> GetAllByOrganization(int id,String orgType,String illness){
        Call<List<EvaluatorTeam>> call=api.GetAllByOrganization(id,orgType,illness);
        AsyncTask<Void, Void, List<EvaluatorTeam>> asyncTask = new AsyncTask<Void, Void, List<EvaluatorTeam>>() {
            List<EvaluatorTeam> resultList = null;
            @Override
            protected List<EvaluatorTeam> doInBackground(Void... voids) {
                try {
                    Response<List<EvaluatorTeam>> response = call.execute();
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
            protected void onPostExecute(List<EvaluatorTeam> evaluatorTeam) {
                resultList=evaluatorTeam;
            }

        };
        asyncTask.execute();
        try {
            List<EvaluatorTeam> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;

    }

    public static EvaluatorTeam Create(EvaluatorTeam evaluatorTeam){
        Call<EvaluatorTeam> call=api.Create(evaluatorTeam);
        AsyncTask<Void, Void, EvaluatorTeam> asyncTask = new AsyncTask<Void, Void, EvaluatorTeam>() {
            EvaluatorTeam resultEvaluatorTeam = null;
            @Override
            protected EvaluatorTeam doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeam> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeam evaluatorTeam) {
                resultEvaluatorTeam=evaluatorTeam;
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


    // PUT action
    public static EvaluatorTeam Update(int id, int idEvaluatorOrg, String orgType, String illness, EvaluatorTeam evaluatorTeam){
        Call<EvaluatorTeam> call=api.Update(id,idEvaluatorOrg,orgType,illness,evaluatorTeam);
        AsyncTask<Void, Void, EvaluatorTeam> asyncTask = new AsyncTask<Void, Void, EvaluatorTeam>() {
            EvaluatorTeam resultEvaluatorTeam = null;
            @Override
            protected EvaluatorTeam doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeam> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeam evaluatorTeam) {
                resultEvaluatorTeam=evaluatorTeam;
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

    public static EvaluatorTeam Delete(int id, int idEvaluatorOrg, String orgType,String illness){
        Call<EvaluatorTeam> call=api.Delete(id,idEvaluatorOrg,orgType,illness);
        AsyncTask<Void, Void, EvaluatorTeam> asyncTask = new AsyncTask<Void, Void, EvaluatorTeam>() {
            EvaluatorTeam resultEvaluatorTeam = null;
            @Override
            protected EvaluatorTeam doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeam> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeam evaluatorTeam) {
                resultEvaluatorTeam=evaluatorTeam;
            }

        };
        asyncTask.execute();
        try {
            EvaluatorTeam evaluatorTeam=asyncTask.get();
            return evaluatorTeam;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

}
