using NRules.Fluent.Dsl;
using NRules.RuleModel;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInProcess : Rule
    {
        public override void Define()
        {
            List<IndicatorsEvaluationIndicatorReg> regs = default;

            When()
            .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs);

            Then()
                .Do(ctx => SetInProcess(regs));
        }

        private static void SetInProcess(List<IndicatorsEvaluationIndicatorReg> regs)
        {
            foreach(var reg in regs) {
                if(reg.numEvidencesMarked == 2 || reg.numEvidencesMarked == 3) { 
                    reg.status = "IN_PROCESS";
                }
            }
            
        }

    }

}
