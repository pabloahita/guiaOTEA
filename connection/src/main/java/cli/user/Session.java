package cli.user;

import com.google.gson.annotations.SerializedName;


public class Session {

    @SerializedName("email")
    public String email;

    @SerializedName("sessionToken")
    public String sessionToken;

    public Session(String email, String sessionToken) {
        setEmail(email);
        setSessionToken(sessionToken);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
