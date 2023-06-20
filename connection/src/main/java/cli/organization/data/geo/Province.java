package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.RegionsApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Province {
    @SerializedName("idProvince")
    private int idProvince;

    @SerializedName("idRegion")
    private int idRegion;

    @SerializedName("idCountry")
    private String idCountry;

    @SerializedName("nameProvince")
    private String nameProvince;

    private Region region;
    private Country country;

    public Province(int idProvince, int idRegion, String idCountry, String nameProvince){
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameProvince(nameProvince);
        obtainRegion(idRegion,idCountry);
    }

    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
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


    public void obtainRegion(int idRegion, String idCountry){
        ConnectionClient con=new ConnectionClient();
        Retrofit retrofit=con.getRetrofit();
        RegionsApi api=retrofit.create(RegionsApi.class);
        Call<Region> call=api.GetRegion(idRegion,idCountry);
        Region[] aux=new Region[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Region> response = call.execute();
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
        setRegion(aux[0]);
        region.obtainCountry(idCountry);
        setCountry(region.getCountry());
    }

    public String getNameProvince() {
        return nameProvince;
    }

    public void setNameProvince(String nameRegion) {
        this.nameProvince = nameRegion;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
