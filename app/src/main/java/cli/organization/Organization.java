package cli.organization;

import java.util.List;
import cli.indicators.Indicator;
import cli.organization.data.Address;
import connection.ConnectionToLocalDatabase;

public interface Organization {
    String getName();
    Address getAddress();
    int getTelephone();

    String getEmail();
    String getInformation();
    ConnectionToLocalDatabase getConnection();

    int getIdOrganization();

    void setIdOrganization(int id);

    void setConnection(ConnectionToLocalDatabase con);

    String getIllness();
    //Common methods for every organization
}
