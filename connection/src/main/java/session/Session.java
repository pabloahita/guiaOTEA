package session;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cli.indicators.Ambit;
import cli.indicators.Indicator;
import cli.indicators.SubAmbit;
import cli.indicators.SubSubAmbit;
import cli.organization.Organization;
import cli.user.User;
import otea.connection.controller.AmbitsController;
import otea.connection.controller.EvidencesController;
import otea.connection.controller.IndicatorsController;
import otea.connection.controller.OrganizationsController;
import otea.connection.controller.SubAmbitsController;
import otea.connection.controller.SubSubAmbitsController;

public class Session {

    User user;

    Organization organization;

    private static Session instance;

    private List<Indicator> indicators;

    private List<Ambit> ambits;

    private Map<Integer,List<SubAmbit>> subAmbits;

    private Map<List<Integer>,List<SubSubAmbit>> subSubAmbits;
    private Session(User user) {
        setUser(user);
        int idOrg=user.getIdOrganization();
        String orgType=user.getOrganizationType();
        String illness=user.getIllness();
        setOrganization(OrganizationsController.getInstance().Get(idOrg, orgType, illness));
    }

    public static synchronized Session getInstance(){
        return instance;
    }

    public static synchronized Session createSession(User user){
        if(instance==null){
            instance=new Session(user);
        }
        return instance;
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
