package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import cli.organization.data.Address;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.ConnectionClient;
import otea.connection.ProvincesApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class City {

    @SerializedName("idCity")
    private int idCity;

    @SerializedName("idProvince")
    private int idProvince;

    @SerializedName("idRegion")
    private int idRegion;

    @SerializedName("idCountry")
    private String idCountry;

    @SerializedName("cityName")
    private String cityName;

    private Province province;
    private Region region;
    private Country country;

    public City(int idCity, int idProvince, int idRegion, String idCountry, String cityName){
        setIdCity(idCity);
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setCityName(cityName);
        obtainProvince(idProvince,idRegion,idCountry);
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void obtainProvince(int idProvince, int idRegion, String idCountry){
        ConnectionClient con=new ConnectionClient();
        Retrofit retrofit=con.getRetrofit();
        ProvincesApi api=retrofit.create(ProvincesApi.class);
        Call<Province> call=api.GetProvince(idProvince,idRegion,idCountry);
        Province[] aux=new Province[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<Province> response = call.execute();
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
                .subscribe(province-> {
                    System.out.println("Province correctly obtained");
                }, error -> {
                    System.out.println(error.toString());
                });
        setProvince(aux[0]);
        province.obtainRegion(idRegion,idCountry);
        setRegion(province.getRegion());
        setCountry(region.getCountry());
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
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
