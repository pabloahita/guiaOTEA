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
            .Match<IndicatorsEvaluation>(() => indEval)
            .Exists<IndicatorsEvaluation>(i => (i.evaluationType=="COMPLETE" && i.totalScore>=200) || (i.evaluationType=="SIMPLE" && i.totalScore>=143));

            Then()
                .Do(ctx=>SetExcellent(indEval));
        }

        private static void SetExcellent(IndicatorsEvaluation indEval) {
            indEval.level = "EXCELLENT";
        }
    }
}
