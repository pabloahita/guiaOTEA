package cli.organization;

import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.user.EvaluatedOrganizationUser;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractEvaluatedOrganization extends AbstractOrganization implements EvaluatedOrganization{

    private EvaluatedOrganizationUser organization_principal;
    private EvaluatedOrganizationUser organization_representant;
    private List<IndicatorsEvaluation> evaluations;
    protected List<Indicator> indicators;
    public AbstractEvaluatedOrganization(String name, Address address, int telephone, String email, String information, EvaluatedOrganizationUser organization_principal, EvaluatedOrganizationUser organization_representant, List<IndicatorsEvaluation> evaluations){
        super(name,address,telephone,email,information);
        setOrganizationPrincipal(organization_principal);
        setOrganizationRepresentant(organization_representant);
        setEvaluations(evaluations);
    }

    public AbstractEvaluatedOrganization(String name, Address address, int telephone, String email, String information){
        super(name,address,telephone,email,information);
    }

    @Override
    public EvaluatedOrganizationUser getOrganizationPrincipal() {
        return organization_principal;
    }

    @Override
    public EvaluatedOrganizationUser getOrganizationRepresentant() {
        return organization_representant;
    }


    public List<IndicatorsEvaluation> getEvaluations() {
        return evaluations;
    }

    @Override
    public void setOrganizationPrincipal(EvaluatedOrganizationUser organization_principal){
        this.organization_principal=organization_principal;
    }

    @Override
    public void setOrganizationRepresentant(EvaluatedOrganizationUser organization_representant){
        this.organization_representant=organization_representant;
    }

    public void setEvaluations(List<IndicatorsEvaluation> evaluations){
        if(evaluations==null){
            this.evaluations=new LinkedList<IndicatorsEvaluation>();
        }
        else{
            this.evaluations=evaluations;
        }
    }

    private void addEvaluation(IndicatorsEvaluation evaluation){
        this.evaluations.add(evaluation);
    }

    @Override
    public List<Indicator> getIndicators(){
        return indicators;
    }

}
