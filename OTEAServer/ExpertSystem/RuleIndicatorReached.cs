using NRules.Fluent.Dsl;
using NRules.RuleModel;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorReached : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(() => reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 4);

            Then()
                .Do(ctx => SetReached(reg));
        }

        private static void SetReached(IndicatorsEvaluationIndicatorReg reg)
        {
            reg.status = "REACHED";
        }

    }
}
