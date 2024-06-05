using NRules.Fluent.Dsl;
using OTEAServer.Controllers;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RulePointsForFundamentalReached : Rule
    {
        private static int regsFundamentalReachedCount = 0;
        public override void Define()
        {
            List<Indicator> indicators = default;
            List<IndicatorsEvaluationIndicatorReg> regs = default;
            IndicatorsEvaluation indicatorsEvaluation = default;
            When()
                .Match<List<IndicatorsEvaluationIndicatorReg>>(() => regs)
                .Match<List<Indicator>>(() => indicators, ctx => SetRegsFundamentalInProcessCount(indicators, regs))
                .Match<IndicatorsEvaluation>(() => indicatorsEvaluation);
            Then()
                .Do(ctx => calculatePoints(indicatorsEvaluation));
        }


        private static bool SetRegsFundamentalInProcessCount(List<Indicator> indicators, List<IndicatorsEvaluationIndicatorReg> regs)
        {
            if (regs != null && indicators != null)
            {
                var fundamentalIndicators = indicators.Where(indicator => indicator.indicatorPriority == "FUNDAMENTAL_INTEREST").Select(indicator => indicator.idIndicator).ToHashSet();
                regsFundamentalReachedCount = regs.Count(reg => reg.status == "IN_PROCESS" && fundamentalIndicators.Contains(reg.idIndicator));
            }
            return regsFundamentalReachedCount > 0;
        }

        private static void calculatePoints(IndicatorsEvaluation indicatorsEvaluation)
        {
            indicatorsEvaluation.scorePriorityThreeColourGreen = regsFundamentalReachedCount * 5;
        }

    }
}
