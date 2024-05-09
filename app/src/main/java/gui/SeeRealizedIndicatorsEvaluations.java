package gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.fundacionmiradas.indicatorsevaluation.R;

public class SeeRealizedIndicatorsEvaluations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_realized_indicators_evaluations);
    }
}