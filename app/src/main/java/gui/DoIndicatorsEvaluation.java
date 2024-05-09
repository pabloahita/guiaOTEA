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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.MainMenu;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import otea.connection.controller.IndicatorsEvaluationEvidenceRegsController;
import otea.connection.controller.IndicatorsEvaluationIndicatorRegsController;
import otea.connection.controller.IndicatorsEvaluationsController;
import session.Session;

public class DoIndicatorsEvaluation extends AppCompatActivity {

    int current_indicator = 0;
    int current_ambit = 0;

    int current_subAmbit = 0;

    int current_subSubAmbit = 0;

    int num_indicators = 0;

    int num_evidences=0;

    int num_evidences_reached = 0;

    List<Indicator> indicators;

    List<Evidence> evidences;

    IndicatorsEvaluation current_evaluation;


    TextView textView;


    IndicatorsEvaluationEvidenceReg[][] evidenceRegs;

    IndicatorsEvaluationIndicatorReg[] indicatorRegs;


    ConstraintLayout background;

    ConstraintLayout base;


    Switch evidence1;
    Switch evidence2;
    Switch evidence3;
    Switch evidence4;

    List<IndicatorsEvaluationEvidenceReg> storedEvidencesRegs;

    List<IndicatorsEvaluationIndicatorReg> storedIndicatorsRegs;

