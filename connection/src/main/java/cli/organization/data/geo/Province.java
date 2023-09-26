package cli.organization.data.geo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Province implements Serializable {
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


    public Province(int idProvince, int idRegion, String idCountry, String nameProvince){
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameProvince(nameProvince);
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

}
