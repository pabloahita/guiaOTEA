package otea.connection.caller;

import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cli.organization.data.Address;
import otea.connection.ConnectionClient;
import otea.connection.api.AddressesApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class AddressesCaller {

    private static AddressesApi api;

    private static AddressesCaller instance;

    private AddressesCaller(){
        api= ConnectionClient.getInstance().getRetrofit().create(AddressesApi.class);
    }

    public static AddressesCaller getInstance(){
        if(instance==null){
            synchronized (AddressesCaller.class){
                if(instance==null){
                    instance=new AddressesCaller();
                }
            }
        }
        return instance;
    }

    public static List<Address> GetAll(){
        Call<List<Address>> call = api.GetAll();
        AsyncTask<Void, Void, List<Address>> asyncTask = new AsyncTask<Void, Void, List<Address>>() {
            List<Address> resultList = null;

            @Override
            protected List<Address> doInBackground(Void... voids) {
                try {
                    Response<List<Address>> response = call.execute();
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
            protected void onPostExecute (List<Address> list){
                resultList=list;
            }

        };
        asyncTask.execute();
        try {
            List<Address> list=asyncTask.get();
            return list;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public static Address Get(int idAddress) {
        Call<Address> call = api.Get(idAddress);
        AsyncTask<Void, Void, Address> asyncTask = new AsyncTask<Void, Void, Address>() {
            Address resultAddress = null;

            @Override
            protected Address doInBackground(Void... voids) {
                try {
                    Response<Address> response = call.execute();
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
            protected void onPostExecute (Address address){
                resultAddress=address;
            }

        };
        asyncTask.execute();
        try {
            Address address=asyncTask.get();
            return address;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // POST action
    public static Address Create(Address address) {
        AsyncTask<Void, Void, Address> asyncTask = new AsyncTask<Void, Void, Address>() {
            @Override
            protected Address doInBackground(Void... voids) {
                try {
                    Response<Address> response = api.Create(address).execute();
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
            protected void onPostExecute(Address address) {
                // Haz algo con la dirección obtenida, por ejemplo, mostrarla en la interfaz de usuario
            }
        };

        asyncTask.execute();
        try {
            Address aux= asyncTask.get();
            return aux;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // PUT action
    public static Address Update(int id, Address address){
        Call<Address> call = api.Update(id,address);
        AsyncTask<Void, Void, Address> asyncTask = new AsyncTask<Void, Void, Address>() {
            Address resultAddress = null;

            @Override
            protected Address doInBackground(Void... voids) {
                try {
                    Response<Address> response = call.execute();
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
            protected void onPostExecute (Address address){
                resultAddress=address;
            }

        };
        asyncTask.execute();
        try {
            Address aux=asyncTask.get();
            return aux;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE action
    public static Address Delete(int id) {
        Call<Address> call = api.Delete(id);
        AsyncTask<Void, Void, Address> asyncTask = new AsyncTask<Void, Void, Address>() {
            Address resultAddress = null;

            @Override
            protected Address doInBackground(Void... voids) {
                try {
                    Response<Address> response = call.execute();
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
            protected void onPostExecute (Address address){
                resultAddress=address;
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
}
