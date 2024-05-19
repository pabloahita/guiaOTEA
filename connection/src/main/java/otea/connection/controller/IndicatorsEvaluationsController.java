package otea.connection.controller;

import com.google.gson.JsonObject;

import java.io.IOException;
;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.IndicatorsEvaluation;
import okhttp3.ResponseBody;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationsApi;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Controller class for indicators evaluations operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class IndicatorsEvaluationsController {

    /**Indicators evaluations api to connect to the server*/
    private static IndicatorsEvaluationsApi api;

    /**Controller instance*/
    private static IndicatorsEvaluationsController instance;

    /**Class constructor*/
    private IndicatorsEvaluationsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**Refresh API*/
    public static void refreshApi(){
        instance=new IndicatorsEvaluationsController();
    }


    /**
     * Method that obtains all indicators evaluations
     *
     * @return Indicators evaluations list
     * */
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

    /**
     * Method that obtains all indicators evaluation registers, from evidences and indicators
     *
     * @return Registers list
     * */
    public static List<JsonObject> GetRegsByIndicatorsEvaluation(IndicatorsEvaluation indicatorsEvaluation) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetRegsByIndicatorsEvaluation(indicatorsEvaluation);
                Response<List<JsonObject>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<JsonObject>> future = executor.submit(callable);
            List<JsonObject> list = future.get();
            executor.shutdown();
            return list;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Gets all indicators evaluations of an evaluator team
     *
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatorOrg - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatedOrg - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param idCenter - Center identifier
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @return Indicators evaluations list
     * */
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

    /**
     * Method that obtains all the non finished indicators evaluations of a evaluator team
     *
     * @param idEvaluatorTeam - Identifier of the evaluator team
     * @param idEvaluatorOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatedOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier
     * @return Indicators evaluations list
     * */
    public static List<IndicatorsEvaluation> GetNonFinishedByEvaluatorTeam(int idEvaluatorTeam,int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluation>> callable = new Callable<List<IndicatorsEvaluation>>() {
            @Override
            public List<IndicatorsEvaluation> call() throws Exception {
                Call<List<IndicatorsEvaluation>> call=api.GetNonFinishedByEvaluatorTeam(idEvaluatorTeam,idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter);
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

    /**
     * Method that obtains an indicators evaluation from the database
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @return Indicators evaluation if success, null if not
     * */
    public static IndicatorsEvaluation Get(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call = api.Get(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,evaluationType);
                Response<IndicatorsEvaluation> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                }else if(response.code()==404){
                    return null;
                }
                else {
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

    /**
     * Method that appends to the database a new indicators evaluation
     *
     * @param indicatorsEvaluation - Indicators evaluation
     * @return Indicators evaluation if success, null if not
     * */
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

    /**
     * Method that updates an indicators evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param indicatorsEvaluation - Indicators evaluation
     * @return Indicators evaluation if success, null if not
     * */
    public static IndicatorsEvaluation Update(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, String evaluationType,IndicatorsEvaluation indicatorsEvaluation){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call=api.Update(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,evaluationType,indicatorsEvaluation);
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

    /**
     * Method that obtains the finished indicator evaluation with its results
     *
     * @param indicatorsEvaluation - Indicators evaluation
     */
    public static void calculateResults(IndicatorsEvaluation indicatorsEvaluation){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<ResponseBody> callable = new Callable<ResponseBody>() {
            @Override
            public ResponseBody call() throws Exception {
                Call<ResponseBody> call=api.calculateResults(indicatorsEvaluation);
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<ResponseBody> future = executor.submit(callable);
            future.get();
            executor.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes an indicator evaluation
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @return Indicators evaluation if success, null if not
     * */
    public static IndicatorsEvaluation Delete(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluation> callable = new Callable<IndicatorsEvaluation>() {
            @Override
            public IndicatorsEvaluation call() throws Exception {
                Call<IndicatorsEvaluation> call = api.Delete(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,evaluationType);
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
