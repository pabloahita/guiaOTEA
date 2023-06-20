package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.CountriesApi;
import otea.connection.RegionsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Region {

    @SerializedName("idRegion")
    private int idRegion;

    @SerializedName("idCountry")
    private String idCountry;

    @SerializedName("nameRegion")
    private String nameRegion;

    private Country country;

    public Region(int idRegion, String idCountry, String nameRegion){
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameRegion(nameRegion);
        obtainCountry(idCountry);
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    public String getNameRegion() {
        return nameRegion;
    }

    public void setNameRegion(String nameRegion) {
        this.nameRegion = nameRegion;
    }

    public void obtainCountry(String idCountry){
        ConnectionClient con=new ConnectionClient();
        Retrofit retrofit=con.getRetrofit();
        CountriesApi api=retrofit.create(CountriesApi.class);
        Call<Country> call=api.GetCountry(idCountry);
        Country[] aux=new Country[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Country> response = call.execute();
                        if (response.isSuccessful()) {
                            aux[0]=response.body();
                            return aux[0];
                        } else {
                            throw new IOException("Error: " + response.code() + " " + response.message());
                        }
                    } catch (IOException e) {
                        throw e;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(region-> {
                    System.out.println("Region correctly obtained");
                }, error -> {
                    System.out.println(error.toString());
                });
        setCountry(aux[0]);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
