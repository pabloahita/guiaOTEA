package cli.organization;

import java.util.List;

import cli.organization.data.Center;
import cli.user.EvaluatedOrganizationUser;

public interface IEvaluatedOrganization {

    public List<Center> getCenterList();
    void setCenterList(List<Center> centerList);
    void addCenter(Center center);
    void removeCenter(Center center);

    EvaluatedOrganizationUser getOrganization_principal();

    void setOrganization_principal(EvaluatedOrganizationUser organization_principal);

    EvaluatedOrganizationUser getOrganization_consultant();

    void setOrganization_consultant(EvaluatedOrganizationUser organization_consultant);
}
