using NRules.Fluent.Dsl;
using NRules.RuleModel;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInProcess : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(() => reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 2 || reg.numEvidencesMarked == 3);

            Then()
                .Do(_ => reg.SetStatus("IN_PROCESS"));
        }

    }

}
