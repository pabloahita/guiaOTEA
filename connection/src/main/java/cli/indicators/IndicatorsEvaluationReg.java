package cli.indicators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class that serves to store all registers of the Indicators' Evaluation
 *
 * @version 1
 * @author Pablo Ahita del Barrio
 *
 * */
public class IndicatorsEvaluationReg implements Serializable {

    /**Evaluation date in timestamp*/
    @SerializedName("evaluationDate")
    public long evaluationDate;

    /**Evaluator team identifier*/
    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;

    /**Identifier of the evaluator organization that will do the indicators evaluation*/
    @SerializedName("idEvaluatorOrganization")
    public int idEvaluatorOrganization;

    /**Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"*/
    @SerializedName("orgTypeEvaluator")
    public String orgTypeEvaluator;


    /**Identifier of the external organization that will recieve the indicators evaluation*/
    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;

    /**Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"*/
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;

    /**Illness of both organizations. In case of Fundación Miradas, it will be "AUTISM"*/
    @SerializedName("illness")
    public String illness;
    
    /**Center identifier of the external organization*/
    @SerializedName("idCenter")
    public int idCenter;
    
    /**Second level division of the ambit. It will be -1 if there is no division*/
    @SerializedName("idSubSubAmbit")
    public int idSubSubAmbit;

    /**First level division of the ambit. It will be -1 if there is no division*/
    @SerializedName("idSubAmbit")
    public int idSubAmbit;
    
    /**Ambit identifier*/
    @SerializedName("idAmbit")
    public int idAmbit;

    /**Indicator's identifier*/
    @SerializedName("idIndicator")
    public int idIndicator;

    /**Evidence's identifier*/
    @SerializedName("idEvidence")
    public int idEvidence;
    
    /**Boolean that is 1 if the indicator is marked and 0 if not*/
    @SerializedName("isMarked")
    public int isMarked;

    /**Indicator version*/
    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    /**Class constructor
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatedOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatorOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param idSubSubAmbit - Second level division of the ambit. It will be -1 if there is no division
     * @param idSubAmbit - First level division of the ambit. It will be -1 if there is no division
     * @param idAmbit - Ambit identifier
     * @param idIndicator - Indicator identifier
     * @param idEvidence - Evidence identifier
     * @param indicatorVersion - Indicator version
     * */
    public IndicatorsEvaluationReg(long evaluationDate, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, int idEvaluatedOrganization, String orgTypeEvaluated, String illness, int idCenter, int idSubSubAmbit, int idSubAmbit, int idAmbit, int idIndicator, int idEvidence, int isMarked, int indicatorVersion) {
        setEvaluationDate(evaluationDate);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgTypeEvaluator(orgTypeEvaluator);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIllness(illness);
        setIdCenter(idCenter);
        setIdSubSubAmbit(idSubSubAmbit);
        setIdSubAmbit(idSubAmbit);
        setIdAmbit(idAmbit);
        setIdIndicator(idIndicator);
        setIdEvidence(idEvidence);
        setIsMarked(isMarked);
        setIndicatorVersion(indicatorVersion);
    }

