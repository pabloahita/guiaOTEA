package otea.connection.controller;

import java.io.IOException;
;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.data.EvaluatorTeam;
import otea.connection.ConnectionClient;
import otea.connection.api.EvaluatorTeamsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

public class EvaluatorTeamsController {

    private static EvaluatorTeamsApi api;

    private static EvaluatorTeamsController instance;

    private EvaluatorTeamsController() {
        api = ConnectionClient.getInstance().getRetrofit().create(EvaluatorTeamsApi.class);
    }

    public static EvaluatorTeamsController getInstance(){
        if(instance==null){
            synchronized (EvaluatorTeamsController.class){
                if(instance==null){
                    instance=new EvaluatorTeamsController();
                }
            }
        }
        return instance;
    }



    public static EvaluatorTeam Get(int id, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Get(id, idEvaluatorOrg, orgTypeEvaluator, idEvaluatedOrg, orgTypeEvaluated, idCenter, illness);
                Response<EvaluatorTeam> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<EvaluatorTeam> future = executor.submit(callable);
            EvaluatorTeam result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<EvaluatorTeam> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<EvaluatorTeam>> callable = new Callable<List<EvaluatorTeam>>() {
            @Override
            public List<EvaluatorTeam> call() throws Exception {
                Call<List<EvaluatorTeam>> call = api.GetAll();
                Response<List<EvaluatorTeam>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<EvaluatorTeam>> future = executor.submit(callable);
            List<EvaluatorTeam> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<EvaluatorTeam> GetAllByCenter(int id, String orgType, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<EvaluatorTeam>> callable = new Callable<List<EvaluatorTeam>>() {
            @Override
            public List<EvaluatorTeam> call() throws Exception {
                Call<List<EvaluatorTeam>> call = api.GetAllByCenter(id,orgType,idCenter,illness);
                Response<List<EvaluatorTeam>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<EvaluatorTeam>> future = executor.submit(callable);
            List<EvaluatorTeam> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<EvaluatorTeam> GetAllByOrganization(int id, String orgType, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<EvaluatorTeam>> callable = new Callable<List<EvaluatorTeam>>() {
            @Override
            public List<EvaluatorTeam> call() throws Exception {
                Call<List<EvaluatorTeam>> call = api.GetAllByOrganization(id,orgType,illness);
                Response<List<EvaluatorTeam>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<EvaluatorTeam>> future = executor.submit(callable);
            List<EvaluatorTeam> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
    public static EvaluatorTeam Create(EvaluatorTeam evaluatorTeam){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Create(evaluatorTeam);
                Response<EvaluatorTeam> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<EvaluatorTeam> future = executor.submit(callable);
            EvaluatorTeam result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    // PUT action
    public static EvaluatorTeam Update(int id, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness, EvaluatorTeam evaluatorTeam){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Update(id,idEvaluatorOrg,orgTypeEvaluator,idEvaluatedOrg,orgTypeEvaluated,idCenter,illness,evaluatorTeam);
                Response<EvaluatorTeam> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<EvaluatorTeam> future = executor.submit(callable);
            EvaluatorTeam result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public static EvaluatorTeam Delete(int id, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Delete(id,idEvaluatorOrg,orgTypeEvaluator,idEvaluatedOrg,orgTypeEvaluated,idCenter,illness);
                Response<EvaluatorTeam> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<EvaluatorTeam> future = executor.submit(callable);
            EvaluatorTeam result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
