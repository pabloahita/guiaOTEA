package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.io.Serializable;
import java.util.List;

import cli.indicators.Indicator;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CountryAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import gui.adapters.OrgsAdapter;
import gui.adapters.ProvinceAdapter;
import otea.connection.caller.EvaluatorTeamsCaller;
import otea.connection.caller.EvidencesCaller;
import otea.connection.caller.IndicatorsCaller;
import otea.connection.caller.OrganizationsCaller;

public class SelectToDoIndicatorsEvaluations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_do_indicators_evaluations);

        ProgressBar progressBar=findViewById(R.id.progressBar);
        TextView textView=findViewById(R.id.loading_indicators);

        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        List<Organization> organizations= OrganizationsCaller.GetAllEvaluatedOrganizations();
        List<EvaluatorTeam> evaluatorTeams= EvaluatorTeamsCaller.GetAllByOrganization(1,"EVALUATOR","AUTISM");
        if(organizations.size()>0 && evaluatorTeams.size()>0){

            Spinner spinnerEvaluatedOrganization=findViewById(R.id.spinner_select_organization);
            Spinner spinnerEvaluatorTeam=findViewById(R.id.select_evaluator_team);

            OrgsAdapter[] orgsAdapter= {new OrgsAdapter(SelectToDoIndicatorsEvaluations.this, organizations)};
            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

            spinnerEvaluatedOrganization.setAdapter(orgsAdapter[0]);


            EvaluatorTeamsAdapter[] evaluatorTeamsAdapter={new EvaluatorTeamsAdapter(SelectToDoIndicatorsEvaluations.this,evaluatorTeams)};
            evaluatorTeamsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

            spinnerEvaluatorTeam.setAdapter(evaluatorTeamsAdapter[0]);


            Organization[] evaluatedOrganization = new Organization[1];

            EvaluatorTeam[] evaluatorTeam = new EvaluatorTeam[1];

            spinnerEvaluatedOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    evaluatedOrganization[0] = orgsAdapter[0].getItem(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerEvaluatorTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    evaluatorTeam[0] = evaluatorTeamsAdapter[0].getItem(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button button=findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    spinnerEvaluatorTeam.setEnabled(false);
                    spinnerEvaluatedOrganization.setEnabled(false);
                    button.setEnabled(false);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Indicator> indicators= IndicatorsCaller.obtainIndicators("AUTISM");
                            int num_indicators=indicators.size();
                            //Indicator i=indicators.get(current_indicator);
                            for(Indicator i: indicators) {
                                if (i.getEvidences() == null) {//En caso de que no se hayan descargado las evidencias del indicador actual
                                    i.setEvidences(EvidencesCaller.obtainEvidences(i.getIdIndicator(), i.getIndicatorType(), i.getIndicatorVersion()));
                                }
                            }
                            int evidences_per_indicator=indicators.get(0).getEvidences().size();

                            Intent intent=new Intent(getApplicationContext(),gui.DoIndicatorsEvaluation.class);
                            intent.putExtra("user",getIntent().getSerializableExtra("user"));
                            intent.putExtra("evaluatorTeam",evaluatorTeam[0]);
                            intent.putExtra("evaluatedOrganization",evaluatedOrganization[0]);
                            for(Indicator i:indicators){
                                intent.putExtra("indicator "+i.getIdIndicator(),i);
                            }
                            intent.putExtra("num_indicators",num_indicators);
                            intent.putExtra("evidence_per_indicator",evidences_per_indicator);
                            startActivity(intent);
                        }
                    }, 100);


                }
            });
        }else{
            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
            intent.putExtra("user",getIntent().getSerializableExtra("user"));
            startActivity(intent);
            if(organizations.size()==0) {
                Toast.makeText(getApplicationContext(),getString(R.string.non_existing_evaluated_organization),Toast.LENGTH_LONG).show();
            }
            if(evaluatorTeams.size()==0){
                Toast.makeText(getApplicationContext(),getString(R.string.non_existing_evaluator_team),Toast.LENGTH_LONG).show();
            }
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(getApplicationContext(),gui.mainMenu.evaluator.MainMenu.class);
            intent.putExtra("user",getIntent().getSerializableExtra("user"));
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}