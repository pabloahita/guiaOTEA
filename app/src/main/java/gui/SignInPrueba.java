package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.fundacionmiradas.indicatorsevaluation.R;

public class SignInPrueba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_prueba);
        Button admin = findViewById(R.id.admin_userButton);
        Button evaluated = findViewById(R.id.EvaluatedOrgUserButton);
        Button evaluator = findViewById(R.id.EvaluatorOrgUserButton);
        admin.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.mainMenu.admin.MainMenu.class);
            startActivity(intent);
        });
       /* evaluated.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.mainMenu.evaluatedUser.MainMenu.class);
            startActivity(intent);
        });*/
        /*evaluator.setOnClickListener(v -> {
            Intent intent = new Intent(this, gui.mainMenu.evaluator.MainMenu.class);
            startActivity(intent);
        });*/
    }
}