using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForHighReached : Rule
    {
        private static int regsHighReachedCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs)
                .Match<List<Indicator>>(() => indicators, ctx => SetRegsHighReachedCount(indicators, regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }


        private static bool SetRegsHighReachedCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "HIGH_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsHighReachedCount = regs.Count(reg => reg.status == "REACHED" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsHighReachedCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {
            indicatorsEvaluation.scorePriorityTwoColourGreen = regsHighReachedCount * 4;
        }
    }
}
