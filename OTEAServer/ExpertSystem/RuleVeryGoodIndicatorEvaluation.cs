using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleVeryGoodIndicatorEvaluation : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluation indEval = default;

            When()
            .Match<IndicatorsEvaluation>(() => indEval)
            .Exists<IndicatorsEvaluation>(i => (i.evaluationType == "COMPLETE" && i.totalScore >= 150 && i.totalScore < 200) || (i.evaluationType == "SIMPLE" && i.totalScore >= 89 && i.totalScore <= 118));

            Then()
                .Do(ctx => SetVeryGood(indEval));
        }

        private static void SetVeryGood(IndicatorsEvaluation indEval)
        {
            indEval.level = "VERY_GOOD";
        }
    }
}
