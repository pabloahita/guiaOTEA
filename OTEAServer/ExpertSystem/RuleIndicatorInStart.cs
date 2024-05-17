using NRules.Fluent.Dsl;
using NRules.RuleModel;
using OTEAServer.Models;
using System.Drawing;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInStart : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(() => reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 0 || reg.numEvidencesMarked == 1);

            Then()
                .Do(ctx => SetInStart(reg));
        }

        private static void SetInStart(IndicatorsEvaluationIndicatorReg reg)
        {
            reg.status = "IN_START";
        }


    }
}
