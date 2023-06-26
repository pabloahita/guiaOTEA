package otea.connection.caller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.Organization;
import otea.connection.ConnectionClient;
import otea.connection.api.OrganizationsApi;
import retrofit2.Call;
import retrofit2.Response;

public class OrganizationsCaller {

    private static OrganizationsCaller instance;

    private static OrganizationsApi api;

    private OrganizationsCaller(){
        api=ConnectionClient.getInstance().getRetrofit().create(OrganizationsApi.class);
    }

    public static OrganizationsCaller getInstance(){
        if(instance==null){
            synchronized (OrganizationsCaller.class){
                if(instance==null){
                    instance=new OrganizationsCaller();
                }
            }
        }
        return instance;
    }
    public static Organization obtainOrganization(int idOrganization, String orgType, String illness) {

        Call<Organization> call=api.Get(idOrganization,orgType,illness);
        AsyncTask<Void, Void, Organization> asyncTask = new AsyncTask<Void, Void, Organization>() {
            Organization resultOrganization = null;
            @Override
            protected Organization doInBackground(Void... voids) {
                try {
                    Response<Organization> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        throw new IOException("Error: " + response.code() + " " + response.message());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Organization organization) {
                resultOrganization=organization;
            }

        };
        asyncTask.execute();
        try {
            Organization organization=asyncTask.get();
            if(organization!=null){
                if(organization.getOrganizationType().equals("EVALUATED") && organization.getIllness().equals("AUTISM")){
                    return new AutisticEvaluatedOrganization(idOrganization,orgType,illness,organization.getName(),organization.getIdAddress(),organization.getTelephone(),organization.getEmail(),organization.getInformation(),organization.getEmailOrgPrincipal(),organization.getEmailOrgConsultant());
                }
                else if(organization.getOrganizationType().equals("EVALUATED") && organization.getIllness().equals("AUTISM")){
                    return new AutisticEvaluatorOrganization(organization.getIdOrganization(),orgType,organization.getIllness(),organization.getName(),organization.getIdAddress(),organization.getTelephone(),organization.getEmail(),organization.getInformation());
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return null;
    }

}
