package cli.organization.data;
import cli.organization.data.RoadType;
public class Address {

    private RoadType road;
    private String name;
    private int number;
    private int floor=-1;
    private char apt='/';
    private int zipCode;
    private String city;
    private String country;

    public Address(RoadType road, String name, int number, int floor, char apt, int zipCode, String city, String country){
        setRoadType(road);
        setName(name);
        setNumber(number);
        setFloor(floor);
        setApartment(apt);
        setZipCode(zipCode);
        setCity(city);
        setContry(country);
    }
    //Getters and setters of every field

    public RoadType getRoadType(){return road;}
    public void setRoadType(RoadType road){this.road=road;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public int getNumber(){return number;}
    public void setNumber(int number){this.number=number;}
    public int getFloor(){return floor;}
    public void setFloor(int floor){this.floor=floor;}
    public char getApartment(){return apt;}
    public void setApartment(char apt){this.apt=apt;}
    public int getZipCode(){return zipCode;}
    public void setZipCode(int zipCode){this.zipCode=zipCode;}
    public String getCity(){return city;}
    public void setCity(String city){this.city=city;}
    public String getCountry(){return country;}
    public void setContry(String country){this.country=country;}

}
