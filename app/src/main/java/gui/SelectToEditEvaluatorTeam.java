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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import misc.ListCallback;
import otea.connection.controller.EvaluatorTeamsController;
import session.EditEvaluatorTeamUtil;
import session.FileManager;
import session.SelectToEditCenterUtil;
import session.SelectToEditEvaluatorTeamUtil;
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

    AwesomeProgressDialog chargingDialog;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_edit_evaluator_team);


        evaluatorTeams= SelectToEditEvaluatorTeamUtil.getInstance().getEvaluatorTeams();

        evaluatorTeams.add(0,new EvaluatorTeam(-1, -1, getString(R.string.evaluator_team), "-", "-", -1, "-", -1, "-", -1, "-", "-", "-", "-",  "-","-","-","-","-","-","-","-","-","-","-",-1,-1,"-"));


        if(evaluatorTeams.size()>1) {
            //base=findViewById(R.id.base);
            //base.setVisibility(View.VISIBLE);
            //final_background=findViewById(R.id.final_background);
            //final_background.setVisibility(View.GONE);
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
                    //base.setVisibility(View.GONE);
                    //final_background.setVisibility(View.VISIBLE);
                    showChargingDialog(R.string.loading_form_to_edit_the_evaluation_team);
                    if (evaluatorTeam.getIdEvaluatorTeam() != -1) {
                        new Thread(()->{
                            try {
                                Thread.sleep(300);
                                SelectToEditEvaluatorTeamUtil.removeInstance();
                                EditEvaluatorTeamUtil.createInstance(evaluatorTeam);
                                runOnUiThread(()->{
                                    Intent intent = new Intent(SelectToEditEvaluatorTeam.this, EditEvaluatorTeam.class);
                                    startActivity(intent);
                                });
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    } else {
                        //base.setVisibility(View.VISIBLE);
                        //final_background.setVisibility(View.GONE);
                        chargingDialog.hide();
                        new AwesomeErrorDialog(SelectToEditEvaluatorTeam.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.please_select_eval_team)
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
                }
            });

            delete = findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (evaluatorTeam.getIdEvaluatorTeam() != -1) {

                        new AwesomeInfoDialog(SelectToEditEvaluatorTeam.this)
                                .setTitle(R.string.are_you_sure_del_eval_team)
                                .setMessage(R.string.if_del_eval_team)
                                .setColoredCircle(R.color.miradas_color)
                                .setCancelable(true)
                                .setDialogIconAndColor(android.R.drawable.ic_menu_help,R.color.white)
                                .setPositiveButtonText(getString(R.string.yes))
                                .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                                .setPositiveButtonTextColor(R.color.white)
                                .setNegativeButtonText(getString(R.string.no))
                                .setNegativeButtonbackgroundColor(com.aminography.primedatepicker.R.color.redA700)
                                .setNegativeButtonTextColor(R.color.white)
                                .setPositiveButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        showChargingDialog(R.string.deleting_eval_team);
                                        new Thread(()->{
                                            FileManager.deleteBlob("profile-photos",evaluatorTeam.getProfilePhoto());
                                            EvaluatorTeamsController.Delete(evaluatorTeam.getIdEvaluatorTeam(), evaluatorTeam.getIdEvaluatorOrganization(), evaluatorTeam.getOrgTypeEvaluator(), evaluatorTeam.getIdEvaluatedOrganization(), evaluatorTeam.getOrgTypeEvaluated(), evaluatorTeam.getIdCenter(), evaluatorTeam.getIllness());

                                            evaluatorTeams.remove(evaluatorTeam);
                                            EvaluatorTeam aux = evaluatorTeams.get(0);
                                            evaluatorTeams.remove(0);
                                            SelectToEditEvaluatorTeamUtil.getInstance().setEvaluatorTeams(evaluatorTeams);
                                            evaluatorTeams.add(0,aux);
                                            runOnUiThread(()->{
                                                evaluatorTeamsAdapter = new EvaluatorTeamsAdapter(SelectToEditEvaluatorTeam.this, evaluatorTeams);
                                                evaluatorTeamSpinner.setAdapter(evaluatorTeamsAdapter);
                                                evaluatorTeam=evaluatorTeams.get(0);
                                                chargingDialog.hide();

                                                new AwesomeSuccessDialog(SelectToEditEvaluatorTeam.this)
                                                        .setTitle(R.string.eval_team_deleted)
                                                        .setMessage(R.string.you_can_choose_another_eval_team)
                                                        .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                                                        .setCancelable(false)
                                                        .setPositiveButtonText(getString(R.string.understood))
                                                        .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                                                        .setPositiveButtonTextColor(R.color.white)
                                                        .setPositiveButtonClick(new Closure() {
                                                            @Override
                                                            public void exec() {
                                                                if(evaluatorTeams.size()==1){
                                                                    SelectToEditCenterUtil.removeInstance();
                                                                    Intent intent=new Intent(getApplicationContext(),MainMenu.class);
                                                                    setResult(RESULT_FIRST_USER,intent);
                                                                    finish();
                                                                }
                                                            }
                                                        })
                                                        .show();
                                            });
                                        }).start();
                                    }
                                })
                                .setNegativeButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        //click
                                    }
                                })
                                .show();
                    } else {

                        new AwesomeErrorDialog(SelectToEditEvaluatorTeam.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.please_select_eval_team)
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
                }
            });
        }else{
            Intent intent=new Intent(SelectToEditEvaluatorTeam.this, MainMenu.class);
            setResult(RESULT_CANCELED, intent);
            finish();
        }


    }

    private void showChargingDialog(int idTitle){
        chargingDialog=new AwesomeProgressDialog(SelectToEditEvaluatorTeam.this)
                .setTitle(idTitle)
                .setMessage(R.string.please_wait)
                .setColoredCircle(R.color.miradas_color)
                .setCancelable(false);
        chargingDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            SelectToEditEvaluatorTeamUtil.removeInstance();
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}