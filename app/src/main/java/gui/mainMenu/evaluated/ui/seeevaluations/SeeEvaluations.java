package gui.mainMenu.evaluated.ui.seeevaluations;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentSeeEvaluationsEvaluatedBinding;

public class SeeEvaluations extends Fragment {

    private FragmentSeeEvaluationsEvaluatedBinding binding;
    private SeeEvaluationsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                new ViewModelProvider(this).get(SeeEvaluationsViewModel.class);

        binding = FragmentSeeEvaluationsEvaluatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final WebView webView= root.findViewById(R.id.see_evaluations_view);

        //webView.loadUrl(ConnectionToServer.base_url);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}