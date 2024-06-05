using NRules.Fluent.Dsl;
using OTEAServer.Models;
using System.Collections.Generic;

namespace OTEAServer.ExpertSystem
{
    public class RuleRequiresImprovementPlan : Rule
    {
        public override void Define()
        {
            List<IndicatorsEvaluationIndicatorReg> regs = null;

            When()
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs, regs=>regs!=null && regs.Any(reg=>reg.status == "IN_START" || reg.status == "IN_PROCESS"));

            Then()
                .Do(ctx => SetYes(regs));
        }

        private static void SetYes(List<IndicatorsEvaluationIndicatorReg> regs)
        {
            foreach (var reg in regs)
            {
                reg.requiresImprovementPlan = 1;
            }
        }
    }
}

