package gui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primedatepicker.picker.PrimeDatePicker;
import com.aminography.primedatepicker.picker.callback.MultipleDaysPickCallback;
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import gui.adapters.UsersAdapter;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.TranslatorController;
import session.EditEvaluatorTeamUtil;
import session.FileManager;
import session.StringPasser;

public class EditEvaluatorTeam extends AppCompatActivity {

    EditText creationDateEditText;

    EditText evaluationDatesEditText;



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

    String imgEvalTeamName="";

    ImageButton helpButton;
    
    EvaluatorTeam evaluatorTeam;

    boolean photoHasChanged=false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_evaluator_team);

        evaluatorTeam=EditEvaluatorTeamUtil.getInstance().getEvaluatorTeam();

        evaluatedUsers= EditEvaluatorTeamUtil.getInstance().getEvaluatedUsers();
        if (!evaluatedUsers.isEmpty()) {

            Spinner responsibleSpinner = findViewById(R.id.spinner_select_responsible);
            Spinner professionalSpinner = findViewById(R.id.spinner_select_professional);

            Drawable correct = ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_check_circle_24);


            List<User> userAuxList=new ArrayList<>();
            userAuxList.add(new User("-1","-",getString(R.string.responsible),"-","","",-1,"-","-","-",-1));
            userAuxList.add(new User("-1","-",getString(R.string.professional),"-","","",-1,"-","-","-",-1));

            List<User> responsibles=new ArrayList<>();
            responsibles.add(0,userAuxList.get(0));
            responsibles.addAll(evaluatedUsers);

            List<User> professionals=new ArrayList<>();
            professionals.add(0,userAuxList.get(1));
            professionals.addAll(evaluatedUsers);

            //otherMembers=new ArrayList<>();

            EditText otherMembers = findViewById(R.id.other_members);
            if(!evaluatorTeam.getOtherMembers().isEmpty()){
                otherMembers.setText(evaluatorTeam.getOtherMembers());
            }
            
            EditText consultant = findViewById(R.id.consultant);
            
            consultant.setText(evaluatorTeam.getExternalConsultant());
            consultant.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

            EditText patient = findViewById(R.id.patientName);
            patient.setText(evaluatorTeam.getPatient_name());
            patient.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
            EditText relative = findViewById(R.id.relativeName);
            relative.setText(evaluatorTeam.getRelative_name());
            relative.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

            creationDateEditText=findViewById(R.id.creation_date);
            creationDate=new CivilCalendar();
            creationDate.setTimeInMillis(evaluatorTeam.getCreationDate());
            creationDateEditText.setText(creationDate.getLongDateString().split(", ")[1]);
            creationDateEditText.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

            evaluationDatesEditText=findViewById(R.id.eval_dates);
            evaluationDates=new ArrayList<>();
            String[] evalDates=evaluatorTeam.getEvaluationDates().split(",");
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<evalDates.length;i++){
                PrimeCalendar evaluationDateUtil=new CivilCalendar();
                evaluationDateUtil.setTimeInMillis(Long.parseLong(evalDates[i]));
                evaluationDates.add(evaluationDateUtil);
                sb.append(evaluationDates.get(i).getLongDateString().split(", ")[1]);
                if(i<evalDates.length-1){
                    sb.append(", ");
                }
            }
            evaluationDatesEditText.setText(sb.toString());
            evaluationDatesEditText.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

            EditText observations=findViewById(R.id.observations);
            String obs="";
            if(Locale.getDefault().getLanguage().equals("es")) {
                obs=evaluatorTeam.getObservationsSpanish();
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                obs=evaluatorTeam.getObservationsFrench();
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                obs=evaluatorTeam.getObservationsBasque();
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                obs=evaluatorTeam.getObservationsCatalan();
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                obs=evaluatorTeam.getObservationsDutch();
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                obs=evaluatorTeam.getObservationsGalician();
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                obs=evaluatorTeam.getObservationsGerman();
            }else if(Locale.getDefault().getLanguage().equals("it")){
                obs=evaluatorTeam.getObservationsItalian();
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                obs=evaluatorTeam.getObservationsPortuguese();
            }else{
                obs=evaluatorTeam.getObservationsEnglish();
            }
            observations.setText(obs);
            observations.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);

            UsersAdapter[] usersAdapter=new UsersAdapter[2];


            usersAdapter[1]=new UsersAdapter(getApplicationContext(),responsibles);
            usersAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            responsibleSpinner.setAdapter(usersAdapter[1]);

            usersAdapter[0]=new UsersAdapter(getApplicationContext(),professionals);
            usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            professionalSpinner.setAdapter(usersAdapter[0]);



            ConstraintLayout finalBackground=findViewById(R.id.final_background);

            ConstraintLayout base=findViewById(R.id.base);

            base.setVisibility(View.VISIBLE);
            finalBackground.setVisibility(View.GONE);

            imageEvalTeamButton=findViewById(R.id.uploadPhoto);
            imageEvalTeam=findViewById(R.id.profilePhoto);
            if(!evaluatorTeam.getProfilePhoto().isEmpty()) {
                List<String> aux = new ArrayList<>();
                aux.add(evaluatorTeam.getProfilePhoto());
                FileManager.downloadPhotosProfileAsync(aux, new FileManager.PhotosDownloadCallback() {
                    @Override
                    public void onPhotoDownloadSuccess(String fileName, ByteArrayOutputStream stream) {
                        profilePhotoEvalTeam = new ByteArrayInputStream(stream.toByteArray());
                        imageEvalTeam.setImageBitmap(ProfilePhotoUtil.getBitmapFromStream(stream));
                    }

                    @Override
                    public void onPhotoDownloadFailure(String fileName, Exception e) {

                    }
                });
            }
            helpButton=findViewById(R.id.helpButton);


            ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri uri) {
                            // Handle the returned Uri
                            try {
                                profilePhotoEvalTeam = getContentResolver().openInputStream(uri);
                                imageEvalTeam.setImageURI(uri);
                                photoHasChanged=true;
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch(NullPointerException ignored){}
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
                                photoHasChanged=true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }catch(NullPointerException ignored){}
                        }
                    });

            imageEvalTeamButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AwesomeInfoDialog(v.getContext())
                            .setTitle(R.string.change_photo)
                            .setMessage(R.string.select_source)
                            .setColoredCircle(R.color.miradas_color)
                            .setCancelable(true)
                            .setDialogIconAndColor(android.R.drawable.ic_menu_camera,R.color.white)
                            .setPositiveButtonText(getString(R.string.camera))
                            .setPositiveButtonbackgroundColor(R.color.miradas_color)
                            .setPositiveButtonTextColor(R.color.white)
                            .setNegativeButtonText(getString(R.string.gallery))
                            .setNegativeButtonbackgroundColor(R.color.miradas_color)
                            .setNegativeButtonTextColor(R.color.white)
                            .setPositiveButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    mTakePicture.launch(null);
                                }
                            })
                            .setNegativeButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    mGetContent.launch("image/*");
                                }
                            })
                            .show();
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

            for(int i=1;i<responsibles.size();i++){
                if(responsibles.get(i).getEmailUser().equals(evaluatorTeam.getEmailResponsible())){
                    responsibleSpinner.setSelection(i);
                    break;
                }
            }

            for(int i=1;i<professionals.size();i++){
                if(professionals.get(i).getEmailUser().equals(evaluatorTeam.getEmailResponsible())){
                    professionalSpinner.setSelection(i);
                    break;
                }
            }

            patient.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    patient.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        patient.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        patient.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                        relative.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        relative.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                        consultant.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        consultant.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
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
                    String input=s.toString();
                    if(input.isEmpty()){
                        otherMembers.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        otherMembers.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    }
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

            creationDateEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    creationDateEditText.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        creationDateEditText.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        creationDateEditText.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

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

                                        new AwesomeWarningDialog(EditEvaluatorTeam.this)
                                                .setTitle(R.string.evaluation_dates_misselected)
                                                .setMessage(R.string.must_select_three_eval_dates)
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
                                    evaluationDatesEditText.setText(text);

                                }
                            })
                            .initiallyPickedMultipleDays(evaluationDates)
                            .minPossibleDate(today)
                            .build();
                    datePicker.show(getSupportFragmentManager(), "EVALUATION_DATES");
                }
            });

            evaluationDatesEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    evaluationDatesEditText.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        evaluationDatesEditText.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        evaluationDatesEditText.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            observations.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String input=charSequence.toString();
                    if(input.isEmpty()){
                        observations.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    }
                    else{
                        observations.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            Button add=findViewById(R.id.add);




            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //base.setVisibility(View.GONE);
                    //finalBackground.setVisibility(View.VISIBLE);
                    AwesomeProgressDialog chargingScreenDialog=new AwesomeProgressDialog(EditEvaluatorTeam.this)
                            .setTitle(R.string.please_wait_save_changes)
                            .setMessage(R.string.please_wait)
                            .setColoredCircle(R.color.miradas_color)
                            .setCancelable(false);
                    chargingScreenDialog.show();
                    if(professional!=null && !professional.getEmailUser().equals("-1") && responsible!=null && !responsible.getEmailUser().equals("-1")
                            && !patient.getText().toString().isEmpty() && !relative.getText().toString().isEmpty() &&
                            creationDate!=null && evaluationDates!=null && !evaluationDates.isEmpty()){                        imgEvalTeamName=evaluatorTeam.getProfilePhoto();
                        if(photoHasChanged){

                            new Thread(()->{
                                if(imgEvalTeamName.isEmpty()){
                                    imgEvalTeamName="EVALTEAM_"+evaluatorTeam.getIdEvaluatorTeam()+"_"+ centerSelected.getIdCenter()+"_"+centerSelected.getIdOrganization()+"_"+centerSelected.getOrgType()+"_"+centerSelected.getIllness()+".webp";
                                }
                                FileManager.uploadFile(profilePhotoEvalTeam, "profile-photos", imgEvalTeamName);
                                try{
                                    profilePhotoEvalTeam.close();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                            }).start();
                        }

                        new Thread(()->{

                            int idEvaluatorTeam= evaluatorTeam.getIdEvaluatorTeam();

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



                            if(!observationsText.isEmpty()){
                                List<String> translations= TranslatorController.getInstance().translate(observationsText,Locale.getDefault().getLanguage());
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


                            EvaluatorTeam evalTeam=new EvaluatorTeam(idEvaluatorTeam,creation_date,professional.getEmailUser(),responsible.getEmailUser(),otherMembers.getText().toString(),evaluatorTeam.getIdEvaluatorOrganization(),evaluatorTeam.getOrgTypeEvaluator(),evaluatorTeam.getIdEvaluatedOrganization(),evaluatorTeam.getOrgTypeEvaluated(),evaluatorTeam.getIdCenter(),evaluatorTeam.getIllness(),consultant.getText().toString(),patient.getText().toString(),relative.getText().toString(),observationsEnglish,observationsSpanish,observationsFrench,observationsBasque,observationsCatalan,observationsDutch,observationsGalician,observationsGerman,observationsItalian,observationsPortuguese,evaluationDatesStr.toString(),0,evaluationDates.size(),evaluatorTeam.getProfilePhoto());


                            EvaluatorTeamsController.Update(idEvaluatorTeam,evaluatorTeam.getIdEvaluatorOrganization(),evaluatorTeam.getOrgTypeEvaluator(),evaluatorTeam.getIdEvaluatedOrganization(),evaluatorTeam.getOrgTypeEvaluated(),evaluatorTeam.getIdCenter(),evaluatorTeam.getIllness(),evalTeam);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            StringPasser.createInstance(R.string.evaluation_team_modified,R.string.choose_other);
                            StringPasser.getInstance().setFlag(1);
                            runOnUiThread(()->{
                                EditEvaluatorTeamUtil.removeInstance();
                                Intent intent=new Intent(getApplicationContext(), MainMenu.class);
                                startActivity(intent);
                            });
                        }).start();
                    } else{
                        //base.setVisibility(View.VISIBLE);
                        //finalBackground.setVisibility(View.GONE);
                        chargingScreenDialog.hide();
                        String msg="<ul>";
                        int numErrors=0;
                        if(responsible==null || responsible.getEmailUser().equals("-1")){
                            msg+="<li><b>"+getString(R.string.please_responsible)+"</b></li>";
                            numErrors++;
                        }
                        if(professional==null || professional.getEmailUser().equals("-1")){
                            msg+="<li><b>"+getString(R.string.please_professional)+"</b></li>";
                            numErrors++;
                        }
                        if(patient.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_patient_name)+"</b></li>";
                            patient.setError(getString(R.string.please_patient_name));
                            numErrors++;
                        }
                        if(relative.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_relative_name)+"</b></li>";
                            relative.setError(getString(R.string.please_relative_name));
                            numErrors++;
                        }
                        if(creationDate==null){
                            msg+="<li><b>"+getString(R.string.please_enter_creation_date)+"</b></li>";
                            creationDateEditText.setError(getString(R.string.please_enter_creation_date));
                            numErrors++;
                        }
                        if(evaluationDates==null || evaluationDates.isEmpty()){
                            msg+="<li><b>"+getString(R.string.must_select_three_eval_dates)+"</b></li>";
                            evaluationDatesEditText.setError(getString(R.string.must_select_three_eval_dates));
                            numErrors++;
                        }
                        if(consultant.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_consultant)+"</b></li>";
                            consultant.setError(getString(R.string.please_consultant));
                            numErrors++;
                        }
                        msg+="</ul>";
                        int idTitle=-1;
                        if(numErrors>1){
                            idTitle=R.string.errors;
                        }else{
                            idTitle=R.string.error;
                        }
                        new AwesomeErrorDialog(EditEvaluatorTeam.this)
                                .setTitle(idTitle)
                                .setMessage(Html.fromHtml(msg,0))
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
                    }}
            });

        }
        else{
            String msg="<ul>";
            int numErrors=0;
            msg+="</ul>";
            int idTitle=-1;
            if(numErrors>1){
                idTitle=R.string.errors;
            }else{
                idTitle=R.string.error;
            }
            new AlertDialog.Builder(EditEvaluatorTeam.this)
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
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            EditEvaluatorTeamUtil.removeInstance();
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}