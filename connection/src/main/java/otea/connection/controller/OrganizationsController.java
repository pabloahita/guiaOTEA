package otea.connection.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.Organization;
import otea.connection.ConnectionClient;
import otea.connection.api.OrganizationsApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;


/**
 * Controller class for organization operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class OrganizationsController {

    /**Controller instance*/
    private static OrganizationsController instance;

    /**Organizations api to connect to the server*/
    private static OrganizationsApi api;

    /**Class constructor*/
    private OrganizationsController(){
        api=ConnectionClient.getInstance().getRetrofit().create(OrganizationsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static OrganizationsController getInstance(){
        if(instance==null){
            synchronized (OrganizationsController.class){
                if(instance==null){
                    instance=new OrganizationsController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new OrganizationsController();
    }

    /**
     * Method that obtains an organization from the database
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @return Organization if success, null if not
     * */
    public static Organization Get(int idOrganization, String orgType, String illness) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.Get(idOrganization,orgType,illness,Session.getInstance().getToken());
                Response<Organization> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Organization> future = executor.submit(callable);
            Organization result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all organizations
     *
     * @return Organization list
     * */
    public static List<Organization> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Organization>> callable = new Callable<List<Organization>>() {
            @Override
            public List<Organization> call() throws Exception {
                Call<List<Organization>> call=api.GetAll();
                Response<List<Organization>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Organization>> future = executor.submit(callable);
            List<Organization> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all evaluated organizations
     *
     * @return Evaluated organization list
     * */
    public static List<Organization> GetAllEvaluatedOrganizations() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Organization>> callable = new Callable<List<Organization>>() {
            @Override
            public List<Organization> call() throws Exception {
                Call<List<Organization>> call=api.GetAllEvaluatedOrganizations();
                Response<List<Organization>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Organization>> future = executor.submit(callable);
            List<Organization> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all evaluator organizations
     *
     * @return Evaluator organization list
     * */
    public static List<Organization> GetAllEvaluatorOrganizations(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<Organization>> callable = new Callable<List<Organization>>() {
            @Override
            public List<Organization> call() throws Exception {
                Call<List<Organization>> call=api.GetAllEvaluatorOrganizations();
                Response<List<Organization>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<Organization>> future = executor.submit(callable);
            List<Organization> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Method that appends an organization to the database
     *
     * @param organization - Organization
     * @return Organization if success, null if not
     * */
    public static Organization Create(Organization organization){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.Create(organization);
                Response<Organization> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Organization> future = executor.submit(callable);
            Organization result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates an organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param organization - Organization
     * @return Organization if success, null if not
     * */
    public static Organization Update(int id, String orgType, String illness, Organization organization){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.Update(id,orgType,illness,organization);
                Response<Organization> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Organization> future = executor.submit(callable);
            Organization result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes an organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @return Organization if success, null if not
     * */
    public static Organization Delete(int id, String orgType, String illness) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.Delete(id, orgType, illness);
                Response<Organization> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Organization> future = executor.submit(callable);
            Organization result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
