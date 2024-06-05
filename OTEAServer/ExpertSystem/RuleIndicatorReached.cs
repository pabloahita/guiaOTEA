using NRules.Fluent.Dsl;
using NRules.RuleModel;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorReached : Rule
    {
        public override void Define()
        {
            List<IndicatorsEvaluationIndicatorReg> regs = default;

            When()
            .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs);

            Then()
                .Do(ctx => SetReached(regs));
        }

        private static void SetReached(List<IndicatorsEvaluationIndicatorReg> regs)
        {
            foreach (var reg in regs) {
                if (reg.numEvidencesMarked == 4)
                {
                    reg.status = "REACHED";
                }
            }
            
        }

    }
}
