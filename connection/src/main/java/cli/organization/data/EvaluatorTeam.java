package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Model class for evaluator teams
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public class EvaluatorTeam implements Serializable {

    /**Evaluator team identifier*/
    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;

    /**Evaluator team creation date in timestamp*/
    @SerializedName("creationDate")
    public long creationDate;

    /**Evaluator team evaluator organization identifier*/
    @SerializedName("idEvaluatorOrganization")
    public int idEvaluatorOrganization;

    /**Evaluator team evaluator organization type*/
    @SerializedName("orgTypeEvaluator")
    public String orgTypeEvaluator;

    /**Evaluator team evaluated organization identifier*/
    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;

    /**Evaluator team evaluated organization type*/
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;

    /**Evaluator organization illness or syndrome*/
    @SerializedName("illness")
    public String illness;

    /**Patient name or names*/
    @SerializedName("patientName")
    public String patientName="";

    /**Patient relatives names*/
    @SerializedName("relativeName")
    public String relativeName="";

    /**External consultant name*/
    @SerializedName("externalConsultant")
    public String externalConsultant;

    /**Responsible email*/
    @SerializedName("emailResponsible")
    public String emailResponsible;

    /**Direct attendance professional email*/
    @SerializedName("emailProfessional")
    public String emailProfessional;

    /**Other members names joint by commas*/
    @SerializedName("otherMembers")
    public String otherMembers;

    /**Center identifier*/
    @SerializedName("idCenter")
    public int idCenter;

    /**Evaluator team observations in English*/
    @SerializedName("observationsEnglish")
    public String observationsEnglish;

    /**Evaluator team observations in Spanish*/
    @SerializedName("observationsSpanish")
    public String observationsSpanish;

    /**Evaluator team observations in French*/
    @SerializedName("observationsFrench")
    public String observationsFrench;

    /**Evaluator team observations in Basque*/
    @SerializedName("observationsBasque")
    public String observationsBasque;

    /**Evaluator team observations in Catalan*/
    @SerializedName("observationsCatalan")
    public String observationsCatalan;

    /**Evaluator team observations in Dutch*/
    @SerializedName("observationsDutch")
    public String observationsDutch;

    /**Evaluator team observations in Galician*/
    @SerializedName("observationsGalician")
    public String observationsGalician;

    /**Evaluator team observations in German*/
    @SerializedName("observationsGerman")
    public String observationsGerman;

    /**Evaluator team observations in Italian*/
    @SerializedName("observationsItalian")
    public String observationsItalian;

    /**Evaluator team observations in Portuguese*/
    @SerializedName("observationsPortuguese")
    public String observationsPortuguese;

    /**Evaluation dates sepparated by commas*/
    @SerializedName("evaluationDates")
    public String evaluationDates;

    /**Number of completed evaluation dates*/
    @SerializedName("completedEvaluationDates")
    public int completedEvaluationDates;

    /**Number of total evaluation dates*/
    @SerializedName("totalEvaluationDates")
    public int totalEvaluationDates;


    /**
     * Class constructor
     *
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param creationDate - Evaluator team creation date in timestamp
     * @param emailProfessional - Direct attendance professional email
     * @param emailResponsible - Responsible email
     * @param otherMembers - Other members names joint by commas
     * @param idEvaluatorOrganization - Evaluator team evaluator organization identifier
     * @param orgTypeEvaluator - Evaluator team evaluator organization type
     * @param idEvaluatedOrganization - Evaluator team evaluated organization identifier
     * @param orgTypeEvaluated - Evaluator team evaluated organization type
     * @param idCenter - Center identifier
     * @param illness - Evaluator organization illness or syndrome
     * @param externalConsultant - External consultant name
     * @param patientName - Patient name or names
     * @param relativeName - Patient relatives names
     * @param observationsEnglish - Evaluator team observations in English
     * @param observationsSpanish - Evaluator team observations in Spanish
     * @param observationsFrench - Evaluator team observations in French
     * @param observationsBasque - Evaluator team observations in Basque
     * @param observationsCatalan - Evaluator team observations in Catalan
     * @param observationsDutch - Evaluator team observations in Dutch
     * @param observationsGalician - Evaluator team observations in Galician
     * @param observationsGerman - Evaluator team observations in German
     * @param observationsItalian - Evaluator team observations in Italian
     * @param observationsPortuguese - Evaluator team observations in Portuguese
     * @param evaluationDates - Evaluation dates sepparated by commas
     * @param completedEvaluationDates - Number of completed evaluation dates
     * @param totalEvaluationDates - Number of total evaluation dates
     * */
    public EvaluatorTeam(int idEvaluatorTeam, long creationDate, String emailProfessional, String emailResponsible, String otherMembers, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, int idCenter, String illness, String externalConsultant, String patientName, String relativeName, String observationsEnglish, String observationsSpanish, String observationsFrench, String observationsBasque, String observationsCatalan, String observationsDutch, String observationsGalician, String observationsGerman, String observationsItalian, String observationsPortuguese, String evaluationDates, int completedEvaluationDates, int totalEvaluationDates){
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
        setEvaluationDates(evaluationDates);
        setCompletedEvaluationDates(completedEvaluationDates);
        setTotalEvaluationDates(totalEvaluationDates);
    }

    /**
     * Method that obtains the creation date in timestamp
     *
     * @return Creation date in timestamp
     * */
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * Method that sets the new creation date in timestamp
     *
     * @param creationDate - Creation date in timestamp
     * */
    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Method that obtains the evaluator team identifier
     *
     * @return Evaluator team identifier
     * */
    public int getIdEvaluatorTeam() {
        return idEvaluatorTeam;
    }

    /**
     * Method that sets the new evaluator team identifier
     *
     * @param idEvaluatorTeam - Evaluator team identifier
     * */
    public void setIdEvaluatorTeam(int idEvaluatorTeam) {
        this.idEvaluatorTeam = idEvaluatorTeam;
    }

    /**
     * Method that obtains the patient name or names
     *
     * @return Patient name or names
     * */
    public String getPatient_name() {
        return patientName;
    }

    /**
     * Method that sets the new patient name or names
     *
     * @param patientName - Patient name or names
     * */
    public void setPatient_name(String patientName) {
        this.patientName = patientName;
    }

    /**
     * Method that obtains the patient relative names
     *
     * @return Patient relative names
     * */
    public String getRelative_name() {
        return relativeName;
    }

    /**
     * Method that sets the new patient relative names
     *
     * @param relativeName - Patient relative names
     * */
    public void setRelative_name(String relativeName) {
        this.relativeName = relativeName;
    }

    /**
     * Method that obtains the external consultant name
     *
     * @return External consultant name
     * */
    public String getExternalConsultant() {
        return externalConsultant;
    }

    /**
     * Method that sets the new external consultant name
     *
     * @param externalConsultant - External consultant name
     * */
    public void setExternalConsultant(String externalConsultant) {
        this.externalConsultant = externalConsultant;
    }

    /**
     * Method that obtains the responsible email
     *
     * @return Responsible email
     * */
    public String getEmailResponsible() {
        return emailResponsible;
    }

    /**
     * Method that sets the new responsible email
     *
     * @param emailResponsible - Responsible email
     * */
    public void setEmailResponsible(String emailResponsible) {
        this.emailResponsible = emailResponsible;
    }

    /**
     * Method that obtains the direct attendance professional email
     *
     * @return Direct attendance professional email
     * */
    public String getEmailProfessional() {
        return emailProfessional;
    }

    /**
     * Method that sets the new direct attendance professional email
     *
     * @param emailProfessional - Direct attendance professional email
     * */
    public void setEmailProfessional(String emailProfessional) {
        this.emailProfessional = emailProfessional;
    }

    /**
     * Method that obtains the evaluator organization identifier
     *
     * @return Evaluator organization identifier
     * */
    public int getIdEvaluatorOrganization() {
        return idEvaluatorOrganization;
    }

    /**
     * Method that sets the new evaluator organization identifier
     *
     * @param idEvaluatorOrganization - Evaluator organization identifier
     * */
    public void setIdEvaluatorOrganization(int idEvaluatorOrganization) {
        this.idEvaluatorOrganization = idEvaluatorOrganization;
    }

    /**
     * Method that obtains the evaluator organization type
     *
     * @return Evaluator organization type
     * */
    public String getOrgTypeEvaluator() {
        return orgTypeEvaluator;
    }

    /**
     * Method that sets the new evaluator organization type
     *
     * @param orgTypeEvaluator - Evaluator organization type
     * */
    public void setOrgTypeEvaluator(String orgTypeEvaluator) {
        this.orgTypeEvaluator = orgTypeEvaluator;
    }

    /**
     * Method that obtains the evaluated organization identifier
     *
     * @return Evaluated organization identifier
     * */
    public int getIdEvaluatedOrganization() {
        return idEvaluatedOrganization;
    }

    /**
     * Method that sets the new evaluated organization identifier
     *
     * @param idEvaluatedOrganization - Evaluated organization identifier
     * */
    public void setIdEvaluatedOrganization(int idEvaluatedOrganization) {
        this.idEvaluatedOrganization = idEvaluatedOrganization;
    }

    /**
     * Method that obtains the evaluated organization type
     *
     * @return Evaluated organization type
     * */
    public String getOrgTypeEvaluated() {
        return orgTypeEvaluated;
    }

    /**
     * Method that sets the new evaluated organization type
     *
     * @param orgTypeEvaluated - Evaluated organization type
     * */
    public void setOrgTypeEvaluated(String orgTypeEvaluated) {
        this.orgTypeEvaluated = orgTypeEvaluated;
    }

    /**
     * Method that obtains the illness or syndrome
     *
     * @return Illness or syndrome
     * */
    public String getIllness() {
        return illness;
    }

    /**
     * Method that sets the new illness or syndrome
     *
     * @return Illness or syndrome
     * */
    public void setIllness(String illness) {
        this.illness = illness;
    }

    /**
     * Method that obtains the other members of the evaluator team
     *
     * @return Other members
     * */
    public String getOtherMembers() {
        return otherMembers;
    }

    /**
     * Method that sets the new other members of the evaluator team
     *
     * @param otherMembers - Other members
     * */
    public void setOtherMembers(String otherMembers) {
        this.otherMembers = otherMembers;
    }

    /**
     * Method that obtains the center identifier
     *
     * @return Center identifier
     * */
    public int getIdCenter() {
        return idCenter;
    }

    /**
     * Method that sets the new center identifier
     *
     * @param idCenter - Center identifier
     * */
    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }



    /**
     * Method that obtains the observations in English
     *
     * @return Evaluator team observations in English
     * */
    public String getObservationsEnglish() {
        return observationsEnglish;
    }

    /**
     * Method that sets the new observations in English
     *
     * @param observationsEnglish - Evaluator team observations in English
     * */
    public void setObservationsEnglish(String observationsEnglish) {
        this.observationsEnglish = observationsEnglish;
    }

    /**
     * Method that obtains the observations in Spanish
     *
     * @return Evaluator team observations in Spanish
     * */
    public String getObservationsSpanish() {
        return observationsSpanish;
    }

    /**
     * Method that sets the new observations in Spanish
     *
     * @param observationsSpanish - Evaluator team observations in Spanish
     * */
    public void setObservationsSpanish(String observationsSpanish) {
        this.observationsSpanish = observationsSpanish;
    }

    /**
     * Method that obtains the observations in French
     *
     * @return Evaluator team observations in French
     * */
    public String getObservationsFrench() {
        return observationsFrench;
    }

    /**
     * Method that sets the new observations in French
     *
     * @param observationsFrench - Evaluator team observations in French
     * */
    public void setObservationsFrench(String observationsFrench) {
        this.observationsFrench = observationsFrench;
    }

    /**
     * Method that obtains the observations in Basque
     *
     * @return Evaluator team observations in Basque
     * */
    public String getObservationsBasque() {
        return observationsBasque;
    }

    /**
     * Method that sets the new observations in Basque
     *
     * @param observationsBasque - Evaluator team observations in Basque
     * */
    public void setObservationsBasque(String observationsBasque) {
        this.observationsBasque = observationsBasque;
    }

    /**
     * Method that obtains the observations in Catalan
     *
     * @return Evaluator team observations in Catalan
     * */
    public String getObservationsCatalan() {
        return observationsCatalan;
    }

    /**
     * Method that sets the new observations in Catalan
     *
     * @param observationsCatalan - Evaluator team observations in Catalan
     * */
    public void setObservationsCatalan(String observationsCatalan) {
        this.observationsCatalan = observationsCatalan;
    }

    /**
     * Method that obtains the observations in Dutch
     *
     * @return Evaluator team observations in Dutch
     * */
    public String getObservationsDutch() {
        return observationsDutch;
    }

    /**
     * Method that sets the new observations in Dutch
     *
     * @param observationsDutch - Evaluator team observations in Dutch
     * */
    public void setObservationsDutch(String observationsDutch) {
        this.observationsDutch = observationsDutch;
    }

    /**
     * Method that obtains the observations in Galician
     *
     * @return Evaluator team observations in Galician
     * */
    public String getObservationsGalician() {
        return observationsGalician;
    }

    /**
     * Method that sets the new observations in Galician
     *
     * @param observationsGalician - Evaluator team observations in Galician
     * */
    public void setObservationsGalician(String observationsGalician) {
        this.observationsGalician = observationsGalician;
    }

    /**
     * Method that obtains the observations in German
     *
     * @return Evaluator team observations in German
     * */
    public String getObservationsGerman() {
        return observationsGerman;
    }

    /**
     * Method that sets the new observations in German
     *
     * @param observationsGerman - Evaluator team observations in German
     * */
    public void setObservationsGerman(String observationsGerman) {
        this.observationsGerman = observationsGerman;
    }

    /**
     * Method that obtains the observations in Italian
     *
     * @return Evaluator team observations in Italian
     * */
    public String getObservationsItalian() {
        return observationsItalian;
    }

    /**
     * Method that sets the new observations in Italian
     *
     * @param observationsItalian - Evaluator team observations in Italian
     * */
    public void setObservationsItalian(String observationsItalian) {
        this.observationsItalian = observationsItalian;
    }

    /**
     * Method that obtains the observations in Portuguese
     *
     * @return Evaluator team observations in Portuguese
     * */
    public String getObservationsPortuguese() {
        return observationsPortuguese;
    }

    /**
     * Method that sets the new observations in Portuguese
     *
     * @param observationsPortuguese - Evaluator team observations in Portuguese
     * */
    public void setObservationsPortuguese(String observationsPortuguese) {
        this.observationsPortuguese = observationsPortuguese;
    }

    /**
     * Method that obtains the evaluation dates sepparated by commas
     *
     * @return Evaluation dates sepparated by commas
     * */
    public String getEvaluationDates() {
        return evaluationDates;
    }

    /**
     * Method that sets the new evaluation dates sepparated by commas
     *
     * @param evaluationDates - Evaluation dates sepparated by commas
     * */
    public void setEvaluationDates(String evaluationDates) {
        this.evaluationDates = evaluationDates;
    }

    /**
     * Method that obtains the number of completed evaluation dates
     *
     * @return Number of completed evaluation dates
     * */
    public int getCompletedEvaluationDates() {
        return completedEvaluationDates;
    }

    /**
     * Method that sets the new number of completed evaluation dates
     *
     * @param completedEvaluationDates - Number of completed evaluation dates
     * */
    public void setCompletedEvaluationDates(int completedEvaluationDates) {
        this.completedEvaluationDates = completedEvaluationDates;
    }

    /**
     * Method that obtains the number of total evaluation dates
     *
     * @return Number of total evaluation dates
     * */
    public int getTotalEvaluationDates() {
        return totalEvaluationDates;
    }

    /**
     * Method that sets the new number of total evaluation dates
     *
     * @param totalEvaluationDates - Number of total evaluation dates
     * */
    public void setTotalEvaluationDates(int totalEvaluationDates) {
        this.totalEvaluationDates = totalEvaluationDates;
    }
}
