using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleGoodIndicatorEvaluation : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluation indEval = default;

            When()
            .Match<IndicatorsEvaluation>(() => indEval)
            .Exists<IndicatorsEvaluation>(i => (i.evaluationType == "COMPLETE" && i.totalScore >= 100 && i.totalScore < 150) || (i.evaluationType == "SIMPLE" && i.totalScore >= 60 && i.totalScore <= 88));

            Then()
                .Do(ctx => SetGood(indEval));
        }

        private static void SetGood(IndicatorsEvaluation indEval)
        {
            indEval.level = "GOOD";
        }
    }
}
