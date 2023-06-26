package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import cli.organization.EvaluatedOrganization;
import cli.organization.EvaluatorOrganization;
import cli.organization.Organization;
import cli.user.Administrator;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;
import cli.user.OrganizationUser;
import cli.user.User;
import otea.connection.ConnectionClient;
import otea.connection.api.UsersApi;
import retrofit2.Call;
import retrofit2.Response;

public class UsersCaller {

    private static UsersApi api;

    private static UsersCaller instance;

    private UsersCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(UsersApi.class);
    }

    public static UsersCaller getInstance(){
        if(instance==null){
            synchronized (UsersCaller.class){
                if(instance==null){
                    instance=new UsersCaller();
                }
            }
        }
        return instance;
    }


    public static OrganizationUser obtainOrgUser(String email, Organization organization){
        Call<User> call = api.Get(email);
        User[] aux=new User[1];
        AsyncTask<Void, Void, User> asyncTask = new AsyncTask<Void, Void, User>() {
            User resultUser = null;
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    Response<User> response = call.execute();
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
            protected void onPostExecute(User user) {
                resultUser=user;
            }

        };
        asyncTask.execute();
        try {
            User user=asyncTask.get();
            if(organization.getIdOrganization()==user.getIdOrganization() && organization.getOrganizationType().equals(user.getOrgType()) && organization.getIllness().equals(user.getIllness())){
                if (user.getOrgType().equals("EVALUATED")) {
                    return new EvaluatedOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone(), (EvaluatedOrganization) organization);
                } else if (user.getOrgType().equals("EVALUATOR")) {
                    return new EvaluatorOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone(), (EvaluatorOrganization) organization);
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }


        return null;
    }
    public static User obtainUserForLogin(String email, String password){
        Call<User> call = api.GetForLogin(email, password);
        AsyncTask<Void, Void, User> asyncTask = new AsyncTask<Void, Void, User>() {
            User resultUser = null;
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    Response<User> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    }
                    return null;
                    //else {

                    //throw new IOException("Error: " + response.code() + " " + response.message());
                    //}
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(User user) {
                if(user!=null) {
                    resultUser = user;
                }
            }
        };
        asyncTask.execute();
        try {
            User user=asyncTask.get();
            if (user != null) {
                if(user.getUserType().equals("ORGANIZATION")){
                    if (user.getOrgType().equals("EVALUATED")) {
                        return new EvaluatedOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    } else if (user.getOrgType().equals("EVALUATOR")) {
                        return new EvaluatorOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    }
                } else if (user.getUserType().equals("ADMIN")) {
                    return new Administrator(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone());
                }
            }else{return null;}
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    public static User obtainUser(String email){
        Call<User> call = api.Get(email);
        AsyncTask<Void, Void, User> asyncTask = new AsyncTask<Void, Void, User>() {
            User resultUser = null;
            @Override
            protected User doInBackground(Void... voids) {
                try {
                    Response<User> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } /*else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }*/return null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(User user) {
                resultUser=user;
            }

        };
        asyncTask.execute();
        try {
            User user=asyncTask.get();
            if (user != null) {
                if(user.getUserType().equals("ORGANIZATION")){
                    if (user.getOrgType().equals("EVALUATED")) {
                        return new EvaluatedOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    } else if (user.getOrgType().equals("EVALUATOR")) {
                        return new EvaluatorOrganizationUser(user.getFirst_name(), user.getLast_name(), user.getUserType(), user.getEmailUser(), user.getTelephone(), user.getIdOrganization(), user.getOrgType(), user.getIllness());
                    }
                } else if (user.getUserType().equals("ADMIN")) {
                    return new Administrator(user.getFirst_name(), user.getLast_name(), user.getOrgType(), user.getEmailUser(), user.getTelephone());
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

}
