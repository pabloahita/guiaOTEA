package gui.mainMenu.evaluator.ui.recentactivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentActivityAdminBinding;
import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentEvaluatedBinding;
import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentEvaluatorBinding;

import java.util.Locale;

import cli.user.User;

public class RecentActivity extends Fragment {

    private FragmentRecentEvaluatorBinding binding;
    private RecentViewModel mViewModel;

    public static RecentActivity newInstance() {
        return new RecentActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding= FragmentRecentEvaluatorBinding.inflate(inflater, container, false);

        View root=binding.getRoot();

        User user= (User) getActivity().getIntent().getSerializableExtra("user");

        TextView textView=binding.textHiEvaluator;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecentViewModel.class);
        // TODO: Use the ViewModel
    }

}