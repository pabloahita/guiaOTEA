package gui.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mMessage;

    public HomeViewModel() {
        mMessage = new MutableLiveData<>();
        mMessage.setValue("¡Bienvenido a la aplicación de ejemplo!");
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }
}