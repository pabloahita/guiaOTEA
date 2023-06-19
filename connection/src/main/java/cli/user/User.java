package cli.user;

public interface User {
    String getFirstName();

    String getLastName();

    String getEmail();

    String getPassword();

    int getTelephone();

    void setFirstName(String first_name);

    void setLastName(String last_name);

    void setEmail(String email);

    void setPassword(String password);

    void setTelephone(int telephone);

    void connect();

    void disconnect();
}
