package cli.organization;

import java.util.List;

import cli.indicators.Indicator;

public interface EvaluatedOrganization extends Organization {
    // Common methods for every evaluator organization
    List<Indicator> getIndicators();
}
