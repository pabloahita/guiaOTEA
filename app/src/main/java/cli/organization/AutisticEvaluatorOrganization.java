package cli.organization;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.Evidence;
import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;
import cli.user.EvaluatorOrganizationUser;

public class AutisticEvaluatorOrganization extends AbstractEvaluatorOrganization {
    public AutisticEvaluatorOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, int telephone, String email, String information) {
        super(idOrganization, orgType, illness, name, idAddress, telephone, email, information);
    }

    @Override
    public void setEvaluatorTeams() {

    }

    @Override
    public void addEvaluatorTeam(EvaluatorTeam evaluatorTeam){

    }

}
