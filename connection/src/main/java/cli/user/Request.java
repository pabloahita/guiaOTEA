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

    /**Organization identifier*/
    @SerializedName("idOrganization")
    public int idOrganization;

    /**Organization type*/
    @SerializedName("orgType")
    public String orgType;

    /**Organization illness or syndrome*/
    @SerializedName("illness")
    public String illness;

    /**
     * Class constructor
     *
     * @param email - Email of the possible new user
     * @param statusReq - Request status
     * @param tempPassword - Temporary password to access to the registration
     * */
    public Request(String email, int statusReq, String tempPassword, int idOrganization, String orgType, String illness) {
        setEmail(email);
        setStatusReq(statusReq);
        setTempPassword(tempPassword);
        setIdOrganization(idOrganization);
        setOrgType(orgType);
        setIllness(illness);
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

    /**
     * Method that obtains the organization identifier
     *
     * @return Organization identifier
     * */
    public int getIdOrganization() {
        return idOrganization;
    }

    /**
     * Method that sets the new organization identifier
     *
     * @param idOrganization - Organization identifier
     * */
    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    /**
     * Method that obtains the organization type
     *
     * @return Organization type
     * */
    public String getOrgType() {
        return orgType;
    }

    /**
     * Method that sets the new organization type
     *
     * @param orgType - Organization type
     * */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * Method that obtains the organization identifier
     *
     * @return Organization identifier
     * */
    public String getIllness() {
        return illness;
    }

    /**
     * Method that sets the new organization illness or syndrome
     *
     * @param illness - Organization illness or syndrome
     * */
    public void setIllness(String illness) {
        this.illness = illness;
    }
}
