package cli.organization;

import cli.organization.data.Address;


public interface IOrganization {
    void setOrgType(String orgType);

    String getName();
    Address getAddress();
    long getTelephone();

    String getEmail();

    void setEmailOrgPrincipal(String emailOrgPrincipal);

    int getIdOrganization();

    void setIdOrganization(int id);


    void setIdAddress(int idAddress);

    void setTelephone(long telephone);

    void setEmail(String email);

    String getInformationEnglish();

    void setInformationEnglish(String informationEnglish);

    String getInformationSpanish();

    void setInformationSpanish(String informationSpanish);

    String getInformationFrench();

    void setInformationFrench(String informationFrench);

    String getInformationBasque();

    void setInformationBasque(String informationBasque);

    String getInformationCatalan();

    void setInformationCatalan(String informationCatalan);

    String getInformationDutch();

    void setInformationDutch(String informationDutch);

    String getInformationGalician();

    void setInformationGalician(String informationGalician);

    String getInformationGerman();

    void setInformationGerman(String informationGerman);

    String getInformationItalian();

    void setInformationItalian(String informationItalian);

    String getInformationPortuguese();

    void setInformationPortuguese(String informationPortuguese);

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
