package gui;

import android.annotation.SuppressLint;
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
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cli.organization.data.geo.Country;
import cli.user.User;
import gui.adapters.PhoneCodeAdapter;
import misc.FieldChecker;
import misc.PasswordFormatter;
import otea.connection.controller.UsersController;
import session.FileManager;
import session.Session;
import session.StringPasser;

public class EditUser extends AppCompatActivity {

    private List<Country> countries;

    PhoneCodeAdapter[] phoneCodeAdapter={null};

    ImageButton helpButton;

    ImageView profilePhoto;

    Uri imageUri;

    InputStream profilePhotoUsr;

    Button uploadPhoto;

    GridLayout gridLayout;

    Boolean photoHasChanged;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ConstraintLayout finalBackground=findViewById(R.id.final_background);
        finalBackground.setVisibility(View.GONE);
        gridLayout=findViewById(R.id.layout);
        helpButton=findViewById(R.id.helpButton);


        User user=Session.getInstance().getUser();


        String[] telephone=user.getTelephone().split(" ");

        countries= new ArrayList<>();
        countries.add(new Country("-2","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-",""));
        countries.addAll(Session.getInstance().getCountries());
        phoneCodeAdapter[0] = new PhoneCodeAdapter(EditUser.this,countries);
        phoneCodeAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);



        Drawable correct = ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_check_circle_24);

        Spinner phoneCode1=findViewById(R.id.phonecode1);
        phoneCode1.setAdapter(phoneCodeAdapter[0]);
        phoneCode1.setEnabled(true);



        EditText firstNameField=(EditText)findViewById(R.id.first_name_reg);
        firstNameField.setText(user.getFirst_name());
        EditText lastNameField=(EditText)findViewById(R.id.last_name_reg);
        lastNameField.setText(user.getLast_name());
        EditText passwordField=(EditText)findViewById(R.id.password_reg);
        EditText telephoneField=(EditText)findViewById(R.id.telephone_reg);
        telephoneField.setText(telephone[1]);

        profilePhoto=findViewById(R.id.profilePhoto);

        if(ProfilePhotoUtil.getInstance().getImgUser()!=null) {
            profilePhoto.setImageBitmap(ProfilePhotoUtil.getInstance().getImgUser());
        }
        photoHasChanged=false;

        uploadPhoto=findViewById(R.id.uploadPhoto);


        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        try {
                            profilePhotoUsr = getContentResolver().openInputStream(uri);
                            profilePhoto.setImageURI(uri);
                            photoHasChanged=true;
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }catch(NullPointerException ignored){}
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
                            photoHasChanged=true;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch(NullPointerException ignored){}
                    }
                });

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
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

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    msg="Para poder editar su usuario, deberá rellenar los siguientes campos:" +
                            "<ul>" +
                            "<li><b>Nombre: </b>En este campo deberá introducir su nombre de pila.</li>" +
                            "<li><b>Apellidos: </b>En este campo deberá introducir su apellido o apellidos.</li>" +
                            "<li><b>Contraseña: </b>En este campo deberá introducir la contraseña que desea utilizar para el acceso desde su cuenta.</li>" +
                            "<li><b>Número de teléfono: </b>En este campo deberá introducir su número de teléfono. Deberá seleccionar el país de su número de teléfono para un correcto registro del mismo.</li>" +
                            "</ul>" +
                            "<i> Opcionalmente puede añadir una foto de perfil para su usuario presionando sobre <b>Cambiar foto</b></i>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    msg="Afin de modifier votre nom d'utilisateur, vous devez remplir les champs suivants:" +
                            "<ul>" +
                            "<li><b>Prénom: </b>Dans ce champ, vous devez saisir votre prénom.</li>" +
                            "<li><b>Nom de famille: </b>Dans ce champ, vous devez saisir votre ou vos noms de famille.</li>" +
                            "<li><b>Mot de passe: </b>Dans ce champ, vous devez saisir le mot de passe que vous souhaitez utiliser pour accéder à votre compte.</li>" +
                            "<li><b>Numéro de téléphone: </b>Dans ce champ, vous devez saisir votre numéro de téléphone. Vous devez sélectionner le pays de votre numéro de téléphone pour une inscription correcte.</li>" +
                            "</ul>" +
                            "<i> Vous pouvez éventuellement ajouter une photo de profil pour votre utilisateur en cliquant sur <b>Changer de photo</b></i>";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    msg="Zure erabiltzaile-izena editatzeko, eremu hauek bete behar dituzu:" +
                            "<ul>" +
                            "<li><b>Lehenengo izena: </b>Eremu honetan zure izena idatzi behar duzu.</li>" +
                            "<li><b>Azken izena: </b>Eremu honetan zure abizenak edo abizenak idatzi behar dituzu.</li>" +
                            "<li><b>Pasahitza: </b>Eremu honetan zure kontutik sartzeko erabili nahi duzun pasahitza idatzi behar duzu.</li>" +
                            "<li><b>Telefono zenbakia: </b>Eremu honetan zure telefono zenbakia idatzi behar duzu. Erregistro zuzena egiteko, zure telefono-zenbakiaren herrialdea hautatu behar duzu.</li>" +
                            "</ul>" +
                            "<i> Aukeran zure erabiltzailearen profileko argazki bat gehi dezakezu <b>Aldatu argazkia</b></i> sakatuta." ;
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    msg="Per poder editar el vostre usuari, haureu d'emplenar els camps següents:" +
                            "<ul>" +
                            "<li><b>Nom: </b>En aquest camp haureu d'introduir el vostre nom de pila.</li>" +
                            "<li><b>Cognoms: </b>En aquest camp haureu d'introduir el vostre cognom o cognoms.</li>" +
                            "<li><b>Contrasenya: </b>En aquest camp haureu d'introduir la contrasenya que voleu utilitzar per accedir al vostre compte.</li>" +
                            "<li><b>Número de telèfon: </b>En aquest camp haureu d'introduir el vostre número de telèfon. Haureu de seleccionar el país del vostre número de telèfon per a un correcte registre.</li>" +
                            "</ul>" +
                            "<i> Opcionalment podeu afegir una foto de perfil per al vostre usuari prement sobre <b>Canviar foto</b></i>" ;
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    msg="Om uw gebruikersnaam te bewerken, moet u de volgende velden invullen:" +
                            "<ul>" +
                            "<li><b>Voornaam: </b>In dit veld moet u uw voornaam invoeren.</li>" +
                            "<li><b>Achternaan: </b>In dit veld moet u uw achternaam of achternamen invoeren.</li>" +
                            "<li><b>Wachtwoord: </b>In dit veld moet u het wachtwoord invoeren dat u wilt gebruiken voor toegang vanuit uw account.</li>" +
                            "<li><b>Telefoonnummer: </b>In dit veld moet u uw telefoonnummer invoeren. Voor een correcte registratie moet u het land van uw telefoonnummer selecteren.</li>" +
                            "</ul>" +
                            "<i> U kunt optioneel een profielfoto voor uw gebruiker toevoegen door op <b>Foto wijzigen</b></i> te klikken" ;
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    msg="Para editar o teu nome de usuario, debes cubrir os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Nome: </b>Neste campo debes introducir o teu nome.</li>" +
                            "<li><b>Apelido: </b>Neste campo debes introducir o teu apelido ou apelidos.</li>" +
                            "<li><b>Contrasinal: </b>Neste campo debes introducir o contrasinal que queres utilizar para acceder desde a túa conta.</li>" +
                            "<li><b>Número de teléfono: </b>Neste campo debes introducir o teu número de teléfono. Debe seleccionar o país do seu número de teléfono para o rexistro correcto.</li>" +
                            "</ul>" +
                            "<i> Opcionalmente podes engadir unha foto de perfil para o teu usuario facendo clic en <b>Cambiar foto</b></i>";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    msg="Um Ihren Benutzernamen zu bearbeiten, müssen Sie die folgenden Felder ausfüllen:" +
                            "<ul>" +
                            "<li><b>Vorname:</b>In diesem Feld müssen Sie Ihren Vornamen eingeben.</li>" +
                            "<li><b>Nachname:</b>In diesem Feld müssen Sie Ihren oder Ihre Nachnamen eingeben.</li>" +
                            "<li><b>Passwort:</b>In diesem Feld müssen Sie das Passwort eingeben, das Sie für den Zugriff von Ihrem Konto aus verwenden möchten.</li>" +
                            "<li><b>Telefonnummer:</b>In dieses Feld müssen Sie Ihre Telefonnummer eingeben. Für eine korrekte Registrierung müssen Sie das Land Ihrer Telefonnummer auswählen.</li>" +
                            "</ul>" +
                            "<i> Sie können optional ein Profilfoto für Ihren Benutzer hinzufügen, indem Sie auf <b>Foto ändern</b></i> klicken";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    msg="Per modificare il tuo nome utente, devi compilare i seguenti campi:" +
                            "<ul>" +
                            "<li><b>Nome: </b>in questo campo devi inserire il tuo nome.</li>" +
                            "<li><b>Cognome: </b>in questo campo devi inserire il tuo cognome o i tuoi cognomi.</li>" +
                            "<li><b>Password: </b>in questo campo devi inserire la password che desideri utilizzare per l'accesso dal tuo account.</li>" +
                            "<li><b>Numero di telefono: </b>In questo campo devi inserire il tuo numero di telefono. Devi selezionare il Paese del tuo numero di telefono per una corretta registrazione.</li>" +
                            "</ul>" +
                            "<i> Puoi facoltativamente aggiungere una foto del profilo per il tuo utente facendo clic su <b>Cambia foto</b></i>";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    msg="Para editar o seu nome de utilizador, deve preencher os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Primeiro nome: </b>Neste campo deve introduzir o seu primeiro nome.</li>" +
                            "<li><b>Sobrenome: </b>Neste campo deve introduzir o seu apelido ou apelidos.</li>" +
                            "<li><b>Senha: </b>Neste campo deve introduzir a senha que pretende utilizar para aceder à sua conta.</li>" +
                            "<li><b>Número de telefone: </b>Neste campo deve introduzir o seu número de telefone. Deve selecionar o país do seu número de telefone para o registo correto.</li>" +
                            "</ul>" +
                            "<i> Opcionalmente, pode adicionar uma fotografia de perfil para o seu utilizador clicando em <b>Alterar fotografia</b></i>";
                }else{
                    msg="In order to edit your username, you must fill out the following fields:" +
                            "<ul>" +
                            "<li><b>First name: </b>In this field you must enter your first name.</li>" +
                            "<li><b>Last name: </b>In this field you must enter your last name or last names.</li>" +
                            "<li><b>Password: </b>In this field you must enter the password you want to use for access from your account.</li>" +
                            "<li><b>Phone number: </b>In this field you must enter your phone number. You must select the country of your telephone number for correct registration.</li>" +
                            "</ul>" +
                            "<i> You can optionally add a profile photo for your user by clicking on <b>Change photo</b></i>" +
                            "<br><b>Do not forget to accept that <i>Fundación Miradas</i> keeps records of your data in accordance with the Organic Law on Data Protection.</b>";
                }
                new AlertDialog.Builder(EditUser.this)
                        .setTitle(R.string.help)
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setMessage(Html.fromHtml(msg,0))
                        .create().show();
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
                        if(!inputText.isEmpty()){
                            firstNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                        }else{
                            firstNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                        }
                        user.setFirst_name(inputText);
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
                        if(!inputText.isEmpty()){
                            lastNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                        }else{
                            lastNameField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                        }
                        user.setLast_name(inputText);
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
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                        }
                        else{
                            passwordField.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
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
                if (FieldChecker.isACorrectPhone(telephone[0]+telephone[1])) {
                    telephoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                    telephoneField.setError(null);
                } else {
                    telephoneField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
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
                        if (FieldChecker.isACorrectPhone(telephone[0]+telephone[1])) {
                            telephoneField.setCompoundDrawablesWithIntrinsicBounds(null,null,correct,null);
                            telephoneField.setError(null);
                        } else {
                            telephoneField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //No es necesario introducir nada aquí
                    }


                }
        );


        for(int i=1;i<countries.size();i++){
            if(countries.get(i).getPhone_code().equals(telephone[0])){
                phoneCode1.setSelection(i);
                break;
            }
        }




        Button register=findViewById(R.id.register_finished);
        register.setEnabled(true);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finalBackground.setVisibility(View.VISIBLE);
                //gridLayout.setVisibility(View.GONE);
                AwesomeProgressDialog chargingScreenDialog=new AwesomeProgressDialog(EditUser.this)
                        .setTitle(R.string.please_wait_save_changes)
                        .setMessage(R.string.please_wait)
                        .setColoredCircle(R.color.miradas_color)
                        .setCancelable(false);
                chargingScreenDialog.show();
                String first_name=firstNameField.getText().toString();
                String last_name=lastNameField.getText().toString();
                String password= passwordField.getText().toString();


                if(!first_name.isEmpty() && !last_name.isEmpty() && FieldChecker.passwordHasCorrectFormat(password) && FieldChecker.isACorrectPhone(telephone[0]+telephone[1])){
                    new Thread(()->{
                        String imgUsrName=user.getProfilePhoto();
                        if(photoHasChanged){
                            if(imgUsrName.isEmpty()){
                                imgUsrName="USER_" + (user.getEmailUser().replace("@", "_").replace(".", "_")) + ".webp";;
                            }
                            FileManager.uploadFile(profilePhotoUsr, "profile-photos", imgUsrName);
                            try{
                                profilePhotoUsr.close();
                                List<String> photo=new ArrayList<>();
                                photo.add(imgUsrName);
                                FileManager.downloadPhotosProfileAsync(photo, new FileManager.PhotosDownloadCallback() {
                                    @Override
                                    public void onPhotoDownloadSuccess(String fileName, ByteArrayOutputStream stream) {
                                        ProfilePhotoUtil.getInstance().setImgUser(ProfilePhotoUtil.getBitmapFromStream(stream));
                                    }

                                    @Override
                                    public void onPhotoDownloadFailure(String fileName, Exception e) {

                                    }
                                });
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                        User usr=new User(user.getEmailUser(),user.getUserType(),first_name,last_name, PasswordFormatter.codify(password),telephone[0]+" "+telephone[1],user.getIdOrganization(),user.getOrganizationType(),user.getIllness(),imgUsrName,user.getIsActive());
                        UsersController.Update(user.getEmailUser(),usr);

                        Session.getInstance().setUser(usr);

                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        StringPasser.createInstance(R.string.user_modified,R.string.choose_other);
                        StringPasser.getInstance().setFlag(1);
                        runOnUiThread(()->{
                            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
                            startActivity(intent);
                        });
                    }).start();

                }
                else{
                    chargingScreenDialog.hide();
                    //finalBackground.setVisibility(View.VISIBLE);
                    //gridLayout.setVisibility(View.GONE);
                    int idTitle=-1;
                    int numErrors=0;
                    String msg="<ul>";
                    if(first_name.equals("")){
                        firstNameField.setError(getString(R.string.mandatory_first_name));
                        msg+="<li><b>"+getString(R.string.mandatory_first_name)+"<b></li>";
                        numErrors++;
                    }
                    if(last_name.equals("")){
                        lastNameField.setError(getString(R.string.mandatory_last_name));
                        msg+="<li><b>"+getString(R.string.mandatory_last_name)+"<b></li>";
                        numErrors++;
                    }
                    if(!FieldChecker.passwordHasCorrectFormat(password)){
                        passwordField.setError(getString(R.string.wrong_password));
                        msg+="<li><b>"+getString(R.string.wrong_password)+"<b></li>";
                        numErrors++;
                    }
                    if(!FieldChecker.isACorrectPhone(telephone[0]+telephone[1])){
                        telephoneField.setError(getString(R.string.wrong_phone));
                        msg+="<li><b>"+getString(R.string.wrong_phone)+"<b></li>";
                        numErrors++;
                    }
                    msg+="</ul>";
                    if(numErrors>1){
                        idTitle=R.string.errors;
                    }else{
                        idTitle=R.string.error;
                    }
                    new AwesomeErrorDialog(EditUser.this)
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
                }



            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100 && data.getData()!=null) {
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}