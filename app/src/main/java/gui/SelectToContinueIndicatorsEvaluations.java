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
import android.widget.Spinner;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvalTypesAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import gui.adapters.IndicatorsEvaluationAdapter;
import gui.adapters.OrgsAdapter;
import misc.DateFormatter;
import session.Session;

public class SelectToContinueIndicatorsEvaluations extends AppCompatActivity {

    List<Center> centers;

    List<EvaluatorTeam> evaluatorTeams;

    List<Organization> organizations;

    List<IndicatorsEvaluation> indicatorsEvaluations;

    ConstraintLayout base;

    ImageButton button;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_continue_indicators_evaluations);

        base=findViewById(R.id.base);
        base.setVisibility(View.VISIBLE);
        button=findViewById(R.id.start);

        CardView final_background=findViewById(R.id.cardView2);

        final_background.setVisibility(View.GONE);

        organizations= Session.getEvaluatedOrganizations();

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
            centers.add(0,new Center(-1,"-","-",-1,"Center of the organization or service","Centro de la organización o servicio","Centre de l'organisation ou du service","Erakundearen edo zerbitzuaren zentroa","Centre de l'organització o servei","Centrum van de organisatie of dienst","Centro da organización ou servizo","Center der Organisation oder des Dienstes","Centro dell'organizzazione o del servizio","Centro da organização ou serviço",-1,"-","-1","-"));


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

                    evaluatedOrganization[0] = orgsAdapter[0].getItem(position);
                    if(position>0) {
                        centers=Session.getInstance().getCentersByOrganization(evaluatedOrganization[0]);
                        centers.add(0,centerAuxList.get(0));
                        centerAdapter[0] = new CenterAdapter(SelectToContinueIndicatorsEvaluations.this, centers);
                        centerAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        spinnerCenter.setAdapter(centerAdapter[0]);
                        spinnerCenter.setVisibility(View.VISIBLE);
                        spinnerCenterAux.setVisibility(View.GONE);
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
                    center[0]=centerAdapter[0].getItem(position);
                    if(position>0) {
                        evaluatorTeams=Session.getInstance().getEvaluatorTeamsByCenter(center[0]);
                        evaluatorTeams.add(0,evaluatorTeamAuxList.get(0));
                        evaluatorTeamsAdapter[0] = new EvaluatorTeamsAdapter(SelectToContinueIndicatorsEvaluations.this, evaluatorTeams);
                        evaluatorTeamsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        spinnerEvaluatorTeam.setAdapter(evaluatorTeamsAdapter[0]);
                        spinnerEvaluatorTeam.setVisibility(View.VISIBLE);
                        spinnerEvaluatorTeamAux.setVisibility(View.GONE);
                        if(evaluatorTeams.size()==1){
                            new AlertDialog.Builder(SelectToContinueIndicatorsEvaluations.this)
                                    .setTitle(getString(R.string.error))
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setMessage(Html.fromHtml("<b>"+getString(R.string.no_eval_team)+"</b>",0))
                                    .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
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
                    evaluatorTeam[0]=evaluatorTeamsAdapter[0].getItem(position);
                    if(position>0) {
                        indicatorsEvaluations=Session.getInstance().getNonFinishedByEvaluatorTeam(evaluatorTeam[0]);
                        indicatorsEvaluations.add(0,indicatorsEvaluationsAuxList.get(0));
                        indicatorsEvaluationAdapter[0] = new IndicatorsEvaluationAdapter(SelectToContinueIndicatorsEvaluations.this, indicatorsEvaluations);
                        indicatorsEvaluationAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        spinnerIndicatorsEvaluation.setAdapter(indicatorsEvaluationAdapter[0]);
                        spinnerIndicatorsEvaluation.setVisibility(View.VISIBLE);
                        spinnerIndicatorsEvaluationAux.setVisibility(View.GONE);
                        if(indicatorsEvaluations.size()==1){
                            new AlertDialog.Builder(SelectToContinueIndicatorsEvaluations.this)
                                    .setTitle(getString(R.string.error))
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setMessage(Html.fromHtml("<b>"+getString(R.string.no_ind_eval)+"</b>",0))
                                    .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
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
                    base.setVisibility(View.GONE);
                    final_background.setVisibility(View.VISIBLE);
                    if(evaluatedOrganization[0].getIdOrganization()==-1 || center[0]==null || center[0].getIdCenter()==-1 || evaluatorTeam[0]==null || evaluatorTeam[0].getIdEvaluatorTeam()==-1 || indicatorsEvaluation[0]==null || indicatorsEvaluation[0].getIdCenter()==-1){
                        final_background.setVisibility(View.GONE);
                        base.setVisibility(View.VISIBLE);
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
                        new AlertDialog.Builder(SelectToContinueIndicatorsEvaluations.this)
                                .setTitle(getString(idTitle))
                                .setMessage(Html.fromHtml(msg,0))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
                    else {
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(getApplicationContext(), gui.DoIndicatorsEvaluation.class);

                                Session.getInstance().setCurrEvaluation(indicatorsEvaluation[0]);
                                Session.getInstance().GetAllIndicatorsRegsByIndicatorsEvaluation(indicatorsEvaluation[0]);
                                Session.getInstance().GetAllEvidencesRegsByIndicatorsEvaluation(indicatorsEvaluation[0]);


                                Session.getInstance().obtainIndicatorsFromDataBase(indicatorsEvaluation[0].getEvaluationType());
                                startActivity(intent);
                            }

                        }, 100);
                    }

                }
            });
        }else{
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
            new AlertDialog.Builder(SelectToContinueIndicatorsEvaluations.this)
                    .setTitle(getString(R.string.error))
                    .setMessage(Html.fromHtml("<b>"+getString(R.string.non_existing_evaluated_organization)+"</b>",0))
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
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}