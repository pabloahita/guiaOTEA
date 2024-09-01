package gui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.civil.CivilCalendar;
import com.aminography.primedatepicker.picker.PrimeDatePicker;
import com.aminography.primedatepicker.picker.callback.MultipleDaysPickCallback;
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import gui.adapters.UsersAdapter;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.TranslatorController;
import session.EditEvaluatorTeamUtil;
import session.FileManager;
import session.StringPasser;

public class EditEvaluatorTeam extends AppCompatActivity {

    EditText creationDateEditText;

    EditText evaluationDatesEditText;



    List<User> evaluatedUsers;

    List<String> otherMembers;

    User professional;

    User responsible;

    String consultant;

    List<Center> centers;

    Center centerSelected;

    static int MIN_NUM_EVAL_DATES=3;

    PrimeCalendar creationDate;

    List<PrimeCalendar> evaluationDates;

    boolean checked=false;

    Button imageEvalTeamButton;

    ImageView imageEvalTeam;

    InputStream profilePhotoEvalTeam;

    String imgEvalTeamName="";

    ImageButton helpButton;
    
    EvaluatorTeam evaluatorTeam;

    boolean photoHasChanged=false;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_evaluator_team);

        evaluatorTeam=EditEvaluatorTeamUtil.getInstance().getEvaluatorTeam();

        evaluatedUsers= EditEvaluatorTeamUtil.getInstance().getEvaluatedUsers();
        if (!evaluatedUsers.isEmpty()) {

            Spinner responsibleSpinner = findViewById(R.id.spinner_select_responsible);
            Spinner professionalSpinner = findViewById(R.id.spinner_select_professional);

            List<User> userAuxList=new ArrayList<>();
            userAuxList.add(new User("-1","-",getString(R.string.responsible),"-","","",-1,"-","-","-",-1));
            userAuxList.add(new User("-1","-",getString(R.string.professional),"-","","",-1,"-","-","-",-1));

            List<User> responsibles=new ArrayList<>();
            responsibles.add(0,userAuxList.get(0));
            responsibles.addAll(evaluatedUsers);

            List<User> professionals=new ArrayList<>();
            professionals.add(0,userAuxList.get(1));
            professionals.addAll(evaluatedUsers);

            //otherMembers=new ArrayList<>();

            EditText otherMembers = findViewById(R.id.other_members);
            if(!evaluatorTeam.getOtherMembers().isEmpty()){
                otherMembers.setText(evaluatorTeam.getOtherMembers());
            }
            
            EditText consultant = findViewById(R.id.consultant);
            
            consultant.setText(evaluatorTeam.getExternalConsultant());

            EditText patient = findViewById(R.id.patientName);
            patient.setText(evaluatorTeam.getPatient_name());
            EditText relative = findViewById(R.id.relativeName);
            relative.setText(evaluatorTeam.getRelative_name());

            creationDateEditText=findViewById(R.id.creation_date);
            creationDate=new CivilCalendar();
            creationDate.setTimeInMillis(evaluatorTeam.getCreationDate());
            creationDateEditText.setText(creationDate.getLongDateString().split(", ")[1]);


            evaluationDatesEditText=findViewById(R.id.eval_dates);
            evaluationDates=new ArrayList<>();
            String[] evalDates=evaluatorTeam.getEvaluationDates().split(",");
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<evalDates.length;i++){
                PrimeCalendar evaluationDateUtil=new CivilCalendar();
                evaluationDateUtil.setTimeInMillis(Long.parseLong(evalDates[i]));
                evaluationDates.add(evaluationDateUtil);
                sb.append(evaluationDates.get(i).getLongDateString().split(", ")[1]);
                if(i<evalDates.length-1){
                    sb.append(", ");
                }
            }
            evaluationDatesEditText.setText(sb.toString());

            EditText observations=findViewById(R.id.observations);
            String obs="";
            if(Locale.getDefault().getLanguage().equals("es")) {
                obs=evaluatorTeam.getObservationsSpanish();
            }else if(Locale.getDefault().getLanguage().equals("fr")){
                obs=evaluatorTeam.getObservationsFrench();
            }else if(Locale.getDefault().getLanguage().equals("eu")) {
                obs=evaluatorTeam.getObservationsBasque();
            }else if(Locale.getDefault().getLanguage().equals("ca")){
                obs=evaluatorTeam.getObservationsCatalan();
            }else if(Locale.getDefault().getLanguage().equals("nl")) {
                obs=evaluatorTeam.getObservationsDutch();
            }else if(Locale.getDefault().getLanguage().equals("gl")){
                obs=evaluatorTeam.getObservationsGalician();
            }else if(Locale.getDefault().getLanguage().equals("de")) {
                obs=evaluatorTeam.getObservationsGerman();
            }else if(Locale.getDefault().getLanguage().equals("it")){
                obs=evaluatorTeam.getObservationsItalian();
            }else if(Locale.getDefault().getLanguage().equals("pt")) {
                obs=evaluatorTeam.getObservationsPortuguese();
            }else{
                obs=evaluatorTeam.getObservationsEnglish();
            }
            observations.setText(obs);

            UsersAdapter[] usersAdapter=new UsersAdapter[2];


            usersAdapter[1]=new UsersAdapter(getApplicationContext(),responsibles);
            usersAdapter[1].setDropDownViewResource(R.layout.spinner_item_layout);
            responsibleSpinner.setAdapter(usersAdapter[1]);

            usersAdapter[0]=new UsersAdapter(getApplicationContext(),professionals);
            usersAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
            professionalSpinner.setAdapter(usersAdapter[0]);



            ConstraintLayout finalBackground=findViewById(R.id.final_background);

            ConstraintLayout base=findViewById(R.id.base);

            base.setVisibility(View.VISIBLE);
            finalBackground.setVisibility(View.GONE);

            imageEvalTeamButton=findViewById(R.id.uploadPhoto);
            imageEvalTeam=findViewById(R.id.profilePhoto);
            if(!evaluatorTeam.getProfilePhoto().isEmpty()) {
                List<String> aux = new ArrayList<>();
                aux.add(evaluatorTeam.getProfilePhoto());
                FileManager.downloadPhotosProfileAsync(aux, new FileManager.PhotosDownloadCallback() {
                    @Override
                    public void onPhotoDownloadSuccess(String fileName, ByteArrayOutputStream stream) {
                        profilePhotoEvalTeam = new ByteArrayInputStream(stream.toByteArray());
                        imageEvalTeam.setImageBitmap(ProfilePhotoUtil.getBitmapFromStream(stream));
                    }

                    @Override
                    public void onPhotoDownloadFailure(String fileName, Exception e) {

                    }
                });
            }
            helpButton=findViewById(R.id.helpButton);


            ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri uri) {
                            // Handle the returned Uri
                            try {
                                profilePhotoEvalTeam = getContentResolver().openInputStream(uri);
                                imageEvalTeam.setImageURI(uri);
                                photoHasChanged=true;
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            } catch(NullPointerException ignored){}
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
                                profilePhotoEvalTeam = bs;
                                imageEvalTeam.setImageBitmap(bitmap);
                                bs.close();
                                photoHasChanged=true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }catch(NullPointerException ignored){}
                        }
                    });

            imageEvalTeamButton.setOnClickListener(new View.OnClickListener() {
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





            responsibleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    responsible=(User) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            

            professionalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    professional=(User) parent.getItemAtPosition(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            for(int i=1;i<evaluatedUsers.size();i++){
                if(evaluatedUsers.get(i).getEmailUser().equals(evaluatorTeam.getEmailResponsible())){
                    responsibleSpinner.setSelection(i);
                }
                if(evaluatedUsers.get(i).getEmailUser().equals(evaluatorTeam.getEmailProfessional())){
                    professionalSpinner.setSelection(i);
                }
            }

            patient.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    patient.setError(null);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String input=s.toString();
                    if(input.isEmpty()){
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
                    if(input.isEmpty()){
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
                    if(input.isEmpty()){
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



            otherMembers.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            creationDateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrimeCalendar today = new CivilCalendar();

                    PrimeDatePicker datePicker = PrimeDatePicker.Companion.dialogWith(today)
                            .pickSingleDay(new SingleDayPickCallback() {
                                @Override
                                public void onSingleDayPicked(PrimeCalendar singleDay) {
                                    creationDate=singleDay;
                                    creationDateEditText.setText(creationDate.getLongDateString().split(", ")[1]);
                                }
                            })
                            .minPossibleDate(today)
                            .build();

                    datePicker.show(getSupportFragmentManager(), "CREATION_DATE");
                }
            });

            evaluationDatesEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PrimeCalendar today = new CivilCalendar();

                    if(evaluationDates==null){
                        evaluationDates=new ArrayList<>();
                    }
                    PrimeDatePicker datePicker = PrimeDatePicker.Companion.dialogWith(today)
                            .pickMultipleDays(new MultipleDaysPickCallback() {
                                @Override
                                public void onMultipleDaysPicked(List<PrimeCalendar> list) {
                                    if(!evaluationDates.isEmpty()) {
                                        evaluationDates.clear();
                                    }
                                    evaluationDates.addAll(list);
                                    String text="";
                                    Collections.sort(evaluationDates, new Comparator<PrimeCalendar>() {
                                        @Override
                                        public int compare(PrimeCalendar o1, PrimeCalendar o2) {
                                            if(o1.getTimeInMillis() < o2.getTimeInMillis()){
                                                return -1;
                                            } else if(o1.getTimeInMillis() > o2.getTimeInMillis()){
                                                return 1;
                                            }
                                            return 0;
                                        }
                                    });
                                    if(evaluationDates.size()>=MIN_NUM_EVAL_DATES && evaluationDates.get(evaluationDates.size()-1).getTimeInMillis()-evaluationDates.get(0).getTimeInMillis()<2629800000L){
                                        StringBuilder sb=new StringBuilder();
                                        for(int i=0;i<evaluationDates.size();i++){
                                            sb.append(evaluationDates.get(i).getLongDateString().split(", ")[1]);
                                            if(i<evaluationDates.size()-1){
                                                sb.append(", ");
                                            }
                                        }
                                        text=sb.toString();
                                    }else{
                                        if(!evaluationDates.isEmpty()) {
                                            evaluationDates.clear();
                                        }
                                        String msg="";
                                        if(evaluationDates.size()<MIN_NUM_EVAL_DATES){
                                            msg="<b>"+getString(R.string.must_select_three_eval_dates)+"</b>";
                                        }
                                        else if(evaluationDates.get(evaluationDates.size()-1).getTimeInMillis()-evaluationDates.get(0).getTimeInMillis()>=2629800000L){
                                            msg="<b>"+getString(R.string.difference_between_dates_is_equal_or_greater_than_a_month)+"</b>";
                                        }
                                        new AlertDialog.Builder(EditEvaluatorTeam.this)
                                                .setTitle(getString(R.string.error))
                                                .setMessage(Html.fromHtml(msg,0))
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .create().show();

                                    }
                                    evaluationDatesEditText.setText(text);

                                }
                            })
                            .initiallyPickedMultipleDays(evaluationDates)
                            .minPossibleDate(today)
                            .build();
                    datePicker.show(getSupportFragmentManager(), "EVALUATION_DATES");
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

            helpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg="";
                    if(Locale.getDefault().getLanguage().equals("es")){
                        msg="Para poder editar correctamente un equipo evaluador, deberá rellenar los siguientes campos:" +
                                "<ul>" +
                                "<li><b>Centro de la organización: </b>En este campo deberá seleccionar el centro o servicio de su organización al que se asignará el equipo evaluador</li>" +
                                "<li><b>Consultor externo: </b>En este campo deberá introducir el nombre del consultor externo, quien es el profesional externo a la organización donde se va a realizar la evaluación, con formación y experiencia en la aplicación de instrumentos relacionados con sistemas de gestión de calidad. Éste, será el encargado de dirigir el proceso de aplicación de los indicadores y contrastar cada uno de ellos definiendo también el papel que para esta tarea pueden desempeñar los demás componentes del Equipo Evaluador.</li>" +
                                "<li><b>Profesional de atención directa: </b>En este campo deberá seleccionar un usuario de su organización como profesional de atención directa, cuya función consistirá en aportar y facilitar el acceso a la información, guiando la búsqueda de evidencias que ayuden a evaluar cada uno de los indicadores. Además del conocimiento de la organización o servicio, sería oportuno que este profesional tuviera conocimientos o experiencia en el ámbito de los sistemas de gestión de calidad.</li>" +
                                "<li><b>Responsable: </b>En este campo deberá seleccionar un usuario de su organización como responsable, cuya función principal será servir de guía e intermediario entre el Evaluador Principal y la organización o el servicio, facilitando a éste el acceso a la información y a toda persona que pueda brindar evidencias que permitan contrastar los indicadores. Esto resulta especialmente necesario en el caso de grandes organizaciones, donde la persona responsable no conozca en profundidad todos los servicios. " +
                                "La persona responsable del servicio o la organización, además de aportar la información propia del cargo, ejercerá las funciones de “secretario”, tomando nota de la información recogida y de los acuerdos tomados. " +
                                " </li>" +
                                "<li><b>Otros miembros: </b>En este campo deberá escribir los nombres de otros miembros participantes dentro del equipo evaluador.</li>" +
                                "<li><b>Nombre de la persona con TEA: </b>En este campo deberá introducir el nombre de la persona con Trastorno del Espectro Autista en la que se centrará el equipo evaluador.</li>" +
                                "<li><b>Nombre del familiar de la persona con TEA: </b>En este campo deberá introducir el nombre del familiar o representante de la persona con Trastorno del Espectro Autista.</li>" +
                                "<li><b>Fecha de creación: </b>En este campo deberá seleccionar la fecha de creación del equipo evaluador.</li>" +
                                "<li><b>Fechas de evaluación: </b>En este campo deberá seleccionar\tal menos tres fechas en un período no superior a un mes.</li>" +
                                "<li><b>Observaciones: </b>En este campo podrá introducir observaciones adicionales sobre el equipo evaluador.</li>" +
                                "</ul>" +
                                "Si lo desea, puede añadir una fotografía de perfil del equipo evaluador, presionando sobre <i><b>Cambiar foto</b></i>.";
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        msg="Afin de modifier correctement une équipe d'évaluation, vous devez remplir les champs suivants:" +
                                "<ul>" +
                                "<li><b>Centre organisationnel: </b>Dans ce champ, vous devez sélectionner le centre ou le service de votre organisation auquel l'équipe d'évaluation sera affectée</li>" +
                                "<li><b>Consultant externe: </b>Dans ce champ, vous devez saisir le nom du consultant externe, qui est le professionnel externe à l'organisation où l'évaluation sera réalisée, avec une formation et une expérience dans l'application de instruments associés avec des systèmes de gestion de la qualité. Celui-ci sera chargé de diriger le processus d'application des indicateurs et de comparer chacun d'eux, définissant également le rôle que les autres composantes de l'équipe d'évaluation peuvent jouer pour cette tâche.</li>" +
                                "<li><b>Professionnel de soins directs: </b>Dans ce champ, vous devez sélectionner un utilisateur de votre organisation en tant que professionnel de soins directs, dont la fonction sera de fournir et de faciliter l'accès à l'information, en guidant la recherche de preuves qui aident évaluer chacun des indicateurs. En plus de la connaissance de l'organisation ou du service, il conviendrait que ce professionnel ait des connaissances ou une expérience dans le domaine des systèmes de gestion de la qualité.</li>" +
                                "<li><b>Responsable: </b>Dans ce champ, vous devez sélectionner un utilisateur de votre organisation comme responsable, dont la fonction principale sera de servir de guide et d'intermédiaire entre l'évaluateur principal et l'organisation ou le service, en facilitant l'accès. à ces derniers, aux informations et à toute personne pouvant apporter des éléments permettant de contraster les indicateurs. Ceci est particulièrement nécessaire dans le cas des grandes organisations, où la personne responsable ne connaît pas en profondeur tous les services." +
                                "La personne responsable du service ou de l'organisme, en plus de fournir les informations spécifiques au poste, exercera les fonctions de « secrétaire », en prenant note des informations recueillies et des ententes conclues." +
                                " </li>" +
                                "<li><b>Autres membres: </b>Dans ce champ, vous devez écrire les noms des autres membres participants au sein de l'équipe d'évaluation.</li>" +
                                "<li><b>Nom de la personne atteinte de TSA: </b>Dans ce champ, vous devez saisir le nom de la personne atteinte de troubles du spectre autistique sur laquelle l'équipe d'évaluation se concentrera.</li>" +
                                "<li><b>Nom du membre de la famille de la personne atteinte de TSA: </b>Dans ce champ, vous devez saisir le nom du membre de la famille ou du représentant de la personne atteinte de troubles du spectre autistique.</li>" +
                                "<li><b>Date de création: </b>Dans ce champ vous devez sélectionner la date de création de l'équipe d'évaluation.</li>" +
                                "<li><b>Dates d'évaluation: </b>Dans ce champ, vous devez sélectionner au moins trois dates sur une période n'excédant pas un mois.</li>" +
                                "<li><b>Observations: </b>Dans ce champ, vous pouvez saisir des observations supplémentaires sur l'équipe d'évaluation.</li>" +
                                "</ul>" +
                                "Si vous le souhaitez, vous pouvez ajouter une photo de profil de l'équipe évaluatrice en cliquant sur <i><b>Changer la photo</b></i>.";
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        msg="Ebaluazio-talde bat behar bezala editatzeko, eremu hauek bete behar dituzu:" +
                                "<ul>" +
                                "<li><b>Antolakuntza-zentroa: </b>Eremu honetan ebaluazio-taldea esleituko zaion zure erakundeko zentroa edo zerbitzua hautatu behar duzu</li>" +
                                "<li><b>Kanpoko aholkularia: </b>Eremu honetan kanpoko aholkulariaren izena sartu behar da, hau da, ebaluazioa egingo den erakundearen kanpoko profesionala, prestakuntza eta esperientzia duena. kalitatea kudeatzeko sistemekin erlazionatutako tresnak. Adierazleak aplikatzeko prozesua bideratzeaz eta horietako bakoitza kontrasteaz arduratuko da, baita Ebaluazio Taldeko gainerako osagaiek zeregin horretarako bete dezaketen zeregina ere zehaztuz.</li>" +
                                "<li><b>Zuzeneko arretako profesionalak: </b>Eremu honetan zure erakundeko erabiltzaile bat hautatu behar duzu arreta zuzeneko profesional gisa, zeinaren funtzioa informazioa ematea eta sarbidea erraztea izango da, laguntzen duten frogak bilatzeko gidatzen duena. adierazle bakoitza ebaluatzea. Erakunde edo zerbitzuaren ezagutzaz gain, egokia litzateke profesional honek kalitatea kudeatzeko sistemen alorrean ezagutza edo esperientzia izatea.</li>" +
                                "<li><b>Arduraduna: </b>Eremu honetan zure erakundeko erabiltzaile bat hautatu behar duzu arduradun gisa, eta haren eginkizun nagusia Ebaluatzaile Nagusiaren eta erakunde edo zerbitzuaren arteko gidari eta bitartekari izatea izango da, sarbidea erraztuz. azken horri informazioari eta adierazleak kontrastea ahalbidetzen duen frogak eman ditzakeen edozein pertsonari. Hori bereziki beharrezkoa da erakunde handien kasuan, non arduradunak zerbitzu guztiak sakon ezagutzen ez dituen." +
                                "Zerbitzuaren edo erakundearen arduradunak, lanpostuari dagokion informazioa emateaz gain, «idazkari» eginkizunak beteko ditu, jasotako informazioa eta hartutako akordioak aintzat hartuta." +
                                " </li>" +
                                "<li><b>Beste kide batzuk: </b>Eremu honetan ebaluazio-taldeko beste kide parte-hartzaileen izenak idatzi behar dituzu.</li>" +
                                "<li><b>TEA duen pertsonaren izena: </b>Eremu honetan ebaluazio-taldeak bideratuko duen Autismoaren Espektroaren Nahastea duen pertsonaren izena idatzi behar duzu.</li>" +
                                "<li><b>TEA duen pertsonaren senitartekoaren izena: </b>Eremu honetan Autismoaren Espektroaren Nahastea duen pertsonaren senitartekoaren edo ordezkariaren izena idatzi behar duzu.</li>" +
                                "<li><b>Sortze-data: </b>Eremu honetan ebaluazio-taldearen sorrera-data hautatu behar duzu.</li>" +
                                "<li><b>Ebaluazio-datak: </b>Eremu honetan gutxienez hiru data hautatu behar dituzu hilabetetik gorakoa izango ez den epean.</li>" +
                                "<li><b>Oharrak: </b>Eremu honetan ebaluazio-taldeari buruzko behaketa gehigarriak sar ditzakezu.</li>" +
                                "</ul>" +
                                "Nahi baduzu, talde ebaluatzailearen profileko argazkia gehi dezakezu <i><b>Aldatu argazkia</b></i> sakatuta.";
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        msg="Per poder editar correctament un equip avaluador, haureu d'emplenar els camps següents:" +
                                "<ul>" +
                                "<li><b>Centre de l'organització: </b>En aquest camp haureu de seleccionar el centre o servei de la vostra organització al qual s'assignarà l'equip avaluador</li>" +
                                "<li><b>Consultor extern: </b>En aquest camp haurà d'introduir el nom del consultor extern, qui és el professional extern a l'organització on es realitzarà l'avaluació, amb formació i experiència en l'aplicació d'instruments relacionats amb sistemes de gestió de qualitat. Aquest serà l'encarregat de dirigir el procés d'aplicació dels indicadors i contrastar cadascun definint també el paper que per a aquesta tasca poden exercir els altres components de l'Equip Avaluador.</li>" +
                                "<li><b>Professional d'atenció directa: </b>En aquest camp haurà de seleccionar un usuari de la seva organització com a professional d'atenció directa, la funció del qual consistirà a aportar i facilitar l'accés a la informació, guiant la cerca d'evidències que ajudin a avaluar cadascun dels indicadors. A més del coneixement de l'organització o el servei, seria oportú que aquest professional tingués coneixements o experiència en l'àmbit dels sistemes de gestió de qualitat.</li>" +
                                "<li><b>Responsable: </b>En aquest camp haureu de seleccionar un usuari de la vostra organització com a responsable, la funció principal del qual serà servir de guia i intermediari entre l'Avaluador Principal i l'organització o el servei, facilitant a aquest l'accés a la informació ia tota persona que pugui brindar evidències que permetin contrastar els indicadors. Això és especialment necessari en el cas de grans organitzacions, on la persona responsable no conegui en profunditat tots els serveis." +
                                "La persona responsable del servei ol'organització, a més d'aportar la informació pròpia del càrrec, exercirà les funcions de “secretari”, prenent nota de la informació recollida i dels acords presos." +
                                " </li>" +
                                "<li><b>Altres membres: </b>En aquest camp heu d'escriure els noms d'altres membres participants dins l'equip avaluador.</li>" +
                                "<li><b>Nom de la persona amb TEA: </b>En aquest camp haurà d'introduir el nom de la persona amb Trastorn de l'Espectre Autista en què se centrarà l'equip avaluador.</li>" +
                                "<li><b>Nom del familiar de la persona amb TEA: </b>En aquest camp haurà d'introduir el nom del familiar o representant de la persona amb Trastorn de l'Espectre Autista.</li>" +
                                "<li><b>Data de creació: </b>En aquest camp haureu de seleccionar la data de creació de l'equip avaluador.</li>" +
                                "<li><b>Datas d'avaluació: </b>En aquest camp haureu de seleccionar almenys tres dates en un període no superior a un mes.</li>" +
                                "<li><b>Observacions: </b>En aquest camp podreu introduir observacions addicionals sobre l'equip avaluador.</li>" +
                                "</ul>" +
                                "Si voleu, podeu afegir una fotografia de perfil de l'equip avaluador, prement sobre <i><b>Canviar foto</b></i>.";
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        msg="Om een evaluatieteam correct te kunnen bewerken, moet u de volgende velden invullen:" +
                                "<ul>" +
                                "<li><b>Organisatiecentrum: </b>In dit veld moet u het centrum of de dienst van uw organisatie selecteren waaraan het evaluatieteam zal worden toegewezen</li>" +
                                "<li><b>Externe consultant: </b>In dit veld moet u de naam invullen van de externe consultant, de professional buiten de organisatie waar de evaluatie zal worden uitgevoerd, met opleiding en ervaring in de toepassing van gerelateerde instrumenten met kwaliteitsmanagementsystemen. Deze zal verantwoordelijk zijn voor het aansturen van het proces van het toepassen van de indicatoren en het contrasteren ervan, en zal ook de rol definiëren die de andere componenten van het Evaluatieteam voor deze taak kunnen spelen.</li>" +
                                "<li><b>Professioneel in de directe zorg: </b>In dit veld moet u een gebruiker uit uw organisatie selecteren als een professional in de directe zorg, wiens functie het zal zijn om de toegang tot informatie te verschaffen en te vergemakkelijken, en de zoektocht naar bewijsmateriaal te begeleiden dat helpt evalueer elk van de indicatoren. Naast kennis van de organisatie of dienst is het passend dat deze professional kennis of ervaring heeft op het gebied van kwaliteitsmanagementsystemen.</li>" +
                                "<li><b>Verantwoordelijk: </b>In dit veld moet u een gebruiker uit uw organisatie als verantwoordelijke selecteren, wiens belangrijkste functie het is om te dienen als gids en tussenpersoon tussen de hoofdbeoordelaar en de organisatie of dienst, waardoor de toegang wordt vergemakkelijkt aan laatstgenoemde, aan de informatie en aan elke persoon die bewijs kan leveren dat het mogelijk maakt de indicatoren te contrasteren. Dit is vooral nodig bij grote organisaties, waar de verantwoordelijke persoon niet alle diensten diepgaand kent." +
                                "De verantwoordelijke voor de dienst of organisatie vervult naast het verstrekken van de functiespecifieke informatie de functie van ‘secretaris’, waarbij hij kennis neemt van de verzamelde informatie en de gemaakte afspraken." +
                                " </li>" +
                                "<li><b>Andere leden: </b>In dit veld moet u de namen van andere deelnemende leden binnen het evaluatieteam schrijven.</li>" +
                                "<li><b>Naam van de persoon met ASS: </b>In dit veld moet u de naam invoeren van de persoon met een autismespectrumstoornis op wie het evaluatieteam zich zal richten.</li>" +
                                "<li><b>Naam van het familielid van de persoon met ASS: </b>In dit veld moet u de naam invullen van het familielid of de vertegenwoordiger van de persoon met een autismespectrumstoornis.</li>" +
                                "<li><b>Aanmaakdatum: </b>In dit veld moet u de aanmaakdatum van het evaluatieteam selecteren.</li>" +
                                "<li><b>Evaluatiedata: </b>In dit veld moet u minimaal drie data selecteren in een periode van maximaal één maand.</li>" +
                                "<li><b>Observaties: </b>In dit veld kunt u aanvullende opmerkingen over het evaluatieteam invoeren.</li>" +
                                "</ul>" +
                                "Als u wilt, kunt u een profielfoto van het beoordelende team toevoegen door op <i><b>Foto wijzigen</b></i> te klikken.";
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        msg="Para editar correctamente un equipo de avaliación, debes cubrir os seguintes campos:" +
                                "<ul>" +
                                "<li><b>Centro da organización: </b>Neste campo debe seleccionar o centro ou servizo da súa organización ao que se destinará o equipo de avaliación</li>" +
                                "<li><b>Consultor externo: </b>Neste campo debe introducir o nome do consultor externo, que é o profesional externo á organización onde se realizará a avaliación, con formación e experiencia na aplicación de instrumentos relacionados cos sistemas de xestión da calidade. Este será o encargado de dirixir o proceso de aplicación dos indicadores e contrastar cada un deles, definindo tamén o papel que poden desempeñar para esta tarefa os demais compoñentes do Equipo de Avaliación.</li>" +
                                "<li><b>Profesional de atención directa: </b>Neste campo debe seleccionar un usuario da súa organización como profesional de atención directa, cuxa función será proporcionar e facilitar o acceso á información, orientando a busca de probas que axuden avaliar cada un dos indicadores. Ademais do coñecemento da organización ou servizo, sería conveniente que este profesional tivese coñecementos ou experiencia no ámbito dos sistemas de xestión da calidade.</li>" +
                                "<li><b>Responsable: </b>Neste campo debes seleccionar un usuario da túa organización como responsable, cuxa función principal será servir de guía e intermediario entre o Avaliador Principal e a organización ou servizo, facilitando o acceso. a estes últimos á información e a calquera persoa que poida aportar probas que permitan contrastar os indicadores. Isto é especialmente necesario no caso das grandes organizacións, onde o responsable non coñece en profundidade todos os servizos." +
                                "A persoa responsable do servizo ou organización, ademais de facilitar a información propia do posto, desempeñará as funcións de “secretaria”, tomando nota da información recollida e dos acordos adoptados." +
                                " </li>" +
                                "<li><b>Outros membros: </b>Neste campo debes escribir os nomes dos outros membros participantes dentro do equipo de avaliación.</li>" +
                                "<li><b>Nome da persoa con TEA: </b>Neste campo debes introducir o nome da persoa con Trastorno do Espectro Autista na que se centrará o equipo de avaliación.</li>" +
                                "<li><b>Nome do familiar da persoa con TEA: </b>Neste campo debes introducir o nome do familiar ou representante da persoa con Trastorno do Espectro Autista.</li>" +
                                "<li><b>Data de creación: </b>Neste campo debes seleccionar a data de creación do equipo de avaliación.</li>" +
                                "<li><b>Datas de avaliación: </b>Neste campo debes seleccionar polo menos tres datas nun período non superior a un mes.</li>" +
                                "<li><b>Observacións: </b>Neste campo pode introducir observacións adicionais sobre o equipo de avaliación.</li>" +
                                "</ul>" +
                                "Se queres, podes engadir unha foto de perfil do equipo avaliador facendo clic en <i><b>Cambiar foto</b></i>.";
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        msg="Um ein Bewertungsteam korrekt zu bearbeiten, müssen Sie folgende Felder ausfüllen:" +
                                "<ul>" +
                                "<li><b>Organisationszentrum: </b>In diesem Feld müssen Sie das Zentrum oder den Dienst Ihrer Organisation auswählen, dem das Bewertungsteam zugewiesen werden soll</li>" +
                                "<li><b>Externer Berater: </b>In diesem Feld müssen Sie den Namen des externen Beraters eingeben, der der Fachmann außerhalb der Organisation ist, in der die Bewertung durchgeführt wird, mit Ausbildung und Erfahrung in der Anwendung von verwandte Instrumente mit Qualitätsmanagementsystemen. Dieser wird dafür verantwortlich sein, den Prozess der Anwendung der Indikatoren und deren Gegenüberstellung zu leiten und außerdem die Rolle zu definieren, die die anderen Komponenten des Evaluierungsteams für diese Aufgabe spielen können.</li>" +
                                "<li><b>Direkter Pflegeexperte: </b>In diesem Feld müssen Sie einen Benutzer aus Ihrer Organisation als direkten Pflegeexperten auswählen, dessen Aufgabe darin besteht, den Zugang zu Informationen bereitzustellen und zu erleichtern und die Suche nach hilfreichen Beweisen zu leiten Bewerten Sie jeden der Indikatoren. Zusätzlich zu den Kenntnissen der Organisation oder Dienstleistung wäre es angemessen, dass dieser Fachmann über Kenntnisse oder Erfahrungen im Bereich Qualitätsmanagementsysteme verfügt.</li>" +
                                "<li><b>Verantwortlicher:</b>In diesem Feld müssen Sie einen Benutzer Ihrer Organisation als Verantwortlichen auswählen, dessen Hauptaufgabe darin besteht, als Leitfaden und Vermittler zwischen dem Hauptgutachter und der Organisation oder dem Dienst zu fungieren und den Zugriff zu erleichtern an letztere an die Informationen und an jede Person, die Beweise vorlegen kann, die eine Gegenüberstellung der Indikatoren ermöglichen. Dies ist insbesondere bei großen Organisationen erforderlich, bei denen die verantwortliche Person nicht alle Dienste im Detail kennt." +
                                "Der Verantwortliche des Dienstes oder der Organisation übernimmt neben der Bereitstellung der für die Stelle spezifischen Informationen auch die Funktion eines „Sekretärs“ und nimmt die gesammelten Informationen und getroffenen Vereinbarungen zur Kenntnis." +
                                " </li>" +
                                "<li><b>Andere Mitglieder: </b>In dieses Feld müssen Sie die Namen anderer teilnehmender Mitglieder innerhalb des Bewertungsteams eingeben.</li>" +
                                "<li><b>Name der Person mit ASD:</b>In diesem Feld müssen Sie den Namen der Person mit Autismus-Spektrum-Störung eingeben, auf die sich das Bewertungsteam konzentrieren wird.</li>" +
                                "<li><b>Name des Familienmitglieds der Person mit Autismus-Spektrum-Störung:</b>In dieses Feld müssen Sie den Namen des Familienmitglieds oder Vertreters der Person mit Autismus-Spektrum-Störung eingeben.</li>" +
                                "<li><b>Erstellungsdatum: </b>In diesem Feld müssen Sie das Erstellungsdatum des Bewertungsteams auswählen.</li>" +
                                "<li><b>Auswertungstermine: </b>In diesem Feld müssen Sie mindestens drei Termine in einem Zeitraum auswählen, der einen Monat nicht überschreitet.</li>" +
                                "<li><b>Bemerkungen:</b>In diesem Feld können Sie zusätzliche Beobachtungen zum Evaluierungsteam eingeben.</li>" +
                                "</ul>" +
                                "Wenn Sie möchten, können Sie ein Profilfoto des bewertenden Teams hinzufügen, indem Sie auf <i><b>Foto ändern</b></i> klicken.";
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        msg="Per poter modificare correttamente un gruppo di valutazione è necessario compilare i seguenti campi:" +
                                "<ul>" +
                                "<li><b>Centro organizzativo: </b>in questo campo devi selezionare il centro o il servizio della tua organizzazione a cui verrà assegnato il team di valutazione</li>" +
                                "<li><b>Consulente esterno: </b>In questo campo è necessario inserire il nome del consulente esterno, ovvero il professionista esterno all'organizzazione in cui verrà effettuata la valutazione, con formazione ed esperienza nell'applicazione di strumenti correlati con i sistemi di gestione della qualità. Questo avrà il compito di indirizzare il processo di applicazione degli indicatori e di contrasto tra ciascuno di essi, definendo anche il ruolo che gli altri componenti del Gruppo di Valutazione possono svolgere per questo compito.</li>" +
                                "<li><b>Professionista dell'assistenza diretta: </b>in questo campo è necessario selezionare un utente della propria organizzazione come professionista dell'assistenza diretta, la cui funzione sarà quella di fornire e facilitare l'accesso alle informazioni, guidando la ricerca di prove che aiutino valutare ciascuno degli indicatori. Oltre alla conoscenza dell'organizzazione o del servizio, sarebbe opportuno che questo professionista avesse conoscenza o esperienza nel campo dei sistemi di gestione della qualità.</li>" +
                                "<li><b>Responsabile: </b>in questo campo è necessario selezionare un utente della propria organizzazione come responsabile, la cui funzione principale sarà quella di fungere da guida e intermediario tra il valutatore principale e l'organizzazione o il servizio, facilitando l'accesso a questi ultimi alle informazioni e a chiunque possa fornire evidenze che consentano il contrasto degli indicatori. Ciò è particolarmente necessario nel caso di grandi organizzazioni, dove la persona responsabile non conosce in modo approfondito tutti i servizi." +
                                "Il responsabile del servizio o dell'organizzazione, oltre a fornire le informazioni specifiche per l'incarico, svolgerà le funzioni di “segretario”, prendendo atto delle informazioni raccolte e degli accordi presi." +
                                " </li>" +
                                "<li><b>Altri membri: </b>in questo campo devi scrivere i nomi degli altri membri partecipanti all'interno del team di valutazione.</li>" +
                                "<li><b>Nome della persona con ASD: </b>In questo campo è necessario inserire il nome della persona con disturbo dello spettro autistico su cui si concentrerà il team di valutazione.</li>" +
                                "<li><b>Nome del familiare della persona con ASD: </b>In questo campo è necessario inserire il nome del familiare o del rappresentante della persona con disturbo dello spettro autistico.</li>" +
                                "<li><b>Data di creazione: </b>In questo campo è necessario selezionare la data di creazione del team di valutazione.</li>" +
                                "<li><b>Date di valutazione: </b>in questo campo devi selezionare almeno tre date in un periodo non superiore a un mese.</li>" +
                                "<li><b>Osservazioni: </b>in questo campo puoi inserire ulteriori osservazioni sul team di valutazione.</li>" +
                                "</ul>" +
                                "Se lo desideri, puoi aggiungere una foto del profilo del team di valutazione facendo clic su <i><b>Cambia foto</b></i>.";
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        msg="Para editar corretamente uma equipa de avaliação é necessário preencher os seguintes campos:" +
                                "<ul>" +
                                "<li><b>Centro da organização: </b>Neste campo deve selecionar o centro ou serviço da sua organização ao qual será atribuída a equipa de avaliação</li>" +
                                "<li><b>Consultor externo: </b>Neste campo deve introduzir o nome do consultor externo, que é o profissional externo à organização onde se vai realizar a avaliação, com formação e experiência na aplicação de instrumentos relacionados com os sistemas de gestão da qualidade. Este será responsável por orientar o processo de aplicação dos indicadores e contrastar cada um deles, definindo também o papel que os restantes componentes da Equipa de Avaliação podem desempenhar nesta tarefa.</li>" +
                                "<li><b>Profissional de atendimento direto: </b>Neste campo deve selecionar um utente da sua organização como profissional de atendimento direto, cuja função será fornecer e facilitar o acesso à informação, orientando a procura de evidências que ajudem a avaliar cada um dos indicadores. Para além do conhecimento da organização ou serviço, seria adequado que este profissional tivesse conhecimentos ou experiência na área dos sistemas de gestão da qualidade.</li>" +
                                "<li><b>Responsável: </b>Neste campo deve selecionar como responsável um utilizador da sua organização, cuja principal função será servir de guia e intermediário entre o Avaliador Principal e a organização ou serviço, facilitando o acesso a este último , à informação e a qualquer pessoa que possa fornecer provas que permitam contrastar os indicadores. Isto é especialmente necessário no caso de grandes organizações, onde o responsável não conhece a fundo todos os serviços." +
                                "O responsável pelo serviço ou organização, para além de prestar as informações específicas do cargo, desempenhará as funções de “secretário”, tomando nota das informações recolhidas e dos acordos celebrados." +
                                " </li>" +
                                "<li><b>Outros membros: </b>Neste campo deve escrever os nomes dos restantes membros participantes na equipa de avaliação.</li>" +
                                "<li><b>Nome da pessoa com PEA: </b>Neste campo deve introduzir o nome da pessoa com Perturbação do Espetro do Autismo na qual a equipa de avaliação se irá focar.</li>" +
                                "<li><b>Nome do familiar da pessoa com PEA: </b>Neste campo deve introduzir o nome do familiar ou representante da pessoa com Perturbação do Espetro do Autismo.</li>" +
                                "<li><b>Data de criação: </b>Neste campo deve selecionar a data de criação da equipa de avaliação.</li>" +
                                "<li><b>Datas de avaliação: </b>Neste campo deve selecionar pelo menos três datas num período não superior a um mês.</li>" +
                                "<li><b>Notas: </b>Neste campo pode introduzir observações adicionais sobre a equipa de avaliação.</li>" +
                                "</ul>" +
                                "Se desejar, pode adicionar uma fotografia de perfil da equipa avaliadora clicando em <i><b>Alterar fotografia</b></i>.";
                    }else{
                        msg="In order to correctly edit an evaluation team, you must fill out the following fields:" +
                                "<ul>" +
                                "<li><b>Organization center: </b>In this field you must select the center or service of your organization to which the evaluation team will be assigned</li>" +
                                "<li><b>External consultant: </b>In this field you must enter the name of the external consultant, who is the professional external to the organization where the evaluation will be carried out, with training and experience in the application of related instruments with quality management systems. This will be in charge of directing the process of applying the indicators and contrasting each one of them, also defining the role that the other components of the Evaluation Team can play for this task.</li>" +
                                "<li><b>Direct care professional: </b>In this field you must select a user from your organization as a direct care professional, whose function will be to provide and facilitate access to information, guiding the search for evidence that help evaluate each of the indicators. In addition to knowledge of the organization or service, it would be appropriate for this professional to have knowledge or experience in the field of quality management systems.</li>" +
                                "<li><b>Responsible: </b>In this field you must select a user from your organization as responsible, whose main function will be to serve as a guide and intermediary between the Principal Evaluator and the organization or service, facilitating access to the latter. to the information and to any person who can provide evidence that allows the indicators to be contrasted. This is especially necessary in the case of large organizations, where the responsible person does not know all the services in depth." +
                                "The person responsible for the service or organization, in addition to providing the information specific to the position, will perform the functions of “secretary”, taking note of the information collected and the agreements made." +
                                " </li>" +
                                "<li><b>Other members: </b>In this field you must write the names of other participating members within the evaluation team.</li>" +
                                "<li><b>Name of the person with ASD: </b>In this field you must enter the name of the person with Autism Spectrum Disorder on whom the evaluation team will focus.</li>" +
                                "<li><b>Name of the family member of the person with ASD: </b>In this field you must enter the name of the family member or representative of the person with Autism Spectrum Disorder.</li>" +
                                "<li><b>Creation date: </b>In this field you must select the creation date of the evaluation team.</li>" +
                                "<li><b>Evaluation dates: </b>In this field you must select at least three dates in a period not exceeding one month.</li>" +
                                "<li><b>Observations: </b>In this field you can enter additional observations about the evaluation team.</li>" +
                                "</ul>" +
                                "If you wish, you can add a profile photo of the evaluating team by clicking on <i><b>Change photo</b></i>.";
                    }
                    new android.app.AlertDialog.Builder(EditEvaluatorTeam.this)
                            .setTitle(getString(R.string.help))
                            .setMessage(Html.fromHtml(msg,0))
                            .create().show();
                }
            });

            Button add=findViewById(R.id.add);




            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //base.setVisibility(View.GONE);
                    //finalBackground.setVisibility(View.VISIBLE);
                    AwesomeProgressDialog chargingScreenDialog=new AwesomeProgressDialog(EditEvaluatorTeam.this)
                            .setTitle(R.string.please_wait_save_changes)
                            .setMessage(R.string.please_wait)
                            .setColoredCircle(R.color.miradas_color)
                            .setCancelable(false);
                    chargingScreenDialog.show();
                    if(!patient.getText().toString().isEmpty() && !relative.getText().toString().isEmpty() && !creationDateEditText.getText().toString().isEmpty() && !consultant.toString().isEmpty() && professional!=null && responsible!=null){
                        imgEvalTeamName=evaluatorTeam.getProfilePhoto();
                        if(photoHasChanged){

                            new Thread(()->{
                                if(imgEvalTeamName.isEmpty()){
                                    imgEvalTeamName="EVALTEAM_"+evaluatorTeam.getIdEvaluatorTeam()+"_"+ centerSelected.getIdCenter()+"_"+centerSelected.getIdOrganization()+"_"+centerSelected.getOrgType()+"_"+centerSelected.getIllness()+".webp";
                                }
                                FileManager.uploadFile(profilePhotoEvalTeam, "profile-photos", imgEvalTeamName);
                                try{
                                    profilePhotoEvalTeam.close();
                                }catch(IOException e){
                                    e.printStackTrace();
                                }
                            }).start();
                        }

                        new Thread(()->{

                            int idEvaluatorTeam= evaluatorTeam.getIdEvaluatorTeam();

                            long creation_date= creationDate.getTimeInMillis();
                            StringBuilder evaluationDatesStr=new StringBuilder();

                            for(int i=0;i<evaluationDates.size();i++){
                                evaluationDatesStr.append(evaluationDates.get(i).getTimeInMillis());
                                if(i<evaluationDates.size()-1){
                                    evaluationDatesStr.append(",");
                                }
                            }

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



                            if(!observationsText.isEmpty()){
                                List<String> translations= TranslatorController.getInstance().translate(observationsText,Locale.getDefault().getLanguage());
                                if(Locale.getDefault().getLanguage().equals("es")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=observationsText;
                                    observationsFrench=translations.get(1);
                                    observationsBasque=translations.get(2);
                                    observationsCatalan=translations.get(3);
                                    observationsDutch=translations.get(4);
                                    observationsGalician=translations.get(5);
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("fr")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=observationsText;
                                    observationsBasque=translations.get(2);
                                    observationsCatalan=translations.get(3);
                                    observationsDutch=translations.get(4);
                                    observationsGalician=translations.get(5);
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("eu")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=observationsText;
                                    observationsCatalan=translations.get(3);
                                    observationsDutch=translations.get(4);
                                    observationsGalician=translations.get(5);
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("ca")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=translations.get(3);
                                    observationsCatalan=observationsText;
                                    observationsDutch=translations.get(4);
                                    observationsGalician=translations.get(5);
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("nl")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=translations.get(3);
                                    observationsCatalan=translations.get(4);
                                    observationsDutch=observationsText;
                                    observationsGalician=translations.get(5);
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("gl")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=translations.get(3);
                                    observationsCatalan=translations.get(4);
                                    observationsDutch=translations.get(5);
                                    observationsGalician=observationsText;
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("de")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=translations.get(3);
                                    observationsCatalan=translations.get(4);
                                    observationsDutch=translations.get(5);
                                    observationsGalician=translations.get(6);
                                    observationsGerman=observationsText;
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("it")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=translations.get(3);
                                    observationsCatalan=translations.get(4);
                                    observationsDutch=translations.get(5);
                                    observationsGalician=translations.get(6);
                                    observationsGerman=translations.get(7);
                                    observationsItalian=observationsText;
                                    observationsPortuguese=translations.get(8);
                                }else if(Locale.getDefault().getLanguage().equals("pt")){
                                    observationsEnglish= translations.get(0);
                                    observationsSpanish=translations.get(1);
                                    observationsFrench=translations.get(2);
                                    observationsBasque=translations.get(3);
                                    observationsCatalan=translations.get(4);
                                    observationsDutch=translations.get(5);
                                    observationsGalician=translations.get(6);
                                    observationsGerman=translations.get(7);
                                    observationsItalian=translations.get(8);
                                    observationsPortuguese=observationsText;
                                }else{
                                    observationsEnglish= observationsText;
                                    observationsSpanish=translations.get(0);
                                    observationsFrench=translations.get(1);
                                    observationsBasque=translations.get(2);
                                    observationsCatalan=translations.get(3);
                                    observationsDutch=translations.get(4);
                                    observationsGalician=translations.get(5);
                                    observationsGerman=translations.get(6);
                                    observationsItalian=translations.get(7);
                                    observationsPortuguese=translations.get(8);
                                }
                            }


                            EvaluatorTeam evalTeam=new EvaluatorTeam(idEvaluatorTeam,creation_date,professional.getEmailUser(),responsible.getEmailUser(),otherMembers.getText().toString(),evaluatorTeam.getIdEvaluatorOrganization(),evaluatorTeam.getOrgTypeEvaluator(),evaluatorTeam.getIdEvaluatedOrganization(),evaluatorTeam.getOrgTypeEvaluated(),evaluatorTeam.getIdCenter(),evaluatorTeam.getIllness(),consultant.getText().toString(),patient.getText().toString(),relative.getText().toString(),observationsEnglish,observationsSpanish,observationsFrench,observationsBasque,observationsCatalan,observationsDutch,observationsGalician,observationsGerman,observationsItalian,observationsPortuguese,evaluationDatesStr.toString(),0,evaluationDates.size(),evaluatorTeam.getProfilePhoto());


                            EvaluatorTeamsController.Update(idEvaluatorTeam,evaluatorTeam.getIdEvaluatorOrganization(),evaluatorTeam.getOrgTypeEvaluator(),evaluatorTeam.getIdEvaluatedOrganization(),evaluatorTeam.getOrgTypeEvaluated(),evaluatorTeam.getIdCenter(),evaluatorTeam.getIllness(),evalTeam);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            StringPasser.createInstance(R.string.evaluation_team_modified,R.string.choose_other);
                            StringPasser.getInstance().setFlag(1);
                            runOnUiThread(()->{
                                EditEvaluatorTeamUtil.removeInstance();
                                Intent intent=new Intent(getApplicationContext(), MainMenu.class);
                                startActivity(intent);
                            });
                        }).start();
                    } else{
                        //base.setVisibility(View.VISIBLE);
                        //finalBackground.setVisibility(View.GONE);
                        chargingScreenDialog.hide();
                        String msg="<ul>";
                        int numErrors=0;
                        if(responsible.getEmailUser().equals("-")){
                            msg+="<li><b>"+getString(R.string.please_responsible)+"</b></li>";
                            numErrors++;
                        }
                        if(professional.getEmailUser().equals("-")){
                            msg+="<li><b>"+getString(R.string.please_professional)+"</b></li>";
                            numErrors++;
                        }
                        if(patient.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_relative_name)+"</b></li>";
                            numErrors++;
                        }
                        if(relative.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_relative_name)+"</b></li>";
                            numErrors++;
                        }
                        if(creationDateEditText.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_date)+"</b></li>";
                            numErrors++;
                        }
                        if(evaluationDatesEditText.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.must_select_three_eval_dates)+"</b></li>";
                            numErrors++;
                        }
                        if(consultant.getText().toString().isEmpty()){
                            msg+="<li><b>"+getString(R.string.please_consultant)+"</b></li>";
                            numErrors++;
                        }
                        if(!checked){
                            msg+="<li><b>"+getString(R.string.you_must_LOPD)+"</b></li>";
                            numErrors++;
                        }
                        msg+="</ul>";
                        int idTitle=-1;
                        if(numErrors>1){
                            idTitle=R.string.errors;
                        }else{
                            idTitle=R.string.error;
                        }
                        new AwesomeErrorDialog(EditEvaluatorTeam.this)
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
                    }}
            });

        }
        else{
            String msg="<ul>";
            int numErrors=0;
            msg+="</ul>";
            int idTitle=-1;
            if(numErrors>1){
                idTitle=R.string.errors;
            }else{
                idTitle=R.string.error;
            }
            new AlertDialog.Builder(EditEvaluatorTeam.this)
                    .setTitle(getString(idTitle))
                    .setMessage(Html.fromHtml(msg,0))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            EditEvaluatorTeamUtil.removeInstance();
            setResult(RESULT_CANCELED,intent);
            finish();
        }
        return super.onKeyDown(keyCode,event);
    }
}