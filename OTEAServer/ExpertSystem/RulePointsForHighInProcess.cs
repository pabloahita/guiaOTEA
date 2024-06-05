using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForHighInProcess : Rule
    {
        private static int regsHighInProcessCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs)
                .Match<List<Indicator>>(() => indicators, ctx => SetRegsHighInProcessCount(indicators, regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }


        private static bool SetRegsHighInProcessCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "HIGH_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsHighInProcessCount = regs.Count(reg => reg.status == "IN_PROCESS" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsHighInProcessCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {
            indicatorsEvaluation.scorePriorityTwoColourYellow = regsHighInProcessCount * 3;
        }
    }
}
