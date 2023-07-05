package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
;


public class EvaluatorTeam implements Serializable {

    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;
    @SerializedName("creationDate")
    public long creationDate;

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

    @SerializedName("evaluationDate1")
    public long evaluationDate1;

    @SerializedName("evaluationDate2")
    public long evaluationDate2;

    @SerializedName("evaluationDate3")
    public long evaluationDate3;

    @SerializedName("evaluationDate4")
    public long evaluationDate4;

    @SerializedName("observations")
    public String observations;



    public EvaluatorTeam(int idEvaluatorTeam, long creationDate, int idOrganization, String orgType, String illness, String emailConsultant, String emailProfessional, String emailResponsible, String patientName, String relativeName, long evaluationDate1, long evaluationDate2, long evaluationDate3, long evaluationDate4, String observations){
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
        setEvaluationDate1(evaluationDate1);
        setEvaluationDate2(evaluationDate2);
        setEvaluationDate3(evaluationDate3);
        setEvaluationDate4(evaluationDate4);
        setObservations(observations);
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
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


    public long getEvaluationDate1() {
        return evaluationDate1;
    }

    public void setEvaluationDate1(long evaluationDate1) {
        this.evaluationDate1 = evaluationDate1;
    }

    public long getEvaluationDate2() {
        return evaluationDate2;
    }

    public void setEvaluationDate2(long evaluationDate2) {
        this.evaluationDate2 = evaluationDate2;
    }

    public long getEvaluationDate3() {
        return evaluationDate3;
    }

    public void setEvaluationDate3(long evaluationDate3) {
        this.evaluationDate3 = evaluationDate3;
    }

    public long getEvaluationDate4() {
        return evaluationDate4;
    }

    public void setEvaluationDate4(long evaluationDate4) {
        this.evaluationDate4 = evaluationDate4;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
