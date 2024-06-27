package otea.connection.controller;


import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cli.organization.Organization;
import cli.organization.data.Center;
import misc.ListCallback;
import otea.connection.ConnectionClient;
import otea.connection.api.CentersApi;
import retrofit2.Call;
import retrofit2.Response;
import session.Session;

/**
 * Controller class for Centers operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class CentersController {

    /**Centers api to connect to the center*/
    private static CentersApi api;

    /**Controller instance*/
    private static CentersController instance;

    private static int numAttempts=0;

    /**
     * Class constructor
     * */
    private CentersController(){
        api= ConnectionClient.getInstance().getRetrofit().create(CentersApi.class);
    }

    /**
     * Method that obtains the singleton instance of the controller
     *
     * @return Instance of the controller
     * */
    public static CentersController getInstance(){
        if(instance==null){
            synchronized (CentersController.class){
                if(instance==null){
                    instance=new CentersController();
                }
            }
        }
        return instance;
    }

    /**Refresh API*/
    public static void refreshApi(){
        instance=new CentersController();
    }

    /**
     * Method that obtains all the centers
     *
     * @return Centers list
     * */
    public static List<Center> GetAll(){
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
            List<Center> centers=new ArrayList<>();
            for(JsonObject center:list){
                int idOrganization=center.getAsJsonPrimitive("idOrganization").getAsInt();
                String orgType=center.getAsJsonPrimitive("orgType").getAsString();
                String illness=center.getAsJsonPrimitive("illness").getAsString();
                int idCenter=center.getAsJsonPrimitive("idCenter").getAsInt();
                String descriptionEnglish=center.getAsJsonPrimitive("descriptionEnglish").getAsString();
                String descriptionSpanish=center.getAsJsonPrimitive("descriptionSpanish").getAsString();
                String descriptionFrench=center.getAsJsonPrimitive("descriptionFrench").getAsString();
                String descriptionBasque=center.getAsJsonPrimitive("descriptionBasque").getAsString();
                String descriptionCatalan=center.getAsJsonPrimitive("descriptionCatalan").getAsString();
                String descriptionDutch=center.getAsJsonPrimitive("descriptionDutch").getAsString();
                String descriptionGalician=center.getAsJsonPrimitive("descriptionGalician").getAsString();
                String descriptionGerman=center.getAsJsonPrimitive("descriptionGerman").getAsString();
                String descriptionItalian=center.getAsJsonPrimitive("descriptionItalian").getAsString();
                String descriptionPortuguese=center.getAsJsonPrimitive("descriptionPortuguese").getAsString();
                int idAddress=center.getAsJsonPrimitive("idAddress").getAsInt();
                String telephone=center.getAsJsonPrimitive("telephone").getAsString();
                String email=center.getAsJsonPrimitive("email").getAsString();
                String profilePhoto=center.getAsJsonPrimitive("profilePhoto").getAsString();
                centers.add(new Center(idOrganization, orgType, illness, idCenter, descriptionEnglish, descriptionSpanish, descriptionFrench, descriptionBasque, descriptionCatalan, descriptionDutch, descriptionGalician, descriptionGerman, descriptionItalian, descriptionPortuguese, idAddress, telephone, email, profilePhoto));
            }
            return centers;
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
     * Method that obtains all the centers of an organization
     *
     * @param organization - Organization
     * @return All centers of an organization
     * */
    public static void GetAllByOrganization(Organization organization, ListCallback callback){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<JsonObject>> callable = new Callable<List<JsonObject>>() {
            @Override
            public List<JsonObject> call() throws Exception {
                Call<List<JsonObject>> call = api.GetAllByOrganization(organization.getIdOrganization(),organization.getOrgType(),organization.getIllness(),Session.getInstance().getToken());
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
            } finally {
                executor.shutdown();
            }
        });
    }

    /**
     * Method that obtains a center using its identifier and the belonging organization
     *
     * @param organization - Organization
     * @param idCenter - Center identifier
     * @return Center if found on database, null if not
     * */
    public static Center Get(Organization organization, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Get(organization.getIdOrganization(),organization.getOrgType(),organization.getIllness(),idCenter,Session.getInstance().getToken());
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that appends a new center to the database
     *
     * @param center - Center to append
     * @return Center appended if sucess, null if not
     * */
    public static Center Create(Center center) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Create(center,Session.getInstance().getToken());
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that updates an existing center
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * @param center - Center
     * @return Updated center if success, null if not
     * */
    public static Center Update(int idOrganization, String orgType, String illness,  int idCenter, Center center){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Update(idOrganization,orgType,illness,idCenter,center,Session.getInstance().getToken());
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that deletes a center
     *
     * @param idOrganization - Organization identifier
     * @param orgType - Organization type
     * @param illness - Organization illness or syndrome
     * @param idCenter - Center identifier
     * @return Deleted center if success, null if not
     * */
    public static Center Delete(int idOrganization, String orgType, String illness, int idCenter){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Center> callable = new Callable<Center>() {
            @Override
            public Center call() throws Exception {
                Call<Center> call = api.Delete(idOrganization,orgType,illness,idCenter,Session.getInstance().getToken());
                Response<Center> response = call.execute();
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    throw new IOException("Error: " + response.code() + " " + response.message());
                }
            }
        };
        try {
            Future<Center> future = executor.submit(callable);
            Center result = future.get();
            executor.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
