package session;



import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import cli.organization.Organization;
import cli.organization.data.geo.Country;
import cli.user.User;
import otea.connection.controller.AddressesController;
import otea.connection.controller.AmbitsController;
import otea.connection.controller.CentersController;
import otea.connection.controller.CitiesController;
import otea.connection.controller.CountriesController;
import otea.connection.controller.EvaluatorTeamsController;
import otea.connection.controller.EvidencesController;
import otea.connection.controller.IndicatorsController;
import otea.connection.controller.IndicatorsEvaluationEvidenceRegsController;
import otea.connection.controller.IndicatorsEvaluationIndicatorRegsController;
import otea.connection.controller.IndicatorsEvaluationsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.ProvincesController;
import otea.connection.controller.RegionsController;
import otea.connection.controller.SubAmbitsController;
import otea.connection.controller.SubSubAmbitsController;
import otea.connection.controller.TranslatorController;
import otea.connection.controller.UsersController;

public class Session {

    User user;

    String token;

    Organization organization;

    private static Session instance;



    private static List<Country> countries;
    private static List<Organization> evaluatedOrganizations;

    private static List<Organization> evaluatorOrganizations;

    private static Request currRequest;



    private Session(JsonObject data) {
        setToken(data.getAsJsonPrimitive("token").getAsString());
        JsonObject jsonUser = data.getAsJsonObject("user");
        JsonObject jsonOrg = data.getAsJsonObject("organization");
        setUser(new User(jsonUser.getAsJsonPrimitive("emailUser").getAsString(), jsonUser.getAsJsonPrimitive("userType").getAsString(), jsonUser.getAsJsonPrimitive("first_name").getAsString(), jsonUser.getAsJsonPrimitive("last_name").getAsString(), "", jsonUser.getAsJsonPrimitive("telephone").getAsString(), jsonUser.getAsJsonPrimitive("idOrganization").getAsInt(), jsonUser.getAsJsonPrimitive("orgType").getAsString(), jsonUser.getAsJsonPrimitive("illness").getAsString(), jsonUser.getAsJsonPrimitive("profilePhoto").getAsString()));
        setOrganization(new Organization(jsonOrg.getAsJsonPrimitive("idOrganization").getAsInt(), jsonOrg.getAsJsonPrimitive("orgType").getAsString(), jsonOrg.getAsJsonPrimitive("illness").getAsString(), jsonOrg.getAsJsonPrimitive("nameOrg").getAsString(), jsonOrg.getAsJsonPrimitive("idAddress").getAsInt(), jsonOrg.getAsJsonPrimitive("email").getAsString(), jsonOrg.getAsJsonPrimitive("telephone").getAsString(), jsonOrg.getAsJsonPrimitive("informationSpanish").getAsString(), jsonOrg.getAsJsonPrimitive("informationEnglish").getAsString(), jsonOrg.getAsJsonPrimitive("informationFrench").getAsString(), jsonOrg.getAsJsonPrimitive("informationBasque").getAsString(), jsonOrg.getAsJsonPrimitive("informationCatalan").getAsString(), jsonOrg.getAsJsonPrimitive("informationDutch").getAsString(), jsonOrg.getAsJsonPrimitive("informationGalician").getAsString(), jsonOrg.getAsJsonPrimitive("informationGerman").getAsString(), jsonOrg.getAsJsonPrimitive("informationItalian").getAsString(), jsonOrg.getAsJsonPrimitive("informationPortuguese").getAsString(), jsonOrg.getAsJsonPrimitive("profilePhoto").getAsString()));
    }

    public static synchronized Session getInstance(){
        return instance;
    }

    public static synchronized Session createSession(JsonObject data){
        if(instance==null){
            instance=new Session(data);
        }
        return instance;
    }




    public static void refreshCallers(){
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
        IndicatorsEvaluationIndicatorRegsController.getInstance();
        IndicatorsEvaluationEvidenceRegsController.getInstance();
        IndicatorsEvaluationsController.getInstance();
        OrganizationsController.getInstance();
        ProvincesController.getInstance();
        RegionsController.getInstance();
        RequestsController.getInstance();
        UsersController.getInstance();
        TranslatorController.getInstance();
        FileManager.getInstance();
    }

    public static synchronized void logout(){
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


    public static List<Organization> getEvaluatorOrganizations() {
        return evaluatorOrganizations;
    }


    public static List<Organization> getEvaluatedOrganizations() {
        return evaluatedOrganizations;
    }


    public List<Country> getCountries() {
        if(countries==null){
            countries=CountriesController.GetAll(Locale.getDefault().getLanguage());
        }
        return countries;
    }










    public static void obtainAllOrgs(){
        List<Organization> orgs=OrganizationsController.GetAll();
        evaluatedOrganizations=orgs.stream().filter(new Predicate<Organization>() {
            @Override
            public boolean test(Organization organization) {
                return organization.getOrganizationType().equals("EVALUATED");
            }
        }).collect(Collectors.toList());
        evaluatorOrganizations=orgs.stream().filter(new Predicate<Organization>() {
            @Override
            public boolean test(Organization organization) {
                return organization.getOrganizationType().equals("EVALUATOR");
            }
        }).collect(Collectors.toList());
    }




    public static Request getCurrRequest() {
        return currRequest;
    }

    public static void setCurrRequest(Request currRequest) {
        Session.currRequest = currRequest;
    }







}
