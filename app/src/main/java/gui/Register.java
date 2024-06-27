package gui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cli.organization.data.geo.Country;
import cli.user.User;
import session.Session;
import gui.adapters.PhoneCodeAdapter;
import misc.FieldChecker;
import cli.organization.Organization;
import misc.PasswordFormatter;
import otea.connection.controller.UsersController;
import session.FileManager;

import java.util.Locale;
import java.util.regex.*;

public class Register extends AppCompatActivity {

    private List<Organization> evaluatedOrganizations;

    private List<Country> countries;

    PhoneCodeAdapter[] phoneCodeAdapter={null};

    ImageView profilePhoto;

    Uri imageUri;

    InputStream profilePhotoUsr;

    Button uploadPhoto;

    GridLayout gridLayout;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ConstraintLayout finalBackground=findViewById(R.id.final_background);
        finalBackground.setVisibility(View.GONE);
        gridLayout=findViewById(R.id.layout);

        Request request=Session.getCurrRequest();
        String[] telephone=new String[2];

        countries= new ArrayList<>();
        countries.add(new Country("-2","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-",""));
        countries.addAll(Session.getInstance().getCountries());
        phoneCodeAdapter[0] = new PhoneCodeAdapter(Register.this,countries);
        phoneCodeAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

        Drawable correct = ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_check_circle_24);


        Spinner phoneCode1=findViewById(R.id.phonecode1);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);

        EditText firstNameField=(EditText)findViewById(R.id.first_name_reg);
        EditText lastNameField=(EditText)findViewById(R.id.last_name_reg);
        EditText passwordField=(EditText)findViewById(R.id.password_reg);
        EditText telephoneField=(EditText)findViewById(R.id.telephone_reg);

        profilePhoto=findViewById(R.id.profilePhoto);

        uploadPhoto=findViewById(R.id.uploadPhoto);


        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        try {
                            profilePhotoUsr = getContentResolver().openInputStream(uri);
                            profilePhoto.setImageURI(uri);
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
                            profilePhotoUsr = bs;
                            profilePhoto.setImageBitmap(bitmap);
                            bs.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.select_source))
                        .setPositiveButton(getString(R.string.camera), (dialog, which) -> mTakePicture.launch(null))
                        .setNegativeButton(getString(R.string.gallery), (dialog, which) -> mGetContent.launch("image/*"))
                        .show();
            }
        });

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

        phoneCode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                telephone[0] = phoneCodeAdapter[0].getItem(position).getPhone_code();
                if (telephone[1].isEmpty()) {
                    telephoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                    telephoneField.setError(null);
                } else if (FieldChecker.isACorrectPhone(telephone[0]+telephone[1])) {
                    telephoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    telephoneField.setError(null);
                } else {
                    if (telephoneField.getError() == null) {
                        telephoneField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        telephoneField.setError(getString(R.string.wrong_phone));
                    } else {
                        telephoneField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        telephoneField.setError(null);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        });

        telephoneField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        //No es necesario introducir nada aquí
                        telephoneField.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        telephone[1]=s.toString();
                        if (telephone[1].isEmpty()) {
                            telephoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                            telephoneField.setError(null);
                        } else if (FieldChecker.isACorrectPhone(telephone[0]+telephone[1])) {
                            telephoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            telephoneField.setError(null);
                        } else {
                            if (telephoneField.getError() == null) {
                                telephoneField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                                telephoneField.setError(getString(R.string.wrong_phone));
                            } else {
                                telephoneField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                                telephoneField.setError(null);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );


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
                finalBackground.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.GONE);
                v.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        String first_name=firstNameField.getText().toString();
                        String last_name=lastNameField.getText().toString();
                        String password= passwordField.getText().toString();

                        if(profilePhotoUsr!=null){
                            String imgUsrName="USER_" + (request.getEmail().replace("@", "_").replace(".", "_")) + ".webp";
                            FileManager.uploadFile(profilePhotoUsr, "profile-photos", imgUsrName);
                            try{
                                profilePhotoUsr.close();
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                        if(!first_name.equals("") && !last_name.equals("") && FieldChecker.passwordHasCorrectFormat(password) && FieldChecker.isACorrectPhone(telephone[0]+telephone[1])){
                            User user=new User(request.getEmail(),request.getUserType(),first_name,last_name, PasswordFormatter.codify(password),telephone[0]+" "+telephone[1],request.getIdOrganization(),request.getOrgType(),request.getIllness(),"");
                            UsersController.Create(user);
                            RequestsController.Delete(request.getEmail());
                            Session.setCurrRequest(null);
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

                            finalBackground.setVisibility(View.VISIBLE);
                            gridLayout.setVisibility(View.GONE);
                            acceptLOPD.setEnabled(true);
                            acceptLOPD.setChecked(false);
                            if(first_name.equals("")){
                                firstNameField.setError(getString(R.string.mandatory_first_name));
                            }
                            if(last_name.equals("")){
                                lastNameField.setError(getString(R.string.mandatory_last_name));
                            }
                            if(!FieldChecker.passwordHasCorrectFormat(password)){
                                passwordField.setError(getString(R.string.wrong_password));
                            }
                            if(!FieldChecker.isACorrectPhone(telephone[0]+telephone[1])){
                                telephoneField.setError(getString(R.string.wrong_phone));
                            }
                        }
                    }
                },100);



            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
        }
    }



}