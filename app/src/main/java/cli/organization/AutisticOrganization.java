package cli.organization;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import cli.indicators.Evidence;
import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.user.RegisteredUser;

public class AutisticOrganization extends AbstractEvaluatedOrganization {

    private final List<Indicator> indicators;

    public AutisticOrganization(String name, Address address, int telephone, String email, String information, RegisteredUser organization_principal, RegisteredUser organization_representant, List<IndicatorsEvaluation> evaluations) {
        super(name, address, telephone, email, information,organization_principal,organization_representant,evaluations);
        indicators=new LinkedList<Indicator>();
        //Obtain indicators
        String nameFile = "AutisticOrganization.csv";
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(nameFile));
            String line = br.readLine();
            while(line != null) {
                String[] aux=line.split(";"); // The first element is the indicator's description, the rest are its four evidences.
                List<Evidence> evidences=new LinkedList<Evidence>();
                for(int i=1;i<aux.length;i++){
                    evidences.add(new Evidence(aux[i],(float) 1));
                }
                indicators.add(new Indicator(aux[0],evidences));
                line = br.readLine();
            }
        }
        catch (FileNotFoundException ex){
            System.out.println("Error: Fichero no encontrado");
            ex.printStackTrace();
        }
        catch(Exception ex){
            System.out.println("Error de lectura del fichero");
            ex.printStackTrace();
        }
        finally{
            try{
                if(br != null){
                    br.close();
                }
            }
            catch (Exception ex){
                System.out.println("Error al cerrar el fichero");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<Indicator> getIndicators() {
        return indicators;
    }
}
