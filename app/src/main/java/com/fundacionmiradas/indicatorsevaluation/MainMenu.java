package com.fundacionmiradas.indicatorsevaluation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cli.indicators.Ambit;
import cli.indicators.Indicator;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.user.User;
import gui.ImageDownloader;
import gui.data.LoginRepository;
import otea.connection.controller.AmbitsController;
import otea.connection.controller.EvidencesController;
import otea.connection.controller.IndicatorsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.SubAmbitsController;
import otea.connection.controller.SubSubAmbitsController;
import otea.connection.controller.UsersController;
import session.Session;


public class MainMenu extends AppCompatActivity {


    ImageButton addNewIndicatorsTest;

    ImageButton continueIndicatorsTest;

    ImageButton editIndicators;

    ImageButton addNewOrg;

    ImageButton addNewOrgCenter;

    ImageButton addNewEvalTeam;

    ImageButton seeRealizedIndicatorTest;

    ImageButton editOrg;

    ImageButton editOrgCenters;

    private AlertDialog dialog;

    Bitmap imgOrg;

    Bitmap imgUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ConstraintLayout superuser=findViewById(R.id.superuser);

        ConstraintLayout evaluator=findViewById(R.id.evaluator);

        ConstraintLayout dirEvaluated=findViewById(R.id.directorEvaluated);

        ConstraintLayout evaluated=findViewById(R.id.evaluated);

        CardView chargingScreen=findViewById(R.id.chargingScreen);
        
        chargingScreen.setVisibility(View.GONE);

        Session session=Session.getInstance();

        User user=session.getUser();

        Organization org=session.getOrganization();

        ImageView vistaImgOrg=findViewById(R.id.imgOrg);

        ImageView vistaImgUser=findViewById(R.id.imgUser);


        if(user!=null && org!=null) {

            String userUrlPhoto="";
            if(user.getProfilePhoto()!=null){
                userUrlPhoto=user.getProfilePhoto();
            }
            String orgUrlPhoto="";
            if(org.getProfilePhoto()!=null){
                orgUrlPhoto=org.getProfilePhoto();
            }
            TextView first_name = findViewById(R.id.val_fn);
            TextView last_name = findViewById(R.id.val_ln);
            TextView organizationName = findViewById(R.id.val_org);
            TextView email = findViewById(R.id.val_email);

            first_name.setText(user.getFirst_name());
            last_name.setText(user.getLast_name());
            organizationName.setText(org.getNameOrg());
            email.setText(user.getEmailUser());

            if(org.getIdOrganization()==1 && org.getOrgType().equals("EVALUATOR") && org.getIllness().equals("AUTISM")){
                dirEvaluated.setVisibility(View.GONE);
                evaluated.setVisibility(View.GONE);
                if(user.getUserType().equals("ADMIN")){
                    superuser.setVisibility(View.VISIBLE);
                    evaluator.setVisibility(View.GONE);
                    addNewIndicatorsTest=findViewById(R.id.addNewIndicatorsTestAdminButton);
                    continueIndicatorsTest=findViewById(R.id.continueIndicatorsTestAdminButton);
                    editIndicators=findViewById(R.id.editIndicatorsAdminButton);
                    addNewOrg=findViewById(R.id.addNewOrgAdminButton);
                    addNewOrgCenter=findViewById(R.id.addNewOrgCenterAdminButton);
                    addNewEvalTeam=findViewById(R.id.addNewEvalTeamAdminButton);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestAdminButton);
                    editOrg=findViewById(R.id.editOrgAdminButton);
                    editOrgCenters=findViewById(R.id.editOrgCenterAdminButton);

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
                if(user.getUserType().equals("ADMIN")){
                    dirEvaluated.setVisibility(View.VISIBLE);
                    evaluated.setVisibility(View.GONE);
                    addNewOrgCenter=findViewById(R.id.addNewOrgCenterDirEvaluatedButton);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorDirEvaluatedButton);
                    editOrg=findViewById(R.id.editOrgDirEvaluatedButton);
                    editOrgCenters=findViewById(R.id.editOrgCenterDirEvaluatedButton);
                }else{
                    dirEvaluated.setVisibility(View.GONE);
                    evaluated.setVisibility(View.VISIBLE);
                    seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorEvaluatedButton);
                }
            }

            if(!orgUrlPhoto.isEmpty()){
                imgOrg = ImageDownloader.getInstance().downloadImg(orgUrlPhoto);
                vistaImgOrg.setImageBitmap(imgOrg);
            }

            if(!userUrlPhoto.isEmpty()){
                imgUser = ImageDownloader.getInstance().downloadImg(userUrlPhoto);
                vistaImgUser.setImageBitmap(imgUser);
            }



            if(addNewIndicatorsTest!=null) {
                addNewIndicatorsTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.SelectToDoIndicatorsEvaluations.class);
                            startActivity(intent);
                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);

                });
            }

            if(continueIndicatorsTest!=null) {
                continueIndicatorsTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.SelectToContinueIndicatorsEvaluations.class);
                            startActivity(intent);
                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);
                });
            }

            if(editIndicators!=null) {
                editIndicators.setOnClickListener(v -> {

                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.EditIndicators.class);
                            session.obtainIndicatorsFromDataBase();
                            startActivity(intent);
                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);
                });
            }

            if(addNewOrg!=null) {
                addNewOrg.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.RegisterOrganization.class);
                            startActivity(intent);

                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);

                });
            }

            if(addNewOrgCenter!=null) {
                addNewOrgCenter.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.RegisterNewCenter.class);
                            startActivity(intent);

                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);

                });
            }

            if(addNewEvalTeam!=null) {
                addNewEvalTeam.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.RegisterNewEvaluatorTeam.class);
                            startActivity(intent);

                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);
                });
            }

            if(seeRealizedIndicatorTest!=null) {
                seeRealizedIndicatorTest.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.SeeRealizedIndicatorsEvaluations.class);
                            startActivity(intent);
                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);
                });
            }

            if(editOrg!=null) {
                editOrg.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.EditEvaluatedOrganization.class);
                            startActivity(intent);
                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);
                });
            }

            if(editOrgCenters!=null) {
                editOrgCenters.setOnClickListener(v -> {
                    chargingScreen.setVisibility(View.VISIBLE);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), gui.EditCenter.class);
                            startActivity(intent);
                            chargingScreen.setVisibility(View.GONE);
                        }
                    }, 200);
                });
            }
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