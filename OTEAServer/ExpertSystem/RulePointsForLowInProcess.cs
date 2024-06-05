using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForLowInProcess : Rule
    {
        private static int regsLowInProcessCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs)
                .Match<List<Indicator>>(() => indicators, ctx => SetRegsLowInProcessCount(indicators, regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }


        private static bool SetRegsLowInProcessCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "LOW_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsLowInProcessCount = regs.Count(reg => reg.status == "IN_PROCESS" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsLowInProcessCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {
            indicatorsEvaluation.scorePriorityZeroColourYellow = regsLowInProcessCount;
        }
    }
}
