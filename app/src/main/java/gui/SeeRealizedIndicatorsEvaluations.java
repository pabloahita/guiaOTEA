package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
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
            //generateSimpleTable();
        }


        FixedHeaderTableLayout layout=findViewById(R.id.tableIndEval);
        layout.computeScroll();
        layout.addViews(mainTable,columnHeaderTable,rowHeaderTable,cornerTable);

    }

    public void generateCompleteTable(){

        //Fundamental interest

        LayoutInflater inflater=getLayoutInflater();

        FixedHeaderTableRow mainTableRow = new FixedHeaderTableRow(this);

        View view=inflater.inflate(R.layout.fund_interest_to_people_complete,null);
        Button ind2=view.findViewById(R.id.ind2);
        Button ind7=view.findViewById(R.id.ind7);
        Button ind16=view.findViewById(R.id.ind16);
        Button ind1=view.findViewById(R.id.ind1);
        Button ind6=view.findViewById(R.id.ind6);
        Button ind11=view.findViewById(R.id.ind11);
        Button ind17=view.findViewById(R.id.ind17);

        ind2.setOnClickListener(generateClickListener(2));
        ind2.setBackground(getBackground(2));
        ind7.setOnClickListener(generateClickListener(7));
        ind7.setBackground(getBackground(7));
        ind16.setOnClickListener(generateClickListener(16));
        ind16.setBackground(getBackground(16));
        ind1.setOnClickListener(generateClickListener(1));
        ind1.setBackground(getBackground(1));
        ind6.setOnClickListener(generateClickListener(6));
        ind6.setBackground(getBackground(6));
        ind11.setOnClickListener(generateClickListener(11));
        ind11.setBackground(getBackground(11));
        ind17.setOnClickListener(generateClickListener(17));
        ind17.setBackground(getBackground(17));


        mainTableRow.addView(view);

        View view2=inflater.inflate(R.layout.fund_interest_needs_id_complete,null);
        Button ind32=view2.findViewById(R.id.ind32);
        Button ind30=view2.findViewById(R.id.ind30);
        Button ind36=view2.findViewById(R.id.ind36);
        Button ind29=view2.findViewById(R.id.ind29);
        Button ind34=view2.findViewById(R.id.ind34);

        ind32.setOnClickListener(generateClickListener(32));
        ind32.setBackground(getBackground(32));
        ind30.setOnClickListener(generateClickListener(30));
        ind30.setBackground(getBackground(30));
        ind36.setOnClickListener(generateClickListener(36));
        ind36.setBackground(getBackground(36));
        ind29.setOnClickListener(generateClickListener(29));
        ind29.setBackground(getBackground(29));
        ind34.setOnClickListener(generateClickListener(34));
        ind34.setBackground(getBackground(34));
        mainTableRow.addView(view2);

        View view3=inflater.inflate(R.layout.fund_interest_prof_learning_complete,null);
        Button ind46=view3.findViewById(R.id.ind46);
        Button ind45=view3.findViewById(R.id.ind45);
        Button ind44=view3.findViewById(R.id.ind44);
        Button ind43=view3.findViewById(R.id.ind43);

        ind46.setOnClickListener(generateClickListener(46));
        ind46.setBackground(getBackground(46));
        ind45.setOnClickListener(generateClickListener(45));
        ind45.setBackground(getBackground(45));
        ind44.setOnClickListener(generateClickListener(44));
        ind44.setBackground(getBackground(44));
        ind43.setOnClickListener(generateClickListener(43));
        ind43.setBackground(getBackground(43));

        mainTableRow.addView(view3);


        View view4=inflater.inflate(R.layout.fund_interest_structure_complete,null);
        Button ind58=view4.findViewById(R.id.ind58);
        Button ind50=view4.findViewById(R.id.ind50);
        Button ind53=view4.findViewById(R.id.ind53);
        Button ind55=view4.findViewById(R.id.ind55);

        ind58.setOnClickListener(generateClickListener(58));
        ind58.setBackground(getBackground(58));
        ind50.setOnClickListener(generateClickListener(50));
        ind50.setBackground(getBackground(50));
        ind53.setOnClickListener(generateClickListener(53));
        ind53.setBackground(getBackground(53));
        ind55.setOnClickListener(generateClickListener(55));
        ind55.setBackground(getBackground(55));

        mainTableRow.addView(view4);

        View view5=inflater.inflate(R.layout.fund_interest_resources_complete,null);
        Button ind67=view5.findViewById(R.id.ind67);

        ind67.setOnClickListener(generateClickListener(67));
        ind67.setBackground(getBackground(67));
        mainTableRow.addView(view5);

        View view6=inflater.inflate(R.layout.fund_interest_community_complete,null);
        mainTableRow.addView(view6);


        mainTable.addView(mainTableRow);

        //High interest

        mainTableRow = new FixedHeaderTableRow(this);

        View view7=inflater.inflate(R.layout.high_interest_to_people_complete,null);
        Button ind5=view7.findViewById(R.id.ind5);
        Button ind10=view7.findViewById(R.id.ind10);
        Button ind15=view7.findViewById(R.id.ind15);
        Button ind3=view7.findViewById(R.id.ind3);
        Button ind8=view7.findViewById(R.id.ind8);
        Button ind13=view7.findViewById(R.id.ind13);
        Button ind14=view7.findViewById(R.id.ind14);
        Button ind18=view7.findViewById(R.id.ind18);
        Button ind20=view7.findViewById(R.id.ind20);
        Button ind26=view7.findViewById(R.id.ind26);

        ind5.setOnClickListener(generateClickListener(5));
        ind5.setBackground(getBackground(5));
        ind10.setOnClickListener(generateClickListener(10));
        ind10.setBackground(getBackground(10));
        ind15.setOnClickListener(generateClickListener(15));
        ind15.setBackground(getBackground(15));
        ind3.setOnClickListener(generateClickListener(3));
        ind3.setBackground(getBackground(3));
        ind8.setOnClickListener(generateClickListener(8));
        ind8.setBackground(getBackground(8));
        ind13.setOnClickListener(generateClickListener(13));
        ind13.setBackground(getBackground(13));
        ind14.setOnClickListener(generateClickListener(14));
        ind14.setBackground(getBackground(14));
        ind18.setOnClickListener(generateClickListener(18));
        ind18.setBackground(getBackground(18));
        ind20.setOnClickListener(generateClickListener(20));
        ind20.setBackground(getBackground(20));
        ind26.setOnClickListener(generateClickListener(26));
        ind26.setBackground(getBackground(26));
        mainTableRow.addView(view7);

        View view8=inflater.inflate(R.layout.high_interest_needs_id_complete,null);
        Button ind31=view8.findViewById(R.id.ind31);
        Button ind35=view8.findViewById(R.id.ind35);
        Button ind37=view8.findViewById(R.id.ind37);

        ind31.setOnClickListener(generateClickListener(31));
        ind31.setBackground(getBackground(31));
        ind35.setOnClickListener(generateClickListener(35));
        ind35.setBackground(getBackground(35));
        ind37.setOnClickListener(generateClickListener(37));
        ind37.setBackground(getBackground(37));

        mainTableRow.addView(view8);

        View view9=inflater.inflate(R.layout.high_interest_prof_learning_complete,null);
        Button ind40=view9.findViewById(R.id.ind40);
        ind40.setOnClickListener(generateClickListener(40));
        ind40.setBackground(getBackground(40));
        mainTableRow.addView(view9);

        View view10=inflater.inflate(R.layout.high_interest_structure_complete,null);
        Button ind54=view10.findViewById(R.id.ind54);
        Button ind56=view10.findViewById(R.id.ind56);
        ind54.setOnClickListener(generateClickListener(54));
        ind54.setBackground(getBackground(54));
        ind56.setOnClickListener(generateClickListener(56));
        ind56.setBackground(getBackground(56));
        mainTableRow.addView(view10);


        View view11=inflater.inflate(R.layout.fund_interest_community_complete,null);
        mainTableRow.addView(view11);

        View view12=inflater.inflate(R.layout.fund_interest_community_complete,null);
        mainTableRow.addView(view12);


        mainTable.addView(mainTableRow);
        //Medium interest

        mainTableRow = new FixedHeaderTableRow(this);

        View view13=inflater.inflate(R.layout.medium_interest_to_people_complete,null);
        Button ind25=view13.findViewById(R.id.ind25);
        Button ind4=view13.findViewById(R.id.ind4);
        Button ind9=view13.findViewById(R.id.ind9);
        Button ind12=view13.findViewById(R.id.ind12);
        Button ind19=view13.findViewById(R.id.ind19);
        Button ind21=view13.findViewById(R.id.ind21);
        Button ind24=view13.findViewById(R.id.ind24);

        ind25.setOnClickListener(generateClickListener(25));
        ind25.setBackground(getBackground(25));
        ind4.setOnClickListener(generateClickListener(4));
        ind4.setBackground(getBackground(4));
        ind9.setOnClickListener(generateClickListener(9));
        ind9.setBackground(getBackground(9));
        ind12.setOnClickListener(generateClickListener(12));
        ind12.setBackground(getBackground(12));
        ind19.setOnClickListener(generateClickListener(19));
        ind19.setBackground(getBackground(19));
        ind21.setOnClickListener(generateClickListener(21));
        ind21.setBackground(getBackground(21));
        ind24.setOnClickListener(generateClickListener(24));
        ind24.setBackground(getBackground(24));
        mainTableRow.addView(view13);

        View view14=inflater.inflate(R.layout.medium_interest_needs_id_complete,null);
        Button ind33=view14.findViewById(R.id.ind33);

        ind33.setOnClickListener(generateClickListener(33));
        ind33.setBackground(getBackground(33));

        mainTableRow.addView(view14);

        View view15=inflater.inflate(R.layout.medium_interest_prof_learning_complete,null);
        Button ind41=view15.findViewById(R.id.ind41);
        Button ind38=view15.findViewById(R.id.ind38);
        ind41.setOnClickListener(generateClickListener(41));
        ind41.setBackground(getBackground(41));
        ind38.setOnClickListener(generateClickListener(38));
        ind38.setBackground(getBackground(38));
        mainTableRow.addView(view15);

        View view16=inflater.inflate(R.layout.medium_interest_structure_complete,null);
        Button ind49=view16.findViewById(R.id.ind49);
        Button ind52=view16.findViewById(R.id.ind52);
        Button ind57=view16.findViewById(R.id.ind57);
        Button ind61=view16.findViewById(R.id.ind61);
        Button ind63=view16.findViewById(R.id.ind63);


        ind49.setOnClickListener(generateClickListener(49));
        ind49.setBackground(getBackground(49));
        ind52.setOnClickListener(generateClickListener(52));
        ind52.setBackground(getBackground(52));
        ind57.setOnClickListener(generateClickListener(57));
        ind57.setBackground(getBackground(57));
        ind61.setOnClickListener(generateClickListener(61));
        ind61.setBackground(getBackground(61));
        ind63.setOnClickListener(generateClickListener(63));
        ind63.setBackground(getBackground(63));
        mainTableRow.addView(view16);


        View view17=inflater.inflate(R.layout.medium_interest_resources_complete,null);
        Button ind66=view17.findViewById(R.id.ind66);
        ind66.setOnClickListener(generateClickListener(66));
        ind66.setBackground(getBackground(66));
        mainTableRow.addView(view17);

        View view18=inflater.inflate(R.layout.medium_interest_community_complete,null);
        Button ind70=view18.findViewById(R.id.ind70);
        Button ind69=view18.findViewById(R.id.ind69);
        ind70.setOnClickListener(generateClickListener(70));
        ind70.setBackground(getBackground(69));
        ind69.setOnClickListener(generateClickListener(69));
        ind69.setBackground(getBackground(69));
        mainTableRow.addView(view18);




        mainTable.addView(mainTableRow);
        //Low interest



        mainTableRow = new FixedHeaderTableRow(this);

        View view19=inflater.inflate(R.layout.low_interest_to_people_complete,null);
        Button ind23=view19.findViewById(R.id.ind23);
        Button ind28=view19.findViewById(R.id.ind28);
        Button ind22=view19.findViewById(R.id.ind22);
        Button ind27=view19.findViewById(R.id.ind27);

        ind23.setOnClickListener(generateClickListener(23));
        ind23.setBackground(getBackground(23));
        ind28.setOnClickListener(generateClickListener(28));
        ind28.setBackground(getBackground(28));
        ind22.setOnClickListener(generateClickListener(22));
        ind22.setBackground(getBackground(22));
        ind27.setOnClickListener(generateClickListener(27));
        ind27.setBackground(getBackground(27));
        mainTableRow.addView(view19);

        View view20=inflater.inflate(R.layout.low_interest_needs_id_complete,null);
        mainTableRow.addView(view20);

        View view21=inflater.inflate(R.layout.low_interest_prof_learning_complete,null);
        Button ind42=view21.findViewById(R.id.ind42);
        Button ind39=view21.findViewById(R.id.ind39);
        Button ind47=view21.findViewById(R.id.ind47);
        Button ind48=view21.findViewById(R.id.ind48);

        ind42.setOnClickListener(generateClickListener(42));
        ind42.setBackground(getBackground(42));
        ind39.setOnClickListener(generateClickListener(39));
        ind39.setBackground(getBackground(39));
        ind47.setOnClickListener(generateClickListener(47));
        ind47.setBackground(getBackground(47));
        ind48.setOnClickListener(generateClickListener(48));
        ind48.setBackground(getBackground(48));

        mainTableRow.addView(view21);

        View view22=inflater.inflate(R.layout.low_interest_structure_complete,null);
        Button ind60=view22.findViewById(R.id.ind60);
        Button ind51=view22.findViewById(R.id.ind51);
        Button ind59=view22.findViewById(R.id.ind59);
        Button ind62=view22.findViewById(R.id.ind62);

        ind60.setOnClickListener(generateClickListener(60));
        ind60.setBackground(getBackground(60));
        ind51.setOnClickListener(generateClickListener(51));
        ind51.setBackground(getBackground(51));
        ind59.setOnClickListener(generateClickListener(59));
        ind59.setBackground(getBackground(59));
        ind62.setOnClickListener(generateClickListener(62));
        ind62.setBackground(getBackground(62));

        mainTableRow.addView(view22);

        View view23=inflater.inflate(R.layout.low_interest_resources_complete,null);
        Button ind65=view23.findViewById(R.id.ind65);
        Button ind64=view23.findViewById(R.id.ind64);

        ind65.setOnClickListener(generateClickListener(65));
        ind65.setBackground(getBackground(65));
        ind64.setOnClickListener(generateClickListener(64));
        ind64.setBackground(getBackground(64));
        mainTableRow.addView(view23);

        View view24=inflater.inflate(R.layout.low_interest_community_complete,null);

        Button ind68=view24.findViewById(R.id.ind68);
        ind68.setOnClickListener(generateClickListener(68));
        ind68.setBackground(getBackground(68));
        mainTableRow.addView(view24);


        mainTable.addView(mainTableRow);



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
                        indCaption="<b>Indicador " + idInd + ": </b>" + indicators.get(idInd-1).getDescriptionSpanish()+"<br> <i>Requiere plan de mejora: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="SÍ</b></i>";
                        }else{
                            indCaption+="NO</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("fr")){
                        indCaption="<b>Indicateur " + idInd + ": </b>" + indicators.get(idInd-1).getDescriptionFrench()+"<br> <i>Nécessite un plan d'amélioration: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="OUI</b></i>";
                        }else{
                            indCaption+="NON</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("eu")){
                        indCaption="<b>"+idInd + ". adierazlea: </b>" + indicators.get(idInd-1).getDescriptionBasque()+"<br> <i>Hobekuntza plana behar du: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="BAI</b></i>";
                        }else{
                            indCaption+="EZ</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("ca")){
                        indCaption="<b>Indicador " + idInd + ": </b>" + indicators.get(idInd-1).getDescriptionCatalan()+"<br> <i>Requereix un pla de millora: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="SÍ</b></i>";
                        }else{
                            indCaption+="NO</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("nl")){
                        indCaption="<b>Indicator " + idInd + ": " + indicators.get(idInd-1).getDescriptionDutch()+"<br> <i>Vereist een verbeterplan: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="JA</b></i>";
                        }else{
                            indCaption+="NEE</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("gl")){
                        indCaption="<b>Indicador " + idInd + ": " + indicators.get(idInd-1).getDescriptionGalician()+"<br> <i>Require plan de mellora: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="SI</b></i>";
                        }else{
                            indCaption+="NON</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("de")){
                        indCaption="<b>Indikator " + idInd + ": " + indicators.get(idInd-1).getDescriptionGerman()+"<br> <i>Erfordert einen Verbesserungsplan: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="JA</b></i>";
                        }else{
                            indCaption+="NEIN</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("it")){
                        indCaption="<b>Indicatore " + idInd + ": " + indicators.get(idInd-1).getDescriptionItalian()+"<br> <i>Richiede un piano di miglioramento: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="SÌ</b></i>";
                        }else{
                            indCaption+="NO</b></i>";
                        }
                    }else if(Locale.getDefault().getLanguage().equals("pt")){
                        indCaption="<b>Indicador " + idInd + ": " + indicators.get(idInd-1).getDescriptionPortuguese()+"<br> <i>Requer um plano de melhoria: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="SIM</b></i>";
                        }else{
                            indCaption+="NÃO</b></i>";
                        }
                    }else{
                        indCaption="<b>Indicator " + idInd + ": " + indicators.get(idInd-1).getDescriptionEnglish()+"<br> <i>Requires improvement plan: <b>";
                        if(indicatorRegs.get(idInd-1).getRequiresImprovementPlan()==1){
                            indCaption+="YES</b></i>";
                        }else{
                            indCaption+="NO</b></i>";
                        }
                    }
                    indicatorCaption.setText(Html.fromHtml(indCaption,0));
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
        

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Session.getInstance().setCurrEvaluation(null);
            Session.getInstance().setCurrRegs(null,null);
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }

}