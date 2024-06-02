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
import cli.indicators.IndicatorsEvaluationEvidenceReg;
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
import cli.user.Request;
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

    private static Request currRequest;

    private static List<IndicatorsEvaluationIndicatorReg> indicatorsRegs;

    private static List<IndicatorsEvaluationEvidenceReg> evidencesRegs;

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

    public static List<User> getEvaluatedUsers() {
        return evaluatedUsers;
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

    public List<IndicatorsEvaluation> getAllByEvaluatorTeam(EvaluatorTeam evaluatorTeam) {
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
            indicatorsEvaluations.addAll(IndicatorsEvaluationsController.GetAllByEvaluatorTeam(evaluatorTeam.getIdEvaluatorTeam(),evaluatorTeam.getIdEvaluatorOrganization(),evaluatorTeam.getOrgTypeEvaluator(),evaluatorTeam.getIdEvaluatedOrganization(),evaluatorTeam.getOrgTypeEvaluated(),evaluatorTeam.getIdCenter(),evaluatorTeam.getIllness()));
        }
        return indicatorsEvaluations.stream().filter(indicatorEvaluationNonFinishedOfThatEvalTeamExist).collect(Collectors.toList());
    }

    public void obtainIndicatorsFromDataBase(String evaluationType){
        if(indicators==null || !indicators.get(0).getEvaluationType().equals(evaluationType)) {
            ambits=new ArrayList<>();
            subAmbits=new ArrayList<>();
            subSubAmbits=new ArrayList<>();
            indicators=new ArrayList<>();
            evidences=new ArrayList<>();
            List<JsonObject> allData=IndicatorsController.GetAll(evaluationType);
            JsonObject tams=allData.get(allData.size()-1);
            int numIndicators=tams.getAsJsonPrimitive("numIndicators").getAsInt();
            int numEvidences=-1;
            if(evaluationType.equals("COMPLETE")){
                numEvidences=tams.getAsJsonPrimitive("numEvidences").getAsInt();
            }
            int numAmbits=tams.getAsJsonPrimitive("numAmbits").getAsInt();
            int numSubAmbits=tams.getAsJsonPrimitive("numSubAmbits").getAsInt();
            int numSubSubAmbits=tams.getAsJsonPrimitive("numSubSubAmbits").getAsInt();
            List<JsonObject> indicatorsJson=allData.subList(0,numIndicators);
            for(JsonObject ind:indicatorsJson){
                indicators.add(new Indicator(ind.getAsJsonPrimitive("idIndicator").getAsInt(), ind.getAsJsonPrimitive("indicatorType").getAsString()
                        , ind.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), ind.getAsJsonPrimitive("idSubAmbit").getAsInt(), ind.getAsJsonPrimitive("idAmbit").getAsInt(),
                        ind.getAsJsonPrimitive("descriptionEnglish").getAsString(), ind.getAsJsonPrimitive("descriptionSpanish").getAsString(), ind.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        ind.getAsJsonPrimitive("descriptionBasque").getAsString(), ind.getAsJsonPrimitive("descriptionCatalan").getAsString(), ind.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        ind.getAsJsonPrimitive("descriptionGalician").getAsString(), ind.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        ind.getAsJsonPrimitive("descriptionItalian").getAsString(), ind.getAsJsonPrimitive("descriptionPortuguese").getAsString(), ind.getAsJsonPrimitive("indicatorPriority").getAsString(),
                        ind.getAsJsonPrimitive("indicatorVersion").getAsInt(), ind.getAsJsonPrimitive("isActive").getAsInt(), evaluationType));
            }
            List<JsonObject> evidencesJson;
            List<JsonObject> ambitsJson;
            List<JsonObject> subAmbitsJson;
            List<JsonObject> subSubAmbitsJson;
            if(evaluationType.equals("COMPLETE")){
                evidencesJson=allData.subList(numIndicators,numIndicators+numEvidences);
                ambitsJson=allData.subList(numIndicators+numEvidences,numIndicators+numEvidences+numAmbits);
                subAmbitsJson=allData.subList(numIndicators+numEvidences+numAmbits,numIndicators+numEvidences+numAmbits+numSubAmbits);
                subSubAmbitsJson=allData.subList(numIndicators+numEvidences+numAmbits+numSubAmbits,numIndicators+numEvidences+numAmbits+numSubAmbits+numSubSubAmbits);

                for(JsonObject ev:evidencesJson){
                    evidences.add(new Evidence(ev.getAsJsonPrimitive("idEvidence").getAsInt(),ev.getAsJsonPrimitive("idIndicator").getAsInt(), ev.getAsJsonPrimitive("indicatorType").getAsString(),
                            ev.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), ev.getAsJsonPrimitive("idSubAmbit").getAsInt(), ev.getAsJsonPrimitive("idAmbit").getAsInt(),
                            ev.getAsJsonPrimitive("descriptionEnglish").getAsString(), ev.getAsJsonPrimitive("descriptionSpanish").getAsString(), ev.getAsJsonPrimitive("descriptionFrench").getAsString(),
                            ev.getAsJsonPrimitive("descriptionBasque").getAsString(), ev.getAsJsonPrimitive("descriptionCatalan").getAsString(), ev.getAsJsonPrimitive("descriptionDutch").getAsString(),
                            ev.getAsJsonPrimitive("descriptionGalician").getAsString(), ev.getAsJsonPrimitive("descriptionGerman").getAsString(),
                            ev.getAsJsonPrimitive("descriptionItalian").getAsString(), ev.getAsJsonPrimitive("descriptionPortuguese").getAsString(),
                            ev.getAsJsonPrimitive("evidenceValue").getAsInt(), ev.getAsJsonPrimitive("indicatorVersion").getAsInt(), evaluationType));
                }
            }
            else{
                ambitsJson=allData.subList(numIndicators,numIndicators+numAmbits);
                subAmbitsJson=allData.subList(numIndicators+numAmbits,numIndicators+numAmbits+numSubAmbits);
                subSubAmbitsJson=allData.subList(numIndicators+numAmbits+numSubAmbits,numIndicators+numAmbits+numSubAmbits+numSubSubAmbits);
            }

            for(JsonObject amb:ambitsJson){
                ambits.add(new Ambit(amb.getAsJsonPrimitive("idAmbit").getAsInt(), amb.getAsJsonPrimitive("descriptionEnglish").getAsString(), amb.getAsJsonPrimitive("descriptionSpanish").getAsString(), amb.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        amb.getAsJsonPrimitive("descriptionBasque").getAsString(), amb.getAsJsonPrimitive("descriptionCatalan").getAsString(), amb.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        amb.getAsJsonPrimitive("descriptionGalician").getAsString(), amb.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        amb.getAsJsonPrimitive("descriptionItalian").getAsString(), amb.getAsJsonPrimitive("descriptionPortuguese").getAsString()));
            }

            for(JsonObject subAmb:subAmbitsJson){
                subAmbits.add(new SubAmbit(subAmb.getAsJsonPrimitive("idSubAmbit").getAsInt(), subAmb.getAsJsonPrimitive("idAmbit").getAsInt(), subAmb.getAsJsonPrimitive("descriptionEnglish").getAsString(), subAmb.getAsJsonPrimitive("descriptionSpanish").getAsString(), subAmb.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        subAmb.getAsJsonPrimitive("descriptionBasque").getAsString(), subAmb.getAsJsonPrimitive("descriptionCatalan").getAsString(), subAmb.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        subAmb.getAsJsonPrimitive("descriptionGalician").getAsString(), subAmb.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        subAmb.getAsJsonPrimitive("descriptionItalian").getAsString(), subAmb.getAsJsonPrimitive("descriptionPortuguese").getAsString()));
            }

            for(JsonObject subSubAmb:subSubAmbitsJson){
                subSubAmbits.add(new SubSubAmbit(subSubAmb.getAsJsonPrimitive("idSubSubAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("idSubAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("idAmbit").getAsInt(), subSubAmb.getAsJsonPrimitive("descriptionEnglish").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionSpanish").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionFrench").getAsString(),
                        subSubAmb.getAsJsonPrimitive("descriptionBasque").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionCatalan").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionDutch").getAsString(),
                        subSubAmb.getAsJsonPrimitive("descriptionGalician").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionGerman").getAsString(),
                        subSubAmb.getAsJsonPrimitive("descriptionItalian").getAsString(), subSubAmb.getAsJsonPrimitive("descriptionPortuguese").getAsString()));
            }
        }
    }



    public void obtainUsersAndCenters(){
        if(centers==null){
            centers=CentersController.GetAllByOrganization(organization);
        }
        if(evaluatedUsers==null){
            evaluatedUsers=UsersController.GetAllOrgUsersByOrganization(organization.getIdOrganization(), organization.getOrgType(), organization.getIllness());
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



    public static Request getCurrRequest() {
        return currRequest;
    }

    public static void setCurrRequest(Request currRequest) {
        Session.currRequest = currRequest;
    }

    public List<IndicatorsEvaluationEvidenceReg> GetAllEvidencesRegsByIndicatorsEvaluation(IndicatorsEvaluation current_evaluation){

        if(evidencesRegs==null){
            evidencesRegs=new ArrayList<>();
        }
        Predicate<IndicatorsEvaluationEvidenceReg> evidencesRegsOfThatIndicatorsEvaluationExist=new Predicate<IndicatorsEvaluationEvidenceReg>() {
            @Override
            public boolean test(IndicatorsEvaluationEvidenceReg reg) {
                return reg.getEvaluationDate()==current_evaluation.getEvaluationDate()
                        && reg.getIdEvaluatorTeam()==current_evaluation.getIdEvaluatorTeam()
                        && reg.getIdEvaluatorOrganization()==current_evaluation.getIdEvaluatorOrganization()
                        && reg.getOrgTypeEvaluator().equals(current_evaluation.getOrgTypeEvaluator())
                        && reg.getIdEvaluatedOrganization()==current_evaluation.getIdEvaluatedOrganization()
                        && reg.getOrgTypeEvaluated().equals(current_evaluation.getOrgTypeEvaluated())
                        && reg.getIllness().equals(current_evaluation.getIllness())
                        && reg.getIdCenter()==current_evaluation.getIdCenter()
                        && reg.getEvaluationType().equals(current_evaluation.getEvaluationType());
            }
        };
        return evidencesRegs.stream().filter(evidencesRegsOfThatIndicatorsEvaluationExist).collect(Collectors.toList());

    }

    public List<IndicatorsEvaluationIndicatorReg> GetAllIndicatorsRegsByIndicatorsEvaluation(IndicatorsEvaluation current_evaluation){

        if(indicatorsRegs==null){
            indicatorsRegs=new ArrayList<>();
        }
        Predicate<IndicatorsEvaluationIndicatorReg> indicatorsRegsOfThatIndicatorsEvaluationExist=new Predicate<IndicatorsEvaluationIndicatorReg>() {
            @Override
            public boolean test(IndicatorsEvaluationIndicatorReg reg) {
                return reg.getEvaluationDate()==current_evaluation.getEvaluationDate()
                        && reg.getIdEvaluatorTeam()==current_evaluation.getIdEvaluatorTeam()
                        && reg.getIdEvaluatorOrganization()==current_evaluation.getIdEvaluatorOrganization()
                        && reg.getOrgTypeEvaluator().equals(current_evaluation.getOrgTypeEvaluator())
                        && reg.getIdEvaluatedOrganization()==current_evaluation.getIdEvaluatedOrganization()
                        && reg.getOrgTypeEvaluated().equals(current_evaluation.getOrgTypeEvaluated())
                        && reg.getIllness().equals(current_evaluation.getIllness())
                        && reg.getIdCenter()==current_evaluation.getIdCenter()
                        && reg.getEvaluationType().equals(current_evaluation.getEvaluationType());
            }
        };
        return indicatorsRegs.stream().filter(indicatorsRegsOfThatIndicatorsEvaluationExist).collect(Collectors.toList());

    }

    public void setCurrRegs(List<IndicatorsEvaluationIndicatorReg> indicatorRegs, List<IndicatorsEvaluationEvidenceReg> evidenceRegs) {
        indicatorsRegs=indicatorRegs;
        evidencesRegs=evidenceRegs;
    }

    public void getRegsByIndicatorsEvaluation(IndicatorsEvaluation indicatorsEvaluation) {
        List<JsonObject> regs=IndicatorsEvaluationsController.GetRegsByIndicatorsEvaluation(indicatorsEvaluation);
        List<IndicatorsEvaluationIndicatorReg> indicatorsRegsList=new ArrayList<>();
        List<IndicatorsEvaluationEvidenceReg> evidencesRegsList=new ArrayList<>();
        List<JsonObject> indicatorsRegs;
        if(indicatorsEvaluation.getEvaluationType().equals("COMPLETE")){
            JsonObject tams=regs.get(regs.size()-1);
            int numIndicatorsRegs=tams.getAsJsonPrimitive("numIndicatorsRegs").getAsInt();
            int numEvidencesRegs=tams.getAsJsonPrimitive("numEvidencesRegs").getAsInt();
            indicatorsRegs=regs.subList(0,numIndicatorsRegs);
            List<JsonObject> evidencesRegs=regs.subList(numIndicatorsRegs,numEvidencesRegs+numIndicatorsRegs);
            for(JsonObject reg: evidencesRegs){
                int idSubSubAmbit=reg.getAsJsonPrimitive("idSubSubAmbit").getAsInt();
                int idSubAmbit=reg.getAsJsonPrimitive("idSubAmbit").getAsInt();
                int idAmbit=reg.getAsJsonPrimitive("idAmbit").getAsInt();
                int idIndicator=reg.getAsJsonPrimitive("idIndicator").getAsInt();
                String indicatorType=reg.getAsJsonPrimitive("indicatorType").getAsString();
                int idEvidence=reg.getAsJsonPrimitive("idEvidence").getAsInt();
                int indicatorVersion=reg.getAsJsonPrimitive("indicatorVersion").getAsInt();
                int isMarked=reg.getAsJsonPrimitive("isMarked").getAsInt();
                String observationsSpanish=reg.getAsJsonPrimitive("observationsSpanish").getAsString();
                String observationsEnglish=reg.getAsJsonPrimitive("observationsEnglish").getAsString();
                String observationsFrench=reg.getAsJsonPrimitive("observationsFrench").getAsString();
                String observationsBasque=reg.getAsJsonPrimitive("observationsBasque").getAsString();
                String observationsCatalan=reg.getAsJsonPrimitive("observationsCatalan").getAsString();
                String observationsDutch=reg.getAsJsonPrimitive("observationsDutch").getAsString();
                String observationsGalician=reg.getAsJsonPrimitive("observationsGalician").getAsString();
                String observationsGerman=reg.getAsJsonPrimitive("observationsGerman").getAsString();
                String observationsItalian=reg.getAsJsonPrimitive("observationsItalian").getAsString();
                String observationsPortuguese=reg.getAsJsonPrimitive("observationsPortuguese").getAsString();
                evidencesRegsList.add(new IndicatorsEvaluationEvidenceReg(indicatorsEvaluation.getEvaluationDate(), indicatorsEvaluation.getIdEvaluatedOrganization(), indicatorsEvaluation.getOrgTypeEvaluated(), indicatorsEvaluation.getIdEvaluatorTeam(), indicatorsEvaluation.getIdEvaluatorOrganization(), indicatorsEvaluation.getOrgTypeEvaluator(), indicatorsEvaluation.getIllness(), indicatorsEvaluation.getIdCenter(), idEvidence, idIndicator, indicatorType, idSubSubAmbit, idSubAmbit, idAmbit, indicatorVersion, isMarked, indicatorsEvaluation.getEvaluationType(),observationsSpanish, observationsEnglish, observationsFrench, observationsBasque, observationsCatalan,
                        observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese));
            }

        }
        else {
            indicatorsRegs = regs;
        }
        for(JsonObject reg: indicatorsRegs){
            int idSubSubAmbit=reg.getAsJsonPrimitive("idSubSubAmbit").getAsInt();
            int idSubAmbit=reg.getAsJsonPrimitive("idSubAmbit").getAsInt();
            int idAmbit=reg.getAsJsonPrimitive("idAmbit").getAsInt();
            int idIndicator=reg.getAsJsonPrimitive("idIndicator").getAsInt();
            int indicatorVersion=reg.getAsJsonPrimitive("indicatorVersion").getAsInt();
            String observationsSpanish=reg.getAsJsonPrimitive("observationsSpanish").getAsString();
            String observationsEnglish=reg.getAsJsonPrimitive("observationsEnglish").getAsString();
            String observationsFrench=reg.getAsJsonPrimitive("observationsFrench").getAsString();
            String observationsBasque=reg.getAsJsonPrimitive("observationsBasque").getAsString();
            String observationsCatalan=reg.getAsJsonPrimitive("observationsCatalan").getAsString();
            String observationsDutch=reg.getAsJsonPrimitive("observationsDutch").getAsString();
            String observationsGalician=reg.getAsJsonPrimitive("observationsGalician").getAsString();
            String observationsGerman=reg.getAsJsonPrimitive("observationsGerman").getAsString();
            String observationsItalian=reg.getAsJsonPrimitive("observationsItalian").getAsString();
            String observationsPortuguese=reg.getAsJsonPrimitive("observationsPortuguese").getAsString();
            int numEvidencesMarked=reg.getAsJsonPrimitive("numEvidencesMarked").getAsInt();
            String status=reg.getAsJsonPrimitive("status").getAsString();
            int requiresImprovementPlan=reg.getAsJsonPrimitive("requiresImprovementPlan").getAsInt();
            indicatorsRegsList.add(new IndicatorsEvaluationIndicatorReg(indicatorsEvaluation.getEvaluationDate(), indicatorsEvaluation.getIdEvaluatedOrganization(), indicatorsEvaluation.getOrgTypeEvaluated(), indicatorsEvaluation.getIdEvaluatorTeam(), indicatorsEvaluation.getIdEvaluatorOrganization(), indicatorsEvaluation.getOrgTypeEvaluator(), indicatorsEvaluation.getIllness(), indicatorsEvaluation.getIdCenter(), idIndicator, idSubSubAmbit, idSubAmbit, idAmbit, indicatorVersion, indicatorsEvaluation.getEvaluationType(), observationsSpanish, observationsEnglish, observationsFrench, observationsBasque, observationsCatalan,
                    observationsDutch, observationsGalician, observationsGerman, observationsItalian, observationsPortuguese, numEvidencesMarked, status, requiresImprovementPlan));
        }

        Session.getInstance().setCurrRegs(indicatorsRegsList,evidencesRegsList);

    }


}
