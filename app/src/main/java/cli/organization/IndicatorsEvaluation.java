package cli.organization; //Pending package changing if it was necessary

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    private Map<Indicator,Integer> evaluation;

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
        if(this.evaluation==null) {
            this.evaluation = new HashMap<Indicator, Integer>();
        }
        Scanner sc=new Scanner(System.in);
        List<Indicator> indicators=getIndicators();
        int num_indicators=indicators.size();
        int i=0;
        while(i>=0 && i<num_indicators){
            Indicator current_indicator=indicators.get(i);
            System.out.println(current_indicator.getDescription());
            List<Evidence> evidences=current_indicator.getEvidences();
            int num_evidences=evidences.size();
            List<Boolean> aux=new LinkedList<Boolean>();
            for(int j=0;j<num_evidences;j++){
                aux.add(false);
                Evidence current_evidence=evidences.get(j);
                System.out.println("Evidencia "+(j+1)+": "+current_evidence.getDescription());
            }
            int k=0;
            int value=0;
            while(k>=0 && k<num_evidences){
                int n=-1;
                while(n<0 || n>3){
                    System.out.print("Introduce valor de la evidencia " + (k + 1) + " (0=Verdadero;1=Falso;2=Anterior Evidencia;3=Siguiente Evidencia): ");
                    n = sc.nextInt();
                }
                if(n==0){
                    if(!aux.get(k)) {
                        value++;
                        aux.set(k,true);
                    }
                }
                else if(n==1){
                    if(aux.get(k)) {
                        value--;
                        aux.set(k,false);
                    }
                }
                if(n==0 || n==1 || n==3){
                    k++;
                }
                else{
                    k--;
                }
            }
            if(this.evaluation.containsKey(current_indicator)){
                this.evaluation.replace(current_indicator, value);
            }
            else {
                this.evaluation.put(current_indicator, value);
            }
            int ax=i;
            if(i==0){
                int n=-1;
                while(n!=2){
                    System.out.println("Presione 2 para ir a la evidencia siguiente: ");
                    n=sc.nextInt();
                }
                ax++;
            }
            else if(i<num_indicators-1){
                int n=-1;
                while(n<0||n>2){
                    System.out.println("Presiona 0 para ir a la evidencia anterior o 1 para editar la evidencia actual o 2 para ir a la evidencia siguiente: ");
                    n=sc.nextInt();
                }
                if(n==0){
                    ax--;
                }
                else if(n==2){
                    ax++;
                }
            }
            else{
                int n=-1;
                while(n<0||n>2){
                    System.out.println("Presiona 0 para ir a la evidencia anterior o 1 para editar la evidencia actual o 2 para finalizar con la evaluación de indicadores: ");
                    n=sc.nextInt();
                }
                if(n==0){
                    ax--;
                }
                else if(n==2){
                    ax++;
                }
            }
            i=ax;
        }
        System.out.println("Puntuación total: "+getResults());
    }

    public void setEvaluation(Map<Indicator,Integer> evaluation){
        this.evaluation=evaluation;
    }
    public int getResults(){
        int[][] numberOfIndicatorsPerLevel=new int[4][3];
        int[][] multiplicators=new int[4][3];
        for(Indicator i: this.evaluation.keySet()){
            int ind=-1;
            if (this.evaluation.get(i)==0 || this.evaluation.get(i)==1){ind=0;}
            if (this.evaluation.get(i)==2 || this.evaluation.get(i)==3){ind=1;}
            if (this.evaluation.get(i)==4){ind=2;}
            numberOfIndicatorsPerLevel[(int) i.getPriority()-1][ind]++;
            if(multiplicators[(int) i.getPriority()-1][ind]!=i.getMultiplicator(this.evaluation.get(i))){
                multiplicators[(int) i.getPriority()-1][ind]=i.getMultiplicator(this.evaluation.get(i));
            }
        }
        int total_score=0;
        for(int i=0;i<numberOfIndicatorsPerLevel.length;i++){
            for(int j=0;j<numberOfIndicatorsPerLevel[i].length;j++){
                total_score+=(numberOfIndicatorsPerLevel[i][j]*multiplicators[i][j]);
            }
        }
        return total_score;
    }

}
