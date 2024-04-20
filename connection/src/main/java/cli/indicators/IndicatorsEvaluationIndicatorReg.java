package cli.indicators;

import com.google.gson.annotations.SerializedName;

public class IndicatorsEvaluationIndicatorReg {

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

    /**Indicator type*/
    @SerializedName("indicatorType")
    public String indicatorType;

    /**Indicator version*/
    @SerializedName("indicatorVersion")
    public int indicatorVersion;

    /**Evaluation type*/
    @SerializedName("evaluationType")
    public String evaluationType;

    /**Observations in English*/
    @SerializedName("observationsEnglish")
    public String observationsEnglish;

    /**Observations in Spanish*/
    @SerializedName("observationsSpanish")
    public String observationsSpanish;

    /**Observations in French*/
    @SerializedName("observationsFrench")
    public String observationsFrench;

    /**Observations in Basque*/
    @SerializedName("observationsBasque")
    public String observationsBasque;

    /**Observations in Catalan*/
    @SerializedName("observationsCatalan")
    public String observationsCatalan;

    /**Observations in Dutch*/
    @SerializedName("observationsDutch")
    public String observationsDutch;

    /**Observations in Galician*/
    @SerializedName("observationsGalician")
    public String observationsGalician;

    /**Observations in German*/
    @SerializedName("observationsGerman")
    public String observationsGerman;

    /**Observations in Italian*/
    @SerializedName("observationsItalian")
    public String observationsItalian;

    /**Observations in Portuguese*/
    @SerializedName("observationsPortuguese")
    public String observationsPortuguese;

    /**Number of marked evidences*/
    @SerializedName("numEvidencesMarked")
    public int numEvidencesMarked;

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
     * @param indicatorVersion - Indicator version
     * @param evaluationType - Evaluation type
     * @param observationsSpanish - Observations in Spanish
     * @param observationsEnglish - Observations in English
     * @param observationsFrench - Observations in French
     * @param observationsBasque - Observations in Basque
     * @param observationsCatalan - Observations in Catalan
     * @param observationsDutch - Observations in Dutch
     * @param observationsGalician - Observations in Galician
     * @param observationsGerman - Observations in German
     * @param observationsItalian - Observations in Italian
     * @param observationsPortuguese - Observations in Portuguese
     * @param numEvidenciesMarked - Number of marked evidences
     * */

