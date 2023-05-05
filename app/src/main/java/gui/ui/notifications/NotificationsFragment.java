package gui.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.fundacionmiradas.indicatorsevaluation.R;

public class NotificationsFragment extends Fragment {
    public NotificationsFragment() {
        // Constructor vacío requerido por Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        // TODO: Obtener referencias a los elementos de la interfaz de usuario

        // TODO: Agregar un Listener a cada botón

        return rootView;
    }
}