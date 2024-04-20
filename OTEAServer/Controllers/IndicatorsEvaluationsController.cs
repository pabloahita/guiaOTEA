using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Net.NetworkInformation;

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
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier</param>
        /// <returns>Indicators evaluations list</returns>
        [HttpGet("nonFinished")]
        public IActionResult GetNonFinishedByCenter([FromQuery] int idEvaluatorOrganization, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrganization, [FromQuery] string orgTypeEvaluated, [FromQuery] string illness, [FromQuery] int idCenter)
        {
            try
            {
                var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatorOrganization == idEvaluatorOrganization && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrganization && e.orgTypeEvaluated == orgTypeEvaluated && e.illness == illness && e.idCenter == idCenter && e.isFinished == 0).ToList();
                return Ok(indicatorsEvaluations);
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
                
                int totalScore = 0;
                int numPriorities = 4;
                int numColours = 3;
                int[,] numIndicatorsPerPriorityPerColour = new int[numPriorities, numColours];
                int[,] results = new int[4,3];
                for (int i = 0; i < numPriorities; i++) {//Number of priorities
                    for (int j = 0; j < numColours; j++) {//Number of possible colours
                        results[i, j] = 0;
                        numIndicatorsPerPriorityPerColour[i, j] = 0;
                    }
                }
                
                for (int i=0;i<numPriorities;i++) {
                    List<Indicator> indicators = _context.Indicators
                    .Where(ind => ind.evaluationType == indicatorsEvaluation.evaluationType && ind.isActive == 1 && ind.indicatorPriority==i)
                    .OrderBy(ind => ind.idIndicator)
                    .ToList();
                    List<int> idsIndicators=indicators.Select(ind => ind.idIndicator).ToList();
                    List<IndicatorsEvaluationIndicatorReg> regs = _context.IndicatorsEvaluationsIndicatorsRegs
                        .Where(r => r.evaluationDate == indicatorsEvaluation.evaluationDate && r.idEvaluatorTeam == indicatorsEvaluation.idEvaluatorTeam && r.idEvaluatorOrganization == indicatorsEvaluation.idEvaluatorOrganization && r.orgTypeEvaluator == indicatorsEvaluation.orgTypeEvaluator && r.idEvaluatedOrganization == indicatorsEvaluation.idEvaluatedOrganization && r.orgTypeEvaluated == indicatorsEvaluation.orgTypeEvaluated && r.illness == indicatorsEvaluation.illness && r.idCenter == indicatorsEvaluation.idCenter && r.evaluationType == indicatorsEvaluation.evaluationType && idsIndicators.Contains(r.idIndicator)).ToList();
                    foreach (IndicatorsEvaluationIndicatorReg reg in regs)
                    {
                        int colour = -1;
                        if (reg.numEvidencesMarked == 0 || reg.numEvidencesMarked == 1)
                        {
                            colour = 0;
                        }
                        if (reg.numEvidencesMarked == 2 || reg.numEvidencesMarked == 3)
                        {
                            colour = 1;
                        }
                        if (reg.numEvidencesMarked == 4)
                        {
                            colour = 2;
                        }
                        numIndicatorsPerPriorityPerColour[i, colour]++;
                    }
                    for (int j = 1; j < numColours; j++)
                    {
                        int mul = i+j;
                        results[i, j] = numIndicatorsPerPriorityPerColour[i, j] * mul;
                        totalScore += results[i, j];
                    }
                }


                var existingIndicatorsEvaluation = _context.IndicatorsEvaluations.FirstOrDefault(e => e.evaluationDate == indicatorsEvaluation.evaluationDate && e.idEvaluatorTeam == indicatorsEvaluation.idEvaluatorTeam && e.idEvaluatorOrganization == indicatorsEvaluation.idEvaluatorOrganization && e.orgTypeEvaluator == indicatorsEvaluation.orgTypeEvaluator && e.idEvaluatedOrganization == indicatorsEvaluation.idEvaluatedOrganization && e.orgTypeEvaluated == indicatorsEvaluation.orgTypeEvaluated && e.illness == indicatorsEvaluation.illness && e.idCenter == indicatorsEvaluation.idCenter && e.evaluationType == indicatorsEvaluation.evaluationType);
                if (existingIndicatorsEvaluation is null)
                    return NotFound();

                int[,] foo1 = numIndicatorsPerPriorityPerColour;
                int[,] foo2 = results;
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
                existingIndicatorsEvaluation.scorePriorityZeroColourRed = results[0,0];
                existingIndicatorsEvaluation.scorePriorityZeroColourYellow = results[0,1];
                existingIndicatorsEvaluation.scorePriorityZeroColourGreen = results[0,2];
                existingIndicatorsEvaluation.scorePriorityOneColourRed = results[1,0];
                existingIndicatorsEvaluation.scorePriorityOneColourYellow = results[1,1];
                existingIndicatorsEvaluation.scorePriorityOneColourGreen = results[1,2];
                existingIndicatorsEvaluation.scorePriorityTwoColourRed = results[2,0];
                existingIndicatorsEvaluation.scorePriorityTwoColourYellow = results[2,1];
                existingIndicatorsEvaluation.scorePriorityTwoColourGreen = results[2,2];
                existingIndicatorsEvaluation.scorePriorityThreeColourRed = results[3,0];
                existingIndicatorsEvaluation.scorePriorityThreeColourYellow = results[3,1];
                existingIndicatorsEvaluation.scorePriorityThreeColourGreen = results[3,2];
                existingIndicatorsEvaluation.totalScore = totalScore;
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
