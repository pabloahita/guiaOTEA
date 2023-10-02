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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
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
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.TranslatorController;
import otea.connection.controller.UsersController;

public class RegisterNewEvaluatorTeam extends AppCompatActivity {

    TextView creationDate;

    TextView evalDate1;
    TextView evalDate2;
    TextView evalDate3;
    TextView evalDate4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_evaluator_team);

        List<Organization> organizations = OrganizationsController.GetAllEvaluatedOrganizations();
        List<User> usersEvaluator = UsersController.GetAllOrgUsersByOrganization(1, "EVALUATOR", "AUTISM");

        if (organizations.size() > 0 && usersEvaluator.size()>0) {

            Spinner orgSpinner = findViewById(R.id.spinner_select_organization);
            Spinner centerSpinner = findViewById(R.id.spinner_select_center);
            Spinner responsibleSpinner = findViewById(R.id.spinner_select_responsible);
            Spinner professionalSpinner = findViewById(R.id.spinner_select_professional);

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

            OrgsAdapter[] orgsAdapter={new OrgsAdapter(getApplicationContext(),organizations)};
            UsersAdapter[] usersAdapter=new UsersAdapter[3];
            CenterAdapter[] centerAdapters=new CenterAdapter[1];
            final List<Center>[] centers = new List[]{new LinkedList<>()};

            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            orgSpinner.setAdapter(orgsAdapter[0]);

            usersAdapter[1]=new UsersAdapter(getApplicationContext(),usersEvaluator);
            usersAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            responsibleSpinner.setAdapter(usersAdapter[1]);

            usersAdapter[2]=new UsersAdapter(getApplicationContext(),usersEvaluator);
            usersAdapter[2].setDropDownViewResource(R.layout.spinner_item_layout);
            professionalSpinner.setAdapter(usersAdapter[2]);

            Organization[] organizationSelected=new Organization[1];

            Center[] centerSelected=new Center[1];


            final List<User>[] users = new List[]{new LinkedList<>()};

            User[] membersSelected=new User[3];


            ConstraintLayout finalBackground=findViewById(R.id.final_background);

            finalBackground.setVisibility(View.GONE);


            orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    organizationSelected[0]= (Organization) parent.getItemAtPosition(position);
                    users[0] = UsersController.GetAllOrgUsersByOrganization(organizationSelected[0].getIdOrganization(), organizationSelected[0].getOrganizationType(), organizationSelected[0].getIllness());
                    usersAdapter[0] = new UsersAdapter(getApplicationContext(), users[0]);
                    usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                    centers[0] = CentersController.GetAllByOrganization(organizationSelected[0]);
                    centerAdapters[0]=new CenterAdapter(getApplicationContext(),centers[0]);
                    centerAdapters[0].setDropDownViewResource(R.layout.spinner_item_layout);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            centerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    centerSelected[0]= (Center) parent.getItemAtPosition(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            responsibleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    membersSelected[1]=(User) parent.getItemAtPosition(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            professionalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    membersSelected[2]=(User) parent.getItemAtPosition(position);

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
                    if(input.equals("")){
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
                    if(input.equals("")){
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
                    if(input.equals("")){
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

            creationDate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    creationDate.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.equals("")){
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
                    if(input.equals("")){
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
                    if(input.equals("")){
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
                    if(input.equals("")){
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
                    if(input.equals("")){
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
                            if(organizationSelected[0]!=null && membersSelected[0]!=null && membersSelected[1]!=null && membersSelected[2]!=null && !patient.getText().toString().equals("") && !relative.getText().toString().equals("") && !creationDate.getText().toString().equals("") && !evalDate1.getText().toString().equals("") && !evalDate2.getText().toString().equals("") && !evalDate3.getText().toString().equals("") && !evalDate4.getText().toString().equals("") && !consultant.toString().equals("")){
                                int idEvaluatorTeam= EvaluatorTeamsController.GetAllByOrganization(1,"EVALUATOR","AUTISM").size()+1;

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

                                if(!observations.getText().toString().equals("")){
                                    if(Locale.getDefault().getLanguage().equals("es")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"es","en");
                                        observationsSpanish=observationsText;
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"es","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"es","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"es","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"es","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"es","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"es","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"es","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"es","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"es","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"fr","es");
                                        observationsFrench=observationsText;
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"fr","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"fr","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"fr","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"fr","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"fr","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"fr","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"fr","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"eu","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"eu","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"eu","fr");
                                        observationsBasque=observationsText;
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"eu","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"eu","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"eu","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"eu","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"eu","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"eu","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"ca","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"ca","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"ca","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"ca","eu");
                                        observationsCatalan=observationsText;
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"ca","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"ca","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"ca","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"ca","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"ca","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"nl","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"nl","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"nl","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"nl","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"nl","ca");
                                        observationsDutch=observationsText;
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"nl","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"nl","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"nl","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"nl","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"gl","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"gl","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"gl","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"gl","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"gl","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"gl","nl");
                                        observationsGalician=observationsText;
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"gl","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"gl","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"gl","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("de")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"de","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"de","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"de","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"de","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"de","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"de","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"de","gl");
                                        observationsGerman=observationsText;
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"de","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"de","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("it")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"it","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"it","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"it","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"it","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"it","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"it","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"it","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"it","de");
                                        observationsItalian=observationsText;
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"it","pt");
                                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                                        observationsEnglish= TranslatorController.getInstance().translate(observationsText,"pt","en");
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"pt","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"pt","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"pt","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"pt","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"pt","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"pt","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"pt","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"pt","it");
                                        observationsPortuguese=observationsText;
                                    }else{
                                        observationsEnglish=observationsText;
                                        observationsSpanish=TranslatorController.getInstance().translate(observationsText,"en","es");
                                        observationsFrench=TranslatorController.getInstance().translate(observationsText,"en","fr");
                                        observationsBasque=TranslatorController.getInstance().translate(observationsText,"en","eu");
                                        observationsCatalan=TranslatorController.getInstance().translate(observationsText,"en","ca");
                                        observationsDutch=TranslatorController.getInstance().translate(observationsText,"en","nl");
                                        observationsGalician=TranslatorController.getInstance().translate(observationsText,"en","gl");
                                        observationsGerman=TranslatorController.getInstance().translate(observationsText,"en","de");
                                        observationsItalian=TranslatorController.getInstance().translate(observationsText,"en","it");
                                        observationsPortuguese=TranslatorController.getInstance().translate(observationsText,"en","pt");
                                    }
                                }


                                EvaluatorTeam evaluatorTeam=new EvaluatorTeam(idEvaluatorTeam,creation_date,membersSelected[2].getEmailUser(),membersSelected[1].getEmailUser(),"",1,"EVALUATOR",organizationSelected[0].getIdOrganization(),organizationSelected[0].getOrgType(),centerSelected[0].getIdCenter(),organizationSelected[0].getIllness(),consultant.getText().toString(),patient.getText().toString(),relative.getText().toString(),eval_date1,eval_date2,eval_date3,eval_date4,observationsSpanish,observationsEnglish,observationsFrench,observationsBasque,observationsCatalan,observationsDutch,observationsGalician,observationsGerman,observationsItalian,observationsPortuguese);





                                EvaluatorTeamsController.Create(evaluatorTeam);


                                Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
                                intent.putExtra("user",getIntent().getSerializableExtra("user"));
                                intent.putExtra("org",getIntent().getSerializableExtra("org"));
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

                                if(organizationSelected[0]==null){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_evaluated_org),Toast.LENGTH_SHORT).show();
                                }
                                if(membersSelected[0]==null){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_consultant),Toast.LENGTH_SHORT).show();
                                }
                                if(membersSelected[1]==null){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_responsible),Toast.LENGTH_SHORT).show();
                                }
                                if(membersSelected[2]==null){
                                    Toast.makeText(getApplicationContext(),getString(R.string.please_professional),Toast.LENGTH_SHORT).show();
                                }
                                if(patient.getText().toString().equals("")){
                                    patient.setError(getString(R.string.please_relative_name));
                                }
                                if(relative.getText().toString().equals("")){
                                    relative.setError(getString(R.string.please_relative_name));
                                }
                                if(creationDate.getText().toString().equals("")){
                                    creationDate.setError(getString(R.string.please_date));
                                }
                                if(evalDate1.getText().toString().equals("")){
                                    evalDate1.setError(getString(R.string.please_date));
                                }
                                if(evalDate2.getText().toString().equals("")){
                                    evalDate2.setError(getString(R.string.please_date));
                                }
                                if(evalDate3.getText().toString().equals("")){
                                    evalDate3.setError(getString(R.string.please_date));
                                }
                                if(evalDate4.getText().toString().equals("")){
                                    evalDate4.setError(getString(R.string.please_date));
                                }
                                if(consultant.getText().toString().equals("")){
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