package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import otea.connection.caller.Caller;

public class Region {

    @SerializedName("idRegion")
    public int idRegion;

    @SerializedName("idCountry")
    public String idCountry;

    @SerializedName("nameRegion")
    public String nameRegion;

    public Country country;

    public Caller caller;

    public Region(int idRegion, String idCountry, String nameRegion){
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameRegion(nameRegion);
        setCaller(new Caller());
        setCountry(caller.obtainCountry(idCountry));
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
