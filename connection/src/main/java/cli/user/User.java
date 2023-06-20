package cli.user;

public interface User {
    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    long getTelephone();

    void setFirstName(String first_name);

    void setLastName(String last_name);

    void setEmail(String email);

    void setPassword(String password);

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
