using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using NRules;
using NRules.Fluent;
using OTEAServer.ExpertSystem;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Net.NetworkInformation;
using System.Text.Json;

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


        /// <summary>
        /// Method that obtains all indicators evaluations
        /// </summary>
        /// <returns>Indicators evaluations list</returns>
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            try
            {
                var indicatorsEvaluations = _context.IndicatorsEvaluations.ToList();
                return Ok(indicatorsEvaluations);
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
        public IActionResult GetAllByEvaluatorTeam([FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness)
        {
            try
            {
                var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatorTeam == idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness).ToList();
                return Ok(indicatorsEvaluations);
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
        public IActionResult GetNonFinishedByEvaluatorTeam([FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter)
        {
            try
            {
                var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatorTeam==idEvaluatorTeam && e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.isFinished == 0).ToList();
                return Ok(indicatorsEvaluations);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("allRegs")]
        public IActionResult GetRegsByIndicatorsEvaluation([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType)
        {
            try
            {
                List<JsonDocument> regs=new List<JsonDocument>();
                var IndicatorsEvaluationsIndicatorsRegs = _context.IndicatorsEvaluationsIndicatorsRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam==idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType == evaluationType)
                    .OrderBy(r => r.idIndicator)
                    .ToList();
                foreach (IndicatorsEvaluationIndicatorReg reg in IndicatorsEvaluationsIndicatorsRegs) {
                    String rg= "{\"idSubSubAmbit\":\"" + reg.idSubSubAmbit + "\"," +
                        "\"idSubAmbit\":\"" + reg.idSubAmbit + "\"," +
                        "\"idAmbit\":\"" + reg.idAmbit + "\"," +
                        "\"idIndicator\":\"" + reg.idIndicator + "\"," +
                        "\"indicatorVersion\":\"" + reg.indicatorVersion + "\"," +
                        "\"observationsSpanish\":\"" + reg.observationsSpanish + "\"," +
                        "\"observationsEnglish\":\"" + reg.observationsEnglish + "\"," +
                        "\"observationsFrench\":\"" + reg.observationsFrench + "\"," +
                        "\"observationsBasque\":\"" + reg.observationsBasque + "\"," +
                        "\"observationsCatalan\":\"" + reg.observationsCatalan + "\"," +
                        "\"observationsDutch\":\"" + reg.observationsDutch + "\"," +
                        "\"observationsGalician\":\"" + reg.observationsGalician + "\"," +
                        "\"observationsGerman\":\"" + reg.observationsGerman + "\"," +
                        "\"observationsItalian\":\"" + reg.observationsItalian + "\"," +
                        "\"observationsPortuguese\":\"" + reg.observationsPortuguese + "\"," +
                        "\"numEvidencesMarked\":\"" + reg.numEvidencesMarked + "\"," +
                        "\"status\":\"" + reg.status + "\","+
                        "\"requiresImprovementPlan\":\"" + reg.requiresImprovementPlan + "\"}";
                    regs.Add(JsonDocument.Parse(rg));
                }
                if (evaluationType == "COMPLETE")
                {
                    var IndicatorsEvaluationsEvidencesRegs = _context.IndicatorsEvaluationsEvidencesRegs.Where(r => r.evaluationDate == evaluationDate && r.idEvaluatorTeam == idEvaluatorTeam && r.idEvaluatorOrganization == idEvaluatorOrganization && r.orgTypeEvaluator == orgTypeEvaluator && r.idEvaluatedOrganization == idEvaluatedOrganization && r.orgTypeEvaluated == orgTypeEvaluated && r.illness == illness && r.idCenter == idCenter && r.evaluationType == evaluationType)
                        .OrderBy(r => r.idIndicator)
                        .ThenBy(r => r.idEvidence)
                        .ToList();
                    foreach (IndicatorsEvaluationEvidenceReg reg in IndicatorsEvaluationsEvidencesRegs)
                    {
                        String rg = "{\"idSubSubAmbit\":\"" + reg.idSubSubAmbit + "\"," +
                            "\"idSubAmbit\":\"" + reg.idSubAmbit + "\"," +
                            "\"idAmbit\":\"" + reg.idAmbit + "\"," +
                            "\"idIndicator\":\"" + reg.idIndicator + "\"," +
                            "\"indicatorType\":\"" + reg.indicatorType + "\"," +
                            "\"idEvidence\":\"" + reg.idEvidence + "\"," +
                            "\"indicatorVersion\":\"" + reg.indicatorVersion + "\"," +
                            "\"isMarked\":\"" + reg.isMarked + "\"," +
                            "\"observationsSpanish\":\"" + reg.observationsSpanish + "\"," +
                            "\"observationsEnglish\":\"" + reg.observationsEnglish + "\"," +
                            "\"observationsFrench\":\"" + reg.observationsFrench + "\"," +
                            "\"observationsBasque\":\"" + reg.observationsBasque + "\"," +
                            "\"observationsCatalan\":\"" + reg.observationsCatalan + "\"," +
                            "\"observationsDutch\":\"" + reg.observationsDutch + "\"," +
                            "\"observationsGalician\":\"" + reg.observationsGalician + "\"," +
                            "\"observationsGerman\":\"" + reg.observationsGerman + "\"," +
                            "\"observationsItalian\":\"" + reg.observationsItalian + "\"," +
                            "\"observationsPortuguese\":\"" + reg.observationsPortuguese + "\"}";
                        regs.Add(JsonDocument.Parse(rg));
                    }
                    String tams= "{\"numIndicatorsRegs\":\"" + IndicatorsEvaluationsIndicatorsRegs.Count + "\"," +
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
        public ActionResult<IndicatorsEvaluation> Get([FromQuery] long evaluationDate,[FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType)
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
        public IActionResult Create([FromBody] IndicatorsEvaluation indicatorsEvaluation)
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
        public IActionResult Update([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType, [FromBody] IndicatorsEvaluation indicatorsEvaluation)
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
        public IActionResult Delete([FromQuery] long evaluationDate, [FromQuery] int idEvaluatorTeam, [FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter, [FromQuery] string evaluationType)
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
        public IActionResult calculateResults([FromBody] IndicatorsEvaluation indicatorsEvaluation) {
            try
            {
                
                Dictionary<string, Dictionary<string, int>> numIndicatorsPerPriority = new Dictionary<string, Dictionary<string, int>>();
                List<IndicatorsEvaluationIndicatorReg> regs = _context.IndicatorsEvaluationsIndicatorsRegs
                        .Where(r => r.evaluationDate == indicatorsEvaluation.evaluationDate && r.idEvaluatorTeam == indicatorsEvaluation.idEvaluatorTeam && r.idEvaluatorOrganization == indicatorsEvaluation.idEvaluatorOrganization && r.orgTypeEvaluator == indicatorsEvaluation.orgTypeEvaluator && r.idEvaluatedOrganization == indicatorsEvaluation.idEvaluatedOrganization && r.orgTypeEvaluated == indicatorsEvaluation.orgTypeEvaluated && r.illness == indicatorsEvaluation.illness &&
                        r.idCenter == indicatorsEvaluation.idCenter && r.evaluationType == indicatorsEvaluation.evaluationType).ToList();

                foreach (IndicatorsEvaluationIndicatorReg reg in regs)
                {
                    var indicator = _context.Indicators.FirstOrDefault(i => i.idIndicator == reg.idIndicator && i.indicatorType == reg.illness && i.idSubSubAmbit == reg.idSubSubAmbit && i.idSubAmbit == reg.idSubAmbit && i.idAmbit == reg.idAmbit && i.indicatorVersion == reg.indicatorVersion && i.evaluationType == reg.evaluationType);
                    
                    if (indicator != null) {
                        if (!numIndicatorsPerPriority.ContainsKey(indicator.indicatorPriority)) {
                            numIndicatorsPerPriority[indicator.indicatorPriority] = new Dictionary<string, int>();
                        }
                        if (!numIndicatorsPerPriority[indicator.indicatorPriority].ContainsKey(reg.status))
                        {
                            numIndicatorsPerPriority[indicator.indicatorPriority][reg.status] = 1;
                        }
                        else {
                            numIndicatorsPerPriority[indicator.indicatorPriority][reg.status] += 1;
                        }
                    }
                    else
                    {
                        return NotFound();
                    }
                    
                }
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
                if (numIndicatorsPerPriority["LOW_INTEREST"].ContainsKey("IN_PROCESS"))
                {
                    existingIndicatorsEvaluation.scorePriorityZeroColourYellow = numIndicatorsPerPriority["LOW_INTEREST"]["IN_PROCESS"];
                }
                else {
                    existingIndicatorsEvaluation.scorePriorityZeroColourYellow = 0;
                }
                if (numIndicatorsPerPriority["LOW_INTEREST"].ContainsKey("COMPLETED"))
                {
                    existingIndicatorsEvaluation.scorePriorityZeroColourGreen = numIndicatorsPerPriority["LOW_INTEREST"]["COMPLETED"] * 2;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityZeroColourGreen = 0;
                }
                existingIndicatorsEvaluation.scorePriorityOneColourRed = 0;
                if (numIndicatorsPerPriority["MEDIUM_INTEREST"].ContainsKey("IN_PROCESS"))
                {
                    existingIndicatorsEvaluation.scorePriorityOneColourYellow = numIndicatorsPerPriority["MEDIUM_INTEREST"]["IN_PROCESS"] * 2;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityOneColourYellow = 0;
                }
                if (numIndicatorsPerPriority["MEDIUM_INTEREST"].ContainsKey("COMPLETED"))
                {
                    existingIndicatorsEvaluation.scorePriorityOneColourGreen = numIndicatorsPerPriority["MEDIUM_INTEREST"]["COMPLETED"] * 3;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityOneColourGreen = 0;
                }
                existingIndicatorsEvaluation.scorePriorityTwoColourRed = 0;
                if (numIndicatorsPerPriority["HIGH_INTEREST"].ContainsKey("IN_PROCESS"))
                {
                    existingIndicatorsEvaluation.scorePriorityTwoColourYellow = numIndicatorsPerPriority["HIGH_INTEREST"]["IN_PROCESS"] * 3;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityTwoColourYellow = 0;
                }
                if (numIndicatorsPerPriority["HIGH_INTEREST"].ContainsKey("COMPLETED"))
                {
                    existingIndicatorsEvaluation.scorePriorityTwoColourGreen = numIndicatorsPerPriority["HIGH_INTEREST"]["COMPLETED"] * 4;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityTwoColourGreen = 0;
                }
                existingIndicatorsEvaluation.scorePriorityThreeColourRed = 0;
                if (numIndicatorsPerPriority["FUNDAMENTAL_INTEREST"].ContainsKey("IN_PROCESS"))
                {
                    existingIndicatorsEvaluation.scorePriorityThreeColourYellow = numIndicatorsPerPriority["FUNDAMENTAL_INTEREST"]["IN_PROCESS"] * 4;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityThreeColourYellow = 0;
                }
                if (numIndicatorsPerPriority["FUNDAMENTAL_INTEREST"].ContainsKey("COMPLETED"))
                {
                    existingIndicatorsEvaluation.scorePriorityThreeColourGreen = numIndicatorsPerPriority["FUNDAMENTAL_INTEREST"]["COMPLETED"] * 5;
                }
                else
                {
                    existingIndicatorsEvaluation.scorePriorityThreeColourGreen = 0;
                }
                existingIndicatorsEvaluation.totalScore = existingIndicatorsEvaluation.scorePriorityZeroColourYellow+ existingIndicatorsEvaluation.scorePriorityZeroColourGreen+ 
                    existingIndicatorsEvaluation.scorePriorityOneColourYellow+ existingIndicatorsEvaluation.scorePriorityOneColourGreen+ 
                    existingIndicatorsEvaluation.scorePriorityTwoColourYellow+ existingIndicatorsEvaluation.scorePriorityTwoColourGreen+ 
                    existingIndicatorsEvaluation.scorePriorityThreeColourYellow+ existingIndicatorsEvaluation.scorePriorityThreeColourGreen;
                existingIndicatorsEvaluation.isFinished = 1;

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
