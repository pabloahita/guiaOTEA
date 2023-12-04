package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;

/**
 * Model class for address
 * @author Pablo Ahita del Barrio
 * @version 1
 */
public class Address implements Serializable {

    /**Address identifier*/
    @SerializedName("idAddress")
    public int idAddress;

    /**Address name*/
    @SerializedName("addressName")
    public String addressName;

    /**City identifier (if is precharged)*/
    @SerializedName("idCity")
    public int idCity;

    /**Province identifier (if is precharged)*/
    @SerializedName("idProvince")
    public int idProvince;

    /**Region identifier (if is precharged)*/
    @SerializedName("idRegion")
    private int idRegion;

    /**Country identifier*/
    @SerializedName("idCountry")
    public String idCountry;

    /**City name*/
    @SerializedName("nameCity")
    public String nameCity;

    /**Province name*/
    @SerializedName("nameProvince")
    public String nameProvince;

    /**Region name*/
    @SerializedName("nameRegion")
    public String nameRegion;



    /**
     * Class constructor
     * @param idAddress - Address identifier
     * @param addressName - Address name
     * @param idCity - City identifier (if is precharged)
     * @param idProvince - Province identifier (if is precharged)
     * @param idRegion - Region identifier (if is precharged)
     * @param idCountry - Country identifier
     * @param nameCity - City name
     * @param nameProvince - Province name
     * @param nameRegion - Region name
     */

    public Address(int idAddress, String addressName, int idCity, int idProvince, int idRegion, String idCountry, String nameCity, String nameProvince, String nameRegion){
        setIdAddress(idAddress);
        setName(addressName);
        setIdCity(idCity);
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        setNameCity(nameCity);
        setNameProvince(nameProvince);
        setNameRegion(nameRegion);

    }

    //Getters and setters of every field

    /**
     * Method that obtains the address identifier
     *
     * @return Address identifier
     */
    public int getIdAddress(){return idAddress;}

    /**
     * Method that sets the new address identifier
     * @param idAddress
     */
    public void setIdAddress(int idAddress){this.idAddress=idAddress;}

    /**
     * Method that obtains the address name
     * @return Address name
     */
    public String getName(){return addressName;}

    /**
     * Method that sets the new address name
     * @param name - Address name
     */
    public void setName(String name){this.addressName=name;}


    /**
     * Method that obtains the city identifier, only if the city is precharged
     *
     * @return City identifier (-1 if is not precharged)
     * */
    public int getIdCity() {
        return idCity;
    }

    /**
     * Method that sets the new city identifier
     *
     * @param idCity - City identifier
     * */
    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    /**
     * Method that obtains the province identifier, only if the province is precharged
     *
     * @return Province identifier (-1 if is not precharged)
     * */
    public int getIdProvince() {
        return idProvince;
    }

    /**
     * Method that sets the new province identifier
     *
     * @param idProvince - Province identifier
     * */
    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    /**
     * Method that obtains the region identifier, only if the region is precharged
     *
     * @return Region identifier (-1 if is not precharged)
     * */
    public int getIdRegion() {
        return idRegion;
    }

    /**
     * Method that sets the new region identifier
     *
     * @param idRegion - Region identifier
     * */
    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }
    /**
     * Method that obtains the country identifier
     *
     * @return Country identifier
     * */
    public String getIdCountry() {
        return idCountry;
    }

    /**
     * Method that sets the new country identifier
     *
     * @param idCountry - Country identifier
     * */
    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    /**
     * Method that obtains the city name
     *
     * @return City name
     * */
    public String getNameCity(){
       return nameCity;
    }

    /**
     * Method that obtains the province name
     *
     * @return Province name
     * */
    public String getNameProvince(){
        return nameProvince;
    }

    /**
     * Method that obtains the region name
     *
     * @return Region name
     * */
    public String getNameRegion(){
        return nameRegion;
    }

    /**
     * Method that sets the new city name
     *
     * @param nameCity - City name
     * */
    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    /**
     * Method that sets the new province name
     *
     * @param nameProvince - Province name
     * */
    public void setNameProvince(String nameProvince) {
        this.nameProvince = nameProvince;
    }

    /**
     * Method that sets the new region name
     *
     * @param nameRegion - Region name
     * */
    public void setNameRegion(String nameRegion) {
        this.nameRegion = nameRegion;
    }


}
