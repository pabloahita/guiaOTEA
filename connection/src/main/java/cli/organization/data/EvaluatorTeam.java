package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.EvaluatedOrganization;
import cli.organization.EvaluatorOrganization;
import cli.organization.Organization;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;
import cli.user.User;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.OrganizationsApi;
import otea.connection.UsersApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EvaluatorTeam {

    @SerializedName("idEvaluatorTeam")
    private int id;
    @SerializedName("creationDate")
    private Date creation_date;

    @SerializedName("idOrganization")
    private int idOrganization;
    @SerializedName("orgType")
    private String orgType;
    @SerializedName("illness")
    private String illness;
    @SerializedName("patientName")
    private String patient_name="";
    @SerializedName("relativeName")
    private String relative_name="";
    @SerializedName("emailConsultant")
    private String emailConsultant;
    @SerializedName("emailResponsible")
    private String emailResponsible;
    @SerializedName("emailProfessional")
    private String emailProfessional;
    private EvaluatorOrganizationUser external_consultant;
    private EvaluatedOrganizationUser professional;
    private EvaluatedOrganizationUser direct_attendance_responsible;
    private List<EvaluatorOrganizationUser> members;
    private EvaluatorOrganization organization;


    public EvaluatorTeam(int id, Date creation_date, int idOrganization, String orgType, String illness, String emailConsultant, String emailProfessional, String emailResponsible, String pacient_name, String relative_name){
        setId(id);
        setCreationDate(creation_date);
        setIdOrganization(idOrganization);
        setEmailConsultant(emailConsultant);
        setEmailProfessional(emailProfessional);
        setEmailResponsible(emailResponsible);
        try{
            obtainOrganization(idOrganization,orgType,illness);
            external_consultant= (EvaluatorOrganizationUser) obtainUser(emailConsultant);
            professional= (EvaluatedOrganizationUser) obtainUser(emailProfessional);
            direct_attendance_responsible= (EvaluatedOrganizationUser) obtainUser(emailResponsible);
            obtainMembers();
        }catch(IOException e){}
        //obtainExternalConsultant(emailExternalConsultant,idOrganization,illness);
        //obtainEvalTeamMembers(id,idOrganization,illness);

    }



    public void obtainOrganization(int idOrganization, String orgType, String illness) throws IOException{
        ConnectionClient cli=new ConnectionClient();
        Retrofit retrofit=cli.getRetrofit();
        OrganizationsApi api=retrofit.create(OrganizationsApi.class);
        Call<Organization> call=api.Get(idOrganization,orgType,illness);
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Organization> response=call.execute();
                        if(response.isSuccessful()){
                            Organization aux=response.body();
                            organization=new AutisticEvaluatorOrganization(aux.getIdOrganization(),orgType,aux.getIllness(),aux.getName(),aux.getIdAddress(),aux.getTelephone(),aux.getEmail(),aux.getInformation());
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

    public User obtainUser(String email){
        ConnectionClient cli=new ConnectionClient();
        Retrofit retrofit=cli.getRetrofit();
        UsersApi api=retrofit.create(UsersApi.class);
        Call<User> call=api.Get(email);
        User[] userObtained=new User[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<User> response=call.execute();
                        if(response.isSuccessful()){
                            User aux=response.body();
                            if(aux.getOrgType()=="EVALUATED"){
                                userObtained[0]=new EvaluatedOrganizationUser(aux.getFirstName(),aux.getLastName(),aux.getEmail(),aux.getPassword(),aux.getTelephone(),aux.getIdOrganization(),aux.getOrgType(),aux.getIllness());
                            }
                            if(aux.getOrgType()=="EVALUATOR"){
                                userObtained[0]=new EvaluatorOrganizationUser(aux.getFirstName(),aux.getLastName(),aux.getEmail(),aux.getPassword(),aux.getTelephone(),aux.getIdOrganization(),aux.getOrgType(),aux.getIllness());
                            }
                            return userObtained[0];
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
                    System.out.println("User correctly obtained");
                }, error -> {
                    System.out.println(error.toString());
                });
        return userObtained[0];
    }

    public void obtainMembers(){}
    public Date getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }

    public EvaluatorOrganizationUser getExternalConsultant() {
        return external_consultant;
    }

    public void setExternalConsultant(EvaluatorOrganizationUser external_consultant) {
        this.external_consultant = external_consultant;
    }

    public List<EvaluatorOrganizationUser> getMembers() {
        return members;
    }

    public void setMembers(List<EvaluatorOrganizationUser> members) {
        this.members = members;
    }


    public EvaluatorOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(EvaluatorOrganization organization) {
        this.organization = organization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getRelative_name() {
        return relative_name;
    }

    public void setRelative_name(String relative_name) {
        this.relative_name = relative_name;
    }

    public String getEmailConsultant() {
        return emailConsultant;
    }

    public void setEmailConsultant(String emailConsultant) {
        this.emailConsultant = emailConsultant;
    }

    public String getEmailResponsible() {
        return emailResponsible;
    }

    public void setEmailResponsible(String emailResponsible) {
        this.emailResponsible = emailResponsible;
    }

    public String getEmailProfessional() {
        return emailProfessional;
    }

    public void setEmailProfessional(String emailProfessional) {
        this.emailProfessional = emailProfessional;
    }

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }
}