    public IndicatorsEvaluationIndicatorReg(long evaluationDate, int idEvaluatedOrganization, String orgTypeEvaluated, int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness, int idCenter, int idIndicator, String indicatorType, int idSubSubAmbit, int idSubAmbit, int idAmbit, int indicatorVersion, String evaluationType, String observationsSpanish, String observationsEnglish, String observationsFrench, String observationsBasque, String observationsCatalan,
                                            String observationsDutch, String observationsGalician, String observationsGerman, String observationsItalian, String observationsPortuguese, int numEvidenciesMarked) {
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
        setIndicatorVersion(indicatorVersion);
        setIndicatorType(indicatorType);
        setEvaluationType(evaluationType);
        setObservationsBasque(observationsBasque);
        setObservationsCatalan(observationsCatalan);
        setObservationsDutch(observationsDutch);
        setObservationsEnglish(observationsEnglish);
        setObservationsFrench(observationsFrench);
        setObservationsGalician(observationsGalician);
        setObservationsGerman(observationsGerman);
        setObservationsItalian(observationsItalian);
        setObservationsPortuguese(observationsPortuguese);
        setObservationsSpanish(observationsSpanish);
        setNumEvidencesMarked(numEvidenciesMarked);
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

    /**
     * Method that obtains the indicator type
     *
     * @return Indicator type
     * */

    public String getIndicatorType() {
        return indicatorType;
    }

    /**
     * Method that sets the new indicator type
     *
     * @param indicatorType - Indicator type
     * */
    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    /**
     * Method that obtains the observations in English
     *
     * @return Observations in English
     * */
    public String getObservationsEnglish() {
        return observationsEnglish;
    }

    /**
     * Method that sets the new observations in English
     *
     * @param observationsEnglish - Observations in English
     * */
    public void setObservationsEnglish(String observationsEnglish) {
        this.observationsEnglish = observationsEnglish;
    }
    /**
     * Method that obtains the observations in Spanish
     *
     * @return Observations in Spanish
     * */
    public String getObservationsSpanish() {
        return observationsSpanish;
    }

    /**
     * Method that sets the new observations in Spanish
     *
     * @param observationsSpanish - Observations in Spanish
     * */
    public void setObservationsSpanish(String observationsSpanish) {
        this.observationsSpanish = observationsSpanish;
    }

    /**
     * Method that obtains the observations in French
     *
     * @return Observations in French
     * */
    public String getObservationsFrench() {
        return observationsFrench;
    }

    /**
     * Method that sets the new observations in French
     *
     * @param observationsFrench - Observations in French
     * */
    public void setObservationsFrench(String observationsFrench) {
        this.observationsFrench = observationsFrench;
    }

    /**
     * Method that obtains the observations in Basque
     *
     * @return Observations in Basque
     * */
    public String getObservationsBasque() {
        return observationsBasque;
    }

    /**
     * Method that sets the new observations in Basque
     *
     * @param observationsBasque - Observations in Basque
     * */
    public void setObservationsBasque(String observationsBasque) {
        this.observationsBasque = observationsBasque;
    }

    /**
     * Method that obtains the observations in Catalan
     *
     * @return Observations in Catalan
     * */
    public String getObservationsCatalan() {
        return observationsCatalan;
    }

    /**
     * Method that sets the new observations in Catalan
     *
     * @param observationsCatalan - Observations in Catalan
     * */
    public void setObservationsCatalan(String observationsCatalan) {
        this.observationsCatalan = observationsCatalan;
    }

    /**
     * Method that obtains the observations in Dutch
     *
     * @return Observations in Dutch
     * */
    public String getObservationsDutch() {
        return observationsDutch;
    }

    /**
     * Method that sets the new observations in Dutch
     *
     * @param observationsDutch - Observations in Dutch
     * */
    public void setObservationsDutch(String observationsDutch) {
        this.observationsDutch = observationsDutch;
    }

    /**
     * Method that obtains the observations in Galician
     *
     * @return Observations in Galician
     * */
    public String getObservationsGalician() {
        return observationsGalician;
    }

    /**
     * Method that sets the new observations in Galician
     *
     * @param observationsGalician - Observations in Galician
     * */
    public void setObservationsGalician(String observationsGalician) {
        this.observationsGalician = observationsGalician;
    }

    /**
     * Method that obtains the observations in German
     *
     * @return Observations in German
     * */
    public String getObservationsGerman() {
        return observationsGerman;
    }

    /**
     * Method that sets the new observations in German
     *
     * @param observationsGerman - Observations in German
     * */
    public void setObservationsGerman(String observationsGerman) {
        this.observationsGerman = observationsGerman;
    }

    /**
     * Method that obtains the observations in Italian
     *
     * @return Observations in Italian
     * */
    public String getObservationsItalian() {
        return observationsItalian;
    }

    /**
     * Method that sets the new observations in Italian
     *
     * @param observationsItalian - Observations in Italian
     * */
    public void setObservationsItalian(String observationsItalian) {
        this.observationsItalian = observationsItalian;
    }

    /**
     * Method that obtains the observations in Portuguese
     *
     * @return Observations in Spanish
     * */
    public String getObservationsPortuguese() {
        return observationsPortuguese;
    }

    /**
     * Method that sets the new observations in Portuguese
     *
     * @param observationsPortuguese - Observations in Portuguese
     * */
    public void setObservationsPortuguese(String observationsPortuguese) {
        this.observationsPortuguese = observationsPortuguese;
    }

    /**
     * Method that obtains the evaluation type
     *
     * @return Evaluation type
     * */

    public String getEvaluationType() {
        return evaluationType;
    }

    /**
     * Method that sets the new evaluation type
     *
     * @param evaluationType - Evaluation type
     * */
    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    /**
     * Method that obtains the number of marked evidences
     *
     * @return number of marked evidences
     */
    public int getNumEvidencesMarked() {
        return numEvidencesMarked;
    }

    /**
     * Method that sets a new number of marked evidences
     *
     * @param numEvidencesMarked - new number of marked evidences
     */
    public void setNumEvidencesMarked(int numEvidencesMarked) {
        this.numEvidencesMarked = numEvidencesMarked;
    }
}
