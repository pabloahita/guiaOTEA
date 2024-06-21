package otea.connection.controller;

import com.google.gson.JsonObject;

import java.io.IOException;
;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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
import session.Session;

/**
 * Controller class for evaluator teams operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class EvaluatorTeamsController {

    /**Evaluator teams api to connect to the server*/
    private static EvaluatorTeamsApi api;

    /**Evaluator teams controller instance*/
    private static EvaluatorTeamsController instance;

    /**Number of attempts*/
    private static int numAttempts=0;

    /**Class constructor*/
    private EvaluatorTeamsController() {
        api = ConnectionClient.getInstance().getRetrofit().create(EvaluatorTeamsApi.class);
    }

    /**
     * Method to obtain the singleton instance of the controller
     *
     * @return Controller instance
     * */
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

    /**Refresh API*/
    public static void refreshApi(){
        instance=new EvaluatorTeamsController();
    }



    /**
     * Method that obtains an evaluator team from the database
     *
     * @param id - Evaluator team identifier
     * @param idEvaluatorOrg - Evaluator organization identifier (who does the indicators evaluations)
     * @param orgTypeEvaluator - Evaluator organization type (who does the indicators evaluations)
     * @param idEvaluatedOrg - Evaluated organization identifier (who receives the indicators evaluations)
     * @param orgTypeEvaluated - Evaluated organization type (who receives the indicators evaluations)
     * @param idCenter - Evaluated organization center (who receives the indicators evaluations)
     * @param illness - Both organizations illness or syndrome
     * @return Evaluator team if sucess, null if not
     * */
    public static EvaluatorTeam Get(int id, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Get(id, idEvaluatorOrg, orgTypeEvaluator, idEvaluatedOrg, orgTypeEvaluated, idCenter, illness, Session.getInstance().getToken());
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

    /**
     * Method that obtains all the evaluator teams
     *
     * @return Evaluator teams list
     * */
    public static List<EvaluatorTeam> GetAll(){
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
            List<EvaluatorTeam> evaluatorTeams=new ArrayList<>();
            for(JsonObject evalTeam:list){
                int idEvaluatorTeam=evalTeam.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                long creationDate=evalTeam.getAsJsonPrimitive("creationDate").getAsLong();
                String emailProfessional=evalTeam.getAsJsonPrimitive("emailProfessional").getAsString();
                String emailResponsible=evalTeam.getAsJsonPrimitive("emailResponsible").getAsString();
                String otherMembers=evalTeam.getAsJsonPrimitive("otherMembers").getAsString();
                int idEvaluatorOrganization=evalTeam.getAsJsonPrimitive("idEvaluatorOrganization").getAsInt();
                String orgTypeEvaluator=evalTeam.getAsJsonPrimitive("orgTypeEvaluator").getAsString();
                int idEvaluatedOrganization=evalTeam.getAsJsonPrimitive("idEvaluatedOrganization").getAsInt();
                String orgTypeEvaluated=evalTeam.getAsJsonPrimitive("orgTypeEvaluated").getAsString();
                int idCenter=evalTeam.getAsJsonPrimitive("idCenter").getAsInt();
                String illness=evalTeam.getAsJsonPrimitive("illness").getAsString();
                String externalConsultant=evalTeam.getAsJsonPrimitive("externalConsultant").getAsString();
                String patientName=evalTeam.getAsJsonPrimitive("patientName").getAsString();
                String relativeName=evalTeam.getAsJsonPrimitive("relativeName").getAsString();
                String observationsEnglish=evalTeam.getAsJsonPrimitive("observationsEnglish").getAsString();
                String observationsSpanish=evalTeam.getAsJsonPrimitive("observationsSpanish").getAsString();
                String observationsFrench=evalTeam.getAsJsonPrimitive("observationsFrench").getAsString();
                String observationsBasque=evalTeam.getAsJsonPrimitive("observationsBasque").getAsString();
                String observationsCatalan=evalTeam.getAsJsonPrimitive("observationsCatalan").getAsString();
                String observationsDutch=evalTeam.getAsJsonPrimitive("observationsDutch").getAsString();
                String observationsGalician=evalTeam.getAsJsonPrimitive("observationsGalician").getAsString();
                String observationsGerman=evalTeam.getAsJsonPrimitive("observationsGerman").getAsString();
                String observationsItalian=evalTeam.getAsJsonPrimitive("observationsItalian").getAsString();
                String observationsPortuguese=evalTeam.getAsJsonPrimitive("observationsPortuguese").getAsString();
                String evaluationDates=evalTeam.getAsJsonPrimitive("evaluationDates").getAsString();
                int completedEvaluationDates=evalTeam.getAsJsonPrimitive("completedEvaluationDates").getAsInt();
                int totalEvaluationDates=evalTeam.getAsJsonPrimitive("totalEvaluationDates").getAsInt();
                String profilePhoto=evalTeam.getAsJsonPrimitive("profilePhoto").getAsString();
                evaluatorTeams.add(new EvaluatorTeam(idEvaluatorTeam, creationDate, emailProfessional, emailResponsible, otherMembers, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, idCenter, illness, externalConsultant, patientName, relativeName, observationsEnglish, observationsSpanish, observationsFrench, observationsBasque, observationsCatalan, observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, evaluationDates, completedEvaluationDates, totalEvaluationDates, profilePhoto));
            }
            return evaluatorTeams;
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
     * Method that obtains all the evaluator teams by center
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param idCenter - Organization center
     * @param illness - Organization illness or syndrome
     * @return Evaluator teams list
     * */
    public static List<EvaluatorTeam> GetAllByCenter(int id, String orgType, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAllByCenter(id,orgType,idCenter,illness,Session.getInstance().getToken());
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
            List<EvaluatorTeam> evaluatorTeams=new ArrayList<>();
            for(JsonObject evalTeam:list){
                int idEvaluatorTeam=evalTeam.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                long creationDate=evalTeam.getAsJsonPrimitive("creationDate").getAsLong();
                String emailProfessional=evalTeam.getAsJsonPrimitive("emailProfessional").getAsString();
                String emailResponsible=evalTeam.getAsJsonPrimitive("emailResponsible").getAsString();
                String otherMembers=evalTeam.getAsJsonPrimitive("otherMembers").getAsString();
                int idEvaluatorOrganization=evalTeam.getAsJsonPrimitive("idEvaluatorOrganization").getAsInt();
                String orgTypeEvaluator=evalTeam.getAsJsonPrimitive("orgTypeEvaluator").getAsString();
                int idEvaluatedOrganization=evalTeam.getAsJsonPrimitive("idEvaluatedOrganization").getAsInt();
                String orgTypeEvaluated=evalTeam.getAsJsonPrimitive("orgTypeEvaluated").getAsString();
                String externalConsultant=evalTeam.getAsJsonPrimitive("externalConsultant").getAsString();
                String patientName=evalTeam.getAsJsonPrimitive("patientName").getAsString();
                String relativeName=evalTeam.getAsJsonPrimitive("relativeName").getAsString();
                String observationsEnglish=evalTeam.getAsJsonPrimitive("observationsEnglish").getAsString();
                String observationsSpanish=evalTeam.getAsJsonPrimitive("observationsSpanish").getAsString();
                String observationsFrench=evalTeam.getAsJsonPrimitive("observationsFrench").getAsString();
                String observationsBasque=evalTeam.getAsJsonPrimitive("observationsBasque").getAsString();
                String observationsCatalan=evalTeam.getAsJsonPrimitive("observationsCatalan").getAsString();
                String observationsDutch=evalTeam.getAsJsonPrimitive("observationsDutch").getAsString();
                String observationsGalician=evalTeam.getAsJsonPrimitive("observationsGalician").getAsString();
                String observationsGerman=evalTeam.getAsJsonPrimitive("observationsGerman").getAsString();
                String observationsItalian=evalTeam.getAsJsonPrimitive("observationsItalian").getAsString();
                String observationsPortuguese=evalTeam.getAsJsonPrimitive("observationsPortuguese").getAsString();
                String evaluationDates=evalTeam.getAsJsonPrimitive("evaluationDates").getAsString();
                int completedEvaluationDates=evalTeam.getAsJsonPrimitive("completedEvaluationDates").getAsInt();
                int totalEvaluationDates=evalTeam.getAsJsonPrimitive("totalEvaluationDates").getAsInt();
                String profilePhoto=evalTeam.getAsJsonPrimitive("profilePhoto").getAsString();
                evaluatorTeams.add(new EvaluatorTeam(idEvaluatorTeam, creationDate, emailProfessional, emailResponsible, otherMembers, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, idCenter, illness, externalConsultant, patientName, relativeName, observationsEnglish, observationsSpanish, observationsFrench, observationsBasque, observationsCatalan, observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, evaluationDates, completedEvaluationDates, totalEvaluationDates, profilePhoto));
            }
            return evaluatorTeams;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetAllByCenter(id,orgType,idCenter,illness);
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
     * Method that obtains all the evaluator teams by organization
     *
     * @param id - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @return Evaluator teams list
     * */
    public static List<EvaluatorTeam> GetAllByOrganization(int id, String orgType, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAllByOrganization(id,orgType,illness,Session.getInstance().getToken());
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
            List<EvaluatorTeam> evaluatorTeams=new ArrayList<>();
            for(JsonObject evalTeam:list){
                int idEvaluatorTeam=evalTeam.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                long creationDate=evalTeam.getAsJsonPrimitive("creationDate").getAsLong();
                String emailProfessional=evalTeam.getAsJsonPrimitive("emailProfessional").getAsString();
                String emailResponsible=evalTeam.getAsJsonPrimitive("emailResponsible").getAsString();
                String otherMembers=evalTeam.getAsJsonPrimitive("otherMembers").getAsString();
                int idEvaluatorOrganization=evalTeam.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                String orgTypeEvaluator=evalTeam.getAsJsonPrimitive("orgTypeEvaluator").getAsString();
                int idEvaluatedOrganization=evalTeam.getAsJsonPrimitive("idEvaluatedTeam").getAsInt();
                String orgTypeEvaluated=evalTeam.getAsJsonPrimitive("orgTypeEvaluated").getAsString();
                int idCenter=evalTeam.getAsJsonPrimitive("idCenter").getAsInt();
                String externalConsultant=evalTeam.getAsJsonPrimitive("externalConsultant").getAsString();
                String patientName=evalTeam.getAsJsonPrimitive("patientName").getAsString();
                String relativeName=evalTeam.getAsJsonPrimitive("relativeName").getAsString();
                String observationsEnglish=evalTeam.getAsJsonPrimitive("observationsEnglish").getAsString();
                String observationsSpanish=evalTeam.getAsJsonPrimitive("observationsSpanish").getAsString();
                String observationsFrench=evalTeam.getAsJsonPrimitive("observationsFrench").getAsString();
                String observationsBasque=evalTeam.getAsJsonPrimitive("observationsBasque").getAsString();
                String observationsCatalan=evalTeam.getAsJsonPrimitive("observationsCatalan").getAsString();
                String observationsDutch=evalTeam.getAsJsonPrimitive("observationsDutch").getAsString();
                String observationsGalician=evalTeam.getAsJsonPrimitive("observationsGalician").getAsString();
                String observationsGerman=evalTeam.getAsJsonPrimitive("observationsGerman").getAsString();
                String observationsItalian=evalTeam.getAsJsonPrimitive("observationsItalian").getAsString();
                String observationsPortuguese=evalTeam.getAsJsonPrimitive("observationsPortuguese").getAsString();
                String evaluationDates=evalTeam.getAsJsonPrimitive("evaluationDates").getAsString();
                int completedEvaluationDates=evalTeam.getAsJsonPrimitive("completedEvaluationDates").getAsInt();
                int totalEvaluationDates=evalTeam.getAsJsonPrimitive("totalEvaluationDates").getAsInt();
                String profilePhoto=evalTeam.getAsJsonPrimitive("profilePhoto").getAsString();
                evaluatorTeams.add(new EvaluatorTeam(idEvaluatorTeam, creationDate, emailProfessional, emailResponsible, otherMembers, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, idCenter, illness, externalConsultant, patientName, relativeName, observationsEnglish, observationsSpanish, observationsFrench, observationsBasque, observationsCatalan, observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, evaluationDates, completedEvaluationDates, totalEvaluationDates, profilePhoto));
            }
            return evaluatorTeams;
        } catch (InterruptedException | ExecutionException e) {
            if(e.getCause() instanceof SocketTimeoutException){
                numAttempts++;
                if(numAttempts<3) {
                    return GetAllByOrganization(id,orgType,illness);
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
     * Method that appends a new evaluator team to the database
     *
     * @param evaluatorTeam - Evaluator team
     * @return Evaluator team if sucess, null if not
     * */
    public static EvaluatorTeam Create(EvaluatorTeam evaluatorTeam){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Create(evaluatorTeam,Session.getInstance().getToken());
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


    /**
     * Method that updates an existant evaluator team
     *
     * @param id - Evaluator team identifier
     * @param idEvaluatorOrg - Evaluator organization identifier (who does the indicators evaluations)
     * @param orgTypeEvaluator - Evaluator organization type (who does the indicators evaluations)
     * @param idEvaluatedOrg - Evaluated organization identifier (who receives the indicators evaluations)
     * @param orgTypeEvaluated - Evaluated organization type (who receives the indicators evaluations)
     * @param idCenter - Evaluated organization center (who receives the indicators evaluations)
     * @param illness - Both organizations illness or syndrome
     * @param evaluatorTeam - Evaluator team
     * @return Updated evaluator team if sucess, null if not
     * */
    public static EvaluatorTeam Update(int id, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness, EvaluatorTeam evaluatorTeam){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Update(id,idEvaluatorOrg,orgTypeEvaluator,idEvaluatedOrg,orgTypeEvaluated,idCenter,illness,evaluatorTeam,Session.getInstance().getToken());
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

    /**
     * Method that deletes an evaluator team
     * @param id - Evaluator team identifier
     * @param idEvaluatorOrg - Evaluator organization identifier (who does the indicators evaluations)
     * @param orgTypeEvaluator - Evaluator organization type (who does the indicators evaluations)
     * @param idEvaluatedOrg - Evaluated organization identifier (who receives the indicators evaluations)
     * @param orgTypeEvaluated - Evaluated organization type (who receives the indicators evaluations)
     * @param idCenter - Evaluated organization center (who receives the indicators evaluations)
     * @param illness - Both organizations illness or syndrome
     * @return Evaluator team if sucess, null if not
     * */
    public static EvaluatorTeam Delete(int id, int idEvaluatorOrg, String orgTypeEvaluator, int idEvaluatedOrg, String orgTypeEvaluated, int idCenter, String illness){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<EvaluatorTeam> callable = new Callable<EvaluatorTeam>() {
            @Override
            public EvaluatorTeam call() throws Exception {
                Call<EvaluatorTeam> call = api.Delete(id,idEvaluatorOrg,orgTypeEvaluator,idEvaluatedOrg,orgTypeEvaluated,idCenter,illness,Session.getInstance().getToken());
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
