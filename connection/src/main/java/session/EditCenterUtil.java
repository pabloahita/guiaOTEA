package session;

import cli.organization.data.Address;
import cli.organization.data.Center;
import otea.connection.controller.AddressesController;

public class EditCenterUtil {

    Center center;

    Address address;

    private static EditCenterUtil instance;

    private EditCenterUtil(Center center){
        setCenter(center);
        setAddress(AddressesController.Get(center.getIdAddress()));
    }

    public static synchronized void createInstance(Center center){
        instance=new EditCenterUtil(center);
    }

    public static synchronized EditCenterUtil getInstance(){
        return instance;
    }

    public static synchronized void removeInstance(){instance=null;}

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
