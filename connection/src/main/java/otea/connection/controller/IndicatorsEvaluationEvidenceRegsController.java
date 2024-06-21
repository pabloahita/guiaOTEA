package otea.connection.controller;


import java.io.IOException;
;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.IndicatorsEvaluationEvidenceReg;
import okhttp3.ResponseBody;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationEvidenceRegsApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

/**
 * Controller for indicators evaluation registers
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class IndicatorsEvaluationEvidenceRegsController {

    /**Indicators evaluation registers api to connect to the server*/
    private static IndicatorsEvaluationEvidenceRegsApi api;

    /**Controller instance*/
    private static IndicatorsEvaluationEvidenceRegsController instance;

    /**Class constructor*/
    private IndicatorsEvaluationEvidenceRegsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationEvidenceRegsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static IndicatorsEvaluationEvidenceRegsController getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationEvidenceRegsController.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationEvidenceRegsController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new IndicatorsEvaluationEvidenceRegsController();
    }
    
    /**
     * Method that obtains all the registers
     *
     * @return Registrations list
     * */
    public static List<IndicatorsEvaluationEvidenceReg> GetAll(String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluationEvidenceReg>> callable = new Callable<List<IndicatorsEvaluationEvidenceReg>>() {
            @Override
            public List<IndicatorsEvaluationEvidenceReg> call() throws Exception {
                Call<List<IndicatorsEvaluationEvidenceReg>> call=api.GetAll(evaluationType, Session.getInstance().getToken());
                Response<List<IndicatorsEvaluationEvidenceReg>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluationEvidenceReg>> future = executor.submit(callable);
            List<IndicatorsEvaluationEvidenceReg> list = future.get();
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
    public static List<IndicatorsEvaluationEvidenceReg> GetAllByIndicatorsEvaluation(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, String evaluationType){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluationEvidenceReg>> callable = new Callable<List<IndicatorsEvaluationEvidenceReg>>() {
            @Override
            public List<IndicatorsEvaluationEvidenceReg> call() throws Exception {
                Call<List<IndicatorsEvaluationEvidenceReg>> call=api.GetAllByIndicatorsEvaluation(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, evaluationType,Session.getInstance().getToken());
                Response<List<IndicatorsEvaluationEvidenceReg>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluationEvidenceReg>> future = executor.submit(callable);
            List<IndicatorsEvaluationEvidenceReg> list = future.get();
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
    public static IndicatorsEvaluationEvidenceReg Get(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationEvidenceReg> callable = new Callable<IndicatorsEvaluationEvidenceReg>() {
            @Override
            public IndicatorsEvaluationEvidenceReg call() throws Exception {
                Call<IndicatorsEvaluationEvidenceReg> call = api.Get(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, idEvidence, indicatorVersion, evaluationType,Session.getInstance().getToken());
                Response<IndicatorsEvaluationEvidenceReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationEvidenceReg> future = executor.submit(callable);
            IndicatorsEvaluationEvidenceReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a new indicators evaluation register
     *
     * @param indicatorsEvaluationEvidenceReg - Indicators evaluation register
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationEvidenceReg Create(IndicatorsEvaluationEvidenceReg indicatorsEvaluationEvidenceReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationEvidenceReg> callable = new Callable<IndicatorsEvaluationEvidenceReg>() {
            @Override
            public IndicatorsEvaluationEvidenceReg call() throws Exception {
                Call<IndicatorsEvaluationEvidenceReg> call = api.Create(indicatorsEvaluationEvidenceReg,Session.getInstance().getToken());
                Response<IndicatorsEvaluationEvidenceReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationEvidenceReg> future = executor.submit(callable);
            IndicatorsEvaluationEvidenceReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends new indicators evaluation register
     *
     * @param regs - Indicators evaluation register
     */
    public static void CreateRegs(IndicatorsEvaluationEvidenceReg[][] regs){
        List<IndicatorsEvaluationEvidenceReg> registers=new ArrayList<>();
        for(IndicatorsEvaluationEvidenceReg[] ind:regs){
            registers.addAll(Arrays.asList(ind));
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<ResponseBody> callable = new Callable<ResponseBody>() {
            @Override
            public ResponseBody call() throws Exception {
                Call<ResponseBody> call = api.CreateRegs(registers,Session.getInstance().getToken());
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
            ResponseBody result = future.get();
            executor.shutdown();
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
     * @param indicatorsEvaluationEvidenceReg - Indicators evaluation registration
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationEvidenceReg Update(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion, String evaluationType, IndicatorsEvaluationEvidenceReg indicatorsEvaluationEvidenceReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationEvidenceReg> callable = new Callable<IndicatorsEvaluationEvidenceReg>() {
            @Override
            public IndicatorsEvaluationEvidenceReg call() throws Exception {
                Call<IndicatorsEvaluationEvidenceReg> call = api.Update(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, idEvidence, indicatorVersion, evaluationType, indicatorsEvaluationEvidenceReg,Session.getInstance().getToken());
                Response<IndicatorsEvaluationEvidenceReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationEvidenceReg> future = executor.submit(callable);
            IndicatorsEvaluationEvidenceReg result = future.get();
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
    public static IndicatorsEvaluationEvidenceReg Delete(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int idEvidence, int indicatorVersion, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationEvidenceReg> callable = new Callable<IndicatorsEvaluationEvidenceReg>() {
            @Override
            public IndicatorsEvaluationEvidenceReg call() throws Exception {
                Call<IndicatorsEvaluationEvidenceReg> call = api.Delete(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, idEvidence, indicatorVersion, evaluationType,Session.getInstance().getToken());
                Response<IndicatorsEvaluationEvidenceReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationEvidenceReg> future = executor.submit(callable);
            IndicatorsEvaluationEvidenceReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
