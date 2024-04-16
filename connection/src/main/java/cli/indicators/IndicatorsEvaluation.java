package cli.indicators;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
;

/**
 * Model class that serves to store the indicators evaluation info
 *
 * @author Pablo Ahita del Barrio
 * @version 1
 * */
public class IndicatorsEvaluation implements Serializable {

    /**Evaluation date in timestamp*/
    @SerializedName("evaluationDate")
    public long evaluationDate;

    /**Identifier of the external organization that will recieve the indicators evaluation*/
    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;

    /**Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"*/
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;


    /**Evaluator team identifier*/
    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;

    /**Identifier of the evaluator organization that will do the indicators evaluation*/
    @SerializedName("idEvaluatorOrganization")
    public int idEvaluatorOrganization;

    /**Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"*/
    @SerializedName("orgTypeEvaluator")
    public String orgTypeEvaluator;

    /**Illness of both organizations. In case of Fundación Miradas, it will be "AUTISM"*/
    @SerializedName("illness")
    public String illness;

    /**Center identifier of the external organization*/
    @SerializedName("idCenter")
    public int idCenter;

    /**Total score of ambit 1*/
    @SerializedName("scoreAmbit1")
    public int scoreAmbit1=0;

    /**Total score of ambit 2*/
    @SerializedName("scoreAmbit2")
    public int scoreAmbit2=0;

    /**Total score of ambit 3*/
    @SerializedName("scoreAmbit3")
    public int scoreAmbit3=0;

    /**Total score of ambit 4*/
    @SerializedName("scoreAmbit4")
    public int scoreAmbit4=0;

    /**Total score of ambit 5*/
    @SerializedName("scoreAmbit5")
    public int scoreAmbit5=0;

    /**Total score of ambit 6*/
    @SerializedName("scoreAmbit6")
    public int scoreAmbit6=0;

    /**Total score of the indicators evaluation*/
    @SerializedName("totalScore")
    public int totalScore=0;

    /**Evaluation's conclusions in English*/
    @SerializedName("conclusionsEnglish")
    public String conclusionsEnglish;

    /**Evaluation's conclusions in Spanish*/
    @SerializedName("conclusionsSpanish")
    public String conclusionsSpanish;

    /**Evaluation's conclusions in French*/
    @SerializedName("conclusionsFrench")
    public String conclusionsFrench;

    /**Evaluation's conclusions in Basque*/
    @SerializedName("conclusionsBasque")
    public String conclusionsBasque;

    /**Evaluation's conclusions in Catalan*/
    @SerializedName("conclusionsCatalan")
    public String conclusionsCatalan;

    /**Evaluation's conclusions in Dutch*/
    @SerializedName("conclusionsDutch")
    public String conclusionsDutch;

    /**Evaluation's conclusions in Galician*/
    @SerializedName("conclusionsGalician")
    public String conclusionsGalician;

    /**Evaluation's conclusions in German*/
    @SerializedName("conclusionsGerman")
    public String conclusionsGerman;

    /**Evaluation's conclusions in Italian*/
    @SerializedName("conclusionsItalian")
    public String conclusionsItalian;

    /**Evaluation's conclusions in Portuguese*/
    @SerializedName("conclusionsPortuguese")
    public String conclusionsPortuguese;

    /**Boolean that determines if the evaluation is finished. 1 if is finished, 0 if not*/
    @SerializedName("isFinished")
    public int isFinished=0;

    /**Evaluation type*/
    @SerializedName("evaluationType")
    public String evaluationType;


