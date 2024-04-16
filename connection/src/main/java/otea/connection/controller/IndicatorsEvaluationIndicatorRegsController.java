package otea.connection.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.IndicatorsEvaluationIndicatorReg;
import okhttp3.ResponseBody;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationIndicatorRegsApi;
import retrofit2.Call;
import retrofit2.Response;

public class IndicatorsEvaluationIndicatorRegsController {

    /**Indicators evaluation registers api to connect to the server*/
    private static IndicatorsEvaluationIndicatorRegsApi api;

    /**Controller instance*/
    private static IndicatorsEvaluationIndicatorRegsController instance;

    /**Class constructor*/
    private IndicatorsEvaluationIndicatorRegsController(){
        api= ConnectionClient.getInstance().getRetrofit().create(IndicatorsEvaluationIndicatorRegsApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Controller instance
     * */
    public static IndicatorsEvaluationIndicatorRegsController getInstance(){
        if(instance==null){
            synchronized (IndicatorsEvaluationIndicatorRegsController.class){
                if(instance==null){
                    instance=new IndicatorsEvaluationIndicatorRegsController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new IndicatorsEvaluationIndicatorRegsController();
    }

    /**
     * Method that obtains all the registers
     *
     * @return Registrations list
     * */
    public static List<IndicatorsEvaluationIndicatorReg> GetAll(String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluationIndicatorReg>> callable = new Callable<List<IndicatorsEvaluationIndicatorReg>>() {
            @Override
            public List<IndicatorsEvaluationIndicatorReg> call() throws Exception {
                Call<List<IndicatorsEvaluationIndicatorReg>> call=api.GetAll(evaluationType);
                Response<List<IndicatorsEvaluationIndicatorReg>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluationIndicatorReg>> future = executor.submit(callable);
            List<IndicatorsEvaluationIndicatorReg> list = future.get();
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
    public static List<IndicatorsEvaluationIndicatorReg> GetAllByIndicatorsEvaluation(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, String evaluationType){

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<IndicatorsEvaluationIndicatorReg>> callable = new Callable<List<IndicatorsEvaluationIndicatorReg>>() {
            @Override
            public List<IndicatorsEvaluationIndicatorReg> call() throws Exception {
                Call<List<IndicatorsEvaluationIndicatorReg>> call=api.GetAllByIndicatorsEvaluation(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, evaluationType);
                Response<List<IndicatorsEvaluationIndicatorReg>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<List<IndicatorsEvaluationIndicatorReg>> future = executor.submit(callable);
            List<IndicatorsEvaluationIndicatorReg> list = future.get();
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
     * @param indicatorVersion - Indicator version
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationIndicatorReg Get(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int indicatorVersion, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationIndicatorReg> callable = new Callable<IndicatorsEvaluationIndicatorReg>() {
            @Override
            public IndicatorsEvaluationIndicatorReg call() throws Exception {
                Call<IndicatorsEvaluationIndicatorReg> call = api.Get(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, indicatorVersion, evaluationType);
                Response<IndicatorsEvaluationIndicatorReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationIndicatorReg> future = executor.submit(callable);
            IndicatorsEvaluationIndicatorReg result = future.get();
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
    public static IndicatorsEvaluationIndicatorReg Create(IndicatorsEvaluationIndicatorReg indicatorsEvaluationEvidenceReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationIndicatorReg> callable = new Callable<IndicatorsEvaluationIndicatorReg>() {
            @Override
            public IndicatorsEvaluationIndicatorReg call() throws Exception {
                Call<IndicatorsEvaluationIndicatorReg> call = api.Create(indicatorsEvaluationEvidenceReg);
                Response<IndicatorsEvaluationIndicatorReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationIndicatorReg> future = executor.submit(callable);
            IndicatorsEvaluationIndicatorReg result = future.get();
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
    public static void CreateRegs(IndicatorsEvaluationIndicatorReg[] regs){
        List<IndicatorsEvaluationIndicatorReg> registers=List.of(regs);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<ResponseBody> callable = new Callable<ResponseBody>() {
            @Override
            public ResponseBody call() throws Exception {
                Call<ResponseBody> call = api.CreateRegs(registers);
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
     * @param indicatorVersion - Indicator version
     * @param indicatorsEvaluationEvidenceReg - Indicators evaluation registration
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationIndicatorReg Update(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int indicatorVersion, String evaluationType, IndicatorsEvaluationIndicatorReg indicatorsEvaluationEvidenceReg){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationIndicatorReg> callable = new Callable<IndicatorsEvaluationIndicatorReg>() {
            @Override
            public IndicatorsEvaluationIndicatorReg call() throws Exception {
                Call<IndicatorsEvaluationIndicatorReg> call = api.Update(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, indicatorVersion, evaluationType, indicatorsEvaluationEvidenceReg);
                Response<IndicatorsEvaluationIndicatorReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationIndicatorReg> future = executor.submit(callable);
            IndicatorsEvaluationIndicatorReg result = future.get();
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
     * @param indicatorVersion - Indicator version
     * @return Register if success, null if not
     * */
    public static IndicatorsEvaluationIndicatorReg Delete(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int indicatorVersion, String evaluationType){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<IndicatorsEvaluationIndicatorReg> callable = new Callable<IndicatorsEvaluationIndicatorReg>() {
            @Override
            public IndicatorsEvaluationIndicatorReg call() throws Exception {
                Call<IndicatorsEvaluationIndicatorReg> call = api.Delete(evaluationDate, idEvaluatorTeam, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter, idSubSubAmbit, idSubAmbit, idAmbit, idIndicator, indicatorVersion, evaluationType);
                Response<IndicatorsEvaluationIndicatorReg> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<IndicatorsEvaluationIndicatorReg> future = executor.submit(callable);
            IndicatorsEvaluationIndicatorReg result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
