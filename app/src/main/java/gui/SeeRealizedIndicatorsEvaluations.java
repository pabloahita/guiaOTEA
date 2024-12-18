package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.IconCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.service.credentials.Action;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeProgressDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.fundacionmiradas.indicatorsevaluation.R;
import com.otaliastudios.zoom.ZoomLayout;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import cli.indicators.IndicatorsEvaluationSimpleEvidenceReg;
import io.karn.notify.Notify;
import io.karn.notify.NotifyCreator;
import misc.DateFormatter;
import misc.FieldChecker;
import session.FileManager;
import session.IndicatorsEvaluationRegsUtil;
import session.IndicatorsUtil;
import session.IndicatorsEvaluationUtil;
import session.Session;

public class SeeRealizedIndicatorsEvaluations extends AppCompatActivity {

    List<Indicator> indicators;

    List<Evidence> evidences;

    List<IndicatorsEvaluationIndicatorReg> indicatorRegs;

    List<IndicatorsEvaluationEvidenceReg> evidenceRegs;

    List<IndicatorsEvaluationSimpleEvidenceReg> simpleEvidenceRegs;

    int[] completeIndicatorIds={R.id.ind1,R.id.ind2,R.id.ind3,R.id.ind4,R.id.ind5,R.id.ind6,R.id.ind7,R.id.ind8,R.id.ind9,R.id.ind10,
            R.id.ind11,R.id.ind12,R.id.ind13,R.id.ind14,R.id.ind15,R.id.ind16,R.id.ind17,R.id.ind18,R.id.ind19,R.id.ind20,
            R.id.ind21,R.id.ind22,R.id.ind23,R.id.ind24,R.id.ind25,R.id.ind26,R.id.ind27,R.id.ind28,R.id.ind29,R.id.ind30,
            R.id.ind31,R.id.ind32,R.id.ind33,R.id.ind34,R.id.ind35,R.id.ind36,R.id.ind37,R.id.ind38,R.id.ind39,R.id.ind40,
            R.id.ind41,R.id.ind42,R.id.ind43,R.id.ind44,R.id.ind45,R.id.ind46,R.id.ind47,R.id.ind48,R.id.ind49,R.id.ind50,
            R.id.ind51,R.id.ind52,R.id.ind53,R.id.ind54,R.id.ind55,R.id.ind56,R.id.ind57,R.id.ind58,R.id.ind59,R.id.ind60,
            R.id.ind61,R.id.ind62,R.id.ind63,R.id.ind64,R.id.ind65,R.id.ind66,R.id.ind67,R.id.ind68,R.id.ind69,R.id.ind70};

    int[] simpleIndicatorIds={R.id.ind1,R.id.ind2,R.id.ind3,R.id.ind4,R.id.ind5,R.id.ind6,R.id.ind7,R.id.ind8,R.id.ind9,R.id.ind10,
            R.id.ind11,R.id.ind12,R.id.ind13,R.id.ind14,R.id.ind15,R.id.ind16,R.id.ind17,R.id.ind18,R.id.ind19,R.id.ind20,
            R.id.ind21,R.id.ind22,R.id.ind23,R.id.ind24,R.id.ind25,R.id.ind26,R.id.ind27,R.id.ind28,R.id.ind29,R.id.ind30,
            R.id.ind31,R.id.ind32,R.id.ind33,R.id.ind34,R.id.ind35,R.id.ind36,R.id.ind37,R.id.ind38,R.id.ind39,R.id.ind40};




    int[][] nums_ids={{R.id.low_red,R.id.low_yellow,R.id.low_green},
            {R.id.medium_red,R.id.medium_yellow,R.id.medium_green},
            {R.id.high_red,R.id.high_yellow,R.id.high_green},
            {R.id.fund_red,R.id.fund_yellow,R.id.fund_green},
    };

    int[][] points_ids={{R.id.points_low_red,R.id.points_low_yellow,R.id.points_low_green},
            {R.id.points_medium_red,R.id.points_medium_yellow,R.id.points_medium_green},
            {R.id.points_high_red,R.id.points_high_yellow,R.id.points_high_green},
            {R.id.points_fund_red,R.id.points_fund_yellow,R.id.points_fund_green},
    };
    ZoomLayout layout;

    ConstraintLayout info;

    ImageButton infoButton;

    ImageButton resultsButton;

    ImageButton generateReportButton;

    ImageButton helpButton;

    int[][] numIndicatorsPerPriority;

    IndicatorsEvaluation evaluation;

