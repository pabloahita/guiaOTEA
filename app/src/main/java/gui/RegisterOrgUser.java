package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cli.organization.Organization;
import cli.organization.data.Address;
import cli.user.User;
import misc.FieldChecker;
import misc.PasswordCodifier;
import otea.connection.caller.UsersCaller;

public class RegisterOrgUser extends AppCompatActivity {

    User aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_org_user);

        EditText firstNameField=(EditText)findViewById(R.id.first_name_reg);
        EditText lastNameField=(EditText)findViewById(R.id.last_name_reg);
        EditText emailField=(EditText)findViewById(R.id.email_reg);
        EditText passwordField=(EditText)findViewById(R.id.password_reg);
        EditText telephoneField=(EditText)findViewById(R.id.telephone_reg);

        Button button=(Button) findViewById(R.id.register_finished);
        User[] user={null};
        String userType=getIntent().getStringExtra("type");
        String[] passwordKey={""};
        String[] userKey={""};

        ProgressBar progressBar=findViewById(R.id.progressBar);
        TextView textView=findViewById(R.id.turning_back);

        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);


        if(userType.equals("director")){
            user[0]=(User) getIntent().getSerializableExtra("director");
            passwordKey[0]="passwordDirector";
            userKey[0]="director";
            button.setText(getString(R.string.add_director));
        }


        if(user[0]!=null){
            firstNameField.setText(user[0].getFirst_name());
            lastNameField.setText(user[0].getLast_name());
            emailField.setText(user[0].getEmailUser());
            passwordField.setText(user[0].getPassword());
            telephoneField.setText(user[0].getTelephone()+"");

        }


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
                        if(user[0]!=null){

                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String inputText=s.toString();
                        Pattern patronPassword=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$&%#.!\"^¨_:;/+><()=¿?¡!-]).{6,}$");
                        Matcher matcher= patronPassword.matcher(inputText);
                        if(matcher.matches()){
                            passwordField.setError(null);
                            getIntent().putExtra(passwordKey[0],inputText);
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

        Button register=findViewById(R.id.register_finished);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                emailField.setEnabled(false);
                passwordField.setEnabled(false);
                telephoneField.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String first_name=firstNameField.getText().toString();
                        String last_name=lastNameField.getText().toString();
                        String email=emailField.getText().toString();
                        String password= passwordField.getText().toString();
                        String telephone=telephoneField.getText().toString();
                        int idOrganization=getIntent().getIntExtra("idOrganization",-1);
                        String orgType=getIntent().getStringExtra("orgType");
                        String illness=getIntent().getStringExtra("illness");
                        if(!first_name.equals("") && !last_name.equals("") && FieldChecker.emailHasCorrectFormat(email) && FieldChecker.passwordHasCorrectFormat(password) && (FieldChecker.isASpanishNumber(telephone) || FieldChecker.isAForeignNumber(telephone))){
                            long phone=Long.parseLong(telephone);
                            User user=new User(email,"ORGANIZATION",first_name,last_name, password,phone,idOrganization,orgType,illness);
                            Intent intent=new Intent(getApplicationContext(),gui.RegisterOrganization.class);
                            intent.putExtra(userKey[0],user);
                            intent.putExtra(passwordKey[0],password);
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            intent.putExtra("idOrganization",idOrganization);
                            intent.putExtra("orgType",orgType);
                            intent.putExtra("illness",illness);
                            intent.putExtra("address",getIntent().getSerializableExtra("address"));
                            intent.putExtra("organization",getIntent().getSerializableExtra("organization"));
                            intent.putExtra("country",getIntent().getSerializableExtra("country"));
                            intent.putExtra("province",getIntent().getSerializableExtra("province"));
                            intent.putExtra("region",getIntent().getSerializableExtra("region"));
                            intent.putExtra("city",getIntent().getSerializableExtra("city"));
                            intent.putExtra("zipCode",getIntent().getStringExtra("zipCode"));
                            intent.putExtra("telephone",getIntent().getStringExtra("telephone"));
                            intent.putExtra("information",getIntent().getStringExtra("information"));
                            intent.putExtra("email",getIntent().getStringExtra("email"));
                            intent.putExtra("centers", getIntent().getSerializableExtra("centers"));
                            startActivity(intent);
                        }
                        else{
                            firstNameField.setEnabled(true);
                            lastNameField.setEnabled(true);
                            emailField.setEnabled(true);
                            passwordField.setEnabled(true);
                            telephoneField.setEnabled(true);

                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
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
                }, 100);




            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),gui.RegisterOrganization.class);
            intent.putExtra("user",getIntent().getSerializableExtra("user"));
            intent.putExtra("idOrganization",getIntent().getIntExtra("idOrganization",-1));
            intent.putExtra("orgType",getIntent().getStringExtra("orgType"));
            intent.putExtra("illness",getIntent().getStringExtra("illness"));
            intent.putExtra("address",getIntent().getSerializableExtra("address"));
            intent.putExtra("organization",getIntent().getSerializableExtra("organization"));
            intent.putExtra("country",getIntent().getSerializableExtra("country"));
            intent.putExtra("province",getIntent().getSerializableExtra("province"));
            intent.putExtra("region",getIntent().getSerializableExtra("region"));
            intent.putExtra("city",getIntent().getSerializableExtra("city"));
            intent.putExtra("zipCode",getIntent().getStringExtra("zipCode"));
            intent.putExtra("telephone",getIntent().getStringExtra("telephone"));
            intent.putExtra("information",getIntent().getStringExtra("information"));
            intent.putExtra("email",getIntent().getStringExtra("email"));
            intent.putExtra("centers", getIntent().getSerializableExtra("centers"));
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}