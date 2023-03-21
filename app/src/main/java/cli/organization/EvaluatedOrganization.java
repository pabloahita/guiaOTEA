package cli.organization;

//import android.content.res.AssetManager;


import java.util.List;

import cli.indicators.Indicator;
import cli.user.EvaluatedOrganizationUser;
import cli.user.OrganizationUser;

public interface EvaluatedOrganization extends Organization {
    // Common methods for every evaluator organization
    void setIndicators();
    List<Indicator> getIndicators();

    void setOrganizationPrincipal(EvaluatedOrganizationUser organization_principal);

    void setOrganizationRepresentant(EvaluatedOrganizationUser organization_principal);

    EvaluatedOrganizationUser getOrganizationPrincipal();

    OrganizationUser getOrganizationRepresentant();

    void addEvaluation(IndicatorsEvaluation evaluation);


    /*void setAssets(AssetManager assets);

    AssetManager getAssets();*/
}
