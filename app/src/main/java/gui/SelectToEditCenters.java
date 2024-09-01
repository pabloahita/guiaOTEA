package gui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import misc.ListCallback;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import session.EditCenterUtil;
import session.FileManager;
import session.SelectToEditCenterUtil;
import session.SelectToEditEvaluatorTeamUtil;
import session.Session;
import session.StringPasser;

public class SelectToEditCenters extends AppCompatActivity {

    List<Center> centers;

    CenterAdapter adapter;

    Spinner centerSpinner;

    Center center;

    ImageButton edit;

    ImageButton delete;

    ConstraintLayout final_background;

    ConstraintLayout base;

    AwesomeProgressDialog chargingDialog;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_select_to_edit_centers);

        centers= SelectToEditCenterUtil.getInstance().getCenters();

        centers.add(0,new Center(-1,"-","-",-1,"Center of the organization or service","Centro de la organización o servicio","Centre de l'organisation ou du service","Erakundearen edo zerbitzuaren zentroa","Centre de l'organització o servei","Centrum van de organisatie of dienst","Centro da organización ou servizo","Center der Organisation oder des Dienstes","Centro dell'organizzazione o del servizio","Centro da organização ou serviço",-1,"-","-1","-"));

        if(centers.size()>1){


            adapter=new CenterAdapter(SelectToEditCenters.this,centers);


            centerSpinner=findViewById(R.id.spinner_select_center);

            centerSpinner.setAdapter(adapter);

            final_background=findViewById(R.id.final_background);

            base=findViewById(R.id.base);

            centerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    center=adapter.getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            edit=findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //final_background.setVisibility(View.VISIBLE);
                    //base.setVisibility(View.GONE);
                    showChargingDialog(R.string.loading_form_to_edit_the_center_or_service);

                    if(center.getIdCenter()!=1) {
                        new Thread(()->{
                            try {
                                Thread.sleep(300);
                                SelectToEditCenterUtil.removeInstance();
                                EditCenterUtil.createInstance(center);
                                runOnUiThread(()->{
                                    Intent intent = new Intent(SelectToEditCenters.this, EditCenter.class);
                                    startActivity(intent);
                                });
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }else{
                        //final_background.setVisibility(View.GONE);
                        //base.setVisibility(View.VISIBLE);
                        chargingDialog.hide();
                        new AwesomeErrorDialog(SelectToEditCenters.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.please_select_center)
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

            delete=findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(center.getIdCenter()!=-1) {
                        new AwesomeInfoDialog(SelectToEditCenters.this)
                                .setTitle(R.string.are_you_sure_del_center)
                                .setMessage(R.string.if_del_center)
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
                                        showChargingDialog(R.string.deleting_center);
                                        new Thread(()->{
                                            List<String> blobs=new ArrayList<>();
                                            EvaluatorTeamsController.GetAllByCenter(center.getIdOrganization(), center.getOrgType(), center.getIdCenter(), center.getIllness(), new ListCallback() {
                                                @Override
                                                public void onSuccess(List<JsonObject> data) {
                                                    for(JsonObject jsonObject:data){
                                                        if(!jsonObject.getAsJsonPrimitive("profilePhoto").getAsString().isEmpty()){
                                                            blobs.add(jsonObject.getAsJsonPrimitive("profilePhoto").getAsString());
                                                        }
                                                    }
                                                    if(!center.getProfilePhoto().isEmpty() && center.getProfilePhoto().startsWith("CENTER")){
                                                        blobs.add(center.getProfilePhoto());
                                                    }
                                                    if(!blobs.isEmpty()) {
                                                        FileManager.deleteBlobsAsync(blobs);
                                                    }
                                                    new Thread(()->{
                                                        CentersController.Delete(center.getIdOrganization(),center.getOrgType(),center.getIllness(),center.getIdCenter());

                                                        centers.remove(center);
                                                        Center aux=centers.get(0);
                                                        centers.remove(0);
                                                        SelectToEditCenterUtil.getInstance().setCenters(centers);
                                                        centers.add(aux);
                                                        runOnUiThread(()->{
                                                            adapter=new CenterAdapter(SelectToEditCenters.this,centers);
                                                            centerSpinner.setAdapter(adapter);
                                                            chargingDialog.hide();
                                                            new AwesomeSuccessDialog(SelectToEditCenters.this)
                                                                    .setTitle(R.string.center_deleted)
                                                                    .setMessage(R.string.you_can_choose_another_center)
                                                                    .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                                                                    .setCancelable(false)
                                                                    .setPositiveButtonText(getString(R.string.understood))
                                                                    .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                                                                    .setPositiveButtonTextColor(R.color.white)
                                                                    .setPositiveButtonClick(new Closure() {
                                                                        @Override
                                                                        public void exec() {
                                                                            if(centers.size()==1){
                                                                                SelectToEditCenterUtil.removeInstance();
                                                                                Intent intent=new Intent(getApplicationContext(),MainMenu.class);
                                                                                setResult(RESULT_FIRST_USER,intent);
                                                                                finish();
                                                                            }
                                                                        }
                                                                    })
                                                                    .show();
                                                            //final_background.setVisibility(View.GONE);
                                                            //base.setVisibility(View.VISIBLE);
                                                        });

                                                    }).start();
                                                }


                                                @Override
                                                public void onError(String errorResponse) {

                                                }
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
                    }else{
                        new AwesomeErrorDialog(SelectToEditCenters.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.please_select_center)
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
            Intent intent=new Intent(SelectToEditCenters.this, MainMenu.class);
            startActivity(intent);
            finish();
        }

    }

    private void showChargingDialog(int idTitle){
        chargingDialog=new AwesomeProgressDialog(SelectToEditCenters.this)
                .setTitle(idTitle)
                .setMessage(R.string.please_wait)
                .setColoredCircle(R.color.miradas_color)
                .setCancelable(false);
        chargingDialog.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            SelectToEditCenterUtil.removeInstance();
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}