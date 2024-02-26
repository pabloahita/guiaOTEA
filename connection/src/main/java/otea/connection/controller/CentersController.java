package otea.connection.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.Organization;
import cli.organization.data.Center;
import otea.connection.ConnectionClient;
import otea.connection.api.CentersApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller class for Centers operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class CentersController {

    /**Centers api to connect to the center*/
    private static CentersApi api;

    /**Controller instance*/
    private static CentersController instance;

    /**
     * Class constructor
     * */
    private CentersController(){
        api= ConnectionClient.getInstance().getRetrofit().create(CentersApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Instance of the controller
     * */
    public static CentersController getInstance(){
        if(instance==null){
            synchronized (CentersController.class){
                if(instance==null){
                    instance=new CentersController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new CentersController();
    }

    /**
     * Method that obtains all the centers
     *
     * @return Centers list
     * */
    public static List<Center> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Center>> callable = new Callable<List<Center>>() {
            @Override
            public List<Center> call() throws Exception {
                Call<List<Center>> call = api.GetAll();
                Response<List<Center>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Center>> future = executor.submit(callable);
            List<Center> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains all the centers of an organization
     *
     * @param organization - Organization
     * @return All centers of an organization
     * */
    public static List<Center> GetAllByOrganization(Organization organization){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Center>> callable = new Callable<List<Center>>() {
            @Override
            public List<Center> call() throws Exception {
                Call<List<Center>> call = api.GetAllByOrganization(organization.getIdOrganization(),organization.getOrgType(),organization.getIllness());
                Response<List<Center>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Center>> future = executor.submit(callable);
            List<Center> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that obtains a center using its identifier and the belonging organization
     *
     * @param organization - Organization
     * @param idCenter - Center identifier
     * @return Center if found on database, null if not
     * */
    public static Center Get(Organization organization, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Get(organization.getIdOrganization(),organization.getOrgType(),organization.getIllness(),idCenter);
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a new center to the database
     *
     * @param center - Center to append
     * @return Center appended if sucess, null if not
     * */
    public static Center Create(Center center) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Create(center);
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates an existing center
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * @param center - Center
     * @return Updated center if success, null if not
     * */
    public static Center Update(int idOrganization, String orgType, String illness,  int idCenter, Center center){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Update(idOrganization,orgType,illness,idCenter,center);
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes a center
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * @return Deleted center if success, null if not
     * */
    public static Center Delete(int idOrganization, String orgType, String illness, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Delete(idOrganization,orgType,illness,idCenter);
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
