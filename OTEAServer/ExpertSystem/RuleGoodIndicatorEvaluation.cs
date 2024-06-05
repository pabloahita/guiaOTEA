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
            .Match<IndicatorsEvaluation>(() => indEval, indEval=> (indEval.evaluationType == "COMPLETE" && indEval.totalScore >= 100 && indEval.totalScore < 150) || (indEval.evaluationType == "SIMPLE" && indEval.totalScore >= 60 && indEval.totalScore <= 88));

            Then()
                .Do(ctx => SetGood(indEval));
        }

        private static void SetGood(IndicatorsEvaluation indEval)
        {
            indEval.level = "GOOD";
        }
    }
}
