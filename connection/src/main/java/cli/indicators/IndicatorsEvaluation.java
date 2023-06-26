package cli.indicators;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cli.organization.EvaluatedOrganization;
import cli.organization.data.EvaluatorTeam;
import otea.connection.caller.EvaluatorTeamsCaller;
import otea.connection.caller.IndicatorsCaller;
import otea.connection.caller.OrganizationsCaller;

public class IndicatorsEvaluation {

    @SerializedName("evaluationDate")
    private Timestamp evaluationDate;

    @SerializedName("idEvaluatedOrganization")
    private int idEvaluatedOrganization;
    
    @SerializedName("orgTypeEvaluated")
    private String orgTypeEvaluated;
    
    @SerializedName("idEvaluatorTeam")
    private int idEvaluatorTeam;
    
    @SerializedName("idEvaluatorOrganization")
    private int idEvaluatorOrganization;
    
    @SerializedName("orgTypeEvaluator")
    private String orgTypeEvaluator;
    
    @SerializedName("illness")
    private String illness;

    @SerializedName("totalScore")
    private int totalScore=0;

    private EvaluatedOrganization evaluated_organization;
    private EvaluatorTeam evaluator_team;

    private List<IndicatorsEvaluationReg> evaluation;

    private List<Indicator> indicators;


    public IndicatorsEvaluation(Timestamp evaluationDate,int idEvaluatedOrganization,String orgTypeEvaluated,int idEvaluatorTeam, int idEvaluatorOrganization, String orgTypeEvaluator, String illness, int totalScore){
        setEvaluationDate(evaluationDate);
        setIdEvaluatedOrganization(idEvaluatedOrganization);
        setOrgTypeEvaluated(orgTypeEvaluated);
        setIdEvaluatorTeam(idEvaluatorTeam);
        setIdEvaluatorOrganization(idEvaluatorOrganization);
        setOrgTypeEvaluator(orgTypeEvaluator);
        setIllness(illness);
        setTotalScore(totalScore);
        setEvaluation(new LinkedList<IndicatorsEvaluationReg>());
        setIndicators(IndicatorsCaller.getInstance().obtainIndicators(illness));
        setEvaluatorTeam(EvaluatorTeamsCaller.getInstance().obtainEvaluatorTeam(idEvaluatorTeam,idEvaluatorOrganization,orgTypeEvaluator,illness));
        setEvaluatedOrganization((EvaluatedOrganization) OrganizationsCaller.getInstance().obtainOrganization(idEvaluatedOrganization,orgTypeEvaluated,illness));
    }

    
    
    /*public IndicatorsEvaluation(Date evaluationDate, EvaluatedOrganization evaluated_organization, EvaluatorTeam evaluator_team) {
        setEvaluationDate(evaluationDate);
        setEvaluatedOrganization(evaluated_organization);
        setEvaluatorTeam(evaluator_team);
    }^*/

    public Timestamp getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Timestamp evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public EvaluatedOrganization getEvaluatedOrganization() {
        return evaluated_organization;
    }

    public void setEvaluatedOrganization(EvaluatedOrganization evaluated_organization) {
        this.evaluated_organization = evaluated_organization;
    }

    public EvaluatorTeam getEvaluatorTeam() {
        return evaluator_team;
    }

    public void setEvaluatorTeam(EvaluatorTeam evaluator_team) {
        this.evaluator_team = evaluator_team;
    }

    public List<Indicator> getIndicators(){
        return indicators;

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


    private void setIndicators(List<Indicator> indicators) {
        this.indicators=indicators;
    }



    public void setEvaluation(List<IndicatorsEvaluationReg> evaluation){
        this.evaluation=evaluation;
    }

    public void setResults(Map<Indicator,Integer> results){
        for(Indicator i: results.keySet()){
            List<Evidence> evidences=i.getEvidences();
        }
    }
    public void getResults(){
        int[][] numberOfIndicatorsPerLevel=new int[4][3];
        int[][] multiplicators=new int[4][3];
        Map<Indicator,Integer> filled=new HashMap<Indicator,Integer>();
        for(IndicatorsEvaluationReg reg:evaluation){
            Indicator indicator=indicators.get(reg.getIndicatorId()-1);
            if(reg.getIsMarked()==0){
                if(!filled.containsKey(indicator)){
                    filled.put(indicator,1);
                }
                else{
                    filled.put(indicator,filled.get(indicator)+1);
                }
            }
        }
        for(Indicator i:indicators){
            if(!filled.containsKey(i)){filled.put(i,0);}
            i.setNumFilledEvidences(filled.get(i));
            int ind=-1;
            if (filled.get(i)==0 || filled.get(i)==1){ind=0;}
            if (filled.get(i)==2 || filled.get(i)==3){ind=1;}
            if (filled.get(i)==4){ind=2;}
            numberOfIndicatorsPerLevel[(int) i.getPriority()-1][ind]++;
            if(multiplicators[(int) i.getPriority()-1][ind]!=i.getMultiplicator()){
                multiplicators[(int) i.getPriority()-1][ind]=i.getMultiplicator();
            }
        }
        for(int i=0;i<numberOfIndicatorsPerLevel.length;i++){
            for(int j=0;j<numberOfIndicatorsPerLevel[i].length;j++){
                totalScore+=(numberOfIndicatorsPerLevel[i][j]*multiplicators[i][j]);
            }
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public EvaluatedOrganization getEvaluated_organization() {
        return evaluated_organization;
    }

    public void setEvaluated_organization(EvaluatedOrganization evaluated_organization) {
        this.evaluated_organization = evaluated_organization;
    }

    public EvaluatorTeam getEvaluator_team() {
        return evaluator_team;
    }

    public void setEvaluator_team(EvaluatorTeam evaluator_team) {
        this.evaluator_team = evaluator_team;
    }

    public List<IndicatorsEvaluationReg> getEvaluation() {
        return evaluation;
    }
}
