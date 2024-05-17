package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderSubTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableRow;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cli.indicators.Ambit;
import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationEvidenceReg;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import misc.FieldChecker;
import otea.connection.controller.EvidencesController;
import session.Session;

public class SeeRealizedIndicatorsEvaluations extends AppCompatActivity {

    List<Indicator> indicators;

    List<Evidence> evidences;

    List<IndicatorsEvaluationIndicatorReg> indicatorRegs;

    List<IndicatorsEvaluationEvidenceReg> evidenceRegs;

    FixedHeaderSubTableLayout mainTable;

    boolean isComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_realized_indicators_evaluations);

        FixedHeaderSubTableLayout columnHeaderTable=new FixedHeaderSubTableLayout(SeeRealizedIndicatorsEvaluations.this);
        FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(SeeRealizedIndicatorsEvaluations.this);

        IndicatorsEvaluation evaluation=Session.getInstance().getCurrEvaluation();

        indicatorRegs=Session.getInstance().GetAllIndicatorsRegsByIndicatorsEvaluation(evaluation);

        indicators=Session.getInstance().getIndicators(evaluation.getEvaluationType());

        isComplete=indicatorRegs.get(0).getEvaluationType().equals("COMPLETE");

        if(isComplete){
            evidenceRegs=Session.getInstance().GetAllEvidencesRegsByIndicatorsEvaluation(evaluation);
        }

        List<String> ambitsRed=new ArrayList<>();
        ambitsRed.add(getString(R.string.to_people));
        ambitsRed.add(getString(R.string.needs_id));
        ambitsRed.add(getString(R.string.prof_training));
        ambitsRed.add(getString(R.string.org_structure));
        ambitsRed.add(getString(R.string.resources));
        ambitsRed.add(getString(R.string.community));
        for(String ambitDescr:ambitsRed){
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(Html.fromHtml("<b>"+ambitDescr+"<b>",0));
            textView.setTextColor(getColor(R.color.black));
            textView.setPadding(5,5,5,5);
            textView.setTextSize(15.0f);
            textView.setBackground(getDrawable(R.drawable.empty_ind_no_borders));
            tableRowData.addView(textView);
        }
        columnHeaderTable.addView(tableRowData);

        FixedHeaderSubTableLayout rowHeaderTable=new FixedHeaderSubTableLayout(SeeRealizedIndicatorsEvaluations.this);

        List<String> interests=new ArrayList<>();
        interests.add(getString(R.string.fundamental_interest));
        interests.add(getString(R.string.high_interest));
        interests.add(getString(R.string.medium_interest));
        interests.add(getString(R.string.low_interest));
        for(String interest:interests){
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(Html.fromHtml("<b>"+interest+"</b>",0));
            textView.setTextColor(getColor(R.color.black));
            textView.setPadding(5,5,5,5);
            textView.setTextSize(10.0f);
            textView.setBackground(getDrawable(R.drawable.empty_ind_no_borders));
            tableRowData = new FixedHeaderTableRow(SeeRealizedIndicatorsEvaluations.this);
            tableRowData.addView(textView);
            rowHeaderTable.addView(tableRowData);
        }

        FixedHeaderSubTableLayout cornerTable=new FixedHeaderSubTableLayout(SeeRealizedIndicatorsEvaluations.this);
        FixedHeaderTableRow row=new FixedHeaderTableRow(SeeRealizedIndicatorsEvaluations.this);
        row.addView(new TextView(SeeRealizedIndicatorsEvaluations.this));
        cornerTable.addView(row);

        mainTable = new FixedHeaderSubTableLayout(SeeRealizedIndicatorsEvaluations.this);

        if(isComplete){
            generateCompleteTable();
        }
        else{
            generateSimpleTable();
        }


        FixedHeaderTableLayout layout=findViewById(R.id.tableIndEval);
        layout.computeScroll();
        layout.addViews(mainTable,columnHeaderTable,rowHeaderTable,cornerTable);

    }

    public Button createButton(String idIndicator){
        Button button = new Button(this);
        int drawable=-1;
        try{

            if(!idIndicator.isEmpty()) {
                int idInd=Integer.parseInt(idIndicator);
                button.setOnClickListener(new View.OnClickListener() {
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
                            if(isComplete){
                                view=inflater.inflate(R.layout.indicator_complete_result,null);
                                TextView evidencesCaption=view.findViewById(R.id.evidences_caption);
                                evidences= Session.getInstance().getEvidencesByIndicator(indicators.get(idInd-1).getIdSubSubAmbit(),indicators.get(idInd-1).getIdSubAmbit(),indicators.get(idInd-1).getIdAmbit(),idInd,indicators.get(idInd-1).getIndicatorType(),indicators.get(idInd-1).getIndicatorVersion(),indicators.get(idInd-1).getEvaluationType());
                                String evCaption="<ul>";
                                if(Locale.getDefault().getLanguage().equals("es")){
                                    evCaption+="<li><b>Evidencia 1: </b>" + evidences.get(0).getDescriptionSpanish()+"<br> <i>Evidencia Cumplida: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidencia 2: </b>" + evidences.get(1).getDescriptionSpanish()+"<br> <i>Evidencia Cumplida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidencia 3: </b>" + evidences.get(2).getDescriptionSpanish()+"<br> <i>Evidencia Cumplida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidencia 4: </b>" + evidences.get(3).getDescriptionSpanish()+"<br> <i>Evidencia Cumplida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("fr")){
                                    evCaption+="<li><b>Preuve 1: </b>" + evidences.get(0).getDescriptionFrench()+"<br> <i>Preuve Accomplie: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="OUI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                    evCaption+="<li><b>Preuve 2: </b>" + evidences.get(1).getDescriptionFrench()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="OUI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                    evCaption+="<li><b>Preuve 3: </b>" + evidences.get(2).getDescriptionFrench()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="OUI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                    evCaption+="<li><b>Preuve 4: </b>" + evidences.get(3).getDescriptionFrench()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="OUI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("eu")){
                                    evCaption+="<li><b>1. froga: </b>" + evidences.get(0).getDescriptionBasque()+"<br> <i>Frogak Beteak: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="BAI</b></i></li>";
                                    }else{
                                        evCaption+="EZ</b></i></li>";
                                    }
                                    evCaption+="<li><b>2. froga: </b>" + evidences.get(1).getDescriptionBasque()+"<br> <i>Frogak Beteak: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="BAI</b></i></li>";
                                    }else{
                                        evCaption+="EZ</b></i></li>";
                                    }
                                    evCaption+="<li><b>3. froga: </b>" + evidences.get(2).getDescriptionBasque()+"<br> <i>Frogak Beteak: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="BAI</b></i></li>";
                                    }else{
                                        evCaption+="EZ</b></i></li>";
                                    }
                                    evCaption+="<li><b>4. froga: </b>" + evidences.get(3).getDescriptionBasque()+"<br> <i>Frogak Beteak: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="BAI</b></i></li>";
                                    }else{
                                        evCaption+="EZ</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("ca")){
                                    evCaption+="<li><b>Evidència 1: </b>" + evidences.get(0).getDescriptionCatalan()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidència 2: </b>" + evidences.get(1).getDescriptionCatalan()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidència 3: </b>" + evidences.get(2).getDescriptionCatalan()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidència 4: </b>" + evidences.get(3).getDescriptionCatalan()+"<br> <i>Evidència Complida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="SÍ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("nl")){
                                    evCaption+="<li><b>Bewijs 1: </b>" + evidences.get(0).getDescriptionDutch()+"<br> <i>Bewijs Voltooid: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEE</b></i></li>";
                                    }
                                    evCaption+="<li><b>Bewijs 2: </b>" + evidences.get(1).getDescriptionDutch()+"<br> <i>Bewijs Voltooid: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEE</b></i></li>";
                                    }
                                    evCaption+="<li><b>Bewijs 3: </b>" + evidences.get(2).getDescriptionDutch()+"<br> <i>Bewijs Voltooid: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEE</b></i></li>";
                                    }
                                    evCaption+="<li><b>Bewijs 4: </b>" + evidences.get(3).getDescriptionDutch()+"<br> <i>Bewijs Voltooid: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEE</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("gl")){
                                    evCaption+="<li><b>Evidencia 1: </b>" + evidences.get(0).getDescriptionGalician()+"<br> <i>Evidencia Cumprida: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="SI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidencia 2: </b>" + evidences.get(1).getDescriptionGalician()+"<br> <i>Evidencia Cumprida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="SI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidencia 3: </b>" + evidences.get(2).getDescriptionGalician()+"<br> <i>Evidencia Cumprida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="SI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidencia 4: </b>" + evidences.get(3).getDescriptionGalician()+"<br> <i>Evidencia Cumprida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="SI</b></i></li>";
                                    }else{
                                        evCaption+="NON</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("de")){
                                    evCaption+="<li><b>Beweis 1: </b>" + evidences.get(0).getDescriptionGerman()+"<br> <i>Beweis Erbracht: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEIN</b></i></li>";
                                    }
                                    evCaption+="<li><b>Beweis 2: </b>" + evidences.get(1).getDescriptionGerman()+"<br> <i>Beweis Erbracht: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEIN</b></i></li>";
                                    }
                                    evCaption+="<li><b>Beweis 3: </b>" + evidences.get(2).getDescriptionGerman()+"<br> <i>Beweis Erbracht: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEIN</b></i></li>";
                                    }
                                    evCaption+="<li><b>Beweis 4: </b>" + evidences.get(3).getDescriptionGerman()+"<br> <i>Beweis Erbracht: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="JA</b></i></li>";
                                    }else{
                                        evCaption+="NEIN</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("it")){
                                    evCaption+="<li><b>Prova 1: </b>" + evidences.get(0).getDescriptionItalian()+"<br> <i>Prova Compiuta: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="SÌ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Prova 2: </b>" + evidences.get(1).getDescriptionItalian()+"<br> <i>Prova Compiuta: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="SÌ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Prova 3: </b>" + evidences.get(2).getDescriptionItalian()+"<br> <i>Prova Compiuta: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="SÌ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Prova 4: </b>" + evidences.get(3).getDescriptionItalian()+"<br> <i>Prova Compiuta: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="SÌ</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                }else if(Locale.getDefault().getLanguage().equals("pt")){
                                    evCaption+="<li><b>Evidência 1: </b>" + evidences.get(0).getDescriptionPortuguese()+"<br> <i>Evidência Cumprida: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="SIM</b></i></li>";
                                    }else{
                                        evCaption+="NÃO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidência 2: </b>" + evidences.get(1).getDescriptionPortuguese()+"<br> <i>Evidência Cumprida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="SIM</b></i></li>";
                                    }else{
                                        evCaption+="NÃO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidência 3: </b>" + evidences.get(2).getDescriptionPortuguese()+"<br> <i>Evidência Cumprida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="SIM</b></i></li>";
                                    }else{
                                        evCaption+="NÃO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidência 4: </b>" + evidences.get(3).getDescriptionPortuguese()+"<br> <i>Evidência Cumprida: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="SIM</b></i></li>";
                                    }else{
                                        evCaption+="NÃO</b></i></li>";
                                    }
                                }else{
                                    evCaption+="<li><b>Evidence 1: </b>" + evidences.get(0).getDescriptionEnglish()+"<br> <i>Evidence fulfilled: <b>";
                                    if(evidenceRegs.get(4*(idInd-1)).getIsMarked()==1){
                                        evCaption+="YES</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidence 2: </b>" + evidences.get(1).getDescriptionEnglish()+"<br> <i>Evidence fulfilled: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+1).getIsMarked()==1){
                                        evCaption+="YES</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidence 3: </b>" + evidences.get(2).getDescriptionEnglish()+"<br> <i>Evidence fulfilled: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+2).getIsMarked()==1){
                                        evCaption+="YES</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                    evCaption+="<li><b>Evidence 4: </b>" + evidences.get(3).getDescriptionEnglish()+"<br> <i>Evidence fulfilled: <b>";
                                    if(evidenceRegs.get((4*(idInd-1))+3).getIsMarked()==1){
                                        evCaption+="YES</b></i></li>";
                                    }else{
                                        evCaption+="NO</b></i></li>";
                                    }
                                }
                                evCaption+="</ul>";
                                evidencesCaption.setText(Html.fromHtml(evCaption,0));
                            }else{
                                view=inflater.inflate(R.layout.indicator_simple_result,null);
                            }
                            TextView indicatorCaption=view.findViewById(R.id.indicator_caption);
                            String indCaption="";
                            if(Locale.getDefault().getLanguage().equals("es")){
                                indCaption="<b>Indicador " + idInd + ": </b>" + indicators.get(0).getDescriptionSpanish();
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                indCaption="<b>Indicateur " + idInd + ": </b>" + indicators.get(0).getDescriptionFrench();
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                indCaption="<b>"+idInd + ". adierazlea: </b>" + indicators.get(0).getDescriptionBasque();
                            }else if(Locale.getDefault().getLanguage().equals("ca")){
                                indCaption="<b>Indicador " + idInd + ": </b>" + indicators.get(0).getDescriptionCatalan();
                            }else if(Locale.getDefault().getLanguage().equals("nl")){
                                indCaption="<b>Indicator " + idInd + ": " + indicators.get(0).getDescriptionDutch();
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                indCaption="<b>Indicador " + idInd + ": " + indicators.get(0).getDescriptionGalician();
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                indCaption="<b>Indikator " + idInd + ": " + indicators.get(0).getDescriptionGerman();
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                indCaption="<b>Indicatore " + idInd + ": " + indicators.get(0).getDescriptionItalian();
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                indCaption="<b>Indicador " + idInd + ": " + indicators.get(0).getDescriptionPortuguese();
                            }else{
                                indCaption="<b>Indicator " + idInd + ": " + indicators.get(0).getDescriptionEnglish();
                            }
                            indicatorCaption.setText(Html.fromHtml(indCaption,0));
                            dialog.setView(view);
                        }else{
                            String msg="<i>";
                            boolean isMiradas=FieldChecker.isAMiradasUser(Session.getInstance().getUser());
                            if(Locale.getDefault().getLanguage().equals("es")){
                                msg+="Aún no se ha evaluado el indicador <b>"+idInd+"</b>.";
                                if(isMiradas){
                                    msg+="Por favor, continúe con la evaluación de indicadores para evaluar este indicador.";
                                }else{
                                    msg+="Por favor, espere a que se haya evaluado este indicador.";
                                }
                            }else if(Locale.getDefault().getLanguage().equals("fr")){
                                msg+="L'indicateur <b>"+idInd+"</b> n'a pas encore été évalué.";
                                if(isMiradas){
                                    msg+="Veuillez continuer l'évaluation des indicateurs pour évaluer cet indicateur.";
                                }else{
                                    msg+="Veuillez attendre que cet indicateur soit évalué.";
                                }
                            }else if(Locale.getDefault().getLanguage().equals("eu")){
                                msg+="Oraindik ez da <b>"+idInd+"</b>. adierazlea ebaluatu";
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
                                msg+="De indicator <b>"+idInd+"</b> is nog niet geëvalueerd.";
                                if(isMiradas){
                                    msg+="Ga alstublieft door met de evaluatie van de indicatoren om deze indicator te evalueren.";
                                }else{
                                    msg+="Wacht alstublieft totdat deze indicator is geëvalueerd.";
                                }
                            }else if(Locale.getDefault().getLanguage().equals("gl")){
                                msg+="Aínda non se avaliou o indicador <b>"+idInd+"</b>.";
                                if(isMiradas){
                                    msg+="Por favor, continúe coa avaliación de indicadores para avaliar este indicador.";
                                }else{
                                    msg+="Por favor, agarde a que se avalíe este indicador.";
                                }
                            }else if(Locale.getDefault().getLanguage().equals("de")){
                                msg+="Der Indikator <b>"+idInd+"</b> wurde noch nicht bewertet.";
                                if(isMiradas){
                                    msg+="Bitte setzen Sie die Bewertung der Indikatoren fort, um diesen Indikator zu bewerten.";
                                }else{
                                    msg+="Bitte warten Sie, bis dieser Indikator bewertet wurde.";
                                }
                            }else if(Locale.getDefault().getLanguage().equals("it")){
                                msg+="L'indicatore <b>"+idInd+"</b> non è stato ancora valutato.";
                                if(isMiradas){
                                    msg+="Per favore, continui con la valutazione degli indicatori per valutare questo indicatore.";
                                }else{
                                    msg+="Per favore, aspetti che questo indicatore sia stato valutato.";
                                }
                            }else if(Locale.getDefault().getLanguage().equals("pt")){
                                msg+="O indicador <b>"+idInd+"</b> ainda não foi avaliado.";
                                if(isMiradas){
                                    msg+="Por favor, continue com a avaliação dos indicadores para avaliar este indicador.";
                                }else{
                                    msg+="Por favor, espere até que este indicador tenha sido avaliado.";
                                }
                            }else{
                                msg+="The indicator <b>"+idInd+"</b> has not yet been evaluated.";
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
                });
                button.setText(Html.fromHtml(idIndicator, 0));
                if(indicatorRegs.size()>=idInd){
                    IndicatorsEvaluationIndicatorReg reg=indicatorRegs.get(idInd-1);
                    if(reg.getStatus().equals("REACHED")){
                        drawable = R.drawable.reached_ind;
                    }else if(reg.getStatus().equals("IN_PROCESS")){
                        drawable = R.drawable.in_process_ind;
                    }else{
                        drawable = R.drawable.in_start_ind;
                    }
                }
                else {
                    drawable = R.drawable.empty_ind;
                }
            }
            else{
                drawable=R.drawable.empty_ind_no_borders;
            }
            button.setBackground(getDrawable(drawable));
            button.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            return button;
        }catch(NumberFormatException e){
            return null;
        }
    }

    public void generateCompleteTable(){

        //Fundamental interest

        FixedHeaderTableRow mainTableRow = new FixedHeaderTableRow(this);

        FixedHeaderSubTableLayout subTable=new FixedHeaderSubTableLayout(this);
        FixedHeaderSubTableLayout subSubTable = new FixedHeaderSubTableLayout(this);

        FixedHeaderTableRow subTableRow = new FixedHeaderTableRow(this);
        FixedHeaderTableRow subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton("2"));
        subSubTableRow.addView(createButton("7"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("16"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton("1"));
        subSubTableRow.addView(createButton("6"));
        subSubTableRow.addView(createButton("11"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("17"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("32"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("30"));
        subSubTableRow.addView(createButton("36"));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("29"));
        subSubTableRow.addView(createButton("34"));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("46"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("45"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("44"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("43"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("58"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("50"));
        subSubTableRow.addView(createButton("53"));
        subSubTableRow.addView(createButton("55"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("67"));
        subSubTable.addView(subSubTableRow);


        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);


        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);




        mainTable.addView(mainTableRow);



        //High interest

        mainTableRow = new FixedHeaderTableRow(this);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);

        subTableRow = new FixedHeaderTableRow(this);
        subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton("5"));
        subSubTableRow.addView(createButton("10"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("15"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton("3"));
        subSubTableRow.addView(createButton("8"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("13"));
        subSubTableRow.addView(createButton("14"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("18"));
        subSubTableRow.addView(createButton("20"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("26"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("31"));
        subSubTableRow.addView(createButton("35"));
        subSubTableRow.addView(createButton("37"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("40"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("54"));
        subSubTableRow.addView(createButton("56"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);




        mainTable.addView(mainTableRow);


        //Medium interest

        mainTableRow = new FixedHeaderTableRow(this);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);

        subTableRow = new FixedHeaderTableRow(this);
        subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("25"));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton("4"));
        subSubTableRow.addView(createButton("9"));
        subSubTableRow.addView(createButton("12"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("19"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("21"));
        subSubTableRow.addView(createButton("24"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("33"));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("41"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("38"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("49"));
        subSubTableRow.addView(createButton("52"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("57"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("61"));
        subSubTableRow.addView(createButton("63"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("66"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("70"));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("69"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);




        mainTable.addView(mainTableRow);



        //Low interest

        mainTableRow = new FixedHeaderTableRow(this);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);

        subTableRow = new FixedHeaderTableRow(this);
        subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("23"));
        subSubTableRow.addView(createButton("28"));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("22"));
        subSubTableRow.addView(createButton("27"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("42"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("39"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("47"));
        subSubTableRow.addView(createButton("48"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("60"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("51"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("59"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("62"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("65"));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("64"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("68"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        mainTable.addView(mainTableRow);
    }

    public void generateSimpleTable(){

        //Fundamental interest

        FixedHeaderTableRow mainTableRow = new FixedHeaderTableRow(this);

        FixedHeaderSubTableLayout subTable=new FixedHeaderSubTableLayout(this);
        FixedHeaderSubTableLayout subSubTable = new FixedHeaderSubTableLayout(this);

        FixedHeaderTableRow subTableRow = new FixedHeaderTableRow(this);
        FixedHeaderTableRow subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton("1"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton("2"));
        subSubTableRow.addView(createButton("4"));
        subSubTableRow.addView(createButton("6"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("9"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("17"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("23"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("22"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("27"));
        subSubTableRow.addView(createButton("29"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("37"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);




        mainTable.addView(mainTableRow);



        //High interest

        mainTableRow = new FixedHeaderTableRow(this);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);

        subTableRow = new FixedHeaderTableRow(this);
        subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton("3"));
        subSubTableRow.addView(createButton("5"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("7"));
        subSubTableRow.addView(createButton("8"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("10"));
        subSubTableRow.addView(createButton("12"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("18"));
        subSubTableRow.addView(createButton("19"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("20"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("30"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);




        mainTable.addView(mainTableRow);


        //Medium interest

        mainTableRow = new FixedHeaderTableRow(this);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);

        subTableRow = new FixedHeaderTableRow(this);
        subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("11"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("13"));
        subSubTableRow.addView(createButton("15"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("21"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("20"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("33"));
        subSubTableRow.addView(createButton("35"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("36"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("40"));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("39"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);




        mainTable.addView(mainTableRow);



        //Low interest

        mainTableRow = new FixedHeaderTableRow(this);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);

        subTableRow = new FixedHeaderTableRow(this);
        subSubTableRow = new FixedHeaderTableRow(this);


        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);

        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("14"));
        subSubTableRow.addView(createButton("16"));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);



        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));


        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("24"));
        subSubTableRow.addView(createButton("25"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("32"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("28"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("31"));
        subSubTableRow.addView(createButton(""));
        subSubTableRow.addView(createButton("34"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);

        subTable=new FixedHeaderSubTableLayout(this);
        subSubTable = new FixedHeaderSubTableLayout(this);
        subTableRow = new FixedHeaderTableRow(this);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton(""));
        subSubTable.addView(subSubTableRow);

        subSubTableRow = new FixedHeaderTableRow(this);
        subSubTableRow.addView(createButton("38"));

        subSubTable.addView(subSubTableRow);
        subTableRow.addView(subSubTable);
        subTable.addView(subTableRow);
        mainTableRow.addView(subTable);


        mainTable.addView(mainTableRow);
    }


        

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

}