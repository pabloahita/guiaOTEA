package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import cli.indicators.Ambit;
import cli.indicators.Indicator;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.organization.data.geo.City;
import cli.organization.data.geo.Country;
import cli.organization.data.geo.Province;
import cli.organization.data.geo.Region;
import cli.user.User;
import otea.connection.controller.AddressesController;
import otea.connection.controller.AmbitsController;
import otea.connection.controller.CentersController;
import otea.connection.controller.CitiesController;
import otea.connection.controller.CountriesController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.EvidencesController;
import otea.connection.controller.IndicatorsController;
import otea.connection.controller.IndicatorsEvaluationRegsController;
import otea.connection.controller.IndicatorsEvaluationsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.ProvincesController;
import otea.connection.controller.RegionsController;
import otea.connection.controller.RequestsController;
import otea.connection.controller.SubAmbitsController;
import otea.connection.controller.SubSubAmbitsController;
import otea.connection.controller.TranslatorController;
import otea.connection.controller.UsersController;

public class Session {

    User user;

    String token;

    Organization organization;

    private static Session instance;

    private List<Indicator> indicators;

    private List<Ambit> ambits;

    private Map<Integer,List<SubAmbit>> subAmbits;

    private Map<List<Integer>,List<SubSubAmbit>> subSubAmbits;

    private static List<Country> countries;

    private static List<Region> regions;

    private static List<Province> provinces;

    private static List<City> cities;


    private static boolean isGeoDataCharged=false;

