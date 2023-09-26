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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;

import cli.user.User;
import gui.adapters.OrgsAdapter;
import misc.FieldChecker;
import cli.organization.Organization;
import misc.PasswordCodifier;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.UsersController;

import java.util.Locale;
import java.util.regex.*;

public class Register extends AppCompatActivity {

    private List<Organization> evaluatedOrganizations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ProgressBar progressBar=findViewById(R.id.loading_reg);
        progressBar.setVisibility(View.GONE);
        TextView textLoading=findViewById(R.id.please_wait);
        textLoading.setVisibility(View.GONE);
        Spinner spinnerUserType = findViewById(R.id.spinner_menu_user_type);
        Spinner spinnerOrgEvaluated = findViewById(R.id.spinner_menu_org_evaluated);
        spinnerOrgEvaluated.setEnabled(false);
        String[] selectedUserType = {null};
        OrgsAdapter[] adapter={null};
        int[] idOrganization = {-1};
        String[] orgType = {""};
        String[] illness = {""};

        EditText firstNameField=(EditText)findViewById(R.id.first_name_reg);
        EditText lastNameField=(EditText)findViewById(R.id.last_name_reg);
        EditText emailField=(EditText)findViewById(R.id.email_reg);
        EditText passwordField=(EditText)findViewById(R.id.password_reg);
        EditText telephoneField=(EditText)findViewById(R.id.telephone_reg);

