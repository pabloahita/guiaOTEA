package cli.user;

import otea.connection.caller.Caller;

public interface IUser {
    Caller getCaller();

    void setCaller(Caller caller);

    String getFirst_name();

    String getLast_name();

    String getEmailUser();

    String getPasswordUser();

    long getTelephone();

    void setFirst_name(String first_name);

    void setLast_name(String last_name);

    void setEmailUser(String email);

    void setPasswordUser(String password);

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