    private Session(JsonObject data) {
        setToken(data.getAsJsonPrimitive("token").getAsString());
        JsonObject jsonUser = data.getAsJsonObject("user");
        JsonObject jsonOrg = data.getAsJsonObject("organization");
        setUser(new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), jsonUser.getAsJsonPrimitive("passwordUser").getAsString(), jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString()));
        setOrganization(new Organization(jsonOrg.getAsJsonPrimitive("idOrganization").getAsInt(), jsonOrg.getAsJsonPrimitive("orgType").getAsString(), jsonOrg.getAsJsonPrimitive("illness").getAsString(), jsonOrg.getAsJsonPrimitive("nameOrg").getAsString(), jsonOrg.getAsJsonPrimitive("idAddress").getAsInt(), jsonOrg.getAsJsonPrimitive("email").getAsString(), jsonOrg.getAsJsonPrimitive("telephone").getAsString(), jsonOrg.getAsJsonPrimitive("informationSpanish").getAsString(), jsonOrg.getAsJsonPrimitive("informationEnglish").getAsString(), jsonOrg.getAsJsonPrimitive("informationFrench").getAsString(), jsonOrg.getAsJsonPrimitive("informationBasque").getAsString(), jsonOrg.getAsJsonPrimitive("informationCatalan").getAsString(), jsonOrg.getAsJsonPrimitive("informationDutch").getAsString(), jsonOrg.getAsJsonPrimitive("informationGalician").getAsString(), jsonOrg.getAsJsonPrimitive("informationGerman").getAsString(), jsonOrg.getAsJsonPrimitive("informationItalian").getAsString(), jsonOrg.getAsJsonPrimitive("informationPortuguese").getAsString(), jsonOrg.getAsJsonPrimitive("profilePhoto").getAsString()));
    }

    public static synchronized Session getInstance(){
        return instance;
    }

    public static synchronized Session createSession(JsonObject data){
        if(instance==null){
            instance=new Session(data);
            instance.refreshCallers(true);
        }
        return instance;
    }

    public static void refreshCallers(boolean hasToken){
        if(!hasToken) {
            AmbitsController.getInstance();
            SubAmbitsController.getInstance();
            SubSubAmbitsController.getInstance();
            AddressesController.getInstance();
            CentersController.getInstance();
            CitiesController.getInstance();
            CountriesController.getInstance();
            EvaluatorTeamsController.getInstance();
            EvidencesController.getInstance();
            IndicatorsController.getInstance();
            IndicatorsEvaluationRegsController.getInstance();
            IndicatorsEvaluationsController.getInstance();
            OrganizationsController.getInstance();
            ProvincesController.getInstance();
            RegionsController.getInstance();
            RequestsController.getInstance();
            UsersController.getInstance();
            TranslatorController.getInstance();
            FileManager.getInstance();
        }else{
            AmbitsController.refreshApi();
            SubAmbitsController.refreshApi();
            SubSubAmbitsController.refreshApi();
            AddressesController.refreshApi();
            CentersController.refreshApi();
            CitiesController.refreshApi();
            CountriesController.refreshApi();
            EvaluatorTeamsController.refreshApi();
            EvidencesController.refreshApi();
            IndicatorsController.refreshApi();
            IndicatorsEvaluationRegsController.refreshApi();
            IndicatorsEvaluationsController.refreshApi();
            OrganizationsController.refreshApi();
            ProvincesController.refreshApi();
            RegionsController.refreshApi();
            RequestsController.refreshApi();
            UsersController.refreshApi();
            TranslatorController.refreshApi();
            FileManager.refreshApi();
        }
    }

    public static void logout(){
        instance=null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }

    public List<Ambit> getAmbits() {
        return ambits;
    }

    public void setAmbits(List<Ambit> ambits) {
        this.ambits = ambits;
    }

    public Map<Integer, List<SubAmbit>> getSubAmbits() {
        return subAmbits;
    }

    public void setSubAmbits(Map<Integer, List<SubAmbit>> subAmbits) {
        this.subAmbits = subAmbits;
    }

    public Map<List<Integer>, List<SubSubAmbit>> getSubSubAmbits() {
        return subSubAmbits;
    }

    public void setSubSubAmbits(Map<List<Integer>, List<SubSubAmbit>> subSubAmbits) {
        this.subSubAmbits = subSubAmbits;
    }

    public List<Country> getCountries() {
        if(countries==null){
            countries=CountriesController.GetAll(Locale.getDefault().getLanguage());
            countries.add(0,new Country("-1","País","Country","Pays","Herrialdea","País","Land","País","Land","Paese","País","-","-"));//Country object for non selected
        }
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Region> getRegionsByCountry(String idCountry){
        Predicate<Region> regionsOfThatCountryExist=new Predicate<Region>() {
            @Override
            public boolean test(Region region) {
                return region.getIdCountry().equals(idCountry);
            }
        };
        Predicate<Region> regionNoneWasCreated=new Predicate<Region>() {
            @Override
            public boolean test(Region region) {
                return region.getIdRegion()==-2;
            }
        };
        if(regions==null){
            regions=RegionsController.GetRegionsByCountry(idCountry);
        }else if(!regions.stream().anyMatch(regionsOfThatCountryExist)){
            regions.addAll(RegionsController.GetRegionsByCountry(idCountry));
        }
        Region region=null;

        if(regions.stream().anyMatch(regionNoneWasCreated)){
            region=regions.get(0);
            region.setIdCountry(idCountry);
            regions.remove(0);
        }
        else{
            region=new Region(-2,idCountry,"Región","Region","Région","Eskualdea","Regió","Regio","Rexión","Region","Regione","Região");
        }
        regions.add(0,region);
        return regions.stream().filter(regionsOfThatCountryExist).collect(Collectors.toList());
    }

    public List<Province> getProvincesByRegion(int idRegion, String idCountry){
        Predicate<Province> provincesOfThatRegionExist=new Predicate<Province>() {
            @Override
            public boolean test(Province province) {
                return province.getIdRegion()==idRegion && province.getIdCountry().equals(idCountry);
            }
        };

        Predicate<Province> provinceNoneWasCreated=new Predicate<Province>() {
            @Override
            public boolean test(Province province) {
                return province.idProvince==-2;
            }
        };
        if(provinces==null){
            provinces=ProvincesController.GetProvincesByRegion(idRegion,idCountry);
        }else if(!provinces.stream().anyMatch(provincesOfThatRegionExist)){
            provinces.addAll(ProvincesController.GetProvincesByRegion(idRegion,idCountry));
        }
        Province province;

        if(provinces.stream().anyMatch(provinceNoneWasCreated)){
            province=provinces.get(0);
            province.setIdRegion(idRegion);
            province.setIdCountry(idCountry);
            provinces.remove(0);
        }
        else{
            province=new Province(-2,idRegion,idCountry,"Provincia","Province","Province","Probintzia","Província","Provincie","Provincia","Provinz","Provincia","Província");
        }
        provinces.add(province);
        return provinces.stream().filter(provincesOfThatRegionExist).collect(Collectors.toList());
    }


    public List<City> getCitiesByProvince(int idProvince, int idRegion, String idCountry){
        Predicate<City> citiesOfThatProvinceExist=new Predicate<City>() {
            @Override
            public boolean test(City city) {
                return city.getIdProvince()==idProvince && city.getIdRegion()==idRegion && city.getIdCountry().equals(idCountry);
            }
        };

        Predicate<City> cityNoneWasCreated=new Predicate<City>() {
            @Override
            public boolean test(City city) {
                return city.getIdCity()==-2;
            }
        };
        if(cities==null){
            cities=CitiesController.GetCitiesByProvince(idProvince,idRegion,idCountry);
        }else if(!cities.stream().anyMatch(citiesOfThatProvinceExist)){
            cities.addAll(CitiesController.GetCitiesByProvince(idProvince,idRegion,idCountry));
        }

        City city;

        if(cities.stream().anyMatch(cityNoneWasCreated)){
            city=cities.get(0);
            city.setIdProvince(idProvince);
            city.setIdRegion(idRegion);
            city.setIdCountry(idCountry);
            cities.remove(0);
        }
        else{
            city=new City(-2,idProvince,idRegion,idCountry,"Ciudad","City","Ville","Hiri","Ciutat","Stad","Cidade","Stad","Città","Cidade");
        }
        cities.add(city);
        return cities.stream().filter(citiesOfThatProvinceExist).collect(Collectors.toList());
    }


    public void obtainIndicatorsFromDataBase(){
        if(indicators==null) {
            ambits = AmbitsController.GetAll();
            indicators = new LinkedList<>();
            subAmbits = new HashMap<Integer, List<SubAmbit>>();
            subSubAmbits = new HashMap<List<Integer>, List<SubSubAmbit>>();
            for (Ambit a : ambits) {
                List<Indicator> aux = IndicatorsController.GetAllByIdAmbit(a.idAmbit);
                indicators.addAll(aux);
                List<SubAmbit> aux2 = SubAmbitsController.GetAllByAmbit(a.idAmbit);
                subAmbits.put(a.idAmbit, aux2);
                for (SubAmbit s : aux2) {
                    List<SubSubAmbit> aux3 = SubSubAmbitsController.GetAllBySubAmbit(s.idSubAmbit, a.idAmbit);
                    List<Integer> key = new LinkedList<>();
                    key.add(s.idSubAmbit);
                    key.add(a.idAmbit);
                    subSubAmbits.put(key, aux3);
                }
            }
            for (Indicator i : indicators) {
                if (i.getEvidences() == null) {//En caso de que no se hayan descargado las evidencias del indicador actual
                    i.setEvidences(EvidencesController.GetAllByIndicator(i.getIdIndicator(), i.getIndicatorType(), i.getIdSubSubAmbit(), i.getIdSubAmbit(), i.getIdAmbit(), i.getIndicatorVersion()));
                }
            }
        }
    }

}
