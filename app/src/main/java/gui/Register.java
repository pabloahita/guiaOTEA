package gui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import cli.organization.data.geo.Country;
import cli.user.User;
import otea.connection.controller.CountriesController;
import session.RegUserUtil;
import session.Session;
import gui.adapters.PhoneCodeAdapter;
import misc.FieldChecker;
import cli.organization.Organization;
import misc.PasswordFormatter;
import otea.connection.controller.UsersController;
import session.FileManager;
import session.StringPasser;

import java.util.Locale;
import java.util.regex.*;

public class Register extends AppCompatActivity {

    Organization organization;

    TextView requestInfo;

    private List<Country> countries;

    PhoneCodeAdapter[] phoneCodeAdapter={null};

    ImageView profilePhoto;

    Uri imageUri;

    InputStream profilePhotoUsr;

    Button uploadPhoto;

    ConstraintLayout base;

    User user;

    ConstraintLayout finalBackground;

    ImageButton helpButton;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        finalBackground=findViewById(R.id.final_background);
        finalBackground.setVisibility(View.GONE);
        base=findViewById(R.id.base);
        requestInfo=findViewById(R.id.requestInfo);
        helpButton=findViewById(R.id.helpButton);
        user= RegUserUtil.getInstance().getUser();
        organization=RegUserUtil.getInstance().getOrganization();

        String reqInfoTxt="<ul><li><b>"+getString(R.string.users_email)+": </b><i>"+user.getEmailUser()+"</i></li>"+
                "<li><b>"+getString(R.string.org_name)+": </b><i>"+organization.getNameOrg()+"</i></li></ul>";

        requestInfo.setText(Html.fromHtml(reqInfoTxt,0));

        String[] telephone=new String[2];

        telephone[0]="";
        telephone[1]="";

