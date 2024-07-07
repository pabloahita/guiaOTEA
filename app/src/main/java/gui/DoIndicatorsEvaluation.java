package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.MainMenu;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import cli.indicators.IndicatorsEvaluationSimpleEvidenceReg;
import misc.DateFormatter;
import otea.connection.controller.IndicatorsEvaluationEvidenceRegsController;
import otea.connection.controller.IndicatorsEvaluationEvidenceSimpleRegsController;
import otea.connection.controller.IndicatorsEvaluationIndicatorRegsController;
import otea.connection.controller.IndicatorsEvaluationsController;
import session.IndicatorsEvaluationUtil;
import session.FileManager;
import session.IndicatorsEvaluationRegsUtil;
import session.IndicatorsUtil;
import session.Session;
import session.StringPasser;

public class DoIndicatorsEvaluation extends AppCompatActivity {

    int current_indicator = 0;
    int current_ambit = 0;

    int current_subAmbit = 0;

    int current_subSubAmbit = 0;

    int num_indicators = 0;

    int num_evidences=0;

    List<Indicator> indicators;

    List<Evidence> evidences;

    IndicatorsEvaluation current_evaluation;


    TextView textView;


    IndicatorsEvaluationEvidenceReg[][] evidenceRegs;

    IndicatorsEvaluationIndicatorReg[] indicatorRegs;

    IndicatorsEvaluationSimpleEvidenceReg[][] simpleEvidenceRegs;


    ConstraintLayout background;

    ConstraintLayout base;


    CheckBox evidence1;
    CheckBox evidence2;
    CheckBox evidence3;
    CheckBox evidence4;

    TextInputEditText ev1Txt;

    TextInputEditText ev2Txt;

    TextInputEditText ev3Txt;

    TextInputEditText ev4Txt;

    List<IndicatorsEvaluationEvidenceReg> storedEvidencesRegs;

    List<IndicatorsEvaluationSimpleEvidenceReg> storedSimpleEvidencesRegs;

    List<IndicatorsEvaluationIndicatorReg> storedIndicatorsRegs;

    boolean youVeFinished;

    boolean isComplete;

    ScrollView completeView;

    ScrollView simpleView;

    TextView indicatorCount;

