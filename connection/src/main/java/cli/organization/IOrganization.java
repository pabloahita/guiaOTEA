package cli.organization;

import cli.organization.data.Address;
import otea.connection.caller.Caller;


public interface IOrganization {
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


    int getIdAddress();

    String getOrganizationType();

    void setIllness(String illness);



    String getEmailOrgPrincipal();


    String getEmailOrgConsultant();


    void setAddress(Address obtainAddress);

    Caller getCaller();

    void setCaller(Caller caller);

    String getOrgType();

    String getNameOrg();

    void setNameOrg(String nameOrg);
}