    /**
     * Method that obtains the evaluation date in timestamp
     *
     * @return Evaluation date in timestamp
     * */
    public long getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * Method that sets a new evaluation date in timestamp
     *
     * @param evaluationDate - Evaluation date in timestamp
     * */
    public void setEvaluationDate(long evaluationDate) {
        this.evaluationDate = evaluationDate;
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
     * Method that sets a new evaluator team identifier
     *
     * @param idEvaluatorTeam - Evaluator team identifier
     * */
    public void setIdEvaluatorTeam(int idEvaluatorTeam) {
        this.idEvaluatorTeam = idEvaluatorTeam;
    }

    /**
     * Method that obtains the identifier of the evaluator organization that will do the indicators evaluation
     *
     * @return identifier of the evaluator organization that will do the indicators evaluation
     * */
    public int getIdEvaluatorOrganization() {
        return idEvaluatorOrganization;
    }

    /**
     * Method that sets a new identifier of the evaluator organization that will do the indicators evaluation
     *
     * @param idEvaluatorOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * */
    public void setIdEvaluatorOrganization(int idEvaluatorOrganization) {
        this.idEvaluatorOrganization = idEvaluatorOrganization;
    }

    /**
     * Method that obtains the organization type of the evaluator organization that will do the indicators evaluation
     *
     * @return identifier of the evaluator organization that will do the indicators evaluation
     * */
    public String getOrgTypeEvaluator() {
        return orgTypeEvaluator;
    }

    /**
     * Method that sets a new organization type
     *
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * */
    public void setOrgTypeEvaluator(String orgTypeEvaluator) {
        this.orgTypeEvaluator = orgTypeEvaluator;
    }


    /**
     * Method that obtains the identifier of the external organization that will recieve the indicators evaluation
     *
     * @return identifier of the external organization that will recieve the indicators evaluation
     * */
    public int getIdEvaluatedOrganization() {
        return idEvaluatedOrganization;
    }

    /**
     * Method that sets a new identifier of the external organization that will recieve the indicators evaluation
     *
     * @param idEvaluatedOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * */
    public void setIdEvaluatedOrganization(int idEvaluatedOrganization) {
        this.idEvaluatedOrganization = idEvaluatedOrganization;
    }

    /**
     * Method that obtains the organization type of the external organization that will recieve the indicators evaluation
     *
     * @return identifier of the external organization that will recieve the indicators evaluation
     * */
    public String getOrgTypeEvaluated() {
        return orgTypeEvaluated;
    }

    /**
     * Method that sets a new organization type
     *
     * @param orgTypeEvaluated - Organization type of the external organization
     * */
    public void setOrgTypeEvaluated(String orgTypeEvaluated) {
        this.orgTypeEvaluated = orgTypeEvaluated;
    }

    /**
     * Method that obtains the illness of the external organization that will recieve the indicators evaluation
     *
     * @return identifier of the external organization that will recieve the indicators evaluation
     * */
    public String getIllness() {
        return illness;
    }

    /**
     * Method that sets a new illness for the external organization
     *
     * @param illness - Illness of the external organization that will recieve the indicators evaluation
     * */
    public void setIllness(String illness) {
        this.illness = illness;
    }

    /**
     * Method that obtains the indicator identifier
     *
     * @return Indicator identifier
     * */
    public int getIdIndicator() {
        return idIndicator;
    }

    /**
     * Method that obtains the indicator identifier
     *
     * @return Indicator identifier
     * */
    public void setIdIndicator(int idIndicator) {
        this.idIndicator = idIndicator;
    }


    /**
     * Method that obtains the evidence identifier
     *
     * @return Evidence identifier
     * */
    public int getIdEvidence() {
        return idEvidence;
    }

    /**
     * Method that sets a new evidence identifier
     *
     * @param idEvidence - Evidence identifier
     * */
    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }

    /**
     * Method that obtains if the evidence is marked
     *
     * @return 0 if is marked, 1 if not
     * */
    public int getIsMarked() {
        return isMarked;
    }

    /**
     * Method that changes the mark on an evidence
     *
     * @param isMarked 0 if is marked, 1 if not
     * */
    public void setIsMarked(int isMarked) {
        this.isMarked = isMarked;
    }

    /**
     * Method that obtains the indicator version
     *
     * @return Indicator version
     * */
    public int getIndicatorVersion() {
        return indicatorVersion;
    }

    /**
     * Method that sets a new version for the indicator
     *
     * @param indicatorVersion - Indicator version
     * */
    public void setIndicatorVersion(int indicatorVersion) {
        this.indicatorVersion = indicatorVersion;
    }

    /**
     * Method that obtains the center identifier of the external organization
     *
     * @return Center identifier of the external organization
     *
     * */
    public int getIdCenter() {
        return idCenter;
    }

    /**
     * Method that sets a new center identifier of the external organization
     *
     * @param idCenter - Center identifier of the external organization
     * */
    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    /**
     * Method that obtains second level division of the ambit
     *
     * @return Second level division of the ambit
     * */

    public int getIdSubSubAmbit() {
        return idSubSubAmbit;
    }


    /**
     * Method that set a new second level division of the ambit
     *
     * @param idSubSubAmbit - Second level division of the ambit
     * */
    public void setIdSubSubAmbit(int idSubSubAmbit) {
        this.idSubSubAmbit = idSubSubAmbit;
    }

    /**
     * Method that obtains first level division of the ambit
     *
     * @return First level division of the ambit
     * */
    public int getIdSubAmbit() {
        return idSubAmbit;
    }

    /**
     * Method that set a new first level division of the ambit
     *
     * @param idSubAmbit - First level division of the ambit
     * */
    public void setIdSubAmbit(int idSubAmbit) {
        this.idSubAmbit = idSubAmbit;
    }

    /**
     * Method that obtains the ambit identifier
     *
     * @return Ambit identifier
     * */
    public int getIdAmbit() {
        return idAmbit;
    }

    /**
     * Method that sets a new ambit identifier
     *
     * @param idAmbit - Ambit identifier
     *
     * */
    public void setIdAmbit(int idAmbit) {
        this.idAmbit = idAmbit;
    }
}
