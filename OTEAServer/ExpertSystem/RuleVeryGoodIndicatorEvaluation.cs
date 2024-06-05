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
            .Match<IndicatorsEvaluation>(() => indEval, indEval=> (indEval.evaluationType == "COMPLETE" && indEval.totalScore >= 150 && indEval.totalScore < 200) || (indEval.evaluationType == "SIMPLE" && indEval.totalScore >= 89 && indEval.totalScore <= 118));

            Then()
                .Do(ctx => SetVeryGood(indEval));
        }


        private static void SetVeryGood(IndicatorsEvaluation indEval)
        {
            indEval.level = "VERY_GOOD";
        }
    }
}
