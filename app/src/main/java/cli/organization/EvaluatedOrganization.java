package cli.organization;

import java.util.List;

import cli.indicators.Indicator;
import cli.organization.data.EvaluatorTeam;
import cli.user.EvaluatedOrganizationUser;
import cli.user.OrganizationUser;
import cli.user.User;

public interface EvaluatedOrganization extends Organization {
    // Common methods for every evaluator organization
    void setIndicators();
    List<Indicator> getIndicators();

    void setOrganizationPrincipal(EvaluatedOrganizationUser organization_principal);

    void setOrganizationRepresentant(EvaluatedOrganizationUser organization_principal);

    EvaluatedOrganizationUser getOrganizationPrincipal();

    OrganizationUser getOrganizationRepresentant();
}