    boolean youVeFinished;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);

        youVeFinished=false;
        base=findViewById(R.id.base);

        textView = findViewById(R.id.loading);

        background = findViewById(R.id.background);
        background.setVisibility(View.GONE);


        current_evaluation=Session.getInstance().getCurrEvaluation();

        indicators=Session.getInstance().getIndicators(current_evaluation.getEvaluationType());
        num_indicators=indicators.size();


        num_evidences=Session.getInstance().getEvidencesByIndicator(indicators.get(0).getIdSubSubAmbit(),indicators.get(0).getIdSubAmbit(),indicators.get(0).getIdAmbit(),indicators.get(0).getIdIndicator(),indicators.get(0).getIndicatorType(),indicators.get(0).getIndicatorVersion(),current_evaluation.getEvaluationType()).size();

        indicatorRegs=new IndicatorsEvaluationIndicatorReg[num_indicators];
        evidenceRegs=new IndicatorsEvaluationEvidenceReg[num_indicators][num_evidences];
        for(int ii=0;ii<num_indicators;ii++){
            indicatorRegs[ii]=null;
            for(int jj=0;jj<num_evidences;jj++){
                evidenceRegs[ii][jj]=null;
            }
        }


        if(IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType())!=null){
            storedEvidencesRegs=IndicatorsEvaluationEvidenceRegsController.GetAllByIndicatorsEvaluation(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType());
            for (IndicatorsEvaluationEvidenceReg evidenceReg:storedEvidencesRegs){
                evidenceRegs[evidenceReg.getIdIndicator()-1][evidenceReg.getIdEvidence()-1]=evidenceReg;
            }
            storedIndicatorsRegs=IndicatorsEvaluationIndicatorRegsController.GetAllByIndicatorsEvaluation(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType());
            for(IndicatorsEvaluationIndicatorReg indicatorReg:storedIndicatorsRegs){
                indicatorRegs[indicatorReg.getIdIndicator()-1]=indicatorReg;
            }
            current_indicator=storedIndicatorsRegs.size()-1;//Ir al indicador donde lo dejamos
        }


        evidence1 = (Switch) findViewById(R.id.evidence1);
        evidence2 = (Switch) findViewById(R.id.evidence2);
        evidence3 = (Switch) findViewById(R.id.evidence3);
        evidence4 = (Switch) findViewById(R.id.evidence4);


        ImageButton previous_indicator = findViewById(R.id.previous_indicatorButton);
        ImageButton next_indicator = findViewById(R.id.next_indicatorButton);
        ImageButton saveChangesButton=findViewById(R.id.save_changesButton);
        ImageButton indicatorObservations=findViewById(R.id.indicatorObsButton);
        ImageButton helpButton=findViewById(R.id.helpIndButton);


        changeIndicator();



        evidence1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        evidenceRegs[current_indicator][0].setIsMarked(1);
                    }
                    Log.d("SW1T", "Switch1 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        evidenceRegs[current_indicator][0].setIsMarked(0);
                    }
                    Log.d("SW1F", "Switch1 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });


        evidence2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        evidenceRegs[current_indicator][1].setIsMarked(1);
                    }
                    Log.d("SW2T", "Switch2 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        evidenceRegs[current_indicator][1].setIsMarked(0);
                    }
                    Log.d("SW2F", "Switch2 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });



        evidence3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        evidenceRegs[current_indicator][2].setIsMarked(1);
                    }
                    Log.d("SW3T", "Switch3 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        evidenceRegs[current_indicator][2].setIsMarked(0);
                    }
                    Log.d("SW3F", "Switch3 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });

        evidence4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        evidenceRegs[current_indicator][3].setIsMarked(1);
                    }
                    Log.d("SW4T", "Switch4 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        evidenceRegs[current_indicator][3].setIsMarked(0);
                    }
                    Log.d("SW4F", "Switch4 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });

        previous_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PI", "Previous Indicator Pressed");
                indicatorRegs[current_indicator].setNumEvidencesMarked(num_evidences_reached);
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
                indicatorRegs[current_indicator].setNumEvidencesMarked(num_evidences_reached);
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
                                            Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                                            startActivity(intent);

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
                indicatorRegs[current_indicator].setNumEvidencesMarked(num_evidences_reached);
                doYouWantToSaveChanges();
            }
        });

        indicatorObservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = null;// inflater.inflate(R.layout.ind_obs, null);

                if(num_evidences==3){
                    view=inflater.inflate(R.layout.three_ev_obs, null);
                }else if(num_evidences==4){
                    view=inflater.inflate(R.layout.four_ev_obs, null);
                }

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
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsSpanish());
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsFrench());
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsBasque());
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsCatalan());
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsDutch());
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsGalician());
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsGerman());
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsItalian());
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsPortuguese());
                }else{
                    editText.setText(evidenceRegs[current_indicator][0].getObservationsEnglish());
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            evidenceRegs[current_indicator][0].setObservationsSpanish(text);
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            evidenceRegs[current_indicator][0].setObservationsFrench(text);
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            evidenceRegs[current_indicator][0].setObservationsBasque(text);
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            evidenceRegs[current_indicator][0].setObservationsCatalan(text);
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            evidenceRegs[current_indicator][0].setObservationsDutch(text);
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            evidenceRegs[current_indicator][0].setObservationsGalician(text);
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            evidenceRegs[current_indicator][0].setObservationsGerman(text);
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            evidenceRegs[current_indicator][0].setObservationsItalian(text);
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            evidenceRegs[current_indicator][0].setObservationsPortuguese(text);
                        }else{
                            evidenceRegs[current_indicator][0].setObservationsEnglish(text);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                editText = view.findViewById(R.id.evObs2);

                if(Locale.getDefault().getLanguage().equals("es")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsSpanish());
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsFrench());
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsBasque());
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsCatalan());
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsDutch());
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsGalician());
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsGerman());
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsItalian());
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsPortuguese());
                }else{
                    editText.setText(evidenceRegs[current_indicator][1].getObservationsEnglish());
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            evidenceRegs[current_indicator][1].setObservationsSpanish(text);
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            evidenceRegs[current_indicator][1].setObservationsFrench(text);
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            evidenceRegs[current_indicator][1].setObservationsBasque(text);
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            evidenceRegs[current_indicator][1].setObservationsCatalan(text);
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            evidenceRegs[current_indicator][1].setObservationsDutch(text);
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            evidenceRegs[current_indicator][1].setObservationsGalician(text);
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            evidenceRegs[current_indicator][1].setObservationsGerman(text);
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            evidenceRegs[current_indicator][1].setObservationsItalian(text);
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            evidenceRegs[current_indicator][1].setObservationsPortuguese(text);
                        }else{
                            evidenceRegs[current_indicator][1].setObservationsEnglish(text);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                editText = view.findViewById(R.id.evObs3);

                if(Locale.getDefault().getLanguage().equals("es")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsSpanish());
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsFrench());
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsBasque());
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsCatalan());
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsDutch());
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsGalician());
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsGerman());
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsItalian());
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsPortuguese());
                }else{
                    editText.setText(evidenceRegs[current_indicator][2].getObservationsEnglish());
                }


                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String text=s.toString();
                        if(Locale.getDefault().getLanguage().equals("es")){
                            evidenceRegs[current_indicator][2].setObservationsSpanish(text);
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            evidenceRegs[current_indicator][2].setObservationsFrench(text);
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            evidenceRegs[current_indicator][2].setObservationsBasque(text);
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            evidenceRegs[current_indicator][2].setObservationsCatalan(text);
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            evidenceRegs[current_indicator][2].setObservationsDutch(text);
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            evidenceRegs[current_indicator][2].setObservationsGalician(text);
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            evidenceRegs[current_indicator][2].setObservationsGerman(text);
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            evidenceRegs[current_indicator][2].setObservationsItalian(text);
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            evidenceRegs[current_indicator][2].setObservationsPortuguese(text);
                        }else{
                            evidenceRegs[current_indicator][2].setObservationsEnglish(text);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if(num_evidences==4){
                    editText = view.findViewById(R.id.evObs4);

                    if(Locale.getDefault().getLanguage().equals("es")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsSpanish());
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsFrench());
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsBasque());
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsCatalan());
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsDutch());
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsGalician());
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsGerman());
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsItalian());
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsPortuguese());
                    }else{
                        editText.setText(evidenceRegs[current_indicator][3].getObservationsEnglish());
                    }


                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String text=s.toString();
                            if(Locale.getDefault().getLanguage().equals("es")){
                                evidenceRegs[current_indicator][3].setObservationsSpanish(text);
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                evidenceRegs[current_indicator][3].setObservationsFrench(text);
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                evidenceRegs[current_indicator][3].setObservationsBasque(text);
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                evidenceRegs[current_indicator][3].setObservationsCatalan(text);
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                evidenceRegs[current_indicator][3].setObservationsDutch(text);
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                evidenceRegs[current_indicator][3].setObservationsGalician(text);
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                evidenceRegs[current_indicator][3].setObservationsGerman(text);
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                evidenceRegs[current_indicator][3].setObservationsItalian(text);
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                evidenceRegs[current_indicator][3].setObservationsPortuguese(text);
                            }else{
                                evidenceRegs[current_indicator][3].setObservationsEnglish(text);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                }

                new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                        .setTitle(getString(R.string.ind_obs))
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
                    text2="En la parte inferior de la pantalla dispone de las siguientes opciones:<ul><li><b>Observaciones del indicador:</b> En este apartado puede rellenar las observaciones del indicador y de sus correspondientes evidencias.</li><li><b>Ayuda:</b> Muestra este mismo apartado.</li><li><b>Indicador Anterior:</b> Si toca este botón se regresará al indicador anterior, salvo si se trata del indicador anterior, que al presionar preguntará si se desean guardar los cambios.</li><li><b>Guardar Cambios:</b> Si se desea continuar en otro momento con la evaluación de indicadores, presione sobre este botón.</li><li><b>Indicador Siguiente:</b> Si toca este botón se avanzará sobre el siguiente indicador, salvo si se trata del último indicador, en cuyo caso se mostrará el apartado de conclusiones de la evaluación de indicadores.";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    text2="En bas de l'écran, vous avez les options suivantes :<ul><li><b>Observations de l'indicateur :</b> Dans cette section, vous pouvez remplir les observations sur l'indicateur et ses preuves correspondantes.</li><li><b>Aide :</b> Affiche cette même section.</li><li><b>Indicateur précédent :</b> Si vous appuyez sur ce bouton, vous retournerez à l'indicateur précédent, sauf s'il s'agit du premier indicateur, auquel cas il vous demandera si vous souhaitez enregistrer les modifications.</li><li><b>Enregistrer les modifications :</b> Si vous souhaitez continuer l'évaluation des indicateurs ultérieurement, appuyez sur ce bouton.</li><li><b>Indicateur suivant :</b> Si vous appuyez sur ce bouton, vous avancerez vers l'indicateur suivant, sauf s'il s'agit du dernier indicateur, auquel cas la section des conclusions de l'évaluation de l'indicateur sera affichée.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    text2="Pantailaren beheko aldean, aukera hauek dituzu:<ul><li><b>Adierazlearen oharrak:</b> Atal honetan, adierazlearen eta bere egiaztagirien oharrak bete ditzakezu.</li><li><b>Laguntza:</b> Atal berean agertuko da.</li><li><b>Aurreko Adierazlea:</b> Sakatzen baduzu, aurreko adierazlera itzuliko zara, izan ere, lehenengo adierazlearen kasuan, aldaketak gordetzeko galdeginga egingo diozu.</li><li><b>Aldaketak Gorde:</b> Adierazleak gauzatzeko beste une batean jarraitu nahi baduzu, sakatu botoi hau.</li><li><b>Hurrengo Adierazlea:</b> Botoi hau sakatzen baduzu, hurrengo adierazlera egingo duzu, azken adierazlearen kasuan, adierazlearen ebaluazioaren amaierako atal agertuko da.</li>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    text2="A la part inferior de la pantalla, teniu les següents opcions:<ul><li><b>Observacions de l'indicador:</b> En aquesta secció, podeu omplir les observacions sobre l'indicador i les seves proves corresponents.</li><li><b>Ajuda:</b> Mostra aquesta mateixa secció.</li><li><b>Indicador anterior:</b> Si toqueu aquest botó, tornareu a l'indicador anterior, llevat que sigui el primer indicador, en què cas preguntarà si voleu desar els canvis.</li><li><b>Desar canvis:</b> Si voleu continuar l'avaluació d'indicadors en un altre moment, premeu aquest botó.</li><li><b>Indicador següent:</b> Si toqueu aquest botó, avançareu al següent indicador, llevat que sigui l'últim indicador, en què cas es mostrarà la secció de conclusions de l'avaluació de l'indicador.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    text2="Onderaan het scherm heb je de volgende opties:<ul><li><b>Indicatorobservaties:</b> In dit gedeelte kun je observaties invullen over de indicator en de bijbehorende bewijzen.</li><li><b>Help:</b> Toont ditzelfde gedeelte.</li><li><b>Vorige indicator:</b> Als je op deze knop drukt, ga je terug naar de vorige indicator, tenzij het de eerste indicator is, waarbij het zal vragen of je de wijzigingen wilt opslaan.</li><li><b>Wijzigingen opslaan:</b> Als je de evaluatie van indicatoren op een later tijdstip wilt voortzetten, druk dan op deze knop.</li><li><b>Volgende indicator:</b> Als je op deze knop drukt, ga je naar de volgende indicator, tenzij het de laatste indicator is, in welk geval het gedeelte met conclusies van de indicator evaluatie wordt weergegeven.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    text2="No extremo inferior da pantalla tes as seguintes opcións:<ul><li><b>Observacións do indicador:</b> Nesta sección, podes encher as observacións sobre o indicador e as súas correspondentes evidencias.</li><li><b>Axuda:</b> Amosa esta mesma sección.</li><li><b>Indicador anterior:</b> Se premes este botón, volverás ao indicador anterior, salvo que sexa o primeiro indicador, no que caso preguntará se desexas gardar os cambios.</li><li><b>Gardar cambios:</b> Se queres continuar a avaliación de indicadores noutro momento, preme este botón.</li><li><b>Indicador seguinte:</b> Se premes este botón, avanzarás ao seguinte indicador, salvo que sexa o último indicador, no que caso mostrarase a sección de conclusións da avaliación do indicador.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    text2="Unten auf dem Bildschirm haben Sie folgende Optionen:<ul><li><b>Indikatorbeobachtungen:</b> In diesem Abschnitt können Sie Beobachtungen zum Indikator und seinen entsprechenden Beweisen ausfüllen.</li><li><b>Hilfe:</b> Zeigt diesen Abschnitt.</li><li><b>Vorheriger Indikator:</b> Wenn Sie auf diese Schaltfläche tippen, kehren Sie zum vorherigen Indikator zurück, es sei denn, es handelt sich um den ersten Indikator, in welchem Fall gefragt wird, ob Sie die Änderungen speichern möchten.</li><li><b>Änderungen speichern:</b> Wenn Sie die Bewertung der Indikatoren zu einem späteren Zeitpunkt fortsetzen möchten, drücken Sie diese Schaltfläche.</li><li><b>Nächster Indikator:</b> Wenn Sie auf diese Schaltfläche tippen, gelangen Sie zum nächsten Indikator, es sei denn, es handelt sich um den letzten Indikator, in welchem Fall der Abschnitt mit den Schlussfolgerungen der Indikatorenbewertung angezeigt wird.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    text2="In basso sullo schermo hai le seguenti opzioni:<ul><li><b>Osservazioni dell'indicatore:</b> In questa sezione puoi compilare osservazioni sull'indicatore e sulle relative prove.</li><li><b>Aiuto:</b> Visualizza questa stessa sezione.</li><li><b>Indicatore precedente:</b> Se toccate questo pulsante, tornerete all'indicatore precedente, a meno che non sia il primo indicatore, nel qual caso chiederà se volete salvare le modifiche.</li><li><b>Salva modifiche:</b> Se desideri continuare l'valutazione degli indicatori in un momento successivo, premi questo pulsante.</li><li><b>Indicatore successivo:</b> Se toccate questo pulsante, passerete all'indicatore successivo, a meno che non sia l'ultimo indicatore, nel qual caso verrà visualizzata la sezione delle conclusioni della valutazione dell'indicatore.</li></ul>";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    text2="No final da tela, você tem as seguintes opções:<ul><li><b>Observações do indicador:</b> Nesta seção, você pode preencher observações sobre o indicador e suas evidências correspondentes.</li><li><b>Ajuda:</b> Exibe esta mesma seção.</li><li><b>Indicador anterior:</b> Se você tocar neste botão, voltará ao indicador anterior, a menos que seja o primeiro indicador, caso em que perguntará se deseja salvar as alterações.</li><li><b>Salvar alterações:</b> Se desejar continuar a avaliação dos indicadores em outro momento, pressione este botão.</li><li><b>Próximo indicador:</b> Se você tocar neste botão, avançará para o próximo indicador, a menos que seja o último indicador, caso em que a seção de conclusões da avaliação do indicador será exibida.</li></ul>";
                }else{
                    text2="At the bottom of the screen, you have the following options:<ul><li><b>Indicator Observations:</b> In this section, you can fill in observations about the indicator and its corresponding evidence.</li><li><b>Help:</b> Displays this same section.</li><li><b>Previous Indicator:</b> If you touch this button, you will return to the previous indicator, unless it is the first indicator, in which case it will ask if you want to save the changes.</li><li><b>Save Changes:</b> If you want to continue the evaluation of indicators at another time, press this button.</li><li><b>Next Indicator:</b> If you touch this button, you will advance to the next indicator, unless it is the last indicator, in which case the conclusions section of the indicator evaluation will be displayed.</li></ul>";
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






    public void changeIndicator() {
        current_ambit = indicators.get(current_indicator).getIdAmbit();
        current_subAmbit = indicators.get(current_indicator).getIdSubAmbit();
        current_subSubAmbit = indicators.get(current_indicator).getIdSubSubAmbit();
        TextView indicatorCaption=findViewById(R.id.indicator_caption);

        Indicator i = indicators.get(current_indicator);
        evidences=Session.getInstance().getEvidencesByIndicator(current_subSubAmbit,current_subAmbit,current_ambit,current_indicator+1,i.getIndicatorType(),i.getIndicatorVersion(),i.getEvaluationType());
        if(indicatorRegs[current_indicator]==null){
            indicatorRegs[current_indicator]=new IndicatorsEvaluationIndicatorReg(current_evaluation.getEvaluationDate(),
                    current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIdEvaluatorTeam(),
                    current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIllness(),
                    current_evaluation.getIdCenter(),current_indicator+1, i.getIndicatorType(),
                    current_subSubAmbit, current_subAmbit, current_ambit, i.getIndicatorVersion(),current_evaluation.getEvaluationType(),"","","","","","","","","","",0,"");
        }
        if(evidenceRegs[current_indicator][0]==null) {
            for (Evidence e : evidences) {
                evidenceRegs[current_indicator][e.getIdEvidence()-1]=new IndicatorsEvaluationEvidenceReg(current_evaluation.getEvaluationDate(),
                        current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIdEvaluatorTeam(),
                current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIllness(),
                current_evaluation.getIdCenter(), e.getIdEvidence(), current_indicator+1, e.getIndicatorType(),
                        current_subSubAmbit, current_subAmbit, current_ambit, e.getIndicatorVersion(),0,current_evaluation.getEvaluationType(),"","","","","","","","","","");
            }
        }
        num_evidences_reached=indicatorRegs[current_indicator].getNumEvidencesMarked();

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
        }if(evidenceRegs[current_indicator][3].getIsMarked()==1){
            evidence4.setChecked(true);
        }else{
            evidence4.setChecked(false);
        }

        String indCaption="";
        if (Locale.getDefault().getLanguage().equals("es")) {//Español

            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMER ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGUNDO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCER ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>CUARTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEXTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": </b>" + i.getDescriptionSpanish()+"<br>";

            evidence1.setText(Html.fromHtml("<b>Evidencia 1: </b>" + evidences.get(0).getDescriptionSpanish(),0));
            evidence2.setText(Html.fromHtml("<b>Evidencia 2: </b>" + evidences.get(1).getDescriptionSpanish(),0));
            evidence3.setText(Html.fromHtml("<b>Evidencia 3: </b>" + evidences.get(2).getDescriptionSpanish(),0));
            evidence4.setText(Html.fromHtml("<b>Evidencia 4: </b>" + evidences.get(3).getDescriptionSpanish(),0));
        } else if (Locale.getDefault().getLanguage().equals("fr")) {//Francés
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PREMIÈRE PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>DEUXIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TROISIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUATRIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>CINQUIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SIXIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicateur " + i.getIdIndicator() + ": </b>" + i.getDescriptionFrench()+"<br>";


            evidence1.setText(Html.fromHtml("<b>Preuve 1: </b>" + evidences.get(0).getDescriptionFrench(),0));
            evidence2.setText(Html.fromHtml("<b>Preuve 2: </b>" + evidences.get(1).getDescriptionFrench(),0));
            evidence3.setText(Html.fromHtml("<b>Preuve 3: </b>" + evidences.get(2).getDescriptionFrench(),0));
            evidence4.setText(Html.fromHtml("<b>Preuve 4: </b>" + evidences.get(3).getDescriptionFrench(),0));
        } else if (Locale.getDefault().getLanguage().equals("eu")) {//Euskera

            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionBasque()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionBasque()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>LEHEN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>BIGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>HIRUGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>LAUGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>BOSTGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEIGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>"+i.getIdIndicator() + ". adierazlea: </b>" + i.getDescriptionBasque()+"<br>";
            evidence1.setText(Html.fromHtml("<b>1. froga: </b>" + evidences.get(0).getDescriptionBasque(),0));
            evidence2.setText(Html.fromHtml("<b>2. froga: </b>" + evidences.get(1).getDescriptionBasque(),0));
            evidence3.setText(Html.fromHtml("<b>3. froga: </b>" + evidences.get(2).getDescriptionBasque(),0));
            evidence4.setText(Html.fromHtml("<b>4. froga: </b>" + evidences.get(3).getDescriptionBasque(),0));
        } else if (Locale.getDefault().getLanguage().equals("ca")) {//Catalán
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionCatalan()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionCatalan()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMER ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGON ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCER ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUART ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>CINQUÈ ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SISÈ ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": </b>" + i.getDescriptionCatalan()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Evidència 1: </b>" + evidences.get(0).getDescriptionCatalan(),0));
            evidence2.setText(Html.fromHtml("<b>Evidència 2: </b>" + evidences.get(1).getDescriptionCatalan(),0));
            evidence3.setText(Html.fromHtml("<b>Evidència 3: </b>" + evidences.get(2).getDescriptionCatalan(),0));
            evidence4.setText(Html.fromHtml("<b>Evidència 4: </b>" + evidences.get(3).getDescriptionCatalan(),0));
        } else if (Locale.getDefault().getLanguage().equals("nl")) {//Neerlandés
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionDutch()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionDutch()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>EERSTE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>TWEEDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>DERDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>VIERDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>VIJFDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>ZESDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicator " + i.getIdIndicator() + ": " + i.getDescriptionDutch()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Bewijs 1: </b>" + evidences.get(0).getDescriptionDutch(),0));
            evidence2.setText(Html.fromHtml("<b>Bewijs 2: </b>" + evidences.get(1).getDescriptionDutch(),0));
            evidence3.setText(Html.fromHtml("<b>Bewijs 3: </b>" + evidences.get(2).getDescriptionDutch(),0));
            evidence4.setText(Html.fromHtml("<b>Bewijs 4: </b>" + evidences.get(3).getDescriptionDutch(),0));
        } else if (Locale.getDefault().getLanguage().equals("gl")) {//Gallego
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGalician()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGalician()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMEIRO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGUNDO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCEIRO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>CUARTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEXTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": " + i.getDescriptionGalician()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Evidencia 1: </b>" + evidences.get(0).getDescriptionGalician(),0));
            evidence2.setText(Html.fromHtml("<b>Evidencia 2: </b>" + evidences.get(1).getDescriptionGalician(),0));
            evidence3.setText(Html.fromHtml("<b>Evidencia 3: </b>" + evidences.get(2).getDescriptionGalician(),0));
            evidence4.setText(Html.fromHtml("<b>Evidencia 4: </b>" + evidences.get(3).getDescriptionGalician(),0));
        } else if (Locale.getDefault().getLanguage().equals("de")) {//Alemán
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGerman()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGerman()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>ERSTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>ZWEITER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>DRITTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>VIERTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>FÜNFTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SECHSTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indikator " + i.getIdIndicator() + ": " + i.getDescriptionGerman()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Beweis 1: </b>" + evidences.get(0).getDescriptionGerman(),0));
            evidence2.setText(Html.fromHtml("<b>Beweis 2: </b>" + evidences.get(1).getDescriptionGerman(),0));
            evidence3.setText(Html.fromHtml("<b>Beweis 3: </b>" + evidences.get(2).getDescriptionGerman(),0));
            evidence4.setText(Html.fromHtml("<b>Beweis 4: </b>" + evidences.get(3).getDescriptionGerman(),0));
        } else if (Locale.getDefault().getLanguage().equals("it")) {//Italiano
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionItalian()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionItalian()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SECONDO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERZO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUARTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SESTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicatore " + i.getIdIndicator() + ": " + i.getDescriptionItalian()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Prova 1: </b>" + evidences.get(0).getDescriptionItalian(),0));
            evidence2.setText(Html.fromHtml("<b>Prova 2: </b>" + evidences.get(1).getDescriptionItalian(),0));
            evidence3.setText(Html.fromHtml("<b>Prova 3: </b>" + evidences.get(2).getDescriptionItalian(),0));
            evidence4.setText(Html.fromHtml("<b>Prova 4: </b>" + evidences.get(3).getDescriptionItalian(),0));
        } else if (Locale.getDefault().getLanguage().equals("pt")) {//Portugués
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionPortuguese()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>PRIMEIRO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SEGUNDO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>TERCEIRO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>QUARTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>QUINTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SEXTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicador " + i.getIdIndicator() + ": " + i.getDescriptionPortuguese()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Evidência 1: </b>" + evidences.get(0).getDescriptionPortuguese(),0));
            evidence2.setText(Html.fromHtml("<b>Evidência 2: </b>" + evidences.get(1).getDescriptionPortuguese(),0));
            evidence3.setText(Html.fromHtml("<b>Evidência 3: </b>" + evidences.get(2).getDescriptionPortuguese(),0));
            evidence4.setText(Html.fromHtml("<b>Evidência 4: </b>" + evidences.get(3).getDescriptionPortuguese(),0));
        } else {//Default

            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionEnglish()+"</b></i><br>";
                }
                indCaption+="<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionEnglish()+"</b></i><br>";
            }
            switch (current_ambit) {
                case 1:
                    indCaption+="<i><b>FIRST AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 2:
                    indCaption+="<i><b>SECOND AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 3:
                    indCaption+="<i><b>THIRD AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 4:
                    indCaption+="<i><b>FOURTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                case 5:
                    indCaption+="<i><b>FIFTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
                default:
                    indCaption+="<i><b>SIXTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i><br>";
                    break;
            }
            indCaption+="<b>Indicator " + i.getIdIndicator() + ": " + i.getDescriptionEnglish()+"<br>";
            evidence1.setText(Html.fromHtml("<b>Evidence 1: </b>" + evidences.get(0).getDescriptionEnglish(),0));
            evidence2.setText(Html.fromHtml("<b>Evidence 2: </b>" + evidences.get(1).getDescriptionEnglish(),0));
            evidence3.setText(Html.fromHtml("<b>Evidence 3: </b>" + evidences.get(2).getDescriptionEnglish(),0));
            evidence4.setText(Html.fromHtml("<b>Evidence 4: </b>" + evidences.get(3).getDescriptionEnglish(),0));
        }
        indicatorCaption.setText(Html.fromHtml(indCaption,0));
        Log.d("NEWIND", "Current indicator has " + num_evidences_reached + " reached evidences");
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
                        Intent intent=new Intent(DoIndicatorsEvaluation.this, MainMenu.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent=new Intent(DoIndicatorsEvaluation.this, MainMenu.class);
                        startActivity(intent);

                    }
                }).create().show();
    }
    private void addToDatabase(){
        Session.getInstance().setCurrEvaluation(null);
        if(IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType())==null) {
            IndicatorsEvaluationsController.Create(current_evaluation);
        }else{
            IndicatorsEvaluationsController.Update(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType(),current_evaluation);
        }
        IndicatorsEvaluationEvidenceRegsController.CreateRegs(evidenceRegs);
        IndicatorsEvaluationIndicatorRegsController.CreateRegs(indicatorRegs);
        if(youVeFinished==true){
            IndicatorsEvaluationsController.calculateResults(current_evaluation);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            doYouWantToSaveChanges();
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guarda el estado de la actividad

        outState.putInt("current_indicator",current_indicator);
        outState.putInt("current_ambit",current_ambit);
        outState.putInt("current_subAmbit",current_subAmbit);
        outState.putInt("current_subSubAmbit",current_subSubAmbit);
        outState.putInt("num_indicators",num_indicators);
        outState.putInt("num_evidences",num_evidences);
        outState.putInt("num_evidences_reached",num_evidences_reached);
        for(int i=0;i<num_indicators;i++){
            outState.putSerializable("reg_ind"+(i+1),indicatorRegs[i]);
            for(int j=0;j<num_evidences;j++){
                outState.putSerializable("reg_ind"+(i+1)+"_ev"+(j+1),evidenceRegs[i][j]);
            }
        }

    }


}