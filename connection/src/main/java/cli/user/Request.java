package cli.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model class for registration requests
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 *
 * */
public class Request implements Serializable {

    /**Email of the possible new user*/
    @SerializedName("email")
    public String email;

    /**Request status*/
    @SerializedName("statusReq")
    public int statusReq;

    /**Temporary password to access to the registration*/
    @SerializedName("tempPassword")
    public String tempPassword;

    /**
     * Class constructor
     *
     * @param email - Email of the possible new user
     * @param statusReq - Request status
     * @param tempPassword - Temporary password to access to the registration
     * */
    public Request(String email, int statusReq, String tempPassword) {
        setEmail(email);
        setStatusReq(statusReq);
        setTempPassword(tempPassword);
    }

    /**
     * Method that obtains the email of the possible new user
     *
     * @return Email of the possible new user
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Method that sets the new email of the possible new user
     *
     * @param email - Email of the possible new user
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method that obtains the request status
     *
     * @return Request status
     * */
    public int getStatusReq() {
        return statusReq;
    }

    /**
     * Method that sets the new request status
     *
     * @param statusReq - Request status
     * */
    public void setStatusReq(int statusReq) {
        this.statusReq = statusReq;
    }

    /**
     * Method that obtains the password to access to the registration
     *
     * @return Password to access to the registration
     * */
    public String getTempPassword() {
        return tempPassword;
    }

    /**
     * Method that sets the new password to access to the registration
     *
     * @param tempPassword - Password to access to the registration
     * */
    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }
}
