package com.fundacionmiradas.indicatorsevaluation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.Organization;
import cli.user.User;
import gui.ConnectionErrorMessage;
import gui.ProfilePhotoUtil;
import otea.connection.controller.CountriesController;
import otea.connection.controller.IndicatorsEvaluationsController;
import session.FileManager;
import session.Session;


public class MainMenu extends AppCompatActivity {


    ImageButton addNewIndicatorsTest;

    ImageButton continueIndicatorsTest;

    ImageButton downloadImprovementPlan;

    ImageButton addNewOrg;

    ImageButton addNewOrgCenter;

    ImageButton addNewEvalTeam;

    ImageButton seeRealizedIndicatorTest;

    ImageButton editOrg;

    ImageButton editOrgCenters;

    private AlertDialog dialog;

    Bitmap imgOrg;

    Bitmap imgUser;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GridLayout superuser=findViewById(R.id.superuser);

        GridLayout evaluator=findViewById(R.id.evaluator);

        GridLayout dirEvaluated=findViewById(R.id.directorEvaluated);

        GridLayout evaluated=findViewById(R.id.evaluated);

        CardView chargingScreen=findViewById(R.id.chargingScreen);
        
        chargingScreen.setVisibility(View.GONE);

        Session session=Session.getInstance();

        User user=session.getUser();

        Organization org=session.getOrganization();

        ImageView vistaImgOrg=findViewById(R.id.imgOrg);

        ImageView vistaImgUser=findViewById(R.id.imgUser);


        if(user!=null && org!=null) {

            TextView userInfo=findViewById(R.id.userInfo);

            String info="<ul><li><b>"+getString(R.string.first_name_user)+": </b><i>"+user.getFirst_name()+"</i></li><li><b>" +
                    getString(R.string.last_name_user)+": </b><i>"+user.getLast_name()+"</i></li><li><b>"+
                    getString(R.string.users_email)+": </b><i>"+user.getEmailUser()+"</i></li><li><b>"+
                    getString(R.string.evaluated_org)+": </b><i>"+org.getNameOrg()+"</i></li></ul>";

            userInfo.setText(Html.fromHtml(info,0));

            if(org.getIdOrganization()==1 && org.getOrgType().equals("EVALUATOR") && org.getIllness().equals("AUTISM")){
                dirEvaluated.setVisibility(View.GONE);
                evaluated.setVisibility(View.GONE);
                if(user.getUserType().equals("ADMIN")){
                    superuser.setVisibility(View.VISIBLE);
                    evaluator.setVisibility(View.GONE);
                    addNewIndicatorsTest=findViewById(R.id.addNewIndicatorsTestAdminButton);
                    continueIndicatorsTest=findViewById(R.id.continueIndicatorsTestAdminButton);
                    downloadImprovementPlan=findViewById(R.id.downloadImprovementPlanAdminButton);
                    addNewOrg=findViewById(R.id.addNewOrgAdminButton);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestAdminButton);

                }
                else{
                    superuser.setVisibility(View.GONE);
                    evaluator.setVisibility(View.VISIBLE);
                    addNewIndicatorsTest=findViewById(R.id.addNewIndicatorsTestEvaluatorButton);
                    continueIndicatorsTest=findViewById(R.id.continueIndicatorsTestEvaluatorButton);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestEvaluatorButton);
                }
            }else{
                superuser.setVisibility(View.GONE);
                evaluator.setVisibility(View.GONE);
                if(user.getUserType().equals("DIRECTOR")){
                    dirEvaluated.setVisibility(View.VISIBLE);
                    evaluated.setVisibility(View.GONE);
                    addNewOrgCenter=findViewById(R.id.addNewOrgCenterDirEvalButton);
                    addNewEvalTeam=findViewById(R.id.addNewEvalTeamDirEvalButton);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestDirEvalButton);
                    editOrg=findViewById(R.id.editOrgDirEvalButton);
                    editOrgCenters=findViewById(R.id.editOrgCenterDirEvalButton);
                }else{
                    dirEvaluated.setVisibility(View.GONE);
                    evaluated.setVisibility(View.VISIBLE);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestEvaluatedButton);
                }
            }

            imgOrg = ProfilePhotoUtil.getInstance().getImgOrg();
            if(imgOrg!=null) {
                vistaImgOrg.setImageBitmap(imgOrg);
            }

            imgUser = ProfilePhotoUtil.getInstance().getImgUser();
            if(imgUser!=null) {
                vistaImgUser.setImageBitmap(imgUser);
            }


            if(addNewIndicatorsTest!=null) {
                addNewIndicatorsTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            session.obtainOrgsAndEvalTeams();
                            Intent intent = new Intent(getApplicationContext(), gui.SelectToDoIndicatorsEvaluations.class);
                            startActivity(intent);
                        }
                    }, 200);

                });
            }

            if(continueIndicatorsTest!=null) {
                continueIndicatorsTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    session.obtainOrgsAndEvalTeams();
                    Intent intent = new Intent(getApplicationContext(), gui.SelectToContinueIndicatorsEvaluations.class);
                    startActivity(intent);
                });
            }

            if(downloadImprovementPlan!=null) {
                downloadImprovementPlan.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.DownloadImprovementPlan.class);
                    startActivity(intent);
                });
            }

            if(addNewOrg!=null) {
                addNewOrg.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.RegisterOrganization.class);
                    startActivity(intent);

                });
            }

            if(addNewOrgCenter!=null) {
                addNewOrgCenter.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.RegisterNewCenter.class);
                    startActivity(intent);

                });
            }

            if(addNewEvalTeam!=null) {
                addNewEvalTeam.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    session.obtainUsersAndCenters();
                    Intent intent = new Intent(getApplicationContext(), gui.RegisterNewEvaluatorTeam.class);
                    startActivity(intent);
                });
            }

            if(seeRealizedIndicatorTest!=null) {
                seeRealizedIndicatorTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    session.obtainOrgsAndEvalTeams();
                    Intent intent = new Intent(getApplicationContext(), gui.SelectToSeeRealizedIndicatorsEvaluations.class);
                    startActivity(intent);
                });
            }

            if(editOrg!=null) {
                editOrg.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.EditEvaluatedOrganization.class);
                    startActivity(intent);
                });
            }

            if(editOrgCenters!=null) {
                editOrgCenters.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.EditCenter.class);
                    startActivity(intent);
                });
            }
        }
        else{
            ConnectionErrorMessage.showMsg(this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (dialog == null || !dialog.isShowing()) {
                // Mostrar el cuadro de diálogo solo si no se está mostrando actualmente
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.do_you_want_to_exit_otea))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                return true;
            } else {
                // Si el cuadro de diálogo ya se está mostrando, se cierra
                dialog.dismiss();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }




}
