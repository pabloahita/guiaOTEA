package cli.organization;

import java.sql.Connection;

import cli.organization.data.Address;


public interface Organization {
    String getName();
    Address getAddress();
    int getTelephone();

    String getEmail();
    String getInformation();
    int getIdOrganization();

    void setIdOrganization(int id);


    String getIllness();

    int getIdAddress();

    String getOrganizationType();

    //Common methods for every organization


}
