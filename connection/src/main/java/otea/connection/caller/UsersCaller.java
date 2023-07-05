package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cli.organization.Organization;
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


    public static User GetForLogin(String email, String password){
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
            return user;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    public static User Get(String email){
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
            return user;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    public static List<User> GetAll(){
        Call<List<User>> call = api.GetAll();
        AsyncTask<Void, Void, List<User>> asyncTask = new AsyncTask<Void, Void, List<User>>() {
            List<User> resultList = null;
            @Override
            protected List<User> doInBackground(Void... voids) {
                try {
                    Response<List<User>> response = call.execute();
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
            protected void onPostExecute(List<User> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<User> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }
    //GET all by user type
    public static List<User> GetAllByType(String userType){
        Call<List<User>> call = api.GetAllByType(userType);
        AsyncTask<Void, Void, List<User>> asyncTask = new AsyncTask<Void, Void, List<User>>() {
            List<User> resultList = null;
            @Override
            protected List<User> doInBackground(Void... voids) {
                try {
                    Response<List<User>> response = call.execute();
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
            protected void onPostExecute(List<User> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<User> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    //GET all organization users by organization type
    public static List<User> GetAllOrgUsersByOrganization(int idOrganization,String orgType, String illness){
        Call<List<User>> call = api.GetAllOrgUsersByOrganization(idOrganization,orgType,illness);
        AsyncTask<Void, Void, List<User>> asyncTask = new AsyncTask<Void, Void, List<User>>() {
            List<User> resultList = null;
            @Override
            protected List<User> doInBackground(Void... voids) {
                try {
                    Response<List<User>> response = call.execute();
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
            protected void onPostExecute(List<User> list) {
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<User> list=asyncTask.get();
            return list;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    public static User GetByType(String email, String userType) {
        Call<User> call = api.GetByType(email,userType);
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
            return user;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    // GET by EMAIL and ORGANIZATION action

    public static User GetOrgUserByOrganization(String email,int idOrganization,String orgType, String illness){
        Call<User> call = api.GetOrgUserByOrganization(email,idOrganization,orgType,illness);
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
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    // POST action

    public static User Create(User user){
        Call<User> call = api.Create(user);
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
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    // PUT action
    public static User Update(String email, User user){
        Call<User> call = api.Update(email,user);
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
            return asyncTask.get();
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

    // DELETE action
    public static User Delete(String email){
        Call<User> call = api.Delete(email);
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
            return user;
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }

        return null;
    }

}
