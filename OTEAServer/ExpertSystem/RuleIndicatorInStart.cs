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
            List<IndicatorsEvaluationIndicatorReg> regs = default;

            When()
            .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs);


            Then()
                .Do(ctx => SetInStart(regs));
        }

        private static void SetInStart(List<IndicatorsEvaluationIndicatorReg> regs)
        {
            foreach (var reg in regs)
            {
                if (reg.numEvidencesMarked == 0 || reg.numEvidencesMarked == 1)
                {
                    reg.status = "IN_START";
                }
            }
                
        }
    }
}

