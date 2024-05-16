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

    /**
     * Total score of the priority zero and colour red
     */
    @SerializedName("scorePriorityZeroColourRed")
    public int scorePriorityZeroColourRed=0;

    /**
     * Total score of the priority zero and colour yellow
     */
    @SerializedName("scorePriorityZeroColourYellow")
    public int scorePriorityZeroColourYellow=0;

    /**
     * Total score of the priority zero and colour green
     */
    @SerializedName("scorePriorityZeroColourGreen")
    public int scorePriorityZeroColourGreen=0;

    /**
     * Total score of the priority one and colour red
     */
    @SerializedName("scorePriorityOneColourRed")
    public int scorePriorityOneColourRed=0;

    /**
     * Total score of the priority one and colour yellow
     */
    @SerializedName("scorePriorityOneColourYellow")
    public int scorePriorityOneColourYellow=0;

    /**
     * Total score of the priority one and colour green
     */
    @SerializedName("scorePriorityOneColourGreen")
    public int scorePriorityOneColourGreen=0;

    /**
     * Total score of the priority two and colour red
     */
    @SerializedName("scorePriorityTwoColourRed")
    public int scorePriorityTwoColourRed=0;

    /**
     * Total score of the priority two and colour yellow
     */
    @SerializedName("scorePriorityTwoColourYellow")
    public int scorePriorityTwoColourYellow=0;

    /**
     * Total score of the priority two and colour green
     */
    @SerializedName("scorePriorityTwoColourGreen")
    public int scorePriorityTwoColourGreen=0;

    /**
     * Total score of the priority three and colour red
     */
    @SerializedName("scorePriorityThreeColourRed")
    public int scorePriorityThreeColourRed=0;

    /**
     * Total score of the priority three and colour yellow
     */
    @SerializedName("scorePriorityThreeColourYellow")
    public int scorePriorityThreeColourYellow=0;

    /**
     * Total score of the priority three and colour green
     */
    @SerializedName("scorePriorityThreeColourGreen")
    public int scorePriorityThreeColourGreen=0;

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

    /**Evaluation level when is finished*/
    @SerializedName("level")
    public String level;


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
     * @param scorePriorityZeroColourRed - Total score of the priority zero and colour red
     * @param scorePriorityZeroColourYellow - Total score of the priority zero and colour yellow
     * @param scorePriorityZeroColourGreen - Total score of the priority zero and colour green
     * @param scorePriorityOneColourRed - Total score of the priority one and colour red
     * @param scorePriorityOneColourYellow - Total score of the priority one and colour yellow
     * @param scorePriorityOneColourGreen - Total score of the priority one and colour green
     * @param scorePriorityTwoColourRed - Total score of the priority two and colour red
     * @param scorePriorityTwoColourYellow - Total score of the priority two and colour yellow
     * @param scorePriorityTwoColourGreen - Total score of the priority two and colour green
     * @param scorePriorityThreeColourRed - Total score of the priority three and colour red
     * @param scorePriorityThreeColourYellow - Total score of the priority three and colour yellow
     * @param scorePriorityThreeColourGreen - Total score of the priority three and colour green
     * @param totalScore - Total score
     * @param isFinished - 1 if is finished, 0 if not
     * @param evaluationType - Evaluation type
     * @param level - Evaluation level when is finished
     * */
    public IndicatorsEvaluation(long evaluationDate,int idEvaluatedOrganization,String orgTypeEvaluated,int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness, int idCenter,
                                int scorePriorityZeroColourRed, int scorePriorityZeroColourYellow, int scorePriorityZeroColourGreen,
                                int scorePriorityOneColourRed, int scorePriorityOneColourYellow, int scorePriorityOneColourGreen,
                                int scorePriorityTwoColourRed, int scorePriorityTwoColourYellow, int scorePriorityTwoColourGreen,
                                int scorePriorityThreeColourRed, int scorePriorityThreeColourYellow, int scorePriorityThreeColourGreen, int totalScore,
                                String conclusionsSpanish, String conclusionsEnglish, String conclusionsFrench, String conclusionsBasque, String conclusionsCatalan, String conclusionsDutch, String conclusionsGalician, String conclusionsGerman, String conclusionsItalian, String conclusionsPortuguese, int isFinished, String evaluationType, String level){
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
        setScorePriorityZeroColourRed(scorePriorityZeroColourRed);
        setScorePriorityZeroColourYellow(scorePriorityZeroColourYellow);
        setScorePriorityZeroColourGreen(scorePriorityZeroColourGreen);
        setScorePriorityOneColourRed(scorePriorityOneColourRed);
        setScorePriorityOneColourYellow(scorePriorityOneColourYellow);
        setScorePriorityOneColourGreen(scorePriorityOneColourGreen);
        setScorePriorityTwoColourRed(scorePriorityTwoColourRed);
        setScorePriorityTwoColourYellow(scorePriorityTwoColourYellow);
        setScorePriorityTwoColourGreen(scorePriorityTwoColourGreen);
        setScorePriorityThreeColourRed(scorePriorityThreeColourRed);
        setScorePriorityThreeColourYellow(scorePriorityThreeColourYellow);
        setScorePriorityThreeColourGreen(scorePriorityThreeColourGreen);
        setTotalScore(totalScore);
        setIsFinished(isFinished);
        setEvaluationType(evaluationType);
        setLevel(level);
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
     * Method that obtains the score of the priority zero colour red
     *
     * @return score of the priority zero colour red
     *
     * */
    public int getScorePriorityZeroColourRed() {
        return scorePriorityZeroColourRed;
    }

    /**
     * Method that sets the new score of the priority zero colour red
     *
     * @param scorePriorityZeroColourRed - score of the priority zero colour red
     * */
    public void setScorePriorityZeroColourRed(int scorePriorityZeroColourRed) {
        this.scorePriorityZeroColourRed = scorePriorityZeroColourRed;
    }

    /**
     * Method that obtains the score of the priority zero colour yellow
     *
     * @return score of the priority zero colour yellow
     *
     * */
    public int getScorePriorityZeroColourYellow() {
        return scorePriorityZeroColourYellow;
    }

    /**
     * Method that sets the new score of the priority zero colour yellow
     *
     * @param scorePriorityZeroColourYellow - score of the priority zero colour yellow
     * */
    public void setScorePriorityZeroColourYellow(int scorePriorityZeroColourYellow) {
        this.scorePriorityZeroColourYellow = scorePriorityZeroColourYellow;
    }

    /**
     * Method that obtains the score of the priority zero colour green
     *
     * @return score of the priority zero colour green
     *
     * */
    public int getScorePriorityZeroColourGreen() {
        return scorePriorityZeroColourGreen;
    }

    /**
     * Method that sets the new score of the priority zero colour green
     *
     * @param scorePriorityZeroColourGreen - score of the priority zero colour green
     * */
    public void setScorePriorityZeroColourGreen(int scorePriorityZeroColourGreen) {
        this.scorePriorityZeroColourGreen = scorePriorityZeroColourGreen;
    }

    /**
     * Method that obtains the score of the priority one colour red
     *
     * @return score of the priority one colour red
     *
     * */
    public int getScorePriorityOneColourRed() {
        return scorePriorityOneColourRed;
    }

    /**
     * Method that sets the new score of the priority one colour red
     *
     * @param scorePriorityOneColourRed - score of the priority one colour red
     * */
    public void setScorePriorityOneColourRed(int scorePriorityOneColourRed) {
        this.scorePriorityOneColourRed = scorePriorityOneColourRed;
    }

    /**
     * Method that obtains the score of the priority one colour yellow
     *
     * @return score of the priority one colour yellow
     *
     * */
    public int getScorePriorityOneColourYellow() {
        return scorePriorityOneColourYellow;
    }

    /**
     * Method that sets the new score of the priority one colour yellow
     *
     * @param scorePriorityOneColourYellow - score of the priority one colour yellow
     * */
    public void setScorePriorityOneColourYellow(int scorePriorityOneColourYellow) {
        this.scorePriorityOneColourYellow = scorePriorityOneColourYellow;
    }

    /**
     * Method that obtains the score of the priority one colour green
     *
     * @return score of the priority one colour green
     *
     * */
    public int getScorePriorityOneColourGreen() {
        return scorePriorityOneColourGreen;
    }

    /**
     * Method that sets the new score of the priority one colour green
     *
     * @param scorePriorityOneColourGreen - score of the priority one colour green
     * */
    public void setScorePriorityOneColourGreen(int scorePriorityOneColourGreen) {
        this.scorePriorityOneColourGreen = scorePriorityOneColourGreen;
    }

    /**
     * Method that obtains the score of the priority two colour red
     *
     * @return score of the priority two colour red
     *
     * */
    public int getScorePriorityTwoColourRed() {
        return scorePriorityTwoColourRed;
    }

    /**
     * Method that sets the new score of the priority two colour red
     *
     * @param scorePriorityTwoColourRed - score of the priority two colour red
     * */
    public void setScorePriorityTwoColourRed(int scorePriorityTwoColourRed) {
        this.scorePriorityTwoColourRed = scorePriorityTwoColourRed;
    }

    /**
     * Method that obtains the score of the priority two colour yellow
     *
     * @return score of the priority two colour yellow
     *
     * */
    public int getScorePriorityTwoColourYellow() {
        return scorePriorityTwoColourYellow;
    }

    /**
     * Method that sets the new score of the priority two colour yellow
     *
     * @param scorePriorityTwoColourYellow - score of the priority two colour yellow
     * */
    public void setScorePriorityTwoColourYellow(int scorePriorityTwoColourYellow) {
        this.scorePriorityTwoColourYellow = scorePriorityTwoColourYellow;
    }

    /**
     * Method that obtains the score of the priority two colour green
     *
     * @return score of the priority two colour green
     *
     * */
    public int getScorePriorityTwoColourGreen() {
        return scorePriorityTwoColourGreen;
    }

    /**
     * Method that sets the new score of the priority two colour green
     *
     * @param scorePriorityTwoColourGreen - score of the priority two colour green
     * */
    public void setScorePriorityTwoColourGreen(int scorePriorityTwoColourGreen) {
        this.scorePriorityTwoColourGreen = scorePriorityTwoColourGreen;
    }

    /**
     * Method that obtains the score of the priority three colour red
     *
     * @return score of the priority three colour red
     *
     * */
    public int getScorePriorityThreeColourRed() {
        return scorePriorityThreeColourRed;
    }

    /**
     * Method that sets the new score of the priority three colour red
     *
     * @param scorePriorityThreeColourRed - score of the priority three colour red
     * */
    public void setScorePriorityThreeColourRed(int scorePriorityThreeColourRed) {
        this.scorePriorityThreeColourRed = scorePriorityThreeColourRed;
    }

    /**
     * Method that obtains the score of the priority three colour yellow
     *
     * @return score of the priority three colour yellow
     *
     * */
    public int getScorePriorityThreeColourYellow() {
        return scorePriorityThreeColourYellow;
    }

    /**
     * Method that sets the new score of the priority three colour yellow
     *
     * @param scorePriorityThreeColourYellow - score of the priority three colour yellow
     * */
    public void setScorePriorityThreeColourYellow(int scorePriorityThreeColourYellow) {
        this.scorePriorityThreeColourYellow = scorePriorityThreeColourYellow;
    }

    /**
     * Method that obtains the score of the priority three colour green
     *
     * @return score of the priority three colour green
     *
     * */
    public int getScorePriorityThreeColourGreen() {
        return scorePriorityThreeColourGreen;
    }

    /**
     * Method that sets the new score of the priority three colour green
     *
     * @param scorePriorityThreeColourGreen - score of the priority three colour green
     * */
    public void setScorePriorityThreeColourGreen(int scorePriorityThreeColourGreen) {
        this.scorePriorityThreeColourGreen = scorePriorityThreeColourGreen;
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

    /**
     * Method that obtains the evaluation level
     *
     * @return Evaluation level
     * */

    public String getLevel() {
        return level;
    }

    /**
     * Method that sets the new evaluation status
     *
     * @param level - Evaluation level
     * */
    public void setLevel(String level) {
        this.level = level;
    }
}
