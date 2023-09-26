package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
;


public class EvaluatorTeam implements Serializable {

    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;
    @SerializedName("creationDate")
    public long creationDate;

    @SerializedName("idEvaluatorOrganization")
    public int idEvaluatorOrganization;

    @SerializedName("orgTypeEvaluator")
    public String orgTypeEvaluator;
    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;
    
    @SerializedName("illness")
    public String illness;
    @SerializedName("patientName")
    public String patientName="";
    @SerializedName("relativeName")
    public String relativeName="";
    @SerializedName("externalConsultant")
    public String externalConsultant;
    @SerializedName("emailResponsible")
    public String emailResponsible;
    @SerializedName("emailProfessional")
    public String emailProfessional;
    @SerializedName("otherMembers")
    public String otherMembers;
    @SerializedName("idCenter")
    public int idCenter;

    @SerializedName("evaluationDate1")
    public long evaluationDate1;

    @SerializedName("evaluationDate2")
    public long evaluationDate2;

    @SerializedName("evaluationDate3")
    public long evaluationDate3;

    @SerializedName("evaluationDate4")
    public long evaluationDate4;

    @SerializedName("observationsEnglish")
    public String observationsEnglish;
    @SerializedName("observationsSpanish")
    public String observationsSpanish;
    @SerializedName("observationsFrench")
    public String observationsFrench;
    @SerializedName("observationsBasque")
    public String observationsBasque;
    @SerializedName("observationsCatalan")
    public String observationsCatalan;
    @SerializedName("observationsDutch")
    public String observationsDutch;
    @SerializedName("observationsGalician")
    public String observationsGalician;
    @SerializedName("observationsGerman")
    public String observationsGerman;
    @SerializedName("observationsItalian")
    public String observationsItalian;
    @SerializedName("observationsPortuguese")
    public String observationsPortuguese;



    public EvaluatorTeam(int idEvaluatorTeam, long creationDate, String emailProfessional, String emailResponsible, String otherMembers, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, int idCenter, String illness, String externalConsultant, String patientName, String relativeName, long evaluationDate1, long evaluationDate2, long evaluationDate3, long evaluationDate4, String observationsSpanish, String observationsEnglish, String observationsFrench, String observationsBasque, String observationsCatalan, String observationsDutch, String observationsGalician, String observationsGerman, String observationsItalian, String observationsPortuguese){
        setIdEvaluatorTeam(idEvaluatorTeam);
        setCreationDate(creationDate);
        setEmailProfessional(emailProfessional);
        setEmailResponsible(emailResponsible);
        setOtherMembers(otherMembers);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgTypeEvaluator(orgTypeEvaluator);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIdCenter(idCenter);
        setIllness(illness);
        setExternalConsultant(externalConsultant);
        setPatient_name(patientName);
        setRelative_name(relativeName);
        setEvaluationDate1(evaluationDate1);
        setEvaluationDate2(evaluationDate2);
        setEvaluationDate3(evaluationDate3);
        setEvaluationDate4(evaluationDate4);
        setObservationsEnglish(observationsEnglish);
        setObservationsSpanish(observationsSpanish);
        setObservationsFrench(observationsFrench);
        setObservationsBasque(observationsBasque);
        setObservationsCatalan(observationsCatalan);
        setObservationsDutch(observationsDutch);
        setObservationsGalician(observationsGalician);
        setObservationsGerman(observationsGerman);
        setObservationsItalian(observationsItalian);
        setObservationsPortuguese(observationsPortuguese);
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

    public String getExternalConsultant() {
        return externalConsultant;
    }

    public void setExternalConsultant(String externalConsultant) {
        this.externalConsultant = externalConsultant;
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

    public int getIdEvaluatorOrganization() {
        return idEvaluatorOrganization;
    }

    public void setIdEvaluatorOrganization(int idEvaluatorOrganization) {
        this.idEvaluatorOrganization = idEvaluatorOrganization;
    }

    public String getOrgTypeEvaluator() {
        return orgTypeEvaluator;
    }

    public void setOrgTypeEvaluator(String orgTypeEvaluator) {
        this.orgTypeEvaluator = orgTypeEvaluator;
    }

    public int getIdEvaluatedOrganization() {
        return idEvaluatedOrganization;
    }

    public void setIdEvaluatedOrganization(int idEvaluatedOrganization) {
        this.idEvaluatedOrganization = idEvaluatedOrganization;
    }

    public String getOrgTypeEvaluated() {
        return orgTypeEvaluated;
    }

    public void setOrgTypeEvaluated(String orgTypeEvaluated) {
        this.orgTypeEvaluated = orgTypeEvaluated;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getOtherMembers() {
        return otherMembers;
    }

    public void setOtherMembers(String otherMembers) {
        this.otherMembers = otherMembers;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
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

    public String getObservationsEnglish() {
        return observationsEnglish;
    }

    public void setObservationsEnglish(String observationsEnglish) {
        this.observationsEnglish = observationsEnglish;
    }

    public String getObservationsSpanish() {
        return observationsSpanish;
    }

    public void setObservationsSpanish(String observationsSpanish) {
        this.observationsSpanish = observationsSpanish;
    }

    public String getObservationsFrench() {
        return observationsFrench;
    }

    public void setObservationsFrench(String observationsFrench) {
        this.observationsFrench = observationsFrench;
    }

    public String getObservationsBasque() {
        return observationsBasque;
    }

    public void setObservationsBasque(String observationsBasque) {
        this.observationsBasque = observationsBasque;
    }

    public String getObservationsCatalan() {
        return observationsCatalan;
    }

    public void setObservationsCatalan(String observationsCatalan) {
        this.observationsCatalan = observationsCatalan;
    }

    public String getObservationsDutch() {
        return observationsDutch;
    }

    public void setObservationsDutch(String observationsDutch) {
        this.observationsDutch = observationsDutch;
    }

    public String getObservationsGalician() {
        return observationsGalician;
    }

    public void setObservationsGalician(String observationsGalician) {
        this.observationsGalician = observationsGalician;
    }

    public String getObservationsGerman() {
        return observationsGerman;
    }

    public void setObservationsGerman(String observationsGerman) {
        this.observationsGerman = observationsGerman;
    }

    public String getObservationsItalian() {
        return observationsItalian;
    }

    public void setObservationsItalian(String observationsItalian) {
        this.observationsItalian = observationsItalian;
    }

    public String getObservationsPortuguese() {
        return observationsPortuguese;
    }

    public void setObservationsPortuguese(String observationsPortuguese) {
        this.observationsPortuguese = observationsPortuguese;
    }
}
