package cli.organization;

import cli.organization.data.Address;


public interface IOrganization {
    void setOrgType(String orgType);

    String getName();
    Address getAddress();
    long getTelephone();

    String getEmail();
    String getInformation();

    void setEmailOrgPrincipal(String emailOrgPrincipal);

    int getIdOrganization();

    void setIdOrganization(int id);


    void setIdAddress(int idAddress);

    void setTelephone(long telephone);

    void setEmail(String email);

    void setInformation(String information);

    String getIllness();

    void setName(String name);


    int getIdAddress();

    String getOrganizationType();

    void setIllness(String illness);



    String getEmailOrgPrincipal();




    void setAddress(Address address);

    String getOrgType();

    String getNameOrg();

    void setNameOrg(String nameOrg);
}