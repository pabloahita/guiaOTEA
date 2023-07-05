package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationReg;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import otea.connection.caller.EvidencesCaller;
import otea.connection.caller.IndicatorsCaller;
import otea.connection.caller.IndicatorsEvaluationRegsCaller;
import otea.connection.caller.IndicatorsEvaluationsCaller;

public class DoIndicatorsEvaluation extends AppCompatActivity implements View.OnClickListener{

    int current_indicator=0;
    int num_indicators=0;

    int num_evidences_reached=0;

    Map<Indicator,Integer> num_evidences_reached_per_indicator;
    
    boolean[][] switches_values;

    List<Indicator> indicators;

    IndicatorsEvaluation current_evaluation;

    ProgressBar progressBar;

    TextView textView;

    Organization evaluatedOrganization;

    EvaluatorTeam evaluatorTeam;

    List<IndicatorsEvaluationReg> regs;

    int total_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);

        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.loading);

        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        num_evidences_reached_per_indicator=new HashMap<Indicator,Integer>();


        int evidences_per_indicator=getIntent().getIntExtra("evidence_per_indicator",-1);

        indicators=new LinkedList<>();
        num_indicators=getIntent().getIntExtra("num_indicators",-1);

        for(int i=1;i<=num_indicators;i++){
            indicators.add((Indicator) getIntent().getSerializableExtra("indicator "+i));
        }


        switches_values=new boolean[num_indicators][evidences_per_indicator];
        TextView indicatorCaption = (TextView) findViewById(R.id.indicator_caption);
        Switch evidence1=(Switch) findViewById(R.id.evidence1);
        Switch evidence2=(Switch) findViewById(R.id.evidence2);
        Switch evidence3=(Switch) findViewById(R.id.evidence3);
        Switch evidence4=(Switch) findViewById(R.id.evidence4);
        Button previous_indicator=(Button) findViewById(R.id.previous_indicator);
        Button next_indicator=(Button) findViewById(R.id.next_indicator);
        num_evidences_reached_per_indicator=new HashMap<Indicator, Integer>();

        evaluatedOrganization=(Organization) getIntent().getSerializableExtra("evaluatedOrganization");
        evaluatorTeam=(EvaluatorTeam) getIntent().getSerializableExtra("evaluatorTeam");


        if(Locale.getDefault().getLanguage().equals("es")) {
            indicatorCaption.setText("Indicador "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionSpanish());
        } else if(Locale.getDefault().getLanguage().equals("fr")) {
            indicatorCaption.setText("Indicateur "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionFrench());
        } else {//Default
            indicatorCaption.setText("Indicator "+indicators.get(current_indicator).getIdIndicator()+": "+indicators.get(current_indicator).getDescriptionEnglish());
        }
        Indicator i=indicators.get(current_indicator);
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
        Switch sw = null;
        switch (view.getId()) {
            case R.id.evidence1: {
                sw = (Switch) findViewById(R.id.evidence1);
                if (sw.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][0] = true;
                    }
                    Log.d("SW1T", "Switch1 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][0] = false;
                    }
                    Log.d("SW1F", "Switch1 false with " + num_evidences_reached + " reached evidences");
                }
                break;
            }
            case R.id.evidence2: {
                sw = (Switch) findViewById(R.id.evidence2);
                if (sw.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][1] = true;
                    }
                    Log.d("SW2T", "Switch2 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][1] = false;
                    }
                    Log.d("SW2F", "Switch2 false with " + num_evidences_reached + " reached evidences");
                }
                break;
            }
            case R.id.evidence3: {
                sw = (Switch) findViewById(R.id.evidence3);
                if (sw.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][2] = true;
                    }
                    Log.d("SW3T", "Switch3 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][2] = false;
                    }
                    Log.d("SW3F", "Switch3 false with " + num_evidences_reached + " reached evidences");
                }
                break;
            }
            case R.id.evidence4: {
                sw = (Switch) findViewById(R.id.evidence4);
                if (sw.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][3] = true;
                    }
                    Log.d("SW4T", "Switch4 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][3] = false;
                    }
                    Log.d("SW4F", "Switch4 false with " + num_evidences_reached + " reached evidences");
                }
                break;
            }
            case R.id.previous_indicator: {
                Log.d("PI", "Previous Indicator Pressed");
                if (current_indicator == 0) {
                    Intent intent = new Intent(this, gui.mainMenu.evaluator.MainMenu.class);
                    intent.putExtra("user", getIntent().getSerializableExtra("user"));
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (num_evidences_reached_per_indicator.containsKey(indicators.get(current_indicator))) {
                                num_evidences_reached_per_indicator.replace(indicators.get(current_indicator), num_evidences_reached);
                            } else {
                                num_evidences_reached_per_indicator.put(indicators.get(current_indicator), num_evidences_reached);
                            }
                            current_indicator--;
                            changeIndicator();
                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                        }
                    }, 100);
                }
                break;
            }
            case R.id.next_indicator: {
                Log.d("NI", "Next Indicator Pressed");
                if (current_indicator < num_indicators - 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (num_evidences_reached_per_indicator.containsKey(indicators.get(current_indicator))) {
                                num_evidences_reached_per_indicator.replace(indicators.get(current_indicator), num_evidences_reached);
                            } else {
                                num_evidences_reached_per_indicator.put(indicators.get(current_indicator), num_evidences_reached);
                            }
                            current_indicator++;
                            changeIndicator();
                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                        }
                    }, 100);
                } else {
                    //current_evaluation.setResults(num_evidences_reached_per_indicator);
                    //current_evaluation.getEvaluatedOrganization().addEvaluation(current_evaluation);
                    //current_evaluation=new IndicatorsEvaluation(long.valueOf());
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(getString(R.string.calculating_results));
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            try {
                                // Obtener la fecha actual en formato de cadena
                                String dateString = dateFormat.format(new java.util.Date());
                                Date date = dateFormat.parse(dateString);
                                long evaluationDate = date.getTime();
                                total_score=getScore();

                                //En la demo solo hay indicadores de nivel 1
                                current_evaluation = new IndicatorsEvaluation(evaluationDate,evaluatedOrganization.getIdOrganization(),evaluatedOrganization.getOrgType(),evaluatorTeam.getIdEvaluatorTeam(),evaluatorTeam.getIdOrganization(),evaluatorTeam.getOrgType(),evaluatorTeam.getIllness(),total_score,0,0,0,0,0,total_score);
                                regs=new LinkedList<>();
                                for(int i=0;i<switches_values.length;i++){
                                    for(int j=0;j<switches_values[i].length;j++){
                                        int isMarked=-1;
                                        if(switches_values[i][j]==true){
                                            isMarked=1;
                                        }
                                        else{
                                            isMarked=0;
                                        }
                                        regs.add(new IndicatorsEvaluationReg(evaluationDate,evaluatedOrganization.getIdOrganization(), evaluatedOrganization.getOrgType(), evaluatorTeam.getIllness(), indicators.get(i).getIdIndicator(), indicators.get(i).getEvidences().get(j).getIdEvidence(), isMarked, indicators.get(i).getIndicatorVersion()));

                                    }
                                }
                                IndicatorsEvaluationsCaller.Create(current_evaluation);
                                for(IndicatorsEvaluationReg reg:regs){
                                    IndicatorsEvaluationRegsCaller.Create(reg);
                                }

                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }

                            Intent intent = new Intent(getApplicationContext(), gui.mainMenu.evaluator.MainMenu.class);
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            String level="";
                            if(Locale.getDefault().equals("es")) {

                                if(total_score>=198 && total_score<=246){
                                    level="EXCELENTE";
                                }
                                if(total_score>=149 && total_score<=197){
                                    level="MUY BUENO";
                                }
                                if(total_score>=100 && total_score<=148){
                                    level="BUENO";
                                }
                                if(total_score>=51 && total_score<=99){
                                    level="MEJORABLE";
                                }else{
                                    level="MUY MEJORABLE";
                                }
                                Toast.makeText(getApplicationContext(), total_score + " points - "+level, Toast.LENGTH_LONG);

                            }else if(Locale.getDefault().equals("fr")){
                                if(total_score>=198 && total_score<=246){
                                    level="EXCELLENT";
                                }
                                if(total_score>=149 && total_score<=197){
                                    level="TRÈS BON";
                                }
                                if(total_score>=100 && total_score<=148){
                                    level="BON";
                                }
                                if(total_score>=51 && total_score<=99){
                                    level="AMÉLIORABLE";
                                }else{
                                    level="TRÈS AMÉLIORABLE";
                                }
                                Toast.makeText(getApplicationContext(), total_score + " points - "+level, Toast.LENGTH_LONG).show();
                            }else{
                                if(total_score>=198 && total_score<=246){
                                    level="EXCELLENT";
                                }
                                if(total_score>=149 && total_score<=197){
                                    level="VERY GOOD";
                                }
                                if(total_score>=100 && total_score<=148){
                                    level="GOOD";
                                }
                                if(total_score>=51 && total_score<=99){
                                    level="IMPROVABLE";
                                }else{
                                    level="VERY IMPROVABLE";
                                }
                                Toast.makeText(getApplicationContext(), total_score + " points - "+level, Toast.LENGTH_LONG).show();

                            }
                            startActivity(intent);
                        }
                    },100);

                    break;
                }
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

    public Integer getScore(){
        int[][] numberOfIndicatorsPerLevel=new int[4][3];
        int[][] multiplicators=new int[4][3];
        Map<Indicator,Integer> filled=new HashMap<Indicator,Integer>();
        Integer[] numberOfIndicatorsPerAmbit=new Integer[6];
        int score=0;
        for(Indicator i:indicators){
            if(!num_evidences_reached_per_indicator.containsKey(i)){num_evidences_reached_per_indicator.put(i,0);}
            i.setNumFilledEvidences(num_evidences_reached_per_indicator.get(i));
            int ind=-1;
            if (num_evidences_reached_per_indicator.get(i)==0 || num_evidences_reached_per_indicator.get(i)==1){ind=0;}
            if (num_evidences_reached_per_indicator.get(i)==2 || num_evidences_reached_per_indicator.get(i)==3){ind=1;}
            if (num_evidences_reached_per_indicator.get(i)==4){ind=2;}
            numberOfIndicatorsPerLevel[(int) i.getPriority()-1][ind]++;
            if(multiplicators[(int) i.getPriority()-1][ind]!=i.getMultiplicator()){
                multiplicators[(int) i.getPriority()-1][ind]=i.getMultiplicator();
            }
        }
        for(int i=0;i<numberOfIndicatorsPerLevel.length;i++){
            for(int j=0;j<numberOfIndicatorsPerLevel[i].length;j++){
                score+=(numberOfIndicatorsPerLevel[i][j]*multiplicators[i][j]);
            }
        }
        return score;
    }


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