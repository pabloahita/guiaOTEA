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
                existingIndicatorsEvaluation.scoreAmbit1 = indicatorsEvaluation.scoreAmbit1;
                existingIndicatorsEvaluation.scoreAmbit2 = indicatorsEvaluation.scoreAmbit2;
                existingIndicatorsEvaluation.scoreAmbit3 = indicatorsEvaluation.scoreAmbit3;
                existingIndicatorsEvaluation.scoreAmbit4 = indicatorsEvaluation.scoreAmbit4;
                existingIndicatorsEvaluation.scoreAmbit5 = indicatorsEvaluation.scoreAmbit5;
                existingIndicatorsEvaluation.scoreAmbit6 = indicatorsEvaluation.scoreAmbit6;
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
        /// <param name="evaluationDate">Evaluation date in timestamp</param>
        /// <param name="idEvaluatorTeam">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrganization">Identifier of the evaluator organization that will do the indicators evaluation</param>
        /// <param name="orgTypeEvaluator">Organization type of the evaluator organization that will do the indicators evaluation. It will be always "EVALUATOR"</param>
        /// <param name="idEvaluatedOrganization">Identifier of the external organization that will recieve the indicators evaluation</param>
        /// <param name="orgTypeEvaluated">Organization type of the external organization that will recieve the indicators evaluation. It will be always "EVALUATED"</param>
        /// <param name="illness">Illness of the external organization that will recieve the indicators evaluation. In case of Fundación Miradas, it will be "AUTISM"</param>
        /// <param name="idCenter">Center identifier of the external organization</param>
        /// <returns>Indicators evaluation if success, null if not</returns>
        [HttpPut("result")]
        public IActionResult calculateResults([FromBody] IndicatorsEvaluation indicatorsEvaluation) {
            try
            {
                List<IndicatorsEvaluationEvidenceReg> regs = _context.IndicatorsEvaluationsEvidencesRegs.Where(r => r.evaluationDate == indicatorsEvaluation.evaluationDate && r.idEvaluatorTeam == indicatorsEvaluation.idEvaluatorTeam && r.idEvaluatorOrganization == indicatorsEvaluation.idEvaluatorOrganization && r.orgTypeEvaluator == indicatorsEvaluation.orgTypeEvaluator && r.idEvaluatedOrganization == indicatorsEvaluation.idEvaluatedOrganization && r.orgTypeEvaluated == indicatorsEvaluation.orgTypeEvaluated && r.illness == indicatorsEvaluation.illness && r.idCenter == indicatorsEvaluation.idCenter && r.evaluationType==indicatorsEvaluation.evaluationType).ToList();
                int lastAmbit = -1;
                int totalScore = 0;
                int numEvidencesInAnIndicator = 0;
                List<int> results = new List<int>();
                for (int i = 0; i < regs.Count; i++)
                {
                    if (i == 0 || lastAmbit != regs[i].idAmbit) { lastAmbit = regs[i].idAmbit; }
                    if (results.Count < lastAmbit) { results.Add(0); }
                    if (regs[i].isMarked == 1) { results[lastAmbit - 1]++; }
                    numEvidencesInAnIndicator++;
                    if (numEvidencesInAnIndicator == 4)
                    {
                        var indicator = _context.Indicators.FirstOrDefault(ind => ind.idIndicator == regs[i].idIndicator && ind.indicatorType == regs[i].indicatorType && ind.idSubSubAmbit == regs[i].idSubSubAmbit && ind.idSubAmbit == regs[i].idSubAmbit && ind.idAmbit == regs[i].idAmbit && ind.indicatorVersion == regs[i].indicatorVersion);
                        int mul = -1;
                        if (results[lastAmbit - 1] == 0 || results[lastAmbit - 1] == 1)
                        {
                            mul = indicator.multiplierOneOrZeroFilled;
                        }
                        if (results[lastAmbit - 1] == 2 || results[lastAmbit - 1] == 3)
                        {
                            mul = indicator.multiplierTwoOrThreeFilled;
                        }
                        if (results[lastAmbit - 1] == 4)
                        {
                            mul = indicator.multiplierFourFilled;
                        }
                        results[lastAmbit - 1] = mul * results[lastAmbit - 1];
                        totalScore += results[lastAmbit - 1];
                        numEvidencesInAnIndicator = 0;
                    }
                }
                results.Add(totalScore);

                indicatorsEvaluation.scoreAmbit1 = results[0];
                indicatorsEvaluation.scoreAmbit2 = results[1];
                indicatorsEvaluation.scoreAmbit3 = results[2];
                indicatorsEvaluation.scoreAmbit4 = results[3];
                indicatorsEvaluation.scoreAmbit5 = results[4];
                indicatorsEvaluation.scoreAmbit6 = results[5];
                indicatorsEvaluation.totalScore = results[6];
                indicatorsEvaluation.isFinished = 1;

                _context.SaveChanges();

                return Ok(indicatorsEvaluation);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }
    }
}
