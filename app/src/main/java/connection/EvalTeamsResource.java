package connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cli.ConnectionClient;
import cli.organization.data.EvaluatorTeam;
import cli.user.EvaluatorOrganizationUser;

@Path("/evalTeams")
public class EvalTeamsResource {

    @GET
    @Path("/{id}{orgId}{illness}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EvaluatorTeam> getEvalTeamsByIdByOrgByIllness(@PathParam("id") int id,@PathParam("orgId") int orgId,@PathParam("illness") String illness) {
        List<EvaluatorTeam> result=new ArrayList<EvaluatorTeam>();

        try {
            DatabaseUtil databaseUtil = new DatabaseUtil();
            Connection connection = databaseUtil.getConnection();

            String sql = "SELECT * FROM evaluatorTeams where idEvaluatorTeam="+id+" and idOrganization="+orgId+" and illness="+illness;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                Date creationDate=resultSet.getDate("creationDate");
                String emailConsultant=resultSet.getString("emailConsultant");
                String pacientName=resultSet.getString("pacientName");
                String relativeName=resultSet.getString("relativeName");
                result.add(new EvaluatorTeam(id,creationDate,emailConsultant,orgId,illness,pacientName,relativeName));
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

    @GET
    @Path("/evalTeamMembers::{id}{idOrg}{illness}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EvaluatorOrganizationUser> getEvalTeamMembers(@PathParam("id") int id, @PathParam("idOrg") int idOrg, @PathParam("illness") String illness){
        List<EvaluatorOrganizationUser> result=new ArrayList<EvaluatorOrganizationUser>();

        try {
            DatabaseUtil databaseUtil = new DatabaseUtil();
            Connection connection = databaseUtil.getConnection();

            String sql = "SELECT * FROM evaluatorTeamMembers where idEvaluatorTeam="+id+" and idOrganization="+idOrg+" and illness="+illness;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                String emailUser=resultSet.getString("emailUser");
                result.add(ConnectionClient.obtainEvaluatorOrganizationUser(emailUser,idOrg,illness));
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
