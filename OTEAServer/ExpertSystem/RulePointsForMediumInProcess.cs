using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForMediumInProcess : Rule
    {
        private static int regsMediumInProcessCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<Indicator>>(() => indicators)
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs, ctx=>SetRegsMediumInProcessCount(indicators,regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }

        private static bool SetRegsMediumInProcessCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "MEDIUM_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsMediumInProcessCount = regs.Count(reg => reg.status == "IN_PROCESS" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsMediumInProcessCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {
            indicatorsEvaluation.scorePriorityOneColourYellow = regsMediumInProcessCount * 2;
        }
    }
}
