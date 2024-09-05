package gui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.Locale;

import cli.organization.Organization;
import cli.user.User;
import session.AddNewEvalTeamUtil;
import session.SelectToEditCenterUtil;
import session.SelectToEditEvaluatorTeamUtil;
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


    Bitmap imgOrg;

    Bitmap imgUser;

    AwesomeProgressDialog chargingScreenDialog;



    GridLayout superuser;

    GridLayout dirEvaluated;

    GridLayout organization;

    User user;

    Organization org;
    Intent[] intentAux;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        superuser=findViewById(R.id.superuser);

        dirEvaluated=findViewById(R.id.directorEvaluated);

        organization=findViewById(R.id.organization);

        //CardView chargingScreen=findViewById(R.id.chargingScreen);
        
        //chargingScreen.setVisibility(View.GONE);

        Session session=Session.getInstance();

        user=session.getUser();

        org=session.getOrganization();

        ImageView vistaImgOrg=findViewById(R.id.imgOrg);

        ImageView vistaImgUser=findViewById(R.id.imgUser);

        intentAux = new Intent[1];



        if(StringPasser.getInstance()!=null && StringPasser.getInstance().getFlag()==1){
            int idTitle=StringPasser.getInstance().getIdTitle();
            int idMsg=StringPasser.getInstance().getIdMsg();
            StringPasser.removeInstance();

            new AwesomeSuccessDialog(this)
                    .setTitle(idTitle)
                    .setMessage(Html.fromHtml(getString(idMsg),0))
                    .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                    .setCancelable(true)
                    .setPositiveButtonText(getString(R.string.understood))
                    .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                    .setPositiveButtonTextColor(R.color.white)
                    .setPositiveButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            //click
                        }
                    })
                    .show();
        }

        ActivityResultLauncher<Intent> activityEditCenterResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_FIRST_USER) {
                        chargingScreenDialog.hide();
                        aboutMe.setEnabled(true);
                        editUser.setEnabled(true);
                        seeRealizedIndicatorTest.setEnabled(true);
                        addNewOrgCenter.setEnabled(true);
                        addNewEvalTeam.setEnabled(true);
                        editOrg.setEnabled(true);
                        editOrgCenters.setEnabled(true);
                        editEvaluatorTeam.setEnabled(true);
                        new AwesomeWarningDialog(MainMenu.this)
                                .setTitle(R.string.no_centers)
                                .setMessage(R.string.choose_other)
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

        ActivityResultLauncher<Intent> activityEditEvalTeamResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_FIRST_USER) {
                        chargingScreenDialog.hide();
                        aboutMe.setEnabled(true);
                        editUser.setEnabled(true);
                        seeRealizedIndicatorTest.setEnabled(true);
                        addNewOrgCenter.setEnabled(true);
                        addNewEvalTeam.setEnabled(true);
                        editOrg.setEnabled(true);
                        editOrgCenters.setEnabled(true);
                        editEvaluatorTeam.setEnabled(true);
                        new AwesomeWarningDialog(MainMenu.this)
                                .setTitle(R.string.no_eval_teams)
                                .setMessage(R.string.choose_other)
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


        ActivityResultLauncher<Intent> activitySuccessResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    chargingScreenDialog.hide();
                    aboutMe.setEnabled(true);
                    editUser.setEnabled(true);
                    seeRealizedIndicatorTest.setEnabled(true);
                    if(user.getUserType().equals("ADMIN")){
                        addNewIndicatorsTest.setEnabled(true);
                        continueIndicatorsTest.setEnabled(true);
                        addNewOrg.setEnabled(true);
                    }
                    else if(user.getUserType().equals("DIRECTOR")){
                        addNewOrgCenter.setEnabled(true);
                        addNewEvalTeam.setEnabled(true);
                        editOrg.setEnabled(true);
                        editOrgCenters.setEnabled(true);
                        editEvaluatorTeam.setEnabled(true);
                    }
                    if (result.getResultCode() == RESULT_OK) {
                        int idTitle=StringPasser.getInstance().getIdTitle();
                        int idMsg=StringPasser.getInstance().getIdMsg();
                        StringPasser.removeInstance();


                        new AwesomeSuccessDialog(this)
                                .setTitle(idTitle)
                                .setMessage(Html.fromHtml(getString(idMsg),0))
                                .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                                .setCancelable(true)
                                .setPositiveButtonText(getString(R.string.understood))
                                .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                                .setPositiveButtonTextColor(R.color.white)
                                .setPositiveButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        //click
                                    }
                                })
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
                    addNewIndicatorsTest.setEnabled(false);
                    continueIndicatorsTest.setEnabled(false);
                    editUser.setEnabled(false);
                    addNewOrg.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    aboutMe.setEnabled(false);
                    showChargingMessage(R.string.loading_list_of_organizations,R.string.please_wait);
                    if(!SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations().isEmpty()) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(300);
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(getApplicationContext(), SelectToDoIndicatorsEvaluations.class);
                                    startActivity(intent);
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }else{
                        chargingScreenDialog.hide();
                        addNewIndicatorsTest.setEnabled(true);
                        continueIndicatorsTest.setEnabled(true);
                        editUser.setEnabled(true);
                        addNewOrg.setEnabled(true);
                        seeRealizedIndicatorTest.setEnabled(true);
                        aboutMe.setEnabled(true);
                        new AwesomeWarningDialog(MainMenu.this)
                                .setTitle(R.string.no_orgs)
                                .setMessage(R.string.please_add_external_org)
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

            if(continueIndicatorsTest!=null) {
                continueIndicatorsTest.setOnClickListener(v -> {
                    addNewIndicatorsTest.setEnabled(false);
                    continueIndicatorsTest.setEnabled(false);
                    editUser.setEnabled(false);
                    addNewOrg.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    aboutMe.setEnabled(false);
                    showChargingMessage(R.string.loading_list_of_organizations,R.string.please_wait);
                    if(!SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations().isEmpty()) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(300);
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(getApplicationContext(), SelectToContinueIndicatorsEvaluations.class);
                                    startActivity(intent);
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }else{
                        chargingScreenDialog.hide();
                        addNewIndicatorsTest.setEnabled(true);
                        continueIndicatorsTest.setEnabled(true);
                        editUser.setEnabled(true);
                        addNewOrg.setEnabled(true);
                        seeRealizedIndicatorTest.setEnabled(true);
                        aboutMe.setEnabled(true);
                        new AwesomeWarningDialog(MainMenu.this)
                                .setTitle(R.string.no_orgs)
                                .setMessage(R.string.please_add_external_org)
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

            if(editUser!=null) {
                editUser.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    if(user.getUserType().equals("ADMIN")){
                        addNewIndicatorsTest.setEnabled(false);
                        continueIndicatorsTest.setEnabled(false);
                        addNewOrg.setEnabled(false);
                    }
                    else if(user.getUserType().equals("DIRECTOR")){
                        addNewOrgCenter.setEnabled(false);
                        addNewEvalTeam.setEnabled(false);
                        editOrg.setEnabled(false);
                        editOrgCenters.setEnabled(false);
                        editEvaluatorTeam.setEnabled(false);
                    }
                    showChargingMessage(R.string.loading_form_to_edit_your_user,R.string.please_wait);
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            runOnUiThread(() -> {
                                Intent intent = new Intent(getApplicationContext(), EditUser.class);
                                startActivity(intent);
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                });
            }

            if(editEvaluatorTeam!=null) {
                editEvaluatorTeam.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    editEvaluatorTeam.setEnabled(false);
                    addNewOrgCenter.setEnabled(false);
                    addNewEvalTeam.setEnabled(false);
                    editOrg.setEnabled(false);
                    editOrgCenters.setEnabled(false);
                    showChargingMessage(R.string.loading_list_of_evaluation_teams,R.string.please_wait);
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            SelectToEditEvaluatorTeamUtil.createInstance();
                            if(!SelectToEditEvaluatorTeamUtil.getInstance().getEvaluatorTeams().isEmpty()){
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(getApplicationContext(), SelectToEditEvaluatorTeam.class);
                                    activityEditEvalTeamResultLauncher.launch(intent);
                                });
                            }else{
                                runOnUiThread(()->{
                                    chargingScreenDialog.hide();
                                    aboutMe.setEnabled(true);
                                    editUser.setEnabled(true);
                                    seeRealizedIndicatorTest.setEnabled(true);
                                    editEvaluatorTeam.setEnabled(true);
                                    addNewOrgCenter.setEnabled(true);
                                    addNewEvalTeam.setEnabled(true);
                                    editOrg.setEnabled(true);
                                    editOrgCenters.setEnabled(true);
                                    new AwesomeWarningDialog(MainMenu.this)
                                            .setTitle(R.string.no_eval_teams)
                                            .setMessage(R.string.choose_other)
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
                                });
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                });
            }

            if(addNewOrg!=null) {
                addNewOrg.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    addNewIndicatorsTest.setEnabled(false);
                    continueIndicatorsTest.setEnabled(false);
                    addNewOrg.setEnabled(false);
                    showChargingMessage(R.string.loading_form_to_add_an_organization,R.string.please_wait);
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            runOnUiThread(() -> {
                                Intent intent = new Intent(getApplicationContext(), RegisterOrganization.class);
                                activitySuccessResultLauncher.launch(intent);
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                });
            }

            if(addNewOrgCenter!=null) {
                addNewOrgCenter.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    addNewOrgCenter.setEnabled(false);
                    addNewEvalTeam.setEnabled(false);
                    editOrg.setEnabled(false);
                    editOrgCenters.setEnabled(false);
                    editEvaluatorTeam.setEnabled(false);
                    showChargingMessage(R.string.loading_form_to_add_a_center_or_service,R.string.please_wait);
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            runOnUiThread(() -> {
                                Intent intent = new Intent(getApplicationContext(), RegisterNewCenter.class);
                                activitySuccessResultLauncher.launch(intent);
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                });
            }

            if(addNewEvalTeam!=null) {
                addNewEvalTeam.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    addNewOrgCenter.setEnabled(false);
                    addNewEvalTeam.setEnabled(false);
                    editOrg.setEnabled(false);
                    editOrgCenters.setEnabled(false);
                    editEvaluatorTeam.setEnabled(false);
                    showChargingMessage(R.string.loading_form_to_add_an_evaluation_team,R.string.please_wait);
                    if(AddNewEvalTeamUtil.getInstance()!=null){
                        new Thread(() -> {
                            try {
                                Thread.sleep(300);
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(getApplicationContext(), RegisterNewEvaluatorTeam.class);
                                    activitySuccessResultLauncher.launch(intent);
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                });
            }

            if(seeRealizedIndicatorTest!=null) {
                seeRealizedIndicatorTest.setOnClickListener(v -> {
                    int title=-1;
                    if(org.getOrganizationType().equals("EVALUATOR")){
                        title=R.string.loading_list_of_organizations;
                    }else{
                        title=R.string.loading_list_of_centers_or_services;
                    }
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    if(user.getUserType().equals("ADMIN")){
                        addNewIndicatorsTest.setEnabled(false);
                        continueIndicatorsTest.setEnabled(false);
                        addNewOrg.setEnabled(false);
                    }
                    else if(user.getUserType().equals("DIRECTOR")){
                        addNewOrgCenter.setEnabled(false);
                        addNewEvalTeam.setEnabled(false);
                        editOrg.setEnabled(false);
                        editOrgCenters.setEnabled(false);
                        editEvaluatorTeam.setEnabled(false);
                    }
                    showChargingMessage(title,R.string.please_wait);
                    if(!SelectToIndicatorsEvaluationUtil.getInstance().getOrganizations().isEmpty()) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(300);
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(getApplicationContext(), SelectToSeeRealizedIndicatorsEvaluations.class);
                                    startActivity(intent);
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }else{
                        chargingScreenDialog.hide();
                        aboutMe.setEnabled(true);
                        editUser.setEnabled(true);
                        seeRealizedIndicatorTest.setEnabled(true);
                        if(user.getUserType().equals("ADMIN")){
                            addNewIndicatorsTest.setEnabled(true);
                            continueIndicatorsTest.setEnabled(true);
                            addNewOrg.setEnabled(true);
                        }
                        else if(user.getUserType().equals("DIRECTOR")){
                            addNewOrgCenter.setEnabled(true);
                            addNewEvalTeam.setEnabled(true);
                            editOrg.setEnabled(true);
                            editOrgCenters.setEnabled(true);
                            editEvaluatorTeam.setEnabled(true);
                        }
                        new AwesomeWarningDialog(MainMenu.this)
                                .setTitle(R.string.no_orgs)
                                .setMessage(R.string.please_add_external_org)
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

            if(editOrg!=null) {
                editOrg.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    addNewOrgCenter.setEnabled(false);
                    addNewEvalTeam.setEnabled(false);
                    editOrg.setEnabled(false);
                    editOrgCenters.setEnabled(false);
                    editEvaluatorTeam.setEnabled(false);
                    showChargingMessage(R.string.loading_form_to_edit_your_organization,R.string.please_wait);
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            runOnUiThread(() -> {
                                Intent intent = new Intent(getApplicationContext(), EditEvaluatedOrganization.class);
                                activitySuccessResultLauncher.launch(intent);
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                });
            }

            if(editOrgCenters!=null) {
                editOrgCenters.setOnClickListener(v -> {
                    aboutMe.setEnabled(false);
                    editUser.setEnabled(false);
                    seeRealizedIndicatorTest.setEnabled(false);
                    addNewOrgCenter.setEnabled(false);
                    addNewEvalTeam.setEnabled(false);
                    editOrg.setEnabled(false);
                    editOrgCenters.setEnabled(false);
                    editEvaluatorTeam.setEnabled(false);
                    showChargingMessage(R.string.loading_list_of_centers_or_services,R.string.please_wait);
                    new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            SelectToEditCenterUtil.createInstance();
                            if(!SelectToEditCenterUtil.getInstance().getCenters().isEmpty()) {
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(getApplicationContext(), SelectToEditCenters.class);
                                    activityEditCenterResultLauncher.launch(intent);
                                });
                            }else{
                                runOnUiThread(()->{
                                    SelectToEditCenterUtil.removeInstance();
                                    chargingScreenDialog.hide();
                                    aboutMe.setEnabled(true);
                                    editUser.setEnabled(true);
                                    seeRealizedIndicatorTest.setEnabled(true);
                                    editEvaluatorTeam.setEnabled(true);
                                    addNewOrgCenter.setEnabled(true);
                                    addNewEvalTeam.setEnabled(true);
                                    editOrg.setEnabled(true);
                                    editOrgCenters.setEnabled(true);
                                    new AwesomeWarningDialog(MainMenu.this)
                                            .setTitle(R.string.no_centers)
                                            .setMessage(R.string.choose_other)
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
                                });
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AwesomeInfoDialog(this)
                    .setTitle(R.string.do_you_want_to_exit_otea)
                    .setMessage(R.string.choose_an_option)
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
                            ProfilePhotoUtil.logout();
                            Session.logout();
                            finishAffinity();
                        }
                    })
                    .setNegativeButtonClick(new Closure() {
                        @Override
                        public void exec() {
                            //click
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    private void showChargingMessage(int title,int msg){
        chargingScreenDialog=new AwesomeProgressDialog(this)
                .setTitle(title)
                .setMessage(msg)
                .setColoredCircle(R.color.miradas_color)
                .setCancelable(false);
        chargingScreenDialog.show();

    }

}
