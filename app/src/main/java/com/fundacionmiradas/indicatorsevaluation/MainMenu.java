package com.fundacionmiradas.indicatorsevaluation;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import cli.organization.Organization;
import cli.user.User;
import gui.ConnectionErrorMessage;
import gui.ProfilePhotoUtil;
import gui.SelectToEditCenters;
import session.AddNewEvalTeamUtil;
import session.SelectToIndicatorsEvaluationUtil;
import session.Session;


public class MainMenu extends AppCompatActivity {


    ImageButton addNewIndicatorsTest;

    ImageButton continueIndicatorsTest;

    ImageButton editUser;

    ImageButton editEvaluatorTeam;

    ImageButton aboutMe;

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

        GridLayout dirEvaluated=findViewById(R.id.directorEvaluated);

        GridLayout organization=findViewById(R.id.organization);

        CardView chargingScreen=findViewById(R.id.chargingScreen);
        
        chargingScreen.setVisibility(View.GONE);

        Session session=Session.getInstance();

        User user=session.getUser();

        Organization org=session.getOrganization();

        ImageView vistaImgOrg=findViewById(R.id.imgOrg);

        ImageView vistaImgUser=findViewById(R.id.imgUser);


        ActivityResultLauncher<Intent> activityEditCenterResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_CANCELED) {
                        chargingScreen.setVisibility(View.GONE);
                        new android.app.AlertDialog.Builder(MainMenu.this)
                                .setIcon(android.R.drawable.stat_notify_error)
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.no_center))
                                .create()
                                .show();
                    }
                });

        ActivityResultLauncher<Intent> activityEditEvalTeamResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_CANCELED) {
                        chargingScreen.setVisibility(View.GONE);
                        new android.app.AlertDialog.Builder(MainMenu.this)
                                .setIcon(android.R.drawable.stat_notify_error)
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.no_existant_eval_team))
                                .create()
                                .show();
                    }
                });


        if(user!=null && org!=null) {


            TextView userInfo=findViewById(R.id.userInfo);

            String info="<ul><li><b>"+getString(R.string.first_name_user)+": </b><i>"+user.getFirst_name()+"</i></li><li><b>" +
                    getString(R.string.last_name_user)+": </b><i>"+user.getLast_name()+"</i></li><li><b>"+
                    getString(R.string.users_email)+": </b><i>"+user.getEmailUser()+"</i></li><li><b>"+
                    getString(R.string.evaluated_org)+": </b><i>"+org.getNameOrg()+"</i></li></ul>";

            userInfo.setText(Html.fromHtml(info,0));


            if(user.getUserType().equals("ADMIN")){
                superuser.setVisibility(View.VISIBLE);
                dirEvaluated.setVisibility(View.GONE);
                organization.setVisibility(View.GONE);
                addNewIndicatorsTest=findViewById(R.id.addNewIndicatorsTestAdminButton);
                continueIndicatorsTest=findViewById(R.id.continueIndicatorsTestAdminButton);
                editUser=findViewById(R.id.editUserAdminButton);
                addNewOrg=findViewById(R.id.addNewOrgAdminButton);
                seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestAdminButton);
                aboutMe=findViewById(R.id.aboutMeAdminButton);

            }else if(user.getUserType().equals("DIRECTOR")){
                dirEvaluated.setVisibility(View.VISIBLE);
                superuser.setVisibility(View.GONE);
                organization.setVisibility(View.GONE);
                addNewOrgCenter=findViewById(R.id.addNewOrgCenterDirEvalButton);
                addNewEvalTeam=findViewById(R.id.addNewEvalTeamDirEvalButton);
                seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestDirEvalButton);
                editOrg=findViewById(R.id.editOrgDirEvalButton);
                editOrgCenters=findViewById(R.id.editOrgCenterDirEvalButton);
                editUser=findViewById(R.id.editUserDirEvalButton);
                aboutMe=findViewById(R.id.aboutMeDirEvalButton);
                editEvaluatorTeam=findViewById(R.id.editEvaluatorTeamDirEvalButton);
            }else{
                dirEvaluated.setVisibility(View.VISIBLE);
                superuser.setVisibility(View.GONE);
                organization.setVisibility(View.GONE);
                seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestEvaluatedButton);
                editUser=findViewById(R.id.editUserEvaluatedButton);
                aboutMe=findViewById(R.id.aboutMeEvaluatedButton);
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
                    if(!SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations().isEmpty()) {
                        Intent intent = new Intent(getApplicationContext(), gui.SelectToDoIndicatorsEvaluations.class);
                        startActivity(intent);
                    }else{
                        new AlertDialog.Builder(MainMenu.this)
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.non_existing_evaluated_organization))
                                .create().show();
                    }

                });
            }

            if(continueIndicatorsTest!=null) {
                continueIndicatorsTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    if(!SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations().isEmpty()) {
                        Intent intent = new Intent(getApplicationContext(), gui.SelectToContinueIndicatorsEvaluations.class);
                        startActivity(intent);
                    }else{
                        new AlertDialog.Builder(MainMenu.this)
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.non_existing_evaluated_organization))
                                .create().show();
                    }
                });
            }

            if(editUser!=null) {
                editUser.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.EditUser.class);
                    startActivity(intent);
                });
            }

            if(editEvaluatorTeam!=null) {
                editEvaluatorTeam.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.SelectToEditEvaluatorTeam.class);
                    activityEditEvalTeamResultLauncher.launch(intent);
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
                    if(AddNewEvalTeamUtil.getInstance()!=null){
                        Intent intent = new Intent(getApplicationContext(), gui.RegisterNewEvaluatorTeam.class);
                        startActivity(intent);
                    }

                });
            }

            if(seeRealizedIndicatorTest!=null) {
                seeRealizedIndicatorTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    if(!SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations().isEmpty()) {
                        Intent intent = new Intent(getApplicationContext(), gui.SelectToSeeRealizedIndicatorsEvaluations.class);
                        startActivity(intent);
                    }else{
                        new AlertDialog.Builder(MainMenu.this)
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.non_existing_evaluated_organization))
                                .create().show();
                    }
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
                    Intent intent = new Intent(getApplicationContext(), gui.SelectToEditCenters.class);
                    activityEditCenterResultLauncher.launch(intent);

                });
            }

            if(aboutMe!=null){
                aboutMe.setOnClickListener(v->{
                    new AlertDialog.Builder(this)
                            .setMessage("Hola")
                            .create()
                            .show();
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
                                ProfilePhotoUtil.logout();
                                Session.logout();
                                finishAffinity();
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
