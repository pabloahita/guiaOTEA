﻿using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleVeryImprovableIndicatorEvaluation : Rule
    {

        public override void Define()
        {
            IndicatorsEvaluation indEval = default;

            When()
            .Match<IndicatorsEvaluation>(() => indEval, indEval=> (indEval.evaluationType == "COMPLETE" && indEval.totalScore < 50) || (indEval.evaluationType == "SIMPLE" && indEval.totalScore <= 29));

            Then()
                .Do(ctx => SetVeryImprovable(indEval));
        }



        private static void SetVeryImprovable(IndicatorsEvaluation indEval)
        {
            indEval.level = "VERY_IMPROVABLE";
        }
    }
}
