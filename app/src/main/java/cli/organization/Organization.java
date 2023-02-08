package cli.organization;

import java.util.List;
import cli.indicators.Indicator;
import cli.organization.data.Address;

public interface Organization {
    String getName();
    Address getAddress();
    int getTelephone();

    String getEmail();
    String getInformation();

    //Common methods for every organization
}
