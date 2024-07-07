using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForLowReached : Rule
    {
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs)
                .Match<List<Indicator>>(() => indicators)
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation, indicators, regs));
        }


        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation, List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            int count = 0;
            for (int i = 0; i < regs.Count; i++)
            {
                if (indicators[i].indicatorPriority == "LOW_INTEREST" && regs[i].status == "REACHED")
                {
                    count++;
                }
            }
            indicatorsEvaluation.scorePriorityZeroColourGreen = count * 2;
        }
    }
}
