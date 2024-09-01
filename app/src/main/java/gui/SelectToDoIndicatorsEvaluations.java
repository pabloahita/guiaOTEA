package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvalTypesAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import gui.adapters.OrgsAdapter;
import misc.DateFormatter;
import misc.ListCallback;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import session.IndicatorsEvaluationUtil;
import session.IndicatorsUtil;
import session.SelectToIndicatorsEvaluationUtil;

public class SelectToDoIndicatorsEvaluations extends AppCompatActivity {

    List<Center> centers;

    List<Organization> organizations;

    List<EvaluatorTeam> evaluatorTeams;

    ProgressBar pbCenter;

    ProgressBar pbEvalTeam;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_do_indicators_evaluations);

        CardView final_background=findViewById(R.id.cardView2);
        ConstraintLayout base=findViewById(R.id.base);

        base.setVisibility(View.VISIBLE);
        final_background.setVisibility(View.GONE);

        organizations= SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations();
        List<String> evaluationTypes=new LinkedList<String>();
        evaluationTypes.add(getString(R.string.eval_type));
        evaluationTypes.add(getString(R.string.complete));
        evaluationTypes.add(getString(R.string.simple));
        EvalTypesAdapter[] evalTypesAdapter=new EvalTypesAdapter[1];
        evalTypesAdapter[0]=new EvalTypesAdapter(SelectToDoIndicatorsEvaluations.this,evaluationTypes);
        evalTypesAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

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
            Spinner evaluationTypeSpinner=findViewById(R.id.spinner_select_eval_type);

            List<Organization> orgsAux=new ArrayList<>();
            orgsAux.add(aux);

            List<Center> centerAuxList=new ArrayList<>();
            centerAuxList.add(new Center(-1,"-","-",-1,"Center of the organization","Centro de la organización","Centre de l'organisation","Erakundearen Zentroa","Centre de l’organització","Centrum van de organisatie","Centro da organización","Zentrum der Organisation","Centro dell'organizzazione","Centro da organização",-1,"-","-1","-"));

            List<EvaluatorTeam> evaluatorTeamAuxList=new ArrayList<>();
            evaluatorTeamAuxList.add(new EvaluatorTeam(-1, -1, getString(R.string.evaluator_team), "-", "-", -1, "-", -1, "-", -1, "-", "-", "-", "-",  "-","-","-","-","-","-","-","-","-","-","-",-1,-1,""));


            OrgsAdapter[] orgsAdapter= new OrgsAdapter[2];
            orgsAdapter[0]=new OrgsAdapter(SelectToDoIndicatorsEvaluations.this, organizations);
            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

            spinnerEvaluatedOrganization.setAdapter(orgsAdapter[0]);


            CenterAdapter[] centerAdapter=new CenterAdapter[2];

            centerAdapter[1]=new CenterAdapter(SelectToDoIndicatorsEvaluations.this, centerAuxList);
            centerAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            spinnerCenterAux.setAdapter(centerAdapter[1]);

            EvaluatorTeamsAdapter[] evaluatorTeamsAdapter= new EvaluatorTeamsAdapter[2];

            evaluatorTeamsAdapter[1]=new EvaluatorTeamsAdapter(SelectToDoIndicatorsEvaluations.this,evaluatorTeamAuxList);
            evaluatorTeamsAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            spinnerEvaluatorTeamAux.setAdapter(evaluatorTeamsAdapter[1]);

            EvalTypesAdapter[] typeAdapter={new EvalTypesAdapter(SelectToDoIndicatorsEvaluations.this,evaluationTypes)};
            typeAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            evaluationTypeSpinner.setAdapter(typeAdapter[0]);

            Organization[] evaluatedOrganization = new Organization[1];

            Center[] center=new Center[1];

            EvaluatorTeam[] evaluatorTeam = new EvaluatorTeam[1];

            String[] evaluationType = new String[1];

            pbCenter=findViewById(R.id.progressBarCenter);
            pbEvalTeam=findViewById(R.id.progressBarEvalTeam);

            evaluationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0){
                        evaluationType[0]="";
                        spinnerCenterAux.setVisibility(View.VISIBLE);
                        spinnerCenter.setVisibility(View.GONE);
                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                    }else{
                        if(position==1){
                            evaluationType[0]="COMPLETE";
                        }else{
                            evaluationType[0]="SIMPLE";
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerEvaluatedOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(spinnerCenter.getVisibility()==View.VISIBLE){
                        spinnerCenter.setVisibility(View.GONE);
                        spinnerCenterAux.setVisibility(View.VISIBLE);
                    }
                    if(spinnerEvaluatorTeam.getVisibility()==View.VISIBLE){
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                    }

                    evaluatedOrganization[0] = orgsAdapter[0].getItem(position);
                    if(position>0) {
                        pbCenter.setVisibility(View.VISIBLE);
                        CentersController.GetAllByOrganizationASync(evaluatedOrganization[0],new ListCallback() {
                            @Override
                            public void onSuccess(List<JsonObject> data) {
                                runOnUiThread(()->{
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
                                    centerAdapter[0] = new CenterAdapter(SelectToDoIndicatorsEvaluations.this, centers);
                                    centerAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                                    spinnerCenter.setAdapter(centerAdapter[0]);
                                    spinnerCenter.setVisibility(View.VISIBLE);
                                    spinnerCenterAux.setVisibility(View.GONE);
                                    pbCenter.setVisibility(View.GONE);
                                });

                            }

                            @Override
                            public void onError(String errorResponse) {
                                runOnUiThread(() -> {
                                    // Manejar el error aquí, como mostrar un mensaje al usuario
                                });
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
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                    if(spinnerEvaluatorTeam.getVisibility()==View.VISIBLE){
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                    }
                    center[0] = centerAdapter[0].getItem(position);
                    if (position > 0) {
                        pbEvalTeam.setVisibility(View.VISIBLE);
                        EvaluatorTeamsController.GetAllByCenter(center[0].getIdOrganization(), center[0].getOrgType(), center[0].getIdCenter(), center[0].getIllness(), new ListCallback() {
                            @Override
                            public void onSuccess(List<JsonObject> data) {
                                runOnUiThread(() -> {
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
                                    evaluatorTeamsAdapter[0] = new EvaluatorTeamsAdapter(SelectToDoIndicatorsEvaluations.this, evaluatorTeams);
                                    evaluatorTeamsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                                    spinnerEvaluatorTeam.setAdapter(evaluatorTeamsAdapter[0]);
                                    spinnerEvaluatorTeam.setVisibility(View.VISIBLE);
                                    spinnerEvaluatorTeamAux.setVisibility(View.GONE);
                                    pbEvalTeam.setVisibility(View.GONE);
                                    if (evaluatorTeams.size() == 1) {
                                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                                        new AwesomeWarningDialog(SelectToDoIndicatorsEvaluations.this)
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
                            }

                            @Override
                            public void onError(String errorResponse) {
                                runOnUiThread(() -> {
                                    // Manejar el error aquí, como mostrar un mensaje al usuario
                                });
                            }
                        });
                    } else {
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                        spinnerEvaluatorTeamAux.setAlpha(0.5f);
                        spinnerEvaluatorTeamAux.setEnabled(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinnerEvaluatorTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    evaluatorTeam[0] = evaluatorTeamsAdapter[0].getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            ImageButton button=findViewById(R.id.start);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //final_background.setVisibility(View.VISIBLE);
                    //base.setVisibility(View.GONE);


                    AwesomeProgressDialog dialog=new AwesomeProgressDialog(SelectToDoIndicatorsEvaluations.this)
                            .setTitle(R.string.loading_indicators)
                            .setMessage(R.string.please_wait)
                            .setColoredCircle(R.color.miradas_color)
                            .setCancelable(false);
                    dialog.show();
                    if(evaluationType[0].isEmpty() || evaluatedOrganization[0]==null || evaluatedOrganization[0].getIdOrganization()==-1 || center[0]==null || center[0].getIdCenter()==-1 || evaluatorTeam[0]==null || evaluatorTeam[0].getIdEvaluatorTeam()==-1){
                        //final_background.setVisibility(View.GONE);
                        //base.setVisibility(View.VISIBLE);
                        dialog.hide();
                        String msg="<ul>";
                        int numErrors=0;
                        if(evaluationType[0].isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_select_eval_type)+"</b></li>";
                        }
                        if(evaluatedOrganization[0]==null || evaluatedOrganization[0].getIdOrganization()==-1){
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
                        msg+="</ul>";
                        int idTitle=-1;
                        if(numErrors>1){
                            idTitle=R.string.errors;
                        }
                        else{
                            idTitle=R.string.error;
                        }

                        new AwesomeErrorDialog(SelectToDoIndicatorsEvaluations.this)
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
                    }else {
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), gui.DoIndicatorsEvaluation.class);

                                IndicatorsEvaluationUtil.createInstance(evaluatedOrganization[0],center[0],evaluatorTeam[0],new IndicatorsEvaluation(DateFormatter.formaUtilDateToTimestamp(new Date()), evaluatedOrganization[0].getIdOrganization(), evaluatedOrganization[0].getOrgType(),
                                        evaluatorTeam[0].getIdEvaluatorTeam(), evaluatorTeam[0].getIdEvaluatorOrganization(), evaluatorTeam[0].getOrgTypeEvaluator(), evaluatorTeam[0].getIllness(), evaluatorTeam[0].getIdCenter(),
                                        0, 0, 0, 0,
                                        0, 0, 0, 0,
                                        0, 0, 0, 0, 0,
                                        "", "", "", "",
                                        "", "", "",
                                        "", "", "",
                                        0, evaluationType[0],""));
                                IndicatorsUtil.createInstance(evaluationType[0]);
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
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            SelectToIndicatorsEvaluationUtil.removeInstance();
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}