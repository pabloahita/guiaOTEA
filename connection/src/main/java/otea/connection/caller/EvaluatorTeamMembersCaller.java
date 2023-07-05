package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.organization.data.EvaluatorTeam;
import cli.organization.data.EvaluatorTeamMember;
import cli.organization.data.geo.City;
import otea.connection.ConnectionClient;
import otea.connection.api.EvaluatorTeamMembersApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class EvaluatorTeamMembersCaller {

    private static EvaluatorTeamMembersApi api;

    private static EvaluatorTeamMembersCaller instance;

    private EvaluatorTeamMembersCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(EvaluatorTeamMembersApi.class);
    }

    public static EvaluatorTeamMembersCaller getInstance(){
        if(instance==null){
            synchronized (EvaluatorTeamMembersCaller.class){
                if(instance==null){
                    instance=new EvaluatorTeamMembersCaller();
                }
            }
        }
        return instance;
    }

    public static List<EvaluatorTeamMember> GetAll(){
        Call<List<EvaluatorTeamMember>> call=api.GetAll();
        AsyncTask<Void, Void, List<EvaluatorTeamMember>> asyncTask = new AsyncTask<Void, Void, List<EvaluatorTeamMember>>() {
            List<EvaluatorTeamMember> resultList = null;
            @Override
            protected List<EvaluatorTeamMember> doInBackground(Void... voids) {
                try {
                    Response<List<EvaluatorTeamMember>> response = call.execute();
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
            protected void onPostExecute(List<EvaluatorTeamMember> listCity) {
                resultList=listCity;
            }

        };
        asyncTask.execute();
        try {
            List<EvaluatorTeamMember> list=asyncTask.get();
            List<EvaluatorTeamMember> aux=new LinkedList<>();
            for(EvaluatorTeamMember member:list){
                aux.add(new EvaluatorTeamMember(member.getEmailUser(), member.getIdEvaluatorTeam(), member.getIdEvaluatorOrganization(), member.getOrgType(), member.getIllness()));
            }
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<EvaluatorTeamMember> GetAllByEvaluatorTeam(EvaluatorTeam evaluatorTeam){
        Call<List<EvaluatorTeamMember>> call=api.GetAllByEvaluatorTeam(evaluatorTeam.getIdEvaluatorTeam(), evaluatorTeam.getIdOrganization(), evaluatorTeam.getOrgType(), evaluatorTeam.getIllness());
        AsyncTask<Void, Void, List<EvaluatorTeamMember>> asyncTask = new AsyncTask<Void, Void, List<EvaluatorTeamMember>>() {
            List<EvaluatorTeamMember> resultList = null;
            @Override
            protected List<EvaluatorTeamMember> doInBackground(Void... voids) {
                try {
                    Response<List<EvaluatorTeamMember>> response = call.execute();
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
            protected void onPostExecute(List<EvaluatorTeamMember> listCity) {
                resultList=listCity;
            }

        };
        asyncTask.execute();
        try {
            List<EvaluatorTeamMember> list=asyncTask.get();
            List<EvaluatorTeamMember> aux=new LinkedList<>();
            for(EvaluatorTeamMember member:list){
                aux.add(new EvaluatorTeamMember(member.getEmailUser(), member.getIdEvaluatorTeam(), member.getIdEvaluatorOrganization(), member.getOrgType(), member.getIllness()));
            }
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static EvaluatorTeamMember Get(String emailUser, int idEvaluatorTeam,int idEvaluatorOrganization,String orgType,String illness){
        Call<EvaluatorTeamMember> call=api.Get(emailUser,idEvaluatorTeam,idEvaluatorOrganization,orgType,illness);
        AsyncTask<Void, Void, EvaluatorTeamMember> asyncTask = new AsyncTask<Void, Void, EvaluatorTeamMember>() {
            EvaluatorTeamMember resultEvaluatorTeamMember = null;
            @Override
            protected EvaluatorTeamMember doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeamMember> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeamMember evidence) {
                resultEvaluatorTeamMember=evidence;
            }

        };
        asyncTask.execute();
        try {
            EvaluatorTeamMember member=asyncTask.get();
            return new EvaluatorTeamMember(member.getEmailUser(), member.getIdEvaluatorTeam(), member.getIdEvaluatorOrganization(), member.getOrgType(), member.getIllness());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static EvaluatorTeamMember Create(EvaluatorTeamMember evaluatorTeamMember){
        AsyncTask<Void, Void, EvaluatorTeamMember> asyncTask = new AsyncTask<Void, Void, EvaluatorTeamMember>() {
            EvaluatorTeamMember resultEvaluatorTeamMember = null;
            @Override
            protected EvaluatorTeamMember doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeamMember> response = api.Create(evaluatorTeamMember).execute();
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
            protected void onPostExecute(EvaluatorTeamMember evidence) {
                resultEvaluatorTeamMember=evidence;
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

    public static EvaluatorTeamMember Update(String emailUser, int idEvaluatorTeam,int idEvaluatorOrganization,String orgType,String illness,EvaluatorTeamMember evaluatorTeamMember) {
        Call<EvaluatorTeamMember> call=api.Update(emailUser,idEvaluatorTeam,idEvaluatorOrganization,orgType,illness,evaluatorTeamMember);
        AsyncTask<Void, Void, EvaluatorTeamMember> asyncTask = new AsyncTask<Void, Void, EvaluatorTeamMember>() {
            EvaluatorTeamMember resultEvaluatorTeamMember = null;
            @Override
            protected EvaluatorTeamMember doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeamMember> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeamMember evidence) {
                resultEvaluatorTeamMember=evidence;
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

    public static EvaluatorTeamMember Delete(String emailUser, int idEvaluatorTeam,int idEvaluatorOrganization,String orgType,String illness){
        Call<EvaluatorTeamMember> call=api.Delete(emailUser,idEvaluatorTeam,idEvaluatorOrganization,orgType,illness);
        AsyncTask<Void, Void, EvaluatorTeamMember> asyncTask = new AsyncTask<Void, Void, EvaluatorTeamMember>() {
            EvaluatorTeamMember resultEvaluatorTeamMember = null;
            @Override
            protected EvaluatorTeamMember doInBackground(Void... voids) {
                try {
                    Response<EvaluatorTeamMember> response = call.execute();
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
            protected void onPostExecute(EvaluatorTeamMember evidence) {
                resultEvaluatorTeamMember=evidence;
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
