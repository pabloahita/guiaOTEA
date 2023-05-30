package cli;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import cli.organization.AbstractEvaluatedOrganization;
import cli.organization.EvaluatedOrganization;
import cli.organization.Organization;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;

public class ConnectionClient {

    private static final String BASE_URL = "http://localhost:8080/OTEA"; // Cambia esto con la URL de tu servidor JAX-RS

    private Client client;
    private static WebTarget target;

    public ConnectionClient() {
        client = ClientBuilder.newClient();
        target = client.target(BASE_URL);
    }

    public static EvaluatorOrganizationUser obtainEvaluatorOrganizationUser(String email, int id, String illness) {
        return target.path("/evaluatorOrg::{email}{id}{illness}").request(MediaType.APPLICATION_JSON).get(EvaluatorOrganizationUser.class);
    }

    public static List<EvaluatorOrganizationUser> obtainEvalTeamMembers(int id, int idOrg, String illness) {
        return target.path("/evalTeamMembers::{id}{idOrg}{illness}").request(MediaType.APPLICATION_JSON).get(List.class);
    }

    public static Organization obtainOrganization(int idOrganization, String orgType, String illness) {
        return target.path("/orgs::{id}{orgType}{illness}").request(MediaType.APPLICATION_JSON).get(Organization.class);
    }

    public static EvaluatedOrganizationUser obtainEvaluatedOrgUser(String email, EvaluatedOrganization organization) {
        return target.path("/users:evaluated:{email}{organization}").request(MediaType.APPLICATION_JSON).get(EvaluatedOrganizationUser.class);
    }



    /*public static void indicatorsToSql(){
        try(BufferedReader br=new BufferedReader(new FileReader("../../assets/AutisticOrganization.csv"))){
            try(BufferedWriter bw=new BufferedWriter(new FileWriter("../../assets/FMiradas.sql",true))){
                String line = br.readLine();
                int current_indicator = 1;
                while (line != null) {
                    String[] aux = line.split(";");
                    bw.write("INSERT INTO indicators VALUES("+current_indicator+",'AUTISM','"+aux[8].split(": ")[1]+"',"+Integer.parseInt(aux[9])+");"+System.lineSeparator());
                    for (int i = 0; i < 4; i++) {
                        int current_evidence = i + 1;
                        //evidences.add(new Evidence(current_evidence, current_indicator, "AUTISM", aux[i + 4], Float.parseFloat(aux[i]))); //The first 4 elements of aux are the values of every incidence, the next 8 elements of aux are the description of every incidence
                        bw.write("INSERT INTO evidences VALUES("+current_evidence+","+current_indicator+",'AUTISM','"+aux[i+4]+"',"+Integer.parseInt(aux[i])+");"+System.lineSeparator());
                    }
                    line = br.readLine();
                    current_indicator++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR. FILE NOT FOUND");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error de lectura del fichero");
            ex.printStackTrace();
        }
    }*/


}
