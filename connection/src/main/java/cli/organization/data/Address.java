package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;

/**
 * <p>EN: Class that represents an address that belongs to an organization. Every organization is composed by a road type, a name, a main entry's number, a floor level, an apartment letter, a zip-code, a city an a country.</p>
 * <p>ES: Clase que representa una direccion la cual pertenece a una organizacion. Cada organizacion se compone por un tipo de calle, un nombre, un numero de portal, un numero de piso, una letra de apartamento, un codigo postal, una ciudad y un pais</p>
 * @author Pablo Ahita del Barrio
 * @version 1.0
 */
public class Address implements Serializable {

    @SerializedName("idAddress")
    public int idAddress;

    @SerializedName("addressName")
    public String addressName;


    @SerializedName("zipCode")
    public int zipCode;

    @SerializedName("idCity")
    public int idCity;

    @SerializedName("idProvince")
    public int idProvince;

    @SerializedName("idRegion")
    private int idRegion;

    @SerializedName("idCountry")
    public String idCountry;

    @SerializedName("nameCity")
    public String nameCity;

    @SerializedName("nameProvince")
    public String nameProvince;

    @SerializedName("nameRegion")
    public String nameRegion;

    private City city;
    private Province province;
    private Region region;
    private Country country;


    /**
     * <p>EN: Address class constructor</p>
     * <p>ES: Constructor de la clase Address</p>
     * @param idAddress    <p>EN: Address identifier </p><p>ES: Identificador de la direccion</p>
     * @param addressName    <p>EN: Address's name</p><p>ES: Nombre de la direccion</p>
     * @param zipCode <p>EN: Address' zip-code</p><p>ES: Codigo postal de la direccion</p>
     * @param idCity    <p>EN: Address' city id</p><p>ES: Id de la ciudad de la direccion</p>
     * @param idProvince <p>EN:Address' province id</p><p>ES: Id de la provincia de la direccion</p>
     * @param idRegion <p>EN: Address' region id</p><p>ES: Id de la region de la direccion</p>
     * @param idCountry <p>EN: Address' country id</p><p>ES: Id del pais de la direccion</p>
     */

    public Address(int idAddress, String addressName, int zipCode, int idCity, int idProvince, int idRegion, String idCountry, String nameCity, String nameProvince, String nameRegion){
        setIdAddress(idAddress);
        setName(addressName);
        setZipCode(zipCode);
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
     * Get address identifier.
     *
     * @return the address identifier
     */
    public int getIdAddress(){return idAddress;}

    /**
     * <p>EN: Method that changes the address' road type</p>
     * <p>ES: Metodo que cambia el tipo de calle de la direccion</p>
     *
     * @param idAddress <p>EN: The new address identifier /p><p>ES: El nuevo identificador de la direccion</p>
     */
    public void setIdAddress(int idAddress){this.idAddress=idAddress;}

    /**
     * <p>EN: Method that obtains the address's name</p>
     * <p>ES: Metodo que obtiene el nombre de la direccion</p>
     * @return <p>EN: Address's name</p><p>ES: Nombre de la direccion</p>
     */
    public String getName(){return addressName;}

    /**
     * <p>EN: Method that changes the address's name</p>
     * <p>ES: Metodo que cambia el nombre de la direccion</p>
     *
     * @param name <p>EN: The new address's name</p><p>ES: El nuevo nombre de la direccion</p>
     */
    public void setName(String name){this.addressName=name;}

    

    /**
     * Get zip code int.
     *
     * @return the int
     */
    public int getZipCode(){return zipCode;}

    /**
     * Set zip code.
     *
     * @param zipCode the zip code
     */
    public void setZipCode(int zipCode){this.zipCode=zipCode;}

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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



    public String getNameCity(){
       return nameCity;
    }
    public String getNameProvince(){
        return nameProvince;
    }
    public String getNameRegion(){
        return nameRegion;
    }


    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public void setNameProvince(String nameProvince) {
        this.nameProvince = nameProvince;
    }

    public void setNameRegion(String nameRegion) {
        this.nameRegion = nameRegion;
    }


}
