package gui.mainMenu.admin.ui.recentactivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> textLiveData = new MutableLiveData<>();

    public LiveData<String> getText() {
        return textLiveData;
    }

    public void onButtonClick() {
        // Acciones que se realizan al hacer clic en el botón
    }
}