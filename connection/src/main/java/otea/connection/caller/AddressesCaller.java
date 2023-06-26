package otea.connection.caller;

import android.os.AsyncTask;

import java.io.IOException;
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
    public static Address obtainAddress(int idAddress) {
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
            if(address!=null) {
                if (address.getIdCountry().equals("ESP")) {
                    //Se devuelve el constructor porque este llama a la obtención de los objetos City, Province, Region y Country
                    return new Address(address.getIdAddress(), address.getName(), address.getNumber(), address.getFloor(), address.getApartment(), address.getZipCode(), address.getIdCity(), address.getIdProvince(), address.getIdRegion(), address.getIdCountry());
                }else{
                    return new Address(address.getIdAddress(), address.getName(), address.getNumber(), address.getFloor(), address.getApartment(), address.getZipCode(), address.getNameCity(), address.getNameProvince(), address.getNameRegion(), address.getIdCountry());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // POST action
    public static Address Create(int idAddress, String nameStreet, int numberStreet, int floorApartment, char apartmentLetter, int zipCode, int idCity, int idProvince, int idRegion, String idCountry, String nameCity, String nameProvince, String nameRegion){
        Call<Address> call = api.Create(idAddress,nameStreet,numberStreet,floorApartment,apartmentLetter,zipCode,idCity,idProvince,idRegion,idCountry,nameCity,nameProvince,nameRegion);
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
            if(address!=null) {
                if (address.getIdCountry().equals("ESP")) {
                    //Se devuelve el constructor porque este llama a la obtención de los objetos City, Province, Region y Country
                    return new Address(address.getIdAddress(), address.getName(), address.getNumber(), address.getFloor(), address.getApartment(), address.getZipCode(), address.getIdCity(), address.getIdProvince(), address.getIdRegion(), address.getIdCountry());
                }else{
                    return new Address(address.getIdAddress(), address.getName(), address.getNumber(), address.getFloor(), address.getApartment(), address.getZipCode(), address.getNameCity(), address.getNameProvince(), address.getNameRegion(), address.getIdCountry());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
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
            if(address!=null) {
                if (aux.getIdCountry().equals("ESP")) {
                    //Se devuelve el constructor porque este llama a la obtención de los objetos City, Province, Region y Country
                    return new Address(aux.getIdAddress(), aux.getName(), aux.getNumber(), aux.getFloor(), aux.getApartment(), aux.getZipCode(), aux.getIdCity(), aux.getIdProvince(), aux.getIdRegion(), aux.getIdCountry());
                }else{
                    return new Address(aux.getIdAddress(), aux.getName(), aux.getNumber(), aux.getFloor(), aux.getApartment(), aux.getZipCode(), aux.getNameCity(), aux.getNameProvince(), aux.getNameRegion(), aux.getIdCountry());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
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
