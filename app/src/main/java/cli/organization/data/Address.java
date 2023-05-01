package cli.organization.data;

/**
 * <p>EN: Class that represents an address that belongs to an organization. Every organization is composed by a road type, a name, a main entry's number, a floor level, an apartment letter, a zip-code, a city an a country.</p>
 * <p>ES: Clase que representa una direccion la cual pertenece a una organizacion. Cada organizacion se compone por un tipo de calle, un nombre, un numero de portal, un numero de piso, una letra de apartamento, un codigo postal, una ciudad y un pais</p>
 * @author Pablo Ahita del Barrio
 * @version 1.0
 */
public class Address {

    private String roadType;
    private String name;
    private int number;
    private int floor=-1;
    private char apt='/';
    private int zipCode;
    private String city;
    private String province;
    private String region;
    private String country;

    /**
     * <p>EN: Address class constructor</p>
     * <p>ES: Constructor de la clase Address</p>
     * @param roadType    <p>EN: Address' road type</p><p>ES: Tipo de calle de la direccion</p>
     * @param name    <p>EN: Road's name</p><p>ES: Nombre de la calle</p>
     * @param number  <p>EN: Main entry's number</p><p>ES: Numero del portal</p>
     * @param floor   <p>EN: Floor's number</p><p>ES: Numero de piso</p>
     * @param apt     <p>EN: Apartment's letter</p><p>ES: Letra del apartamento</p>
     * @param zipCode <p>EN: Address' zip-code</p><p>ES: Codigo postal de la direccion</p>
     * @param city    <p>EN: Address' city</p><p>ES: Ciudad de la direccion</p>
     * @param province <p>EN:Address' province</p><p>ES: Provincia de la direccion</p>
     * @param region <p>EN: Address' region </p><p>ES: Provincia de la direccion</p>
     * @param country <p>EN: Address' country</p><p>ES: Pais de la direccion</p>
     */
    public Address(String roadType, String name, int number, int floor, char apt, int zipCode, String city, String province, String region, String country){
        setRoadType(roadType);
        setName(name);
        setNumber(number);
        setFloor(floor);
        setApartment(apt);
        setZipCode(zipCode);
        setCity(city);
        setProvince(province);
        setRegion(region);
        setContry(country);
    }
    //Getters and setters of every field

    /**
     * Get road type road type.
     *
     * @return the road type
     */
    public String getRoadType(){return roadType;}

    /**
     * <p>EN: Method that changes the address' road type</p>
     * <p>ES: Metodo que cambia el tipo de calle de la direccion</p>
     *
     * @param roadType <p>EN: The new road type</p><p>ES: El nuevo tipo de calle de la direccion</p>
     */
    public void setRoadType(String roadType){this.roadType=roadType;}

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

    /**
     * Get city string.
     *
     * @return the string
     */
    public String getCity(){return city;}

    /**
     * Set city.
     *
     * @param city the city
     */
    public void setCity(String city){this.city=city;}

    /**
     * Get country string.
     *
     * @return the string
     */
    public String getCountry(){return country;}

    /**
     * Set contry.
     *
     * @param country the country
     */
    public void setContry(String country){this.country=country;}

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
