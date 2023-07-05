package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import cli.organization.data.EvaluatorTeamMember;
import cli.user.User;
import gui.adapters.OrgsAdapter;
import gui.adapters.UsersAdapter;
import misc.DateFormatter;
import otea.connection.caller.EvaluatorTeamMembersCaller;
import otea.connection.caller.EvaluatorTeamsCaller;
import otea.connection.caller.OrganizationsCaller;
import otea.connection.caller.UsersCaller;

public class RegisterNewEvaluatorTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_evaluator_team);

        List<Organization> organizations = OrganizationsCaller.GetAllEvaluatedOrganizations();
        List<User> usersEvaluator = UsersCaller.GetAllOrgUsersByOrganization(1, "EVALUATOR", "AUTISM");

        if (organizations.size() > 0 && usersEvaluator.size()>0) {

            Spinner orgSpinner = findViewById(R.id.spinner_select_organization);
            Spinner consultantSpinner = findViewById(R.id.spinner_select_consultant);
            Spinner responsibleSpinner = findViewById(R.id.spinner_select_responsible);
            Spinner professionalSpinner = findViewById(R.id.spinner_select_professional);

            TextView patient = findViewById(R.id.patientName);
            TextView relative = findViewById(R.id.relativeName);

            TextView creationDate=findViewById(R.id.creation_date);
            TextView evalDate1=findViewById(R.id.eval_date_1);
            TextView evalDate2=findViewById(R.id.eval_date_2);
            TextView evalDate3=findViewById(R.id.eval_date_3);
            TextView evalDate4=findViewById(R.id.eval_date_4);

            TextView observations=findViewById(R.id.observations);

            OrgsAdapter[] orgsAdapter={new OrgsAdapter(getApplicationContext(),organizations)};
            UsersAdapter[] usersAdapter=new UsersAdapter[3];

            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            orgSpinner.setAdapter(orgsAdapter[0]);

            usersAdapter[1]=new UsersAdapter(getApplicationContext(),usersEvaluator);
            usersAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            responsibleSpinner.setAdapter(usersAdapter[1]);

            usersAdapter[2]=new UsersAdapter(getApplicationContext(),usersEvaluator);
            usersAdapter[2].setDropDownViewResource(R.layout.spinner_item_layout);
            professionalSpinner.setAdapter(usersAdapter[2]);

            Organization[] organizationSelected=new Organization[1];

            consultantSpinner.setEnabled(false);

            final List<User>[] users = new List[]{new LinkedList<>()};

            User[] membersSelected=new User[3];


            ProgressBar progressBar=findViewById(R.id.progressBar);
            TextView textLoading=findViewById(R.id.textProgress);

            progressBar.setVisibility(View.GONE);
            textLoading.setVisibility(View.GONE);


            orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    organizationSelected[0]= (Organization) parent.getItemAtPosition(position);
                    users[0] = UsersCaller.GetAllOrgUsersByOrganization(organizationSelected[0].getIdOrganization(), organizationSelected[0].getOrganizationType(), organizationSelected[0].getIllness());
                    usersAdapter[0] = new UsersAdapter(getApplicationContext(), users[0]);
                    usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                    consultantSpinner.setAdapter(usersAdapter[0]);
                    consultantSpinner.setEnabled(true);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            consultantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    membersSelected[0]=(User) parent.getItemAtPosition(position);

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

            Button add=findViewById(R.id.add);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orgSpinner.setEnabled(false);
                    consultantSpinner.setEnabled(false);
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

                    progressBar.setVisibility(View.VISIBLE);
                    textLoading.setVisibility(View.VISIBLE);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(organizationSelected[0]!=null && membersSelected[0]!=null && membersSelected[1]!=null && membersSelected[2]!=null && !patient.getText().toString().equals("") && !relative.getText().toString().equals("") && !creationDate.getText().toString().equals("") && !evalDate1.getText().toString().equals("") && !evalDate2.getText().toString().equals("") && !evalDate3.getText().toString().equals("") && !evalDate4.getText().toString().equals("")){
                                int idEvaluatorTeam= EvaluatorTeamsCaller.GetAllByOrganization(1,"EVALUATOR","AUTISM").size()+1;

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                long creation_date= DateFormatter.formatDate(creationDate.getText().toString());
                                long eval_date1= DateFormatter.formatDate(evalDate1.getText().toString());
                                long eval_date2= DateFormatter.formatDate(evalDate2.getText().toString());
                                long eval_date3= DateFormatter.formatDate(evalDate3.getText().toString());
                                long eval_date4= DateFormatter.formatDate(evalDate4.getText().toString());
                                EvaluatorTeam evaluatorTeam=new EvaluatorTeam(idEvaluatorTeam, creation_date, 1, "EVALUATOR", "AUTISM", membersSelected[0].getEmailUser(), membersSelected[1].getEmailUser(), membersSelected[2].getEmailUser(), patient.getText().toString(), relative.getText().toString(),eval_date1,eval_date2,eval_date3,eval_date4,observations.getText().toString());

                                List<EvaluatorTeamMember> members=new LinkedList<>();
                                for(User user:membersSelected){
                                    EvaluatorTeamMember member=new EvaluatorTeamMember(user.getEmailUser(),idEvaluatorTeam,1,"EVALUATOR","AUTISM");
                                    members.add(member);
                                }

                                EvaluatorTeamsCaller.Create(evaluatorTeam);


                                Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
                                intent.putExtra("user",getIntent().getSerializableExtra("user"));
                                progressBar.setVisibility(View.GONE);
                                textLoading.setVisibility(View.GONE);
                                startActivity(intent);


                            }
                            else{
                                orgSpinner.setEnabled(true);
                                consultantSpinner.setEnabled(true);
                                responsibleSpinner.setEnabled(true);
                                professionalSpinner.setEnabled(true);
                                evalDate1.setEnabled(true);
                                evalDate2.setEnabled(true);
                                evalDate3.setEnabled(true);
                                evalDate4.setEnabled(true);

                                progressBar.setVisibility(View.GONE);
                                textLoading.setVisibility(View.GONE);

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
                            }
                        }
                    }, 100);
                }
            });


        }
    }

}