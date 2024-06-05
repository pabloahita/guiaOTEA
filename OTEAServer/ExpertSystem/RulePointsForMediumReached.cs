using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForMediumReached : Rule
    {

        private static int regsMediumReachedCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<Indicator>>(() => indicators)
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs, ctx=>SetRegsMediumReachedCount(indicators,regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }

        private static bool SetRegsMediumReachedCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "MEDIUM_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsMediumReachedCount = regs.Count(reg => reg.status == "IN_PROCESS" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsMediumReachedCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {
            indicatorsEvaluation.scorePriorityOneColourGreen = regsMediumReachedCount * 3;
        }
    }
}
