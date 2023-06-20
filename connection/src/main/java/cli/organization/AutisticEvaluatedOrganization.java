package cli.organization;

//import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import cli.indicators.Indicator;

public class AutisticEvaluatedOrganization extends AbstractEvaluatedOrganization {


    public AutisticEvaluatedOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, long telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant) {
        super(idOrganization, orgType, illness, name, idAddress, telephone, email, information, emailOrgPrincipal, emailOrgConsultant);
    }

    @Override
    public void setIndicators() {

    }

}
