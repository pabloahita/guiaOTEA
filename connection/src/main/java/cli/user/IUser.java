package cli.user;

public interface IUser {

    String getFirst_name();

    String getLast_name();

    String getEmailUser();

    long getTelephone();

    void setFirst_name(String first_name);

    void setLast_name(String last_name);

    void setEmailUser(String email);


    void setTelephone(long telephone);

    int getIdOrganization();

    void setIdOrganization(int idOrganization);

    String getOrgType();

    void setOrgType(String orgType);

    String getIllness();

    void setIllness(String illness);

    void connect();

    void disconnect();
}
