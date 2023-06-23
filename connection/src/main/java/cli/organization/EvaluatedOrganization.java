package cli.organization;



import java.util.LinkedList;
import java.util.List;

import cli.indicators.IndicatorsEvaluation;
import cli.organization.data.Center;
import cli.user.EvaluatedOrganizationUser;
import cli.user.User;

public abstract class EvaluatedOrganization extends Organization implements IEvaluatedOrganization{

    public EvaluatedOrganizationUser organization_principal;
    public EvaluatedOrganizationUser organization_consultant;
    public List<IndicatorsEvaluation> evaluations;

    public List<Center> centerList;
    public EvaluatedOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant) {
        super(idOrganization,orgType,illness,name,idAddress,telephone,email,information,emailOrgPrincipal,emailOrgConsultant);
        setCenterList(new LinkedList<Center>());
        User auxPrincipal=getCaller().obtainOrgUser(emailOrgPrincipal,this);
        setOrganization_principal(new EvaluatedOrganizationUser(auxPrincipal.getFirst_name(), auxPrincipal.getLast_name(), auxPrincipal.getUserType(), auxPrincipal.getEmailUser(), auxPrincipal.getPasswordUser(), auxPrincipal.getTelephone(), this));
        User auxConsultant=getCaller().obtainOrgUser(emailOrgConsultant,this);
        setOrganization_consultant(new EvaluatedOrganizationUser(auxConsultant.getFirst_name(), auxConsultant.getLast_name(), auxConsultant.getUserType(), auxConsultant.getEmailUser(), auxConsultant.getPasswordUser(), auxConsultant.getTelephone(), this));
    }



    public List<IndicatorsEvaluation> getEvaluations() {
        return evaluations;
    }



    public void addEvaluation(IndicatorsEvaluation evaluation){
        this.evaluations.add(evaluation);
        //ConnectionClient.addEvaluation(evaluation);
    }

    @Override
    public List<Center> getCenterList(){return centerList;}

    @Override
    public void setCenterList(List<Center> centerList){this.centerList=centerList;}

    @Override
    public void addCenter(Center center){centerList.add(center);}
    @Override
    public void removeCenter(Center center){centerList.remove(center);}


    @Override
    public EvaluatedOrganizationUser getOrganization_principal() {
        return organization_principal;
    }

    @Override
    public void setOrganization_principal(EvaluatedOrganizationUser organization_principal) {
        this.organization_principal = organization_principal;
    }

    @Override
    public EvaluatedOrganizationUser getOrganization_consultant() {
        return organization_consultant;
    }

    @Override
    public void setOrganization_consultant(EvaluatedOrganizationUser organization_consultant) {
        this.organization_consultant = organization_consultant;
    }
}