    /**Class constructor
     *
     * @param evaluationDate - Evaluation date in timestamp
     * @param idEvaluatorTeam - Evaluator team identifier
     * @param idEvaluatorOrganization - Identifier of the evaluator organization that will do the indicators evaluation
     * @param orgTypeEvaluator - Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"
     * @param idEvaluatedOrganization - Identifier of the external organization that will recieve the indicators evaluation
     * @param orgTypeEvaluated - Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"
     * @param illness - Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"
     * @param idCenter - Center identifier of the external organization
     * @param conclusionsEnglish - Evaluation's conclusions in English
     * @param conclusionsSpanish - Evaluation's conclusions in Spanish
     * @param conclusionsFrench - Evaluation's conclusions in French
     * @param conclusionsBasque - Evaluation's conclusions in Basque
     * @param conclusionsCatalan - Evaluation's conclusions in Catalan
     * @param conclusionsDutch - Evaluation's conclusions in Dutch
     * @param conclusionsGalician - Evaluation's conclusions in Galician
     * @param conclusionsGerman - Evaluation's conclusions in German
     * @param conclusionsItalian - Evaluation's conclusions in Italian
     * @param conclusionsPortuguese - Evaluation's conclusions in Portuguese
     * @param scoreAmbit1 - Total score of ambit 1
     * @param scoreAmbit2 - Total score of ambit 2
     * @param scoreAmbit3 - Total score of ambit 3
     * @param scoreAmbit4 - Total score of ambit 4
     * @param scoreAmbit5 - Total score of ambit 5
     * @param scoreAmbit6 - Total score of ambit 6
     * @param totalScore - Total score
     * @param isFinished - 1 if is finished, 0 if not
     * @param evaluationType - Evaluation type
     * */
    public IndicatorsEvaluation(long evaluationDate,int idEvaluatedOrganization,String orgTypeEvaluated,int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness, int idCenter, int scoreAmbit1, int scoreAmbit2, int scoreAmbit3, int scoreAmbit4, int scoreAmbit5, int scoreAmbit6, int totalScore, String conclusionsSpanish, String conclusionsEnglish, String conclusionsFrench, String conclusionsBasque, String conclusionsCatalan, String conclusionsDutch, String conclusionsGalician, String conclusionsGerman, String conclusionsItalian, String conclusionsPortuguese, int isFinished, String evaluationType){
        setEvaluationDate(evaluationDate);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgTypeEvaluator(orgTypeEvaluator);
        setIllness(illness);
        setIdCenter(idCenter);
        setConclusionsEnglish(conclusionsEnglish);
        setConclusionsSpanish(conclusionsSpanish);
        setConclusionsFrench(conclusionsFrench);
        setConclusionsBasque(conclusionsBasque);
        setConclusionsCatalan(conclusionsCatalan);
        setConclusionsDutch(conclusionsDutch);
        setConclusionsGalician(conclusionsGalician);
        setConclusionsGerman(conclusionsGerman);
        setConclusionsItalian(conclusionsItalian);
        setConclusionsPortuguese(conclusionsPortuguese);
        setScoreAmbit1(scoreAmbit1);
        setScoreAmbit2(scoreAmbit2);
        setScoreAmbit3(scoreAmbit3);
        setScoreAmbit4(scoreAmbit4);
        setScoreAmbit5(scoreAmbit5);
        setScoreAmbit6(scoreAmbit6);
        setTotalScore(totalScore);
        setIsFinished(isFinished);
        setEvaluationType(evaluationType);
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
     * Method that obtains the total score of the evaluation
     *
     * @return Total score of the indicators evaluation
     * */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Method that sets a new total score for the evaluation
     *
     * @param totalScore - Total score of the indicators evaluation
     * */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Method that obtains the total score of the ambit 1
     *
     * @return Total score of the ambit 1
     * */
    public int getScoreAmbit1() {
        return scoreAmbit1;
    }

    /**
     * Method that sets a new total score for the ambit 1
     *
     * @param scoreAmbit1 - Total score of the ambit 1
     * */
    public void setScoreAmbit1(int scoreAmbit1) {
        this.scoreAmbit1 = scoreAmbit1;
    }

    /**
     * Method that obtains the total score of the ambit 2
     *
     * @return Total score of the ambit 2
     * */
    public int getScoreAmbit2() {
        return scoreAmbit2;
    }

    /**
     * Method that sets a new total score for the ambit 2
     *
     * @param scoreAmbit2 - Total score of the ambit 2
     * */
    public void setScoreAmbit2(int scoreAmbit2) {
        this.scoreAmbit2 = scoreAmbit2;
    }

    /**
     * Method that obtains the total score of the ambit 3
     *
     * @return Total score of the ambit 3
     * */
    public int getScoreAmbit3() {
        return scoreAmbit3;
    }

    /**
     * Method that sets a new total score for the ambit 3
     *
     * @param scoreAmbit3 - Total score of the ambit 3
     * */
    public void setScoreAmbit3(int scoreAmbit3) {
        this.scoreAmbit3 = scoreAmbit3;
    }

    /**
     * Method that obtains the total score of the ambit 4
     *
     * @return Total score of the ambit 4
     * */
    public int getScoreAmbit4() {
        return scoreAmbit4;
    }

    /**
     * Method that sets a new total score for the ambit 4
     *
     * @param scoreAmbit4 - Total score of the ambit 4
     * */
    public void setScoreAmbit4(int scoreAmbit4) {
        this.scoreAmbit4 = scoreAmbit4;
    }

    /**
     * Method that obtains the total score of the ambit 5
     *
     * @return Total score of the ambit 5
     * */
    public int getScoreAmbit5() {
        return scoreAmbit5;
    }

    /**
     * Method that sets a new total score for the ambit 5
     *
     * @param scoreAmbit5 - Total score of the ambit 5
     * */
    public void setScoreAmbit5(int scoreAmbit5) {
        this.scoreAmbit5 = scoreAmbit5;
    }

    /**
     * Method that obtains the total score of the ambit 6
     *
     * @return Total score of the ambit 6
     * */
    public int getScoreAmbit6() {
        return scoreAmbit6;
    }

    /**
     * Method that sets a new total score for the ambit 6
     *
     * @param scoreAmbit6 - Total score of the ambit 6
     * */
    public void setScoreAmbit6(int scoreAmbit6) {
        this.scoreAmbit6 = scoreAmbit6;
    }

    /**
     * Method that obtains if the evaluation is finished
     *
     * @return 1 if is finished, 0 if not
     * */
    public int getIsFinished() {
        return isFinished;
    }

    /**
     * Method that changes the finalisation of the evaluation
     *
     * @param isFinished - 1 if is finished, 0 if not
     * */
    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }


