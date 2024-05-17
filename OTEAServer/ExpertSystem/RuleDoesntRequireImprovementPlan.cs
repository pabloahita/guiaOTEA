using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleDoesntRequireImprovementPlan : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(() => reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.status == "REACHED");

            Then()
                .Do(ctx => SetNo(reg));
        }

        private static void SetNo(IndicatorsEvaluationIndicatorReg reg)
        {
            reg.requiresImprovementPlan = 0;
        }
    }
}
