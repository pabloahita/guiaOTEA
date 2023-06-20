package cli.user;

import java.io.IOException;

import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.Organization;
import cli.organization.EvaluatedOrganization;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.OrganizationsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrganizationUser extends AbstractUser{

    private Organization organization;


    public OrganizationUser(String first_name, String last_name, String email, String password, long telephone, int idOrganization, String orgType, String illness) {
        super(first_name, last_name, email, password, telephone,idOrganization,orgType,illness);
        try {
            obtainOrganization(idOrganization,orgType,illness);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OrganizationUser(String first_name, String last_name, String email, String password, long telephone, EvaluatedOrganization organization) {
        super(first_name, last_name, email, password, telephone,organization.getIdOrganization(),organization.getOrganizationType(),organization.getIllness());
        setOrganization(organization);
    }

    private void obtainOrganization(int idOrganization, String orgType, String illness) throws IOException {
        ConnectionClient cli=new ConnectionClient();
        Retrofit retrofit=cli.getRetrofit();
        OrganizationsApi api=retrofit.create(OrganizationsApi.class);
        Call<Organization> call=api.Get(idOrganization,orgType,illness);
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Organization> response=call.execute();
                        if(response.isSuccessful()){
                            Organization aux=response.body();
                            if(this instanceof EvaluatedOrganizationUser && aux instanceof AutisticEvaluatedOrganization){
                                organization=new AutisticEvaluatedOrganization(aux.getIdOrganization(),orgType,aux.getIllness(),aux.getName(),aux.getIdAddress(),aux.getTelephone(),aux.getEmail(),aux.getInformation(),((EvaluatedOrganization) aux).getEmailOrgPrincipal(),((EvaluatedOrganization) aux).getEmailOrgConsultant());
                            }
                            else if(this instanceof EvaluatorOrganizationUser && aux instanceof AutisticEvaluatorOrganization){
                                organization=new AutisticEvaluatorOrganization(aux.getIdOrganization(),orgType,aux.getIllness(),aux.getName(),aux.getIdAddress(),aux.getTelephone(),aux.getEmail(),aux.getInformation());
                            }
                            return organization;
                        } else {
                            throw new IOException("Error: " + response.code() + " " + response.message());
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user-> {
                    System.out.println("Evaluated user correctly obtained");
                }, error -> {
                    System.out.println(error.toString());
                });
    }

    public void setOrganization(Organization organization){
        this.organization=organization;
    }

    public Organization getOrganization(){
        return organization;
    }
}
