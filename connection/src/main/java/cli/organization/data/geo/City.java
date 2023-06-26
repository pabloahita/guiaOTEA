package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import otea.connection.caller.ProvincesCaller;

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
        setProvince(ProvincesCaller.getInstance().obtainProvince(idProvince,idRegion,idCountry));
        setRegion(province.getRegion());
        setCountry(region.getCountry());
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
