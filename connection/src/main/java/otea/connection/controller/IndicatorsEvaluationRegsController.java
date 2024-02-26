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

/**
 * Controller for indicators evaluation registers
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class IndicatorsEvaluationRegsController {

    /**Indicators evaluation registers api to connect to the server*/
    private static IndicatorsEvaluationRegsApi api;

    /**Controller instance*/
    private static IndicatorsEvaluationRegsController instance;

    /**Class constructor*/
    private IndicatorsEvaluationRegsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationRegsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**Refresh API*/
    public static void refreshApi(){
        instance=new IndicatorsEvaluationRegsController();
    }
    
    /**
     * Method that obtains all the registers
     *
     * @return Registrations list
     * */
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

    /**
     * Method that obtains all registers of an indicators evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @return Indicators list
     * */
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

    /**
     * Gets an indicators evaluation register
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationReg Get(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit,int idAmbit, int idIndicator, int idEvidence, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Get(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, idEvidence, indicatorVersion);
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

    /**
     * Method that appends a new indicators evaluation register
     *
     * @param indicatorsEvaluationReg - Indicators evaluation register
     * @return Register if success, null if not
     * */
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

    /**
     * Updates an indicators evaluation register
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * @param indicatorsEvaluationReg - Indicators evaluation registration
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationReg Update(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion, IndicatorsEvaluationReg indicatorsEvaluationReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Update(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, idEvidence, indicatorVersion, indicatorsEvaluationReg);
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


    /**
     * Method that deletes an indicators evaluation register
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationReg Delete(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationReg> callable = new Callable<IndicatorsEvaluationReg>() {
            @Override
            public IndicatorsEvaluationReg call() throws Exception {
                Call<IndicatorsEvaluationReg> call = api.Delete(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, idEvidence, indicatorVersion);
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
