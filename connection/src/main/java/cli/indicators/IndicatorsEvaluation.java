package cli.indicators;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
;

public class IndicatorsEvaluation implements Serializable {

    @SerializedName("evaluationDate")
    public long evaluationDate;

    @SerializedName("idEvaluatedOrganization")
    public int idEvaluatedOrganization;
    
    @SerializedName("orgTypeEvaluated")
    public String orgTypeEvaluated;
    
    @SerializedName("idEvaluatorTeam")
    public int idEvaluatorTeam;
    
    @SerializedName("idEvaluatorOrganization")
    public int idEvaluatorOrganization;
    
    @SerializedName("orgTypeEvaluator")
    public String orgTypeEvaluator;
    
    @SerializedName("illness")
    public String illness;

    @SerializedName("scoreLevel1")
    public int scoreLevel1=0;

    @SerializedName("scoreLevel2")
    public int scoreLevel2=0;

    @SerializedName("scoreLevel3")
    public int scoreLevel3=0;

    @SerializedName("scoreLevel4")
    public int scoreLevel4=0;

    @SerializedName("scoreLevel5")
    public int scoreLevel5=0;

    @SerializedName("scoreLevel6")
    public int scoreLevel6=0;

    @SerializedName("totalScore")
    public int totalScore=0;

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

    @SerializedName("conclusionsEnglish")
    public String conclusionsEnglish;
    @SerializedName("conclusionsSpanish")
    public String conclusionsSpanish;
    @SerializedName("conclusionsFrench")
    public String conclusionsFrench;
    @SerializedName("conclusionsBasque")
    public String conclusionsBasque;
    @SerializedName("conclusionsCatalan")
    public String conclusionsCatalan;
    @SerializedName("conclusionsDutch")
    public String conclusionsDutch;
    @SerializedName("conclusionsGalician")
    public String conclusionsGalician;
    @SerializedName("conclusionsGerman")
    public String conclusionsGerman;
    @SerializedName("conclusionsItalian")
    public String conclusionsItalian;
    @SerializedName("conclusionsPortuguese")
    public String conclusionsPortuguese;

    @SerializedName("isFinished")
    public int isFinished=0;




    public IndicatorsEvaluation(long evaluationDate,int idEvaluatedOrganization,String orgTypeEvaluated,int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness, String observationsEnglish, String observationsSpanish, String observationsFrench, String observationsBasque, String observationsCatalan, String observationsDutch, String observationsGalician, String observationsGerman, String observationsItalian, String observationsPortuguese,String conclusionsEnglish, String conclusionsSpanish, String conclusionsFrench, String conclusionsBasque, String conclusionsCatalan, String conclusionsDutch, String conclusionsGalician, String conclusionsGerman, String conclusionsItalian, String conclusionsPortuguese, String conclusions, int scoreLevel1, int scoreLevel2, int scoreLevel3, int scoreLevel4, int scoreLevel5, int scoreLevel6, int totalScore, int isFinished){
        setEvaluationDate(evaluationDate);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgTypeEvaluator(orgTypeEvaluator);
        setIllness(illness);
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
        setScoreLevel1(scoreLevel1);
        setScoreLevel2(scoreLevel2);
        setScoreLevel3(scoreLevel3);
        setScoreLevel4(scoreLevel4);
        setScoreLevel5(scoreLevel5);
        setScoreLevel6(scoreLevel6);
        setTotalScore(totalScore);
        setIsFinished(isFinished);
    }



    public long getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(long evaluationDate) {
        this.evaluationDate = evaluationDate;
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

    public int getIdEvaluatorTeam() {
        return idEvaluatorTeam;
    }

    public void setIdEvaluatorTeam(int idEvaluatorTeam) {
        this.idEvaluatorTeam = idEvaluatorTeam;
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

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }



    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getScoreLevel1() {
        return scoreLevel1;
    }

    public void setScoreLevel1(int scoreLevel1) {
        this.scoreLevel1 = scoreLevel1;
    }

    public int getScoreLevel2() {
        return scoreLevel2;
    }

    public void setScoreLevel2(int scoreLevel2) {
        this.scoreLevel2 = scoreLevel2;
    }

    public int getScoreLevel3() {
        return scoreLevel3;
    }

    public void setScoreLevel3(int scoreLevel3) {
        this.scoreLevel3 = scoreLevel3;
    }

    public int getScoreLevel4() {
        return scoreLevel4;
    }

    public void setScoreLevel4(int scoreLevel4) {
        this.scoreLevel4 = scoreLevel4;
    }

    public int getScoreLevel5() {
        return scoreLevel5;
    }

    public void setScoreLevel5(int scoreLevel5) {
        this.scoreLevel5 = scoreLevel5;
    }

    public int getScoreLevel6() {
        return scoreLevel6;
    }

    public void setScoreLevel6(int scoreLevel6) {
        this.scoreLevel6 = scoreLevel6;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
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

    public String getConclusionsEnglish() {
        return conclusionsEnglish;
    }

    public void setConclusionsEnglish(String conclusionsEnglish) {
        this.conclusionsEnglish = conclusionsEnglish;
    }

    public String getConclusionsSpanish() {
        return conclusionsSpanish;
    }

    public void setConclusionsSpanish(String conclusionsSpanish) {
        this.conclusionsSpanish = conclusionsSpanish;
    }

    public String getConclusionsFrench() {
        return conclusionsFrench;
    }

    public void setConclusionsFrench(String conclusionsFrench) {
        this.conclusionsFrench = conclusionsFrench;
    }

    public String getConclusionsBasque() {
        return conclusionsBasque;
    }

    public void setConclusionsBasque(String conclusionsBasque) {
        this.conclusionsBasque = conclusionsBasque;
    }

    public String getConclusionsCatalan() {
        return conclusionsCatalan;
    }

    public void setConclusionsCatalan(String conclusionsCatalan) {
        this.conclusionsCatalan = conclusionsCatalan;
    }

    public String getConclusionsDutch() {
        return conclusionsDutch;
    }

    public void setConclusionsDutch(String conclusionsDutch) {
        this.conclusionsDutch = conclusionsDutch;
    }

    public String getConclusionsGalician() {
        return conclusionsGalician;
    }

    public void setConclusionsGalician(String conclusionsGalician) {
        this.conclusionsGalician = conclusionsGalician;
    }

    public String getConclusionsGerman() {
        return conclusionsGerman;
    }

    public void setConclusionsGerman(String conclusionsGerman) {
        this.conclusionsGerman = conclusionsGerman;
    }

    public String getConclusionsItalian() {
        return conclusionsItalian;
    }

    public void setConclusionsItalian(String conclusionsItalian) {
        this.conclusionsItalian = conclusionsItalian;
    }

    public String getConclusionsPortuguese() {
        return conclusionsPortuguese;
    }

    public void setConclusionsPortuguese(String conclusionsPortuguese) {
        this.conclusionsPortuguese = conclusionsPortuguese;
    }
}
