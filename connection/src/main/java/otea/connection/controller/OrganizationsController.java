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


public class OrganizationsController {

    private static OrganizationsController instance;

    private static OrganizationsApi api;

    private OrganizationsController(){
        api=ConnectionClient.getInstance().getRetrofit().create(OrganizationsApi.class);
    }

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
    public static Organization Get(int idOrganization, String orgType, String illness) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.Get(idOrganization,orgType,illness);
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

    // GET all evaluated organizations action

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

    // GET all evaluator organizations action
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


    public static Organization GetEvaluatedOrganizationById(int id,String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.GetEvaluatedOrganizationById(id,illness);
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

    public static Organization GetEvaluatorOrganizationById(int id, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Organization> callable = new Callable<Organization>() {
            @Override
            public Organization call() throws Exception {
                Call<Organization> call = api.GetEvaluatorOrganizationById(id,illness);
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

    // POST action

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
    // PUT action
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
    // DELETE action

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
