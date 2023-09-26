package gui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.fundacionmiradas.indicatorsevaluation.R;

import otea.connection.controller.*;

public class MainActivity extends AppCompatActivity {

    //Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startCallers();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sign_in = findViewById(R.id.sign_in);
        Button sign_up = findViewById(R.id.sign_up);
           sign_in.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.ui.startSession.StartSession.class);
            startActivity(intent);
        });
        sign_up.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.Register.class);
            startActivity(intent);
        });
    }

    public void startCallers(){
        AddressesController.getInstance();
        CentersController.getInstance();
        CitiesController.getInstance();
        CountriesController.getInstance();
        EvaluatorTeamsController.getInstance();
        EvidencesController.getInstance();
        IndicatorsController.getInstance();
        IndicatorsEvaluationRegsController.getInstance();
        IndicatorsEvaluationsController.getInstance();
        OrganizationsController.getInstance();
        ProvincesController.getInstance();
        RegionsController.getInstance();
        UsersController.getInstance();
        TranslatorController.getInstance();
    }




}