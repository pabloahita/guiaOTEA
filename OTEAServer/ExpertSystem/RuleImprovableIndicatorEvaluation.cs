using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleImprovableIndicatorEvaluation : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluation indEval = default;

            When()
            .Match<IndicatorsEvaluation>(() => indEval)
            .Exists<IndicatorsEvaluation>(i => (i.evaluationType == "COMPLETE" && i.totalScore >= 50 && i.totalScore < 100) || (i.evaluationType == "SIMPLE" && i.totalScore >= 30 && i.totalScore <= 59));

            Then()
                .Do(ctx => SetImprovable(indEval));
        }

        private static void SetImprovable(IndicatorsEvaluation indEval)
        {
            indEval.level = "IMPROVABLE";
        }
    }
}
