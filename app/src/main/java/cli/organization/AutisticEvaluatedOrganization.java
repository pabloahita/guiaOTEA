package cli.organization;

//import android.util.Log;

import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.user.EvaluatedOrganizationUser;

public class AutisticEvaluatedOrganization extends AbstractEvaluatedOrganization {


    public AutisticEvaluatedOrganization(int idOrganization, String orgType, String illness, String name, int idAddress, int telephone, String email, String information, String emailOrgPrincipal, String emailOrgConsultant) {
        super(idOrganization, orgType, illness, name, idAddress, telephone, email, information, emailOrgPrincipal, emailOrgConsultant);
    }

    @Override
    public void setIndicators() {
        ResultSet rs=null;
        PreparedStatement ps = null;
        if(super.indicators==null){
            super.indicators=new LinkedList<>();
        }
        try{
            ps=super.getConnection().prepareStatement("SELECT * FROM INDICATORS WHERE indicatorType=\"AUTISTIC\"");
            rs=ps.executeQuery();
            while(rs.next()){
                int idIndicator=rs.getInt("indicatorId");
                String indicatorDescription=rs.getString("indicatorDescription");
                int indicatorPriority=rs.getInt("indicatorPriority");
                super.indicators.add(new Indicator(idIndicator,"AUTISTIC",indicatorDescription,indicatorPriority));
            }
        }catch(SQLException e){
            Log.d("SQLException",e.toString());
        }
    }

}
