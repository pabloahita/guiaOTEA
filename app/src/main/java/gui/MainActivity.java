package gui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.organization.Organization;
import cli.user.User;
import gui.adapters.OrgsAdapter;
import misc.FieldChecker;
import misc.PasswordFormatter;
import otea.connection.controller.*;
import session.RegUserUtil;
import session.Session;
import session.StringPasser;

public class MainActivity extends AppCompatActivity {


    ImageButton sign_in;
    ImageButton sign_up;

    ImageButton start_session;

    ImageButton helpSignInButton;

    ImageButton helpRequestButton;

    ImageButton requestOrCreation;

    ImageButton sendRequestButton;

    GridLayout sendRequestLayout;

    TextView welcome;

    EditText emailField;

    EditText passwordField;
    Drawable correct;


    EditText emailFieldReq;



    ConstraintLayout final_background;

    ConstraintLayout firstButtons;


    GridLayout startSessionLayout;



    ConstraintLayout startSessionButtonLayout;

    ConstraintLayout requestOrCreateButtonLayout;

    ConstraintLayout sendRequestButtonConstraintLayout;

    GridLayout requestLayout;

    GridLayout base;

    GridLayout send_request;

    TextView textProgress;


    TextView requestNotFound;

    List<Organization> organizations;

    Spinner spinnerOrgs;

    Organization organization;

    String tempPasswd;


    JsonObject data;

    String[] credentials;

