package gui.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    //TODO: ES UN EJEMPLO
    private MutableLiveData<String> mMessage;

    public DashboardViewModel() {
        mMessage = new MutableLiveData<>();
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage.setValue(message);
    }
}