package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderSubTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableLayout;
import com.github.zardozz.FixedHeaderTableLayout.FixedHeaderTableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cli.indicators.Ambit;
import session.Session;

public class SeeRealizedIndicatorsEvaluations extends AppCompatActivity {

    List<Ambit> ambits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_realized_indicators_evaluations);
        //setContentView(R.layout.table_base);

        FixedHeaderSubTableLayout columnHeaderTable=new FixedHeaderSubTableLayout(SeeRealizedIndicatorsEvaluations.this);
        FixedHeaderTableRow tableRowData = new FixedHeaderTableRow(SeeRealizedIndicatorsEvaluations.this);

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

        FixedHeaderSubTableLayout mainTable = new FixedHeaderSubTableLayout(SeeRealizedIndicatorsEvaluations.this);


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


        FixedHeaderTableLayout layout=findViewById(R.id.tableIndEval);
        layout.computeScroll();
        layout.addViews(mainTable,columnHeaderTable,rowHeaderTable,cornerTable);

    }

    public Button createButton(String idIndicator){
        Button button = new Button(this);
        int drawable=-1;
        if(!idIndicator.isEmpty()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            button.setText(Html.fromHtml(idIndicator, 0));
            drawable=R.drawable.empty_ind;
        }
        else{
            drawable=R.drawable.empty_ind_no_borders;
        }
        button.setBackground(getDrawable(drawable));
        button.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        return button;
    }

}