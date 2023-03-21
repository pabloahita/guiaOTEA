package gui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fundacionmiradas.indicatorsevaluation.R;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import cli.Main;
import cli.indicators.Indicator;
import connection.ConnectionToLocalDatabase;

public class MainActivity extends AppCompatActivity {

    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start_evaluation = findViewById(R.id.button);
        ConnectionToLocalDatabase con=new ConnectionToLocalDatabase();
        Indicator.setConnection(con);

        start_evaluation.setOnClickListener(v -> {
            /*Intent intent = new Intent(this, gui.IndicatorsEvaluation.class);
            startActivity(intent);*/
        });
    }




}