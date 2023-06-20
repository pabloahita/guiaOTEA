package cli.organization.data;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import otea.connection.CitiesApi;
import otea.connection.ConnectionClient;
import otea.connection.ProvincesApi;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * <p>EN: Class that represents an address that belongs to an organization. Every organization is composed by a road type, a name, a main entry's number, a floor level, an apartment letter, a zip-code, a city an a country.</p>
 * <p>ES: Clase que representa una direccion la cual pertenece a una organizacion. Cada organizacion se compone por un tipo de calle, un nombre, un numero de portal, un numero de piso, una letra de apartamento, un codigo postal, una ciudad y un pais</p>
 * @author Pablo Ahita del Barrio
 * @version 1.0
 */
public class Address {

    @SerializedName("idAddress")
    private int idAddress;

    @SerializedName("nameStreet")
    private String name;

    @SerializedName("numberStreet")
    private int number;

    @SerializedName("floorApartment")
    private int floor=-1;

    @SerializedName("apartmentLetter")
    private char apt='/';

    @SerializedName("zipCode")
    private int zipCode;

    @SerializedName("idCity")
    private int idCity;

    @SerializedName("idProvince")
    private int idProvince;

    @SerializedName("idRegion")
    private int idRegion;

    @SerializedName("idCountry")
    private String idCountry;

    private City city;
    private Province province;
    private Region region;
    private Country country;

    /**
     * <p>EN: Address class constructor</p>
     * <p>ES: Constructor de la clase Address</p>
     * @param idAddress    <p>EN: Address identifier </p><p>ES: Identificador de la direccion</p>
     * @param name    <p>EN: Road's name</p><p>ES: Nombre de la calle</p>
     * @param number  <p>EN: Main entry's number</p><p>ES: Numero del portal</p>
     * @param floor   <p>EN: Floor's number</p><p>ES: Numero de piso</p>
     * @param apt     <p>EN: Apartment's letter</p><p>ES: Letra del apartamento</p>
     * @param zipCode <p>EN: Address' zip-code</p><p>ES: Codigo postal de la direccion</p>
     * @param idCity    <p>EN: Address' city id</p><p>ES: Id de la ciudad de la direccion</p>
     * @param idProvince <p>EN:Address' province id</p><p>ES: Id de la provincia de la direccion</p>
     * @param idRegion <p>EN: Address' region id</p><p>ES: Id de la region de la direccion</p>
     * @param idCountry <p>EN: Address' country id</p><p>ES: Id del pais de la direccion</p>
     */
    public Address(int idAddress, String name, int number, int floor, char apt, int zipCode, int idCity, int idProvince, int idRegion, String idCountry){
        setIdAddress(idAddress);
        setName(name);
        setNumber(number);
        setFloor(floor);
        setApartment(apt);
        setZipCode(zipCode);
        setIdCity(idCity);
        setIdProvince(idProvince);
        setIdRegion(idRegion);
        setIdCountry(idCountry);
        obtainCity(idCity,idProvince,idRegion,idCountry);

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
     * <p>EN: Method that obtains the road's name</p>
     * <p>ES: Metodo que obtiene el nombre de la calle</p>
     * @return <p>EN: Road's name</p><p>ES: Nombre de la calle</p>
     */
    public String getName(){return name;}

    /**
     * <p>EN: Method that changes the road's name</p>
     * <p>ES: Metodo que cambia el nombre de la calle</p>
     *
     * @param name <p>EN: The new road's name</p><p>ES: El nuevo nombre de la calle</p>
     */
    public void setName(String name){this.name=name;}

    /**
     * <p>EN: Method that obtains the address' main door's number</p>
     * <p>ES: Metodo que obtiene el numero de portal de la direccion</p>
     * @return <p>EN: Main door's number</p><p>ES: Numero de portal</p>
     */
    public int getNumber(){return number;}

    /**
     * <p>EN: Method that changes the main door's number</p>
     * <p>ES: Metodo que cambia el numero de portal de la direccion</p>
     *
     * @param number <p>EN: The new main door's number</p><p>ES: El nuevo numero de portal</p>
     */
    public void setNumber(int number){this.number=number;}

    /**
     * <p>EN: Method that obtains the address' floor number</p>
     * <p>ES: Metodo que obtiene el numero de piso de la direccion</p>
     *
     * @return the int
     */
    public int getFloor(){return floor;}

    /**
     * <p>EN: Method that changes the address' floor's number</p>
     * <p>ES: Metodo que cambia el numero de piso de la direccion</p>
     *
     * @param floor <p>EN: The new floor's number</p><p>ES: El nuevo numero de piso</p>
     */
    public void setFloor(int floor){this.floor=floor;}

    /**
     * <p>EN: Method that obtains the apartment's letter</p>
     * <p>ES: Metodo que obtiene la letra del apartamento</p>
     *
     * @return <p>EN: Apartment's letter</p><p>ES: Letra del apartamento</p>
     */
    public char getApartment(){return apt;}

    /**
     * Set apartment.
     *
     * @param apt the apt
     */
    public void setApartment(char apt){this.apt=apt;}

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

    public void obtainCity(int idCity, int idProvince, int idRegion, String idCountry){
        ConnectionClient con=new ConnectionClient();
        Retrofit retrofit=con.getRetrofit();
        CitiesApi api=retrofit.create(CitiesApi.class);
        Call<City> call=api.GetCity(idCity,idProvince,idRegion,idCountry);
        City[] aux=new City[1];
        Disposable disposable = Observable.fromCallable(() -> {
                    try {
                        Response<City> response = call.execute();
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
        setCity(aux[0]);
        city.obtainProvince(idProvince,idRegion,idCountry);
        setProvince(city.getProvince());
        setRegion(province.getRegion());
        setCountry(region.getCountry());
    }

    public String getCityName(){
       return city.getCityName();
    }
    public String getProvinceName(){
        return province.getNameProvince();
    }
    public String getRegionName(){
        return region.getNameRegion();
    }
    public String getCountryName(String language){
        if(language=="ESP"){return country.getNameSpanish();}
        if(language=="FRA"){return country.getNameFrench();}
        return country.getNameEnglish();//En inglés es por defectp
    }
}
