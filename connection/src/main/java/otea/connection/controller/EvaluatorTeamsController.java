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

    /**
     * Method that obtains all the evaluator teams
     *
     * @return Evaluator teams list
     * */
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
