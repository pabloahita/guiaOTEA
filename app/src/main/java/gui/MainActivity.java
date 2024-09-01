package gui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

import cli.organization.Organization;
import cli.user.User;
import gui.adapters.OrgsAdapter;
import io.karn.notify.Notify;
import io.karn.notify.NotifyCreator;
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

    View chargingScreen;

    TextView msgChargingScreen;

    AwesomeProgressDialog dialog;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Session.refreshCallers();
        super.onCreate(savedInstanceState);
        launcher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //final_background.setVisibility(View.GONE);
                    //base.setVisibility(View.VISIBLE);
                    dialog.hide();
                    requestLayout.setVisibility(View.GONE);
                    firstButtons.setVisibility(View.VISIBLE);
                    if(result.getResultCode()==RESULT_OK) {
                        int idTitle=StringPasser.getInstance().getIdTitle();
                        int idMsg=StringPasser.getInstance().getIdMsg();
                        StringPasser.removeInstance();
                        new AwesomeSuccessDialog(this)
                                .setTitle(idTitle)
                                .setMessage(Html.fromHtml(getString(idMsg),0))
                                .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                                .setCancelable(true)
                                .setPositiveButtonText(getString(R.string.understood))
                                .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                                .setPositiveButtonTextColor(R.color.white)
                                .setPositiveButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        //click
                                    }
                                })
                                .show();
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


        welcome.setText(Html.fromHtml("<i>Guía de indicadores de calidad para <b>O</b>rganizaciones que presentan apoyo a personas con <b>T</b>rastorno del <b>E</b>spectro <b>A</b>utista </i>",0));

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
                    //final_background.setVisibility(View.VISIBLE);
                    //base.setVisibility(View.GONE);

                    AwesomeProgressDialog dialog=new AwesomeProgressDialog(MainActivity.this)
                            .setTitle(R.string.sending_request)
                            .setMessage(R.string.please_wait)
                            .setColoredCircle(R.color.miradas_color)
                            .setCancelable(false);
                    dialog.show();
                    new Thread(()->{
                        UsersController.Create(new User(credentials[0],"ORGANIZATION","","","","",organization.getIdOrganization(),organization.getOrgType(),organization.getIllness(),"",-1));
                        runOnUiThread(()->{
                            String text="";

                            //final_background.setVisibility(View.GONE);
                            //base.setVisibility(View.VISIBLE);
                            dialog.hide();
                            send_request.setVisibility(View.GONE);
                            firstButtons.setVisibility(View.VISIBLE);
                            spinnerOrgs.setVisibility(View.GONE);
                            requestNotFound.setVisibility(View.GONE);
                            String aux=getString(R.string.please_wait_response_request);
                            String[] auxx=aux.split("aaa@aaa.com");
                            String msg=auxx[0]+credentials[0]+auxx[1];
                            new AwesomeSuccessDialog(MainActivity.this)
                                    .setTitle(R.string.request_sent)
                                    .setMessage(Html.fromHtml(msg,0))
                                    .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                                    .setCancelable(true)
                                    .setPositiveButtonText(getString(R.string.understood))
                                    .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                                    .setPositiveButtonTextColor(R.color.white)
                                    .setPositiveButtonClick(new Closure() {
                                        @Override
                                        public void exec() {
                                            //click
                                        }
                                    })
                                    .show();
                        });
                    }).start();
                });

            }
        });



    }

    private void startSession(){
        AwesomeProgressDialog dialog=new AwesomeProgressDialog(MainActivity.this)
                .setTitle(R.string.starting_session)
                .setMessage(R.string.please_wait)
                .setColoredCircle(R.color.miradas_color)
                .setCancelable(false);
        dialog.show();
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
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    // Switch to UI thread for UI updates
                    runOnUiThread(() -> {
                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(intent);
                    });
                }).start();
            }

            @Override
            public void onError(JsonObject errorResponse) {
                runOnUiThread(() -> {
                    dialog.hide();
                    int idTitle,idMsg = -1;
                    if (errorResponse.get("errorCode").getAsInt() == 404) {
                        idTitle = R.string.email_not_in_db;
                        idMsg=R.string.please_check_email;
                        emailField.setError(getString(idMsg));
                    } else if (errorResponse.get("errorCode").getAsInt() == 401) {
                        idTitle = R.string.wrong_password;
                        idMsg=R.string.please_password;
                        passwordField.setError(getString(idMsg));
                    } else {
                        idTitle = R.string.login_error;
                        idMsg=R.string.login_failed;
                    }


                    new AwesomeErrorDialog(MainActivity.this)
                            .setTitle(idTitle)
                            .setMessage(idMsg)
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
                });
            }
        });
    }

    private void manageRequest(){
        runOnUiThread(()->{
            //base.setVisibility(View.GONE);
            //final_background.setVisibility(View.VISIBLE);
            dialog=new AwesomeProgressDialog(MainActivity.this)
                    .setTitle(R.string.checking_req)
                    .setMessage(R.string.please_wait)
                    .setColoredCircle(R.color.miradas_color)
                    .setCancelable(false);
            dialog.show();
            new Thread(()->{

                User user=UsersController.Get(credentials[0]);

                runOnUiThread(()->{

                    String text="";
                    if(user!=null){
                        if(user.getIsActive()==0) {
                            new Thread(()->{
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
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
                            //final_background.setVisibility(View.GONE);
                            //base.setVisibility(View.VISIBLE);
                            dialog.hide();
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



                            new AwesomeInfoDialog(this)
                                    .setTitle(R.string.user_already_registered)
                                    .setMessage(Html.fromHtml(text,0))
                                    .setColoredCircle(R.color.miradas_color)
                                    .setCancelable(true)
                                    .show();
                        }else{
                            firstButtons.setVisibility(View.VISIBLE);
                            requestLayout.setVisibility(View.GONE);
                            //final_background.setVisibility(View.GONE);
                            //base.setVisibility(View.VISIBLE);
                            dialog.hide();
                            SpannableStringBuilder ssb1 = new SpannableStringBuilder();
                            SpannableStringBuilder ssb2 = new SpannableStringBuilder();
                            int startFM, startEm, endFM, endEm=-1;
                            String title="";
                            if(Locale.getDefault().getLanguage().equals("es")){
                                ssb1.append("La solicitud del email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> no ha sido respondida todavía");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Por favor, espere a su respuesta o contacte con ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();

                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                ssb1.append("La demande pour l'email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> n'a pas encore été répondue");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Veuillez attendre une réponse ou contacter ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                ssb1.append("<b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> helbideko eskaria oraindik ez da erantzun");
                                ssb2.append("Mesedez, erantzunaren zain egon edo ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradasrekin");
                                endFM = ssb2.length();
                                ssb2.append(" harremanetan jarri.");
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                ssb1.append("La sol·licitud de l'email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> encara no ha estat responduda");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Si us plau, espereu una resposta o poseu-vos en contacte amb ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                ssb1.append("Het verzoek voor het e-mailadres <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> is nog niet beantwoord");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Wacht alstublieft op een reactie of neem contact op met ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                ssb1.append("A solicitude do email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> aínda non foi respondida");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Por favor, agarde unha resposta ou contacte con ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                ssb1.append("Die Anfrage für die E-Mail <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> wurde noch nicht beantwortet");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Bitte warten Sie auf eine Antwort oder kontaktieren Sie ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                ssb1.append("La richiesta per l'email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> non è ancora stata risposta");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Si prega di attendere una risposta o contattare ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                ssb1.append("O pedido para o email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> ainda não foi respondido");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Por favor, aguarde uma resposta ou entre em contato com ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }else{ //Valor por defecto
                                ssb1.append("The request for the email <b><i>");
                                startEm = ssb1.length();
                                ssb1.append(credentials[0]);
                                endEm = ssb1.length();
                                ssb1.append("</i></b> has not been answered yet");//. Por favor, espere a su respuesta o contacte con \n");
                                ssb2.append("Please wait for a response or contact ");
                                startFM = ssb2.length();
                                ssb2.append("Fundación Miradas");
                                endFM = ssb2.length();
                            }


                            String url = "https://fundacionmiradas.org/contacto";

                            ssb2.setSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View textView) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                }
                            }, startFM, endFM, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);



                            new AwesomeInfoDialog(this)
                                    .setTitle(R.string.request_non_answered)
                                    .setMessage(Html.fromHtml(ssb1.toString()+". "+ssb2.toString(),0))
                                    .setColoredCircle(R.color.miradas_color)
                                    .setCancelable(true).show();
                        }
                    }
                    else{
                        //final_background.setVisibility(View.GONE);
                        requestLayout.setVisibility(View.GONE);
                        spinnerOrgs.setVisibility(View.VISIBLE);
                        requestNotFound.setVisibility(View.VISIBLE);
                        send_request.setVisibility(View.VISIBLE);
                        sendRequestLayout.setVisibility(View.VISIBLE);
                        //base.setVisibility(View.VISIBLE);
                        dialog.hide();
                        new Thread(()->{
                            organizations=OrganizationsController.GetAll();
                            runOnUiThread(()->{
                                String text2="";
                                organizations.add(0,new Organization(-1,"-","-",getString(R.string.evaluated_org),-1,"-","-","-","-","-","-","-","-","-","-","-","-","-"));
                                spinnerOrgs.setAdapter(new OrgsAdapter(MainActivity.this,organizations));
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
                welcome.setVisibility(View.VISIBLE);
                startSessionLayout.setVisibility(View.GONE);
                firstButtons.setVisibility(View.VISIBLE);
            } else if (requestLayout.getVisibility() == View.VISIBLE) {
                welcome.setVisibility(View.VISIBLE);
                requestLayout.setVisibility(View.GONE);
                firstButtons.setVisibility(View.VISIBLE);
            } else if (sendRequestLayout.getVisibility() == View.VISIBLE)
                welcome.setVisibility(View.VISIBLE);{
                sendRequestLayout.setVisibility(View.GONE);
                firstButtons.setVisibility(View.VISIBLE);
            }
        }
        return super.onKeyDown(keyCode,event);
    }

}