        countries= new ArrayList<>();
        countries.add(new Country("-2","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-",""));
        countries.addAll(CountriesController.GetAll(Locale.getDefault().getLanguage()));
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

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg="";
                if(Locale.getDefault().getLanguage().equals("es")){
                    msg="Para poder registrar su usuario tras la aceptación de su solicitud, deberá rellenar los siguientes campos:" +
                            "<ul>" +
                            "<li><b>Nombre: </b>En este campo deberá introducir su nombre de pila.</li>" +
                            "<li><b>Apellidos: </b>En este campo deberá introducir su apellido o apellidos.</li>" +
                            "<li><b>Contraseña: </b>En este campo deberá introducir la contraseña que desea utilizar para el acceso desde su cuenta.</li>" +
                            "<li><b>Número de teléfono: </b>En este campo deberá introducir su número de teléfono. Deberá seleccionar el país de su número de teléfono para un correcto registro del mismo.</li>" +
                            "</ul>" +
                            "<i> Opcionalmente puede añadir una foto de perfil para su usuario presionando sobre <b>Cambiar foto</b></i>"
                            +"<br><b>No olvide aceptar que <i>Fundación Miradas</i> guarde registro sobre sus datos de acuerdo con la Ley Orgánica de Protección de Datos.</b>";
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    msg="Afin d'enregistrer votre utilisateur après avoir accepté votre demande, vous devez remplir les champs suivants :" +
                            "<ul>" +
                            "<li><b>Prénom: </b>Dans ce champ, vous devez saisir votre prénom.</li>" +
                            "<li><b>Nom de famille: </b>Dans ce champ, vous devez saisir votre ou vos noms de famille.</li>" +
                            "<li><b>Mot de passe: </b>Dans ce champ, vous devez saisir le mot de passe que vous souhaitez utiliser pour accéder à votre compte.</li>" +
                            "<li><b>Numéro de téléphone: </b>Dans ce champ, vous devez saisir votre numéro de téléphone. Vous devez sélectionner le pays de votre numéro de téléphone pour une inscription correcte.</li>" +
                            "</ul>" +
                            "<i> Vous pouvez éventuellement ajouter une photo de profil pour votre utilisateur en cliquant sur <b>Changer de photo</b></i>"
                            +"<br><b>N'oubliez pas d'accepter que la <i>Fundación Miradas</i> conserve un enregistrement de vos données conformément à la loi organique sur la protection des données.</b>";
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    msg="Zure eskaera onartu ondoren zure erabiltzailea erregistratzeko, eremu hauek bete behar dituzu:" +
                            "<ul>" +
                            "<li><b>Lehenengo izena: </b>Eremu honetan zure izena idatzi behar duzu.</li>" +
                            "<li><b>Azken izena: </b>Eremu honetan zure abizenak edo abizenak idatzi behar dituzu.</li>" +
                            "<li><b>Pasahitza: </b>Eremu honetan zure kontutik sartzeko erabili nahi duzun pasahitza idatzi behar duzu.</li>" +
                            "<li><b>Telefono zenbakia: </b>Eremu honetan zure telefono zenbakia idatzi behar duzu. Erregistro zuzena egiteko, zure telefono-zenbakiaren herrialdea hautatu behar duzu.</li>" +
                            "</ul>" +
                            "<i> Aukeran zure erabiltzailearen profileko argazki bat gehi dezakezu <b>Aldatu argazkia</b></i> sakatuta." +
                            "<br><b>Ez ahaztu onartzea <i>Fundación Miradas</i> zure datuen erregistroak gordetzen dituela Datuak Babesteko Lege Organikoaren arabera.</b>";
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    msg="Per poder registrar el vostre usuari després de l'acceptació de la vostra sol·licitud, heu d'emplenar els camps següents:" +
                            "<ul>" +
                            "<li><b>Nom: </b>En aquest camp haureu d'introduir el vostre nom de pila.</li>" +
                            "<li><b>Cognoms: </b>En aquest camp haureu d'introduir el vostre cognom o cognoms.</li>" +
                            "<li><b>Contrasenya: </b>En aquest camp haureu d'introduir la contrasenya que voleu utilitzar per accedir al vostre compte.</li>" +
                            "<li><b>Número de telèfon: </b>En aquest camp haureu d'introduir el vostre número de telèfon. Haureu de seleccionar el país del vostre número de telèfon per a un correcte registre.</li>" +
                            "</ul>" +
                            "<i> Opcionalment podeu afegir una foto de perfil per al vostre usuari prement sobre <b>Canviar foto</b></i>" +
                            "<br><b>No oblideu acceptar que <i>Fundación Miradas</i> guardi registre sobre les vostres dades d'acord amb la Llei Orgànica de Protecció de Dades.</b>";
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    msg="Om uw gebruiker te registreren nadat u uw verzoek heeft geaccepteerd, moet u de volgende velden invullen:" +
                            "<ul>" +
                            "<li><b>Voornaam: </b>In dit veld moet u uw voornaam invoeren.</li>" +
                            "<li><b>Achternaan: </b>In dit veld moet u uw achternaam of achternamen invoeren.</li>" +
                            "<li><b>Wachtwoord: </b>In dit veld moet u het wachtwoord invoeren dat u wilt gebruiken voor toegang vanuit uw account.</li>" +
                            "<li><b>Telefoonnummer: </b>In dit veld moet u uw telefoonnummer invoeren. Voor een correcte registratie moet u het land van uw telefoonnummer selecteren.</li>" +
                            "</ul>" +
                            "<i> U kunt optioneel een profielfoto voor uw gebruiker toevoegen door op <b>Foto wijzigen</b></i> te klikken" +
                            "<br><b>Vergeet niet te accepteren dat <i>Fundación Miradas</i> uw gegevens bijhoudt in overeenstemming met de organieke wet op gegevensbescherming.</b>";
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    msg="Para rexistrar o teu usuario despois de aceptar a túa solicitude, debes cubrir os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Nome: </b>Neste campo debes introducir o teu nome.</li>" +
                            "<li><b>Apelido: </b>Neste campo debes introducir o teu apelido ou apelidos.</li>" +
                            "<li><b>Contrasinal: </b>Neste campo debes introducir o contrasinal que queres utilizar para acceder desde a túa conta.</li>" +
                            "<li><b>Número de teléfono: </b>Neste campo debes introducir o teu número de teléfono. Debe seleccionar o país do seu número de teléfono para o rexistro correcto.</li>" +
                            "</ul>" +
                            "<i> Opcionalmente podes engadir unha foto de perfil para o teu usuario facendo clic en <b>Cambiar foto</b></i>" +
                            "<br><b>Non esquezas aceptar que a <i>Fundación Miradas</i> leva un rexistro dos teus datos de acordo coa Lei Orgánica de Protección de Datos.</b>";
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    msg="Um Ihren Benutzer nach Annahme Ihrer Anfrage zu registrieren, müssen Sie die folgenden Felder ausfüllen:" +
                            "<ul>" +
                            "<li><b>Vorname:</b>In diesem Feld müssen Sie Ihren Vornamen eingeben.</li>" +
                            "<li><b>Nachname:</b>In diesem Feld müssen Sie Ihren oder Ihre Nachnamen eingeben.</li>" +
                            "<li><b>Passwort:</b>In diesem Feld müssen Sie das Passwort eingeben, das Sie für den Zugriff von Ihrem Konto aus verwenden möchten.</li>" +
                            "<li><b>Telefonnummer:</b>In dieses Feld müssen Sie Ihre Telefonnummer eingeben. Für eine korrekte Registrierung müssen Sie das Land Ihrer Telefonnummer auswählen.</li>" +
                            "</ul>" +
                            "<i> Sie können optional ein Profilfoto für Ihren Benutzer hinzufügen, indem Sie auf <b>Foto ändern</b></i> klicken" +
                            "<br><b>Vergessen Sie nicht zu akzeptieren, dass die <i>Fundación Miradas</i> Aufzeichnungen über Ihre Daten gemäß dem Datenschutzgesetz führt.</b>";
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    msg="Per poter registrare il tuo utente dopo aver accettato la tua richiesta, devi compilare i seguenti campi:" +
                            "<ul>" +
                            "<li><b>Nome: </b>in questo campo devi inserire il tuo nome.</li>" +
                            "<li><b>Cognome: </b>in questo campo devi inserire il tuo cognome o i tuoi cognomi.</li>" +
                            "<li><b>Password: </b>in questo campo devi inserire la password che desideri utilizzare per l'accesso dal tuo account.</li>" +
                            "<li><b>Numero di telefono: </b>In questo campo devi inserire il tuo numero di telefono. Devi selezionare il Paese del tuo numero di telefono per una corretta registrazione.</li>" +
                            "</ul>" +
                            "<i> Puoi facoltativamente aggiungere una foto del profilo per il tuo utente facendo clic su <b>Cambia foto</b></i>" +
                            "<br><b>Non dimenticare di accettare che <i>Fundación Miradas</i> conservi un registro dei tuoi dati in conformità con la Legge organica sulla protezione dei dati.</b>";
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    msg="Para registar o seu utilizador após aceitar o seu pedido, deverá preencher os seguintes campos:" +
                            "<ul>" +
                            "<li><b>Primeiro nome: </b>Neste campo deve introduzir o seu primeiro nome.</li>" +
                            "<li><b>Sobrenome: </b>Neste campo deve introduzir o seu apelido ou apelidos.</li>" +
                            "<li><b>Senha: </b>Neste campo deve introduzir a senha que pretende utilizar para aceder à sua conta.</li>" +
                            "<li><b>Número de telefone: </b>Neste campo deve introduzir o seu número de telefone. Deve selecionar o país do seu número de telefone para o registo correto.</li>" +
                            "</ul>" +
                            "<i> Opcionalmente, pode adicionar uma fotografia de perfil para o seu utilizador clicando em <b>Alterar fotografia</b></i>" +
                            "<br><b>Não se esqueça de aceitar que a <i>Fundación Miradas</i> mantenha registos dos seus dados de acordo com a Lei Orgânica de Proteção de Dados.</b>";
                }else{
                    msg="In order to register your user after accepting your request, you must fill out the following fields:" +
                            "<ul>" +
                            "<li><b>First name: </b>In this field you must enter your first name.</li>" +
                            "<li><b>Last name: </b>In this field you must enter your last name or last names.</li>" +
                            "<li><b>Password: </b>In this field you must enter the password you want to use for access from your account.</li>" +
                            "<li><b>Phone number: </b>In this field you must enter your phone number. You must select the country of your telephone number for correct registration.</li>" +
                            "</ul>" +
                            "<i> You can optionally add a profile photo for your user by clicking on <b>Change photo</b></i>" +
                            "<br><b>Do not forget to accept that <i>Fundación Miradas</i> keeps records of your data in accordance with the Organic Law on Data Protection.</b>";
                }
                new AlertDialog.Builder(Register.this)
                        .setTitle(R.string.help)
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setMessage(Html.fromHtml(msg,0))
                        .create().show();
            }
        });


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
                        } catch (NullPointerException ignored){}
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
                        } catch (NullPointerException ignored){}
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


        CheckBox acceptLOPD=findViewById(R.id.accept_LOPD);


        Button register=findViewById(R.id.register_finished);
        register.setAlpha(0.5f);

        acceptLOPD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                      if (isChecked) {
                          register.setAlpha(1f);
                      } else {
                          register.setAlpha(0.5f);
                      }
                  }
              }
        );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finalBackground.setVisibility(View.VISIBLE);
                //gridLayout.setVisibility(View.GONE);
                //base.setVisibility(View.GONE);

                AwesomeProgressDialog chargingScreenDialog=new AwesomeProgressDialog(Register.this)
                        .setTitle(R.string.please_wait_register_user)
                        .setMessage(R.string.please_wait)
                        .setColoredCircle(R.color.miradas_color)
                        .setCancelable(false);
                chargingScreenDialog.show();
                String password=passwordField.getText().toString();
                if(!user.getFirst_name().isEmpty() && !user.getLast_name().isEmpty() && FieldChecker.passwordHasCorrectFormat(password) && FieldChecker.isACorrectPhone(telephone[0]+telephone[1]) && acceptLOPD.isChecked()){
                    new Thread(()->{
                        String imgUsrName="";
                        if(profilePhotoUsr!=null){
                            imgUsrName="USER_" + (user.getEmailUser().replace("@", "_").replace(".", "_")) + ".webp";
                            FileManager.uploadFile(profilePhotoUsr, "profile-photos", imgUsrName);
                            try{
                                profilePhotoUsr.close();
                            }catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                        user.setPassword(PasswordFormatter.codify(password));
                        user.setTelephone(telephone[0]+" "+telephone[1]);
                        user.setProfilePhoto(imgUsrName);
                        user.setIsActive(1);
                        UsersController.Update(user.getEmailUser(),user);
                        RegUserUtil.removeInstance();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(()->{
                            Intent intent=new Intent(getApplicationContext(),gui.MainActivity.class);

                            StringPasser.createInstance(R.string.user_registered,R.string.you_can_login);
                            setResult(RESULT_OK,intent);
                            finish();




                        });
                    }).start();


                }
                else{

                    //finalBackground.setVisibility(View.GONE);
                    //base.setVisibility(View.VISIBLE);
                    chargingScreenDialog.hide();
                    String msg="<ul>";
                    int numErrors=0;
                    if(user.getFirst_name().isEmpty()){
                        firstNameField.setError(getString(R.string.mandatory_first_name));
                        msg+="<li><b>"+getString(R.string.mandatory_first_name)+"</b></li>";
                        numErrors++;
                    }
                    if(user.getLast_name().isEmpty()){
                        lastNameField.setError(getString(R.string.mandatory_last_name));
                        msg+="<li><b>"+getString(R.string.mandatory_last_name)+"</b></li>";
                        numErrors++;
                    }
                    if(!FieldChecker.passwordHasCorrectFormat(password)){
                        passwordField.setError(getString(R.string.mandatory_password));
                        msg+="<li><b>"+getString(R.string.mandatory_password)+"</b></li>";
                        numErrors++;
                    }
                    if(!FieldChecker.isACorrectPhone(telephone[0]+telephone[1])){
                        telephoneField.setError(getString(R.string.wrong_phone));
                        msg+="<li><b>"+getString(R.string.wrong_phone)+"</b></li>";
                        numErrors++;
                    }
                    if(!acceptLOPD.isChecked()){
                        msg+="<li><b>"+getString(R.string.you_must_LOPD)+"</b></li>";
                        numErrors++;
                    }
                    msg+="</ul>";
                    int idError=-1;
                    if (numErrors > 1) {
                        idError=R.string.errors;
                    }else{
                        idError=R.string.error;
                    }
                    new AwesomeErrorDialog(Register.this)
                            .setTitle(idError)
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
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data.getData();
            profilePhoto.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(Register.this,MainActivity.class);
            setResult(RESULT_CANCELED,intent);
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }




}