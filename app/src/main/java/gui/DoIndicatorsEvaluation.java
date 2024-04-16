package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
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

    int num_ambits = 0;

    int num_evidences_reached = 0;

    //int[][] switches_values;

    List<Indicator> indicators;

    List<Evidence> evidences;

    IndicatorsEvaluation current_evaluation;


    ProgressBar progressBar;

    TextView textView;

    Organization evaluatedOrganization;

    EvaluatorTeam evaluatorTeam;


    IndicatorsEvaluationEvidenceReg[][] evidenceRegs;

    IndicatorsEvaluationIndicatorReg[] indicatorRegs;


    ConstraintLayout background;

    ConstraintLayout base;


    ConstraintLayout nextAmbit;

    Switch evidence1;
    Switch evidence2;
    Switch evidence3;
    Switch evidence4;

    List<IndicatorsEvaluationEvidenceReg> storedEvidencesRegs;

    List<IndicatorsEvaluationIndicatorReg> storedIndicatorsRegs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);

        base=findViewById(R.id.base);

        textView = findViewById(R.id.loading);

        background = findViewById(R.id.background);
        background.setVisibility(View.GONE);


        indicators=Session.getInstance().getIndicators();//.subList(0,9);
        num_indicators=indicators.size();


        current_evaluation=Session.getInstance().getCurrEvaluation();

        int num_evidences=Session.getInstance().getEvidencesByIndicator(indicators.get(0).getIdSubSubAmbit(),indicators.get(0).getIdSubAmbit(),indicators.get(0).getIdAmbit(),indicators.get(0).getIdIndicator(),indicators.get(0).getIndicatorType(),indicators.get(0).getIndicatorVersion()).size();

        indicatorRegs=new IndicatorsEvaluationIndicatorReg[num_indicators];
        evidenceRegs=new IndicatorsEvaluationEvidenceReg[num_indicators][num_evidences];
        for(int ii=0;ii<num_indicators;ii++){
            indicatorRegs[ii]=null;
            for(int jj=0;jj<num_evidences;jj++){
                evidenceRegs[ii][jj]=null;
            }
        }


        if(IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter())!=null){
            storedEvidencesRegs=IndicatorsEvaluationEvidenceRegsController.GetAllByIndicatorsEvaluation(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType());
            for (IndicatorsEvaluationEvidenceReg evidenceReg:storedEvidencesRegs){
                evidenceRegs[evidenceReg.getIdIndicator()-1][evidenceReg.getIdEvidence()-1]=evidenceReg;
            }
            storedIndicatorsRegs=IndicatorsEvaluationIndicatorRegsController.GetAllByIndicatorsEvaluation(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation.getEvaluationType());
            for(IndicatorsEvaluationIndicatorReg indicatorReg:storedIndicatorsRegs){
                indicatorRegs[indicatorReg.getIdIndicator()-1]=indicatorReg;
            }
        }


        evidence1 = (Switch) findViewById(R.id.evidence1);
        evidence2 = (Switch) findViewById(R.id.evidence2);
        evidence3 = (Switch) findViewById(R.id.evidence3);
        evidence4 = (Switch) findViewById(R.id.evidence4);


        ImageButton previous_indicator = findViewById(R.id.previous_indicatorButton);
        ImageButton next_indicator = findViewById(R.id.next_indicatorButton);
        ImageButton saveChangesButton=findViewById(R.id.save_changesButton);
        ImageButton indicatorObservations=findViewById(R.id.indicatorObsButton);
        ImageButton evidencesObservations=findViewById(R.id.evidencesObsButton);
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
                if (current_indicator == 0) {
                    Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
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
                base.setVisibility(View.GONE);
                background.setVisibility(View.VISIBLE);
                if (current_indicator < num_indicators - 1) {
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
                    //Primero mostrar observaciones y conclusiones y luego calcular resultados
                    textView.setText(getString(R.string.calculating_results));
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addToDatabase(1);
                            IndicatorsEvaluationsController.calculateResults(current_evaluation);
                            Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                            startActivity(intent);

                        }
                    }, 100);

                }
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                        .setMessage(getString(R.string.do_you_want_save_changes))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addToDatabase(0);
                                Intent intent=new Intent(DoIndicatorsEvaluation.this, MainMenu.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        indicatorObservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                        .setTitle("Observaciones del indicador")
                        .setView(new EditText(DoIndicatorsEvaluation.this))
                        .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText editText = (EditText) ((AlertDialog) dialog).findViewById(android.R.id.custom);
                                String userInput = editText.getText().toString();
                                // Aquí puedes usar userInput que es el texto introducido por el usuario
                            }
                        })
                        .create().show();
            }
        });

        evidencesObservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(DoIndicatorsEvaluation.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                
                for (int i = 0; i < num_evidences; i++) {
                    EditText editText = new EditText(DoIndicatorsEvaluation.this);
                    editText.setId(i);
                    layout.addView(editText);
                }

                new AlertDialog.Builder(DoIndicatorsEvaluation.this)
                        .setTitle("Observaciones")
                        .setView(layout)
                        .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                for (int i = 0; i < num_evidences; i++) {
                                    EditText editText = (EditText) ((AlertDialog) dialog).findViewById(i);
                                    String userInput = editText.getText().toString();
                                    // Aquí puedes usar userInput que es el texto introducido por el usuario
                                }
                            }
                        })
                        .create().show();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }






    public void changeIndicator() {
        TextView indicatorCaption = null;//(TextView) findViewById(R.id.indicator_caption);
        TextView ambitCaption = null;
        TextView subAmbitCaption = null;
        TextView subSubAmbitCaption = null;
        ConstraintLayout layoutWithAmbit = (ConstraintLayout) findViewById(R.id.layoutWithAmbit);
        ConstraintLayout layoutWithSubAmbit = (ConstraintLayout) findViewById(R.id.layoutWithSubAmbit);
        ConstraintLayout layoutWithSubSubAmbit = (ConstraintLayout) findViewById(R.id.layoutWithSubSubAmbit);
        current_ambit = indicators.get(current_indicator).getIdAmbit();
        current_subAmbit = indicators.get(current_indicator).getIdSubAmbit();
        current_subSubAmbit = indicators.get(current_indicator).getIdSubSubAmbit();
        if (current_subAmbit == -1) {
            layoutWithAmbit.setVisibility(View.VISIBLE);
            layoutWithSubAmbit.setVisibility(View.GONE);
            layoutWithSubSubAmbit.setVisibility(View.GONE);
            indicatorCaption = (TextView) findViewById(R.id.indicator_caption3);
            ambitCaption = (TextView) findViewById(R.id.ambit_caption3);
        } else {
            if (current_subSubAmbit == -1) {
                layoutWithAmbit.setVisibility(View.GONE);
                layoutWithSubAmbit.setVisibility(View.VISIBLE);
                layoutWithSubSubAmbit.setVisibility(View.GONE);
                indicatorCaption = (TextView) findViewById(R.id.indicator_caption);
                ambitCaption = (TextView) findViewById(R.id.ambit_caption);
                subAmbitCaption = (TextView) findViewById(R.id.subAmbit_caption);
            } else {
                layoutWithAmbit.setVisibility(View.GONE);
                layoutWithSubAmbit.setVisibility(View.GONE);
                layoutWithSubSubAmbit.setVisibility(View.VISIBLE);
                indicatorCaption = (TextView) findViewById(R.id.indicator_caption2);
                ambitCaption = (TextView) findViewById(R.id.ambit_caption2);
                subAmbitCaption = (TextView) findViewById(R.id.subAmbit_caption2);
                subSubAmbitCaption = (TextView) findViewById(R.id.subSubAmbit_caption);
            }
        }


        Indicator i = indicators.get(current_indicator);
        evidences=Session.getInstance().getEvidencesByIndicator(current_subSubAmbit,current_subAmbit,current_ambit,current_indicator+1,i.getIndicatorType(),i.getIndicatorVersion());

        if(indicatorRegs[current_indicator]==null){
            indicatorRegs[current_indicator]=new IndicatorsEvaluationIndicatorReg(current_evaluation.getEvaluationDate(),
                    current_evaluation.getIdEvaluatedOrganization(), current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIdEvaluatorTeam(),
                    current_evaluation.getIdEvaluatorOrganization(), current_evaluation.getOrgTypeEvaluator(), current_evaluation.getIllness(),
                    current_evaluation.getIdCenter(),current_indicator+1, i.getIndicatorType(),
                    current_subSubAmbit, current_subAmbit, current_ambit, i.getIndicatorVersion(),current_evaluation.getEvaluationType(),"","","","","","","","","","");
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

        if (Locale.getDefault().getLanguage().equals("es")) {//Español
            indicatorCaption.setText(Html.fromHtml("<b>Indicador " + i.getIdIndicator() + ": </b>" + i.getDescriptionSpanish(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>PRIMER ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEGUNDO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>TERCER ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>CUARTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUINTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEXTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish()+"</b></i>",0));
            }

            evidence1.setText(Html.fromHtml("<b>Evidencia 1: </b>" + evidences.get(0).getDescriptionSpanish(),0));
            evidence2.setText(Html.fromHtml("<b>Evidencia 2: </b>" + evidences.get(1).getDescriptionSpanish(),0));
            evidence3.setText(Html.fromHtml("<b>Evidencia 3: </b>" + evidences.get(2).getDescriptionSpanish(),0));
            evidence4.setText(Html.fromHtml("<b>Evidencia 4: </b>" + evidences.get(3).getDescriptionSpanish(),0));
        } else if (Locale.getDefault().getLanguage().equals("fr")) {//Francés
            indicatorCaption.setText(Html.fromHtml("<b>Indicateur " + i.getIdIndicator() + ": </b>" + i.getDescriptionFrench(),0));

            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>PREMIÈRE PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>DEUXIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>TROISIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUATRIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>CINQUIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SIXIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionFrench()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionFrench()+"</b></i>",0));
            }
            evidence1.setText(Html.fromHtml("<b>Preuve 1: </b>" + evidences.get(0).getDescriptionFrench(),0));
            evidence2.setText(Html.fromHtml("<b>Preuve 2: </b>" + evidences.get(1).getDescriptionFrench(),0));
            evidence3.setText(Html.fromHtml("<b>Preuve 3: </b>" + evidences.get(2).getDescriptionFrench(),0));
            evidence4.setText(Html.fromHtml("<b>Preuve 4: </b>" + evidences.get(3).getDescriptionFrench(),0));
        } else if (Locale.getDefault().getLanguage().equals("eu")) {//Euskera
            indicatorCaption.setText(Html.fromHtml("<b>"+i.getIdIndicator() + ". adierazlea: </b>" + i.getDescriptionBasque(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>LEHEN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>BIGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>HIRUGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>LAUGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>BOSTGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEIGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionBasque()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionBasque()+"</b></i>",0));
            }

            evidence1.setText(Html.fromHtml("<b>1. froga: </b>" + evidences.get(0).getDescriptionBasque(),0));
            evidence2.setText(Html.fromHtml("<b>2. froga: </b>" + evidences.get(1).getDescriptionBasque(),0));
            evidence3.setText(Html.fromHtml("<b>3. froga: </b>" + evidences.get(2).getDescriptionBasque(),0));
            evidence4.setText(Html.fromHtml("<b>4. froga: </b>" + evidences.get(3).getDescriptionBasque(),0));
        } else if (Locale.getDefault().getLanguage().equals("ca")) {//Catalán
            indicatorCaption.setText(Html.fromHtml("<b>Indicador " + i.getIdIndicator() + ": </b>" + i.getDescriptionCatalan(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>PRIMER ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEGON ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>TERCER ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUART ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>CINQUÈ ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SISÈ ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionCatalan()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionCatalan()+"</b></i>",0));
            }

            evidence1.setText(Html.fromHtml("<b>Evidència 1: </b>" + evidences.get(0).getDescriptionCatalan(),0));
            evidence2.setText(Html.fromHtml("<b>Evidència 2: </b>" + evidences.get(1).getDescriptionCatalan(),0));
            evidence3.setText(Html.fromHtml("<b>Evidència 3: </b>" + evidences.get(2).getDescriptionCatalan(),0));
            evidence4.setText(Html.fromHtml("<b>Evidència 4: </b>" + evidences.get(3).getDescriptionCatalan(),0));
        } else if (Locale.getDefault().getLanguage().equals("nl")) {//Neerlandés
            indicatorCaption.setText(Html.fromHtml("<b>Indicator " + i.getIdIndicator() + ": " + i.getDescriptionDutch(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>EERSTE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>TWEEDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>DERDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>VIERDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>VIJFDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>ZESDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionDutch()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionDutch()+"</b></i>",0));
            }

            evidence1.setText(Html.fromHtml("<b>Bewijs 1: </b>" + evidences.get(0).getDescriptionDutch(),0));
            evidence2.setText(Html.fromHtml("<b>Bewijs 2: </b>" + evidences.get(1).getDescriptionDutch(),0));
            evidence3.setText(Html.fromHtml("<b>Bewijs 3: </b>" + evidences.get(2).getDescriptionDutch(),0));
            evidence4.setText(Html.fromHtml("<b>Bewijs 4: </b>" + evidences.get(3).getDescriptionDutch(),0));
        } else if (Locale.getDefault().getLanguage().equals("gl")) {//Gallego
            indicatorCaption.setText(Html.fromHtml("<b>Indicador " + i.getIdIndicator() + ": " + i.getDescriptionGalician(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>PRIMEIRO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEGUNDO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>TERCEIRO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>CUARTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUINTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEXTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGalician()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGalician()+"</b></i>",0));
            }


            evidence1.setText(Html.fromHtml("<b>Evidencia 1: </b>" + evidences.get(0).getDescriptionGalician(),0));
            evidence2.setText(Html.fromHtml("<b>Evidencia 2: </b>" + evidences.get(1).getDescriptionGalician(),0));
            evidence3.setText(Html.fromHtml("<b>Evidencia 3: </b>" + evidences.get(2).getDescriptionGalician(),0));
            evidence4.setText(Html.fromHtml("<b>Evidencia 4: </b>" + evidences.get(3).getDescriptionGalician(),0));
        } else if (Locale.getDefault().getLanguage().equals("de")) {//Alemán
            indicatorCaption.setText(Html.fromHtml("<b>Indikator " + i.getIdIndicator() + ": " + i.getDescriptionGerman(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>ERSTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>ZWEITER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>DRITTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>VIERTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>FÜNFTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SECHSTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGerman()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGerman()+"</b></i>",0));
            }

            evidence1.setText(Html.fromHtml("<b>Beweis 1: </b>" + evidences.get(0).getDescriptionGerman(),0));
            evidence2.setText(Html.fromHtml("<b>Beweis 2: </b>" + evidences.get(1).getDescriptionGerman(),0));
            evidence3.setText(Html.fromHtml("<b>Beweis 3: </b>" + evidences.get(2).getDescriptionGerman(),0));
            evidence4.setText(Html.fromHtml("<b>Beweis 4: </b>" + evidences.get(3).getDescriptionGerman(),0));
        } else if (Locale.getDefault().getLanguage().equals("it")) {//Italiano
            indicatorCaption.setText(Html.fromHtml("<b>Indicatore " + i.getIdIndicator() + ": " + i.getDescriptionItalian(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>PRIMO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>SECONDO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>TERZO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUARTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUINTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SESTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionItalian()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionItalian()+"</b></i>",0));
            }


            evidence1.setText(Html.fromHtml("<b>Prova 1: </b>" + evidences.get(0).getDescriptionItalian(),0));
            evidence2.setText(Html.fromHtml("<b>Prova 2: </b>" + evidences.get(1).getDescriptionItalian(),0));
            evidence3.setText(Html.fromHtml("<b>Prova 3: </b>" + evidences.get(2).getDescriptionItalian(),0));
            evidence4.setText(Html.fromHtml("<b>Prova 4: </b>" + evidences.get(3).getDescriptionItalian(),0));
        } else if (Locale.getDefault().getLanguage().equals("pt")) {//Portugués
            indicatorCaption.setText(Html.fromHtml("<b>Indicador " + i.getIdIndicator() + ": " + i.getDescriptionPortuguese(),0));
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>PRIMEIRO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEGUNDO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>TERCEIRO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUARTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>QUINTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SEXTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionPortuguese()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionPortuguese()+"</b></i>",0));
            }


            evidence1.setText(Html.fromHtml("<b>Evidência 1: </b>" + evidences.get(0).getDescriptionPortuguese(),0));
            evidence2.setText(Html.fromHtml("<b>Evidência 2: </b>" + evidences.get(1).getDescriptionPortuguese(),0));
            evidence3.setText(Html.fromHtml("<b>Evidência 3: </b>" + evidences.get(2).getDescriptionPortuguese(),0));
            evidence4.setText(Html.fromHtml("<b>Evidência 4: </b>" + evidences.get(3).getDescriptionPortuguese(),0));
        } else {//Default
            indicatorCaption.setText(Html.fromHtml("<b>Indicator " + i.getIdIndicator() + ": " + i.getDescriptionEnglish(),0));

            switch (current_ambit) {
                case 1:
                    ambitCaption.setText(Html.fromHtml("<i><b>FIRST AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i>",0));
                    break;
                case 2:
                    ambitCaption.setText(Html.fromHtml("<i><b>SECOND AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i>",0));
                    break;
                case 3:
                    ambitCaption.setText(Html.fromHtml("<i><b>THIRD AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i>",0));
                    break;
                case 4:
                    ambitCaption.setText(Html.fromHtml("<i><b>FOURTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i>",0));
                    break;
                case 5:
                    ambitCaption.setText(Html.fromHtml("<i><b>FIFTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i>",0));
                    break;
                default:
                    ambitCaption.setText(Html.fromHtml("<i><b>SIXTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish()+"</b></i>",0));
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionEnglish()+"</b></i>",0));
                }
                subAmbitCaption.setText(Html.fromHtml("<i><b>"+current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionEnglish()+"</b></i>",0));
            }

            evidence1.setText(Html.fromHtml("<b>Evidence 1: </b>" + evidences.get(0).getDescriptionEnglish(),0));
            evidence2.setText(Html.fromHtml("<b>Evidence 2: </b>" + evidences.get(1).getDescriptionEnglish(),0));
            evidence3.setText(Html.fromHtml("<b>Evidence 3: </b>" + evidences.get(2).getDescriptionEnglish(),0));
            evidence4.setText(Html.fromHtml("<b>Evidence 4: </b>" + evidences.get(3).getDescriptionEnglish(),0));
        }

        Log.d("NEWIND", "Current indicator has " + num_evidences_reached + " reached evidences");
    }

    private void addToDatabase(int isFinished){
        Session.getInstance().setCurrEvaluation(null);
        current_evaluation.setIsFinished(isFinished);
        if(IndicatorsEvaluationsController.Get(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter())==null) {
            IndicatorsEvaluationsController.Create(current_evaluation);
        }else{
            IndicatorsEvaluationsController.Update(current_evaluation.getEvaluationDate(),current_evaluation.getIdEvaluatorTeam(),current_evaluation.getIdEvaluatorOrganization(),current_evaluation.getOrgTypeEvaluator(),current_evaluation.getIdEvaluatedOrganization(),current_evaluation.getOrgTypeEvaluated(),current_evaluation.getIllness(),current_evaluation.getIdCenter(),current_evaluation);
        }
        IndicatorsEvaluationEvidenceRegsController.CreateRegs(evidenceRegs);
        IndicatorsEvaluationIndicatorRegsController.CreateRegs(indicatorRegs);
        //Si isFinished==1 entonces calcula los resultados
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }



}