    int[][] numIndicatorsPerPriority;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);

        youVeFinished=false;
        base=findViewById(R.id.base);

        textView = findViewById(R.id.textProgress);

        background = findViewById(R.id.background);
        background.setVisibility(View.GONE);

        indicatorCount=findViewById(R.id.count_ind);


        current_evaluation= IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation();

        isComplete=current_evaluation.getEvaluationType().equals("COMPLETE");

        completeView=findViewById(R.id.complete_evidences);
        simpleView=findViewById(R.id.simple_evidences);

        if(isComplete){
            completeView.setVisibility(View.VISIBLE);
            simpleView.setVisibility(View.GONE);
        }
        else{
            completeView.setVisibility(View.GONE);
            simpleView.setVisibility(View.VISIBLE);
        }

        indicators= IndicatorsUtil.getInstance().getIndicators();
        num_indicators=indicators.size();


        num_evidences=4;

        indicatorRegs=new IndicatorsEvaluationIndicatorReg[num_indicators];
        if(isComplete) {
            evidenceRegs = new IndicatorsEvaluationEvidenceReg[num_indicators][num_evidences];
        }else{
            simpleEvidenceRegs = new IndicatorsEvaluationSimpleEvidenceReg[num_indicators][num_evidences];
        }
        for(int ii=0;ii<num_indicators;ii++){
            indicatorRegs[ii]=null;
            for (int jj = 0; jj < num_evidences; jj++) {
                if(isComplete) {
                    evidenceRegs[ii][jj] = null;
                }else{
                    simpleEvidenceRegs[ii][jj] = null;
                }
            }
        }


        if(IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType())!=null){
            if(isComplete) {
                storedEvidencesRegs = IndicatorsEvaluationRegsUtil.getInstance().getEvidenceRegs();
                for (IndicatorsEvaluationEvidenceReg evidenceReg : storedEvidencesRegs) {
                    evidenceRegs[evidenceReg.getIdIndicator() - 1][evidenceReg.getIdEvidence() - 1] = evidenceReg;
                }
            }else{
                storedSimpleEvidencesRegs = IndicatorsEvaluationRegsUtil.getInstance().getEvidenceSimpleRegs();
                for(IndicatorsEvaluationSimpleEvidenceReg evidenceReg:storedSimpleEvidencesRegs){
                    simpleEvidenceRegs[evidenceReg.getIdIndicator() - 1][evidenceReg.getIdEvidence() - 1] = evidenceReg;
                }
            }
            storedIndicatorsRegs=IndicatorsEvaluationRegsUtil.getInstance().getIndicatorRegs();
            for(IndicatorsEvaluationIndicatorReg indicatorReg:storedIndicatorsRegs){
                indicatorRegs[indicatorReg.getIdIndicator()-1]=indicatorReg;
            }
            current_indicator=storedIndicatorsRegs.size()-1;//Ir al indicador donde lo dejamos
        }


        if(isComplete){
            evidence1 = findViewById(R.id.evidence1_complete);
            evidence2 = findViewById(R.id.evidence2_complete);
            evidence3 = findViewById(R.id.evidence3_complete);
            evidence4 = findViewById(R.id.evidence4_complete);
        }
        else{
            ev1Txt = findViewById(R.id.ev1txt);
            ev2Txt = findViewById(R.id.ev2txt);
            ev3Txt = findViewById(R.id.ev3txt);
            ev4Txt = findViewById(R.id.ev4txt);
        }



        ImageButton previous_indicator = findViewById(R.id.previous_indicatorButton);
        ImageButton next_indicator = findViewById(R.id.next_indicatorButton);
        ImageButton saveChangesButton=findViewById(R.id.save_changesButton);
        Button improvement_opportunities=findViewById(R.id.improvement_opportunities_button);
        ImageButton helpButton=findViewById(R.id.helpIndButton);


        changeIndicator();



        if(isComplete){
            evidence1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        evidenceRegs[current_indicator][0].setIsMarked(1);
                    } else {
                        evidenceRegs[current_indicator][0].setIsMarked(0);
                    }
                }
            });


            evidence2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        evidenceRegs[current_indicator][1].setIsMarked(1);
                    } else {
                        evidenceRegs[current_indicator][1].setIsMarked(0);
                    }
                }
            });



            evidence3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        evidenceRegs[current_indicator][2].setIsMarked(1);
                    } else {
                        evidenceRegs[current_indicator][2].setIsMarked(0);
                    }
                }
            });


            evidence4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        evidenceRegs[current_indicator][3].setIsMarked(1);
                    } else {
                        evidenceRegs[current_indicator][3].setIsMarked(0);
                    }
                }
            });
        }

        else{
            ev1Txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    simpleEvidenceRegs[current_indicator][0].setDescriptionSpanish(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ev2Txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    simpleEvidenceRegs[current_indicator][1].setDescriptionSpanish(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ev3Txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    simpleEvidenceRegs[current_indicator][2].setDescriptionSpanish(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ev4Txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    simpleEvidenceRegs[current_indicator][3].setDescriptionSpanish(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        previous_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numEvidencesMarked=0;
                if(isComplete) {
                    for (int i = 0; i < num_evidences; i++) {
                        if (evidenceRegs[current_indicator][i].getIsMarked() == 1) {
                            numEvidencesMarked++;
                        }
                    }
                }else{
                    if(!simpleEvidenceRegs[current_indicator][0].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][1].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][2].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][3].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }
                }
                indicatorRegs[current_indicator].setNumEvidencesMarked(numEvidencesMarked);
                if (current_indicator == 0) {
                    doYouWantToSaveChanges();
                } else {
                    base.setVisibility(View.GONE);
                    background.setVisibility(View.VISIBLE);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            current_indicator--;
                            changeIndicator();
                            background.setVisibility(View.GONE);
                            base.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }
            }
        });
        next_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numEvidencesMarked=0;
                if(isComplete) {
                    for (int i = 0; i < num_evidences; i++) {
                        if (evidenceRegs[current_indicator][i].getIsMarked() == 1) {
                            numEvidencesMarked++;
                        }
                    }
                }else{
                    if(!simpleEvidenceRegs[current_indicator][0].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][1].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][2].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][3].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }
                }
                indicatorRegs[current_indicator].setNumEvidencesMarked(numEvidencesMarked);
                if (current_indicator < num_indicators - 1) {
                    base.setVisibility(View.GONE);
                    background.setVisibility(View.VISIBLE);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            current_indicator++;
                            changeIndicator();
                            background.setVisibility(View.GONE);
                            base.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }

                else {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewFinish=inflater.inflate(R.layout.conclusions_ind_eval,null);
                    EditText indObs=viewFinish.findViewById(R.id.indObs);
                    indObs.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String text=s.toString();
                            if(Locale.getDefault().getLanguage().equals("es")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                current_evaluation.setConclusionsSpanish(text);
                            }else{
                                current_evaluation.setConclusionsSpanish(text);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                            .setTitle(getString(R.string.conclusions))
                            .setView(viewFinish)
                            .setPositiveButton(getString(R.string.finish), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    youVeFinished=true;
                                    dialog.dismiss();
                                    base.setVisibility(View.GONE);
                                    background.setVisibility(View.VISIBLE);
                                    textView.setText(getString(R.string.calculating_results));
                                    view.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            addToDatabase();

                                        }
                                    }, 100);
                                }
                            })
                            .create().show();
                    //Primero mostrar observaciones y conclusiones y luego calcular resultados


                }
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int numEvidencesMarked=0;
                if(isComplete) {
                    for (int i = 0; i < num_evidences; i++) {
                        if (evidenceRegs[current_indicator][i].getIsMarked() == 1) {
                            numEvidencesMarked++;
                        }
                    }
                }else{
                    if(!simpleEvidenceRegs[current_indicator][0].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][1].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][2].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }if(!simpleEvidenceRegs[current_indicator][3].getDescriptionSpanish().isEmpty()){
                        numEvidencesMarked++;
                    }
                }
                indicatorRegs[current_indicator].setNumEvidencesMarked(numEvidencesMarked);
                doYouWantToSaveChanges();
            }
        });

        improvement_opportunities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.four_ev_obs, null);

                EditText editText = view.findViewById(R.id.indObs);

                if(Locale.getDefault().getLanguage().equals("es")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsSpanish());
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsFrench());
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsBasque());
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsCatalan());
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsDutch());
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsGalician());
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsGerman());
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsItalian());
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    editText.setText(indicatorRegs[current_indicator].getObservationsPortuguese());
                }else{
                    editText.setText(indicatorRegs[current_indicator].getObservationsEnglish());
                }

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            indicatorRegs[current_indicator].setObservationsSpanish(text);
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            indicatorRegs[current_indicator].setObservationsFrench(text);
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            indicatorRegs[current_indicator].setObservationsBasque(text);
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            indicatorRegs[current_indicator].setObservationsCatalan(text);
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            indicatorRegs[current_indicator].setObservationsDutch(text);
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            indicatorRegs[current_indicator].setObservationsGalician(text);
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            indicatorRegs[current_indicator].setObservationsGerman(text);
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            indicatorRegs[current_indicator].setObservationsItalian(text);
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            indicatorRegs[current_indicator].setObservationsPortuguese(text);
                        }else{
                            indicatorRegs[current_indicator].setObservationsEnglish(text);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                editText = view.findViewById(R.id.evObs1);

                if(Locale.getDefault().getLanguage().equals("es")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsSpanish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsSpanish());
                    }
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsFrench());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsFrench());
                    }
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsBasque());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsBasque());
                    }
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsCatalan());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsCatalan());
                    }
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsDutch());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsDutch());
                    }
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsGalician());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsGalician());
                    }
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsGerman());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsGerman());
                    }
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsItalian());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsItalian());
                    }
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsPortuguese());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsPortuguese());
                    }
                }else{
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][0].getObservationsEnglish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][0].getObservationsEnglish());
                    }
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsSpanish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsSpanish(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsFrench(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsFrench(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsBasque(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsBasque(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsCatalan(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsCatalan(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsDutch(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsDutch(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsGalician(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsGalician(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsGerman(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsGerman(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsItalian(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsItalian(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsPortuguese(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsPortuguese(text);
                            }
                        }else{
                            if(isComplete){
                                evidenceRegs[current_indicator][0].setObservationsEnglish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][0].setObservationsEnglish(text);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                editText = view.findViewById(R.id.evObs2);

                if(Locale.getDefault().getLanguage().equals("es")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsSpanish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsSpanish());
                    }
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsFrench());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsFrench());
                    }
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsBasque());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsBasque());
                    }
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsCatalan());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsCatalan());
                    }
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsDutch());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsDutch());
                    }
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsGalician());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsGalician());
                    }
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsGerman());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsGerman());
                    }
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsItalian());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsItalian());
                    }
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsPortuguese());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsPortuguese());
                    }
                }else{
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][1].getObservationsEnglish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][1].getObservationsEnglish());
                    }
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsSpanish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsSpanish(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsFrench(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsFrench(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsBasque(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsBasque(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsCatalan(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsCatalan(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsDutch(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsDutch(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsGalician(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsGalician(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsGerman(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsGerman(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsItalian(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsItalian(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsPortuguese(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsPortuguese(text);
                            }
                        }else{
                            if(isComplete){
                                evidenceRegs[current_indicator][1].setObservationsEnglish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][1].setObservationsEnglish(text);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                editText = view.findViewById(R.id.evObs3);

                if(Locale.getDefault().getLanguage().equals("es")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsSpanish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsSpanish());
                    }
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsFrench());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsFrench());
                    }
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsBasque());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsBasque());
                    }
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsCatalan());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsCatalan());
                    }
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsDutch());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsDutch());
                    }
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsGalician());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsGalician());
                    }
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsGerman());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsGerman());
                    }
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsItalian());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsItalian());
                    }
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsPortuguese());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsPortuguese());
                    }
                }else{
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][2].getObservationsEnglish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][2].getObservationsEnglish());
                    }
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsSpanish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsSpanish(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsFrench(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsFrench(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsBasque(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsBasque(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsCatalan(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsCatalan(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsDutch(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsDutch(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsGalician(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsGalician(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsGerman(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsGerman(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsItalian(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsItalian(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsPortuguese(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsPortuguese(text);
                            }
                        }else{
                            if(isComplete){
                                evidenceRegs[current_indicator][2].setObservationsEnglish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][2].setObservationsEnglish(text);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                editText = view.findViewById(R.id.evObs4);

                if(Locale.getDefault().getLanguage().equals("es")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsSpanish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsSpanish());
                    }
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsFrench());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsFrench());
                    }
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsBasque());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsBasque());
                    }
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsCatalan());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsCatalan());
                    }
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsDutch());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsDutch());
                    }
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsGalician());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsGalician());
                    }
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsGerman());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsGerman());
                    }
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsItalian());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsItalian());
                    }
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsPortuguese());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsPortuguese());
                    }
                }else{
                    if(isComplete){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsEnglish());
                    }
                    else{
                        editText.setText(simpleEvidenceRegs[current_indicator][3].getObservationsEnglish());
                    }
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsSpanish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsSpanish(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsFrench(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsFrench(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsBasque(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsBasque(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsCatalan(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsCatalan(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsDutch(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsDutch(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsGalician(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsGalician(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsGerman(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsGerman(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsItalian(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsItalian(text);
                            }
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsPortuguese(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsPortuguese(text);
                            }
                        }else{
                            if(isComplete){
                                evidenceRegs[current_indicator][3].setObservationsEnglish(text);                            }
                            else {
                                simpleEvidenceRegs[current_indicator][3].setObservationsEnglish(text);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                        .setTitle(getString(R.string.improvement_opportunities))
                        .setView(view)
                        .create().show();
            }
        });


        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.ind_eval_help, null);
                TextView textView1=view.findViewById(R.id.textView1);
                TextView textView2=view.findViewById(R.id.textView2);
                String text="";
                String text2="";
                if(num_evidences==3){
                    if(Locale.getDefault().getLanguage().equals("es")){
                        text="En la parte superior de su pantalla aparecerá el título del indicador junto con el ámbito y subdivisiones a las que pertenece. Al tratarse de una <b>evaluación simple</b>, debajo dispone de <b>tres</b> evidencias, las cuales pueden tener un valor de <i>Evidencia Cumplida</i> o de <i>Evidencia No Cumplida</i>. Para cambiar el valor de la evidencia, simplemente debe <b><i>tocar sobre el recuadro de cualquiera de las evidencias</i></b>, mostrándose en pantalla uno de los siguientes dos valores:";
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        text="En haut de votre écran, vous verrez le titre de l'indicateur ainsi que le champ d'application et les subdivisions auxquelles il appartient. Comme il s'agit d'une <b>évaluation simple</b>, vous disposez de <b>trois</b> preuves ci-dessous, qui peuvent avoir une valeur de <i>Preuve remplie</i> ou de <i>Preuve non remplie</i>. Pour changer la valeur de la preuve, il vous <b><i>suffit de toucher la case de l'une des preuves</i></b>, affichant l'une des deux valeurs suivantes à l'écran :";
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        text="Adierazlearen izenburua zure pantailaren goialdean agertuko da dagokion esparru eta azpizatiketekin batera. <b>Ebaluazio sinplea</b> denez, behean <b>hiru</b> froga daude, <i>Osagarria beteta</i> edo <i>Osagarria ez beteta</i> balio izan dezaketenak. Frogaren balioa aldatzeko, <b><i>frogaren edozein laukia ukitu besterik ez duzu</i></b> egin behar, pantailan bi balio hauetako bat erakutsiz:";
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        text="A la part superior de la pantalla, apareixerà el títol de l'indicador juntament amb l'àmbit i les subdivisions a les quals pertany. Com que es tracta d'una <b>avaluació simple</b>, a continuació tindrà <b>tres</b> evidències, les quals poden tenir un valor d'<i>Evidència Complerta</i> o d'<i>Evidència No Complerta</i>. Per canviar el valor de l'evidència, simplement cal que <b><i>toqui sobre el quadre de qualsevol de les evidències</i></b>, mostrant-se a la pantalla un dels dos valors següents:";
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        text="Aan de bovenkant van uw scherm ziet u de titel van de indicator samen met het bereik en de onderverdelingen waartoe het behoort. Omdat dit een <b>eenvoudige evaluatie</b> is, heeft u hieronder <b>drie</b> stukken bewijs, die een waarde kunnen hebben van <i>Voltooid bewijs</i> of <i>Niet-voltooid bewijs</i>. Om de waarde van het bewijs te wijzigen, hoeft u alleen maar <b><i>op het vakje van een van de bewijzen te tikken</i></b>, waarbij een van de volgende twee waarden op het scherm wordt weergegeven:";
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        text="Na parte superior da súa pantalla, verá o título do indicador xunto co alcance e as subdivisións ás que pertence. Ao tratarse dunha <b>avaliación sinxela</b>, a continuación terá <b>tres</b> probas, que poden ter un valor de <i>Evidencia cumprida</i> ou de <i>Evidencia non cumprida</i>. Para cambiar o valor da evidencia, simplemente <b><i>toque na caixa de calquera das evidencias</i></b>, amosando un dos seguintes dous valores na pantalla:";
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        text="Oben auf Ihrem Bildschirm sehen Sie den Titel des Indikators zusammen mit dem Umfang und den Unterteilungen, zu denen er gehört. Da es sich um eine <b>einfache Bewertung</b> handelt, haben Sie unten <b>drei</b> Beweise, die einen Wert von <i>Erfüllte Evidenz</i> oder <i>Nicht erfüllte Evidenz</i> haben können. Um den Wert des Beweises zu ändern, <b><i>tippen Sie einfach auf das Feld eines der Beweise</i></b>, wobei einer der folgenden beiden Werte auf dem Bildschirm angezeigt wird:";
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        text="In alto sullo schermo vedrai il titolo dell'indicatore insieme all'ambito e alle suddivisioni a cui appartiene. Poiché si tratta di una <b>valutazione semplice</b>, di seguito avrai <b>tre</b> prove, che possono avere un valore di <i>Evidenza soddisfatta</i> o di <i>Evidenza non soddisfatta</i>. Per cambiare il valore della prova, basta <b><i>toccare sulla casella di una qualsiasi delle prove</i></b>, mostrando sullo schermo uno dei seguenti due valori:";
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        text="No topo da tela, você verá o título do indicador junto com o escopo e as subdivisões às quais ele pertence. Como se trata de uma <b>avaliação simples</b>, abaixo você tem <b>três</b> evidências, que podem ter um valor de <i>Evidência cumprida</i> ou de <i>Evidência não cumprida</i>. Para alterar o valor da evidência, basta <b><i>tocar na caixa de qualquer uma das evidências</i></b>, exibindo na tela um dos dois valores a seguir:";
                    }else{
                        text="At the top of your screen, you will see the title of the indicator along with the scope and subdivisions to which it belongs. As this is a <b>simple evaluation</b>, below you have <b>three</b> pieces of evidence, which can have a value of <i>Fulfilled Evidence</i> or <i>Unfulfilled Evidence</i>. To change the value of the evidence, simply <b><i>tap on the box of any of the evidences</i></b>, displaying one of the following two values on the screen:";
                    }
                }else if(num_evidences==4){
                    if(Locale.getDefault().getLanguage().equals("es")){
                        text="En la parte superior de su pantalla aparecerá el título del indicador junto con el ámbito y subdivisiones a las que pertenece. Al tratarse de una <b>evaluación completa</b>, debajo dispone de <b>cuatro</b> evidencias, las cuales pueden tener un valor de <i>Evidencia Cumplida</i> o de <i>Evidencia No Cumplida</i>. Para cambiar el valor de la evidencia, simplemente debe <b><i>tocar sobre el recuadro de cualquiera de las evidencias</i></b>, mostrándose en pantalla uno de los siguientes dos valores:";
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        text="En haut de votre écran, vous verrez le titre de l'indicateur ainsi que le champ d'application et les subdivisions auxquelles il appartient. Comme il s'agit d'une <b>évaluation complète</b>, vous disposez de <b>quatre</b> preuves ci-dessous, qui peuvent avoir une valeur de <i>Preuve remplie</i> ou de <i>Preuve non remplie</i>. Pour changer la valeur de la preuve, il vous <b><i>suffit de toucher la case de l'une des preuves</i></b>, affichant l'une des deux valeurs suivantes à l'écran :";
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        text="Adierazlearen izenburua zure pantailaren goialdean agertuko da dagokion esparru eta azpizatiketekin batera. <b>Ebaluazio osoa</b> denez, behean <b>lau</b> froga daude, <i>Osagarria beteta</i> edo <i>Osagarria ez beteta</i> balio izan dezaketenak. Frogaren balioa aldatzeko, <b><i>frogaren edozein laukia ukitu besterik ez duzu</i></b> egin behar, pantailan bi balio hauetako bat erakutsiz:";
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        text="A la part superior de la pantalla, apareixerà el títol de l'indicador juntament amb l'àmbit i les subdivisions a les quals pertany. Com que es tracta d'una <b>avaluació completa</b>, a continuació tindrà <b>quatre</b> evidències, les quals poden tenir un valor d'<i>Evidència Complerta</i> o d'<i>Evidència No Complerta</i>. Per canviar el valor de l'evidència, simplement cal que <b><i>toqui sobre el quadre de qualsevol de les evidències</i></b>, mostrant-se a la pantalla un dels dos valors següents:";
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        text="Aan de bovenkant van uw scherm ziet u de titel van de indicator samen met het bereik en de onderverdelingen waartoe het behoort. Omdat dit een <b>volledige evaluatie</b> is, heeft u hieronder <b>vier</b> stukken bewijs, die een waarde kunnen hebben van <i>Voltooid bewijs</i> of <i>Niet-voltooid bewijs</i>. Om de waarde van het bewijs te wijzigen, hoeft u alleen maar <b><i>op het vakje van een van de bewijzen te tikken</i></b>, waarbij een van de volgende twee waarden op het scherm wordt weergegeven:";
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        text="Na parte superior da súa pantalla, verá o título do indicador xunto co alcance e as subdivisións ás que pertence. Ao tratarse dunha <b>avaliación completa</b>, a continuación terá <b>catro</b> probas, que poden ter un valor de <i>Evidencia cumprida</i> ou de <i>Evidencia non cumprida</i>. Para cambiar o valor da evidencia, simplemente <b><i>toque na caixa de calquera das evidencias</i></b>, amosando un dos seguintes dous valores na pantalla:";
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        text="Oben auf Ihrem Bildschirm sehen Sie den Titel des Indikators zusammen mit dem Umfang und den Unterteilungen, zu denen er gehört. Da es sich um eine <b>vollständige Bewertung</b> handelt, haben Sie unten <b>drei</b> Beweise, die einen Wert von <i>Erfüllte Evidenz</i> oder <i>Nicht erfüllte Evidenz</i> haben können. Um den Wert des Beweises zu ändern, <b><i>tippen Sie einfach auf das Feld eines der Beweise</i></b>, wobei einer der folgenden beiden Werte auf dem Bildschirm angezeigt wird:";
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        text="In alto sullo schermo vedrai il titolo dell'indicatore insieme all'ambito e alle suddivisioni a cui appartiene. Poiché si tratta di una <b>valutazione completa</b>, di seguito avrai <b>quattro</b> prove, che possono avere un valore di <i>Evidenza soddisfatta</i> o di <i>Evidenza non soddisfatta</i>. Per cambiare il valore della prova, basta <b><i>toccare sulla casella di una qualsiasi delle prove</i></b>, mostrando sullo schermo uno dei seguenti due valori:";
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        text="No topo da tela, você verá o título do indicador junto com o escopo e as subdivisões às quais ele pertence. Como se trata de uma <b>avaliação simples</b>, abaixo você tem <b>quatro</b> evidências, que podem ter um valor de <i>Evidência cumprida</i> ou de <i>Evidência não cumprida</i>. Para alterar o valor da evidência, basta <b><i>tocar na caixa de qualquer uma das evidências</i></b>, exibindo na tela um dos dois valores a seguir:";
                    }else{
                        text="At the top of your screen, you will see the title of the indicator along with the scope and subdivisions to which it belongs. As this is a <b>complete evaluation</b>, below you have <b>four</b> pieces of evidence, which can have a value of <i>Fulfilled Evidence</i> or <i>Unfulfilled Evidence</i>. To change the value of the evidence, simply <b><i>tap on the box of any of the evidences</i></b>, displaying one of the following two values on the screen:";
                    }
                }

                if(Locale.getDefault().getLanguage().equals("es")){
                    text2="En la parte inferior de la pantalla dispone de las siguientes opciones:<ul><li><b>Oportunidades de mejora:</b> En este apartado puede rellenar las oportunidades de mejora del indicador y de sus correspondientes evidencias.</li><li><b>Ayuda:</b> Muestra este mismo apartado.</li><li><b>Indicador Anterior:</b> Si toca este botón se regresará al indicador anterior, salvo si se trata del indicador anterior, que al presionar preguntará si se desean guardar los cambios.</li><li><b>Guardar Cambios:</b> Si se desea continuar en otro momento con la evaluación de indicadores, presione sobre este botón.</li><li><b>Indicador Siguiente:</b> Si toca este botón se avanzará sobre el siguiente indicador, salvo si se trata del último indicador, en cuyo caso se mostrará el apartado de conclusiones de la evaluación de indicadores.";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    text2="En bas de l'écran, vous avez les options suivantes :<ul><li><b>Opportunités d'amélioration :</b> Dans cette section, vous pouvez remplir les opportunités d'amélioration sur l'indicateur et ses preuves correspondantes.</li><li><b>Aide :</b> Affiche cette même section.</li><li><b>Indicateur précédent :</b> Si vous appuyez sur ce bouton, vous retournerez à l'indicateur précédent, sauf s'il s'agit du premier indicateur, auquel cas il vous demandera si vous souhaitez enregistrer les modifications.</li><li><b>Enregistrer les modifications :</b> Si vous souhaitez continuer l'évaluation des indicateurs ultérieurement, appuyez sur ce bouton.</li><li><b>Indicateur suivant :</b> Si vous appuyez sur ce bouton, vous avancerez vers l'indicateur suivant, sauf s'il s'agit du dernier indicateur, auquel cas la section des conclusions de l'évaluation de l'indicateur sera affichée.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    text2="Pantailaren beheko aldean, aukera hauek dituzu:<ul><li><b>Hobekuntza aukerak:</b> Atal honetan adierazlea hobetzeko aukerak eta horri dagozkion frogak bete ditzakezu.</li><li><b>Laguntza:</b> Atal berean agertuko da.</li><li><b>Aurreko Adierazlea:</b> Sakatzen baduzu, aurreko adierazlera itzuliko zara, izan ere, lehenengo adierazlearen kasuan, aldaketak gordetzeko galdeginga egingo diozu.</li><li><b>Aldaketak Gorde:</b> Adierazleak gauzatzeko beste une batean jarraitu nahi baduzu, sakatu botoi hau.</li><li><b>Hurrengo Adierazlea:</b> Botoi hau sakatzen baduzu, hurrengo adierazlera egingo duzu, azken adierazlearen kasuan, adierazlearen ebaluazioaren amaierako atal agertuko da.</li>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    text2="A la part inferior de la pantalla, teniu les següents opcions:<ul><li><b>Oportunitats de millora:</b> En aquesta secció, podeu omplir les oportunitats de millora sobre l'indicador i les seves proves corresponents.</li><li><b>Ajuda:</b> Mostra aquesta mateixa secció.</li><li><b>Indicador anterior:</b> Si toqueu aquest botó, tornareu a l'indicador anterior, llevat que sigui el primer indicador, en què cas preguntarà si voleu desar els canvis.</li><li><b>Desar canvis:</b> Si voleu continuar l'avaluació d'indicadors en un altre moment, premeu aquest botó.</li><li><b>Indicador següent:</b> Si toqueu aquest botó, avançareu al següent indicador, llevat que sigui l'últim indicador, en què cas es mostrarà la secció de conclusions de l'avaluació de l'indicador.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    text2="Onderaan het scherm heb je de volgende opties:<ul><li><b>Verbeterkansen:</b> In deze sectie kunt u de mogelijkheden voor verbetering van de indicator en het bijbehorende bewijsmateriaal invullen.</li><li><b>Help:</b> Toont ditzelfde gedeelte.</li><li><b>Vorige indicator:</b> Als je op deze knop drukt, ga je terug naar de vorige indicator, tenzij het de eerste indicator is, waarbij het zal vragen of je de wijzigingen wilt opslaan.</li><li><b>Wijzigingen opslaan:</b> Als je de evaluatie van indicatoren op een later tijdstip wilt voortzetten, druk dan op deze knop.</li><li><b>Volgende indicator:</b> Als je op deze knop drukt, ga je naar de volgende indicator, tenzij het de laatste indicator is, in welk geval het gedeelte met conclusies van de indicator evaluatie wordt weergegeven.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    text2="No extremo inferior da pantalla tes as seguintes opcións:<ul><li><b>Oportunidades de mellora:</b> Nesta sección, podes encher as oportunidades de mellora sobre o indicador e as súas correspondentes evidencias.</li><li><b>Axuda:</b> Amosa esta mesma sección.</li><li><b>Indicador anterior:</b> Se premes este botón, volverás ao indicador anterior, salvo que sexa o primeiro indicador, no que caso preguntará se desexas gardar os cambios.</li><li><b>Gardar cambios:</b> Se queres continuar a avaliación de indicadores noutro momento, preme este botón.</li><li><b>Indicador seguinte:</b> Se premes este botón, avanzarás ao seguinte indicador, salvo que sexa o último indicador, no que caso mostrarase a sección de conclusións da avaliación do indicador.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    text2="Unten auf dem Bildschirm haben Sie folgende Optionen:<ul><li><b>Verbesserungsmöglichkeiten:</b> In diesem Abschnitt können Sie die Möglichkeiten zur Verbesserung des Indikators und die entsprechenden Belege eintragen.</li><li><b>Hilfe:</b> Zeigt diesen Abschnitt.</li><li><b>Vorheriger Indikator:</b> Wenn Sie auf diese Schaltfläche tippen, kehren Sie zum vorherigen Indikator zurück, es sei denn, es handelt sich um den ersten Indikator, in welchem Fall gefragt wird, ob Sie die Änderungen speichern möchten.</li><li><b>Änderungen speichern:</b> Wenn Sie die Bewertung der Indikatoren zu einem späteren Zeitpunkt fortsetzen möchten, drücken Sie diese Schaltfläche.</li><li><b>Nächster Indikator:</b> Wenn Sie auf diese Schaltfläche tippen, gelangen Sie zum nächsten Indikator, es sei denn, es handelt sich um den letzten Indikator, in welchem Fall der Abschnitt mit den Schlussfolgerungen der Indikatorenbewertung angezeigt wird.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    text2="In basso sullo schermo hai le seguenti opzioni:<ul><li><b>Opportunità di miglioramento:</b> In questa sezione puoi compilare opportunità di miglioramento sull'indicatore e sulle relative prove.</li><li><b>Aiuto:</b> Visualizza questa stessa sezione.</li><li><b>Indicatore precedente:</b> Se toccate questo pulsante, tornerete all'indicatore precedente, a meno che non sia il primo indicatore, nel qual caso chiederà se volete salvare le modifiche.</li><li><b>Salva modifiche:</b> Se desideri continuare l'valutazione degli indicatori in un momento successivo, premi questo pulsante.</li><li><b>Indicatore successivo:</b> Se toccate questo pulsante, passerete all'indicatore successivo, a meno che non sia l'ultimo indicatore, nel qual caso verrà visualizzata la sezione delle conclusioni della valutazione dell'indicatore.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    text2="No final da tela, você tem as seguintes opções:<ul><li><b>Oportunidades de melhoria:</b> Nesta seção, você pode preencher oportunidades de melhoria sobre o indicador e suas evidências correspondentes.</li><li><b>Ajuda:</b> Exibe esta mesma seção.</li><li><b>Indicador anterior:</b> Se você tocar neste botão, voltará ao indicador anterior, a menos que seja o primeiro indicador, caso em que perguntará se deseja salvar as alterações.</li><li><b>Salvar alterações:</b> Se desejar continuar a avaliação dos indicadores em outro momento, pressione este botão.</li><li><b>Próximo indicador:</b> Se você tocar neste botão, avançará para o próximo indicador, a menos que seja o último indicador, caso em que a seção de conclusões da avaliação do indicador será exibida.</li></ul>";
                }else{
                    text2="At the bottom of the screen, you have the following options:<ul><li><b>Opportunities for improvement:</b> In this section, you can fill in opportunities for improvement about the indicator and its corresponding evidence.</li><li><b>Help:</b> Displays this same section.</li><li><b>Previous Indicator:</b> If you touch this button, you will return to the previous indicator, unless it is the first indicator, in which case it will ask if you want to save the changes.</li><li><b>Save Changes:</b> If you want to continue the evaluation of indicators at another time, press this button.</li><li><b>Next Indicator:</b> If you touch this button, you will advance to the next indicator, unless it is the last indicator, in which case the conclusions section of the indicator evaluation will be displayed.</li></ul>";
                }

                textView1.setText(Html.fromHtml(text,0));
                textView2.setText(Html.fromHtml(text2,0));
                new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                        .setTitle(getString(R.string.help))
                        .setView(view)
                        .create().show();
            }
        });

    }






    @SuppressLint("SetTextI18n")
    public void changeIndicator() {
        current_ambit = indicators.get(current_indicator).getIdAmbit();
        current_subAmbit = indicators.get(current_indicator).getIdSubAmbit();
        current_subSubAmbit = indicators.get(current_indicator).getIdSubSubAmbit();
        TextView indicatorCaption=null;
        if(isComplete){
            indicatorCaption=findViewById(R.id.indicator_caption_complete);
        }
        else{
            indicatorCaption=findViewById(R.id.indicator_caption_simple);
        }

        Indicator i = indicators.get(current_indicator);
        if(isComplete) {
            evidences = IndicatorsUtil.getInstance().getEvidencesByIndicator(current_subSubAmbit, current_subAmbit, current_ambit, current_indicator + 1, i.getIndicatorType(), i.getIndicatorVersion(), i.getEvaluationType());
            if(evidenceRegs[current_indicator][0]==null) {
                for (Evidence e : evidences) {
                    evidenceRegs[current_indicator][e.getIdEvidence()-1]=new IndicatorsEvaluationEvidenceReg(current_evaluation.getEvaluationDate(),
                            current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIdEvaluatorTeam(),
                            current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIllness(),
                            current_evaluation.getIdCenter(), e.getIdEvidence(), current_indicator+1, e.getIndicatorType(),
                            current_subSubAmbit, current_subAmbit, current_ambit, e.getIndicatorVersion(),0,current_evaluation.getEvaluationType(),"","","","","","","","","","");
                }
            }
            if(evidenceRegs[current_indicator][0].getIsMarked()==1){
                evidence1.setChecked(true);
            }else{
                evidence1.setChecked(false);
            }if(evidenceRegs[current_indicator][1].getIsMarked()==1){
                evidence2.setChecked(true);
            }else{
                evidence2.setChecked(false);
            }if(evidenceRegs[current_indicator][2].getIsMarked()==1){
                evidence3.setChecked(true);
            }else{
                evidence3.setChecked(false);
            }
            if (evidenceRegs[current_indicator][3].getIsMarked() == 1) {
                evidence4.setChecked(true);
            } else {
                evidence4.setChecked(false);
            }
        }else{
            if(simpleEvidenceRegs[current_indicator][0]==null){
                for(int ii=0;ii<num_evidences;ii++){
                    simpleEvidenceRegs[current_indicator][ii]=new IndicatorsEvaluationSimpleEvidenceReg(current_evaluation.getEvaluationDate(), current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(), current_evaluation.getIdEvaluatorTeam(), current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIllness(), current_evaluation.getIdCenter(), current_indicator+1, ii+1,
                    "", "","","","","","","","","",
                            current_subSubAmbit, current_subAmbit, current_ambit, indicators.get(current_indicator).getIndicatorVersion(), current_evaluation.getEvaluationType(), "", "","","","","","","","","");
                }
            }
            ev1Txt.setText(simpleEvidenceRegs[current_indicator][0].getDescriptionSpanish());
            ev2Txt.setText(simpleEvidenceRegs[current_indicator][1].getDescriptionSpanish());
            ev3Txt.setText(simpleEvidenceRegs[current_indicator][2].getDescriptionSpanish());
            ev4Txt.setText(simpleEvidenceRegs[current_indicator][3].getDescriptionSpanish());
        }
        if(indicatorRegs[current_indicator]==null){
            indicatorRegs[current_indicator]=new IndicatorsEvaluationIndicatorReg(current_evaluation.getEvaluationDate(),
                    current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIdEvaluatorTeam(),
                    current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIllness(),
                    current_evaluation.getIdCenter(),current_indicator+1,
                    current_subSubAmbit, current_subAmbit, current_ambit, i.getIndicatorVersion(),current_evaluation.getEvaluationType(),"","","","","","","","","","",0,"",-1);
        }




        String indCaption="";
        String ev1Caption="";
        String ev2Caption="";
        String ev3Caption="";
        String ev4Caption="";
        if(isComplete){
            ev1Caption="<b>"+getString(R.string.evidence1)+": </b>";
            ev2Caption="<b>"+getString(R.string.evidence2)+": </b>";
            ev3Caption="<b>"+getString(R.string.evidence3)+": </b>";
            ev4Caption="<b>"+getString(R.string.evidence4)+": </b>";
        }
        if (Locale.getDefault().getLanguage().equals("es")) {//Español

            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMER ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGUNDO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCER ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>CUARTO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEXTO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": </b>" + i.getDescriptionSpanish()+"<br>";

            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionSpanish();
                ev2Caption+= evidences.get(1).getDescriptionSpanish();
                ev3Caption+= evidences.get(2).getDescriptionSpanish();
                ev4Caption+= evidences.get(3).getDescriptionSpanish();
            }
        } else if (Locale.getDefault().getLanguage().equals("fr")) {//Francés
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PREMIÈRE PORTÉE: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>DEUXIÈME PORTÉE: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TROISIÈME PORTÉE: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUATRIÈME PORTÉE: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>CINQUIÈME PORTÉE: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SIXIÈME PORTÉE: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicateur " + i.getIdIndicator() + ": </b>" + i.getDescriptionFrench()+"<br>";

            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionFrench();
                ev2Caption+= evidences.get(1).getDescriptionFrench();
                ev3Caption+= evidences.get(2).getDescriptionFrench();
                ev4Caption+= evidences.get(3).getDescriptionFrench();
            }
        } else if (Locale.getDefault().getLanguage().equals("eu")) {//Euskera

            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionBasque()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionBasque()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>LEHEN IRISMENA: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>BIGARREN IRISMENA: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>HIRUGARREN IRISMENA: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>LAUGARREN IRISMENA: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>BOSTGARREN IRISMENA: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEIGARREN IRISMENA: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>"+i.getIdIndicator() + ". adierazlea: </b>" + i.getDescriptionBasque()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionBasque();
                ev2Caption+= evidences.get(1).getDescriptionBasque();
                ev3Caption+= evidences.get(2).getDescriptionBasque();
                ev4Caption+= evidences.get(3).getDescriptionBasque();
            }
        } else if (Locale.getDefault().getLanguage().equals("ca")) {//Catalán
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionCatalan()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionCatalan()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMER ÀMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGON ÀMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCER ÀMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUART ÀMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>CINQUÈ ÀMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SISÈ ÀMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": </b>" + i.getDescriptionCatalan()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionCatalan();
                ev2Caption+= evidences.get(1).getDescriptionCatalan();
                ev3Caption+= evidences.get(2).getDescriptionCatalan();
                ev4Caption+= evidences.get(3).getDescriptionCatalan();
            }
        } else if (Locale.getDefault().getLanguage().equals("nl")) {//Neerlandés
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionDutch()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionDutch()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>EERSTE TOEPASSINGSGEBIED: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>TWEEDE TOEPASSINGSGEBIED: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>DERDE TOEPASSINGSGEBIED: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>VIERDE TOEPASSINGSGEBIED: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>VIJFDE TOEPASSINGSGEBIED: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>ZESDE TOEPASSINGSGEBIED: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicator " + i.getIdIndicator() + ": " + i.getDescriptionDutch()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionDutch();
                ev2Caption+= evidences.get(1).getDescriptionDutch();
                ev3Caption+= evidences.get(2).getDescriptionDutch();
                ev4Caption+= evidences.get(3).getDescriptionDutch();
            }
        } else if (Locale.getDefault().getLanguage().equals("gl")) {//Gallego
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGalician()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGalician()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMEIRO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGUNDO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCEIRO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>CUARTO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEXTO ÁMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": " + i.getDescriptionGalician()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionGalician();
                ev2Caption+= evidences.get(1).getDescriptionGalician();
                ev3Caption+= evidences.get(2).getDescriptionGalician();
                ev4Caption+= evidences.get(3).getDescriptionGalician();
            }
        } else if (Locale.getDefault().getLanguage().equals("de")) {//Alemán
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGerman()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGerman()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>ERSTER UMFANG: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>ZWEITER UMFANG: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>DRITTER UMFANG: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>VIERTER UMFANG: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>FÜNFTER UMFANG: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SECHSTER UMFANG: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indikator " + i.getIdIndicator() + ": " + i.getDescriptionGerman()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionGerman();
                ev2Caption+= evidences.get(1).getDescriptionGerman();
                ev3Caption+= evidences.get(2).getDescriptionGerman();
                ev4Caption+= evidences.get(3).getDescriptionGerman();
            }
        } else if (Locale.getDefault().getLanguage().equals("it")) {//Italiano
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionItalian()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionItalian()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMO AMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SECONDO AMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERZO AMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUARTO AMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO AMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SESTO AMBITO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicatore " + i.getIdIndicator() + ": " + i.getDescriptionItalian()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionItalian();
                ev2Caption+= evidences.get(1).getDescriptionItalian();
                ev3Caption+= evidences.get(2).getDescriptionItalian();
                ev4Caption+= evidences.get(3).getDescriptionDutch();
            }
        } else if (Locale.getDefault().getLanguage().equals("pt")) {//Portugués
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionPortuguese()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMEIRO ESCOPO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGUNDO ESCOPO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCEIRO ESCOPO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUARTO ESCOPO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO ESCOPO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEXTO ESCOPO: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": " + i.getDescriptionPortuguese()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionPortuguese();
                ev2Caption+= evidences.get(1).getDescriptionPortuguese();
                ev3Caption+= evidences.get(2).getDescriptionPortuguese();
                ev4Caption+= evidences.get(3).getDescriptionPortuguese();
            }
        } else {//Default

            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + IndicatorsUtil.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionEnglish()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + IndicatorsUtil.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionEnglish()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>FIRST AMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SECOND AMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>THIRD AMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>FOURTH AMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>FIFTH AMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SIXTH AMBIT: " + IndicatorsUtil.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicator " + i.getIdIndicator() + ": " + i.getDescriptionEnglish()+"<br>";
            if(isComplete){
                ev1Caption+= evidences.get(0).getDescriptionEnglish();
                ev2Caption+= evidences.get(1).getDescriptionEnglish();
                ev3Caption+= evidences.get(2).getDescriptionEnglish();
                ev4Caption+= evidences.get(3).getDescriptionEnglish();
            }
        }
        indicatorCaption.setText(Html.fromHtml(indCaption,0));
        if(isComplete) {
            evidence1.setText(Html.fromHtml(ev1Caption,0));
            evidence2.setText(Html.fromHtml(ev2Caption,0));
            evidence3.setText(Html.fromHtml(ev3Caption,0));
            evidence4.setText(Html.fromHtml(ev4Caption, 0));
        }

        indicatorCount.setText(getString(R.string.indicator)+" "+(current_indicator+1)+"/"+num_indicators);
    }


    private void doYouWantToSaveChanges(){
        new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                .setMessage(getString(R.string.do_you_want_save_changes))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        base.setVisibility(View.GONE);
                        textView.setText(getString(R.string.saving_eval_ind_changes));
                        background.setVisibility(View.VISIBLE);
                        addToDatabase();
                    }
                })
                .setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent=new Intent(DoIndicatorsEvaluation.this, MainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                            dialog.dismiss();
                            return true;
                        }
                        return false;
                    }
                })
                .create().show();
    }

    private void generateReport(){
        ExecutorService executor= Executors.newSingleThreadExecutor();
        try (InputStream is = FileManager.getInstance().downloadReportTemplate(current_evaluation.getEvaluationType()).join()) {


            //CompletableFuture<Void> reportFuture=CompletableFuture.runAsync(()->{
            //is.mark(0);
            // Abrir el documento Word
            XWPFDocument document = null;
            try {
                document = new XWPFDocument(is);

                // Obtener todas las tablas en el documento
                List<XWPFTable> tables = document.getTables();

                XWPFTable tableInfo=tables.get(0);

                List<XWPFTableRow> rows=tableInfo.getRows();

                rows.get(1).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getNameOrg());
                rows.get(2).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getDirector().getFirst_name()+" "+ IndicatorsEvaluationUtil.getInstance().getDirector().getLast_name());
                rows.get(3).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getTelephone()+" "+ IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getEmail());

                rows.get(5).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getExternalConsultant());
                rows.get(6).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getResponsible().getFirst_name()+" "+ IndicatorsEvaluationUtil.getInstance().getResponsible().getLast_name());
                rows.get(7).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getProfessional().getFirst_name()+" "+ IndicatorsEvaluationUtil.getInstance().getProfessional().getLast_name());
                rows.get(8).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getRelative_name());
                rows.get(9).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getPatient_name());
                int indAddedRow=10;
                int numAddedRows=0;
                String[] otherMembers= IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getOtherMembers().split(",");
                boolean firstAditionalMemberAdded=false;
                XWPFTableRow baseRow=rows.get(indAddedRow);
                for(String member:otherMembers){
                    XWPFTableRow newRow;
                    if(firstAditionalMemberAdded){
                        newRow=tableInfo.insertNewTableRow(indAddedRow);
                        cloneRowStructure(newRow,baseRow);
                        setCellFormat(newRow.getTableCells().get(0),true,"F2F2F2","");
                        numAddedRows++;
                    }
                    else{
                        newRow=baseRow;
                        firstAditionalMemberAdded=true;
                    }
                    XWPFTableCell newCell=newRow.getTableCells().get(1);
                    newCell.setText(member);
                    indAddedRow++;
                }

                int indInicial=13+numAddedRows;

                String[] aux= IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getEvaluationDates().split(",");
                long[] dates=new long[aux.length+1];
                dates[0]=IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getCreationDate();

                for(int i=0;i<aux.length;i++){
                    dates[i+1]=Long.parseLong(aux[i]);
                }
                int ii=0;
                for(int i=indInicial;i<indInicial+dates.length;i++){
                    XWPFTableRow newRow=tableInfo.insertNewTableRow(i);
                    XWPFTableCell newCell=newRow.createCell();
                    CTTcBorders borders = newCell.getCTTc().addNewTcPr().addNewTcBorders();
                    borders.addNewBottom().setVal(STBorder.SINGLE);
                    borders.addNewTop().setVal(STBorder.SINGLE);
                    borders.addNewLeft().setVal(STBorder.SINGLE);
                    borders.addNewRight().setVal(STBorder.SINGLE);
                    newCell.setText(DateFormatter.timeStampToStrDate(dates[ii]));
                    newCell=newRow.createCell();
                    borders = newCell.getCTTc().addNewTcPr().addNewTcBorders();
                    borders.addNewBottom().setVal(STBorder.SINGLE);
                    borders.addNewTop().setVal(STBorder.SINGLE);
                    borders.addNewLeft().setVal(STBorder.SINGLE);
                    borders.addNewRight().setVal(STBorder.SINGLE);
                    String text="";
                    if(ii==0){
                        text="Fecha de creación";
                    }else{
                        text="Fecha de evaluación "+ii;
                    }
                    newCell.setText(text);
                    ii++;
                }

                XWPFTable tableResults=tables.get(1);

                List<XWPFTableRow> rows2=tableResults.getRows();

                rows2.get(2).getTableCells().get(1).setText(DateFormatter.timeStampToStrDate(System.currentTimeMillis()));
                rows2.get(3).getTableCells().get(1).setText(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getTotalScore()+"");
                String levelMsg="";
                if(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getLevel().equals("EXCELLENT")){
                    levelMsg="Excelente";
                }else if(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getLevel().equals("VERY_GOOD")){
                    levelMsg="Muy bueno";
                }else if(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getLevel().equals("GOOD")){
                    levelMsg="Bueno";
                }else if(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getLevel().equals("IMPROVABLE")){
                    levelMsg="Mejorable";
                }else{
                    levelMsg="Muy mejorable";
                }
                rows2.get(3).getTableCells().get(3).setText(levelMsg);

                rows2.get(5).getTableCells().get(0).setText(current_evaluation.getConclusionsSpanish());

                List<IndicatorsEvaluationIndicatorReg> indicatorRegs=IndicatorsEvaluationRegsUtil.getInstance().getIndicatorRegs();

                Map<String, Integer> highNumEvidencesMarkedPerAmbit=new HashMap<String,Integer>();
                Map<String, Integer> lowNumEvidencesMarkedPerAmbit=new HashMap<String,Integer>();

                for(IndicatorsEvaluationIndicatorReg reg:indicatorRegs){
                    String key=reg.getIdSubSubAmbit()+","+reg.getIdSubAmbit()+","+reg.getIdAmbit();
                    if (highNumEvidencesMarkedPerAmbit.containsKey(key)) {
                        if(reg.getNumEvidencesMarked()>highNumEvidencesMarkedPerAmbit.get(key)) {
                            highNumEvidencesMarkedPerAmbit.replace(key, reg.getNumEvidencesMarked());
                        }
                    }else{
                        highNumEvidencesMarkedPerAmbit.put(key,reg.getNumEvidencesMarked());
                    }
                    if(lowNumEvidencesMarkedPerAmbit.containsKey(key)){
                        if(reg.getNumEvidencesMarked()<lowNumEvidencesMarkedPerAmbit.get(key)){
                            lowNumEvidencesMarkedPerAmbit.replace(key, reg.getNumEvidencesMarked());
                        }
                    }else{
                        lowNumEvidencesMarkedPerAmbit.put(key,reg.getNumEvidencesMarked());
                    }
                }

                List<String> strongPointsList=new ArrayList<>();
                for(String key : highNumEvidencesMarkedPerAmbit.keySet()){
                    if(highNumEvidencesMarkedPerAmbit.get(key)==4 && lowNumEvidencesMarkedPerAmbit.get(key)==4) {
                        String[] auxx = key.split(",");
                        int idSubSubAmbit = Integer.parseInt(auxx[0]);
                        int idSubAmbit = Integer.parseInt(auxx[1]);
                        int idAmbit = Integer.parseInt(auxx[2]);

                        if (idSubSubAmbit != -1) {
                            strongPointsList.add(IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionSpanish());
                        } else {
                            if (idSubAmbit != -1) {
                                strongPointsList.add(IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionSpanish());
                            } else {
                                strongPointsList.add(IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionSpanish());
                            }
                        }
                    }
                }

                String strongPoints="";
                int lastIndex=strongPointsList.size()-1;
                int added=0;
                for(String point:strongPointsList){
                    strongPoints+=point;
                    if(added<lastIndex){
                        strongPoints+=", ";
                    }
                    added++;
                }

                rows2.get(7).getTableCells().get(0).setText(strongPoints);

                int rowNum=9;

                baseRow = rows2.get(rowNum);
                boolean firstIsAdded=false;
                for(IndicatorsEvaluationIndicatorReg indicatorReg:indicatorRegs){
                    if(!indicatorReg.getStatus().equals("REACHED")){
                        XWPFTableRow newRow;
                        if(firstIsAdded){
                            newRow = tableResults.insertNewTableRow(rowNum);
                            cloneRowStructure(newRow, baseRow);
                            setCellFormat(newRow.getCell(0), true, "F2F2F2",baseRow.getCell(0).getText());
                            setCellFormat(newRow.getCell(2), true, "F2F2F2",baseRow.getCell(2).getText());
                            setCellFormat(newRow.getCell(4), true, "F2F2F2",baseRow.getCell(4).getText());
                            setCellFormat(newRow.getCell(6), true, "F2F2F2",baseRow.getCell(6).getText());
                        }
                        else{
                            newRow=rows2.get(rowNum);
                            firstIsAdded=true;
                        }
                        String ambitDescription="";
                        if(indicatorReg.getIdAmbit()==1){
                            ambitDescription=IndicatorsUtil.getInstance().getSubSubAmbit(indicatorReg.getIdSubSubAmbit(),indicatorReg.getIdSubAmbit(),indicatorReg.getIdAmbit()).getDescriptionSpanish();
                        }else if(indicatorReg.getIdAmbit()>1 && indicatorReg.getIdAmbit()<5){
                            ambitDescription=IndicatorsUtil.getInstance().getSubAmbit(indicatorReg.getIdSubAmbit(),indicatorReg.getIdAmbit()).getDescriptionSpanish();
                        }else{
                            ambitDescription=IndicatorsUtil.getInstance().getAmbit(indicatorReg.getIdAmbit()).getDescriptionSpanish();
                        }
                        newRow.getCell(1).setText(ambitDescription);
                        newRow.getCell(3).setText(""+indicatorReg.getIdIndicator());
                        newRow.getCell(5).setText("-");
                        newRow.getCell(7).setText(indicatorReg.getObservationsSpanish());
                        rowNum++;

                        if(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getEvaluationType().equals("COMPLETE")) {
                            List<IndicatorsEvaluationEvidenceReg> evRegs = IndicatorsEvaluationRegsUtil.getInstance().getEvidencesRegsByIndicator(indicatorReg.getIdSubSubAmbit(), indicatorReg.getIdSubAmbit(), indicatorReg.getIdAmbit(), indicatorReg.getIdIndicator(), indicators.get(0).getIndicatorType(), indicatorReg.getIndicatorVersion(), indicatorReg.getEvaluationType());

                            for (IndicatorsEvaluationEvidenceReg evidenceReg : evRegs) {
                                newRow = tableResults.insertNewTableRow(rowNum);
                                cloneRowStructure(newRow, baseRow);
                                setCellFormat(newRow.getCell(0), true, "F2F2F2", baseRow.getCell(0).getText());
                                setCellFormat(newRow.getCell(2), true, "F2F2F2", baseRow.getCell(2).getText());
                                setCellFormat(newRow.getCell(4), true, "F2F2F2", baseRow.getCell(4).getText());
                                setCellFormat(newRow.getCell(6), true, "F2F2F2", baseRow.getCell(6).getText());
                                newRow.getCell(1).setText(ambitDescription);
                                newRow.getCell(3).setText("" + indicatorReg.getIdIndicator());
                                newRow.getCell(5).setText("" + evidenceReg.getIdEvidence());
                                newRow.getCell(7).setText(evidenceReg.getObservationsSpanish());
                                rowNum++;
                            }
                        }else{
                            List<IndicatorsEvaluationSimpleEvidenceReg> evRegs = IndicatorsEvaluationRegsUtil.getInstance().getSimpleEvidencesRegsByIndicator(indicatorReg.getIdSubSubAmbit(), indicatorReg.getIdSubAmbit(), indicatorReg.getIdAmbit(), indicatorReg.getIdIndicator(), indicatorReg.getIndicatorVersion(), indicatorReg.getEvaluationType());

                            for (IndicatorsEvaluationSimpleEvidenceReg evidenceReg : evRegs) {
                                newRow = tableResults.insertNewTableRow(rowNum);
                                cloneRowStructure(newRow, baseRow);
                                setCellFormat(newRow.getCell(0), true, "F2F2F2", baseRow.getCell(0).getText());
                                setCellFormat(newRow.getCell(2), true, "F2F2F2", baseRow.getCell(2).getText());
                                setCellFormat(newRow.getCell(4), true, "F2F2F2", baseRow.getCell(4).getText());
                                setCellFormat(newRow.getCell(6), true, "F2F2F2", baseRow.getCell(6).getText());
                                newRow.getCell(1).setText(ambitDescription);
                                newRow.getCell(3).setText("" + indicatorReg.getIdIndicator());
                                newRow.getCell(5).setText("" + evidenceReg.getIdEvidence());
                                newRow.getCell(7).setText(evidenceReg.getObservationsSpanish());
                                rowNum++;
                            }
                        }
                    }


                }

                XWPFTable pointsTable=tables.get(2);

                List<XWPFTableRow> rows3=pointsTable.getRows();

                numIndicatorsPerPriority=new int[4][3];
                for(int i=0;i<4;i++){
                    for(int j=0;j<3;j++){
                        numIndicatorsPerPriority[i][j]=0;
                    }
                }

                for(int i=0;i< indicatorRegs.size();i++){
                    IndicatorsEvaluationIndicatorReg reg=indicatorRegs.get(i);
                    int iiii=-1;
                    int jjjj=-1;
                    if(reg.getStatus().equals("REACHED")){
                        jjjj=2;
                    }else if(reg.getStatus().equals("IN_PROCESS")){
                        jjjj=1;
                    }else{
                        jjjj=0;
                    }
                    if(indicators.get(i).getIndicatorPriority().equals("FUNDAMENTAL_INTEREST")){
                        iiii=3;
                    }else if(indicators.get(i).getIndicatorPriority().equals("HIGH_INTEREST")){
                        iiii=2;
                    }else if(indicators.get(i).getIndicatorPriority().equals("MEDIUM_INTEREST")){
                        iiii=1;
                    }else{
                        iiii=0;
                    }
                    numIndicatorsPerPriority[iiii][jjjj]+=1;
                }

                rows3.get(1).getTableCells().get(2).setText(""+numIndicatorsPerPriority[3][2]);
                rows3.get(1).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityThreeColourGreen());
                rows3.get(2).getTableCells().get(2).setText(""+numIndicatorsPerPriority[3][1]);
                rows3.get(2).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityThreeColourYellow());
                rows3.get(3).getTableCells().get(2).setText(""+numIndicatorsPerPriority[3][0]);
                rows3.get(4).getTableCells().get(2).setText(""+numIndicatorsPerPriority[2][2]);
                rows3.get(4).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityTwoColourGreen());
                rows3.get(5).getTableCells().get(2).setText(""+numIndicatorsPerPriority[2][1]);
                rows3.get(5).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityTwoColourYellow());
                rows3.get(6).getTableCells().get(2).setText(""+numIndicatorsPerPriority[2][0]);
                rows3.get(7).getTableCells().get(2).setText(""+numIndicatorsPerPriority[1][2]);
                rows3.get(7).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityOneColourGreen());
                rows3.get(8).getTableCells().get(2).setText(""+numIndicatorsPerPriority[1][1]);
                rows3.get(8).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityOneColourYellow());
                rows3.get(9).getTableCells().get(2).setText(""+numIndicatorsPerPriority[1][0]);
                rows3.get(10).getTableCells().get(2).setText(""+numIndicatorsPerPriority[0][2]);
                rows3.get(10).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityZeroColourGreen());
                rows3.get(11).getTableCells().get(2).setText(""+numIndicatorsPerPriority[0][1]);
                rows3.get(11).getTableCells().get(4).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getScorePriorityZeroColourYellow());
                rows3.get(12).getTableCells().get(2).setText(""+numIndicatorsPerPriority[0][0]);
                rows3.get(13).getTableCells().get(3).setText(""+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getTotalScore());



                XWPFTable indicatorsTable=tables.get(3);

                List<XWPFTableRow> rows4=indicatorsTable.getRows();


                for(XWPFTableRow row:rows4){
                    List<XWPFTableCell> cells=row.getTableCells();
                    for(XWPFTableCell cell:cells){
                        try{
                            int numIndicator=Integer.parseInt(cell.getText());

                            if(numIndicator<=indicatorRegs.size()){
                                String color="";
                                if(indicatorRegs.get(numIndicator-1).getStatus().equals("IN_START")){
                                    color="FF0000";
                                } else if (indicatorRegs.get(numIndicator-1).getStatus().equals("IN_PROCESS")) {
                                    color="FFFF00";
                                }else{
                                    color="00FF00";
                                }
                                setCellFormat(cell, false, color,"");
                            }
                        }catch(NumberFormatException e){
                            //Continuamos
                        }
                    }

                }




                if(IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getEvaluationType().equals("COMPLETE")){
                    XWPFTable evidencesTable=tables.get(4);

                    List<XWPFTableRow> rows5=evidencesTable.getRows();
                    int[][][] evs;
                    int titlesEveryXRows=-1;
                    evs=new int[24][4][3];
                    titlesEveryXRows=5;
                    int k=0;
                    for(int i=0;i<evs.length;i++){
                        for(int j=0;j<evs[i].length;j++){
                            evs[i][j][0]=k;
                            evs[i][j][1]=evs[i][j][0]+evs[i].length;
                            evs[i][j][2]=evs[i][j][1]+evs[i].length;
                            if(j<evs[i].length-1){
                                k++;
                            }
                        }
                        k=evs[i][evs[i].length-1][2]+1;
                    }


                    int iii=0;
                    int jjj=0;
                    boolean isTheLast=false;
                    List<IndicatorsEvaluationEvidenceReg> evidenceRegs=IndicatorsEvaluationRegsUtil.getInstance().getEvidenceRegs();
                    for(int i=0;i<rows5.size();i++){
                        if(i%titlesEveryXRows!=0){
                            List<XWPFTableCell> cells=rows5.get(i).getTableCells();
                            if(evs[iii][jjj][0]<evidenceRegs.size()-1) {
                                IndicatorsEvaluationEvidenceReg reg = evidenceRegs.get(evs[iii][jjj][0]);
                                String color = "";
                                int ind = -1;
                                if (reg.getIsMarked() == 0) {
                                    color = "FF0000";
                                    ind = 1;
                                } else {
                                    color = "00FF00";
                                    ind = 2;
                                }
                                setCellFormat(cells.get(ind),false,color,"");
                            }
                            if(!isTheLast){
                                if(evs[iii][jjj][1]<evidenceRegs.size()-1) {
                                    IndicatorsEvaluationEvidenceReg reg = evidenceRegs.get(evs[iii][jjj][1]);
                                    String color = "";
                                    int ind = -1;
                                    if (reg.getIsMarked() == 0) {
                                        color = "FF0000";
                                        ind = 4;
                                    } else {
                                        color = "00FF00";
                                        ind = 5;
                                    }
                                    setCellFormat(cells.get(ind), false, color, "");
                                }
                                if(evs[iii][jjj][2]<evidenceRegs.size()-1) {
                                    IndicatorsEvaluationEvidenceReg reg = evidenceRegs.get(evs[iii][jjj][2]);
                                    String color = "";
                                    int ind = -1;
                                    if (reg.getIsMarked() == 0) {
                                        color = "FF0000";
                                        ind = 7;
                                    } else {
                                        color = "00FF00";
                                        ind = 8;
                                    }
                                    setCellFormat(cells.get(ind), false, color, "");
                                }
                            }
                            if(jjj<evs[0].length-1){
                                jjj++;
                            }else{
                                iii++;
                                jjj=0;
                            }
                        }
                        else if(iii==evs.length-1){
                            isTheLast=true;
                        }
                    }
                }
                else{
                    int idEvidenceBase=0;
                    int incr=4;
                    List<IndicatorsEvaluationSimpleEvidenceReg> evRegs=IndicatorsEvaluationRegsUtil.getInstance().getEvidenceSimpleRegs();
                    int max=4+(evRegs.size()/4);
                    for(int i=4;i<=max;i++){
                        XWPFTable currTable= tables.get(i);
                        List<XWPFTableRow> rows6=currTable.getRows();
                        rows6.get(1).getTableCells().get(0).setText("----V-----A-----A---");
                        rows6.get(2).getTableCells().get(0).setText("----V-----A-----A---");


                        /*rows6.get(1).getTableCells().get(0).setText(rows6.get(1).getTableCells().get(0).getText()+": "+evRegs.get(idEvidenceBase).getDescriptionSpanish());
                        rows6.get(2).getTableCells().get(0).setText(rows6.get(2).getTableCells().get(0).getText()+": "+evRegs.get(idEvidenceBase+1).getDescriptionSpanish());
                        rows6.get(3).getTableCells().get(0).setText(rows6.get(3).getTableCells().get(0).getText()+": "+evRegs.get(idEvidenceBase+2).getDescriptionSpanish());
                        rows6.get(4).getTableCells().get(0).setText(rows6.get(4).getTableCells().get(0).getText()+": "+evRegs.get(idEvidenceBase+3).getDescriptionSpanish());

                        int numIndicator=evRegs.get(idEvidenceBase).getIdIndicator()-1;
                        String color="";
                        int ind=-1;
                        if(indicatorRegs.get(numIndicator).getStatus().equals("IN_START")){
                            color="FF0000";
                            ind=0;
                        } else if (indicatorRegs.get(numIndicator).getStatus().equals("IN_PROCESS")) {
                            color="FFFF00";
                            ind=1;
                        }else{
                            color="00FF00";
                            ind=2;
                        }

                        XWPFTableCell cell=rows6.get(6).getTableCells().get(ind);
                        setCellFormat(cell, false, color,cell.getText());
*/

                        idEvidenceBase+=incr;
                    }
                }





                try(ByteArrayOutputStream os=new ByteArrayOutputStream()){
                    document.write(os);
                    byte[] data=os.toByteArray();
                    is.close();
                    os.close();
                    try(ByteArrayInputStream is2=new ByteArrayInputStream(data)){
                        String patientName= IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getPatient_name().replace(" ","-");
                        String creationDate=DateFormatter.timeStampToStrDate(IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getCreationDate()).replace("/","");
                        FileManager.uploadFile(is2,"reports","ORG_"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getIdOrganization()+"_"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getOrganizationType()+"_"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getIllness()+"_CENTER_"+IndicatorsEvaluationUtil.getInstance().getCenter().getIdCenter()+"_EVALTEAM_"+patientName+"-"+creationDate+"_TYPE_"+ current_evaluation.getEvaluationType()+".docx");
                        IndicatorsEvaluationUtil.removeInstance();
                        IndicatorsEvaluationRegsUtil.removeInstance();
                        IndicatorsUtil.removeInstance();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void setCellFormat(XWPFTableCell cell, boolean bold, String shadingColor, String text) {
        // Aplicar sombreado si se especifica
        if (shadingColor != null) {
            CTShd ctShd = cell.getCTTc().getTcPr().addNewShd();
            ctShd.setVal(STShd.CLEAR);
            ctShd.setColor("auto");
            ctShd.setFill(shadingColor);
        }

        // Anadir párrafo y texto con formato
        if(!text.isEmpty()){
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            XWPFRun run = paragraph.createRun();
            run.setBold(bold);
            run.setText(text);
        }
    }

    private static void cloneRowStructure(XWPFTableRow newRow, XWPFTableRow baseRow) {
        for (int i = 0; i < baseRow.getTableCells().size(); i++) {
            XWPFTableCell baseCell = baseRow.getCell(i);
            XWPFTableCell newCell = newRow.createCell();

            // Clonar ancho y bordes
            newCell.getCTTc().setTcPr(baseCell.getCTTc().getTcPr());

            // Clonar sombreado si existe
            if (baseCell.getCTTc().getTcPr().isSetShd()) {
                newCell.getCTTc().getTcPr().setShd(baseCell.getCTTc().getTcPr().getShd());
            }
        }
    }
    private void addToDatabase(){

        CountDownLatch latch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                if (youVeFinished) {
                    current_evaluation.setIsFinished(1);
                }
                if (IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(), current_evaluation.getIdEvaluatorTeam(), current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(), current_evaluation.getIllness(), current_evaluation.getIdCenter(), current_evaluation.getEvaluationType()) == null) {
                    IndicatorsEvaluationsController.Create(current_evaluation);
                } else {
                    IndicatorsEvaluationsController.Update(current_evaluation.getEvaluationDate(), current_evaluation.getIdEvaluatorTeam(), current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(), current_evaluation.getIllness(), current_evaluation.getIdCenter(), current_evaluation.getEvaluationType(), current_evaluation);
                }
            } finally {
                latch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                if(isComplete) {
                    IndicatorsEvaluationEvidenceRegsController.CreateRegs(evidenceRegs);
                }else{
                    IndicatorsEvaluationEvidenceSimpleRegsController.CreateRegs(simpleEvidenceRegs);
                }
            } finally {
                latch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                IndicatorsEvaluationIndicatorRegsController.CreateRegs(indicatorRegs);
            } finally {
                latch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                latch.await();
                IndicatorsEvaluationsController.calculateResults(current_evaluation);
                IndicatorsEvaluationRegsUtil.removeInstance();

                current_evaluation = IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(), current_evaluation.getIdEvaluatorTeam(), current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(), current_evaluation.getIllness(), current_evaluation.getIdCenter(), current_evaluation.getEvaluationType());
                IndicatorsEvaluationUtil.getInstance().setIndicatorsEvaluation(current_evaluation);
                IndicatorsEvaluationRegsUtil.createInstance(current_evaluation);
                generateReport();
                runOnUiThread(()->{
                    int idMsg=-1;
                    if(current_evaluation.getIsFinished()==1){
                        idMsg=R.string.ind_eval_reg_suc_end;
                    }else{
                        idMsg=R.string.ind_eval_reg_suc;
                    }
                    StringPasser.createInstance(getString(idMsg));
                    StringPasser.getInstance().setFlag(1);
                    Intent intent = new Intent(DoIndicatorsEvaluation.this, com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }).start();


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            doYouWantToSaveChanges();
            return true;
        }
        return false;
    }


}