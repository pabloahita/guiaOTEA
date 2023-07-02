package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;


public class EvaluatorTeam implements Serializable {

    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;
    @SerializedName("creationDate")
    public Timestamp creationDate;

    @SerializedName("idOrganization")
    public int idOrganization;

    @SerializedName("orgType")
    public String orgType;
    
    @SerializedName("illness")
    public String illness;
    @SerializedName("patientName")
    public String patientName="";
    @SerializedName("relativeName")
    public String relativeName="";
    @SerializedName("emailConsultant")
    public String emailConsultant;
    @SerializedName("emailResponsible")
    public String emailResponsible;
    @SerializedName("emailProfessional")
    public String emailProfessional;


    public EvaluatorTeam(int idEvaluatorTeam, Timestamp creationDate, int idOrganization, String orgType, String illness, String emailConsultant, String emailProfessional, String emailResponsible, String patientName, String relativeName){
        setIdEvaluatorTeam(idEvaluatorTeam);
        setCreationDate(creationDate);
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
        setEmailConsultant(emailConsultant);
        setEmailProfessional(emailProfessional);
        setEmailResponsible(emailResponsible);
        setPatient_name(patientName);
        setRelative_name(relativeName);
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdEvaluatorTeam() {
        return idEvaluatorTeam;
    }

    public void setIdEvaluatorTeam(int idEvaluatorTeam) {
        this.idEvaluatorTeam = idEvaluatorTeam;
    }

    public String getPatient_name() {
        return patientName;
    }

    public void setPatient_name(String patientName) {
        this.patientName = patientName;
    }

    public String getRelative_name() {
        return relativeName;
    }

    public void setRelative_name(String relativeName) {
        this.relativeName = relativeName;
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
