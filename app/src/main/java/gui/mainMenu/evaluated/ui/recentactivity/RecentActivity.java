package gui.mainMenu.evaluated.ui.recentactivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionmiradas.indicatorsevaluation.R;

public class RecentActivity extends Fragment {

    private RecentViewModel mViewModel;

    public static RecentActivity newInstance() {
        return new RecentActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent_evaluated, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RecentViewModel.class);
        // TODO: Use the ViewModel
    }

}