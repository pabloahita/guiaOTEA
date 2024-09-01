package otea.connection.controller;

import com.google.gson.JsonObject;

import java.io.IOException;
;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.data.EvaluatorTeam;
import misc.ListCallback;
import okhttp3.ResponseBody;
import otea.connection.ConnectionClient;
import otea.connection.api.IndicatorsEvaluationsApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

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

    /**Number of attempts*/
    private static int numAttempts=0;

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
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAll(Session.getInstance().getToken());
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
            numAttempts=0;
            List<IndicatorsEvaluation> indicatorsEvaluations=new ArrayList<>();
            for(JsonObject indEval:list){
                long evaluationDate=indEval.getAsJsonPrimitive("evaluationDate").getAsLong();
                int idEvaluatorTeam=indEval.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                int idEvaluatedOrg=indEval.getAsJsonPrimitive("idEvaluatedOrganization").getAsInt();
                String orgTypeEvaluated=indEval.getAsJsonPrimitive("orgTypeEvaluated").getAsString();
                int idEvaluatorOrg=indEval.getAsJsonPrimitive("idEvaluatorOrganization").getAsInt();
                String orgTypeEvaluator=indEval.getAsJsonPrimitive("orgTypeEvaluator").getAsString();
                String illness=indEval.getAsJsonPrimitive("illness").getAsString();
                int idCenter=indEval.getAsJsonPrimitive("idCenter").getAsInt();
                int scorePriorityZeroColourRed=indEval.getAsJsonPrimitive("scorePriorityZeroColourRed").getAsInt();
                int scorePriorityZeroColourYellow=indEval.getAsJsonPrimitive("scorePriorityZeroColourYellow").getAsInt();
                int scorePriorityZeroColourGreen=indEval.getAsJsonPrimitive("scorePriorityZeroColourGreen").getAsInt();
                int scorePriorityOneColourRed=indEval.getAsJsonPrimitive("scorePriorityOneColourRed").getAsInt();
                int scorePriorityOneColourYellow=indEval.getAsJsonPrimitive("scorePriorityOneColourYellow").getAsInt();
                int scorePriorityOneColourGreen=indEval.getAsJsonPrimitive("scorePriorityOneColourGreen").getAsInt();
                int scorePriorityTwoColourRed=indEval.getAsJsonPrimitive("scorePriorityTwoColourRed").getAsInt();
                int scorePriorityTwoColourYellow=indEval.getAsJsonPrimitive("scorePriorityTwoColourYellow").getAsInt();
                int scorePriorityTwoColourGreen=indEval.getAsJsonPrimitive("scorePriorityTwoColourGreen").getAsInt();
                int scorePriorityThreeColourRed=indEval.getAsJsonPrimitive("scorePriorityThreeColourRed").getAsInt();
                int scorePriorityThreeColourYellow=indEval.getAsJsonPrimitive("scorePriorityThreeColourYellow").getAsInt();
                int scorePriorityThreeColourGreen=indEval.getAsJsonPrimitive("scorePriorityThreeColourGreen").getAsInt();
                int totalScore=indEval.getAsJsonPrimitive("totalScore").getAsInt();
                String conclusionsSpanish=indEval.getAsJsonPrimitive("conclusionsSpanish").getAsString();
                String conclusionsEnglish=indEval.getAsJsonPrimitive("conclusionsEnglish").getAsString();
                String conclusionsFrench=indEval.getAsJsonPrimitive("conclusionsFrench").getAsString();
                String conclusionsBasque=indEval.getAsJsonPrimitive("conclusionsBasque").getAsString();
                String conclusionsCatalan=indEval.getAsJsonPrimitive("conclusionsCatalan").getAsString();
                String conclusionsDutch=indEval.getAsJsonPrimitive("conclusionsDutch").getAsString();
                String conclusionsGalician=indEval.getAsJsonPrimitive("conclusionsGalician").getAsString();
                String conclusionsGerman=indEval.getAsJsonPrimitive("conclusionsGerman").getAsString();
                String conclusionsItalian=indEval.getAsJsonPrimitive("conclusionsItalian").getAsString();
                String conclusionsPortuguese=indEval.getAsJsonPrimitive("conclusionsPortuguese").getAsString();
                int isFinished=indEval.getAsJsonPrimitive("isFinished").getAsInt();
                String evaluationType=indEval.getAsJsonPrimitive("evaluationType").getAsString();
                String level=indEval.getAsJsonPrimitive("level").getAsString();

                indicatorsEvaluations.add(new IndicatorsEvaluation(evaluationDate,idEvaluatedOrg, orgTypeEvaluated,idEvaluatorTeam, idEvaluatorOrg,  orgTypeEvaluator,  illness, idCenter,
                        scorePriorityZeroColourRed, scorePriorityZeroColourYellow, scorePriorityZeroColourGreen,
                        scorePriorityOneColourRed, scorePriorityOneColourYellow, scorePriorityOneColourGreen,
                        scorePriorityTwoColourRed, scorePriorityTwoColourYellow, scorePriorityTwoColourGreen,
                        scorePriorityThreeColourRed, scorePriorityThreeColourYellow, scorePriorityThreeColourGreen, totalScore,
                        conclusionsSpanish,  conclusionsEnglish,  conclusionsFrench,  conclusionsBasque,  conclusionsCatalan,  conclusionsDutch,  conclusionsGalician,  conclusionsGerman,  conclusionsItalian,  conclusionsPortuguese, isFinished,  evaluationType,  level));
            }
            return indicatorsEvaluations;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetAll();
                }
                else{
                    numAttempts=0;
                    return null;
                }
            }
            else{
                throw new RuntimeException(e);
            }
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
                Call<List<JsonObject>> call = api.GetRegsByIndicatorsEvaluation(indicatorsEvaluation.getEvaluationDate(),indicatorsEvaluation.getIdEvaluatorTeam(),indicatorsEvaluation.getIdEvaluatorOrganization(),indicatorsEvaluation.getOrgTypeEvaluator(),indicatorsEvaluation.getIdEvaluatedOrganization(),indicatorsEvaluation.getOrgTypeEvaluated(),indicatorsEvaluation.getIllness(),indicatorsEvaluation.getIdCenter(),indicatorsEvaluation.getEvaluationType(), Locale.getDefault().getLanguage(),Session.getInstance().getToken());
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
            numAttempts=0;
            return list;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetRegsByIndicatorsEvaluation(indicatorsEvaluation);
                }
                else{
                    numAttempts=0;
                    return null;
                }
            }
            else{
                throw new RuntimeException(e);
            }
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
    public static void GetAllByEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness, ListCallback callback){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call=api.GetAllByEvaluatorTeam(idEvaluatorTeam, idEvaluatorOrg, orgTypeEvaluator, idEvaluatedOrg, orgTypeEvaluated, idCenter,illness,Locale.getDefault().getLanguage(),Session.getInstance().getToken());
                Response<List<JsonObject>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        executor.submit(()->{
            try {
                List<JsonObject> result=callable.call();
                callback.onSuccess(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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
    public static void GetNonFinishedByEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, ListCallback callback){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call=api.GetNonFinishedByEvaluatorTeam(idEvaluatorTeam,idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, illness, idCenter,Locale.getDefault().getLanguage(),Session.getInstance().getToken());
                Response<List<JsonObject>> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        executor.submit(()->{
            try {
                List<JsonObject> result=callable.call();
                callback.onSuccess(result);
            } catch (Exception e) {
                callback.onError(e.getCause().toString());
            }finally {
                executor.shutdown();
            }
        });
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
                Call<IndicatorsEvaluation> call = api.Get(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,evaluationType,Session.getInstance().getToken());
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
                Call<IndicatorsEvaluation> call = api.Create(indicatorsEvaluation,Session.getInstance().getToken());
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
                Call<IndicatorsEvaluation> call=api.Update(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,evaluationType,indicatorsEvaluation,Session.getInstance().getToken());
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
                Call<ResponseBody> call=api.calculateResults(indicatorsEvaluation,Session.getInstance().getToken());
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
                Call<IndicatorsEvaluation> call = api.Delete(evaluationDate,idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,idEvaluatedOrganization,orgTypeEvaluated,illness,idCenter,evaluationType,Session.getInstance().getToken());
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
