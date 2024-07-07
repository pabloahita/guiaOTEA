package com.fundacionmiradas.indicatorsevaluation;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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
import android.view.LayoutInflater;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Locale;

import cli.organization.Organization;
import cli.user.User;
import gui.ConnectionErrorMessage;
import gui.ProfilePhotoUtil;
import gui.SelectToEditCenters;
import session.AddNewEvalTeamUtil;
import session.SelectToIndicatorsEvaluationUtil;
import session.Session;
import session.StringPasser;


public class MainMenu extends AppCompatActivity{


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

        if(StringPasser.getInstance()!=null && StringPasser.getInstance().getFlag()==1){
            String msg= StringPasser.getInstance().getString();
            StringPasser.removeInstance();

            new AlertDialog.Builder(MainMenu.this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage(Html.fromHtml(msg,0))
                    .setPositiveButton(R.string.understood,null)
                    .create().show();
        }


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

        ActivityResultLauncher<Intent> activitySuccessResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    chargingScreen.setVisibility(View.GONE);
                    if (result.getResultCode() == RESULT_OK) {
                        String msg= StringPasser.getInstance().getString();
                        StringPasser.removeInstance();

                        new AlertDialog.Builder(MainMenu.this)
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setMessage(Html.fromHtml(msg,0))
                                .setPositiveButton(R.string.understood,null)
                                .create().show();
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
                dirEvaluated.setVisibility(View.GONE);
                superuser.setVisibility(View.GONE);
                organization.setVisibility(View.VISIBLE);
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
                        chargingScreen.setVisibility(View.GONE);
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
                    activitySuccessResultLauncher.launch(intent);
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
                    activitySuccessResultLauncher.launch(intent);

                });
            }

            if(addNewOrgCenter!=null) {
                addNewOrgCenter.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), gui.RegisterNewCenter.class);
                    activitySuccessResultLauncher.launch(intent);

                });
            }

            if(addNewEvalTeam!=null) {
                addNewEvalTeam.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    if(AddNewEvalTeamUtil.getInstance()!=null){
                        Intent intent = new Intent(getApplicationContext(), gui.RegisterNewEvaluatorTeam.class);
                        activitySuccessResultLauncher.launch(intent);
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
                        chargingScreen.setVisibility(View.GONE);
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
                    activitySuccessResultLauncher.launch(intent);
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
                    LayoutInflater inflater=getLayoutInflater();
                    View view=inflater.inflate(R.layout.about_me,null);
                    TextView textView=view.findViewById(R.id.textView18);
                    String txt="";
                    if(Locale.getDefault().getLanguage().equals("es")){
                        txt="<ul>" +
                                "<li>Curso 2022-2023 <b>Trabajo de Final de Grado</b></li>" +
                                "<li>Curso 2023-2024 <b>Trabajo de Final de Máster</b></li>" +
                                "<li><b>Universidad de Burgos</b></li>" +
                                "<li><b>Alumno: </b>Pablo Ahíta del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        txt="<ul>" +
                                "<li>Année universitaire 2022-2023 <b>Projet de fin d'études</b></li>" +
                                "<li>Année universitaire 2023-2024 <b>Projet final de Master</b></li>" +
                                "<li><b>Université de Burgos</b></li>" +
                                "<li><b>Étudiant: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        txt="<ul>" +
                                "<li>2022-2023 ikasturtea <b>Gradu Amaierako Proiektua</b></li>" +
                                "<li>2023-2024 ikasturtea <b>Master Amaierako Proiektua</b></li>" +
                                "<li><b>Burgosko Unibertsitatea</b></li>" +
                                "<li><b>Ikaslea: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        txt="<ul>" +
                                "<li>Curs 2022-2023 <b>Treball de Final de Grau</b></li>" +
                                "<li>Curs 2023-2024 <b>Treball de Final de Màster</b></li>" +
                                "<li><b>Universitat de Burgos</b></li>" +
                                "<li><b>Alumne: </b>Pablo Ahíta del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        txt="<ul>" +
                                "<li>Academisch jaar 2022-2023 <b>Afstudeerproject</b></li>" +
                                "<li>Academisch jaar 2023-2024 <b>Master eindproject</b></li>" +
                                "<li><b>Universiteit van Burgos</b></li>" +
                                "<li><b>Leerling: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        txt="<ul>" +
                                "<li>Curso 2022-2023 <b>Traballo Fin de Grao</b></li>" +
                                "<li>Curso 2023-2024 <b>Traballo Fin de Máster</b></li>" +
                                "<li><b>Universidade de Burgos</b></li>" +
                                "<li><b>Estudante: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        txt="<ul>" +
                                "<li>Studienjahr 2022–2023 <b>Abschlussprojekt</b></li>" +
                                "<li>Studienjahr 2023–2024 <b>Master-Abschlussprojekt</b></li>" +
                                "<li><b>Universität Burgos</b></li>" +
                                "<li><b>Student: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        txt="<ul>" +
                                "<li>Anno accademico 2022-2023 <b>Progetto di Laurea</b></li>" +
                                "<li>Anno accademico 2023-2024 <b>Progetto finale del Master</b></li>" +
                                "<li><b>Università di Burgos</b></li>" +
                                "<li><b>Studente: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        txt="<ul>" +
                                "<li>Ano letivo 2022-2023 <b>Projeto final de licenciatura</b></li>" +
                                "<li>Ano letivo 2023-2024 <b>Projeto Final de Mestrado</b></li>" +
                                "<li><b>Universidade de Burgos</b></li>" +
                                "<li><b>Aluno: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }else{
                        txt="<ul>" +
                                "<li>2022-2023 academic year <b>Final Degree Project</b></li>" +
                                "<li>2023-2024 academic year <b>Master's Final Project</b></li>" +
                                "<li><b>University of Burgos</b></li>" +
                                "<li><b>Student: </b>Pablo Ahita del Barrio</li>" +
                                "</ul>";
                    }
                    textView.setText(Html.fromHtml(txt,0));
                    new AlertDialog.Builder(this)
                            .setView(view)
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
