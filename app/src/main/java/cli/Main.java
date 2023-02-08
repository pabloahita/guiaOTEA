package cli;

import java.sql.Date;
import java.util.LinkedList;

import cli.organization.AutisticOrganization;
import cli.organization.CEvaluatorOrganization;
import cli.organization.EvaluatedOrganization;
import cli.organization.EvaluatorOrganization;
import cli.organization.IndicatorsEvaluation;
import cli.organization.Organization;
import cli.organization.data.Address;
import cli.organization.data.EvaluatorTeam;
import cli.organization.data.RoadType;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;

public class Main {
    public static void main(String[] args){
        //Main method (
        java.util.Date aux=new java.util.Date();
        EvaluatedOrganizationUser organization_principal=new EvaluatedOrganizationUser("MJ","LJ","mjlj@google.com","123456",655447788);
        EvaluatedOrganization evaluatedOrganization=new AutisticOrganization("Autismo Majadahonda", new Address(RoadType.CARRETERA,"de Boadilla del Monte",5,0,'/',28220,"Majadahonda (Madrid)","España"), 916390390, "cita@autismo.es", "");
        evaluatedOrganization.setOrganizationPrincipal(organization_principal);
        evaluatedOrganization.setOrganizationRepresentant(organization_principal);
        evaluatedOrganization.getOrganizationPrincipal().setOrganization(evaluatedOrganization);
        evaluatedOrganization.getOrganizationRepresentant().setOrganization(evaluatedOrganization);
        EvaluatorOrganization evaluatorOrganization=new CEvaluatorOrganization("Casa del Desarrollador", new Address(RoadType.CALLE,"Vitoria",171,5,'C',9007,"Burgos","Spain"), 947123456, "acasa@hotmail.com", "");
        EvaluatorOrganizationUser external_consultant=new EvaluatorOrganizationUser("MY", "dBG", "ydbg@ymail.com", "987654", 612345678);
        EvaluatorTeam evaluatorTeam=new EvaluatorTeam(new Date(aux.getTime()),external_consultant);
        evaluatorTeam.setMembers(new LinkedList<EvaluatorOrganizationUser>());
        evaluatorTeam.setOrganization(evaluatorOrganization);
        evaluatorTeam.getExternalConsultant().setOrganization((Organization) evaluatorOrganization);
        evaluatorTeam.getMembers().add(external_consultant);
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("AD", "AO", "adao@ymail.com", "246810", 658563214,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("AJ", "dBG", "ajdbg@ymail.com", "3691215", 678965412,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("MP", "MR", "mpmr@ymail.com", "481216", 696352125,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("S", "dBR", "sdbr@ymail.com", "5101520", 698765432,(Organization) evaluatorOrganization));
        evaluatorTeam.getMembers().add(new EvaluatorOrganizationUser("T", "GR", "tgr@ymail.com", "6121824", 698765432,(Organization) evaluatorOrganization));
        IndicatorsEvaluation evaluation=new IndicatorsEvaluation(new Date(aux.getTime()),evaluatedOrganization,evaluatorTeam);
        evaluation.doEvaluationCli();
    }
}
