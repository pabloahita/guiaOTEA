package cli.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Request implements Serializable {

    @SerializedName("email")
    public String email;

    @SerializedName("statusReq")
    public int statusReq;

    @SerializedName("tempPassword")
    public String tempPassword;

    public Request(String email, int statusReq, String tempPassword) {
        setEmail(email);
        setStatusReq(statusReq);
        setTempPassword(tempPassword);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatusReq() {
        return statusReq;
    }

    public void setStatusReq(int statusReq) {
        this.statusReq = statusReq;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }
}
