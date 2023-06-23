package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import otea.connection.caller.Caller;

public class Province {
    @SerializedName("idProvince")
    public int idProvince;

    @SerializedName("idRegion")
    public int idRegion;

    @SerializedName("idCountry")
    public String idCountry;

    @SerializedName("nameProvince")
    public String nameProvince;

    public Region region;
    public Country country;

    public Caller caller;

    public Province(int idProvince, int idRegion, String idCountry, String nameProvince){
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameProvince(nameProvince);
        setCaller(new Caller());
        setRegion(caller.obtainRegion(idRegion,idCountry));
        setCountry(region.getCountry());
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

    public Caller getCaller() {
        return caller;
    }

    public void setCaller(Caller caller) {
        this.caller = caller;
    }
}
