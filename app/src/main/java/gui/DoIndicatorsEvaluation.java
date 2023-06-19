package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.IndicatorsEvaluation;

public class DoIndicatorsEvaluation extends AppCompatActivity implements View.OnClickListener{

    int current_indicator=0;
    int num_indicators=0;

    int num_evidences_reached=0;

    Map<Indicator,Integer> num_evidences_reached_per_indicator;
    
    boolean[][] switches_values;

    IndicatorsEvaluation current_evaluation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);
        //current_evaluation=startEvaluation();
        num_evidences_reached_per_indicator=new HashMap<Indicator,Integer>();
        List<Indicator> indicators=current_evaluation.getIndicators();
        num_indicators=indicators.size();
        int evidences_per_indicator=indicators.get(0).getEvidences().size();
        switches_values=new boolean[num_indicators][evidences_per_indicator];
        TextView indicatorCaption = (TextView) findViewById(R.id.indicator_caption);
        Switch evidence1=(Switch) findViewById(R.id.evidence1);
        Switch evidence2=(Switch) findViewById(R.id.evidence2);
        Switch evidence3=(Switch) findViewById(R.id.evidence3);
        Switch evidence4=(Switch) findViewById(R.id.evidence4);
        Button previous_indicator=(Button) findViewById(R.id.previous_indicator);
        Button next_indicator=(Button) findViewById(R.id.next_indicator);
        Map<Indicator,Integer> num_evidences_reached_per_indicator=new HashMap<Indicator, Integer>();
        indicatorCaption.setText(indicators.get(current_indicator).getDescription());
        List<Evidence> evidences=indicators.get(current_indicator).getEvidences();
        evidence1.setText(evidences.get(0).getDescription());
        evidence2.setText(evidences.get(1).getDescription());
        evidence3.setText(evidences.get(2).getDescription());
        evidence4.setText(evidences.get(3).getDescription());
        evidence1.setOnClickListener(this);
        evidence2.setOnClickListener(this);
        evidence3.setOnClickListener(this);
        evidence4.setOnClickListener(this);
        previous_indicator.setOnClickListener(this);
        next_indicator.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Switch sw=null;
        switch(view.getId()){
            case R.id.evidence1:{
                sw=(Switch) findViewById(R.id.evidence1);
                if(sw.isChecked()) {
                    if(num_evidences_reached>=0 && num_evidences_reached<4){
                        num_evidences_reached++;
                        switches_values[current_indicator][0]=true;
                    }
                    Log.d("SW1T", "Switch1 true with "+num_evidences_reached+" reached evidences");
                }
                else{
                    if(num_evidences_reached>0 && num_evidences_reached<=4){
                        num_evidences_reached--;
                        switches_values[current_indicator][0]=false;
                    }
                    Log.d("SW1F", "Switch1 false with "+num_evidences_reached+" reached evidences");
                }
                break;
            }
            case R.id.evidence2:{
                sw=(Switch) findViewById(R.id.evidence2);
                if(sw.isChecked()) {
                    if(num_evidences_reached>=0 && num_evidences_reached<4){
                        num_evidences_reached++;
                        switches_values[current_indicator][1]=true;
                    }
                    Log.d("SW2T", "Switch2 true with "+num_evidences_reached+" reached evidences");
                }
                else{
                    if(num_evidences_reached>0 && num_evidences_reached<=4){
                        num_evidences_reached--;
                        switches_values[current_indicator][1]=false;
                    }
                    Log.d("SW2F", "Switch2 false with "+num_evidences_reached+" reached evidences");
                }
                break;
            }
            case R.id.evidence3:{
                sw=(Switch) findViewById(R.id.evidence3);
                if(sw.isChecked()) {
                    if(num_evidences_reached>=0 && num_evidences_reached<4){
                        num_evidences_reached++;
                        switches_values[current_indicator][2]=true;
                    }
                    Log.d("SW3T", "Switch3 true with "+num_evidences_reached+" reached evidences");
                }
                else{
                    if(num_evidences_reached>0 && num_evidences_reached<=4){
                        num_evidences_reached--;
                        switches_values[current_indicator][2]=false;
                    }
                    Log.d("SW3F", "Switch3 false with "+num_evidences_reached+" reached evidences");
                }
                break;
            }
            case R.id.evidence4:{
                sw=(Switch) findViewById(R.id.evidence4);
                if(sw.isChecked()) {
                    if(num_evidences_reached>=0 && num_evidences_reached<4){
                        num_evidences_reached++;
                        switches_values[current_indicator][3]=true;
                    }
                    Log.d("SW4T", "Switch4 true with "+num_evidences_reached+" reached evidences");
                }
                else{
                    if(num_evidences_reached>0 && num_evidences_reached<=4){
                        num_evidences_reached--;
                        switches_values[current_indicator][3]=false;
                    }
                    Log.d("SW4F", "Switch4 false with "+num_evidences_reached+" reached evidences");
                }
                break;
            }
            case R.id.previous_indicator:{
                Log.d("PI","Previous Indicator Pressed");
                if(current_indicator==0){Intent intent=new Intent(this, gui.MainActivity.class);
                    startActivity(intent);}
                else{
                    if(num_evidences_reached_per_indicator.containsKey(current_evaluation.getIndicators().get(current_indicator))) {
                        num_evidences_reached_per_indicator.replace(current_evaluation.getIndicators().get(current_indicator),num_evidences_reached);
                    }
                    else{
                        num_evidences_reached_per_indicator.put(current_evaluation.getIndicators().get(current_indicator),num_evidences_reached);
                    }
                    current_indicator--;
                    changeIndicator();}
                break;
            }
            case R.id.next_indicator:{
                Log.d("NI","Next Indicator Pressed");
                if(current_indicator<num_indicators-1){
                    if(num_evidences_reached_per_indicator.containsKey(current_evaluation.getIndicators().get(current_indicator))) {
                        num_evidences_reached_per_indicator.replace(current_evaluation.getIndicators().get(current_indicator),num_evidences_reached);
                    }
                    else{
                        num_evidences_reached_per_indicator.put(current_evaluation.getIndicators().get(current_indicator),num_evidences_reached);
                    }
                    current_indicator++;changeIndicator();}
                else{
                    current_evaluation.setEvaluation(num_evidences_reached_per_indicator);
                    //current_evaluation.getEvaluatedOrganization().addEvaluation(current_evaluation);
                    Intent intent=new Intent(this, gui.MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Total score: "+current_evaluation.getResults()+" points",Toast.LENGTH_LONG);
                }
                break;
            }
        }

    }


    public void changeIndicator(){
        if(num_evidences_reached_per_indicator.containsKey(current_evaluation.getIndicators().get(current_indicator))){
            num_evidences_reached=num_evidences_reached_per_indicator.get(current_evaluation.getIndicators().get(current_indicator));
        }
        else{num_evidences_reached=0;}
        TextView indicatorCaption = (TextView) findViewById(R.id.indicator_caption);
        Switch evidence1=(Switch) findViewById(R.id.evidence1);
        Switch evidence2=(Switch) findViewById(R.id.evidence2);
        Switch evidence3=(Switch) findViewById(R.id.evidence3);
        Switch evidence4=(Switch) findViewById(R.id.evidence4);
        evidence1.setChecked(switches_values[current_indicator][0]);
        evidence2.setChecked(switches_values[current_indicator][1]);
        evidence3.setChecked(switches_values[current_indicator][2]);
        evidence4.setChecked(switches_values[current_indicator][3]);
        indicatorCaption.setText(current_evaluation.getIndicators().get(current_indicator).getDescription());
        List<Evidence> evidences=current_evaluation.getIndicators().get(current_indicator).getEvidences();
        evidence1.setText(evidences.get(0).getDescription());
        evidence2.setText(evidences.get(1).getDescription());
        evidence3.setText(evidences.get(2).getDescription());
        evidence4.setText(evidences.get(3).getDescription());
        Log.d("NEWIND","Current indicator has "+num_evidences_reached+" reached evidences");
    }

    /*public cli.organization.IndicatorsEvaluation startEvaluation(){
        java.util.Date aux=new java.util.Date();
        EvaluatedOrganizationUser organization_principal=new EvaluatedOrganizationUser("MJ","LJ","mjlj@google.com","123456",655447788);
        EvaluatedOrganization evaluatedOrganization=new AutisticOrganization("Autismo Majadahonda", new Address(RoadType.CARRETERA,"de Boadilla del Monte",5,0,'/',28220,"Majadahonda (Madrid)","España"), 916390390, "cita@autismo.es", "");
        evaluatedOrganization.setOrganizationPrincipal(organization_principal);
        evaluatedOrganization.setOrganizationRepresentant(organization_principal);
        evaluatedOrganization.getOrganizationPrincipal().setOrganization(evaluatedOrganization);
        evaluatedOrganization.getOrganizationRepresentant().setOrganization(evaluatedOrganization);
        EvaluatorOrganization evaluatorOrganization=new CEvaluatorOrganization("Casa del Desarrollador", new Address(RoadType.CALLE,"Vitoria",171,5,'C',9007,"Burgos","Spain"), 947123456, "acasa@hotmail.com", "");
        EvaluatorOrganizationUser external_consultant=new EvaluatorOrganizationUser("MY", "dBG", "ydbg@ymail.com", "987654", 612345678);
        EvaluatorTeam evaluatorTeam=new EvaluatorTeam(new Date(aux.getTime()),external_consultant);
        evaluatorTeam.setMembers(new LinkedList<EvaluatorOrganizationUser>());
        evaluatorTeam.setOrganization(evaluatorOrganization);
        evaluatorTeam.getExternalConsultant().setOrganization((Organization) evaluatorOrganization);
        evaluatorTeam.getMembers().add(external_consultant);
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("AD", "AO", "adao@ymail.com", "246810", 658563214,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("AJ", "dBG", "ajdbg@ymail.com", "3691215", 678965412,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("MP", "MR", "mpmr@ymail.com", "481216", 696352125,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("S", "dBR", "sdbr@ymail.com", "5101520", 698765432,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("T", "GR", "tgr@ymail.com", "6121824", 698765432,(Organization) evaluatorOrganization));
        cli.organization.IndicatorsEvaluation evaluation=new cli.organization.IndicatorsEvaluation(new Date(aux.getTime()),evaluatedOrganization,evaluatorTeam);
        //evaluation.getEvaluatedOrganization().setAssets(getAssets());
        evaluation.getEvaluatedOrganization().setIndicators();
        return evaluation;
    }*/


}