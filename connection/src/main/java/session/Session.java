package session;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

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

    private List<Country> countries;

    private List<Region> regions;

    private List<Province> provinces;

    private List<City> cities;
    private Session(JsonObject data) {
        setToken(data.getAsJsonPrimitive("token").getAsString());
        JsonObject jsonUser=data.getAsJsonObject("user");
        setUser(new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), jsonUser.getAsJsonPrimitive("passwordUser").getAsString(), jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString()));
    }

    public static synchronized Session getInstance(){
        return instance;
    }

    public static synchronized Session createSession(JsonObject data){
        if(instance==null){
            instance=new Session(data);
            instance.refreshCallers(true);
            int idOrg=instance.getUser().getIdOrganization();
            String orgType=instance.getUser().getOrganizationType();
            String illness=instance.getUser().getIllness();
            instance.setOrganization(OrganizationsController.getInstance().Get(idOrg, orgType, illness));
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
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void obtainGeoDataFromDataBase(){
        try{
            if(countries==null){
                cities=new ArrayList<>();
                CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                    countries=CountriesController.getInstance().GetAll(Locale.getDefault().getLanguage());
                });

                CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                    regions=RegionsController.getInstance().GetAll();
                });

                CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
                    provinces=ProvincesController.getInstance().GetAll();
                });

                CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
                    cities.addAll(CitiesController.getInstance().GetAll(1,116892)); //There are 467568 cities. Pagination
                });

                CompletableFuture<Void> future5 = CompletableFuture.runAsync(() -> {
                    cities.addAll(CitiesController.getInstance().GetAll(2,116892)); //There are 467568 cities. Pagination
                });

                CompletableFuture<Void> future6 = CompletableFuture.runAsync(() -> {
                    cities.addAll(CitiesController.getInstance().GetAll(3,116892)); //There are 467568 cities. Pagination
                });

                CompletableFuture<Void> future7 = CompletableFuture.runAsync(() -> {
                    cities.addAll(CitiesController.getInstance().GetAll(4,116892)); //There are 467568 cities. Pagination
                });

                CompletableFuture.allOf(future1, future2, future3, future4, future5, future6, future7).get();

            }
        }catch(InterruptedException | ExecutionException e){
            throw new RuntimeException(e);
        }
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
