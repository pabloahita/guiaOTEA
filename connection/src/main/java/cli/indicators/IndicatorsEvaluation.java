package cli.indicators;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cli.organization.data.EvaluatorTeam;
import otea.connection.caller.EvaluatorTeamsCaller;
import otea.connection.caller.IndicatorsCaller;
import otea.connection.caller.IndicatorsEvaluationRegsCaller;
import otea.connection.caller.OrganizationsCaller;

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


    public IndicatorsEvaluation(long evaluationDate,int idEvaluatedOrganization,String orgTypeEvaluated,int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness, int scoreLevel1, int scoreLevel2, int scoreLevel3, int scoreLevel4, int scoreLevel5, int scoreLevel6, int totalScore){
        setEvaluationDate(evaluationDate);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgTypeEvaluator(orgTypeEvaluator);
        setIllness(illness);
        setScoreLevel1(scoreLevel1);
        setScoreLevel2(scoreLevel2);
        setScoreLevel3(scoreLevel3);
        setScoreLevel4(scoreLevel4);
        setScoreLevel5(scoreLevel5);
        setScoreLevel6(scoreLevel6);
        setTotalScore(totalScore);
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
}
