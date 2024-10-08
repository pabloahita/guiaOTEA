using GTranslate;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Diagnostics.HealthChecks;
using NRules;
using NRules.Fluent;
using OTEAServer.ExpertSystem;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Linq.Expressions;
using System.Runtime.Intrinsics.X86;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller for indicators evaluations operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("IndicatorsEvaluations")]
    public class IndicatorsEvaluationsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public IndicatorsEvaluationsController(DatabaseContext context)
        {
            _context=context;
        }

        private class IndicatorsEvaluationDto {
            public long evaluationDate { get; set; }
            public int idEvaluatedOrganization { get; set; }
            public string orgTypeEvaluated { get; set; }
            public int idEvaluatorTeam { get; set; }
            public int idEvaluatorOrganization { get; set; }
            public string orgTypeEvaluator { get; set; }
            public string illness { get; set; }
            public int idCenter { get; set; }
            public int scorePriorityZeroColourRed { get; set; }
            public int scorePriorityZeroColourYellow { get; set; }
            public int scorePriorityZeroColourGreen { get; set; }
            public int scorePriorityOneColourRed { get; set; }
            public int scorePriorityOneColourYellow { get; set; }
            public int scorePriorityOneColourGreen { get; set; }
            public int scorePriorityTwoColourRed { get; set; }
            public int scorePriorityTwoColourYellow { get; set; }
            public int scorePriorityTwoColourGreen { get; set; }
            public int scorePriorityThreeColourRed { get; set; }
            public int scorePriorityThreeColourYellow { get; set; }
            public int scorePriorityThreeColourGreen { get; set; }
            public int totalScore { get; set; }
            public string conclusions { get; set; }
            public int isFinished { get; set; }
            public string evaluationType { get; set; }
            public string level { get; set; }

        }

        private class IndicatorsEvaluationIndicatorRegDto
        {
            public int idSubSubAmbit;
            public int idSubAmbit;
            public int idAmbit;
            public int idIndicator;
            public int indicatorVersion;
            public string observations;
            public int numEvidencesMarked;
            public string status;
            public int requiresImprovementPlan;
        
        }

        private class IndicatorsEvaluationEvidenceRegDto {
            public int idSubSubAmbit;
            public int idSubAmbit;
            public int idAmbit;
            public int idIndicator;
            public string indicatorType;
            public int idEvidence;
            public int indicatorVersion;
            public int isMarked;
            public string observations;
        }

        private class IndicatorsEvaluationSimpleEvidenceRegDto {

            public int idSubSubAmbit;
            public int idSubAmbit;
            public int idAmbit;
            public int idIndicator;
            public int idEvidence;
            public string description;
            public int indicatorVersion;
            public string observations;
        }


        /// <summary>
        /// Method that obtains all indicators evaluations
        /// </summary>
        /// <returns>Indicators evaluations list</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromHeader] string Authorization)
        {
            try
            {
                var indicatorsEvaluations = _context.IndicatorsEvaluations.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var indicatorsEvaluation in indicatorsEvaluations)
                {
                    String rg = "{\"evaluationDate\":\"" + indicatorsEvaluation.evaluationDate + "\"," +
                                "\"idEvaluatedOrganization\":\"" + indicatorsEvaluation.idEvaluatedOrganization + "\"," +
                                "\"orgTypeEvaluated\":\"" + indicatorsEvaluation.orgTypeEvaluated + "\"," +
                                "\"idEvaluatorTeam\":\"" + indicatorsEvaluation.idEvaluatorTeam + "\"," +
                                "\"idEvaluatorOrganization\":\"" + indicatorsEvaluation.idEvaluatorOrganization + "\"," +
                                "\"orgTypeEvaluator\":\"" + indicatorsEvaluation.orgTypeEvaluator + "\"," +
                                "\"illness\":\"" + indicatorsEvaluation.illness + "\"," +
                                "\"idCenter\":\"" + indicatorsEvaluation.idCenter + "\"," +
                                "\"conclusionsSpanish\":\"" + indicatorsEvaluation.conclusionsSpanish + "\"," +
                                "\"conclusionsEnglish\":\"" + indicatorsEvaluation.conclusionsEnglish + "\"," +
                                "\"conclusionsFrench\":\"" + indicatorsEvaluation.conclusionsFrench + "\"," +
                                "\"conclusionsBasque\":\"" + indicatorsEvaluation.conclusionsBasque + "\"," +
                                "\"conclusionsCatalan\":\"" + indicatorsEvaluation.conclusionsCatalan + "\"," +
                                "\"conclusionsDutch\":\"" + indicatorsEvaluation.conclusionsDutch + "\"," +
                                "\"conclusionsGalician\":\"" + indicatorsEvaluation.conclusionsGalician + "\"," +
                                "\"conclusionsGerman\":\"" + indicatorsEvaluation.conclusionsGerman + "\"," +
                                "\"conclusionsItalian\":\"" + indicatorsEvaluation.conclusionsItalian + "\"," +
                                "\"conclusionsPortuguese\":\"" + indicatorsEvaluation.conclusionsPortuguese + "\"," +
                                "\"scorePriorityZeroColourRed\":\"" + indicatorsEvaluation.scorePriorityZeroColourRed + "\"," +
                                "\"scorePriorityZeroColourYellow\":\"" + indicatorsEvaluation.scorePriorityZeroColourYellow + "\"," +
                                "\"scorePriorityZeroColourGreen\":\"" + indicatorsEvaluation.scorePriorityZeroColourGreen + "\"," +
                                "\"scorePriorityOneColourRed\":\"" + indicatorsEvaluation.scorePriorityOneColourRed + "\"," +
                                "\"scorePriorityOneColourYellow\":\"" + indicatorsEvaluation.scorePriorityOneColourYellow + "\"," +
                                "\"scorePriorityOneColourGreen\":\"" + indicatorsEvaluation.scorePriorityOneColourGreen + "\"," +
                                "\"scorePriorityTwoColourRed\":\"" + indicatorsEvaluation.scorePriorityTwoColourRed + "\"," +
                                "\"scorePriorityTwoColourYellow\":\"" + indicatorsEvaluation.scorePriorityTwoColourYellow + "\"," +
                                "\"scorePriorityTwoColourGreen\":\"" + indicatorsEvaluation.scorePriorityTwoColourGreen + "\"," +
                                "\"scorePriorityThreeColourRed\":\"" + indicatorsEvaluation.scorePriorityThreeColourRed + "\"," +
                                "\"scorePriorityThreeColourYellow\":\"" + indicatorsEvaluation.scorePriorityThreeColourYellow + "\"," +
                                "\"scorePriorityThreeColourGreen\":\"" + indicatorsEvaluation.scorePriorityThreeColourGreen + "\"," +
                                "\"totalScore\":\"" + indicatorsEvaluation.totalScore + "\"," +
                                "\"isFinished\":\"" + indicatorsEvaluation.isFinished + "\"," +
                                "\"evaluationType\":\"" + indicatorsEvaluation.evaluationType + "\"," +
                                "\"level\":\"" + indicatorsEvaluation.level + "\"}";

                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="idCenter">Center identifier</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <returns>Indicators evaluations list</returns>
        [HttpGet("evaluatorTeam")]
        [Authorize]
        public IActionResult GetAllByEvaluatorTeam([FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness, [FromQuery] string language, [FromHeader] string Authorization)
        {
            try
            {
                var aux = _context.IndicatorsEvaluations.Where(e => e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);//.ToList();
                Expression<Func<IndicatorsEvaluation, IndicatorsEvaluationDto>> selector = ie => new IndicatorsEvaluationDto
                {
                    evaluationDate = ie.evaluationDate,
                    idEvaluatedOrganization = ie.idEvaluatedOrganization,

                    orgTypeEvaluated = ie.orgTypeEvaluated,
                    idEvaluatorTeam = ie.idEvaluatorTeam,
                    idEvaluatorOrganization = ie.idEvaluatorOrganization,

                    orgTypeEvaluator = ie.orgTypeEvaluator,

                    illness = ie.illness,
                    idCenter = ie.idCenter,
                    scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                    scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                    scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                    scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                    scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                    scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                    scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                    scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                    scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                    scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                    scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                    scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                    totalScore = ie.totalScore,
                    conclusions = ie.conclusionsEnglish,
                    isFinished = ie.isFinished,
                    evaluationType = ie.evaluationType,
                    level = ie.level
                };
                switch (language)
                {
                    case "es":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsSpanish,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "fr":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsFrench,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "eu":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsBasque,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "ca":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsCatalan,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "gl":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsGalician,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "pt":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsPortuguese,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "de":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsGerman,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "it":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsItalian,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "nl":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsDutch,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    default:
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsEnglish,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                }
                var indicatorsEvaluations=aux.Select(selector).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var indicatorsEvaluation in indicatorsEvaluations)
                {
                    String rg = "{\"evaluationDate\":\"" + indicatorsEvaluation.evaluationDate + "\"," +
                                "\"idEvaluatedOrganization\":\"" + idEvaluatedOrganization + "\"," +
                                "\"orgTypeEvaluated\":\"" + orgTypeEvaluated + "\"," +
                                "\"idEvaluatorTeam\":\"" + idEvaluatorTeam + "\"," +
                                "\"idEvaluatorOrganization\":\"" + idEvaluatorOrganization + "\"," +
                                "\"orgTypeEvaluator\":\"" + orgTypeEvaluator + "\"," +
                                "\"illness\":\"" + illness + "\"," +
                                "\"idCenter\":\"" + idCenter + "\"," +
                                "\"conclusions\":\"" + indicatorsEvaluation.conclusions + "\"," +
                                "\"scorePriorityZeroColourRed\":\"" + indicatorsEvaluation.scorePriorityZeroColourRed + "\"," +
                                "\"scorePriorityZeroColourYellow\":\"" + indicatorsEvaluation.scorePriorityZeroColourYellow + "\"," +
                                "\"scorePriorityZeroColourGreen\":\"" + indicatorsEvaluation.scorePriorityZeroColourGreen + "\"," +
                                "\"scorePriorityOneColourRed\":\"" + indicatorsEvaluation.scorePriorityOneColourRed + "\"," +
                                "\"scorePriorityOneColourYellow\":\"" + indicatorsEvaluation.scorePriorityOneColourYellow + "\"," +
                                "\"scorePriorityOneColourGreen\":\"" + indicatorsEvaluation.scorePriorityOneColourGreen + "\"," +
                                "\"scorePriorityTwoColourRed\":\"" + indicatorsEvaluation.scorePriorityTwoColourRed + "\"," +
                                "\"scorePriorityTwoColourYellow\":\"" + indicatorsEvaluation.scorePriorityTwoColourYellow + "\"," +
                                "\"scorePriorityTwoColourGreen\":\"" + indicatorsEvaluation.scorePriorityTwoColourGreen + "\"," +
                                "\"scorePriorityThreeColourRed\":\"" + indicatorsEvaluation.scorePriorityThreeColourRed + "\"," +
                                "\"scorePriorityThreeColourYellow\":\"" + indicatorsEvaluation.scorePriorityThreeColourYellow + "\"," +
                                "\"scorePriorityThreeColourGreen\":\"" + indicatorsEvaluation.scorePriorityThreeColourGreen + "\"," +
                                "\"totalScore\":\"" + indicatorsEvaluation.totalScore + "\"," +
                                "\"isFinished\":\"" + indicatorsEvaluation.isFinished + "\"," +
                                "\"evaluationType\":\"" + indicatorsEvaluation.evaluationType + "\"," +
                                "\"level\":\"" + indicatorsEvaluation.level + "\"}";

                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that obtains all the non finished indicators evaluations of a center
        /// </summary>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier</param>
        /// <returns>Indicators evaluations list</returns>
        [HttpGet("nonFinished")]
        [Authorize]
        public IActionResult GetNonFinishedByEvaluatorTeam([FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string language, [FromHeader] string Authorization)
        {
            try
            {
                var aux = _context.IndicatorsEvaluations.Where(e => e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.isFinished == 0);//.ToList();
                Expression<Func<IndicatorsEvaluation, IndicatorsEvaluationDto>> selector = ie => new IndicatorsEvaluationDto
                {
                    evaluationDate = ie.evaluationDate,
                    idEvaluatedOrganization = ie.idEvaluatedOrganization,

                    orgTypeEvaluated = ie.orgTypeEvaluated,
                    idEvaluatorTeam = ie.idEvaluatorTeam,
                    idEvaluatorOrganization = ie.idEvaluatorOrganization,

                    orgTypeEvaluator = ie.orgTypeEvaluator,

                    illness = ie.illness,
                    idCenter = ie.idCenter,
                    scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                    scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                    scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                    scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                    scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                    scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                    scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                    scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                    scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                    scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                    scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                    scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                    totalScore = ie.totalScore,
                    conclusions = ie.conclusionsEnglish,
                    isFinished = ie.isFinished,
                    evaluationType = ie.evaluationType,
                    level = ie.level
                };

                switch (language)
                {
                    case "es":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsSpanish,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "fr":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsFrench,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "eu":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsBasque,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "ca":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsCatalan,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "gl":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsGalician,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "pt":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsPortuguese,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "de":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsGerman,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "it":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsItalian,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    case "nl":
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsDutch,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                    default:
                        selector = ie => new IndicatorsEvaluationDto
                        {
                            evaluationDate = ie.evaluationDate,
                            idEvaluatedOrganization = ie.idEvaluatedOrganization,

                            orgTypeEvaluated = ie.orgTypeEvaluated,
                            idEvaluatorTeam = ie.idEvaluatorTeam,
                            idEvaluatorOrganization = ie.idEvaluatorOrganization,

                            orgTypeEvaluator = ie.orgTypeEvaluator,

                            illness = ie.illness,
                            idCenter = ie.idCenter,
                            scorePriorityZeroColourRed = ie.scorePriorityZeroColourRed,
                            scorePriorityZeroColourYellow = ie.scorePriorityZeroColourYellow,
                            scorePriorityZeroColourGreen = ie.scorePriorityZeroColourGreen,
                            scorePriorityOneColourRed = ie.scorePriorityOneColourRed,
                            scorePriorityOneColourYellow = ie.scorePriorityOneColourYellow,
                            scorePriorityOneColourGreen = ie.scorePriorityOneColourGreen,
                            scorePriorityTwoColourRed = ie.scorePriorityTwoColourRed,
                            scorePriorityTwoColourYellow = ie.scorePriorityTwoColourYellow,
                            scorePriorityTwoColourGreen = ie.scorePriorityTwoColourGreen,
                            scorePriorityThreeColourRed = ie.scorePriorityThreeColourRed,
                            scorePriorityThreeColourYellow = ie.scorePriorityThreeColourYellow,
                            scorePriorityThreeColourGreen = ie.scorePriorityThreeColourGreen,
                            totalScore = ie.totalScore,
                            conclusions = ie.conclusionsEnglish,
                            isFinished = ie.isFinished,
                            evaluationType = ie.evaluationType,
                            level = ie.level
                        };
                        break;
                }
                var indicatorsEvaluations = aux.Select(selector).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var indicatorsEvaluation in indicatorsEvaluations)
                {
                    String rg = "{\"evaluationDate\":\"" + indicatorsEvaluation.evaluationDate + "\"," +
                                "\"idEvaluatedOrganization\":\"" + idEvaluatedOrganization + "\"," +
                                "\"orgTypeEvaluated\":\"" + orgTypeEvaluated + "\"," +
                                "\"idEvaluatorTeam\":\"" + idEvaluatorTeam + "\"," +
                                "\"idEvaluatorOrganization\":\"" + idEvaluatorOrganization + "\"," +
                                "\"orgTypeEvaluator\":\"" + orgTypeEvaluator + "\"," +
                                "\"illness\":\"" + illness + "\"," +
                                "\"idCenter\":\"" + idCenter + "\"," +
                                "\"conclusions\":\"" + indicatorsEvaluation.conclusions + "\"," +
                                "\"scorePriorityZeroColourRed\":\"" + indicatorsEvaluation.scorePriorityZeroColourRed + "\"," +
                                "\"scorePriorityZeroColourYellow\":\"" + indicatorsEvaluation.scorePriorityZeroColourYellow + "\"," +
                                "\"scorePriorityZeroColourGreen\":\"" + indicatorsEvaluation.scorePriorityZeroColourGreen + "\"," +
                                "\"scorePriorityOneColourRed\":\"" + indicatorsEvaluation.scorePriorityOneColourRed + "\"," +
                                "\"scorePriorityOneColourYellow\":\"" + indicatorsEvaluation.scorePriorityOneColourYellow + "\"," +
                                "\"scorePriorityOneColourGreen\":\"" + indicatorsEvaluation.scorePriorityOneColourGreen + "\"," +
                                "\"scorePriorityTwoColourRed\":\"" + indicatorsEvaluation.scorePriorityTwoColourRed + "\"," +
                                "\"scorePriorityTwoColourYellow\":\"" + indicatorsEvaluation.scorePriorityTwoColourYellow + "\"," +
                                "\"scorePriorityTwoColourGreen\":\"" + indicatorsEvaluation.scorePriorityTwoColourGreen + "\"," +
                                "\"scorePriorityThreeColourRed\":\"" + indicatorsEvaluation.scorePriorityThreeColourRed + "\"," +
                                "\"scorePriorityThreeColourYellow\":\"" + indicatorsEvaluation.scorePriorityThreeColourYellow + "\"," +
                                "\"scorePriorityThreeColourGreen\":\"" + indicatorsEvaluation.scorePriorityThreeColourGreen + "\"," +
                                "\"totalScore\":\"" + indicatorsEvaluation.totalScore + "\"," +
                                "\"isFinished\":\"" + indicatorsEvaluation.isFinished + "\"," +
                                "\"evaluationType\":\"" + indicatorsEvaluation.evaluationType + "\"," +
                                "\"level\":\"" + indicatorsEvaluation.level + "\"}";

                    result.Add(JsonDocument.Parse(rg));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("allRegs")]
        [Authorize]
        public IActionResult GetRegsByIndicatorsEvaluation([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType, [FromQuery] string language, [FromHeader] string Authorization)
        {
            try
            {
                List<JsonDocument> regs=new List<JsonDocument>();
                var auxIr = _context.IndicatorsEvaluationsIndicatorsRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType == evaluationType)
                    .OrderBy(r => r.idIndicator);
                Expression<Func<IndicatorsEvaluationIndicatorReg, IndicatorsEvaluationIndicatorRegDto>> selectorIndReg = ir => new IndicatorsEvaluationIndicatorRegDto
                {
                    idSubSubAmbit=ir.idSubSubAmbit,
                    idSubAmbit=ir.idSubAmbit,
                    idAmbit = ir.idAmbit,
                    idIndicator = ir.idIndicator,
                    indicatorVersion=ir.indicatorVersion,
                    observations=ir.observationsSpanish,
                    numEvidencesMarked = ir.numEvidencesMarked,
                    status=ir.status,
                    requiresImprovementPlan=ir.requiresImprovementPlan
                };
                
                var IndicatorsEvaluationsIndicatorsRegs=auxIr.Select(selectorIndReg).ToList();
                foreach (IndicatorsEvaluationIndicatorRegDto reg in IndicatorsEvaluationsIndicatorsRegs) {
                    String rg= "{\"idSubSubAmbit\":\"" + reg.idSubSubAmbit + "\"," +
                        "\"idSubAmbit\":\"" + reg.idSubAmbit + "\"," +
                        "\"idAmbit\":\"" + reg.idAmbit + "\"," +
                        "\"idIndicator\":\"" + reg.idIndicator + "\"," +
                        "\"indicatorVersion\":\"" + reg.indicatorVersion + "\"," +
                        "\"observations\":\"" + reg.observations + "\"," +
                        "\"numEvidencesMarked\":\"" + reg.numEvidencesMarked + "\"," +
                        "\"status\":\"" + reg.status + "\","+
                        "\"requiresImprovementPlan\":\"" + reg.requiresImprovementPlan + "\"}";
                    regs.Add(JsonDocument.Parse(rg));
                }
                if (evaluationType == "COMPLETE")
                {
                    var auxEvReg = _context.IndicatorsEvaluationsEvidencesRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType == evaluationType)
                        .OrderBy(r => r.idIndicator)
                        .ThenBy(r => r.idEvidence);

                    Expression<Func<IndicatorsEvaluationEvidenceReg, IndicatorsEvaluationEvidenceRegDto>> selectorEvReg = er => new IndicatorsEvaluationEvidenceRegDto
                    {
                        idSubSubAmbit = er.idSubSubAmbit,
                        idSubAmbit = er.idSubAmbit,
                        idAmbit = er.idAmbit,
                        idIndicator = er.idIndicator,
                        indicatorType = er.indicatorType,
                        idEvidence = er.idEvidence,
                        indicatorVersion = er.indicatorVersion,
                        isMarked = er.isMarked,
                        observations = er.observationsSpanish
                    };

                    
                    var IndicatorsEvaluationsEvidencesRegs=auxEvReg.Select(selectorEvReg).ToList();

                    foreach (IndicatorsEvaluationEvidenceRegDto reg in IndicatorsEvaluationsEvidencesRegs)
                    {
                        String rg = "{\"idSubSubAmbit\":\"" + reg.idSubSubAmbit + "\"," +
                            "\"idSubAmbit\":\"" + reg.idSubAmbit + "\"," +
                            "\"idAmbit\":\"" + reg.idAmbit + "\"," +
                            "\"idIndicator\":\"" + reg.idIndicator + "\"," +
                            "\"indicatorType\":\"" + reg.indicatorType + "\"," +
                            "\"idEvidence\":\"" + reg.idEvidence + "\"," +
                            "\"indicatorVersion\":\"" + reg.indicatorVersion + "\"," +
                            "\"isMarked\":\"" + reg.isMarked + "\"," +
                            "\"observations\":\"" + reg.observations + "\"}";
                        regs.Add(JsonDocument.Parse(rg));
                    }
                    String tams = "{\"numIndicatorsRegs\":\"" + IndicatorsEvaluationsIndicatorsRegs.Count + "\"," +
                            "\"numEvidencesRegs\":\"" + IndicatorsEvaluationsEvidencesRegs.Count + "\"}";
                    regs.Add(JsonDocument.Parse(tams));
                }
                else {
                    var auxSer = _context.IndicatorsEvaluationsSimpleEvidencesRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType == evaluationType)
                        .OrderBy(r => r.idIndicator)
                        .ThenBy(r => r.idEvidence);

                    Expression<Func<IndicatorsEvaluationSimpleEvidenceReg, IndicatorsEvaluationSimpleEvidenceRegDto>> selectorSimpleEvReg = ser => new IndicatorsEvaluationSimpleEvidenceRegDto
                    {
                        idSubSubAmbit = ser.idSubSubAmbit,
                        idSubAmbit = ser.idSubAmbit,
                        idAmbit = ser.idAmbit,
                        idIndicator = ser.idIndicator,
                        idEvidence = ser.idEvidence,
                        description = ser.descriptionSpanish,
                        indicatorVersion = ser.indicatorVersion,
                        observations = ser.observationsSpanish
                    };

                    

                    var IndicatorsEvaluationsEvidencesRegs=auxSer.Select(selectorSimpleEvReg).ToList();

                    foreach (IndicatorsEvaluationSimpleEvidenceRegDto reg in IndicatorsEvaluationsEvidencesRegs)
                    {
                        String rg = "{\"idSubSubAmbit\":\"" + reg.idSubSubAmbit + "\"," +
                            "\"idSubAmbit\":\"" + reg.idSubAmbit + "\"," +
                            "\"idAmbit\":\"" + reg.idAmbit + "\"," +
                            "\"idIndicator\":\"" + reg.idIndicator + "\"," +
                            "\"idEvidence\":\"" + reg.idEvidence + "\"," +
                            "\"description\":\"" + reg.description + "\"," +
                            "\"indicatorVersion\":\"" + reg.indicatorVersion + "\"," +
                            "\"observations\":\"" + reg.observations + "\"}";
                        regs.Add(JsonDocument.Parse(rg));
                    }
                    String tams = "{\"numIndicatorsRegs\":\"" + IndicatorsEvaluationsIndicatorsRegs.Count + "\"," +
                            "\"numEvidencesRegs\":\"" + IndicatorsEvaluationsEvidencesRegs.Count + "\"}";
                    regs.Add(JsonDocument.Parse(tams));
                }
                
                return Ok(regs);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains an indicators evaluation from the database
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicators evaluation if success, null if not</returns>
        [HttpGet("get")]
        [Authorize]
        public ActionResult<IndicatorsEvaluation> Get([FromQuery] long evaluationDate,[FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType, [FromHeader] string Authorization)
        {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == evaluationDate && e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.evaluationType==evaluationType);

                if (indicatorsEvaluation == null)
                    return NotFound();

                return indicatorsEvaluation;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }



        /// <summary>
        /// Method that appends to the database a new indicators evaluation
        /// </summary>
        /// <param name="indicatorsEvaluation">Indicators evaluation</param>
        /// <returns>Indicators evaluation if success, null if not</returns>
        [HttpPost]
        [Authorize(Roles = "ADMIN,DIRECTOR")]
        public IActionResult Create([FromBody] IndicatorsEvaluation indicatorsEvaluation, [FromHeader] string Authorization)
        {
            try
            {
                _context.IndicatorsEvaluations.Add(indicatorsEvaluation);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { evaluation_date = indicatorsEvaluation.evaluationDate, idEvaluatorTeam = indicatorsEvaluation.idEvaluatorTeam, idEvaluatorOrganization = indicatorsEvaluation.idEvaluatorOrganization, orgTypeEvaluator = indicatorsEvaluation.orgTypeEvaluator, idEvaluatedOrganization = indicatorsEvaluation.idEvaluatedOrganization, orgTypeEvaluated = indicatorsEvaluation.orgTypeEvaluated, illness = indicatorsEvaluation.illness, idCenter = indicatorsEvaluation.idCenter, evaluationType=indicatorsEvaluation.evaluationType }, indicatorsEvaluation);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an indicators evaluation
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <param name="indicatorsEvaluation">Indicators evaluation</param>
        /// <returns>Indicators evaluation if success, null if not</returns>
        [HttpPut]
        [Authorize(Roles = "ADMIN,DIRECTOR")]
        public IActionResult Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType, [FromBody] IndicatorsEvaluation indicatorsEvaluation, [FromHeader] string Authorization)
        {
            try
            {
                if (evaluationDate != indicatorsEvaluation.evaluationDate || idEvaluatorOrganization != indicatorsEvaluation.idEvaluatorOrganization || orgTypeEvaluator != indicatorsEvaluation.orgTypeEvaluator || idEvaluatedOrganization != indicatorsEvaluation.idEvaluatedOrganization || orgTypeEvaluated != indicatorsEvaluation.orgTypeEvaluated || illness != indicatorsEvaluation.illness || idCenter != indicatorsEvaluation.idCenter || evaluationType !=indicatorsEvaluation.evaluationType)
                    return BadRequest();

                var existingIndicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == evaluationDate && e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.evaluationType==evaluationType);
                if (existingIndicatorsEvaluation is null)
                    return NotFound();

                existingIndicatorsEvaluation.evaluationDate = evaluationDate;
                existingIndicatorsEvaluation.idEvaluatedOrganization = idEvaluatedOrganization;
                existingIndicatorsEvaluation.orgTypeEvaluated = orgTypeEvaluated;
                existingIndicatorsEvaluation.idEvaluatorTeam = idEvaluatorTeam;
                existingIndicatorsEvaluation.idEvaluatorOrganization = indicatorsEvaluation.idEvaluatorOrganization;
                existingIndicatorsEvaluation.orgTypeEvaluator = indicatorsEvaluation.orgTypeEvaluator;
                existingIndicatorsEvaluation.illness = illness;
                existingIndicatorsEvaluation.idCenter = idCenter;
                existingIndicatorsEvaluation.conclusionsSpanish = indicatorsEvaluation.conclusionsSpanish;
                existingIndicatorsEvaluation.conclusionsEnglish = indicatorsEvaluation.conclusionsEnglish;
                existingIndicatorsEvaluation.conclusionsFrench = indicatorsEvaluation.conclusionsFrench;
                existingIndicatorsEvaluation.conclusionsBasque = indicatorsEvaluation.conclusionsBasque;
                existingIndicatorsEvaluation.conclusionsCatalan = indicatorsEvaluation.conclusionsCatalan;
                existingIndicatorsEvaluation.conclusionsDutch = indicatorsEvaluation.conclusionsDutch;
                existingIndicatorsEvaluation.conclusionsGalician = indicatorsEvaluation.conclusionsGalician;
                existingIndicatorsEvaluation.conclusionsGerman = indicatorsEvaluation.conclusionsGerman;
                existingIndicatorsEvaluation.conclusionsItalian = indicatorsEvaluation.conclusionsItalian;
                existingIndicatorsEvaluation.conclusionsPortuguese = indicatorsEvaluation.conclusionsPortuguese;
                existingIndicatorsEvaluation.scorePriorityZeroColourRed = indicatorsEvaluation.scorePriorityZeroColourRed;
                existingIndicatorsEvaluation.scorePriorityZeroColourYellow = indicatorsEvaluation.scorePriorityZeroColourYellow;
                existingIndicatorsEvaluation.scorePriorityZeroColourGreen = indicatorsEvaluation.scorePriorityZeroColourGreen;
                existingIndicatorsEvaluation.scorePriorityOneColourRed = indicatorsEvaluation.scorePriorityOneColourRed;
                existingIndicatorsEvaluation.scorePriorityOneColourYellow = indicatorsEvaluation.scorePriorityOneColourYellow;
                existingIndicatorsEvaluation.scorePriorityOneColourGreen = indicatorsEvaluation.scorePriorityOneColourGreen;
                existingIndicatorsEvaluation.scorePriorityTwoColourRed = indicatorsEvaluation.scorePriorityTwoColourRed;
                existingIndicatorsEvaluation.scorePriorityTwoColourYellow = indicatorsEvaluation.scorePriorityTwoColourYellow;
                existingIndicatorsEvaluation.scorePriorityTwoColourGreen = indicatorsEvaluation.scorePriorityTwoColourGreen;
                existingIndicatorsEvaluation.scorePriorityThreeColourRed = indicatorsEvaluation.scorePriorityThreeColourRed;
                existingIndicatorsEvaluation.scorePriorityThreeColourYellow = indicatorsEvaluation.scorePriorityThreeColourYellow;
                existingIndicatorsEvaluation.scorePriorityThreeColourGreen = indicatorsEvaluation.scorePriorityThreeColourGreen;
                existingIndicatorsEvaluation.totalScore = indicatorsEvaluation.totalScore;
                existingIndicatorsEvaluation.isFinished = indicatorsEvaluation.isFinished;
                existingIndicatorsEvaluation.evaluationType = indicatorsEvaluation.evaluationType;
                existingIndicatorsEvaluation.level=indicatorsEvaluation.level;
                _context.SaveChanges();
                return Ok(existingIndicatorsEvaluation);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an indicator evaluation
        /// </summary>
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <param name="evaluationType">Evaluation type</param>
        /// <returns>Indicators evaluation if success, null if not</returns>
        [HttpDelete]
        [Authorize]
        public IActionResult Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType, [FromHeader] string Authorization)
        {
            try
            {
                var indicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == evaluationDate && e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.evaluationType == evaluationType);

                if (indicatorsEvaluation is null)
                    return NotFound();

                _context.IndicatorsEvaluations.Remove(indicatorsEvaluation);
                _context.SaveChanges();

                return NoContent();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains the finished indicator evaluation with its results
        /// </summary>
        /// <param name="indicatorsEvaluation">Indicator evaluation with no results</param>
        /// <returns>Indicators evaluation if success, null if not</returns>
        [HttpPut("result")]
        [Authorize(Roles = "ADMIN,DIRECTOR")]
        public IActionResult calculateResults([FromBody] IndicatorsEvaluation indicatorsEvaluation, [FromHeader] string Authorization) {
            try
            {
                List<IndicatorsEvaluationIndicatorReg> regs = _context.IndicatorsEvaluationsIndicatorsRegs
                        .Where(r => r.evaluationDate == indicatorsEvaluation.evaluationDate && r.idEvaluatorTeam == indicatorsEvaluation.idEvaluatorTeam && r.idEvaluatorOrganization == indicatorsEvaluation.idEvaluatorOrganization && r.orgTypeEvaluator == indicatorsEvaluation.orgTypeEvaluator && r.idEvaluatedOrganization == indicatorsEvaluation.idEvaluatedOrganization && r.orgTypeEvaluated == indicatorsEvaluation.orgTypeEvaluated && r.illness == indicatorsEvaluation.illness &&
                        r.idCenter == indicatorsEvaluation.idCenter && r.evaluationType == indicatorsEvaluation.evaluationType).ToList();

                List<Indicator> indicators = _context.Indicators
                    .Where(i => i.evaluationType == indicatorsEvaluation.evaluationType && i.isActive == 1)
                    .OrderBy(i => i.idIndicator)
                    .ToList();


                var session=ExpertSystemUtil.Instance.CreateSessionWithRules(typeof(RulePointsForFundamentalReached), typeof(RulePointsForFundamentalInProcess),
                    typeof(RulePointsForHighReached), typeof(RulePointsForHighInProcess), typeof(RulePointsForMediumReached),
                    typeof(RulePointsForMediumInProcess), typeof(RulePointsForLowReached), typeof(RulePointsForLowInProcess));

                List<object> facts= new List<object>();
                facts.Add(indicators);
                facts.Add(regs);
                facts.Add(indicatorsEvaluation);
                ExpertSystemUtil.Instance.RunRules(session,facts);


                indicatorsEvaluation.totalScore= indicatorsEvaluation.scorePriorityZeroColourYellow + indicatorsEvaluation.scorePriorityZeroColourGreen +
                    indicatorsEvaluation.scorePriorityOneColourYellow + indicatorsEvaluation.scorePriorityOneColourGreen +
                    indicatorsEvaluation.scorePriorityTwoColourYellow + indicatorsEvaluation.scorePriorityTwoColourGreen +
                    indicatorsEvaluation.scorePriorityThreeColourYellow + indicatorsEvaluation.scorePriorityThreeColourGreen;

                session=ExpertSystemUtil.Instance.CreateSessionWithRules(typeof(RuleExcellentIndicatorEvaluation), typeof(RuleVeryGoodIndicatorEvaluation),
                    typeof(RuleGoodIndicatorEvaluation),typeof(RuleImprovableIndicatorEvaluation), typeof(RuleVeryImprovableIndicatorEvaluation));
                
                facts=new List<object>();
                facts.Add(indicatorsEvaluation);
                ExpertSystemUtil.Instance.RunRules(session,facts);


                var existingIndicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == indicatorsEvaluation.evaluationDate && e.idEvaluatorTeam == indicatorsEvaluation.idEvaluatorTeam && e.idEvaluatorOrganization == indicatorsEvaluation.idEvaluatorOrganization && e.orgTypeEvaluator == indicatorsEvaluation.orgTypeEvaluator && e.idEvaluatedOrganization == indicatorsEvaluation.idEvaluatedOrganization && e.orgTypeEvaluated == indicatorsEvaluation.orgTypeEvaluated && e.illness == indicatorsEvaluation.illness && e.idCenter == indicatorsEvaluation.idCenter && e.evaluationType == indicatorsEvaluation.evaluationType);
                if (existingIndicatorsEvaluation is null)
                    return NotFound();
                existingIndicatorsEvaluation.conclusionsSpanish = indicatorsEvaluation.conclusionsSpanish;
                existingIndicatorsEvaluation.conclusionsEnglish = indicatorsEvaluation.conclusionsEnglish;
                existingIndicatorsEvaluation.conclusionsFrench = indicatorsEvaluation.conclusionsFrench;
                existingIndicatorsEvaluation.conclusionsBasque = indicatorsEvaluation.conclusionsBasque;
                existingIndicatorsEvaluation.conclusionsCatalan = indicatorsEvaluation.conclusionsCatalan;
                existingIndicatorsEvaluation.conclusionsDutch = indicatorsEvaluation.conclusionsDutch;
                existingIndicatorsEvaluation.conclusionsGalician = indicatorsEvaluation.conclusionsGalician;
                existingIndicatorsEvaluation.conclusionsGerman = indicatorsEvaluation.conclusionsGerman;
                existingIndicatorsEvaluation.conclusionsItalian = indicatorsEvaluation.conclusionsItalian;
                existingIndicatorsEvaluation.conclusionsPortuguese = indicatorsEvaluation.conclusionsPortuguese;
                existingIndicatorsEvaluation.scorePriorityZeroColourRed = 0;
                existingIndicatorsEvaluation.scorePriorityZeroColourYellow = indicatorsEvaluation.scorePriorityZeroColourYellow;
                existingIndicatorsEvaluation.scorePriorityZeroColourGreen = indicatorsEvaluation.scorePriorityZeroColourGreen;
                existingIndicatorsEvaluation.scorePriorityOneColourRed = 0;
                existingIndicatorsEvaluation.scorePriorityOneColourYellow = indicatorsEvaluation.scorePriorityOneColourYellow;
                existingIndicatorsEvaluation.scorePriorityOneColourGreen = indicatorsEvaluation.scorePriorityOneColourGreen;
                existingIndicatorsEvaluation.scorePriorityTwoColourRed = 0;
                existingIndicatorsEvaluation.scorePriorityTwoColourYellow = indicatorsEvaluation.scorePriorityTwoColourYellow;
                existingIndicatorsEvaluation.scorePriorityTwoColourGreen = indicatorsEvaluation.scorePriorityTwoColourGreen;
                existingIndicatorsEvaluation.scorePriorityThreeColourRed = 0;
                existingIndicatorsEvaluation.scorePriorityThreeColourYellow = indicatorsEvaluation.scorePriorityThreeColourYellow;
                existingIndicatorsEvaluation.scorePriorityThreeColourGreen = indicatorsEvaluation.scorePriorityThreeColourGreen;
                existingIndicatorsEvaluation.totalScore = indicatorsEvaluation.totalScore;
                existingIndicatorsEvaluation.isFinished = indicatorsEvaluation.isFinished;
                existingIndicatorsEvaluation.level= indicatorsEvaluation.level;

                _context.SaveChanges();

                return Ok(existingIndicatorsEvaluation);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }
    }
}
