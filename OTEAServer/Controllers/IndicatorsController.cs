using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Linq.Expressions;
using System.Security.Policy;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for indicators
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    
    
    [ApiController]
    [Route("Indicators")]
    public class IndicatorsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        public IndicatorsController(DatabaseContext context)
        {
            _context = context;
        }

        private class IndicatorDto {
            public int idIndicator { get; set; }
            public string indicatorType { get; set; }
            public int idSubSubAmbit { get; set; }
            public int idSubAmbit { get; set; }
            public int idAmbit { get; set; }
            public string description { get; set; }
            public string indicatorPriority { get; set; }
            public int indicatorVersion { get; set; }
            public int isActive { get; set; }
            public string evaluationType { get; set; }
        }

        private class EvidenceDto
        {
            public int idEvidence { get; set; }
            public int idIndicator { get; set; }
            public string indicatorType { get; set; }
            public int idSubSubAmbit { get; set; }
            public int idSubAmbit { get; set; }
            public int idAmbit { get; set; }
            public string description { get; set; }
            public int evidenceValue { get; set; }
            public int indicatorVersion { get; set; }
            public string evaluationType { get; set; }
        }

        private class AmbitDto
        {
            public int idAmbit { get; set; }
            public string description { get; set; }
        }

        private class SubAmbitDto
        {
            public int idSubAmbit { get; set; }
            public int idAmbit { get; set; }
            public string description { get; set; }
        }

        private class SubSubAmbitDto
        {
            public int idSubSubAmbit { get; set; }
            public int idSubAmbit { get; set; }
            public int idAmbit { get; set; }
            public string description { get; set; }
        }

        /// <summary>
        /// Method that obtains all the indicators
        /// </summary>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicators list</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromQuery] string evaluationType, [FromQuery] string language, [FromHeader] string Authorization)
        {
            try
            {
                List<JsonDocument> allData=new List<JsonDocument>();
                var auxInd = _context.Indicators
                    .Where(i => i.evaluationType == evaluationType && i.isActive == 1)
                    .OrderBy(i => i.idIndicator);
                 

                Expression<Func<Indicator, IndicatorDto>> selectorInd = i => new IndicatorDto
                {
                    idIndicator = i.idIndicator,
                    indicatorType = i.indicatorType,
                    idSubSubAmbit = i.idSubSubAmbit,
                    idSubAmbit = i.idSubAmbit,
                    idAmbit = i.idAmbit,
                    description = i.descriptionEnglish,
                    indicatorPriority = i.indicatorPriority,
                    indicatorVersion = i.indicatorVersion,
                    isActive = i.isActive,
                    evaluationType = i.evaluationType
                };

                switch (language)
                {
                    case "es":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionSpanish,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "fr":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionFrench,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "eu":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionBasque,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "ca":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionCatalan,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "gl":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionGalician,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "pt":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionPortuguese,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "de":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionGerman,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "it":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionItalian,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    case "nl":
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionDutch,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                    default:
                        selectorInd = i => new IndicatorDto
                        {
                            idIndicator = i.idIndicator,
                            indicatorType = i.indicatorType,
                            idSubSubAmbit = i.idSubSubAmbit,
                            idSubAmbit = i.idSubAmbit,
                            idAmbit = i.idAmbit,
                            description = i.descriptionEnglish,
                            indicatorPriority = i.indicatorPriority,
                            indicatorVersion = i.indicatorVersion,
                            isActive = i.isActive,
                            evaluationType = i.evaluationType
                        };
                        break;
                }

                var indicators= auxInd.Select(selectorInd).ToList();

                foreach (IndicatorDto indicator in indicators)
                {                    
                    String ind= "{\"idIndicator\":\"" + indicator.idIndicator + "\"," +
                        "\"indicatorType\":\"" + indicator.indicatorType + "\"," +
                        "\"idSubSubAmbit\":\"" + indicator.idSubSubAmbit + "\"," +
                        "\"idSubAmbit\":\"" + indicator.idSubAmbit + "\"," +
                        "\"idAmbit\":\"" + indicator.idAmbit + "\"," +
                        "\"description\":\"" + indicator.description + "\"," +
                        "\"indicatorPriority\":\"" + indicator.indicatorPriority + "\"," +
                        "\"indicatorVersion\":\"" + indicator.indicatorVersion + "\"," +
                        "\"isActive\":\"" + indicator.indicatorVersion + "\"," +
                        "\"evaluationType\":\"" + indicator.evaluationType + "\"}";
                    allData.Add(JsonDocument.Parse(ind));
                }

                int tamEvidences = 0;

                if (evaluationType == "COMPLETE") {
                    var auxEv = _context.Evidences
                        .Where(e => e.evaluationType == evaluationType)
                        .OrderBy(e => e.idIndicator)
                        .ThenBy(e => e.idEvidence);

                    Expression<Func<OTEAServer.Models.Evidence,EvidenceDto>> selectorEv = e => new EvidenceDto
                    {
                        idEvidence=e.idEvidence,
                        idIndicator = e.idIndicator,
                        indicatorType = e.indicatorType,
                        idSubSubAmbit = e.idSubSubAmbit,
                        idSubAmbit = e.idSubAmbit,
                        idAmbit = e.idAmbit,
                        description = e.descriptionEnglish,
                        evidenceValue = e.evidenceValue,
                        indicatorVersion = e.indicatorVersion,
                        evaluationType = e.evaluationType
                    };

                    switch (language)
                    {
                        case "es":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionSpanish,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "fr":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionFrench,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "eu":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionBasque,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "ca":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionCatalan,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "gl":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionGalician,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "pt":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionPortuguese,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "de":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionGerman,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "it":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionItalian,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        case "nl":
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionDutch,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                        default:
                            selectorEv = e => new EvidenceDto
                            {
                                idEvidence = e.idEvidence,
                                idIndicator = e.idIndicator,
                                indicatorType = e.indicatorType,
                                idSubSubAmbit = e.idSubSubAmbit,
                                idSubAmbit = e.idSubAmbit,
                                idAmbit = e.idAmbit,
                                description = e.descriptionEnglish,
                                evidenceValue = e.evidenceValue,
                                indicatorVersion = e.indicatorVersion,
                                evaluationType = e.evaluationType
                            };
                            break;
                    }

                    var evidences=auxEv.Select(selectorEv).ToList();
                    foreach (EvidenceDto evidence in evidences)
                    {
                        String ev = "{\"idEvidence\":\"" + evidence.idEvidence + "\"," +
                                "\"idIndicator\":\"" + evidence.idIndicator + "\"," +
                                "\"indicatorType\":\"" + evidence.indicatorType + "\"," +
                                "\"idSubSubAmbit\":\"" + evidence.idSubSubAmbit + "\"," +
                                "\"idSubAmbit\":\"" + evidence.idSubAmbit + "\"," +
                                "\"idAmbit\":\"" + evidence.idAmbit + "\"," +
                                "\"description\":\"" + evidence.description + "\"," +
                                "\"evidenceValue\":\"" + evidence.evidenceValue + "\"," +
                                "\"indicatorVersion\":\"" + evidence.indicatorVersion + "\"," +
                                "\"evaluationType\":\"" + evidence.evaluationType + "\"}";
                        allData.Add(JsonDocument.Parse(ev));
                    }
                    tamEvidences = evidences.Count;
                }

                Expression<Func<Ambit, AmbitDto>> selectorAmbit = a => new AmbitDto
                {
                    idAmbit = a.idAmbit,
                    description = a.descriptionEnglish
                };


                switch (language)
                {
                    case "es":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionSpanish
                        };
                        break;
                    case "fr":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionFrench
                        };
                        break;
                    case "eu":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionBasque
                        };
                        break;
                    case "ca":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionCatalan
                        };
                        break;
                    case "gl":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionGalician
                        };
                        break;
                    case "pt":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionPortuguese
                        };
                        break;
                    case "de":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionGerman
                        };
                        break;
                    case "it":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionItalian
                        };
                        break;
                    case "nl":
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionDutch
                        };
                        break;
                    default:
                        selectorAmbit = a => new AmbitDto
                        {
                            idAmbit = a.idAmbit,
                            description = a.descriptionEnglish
                        };
                        break;
                }

                var ambits = _context.Ambits.Select(selectorAmbit).ToList();
                foreach (AmbitDto ambit in ambits) {
                    String amb= "{\"idAmbit\":\"" + ambit.idAmbit + "\"," +
                            "\"description\":\"" + ambit.description + "\"}";
                    allData.Add(JsonDocument.Parse(amb));
                }

                Expression<Func<SubAmbit, SubAmbitDto>> selectorSubAmbit = sa => new SubAmbitDto
                {
                    idSubAmbit = sa.idSubAmbit,
                    idAmbit = sa.idAmbit,
                    description = sa.descriptionEnglish
                };

                switch (language)
                {
                    case "es":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionSpanish
                        };
                        break;
                    case "fr":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionFrench
                        };
                        break;
                    case "eu":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionBasque
                        };
                        break;
                    case "ca":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionCatalan
                        };
                        break;
                    case "gl":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionGalician
                        };
                        break;
                    case "pt":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionPortuguese
                        };
                        break;
                    case "de":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionGerman
                        };
                        break;
                    case "it":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionItalian
                        };
                        break;
                    case "nl":
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionDutch
                        };
                        break;
                    default:
                        selectorSubAmbit = sa => new SubAmbitDto
                        {
                            idSubAmbit = sa.idSubAmbit,
                            idAmbit = sa.idAmbit,
                            description = sa.descriptionEnglish
                        };
                        break;
                }

                var subAmbits = _context.SubAmbits.Select(selectorSubAmbit).ToList();
                foreach (SubAmbitDto subAmbit in subAmbits) {
                    String subAmb = "{\"idSubAmbit\":\"" + subAmbit.idSubAmbit + "\"," +
                                "\"idAmbit\":\"" + subAmbit.idAmbit + "\"," +
                                "\"description\":\"" + subAmbit.description + "\"}";
                    allData.Add(JsonDocument.Parse(subAmb));
                }



                Expression<Func<SubSubAmbit, SubSubAmbitDto>> selectorSubSubAmbit = ssa => new SubSubAmbitDto
                {
                    idSubSubAmbit = ssa.idSubSubAmbit,
                    idSubAmbit = ssa.idSubAmbit,
                    idAmbit = ssa.idAmbit,
                    description = ssa.descriptionEnglish
                };

                switch (language)
                {
                    case "es":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionSpanish
                        };
                        break;
                    case "fr":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionFrench
                        };
                        break;
                    case "eu":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionBasque
                        };
                        break;
                    case "ca":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionCatalan
                        };
                        break;
                    case "gl":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionGalician
                        };
                        break;
                    case "pt":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionPortuguese
                        };
                        break;
                    case "de":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionGerman
                        };
                        break;
                    case "it":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionItalian
                        };
                        break;
                    case "nl":
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionDutch
                        };
                        break;
                    default:
                        selectorSubSubAmbit = ssa => new SubSubAmbitDto
                        {
                            idSubSubAmbit = ssa.idSubSubAmbit,
                            idSubAmbit = ssa.idSubAmbit,
                            idAmbit = ssa.idAmbit,
                            description = ssa.descriptionEnglish
                        };
                        break;
                }

                var subSubAmbits = _context.SubSubAmbits.Select(selectorSubSubAmbit).ToList();
                foreach (var subSubAmbit in subSubAmbits) {
                    String subSubAmb = "{\"idSubSubAmbit\":\"" + subSubAmbit.idSubSubAmbit + "\"," +
                                "\"idSubAmbit\":\"" + subSubAmbit.idSubAmbit + "\"," +
                                "\"idAmbit\":\"" + subSubAmbit.idAmbit + "\"," +
                                "\"description\":\"" + subSubAmbit.description + "\"}";
                    allData.Add(JsonDocument.Parse(subSubAmb));
                }
                String tams = "{\"numIndicators\":\"" + indicators.Count + "\",";
                if (evaluationType == "COMPLETE") {
                    tams += "\"numEvidences\":\"" + tamEvidences + "\",";
                }
                 tams+="\"numAmbits\":\"" + ambits.Count + "\"," +
                        "\"numSubAmbits\":\"" + subAmbits.Count + "\"," +
                        "\"numSubSubAmbits\":\"" + subSubAmbits.Count + "\"}";
                allData.Add(JsonDocument.Parse(tams));
                return Ok(allData);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains all the indicators of an ambit
        /// </summary>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <returns>Indicators list</returns>
        [HttpGet("ambit")]
        [Authorize]
        public IActionResult GetAllByType([FromQuery] int idAmbit, [FromHeader] string Authorization)
        {
            try
            {
                var indicators = _context.Indicators.Where(i => i.idAmbit == idAmbit && i.isActive == 1).ToList();
                return Ok(indicators);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains an indicator from the database
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicator if success, null if not</returns>

        [HttpGet("get")]
        [Authorize]
        public ActionResult<Indicator> Get([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromHeader] string Authorization)
        {
            try
            {
                var indicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == idIndicator && i.indicatorType == indicatorType && i.idSubSubAmbit == idSubSubAmbit && i.idSubAmbit == idSubAmbit && i.idAmbit == idAmbit && i.indicatorVersion == indicatorVersion && i.evaluationType==evaluationType);

                if (indicator == null)
                    return NotFound();

                return indicator;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }



        /// <summary>
        /// Method that creates an indicator
        /// </summary>
        /// <param name="indicator">Indicator</param>
        /// <returns>Indicator if success, null if not</returns>
        [HttpPost]
        [Authorize(Policy = "Administrator")]
        public IActionResult Create([FromBody] Indicator indicator, [FromHeader] string Authorization)
        {
            try
            {
                _context.Indicators.Add(indicator);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { id = indicator.idIndicator, type = indicator.indicatorType, idSubSubAmbit = indicator.idSubSubAmbit, idSubAmbit = indicator.idSubAmbit, idAmbit = indicator.idAmbit, version = indicator.indicatorVersion, evaluationType =indicator.evaluationType }, indicator);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an indicator
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <param name="indicator">Indicator</param>
        /// <returns>Indicator if success, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Administrator")]
        public IActionResult Update([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromBody] Indicator indicator, [FromHeader] string Authorization)
        {
            try
            {
                if (idIndicator != indicator.idIndicator || indicatorType != indicator.indicatorType || indicatorVersion != indicator.indicatorVersion - 1 || idSubSubAmbit != indicator.idSubSubAmbit || idSubAmbit != indicator.idSubAmbit || idAmbit != indicator.idAmbit || evaluationType!=indicator.evaluationType)
                    return BadRequest();
                var existingIndicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == idIndicator && i.indicatorType == indicatorType && i.idSubSubAmbit == idSubSubAmbit && i.idSubAmbit == idSubAmbit && i.idAmbit == idAmbit && i.indicatorVersion == indicatorVersion && i.evaluationType == evaluationType);
                if (existingIndicator is null)
                    return NotFound();

                _context.Indicators.Add(indicator);
                existingIndicator.isActive = 0;
                _context.SaveChanges();

                return Ok(indicator);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an indicator
        /// </summary>
        /// <param name="idIndicator">Indicator identifier</param>
        /// <param name="indicatorType">Indicator type</param>
        /// <param name="idSubSubAmbit">Second level division of the ambit</param>
        /// <param name="idSubAmbit">First level division of the ambit</param>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="indicatorVersion">Indicator version</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicator if success, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Administrator")]
        public IActionResult Delete([FromQuery] int idIndicator, [FromQuery] string indicatorType, [FromQuery] int idSubSubAmbit, [FromQuery] int idSubAmbit, [FromQuery] int idAmbit, [FromQuery] int indicatorVersion, [FromQuery] string evaluationType, [FromHeader] string Authorization)
        {
            try
            {
                var indicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == idIndicator && i.indicatorType == indicatorType && i.idSubSubAmbit == idSubSubAmbit && i.idSubAmbit == idSubAmbit && i.idAmbit == idAmbit && i.indicatorVersion == indicatorVersion && i.evaluationType == evaluationType);

                if (indicator is null)
                    return NotFound();

                _context.Indicators.Remove(indicator);
                _context.SaveChanges();
                return NoContent();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
