package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import session.Session;

public class EditIndicators extends AppCompatActivity{

    int current_indicator=0;
    int current_ambit=0;

    int current_subAmbit=0;

    int current_subSubAmbit=0;

    int num_indicators=0;


    int num_evidences_reached=0;


    List<Indicator> indicators;


    ProgressBar progressBar;

    TextView textView;

    Organization evaluatedOrganization;

    EvaluatorTeam evaluatorTeam;



    TextView evidence1;
    TextView evidence2;
    TextView evidence3;
    TextView evidence4;


    ConstraintLayout background;


    ConstraintLayout nextAmbit;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_indicators);

        textView=findViewById(R.id.loading);

        background=findViewById(R.id.background);
        background.setVisibility(View.GONE);

        Session session= Session.getInstance();

        indicators=session.getIndicators(""); //Cambiar
        num_indicators=indicators.size();

        TextView indicatorCaption = (TextView) findViewById(R.id.indicator_caption);
        evidence1=findViewById(R.id.evidence1);
        evidence2=findViewById(R.id.evidence2);
        evidence3=findViewById(R.id.evidence3);
        evidence4=findViewById(R.id.evidence4);



        ImageButton previous_indicator=findViewById(R.id.previous_indicatorButton);
        ImageButton next_indicator=findViewById(R.id.next_indicatorButton);

        evaluatedOrganization=(Organization) getIntent().getSerializableExtra("evaluatedOrganization");
        evaluatorTeam=(EvaluatorTeam) getIntent().getSerializableExtra("evaluatorTeam");


        changeIndicator();


        previous_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PI", "Previous Indicator Pressed");
                if (current_indicator == 0) {
                    Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
                } else {
                    int nextAmbit=indicators.get(current_indicator-1).getIdAmbit();
                    background.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
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
            public void onClick(View v) {
                Log.d("NI", "Next Indicator Pressed");
                if (current_indicator < num_indicators - 1) {
                    int formerAmbit = indicators.get(current_indicator + 1).getIdAmbit();
                    int formerSubAmbit = indicators.get(current_indicator + 1).getIdSubAmbit();
                    int formerSubSubAmbit = indicators.get(current_indicator + 1).getIdSubSubAmbit();
                    background.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            current_indicator++;
                            changeIndicator();
                            background.setVisibility(View.GONE);

                        }
                    }, 100);
                } else {
                    //???
                    Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                    startActivity(intent);
                }
            }
        });

    }


    public void changeIndicator(){
        TextView indicatorCaption = null;//(TextView) findViewById(R.id.indicator_caption);
        TextView ambitCaption=null;
        TextView subAmbitCaption=null;
        TextView subSubAmbitCaption=null;
        ConstraintLayout layoutWithAmbit=(ConstraintLayout) findViewById(R.id.layoutWithAmbit);
        ConstraintLayout layoutWithSubAmbit=(ConstraintLayout) findViewById(R.id.layoutWithSubAmbit);
        ConstraintLayout layoutWithSubSubAmbit=(ConstraintLayout) findViewById(R.id.layoutWithSubSubAmbit);
        current_ambit=indicators.get(current_indicator).getIdAmbit();
        current_subAmbit=indicators.get(current_indicator).getIdSubAmbit();
        current_subSubAmbit=indicators.get(current_indicator).getIdSubSubAmbit();
        if(current_subAmbit==-1){
            layoutWithAmbit.setVisibility(View.VISIBLE);
            layoutWithSubAmbit.setVisibility(View.GONE);
            layoutWithSubSubAmbit.setVisibility(View.GONE);
            indicatorCaption=(TextView) findViewById(R.id.indicator_caption3);
            ambitCaption=(TextView) findViewById(R.id.ambit_caption3);
        }
        else{
            if(current_subSubAmbit==-1){
                layoutWithAmbit.setVisibility(View.GONE);
                layoutWithSubAmbit.setVisibility(View.VISIBLE);
                layoutWithSubSubAmbit.setVisibility(View.GONE);
                indicatorCaption=(TextView) findViewById(R.id.indicator_caption);
                ambitCaption=(TextView) findViewById(R.id.ambit_caption);
                subAmbitCaption=(TextView) findViewById(R.id.subAmbit_caption);
            }else{
                layoutWithAmbit.setVisibility(View.GONE);
                layoutWithSubAmbit.setVisibility(View.GONE);
                layoutWithSubSubAmbit.setVisibility(View.VISIBLE);
                indicatorCaption=(TextView) findViewById(R.id.indicator_caption2);
                ambitCaption=(TextView) findViewById(R.id.ambit_caption2);
                subAmbitCaption=(TextView) findViewById(R.id.subAmbit_caption2);
                subSubAmbitCaption=(TextView) findViewById(R.id.subSubAmbit_caption);
            }
        }
        List<Integer> aux = null;
        if(current_subAmbit!=-1 && current_subSubAmbit!=-1) {
            aux = new ArrayList<>();
            aux.add(current_subAmbit);
            aux.add(current_ambit);
        }

        Indicator i=indicators.get(current_indicator);
        List<Evidence> evidences=Session.getInstance().getEvidencesByIndicator(current_subSubAmbit,current_subAmbit,current_ambit,current_indicator+1,i.getIndicatorType(),i.getIndicatorVersion(),i.getEvaluationType());

        if (Locale.getDefault().getLanguage().equals("es")) {//Español
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionSpanish());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMER ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish());
                    break;
                case 2:
                    ambitCaption.setText("SEGUNDO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish());
                    break;
                case 3:
                    ambitCaption.setText("TERCER ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish());
                    break;
                case 4:
                    ambitCaption.setText("CUARTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish());
                    break;
                default:
                    ambitCaption.setText("SEXTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionSpanish());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish());
            }

            evidence1.setText("Evidencia 1: " + evidences.get(0).getDescriptionSpanish());
            evidence2.setText("Evidencia 2: " + evidences.get(1).getDescriptionSpanish());
            evidence3.setText("Evidencia 3: " + evidences.get(2).getDescriptionSpanish());
            evidence4.setText("Evidencia 4: " + evidences.get(3).getDescriptionSpanish());
        } else if (Locale.getDefault().getLanguage().equals("fr")) {//Francés
            indicatorCaption.setText("Indicateur " + i.getIdIndicator() + ": " + i.getDescriptionFrench());

            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PREMIÈRE PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench());
                    break;
                case 2:
                    ambitCaption.setText("DEUXIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench());
                    break;
                case 3:
                    ambitCaption.setText("TROISIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench());
                    break;
                case 4:
                    ambitCaption.setText("QUATRIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench());
                    break;
                case 5:
                    ambitCaption.setText("CINQUIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench());
                    break;
                default:
                    ambitCaption.setText("SIXIÈME PORTÉE: " + Session.getInstance().getAmbit(current_ambit).getDescriptionFrench());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionFrench());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionFrench());
            }
            evidence1.setText("Preuve 1: " + evidences.get(0).getDescriptionFrench());
            evidence2.setText("Preuve 2: " + evidences.get(1).getDescriptionFrench());
            evidence3.setText("Preuve 3: " + evidences.get(2).getDescriptionFrench());
            evidence4.setText("Preuve 4: " + evidences.get(3).getDescriptionFrench());
        } else if (Locale.getDefault().getLanguage().equals("eu")) {//Euskera
            indicatorCaption.setText(i.getIdIndicator() + ". adierazlea: " + i.getDescriptionBasque());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("LEHEN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque());
                    break;
                case 2:
                    ambitCaption.setText("BIGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque());
                    break;
                case 3:
                    ambitCaption.setText("HIRUGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque());
                    break;
                case 4:
                    ambitCaption.setText("LAUGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque());
                    break;
                case 5:
                    ambitCaption.setText("BOSTGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque());
                    break;
                default:
                    ambitCaption.setText("SEIGARREN IRISMENA: " + Session.getInstance().getAmbit(current_ambit).getDescriptionBasque());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionSpanish());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionSpanish());
            }

            evidence1.setText("1. froga: " + evidences.get(0).getDescriptionBasque());
            evidence2.setText("2. froga: " + evidences.get(1).getDescriptionBasque());
            evidence3.setText("3. froga: " + evidences.get(2).getDescriptionBasque());
            evidence4.setText("4. froga: " + evidences.get(3).getDescriptionBasque());
        } else if (Locale.getDefault().getLanguage().equals("ca")) {//Catalán
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionCatalan());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMER ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan());
                    break;
                case 2:
                    ambitCaption.setText("SEGON ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan());
                    break;
                case 3:
                    ambitCaption.setText("TERCER ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan());
                    break;
                case 4:
                    ambitCaption.setText("QUART ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan());
                    break;
                case 5:
                    ambitCaption.setText("CINQUÈ ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan());
                    break;
                default:
                    ambitCaption.setText("SISÈ ÀMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionCatalan());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionCatalan());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionCatalan());
            }

            evidence1.setText("Evidència 1: " + evidences.get(0).getDescriptionCatalan());
            evidence2.setText("Evidència 2: " + evidences.get(1).getDescriptionCatalan());
            evidence3.setText("Evidència 3: " + evidences.get(2).getDescriptionCatalan());
            evidence4.setText("Evidència 4: " + evidences.get(3).getDescriptionCatalan());
        } else if (Locale.getDefault().getLanguage().equals("nl")) {//Neerlandés
            indicatorCaption.setText("Indicator " + i.getIdIndicator() + ": " + i.getDescriptionDutch());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("EERSTE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch());
                    break;
                case 2:
                    ambitCaption.setText("TWEEDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch());
                    break;
                case 3:
                    ambitCaption.setText("DERDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch());
                    break;
                case 4:
                    ambitCaption.setText("VIERDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch());
                    break;
                case 5:
                    ambitCaption.setText("VIJFDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch());
                    break;
                default:
                    ambitCaption.setText("ZESDE TOEPASSINGSGEBIED: " + Session.getInstance().getAmbit(current_ambit).getDescriptionDutch());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionDutch());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionDutch());
            }

            evidence1.setText("Bewijs 1: " + evidences.get(0).getDescriptionDutch());
            evidence2.setText("Bewijs 2: " + evidences.get(1).getDescriptionDutch());
            evidence3.setText("Bewijs 3: " + evidences.get(2).getDescriptionDutch());
            evidence4.setText("Bewijs 4: " + evidences.get(3).getDescriptionDutch());
        } else if (Locale.getDefault().getLanguage().equals("gl")) {//Gallego
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionGalician());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMEIRO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician());
                    break;
                case 2:
                    ambitCaption.setText("SEGUNDO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician());
                    break;
                case 3:
                    ambitCaption.setText("TERCEIRO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician());
                    break;
                case 4:
                    ambitCaption.setText("CUARTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician());
                    break;
                default:
                    ambitCaption.setText("SEXTO ÁMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGalician());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGalician());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGalician());
            }


            evidence1.setText("Evidencia 1: " + evidences.get(0).getDescriptionGalician());
            evidence2.setText("Evidencia 2: " + evidences.get(1).getDescriptionGalician());
            evidence3.setText("Evidencia 3: " + evidences.get(2).getDescriptionGalician());
            evidence4.setText("Evidencia 4: " + evidences.get(3).getDescriptionGalician());
        } else if (Locale.getDefault().getLanguage().equals("de")) {//Alemán
            indicatorCaption.setText("Indikator " + i.getIdIndicator() + ": " + i.getDescriptionGerman());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("ERSTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman());
                    break;
                case 2:
                    ambitCaption.setText("ZWEITER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman());
                    break;
                case 3:
                    ambitCaption.setText("DRITTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman());
                    break;
                case 4:
                    ambitCaption.setText("VIERTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman());
                    break;
                case 5:
                    ambitCaption.setText("FÜNFTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman());
                    break;
                default:
                    ambitCaption.setText("SECHSTER UMFANG: " + Session.getInstance().getAmbit(current_ambit).getDescriptionGerman());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionGerman());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionGerman());
            }

            evidence1.setText("Beweis 1: " + evidences.get(0).getDescriptionGerman());
            evidence2.setText("Beweis 2: " + evidences.get(1).getDescriptionGerman());
            evidence3.setText("Beweis 3: " + evidences.get(2).getDescriptionGerman());
            evidence4.setText("Beweis 4: " + evidences.get(3).getDescriptionGerman());
        } else if (Locale.getDefault().getLanguage().equals("it")) {//Italiano
            indicatorCaption.setText("Indicatore " + i.getIdIndicator() + ": " + i.getDescriptionItalian());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian());
                    break;
                case 2:
                    ambitCaption.setText("SECONDO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian());
                    break;
                case 3:
                    ambitCaption.setText("TERZO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian());
                    break;
                case 4:
                    ambitCaption.setText("QUARTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian());
                    break;
                default:
                    ambitCaption.setText("SESTO AMBITO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionItalian());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionItalian());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionItalian());
            }


            evidence1.setText("Prova 1: " + evidences.get(0).getDescriptionItalian());
            evidence2.setText("Prova 2: " + evidences.get(1).getDescriptionItalian());
            evidence3.setText("Prova 3: " + evidences.get(2).getDescriptionItalian());
            evidence4.setText("Prova 4: " + evidences.get(3).getDescriptionItalian());
        } else if (Locale.getDefault().getLanguage().equals("pt")) {//Portugués
            indicatorCaption.setText("Indicador " + i.getIdIndicator() + ": " + i.getDescriptionPortuguese());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("PRIMEIRO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese());
                    break;
                case 2:
                    ambitCaption.setText("SEGUNDO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese());
                    break;
                case 3:
                    ambitCaption.setText("TERCEIRO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese());
                    break;
                case 4:
                    ambitCaption.setText("QUARTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese());
                    break;
                case 5:
                    ambitCaption.setText("QUINTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese());
                    break;
                default:
                    ambitCaption.setText("SEXTO ESCOPO: " + Session.getInstance().getAmbit(current_ambit).getDescriptionPortuguese());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionPortuguese());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionPortuguese());
            }


            evidence1.setText("Evidência 1: " + evidences.get(0).getDescriptionPortuguese());
            evidence2.setText("Evidência 2: " + evidences.get(1).getDescriptionPortuguese());
            evidence3.setText("Evidência 3: " + evidences.get(2).getDescriptionPortuguese());
            evidence4.setText("Evidência 4: " + evidences.get(3).getDescriptionPortuguese());
        } else {//Default
            indicatorCaption.setText("Indicator " + i.getIdIndicator() + ": " + i.getDescriptionEnglish());
            switch (current_ambit) {
                case 1:
                    ambitCaption.setText("FIRST AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish());
                    break;
                case 2:
                    ambitCaption.setText("SECOND AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish());
                    break;
                case 3:
                    ambitCaption.setText("THIRD AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish());
                    break;
                case 4:
                    ambitCaption.setText("FOURTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish());
                    break;
                case 5:
                    ambitCaption.setText("FIFTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish());
                    break;
                default:
                    ambitCaption.setText("SIXTH AMBIT: " + Session.getInstance().getAmbit(current_ambit).getDescriptionEnglish());
                    break;
            }
            if (current_subAmbit != -1) {
                if (current_subSubAmbit != -1) {
                    subSubAmbitCaption.setText(current_ambit + "." + current_subAmbit + "." + current_subSubAmbit + " " + Session.getInstance().getSubSubAmbit(current_subSubAmbit,current_subAmbit,current_ambit).getDescriptionEnglish());
                }
                subAmbitCaption.setText(current_ambit + "." + current_subAmbit + " " + Session.getInstance().getSubAmbit(current_subAmbit,current_ambit).getDescriptionEnglish());
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
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}