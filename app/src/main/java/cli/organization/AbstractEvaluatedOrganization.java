package cli.organization;

//import android.content.res.AssetManager;

import android.util.Log;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.user.EvaluatedOrganizationUser;

import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractEvaluatedOrganization extends AbstractOrganization implements EvaluatedOrganization{

    private EvaluatedOrganizationUser organization_principal;
    private EvaluatedOrganizationUser organization_representant;
    private List<IndicatorsEvaluation> evaluations;
    protected List<Indicator> indicators;
    //private AssetManager assets;

    public AbstractEvaluatedOrganization(int idOrganization, String orgType, String illness, String name, Address address, int telephone, String email, String information){
        super(idOrganization,orgType,illness,name,address,telephone,email,information);
        setOrganizationPrincipal(organization_principal);
        setOrganizationRepresentant(organization_representant);
    }

    @Override
    public EvaluatedOrganizationUser getOrganizationPrincipal() {
        return organization_principal;
    }

    @Override
    public EvaluatedOrganizationUser getOrganizationRepresentant() {
        return organization_representant;
    }


    public List<IndicatorsEvaluation> getEvaluations() {
        return evaluations;
    }

    @Override
    public void setOrganizationPrincipal(EvaluatedOrganizationUser organization_principal){
        this.organization_principal=organization_principal;
    }

    @Override
    public void setOrganizationRepresentant(EvaluatedOrganizationUser organization_representant){
        this.organization_representant=organization_representant;
    }

    public void setEvaluations(){
        ResultSet rs=null;
        PreparedStatement ps = null;
        this.evaluations=new LinkedList<IndicatorsEvaluation>();
        try{
            ps=super.getConnection().prepareStatement("SELECT * FROM INDICATORSEVALUATIONS WHERE evaluatedOrganizationId=? AND illness=\"?\"");
            ps.setInt(1,super.getIdOrganization());
            ps.setString(2,getIllness());
            rs=ps.executeQuery();
            while(rs.next()){
                Date evaluationDate=rs.getDate("evaluationDate");
                int evaluatorTeamId=rs.getInt("evaluatorTeamId");
                int evaluatorOrganizationId=rs.getInt("evaluatorOrganizationId");

            }
        }catch(SQLException e){
            Log.d("SQLException",e.toString());
        }

    }

    public void addEvaluation(IndicatorsEvaluation evaluation){
        this.evaluations.add(evaluation);
        ResultSet rs=null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try{
            ps1=super.getConnection().prepareStatement("INSERT INTO indicatorsEvaluations VALUES(?,?,?,?,?)");
            ps1.setDate(1,evaluation.getEvaluationDate());
            ps1.setInt(2,evaluation.getEvaluatedOrganization().getIdOrganization());
            ps1.setInt(3,evaluation.getEvaluatorTeam().getId());
            ps1.setInt(4,evaluation.getEvaluatorTeam().getOrganization().getIdOrganization());
            ps1.setString(5,evaluation.getEvaluatedOrganization().getIllness());
            ps1.executeUpdate();
            //---
            Map<Indicator,Integer> evaluationFilled=evaluation.getEvaluation();
            for(Indicator i:evaluationFilled.keySet()){
                for(Evidence e:i.getEvidences()) {
                    ps2 = super.getConnection().prepareStatement("INSERT INTO indicatorsEvaluationsRegs VALUES(?,?,?,?,?)");
                    ps2.setDate(1, evaluation.getEvaluationDate());
                    ps2.setInt(2, evaluation.getEvaluatedOrganization().getIdOrganization());
                    ps2.setString(3, evaluation.getEvaluatedOrganization().getIllness());
                    ps2.setInt(4, i.getIdIndicator());
                    ps2.setInt(5,e.getIdEvidence());
                    ps2.setBoolean(6,e.getStatus());
                    ps2.executeUpdate();
                }
            }
        }catch(SQLException e){
            Log.d("SQLException",e.toString());
        }
    }
    @Override
    public List<Indicator> getIndicators(){
        return indicators;
    }


}
