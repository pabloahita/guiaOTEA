package gui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.util.List;

import cli.organization.Organization;
import cli.organization.data.Address;
import cli.user.User;
import misc.PasswordCodifier;
import otea.connection.caller.*;

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
        AddressesCaller.getInstance();
        CentersCaller.getInstance();
        CitiesCaller.getInstance();
        CountriesCaller.getInstance();
        EvaluatorTeamMembersCaller.getInstance();
        EvaluatorTeamsCaller.getInstance();
        EvidencesCaller.getInstance();
        IndicatorsCaller.getInstance();
        IndicatorsEvaluationRegsCaller.getInstance();
        IndicatorsEvaluationsCaller.getInstance();
        OrganizationsCaller.getInstance();
        ProvincesCaller.getInstance();
        RegionsCaller.getInstance();
        UsersCaller.getInstance();
    }




}