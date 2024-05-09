using NRules.Fluent.Dsl;
using OTEAServer.Models;
using System.Drawing;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInStart : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg=default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(()=>reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg=>reg.numEvidencesMarked == 0 || reg.numEvidencesMarked == 1);

            Then()
                .Do(_ => reg.setStatus("IN_START"));
        }

    }
}
