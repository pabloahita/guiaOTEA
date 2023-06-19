package gui.mainMenu.evaluated.ui.recentactivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fundacionmiradas.indicatorsevaluation.R;
import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentEvaluatedBinding;

import java.util.List;

import gui.mainMenu.admin.ui.recentactivity.RecentViewModel;
import otea.connection.ConnectionClient;

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

        /*final TextView textView = binding.textRecentActivityAdmin;
        recentViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        */
        WebView web=root.findViewById(R.id.webview_recent_evaluated);
        web.setWebViewClient(new WebViewClient());
        //web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl("https://oteaserver-prueba1.azurewebsites.net/Sillas");

        /*String htmlText = "<html><body><h1>Texto de ejemplo</h1><p>Este es un párrafo de ejemplo.</p></body></html>";
        web.loadData(htmlText, "text/html", "UTF-8");
        */
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}