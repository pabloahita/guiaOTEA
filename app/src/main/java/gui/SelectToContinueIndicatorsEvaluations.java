package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import gui.adapters.IndicatorsEvaluationAdapter;
import gui.adapters.OrgsAdapter;
import misc.ListCallback;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.IndicatorsEvaluationsController;
import session.IndicatorsEvaluationRegsUtil;
import session.IndicatorsEvaluationUtil;
import session.IndicatorsUtil;
import session.SelectToIndicatorsEvaluationUtil;

public class SelectToContinueIndicatorsEvaluations extends AppCompatActivity {

    List<Center> centers;

    List<EvaluatorTeam> evaluatorTeams;

    List<Organization> organizations;

    List<IndicatorsEvaluation> indicatorsEvaluations;

    ConstraintLayout base;

    ImageButton button;

    ProgressBar pbCenter;

    ProgressBar pbEvaluatorTeam;

    ProgressBar pbIndicatorsEvaluation;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_continue_indicators_evaluations);

        base=findViewById(R.id.base);
        base.setVisibility(View.VISIBLE);
        button=findViewById(R.id.start);

        pbCenter=findViewById(R.id.progressBarCenter);
        pbEvaluatorTeam=findViewById(R.id.progressBarEvalTeam);
        pbIndicatorsEvaluation=findViewById(R.id.progressBarIndEval);


        CardView final_background=findViewById(R.id.cardView2);

        organizations= new ArrayList<>();

        final_background.setVisibility(View.GONE);

        organizations.addAll(SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations());

        if(!organizations.isEmpty()){
            Organization aux=new Organization(-1,"-","-",getString(R.string.evaluated_org),-1,"-","-","-","-","-","-","-","-","-","-","-","-","-");
            if(organizations.get(0).getIdOrganization()!=-1) {
                organizations.add(0,aux);
            }
            Spinner spinnerEvaluatedOrganization=findViewById(R.id.spinner_select_organization);
            Spinner spinnerCenter=findViewById(R.id.spinner_select_center);
            Spinner spinnerCenterAux=findViewById(R.id.spinner_select_center_aux);
            Spinner spinnerEvaluatorTeam=findViewById(R.id.spinner_select_eval_team);
            Spinner spinnerEvaluatorTeamAux=findViewById(R.id.spinner_select_eval_team_aux);
            Spinner spinnerIndicatorsEvaluation=findViewById(R.id.spinner_select_ind_eval);
            Spinner spinnerIndicatorsEvaluationAux=findViewById(R.id.spinner_select_ind_eval_aux);

            List<Center> centerAuxList=new ArrayList<>();

            centerAuxList.add(0,new Center(-1,"-","-",-1,"Center or service of the organization","Centro o servicio de la organización","Centre ou service de l'organisation","Erakundearen edo zerbitzuaren zentroa","Centre o servei de l'organització","Centrum of dienst van de organisatie","Centro ou servizo da organización","Center oder Dienstes der Organisation","Centro o servizio dell'organizzazione","Centro ou serviço da organização",-1,"-","-1","-"));


            List<EvaluatorTeam> evaluatorTeamAuxList=new ArrayList<>();
            evaluatorTeamAuxList.add(new EvaluatorTeam(-1, -1, getString(R.string.evaluator_team), "-", "-", -1, "-", -1, "-", -1, "-", "-", "-", "-",  "-","-","-","-","-","-","-","-","-","-","-",-1,-1,"-"));

            List<IndicatorsEvaluation> indicatorsEvaluationsAuxList=new ArrayList<>();
            indicatorsEvaluationsAuxList.add(new IndicatorsEvaluation(-1,-1,"",-1, -1, "", "", -1,
            -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1,
            "", "", "", "", "", "", "", "", "", "", -1, getString(R.string.indicators_evaluation),""));

            OrgsAdapter[] orgsAdapter= new OrgsAdapter[1];
            orgsAdapter[0]=new OrgsAdapter(SelectToContinueIndicatorsEvaluations.this, organizations);
            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

            spinnerEvaluatedOrganization.setAdapter(orgsAdapter[0]);

            CenterAdapter[] centerAdapter=new CenterAdapter[2];

            centerAdapter[1]=new CenterAdapter(SelectToContinueIndicatorsEvaluations.this, centerAuxList);
            centerAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            spinnerCenterAux.setAdapter(centerAdapter[1]);

            EvaluatorTeamsAdapter[] evaluatorTeamsAdapter= new EvaluatorTeamsAdapter[2];

            evaluatorTeamsAdapter[1]=new EvaluatorTeamsAdapter(SelectToContinueIndicatorsEvaluations.this,evaluatorTeamAuxList);
            evaluatorTeamsAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            spinnerEvaluatorTeamAux.setAdapter(evaluatorTeamsAdapter[1]);

            IndicatorsEvaluationAdapter[] indicatorsEvaluationAdapter=new IndicatorsEvaluationAdapter[2];

            indicatorsEvaluationAdapter[1]=new IndicatorsEvaluationAdapter(SelectToContinueIndicatorsEvaluations.this,indicatorsEvaluationsAuxList);
            indicatorsEvaluationAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            spinnerIndicatorsEvaluationAux.setAdapter(indicatorsEvaluationAdapter[1]);

            Organization[] evaluatedOrganization = new Organization[1];

            Center[] center=new Center[1];

            EvaluatorTeam[] evaluatorTeam=new EvaluatorTeam[1];

            IndicatorsEvaluation[] indicatorsEvaluation = new IndicatorsEvaluation[1];


            spinnerEvaluatedOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(spinnerCenter.getVisibility()==View.VISIBLE){
                        spinnerCenter.setVisibility(View.GONE);
                        spinnerCenterAux.setVisibility(View.VISIBLE);
                    }

                    if(spinnerEvaluatorTeam.getVisibility()==View.VISIBLE){
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                        spinnerEvaluatorTeam.setVisibility(View.VISIBLE);
                    }

                    if(spinnerIndicatorsEvaluation.getVisibility()==View.VISIBLE){
                        spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                        spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                    }

                    evaluatedOrganization[0] = orgsAdapter[0].getItem(position);
                    if(position>0) {
                        pbCenter.setVisibility(View.VISIBLE);
                        CentersController.GetAllByOrganizationASync(evaluatedOrganization[0],new ListCallback() {
                            @Override
                            public void onSuccess(List<JsonObject> data) {
                                new Thread(()->{
                                    centers=new ArrayList<>();
                                    centers.add(centerAuxList.get(0));
                                    for(JsonObject center:data) {
                                        int idOrganization = center.getAsJsonPrimitive("idOrganization").getAsInt();
                                        String orgType = center.getAsJsonPrimitive("orgType").getAsString();
                                        String illness = center.getAsJsonPrimitive("illness").getAsString();
                                        int idCenter = center.getAsJsonPrimitive("idCenter").getAsInt();
                                        String description=center.getAsJsonPrimitive("description").getAsString();
                                        String descriptionSpanish="";
                                        String descriptionEnglish="";
                                        String descriptionFrench="";
                                        String descriptionBasque="";
                                        String descriptionCatalan="";
                                        String descriptionDutch="";
                                        String descriptionGalician="";
                                        String descriptionGerman="";
                                        String descriptionItalian="";
                                        String descriptionPortuguese="";
                                        if(Locale.getDefault().getLanguage().equals("es")){
                                            descriptionSpanish=description;
                                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                                            descriptionFrench=description;
                                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                                            descriptionBasque=description;
                                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                                            descriptionCatalan=description;
                                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                                            descriptionDutch=description;
                                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                                            descriptionGalician=description;
                                        }else if(Locale.getDefault().getLanguage().equals("de")){
                                            descriptionGerman=description;
                                        }else if(Locale.getDefault().getLanguage().equals("it")){
                                            descriptionItalian=description;
                                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                                            descriptionPortuguese=description;
                                        }else{
                                            descriptionEnglish=description;
                                        }
                                        int idAddress = center.getAsJsonPrimitive("idAddress").getAsInt();
                                        String telephone = center.getAsJsonPrimitive("telephone").getAsString();
                                        String email = center.getAsJsonPrimitive("email").getAsString();
                                        String profilePhoto = center.getAsJsonPrimitive("profilePhoto").getAsString();
                                        centers.add(new Center(idOrganization, orgType, illness, idCenter, descriptionEnglish, descriptionSpanish, descriptionFrench, descriptionBasque, descriptionCatalan, descriptionDutch, descriptionGalician, descriptionGerman, descriptionItalian, descriptionPortuguese, idAddress, telephone, email, profilePhoto));
                                    }
                                    runOnUiThread(()->{
                                        centerAdapter[0] = new CenterAdapter(SelectToContinueIndicatorsEvaluations.this, centers);
                                        centerAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                                        spinnerCenter.setAdapter(centerAdapter[0]);
                                        spinnerCenter.setVisibility(View.VISIBLE);
                                        spinnerCenterAux.setVisibility(View.GONE);
                                        pbCenter.setVisibility(View.GONE);
                                    });
                                }).start();

                            }

                            @Override
                            public void onError(String errorResponse) {

                            }
                        });
                    }
                    else{
                        spinnerCenter.setVisibility(View.GONE);
                        spinnerCenterAux.setVisibility(View.VISIBLE);
                        spinnerCenterAux.setAlpha(0.5f);
                        spinnerCenterAux.setEnabled(false);
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                        spinnerEvaluatorTeamAux.setAlpha(0.5f);
                        spinnerEvaluatorTeamAux.setEnabled(false);
                        spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                        spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                        spinnerIndicatorsEvaluationAux.setAlpha(0.5f);
                        spinnerIndicatorsEvaluationAux.setEnabled(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


       

        spinnerCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {

                if(spinnerEvaluatorTeam.getVisibility()==View.VISIBLE){
                    spinnerEvaluatorTeam.setVisibility(View.GONE);
                    spinnerEvaluatorTeam.setVisibility(View.VISIBLE);
                }

                if(spinnerIndicatorsEvaluation.getVisibility()==View.VISIBLE){
                    spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                    spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                }
                center[0]=centerAdapter[0].getItem(position);
                if(position>0) {
                    pbEvaluatorTeam.setVisibility(View.VISIBLE);
                    EvaluatorTeamsController.GetAllByCenter(center[0].getIdOrganization(), center[0].getOrgType(), center[0].getIdCenter(), center[0].getIllness(), new ListCallback() {
                        @Override
                        public void onSuccess(List<JsonObject> data) {
                            new Thread(()->{
                                evaluatorTeams = new ArrayList<>();
                                evaluatorTeams.add(0, evaluatorTeamAuxList.get(0));
                                for (JsonObject evalTeam : data) {
                                    int idEvaluatorTeam = evalTeam.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                                    long creationDate = evalTeam.getAsJsonPrimitive("creationDate").getAsLong();
                                    String emailProfessional = evalTeam.getAsJsonPrimitive("emailProfessional").getAsString();
                                    String emailResponsible = evalTeam.getAsJsonPrimitive("emailResponsible").getAsString();
                                    String otherMembers = evalTeam.getAsJsonPrimitive("otherMembers").getAsString();
                                    int idEvaluatorOrganization = evalTeam.getAsJsonPrimitive("idEvaluatorOrganization").getAsInt();
                                    String orgTypeEvaluator = evalTeam.getAsJsonPrimitive("orgTypeEvaluator").getAsString();
                                    int idEvaluatedOrganization = evalTeam.getAsJsonPrimitive("idEvaluatedOrganization").getAsInt();
                                    String orgTypeEvaluated = evalTeam.getAsJsonPrimitive("orgTypeEvaluated").getAsString();
                                    String externalConsultant = evalTeam.getAsJsonPrimitive("externalConsultant").getAsString();
                                    String patientName = evalTeam.getAsJsonPrimitive("patientName").getAsString();
                                    String relativeName = evalTeam.getAsJsonPrimitive("relativeName").getAsString();
                                    String observations=evalTeam.getAsJsonPrimitive("observations").getAsString();
                                    String observationsSpanish="";
                                    String observationsEnglish="";
                                    String observationsFrench="";
                                    String observationsBasque="";
                                    String observationsCatalan="";
                                    String observationsDutch="";
                                    String observationsGalician="";
                                    String observationsGerman="";
                                    String observationsItalian="";
                                    String observationsPortuguese="";
                                    if(Locale.getDefault().getLanguage().equals("es")){
                                        observationsSpanish=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                                        observationsFrench=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                                        observationsBasque=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                                        observationsCatalan=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                                        observationsDutch=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                                        observationsGalician=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("de")){
                                        observationsGerman=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("it")){
                                        observationsItalian=observations;
                                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                                        observationsPortuguese=observations;
                                    }else{
                                        observationsEnglish=observations;
                                    }
                                    String evaluationDates=evalTeam.getAsJsonPrimitive("evaluationDates").getAsString();
                                    int completedEvaluationDates = evalTeam.getAsJsonPrimitive("completedEvaluationDates").getAsInt();
                                    int totalEvaluationDates = evalTeam.getAsJsonPrimitive("totalEvaluationDates").getAsInt();
                                    String profilePhoto = evalTeam.getAsJsonPrimitive("profilePhoto").getAsString();
                                    evaluatorTeams.add(new EvaluatorTeam(idEvaluatorTeam, creationDate, emailProfessional, emailResponsible, otherMembers, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, center[0].getIdCenter(), center[0].getIllness(), externalConsultant, patientName, relativeName, observationsEnglish, observationsSpanish, observationsFrench, observationsBasque, observationsCatalan, observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, evaluationDates, completedEvaluationDates, totalEvaluationDates, profilePhoto));
                                }
                                runOnUiThread(() -> {

                                    evaluatorTeamsAdapter[0] = new EvaluatorTeamsAdapter(SelectToContinueIndicatorsEvaluations.this, evaluatorTeams);
                                    evaluatorTeamsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                                    spinnerEvaluatorTeam.setAdapter(evaluatorTeamsAdapter[0]);
                                    spinnerEvaluatorTeam.setVisibility(View.VISIBLE);
                                    spinnerEvaluatorTeamAux.setVisibility(View.GONE);
                                    pbEvaluatorTeam.setVisibility(View.GONE);
                                    if (evaluatorTeams.size() == 1) {
                                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                                        new AwesomeWarningDialog(SelectToContinueIndicatorsEvaluations.this)
                                                .setTitle(R.string.center_without_eval_teams)
                                                .setMessage(R.string.please_select_other_center_new)
                                                .setColoredCircle(com.aminography.primedatepicker.R.color.yellowA700)
                                                .setCancelable(true).setButtonText(getString(R.string.understood))
                                                .setButtonBackgroundColor(com.aminography.primedatepicker.R.color.yellowA700)
                                                .setButtonText(getString(R.string.understood))
                                                .setWarningButtonClick(new Closure() {
                                                    @Override
                                                    public void exec() {
                                                        // click
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }).start();

                        }

                        @Override
                        public void onError(String errorResponse) {

                        }
                    });
                }else{
                    spinnerEvaluatorTeam.setVisibility(View.GONE);
                    spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                    spinnerEvaluatorTeamAux.setAlpha(0.5f);
                    spinnerEvaluatorTeamAux.setEnabled(false);
                    spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                    spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                    spinnerIndicatorsEvaluationAux.setAlpha(0.5f);
                    spinnerIndicatorsEvaluationAux.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEvaluatorTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerIndicatorsEvaluation.getVisibility()==View.VISIBLE){
                    spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                    spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                }
                evaluatorTeam[0]=evaluatorTeamsAdapter[0].getItem(position);
                if(position>0){
                    pbIndicatorsEvaluation.setVisibility(View.VISIBLE);
                    IndicatorsEvaluationsController.GetNonFinishedByEvaluatorTeam(evaluatorTeam[0].getIdEvaluatorTeam(), evaluatorTeam[0].getIdEvaluatorOrganization(), evaluatorTeam[0].getOrgTypeEvaluator(), evaluatorTeam[0].getIdEvaluatedOrganization(), evaluatorTeam[0].getOrgTypeEvaluated(), evaluatorTeam[0].getIllness(), evaluatorTeam[0].getIdCenter(),new ListCallback() {
                        @Override
                        public void onSuccess(List<JsonObject> data) {
                            new Thread(()->{
                                indicatorsEvaluations=new ArrayList<>();
                                indicatorsEvaluations.add(0,indicatorsEvaluationsAuxList.get(0));
                                for(JsonObject indEval:data){
                                    long evaluationDate=indEval.getAsJsonPrimitive("evaluationDate").getAsLong();
                                    int scorePriorityZeroColourRed=indEval.getAsJsonPrimitive("scorePriorityZeroColourRed").getAsInt();
                                    int scorePriorityZeroColourYellow=indEval.getAsJsonPrimitive("scorePriorityZeroColourYellow").getAsInt();
                                    int scorePriorityZeroColourGreen=indEval.getAsJsonPrimitive("scorePriorityZeroColourGreen").getAsInt();
                                    int scorePriorityOneColourRed=indEval.getAsJsonPrimitive("scorePriorityOneColourRed").getAsInt();
                                    int scorePriorityOneColourYellow=indEval.getAsJsonPrimitive("scorePriorityOneColourYellow").getAsInt();
                                    int scorePriorityOneColourGreen=indEval.getAsJsonPrimitive("scorePriorityOneColourGreen").getAsInt();
                                    int scorePriorityTwoColourRed=indEval.getAsJsonPrimitive("scorePriorityTwoColourRed").getAsInt();
                                    int scorePriorityTwoColourYellow=indEval.getAsJsonPrimitive("scorePriorityTwoColourYellow").getAsInt();
                                    int scorePriorityTwoColourGreen=indEval.getAsJsonPrimitive("scorePriorityTwoColourGreen").getAsInt();
                                    int scorePriorityThreeColourRed=indEval.getAsJsonPrimitive("scorePriorityThreeColourRed").getAsInt();
                                    int scorePriorityThreeColourYellow=indEval.getAsJsonPrimitive("scorePriorityThreeColourYellow").getAsInt();
                                    int scorePriorityThreeColourGreen=indEval.getAsJsonPrimitive("scorePriorityThreeColourGreen").getAsInt();
                                    int totalScore=indEval.getAsJsonPrimitive("totalScore").getAsInt();
                                    String conclusions=indEval.getAsJsonPrimitive("conclusions").getAsString();
                                    String conclusionsSpanish="";
                                    String conclusionsEnglish="";
                                    String conclusionsFrench="";
                                    String conclusionsBasque="";
                                    String conclusionsCatalan="";
                                    String conclusionsDutch="";
                                    String conclusionsGalician="";
                                    String conclusionsGerman="";
                                    String conclusionsItalian="";
                                    String conclusionsPortuguese="";
                                    if(Locale.getDefault().getLanguage().equals("es")){
                                        conclusionsSpanish=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                                        conclusionsFrench=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                                        conclusionsBasque=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                                        conclusionsCatalan=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                                        conclusionsDutch=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                                        conclusionsGalician=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("de")){
                                        conclusionsGerman=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("it")){
                                        conclusionsItalian=conclusions;
                                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                                        conclusionsPortuguese=conclusions;
                                    }else{
                                        conclusionsEnglish=conclusions;
                                    }
                                    int isFinished=indEval.getAsJsonPrimitive("isFinished").getAsInt();
                                    String evaluationType=indEval.getAsJsonPrimitive("evaluationType").getAsString();
                                    String level=indEval.getAsJsonPrimitive("level").getAsString();

                                    indicatorsEvaluations.add(new IndicatorsEvaluation(evaluationDate,evaluatorTeam[0].getIdEvaluatedOrganization(), evaluatorTeam[0].getOrgTypeEvaluated(),evaluatorTeam[0].getIdEvaluatorTeam(), evaluatorTeam[0].getIdEvaluatorOrganization(), evaluatorTeam[0].getOrgTypeEvaluator(), evaluatorTeam[0].getIllness(), evaluatorTeam[0].getIdCenter(),
                                            scorePriorityZeroColourRed, scorePriorityZeroColourYellow, scorePriorityZeroColourGreen,
                                            scorePriorityOneColourRed, scorePriorityOneColourYellow, scorePriorityOneColourGreen,
                                            scorePriorityTwoColourRed, scorePriorityTwoColourYellow, scorePriorityTwoColourGreen,
                                            scorePriorityThreeColourRed, scorePriorityThreeColourYellow, scorePriorityThreeColourGreen, totalScore,
                                            conclusionsSpanish,  conclusionsEnglish,  conclusionsFrench,  conclusionsBasque,  conclusionsCatalan,  conclusionsDutch,  conclusionsGalician,  conclusionsGerman,  conclusionsItalian,  conclusionsPortuguese, isFinished,  evaluationType,  level));
                                }

                                runOnUiThread(()->{

                                    indicatorsEvaluationAdapter[0] = new IndicatorsEvaluationAdapter(SelectToContinueIndicatorsEvaluations.this, indicatorsEvaluations);
                                    indicatorsEvaluationAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                                    spinnerIndicatorsEvaluation.setAdapter(indicatorsEvaluationAdapter[0]);
                                    spinnerIndicatorsEvaluation.setVisibility(View.VISIBLE);
                                    spinnerIndicatorsEvaluationAux.setVisibility(View.GONE);
                                    pbIndicatorsEvaluation.setVisibility(View.GONE);
                                    if(indicatorsEvaluations.size()==1){
                                        spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                                        spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                                        new AwesomeWarningDialog(SelectToContinueIndicatorsEvaluations.this)
                                                .setTitle(R.string.eval_team_without_ind_evals)
                                                .setMessage(R.string.please_select_other_eval_team)
                                                .setColoredCircle(com.aminography.primedatepicker.R.color.yellowA700)
                                                .setCancelable(true).setButtonText(getString(R.string.understood))
                                                .setButtonBackgroundColor(com.aminography.primedatepicker.R.color.yellowA700)
                                                .setButtonText(getString(R.string.understood))
                                                .setWarningButtonClick(new Closure() {
                                                    @Override
                                                    public void exec() {
                                                        // click
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }).start();

                        }

                        @Override
                        public void onError(String errorResponse) {

                        }
                    });

                }else{
                    spinnerIndicatorsEvaluation.setVisibility(View.GONE);
                    spinnerIndicatorsEvaluationAux.setVisibility(View.VISIBLE);
                    spinnerIndicatorsEvaluationAux.setAlpha(0.5f);
                    spinnerIndicatorsEvaluationAux.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
            spinnerIndicatorsEvaluation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    indicatorsEvaluation[0] = indicatorsEvaluationAdapter[0].getItem(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });





            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //base.setVisibility(View.GONE);
                    //final_background.setVisibility(View.VISIBLE);
                    AwesomeProgressDialog dialog=new AwesomeProgressDialog(SelectToContinueIndicatorsEvaluations.this)
                            .setTitle(R.string.loading_indicators)
                            .setMessage(R.string.please_wait)
                            .setColoredCircle(R.color.miradas_color)
                            .setCancelable(false);
                    dialog.show();
                    if(evaluatedOrganization[0].getIdOrganization()==-1 || center[0]==null || center[0].getIdCenter()==-1 || evaluatorTeam[0]==null || evaluatorTeam[0].getIdEvaluatorTeam()==-1 || indicatorsEvaluation[0]==null || indicatorsEvaluation[0].getIdCenter()==-1){
                        //final_background.setVisibility(View.GONE);
                        //base.setVisibility(View.VISIBLE);
                        dialog.hide();
                        String msg="<ul>";
                        int numErrors=0;
                        if(evaluatedOrganization[0].getIdOrganization()==-1){
                            msg+="<li><b>"+getString(R.string.please_select_org)+"</b></li>";
                            numErrors++;
                        }
                        if(center[0]==null || center[0].getIdCenter()==-1){
                            msg+="<li><b>"+getString(R.string.please_select_center)+"</b></li>";
                            numErrors++;
                        }
                        if(evaluatorTeam[0]==null || evaluatorTeam[0].getIdEvaluatorTeam()==-1){
                            msg+="<li><b>"+getString(R.string.please_select_eval_team)+"</b></li>";
                            numErrors++;
                        }
                        if(indicatorsEvaluation[0]==null || indicatorsEvaluation[0].getIdCenter()==-1){
                            msg+="<li><b>"+getString(R.string.please_select_ind_eval)+"</b></li>";
                            numErrors++;
                        }
                        msg+="</ul>";
                        int idTitle=-1;
                        if(numErrors>1){
                            idTitle=R.string.errors;
                        }
                        else{
                            idTitle=R.string.error;
                        }
                        new AwesomeErrorDialog(SelectToContinueIndicatorsEvaluations.this)
                                .setTitle(idTitle)
                                .setMessage(Html.fromHtml(msg,0))
                                .setColoredCircle(com.aminography.primedatepicker.R.color.redA700)
                                .setCancelable(true).setButtonText(getString(R.string.understood))
                                .setButtonBackgroundColor(com.aminography.primedatepicker.R.color.redA700)
                                .setButtonText(getString(R.string.understood))
                                .setErrorButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        // click
                                    }
                                })
                                .show();
                    }
                    else {
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(getApplicationContext(), gui.DoIndicatorsEvaluation.class);

                                IndicatorsEvaluationUtil.createInstance(evaluatedOrganization[0],center[0],evaluatorTeam[0],indicatorsEvaluation[0]);
                                IndicatorsEvaluationRegsUtil.createInstance(indicatorsEvaluation[0]);
                                IndicatorsUtil.createInstance(indicatorsEvaluation[0].getEvaluationType());
                                SelectToIndicatorsEvaluationUtil.removeInstance();
                                startActivity(intent);
                            }

                        }, 100);
                    }

                }
            });
        }else{
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
            new AlertDialog.Builder(SelectToContinueIndicatorsEvaluations.this)
                    .setTitle(getString(R.string.error))
                    .setMessage(Html.fromHtml("<b>"+getString(R.string.non_existing_evaluated_organization_old)+"</b>",0))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}