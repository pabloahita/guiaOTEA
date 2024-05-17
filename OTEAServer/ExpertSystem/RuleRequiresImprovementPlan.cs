using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleRequiresImprovementPlan : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(() => reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.status=="IN_START" || reg.status=="IN_PROCESS");

            Then()
                .Do(ctx => SetYes(reg));
        }

        private static void SetYes(IndicatorsEvaluationIndicatorReg reg)
        {
            reg.requiresImprovementPlan = 1;
        }
    }
}
