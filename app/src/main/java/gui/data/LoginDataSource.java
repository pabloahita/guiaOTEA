package gui.data;

import android.util.Log;

import cli.indicators.Indicator;
import cli.organization.AbstractOrganization;
import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.Organization;
import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;
import cli.user.Administrator;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;
import cli.user.User;
import connection.ConnectionToLocalDatabase;
import gui.data.model.UserNonExistingException;
import gui.data.model.WrongPasswordException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    Connection con;
    Driver d;

    static String server="DESKTOP-MGP7BPL:1433"; //TODO: Cambiar nombre del servidor cuando se pase éste a Azure
    static String instance="FMIRADAS";
    static String database="fmiradas";

    //public Result<User> login(String username, String password) {
        /*try {
            // TODO: handle loggedInUser authentication
            Log.d("TryingToConnect","Intentando abrir la conexión");
            d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            Log.d("AccessDriver","Acceder al driver");
            con=d.connect("jdbc:sqlserver://"+server+";DatabaseName="+database+";instance="+instance+";encrypt=true;trustServerCertificate=true;user="+username+";password="+password, new Properties());
            Log.d("ConSuc","Connection succesful");
            //connect();
            Indicator.setConnection(con);
            AbstractOrganization.setConnection(con);
            EvaluatorTeam.setConnection(con);
            PreparedStatement ps=con.prepareStatement("SELECT * FROM USERS WHERE emailUser=?");
            ps.setString(1,username);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                if(rs.getString("passwordUser")==password){
                    List<Object> userParams=new LinkedList<>();
                    userParams.add(username);
                    userParams.add(password);
                    userParams.add(rs.getString("first_name"));
                    userParams.add(rs.getString("last_name"));
                    userParams.add(rs.getInt("telephone"));
                    User user=null;
                    String[] userTypes={"ADMIN","EVALUATED","EVALUATOR"};
                    int i=0;
                    while(user==null && i<userTypes.length) {
                        user = makeSelect(userTypes[i], con.getConnection(), userParams);
                        i++;
                    }
                    if(user==null){
                        //con.stop();
                        return new Result.Error(new UserNonExistingException());
                    }
                    user.connect();
                    return new Result.Success<>(user);
                }
                else{
                    //con.stop();
                    return new Result.Error(new WrongPasswordException());
                }
            }
            else{
                //con.stop();
                return new Result.Error(new UserNonExistingException());
            }
        } catch (Exception e) {
            Log.d("EXC",e.toString());
            return new Result.Error(new IOException("Error logging in", e));
        }*/
    //}

    public void logout() {
        // TODO: revoke authentication
    }

    private User makeSelect(String userType, Connection con, List<Object> userParams) throws Exception {
        if(userType=="ADMIN"){
            return makeAdminSelect(con,userParams);
        }
        return makeOrgSelect(userType,con,userParams);
    }

    private User makeAdminSelect(Connection con, List<Object> userParams) throws SQLException {
        String email=(String) userParams.get(0);
        String password=(String) userParams.get(1);
        String first_name=(String) userParams.get(2);
        String last_name=(String) userParams.get(3);
        int telephoneUser=(int) userParams.get(4);
        PreparedStatement ps=con.prepareStatement("SELECT emailUser FROM administrators WHERE emailUser=?");
        ps.setString(1,email);
        ResultSet rs= ps.executeQuery();
        if(rs.next()){
            return new Administrator(first_name,last_name,email,password,telephoneUser);
        }
        return null;
    }

    private User makeOrgSelect(String orgType, Connection con, List<Object> userParams) throws Exception {
        String tableName="";
        if(orgType=="EVALUATED"){
            tableName="evaluatedOrganizationUsers";
        }
        else if(orgType=="EVALUATOR"){
            tableName="evaluatorOrganizationUsers";
        }
        else{
            throw new Exception();
        }
        String email=(String) userParams.get(0);
        String password=(String) userParams.get(1);
        String first_name=(String) userParams.get(2);
        String last_name=(String) userParams.get(3);
        int telephoneUser=(int) userParams.get(4);
        PreparedStatement ps=con.prepareStatement("SELECT emailUser, idOrganization, illness FROM "+tableName+" WHERE emailUser=?");
        ps.setString(1,email);
        ResultSet rs= ps.executeQuery();
        if(rs.next()){
            int idOrganization=rs.getInt("idOrganization");
            String illness=rs.getString("illness");
            ps=con.prepareStatement("SELECT * FROM organizations WHERE idOrganization=? AND illness=? AND orgType=?");
            ps.setInt(1,idOrganization);
            ps.setString(2,illness);
            ps.setString(3,orgType);
            rs=ps.executeQuery();
            if(rs.next()){
                String nameOrganization=rs.getString("nameOrg");
                int idAddress=rs.getInt("idAddress");
                String information=rs.getString("information");
                String emailOrg=rs.getString("email");
                int telephoneOrg=rs.getInt("telephone");
                ps=con.prepareStatement("SELECT * FROM addreses WHERE idAddress=?");
                ps.setInt(1,idAddress);
                rs= ps.executeQuery();
                if(rs.next()){
                    String roadType=rs.getString("roadType");
                    String nameSt=rs.getString("nameSt");
                    int numberSt=rs.getInt("numberSt");
                    int floorSt=rs.getInt("floorSt");
                    char apt=rs.getString("apt").charAt(0);
                    int zipCode=rs.getInt("zipCode");
                    String city=rs.getString("city");
                    String province=rs.getString("province");
                    String region=rs.getString("region");
                    String country=rs.getString("country");
                    Address address=new Address(roadType,nameSt,numberSt,floorSt,apt,zipCode,city,province,region,country);
                    if(orgType=="EVALUATED"){
                        Organization org=new AutisticEvaluatedOrganization(idOrganization,orgType,illness,nameOrganization,address,telephoneOrg,emailOrg,information);
                        return new EvaluatedOrganizationUser(first_name,last_name,email,password,telephoneUser,org);
                    }
                    else if(orgType=="EVALUATOR"){
                        Organization org=new AutisticEvaluatorOrganization(idOrganization,orgType,illness,nameOrganization,address,telephoneOrg,emailOrg,information);
                        return new EvaluatorOrganizationUser(first_name,last_name,email,password,telephoneUser,org);
                    }

                }
            }
        }
        return null;
    }
}