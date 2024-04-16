package gui;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

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
import cli.user.Request;
import cli.user.User;
import gui.adapters.OrgsAdapter;
import misc.FieldChecker;
import misc.PasswordCodifier;
import otea.connection.controller.*;
import session.Session;

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

    EditText passFieldReq;


    ConstraintLayout final_background;

    ConstraintLayout firstButtons;

    ConstraintLayout helpRequestScreen;

    GridLayout startSessionLayout;

    GridLayout createAccount2GridLayout;

    ConstraintLayout createAccount2ConstraintLayout;

    ImageButton createAccount2Button;

    ConstraintLayout startSessionButtonLayout;

    ConstraintLayout requestOrCreateButtonLayout;

    ConstraintLayout sendRequestButtonConstraintLayout;

    GridLayout requestLayout;

    GridLayout base;

    GridLayout send_request;

    TextView textProgress;

    TextView requestFound;

    TextView requestNotFound;

    List<Organization> organizations;

    Spinner spinnerOrgs;

    Organization organization;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Session.refreshCallers();
        super.onCreate(savedInstanceState);
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
        helpRequestScreen=findViewById(R.id.helpReqBackground);
        helpRequestScreen.setVisibility(View.GONE);
        createAccount2GridLayout=findViewById(R.id.createAccount2GridLayout);
        createAccount2GridLayout.setVisibility(View.GONE);
        spinnerOrgs=findViewById(R.id.spinner_orgs);
        spinnerOrgs.setVisibility(View.GONE);
        requestFound=findViewById(R.id.requestFound);
        requestFound.setVisibility(View.GONE);
        passFieldReq=findViewById(R.id.passFieldReq);
        send_request=findViewById(R.id.send_request);
        sendRequestButtonConstraintLayout=findViewById(R.id.sendRequestButtonConstraintLayout);
        sendRequestButton=findViewById(R.id.sendRequestButton);
        requestNotFound=findViewById(R.id.requestNotFound);
        requestNotFound.setVisibility(View.GONE);
        createAccount2ConstraintLayout=findViewById(R.id.createAccount2ConstraintLayout);
        createAccount2Button=findViewById(R.id.createAccount2Button);
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


        String[] credentials=new String[2];

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
                credentials[1]= PasswordCodifier.codify(s.toString());
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
                    base.setVisibility(View.GONE);
                    final_background.setVisibility(View.VISIBLE);
                    textProgress.setText(getString(R.string.please_wait_login));
                    JsonObject creds=new JsonObject();
                    creds.addProperty("email",credentials[0]);
                    creds.addProperty("password",credentials[1]);
                    JsonObject data=UsersController.Login(creds);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(data.has("errorCode")){
                                base.setVisibility(View.VISIBLE);
                                final_background.setVisibility(View.GONE);
                                if(data.get("errorCode").getAsInt()==404){
                                    emailField.setError(getString(R.string.email_not_in_db));
                                }
                                passwordField.setError(getString(R.string.wrong_password));
                            }else{
                                Session.createSession(data);
                                Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                                startActivity(intent);
                            }
                        }
                    }, 200);
                }
                return false;
            }
        });

        start_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base.setVisibility(View.GONE);
                final_background.setVisibility(View.VISIBLE);
                textProgress.setText(getString(R.string.please_wait_login));
                JsonObject creds=new JsonObject();
                creds.addProperty("email",credentials[0]);
                creds.addProperty("password",credentials[1]);
                JsonObject data=UsersController.Login(creds);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(data.has("errorCode")){
                            base.setVisibility(View.VISIBLE);
                            final_background.setVisibility(View.GONE);
                            if(data.get("errorCode").getAsInt()==404){
                                emailField.setError(getString(R.string.email_not_in_db));
                            }
                            passwordField.setError(getString(R.string.wrong_password));
                        }else{
                            Session.createSession(data);
                            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
                            startActivity(intent);
                        }
                    }
                }, 200);
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

        requestOrCreation.setOnClickListener(new View.OnClickListener() {

            User user;

            String text;
            Request request;
            @Override
            public void onClick(View v) {
                base.setVisibility(View.GONE);
                final_background.setVisibility(View.VISIBLE);
                textProgress.setText(getString(R.string.checking_req));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        request=RequestsController.Get(credentials[0]);
                        user=UsersController.Get(credentials[0]);
                        text="";
                        if(request!=null && user==null && request.getStatusReq()==0) {
                            credentials[1]="";
                            createAccount2GridLayout.setVisibility(View.VISIBLE);
                            if(Locale.getDefault().getLanguage().equals("es")){
                                text="Solicitud aceptada para <b>"+credentials[0]+"</b>. Por favor, introduzca la contraseña temporal que ha recibido en el mensaje de aceptación de la solicitud.";
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                text="Demande acceptée pour <b>"+credentials[0]+"</b>. Veuillez entrer le mot de passe temporaire que vous avez reçu dans le message d'acceptation de la demande.";
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                text="Eskaria onartuta <b>"+credentials[0]+"</b>-rentzat. Mesedez, sartu eskaria onartzeko mezuaren barruan jaso duzun pasahitz temporala.";
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                text="Sol·licitud acceptada per a <b>"+credentials[0]+"</b>. Si us plau, introdueixi la contrasenya temporal que ha rebut en el missatge d'acceptació de la sol·licitud.";
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                text="Aanvraag geaccepteerd voor <b>"+credentials[0]+"</b>. Voer alstublieft het tijdelijke wachtwoord in dat u heeft ontvangen in het acceptatiebericht van het verzoek.";
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                text="Solicitude aceptada para <b>"+credentials[0]+"</b>. Introduza o contrasinal temporal que recibiu no mensaxe de aceptación da solicitude.";
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                text="Anfrage akzeptiert für <b>"+credentials[0]+"</b>. Bitte geben Sie das temporäre Passwort ein, das Sie in der Akzeptanznachricht der Anfrage erhalten haben.";
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                text="Richiesta accettata per <b>"+credentials[0]+"</b>. Si prega di inserire la password temporanea ricevuta nel messaggio di accettazione della richiesta.";
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                text="Solicitação encontrada para <b>"+credentials[0]+"</b>. Por favor, insira a senha temporária que você recebeu na mensagem de aceitação da solicitação.";
                            }else{ //Valor por defecto
                                text="Solicitud aceptada para <b>"+credentials[0]+"</b>. Por favor, introduzca la contraseña temporal que ha recibido en el mensaje de aceptación de la solicitud.";
                            }
                            requestFound.setText(Html.fromHtml(text,0));
                            requestFound.setVisibility(View.VISIBLE);
                        }else if(request==null && user!=null){
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
                            emailField.setText(credentials[0]);
                            passwordField.setText(credentials[1]);
                            startSessionLayout.setVisibility(View.VISIBLE);
                        }else if(request==null){
                            //Si el request no existe y el usuario no existe
                            if(organizations==null){
                                organizations=new ArrayList<>();
                                Session.obtainAllOrgs();
                                organizations.add(new Organization(-1,"-","-",getString(R.string.evaluated_org),-1,"-","-","-","-","-","-","-","-","-","-","-","-","-"));
                                organizations.addAll(Session.getEvaluatorOrganizations());
                                organizations.addAll(Session.getEvaluatedOrganizations());
                                spinnerOrgs.setAdapter(new OrgsAdapter(MainActivity.this,organizations));
                            }
                            spinnerOrgs.setVisibility(View.VISIBLE);
                            credentials[1]="";
                            if(Locale.getDefault().getLanguage().equals("es")){
                                text="Solicitud no encontrada para <b>"+credentials[0]+"</b>. Por favor, seleccione una organización del desplegable para completar su solicitud.";
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                text="Demande non trouvée pour <b>"+credentials[0]+"</b>. Veuillez sélectionner une organisation dans le menu déroulant pour compléter l'envoi de votre demande.";
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                text="<b>"+credentials[0]+"</b> rako eskaria ez da aurkitu. Mesedez, aukeratu desplegagarritik erakunde bat zure eskaria bidaltzeko.";
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                text="Sol·licitud no trobada per a <b>"+credentials[0]+"</b>. Si us plau, seleccioneu una organització del desplegable per completar l'enviament de la vostra sol·licitud.";
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                text="Aanvraag niet gevonden voor <b>"+credentials[0]+"</b>. Selecteer alstublieft een organisatie uit het dropdownmenu om de indiening van uw aanvraag te voltooien.";
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                text="Solicitude non atopada para <b>"+credentials[0]+"</b>. Por favor, seleccione unha organización do despregable para completar o envío da súa solicitude.";
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                text="Anfrage nicht gefunden für <b>"+credentials[0]+"</b>. Bitte wählen Sie eine Organisation aus dem Dropdown-Menü aus, um den Versand Ihrer Anfrage abzuschließen.";
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                text="Richiesta non trovata per <b>"+credentials[0]+"</b>. Seleziona un'organizzazione dal menu a discesa per completare l'invio della tua richiesta.";
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                text="Solicitação não encontrada para <b>"+credentials[0]+"</b>. Por favor, selecione uma organização do menu suspenso para completar o envio da sua solicitação.";
                            }else{ //Valor por defecto
                                text="Request not found for <b>"+credentials[0]+"</b>. Please select an organization from the dropdown menu to complete your request.";
                            }
                            requestNotFound.setText(Html.fromHtml(text,0));
                            requestNotFound.setVisibility(View.VISIBLE);
                            send_request.setVisibility(View.VISIBLE);
                            sendRequestLayout.setVisibility(View.VISIBLE);
                        }else{
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
                            firstButtons.setVisibility(View.VISIBLE);
                        }
                        requestLayout.setVisibility(View.GONE);
                        final_background.setVisibility(View.GONE);
                        base.setVisibility(View.VISIBLE);
                    }
                }, 200);

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
                final_background.setVisibility(View.VISIBLE);
                send_request.setVisibility(View.GONE);
                base.setVisibility(View.GONE);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RequestsController.Create(new Request(credentials[0],-1,"-",organization.getIdOrganization(),organization.getOrgType(),organization.getIllness()));
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
                    }
                },200);
            }
        });



    }

    @Override
    public void onBackPressed() {
         if(startSessionLayout.getVisibility()==View.VISIBLE){
            //welcome.setVisibility(View.VISIBLE);
            startSessionLayout.setVisibility(View.GONE);
            firstButtons.setVisibility(View.VISIBLE);
        }else if(requestLayout.getVisibility()==View.VISIBLE){
            requestLayout.setVisibility(View.GONE);
            firstButtons.setVisibility(View.VISIBLE);
        }else if(sendRequestLayout.getVisibility()==View.VISIBLE){
             sendRequestLayout.setVisibility(View.GONE);
             firstButtons.setVisibility(View.VISIBLE);
         }
    }

}