using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleVeryImprovableIndicatorEvaluation : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluation indEval = default;

            When()
            .Match<IndicatorsEvaluation>(() => indEval)
            .Exists<IndicatorsEvaluation>(i => (i.evaluationType == "COMPLETE" && i.totalScore <50) || (i.evaluationType == "SIMPLE" && i.totalScore <=29));

            Then()
                .Do(ctx => SetVeryImprovable(indEval));
        }

        private static void SetVeryImprovable(IndicatorsEvaluation indEval)
        {
            indEval.level = "VERY_IMPROVABLE";
        }
    }
}
