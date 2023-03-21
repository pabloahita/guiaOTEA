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


    public AutisticEvaluatedOrganization(int idOrganization, String orgType, String illness, String name, Address address, int telephone, String email, String information) {
        super(idOrganization, orgType, illness, name, address, telephone, email, information);
        setIndicators();
    }

    @Override
    public void setIndicators() {
        ResultSet rs=null;
        PreparedStatement ps = null;
        if(super.indicators==null){
            super.indicators=new LinkedList<>();
        }
        try{
            ps=super.getConnection().getConnection().prepareStatement("SELECT * FROM INDICATORS WHERE indicatorType=\"AUTISTIC\"");
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

    /*public void setIndicatorsGui(){
        InputStream file=null;
        BufferedReader br=null;
        try {
            file = getAssets().open("AutisticOrganization.csv");
            br = new BufferedReader(new InputStreamReader(file));
            String line = br.readLine();
            while (line != null) {
                String[] aux = line.split(";");
                List<Evidence> evidences = new LinkedList<Evidence>();
                for (int i = 0; i < 4; i++) {
                    evidences.add(new Evidence(aux[i + 4], Float.parseFloat(aux[i]))); //The first 4 elements of aux are the values of every incidence, the next 8 elements of aux are the description of every incidence
                }
                indicators.add(new Indicator(aux[8], evidences, Float.parseFloat(aux[9]))); // The penultimate element of aux is the indicator's description and the last element of aux is the indicator's priority
                line = br.readLine();
            }
        } catch (IOException ex) {
            Log.d("IOException","ERROR. FILE NOT FOUND");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error de lectura del fichero");
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al cerrar el fichero");
                ex.printStackTrace();
            }
        }
    }

    public void setIndicatorsCli(){
        InputStream file=null;
        BufferedReader br=null;
        String nameFile = "../../assets/AutisticOrganization.csv";
        try {
            br = new BufferedReader(new FileReader(nameFile));
            String line = br.readLine();
            while (line != null) {
                String[] aux = line.split(";");
                List<Evidence> evidences = new LinkedList<Evidence>();
                for (int i = 0; i < 4; i++) {
                    evidences.add(new Evidence(aux[i + 4], Float.parseFloat(aux[i]))); //The first 4 elements of aux are the values of every incidence, the next 8 elements of aux are the description of every incidence
                }
                indicators.add(new Indicator(aux[8], evidences, Float.parseFloat(aux[9]))); // The penultimate element of aux is the indicator's description and the last element of aux is the indicator's priority
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR. FILE NOT FOUND");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error de lectura del fichero");
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al cerrar el fichero");
                ex.printStackTrace();
            }
        }
    }*/
}
