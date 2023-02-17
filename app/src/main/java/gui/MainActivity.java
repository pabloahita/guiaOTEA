package gui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.fundacionmiradas.indicatorsevaluation.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start_evaluation=findViewById(R.id.button);
        start_evaluation.setOnClickListener(v->{
            Intent intent=new Intent(this, gui.IndicatorsEvaluation.class);
            startActivity(intent);
        });

    }
}