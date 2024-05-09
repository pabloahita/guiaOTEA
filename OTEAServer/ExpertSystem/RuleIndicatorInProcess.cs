﻿using NRules.Fluent.Dsl;
using OTEAServer.Models;

namespace OTEAServer.ExpertSystem
{
    public class RuleIndicatorInProcess : Rule
    {
        public override void Define()
        {
            IndicatorsEvaluationIndicatorReg reg = default;

            When()
            .Match<IndicatorsEvaluationIndicatorReg>(() => reg)
            .Exists<IndicatorsEvaluationIndicatorReg>(reg => reg.numEvidencesMarked == 2 || reg.numEvidencesMarked == 3);

            Then()
                .Do(_ => reg.setStatus("IN_PROCESS"));
        }
    }
}
