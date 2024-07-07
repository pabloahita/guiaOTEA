package gui;

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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fundacionmiradas.indicatorsevaluation.MainMenu;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import misc.ListCallback;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import session.EditCenterUtil;
import session.EditEvaluatorTeamUtil;
import session.FileManager;
import session.Session;

public class SelectToEditEvaluatorTeam extends AppCompatActivity {

    List<EvaluatorTeam> evaluatorTeams;



    Spinner centerSpinner;

    Spinner evaluatorTeamSpinner;

    Spinner evaluatorTeamSpinnerAux;

    CenterAdapter centerAdapter;
    
    EvaluatorTeam evaluatorTeam;

    EvaluatorTeamsAdapter evaluatorTeamsAdapter;

    ImageButton edit;

    ImageButton delete;

    ConstraintLayout base;

    ConstraintLayout final_background;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_edit_evaluator_team);




        EvaluatorTeamsController.GetAllByOrganizationAsync(Session.getInstance().getOrganization().getIdOrganization(), Session.getInstance().getOrganization().getOrgType(), Session.getInstance().getOrganization().getIllness(), new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                runOnUiThread(()->{
                    evaluatorTeams=new ArrayList<>();
                    evaluatorTeams.add(new EvaluatorTeam(-1, -1, getString(R.string.evaluator_team), "-", "-", -1, "-", -1, "-", -1, "-", "-", "-", "-",  "-","-","-","-","-","-","-","-","-","-","-",-1,-1,"-"));
                    for(JsonObject evalTeam:data){
                        int idEvaluatorTeam=evalTeam.getAsJsonPrimitive("idEvaluatorTeam").getAsInt();
                        long creationDate=evalTeam.getAsJsonPrimitive("creationDate").getAsLong();
                        String emailProfessional=evalTeam.getAsJsonPrimitive("emailProfessional").getAsString();
                        String emailResponsible=evalTeam.getAsJsonPrimitive("emailResponsible").getAsString();
                        String otherMembers=evalTeam.getAsJsonPrimitive("otherMembers").getAsString();
                        int idEvaluatorOrganization=evalTeam.getAsJsonPrimitive("idEvaluatorOrganization").getAsInt();
                        String orgTypeEvaluator=evalTeam.getAsJsonPrimitive("orgTypeEvaluator").getAsString();
                        int idEvaluatedOrganization=evalTeam.getAsJsonPrimitive("idEvaluatedOrganization").getAsInt();
                        String orgTypeEvaluated=evalTeam.getAsJsonPrimitive("orgTypeEvaluated").getAsString();
                        int idCenter=evalTeam.getAsJsonPrimitive("idCenter").getAsInt();
                        String externalConsultant=evalTeam.getAsJsonPrimitive("externalConsultant").getAsString();
                        String patientName=evalTeam.getAsJsonPrimitive("patientName").getAsString();
                        String relativeName=evalTeam.getAsJsonPrimitive("relativeName").getAsString();
                        String observationsEnglish=evalTeam.getAsJsonPrimitive("observationsEnglish").getAsString();
                        String observationsSpanish=evalTeam.getAsJsonPrimitive("observationsSpanish").getAsString();
                        String observationsFrench=evalTeam.getAsJsonPrimitive("observationsFrench").getAsString();
                        String observationsBasque=evalTeam.getAsJsonPrimitive("observationsBasque").getAsString();
                        String observationsCatalan=evalTeam.getAsJsonPrimitive("observationsCatalan").getAsString();
                        String observationsDutch=evalTeam.getAsJsonPrimitive("observationsDutch").getAsString();
                        String observationsGalician=evalTeam.getAsJsonPrimitive("observationsGalician").getAsString();
                        String observationsGerman=evalTeam.getAsJsonPrimitive("observationsGerman").getAsString();
                        String observationsItalian=evalTeam.getAsJsonPrimitive("observationsItalian").getAsString();
                        String observationsPortuguese=evalTeam.getAsJsonPrimitive("observationsPortuguese").getAsString();
                        String evaluationDates=evalTeam.getAsJsonPrimitive("evaluationDates").getAsString();
                        int completedEvaluationDates=evalTeam.getAsJsonPrimitive("completedEvaluationDates").getAsInt();
                        int totalEvaluationDates=evalTeam.getAsJsonPrimitive("totalEvaluationDates").getAsInt();
                        String profilePhoto=evalTeam.getAsJsonPrimitive("profilePhoto").getAsString();
                        evaluatorTeams.add(new EvaluatorTeam(idEvaluatorTeam, creationDate, emailProfessional, emailResponsible, otherMembers, idEvaluatorOrganization, orgTypeEvaluator, idEvaluatedOrganization, orgTypeEvaluated, idCenter, Session.getInstance().getOrganization().getIllness(), externalConsultant, patientName, relativeName, observationsEnglish, observationsSpanish, observationsFrench, observationsBasque, observationsCatalan, observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, evaluationDates, completedEvaluationDates, totalEvaluationDates, profilePhoto));
                    }
                    if(evaluatorTeams.size()>1) {
                        base=findViewById(R.id.base);
                        base.setVisibility(View.VISIBLE);
                        final_background=findViewById(R.id.final_background);
                        final_background.setVisibility(View.GONE);
                        evaluatorTeamsAdapter = new EvaluatorTeamsAdapter(SelectToEditEvaluatorTeam.this, evaluatorTeams);
                        evaluatorTeamsAdapter.setDropDownViewResource(R.layout.spinner_item_layout);
                        evaluatorTeamSpinner=findViewById(R.id.spinner_select_eval_team);
                        evaluatorTeamSpinner.setAdapter(evaluatorTeamsAdapter);
                        evaluatorTeamSpinner.setVisibility(View.VISIBLE);

                        evaluatorTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                evaluatorTeam = evaluatorTeamsAdapter.getItem(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        edit = findViewById(R.id.edit);
                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                base.setVisibility(View.GONE);
                                final_background.setVisibility(View.VISIBLE);
                                if (evaluatorTeam.getIdEvaluatorTeam() != -1) {
                                    new Thread(()->{
                                        EditEvaluatorTeamUtil.createInstance(evaluatorTeam);
                                        runOnUiThread(()->{
                                            Intent intent = new Intent(SelectToEditEvaluatorTeam.this, EditEvaluatorTeam.class);
                                            startActivity(intent);
                                        });
                                    }).start();
                                } else {
                                    base.setVisibility(View.VISIBLE);
                                    final_background.setVisibility(View.GONE);
                                    new AlertDialog.Builder(SelectToEditEvaluatorTeam.this)
                                            .setIcon(android.R.drawable.stat_notify_error)
                                            .setTitle(getString(R.string.error))
                                            .setMessage(getString(R.string.please_select_eval_team))
                                            .create()
                                            .show();
                                }
                            }
                        });

                        delete = findViewById(R.id.delete);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (evaluatorTeam.getIdEvaluatorTeam() != -1) {
                                    new AlertDialog.Builder(SelectToEditEvaluatorTeam.this)
                                            .setMessage(R.string.are_you_sure_del_eval_team)
                                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    new Thread(()->{
                                                        runOnUiThread(()->{
                                                            base.setVisibility(View.GONE);
                                                            final_background.setVisibility(View.VISIBLE);
                                                        });
                                                        FileManager.deleteBlob("profile-photos",evaluatorTeam.getProfilePhoto());
                                                        EvaluatorTeamsController.Delete(evaluatorTeam.getIdEvaluatorTeam(), evaluatorTeam.getIdEvaluatorOrganization(), evaluatorTeam.getOrgTypeEvaluator(), evaluatorTeam.getIdEvaluatedOrganization(), evaluatorTeam.getOrgTypeEvaluated(), evaluatorTeam.getIdCenter(), evaluatorTeam.getIllness());
                                                        runOnUiThread(()->{
                                                            evaluatorTeams.remove(evaluatorTeam);
                                                            EvaluatorTeam aux = evaluatorTeams.get(0);
                                                            evaluatorTeams.remove(0);
                                                            evaluatorTeams.add(aux);
                                                            evaluatorTeamsAdapter = new EvaluatorTeamsAdapter(SelectToEditEvaluatorTeam.this, evaluatorTeams);
                                                            evaluatorTeamSpinner.setAdapter(evaluatorTeamsAdapter);
                                                            evaluatorTeam=evaluatorTeams.get(0);
                                                            base.setVisibility(View.VISIBLE);
                                                            final_background.setVisibility(View.GONE);
                                                            new AlertDialog.Builder(SelectToEditEvaluatorTeam.this)
                                                                    .setMessage(R.string.eval_team_deleted)
                                                                    .setPositiveButton(R.string.understood,null)
                                                                    .create().show();
                                                        });
                                                    }).start();
                                                }
                                            })
                                            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .create().show();
                                } else {
                                    new AlertDialog.Builder(SelectToEditEvaluatorTeam.this)
                                            .setIcon(android.R.drawable.stat_notify_error)
                                            .setTitle(getString(R.string.error))
                                            .setMessage(getString(R.string.please_select_eval_team))
                                            .create()
                                            .show();
                                }
                            }
                        });
                    }else{
                        Intent intent=new Intent(SelectToEditEvaluatorTeam.this, MainMenu.class);
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                });
            }

            @Override
            public void onError(String errorResponse) {

            }
        });





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