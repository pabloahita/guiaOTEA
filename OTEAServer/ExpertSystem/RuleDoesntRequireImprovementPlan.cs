using NRules.Fluent.Dsl;
using OTEAServer.Models;
using System.Collections.Generic;

namespace OTEAServer.ExpertSystem
{
    public class RuleDoesntRequireImprovementPlan : Rule
    {
        public override void Define()
        {
            List<IndicatorsEvaluationIndicatorReg> regs = null;

            When()
                .Match(() => regs);

            Then()
                .Do(ctx => SetNo(regs));
        }

        private static void SetNo(List<IndicatorsEvaluationIndicatorReg> regs)
        {
            foreach (var reg in regs)
            {
                if (reg.status == "REACHED")
                {
                    reg.requiresImprovementPlan = 0;
                }
                
            }
        }
    }

}
