package session;

import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import cli.indicators.Ambit;
import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.indicators.IndicatorsEvaluation;
import cli.indicators.IndicatorsEvaluationIndicatorReg;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.organization.data.Center;
import cli.organization.data.EvaluatorTeam;
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
import otea.connection.controller.IndicatorsEvaluationEvidenceRegsController;
import otea.connection.controller.IndicatorsEvaluationIndicatorRegsController;
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

    private List<Evidence> evidences;

    private List<SubAmbit> subAmbits;

    private List<SubSubAmbit> subSubAmbits;

    private static List<Country> countries;

    private static List<Region> regions;

    private static List<Province> provinces;

    private static List<City> cities;

    private static List<Organization> evaluatedOrganizations;

    private static List<Organization> evaluatorOrganizations;

    private static List<User> evaluatorUsers;

    private static List<User> evaluatedUsers;

    private static List<Center> centers;

    private static List<EvaluatorTeam> evaluatorTeams;

    private ByteArrayOutputStream orgPhoto;

    private ByteArrayOutputStream usrPhoto;

    private IndicatorsEvaluation currEvaluation;

    private List<IndicatorsEvaluation> indicatorsEvaluations;


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

    public static void logout(){
        instance=null;
    }

    public IndicatorsEvaluation getCurrEvaluation() {
        return currEvaluation;
    }

    public void setCurrEvaluation(IndicatorsEvaluation currEvaluation) {
        this.currEvaluation = currEvaluation;
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

    public List<Indicator> getIndicators(String evaluationType) {

        Predicate<Indicator> indicatorsOfThatEvaluationTypeExist=new Predicate<Indicator>() {
            @Override
            public boolean test(Indicator indicator) {
                return indicator.getEvaluationType().equals(evaluationType);
            }
        };
        return indicators.stream().filter(indicatorsOfThatEvaluationTypeExist).collect(Collectors.toList());
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

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public List<SubAmbit> getSubAmbits() {
        return subAmbits;
    }

    public void setSubAmbits(List<SubAmbit> subAmbits) {
        this.subAmbits = subAmbits;
    }

    public List<SubSubAmbit> getSubSubAmbits() {
        return subSubAmbits;
    }

    public void setSubSubAmbits(List<SubSubAmbit> subSubAmbits) {
        this.subSubAmbits = subSubAmbits;
    }

    public static List<Organization> getEvaluatorOrganizations() {
        return evaluatorOrganizations;
    }

    public static void setEvaluatorOrganizations(List<Organization> evaluatorOrganizations) {
        Session.evaluatorOrganizations = evaluatorOrganizations;
    }

    public static List<Organization> getEvaluatedOrganizations() {
        return evaluatedOrganizations;
    }

    public static void setEvaluatedOrganizations(List<Organization> evaluatedOrganizations) {
        Session.evaluatedOrganizations = evaluatedOrganizations;
    }

    public static List<User> getEvaluatorUsers() {
        return evaluatorUsers;
    }

    public static void setEvaluatorUsers(List<User> evaluatorUsers) {
        Session.evaluatorUsers = evaluatorUsers;
    }

    public static List<EvaluatorTeam> getEvaluatorTeams() {
        return evaluatorTeams;
    }

    public static void setEvaluatorTeams(List<EvaluatorTeam> evaluatorTeams) {
        Session.evaluatorTeams = evaluatorTeams;
    }

    public List<Country> getCountries() {
        if(countries==null){
            countries=CountriesController.GetAll(Locale.getDefault().getLanguage());
        }
        return countries;
    }


    public List<Region> getRegionsByCountry(String idCountry){
        Predicate<Region> regionsOfThatCountryExist=new Predicate<Region>() {
            @Override
            public boolean test(Region region) {
                return region.getIdCountry().equals(idCountry);
            }
        };
        if(regions==null){
            regions=RegionsController.GetRegionsByCountry(idCountry);
        }else if(regions.stream().noneMatch(regionsOfThatCountryExist)){
            regions.addAll(RegionsController.GetRegionsByCountry(idCountry));
        }
        return regions.stream().filter(regionsOfThatCountryExist).collect(Collectors.toList());
    }

    public List<Province> getProvincesByRegion(int idRegion, String idCountry){
        Predicate<Province> provincesOfThatRegionExist=new Predicate<Province>() {
            @Override
            public boolean test(Province province) {
                return province.getIdRegion()==idRegion && province.getIdCountry().equals(idCountry);
            }
        };
        if(provinces==null){
            provinces=ProvincesController.GetProvincesByRegion(idRegion,idCountry);
        }else if(provinces.stream().noneMatch(provincesOfThatRegionExist)){
            provinces.addAll(ProvincesController.GetProvincesByRegion(idRegion,idCountry));
        }
        return provinces.stream().filter(provincesOfThatRegionExist).collect(Collectors.toList());
    }


    public List<City> getCitiesByProvince(int idProvince, int idRegion, String idCountry) {
        Predicate<City> citiesOfThatProvinceExist = new Predicate<City>() {
            @Override
            public boolean test(City city) {
                return city.getIdProvince() == idProvince && city.getIdRegion() == idRegion && city.getIdCountry().equals(idCountry);
            }
        };
        if (cities == null) {
            cities = CitiesController.GetCitiesByProvince(idProvince, idRegion, idCountry);
        } else if (cities.stream().noneMatch(citiesOfThatProvinceExist)) {
            cities.addAll(CitiesController.GetCitiesByProvince(idProvince, idRegion, idCountry));
        }
        return cities.stream().filter(citiesOfThatProvinceExist).collect(Collectors.toList());
    }

    public List<User> getOrgUsersByOrganization(Organization organization){
        Predicate<User> usersOfThatOrganizationExist=new Predicate<User>() {
            @Override
            public boolean test(User user) {
                return user.getIdOrganization()==organization.getIdOrganization() &&
                        user.getOrganizationType().equals(organization.getOrganizationType()) &&
                        user.getIllness().equals(organization.getIllness());
            }
        };
        if(evaluatedUsers==null){
            evaluatedUsers=UsersController.GetAllOrgUsersByOrganization(organization.getIdOrganization(), organization.getOrganizationType(), organization.getIllness());
        }else if(evaluatedUsers.stream().noneMatch(usersOfThatOrganizationExist)){
            evaluatedUsers.addAll(UsersController.GetAllOrgUsersByOrganization(organization.getIdOrganization(), organization.getOrganizationType(), organization.getIllness()));
        }
        return evaluatedUsers.stream().filter(usersOfThatOrganizationExist).collect(Collectors.toList());
    }

    public List<Center> getCentersByOrganization(Organization organization){
        Predicate<Center> centersOfThatOrganizationExist=new Predicate<Center>() {
            @Override
            public boolean test(Center center) {
                return center.getIdOrganization()==organization.getIdOrganization() &&
                        center.getOrgType().equals(organization.getOrganizationType()) &&
                        center.getIllness().equals(organization.getIllness());
            }
        };
        if(centers==null){
            centers=CentersController.GetAllByOrganization(organization);
        }else if(centers.stream().noneMatch(centersOfThatOrganizationExist)){
            centers.addAll(CentersController.GetAllByOrganization(organization));
        }
        return centers.stream().filter(centersOfThatOrganizationExist).collect(Collectors.toList());
    }

    public Ambit getAmbit(int idAmbit){
        Predicate<Ambit> ambitExist=new Predicate<Ambit>() {
            @Override
            public boolean test(Ambit ambit) {
                return ambit.getIdAmbit()==idAmbit;
            }
        };
        return ambits.stream().filter(ambitExist).collect(Collectors.toList()).get(0);
    }

    public SubAmbit getSubAmbit(int idSubAmbit, int idAmbit){
        Predicate<SubAmbit> subAmbitExist=new Predicate<SubAmbit>() {
            @Override
            public boolean test(SubAmbit subAmbit) {
                return subAmbit.getIdSubAmbit()==idSubAmbit && subAmbit.getIdAmbit()==idAmbit;
            }
        };
        return subAmbits.stream().filter(subAmbitExist).collect(Collectors.toList()).get(0);
    }

    public SubSubAmbit getSubSubAmbit(int idSubSubAmbit, int idSubAmbit, int idAmbit){
        Predicate<SubSubAmbit> subSubAmbitExist=new Predicate<SubSubAmbit>() {
            @Override
            public boolean test(SubSubAmbit subSubAmbit) {
                return subSubAmbit.getIdSubSubAmbit()==idSubSubAmbit &&
                        subSubAmbit.getIdSubAmbit()==idSubAmbit && subSubAmbit.getIdAmbit()==idAmbit;
            }
        };
        return subSubAmbits.stream().filter(subSubAmbitExist).collect(Collectors.toList()).get(0);
    }

    public List<Evidence> getEvidencesByIndicator(int idSubSubAmbit,int idSubAmbit, int idAmbit, int idIndicator, String indicatorType, int indicatorVersion, String evaluationType){
        Predicate<Evidence> evidenceOfThatIndicatorExist=new Predicate<Evidence>() {
            @Override
            public boolean test(Evidence evidence) {
                return evidence.getIdSubSubAmbit()==idSubSubAmbit && evidence.getIdSubAmbit()==idSubAmbit &&
                        evidence.getIdAmbit()==idAmbit && evidence.getIdIndicator()==idIndicator &&
                        evidence.getIndicatorType().equals(indicatorType) &&
                        evidence.getIndicatorVersion()==indicatorVersion && evidence.getEvaluationType().equals(evaluationType);
            }
        };
        return evidences.stream().filter(evidenceOfThatIndicatorExist).collect(Collectors.toList());
    }

    public List<EvaluatorTeam> getEvaluatorTeamsByCenter(Center center){
        Predicate<EvaluatorTeam> evaluatorTeamOfThatCenterExist=new Predicate<EvaluatorTeam>() {
            @Override
            public boolean test(EvaluatorTeam evaluatorTeam) {
                return evaluatorTeam.getIdEvaluatedOrganization()==center.getIdOrganization() &&
                        evaluatorTeam.getOrgTypeEvaluated().equals(center.getOrgType()) &&
                        evaluatorTeam.getIdCenter()==center.getIdCenter() &&
                        evaluatorTeam.getIllness().equals(center.getIllness());
            }
        };
        return evaluatorTeams.stream().filter(evaluatorTeamOfThatCenterExist).collect(Collectors.toList());
    }

    public List<IndicatorsEvaluation> getNonFinishedByEvaluatorTeam(EvaluatorTeam evaluatorTeam) {
        if(indicatorsEvaluations==null){
            indicatorsEvaluations=new ArrayList<>();
        }
        Predicate<IndicatorsEvaluation> indicatorEvaluationNonFinishedOfThatEvalTeamExist=new Predicate<IndicatorsEvaluation>() {
            @Override
            public boolean test(IndicatorsEvaluation indicatorsEvaluation) {
                return indicatorsEvaluation.getIdEvaluatorTeam()==evaluatorTeam.getIdEvaluatorTeam()
                        && indicatorsEvaluation.getIdEvaluatedOrganization()==evaluatorTeam.getIdEvaluatedOrganization()
                        && indicatorsEvaluation.getOrgTypeEvaluated().equals(evaluatorTeam.getOrgTypeEvaluated())
                        && indicatorsEvaluation.getIllness().equals(evaluatorTeam.getIllness())
                        && indicatorsEvaluation.getIdCenter()==evaluatorTeam.getIdCenter();
            }
        };
        List<IndicatorsEvaluation> aux=indicatorsEvaluations.stream().filter(indicatorEvaluationNonFinishedOfThatEvalTeamExist).collect(Collectors.toList());
        if(aux.isEmpty()){
            indicatorsEvaluations.addAll(IndicatorsEvaluationsController.GetNonFinishedByEvaluatorTeam(evaluatorTeam.getIdEvaluatorTeam(),evaluatorTeam.getIdEvaluatorOrganization(),evaluatorTeam.getOrgTypeEvaluator(),evaluatorTeam.getIdEvaluatedOrganization(),evaluatorTeam.getOrgTypeEvaluated(),evaluatorTeam.getIllness(),evaluatorTeam.getIdCenter()));
        }
        return indicatorsEvaluations.stream().filter(indicatorEvaluationNonFinishedOfThatEvalTeamExist).collect(Collectors.toList());
    }

    public void obtainIndicatorsFromDataBase(String evaluationType){
        if(indicators==null) {
            ambits = AmbitsController.GetAll();
            subAmbits= SubAmbitsController.GetAll();
            subSubAmbits = SubSubAmbitsController.GetAll();
            indicators = IndicatorsController.GetAll(evaluationType);
            evidences=EvidencesController.GetAll(evaluationType);
        }
    }



    public void obtainOrgsAndEvalUsers(){
        if(evaluatedOrganizations==null){
            evaluatedOrganizations=OrganizationsController.GetAllEvaluatedOrganizations();
        }
        if(evaluatorUsers==null){
            evaluatorUsers=UsersController.GetAllOrgUsersByOrganization(1, "EVALUATOR", "AUTISM");
        }
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

    public void obtainOrgsAndEvalTeams(){
        if(evaluatedOrganizations==null){
            evaluatedOrganizations=OrganizationsController.GetAllEvaluatedOrganizations();
        }
        if(evaluatorTeams==null){
            evaluatorTeams=EvaluatorTeamsController.GetAll();
        }
    }

    public ByteArrayOutputStream getProfilePhoto(boolean isUser){
        if(isUser){
            if(usrPhoto==null && !user.getProfilePhoto().isEmpty()) {
                usrPhoto = FileManager.downloadPhotoProfile(user.getProfilePhoto());
            }
            return usrPhoto;
        }
        if(orgPhoto==null) {
            orgPhoto = FileManager.downloadPhotoProfile(organization.getProfilePhoto());
        }
        return orgPhoto;
    }


}
