package otea.connection.controller;

import java.io.IOException;
;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

public class IndicatorsEvaluationsController {

    private static IndicatorsEvaluationsApi api;

    private static IndicatorsEvaluationsController instance;

    private IndicatorsEvaluationsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationsApi.class);
    }

    public static IndicatorsEvaluationsController getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationsController.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationsController();
                }
            }
        }
        return instance;
    }

    
    public static List<IndicatorsEvaluation> GetAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluation>> callable = new Callable<List<IndicatorsEvaluation>>() {
            @Override
            public List<IndicatorsEvaluation> call() throws Exception {
                Call<List<IndicatorsEvaluation>> call = api.GetAll();
                Response<List<IndicatorsEvaluation>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluation>> future = executor.submit(callable);
            List<IndicatorsEvaluation> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }



    public static List<IndicatorsEvaluation> GetAllByEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluation>> callable = new Callable<List<IndicatorsEvaluation>>() {
            @Override
            public List<IndicatorsEvaluation> call() throws Exception {
                Call<List<IndicatorsEvaluation>> call=api.GetAllByEvaluatorTeam(idEvaluatorTeam, idEvaluatorOrg, orgTypeEvaluator, idEvaluatedOrg, orgTypeEvaluated, idCenter,illness);
                Response<List<IndicatorsEvaluation>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluation>> future = executor.submit(callable);
            List<IndicatorsEvaluation> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<IndicatorsEvaluation> GetNonFinishedByCenter(int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluation>> callable = new Callable<List<IndicatorsEvaluation>>() {
            @Override
            public List<IndicatorsEvaluation> call() throws Exception {
                Call<List<IndicatorsEvaluation>> call=api.GetNonFinishedByCenter(idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter);
                Response<List<IndicatorsEvaluation>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluation>> future = executor.submit(callable);
            List<IndicatorsEvaluation> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static IndicatorsEvaluation Get(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call = api.Get(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter);
                Response<IndicatorsEvaluation> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluation> future = executor.submit(callable);
            IndicatorsEvaluation result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static IndicatorsEvaluation Create(IndicatorsEvaluation indicatorsEvaluation){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call = api.Create(indicatorsEvaluation);
                Response<IndicatorsEvaluation> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluation> future = executor.submit(callable);
            IndicatorsEvaluation result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static IndicatorsEvaluation Update(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter,IndicatorsEvaluation indicatorsEvaluation){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call=api.Update(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,indicatorsEvaluation);
                Response<IndicatorsEvaluation> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluation> future = executor.submit(callable);
            IndicatorsEvaluation result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    
    public static IndicatorsEvaluation Delete(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call = api.Delete(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter);
                Response<IndicatorsEvaluation> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluation> future = executor.submit(callable);
            IndicatorsEvaluation result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
