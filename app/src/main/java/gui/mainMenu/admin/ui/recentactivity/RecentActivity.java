package gui.mainMenu.admin.ui.recentactivity;


import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionmiradas.indicatorsevaluation.databinding.FragmentRecentActivityAdminBinding;

import java.io.IOException;
import java.util.List;

import cli.organization.data.Address;
import io.reactivex.rxjava3.disposables.Disposable;
import otea.connection.AddressesApi;
import otea.connection.ConnectionClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


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

        ConnectionClient cli=new ConnectionClient();
        Retrofit retrofit=cli.getRetrofit();
        AddressesApi api=retrofit.create(AddressesApi.class);
        Call<Address> call=api.Get(1);
        Address aux;
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Address> response = call.execute();
                        if (response.isSuccessful()) {
                            return response.body();
                        } else {
                            throw new IOException("Error: " + response.code() + " " + response.message());
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address -> {
                    Log.d("CORRECT_RESPONSE", address.getName()+", "+address.getNumber()+"-"+address.getFloor()+"º "+address.getApartment()+"\n"+address.getZipCode()+" "+address.getCity()+" ("+address.getProvince()+", "+address.getRegion()+", "+address.getCountry()+")");
                    // Actualizar la interfaz de usuario con los datos obtenidos
                    // Puedes asignar los datos a tus vistas aquí
                }, error -> {
                    Log.d("EEEXCEPTION", error.toString());
                });

        final TextView textView = binding.textRecentActivityAdmin;
        recentViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}