package cli.organization;

import java.io.IOException;
import java.sql.Connection;

import cli.organization.data.Address;


public interface Organization {
    void setOrgType(String orgType);

    String getName();
    Address getAddress();
    long getTelephone();

    String getEmail();
    String getInformation();

    void setEmailOrgConsultant(String emailOrgPrincipal);

    void setEmailOrgPrincipal(String emailOrgPrincipal);

    int getIdOrganization();

    void setIdOrganization(int id);


    void setIdAddress(int idAddress);

    void setTelephone(long telephone);

    void setEmail(String email);

    void setInformation(String information);

    String getIllness();

    void setName(String name);

    void obtainAddress() throws IOException;

    int getIdAddress();

    String getOrganizationType();

    void setIllness(String illness);

    //Common methods for every organization


}