    ActivityResultLauncher<Intent> launcher;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Session.refreshCallers();
        super.onCreate(savedInstanceState);
        launcher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    final_background.setVisibility(View.GONE);
                    base.setVisibility(View.VISIBLE);
                    requestLayout.setVisibility(View.GONE);
                    firstButtons.setVisibility(View.VISIBLE);
                    if(result.getResultCode()==RESULT_OK) {
                        String msg = StringPasser.getInstance().getString();
                        StringPasser.removeInstance();
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage(Html.fromHtml(msg, 0))
                                .setPositiveButton(R.string.understood, null)
                                .create().show();
                    }
                });
        setContentView(R.layout.activity_main);
        sign_in = findViewById(R.id.signInButton);
        sign_up = findViewById(R.id.reqRegButton);
        welcome=findViewById(R.id.welcome);
        final_background=findViewById(R.id.final_background);
        firstButtons=findViewById(R.id.firstButtons);
        startSessionLayout=findViewById(R.id.startSessionGrid);
        correct= ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_check_circle_24);
        final_background.setVisibility(View.GONE);
        firstButtons.setVisibility(View.VISIBLE);
        startSessionLayout.setVisibility(View.GONE);
        start_session=findViewById(R.id.startSessionButton);
        startSessionButtonLayout=findViewById(R.id.startSessionConstraintLayout);
        startSessionButtonLayout.setEnabled(false);
        startSessionButtonLayout.setAlpha(0.5f);
        textProgress=findViewById(R.id.textProgress);
        base=findViewById(R.id.base);
        requestLayout=findViewById(R.id.requestLayout);
        requestLayout.setVisibility(View.GONE);
        emailField=findViewById(R.id.emailField);
        passwordField=findViewById(R.id.passwordField);
        emailFieldReq=findViewById(R.id.emailFieldReq);
        requestOrCreateButtonLayout=findViewById(R.id.requestOrCreateConstraintLayout);
        requestOrCreation=findViewById(R.id.requestOrCreateButton);
        //helpSignInButton=findViewById(R.id.helpSignInButton);
        helpRequestButton=findViewById(R.id.helpRequestButton);
        spinnerOrgs=findViewById(R.id.spinner_orgs);
        spinnerOrgs.setVisibility(View.GONE);
        send_request=findViewById(R.id.send_request);
        sendRequestButtonConstraintLayout=findViewById(R.id.sendRequestButtonConstraintLayout);
        sendRequestButton=findViewById(R.id.sendRequestButton);
        requestNotFound=findViewById(R.id.requestNotFound);
        requestNotFound.setVisibility(View.GONE);
        sendRequestLayout=findViewById(R.id.sendRequestLayout);

        sign_in.setOnClickListener(v -> {
            firstButtons.setVisibility(View.GONE);
            welcome.setVisibility(View.GONE);
            emailField.setText("");
            passwordField.setText("");
            startSessionLayout.setVisibility(View.VISIBLE);
        });

        sign_up.setOnClickListener(v->{
            firstButtons.setVisibility(View.GONE);
            welcome.setVisibility(View.GONE);
            emailFieldReq.setText("");
            requestLayout.setVisibility(View.VISIBLE);

        });


        credentials=new String[2];

        credentials[0]="";
        credentials[1]="";

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(emailField.getError()!=null){
                    emailField.setError(null);
                    start_session.setEnabled(false);
                    startSessionButtonLayout.setAlpha(0.5f);
                }
                credentials[0]=s.toString();
                if(FieldChecker.emailHasCorrectFormat(credentials[0]) && !credentials[1].isEmpty()){
                    start_session.setEnabled(true);
                    startSessionButtonLayout.setAlpha(1f);
                }
                else{
                    start_session.setEnabled(false);
                    startSessionButtonLayout.setAlpha(0.5f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                passwordField.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordField.getError()!=null){
                    passwordField.setError(null);
                    start_session.setEnabled(false);
                    startSessionButtonLayout.setAlpha(0.5f);
                }
                credentials[1]= PasswordFormatter.codify(s.toString());
                if(FieldChecker.emailHasCorrectFormat(credentials[0]) && !credentials[1].isEmpty()){
                    start_session.setEnabled(true);
                    startSessionButtonLayout.setAlpha(1f);
                }
                else{
                    start_session.setEnabled(false);
                    startSessionButtonLayout.setAlpha(0.5f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && FieldChecker.emailHasCorrectFormat(credentials[0]) && !credentials[1].isEmpty()) {
                    startSession();
                }
                return false;
            }
        });

        start_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSession();
            }
        });


        spinnerOrgs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                organization=(Organization) parent.getItemAtPosition(position);
                if(organization.getIdOrganization()!=-1){
                    sendRequestButton.setEnabled(true);
                    sendRequestButtonConstraintLayout.setAlpha(1f);
                }
                else{
                    sendRequestButton.setEnabled(false);
                    sendRequestButtonConstraintLayout.setAlpha(0.5f);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        emailFieldReq.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                emailFieldReq.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(emailFieldReq.getError()!=null){
                    emailFieldReq.setError(null);
                    requestOrCreation.setEnabled(false);
                    requestOrCreateButtonLayout.setAlpha(0.5f);
                }
                credentials[0]=s.toString();
                if(FieldChecker.emailHasCorrectFormat(credentials[0])){
                    requestOrCreation.setEnabled(true);
                    requestOrCreateButtonLayout.setAlpha(1f);
                }
                else{
                    requestOrCreation.setEnabled(false);
                    requestOrCreateButtonLayout.setAlpha(0.5f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailFieldReq.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && FieldChecker.emailHasCorrectFormat(credentials[0])) {
                    manageRequest();
                }
                return false;
            }
        });







        requestOrCreation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                manageRequest();



            }
        });

        helpRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String req_ins=getString(R.string.req_ins);
                req_ins = req_ins.replace("'"+getString(R.string.check_request)+"'", "<i>"+getString(R.string.check_request)+"</i>");
                req_ins = req_ins.replace("'Fundación Miradas'", "<b><u>Fundación Miradas</u></b>");
                String req_ins1=getString(R.string.req_ins1);
                req_ins1 = req_ins1.replace("'Fundación Miradas'", "<b><u>Fundación Miradas</u></b>");
                String text=req_ins+"<ul><li>"+req_ins1+"</li><li>"+getString(R.string.req_ins2)+"</li><li>"+getString(R.string.req_ins3)+"</li></ul>";
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.registration_request))
                        .setMessage(Html.fromHtml(text,0))
                        //.setPositiveButton(android.R.string.yes, null)
                        .setIcon(android.R.drawable.ic_menu_help)
                        .show();
            }
        });

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(()->{
                    final_background.setVisibility(View.VISIBLE);
                    send_request.setVisibility(View.GONE);
                    base.setVisibility(View.GONE);
                    textProgress.setText(R.string.sending_request);
                    new Thread(()->{
                        UsersController.Create(new User(credentials[0],"ORGANIZATION","","","","",organization.getIdOrganization(),organization.getOrgType(),organization.getIllness(),"",-1));
                        runOnUiThread(()->{
                            String text="";
                            if(Locale.getDefault().getLanguage().equals("es")){
                                text="La solicitud con el email <b>"+credentials[0]+"</b> ha sido enviada satisfactoriamente. Por favor, espere su respuesta o contacte con Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                text="La demande avec l'email <b>"+credentials[0]+"</b> a été envoyée avec succès. Veuillez attendre une réponse ou contacter Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                text="<b>"+credentials[0]+"</b> helbideko eskaria arrakasta izan da bidalia. Mesedez, erantzunaren zain egon edo Fundación Miradasrekin harremanetan jarri.";
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                text="La sol·licitud amb l'email <b>"+credentials[0]+"</b> ha estat enviada satisfactòriament. Si us plau, espereu una resposta o poseu-vos en contacte amb Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                text="Het verzoek met het e-mailadres <b>"+credentials[0]+"</b> is succesvol verzonden. Wacht alstublieft op een reactie of neem contact op met Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                text="A solicitude co email <b>"+credentials[0]+"</b> enviouse satisfactoriamente. Por favor, agarde unha resposta ou contacte con Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                text="Die Anfrage mit der E-Mail <b>"+credentials[0]+"</b> wurde erfolgreich gesendet. Bitte warten Sie auf eine Antwort oder kontaktieren Sie Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                text="La richiesta con l'email <b>"+credentials[0]+"</b> è stata inviata con successo. Si prega di attendere una risposta o contattare Fundación Miradas.";
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                text="O pedido para o email <b>"+credentials[0]+"</b> ainda não foi respondido. Por favor, aguarde uma resposta ou entre em contato com Fundación Miradas.";
                            }else{ //Valor por defecto
                                text="The request with the email <b>"+credentials[0]+"</b> has been successfully sent. Please wait for a response or contact Fundación Miradas.";
                            }

                            final_background.setVisibility(View.GONE);
                            base.setVisibility(View.VISIBLE);
                            firstButtons.setVisibility(View.VISIBLE);
                            spinnerOrgs.setVisibility(View.GONE);
                            requestNotFound.setVisibility(View.GONE);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(getString(R.string.registration_request))
                                    .setMessage(Html.fromHtml(text,0))
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();
                        });
                    }).start();
                });

            }
        });



    }

    private void startSession(){
        base.setVisibility(View.GONE);
        final_background.setVisibility(View.VISIBLE);
        textProgress.setText(getString(R.string.please_wait_login));
        JsonObject creds=new JsonObject();
        creds.addProperty("email",credentials[0]);
        creds.addProperty("password",credentials[1]);

        UsersController.Login(creds, new UsersController.LoginCallback() {
            @Override
            public void onSuccess(JsonObject data) {
                // Run on a background thread
                new Thread(() -> {
                    Session.createSession(data);

                    // Ensure Session and its properties are initialized before proceeding
                    Session session = Session.getInstance();

                    // Initialize ProfilePhotoUtil
                    ProfilePhotoUtil.createInstance(
                            session.getUser().getProfilePhoto(),
                            session.getOrganization().getProfilePhoto()
                    );

                    // Switch to UI thread for UI updates
                    runOnUiThread(() -> {
                        Intent intent = new Intent(getApplicationContext(), com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                        startActivity(intent);
                    });
                }).start();
            }

            @Override
            public void onError(JsonObject errorResponse) {
                runOnUiThread(() -> {
                    base.setVisibility(View.VISIBLE);
                    final_background.setVisibility(View.GONE);
                    int idMsg = -1;
                    if (errorResponse.get("errorCode").getAsInt() == 404) {
                        idMsg = R.string.email_not_in_db;
                        emailField.setError(getString(idMsg));
                    } else if (errorResponse.get("errorCode").getAsInt() == 401) {
                        idMsg = R.string.wrong_password;
                        passwordField.setError(getString(idMsg));
                    } else {
                        idMsg = R.string.login_error;
                    }
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.error))
                            .setMessage(Html.fromHtml("<b>" + getString(idMsg) + "</b>", 0))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                });
            }
        });
    }

    private void manageRequest(){
        runOnUiThread(()->{
            base.setVisibility(View.GONE);
            final_background.setVisibility(View.VISIBLE);
            textProgress.setText(getString(R.string.checking_req));
            new Thread(()->{

                User user=UsersController.Get(credentials[0]);

                runOnUiThread(()->{

                    String text="";
                    if(user!=null){
                        if(user.getIsActive()==0) {
                            new Thread(()->{
                                RegUserUtil.createInstance(user);
                                runOnUiThread(()->{
                                    Intent intent=new Intent(getApplicationContext(),Register.class);
                                    launcher.launch(intent);
                                });
                            }).start();
                        }else if(user.getIsActive()==1){
                            emailField.setText(credentials[0]);
                            passwordField.setText(credentials[1]);
                            startSessionLayout.setVisibility(View.VISIBLE);
                            final_background.setVisibility(View.GONE);
                            base.setVisibility(View.VISIBLE);
                            requestLayout.setVisibility(View.GONE);
                            credentials[1]="";
                            if(Locale.getDefault().getLanguage().equals("es")){
                                text="El usuario con email <b>"+credentials[0]+"</b> existe en la base de datos. Por favor, inicie sesión.";
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                text="L'utilisateur avec l'email <b>"+credentials[0]+"</b> existe dans la base de données. Veuillez vous connecter.";
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                text="<b>"+credentials[0]+"</b> helbideko erabiltzailea datu-basean existitzen da. Mesedez, saioa hasi.";
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                text="L'usuari amb email <b>"+credentials[0]+"</b> existeix a la base de dades. Si us plau, inicieu sessió.";
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                text="De gebruiker met het e-mailadres <b>"+credentials[0]+"</b> bestaat in de database. Gelieve in te loggen.";
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                text="O usuario co email <b>"+credentials[0]+"</b> existe na base de datos. Por favor, inicie sesión.";
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                text="Der Benutzer mit der E-Mail <b>"+credentials[0]+"</b> existiert in der Datenbank. Bitte einloggen.";
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                text="L'utente con l'email <b>"+credentials[0]+"</b> esiste nel database. Si prega di effettuare l'accesso.";
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                text="O usuário com o email <b>"+credentials[0]+"</b> existe no banco de dados. Por favor, faça login.";
                            }else{ //Valor por defecto
                                text="The user with email <b>"+credentials[0]+"</b> exists in the database. Please log in.";
                            }
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(getString(R.string.user_already_registered))
                                    .setMessage(Html.fromHtml(text,0))
                                    //.setPositiveButton(android.R.string.yes, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }else{
                            firstButtons.setVisibility(View.VISIBLE);
                            requestLayout.setVisibility(View.GONE);
                            final_background.setVisibility(View.GONE);
                            base.setVisibility(View.VISIBLE);
                            SpannableStringBuilder ssb = new SpannableStringBuilder();
                            int startFM, startEm, endFM, endEm=-1;
                            if(Locale.getDefault().getLanguage().equals("es")){
                                ssb.append("La solicitud del email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append(" no ha sido respondida todavía. Por favor, espere a su respuesta o contacte con \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                ssb.append("La demande pour l'email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n n'a pas encore été répondue. Veuillez attendre une réponse ou contacter \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append(" helbideko eskaria oraindik ez da erantzun. Mesedez, erantzunaren zain egon edo \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradasrekin");
                                endFM = ssb.length();
                                ssb.append(" harremanetan jarri.");
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                ssb.append("La sol·licitud de l'email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n encara no ha estat responduda. Si us plau, espereu una resposta o poseu-vos en contacte amb \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                ssb.append("Het verzoek voor het e-mailadres \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n is nog niet beantwoord. Wacht alstublieft op een reactie of neem contact op met \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                ssb.append("A solicitude do email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n aínda non foi respondida. Por favor, agarde unha resposta ou contacte con \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                ssb.append("Die Anfrage für die E-Mail \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n wurde noch nicht beantwortet. Bitte warten Sie auf eine Antwort oder kontaktieren Sie \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                ssb.append("La richiesta per l'email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n non è ancora stata risposta. Si prega di attendere una risposta o contattare \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                ssb.append("O pedido para o email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n ainda não foi respondido. Por favor, aguarde uma resposta ou entre em contato com \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }else{ //Valor por defecto
                                ssb.append("The request for the email \n");
                                startEm = ssb.length();
                                ssb.append(credentials[0]);
                                endEm = ssb.length();
                                ssb.append("\n has not been answered yet. Please wait for a response or contact \n");
                                startFM = ssb.length();
                                ssb.append("Fundación Miradas");
                                endFM = ssb.length();
                                ssb.append(".");
                            }


                            String url = "https://fundacionmiradas.org/contacto";
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle(getString(R.string.unanswered_request));

                            ssb.setSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View textView) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                }
                            }, startFM, endFM, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            ssb.setSpan(new StyleSpan(Typeface.BOLD),startEm,endEm,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            TextView message = new TextView(MainActivity.this);
                            message.setText(ssb);
                            message.setMovementMethod(LinkMovementMethod.getInstance());
                            //message.setPadding(10, 10, 10, 10);
                            message.setGravity(Gravity.CENTER);
                            message.setTextSize(17f);
                            message.setTextColor(getColor(R.color.black));
                            builder.setView(message);
                            builder.setIcon(android.R.drawable.ic_dialog_alert);
                            builder.show();
                        }
                    }
                    else{
                        final_background.setVisibility(View.GONE);
                        requestLayout.setVisibility(View.GONE);
                        base.setVisibility(View.VISIBLE);
                        new Thread(()->{
                            organizations=OrganizationsController.GetAll();
                            runOnUiThread(()->{
                                String text2="";
                                organizations.add(0,new Organization(-1,"-","-",getString(R.string.evaluated_org),-1,"-","-","-","-","-","-","-","-","-","-","-","-","-"));
                                spinnerOrgs.setAdapter(new OrgsAdapter(MainActivity.this,organizations));
                                spinnerOrgs.setVisibility(View.VISIBLE);
                                if(Locale.getDefault().getLanguage().equals("es")){
                                    text2="Solicitud no encontrada para <b>"+credentials[0]+"</b>. Por favor, seleccione una organización del desplegable para completar su solicitud.";
                                }else if(Locale.getDefault().getLanguage().equals("fr")){
                                    text2="Demande non trouvée pour <b>"+credentials[0]+"</b>. Veuillez sélectionner une organisation dans le menu déroulant pour compléter l'envoi de votre demande.";
                                }else if(Locale.getDefault().getLanguage().equals("eu")){
                                    text2="<b>"+credentials[0]+"</b> rako eskaria ez da aurkitu. Mesedez, aukeratu desplegagarritik erakunde bat zure eskaria bidaltzeko.";
                                }else if(Locale.getDefault().getLanguage().equals("ca")){
                                    text2="Sol·licitud no trobada per a <b>"+credentials[0]+"</b>. Si us plau, seleccioneu una organització del desplegable per completar l'enviament de la vostra sol·licitud.";
                                }else if(Locale.getDefault().getLanguage().equals("nl")){
                                    text2="Aanvraag niet gevonden voor <b>"+credentials[0]+"</b>. Selecteer alstublieft een organisatie uit het dropdownmenu om de indiening van uw aanvraag te voltooien.";
                                }else if(Locale.getDefault().getLanguage().equals("gl")){
                                    text2="Solicitude non atopada para <b>"+credentials[0]+"</b>. Por favor, seleccione unha organización do despregable para completar o envío da súa solicitude.";
                                }else if(Locale.getDefault().getLanguage().equals("de")){
                                    text2="Anfrage nicht gefunden für <b>"+credentials[0]+"</b>. Bitte wählen Sie eine Organisation aus dem Dropdown-Menü aus, um den Versand Ihrer Anfrage abzuschließen.";
                                }else if(Locale.getDefault().getLanguage().equals("it")){
                                    text2="Richiesta non trovata per <b>"+credentials[0]+"</b>. Seleziona un'organizzazione dal menu a discesa per completare l'invio della tua richiesta.";
                                }else if(Locale.getDefault().getLanguage().equals("pt")){
                                    text2="Solicitação não encontrada para <b>"+credentials[0]+"</b>. Por favor, selecione uma organização do menu suspenso para completar o envio da sua solicitação.";
                                }else{ //Valor por defecto
                                    text2="Request not found for <b>"+credentials[0]+"</b>. Please select an organization from the dropdown menu to complete your request.";
                                }
                                requestNotFound.setText(Html.fromHtml(text2,0));
                                requestNotFound.setVisibility(View.VISIBLE);
                                send_request.setVisibility(View.VISIBLE);
                                sendRequestLayout.setVisibility(View.VISIBLE);
                            });
                        }).start();


                    }
                });
            }).start();


        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            if (startSessionLayout.getVisibility() == View.VISIBLE) {
                //welcome.setVisibility(View.VISIBLE);
                startSessionLayout.setVisibility(View.GONE);
                firstButtons.setVisibility(View.VISIBLE);
            } else if (requestLayout.getVisibility() == View.VISIBLE) {
                requestLayout.setVisibility(View.GONE);
                firstButtons.setVisibility(View.VISIBLE);
            } else if (sendRequestLayout.getVisibility() == View.VISIBLE) {
                sendRequestLayout.setVisibility(View.GONE);
                firstButtons.setVisibility(View.VISIBLE);
            } else{
                finishAffinity();
            }
        }
        return super.onKeyDown(keyCode,event);
    }

}