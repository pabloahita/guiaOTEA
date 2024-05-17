package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cli.indicators.Ambit;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvalTypesAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import gui.adapters.OrgsAdapter;
import misc.DateFormatter;
import otea.connection.controller.AmbitsController;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.EvidencesController;
import otea.connection.controller.IndicatorsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.SubAmbitsController;
import otea.connection.controller.SubSubAmbitsController;
import session.Session;

public class SelectToDoIndicatorsEvaluations extends AppCompatActivity {

    List<Center> centers;

    List<Organization> organizations;

    List<EvaluatorTeam> evaluatorTeams;

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

        organizations= new ArrayList<>();
        organizations.addAll(Session.getEvaluatedOrganizations());
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
            Spinner spinnerEvaluatedOrganizationAux=findViewById(R.id.spinner_select_organization_aux);
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
            orgsAdapter[1]=new OrgsAdapter(SelectToDoIndicatorsEvaluations.this, orgsAux);
            orgsAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);

            spinnerEvaluatedOrganization.setAdapter(orgsAdapter[0]);
            spinnerEvaluatedOrganizationAux.setAdapter(orgsAdapter[1]);

            spinnerEvaluatedOrganizationAux.setEnabled(false);
            spinnerEvaluatedOrganizationAux.setAlpha(0.5f);

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

            evaluationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0){
                        evaluationType[0]="";
                        spinnerEvaluatedOrganizationAux.setVisibility(View.VISIBLE);
                        spinnerEvaluatedOrganization.setVisibility(View.GONE);
                        spinnerCenterAux.setVisibility(View.VISIBLE);
                        spinnerCenter.setVisibility(View.GONE);
                        spinnerEvaluatorTeamAux.setVisibility(View.VISIBLE);
                        spinnerEvaluatorTeam.setVisibility(View.GONE);
                    }else{
                        spinnerEvaluatedOrganizationAux.setVisibility(View.GONE);
                        spinnerEvaluatedOrganization.setVisibility(View.VISIBLE);
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

                    evaluatedOrganization[0] = orgsAdapter[0].getItem(position);
                    if(position>0) {
                        centers=Session.getInstance().getCentersByOrganization(evaluatedOrganization[0]);
                        centers.add(0,centerAuxList.get(0));
                        centerAdapter[0] = new CenterAdapter(SelectToDoIndicatorsEvaluations.this, centers);
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
                        evaluatorTeamsAdapter[0] = new EvaluatorTeamsAdapter(SelectToDoIndicatorsEvaluations.this, evaluatorTeams);
                        evaluatorTeamsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        spinnerEvaluatorTeam.setAdapter(evaluatorTeamsAdapter[0]);
                        spinnerEvaluatorTeam.setVisibility(View.VISIBLE);
                        spinnerEvaluatorTeamAux.setVisibility(View.GONE);
                        if(evaluatorTeams.size()==1){
                            new AlertDialog.Builder(SelectToDoIndicatorsEvaluations.this)
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
                    final_background.setVisibility(View.VISIBLE);
                    base.setVisibility(View.GONE);
                    if(evaluationType[0].isEmpty() || evaluatedOrganization[0]==null || evaluatedOrganization[0].getIdOrganization()==-1 || center[0]==null || center[0].getIdCenter()==-1 || evaluatorTeam[0]==null || evaluatorTeam[0].getIdEvaluatorTeam()==-1){
                        final_background.setVisibility(View.GONE);
                        base.setVisibility(View.VISIBLE);
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
                        new AlertDialog.Builder(SelectToDoIndicatorsEvaluations.this)
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
                    }else {
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), gui.DoIndicatorsEvaluation.class);

                                Session.getInstance().setCurrEvaluation(new IndicatorsEvaluation(DateFormatter.formaUtilDateToTimestamp(new Date()), evaluatedOrganization[0].getIdOrganization(), evaluatedOrganization[0].getOrgType(),
                                        evaluatorTeam[0].getIdEvaluatorTeam(), evaluatorTeam[0].getIdEvaluatorOrganization(), evaluatorTeam[0].getOrgTypeEvaluator(), evaluatorTeam[0].getIllness(), evaluatorTeam[0].getIdCenter(),
                                        0, 0, 0, 0,
                                        0, 0, 0, 0,
                                        0, 0, 0, 0, 0,
                                        "", "", "", "",
                                        "", "", "",
                                        "", "", "",
                                        0, evaluationType[0],""));


                                Session.getInstance().GetAllIndicatorsRegsByIndicatorsEvaluation(Session.getInstance().getCurrEvaluation());
                                Session.getInstance().GetAllEvidencesRegsByIndicatorsEvaluation(Session.getInstance().getCurrEvaluation());

                                Session.getInstance().obtainIndicatorsFromDataBase(evaluationType[0]);
                                startActivity(intent);
                            }
                        }, 100);
                    }

                }
            });
        }else{
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
            new AlertDialog.Builder(SelectToDoIndicatorsEvaluations.this)
                    .setTitle(getString(R.string.error))
                    .setMessage(getString(R.string.non_existing_evaluated_organization))
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