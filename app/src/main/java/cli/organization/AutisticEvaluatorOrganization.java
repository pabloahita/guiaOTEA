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
    public AutisticEvaluatorOrganization(int idOrganization, String orgType, String illness, String name, Address address, int telephone, String email, String information) {
        super(idOrganization, orgType, illness, name, address, telephone, email, information);
    }

    @Override
    public void setEvaluatorTeams() {
        super.setEvaluatorTeams(new LinkedList<EvaluatorTeam>());
        ResultSet rs1 = null;
        PreparedStatement ps1 = null;
        ResultSet rs2 = null;
        PreparedStatement ps2 = null;
        ResultSet rs3 = null;
        PreparedStatement ps3 = null;
        try {
            ps1 = getConnection().getConnection().prepareStatement("SELECT * FROM EVALUATORTEAMS WHERE idOrganization=? AND illness=?");
            ps1.setInt(1, getIdOrganization());
            ps1.setString(2, getIllness());
            rs1 = ps1.executeQuery();
            while (rs1.next()) {
                int idEvaluatorTeam = rs1.getInt("idEvaluatorTeam");
                Date creationDate = rs1.getDate("evaluationDate");
                String emailConsultant = rs1.getString("emailConsultant");
                ps2 = getConnection().getConnection().prepareStatement("SELECT * FROM EVALUATORTEAMMEMBERS WHERE idEvaluatorTeam=? AND illness=?");
                ps2.setInt(1, idEvaluatorTeam);
                ps2.setString(2, getIllness());
                rs2 = ps2.executeQuery();
                List<EvaluatorOrganizationUser> members = new LinkedList<>();
                while (rs2.next()) {
                    String emailUser = rs2.getString("emailUser");
                    ps3 = getConnection().getConnection().prepareStatement("SELECT " +
                            "USERS.emailUser AS emailUser," +
                            "USERS.userType AS userType," +
                            "USERS.firstName AS firstName," +
                            "USERS.lastName AS lastName," +
                            "USERS.telephone AS telephone," +
                            "AUTISTICEVALUATORORGUSERS.idOrganization AS idOrganization," +
                            "AUTISTICEVALUATORORGUSERS.illness AS illness," +
                            " FROM USERS, AUTISTICEVALUATORORGUSERS " +
                            "WHERE USERS.emailUser=AUTISTICEVALUATORORGUSERS.emailUser" +
                            "AND USERS.emailUser=?" +
                            "AND AUTISTICEVALUATORORGUSERS.idOrganization=?" +
                            "AND AUTISTICEVALUATORORGUSERS.illness=?");
                    ps3.setString(1, emailUser);
                    ps3.setInt(2, getIdOrganization());
                    ps3.setString(3, getIllness());
                    rs3 = ps3.executeQuery();
                    if (rs3.next()) {
                        String first_name = rs3.getString("firstName");
                        String last_name = rs3.getString("lastName");
                        String passwordUser = rs3.getString("passwordUser");
                        int telephone = rs3.getInt("telephone");
                        members.add(new EvaluatorOrganizationUser(first_name, last_name, emailUser, passwordUser, telephone, this));
                    }
                }
                getEvaluatorTeams().add(new EvaluatorTeam(idEvaluatorTeam, creationDate, members.get(0), members, this));
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public void addEvaluatorTeam(EvaluatorTeam evaluatorTeam){
        getEvaluatorTeams().add(evaluatorTeam);
        int res=0;
        PreparedStatement ps = null;
        try{
            ps=getConnection().getConnection().prepareStatement("INSERT INTO EVALUATORTEAMS VALUES (?,?,?,?,?)");
            ps.setInt(1,evaluatorTeam.getId());
            ps.setDate(2,evaluatorTeam.getCreationDate());
            ps.setString(3,evaluatorTeam.getExternalConsultant().getEmail());
            ps.setInt(4,getIdOrganization());
            ps.setString(5,getIllness());
            res=ps.executeUpdate();
            if(res==1){
                ps=getConnection().getConnection().prepareStatement("INSERT INTO AUTISTICEVALUATORTEAMS VALUES (?,?,?)");
                ps.setInt(1,evaluatorTeam.getId());
                ps.setInt(2,getIdOrganization());
                ps.setString(3,getIllness());
                res=ps.executeUpdate();
                if(res==1){
                    for(EvaluatorOrganizationUser user: evaluatorTeam.getMembers()) {
                        ps=getConnection().getConnection().prepareStatement("INSERT INTO EVALUATORTEAMMEMBERS VALUES(?,?,?,?,?)");
                        ps.setString(1, user.getEmail());
                        ps.setInt(2,evaluatorTeam.getId());
                        ps.setInt(3,getIdOrganization());
                        ps.setString(4,getIllness());
                        res=ps.executeUpdate();
                    }
                }
            }
        }catch(SQLException e){}
    }

}
