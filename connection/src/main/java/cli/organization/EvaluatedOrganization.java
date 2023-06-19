package cli.organization;

//import android.content.res.AssetManager;


import java.util.List;

import cli.indicators.Indicator;
import cli.organization.data.Center;
import cli.user.EvaluatedOrganizationUser;

public interface EvaluatedOrganization extends Organization {
    // Common methods for every evaluator organization
    void setIndicators();
    List<Indicator> getIndicators();


    void addEvaluation(IndicatorsEvaluation evaluation);


    List<Center> getCenterList();

    void setCenterList(List<Center> centerList);

    void addCenter(Center center);

    void removeCenter(Center center);

    String getEmailOrgPrincipal();

    String getEmailOrgConsultant();
    int getIdOrganization();

}
