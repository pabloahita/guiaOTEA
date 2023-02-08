package cli.organization; //Pending package changing if it was necessary

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.data.EvaluatorTeam;

public class IndicatorsEvaluation {
    private Date evaluation_date;
    private EvaluatedOrganization evaluated_organization;
    private EvaluatorTeam evaluator_team;
    private Map<Indicator,Float> results;

    public IndicatorsEvaluation(Date evaluation_date, EvaluatedOrganization evaluated_organization, EvaluatorTeam evaluator_team) {
        setEvaluationDate(evaluation_date);
        setEvaluatedOrganization(evaluated_organization);
        setEvaluatorTeam(evaluator_team);
    }

    public Date getEvaluationDate() {
        return evaluation_date;
    }

    public void setEvaluationDate(Date evaluation_date) {
        this.evaluation_date = evaluation_date;
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
        return evaluated_organization.getIndicators();
    }

    public void doEvaluationCli(){
        if(this.results==null){
            this.results=new HashMap<Indicator,Float>();
        }
        Scanner sc=new Scanner(System.in);
        List<Indicator> indicators=getIndicators();
        int num_indicators=indicators.size();
        int i=0;
        while(i>=0 && i<num_indicators){
            Indicator current_indicator=indicators.get(i);
            System.out.println("Indicador "+(i+1)+": "+current_indicator.getDescription());
            List<Evidence> evidences=current_indicator.getEvidences();
            int num_evidences=evidences.size();
            Map<Evidence,Boolean> aux=new HashMap<Evidence,Boolean>();
            for(int j=0;j<num_evidences;j++){
                Evidence current_evidence=evidences.get(j);
                System.out.println("Evidencia "+(j+1)+": "+current_evidence.getDescription());
                aux.put(current_evidence,false);//Create default values
            }
            int k=0;
            float indicator_value=0;
            while(k>=0 && k<num_evidences){
                int n=-1;
                Evidence current_evidence=evidences.get(k);
                while(n<0 || n>3){
                    System.out.print("Introduce valor de la evidencia " + (k + 1) + " (0=Verdadero;1=Falso;2=Anterior Evidencia;3=Siguiente Evidencia): ");
                    n = sc.nextInt();
                }
                if(n==0){
                    if(!aux.get(current_evidence)) {
                        indicator_value += current_evidence.getValue();
                        aux.replace(current_evidence,true);
                    }
                }
                else if(n==1){
                    if(aux.get(current_evidence)) {
                        indicator_value -= current_evidence.getValue();
                        aux.replace(current_evidence,false);
                    }
                }
                if(n==0 || n==1 || n==3){
                    k++;
                }
                else{
                    k--;
                }
            }
            this.results.put(current_indicator,indicator_value* current_indicator.getPriority());
            int ax=i;
            if(i==0){
                int n=-1;
                while(n!=0){
                    System.out.println("Presione 0 para ir a la evidencia siguiente: ");
                    n=sc.nextInt();
                }
                ax++;
            }
            else if(i<num_indicators-1){
                int n=-1;
                while(n<0||n>1){
                    System.out.println("Presiona 0 para ir a la evidencia anterior o 1 para ir a la evidencia siguiente: ");
                    n=sc.nextInt();
                }
                if(n==0){
                    ax--;
                }
                else{
                    ax++;
                }
            }
            else{
                int n=-1;
                while(n<0||n>1){
                    System.out.println("Presiona 0 para ir a la evidencia anterior o 1 para finalizar con la evaluación de indicadores: ");
                    n=sc.nextInt();
                }
                if(n==0){
                    ax--;
                }
                else{
                    ax++;
                }
            }
            i=ax;
        }
        float value=0;
        for(float v:this.results.values()){
            System.out.println("Añadir "+v);
            value+=v;
        }
        System.out.println("Total="+value);
    }
    public Map<Indicator, Float> getResults() {
        return results;
    }

    public void setResults(Map<Indicator, Float> results) {
        this.results = results;
    }
}
