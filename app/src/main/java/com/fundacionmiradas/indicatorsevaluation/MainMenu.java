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


import cli.organization.Organization;
import cli.user.User;
import gui.ImageDownloader;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.UsersController;


public class MainMenu extends AppCompatActivity {


    ImageButton addNewIndicatorsTest;

    ImageButton continueIndicatorsTest;

    ImageButton editIndicators;

    ImageButton addNewOrg;

    ImageButton addNewOrgCenter;

    ImageButton addNewEvalTeam;

    ImageButton seeRealizedIndicatorTest;

    ImageButton editOrg;

    ImageButton editUsers;

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ConstraintLayout superuser=findViewById(R.id.superuser);

        ConstraintLayout evaluator=findViewById(R.id.evaluator);

        CardView chargingScreen=findViewById(R.id.chargingScreen);
        
        chargingScreen.setVisibility(View.GONE);

        String userEmail=(String) getIntent().getSerializableExtra("userEmail");

        User user= UsersController.getInstance().Get(userEmail);

        ImageView vistaImgOrg=findViewById(R.id.imgOrg);

        ImageView vistaImgUser=findViewById(R.id.imgUser);

        String userUrlPhoto="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";

        String orgUrlPhoto="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";



        if(user!=null) {
            Organization org = OrganizationsController.getInstance().Get(user.getIdOrganization(), user.getOrganizationType(), user.getIllness());

            if (org != null) {
                TextView first_name = findViewById(R.id.val_fn);
                TextView last_name = findViewById(R.id.val_ln);
                TextView organizationName = findViewById(R.id.val_org);
                TextView email = findViewById(R.id.val_email);

                first_name.setText(user.getFirst_name());
                last_name.setText(user.getLast_name());
                organizationName.setText(org.getNameOrg());
                email.setText(userEmail);

                if(org.getIdOrganization()==1 && org.getOrgType().equals("EVALUATOR") && org.getIllness().equals("AUTISM")){
                    orgUrlPhoto="https://bbmiradas.fundacionmiradas.org/wp-content/uploads/2022/02/fundacion-miradas-1.png";
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
                        editUsers=findViewById(R.id.editUserAdminButton);

                    }
                    else{
                        superuser.setVisibility(View.GONE);
                        evaluator.setVisibility(View.VISIBLE);
                        addNewIndicatorsTest=findViewById(R.id.addNewIndicatorsTestAdminButton);
                        continueIndicatorsTest=findViewById(R.id.continueIndicatorsTestAdminButton);
                        seeRealizedIndicatorTest=findViewById(R.id.seeRealizedIndicatorTestAdminButton);
                    }
                }else{
                    superuser.setVisibility(View.GONE);
                    evaluator.setVisibility(View.GONE);
                }

                Bitmap imgOrg= ImageDownloader.getInstance().downloadImg(orgUrlPhoto);

                Bitmap imgUser= ImageDownloader.getInstance().downloadImg(userUrlPhoto);

                vistaImgOrg.setImageBitmap(imgOrg);

                vistaImgUser.setImageBitmap(imgUser);

                if(addNewIndicatorsTest!=null) {
                    addNewIndicatorsTest.setOnClickListener(v -> {
                        chargingScreen.setVisibility(View.VISIBLE);
                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), gui.SelectToDoIndicatorsEvaluations.class);
                                intent.putExtra("userEmail", userEmail);
                                startActivity(intent);
                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chargingScreen.setVisibility(View.GONE);
                                    }
                                }, 50);
                            }
                        }, 50);

                    });
                }

                if(continueIndicatorsTest!=null) {
                    continueIndicatorsTest.setOnClickListener(v -> {

                    });
                }

                if(editIndicators!=null) {
                    editIndicators.setOnClickListener(v -> {

                    });
                }

                if(addNewOrg!=null) {
                    addNewOrg.setOnClickListener(v -> {
                        chargingScreen.setVisibility(View.VISIBLE);

                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), gui.RegisterOrganization.class);
                                intent.putExtra("userEmail", userEmail);
                                startActivity(intent);

                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chargingScreen.setVisibility(View.GONE);
                                    }
                                }, 50);
                            }
                        }, 50);

                    });
                }

                if(addNewOrgCenter!=null) {
                    addNewOrgCenter.setOnClickListener(v -> {
                        chargingScreen.setVisibility(View.VISIBLE);

                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), gui.RegisterNewCenter.class);
                                intent.putExtra("userEmail", userEmail);
                                //intent.putExtra("org",org);
                                startActivity(intent);

                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chargingScreen.setVisibility(View.GONE);
                                    }
                                }, 50);
                            }
                        }, 50);

                    });
                }

                if(addNewEvalTeam!=null) {
                    addNewEvalTeam.setOnClickListener(v -> {
                        chargingScreen.setVisibility(View.VISIBLE);

                        v.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), gui.RegisterNewEvaluatorTeam.class);
                                intent.putExtra("userEmail", userEmail);
                                startActivity(intent);

                                v.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        chargingScreen.setVisibility(View.GONE);
                                    }
                                }, 50);
                            }
                        }, 50);
                    });
                }

                if(seeRealizedIndicatorTest!=null) {
                    seeRealizedIndicatorTest.setOnClickListener(v -> {

                    });
                }

                if(editOrg!=null) {
                    editOrg.setOnClickListener(v -> {

                    });
                }

                if(editUsers!=null) {
                    editUsers.setOnClickListener(v -> {

                    });
                }
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