        firstNameField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        firstNameField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(inputText.equals("")){
                            firstNameField.setError(getString(R.string.mandatory_first_name));
                        }else{
                            firstNameField.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }
                }
        );

        lastNameField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        lastNameField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(inputText.equals("")){
                            lastNameField.setError(getString(R.string.mandatory_first_name));
                        }else{
                            lastNameField.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }
                }
        );

        emailField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        emailField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(FieldChecker.emailHasCorrectFormat(inputText)){
                            emailField.setError(null);
                        }
                        else{
                            if(inputText.equals("")){
                                emailField.setError(getString(R.string.mandatory_email));
                            }
                            else{
                                emailField.setError(getString(R.string.wrong_email));
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );

        passwordField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        passwordField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        Pattern patronPassword=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$&%#.!\"^¨_:;/+><()=¿?¡!-]).{6,}$");
                        Matcher matcher= patronPassword.matcher(inputText);
                        if(matcher.matches()){
                            passwordField.setError(null);
                        }
                        else{
                            passwordField.setError(getString(R.string.mandatory_password));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );

        telephoneField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        telephoneField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        if(inputText.equals("")){
                            telephoneField.setError(getString(R.string.mandatory_phone));
                        }
                        else if(FieldChecker.isASpanishNumber(inputText)||FieldChecker.isAForeignNumber(inputText)){
                            telephoneField.setError(null);
                        }
                        else{
                            telephoneField.setError(getString(R.string.wrong_phone));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );
        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUserType[0] = parent.getItemAtPosition(position).toString();

                if (selectedUserType[0].equals(getString(R.string.evaluated_org_user_reg))) {
                    // Activar el spinner_menu_org_evaluated
                    obtainOrganizations();
                    adapter[0]= new OrgsAdapter(Register.this, evaluatedOrganizations);
                    adapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
                    spinnerOrgEvaluated.setAdapter(adapter[0]);
                    spinnerOrgEvaluated.setEnabled(true);
                } else {
                    // Desactivar el spinner_menu_org_evaluated
                    spinnerOrgEvaluated.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        Organization[] selectedOrganization={null};
        spinnerOrgEvaluated.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerOrgEvaluated.isEnabled()) {
                    selectedUserType[0] = adapter[0].getText();
                    selectedOrganization[0] = adapter[0].getItem(position);
                    idOrganization[0]=selectedOrganization[0].getIdOrganization();
                    orgType[0]=selectedOrganization[0].getOrganizationType();
                    illness[0]=selectedOrganization[0].getIllness();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        CheckBox acceptLOPD=findViewById(R.id.accept_LOPD);

        Button register=findViewById(R.id.register_finished);
        register.setEnabled(false);
        acceptLOPD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                      if (isChecked) {
                          register.setEnabled(true);
                      } else {
                          register.setEnabled(false);
                      }
                  }
              }
        );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                textLoading.setVisibility(View.VISIBLE);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                emailField.setEnabled(false);
                passwordField.setEnabled(false);
                telephoneField.setEnabled(false);
                spinnerUserType.setEnabled(false);
                spinnerOrgEvaluated.setEnabled(false);
                acceptLOPD.setEnabled(false);
                register.setEnabled(false);
                v.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        String first_name=firstNameField.getText().toString();
                        String last_name=lastNameField.getText().toString();
                        String email=emailField.getText().toString();
                        String password= passwordField.getText().toString();
                        String telephone=telephoneField.getText().toString();

                        if(selectedUserType[0].equals(getString(R.string.fMiradas_user_reg))){
                            idOrganization[0] =1;
                            orgType[0] ="EVALUATOR";
                            illness[0] ="AUTISM";
                        }
                        if(!first_name.equals("") && !last_name.equals("") && FieldChecker.emailHasCorrectFormat(email) && FieldChecker.passwordHasCorrectFormat(password) && (FieldChecker.isASpanishNumber(telephone) || FieldChecker.isAForeignNumber(telephone))){
                            long phone=Long.parseLong(telephone);
                            User user=new User(email,"ORGANIZATION",first_name,last_name, PasswordCodifier.codify(password),phone,idOrganization[0],orgType[0],illness[0]);
                            UsersController.Create(user);
                            Log.d("USER_REGISTERED","USER REGISTERED");
                            Intent intent=new Intent(getApplicationContext(),gui.MainActivity.class);
                            String messageSuccess="";
                            if(Locale.getDefault().getLanguage().equals("es")){
                                messageSuccess="El usuario "+first_name+" "+last_name+" ha sido registrado satisfactoriamente";
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                messageSuccess="L'utilisateur "+first_name+" "+last_name+"s'est enregistré avec succès";
                            }else{
                                messageSuccess="User "+first_name+" "+last_name+" was registered successfully";
                            }
                            Toast.makeText(getApplicationContext(),messageSuccess,Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            textLoading.setVisibility(View.GONE);
                            firstNameField.setEnabled(true);
                            lastNameField.setEnabled(true);
                            emailField.setEnabled(true);
                            passwordField.setEnabled(true);
                            telephoneField.setEnabled(true);
                            spinnerUserType.setEnabled(true);
                            if(spinnerUserType.getSelectedItem().equals(getString(R.string.evaluated_org_user_reg))){
                                spinnerOrgEvaluated.setEnabled(true);
                            }
                            acceptLOPD.setEnabled(true);
                            acceptLOPD.setChecked(false);
                            if(first_name.equals("")){
                                firstNameField.setError(getString(R.string.mandatory_first_name));
                            }
                            if(last_name.equals("")){
                                lastNameField.setError(getString(R.string.mandatory_last_name));
                            }
                            if(!FieldChecker.emailHasCorrectFormat(email)){
                                emailField.setError(getString(R.string.wrong_email));
                            }
                            if(!FieldChecker.passwordHasCorrectFormat(password)){
                                passwordField.setError(getString(R.string.wrong_password));
                            }
                            if(!(FieldChecker.isASpanishNumber(telephone) || FieldChecker.isAForeignNumber(telephone))){
                                telephoneField.setError(getString(R.string.wrong_phone));
                            }
                        }
                    }
                },100);



            }
        });

    }

    public List<Organization> obtainOrganizations() {
        if (evaluatedOrganizations == null) {
            evaluatedOrganizations= OrganizationsController.GetAllEvaluatedOrganizations();
        }
        return evaluatedOrganizations;
    }





}