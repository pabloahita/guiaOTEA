package cli.organization;

import java.sql.Connection;
import java.util.List;
import cli.indicators.Indicator;
import cli.organization.data.Address;
import cli.organization.data.Center;
import connection.ConnectionToLocalDatabase;

public interface Organization {
    String getName();
    Address getAddress();
    int getTelephone();

    String getEmail();
    String getInformation();
    Connection getConnection();
    int getIdOrganization();

    void setIdOrganization(int id);


    String getIllness();
    //Common methods for every organization

    List<Center> getCenterList();

    void setCenterList(List<Center> centerList);

    void addCenter(Center center);

    void removeCenter(Center center);
}
