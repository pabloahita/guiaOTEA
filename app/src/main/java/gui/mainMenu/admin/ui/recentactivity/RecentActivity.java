package gui.mainMenu.admin.ui.recentactivity;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentActivityAdminBinding;

import java.util.Locale;

import cli.user.User;


public class RecentActivity extends Fragment {

    private FragmentRecentActivityAdminBinding binding;
    private RecentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecentViewModel recentViewModel =
                new ViewModelProvider(this).get(RecentViewModel.class);

        binding = FragmentRecentActivityAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        User user= (User) getActivity().getIntent().getSerializableExtra("user");

        final TextView textView = binding.textHiAdmin;
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