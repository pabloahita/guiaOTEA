package cli.organization;



import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.organization.data.Center;
import cli.user.EvaluatedOrganizationUser;
import cli.user.User;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.UsersApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class AbstractEvaluatedOrganization extends AbstractOrganization implements EvaluatedOrganization {

    private EvaluatedOrganizationUser organization_principal;
    private EvaluatedOrganizationUser organization_consultant;

    private String email_organization_principal;
    private String email_organization_consultant;
    private List<IndicatorsEvaluation> evaluations;
    protected List<Indicator> indicators;

    private List<Center> centerList;
    public AbstractEvaluatedOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant) {
        super(idOrganization,orgType,illness,name,idAddress,telephone,email,information,emailOrgPrincipal,emailOrgConsultant);
        try {
            organization_principal = obtainUser(emailOrgPrincipal);
            organization_consultant = obtainUser(emailOrgConsultant);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setCenterList(new LinkedList<Center>());
    }

    private EvaluatedOrganizationUser obtainUser(String email) throws IOException {
        ConnectionClient cli = new ConnectionClient();
        Retrofit retrofit = cli.getRetrofit();
        UsersApi api = retrofit.create(UsersApi.class);
        Call<User> call = api.Get(email);
        EvaluatedOrganizationUser[] evalUser=new EvaluatedOrganizationUser[1];
         Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<User> response = call.execute();
                        if (response.isSuccessful()) {
                            User aux=response.body();
                            evalUser[0]=new EvaluatedOrganizationUser(aux.getFirstName(),aux.getLastName(),aux.getEmail(),aux.getPassword(),aux.getTelephone(),this);
                            return evalUser[0];
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
        return evalUser[0];
    }

    public List<IndicatorsEvaluation> getEvaluations() {
        return evaluations;
    }

    @Override
    public String getEmailOrgPrincipal(){return email_organization_principal;}
    @Override
    public String getEmailOrgConsultant(){return email_organization_consultant;}

    /*public void setEvaluations(){
        ResultSet rs=null;
        PreparedStatement ps = null;
        this.evaluations=new LinkedList<IndicatorsEvaluation>();
        try{
            ps=super.getConnection().prepareStatement("SELECT * FROM INDICATORSEVALUATIONS WHERE evaluatedOrganizationId=? AND illness=\"?\"");
            ps.setInt(1,super.getIdOrganization());
            ps.setString(2,getIllness());
            rs=ps.executeQuery();
            while(rs.next()){
                Date evaluationDate=rs.getDate("evaluationDate");
                int evaluatorTeamId=rs.getInt("evaluatorTeamId");
                int evaluatorOrganizationId=rs.getInt("evaluatorOrganizationId");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }*/

    public void addEvaluation(IndicatorsEvaluation evaluation){
        this.evaluations.add(evaluation);
        //ConnectionClient.addEvaluation(evaluation);
    }
    @Override
    public List<Indicator> getIndicators(){
        return indicators;
    }

    @Override
    public List<Center> getCenterList(){return centerList;}

    @Override
    public void setCenterList(List<Center> centerList){this.centerList=centerList;}

    @Override
    public void addCenter(Center center){centerList.add(center);}
    @Override
    public void removeCenter(Center center){centerList.remove(center);}

}
