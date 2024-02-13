package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cli.indicators.Ambit;
import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationReg;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import otea.connection.controller.IndicatorsEvaluationRegsController;
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

    int[][] switches_values;

    List<Indicator> indicators;

    List<Ambit> ambits;

    Map<Integer, List<SubAmbit>> subAmbits;

    Map<List<Integer>, List<SubSubAmbit>> subSubAmbits;

    IndicatorsEvaluation current_evaluation;

    ProgressBar progressBar;

    TextView textView;

    Organization evaluatedOrganization;

    EvaluatorTeam evaluatorTeam;

    List<IndicatorsEvaluationReg> regs;

    ConstraintLayout background;


    ConstraintLayout nextAmbit;

    Switch evidence1;
    Switch evidence2;
    Switch evidence3;
    Switch evidence4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_evaluation);

        textView = findViewById(R.id.loading);

        background = findViewById(R.id.background);
        background.setVisibility(View.GONE);

        Session session = Session.getInstance();

        indicators = session.getIndicators();
        ambits = session.getAmbits();
        subAmbits = session.getSubAmbits();
        subSubAmbits = session.getSubSubAmbits();

        int evidences_per_indicator = indicators.get(0).getEvidences().size();


        switches_values = new int[num_indicators][evidences_per_indicator];
        for (int i = 0; i < num_indicators; i++) {
            for (int j = 0; j < evidences_per_indicator; j++) {
                switches_values[i][j] = 0;
            }
        }
        evidence1 = (Switch) findViewById(R.id.evidence1);
        evidence2 = (Switch) findViewById(R.id.evidence2);
        evidence3 = (Switch) findViewById(R.id.evidence3);
        evidence4 = (Switch) findViewById(R.id.evidence4);


        ImageButton previous_indicator = findViewById(R.id.previous_indicatorButton);
        ImageButton next_indicator = findViewById(R.id.next_indicatorButton);

        evaluatedOrganization = (Organization) getIntent().getSerializableExtra("evaluatedOrganization");
        evaluatorTeam = (EvaluatorTeam) getIntent().getSerializableExtra("evaluatorTeam");


        changeIndicator();

        evidence1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evidence1.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][0] = 1;
                    }
                    Log.d("SW1T", "Switch1 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][0] = 0;
                    }
                    Log.d("SW1F", "Switch1 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });
        evidence2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evidence2.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][1] = 1;
                    }
                    Log.d("SW2T", "Switch2 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][1] = 0;
                    }
                    Log.d("SW2F", "Switch2 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });
        evidence3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evidence3.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][2] = 1;
                    }
                    Log.d("SW3T", "Switch3 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][2] = 0;
                    }
                    Log.d("SW3F", "Switch3 false with " + num_evidences_reached + " reached evidences");
                }
            }
        });
        evidence4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evidence4.isChecked()) {
                    if (num_evidences_reached >= 0 && num_evidences_reached < 4) {
                        num_evidences_reached++;
                        switches_values[current_indicator][3] = 1;
                    }
                    Log.d("SW4T", "Switch4 true with " + num_evidences_reached + " reached evidences");
                } else {
                    if (num_evidences_reached > 0 && num_evidences_reached <= 4) {
                        num_evidences_reached--;
                        switches_values[current_indicator][3] = 0;
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
                    intent.putExtra("userEmail", getIntent().getSerializableExtra("userEmail"));
                    startActivity(intent);
                } else {
                    int nextAmbit = indicators.get(current_indicator - 1).getIdAmbit();
                    background.setVisibility(View.VISIBLE);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            current_indicator--;
                            changeIndicator();
                            background.setVisibility(View.GONE);
                        }
                    }, 100);
                }
            }
        });
        next_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_indicator < num_indicators - 1) {
                    int formerAmbit = indicators.get(current_indicator + 1).getIdAmbit();
                    int formerSubAmbit = indicators.get(current_indicator + 1).getIdSubAmbit();
                    int formerSubSubAmbit = indicators.get(current_indicator + 1).getIdSubSubAmbit();
                    background.setVisibility(View.VISIBLE);
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            current_indicator++;
                            changeIndicator();
                            background.setVisibility(View.GONE);
                            if (formerAmbit < current_ambit) {
                                if (current_subAmbit != -1) {
                                    if (current_subSubAmbit != -1) {
                                        nextAmbit = (ConstraintLayout) findViewById(R.id.ambitChange1);
                                    } else {
                                        nextAmbit = (ConstraintLayout) findViewById(R.id.ambitChange2);
                                    }
                                } else {
                                    nextAmbit = (ConstraintLayout) findViewById(R.id.ambitChange3);
                                }
                                nextAmbit.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        nextAmbit.setVisibility(View.GONE);
                                    }
                                }, 1500);
                            } else {
                                if (formerSubAmbit < current_subAmbit) {
                                    if (current_subSubAmbit != -1) {
                                        nextAmbit = (ConstraintLayout) findViewById(R.id.ambitChange1);
                                    } else {
                                        nextAmbit = (ConstraintLayout) findViewById(R.id.ambitChange2);
                                    }
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            nextAmbit.setVisibility(View.GONE);
                                        }
                                    }, 1500);
                                }
                            }
                        }
                    }, 100);
                }

                else {

                    background.setVisibility(View.VISIBLE);
                    //Primero mostrar observaciones y conclusiones y luego calcular resultados
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

                                current_evaluation = new IndicatorsEvaluation(evaluationDate, evaluatorTeam.getIdEvaluatedOrganization(), evaluatorTeam.getOrgTypeEvaluated(), evaluatorTeam.getIdEvaluatorTeam(), evaluatorTeam.getIdEvaluatorOrganization(), evaluatorTeam.getOrgTypeEvaluator(), evaluatorTeam.getIllness(), evaluatorTeam.getIdCenter(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 0, 0, 0, 0, 0, 0, 0, 1);
                                regs = new LinkedList<>();
                                for (int i = 0; i < switches_values.length; i++) {
                                    for (int j = 0; j < switches_values[i].length; j++) {
                                        regs.add(new IndicatorsEvaluationReg(evaluationDate, evaluatorTeam.getIdEvaluatorTeam(), evaluatorTeam.getIdEvaluatorOrganization(), evaluatorTeam.getOrgTypeEvaluator(), evaluatorTeam.getIdEvaluatedOrganization(), evaluatorTeam.getOrgTypeEvaluated(), evaluatorTeam.getIllness(), evaluatorTeam.getIdCenter(), indicators.get(i).getIdSubSubAmbit(), indicators.get(i).getIdSubAmbit(), indicators.get(i).getIdAmbit(), indicators.get(i).getIdIndicator(), indicators.get(i).getEvidences().get(j).getIdEvidence(), switches_values[i][j], indicators.get(i).getIndicatorVersion()));
                                    }
                                }
                                IndicatorsEvaluationsController.Create(current_evaluation);
                                for (IndicatorsEvaluationReg reg : regs) {
                                    IndicatorsEvaluationRegsController.Create(reg);
                                }
                                IndicatorsEvaluation results = IndicatorsEvaluationsController.calculateResults(evaluationDate, evaluatorTeam.getIdEvaluatorTeam(), evaluatorTeam.getIdEvaluatorOrganization(), evaluatorTeam.getOrgTypeEvaluator(), evaluatorTeam.getIdEvaluatedOrganization(), evaluatorTeam.getOrgTypeEvaluated(), evaluatorTeam.getIllness(), evaluatorTeam.getIdCenter());
                                /*
                                Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                                intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));
                                startActivity(intent);*/

                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    }, 100);

                }
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
        List<Integer> aux = null;
        if (current_subAmbit != -1 && current_subSubAmbit != -1) {
            aux = new ArrayList<>();
            aux.add(current_subAmbit);
            aux.add(current_ambit);
        }

        Indicator i = indicators.get(current_indicator);
        List<Evidence> evidences = i.getEvidences();
        if (Locale.getDefault().getLanguage().equals("es")) {//Español
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionSpanish());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMER ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionSpanish());
                    break;
                case 2:
                    ambitCaption.setText("SEGUNDO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionSpanish());
                    break;
                case 3:
                    ambitCaption.setText("TERCER ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionSpanish());
                    break;
                case 4:
                    ambitCaption.setText("CUARTO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionSpanish());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionSpanish());
                    break;
                default:
                    ambitCaption.setText("SEXTO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionSpanish());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionSpanish());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionSpanish());
            }

            evidence1.setText("Evidencia 1: " + evidences.get(0).getDescriptionSpanish());
            evidence2.setText("Evidencia 2: " + evidences.get(1).getDescriptionSpanish());
            evidence3.setText("Evidencia 3: " + evidences.get(2).getDescriptionSpanish());
            evidence4.setText("Evidencia 4: " + evidences.get(3).getDescriptionSpanish());
        } else if (Locale.getDefault().getLanguage().equals("fr")) {//Francés
            indicatorCaption.setText("Indicateur " + i.getIdIndicator() + ": " + i.getDescriptionFrench());

            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PREMIÈRE PORTÉE: " + ambits.get(current_ambit - 1).getDescriptionFrench());
                    break;
                case 2:
                    ambitCaption.setText("DEUXIÈME PORTÉE: " + ambits.get(current_ambit - 1).getDescriptionFrench());
                    break;
                case 3:
                    ambitCaption.setText("TROISIÈME PORTÉE: " + ambits.get(current_ambit - 1).getDescriptionFrench());
                    break;
                case 4:
                    ambitCaption.setText("QUATRIÈME PORTÉE: " + ambits.get(current_ambit - 1).getDescriptionFrench());
                    break;
                case 5:
                    ambitCaption.setText("CINQUIÈME PORTÉE: " + ambits.get(current_ambit - 1).getDescriptionFrench());
                    break;
                default:
                    ambitCaption.setText("SIXIÈME PORTÉE: " + ambits.get(current_ambit - 1).getDescriptionFrench());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionFrench());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionFrench());
            }
            evidence1.setText("Preuve 1: " + evidences.get(0).getDescriptionFrench());
            evidence2.setText("Preuve 2: " + evidences.get(1).getDescriptionFrench());
            evidence3.setText("Preuve 3: " + evidences.get(2).getDescriptionFrench());
            evidence4.setText("Preuve 4: " + evidences.get(3).getDescriptionFrench());
        } else if (Locale.getDefault().getLanguage().equals("eu")) {//Euskera
            indicatorCaption.setText(i.getIdIndicator() + ". adierazlea: " + i.getDescriptionBasque());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("LEHEN IRISMENA: " + ambits.get(current_ambit - 1).getDescriptionBasque());
                    break;
                case 2:
                    ambitCaption.setText("BIGARREN IRISMENA: " + ambits.get(current_ambit - 1).getDescriptionBasque());
                    break;
                case 3:
                    ambitCaption.setText("HIRUGARREN IRISMENA: " + ambits.get(current_ambit - 1).getDescriptionBasque());
                    break;
                case 4:
                    ambitCaption.setText("LAUGARREN IRISMENA: " + ambits.get(current_ambit - 1).getDescriptionBasque());
                    break;
                case 5:
                    ambitCaption.setText("BOSTGARREN IRISMENA: " + ambits.get(current_ambit - 1).getDescriptionBasque());
                    break;
                default:
                    ambitCaption.setText("SEIGARREN IRISMENA: " + ambits.get(current_ambit - 1).getDescriptionBasque());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionSpanish());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionSpanish());
            }

            evidence1.setText("1. froga: " + evidences.get(0).getDescriptionBasque());
            evidence2.setText("2. froga: " + evidences.get(1).getDescriptionBasque());
            evidence3.setText("3. froga: " + evidences.get(2).getDescriptionBasque());
            evidence4.setText("4. froga: " + evidences.get(3).getDescriptionBasque());
        } else if (Locale.getDefault().getLanguage().equals("ca")) {//Catalán
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionCatalan());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMER ÀMBIT: " + ambits.get(current_ambit - 1).getDescriptionCatalan());
                    break;
                case 2:
                    ambitCaption.setText("SEGON ÀMBIT: " + ambits.get(current_ambit - 1).getDescriptionCatalan());
                    break;
                case 3:
                    ambitCaption.setText("TERCER ÀMBIT: " + ambits.get(current_ambit - 1).getDescriptionCatalan());
                    break;
                case 4:
                    ambitCaption.setText("QUART ÀMBIT: " + ambits.get(current_ambit - 1).getDescriptionCatalan());
                    break;
                case 5:
                    ambitCaption.setText("CINQUÈ ÀMBIT: " + ambits.get(current_ambit - 1).getDescriptionCatalan());
                    break;
                default:
                    ambitCaption.setText("SISÈ ÀMBIT: " + ambits.get(current_ambit - 1).getDescriptionCatalan());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionCatalan());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionCatalan());
            }

            evidence1.setText("Evidència 1: " + evidences.get(0).getDescriptionCatalan());
            evidence2.setText("Evidència 2: " + evidences.get(1).getDescriptionCatalan());
            evidence3.setText("Evidència 3: " + evidences.get(2).getDescriptionCatalan());
            evidence4.setText("Evidència 4: " + evidences.get(3).getDescriptionCatalan());
        } else if (Locale.getDefault().getLanguage().equals("nl")) {//Neerlandés
            indicatorCaption.setText("Indicator " + i.getIdIndicator() + ": " + i.getDescriptionDutch());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("EERSTE TOEPASSINGSGEBIED: " + ambits.get(current_ambit - 1).getDescriptionDutch());
                    break;
                case 2:
                    ambitCaption.setText("TWEEDE TOEPASSINGSGEBIED: " + ambits.get(current_ambit - 1).getDescriptionDutch());
                    break;
                case 3:
                    ambitCaption.setText("DERDE TOEPASSINGSGEBIED: " + ambits.get(current_ambit - 1).getDescriptionDutch());
                    break;
                case 4:
                    ambitCaption.setText("VIERDE TOEPASSINGSGEBIED: " + ambits.get(current_ambit - 1).getDescriptionDutch());
                    break;
                case 5:
                    ambitCaption.setText("VIJFDE TOEPASSINGSGEBIED: " + ambits.get(current_ambit - 1).getDescriptionDutch());
                    break;
                default:
                    ambitCaption.setText("ZESDE TOEPASSINGSGEBIED: " + ambits.get(current_ambit - 1).getDescriptionDutch());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionDutch());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionDutch());
            }

            evidence1.setText("Bewijs 1: " + evidences.get(0).getDescriptionDutch());
            evidence2.setText("Bewijs 2: " + evidences.get(1).getDescriptionDutch());
            evidence3.setText("Bewijs 3: " + evidences.get(2).getDescriptionDutch());
            evidence4.setText("Bewijs 4: " + evidences.get(3).getDescriptionDutch());
        } else if (Locale.getDefault().getLanguage().equals("gl")) {//Gallego
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionGalician());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMEIRO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionGalician());
                    break;
                case 2:
                    ambitCaption.setText("SEGUNDO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionGalician());
                    break;
                case 3:
                    ambitCaption.setText("TERCEIRO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionGalician());
                    break;
                case 4:
                    ambitCaption.setText("CUARTO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionGalician());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionGalician());
                    break;
                default:
                    ambitCaption.setText("SEXTO ÁMBITO: " + ambits.get(current_ambit - 1).getDescriptionGalician());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionGalician());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionGalician());
            }


            evidence1.setText("Evidencia 1: " + evidences.get(0).getDescriptionGalician());
            evidence2.setText("Evidencia 2: " + evidences.get(1).getDescriptionGalician());
            evidence3.setText("Evidencia 3: " + evidences.get(2).getDescriptionGalician());
            evidence4.setText("Evidencia 4: " + evidences.get(3).getDescriptionGalician());
        } else if (Locale.getDefault().getLanguage().equals("de")) {//Alemán
            indicatorCaption.setText("Indikator " + i.getIdIndicator() + ": " + i.getDescriptionGerman());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("ERSTER UMFANG: " + ambits.get(current_ambit - 1).getDescriptionGerman());
                    break;
                case 2:
                    ambitCaption.setText("ZWEITER UMFANG: " + ambits.get(current_ambit - 1).getDescriptionGerman());
                    break;
                case 3:
                    ambitCaption.setText("DRITTER UMFANG: " + ambits.get(current_ambit - 1).getDescriptionGerman());
                    break;
                case 4:
                    ambitCaption.setText("VIERTER UMFANG: " + ambits.get(current_ambit - 1).getDescriptionGerman());
                    break;
                case 5:
                    ambitCaption.setText("FÜNFTER UMFANG: " + ambits.get(current_ambit - 1).getDescriptionGerman());
                    break;
                default:
                    ambitCaption.setText("SECHSTER UMFANG: " + ambits.get(current_ambit - 1).getDescriptionGerman());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionGerman());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionGerman());
            }

            evidence1.setText("Beweis 1: " + evidences.get(0).getDescriptionGerman());
            evidence2.setText("Beweis 2: " + evidences.get(1).getDescriptionGerman());
            evidence3.setText("Beweis 3: " + evidences.get(2).getDescriptionGerman());
            evidence4.setText("Beweis 4: " + evidences.get(3).getDescriptionGerman());
        } else if (Locale.getDefault().getLanguage().equals("it")) {//Italiano
            indicatorCaption.setText("Indicatore " + i.getIdIndicator() + ": " + i.getDescriptionItalian());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMO AMBITO: " + ambits.get(current_ambit - 1).getDescriptionItalian());
                    break;
                case 2:
                    ambitCaption.setText("SECONDO AMBITO: " + ambits.get(current_ambit - 1).getDescriptionItalian());
                    break;
                case 3:
                    ambitCaption.setText("TERZO AMBITO: " + ambits.get(current_ambit - 1).getDescriptionItalian());
                    break;
                case 4:
                    ambitCaption.setText("QUARTO AMBITO: " + ambits.get(current_ambit - 1).getDescriptionItalian());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO AMBITO: " + ambits.get(current_ambit - 1).getDescriptionItalian());
                    break;
                default:
                    ambitCaption.setText("SESTO AMBITO: " + ambits.get(current_ambit - 1).getDescriptionItalian());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionItalian());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionItalian());
            }


            evidence1.setText("Prova 1: " + evidences.get(0).getDescriptionItalian());
            evidence2.setText("Prova 2: " + evidences.get(1).getDescriptionItalian());
            evidence3.setText("Prova 3: " + evidences.get(2).getDescriptionItalian());
            evidence4.setText("Prova 4: " + evidences.get(3).getDescriptionItalian());
        } else if (Locale.getDefault().getLanguage().equals("pt")) {//Portugués
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionPortuguese());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMEIRO ESCOPO: " + ambits.get(current_ambit - 1).getDescriptionPortuguese());
                    break;
                case 2:
                    ambitCaption.setText("SEGUNDO ESCOPO: " + ambits.get(current_ambit - 1).getDescriptionPortuguese());
                    break;
                case 3:
                    ambitCaption.setText("TERCEIRO ESCOPO: " + ambits.get(current_ambit - 1).getDescriptionPortuguese());
                    break;
                case 4:
                    ambitCaption.setText("QUARTO ESCOPO: " + ambits.get(current_ambit - 1).getDescriptionPortuguese());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO ESCOPO: " + ambits.get(current_ambit - 1).getDescriptionPortuguese());
                    break;
                default:
                    ambitCaption.setText("SEXTO ESCOPO: " + ambits.get(current_ambit - 1).getDescriptionPortuguese());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionPortuguese());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionPortuguese());
            }


            evidence1.setText("Evidência 1: " + evidences.get(0).getDescriptionPortuguese());
            evidence2.setText("Evidência 2: " + evidences.get(1).getDescriptionPortuguese());
            evidence3.setText("Evidência 3: " + evidences.get(2).getDescriptionPortuguese());
            evidence4.setText("Evidência 4: " + evidences.get(3).getDescriptionPortuguese());
        } else {//Default
            indicatorCaption.setText("Indicator " + i.getIdIndicator() + ": " + i.getDescriptionEnglish());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("FIRST AMBIT: " + ambits.get(current_ambit - 1).getDescriptionEnglish());
                    break;
                case 2:
                    ambitCaption.setText("SECOND AMBIT: " + ambits.get(current_ambit - 1).getDescriptionEnglish());
                    break;
                case 3:
                    ambitCaption.setText("THIRD AMBIT: " + ambits.get(current_ambit - 1).getDescriptionEnglish());
                    break;
                case 4:
                    ambitCaption.setText("FOURTH AMBIT: " + ambits.get(current_ambit - 1).getDescriptionEnglish());
                    break;
                case 5:
                    ambitCaption.setText("FIFTH AMBIT: " + ambits.get(current_ambit - 1).getDescriptionEnglish());
                    break;
                default:
                    ambitCaption.setText("SIXTH AMBIT: " + ambits.get(current_ambit - 1).getDescriptionEnglish());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + subSubAmbits.get(aux).get(current_subSubAmbit - 1).getDescriptionEnglish());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + subAmbits.get(current_ambit).get(current_subAmbit - 1).getDescriptionEnglish());
            }

            evidence1.setText("Evidence 1: " + evidences.get(0).getDescriptionEnglish());
            evidence2.setText("Evidence 2: " + evidences.get(1).getDescriptionEnglish());
            evidence3.setText("Evidence 3: " + evidences.get(2).getDescriptionEnglish());
            evidence4.setText("Evidence 4: " + evidences.get(3).getDescriptionEnglish());
        }

        Log.d("NEWIND", "Current indicator has " + num_evidences_reached + " reached evidences");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }



}