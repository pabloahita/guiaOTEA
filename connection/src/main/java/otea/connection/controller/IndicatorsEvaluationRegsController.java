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
import cli.indicators.IndicatorsEvaluationReg;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationRegsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

public class IndicatorsEvaluationRegsController {
    private static IndicatorsEvaluationRegsApi api;

    private static IndicatorsEvaluationRegsController instance;

    private IndicatorsEvaluationRegsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationRegsApi.class);
    }

    public static IndicatorsEvaluationRegsController getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationRegsController.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationRegsController();
                }
            }
        }
        return instance;
    }
    
    
    public static List<IndicatorsEvaluationReg> GetAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluationReg>> callable = new Callable<List<IndicatorsEvaluationReg>>() {
            @Override
            public List<IndicatorsEvaluationReg> call() throws Exception {
                Call<List<IndicatorsEvaluationReg>> call=api.GetAll();
                Response<List<IndicatorsEvaluationReg>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluationReg>> future = executor.submit(callable);
            List<IndicatorsEvaluationReg> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<IndicatorsEvaluationReg> GetAllByIndicatorsEvaluation(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluationReg>> callable = new Callable<List<IndicatorsEvaluationReg>>() {
            @Override
            public List<IndicatorsEvaluationReg> call() throws Exception {
                Call<List<IndicatorsEvaluationReg>> call=api.GetAllByIndicatorsEvaluation(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter);
                Response<List<IndicatorsEvaluationReg>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluationReg>> future = executor.submit(callable);
            List<IndicatorsEvaluationReg> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static IndicatorsEvaluationReg Get(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Get(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idAmbit, idIndicator, idEvidence, indicatorVersion);
                Response<IndicatorsEvaluationReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationReg> future = executor.submit(callable);
            IndicatorsEvaluationReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static IndicatorsEvaluationReg Create(IndicatorsEvaluationReg indicatorsEvaluationReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Create(indicatorsEvaluationReg);
                Response<IndicatorsEvaluationReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationReg> future = executor.submit(callable);
            IndicatorsEvaluationReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static IndicatorsEvaluationReg Update(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion, IndicatorsEvaluationReg indicatorsEvaluationReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Update(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idAmbit, idIndicator, idEvidence, indicatorVersion, indicatorsEvaluationReg);
                Response<IndicatorsEvaluationReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationReg> future = executor.submit(callable);
            IndicatorsEvaluationReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static IndicatorsEvaluationReg Delete(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Delete(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idAmbit, idIndicator, idEvidence, indicatorVersion);
                Response<IndicatorsEvaluationReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationReg> future = executor.submit(callable);
            IndicatorsEvaluationReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
