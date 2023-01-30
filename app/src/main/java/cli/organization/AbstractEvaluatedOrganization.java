package cli.organization;

import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.user.RegisteredUser;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractEvaluatedOrganization implements EvaluatedOrganization{

    private String name;
    private Address address;
    private int telephone;
    private String email;
    private String information;
    private RegisteredUser organization_principal;
    private RegisteredUser organization_representant;
    private List<IndicatorsEvaluation> evaluations;
    public AbstractEvaluatedOrganization(String name, Address address, int telephone, String email, String information, RegisteredUser organization_principal, RegisteredUser organization_representant, List<IndicatorsEvaluation> evaluations){
        setName(name);
        setAddress(address);
        setTelephone(telephone);
        setEmail(email);
        setInformation(information);
        setOrganizationPrincipal(organization_principal);
        setOrganizationRepresentant(organization_representant);
        setEvaluations(evaluations);
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setAddress(Address address) {
        this.address=address;
    }

    public void setTelephone(int telephone) {
        this.telephone=telephone;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setInformation(String information) {
        this.information=information;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public int getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getInformation() {
        return information;
    }

    public RegisteredUser getOrganization_principal() {
        return organization_principal;
    }

    public RegisteredUser getOrganization_representant() {
        return organization_representant;
    }

    public List<IndicatorsEvaluation> getEvaluations() {
        return evaluations;
    }

    public void setOrganizationPrincipal(RegisteredUser organization_principal){
        this.organization_principal=organization_principal;
    }

    public void setOrganizationRepresentant(RegisteredUser organization_representant){
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

}
