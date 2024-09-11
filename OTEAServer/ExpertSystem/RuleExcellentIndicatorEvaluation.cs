using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleExcellentIndicatorEvaluation : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluation indEval = default;

            When()
            .Match<IndicatorsEvaluation>(() => indEval, indEval => (indEval.evaluationType == "COMPLETE" && indEval.totalScore >= 200) || (indEval.evaluationType == "SIMPLE" && indEval.totalScore >= 118));

            Then()
                .Do(ctx=>SetExcellent(indEval));
        }

        private static void SetExcellent(IndicatorsEvaluation indEval) {
            indEval.level = "EXCELLENT";
        }
    }
}
