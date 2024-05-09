using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorReached : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 4);

            Then()
                .Do(cx => setStatus(reg, "COMPLETED"));
        }

        public static void setStatus(IndicatorsEvaluationIndicatorReg reg, string status)
        {
            reg.status = status;
        }
    }
}
