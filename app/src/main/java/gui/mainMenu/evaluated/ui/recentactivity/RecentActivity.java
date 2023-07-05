package gui.mainMenu.evaluated.ui.recentactivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentEvaluatedBinding;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.data.EvaluatorTeam;
import cli.user.User;
import gui.mainMenu.admin.ui.recentactivity.RecentViewModel;
import otea.connection.ConnectionClient;
import otea.connection.caller.EvaluatorTeamsCaller;
import otea.connection.caller.IndicatorsEvaluationsCaller;

import java.util.Locale;
public class RecentActivity extends Fragment {

    private FragmentRecentEvaluatedBinding binding;
    private RecentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gui.mainMenu.admin.ui.recentactivity.RecentViewModel recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentEvaluatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        User user= (User) getActivity().getIntent().getSerializableExtra("user");

        TextView textView=binding.textHiEvaluated;
        String text="";
        if(Locale.getDefault().getLanguage().equals("es")){
            text="¡Hola "+user.getFirst_name()+"!";
        }else if(Locale.getDefault().getLanguage().equals("fr")) {
            text="Salut "+user.getFirst_name()+"!";
        }else{ //Por defecto en inglés
            text="Hi "+user.getFirst_name()+"!";
        }
        textView.setText(text);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}