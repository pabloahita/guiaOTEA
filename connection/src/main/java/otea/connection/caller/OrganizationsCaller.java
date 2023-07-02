package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cli.organization.Organization;
import otea.connection.ConnectionClient;
import otea.connection.api.OrganizationsApi;
import retrofit2.Call;
import retrofit2.Response;


public class OrganizationsCaller {

    private static OrganizationsCaller instance;

    private static OrganizationsApi api;

    private OrganizationsCaller(){
        api=ConnectionClient.getInstance().getRetrofit().create(OrganizationsApi.class);
    }

    public static OrganizationsCaller getInstance(){
        if(instance==null){
            synchronized (OrganizationsCaller.class){
                if(instance==null){
                    instance=new OrganizationsCaller();
                }
            }
        }
        return instance;
    }
    public static Organization obtainOrganization(int idOrganization, String orgType, String illness) {

        Call<Organization> call=api.Get(idOrganization,orgType,illness);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
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
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
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

    public static List<Organization> GetAll(){
        Call<List<Organization>> call=api.GetAll();
        AsyncTask<Void, Void, List<Organization>> asyncTask = new AsyncTask<Void, Void, List<Organization>>() {
            List<Organization> resultList = null;
            @Override
            protected List<Organization> doInBackground(Void... voids) {
                try {
                    Response<List<Organization>> response = call.execute();
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
            protected void onPostExecute(List<Organization> listCity) {
                resultList=listCity;
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

    // GET all evaluated organizations action

    public static List<Organization> GetAllEvaluatedOrganizations() {
        Call<List<Organization>> call=api.GetAllEvaluatedOrganizations();
        AsyncTask<Void, Void, List<Organization>> asyncTask = new AsyncTask<Void, Void, List<Organization>>() {
            List<Organization> resultList = null;
            @Override
            protected List<Organization> doInBackground(Void... voids) {
                try {
                    Response<List<Organization>> response = call.execute();
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
            protected void onPostExecute(List<Organization> listCity) {
                resultList=listCity;
            }

        };
        asyncTask.execute();
        try {
            List<Organization> list=asyncTask.get();

            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    // GET all evaluator organizations action
    public static List<Organization> GetAllEvaluatorOrganizations(){
        Call<List<Organization>> call=api.GetAll();
        AsyncTask<Void, Void, List<Organization>> asyncTask = new AsyncTask<Void, Void, List<Organization>>() {
            List<Organization> resultList = null;
            @Override
            protected List<Organization> doInBackground(Void... voids) {
                try {
                    Response<List<Organization>> response = call.execute();
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
            protected void onPostExecute(List<Organization> listCity) {
                resultList=listCity;
            }

        };
        asyncTask.execute();
        try {
            List<Organization> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }


    public static Organization GetEvaluatedOrganizationById(int id,String illness){
        Call<Organization> call=api.GetEvaluatedOrganizationById(id,illness);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
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
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
            }

        };
        asyncTask.execute();
        try {
            Organization organization=asyncTask.get();
            return organization;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public static Organization GetEvaluatorOrganizationById(int id, String illness){
        Call<Organization> call=api.GetEvaluatorOrganizationById(id,illness);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
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
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
            }

        };
        asyncTask.execute();
        try {
            Organization organization=asyncTask.get();
            return organization;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    // POST action

    public static Organization Create(Organization organization){
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = api.Create(organization).execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        // Maneja los errores de manera adecuada
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "";
                        throw new IOException("Error: " + response.code() + " " + response.message() + " " + errorBody);
                    }
                } catch (IOException e) {
                    // Maneja la excepción adecuadamente, por ejemplo, mostrando un mensaje de error
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Organization address) {
            }
        };

        asyncTask.execute();
        try {
            return asyncTask.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    // PUT action
    public static Organization Update(int id, String orgType, String illness, Organization organization){
        Call<Organization> call=api.Update(id, orgType, illness,organization);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
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
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
            }

        };
        asyncTask.execute();
        try {
            Organization org=asyncTask.get();
            return org;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }
    // DELETE action

    public static Organization Delete(int id, String orgType, String illness){
        Call<Organization> call=api.Delete(id, orgType, illness);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
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
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
            }

        };
        asyncTask.execute();
        try {
            Organization org=asyncTask.get();
            return org;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

    public interface OrganizationsCallback {
        void onOrganizationsLoaded(List<Organization> organizations);
        void onError(Throwable error);
    }

}
