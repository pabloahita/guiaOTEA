package gui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primedatepicker.picker.PrimeDatePicker;
import com.aminography.primedatepicker.picker.callback.MultipleDaysPickCallback;
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;
import com.fundacionmiradas.indicatorsevaluation.R;

;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import gui.adapters.CenterAdapter;
import gui.adapters.OrgsAdapter;
import gui.adapters.UsersAdapter;
import misc.DateFormatter;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.TranslatorController;
import session.FileManager;
import session.Session;

public class RegisterNewEvaluatorTeam extends AppCompatActivity {

    EditText creationDateEditText;

    EditText evaluationDatesEditText;

    List<Organization> evaluatedOrganizations;

    List<User> evaluatorUsers;

    List<User> evaluatedUsers;

    List<String> otherMembers;

    User professional;

    User responsible;

    String consultant;

    List<Center> centers;

    Center centerSelected;

    static int MIN_NUM_EVAL_DATES=3;

    PrimeCalendar creationDate;

    List<PrimeCalendar> evaluationDates;

    boolean checked=false;

    Button imageEvalTeamButton;

    ImageView imageEvalTeam;

    InputStream profilePhotoEvalTeam;

    String imgEvalTeamName;

    ImageButton helpButton;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_evaluator_team);

        evaluatorUsers = Session.getEvaluatorUsers();
        evaluatedUsers=Session.getEvaluatedUsers();

        if (!evaluatedUsers.isEmpty() && !evaluatorUsers.isEmpty()) {

            Spinner centerSpinner = findViewById(R.id.spinner_select_center);
            Spinner responsibleSpinner = findViewById(R.id.spinner_select_responsible);
            Spinner professionalSpinner = findViewById(R.id.spinner_select_professional);

            List<User> userAuxList=new ArrayList<>();
            userAuxList.add(new User("-1","-",getString(R.string.responsible),"-","","",-1,"-","-","-"));
            userAuxList.add(new User("-1","-",getString(R.string.professional),"-","","",-1,"-","-","-"));

            evaluatorUsers.add(0,userAuxList.get(0));
            evaluatedUsers.add(0,userAuxList.get(1));

            otherMembers=new ArrayList<>();

            EditText otherMembers = findViewById(R.id.other_members);

            EditText consultant = findViewById(R.id.consultant);

            EditText patient = findViewById(R.id.patientName);
            EditText relative = findViewById(R.id.relativeName);

            creationDateEditText=findViewById(R.id.creation_date);
            evaluationDatesEditText=findViewById(R.id.eval_dates);

            EditText observations=findViewById(R.id.observations);

            UsersAdapter[] usersAdapter=new UsersAdapter[2];
            CenterAdapter[] centerAdapters=new CenterAdapter[1];
            centers = Session.getInstance().getCentersByOrganization(Session.getInstance().getOrganization());
            centers.add(0,new Center(-1,"-","-",-1,"Center of the organization or service","Centro de la organización o servicio","Centre de l'organisation ou du service","Erakundearen edo zerbitzuaren zentroa","Centre de l'organització o servei","Centrum van de organisatie of dienst","Centro da organización ou servizo","Center der Organisation oder des Dienstes","Centro dell'organizzazione o del servizio","Centro da organização ou serviço",-1,"-","-1","-"));
            centerAdapters[0]=new CenterAdapter(getApplicationContext(),centers);
            centerAdapters[0].setDropDownViewResource(R.layout.spinner_item_layout);
            centerSpinner.setAdapter(centerAdapters[0]);


            usersAdapter[1]=new UsersAdapter(getApplicationContext(),evaluatorUsers);
            usersAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            responsibleSpinner.setAdapter(usersAdapter[1]);

            usersAdapter[0]=new UsersAdapter(getApplicationContext(),evaluatedUsers);
            usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            professionalSpinner.setAdapter(usersAdapter[0]);



            ConstraintLayout finalBackground=findViewById(R.id.final_background);

            ConstraintLayout base=findViewById(R.id.base);

            base.setVisibility(View.VISIBLE);
            finalBackground.setVisibility(View.GONE);

            imageEvalTeamButton=findViewById(R.id.uploadPhoto);
            imageEvalTeam=findViewById(R.id.profilePhoto);



            ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri uri) {
                            // Handle the returned Uri
                            try {
                                profilePhotoEvalTeam = getContentResolver().openInputStream(uri);
                                imageEvalTeam.setImageURI(uri);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

            ActivityResultLauncher<Void> mTakePicture = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                    new ActivityResultCallback<Bitmap>() {
                        @Override
                        public void onActivityResult(Bitmap bitmap) {
                            // Handle the returned Bitmap
                            try {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                                byte[] bitmapdata = bos.toByteArray();
                                bos.close();
                                ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                                profilePhotoEvalTeam = bs;
                                imageEvalTeam.setImageBitmap(bitmap);
                                bs.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

            imageEvalTeamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new androidx.appcompat.app.AlertDialog.Builder(v.getContext())
                            .setTitle(getString(R.string.select_source))
                            .setPositiveButton(getString(R.string.camera), (dialog, which) -> mTakePicture.launch(null))
                            .setNegativeButton(getString(R.string.gallery), (dialog, which) -> mGetContent.launch("image/*"))
                            .show();
                }
            });


            centerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    centerSelected= (Center) parent.getItemAtPosition(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            responsibleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    responsible=(User) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            professionalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    professional=(User) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            patient.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    patient.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        patient.setError(getString(R.string.please_patient_name));
                    }
                    else{
                        patient.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            relative.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    relative.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        relative.setError(getString(R.string.please_relative_name));
                    }
                    else{
                        relative.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            consultant.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    consultant.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        consultant.setError(getString(R.string.please_consultant));
                    }
                    else{
                        consultant.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



            otherMembers.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            creationDateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrimeCalendar today = new CivilCalendar();

                    PrimeDatePicker datePicker = PrimeDatePicker.Companion.dialogWith(today)
                            .pickSingleDay(new SingleDayPickCallback() {
                                @Override
                                public void onSingleDayPicked(PrimeCalendar singleDay) {
                                    creationDate=singleDay;
                                    creationDateEditText.setText(creationDate.getLongDateString().split(", ")[1]);
                                }
                            })
                            .minPossibleDate(today)
                            .build();

                    datePicker.show(getSupportFragmentManager(), "CREATION_DATE");
                }
            });

            evaluationDatesEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrimeCalendar today = new CivilCalendar();

                    if(evaluationDates==null){
                        evaluationDates=new ArrayList<>();
                    }
                    PrimeDatePicker datePicker = PrimeDatePicker.Companion.dialogWith(today)
                            .pickMultipleDays(new MultipleDaysPickCallback() {
                                @Override
                                public void onMultipleDaysPicked(List<PrimeCalendar> list) {
                                    if(!evaluationDates.isEmpty()) {
                                        evaluationDates.clear();
                                    }
                                    evaluationDates.addAll(list);
                                    String text="";
                                    Collections.sort(evaluationDates, new Comparator<PrimeCalendar>() {
                                        @Override
                                        public int compare(PrimeCalendar o1, PrimeCalendar o2) {
                                            if(o1.getTimeInMillis() < o2.getTimeInMillis()){
                                                return -1;
                                            } else if(o1.getTimeInMillis() > o2.getTimeInMillis()){
                                                return 1;
                                            }
                                            return 0;
                                        }
                                    });
                                    if(evaluationDates.size()>=MIN_NUM_EVAL_DATES && evaluationDates.get(evaluationDates.size()-1).getTimeInMillis()-evaluationDates.get(0).getTimeInMillis()<2629800000L){
                                        StringBuilder sb=new StringBuilder();
                                        for(int i=0;i<evaluationDates.size();i++){
                                            sb.append(evaluationDates.get(i).getLongDateString().split(", ")[1]);
                                            if(i<evaluationDates.size()-1){
                                                sb.append(", ");
                                            }
                                        }
                                        text=sb.toString();
                                    }else{
                                        if(!evaluationDates.isEmpty()) {
                                            evaluationDates.clear();
                                        }
                                        String msg="";
                                        if(evaluationDates.size()<MIN_NUM_EVAL_DATES){
                                            msg="<b>"+getString(R.string.must_select_three_eval_dates)+"</b>";
                                        }
                                        else if(evaluationDates.get(evaluationDates.size()-1).getTimeInMillis()-evaluationDates.get(0).getTimeInMillis()>=2629800000L){
                                            msg="<b>"+getString(R.string.difference_between_dates_is_equal_or_greater_than_a_month)+"</b>";
                                        }
                                        new AlertDialog.Builder(RegisterNewEvaluatorTeam.this)
                                                .setTitle(getString(R.string.error))
                                                .setMessage(Html.fromHtml(msg,0))
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .create().show();

                                    }
                                    evaluationDatesEditText.setText(text);

                                }
                            })
                            .initiallyPickedMultipleDays(evaluationDates)
                            .minPossibleDate(today)
                            .build();
                    datePicker.show(getSupportFragmentManager(), "EVALUATION_DATES");
                }
            });

            observations.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String input=charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            helpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg="";
                    if(Locale.getDefault().getLanguage().equals("es")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        msg="";
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        msg="";
                    }else{
                        msg="";
                    }
                    new android.app.AlertDialog.Builder(RegisterNewEvaluatorTeam.this)
                            .setTitle(getString(R.string.help))
                            .setMessage(Html.fromHtml(msg,0))
                            .create().show();
                }
            });

            Button add=findViewById(R.id.add);
            add.setAlpha(0.5f);

            CheckBox acceptLOPD=findViewById(R.id.accept_LOPD);


            acceptLOPD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                          if (isChecked) {
                                                              add.setAlpha(1f);
                                                          } else {
                                                              add.setAlpha(0.5f);
                                                          }
                                                          checked=isChecked;
                                                      }
                                                  }
            );

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    base.setVisibility(View.GONE);
                    finalBackground.setVisibility(View.VISIBLE);
                    if(!userAuxList.contains(professional) && !userAuxList.contains(responsible) && !patient.getText().toString().isEmpty() && !relative.getText().toString().isEmpty() && !creationDateEditText.getText().toString().isEmpty() && !consultant.toString().isEmpty() && checked){
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                                int idEvaluatorTeam= EvaluatorTeamsController.GetAllByOrganization(1,"EVALUATOR","AUTISM").size()+1;


                                long creation_date= creationDate.getTimeInMillis();
                                StringBuilder evaluationDatesStr=new StringBuilder();

                                for(int i=0;i<evaluationDates.size();i++){
                                    evaluationDatesStr.append(evaluationDates.get(i).getTimeInMillis());
                                    if(i<evaluationDates.size()-1){
                                        evaluationDatesStr.append(",");
                                    }
                                }

                                String observationsEnglish="";
                                String observationsSpanish="";
                                String observationsFrench="";
                                String observationsBasque="";
                                String observationsCatalan="";
                                String observationsDutch="";
                                String observationsGalician="";
                                String observationsGerman="";
                                String observationsItalian="";
                                String observationsPortuguese="";

                                String observationsText=observations.getText().toString();

                            if(profilePhotoEvalTeam!=null){
                                imgEvalTeamName="EVALTEAM_"+idEvaluatorTeam+"_"+ centerSelected.getIdCenter()+"_"+centerSelected.getIdOrganization()+"_"+centerSelected.getOrgType()+"_"+centerSelected.getIllness()+".jpg";
                                FileManager.uploadFile(profilePhotoEvalTeam, "profile-photos", imgEvalTeamName);
                                try{
                                    profilePhotoEvalTeam.close();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                            }

                                if(!observationsText.isEmpty()){
                                    List<String> translations=TranslatorController.getInstance().translate(observationsText,Locale.getDefault().getLanguage());
                                    if(Locale.getDefault().getLanguage().equals("es")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=observationsText;
                                        observationsFrench=translations.get(1);
                                        observationsBasque=translations.get(2);
                                        observationsCatalan=translations.get(3);
                                        observationsDutch=translations.get(4);
                                        observationsGalician=translations.get(5);
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=observationsText;
                                        observationsBasque=translations.get(2);
                                        observationsCatalan=translations.get(3);
                                        observationsDutch=translations.get(4);
                                        observationsGalician=translations.get(5);
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=observationsText;
                                        observationsCatalan=translations.get(3);
                                        observationsDutch=translations.get(4);
                                        observationsGalician=translations.get(5);
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=translations.get(3);
                                        observationsCatalan=observationsText;
                                        observationsDutch=translations.get(4);
                                        observationsGalician=translations.get(5);
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=translations.get(3);
                                        observationsCatalan=translations.get(4);
                                        observationsDutch=observationsText;
                                        observationsGalician=translations.get(5);
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=translations.get(3);
                                        observationsCatalan=translations.get(4);
                                        observationsDutch=translations.get(5);
                                        observationsGalician=observationsText;
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("de")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=translations.get(3);
                                        observationsCatalan=translations.get(4);
                                        observationsDutch=translations.get(5);
                                        observationsGalician=translations.get(6);
                                        observationsGerman=observationsText;
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("it")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=translations.get(3);
                                        observationsCatalan=translations.get(4);
                                        observationsDutch=translations.get(5);
                                        observationsGalician=translations.get(6);
                                        observationsGerman=translations.get(7);
                                        observationsItalian=observationsText;
                                        observationsPortuguese=translations.get(8);
                                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                                        observationsEnglish= translations.get(0);
                                        observationsSpanish=translations.get(1);
                                        observationsFrench=translations.get(2);
                                        observationsBasque=translations.get(3);
                                        observationsCatalan=translations.get(4);
                                        observationsDutch=translations.get(5);
                                        observationsGalician=translations.get(6);
                                        observationsGerman=translations.get(7);
                                        observationsItalian=translations.get(8);
                                        observationsPortuguese=observationsText;
                                    }else{
                                        observationsEnglish= observationsText;
                                        observationsSpanish=translations.get(0);
                                        observationsFrench=translations.get(1);
                                        observationsBasque=translations.get(2);
                                        observationsCatalan=translations.get(3);
                                        observationsDutch=translations.get(4);
                                        observationsGalician=translations.get(5);
                                        observationsGerman=translations.get(6);
                                        observationsItalian=translations.get(7);
                                        observationsPortuguese=translations.get(8);
                                    }
                                }


                                EvaluatorTeam evaluatorTeam=new EvaluatorTeam(idEvaluatorTeam,creation_date,professional.getEmailUser(),responsible.getEmailUser(),otherMembers.getText().toString(),1,"EVALUATOR",centerSelected.getIdOrganization(),centerSelected.getOrgType(),centerSelected.getIdCenter(),centerSelected.getIllness(),consultant.getText().toString(),patient.getText().toString(),relative.getText().toString(),observationsEnglish,observationsSpanish,observationsFrench,observationsBasque,observationsCatalan,observationsDutch,observationsGalician,observationsGerman,observationsItalian,observationsPortuguese,evaluationDatesStr.toString(),0,evaluationDates.size(),imgEvalTeamName);


                                EvaluatorTeamsController.Create(evaluatorTeam);


                                Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);

                                startActivity(intent);



                        }
                    }, 100);
                } else{
                    base.setVisibility(View.VISIBLE);
                    finalBackground.setVisibility(View.GONE);
                    String msg="<ul>";
                    int numErrors=0;
                    if(centerSelected==centers.get(0)){
                        msg+="<li><b>"+getString(R.string.please_select_center)+"</b></li>";
                        numErrors++;
                    }
                    if(responsible.getEmailUser().equals("-")){
                        msg+="<li><b>"+getString(R.string.please_responsible)+"</b></li>";
                        numErrors++;
                    }
                    if(!professional.getEmailUser().equals("-")){
                        msg+="<li><b>"+getString(R.string.please_professional)+"</b></li>";
                        numErrors++;
                    }
                    if(patient.getText().toString().isEmpty()){
                        msg+="<li><b>"+getString(R.string.please_relative_name)+"</b></li>";
                        numErrors++;
                    }
                    if(relative.getText().toString().isEmpty()){
                        msg+="<li><b>"+getString(R.string.please_relative_name)+"</b></li>";
                        numErrors++;
                    }
                    if(creationDateEditText.getText().toString().isEmpty()){
                        msg+="<li><b>"+getString(R.string.please_date)+"</b></li>";
                        numErrors++;
                    }
                    if(evaluationDatesEditText.getText().toString().isEmpty()){
                        msg+="<li><b>"+getString(R.string.must_select_three_eval_dates)+"</b></li>";
                        numErrors++;
                    }
                    if(consultant.getText().toString().isEmpty()){
                        msg+="<li><b>"+getString(R.string.please_consultant)+"</b></li>";
                        numErrors++;
                    }
                    if(!checked){
                        msg+="<li><b>"+getString(R.string.you_must_LOPD)+"</b></li>";
                        numErrors++;
                    }
                    msg+="</ul>";
                    int idTitle=-1;
                    if(numErrors>1){
                        idTitle=R.string.errors;
                    }else{
                        idTitle=R.string.error;
                    }
                    new AlertDialog.Builder(RegisterNewEvaluatorTeam.this)
                            .setTitle(getString(idTitle))
                            .setMessage(Html.fromHtml(msg,0))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                }}
            });

        }
        else{
            String msg="<ul>";
            int numErrors=0;
            if(evaluatedUsers.isEmpty()){
                msg+="<li><b>"+getString(R.string.please_select_center)+"</b></li>";
                numErrors++;
            }
            if(evaluatorUsers.isEmpty()){
                msg+="<li><b>"+getString(R.string.please_select_center)+"</b></li>";
                numErrors++;
            }
            msg+="</ul>";
            int idTitle=-1;
            if(numErrors>1){
                idTitle=R.string.errors;
            }else{
                idTitle=R.string.error;
            }
            new AlertDialog.Builder(RegisterNewEvaluatorTeam.this)
                    .setTitle(getString(idTitle))
                    .setMessage(Html.fromHtml(msg,0))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
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