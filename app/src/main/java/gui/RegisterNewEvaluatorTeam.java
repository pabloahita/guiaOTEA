package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import gui.adapters.CenterAdapter;
import gui.adapters.OrgsAdapter;
import gui.adapters.UsersAdapter;
import misc.DateFormatter;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.TranslatorController;
import otea.connection.controller.UsersController;
import session.Session;

public class RegisterNewEvaluatorTeam extends AppCompatActivity {

    TextView creationDate;

    TextView evalDate1;
    TextView evalDate2;
    TextView evalDate3;
    TextView evalDate4;

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



            TextView otherMembers = findViewById(R.id.other_members);

            TextView consultant = findViewById(R.id.consultant);

            TextView patient = findViewById(R.id.patientName);
            TextView relative = findViewById(R.id.relativeName);

            creationDate=findViewById(R.id.creation_date);
            evalDate1=findViewById(R.id.eval_date_1);
            evalDate2=findViewById(R.id.eval_date_2);
            evalDate3=findViewById(R.id.eval_date_3);
            evalDate4=findViewById(R.id.eval_date_4);

            TextView observations=findViewById(R.id.observations);

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

            creationDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    creationDate.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        creationDate.setError(getString(R.string.please_date));
                    }
                    else{
                        creationDate.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            evalDate1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    evalDate1.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        evalDate2.setError(getString(R.string.please_date));
                    }
                    else{
                        evalDate1.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            evalDate2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    evalDate2.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        evalDate2.setError(getString(R.string.please_date));
                    }
                    else{
                        evalDate2.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            evalDate3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    evalDate3.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        evalDate3.setError(getString(R.string.please_date));
                    }
                    else{
                        evalDate3.setError(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            evalDate4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    evalDate4.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
                        evalDate4.setError(getString(R.string.please_date));
                    }
                    else{
                        evalDate4.setError(null);
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
                    creationDate.setEnabled(false);
                    evalDate1.setEnabled(false);
                    evalDate2.setEnabled(false);
                    evalDate3.setEnabled(false);
                    evalDate4.setEnabled(false);
                    observations.setEnabled(false);
                    consultant.setEnabled(false);

                    finalBackground.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(organizationSelected!=evaluatedOrganizations.get(0) && !userAuxList.contains(professional) && !userAuxList.contains(responsible) && !patient.getText().toString().isEmpty() && !relative.getText().toString().isEmpty() && !creationDate.getText().toString().isEmpty() && !evalDate1.getText().toString().isEmpty() && !evalDate2.getText().toString().isEmpty() && !evalDate3.getText().toString().isEmpty() && !evalDate4.getText().toString().isEmpty() && !consultant.toString().isEmpty()){
                                int idEvaluatorTeam= EvaluatorTeamsController.GetAllByOrganization(1,"EVALUATOR","AUTISM").size()+1;


                                long creation_date= DateFormatter.formatDate(creationDate.getText().toString());
                                long eval_date1= DateFormatter.formatDate(evalDate1.getText().toString());
                                long eval_date2= DateFormatter.formatDate(evalDate2.getText().toString());
                                long eval_date3= DateFormatter.formatDate(evalDate3.getText().toString());
                                long eval_date4= DateFormatter.formatDate(evalDate4.getText().toString());

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


                                EvaluatorTeam evaluatorTeam=new EvaluatorTeam(idEvaluatorTeam,creation_date,professional.getEmailUser(),responsible.getEmailUser(),otherMembers.getText().toString(),1,"EVALUATOR",organizationSelected.getIdOrganization(),organizationSelected.getOrgType(),centerSelected.getIdCenter(),organizationSelected.getIllness(),consultant.getText().toString(),patient.getText().toString(),relative.getText().toString(),eval_date1,eval_date2,eval_date3,eval_date4,observationsEnglish,observationsSpanish,observationsFrench,observationsBasque,observationsCatalan,observationsDutch,observationsGalician,observationsGerman,observationsItalian,observationsPortuguese);


                                EvaluatorTeamsController.Create(evaluatorTeam);


                                Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);

                                finalBackground.setVisibility(View.GONE);
                                startActivity(intent);


                            }
                            else{
                                orgSpinner.setEnabled(true);
                                responsibleSpinner.setEnabled(true);
                                professionalSpinner.setEnabled(true);
                                evalDate1.setEnabled(true);
                                evalDate2.setEnabled(true);
                                evalDate3.setEnabled(true);
                                evalDate4.setEnabled(true);

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
                                if(creationDate.getText().toString().isEmpty()){
                                    creationDate.setError(getString(R.string.please_date));
                                }
                                if(evalDate1.getText().toString().isEmpty()){
                                    evalDate1.setError(getString(R.string.please_date));
                                }
                                if(evalDate2.getText().toString().isEmpty()){
                                    evalDate2.setError(getString(R.string.please_date));
                                }
                                if(evalDate3.getText().toString().isEmpty()){
                                    evalDate3.setError(getString(R.string.please_date));
                                }
                                if(evalDate4.getText().toString().isEmpty()){
                                    evalDate4.setError(getString(R.string.please_date));
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

    private void changeDate(String type, View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, yearSelected, monthOfYear, dayOfMonth) -> {
                    // Aquí obtienes la fecha seleccionada
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + yearSelected;
                    if(type.equals("CREATION")) {
                        creationDate.setText(selectedDate);
                    }
                    if(type.equals("EVALDATE1")) {
                        evalDate1.setText(selectedDate);
                    }
                    if(type.equals("EVALDATE2")) {
                        evalDate2.setText(selectedDate);
                    }
                    if(type.equals("EVALDATE3")) {
                        evalDate3.setText(selectedDate);
                    }
                    if(type.equals("EVALDATE4")) {
                        evalDate4.setText(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    public void changeCreationDate(View view){changeDate("CREATION",view);}

    public void changeEvalDate1(View view){changeDate("EVALDATE1",view);}

    public void changeEvalDate2(View view){changeDate("EVALDATE2",view);}

    public void changeEvalDate3(View view){changeDate("EVALDATE3",view);}

    public void changeEvalDate4(View view){changeDate("EVALDATE4",view);}

}