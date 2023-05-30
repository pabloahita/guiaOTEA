package connection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.Organization;
import cli.organization.data.EvaluatorTeam;
@Path("/evalTeams")
public class OrganizationsResource {
    
    @GET
    @Path("/orgs::{id}{orgType}{illness}")
    @Produces(MediaType.APPLICATION_JSON)
    public Organization getOrganization(@PathParam("id") int id, @PathParam("orgType") String orgType, @PathParam("illness") String illness) {
        Organization result=null;

        try {
            DatabaseUtil databaseUtil = new DatabaseUtil();
            Connection connection = databaseUtil.getConnection();

            String sql = "SELECT * FROM organizations where idEvaluatorTeam="+id+" and orgType="+orgType+" and illness="+illness;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            if (resultSet.next()) {
                String nameOrg=resultSet.getString("emailConsultant");
                int idAddress=resultSet.getInt("idAddress");
                String email=resultSet.getString("email");
                int telephone=resultSet.getInt("telephone");
                String information=resultSet.getString("information");
                if(orgType=="EVALUATED"){
                    String sql2 = "SELECT emailOrgPrincipal,emailOrgConsultant FROM evaluatedOrganizations where idEvaluatorTeam="+id+" and orgType="+orgType+" and illness="+illness;
                    Statement statement2 = connection.createStatement();
                    ResultSet resultSet2 = statement2.executeQuery(sql2);
                    if(resultSet2.next()){
                        String emailOrgPrincipal=resultSet.getString("emailOrgPrincipal");
                        String emailOrgConsultant=resultSet.getString("emailOrgConsultant");
                        result=new AutisticEvaluatedOrganization(id, orgType, illness, nameOrg, idAddress, telephone, email, information, emailOrgPrincipal, emailOrgConsultant);
                    }
                }
                else{
                    result=new AutisticEvaluatorOrganization(id, orgType, illness, nameOrg, idAddress, telephone, email, information);
                }
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
