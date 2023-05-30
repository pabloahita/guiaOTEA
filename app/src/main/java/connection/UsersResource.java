package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.EvaluatedOrganization;
import cli.organization.Organization;
import cli.user.EvaluatedOrganizationUser;

@Path("/users")
public class UsersResource {

    @GET
    @Path("/users:evaluated:{email}{organization}")
    @Produces(MediaType.APPLICATION_JSON)
    public EvaluatedOrganizationUser obtainEvaluatedOrgUser(@PathParam("email") String email, @PathParam("organization")EvaluatedOrganization organization) {
        EvaluatedOrganizationUser result=null;

        try {
            DatabaseUtil databaseUtil = new DatabaseUtil();
            Connection connection = databaseUtil.getConnection();

            String sql="SELECT * from users WHERE emailUser="+email;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                String userType=resultSet.getString("userType");
                String passwordUser=resultSet.getString("passwordUser");
                String first_name=resultSet.getString("first_name");
                String last_name=resultSet.getString("last_name");
                int telephone=resultSet.getInt("telephone");
                result=new EvaluatedOrganizationUser(first_name,last_name,email,passwordUser,telephone, organization);
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
