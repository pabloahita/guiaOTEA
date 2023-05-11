package cli.indicators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Indicator {
    private int idIndicator;
    private String indicatorType;
    private String description;
    private List<Evidence> evidences;
    private float priority;

    private static Connection con;

    public Indicator(int idIndicator, String indicatorType, String description, float priority) {
        setIdIndicator(idIndicator);
        setIndicatorType(indicatorType);
        setDescription(description);
        setPriority(priority);
        setEvidences();
    }

    public int getIdIndicator() {
        return idIndicator;
    }

    public void setIdIndicator(int idIndicator) {
        this.idIndicator = idIndicator;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public void setEvidences() {
        ResultSet rs=null;
        PreparedStatement ps = null;
        if(this.evidences==null){
            this.evidences=new LinkedList<>();
        }
        try{
            ps=con.prepareStatement("SELECT * FROM EVIDENCES WHERE idIndicator=? AND indicatorType=?");
            ps.setInt(1,getIdIndicator());
            ps.setString(2,getIndicatorType());
            rs=ps.executeQuery();
            while(rs.next()){
                int idEvidence=rs.getInt("idEvidence");
                String evidenceDescription=rs.getString("evidenceDescription");
                int evidenceValue=rs.getInt("evidenceValue");
                evidences.add(new Evidence(idEvidence,getIdIndicator(),getIndicatorType(),evidenceDescription,evidenceValue,false));
            }
        }catch(SQLException e){

        }
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setDescription(String description) {
        this.description=description;
    }
    public String getDescription() {
        return description;
    }


    public int getIndicatorValue(){
        int value=0;
        for(Evidence e : evidences){
            value+=e.getValue();
        }
        return value;
    }


    public void addEvidence(Evidence evidence){
        this.evidences.add(evidence);
    }


    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public int getMultiplicator(int num_evidences_filled){
        if(num_evidences_filled==0||num_evidences_filled==1){return 0;}
        else if (num_evidences_filled==2||num_evidences_filled==3){
            if (this.priority==1){return 1;}
            else if (this.priority==2){return 2;}
            else if (this.priority==3){return 3;}
            else{return 4;}
        }
        else{
            if (this.priority==1){return 2;}
            else if (this.priority==2){return 3;}
            else if (this.priority==3){return 4;}
            else{return 5;}
        }
    }

    public Connection getConnection() {
        return con;
    }

    public static void setConnection(Connection con) {
        Indicator.con = con;
    }


}