    boolean isComplete;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_realized_indicators_evaluations);

        evaluation= IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation();


        indicatorRegs= IndicatorsEvaluationRegsUtil.getInstance().getIndicatorRegs();

        isComplete=evaluation.getEvaluationType().equals("COMPLETE");

        if(isComplete){
            evidenceRegs=IndicatorsEvaluationRegsUtil.getInstance().getEvidenceRegs();
        }
        else{
            simpleEvidenceRegs=IndicatorsEvaluationRegsUtil.getInstance().getEvidenceSimpleRegs();
        }


        indicators= IndicatorsUtil.getInstance().getIndicators();



        int[] indicatorIds;

        if(isComplete){
            indicatorIds=completeIndicatorIds;
            layout=findViewById(R.id.zoom_layout_complete);
        }
        else{
            indicatorIds=simpleIndicatorIds;
            layout=findViewById(R.id.zoom_layout_simple);
        }

        int idIndicator=1;
        for(int buttonId : indicatorIds){
            Button button=layout.findViewById(buttonId);
            button.setOnClickListener(generateClickListener(idIndicator));
            button.setBackground(getBackground(idIndicator));
            idIndicator++;
        }

        layout.setVisibility(View.VISIBLE);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float scaledX = event.getX() / layout.getZoom();
                float scaledY = event.getY() / layout.getZoom();
                return false;
            }
        });


        info=findViewById(R.id.ind_eval_info);

        buildPointsTable();


        infoButton=findViewById(R.id.infoButton);
        resultsButton=findViewById(R.id.resultsButton);
        generateReportButton=findViewById(R.id.generateReportButton);
        helpButton=findViewById(R.id.helpButton);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layout.getVisibility()==View.VISIBLE){
                    layout.setVisibility(View.GONE);
                    info.setVisibility(View.VISIBLE);
                }
            }
        });

        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info.getVisibility()==View.VISIBLE){
                    layout.setVisibility(View.VISIBLE);
                    info.setVisibility(View.GONE);
                }
            }
        });

        generateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadReport();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view=inflater.inflate(R.layout.help_see_ind,null);
                TextView downloadTxt=view.findViewById(R.id.textView22);
                SpannableStringBuilder sb = new SpannableStringBuilder();
                int start, end=-1;
                String url="https://guiaoteainformes.azurewebsites.net";
                if(Locale.getDefault().getLanguage().equals("es")){
                    sb.append("Presionando en este botón descargará el informe relacionado con este test de indicadores. También puede descargarlo en ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("fr")){
                    sb.append("En appuyant sur ce bouton, vous téléchargerez le rapport lié à ce test d'indicateurs. Vous pouvez également le télécharger sur ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("eu")){
                    sb.append("Boton honetan sakatuz, adierazleen testekin erlazionatutako txostena deskargatuko duzu. Horrez gain, ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                    sb.append(" webgunean ere deskargatu dezakezu");
                }else if(Locale.getDefault().getLanguage().equals("ca")){
                    sb.append("Premen en aquest botó descarregarà l'informe relacionat amb aquest test d'indicadors. També el pot descarregar a ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("nl")){
                    sb.append("Door op deze knop te drukken wordt het rapport gedownload dat gerelateerd is aan deze indicatorentest. U kunt het ook downloaden op ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("gl")){
                    sb.append("Premendo neste botón descargará o informe relacionado con este test de indicadores. Tamén pode descargalo en ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("de")){
                    sb.append("Durch Drücken dieses Buttons wird der Bericht zum Indikatortest heruntergeladen. Sie können ihn auch unter ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("it")){
                    sb.append("Premendo su questo pulsante verrà scaricato il rapporto relativo a questo test degli indicatori. Puoi anche scaricarlo su ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else if(Locale.getDefault().getLanguage().equals("pt")){
                    sb.append("Ao pressionar este botão, você irá baixar o relatório relacionado a este teste de indicadores. Você também pode baixá-lo em ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }else{
                    sb.append("Pressing this button will download the report related to this indicator test. You can also download it at ");
                    start=sb.length();
                    sb.append(url);
                    end=sb.length();
                }
                sb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(browserIntent);
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                downloadTxt.setText(sb);
                downloadTxt.setMovementMethod(LinkMovementMethod.getInstance());
                new AlertDialog.Builder(SeeRealizedIndicatorsEvaluations.this)
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setTitle(R.string.help)
                        .setView(view)
                        .create().show();
            }
        });


    }



    private View.OnClickListener generateClickListener(int idInd){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=getLayoutInflater();
                View view;
                AlertDialog dialog=new AlertDialog.Builder(SeeRealizedIndicatorsEvaluations.this)
                        .setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                if(indicatorRegs.size()>=idInd){
                    CheckBox ev1=null;
                    CheckBox ev2=null;
                    CheckBox ev3=null;
                    CheckBox ev4=null;
                    TextView ev_1=null;
                    TextView ev_2=null;
                    TextView ev_3=null;
                    TextView ev_4=null;
                    CheckBox requires_improvement_plan=null;
                    String ev1Caption="";
                    String ev2Caption="";
                    String ev3Caption="";
                    String ev4Caption="";
                    int idEvidenceBase=4*(idInd-1);
                    if(isComplete){
                        view=inflater.inflate(R.layout.indicator_complete_result,null);
                        evidences= IndicatorsUtil.getInstance().getEvidencesByIndicator(indicators.get(idInd-1).getIdSubSubAmbit(),indicators.get(idInd-1).getIdSubAmbit(),indicators.get(idInd-1).getIdAmbit(),idInd,indicators.get(idInd-1).getIndicatorType(),indicators.get(idInd-1).getIndicatorVersion(),indicators.get(idInd-1).getEvaluationType());
                    }
                    else{
                        view=inflater.inflate(R.layout.indicator_simple_result,null);
                    }
                    TextView about_ev_1=view.findViewById(R.id.about_ev_1);
                    TextView about_ev_2=view.findViewById(R.id.about_ev_2);
                    TextView about_ev_3=view.findViewById(R.id.about_ev_3);
                    TextView about_ev_4=view.findViewById(R.id.about_ev_4);
                    if(isComplete){
                        ev1=view.findViewById(R.id.evidence1);
                        ev2=view.findViewById(R.id.evidence2);
                        ev3=view.findViewById(R.id.evidence3);
                        ev4=view.findViewById(R.id.evidence4);
                        if(Locale.getDefault().getLanguage().equals("es")){
                            ev1Caption="<b>Evidencia 1: </b>" + evidences.get(0).getDescriptionSpanish();
                            ev2Caption="<b>Evidencia 2: </b>" + evidences.get(1).getDescriptionSpanish();
                            ev3Caption="<b>Evidencia 3: </b>" + evidences.get(2).getDescriptionSpanish();
                            ev4Caption = "<b>Evidencia 4: </b>" + evidences.get(3).getDescriptionSpanish();
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            ev1Caption="<b>Preuve 1: </b>" + evidences.get(0).getDescriptionFrench();
                            ev2Caption="<b>Preuve 2: </b>" + evidences.get(1).getDescriptionFrench();
                            ev3Caption="<b>Preuve 3: </b>" + evidences.get(2).getDescriptionFrench();
                            ev4Caption = "<b>Preuve 4: </b>" + evidences.get(3).getDescriptionFrench();

                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            ev1Caption="<b>1. froga: </b>" + evidences.get(0).getDescriptionBasque();
                            ev2Caption="<b>2. froga: </b>" + evidences.get(1).getDescriptionBasque();
                            ev3Caption="<b>3. froga: </b>" + evidences.get(2).getDescriptionBasque();
                            ev4Caption = "<b>4. froga: </b>" + evidences.get(3).getDescriptionBasque();

                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            ev1Caption="<b>Evidència 1: </b>" + evidences.get(0).getDescriptionCatalan();
                            ev2Caption="<b>Evidència 2: </b>" + evidences.get(1).getDescriptionCatalan();
                            ev3Caption="<b>Evidència 3: </b>" + evidences.get(2).getDescriptionCatalan();
                            ev4Caption = "<b>Evidència 4: </b>" + evidences.get(3).getDescriptionCatalan();

                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            ev1Caption="<b>Bewijs 1: </b>" + evidences.get(0).getDescriptionDutch();
                            ev2Caption="<b>Bewijs 2: </b>" + evidences.get(1).getDescriptionDutch();
                            ev3Caption="<b>Bewijs 3: </b>" + evidences.get(2).getDescriptionDutch();
                            ev4Caption = "<b>Bewijs 4: </b>" + evidences.get(3).getDescriptionDutch();

                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            ev1Caption="<b>Evidencia 1: </b>" + evidences.get(0).getDescriptionGalician();
                            ev2Caption="<b>Evidencia 2: </b>" + evidences.get(1).getDescriptionGalician();
                            ev3Caption="<b>Evidencia 3: </b>" + evidences.get(2).getDescriptionGalician();
                            ev4Caption = "<b>Evidencia 4: </b>" + evidences.get(3).getDescriptionGalician();

                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            ev1Caption="<b>Beweis 1: </b>" + evidences.get(0).getDescriptionGerman();
                            ev2Caption="<b>Beweis 2: </b>" + evidences.get(1).getDescriptionGerman();
                            ev3Caption="<b>Beweis 3: </b>" + evidences.get(2).getDescriptionGerman();
                            ev4Caption = "<b>Beweis 4: </b>" + evidences.get(3).getDescriptionGerman();

                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            ev1Caption="<b>Prova 1: </b>" + evidences.get(0).getDescriptionItalian();
                            ev2Caption="<b>Prova 2: </b>" + evidences.get(1).getDescriptionItalian();
                            ev3Caption="<b>Prova 3: </b>" + evidences.get(2).getDescriptionItalian();
                            ev4Caption = "<b>Prova 4: </b>" + evidences.get(3).getDescriptionItalian();

                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            ev1Caption="<b>Evidência 1: </b>" + evidences.get(0).getDescriptionPortuguese();
                            ev2Caption="<b>Evidência 2: </b>" + evidences.get(1).getDescriptionPortuguese();
                            ev3Caption="<b>Evidência 3: </b>" + evidences.get(2).getDescriptionPortuguese();
                            ev4Caption = "<b>Evidência 4: </b>" + evidences.get(3).getDescriptionPortuguese();

                        }else{
                            ev1Caption="<b>Evidence 1: </b>" + evidences.get(0).getDescriptionEnglish();
                            ev2Caption="<b>Evidence 2: </b>" + evidences.get(1).getDescriptionEnglish();
                            ev3Caption="<b>Evidence 3: </b>" + evidences.get(2).getDescriptionEnglish();
                            ev4Caption = "<b>Evidence 4: </b>" + evidences.get(3).getDescriptionEnglish();

                        }

                        if(evidenceRegs.get(idEvidenceBase).getIsMarked()==1){
                            ev1.setChecked(true);
                        }else{
                            ev1.setChecked(false);
                        }
                        if(evidenceRegs.get(idEvidenceBase+1).getIsMarked()==1){
                            ev2.setChecked(true);
                        }else{
                            ev2.setChecked(false);
                        }
                        if(evidenceRegs.get(idEvidenceBase+2).getIsMarked()==1){
                            ev3.setChecked(true);
                        }else{
                            ev3.setChecked(false);
                        }
                        if(evidenceRegs.get(idEvidenceBase+3).getIsMarked()==1){
                            ev4.setChecked(true);
                        }else{
                            ev4.setChecked(false);
                        }
                        ev1.setText(Html.fromHtml(ev1Caption,0));
                        ev2.setText(Html.fromHtml(ev2Caption,0));
                        ev3.setText(Html.fromHtml(ev3Caption,0));
                        ev4.setText(Html.fromHtml(ev4Caption,0));
                        about_ev_1.setText(evidenceRegs.get(idEvidenceBase).getObservationsSpanish());
                        about_ev_2.setText(evidenceRegs.get(idEvidenceBase+1).getObservationsSpanish());
                        about_ev_3.setText(evidenceRegs.get(idEvidenceBase+2).getObservationsSpanish());
                        about_ev_4.setText(evidenceRegs.get(idEvidenceBase+3).getObservationsSpanish());
                    }else{
                        ev_1=view.findViewById(R.id.ev_1);
                        ev_1.setText(simpleEvidenceRegs.get(idEvidenceBase).getDescriptionSpanish());
                        ev_2=view.findViewById(R.id.ev_2);
                        ev_2.setText(simpleEvidenceRegs.get(idEvidenceBase+1).getDescriptionSpanish());
                        ev_3=view.findViewById(R.id.ev_3);
                        ev_3.setText(simpleEvidenceRegs.get(idEvidenceBase+2).getDescriptionSpanish());
                        ev_4=view.findViewById(R.id.ev_4);
                        ev_4.setText(simpleEvidenceRegs.get(idEvidenceBase+3).getDescriptionSpanish());
                        about_ev_1.setText(simpleEvidenceRegs.get(idEvidenceBase).getObservationsSpanish());
                        about_ev_2.setText(simpleEvidenceRegs.get(idEvidenceBase+1).getObservationsSpanish());
                        about_ev_3.setText(simpleEvidenceRegs.get(idEvidenceBase+2).getObservationsSpanish());
                        about_ev_4.setText(simpleEvidenceRegs.get(idEvidenceBase+3).getObservationsSpanish());
                    }

                    requires_improvement_plan=view.findViewById(R.id.requires_improvement_plan);
                    TextView indicatorCaption=view.findViewById(R.id.indicator_caption);

                    TextView info_help=view.findViewById(R.id.info_help);
                    String indCaption="";
                    if(Locale.getDefault().getLanguage().equals("es")){
                        indCaption="<b>Indicador " + idInd + ": </b>" + indicators.get(idInd-1).getDescriptionSpanish();
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        indCaption="<b>Indicateur " + idInd + ": </b>" + indicators.get(idInd-1).getDescriptionFrench();
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        indCaption="<b>"+idInd + ". adierazlea: </b>" + indicators.get(idInd-1).getDescriptionBasque();
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        indCaption="<b>Indicador " + idInd + ": </b>" + indicators.get(idInd-1).getDescriptionCatalan();
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        indCaption="<b>Indicator " + idInd + ": " + indicators.get(idInd-1).getDescriptionDutch();
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        indCaption="<b>Indicador " + idInd + ": " + indicators.get(idInd-1).getDescriptionGalician();
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        indCaption="<b>Indikator " + idInd + ": " + indicators.get(idInd-1).getDescriptionGerman();
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        indCaption="<b>Indicatore " + idInd + ": " + indicators.get(idInd-1).getDescriptionItalian();
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        indCaption="<b>Indicador " + idInd + ": " + indicators.get(idInd-1).getDescriptionPortuguese();
                    }else{
                        indCaption="<b>Indicator " + idInd + ": " + indicators.get(idInd-1).getDescriptionEnglish();
                    }
                    indicatorCaption.setText(Html.fromHtml(indCaption,0));
                    if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                        requires_improvement_plan.setChecked(true);
                    }
                    else{
                        requires_improvement_plan.setChecked(false);
                    }







                    TextView about_indicator=view.findViewById(R.id.about_indicator);
                    about_indicator.setText(indicatorRegs.get(idInd-1).getObservationsSpanish());




                    if(!Locale.getDefault().getLanguage().equals("es")){
                        about_indicator.setTextIsSelectable(true);
                        about_ev_1.setTextIsSelectable(true);
                        about_ev_2.setTextIsSelectable(true);
                        about_ev_3.setTextIsSelectable(true);
                        about_ev_4.setTextIsSelectable(true);
                        info_help.setVisibility(View.VISIBLE);
                    }else{
                        info_help.setVisibility(View.GONE);
                    }
                    dialog.setView(view);
                }else{
                    String msg="<i>";
                    boolean isMiradas=FieldChecker.isAMiradasUser(Session.getInstance().getUser());
                    if(Locale.getDefault().getLanguage().equals("es")){
                        msg+="Aún no se ha evaluado el indicador <b>"+idInd+"</b>. ";
                        if(isMiradas){
                            msg+="Por favor, continúe con la evaluación de indicadores para evaluar este indicador.";
                        }else{
                            msg+="Por favor, espere a que se haya evaluado este indicador.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        msg+="L'indicateur <b>"+idInd+"</b> n'a pas encore été évalué. ";
                        if(isMiradas){
                            msg+="Veuillez continuer l'évaluation des indicateurs pour évaluer cet indicateur.";
                        }else{
                            msg+="Veuillez attendre que cet indicateur soit évalué.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        msg+="Oraindik ez da <b>"+idInd+"</b>. adierazlea ebaluatu. ";
                        if(isMiradas){
                            msg+="Mesedez, jarraitu adierazleen ebaluazioarekin adierazle hau ebaluatzeko.";
                        }else{
                            msg+="Mesedez, itxaron adierazle hau ebaluatu arte.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        msg+="Encara no s'ha avaluat l'indicador <b>"+idInd+"</b>.";
                        if(isMiradas){
                            msg+="Si us plau, continuï amb l'avaluació d'indicadors per avaluar aquest indicador.";
                        }else{
                            msg+="Si us plau, esperi que s'hagi avaluat aquest indicador.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        msg+="De indicator <b>"+idInd+"</b> is nog niet geëvalueerd. ";
                        if(isMiradas){
                            msg+="Ga alstublieft door met de evaluatie van de indicatoren om deze indicator te evalueren.";
                        }else{
                            msg+="Wacht alstublieft totdat deze indicator is geëvalueerd.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        msg+="Aínda non se avaliou o indicador <b>"+idInd+"</b>. ";
                        if(isMiradas){
                            msg+="Por favor, continúe coa avaliación de indicadores para avaliar este indicador.";
                        }else{
                            msg+="Por favor, agarde a que se avalíe este indicador.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        msg+="Der Indikator <b>"+idInd+"</b> wurde noch nicht bewertet. ";
                        if(isMiradas){
                            msg+="Bitte setzen Sie die Bewertung der Indikatoren fort, um diesen Indikator zu bewerten.";
                        }else{
                            msg+="Bitte warten Sie, bis dieser Indikator bewertet wurde.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        msg+="L'indicatore <b>"+idInd+"</b> non è stato ancora valutato. ";
                        if(isMiradas){
                            msg+="Per favore, continui con la valutazione degli indicatori per valutare questo indicatore.";
                        }else{
                            msg+="Per favore, aspetti che questo indicatore sia stato valutato.";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        msg+="O indicador <b>"+idInd+"</b> ainda não foi avaliado. ";
                        if(isMiradas){
                            msg+="Por favor, continue com a avaliação dos indicadores para avaliar este indicador.";
                        }else{
                            msg+="Por favor, espere até que este indicador tenha sido avaliado.";
                        }
                    }else{
                        msg+="The indicator <b>"+idInd+"</b> has not yet been evaluated. ";
                        if(isMiradas){
                            msg+="Please continue with the evaluation of indicators to evaluate this indicator.";
                        }else{
                            msg+="Please wait until this indicator has been evaluated.";
                        }
                    }
                    msg+="</i>";
                    dialog.setMessage(Html.fromHtml(msg,0));
                }
                dialog.show();
            }
        };
    }


    private Drawable getBackground(int idInd){
        int drawableId=-1;
        if(indicatorRegs.size()>=idInd){
            IndicatorsEvaluationIndicatorReg reg=indicatorRegs.get(idInd-1);
            if(reg.getStatus().equals("REACHED")){
                drawableId = R.drawable.reached_ind;
            }else if(reg.getStatus().equals("IN_PROCESS")){
                drawableId = R.drawable.in_process_ind;
            }else{
                drawableId = R.drawable.in_start_ind;
            }
        }
        else {
            drawableId = R.drawable.empty_ind;
        }
        return AppCompatResources.getDrawable(SeeRealizedIndicatorsEvaluations.this,drawableId);
    }

    @SuppressLint("SetTextI18n")
    private void buildPointsTable(){
        numIndicatorsPerPriority=new int[4][3];
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                numIndicatorsPerPriority[i][j]=0;
            }
        }

        int[][] points={{evaluation.getScorePriorityZeroColourRed(),evaluation.getScorePriorityZeroColourYellow(),evaluation.getScorePriorityZeroColourGreen()},
                {evaluation.getScorePriorityOneColourRed(),evaluation.getScorePriorityOneColourYellow(),evaluation.getScorePriorityOneColourGreen()},
                {evaluation.getScorePriorityTwoColourRed(),evaluation.getScorePriorityTwoColourYellow(),evaluation.getScorePriorityTwoColourGreen()},
                {evaluation.getScorePriorityThreeColourRed(),evaluation.getScorePriorityThreeColourYellow(),evaluation.getScorePriorityThreeColourGreen()}
        };

        int ii=-1;
        int jj=-1;
        for(IndicatorsEvaluationIndicatorReg reg:indicatorRegs){
            Indicator ind=indicators.get(reg.getIdIndicator()-1);

            if(ind.getIndicatorPriority().equals("FUNDAMENTAL_INTEREST")){
                ii=3;
            }
            if(ind.getIndicatorPriority().equals("HIGH_INTEREST")){
                ii=2;
            }
            if(ind.getIndicatorPriority().equals("MEDIUM_INTEREST")){
                ii=1;
            }
            if(ind.getIndicatorPriority().equals("LOW_INTEREST")){
                ii=0;
            }

            if(reg.getStatus().equals("REACHED")){
                jj=2;
            }
            if(reg.getStatus().equals("IN_PROCESS")){
                jj=1;
            }
            if(reg.getStatus().equals("IN_START")){
                jj=0;
            }
            numIndicatorsPerPriority[ii][jj]+=1;

        }

        TextView currTextView=null;
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                currTextView = findViewById(nums_ids[i][j]);
                currTextView.setText("" + numIndicatorsPerPriority[i][j]);
                currTextView = findViewById(points_ids[i][j]);
                currTextView.setText("" + points[i][j]);
            }
        }

        currTextView=findViewById(R.id.points);
        currTextView.setText(""+evaluation.getTotalScore());

        TextView idData=findViewById(R.id.idData);
        String info="<ul>" +
                "<li><b>"+getString(R.string.org_or_serv)+": </b>"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getNameOrg()+"</li>" +
                "<li><b>"+getString(R.string.resp_prog)+": </b>"+IndicatorsEvaluationUtil.getInstance().getDirector().getFirst_name()+" "+IndicatorsEvaluationUtil.getInstance().getDirector().getLast_name()+"</li>" +
                "<li><b>"+getString(R.string.contact_data)+": </b>"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getTelephone()+"<br>"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getEmail()+"</li></ul>";
        idData.setText(Html.fromHtml(info,0));

        TextView evalTeam=findViewById(R.id.evalTeam);
        String externalConsultant=IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getExternalConsultant();
        if(externalConsultant.equals("-")){
            externalConsultant=getString(R.string.not_applicable);
        }
        String evalTeamInfo="<ul>"+
                "<li><b>"+getString(R.string.consultant)+": </b>"+externalConsultant+"</li>" +
                "<li><b>"+getString(R.string.responsible)+": </b>"+IndicatorsEvaluationUtil.getInstance().getResponsible().getFirst_name()+" "+IndicatorsEvaluationUtil.getInstance().getResponsible().getLast_name()+"</li>" +
                "<li><b>"+getString(R.string.professional)+": </b>"+IndicatorsEvaluationUtil.getInstance().getProfessional().getFirst_name()+" "+IndicatorsEvaluationUtil.getInstance().getProfessional().getLast_name()+"</li>"+
                "<li><b>"+getString(R.string.relative_name)+": </b>"+IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getRelative_name()+"</li>"+
                "<li><b>"+getString(R.string.patient_name)+": </b>"+IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getPatient_name()+"</li>" +
                "<li><b>"+getString(R.string.other_members)+": </b><ul>";

        String[] otherMembers=IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getOtherMembers().split(",");
        for(String member:otherMembers){
            evalTeamInfo+="<li>"+member+"</li>";
        }
        evalTeamInfo+="</ul></li></ul>";

        evalTeam.setText(Html.fromHtml(evalTeamInfo,0));

        TextView evalProc=findViewById(R.id.evalProc);
        String evalDatesInfo="<ul><li><b>"+getString(R.string.creation_date)+": </b>"+DateFormatter.timeStampToStrDate(IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getCreationDate())+"</li>";
        String[] evalDates=IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getEvaluationDates().split(",");
        for(int i=0;i<evalDates.length;i++){
            evalDatesInfo+="<li><b>"+getString(R.string.eval_date)+" "+(i+1)+": </b>"+DateFormatter.timeStampToStrDate(Long.parseLong(evalDates[i]))+"</li>";
        }
        evalDatesInfo+="</ul>";

        evalProc.setText(Html.fromHtml(evalDatesInfo,0));
        TextView overall=findViewById(R.id.overall);
        TextView helpOverall=findViewById(R.id.helpOverall);
        if(Locale.getDefault().getLanguage().equals("es")){
            overall.setTextIsSelectable(false);
            helpOverall.setVisibility(View.GONE);
        }
        else{
            overall.setTextIsSelectable(true);
            helpOverall.setVisibility(View.VISIBLE);
        }
        overall.setText(Html.fromHtml(evaluation.getConclusionsSpanish(),0));

        Map<String, Integer> highNumEvidencesMarkedPerAmbit=new HashMap<String,Integer>();
        Map<String, Integer> lowNumEvidencesMarkedPerAmbit=new HashMap<String,Integer>();

        for(IndicatorsEvaluationIndicatorReg reg:indicatorRegs){
            String key=reg.getIdSubSubAmbit()+","+reg.getIdSubAmbit()+","+reg.getIdAmbit();
            if (highNumEvidencesMarkedPerAmbit.containsKey(key)) {
                if(reg.getNumEvidencesMarked()>highNumEvidencesMarkedPerAmbit.get(key)) {
                    highNumEvidencesMarkedPerAmbit.replace(key, reg.getNumEvidencesMarked());
                }
            }else{
                highNumEvidencesMarkedPerAmbit.put(key,reg.getNumEvidencesMarked());
            }
            if(lowNumEvidencesMarkedPerAmbit.containsKey(key)){
                if(reg.getNumEvidencesMarked()<lowNumEvidencesMarkedPerAmbit.get(key)){
                    lowNumEvidencesMarkedPerAmbit.replace(key, reg.getNumEvidencesMarked());
                }
            }else{
                lowNumEvidencesMarkedPerAmbit.put(key,reg.getNumEvidencesMarked());
            }
        }

        String strongPoints="<ul>";
        for(String key : highNumEvidencesMarkedPerAmbit.keySet()){
            if(highNumEvidencesMarkedPerAmbit.get(key)==4 && lowNumEvidencesMarkedPerAmbit.get(key)==4) {
                String[] auxx = key.split(",");
                int idSubSubAmbit = Integer.parseInt(auxx[0]);
                int idSubAmbit = Integer.parseInt(auxx[1]);
                int idAmbit = Integer.parseInt(auxx[2]);

                if (idSubSubAmbit != -1) {
                    if(Locale.getDefault().getLanguage().equals("es")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionSpanish()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionFrench()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionBasque()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionCatalan()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionDutch()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionGalician()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionGerman()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionItalian()+"</li></b>";
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionPortuguese()+"</li></b>";
                    }else{
                        strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubSubAmbit(idSubSubAmbit, idSubAmbit, idAmbit).getDescriptionEnglish()+"</li></b>";
                    }
                } else {
                    if (idSubAmbit != -1) {
                        if(Locale.getDefault().getLanguage().equals("es")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionSpanish()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionFrench()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionBasque()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionCatalan()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionDutch()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionGalician()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionGerman()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionItalian()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionPortuguese()+"</li></b>";
                        }else{
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getSubAmbit(idSubAmbit, idAmbit).getDescriptionEnglish()+"</li></b>";
                        }
                    } else {
                        if(Locale.getDefault().getLanguage().equals("es")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionSpanish()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("fr")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionFrench()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("eu")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionBasque()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("ca")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionCatalan()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("nl")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionDutch()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("gl")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionGalician()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("de")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionGerman()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("it")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionItalian()+"</li></b>";
                        }else if(Locale.getDefault().getLanguage().equals("pt")){
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionPortuguese()+"</li></b>";
                        }else{
                            strongPoints+="<li><b>"+IndicatorsUtil.getInstance().getAmbit(idAmbit).getDescriptionEnglish()+"</li></b>";
                        }
                    }
                }
            }
        }

        strongPoints+="</ul>";

        TextView strenghts=findViewById(R.id.strengths);
        strenghts.setText(Html.fromHtml(strongPoints,0));


        TextView results=findViewById(R.id.results);

        int levelId=-1;
        if(evaluation.getLevel().equals("EXCELLENT")){
            levelId=R.string.excellent;
        }else if(evaluation.getLevel().equals("VERY_GOOD")){
            levelId=R.string.very_good;
        }else if(evaluation.getLevel().equals("GOOD")){
            levelId=R.string.good;
        }else if(evaluation.getLevel().equals("IMPROVABLE")){
            levelId=R.string.improvable;
        }else{
            levelId=R.string.very_improvable;
        }

        String resultText="<b>"+getString(R.string.level)+": </b>"+getString(levelId);

        results.setText(Html.fromHtml(resultText,0));


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            IndicatorsEvaluationUtil.removeInstance();
            IndicatorsUtil.removeInstance();
            Intent intent=new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

    private void downloadReport(){

        ExecutorService executor=Executors.newSingleThreadExecutor();


        String patientName= IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getPatient_name().replace(" ","-");
        String creationDate= DateFormatter.timeStampToStrDate(IndicatorsEvaluationUtil.getInstance().getEvaluatorTeam().getCreationDate()).replace("/","");
        String fileName="ORG_"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getIdOrganization()+"_"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getOrganizationType()+"_"+IndicatorsEvaluationUtil.getInstance().getEvaluatedOrganization().getIllness()+"_CENTER_"+IndicatorsEvaluationUtil.getInstance().getCenter().getIdCenter()+"_EVALTEAM_"+patientName+"-"+creationDate+"_TYPE_"+ IndicatorsEvaluationUtil.getInstance().getIndicatorsEvaluation().getEvaluationType()+".docx";


        String[] title={getString(R.string.downloading_report).replace("{Pepito}",patientName)};


        Context context=SeeRealizedIndicatorsEvaluations.this;
        NotifyCreator notification=Notify.Companion.with(context)
                .header(builder->{
                    builder.setIcon(android.R.drawable.stat_sys_download);
                    builder.setColor(R.color.miradas_color);
                    return null;
                })
                .asBigText(builder->{
                    builder.setTitle(title[0]);
                    builder.setExpandedText(getString(R.string.please_wait));
                    builder.setBigText(fileName);
                    return null;
                })
                .progress(builder->{
                    builder.setShowProgress(true);
                    return null;
                });

        int idNotification=notification.show();

        /*AwesomeProgressDialog chargingScreenDialog=new AwesomeProgressDialog(this)
                .setTitle(title[0])
                .setMessage(R.string.please_wait)
                .setColoredCircle(R.color.miradas_color)
                .setCancelable(false);
        chargingScreenDialog.show();*/


        executor.execute(()->{

            try {

                ByteArrayOutputStream bos=FileManager.downloadReport(fileName).join();
                byte[] data=bos.toByteArray();
                bos.close();
                InputStream is=new ByteArrayInputStream(data);
                XWPFDocument document=new XWPFDocument(is);
                FileOutputStream fos=new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator+fileName);
                document.write(fos);
                is.close();
                fos.close();
                runOnUiThread(()->{
                    NotificationManager notificationManager =
                            (NotificationManager) SeeRealizedIndicatorsEvaluations.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.cancel(idNotification);
                    //chargingScreenDialog.hide();
                    // Crear un Intent para abrir el archivo descargado
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                    Uri fileUri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        // Para Android 7.0 y superior, usar FileProvider
                        fileUri = FileProvider.getUriForFile(SeeRealizedIndicatorsEvaluations.this, getApplicationContext().getPackageName() + ".provider", file);
                    } else {
                        fileUri = Uri.fromFile(file);
                    }

                    Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
                    openFileIntent.setDataAndType(fileUri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    @SuppressLint("WrongConstant") PendingIntent openFilePendingIntent = PendingIntent.getActivity(
                            SeeRealizedIndicatorsEvaluations.this,
                            0,
                            openFileIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    Notify.Companion.with(SeeRealizedIndicatorsEvaluations.this)
                            .header(builder->{
                                builder.setIcon(android.R.drawable.stat_sys_download_done);
                                builder.setColor(R.color.miradas_color);
                                return null;
                            })
                            .asBigText(builder->{
                                builder.setTitle(getString(R.string.report_complete_download));
                                builder.setExpandedText(getString(R.string.you_can_see_report));
                                builder.setBigText("");
                                return null;
                            })
                            .actions(actions -> {
                                actions.add(new NotificationCompat.Action(android.R.drawable.edit_text,
                                        getString(R.string.open_report),
                                        openFilePendingIntent)
                                );
                                return null;
                            })
                            .progress(builder -> {
                                builder.setShowProgress(false);
                                return null;
                            }).show();
                    /*new AwesomeSuccessDialog(this)
                            .setTitle(R.string.report_complete_download)
                            .setMessage(R.string.you_can_see_report)
                            .setColoredCircle(com.aminography.primedatepicker.R.color.greenA700)
                            .setCancelable(true)
                            .setPositiveButtonText(getString(R.string.))
                            .setPositiveButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                            .setPositiveButtonTextColor(R.color.white)
                            .setNegativeButtonText(getString(R.string.))
                            .setNegativeButtonbackgroundColor(com.aminography.primedatepicker.R.color.greenA700)
                            .setNegativeButtonTextColor(R.color.white)
                            .setPositiveButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                                    Uri fileUri;

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        // Para Android 7.0 y superior, usar FileProvider
                                        fileUri = FileProvider.getUriForFile(SeeRealizedIndicatorsEvaluations.this, getApplicationContext().getPackageName() + ".provider", file);
                                    } else {
                                        fileUri = Uri.fromFile(file);
                                    }

                                    Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
                                    openFileIntent.setDataAndType(fileUri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                                    openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                    // Verificar si hay una aplicación que pueda manejar este Intent
                                    if (openFileIntent.resolveActivity(getPackageManager()) != null) {
                                        startActivity(openFileIntent);
                                    } else {

                                    }
                                }
                            })
                            .setNegativeButtonClick(new Closure() {
                                @Override
                                public void exec() {
                                    //click
                                }
                            })
                            .show();*/

                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });


    }


}