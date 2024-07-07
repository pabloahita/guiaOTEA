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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fundacionmiradas.indicatorsevaluation.MainMenu;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import cli.organization.data.Center;
import gui.adapters.CenterAdapter;
import misc.ListCallback;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import session.EditCenterUtil;
import session.FileManager;
import session.Session;

public class SelectToEditCenters extends AppCompatActivity {

    List<Center> centers;

    CenterAdapter adapter;

    Spinner centerSpinner;

    Center center;

    ImageButton edit;

    ImageButton delete;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CentersController.GetAllByOrganization(Session.getInstance().getOrganization(), new ListCallback() {
            @Override
            public void onSuccess(List<JsonObject> data) {
                runOnUiThread(()->{
                    centers=new ArrayList<>();
                    centers.add(new Center(-1,"-","-",-1,"Center of the organization or service","Centro de la organización o servicio","Centre de l'organisation ou du service","Erakundearen edo zerbitzuaren zentroa","Centre de l'organització o servei","Centrum van de organisatie of dienst","Centro da organización ou servizo","Center der Organisation oder des Dienstes","Centro dell'organizzazione o del servizio","Centro da organização ou serviço",-1,"-","-1","-"));
                    for(JsonObject center:data) {
                        int idOrganization = center.getAsJsonPrimitive("idOrganization").getAsInt();
                        String orgType = center.getAsJsonPrimitive("orgType").getAsString();
                        String illness = center.getAsJsonPrimitive("illness").getAsString();
                        int idCenter = center.getAsJsonPrimitive("idCenter").getAsInt();
                        String descriptionEnglish = center.getAsJsonPrimitive("descriptionEnglish").getAsString();
                        String descriptionSpanish = center.getAsJsonPrimitive("descriptionSpanish").getAsString();
                        String descriptionFrench = center.getAsJsonPrimitive("descriptionFrench").getAsString();
                        String descriptionBasque = center.getAsJsonPrimitive("descriptionBasque").getAsString();
                        String descriptionCatalan = center.getAsJsonPrimitive("descriptionCatalan").getAsString();
                        String descriptionDutch = center.getAsJsonPrimitive("descriptionDutch").getAsString();
                        String descriptionGalician = center.getAsJsonPrimitive("descriptionGalician").getAsString();
                        String descriptionGerman = center.getAsJsonPrimitive("descriptionGerman").getAsString();
                        String descriptionItalian = center.getAsJsonPrimitive("descriptionItalian").getAsString();
                        String descriptionPortuguese = center.getAsJsonPrimitive("descriptionPortuguese").getAsString();
                        int idAddress = center.getAsJsonPrimitive("idAddress").getAsInt();
                        String telephone = center.getAsJsonPrimitive("telephone").getAsString();
                        String email = center.getAsJsonPrimitive("email").getAsString();
                        String profilePhoto = center.getAsJsonPrimitive("profilePhoto").getAsString();
                        if (idCenter>1) {
                            centers.add(new Center(idOrganization, orgType, illness, idCenter, descriptionEnglish, descriptionSpanish, descriptionFrench, descriptionBasque, descriptionCatalan, descriptionDutch, descriptionGalician, descriptionGerman, descriptionItalian, descriptionPortuguese, idAddress, telephone, email, profilePhoto));
                        }
                    }
                    if(centers.size()>1){

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        setContentView(R.layout.activity_select_to_edit_centers);

                        adapter=new CenterAdapter(SelectToEditCenters.this,centers);


                        centerSpinner=findViewById(R.id.spinner_select_center);

                        centerSpinner.setAdapter(adapter);



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
                                if(center.getIdCenter()!=1) {
                                    EditCenterUtil.createInstance(center);
                                    Intent intent = new Intent(SelectToEditCenters.this, EditCenter.class);
                                    startActivity(intent);
                                }else{
                                    new AlertDialog.Builder(SelectToEditCenters.this)
                                            .setIcon(android.R.drawable.stat_notify_error)
                                            .setTitle(getString(R.string.error))
                                            .setMessage(getString(R.string.please_select_center))
                                            .create()
                                            .show();
                                }
                            }
                        });

                        delete=findViewById(R.id.delete);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(center.getIdCenter()!=-1) {
                                    new AlertDialog.Builder(SelectToEditCenters.this)
                                            .setMessage(R.string.are_you_sure_del_center)
                                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
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
                                                                centers.add(aux);
                                                                runOnUiThread(()->{
                                                                    adapter=new CenterAdapter(SelectToEditCenters.this,centers);
                                                                    centerSpinner.setAdapter(adapter);
                                                                    new AlertDialog.Builder(SelectToEditCenters.this)
                                                                            .setMessage(R.string.center_deleted)
                                                                            .setPositiveButton(R.string.understood,null)
                                                                            .create().show();
                                                                });

                                                            }).start();
                                                        }

                                                        @Override
                                                        public void onError(String errorResponse) {

                                                        }
                                                    });


                                                }
                                            })
                                            .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .create().show();
                                }else{
                                    new AlertDialog.Builder(SelectToEditCenters.this)
                                            .setMessage(getString(R.string.please_select_center))
                                            .create()
                                            .show();
                                }
                            }
                        });



                    }else{
                        Intent intent=new Intent(SelectToEditCenters.this, MainMenu.class);
                        startActivity(intent);
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