package gui.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mMessage;

    public NotificationsViewModel() {
        mMessage = new MutableLiveData<>();
        mMessage.setValue("¡Bienvenido a la aplicación de ejemplo!");
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }
}