using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInProcess : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 2 || reg.numEvidencesMarked == 3);

            Then()
                .Do(cx => setStatus(reg,"IN_PROCESS"));
        }

        public static void setStatus(IndicatorsEvaluationIndicatorReg reg, string status) {
            reg.status = status;
        }
    }
}