    /**
     * Method that obtains the conclusions in English
     *
     * @return Evaluation's conclusions in English
     * */
    public String getConclusionsEnglish() {
        return conclusionsEnglish;
    }

    /**
     * Method that sets the new conclusions in English
     *
     * @param conclusionsEnglish - Evaluation's conclusions in English
     * */
    public void setConclusionsEnglish(String conclusionsEnglish) {
        this.conclusionsEnglish = conclusionsEnglish;
    }
    /**
     * Method that obtains the conclusions in Spanish
     *
     * @return Evaluation's conclusions in Spanish
     * */
    public String getConclusionsSpanish() {
        return conclusionsSpanish;
    }

    /**
     * Method that sets the new conclusions in Spanish
     *
     * @param conclusionsSpanish - Evaluation's conclusions in Spanish
     * */
    public void setConclusionsSpanish(String conclusionsSpanish) {
        this.conclusionsSpanish = conclusionsSpanish;
    }

    /**
     * Method that obtains the conclusions in French
     *
     * @return Evaluation's conclusions in French
     * */
    public String getConclusionsFrench() {
        return conclusionsFrench;
    }

    /**
     * Method that sets the new conclusions in French
     *
     * @param conclusionsFrench - Evaluation's conclusions in French
     * */
    public void setConclusionsFrench(String conclusionsFrench) {
        this.conclusionsFrench = conclusionsFrench;
    }

    /**
     * Method that obtains the conclusions in Basque
     *
     * @return Evaluation's conclusions in Basque
     * */
    public String getConclusionsBasque() {
        return conclusionsBasque;
    }

    /**
     * Method that sets the new conclusions in Basque
     *
     * @param conclusionsBasque - Evaluation's conclusions in Basque
     * */
    public void setConclusionsBasque(String conclusionsBasque) {
        this.conclusionsBasque = conclusionsBasque;
    }

    /**
     * Method that obtains the conclusions in Catalan
     *
     * @return Evaluation's conclusions in Catalan
     * */
    public String getConclusionsCatalan() {
        return conclusionsCatalan;
    }

    /**
     * Method that sets the new conclusions in Catalan
     *
     * @param conclusionsCatalan - Evaluation's conclusions in Catalan
     * */
    public void setConclusionsCatalan(String conclusionsCatalan) {
        this.conclusionsCatalan = conclusionsCatalan;
    }

    /**
     * Method that obtains the conclusions in Dutch
     *
     * @return Evaluation's conclusions in Dutch
     * */
    public String getConclusionsDutch() {
        return conclusionsDutch;
    }

    /**
     * Method that sets the new conclusions in Dutch
     *
     * @param conclusionsDutch - Evaluation's conclusions in Dutch
     * */
    public void setConclusionsDutch(String conclusionsDutch) {
        this.conclusionsDutch = conclusionsDutch;
    }

    /**
     * Method that obtains the conclusions in Galician
     *
     * @return Evaluation's conclusions in Galician
     * */
    public String getConclusionsGalician() {
        return conclusionsGalician;
    }

    /**
     * Method that sets the new conclusions in Galician
     *
     * @param conclusionsGalician - Evaluation's conclusions in Galician
     * */
    public void setConclusionsGalician(String conclusionsGalician) {
        this.conclusionsGalician = conclusionsGalician;
    }

    /**
     * Method that obtains the conclusions in German
     *
     * @return Evaluation's conclusions in German
     * */
    public String getConclusionsGerman() {
        return conclusionsGerman;
    }

    /**
     * Method that sets the new conclusions in German
     *
     * @param conclusionsGerman - Evaluation's conclusions in German
     * */
    public void setConclusionsGerman(String conclusionsGerman) {
        this.conclusionsGerman = conclusionsGerman;
    }

    /**
     * Method that obtains the conclusions in Italian
     *
     * @return Evaluation's conclusions in Italian
     * */
    public String getConclusionsItalian() {
        return conclusionsItalian;
    }

    /**
     * Method that sets the new conclusions in Italian
     *
     * @param conclusionsItalian - Evaluation's conclusions in Italian
     * */
    public void setConclusionsItalian(String conclusionsItalian) {
        this.conclusionsItalian = conclusionsItalian;
    }

    /**
     * Method that obtains the conclusions in Portuguese
     *
     * @return Evaluation's conclusions in Spanish
     * */
    public String getConclusionsPortuguese() {
        return conclusionsPortuguese;
    }

    /**
     * Method that sets the new conclusions in Portuguese
     *
     * @param conclusionsPortuguese - Evaluation's conclusions in Portuguese
     * */
    public void setConclusionsPortuguese(String conclusionsPortuguese) {
        this.conclusionsPortuguese = conclusionsPortuguese;
    }

    /**
     * Method that obtains the center identifier of the external organization
     *
     * @return Center identifier
     *
     * */
    public int getIdCenter() {
        return idCenter;
    }

    /**
     * Method that sets the new center identifier of the external organization
     *
     * @param idCenter - Center identifier
     * */
    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
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
}
