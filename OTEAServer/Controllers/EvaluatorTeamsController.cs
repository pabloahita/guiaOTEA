using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Net;
using System.Text.Json;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for evaluator teams operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("EvaluatorTeams")]
    public class EvaluatorTeamsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public EvaluatorTeamsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all the evaluator teams
        /// </summary>
        /// <returns>Evaluator team list</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromHeader] string Authorization)
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var evaluatorTeam in evaluatorTeams)
                {
                    String rg = "{\"idEvaluatorTeam\":\"" + evaluatorTeam.idEvaluatorTeam + "\"," +
                            "\"creationDate\":\"" + evaluatorTeam.creationDate + "\"," +
                            "\"emailProfessional\":\"" + evaluatorTeam.emailProfessional + "\"," +
                            "\"emailResponsible\":\"" + evaluatorTeam.emailResponsible + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"idEvaluatorOrganization\":\"" + evaluatorTeam.idEvaluatorOrganization + "\"," +
                            "\"orgTypeEvaluator\":\"" + evaluatorTeam.orgTypeEvaluator + "\"," +
                            "\"idEvaluatedOrganization\":\"" + evaluatorTeam.idEvaluatedOrganization + "\"," +
                            "\"orgTypeEvaluated\":\"" + evaluatorTeam.orgTypeEvaluated + "\"," +
                            "\"idCenter\":\"" + evaluatorTeam.idCenter + "\"," +
                            "\"illness\":\"" + evaluatorTeam.illness + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"externalConsultant\":\"" + evaluatorTeam.externalConsultant + "\"," +
                            "\"patientName\":\"" + evaluatorTeam.patientName + "\"," +
                            "\"relativeName\":\"" + evaluatorTeam.relativeName + "\"," +
                            "\"observationsSpanish\":\"" + evaluatorTeam.observationsSpanish + "\"," +
                            "\"observationsEnglish\":\"" + evaluatorTeam.observationsEnglish + "\"," +
                            "\"observationsFrench\":\"" + evaluatorTeam.observationsFrench + "\"," +
                            "\"observationsBasque\":\"" + evaluatorTeam.observationsBasque + "\"," +
                            "\"observationsCatalan\":\"" + evaluatorTeam.observationsCatalan + "\"," +
                            "\"observationsDutch\":\"" + evaluatorTeam.observationsDutch + "\"," +
                            "\"observationsGalician\":\"" + evaluatorTeam.observationsGalician + "\"," +
                            "\"observationsGerman\":\"" + evaluatorTeam.observationsGerman + "\"," +
                            "\"observationsItalian\":\"" + evaluatorTeam.observationsItalian + "\"," +
                            "\"observationsPortuguese\":\"" + evaluatorTeam.observationsPortuguese + "\"," +
                            "\"evaluationDates\":\"" + evaluatorTeam.evaluationDates + "\"," +
                            "\"completedEvaluationDates\":\"" + evaluatorTeam.completedEvaluationDates + "\"," +
                            "\"totalEvaluationDates\":\"" + evaluatorTeam.totalEvaluationDates + "\"," +
                            "\"profilePhoto\":\"" + evaluatorTeam.profilePhoto + "\"}";
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
        /// Method that obtains all the non finished evaluator teams by center
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="idCenter">Organization center</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Evaluator teams list</returns>
        [HttpGet("allNonFinishedByCenter")]
        [Authorize]
        public IActionResult GetAllNonFinishedByCenter([FromQuery] int id, [FromQuery] string orgType, [FromQuery] int idCenter, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.Where(e => e.idEvaluatedOrganization == id && e.orgTypeEvaluated == orgType && e.idCenter == idCenter && e.illness == illness && e.completedEvaluationDates<e.totalEvaluationDates).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var evaluatorTeam in evaluatorTeams)
                {
                    String rg = "{\"idEvaluatorTeam\":\"" + evaluatorTeam.idEvaluatorTeam + "\"," +
                            "\"creationDate\":\"" + evaluatorTeam.creationDate + "\"," +
                            "\"emailProfessional\":\"" + evaluatorTeam.emailProfessional + "\"," +
                            "\"emailResponsible\":\"" + evaluatorTeam.emailResponsible + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"idEvaluatorOrganization\":\"" + evaluatorTeam.idEvaluatorOrganization + "\"," +
                            "\"orgTypeEvaluator\":\"" + evaluatorTeam.orgTypeEvaluator + "\"," +
                            "\"idEvaluatedOrganization\":\"" + evaluatorTeam.idEvaluatedOrganization + "\"," +
                            "\"orgTypeEvaluated\":\"" + evaluatorTeam.orgTypeEvaluated + "\"," +
                            "\"idCenter\":\"" + evaluatorTeam.idCenter + "\"," +
                            "\"illness\":\"" + evaluatorTeam.illness + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"externalConsultant\":\"" + evaluatorTeam.externalConsultant + "\"," +
                            "\"patientName\":\"" + evaluatorTeam.patientName + "\"," +
                            "\"relativeName\":\"" + evaluatorTeam.relativeName + "\"," +
                            "\"observationsSpanish\":\"" + evaluatorTeam.observationsSpanish + "\"," +
                            "\"observationsEnglish\":\"" + evaluatorTeam.observationsEnglish + "\"," +
                            "\"observationsFrench\":\"" + evaluatorTeam.observationsFrench + "\"," +
                            "\"observationsBasque\":\"" + evaluatorTeam.observationsBasque + "\"," +
                            "\"observationsCatalan\":\"" + evaluatorTeam.observationsCatalan + "\"," +
                            "\"observationsDutch\":\"" + evaluatorTeam.observationsDutch + "\"," +
                            "\"observationsGalician\":\"" + evaluatorTeam.observationsGalician + "\"," +
                            "\"observationsGerman\":\"" + evaluatorTeam.observationsGerman + "\"," +
                            "\"observationsItalian\":\"" + evaluatorTeam.observationsItalian + "\"," +
                            "\"observationsPortuguese\":\"" + evaluatorTeam.observationsPortuguese + "\"," +
                            "\"evaluationDates\":\"" + evaluatorTeam.evaluationDates + "\"," +
                            "\"completedEvaluationDates\":\"" + evaluatorTeam.completedEvaluationDates + "\"," +
                            "\"totalEvaluationDates\":\"" + evaluatorTeam.totalEvaluationDates + "\"," +
                            "\"profilePhoto\":\"" + evaluatorTeam.profilePhoto + "\"}";
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
        /// Method that obtains all the finished evaluator teams by center
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="idCenter">Organization center</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Evaluator teams list</returns>
        [HttpGet("allByCenter")]
        [Authorize]
        public IActionResult GetAllByCenter([FromQuery] int id, [FromQuery] string orgType, [FromQuery] int idCenter, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.Where(e => e.idEvaluatedOrganization == id && e.orgTypeEvaluated == orgType && e.idCenter == idCenter && e.illness == illness).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach( var evaluatorTeam in evaluatorTeams )
                {
                    String rg = "{\"idEvaluatorTeam\":\"" + evaluatorTeam.idEvaluatorTeam + "\"," +
                            "\"creationDate\":\"" + evaluatorTeam.creationDate + "\"," +
                            "\"emailProfessional\":\"" + evaluatorTeam.emailProfessional + "\"," +
                            "\"emailResponsible\":\"" + evaluatorTeam.emailResponsible + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"idEvaluatorOrganization\":\"" + evaluatorTeam.idEvaluatorOrganization + "\"," +
                            "\"orgTypeEvaluator\":\"" + evaluatorTeam.orgTypeEvaluator + "\"," +
                            "\"idEvaluatedOrganization\":\"" + evaluatorTeam.idEvaluatedOrganization + "\"," +
                            "\"orgTypeEvaluated\":\"" + evaluatorTeam.orgTypeEvaluated + "\"," +
                            "\"idCenter\":\"" + evaluatorTeam.idCenter + "\"," +
                            "\"illness\":\"" + evaluatorTeam.illness + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"externalConsultant\":\"" + evaluatorTeam.externalConsultant + "\"," +
                            "\"patientName\":\"" + evaluatorTeam.patientName + "\"," +
                            "\"relativeName\":\"" + evaluatorTeam.relativeName + "\"," +
                            "\"observationsSpanish\":\"" + evaluatorTeam.observationsSpanish + "\"," +
                            "\"observationsEnglish\":\"" + evaluatorTeam.observationsEnglish + "\"," +
                            "\"observationsFrench\":\"" + evaluatorTeam.observationsFrench + "\"," +
                            "\"observationsBasque\":\"" + evaluatorTeam.observationsBasque + "\"," +
                            "\"observationsCatalan\":\"" + evaluatorTeam.observationsCatalan + "\"," +
                            "\"observationsDutch\":\"" + evaluatorTeam.observationsDutch + "\"," +
                            "\"observationsGalician\":\"" + evaluatorTeam.observationsGalician + "\"," +
                            "\"observationsGerman\":\"" + evaluatorTeam.observationsGerman + "\"," +
                            "\"observationsItalian\":\"" + evaluatorTeam.observationsItalian + "\"," +
                            "\"observationsPortuguese\":\"" + evaluatorTeam.observationsPortuguese + "\"," +
                            "\"evaluationDates\":\"" + evaluatorTeam.evaluationDates + "\"," +
                            "\"completedEvaluationDates\":\"" + evaluatorTeam.completedEvaluationDates + "\"," +
                            "\"totalEvaluationDates\":\"" + evaluatorTeam.totalEvaluationDates + "\"," +
                            "\"profilePhoto\":\"" + evaluatorTeam.profilePhoto + "\"}";
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
        /// Method that obtains all the evaluator teams by organization
        /// </summary>
        /// <param name="id">Organization identifier</param>
        /// <param name="orgType">Organization type</param>
        /// <param name="illness">Organization illness or syndrome</param>
        /// <returns>Evaluator teams list</returns>
        [HttpGet("allByOrganization")]
        [Authorize]
        public IActionResult GetAllByOrganization([FromQuery] int id, [FromQuery] string orgType, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var evaluatorTeams = _context.EvaluatorTeams.Where(e => e.idEvaluatedOrganization == id && e.orgTypeEvaluated == orgType && e.illness == illness).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var evaluatorTeam in evaluatorTeams)
                {
                    String rg = "{\"idEvaluatorTeam\":\"" + evaluatorTeam.idEvaluatorTeam + "\"," +
                            "\"creationDate\":\"" + evaluatorTeam.creationDate + "\"," +
                            "\"emailProfessional\":\"" + evaluatorTeam.emailProfessional + "\"," +
                            "\"emailResponsible\":\"" + evaluatorTeam.emailResponsible + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"idEvaluatorOrganization\":\"" + evaluatorTeam.idEvaluatorOrganization + "\"," +
                            "\"orgTypeEvaluator\":\"" + evaluatorTeam.orgTypeEvaluator + "\"," +
                            "\"idEvaluatedOrganization\":\"" + evaluatorTeam.idEvaluatedOrganization + "\"," +
                            "\"orgTypeEvaluated\":\"" + evaluatorTeam.orgTypeEvaluated + "\"," +
                            "\"idCenter\":\"" + evaluatorTeam.idCenter + "\"," +
                            "\"illness\":\"" + evaluatorTeam.illness + "\"," +
                            "\"otherMembers\":\"" + evaluatorTeam.otherMembers + "\"," +
                            "\"externalConsultant\":\"" + evaluatorTeam.externalConsultant + "\"," +
                            "\"patientName\":\"" + evaluatorTeam.patientName + "\"," +
                            "\"relativeName\":\"" + evaluatorTeam.relativeName + "\"," +
                            "\"observationsSpanish\":\"" + evaluatorTeam.observationsSpanish + "\"," +
                            "\"observationsEnglish\":\"" + evaluatorTeam.observationsEnglish + "\"," +
                            "\"observationsFrench\":\"" + evaluatorTeam.observationsFrench + "\"," +
                            "\"observationsBasque\":\"" + evaluatorTeam.observationsBasque + "\"," +
                            "\"observationsCatalan\":\"" + evaluatorTeam.observationsCatalan + "\"," +
                            "\"observationsDutch\":\"" + evaluatorTeam.observationsDutch + "\"," +
                            "\"observationsGalician\":\"" + evaluatorTeam.observationsGalician + "\"," +
                            "\"observationsGerman\":\"" + evaluatorTeam.observationsGerman + "\"," +
                            "\"observationsItalian\":\"" + evaluatorTeam.observationsItalian + "\"," +
                            "\"observationsPortuguese\":\"" + evaluatorTeam.observationsPortuguese + "\"," +
                            "\"evaluationDates\":\"" + evaluatorTeam.evaluationDates + "\"," +
                            "\"completedEvaluationDates\":\"" + evaluatorTeam.completedEvaluationDates + "\"," +
                            "\"totalEvaluationDates\":\"" + evaluatorTeam.totalEvaluationDates + "\"," +
                            "\"profilePhoto\":\"" + evaluatorTeam.profilePhoto + "\"}";
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
        /// Method that obtains an evaluator team from the database
        /// </summary>
        /// <param name="id">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrg">Evaluator organization identifier (who does the indicators evaluations)</param>
        /// <param name="orgTypeEvaluator">Evaluator organization type (who does the indicators evaluations)</param>
        /// <param name="idEvaluatedOrg">Evaluated organization identifier (who receives the indicators evaluations)</param>
        /// <param name="orgTypeEvaluated">Evaluated organization type (who receives the indicators evaluations)</param>
        /// <param name="idCenter">Evaluated organization center (who receives the indicators evaluations)</param>
        /// <param name="illness">Both organizations illness or syndrome</param>
        /// <returns>Evaluator team if sucess, null if not</returns>

        [HttpGet("get")]
        [Authorize]
        public ActionResult<EvaluatorTeam> Get([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

                if (evaluatorTeam == null)
                    return NotFound();

                return evaluatorTeam;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a new evaluator team to the database
        /// </summary>
        /// <param name="evaluatorTeam">Evaluator team</param>
        /// <returns>Evaluator team if sucess, null if not</returns>
        [HttpPost]
        [Authorize(Policy = "Director")]
        public IActionResult Create([FromBody] EvaluatorTeam evaluatorTeam, [FromHeader] string Authorization)
        {
            try
            {
                _context.EvaluatorTeams.Add(evaluatorTeam);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new
                {
                    id = evaluatorTeam.idEvaluatorTeam,
                    idEvaluatorOrg = evaluatorTeam.idEvaluatorOrganization,
                    orgTypeEvaluator = evaluatorTeam.orgTypeEvaluator,
                    idEvaluatedOrg = evaluatorTeam.idEvaluatedOrganization,
                    orgTypeEvaluated = evaluatorTeam.orgTypeEvaluated,
                    idCenter = evaluatorTeam.idCenter,
                    illness = evaluatorTeam.illness
                }, evaluatorTeam);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an existant evaluator team
        /// </summary>
        /// <param name="id">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrg">Evaluator organization identifier (who does the indicators evaluations)</param>
        /// <param name="orgTypeEvaluator">Evaluator organization type (who does the indicators evaluations)</param>
        /// <param name="idEvaluatedOrg">Evaluated organization identifier (who receives the indicators evaluations)</param>
        /// <param name="orgTypeEvaluated">Evaluated organization type (who receives the indicators evaluations)</param>
        /// <param name="idCenter">Evaluated organization center (who receives the indicators evaluations)</param>
        /// <param name="illness">Both organizations illness or syndrome</param>
        /// <param name="evaluatorTeam">Evaluator team</param>
        /// <returns>Updated evaluator team if sucess, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Director")]
        public IActionResult Update([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness, [FromBody] EvaluatorTeam evaluatorTeam, [FromHeader] string Authorization)
        {
            try
            {
                if (id != evaluatorTeam.idEvaluatorTeam || idEvaluatorOrg != evaluatorTeam.idEvaluatorOrganization || orgTypeEvaluator != evaluatorTeam.orgTypeEvaluator || idEvaluatedOrg != evaluatorTeam.idEvaluatedOrganization || orgTypeEvaluated != evaluatorTeam.orgTypeEvaluated || idCenter != evaluatorTeam.idCenter || illness != evaluatorTeam.illness)
                    return BadRequest();

                var existingEvaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

                if (existingEvaluatorTeam is null)
                    return NotFound();


                existingEvaluatorTeam.idEvaluatorTeam = id;
                existingEvaluatorTeam.creationDate = evaluatorTeam.creationDate;
                existingEvaluatorTeam.idEvaluatorOrganization = idEvaluatorOrg;
                existingEvaluatorTeam.orgTypeEvaluator = orgTypeEvaluator;
                existingEvaluatorTeam.idEvaluatedOrganization = idEvaluatedOrg;
                existingEvaluatorTeam.orgTypeEvaluated = orgTypeEvaluated;
                existingEvaluatorTeam.idCenter = idCenter;
                existingEvaluatorTeam.illness = illness;
                existingEvaluatorTeam.externalConsultant = evaluatorTeam.externalConsultant;
                existingEvaluatorTeam.emailProfessional = evaluatorTeam.emailProfessional;
                existingEvaluatorTeam.emailResponsible = evaluatorTeam.emailResponsible;
                existingEvaluatorTeam.otherMembers = evaluatorTeam.otherMembers;
                existingEvaluatorTeam.patientName = evaluatorTeam.patientName;
                existingEvaluatorTeam.relativeName = evaluatorTeam.relativeName;
                existingEvaluatorTeam.observationsSpanish = evaluatorTeam.observationsSpanish;
                existingEvaluatorTeam.observationsEnglish = evaluatorTeam.observationsEnglish;
                existingEvaluatorTeam.observationsFrench = evaluatorTeam.observationsFrench;
                existingEvaluatorTeam.observationsBasque = evaluatorTeam.observationsBasque;
                existingEvaluatorTeam.observationsCatalan = evaluatorTeam.observationsCatalan;
                existingEvaluatorTeam.observationsDutch = evaluatorTeam.observationsDutch;
                existingEvaluatorTeam.observationsGalician = evaluatorTeam.observationsGalician;
                existingEvaluatorTeam.observationsGerman = evaluatorTeam.observationsGerman;
                existingEvaluatorTeam.observationsItalian = evaluatorTeam.observationsItalian;
                existingEvaluatorTeam.observationsPortuguese = evaluatorTeam.observationsPortuguese;
                existingEvaluatorTeam.evaluationDates = evaluatorTeam.evaluationDates;
                existingEvaluatorTeam.completedEvaluationDates = evaluatorTeam.completedEvaluationDates;
                existingEvaluatorTeam.totalEvaluationDates = evaluatorTeam.totalEvaluationDates;
                existingEvaluatorTeam.profilePhoto= evaluatorTeam.profilePhoto;
                _context.SaveChanges();

                return Ok(existingEvaluatorTeam);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an evaluator team
        /// </summary>
        /// <param name="id">Evaluator team identifier</param>
        /// <param name="idEvaluatorOrg">Evaluator organization identifier (who does the indicators evaluations)</param>
        /// <param name="orgTypeEvaluator">Evaluator organization type (who does the indicators evaluations)</param>
        /// <param name="idEvaluatedOrg">Evaluated organization identifier (who receives the indicators evaluations)</param>
        /// <param name="orgTypeEvaluated">Evaluated organization type (who receives the indicators evaluations)</param>
        /// <param name="idCenter">Evaluated organization center (who receives the indicators evaluations)</param>
        /// <param name="illness">Both organizations illness or syndrome</param>
        /// <returns>Evaluator team if sucess, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Director")]
        public IActionResult Delete([FromQuery] int id, [FromQuery] int idEvaluatorOrg, [FromQuery] string orgTypeEvaluator, [FromQuery] int idEvaluatedOrg, [FromQuery] string orgTypeEvaluated, [FromQuery] int idCenter, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var evaluatorTeam = _context.EvaluatorTeams.FirstOrDefault(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness);

                if (evaluatorTeam is null)
                    return NotFound();

                var indicatorsEvaluationEvidenceRegs = _context.IndicatorsEvaluationsEvidencesRegs.Where(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness).ToList();
                if (indicatorsEvaluationEvidenceRegs is not null)
                {
                    foreach (var reg in indicatorsEvaluationEvidenceRegs)
                    {
                        _context.IndicatorsEvaluationsEvidencesRegs.Remove(reg);
                    }
                }

                var indicatorsEvaluationIndicatorRegs = _context.IndicatorsEvaluationsIndicatorsRegs.Where(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness).ToList();
                if (indicatorsEvaluationIndicatorRegs is not null)
                {
                    foreach (var reg in indicatorsEvaluationIndicatorRegs)
                    {
                        _context.IndicatorsEvaluationsIndicatorsRegs.Remove(reg);
                    }
                }

                var indicatorsEvaluations = _context.IndicatorsEvaluations.Where(e => e.idEvaluatorTeam == id && e.idEvaluatorOrganization == idEvaluatorOrg && e.orgTypeEvaluator == orgTypeEvaluator && e.idEvaluatedOrganization == idEvaluatedOrg && e.orgTypeEvaluated == orgTypeEvaluated && e.idCenter == idCenter && e.illness == illness).ToList();
                if (indicatorsEvaluations is not null)
                {
                    foreach (var indEval in indicatorsEvaluations)
                    {
                        _context.IndicatorsEvaluations.Remove(indEval);
                    }
                }
                
                _context.EvaluatorTeams.Remove(evaluatorTeam);
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
