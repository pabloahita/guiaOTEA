package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import cli.organization.EvaluatedOrganization;
import cli.organization.Organization;
import cli.organization.data.Center;
import otea.connection.ConnectionClient;
import otea.connection.api.CentersApi;
import retrofit2.Call;
import retrofit2.Response;

public class CentersCaller {

    private static CentersApi api;

    private static CentersCaller instance;

    private CentersCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(CentersApi.class);
    }

    public static CentersCaller getInstance(){
        if(instance==null){
            synchronized (CentersCaller.class){
                if(instance==null){
                    instance=new CentersCaller();
                }
            }
        }
        return instance;
    }

    public static List<Center> GetAll(){
        Call<List<Center>> call=api.GetAll();
        AsyncTask<Void, Void, List<Center>> asyncTask = new AsyncTask<Void, Void, List<Center>>() {
            List<Center> resultList = null;
            @Override
            protected List<Center> doInBackground(Void... voids) {
                try {
                    Response<List<Center>> response = call.execute();
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
            protected void onPostExecute(List<Center> listCenter) {
                resultList=listCenter;
            }

        };
        asyncTask.execute();
        try {
            List<Center> list=asyncTask.get();
            List<Center> result=new LinkedList<Center>();
            for(Center center:list){
                result.add(new Center(center.getIdOrganization(),center.getOrgType(),center.getIllness(),center.getIdCenter(),center.getCenterDescription(),center.getIdAddress(),center.getTelephone()));
            }
            return result;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static List<Center> GetAllByOrganization(int idOrganization, String orgType,String illness){
        Call<List<Center>> call=api.GetAllByOrganization(idOrganization, orgType, illness);
        AsyncTask<Void, Void, List<Center>> asyncTask = new AsyncTask<Void, Void, List<Center>>() {
            List<Center> resultList = null;
            @Override
            protected List<Center> doInBackground(Void... voids) {
                try {
                    Response<List<Center>> response = call.execute();
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
            protected void onPostExecute(List<Center> listCenter) {
                resultList=listCenter;
            }

        };
        asyncTask.execute();
        try {
            List<Center> list=asyncTask.get();
            List<Center> result=new LinkedList<Center>();
            for(Center center:list){
                result.add(new Center(center.getIdOrganization(),center.getOrgType(),center.getIllness(),center.getIdCenter(),center.getCenterDescription(),center.getIdAddress(),center.getTelephone()));
            }
            return result;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Center Get(Organization organization, int idCenter){
        Call<Center> call=api.Get(organization.getIdOrganization(), organization.getOrgType(), organization.getIllness(),idCenter);
        AsyncTask<Void, Void, Center> asyncTask = new AsyncTask<Void, Void, Center>() {
            Center result = null;
            @Override
            protected Center doInBackground(Void... voids) {
                try {
                    Response<Center> response = call.execute();
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
            protected void onPostExecute(Center center) {
                result=center;
            }

        };
        asyncTask.execute();
        try {
            Center aux=asyncTask.get();
            return new Center((EvaluatedOrganization) organization,idCenter,aux.getCenterDescription(),AddressesCaller.obtainAddress(aux.getIdAddress()));
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Center Create(int idOrganization,String orgType,String illness,int idCenter,String centerDescription,int idAddress,long telephone) {
        Call<Center> call=api.Create(idOrganization,orgType,illness,idCenter,centerDescription,idAddress,telephone);
        AsyncTask<Void, Void, Center> asyncTask = new AsyncTask<Void, Void, Center>() {
            Center result = null;
            @Override
            protected Center doInBackground(Void... voids) {
                try {
                    Response<Center> response = call.execute();
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
            protected void onPostExecute(Center center) {
                result=center;
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

    public static Center Update(int idOrganization, String orgType, String illness,  int idCenter, Center center){
        Call<Center> call=api.Update(idOrganization,orgType,illness,idCenter,center);
        AsyncTask<Void, Void, Center> asyncTask = new AsyncTask<Void, Void, Center>() {
            Center result = null;
            @Override
            protected Center doInBackground(Void... voids) {
                try {
                    Response<Center> response = call.execute();
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
            protected void onPostExecute(Center center) {
                result=center;
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

    public static Center Delete(int idOrganization, String orgType, String illness, int idCenter){
        Call<Center> call=api.Delete(idOrganization,orgType,illness,idCenter);
        AsyncTask<Void, Void, Center> asyncTask = new AsyncTask<Void, Void, Center>() {
            Center result = null;
            @Override
            protected Center doInBackground(Void... voids) {
                try {
                    Response<Center> response = call.execute();
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
            protected void onPostExecute(Center center) {
                result=center;
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
