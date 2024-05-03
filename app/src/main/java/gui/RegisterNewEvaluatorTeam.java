package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    Organization organizationSelected;
    
    static int MIN_NUM_EVAL_DATES=3;

    static int MAX_NUM_EVAL_DATES=30;

    PrimeCalendar creationDate;

    List<PrimeCalendar> evaluationDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_evaluator_team);

        evaluatedOrganizations = Session.getEvaluatedOrganizations();
        evaluatorUsers = Session.getEvaluatorUsers();

        if (!evaluatedOrganizations.isEmpty() && !evaluatorUsers.isEmpty()) {

            evaluatedOrganizations.add(0,new Organization(-1,"-","-",getString(R.string.evaluated_org),-1,"-","-","-","-","-","-","-","-","-","-","-","-","-"));
            Spinner orgSpinner = findViewById(R.id.spinner_select_organization);
            Spinner centerSpinner = findViewById(R.id.spinner_select_center);
            Spinner centerSpinnerAux = findViewById(R.id.spinner_select_center_aux);
            Spinner responsibleSpinner = findViewById(R.id.spinner_select_responsible);
            Spinner professionalSpinner = findViewById(R.id.spinner_select_professional);
            Spinner professionalSpinnerAux = findViewById(R.id.spinner_select_professional_aux);

            List<User> userAuxList=new ArrayList<>();
            userAuxList.add(new User("-1","-",getString(R.string.responsible),"-","","",-1,"-","-","-"));
            userAuxList.add(new User("-1","-",getString(R.string.professional),"-","","",-1,"-","-","-"));

            List<Center> centerAuxList=new ArrayList<>();
            centerAuxList.add(new Center(-1,"-","-",-1,"Center of the organization","Centro de la organización","Centre de l'organisation","Erakundearen Zentroa","Centre de l’organització","Centrum van de organisatie","Centro da organización","Zentrum der Organisation","Centro dell'organizzazione","Centro da organização",-1,"-","-1"));


            evaluatorUsers.add(0,userAuxList.get(0));

            otherMembers=new ArrayList<>();

            EditText otherMembers = findViewById(R.id.other_members);

            EditText consultant = findViewById(R.id.consultant);

            EditText patient = findViewById(R.id.patientName);
            EditText relative = findViewById(R.id.relativeName);

            creationDateEditText=findViewById(R.id.creation_date);
            evaluationDatesEditText=findViewById(R.id.eval_dates);

            EditText observations=findViewById(R.id.observations);

            OrgsAdapter[] orgsAdapter={new OrgsAdapter(getApplicationContext(),evaluatedOrganizations)};
            UsersAdapter[] usersAdapter=new UsersAdapter[2];
            CenterAdapter[] centerAdapters=new CenterAdapter[2];
            centerAdapters[1]=new CenterAdapter(getApplicationContext(),centerAuxList);
            centerSpinnerAux.setAdapter(centerAdapters[1]);

            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            orgSpinner.setAdapter(orgsAdapter[0]);

            usersAdapter[1]=new UsersAdapter(getApplicationContext(),evaluatorUsers);
            usersAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            responsibleSpinner.setAdapter(usersAdapter[1]);

            usersAdapter[0]=new UsersAdapter(getApplicationContext(),userAuxList.subList(1,2));
            usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            professionalSpinnerAux.setAdapter(usersAdapter[0]);



            ConstraintLayout finalBackground=findViewById(R.id.final_background);

            finalBackground.setVisibility(View.GONE);


            orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    organizationSelected= (Organization) parent.getItemAtPosition(position);
                    if(position>0){
                        evaluatedUsers=Session.getInstance().getOrgUsersByOrganization(organizationSelected);
                        evaluatedUsers.add(0,userAuxList.get(1));
                        usersAdapter[0] = new UsersAdapter(getApplicationContext(), evaluatedUsers);
                        usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        centers = Session.getInstance().getCentersByOrganization(organizationSelected);
                        centers.add(0,centerAuxList.get(0));
                        centerAdapters[0]=new CenterAdapter(getApplicationContext(),centers);
                        centerAdapters[0].setDropDownViewResource(R.layout.spinner_item_layout);
                        centerSpinner.setAdapter(centerAdapters[0]);
                        professionalSpinner.setAdapter(usersAdapter[0]);
                        centerSpinner.setVisibility(View.VISIBLE);
                        centerSpinnerAux.setVisibility(View.GONE);
                        professionalSpinner.setVisibility(View.VISIBLE);
                        professionalSpinnerAux.setVisibility(View.GONE);
                    }else{
                        centerSpinner.setVisibility(View.GONE);
                        centerSpinnerAux.setVisibility(View.VISIBLE);
                        centerSpinnerAux.setEnabled(false);
                        centerSpinnerAux.setAlpha(0.5f);
                        professionalSpinner.setVisibility(View.GONE);
                        professionalSpinnerAux.setVisibility(View.VISIBLE);
                        professionalSpinnerAux.setEnabled(false);
                        professionalSpinnerAux.setAlpha(0.5f);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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
                                    creationDateEditText.setText(creationDate.getLongDateString());
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
            /*addEvaluationDates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Minimo 3 Maximo 30 (en maximo 1 mes)

                }
            });*/

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

            Button add=findViewById(R.id.add);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orgSpinner.setEnabled(false);
                    responsibleSpinner.setEnabled(false);
                    professionalSpinner.setEnabled(false);
                    patient.setEnabled(false);
                    relative.setEnabled(false);
                    creationDateEditText.setEnabled(false);
                    observations.setEnabled(false);
                    consultant.setEnabled(false);

                    finalBackground.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(organizationSelected!=evaluatedOrganizations.get(0) && !userAuxList.contains(professional) && !userAuxList.contains(responsible) && !patient.getText().toString().isEmpty() && !relative.getText().toString().isEmpty() && !creationDateEditText.getText().toString().isEmpty() && !consultant.toString().isEmpty()){
                                int idEvaluatorTeam= EvaluatorTeamsController.GetAllByOrganization(1,"EVALUATOR","AUTISM").size()+1;


                                long creation_date= DateFormatter.formatDate(creationDateEditText.getText().toString());


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


                                EvaluatorTeam evaluatorTeam=new EvaluatorTeam(idEvaluatorTeam,creation_date,professional.getEmailUser(),responsible.getEmailUser(),otherMembers.getText().toString(),1,"EVALUATOR",organizationSelected.getIdOrganization(),organizationSelected.getOrgType(),centerSelected.getIdCenter(),organizationSelected.getIllness(),consultant.getText().toString(),patient.getText().toString(),relative.getText().toString(),observationsEnglish,observationsSpanish,observationsFrench,observationsBasque,observationsCatalan,observationsDutch,observationsGalician,observationsGerman,observationsItalian,observationsPortuguese,"evalDates",0,evaluationDates.size());


                                EvaluatorTeamsController.Create(evaluatorTeam);


                                Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);

                                finalBackground.setVisibility(View.GONE);
                                startActivity(intent);


                            }
                            else{
                                orgSpinner.setEnabled(true);
                                responsibleSpinner.setEnabled(true);
                                professionalSpinner.setEnabled(true);
                                finalBackground.setVisibility(View.GONE);

                                if(organizationSelected==evaluatedOrganizations.get(0)){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_evaluated_org),Toast.LENGTH_SHORT).show();
                                }
                                if(userAuxList.contains(responsible)){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_responsible),Toast.LENGTH_SHORT).show();
                                }
                                if(userAuxList.contains(professional)){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_professional),Toast.LENGTH_SHORT).show();
                                }
                                if(patient.getText().toString().isEmpty()){
                                    patient.setError(getString(R.string.please_relative_name));
                                }
                                if(relative.getText().toString().isEmpty()){
                                    relative.setError(getString(R.string.please_relative_name));
                                }
                                if(creationDateEditText.getText().toString().isEmpty()){
                                    creationDateEditText.setError(getString(R.string.please_date));
                                }

                                if(consultant.getText().toString().isEmpty()){
                                    consultant.setError(getString(R.string.please_consultant));
                                }
                            }
                        }
                    }, 100);
                }
            });

        }
    }



}