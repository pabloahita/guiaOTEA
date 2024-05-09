using NRules.Fluent.Dsl;
using OTEAServer.Models;
using System.Drawing;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInStart : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 0 || reg.numEvidencesMarked == 1);

            Then()
                .Do(cx => setStatus(reg, "IN_START"));
        }

        public static void setStatus(IndicatorsEvaluationIndicatorReg reg, string status)
        {
            reg.status = status;
        }
    }
}
