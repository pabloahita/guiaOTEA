package gui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cli.indicators.Ambit;
import cli.indicators.Indicator;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
import gui.adapters.CenterAdapter;
import gui.adapters.EvalTypesAdapter;
import gui.adapters.EvaluatorTeamsAdapter;
import gui.adapters.OrgsAdapter;
import otea.connection.controller.AmbitsController;
import otea.connection.controller.CentersController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.EvidencesController;
import otea.connection.controller.IndicatorsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.SubAmbitsController;
import otea.connection.controller.SubSubAmbitsController;

public class SelectToDoIndicatorsEvaluations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_to_do_indicators_evaluations);

        CardView final_background=findViewById(R.id.cardView2);

        final_background.setVisibility(View.GONE);

        List<Organization> organizations= OrganizationsController.GetAllEvaluatedOrganizations();
        List<EvaluatorTeam> evaluatorTeams= new LinkedList<>();
        List<String> evaluationTypes=new LinkedList<String>();
        evaluationTypes.add(getString(R.string.complete));
        evaluationTypes.add(getString(R.string.simple));
        EvalTypesAdapter[] evalTypesAdapter={new EvalTypesAdapter(SelectToDoIndicatorsEvaluations.this,evaluationTypes)};
        evalTypesAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);
        if(organizations.size()>0 && evaluatorTeams.size()>0){

            Spinner spinnerEvaluatedOrganization=findViewById(R.id.spinner_select_organization);
            Spinner spinnerCenter=findViewById(R.id.spinner_select_center);
            Spinner spinnerEvaluatorTeam=findViewById(R.id.select_evaluator_team);

            OrgsAdapter[] orgsAdapter= {new OrgsAdapter(SelectToDoIndicatorsEvaluations.this, organizations)};
            orgsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

            spinnerEvaluatedOrganization.setAdapter(orgsAdapter[0]);

            CenterAdapter[] centerAdapter=new CenterAdapter[1];


            EvaluatorTeamsAdapter[] evaluatorTeamsAdapter={null};




            Organization[] evaluatedOrganization = new Organization[1];

            Center[] center=new Center[1];

            EvaluatorTeam[] evaluatorTeam = new EvaluatorTeam[1];

            spinnerEvaluatedOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    evaluatedOrganization[0] = orgsAdapter[0].getItem(position);

                    centerAdapter[0]=new CenterAdapter(SelectToDoIndicatorsEvaluations.this, CentersController.GetAllByOrganization(evaluatedOrganization[0]));

                    centerAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

                    spinnerCenter.setAdapter(centerAdapter[0]);

                    


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,int position, long id) {
                    center[0]=centerAdapter[0].getItem(position);
                    evaluatorTeamsAdapter[0]=new EvaluatorTeamsAdapter(SelectToDoIndicatorsEvaluations.this,EvaluatorTeamsController.GetAllByCenter(center[0].getIdOrganization(),center[0].getOrgType(),center[0].getIdCenter(),center[0].getIllness()));
                    evaluatorTeamsAdapter[0].setDropDownViewResource(R.layout.spinner_item_layout);

                    spinnerEvaluatorTeam.setAdapter(evaluatorTeamsAdapter[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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
                    final_background.setVisibility(View.VISIBLE);
                    spinnerEvaluatorTeam.setEnabled(false);
                    spinnerEvaluatedOrganization.setEnabled(false);
                    button.setEnabled(false);

                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Ambit> ambits= AmbitsController.GetAll();
                            List<Indicator> indicators=new LinkedList<>();
                            Map<Integer,List<SubAmbit>> subAmbits=new HashMap<Integer,List<SubAmbit>>();
                            Map<List<Integer>,List<SubSubAmbit>> subSubAmbits=new HashMap<List<Integer>,List<SubSubAmbit>>();
                            for (Ambit a : ambits){
                                List<Indicator> aux=IndicatorsController.GetAllByIdAmbit(a.idAmbit);
                                indicators.addAll(aux);
                                List<SubAmbit> aux2=SubAmbitsController.GetAllByAmbit(a.idAmbit);
                                subAmbits.put(a.idAmbit,aux2);
                                for(SubAmbit s : aux2){
                                    List<SubSubAmbit> aux3=SubSubAmbitsController.GetAllBySubAmbit(s.idSubAmbit,a.idAmbit);
                                    List<Integer> key=new LinkedList<>();
                                    key.add(s.idSubAmbit);
                                    key.add(a.idAmbit);
                                    subSubAmbits.put(key,aux3);
                                }
                            }
                            int num_indicators=indicators.size();
                            //Indicator i=indicators.get(current_indicator);
                            for(Indicator i: indicators) {
                                if (i.getEvidences() == null) {//En caso de que no se hayan descargado las evidencias del indicador actual
                                    i.setEvidences(EvidencesController.GetAllByIndicator(i.getIdIndicator(), i.getIndicatorType(), i.getIdSubSubAmbit(), i.getIdSubAmbit(), i.getIdAmbit(), i.getIndicatorVersion()));
                                }
                            }
                            int evidences_per_indicator=indicators.get(0).getEvidences().size();

                            Intent intent=new Intent(getApplicationContext(),gui.DoIndicatorsEvaluation.class);
                            intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));
                            intent.putExtra("evaluatorTeam",evaluatorTeam[0]);
                            intent.putExtra("evaluatedOrganization",evaluatedOrganization[0]);
                            for(Ambit a : ambits){
                                intent.putExtra("ambit "+a.getIdAmbit(),a);
                            }
                            for(int i:subAmbits.keySet()) {
                                List<SubAmbit> aux=subAmbits.get(i);
                                int numSubAmbits=-1;
                                if(aux.size()==1 && aux.get(0).getIdSubAmbit()==-1){
                                    numSubAmbits=0;
                                }
                                else{
                                    numSubAmbits=aux.size();
                                }
                                intent.putExtra("numSubAmbits_ambit"+i,numSubAmbits);
                                for (SubAmbit s : aux) {
                                    intent.putExtra("subAmbit "+s.getIdSubAmbit()+",ambit " + s.getIdAmbit(), s);
                                }
                            }
                            for(List<Integer> l:subSubAmbits.keySet()) {
                                List<SubSubAmbit> aux=subSubAmbits.get(l);
                                int numSubSubAmbits=-1;
                                if(aux.size()==1 && aux.get(0).getIdSubSubAmbit()==-1 && aux.get(0).getIdSubAmbit()==-1){
                                    numSubSubAmbits=0;
                                }
                                else{
                                    numSubSubAmbits=aux.size();
                                }
                                intent.putExtra("numSubSubAmbits_subAmbit"+l.get(0)+"_ambit"+l.get(1),numSubSubAmbits);
                                for (SubSubAmbit s : aux) {
                                    intent.putExtra("subSubAmbit "+s.getIdSubSubAmbit()+",subAmbit "+s.getIdSubAmbit()+",ambit " + s.getIdAmbit(), s);
                                }
                            }
                            for(Indicator i:indicators){
                                intent.putExtra("indicator "+i.getIdIndicator(),i);
                            }
                            intent.putExtra("num_indicators",num_indicators);
                            intent.putExtra("num_ambits",ambits.size());
                            intent.putExtra("evidence_per_indicator",evidences_per_indicator);
                            startActivity(intent);
                        }
                    }, 100);


                }
            });
        }else{
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));
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
            Intent intent=new Intent(getApplicationContext(),com.fundacionmiradas.indicatorsevaluation.MainMenu.class);
            intent.putExtra("userEmail",getIntent().getSerializableExtra("userEmail"));
            startActivity(intent);
        }
        return super.onKeyDown(keyCode,event);
    }
}