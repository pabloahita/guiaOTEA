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
            .Match<IndicatorsEvaluation>(() => indEval, indEval=> (indEval.evaluationType == "COMPLETE" && indEval.totalScore >= 50 && indEval.totalScore < 100) || (indEval.evaluationType == "SIMPLE" && indEval.totalScore >= 30 && indEval.totalScore <= 59));

            Then()
                .Do(ctx => SetImprovable(indEval));
        }


        private static void SetImprovable(IndicatorsEvaluation indEval)
        {
            indEval.level = "IMPROVABLE";
        }
    }
}
