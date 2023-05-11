package gui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.fundacionmiradas.indicatorsevaluation.R;

public class MainActivity extends AppCompatActivity {

    //Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sign_in = findViewById(R.id.sign_in);
        Button sign_up = findViewById(R.id.sign_up);
        sign_in.setOnClickListener(v -> {
            //Intent intent = new Intent(this, gui.ui.login.StartSession.class);
            Intent intent = new Intent(this, gui.SignInPrueba.class);
            startActivity(intent);
        });
        /*sign_up.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.SignUp.class);
            startActivity(intent);
        });*/
    }




}