package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import otea.connection.caller.EvidencesCaller;
import otea.connection.caller.IndicatorsCaller;

public class DoIndicatorsEvaluation extends AppCompatActivity implements View.OnClickListener{

    int current_indicator=0;
    int num_indicators=0;

    int num_evidences_reached=0;

    Map<Indicator,Integer> num_evidences_reached_per_indicator;
    
    boolean[][] switches_values;

    List<Indicator> indicators;

    IndicatorsEvaluation current_evaluation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);
        //current_evaluation=startEvaluation();
        num_evidences_reached_per_indicator=new HashMap<Indicator,Integer>();
        //List<Indicator> indicators=indicators;
        indicators= IndicatorsCaller.obtainIndicators("AUTISM");
        int c=0;
        num_indicators=indicators.size();
        Indicator i=indicators.get(current_indicator);
        if(i.getEvidences()==null){//En caso de que no se hayan descargado las evidencias del indicador actual
            i.setEvidences(EvidencesCaller.obtainEvidences(i.getIdIndicator(),i.getIndicatorType(),i.getIndicatorVersion()));
        }
        int evidences_per_indicator=i.getEvidences().size();
        switches_values=new boolean[num_indicators][evidences_per_indicator];
        TextView indicatorCaption = (TextView) findViewById(R.id.indicator_caption);
        Switch evidence1=(Switch) findViewById(R.id.evidence1);
        Switch evidence2=(Switch) findViewById(R.id.evidence2);
        Switch evidence3=(Switch) findViewById(R.id.evidence3);
        Switch evidence4=(Switch) findViewById(R.id.evidence4);
        Button previous_indicator=(Button) findViewById(R.id.previous_indicator);
        Button next_indicator=(Button) findViewById(R.id.next_indicator);
        Map<Indicator,Integer> num_evidences_reached_per_indicator=new HashMap<Indicator, Integer>();
        if(Locale.getDefault().getLanguage().equals("es")) {
            indicatorCaption.setText("Indicador "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionSpanish());
        } else if(Locale.getDefault().getLanguage().equals("fr")) {
            indicatorCaption.setText("Indicateur "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionFrench());
        } else {//Default
            indicatorCaption.setText("Indicator "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionEnglish());
        }

        List<Evidence> evidences=i.getEvidences();
        if(Locale.getDefault().getLanguage().equals("es")) {
            evidence1.setText("Evidencia 1: "+evidences.get(0).getDescriptionSpanish());
            evidence2.setText("Evidencia 2: "+evidences.get(1).getDescriptionSpanish());
            evidence3.setText("Evidencia 3: "+evidences.get(2).getDescriptionSpanish());
            evidence4.setText("Evidencia 4: "+evidences.get(3).getDescriptionSpanish());
        }else if(Locale.getDefault().getLanguage().equals("fr")) {
            evidence1.setText("Preuve 1: "+evidences.get(0).getDescriptionFrench());
            evidence2.setText("Preuve 2: "+evidences.get(1).getDescriptionFrench());
            evidence3.setText("Preuve 3: "+evidences.get(2).getDescriptionFrench());
            evidence4.setText("Preuve 4: "+evidences.get(3).getDescriptionFrench());
        }else{
            evidence1.setText("Evidence 1: "+evidences.get(0).getDescriptionEnglish());
            evidence2.setText("Evidence 2: "+evidences.get(1).getDescriptionEnglish());
            evidence3.setText("Evidence 3: "+evidences.get(2).getDescriptionEnglish());
            evidence4.setText("Evidence 4: "+evidences.get(3).getDescriptionEnglish());
        }
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
                if(current_indicator==0){
                    Intent intent=new Intent(this, gui.MainActivity.class);
                    intent.putExtra("user",getIntent().getSerializableExtra("user"));
                    startActivity(intent);}
                else{
                    if(num_evidences_reached_per_indicator.containsKey(indicators.get(current_indicator))) {
                        num_evidences_reached_per_indicator.replace(indicators.get(current_indicator),num_evidences_reached);
                    }
                    else{
                        num_evidences_reached_per_indicator.put(indicators.get(current_indicator),num_evidences_reached);
                    }
                    current_indicator--;
                    changeIndicator();}
                break;
            }
            case R.id.next_indicator:{
                Log.d("NI","Next Indicator Pressed");
                if(current_indicator<num_indicators-1){
                    if(num_evidences_reached_per_indicator.containsKey(indicators.get(current_indicator))) {
                        num_evidences_reached_per_indicator.replace(indicators.get(current_indicator),num_evidences_reached);
                    }
                    else{
                        num_evidences_reached_per_indicator.put(indicators.get(current_indicator),num_evidences_reached);
                    }
                    current_indicator++;changeIndicator();}
                else{
                    current_evaluation.setResults(num_evidences_reached_per_indicator);
                    //current_evaluation.getEvaluatedOrganization().addEvaluation(current_evaluation);
                    Intent intent=new Intent(this, gui.mainMenu.evaluator.MainMenu.class);
                    startActivity(intent);
                    current_evaluation.getResults();
                    Toast.makeText(getApplicationContext(),"Total score: "+current_evaluation.getTotalScore()+" points",Toast.LENGTH_LONG);
                }
                break;
            }
        }

    }


    public void changeIndicator(){
        if(num_evidences_reached_per_indicator.containsKey(indicators.get(current_indicator))){
            num_evidences_reached=num_evidences_reached_per_indicator.get(indicators.get(current_indicator));
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
        if(Locale.getDefault().getLanguage().equals("es")) {
            indicatorCaption.setText("Indicador "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionSpanish());
        } else if(Locale.getDefault().getLanguage().equals("fr")) {
            indicatorCaption.setText("Indicateur "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionFrench());
        } else {//Default
            indicatorCaption.setText("Indicator "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionEnglish());
        }
        Indicator i=indicators.get(current_indicator);
        if(i.getEvidences()==null){//En caso de que no se hayan descargado las evidencias del indicador actual
            i.setEvidences(EvidencesCaller.obtainEvidences(i.getIdIndicator(),i.getIndicatorType(),i.getIndicatorVersion()));
        }
        List<Evidence> evidences=i.getEvidences();
        if(Locale.getDefault().getLanguage().equals("es")) {
            evidence1.setText("Evidencia 1: "+evidences.get(0).getDescriptionSpanish());
            evidence2.setText("Evidencia 2: "+evidences.get(1).getDescriptionSpanish());
            evidence3.setText("Evidencia 3: "+evidences.get(2).getDescriptionSpanish());
            evidence4.setText("Evidencia 4: "+evidences.get(3).getDescriptionSpanish());
        }else if(Locale.getDefault().getLanguage().equals("fr")) {
            evidence1.setText("Preuve 1: "+evidences.get(0).getDescriptionFrench());
            evidence2.setText("Preuve 2: "+evidences.get(1).getDescriptionFrench());
            evidence3.setText("Preuve 3: "+evidences.get(2).getDescriptionFrench());
            evidence4.setText("Preuve 4: "+evidences.get(3).getDescriptionFrench());
        }else{
            evidence1.setText("Evidence 1: "+evidences.get(0).getDescriptionEnglish());
            evidence2.setText("Evidence 2: "+evidences.get(1).getDescriptionEnglish());
            evidence3.setText("Evidence 3: "+evidences.get(2).getDescriptionEnglish());
            evidence4.setText("Evidence 4: "+evidences.get(3).getDescriptionEnglish());
        }
        Log.d("NEWIND","Current indicator has "+num_evidences_reached+" reached evidences");
    }

    /*public cli.indicators.IndicatorsEvaluation startEvaluation(){
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
        cli.indicators.IndicatorsEvaluation evaluation=new cli.indicators.IndicatorsEvaluation(new Date(aux.getTime()),evaluatedOrganization,evaluatorTeam);
        //evaluation.getEvaluatedOrganization().setAssets(getAssets());
        evaluation.getEvaluatedOrganization().setIndicators();
        return evaluation;
    }*/


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
            intent.putExtra("user",getIntent().getSerializableExtra("user"));
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

}