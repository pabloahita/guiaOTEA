using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForLowReached : Rule
    {
        private static int regsLowReachedCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<Indicator>>(() => indicators)
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs, ctx=>SetRegsLowReachedCount(indicators,regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }

        private static bool SetRegsLowReachedCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "LOW_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsLowReachedCount = regs.Count(reg => reg.status == "REACHED" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsLowReachedCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {

            indicatorsEvaluation.scorePriorityZeroColourGreen = regsLowReachedCount * 2;
        }
    }
}
