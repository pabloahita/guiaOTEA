using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("EvaluatorTeams")]
    public class EvaluatorTeamsController : ControllerBase
    {
        private readonly ILogger<EvaluatorTeamsController> _logger;
        private readonly EvaluatorTeamsService _evaluatorTeamsService;

        public EvaluatorTeamsController(ILogger<EvaluatorTeamsController> logger, EvaluatorTeamsService evaluatorTeamsService)
        {
            _logger = logger;
            _evaluatorTeamsService = evaluatorTeamsService;
        }
        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var evaluatorTeams = _evaluatorTeamsService.GetAll();
            return Ok(evaluatorTeams);
        }

        [HttpGet("org::id={id}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByOrganization(int id, string orgType, string illness)
        {
            var evaluatorTeams = _evaluatorTeamsService.GetAllByOrganization(id,orgType,illness);
            return Ok(evaluatorTeams);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeam> Get(int id, int idEvaluatorOrg, string orgType, string illness)
        {
            var evaluatorTeam = _evaluatorTeamsService.Get(id,idEvaluatorOrg,orgType,illness);

            if (evaluatorTeam == null)
                return NotFound();

            return evaluatorTeam;
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] EvaluatorTeam evaluatorTeam)
        {
            _evaluatorTeamsService.Add(evaluatorTeam.idEvaluatorTeam, evaluatorTeam.creationDate, evaluatorTeam.idOrganization, evaluatorTeam.orgType, evaluatorTeam.illness, evaluatorTeam.emailConsultant,evaluatorTeam.emailProfessional, evaluatorTeam.emailResponsible, evaluatorTeam.patientName,evaluatorTeam.relativeName);
            //EvaluatorTeam evaluatorTeam = new EvaluatorTeam(id, creation_date, idOrganization, orgType, illness, emailConsultant, emailProfessional, emailResponsible, patient_name, relative_name);
            return CreatedAtAction(nameof(Get), new { id = evaluatorTeam.idEvaluatorTeam ,idOrganization=evaluatorTeam.idOrganization, orgType=evaluatorTeam.orgType, illness=evaluatorTeam.illness }, evaluatorTeam);
        }

        // PUT action
        [HttpPut("upd::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")]
        public IActionResult Update(int id, int idEvaluatorOrg, string orgType, string illness, [FromBody] EvaluatorTeam evaluatorTeam)
        {
            // This code will update the evaluator team and return a result
            if (id != evaluatorTeam.idEvaluatorTeam || idEvaluatorOrg!=evaluatorTeam.idOrganization || orgType!=evaluatorTeam.orgType || illness!=evaluatorTeam.illness)
                return BadRequest();

            var existingEvaluatorTeam = _evaluatorTeamsService.Get(id, idEvaluatorOrg, orgType, illness);
            if (existingEvaluatorTeam is null)
                return NotFound();

            _evaluatorTeamsService.Update(id,idEvaluatorOrg,orgType,illness,evaluatorTeam);

            return Ok(evaluatorTeam);
        }

        // DELETE action
        [HttpDelete("del::id={id}:idEvaluatorOrg={idEvaluatorOrg}:orgType={orgType}:illness={illness}")]
        public IActionResult Delete(int id, int idEvaluatorOrg, string orgType, string illness)
        {
            // This code will delete the evaluator team and return a result
            var evaluatorTeam = _evaluatorTeamsService.Get(id, idEvaluatorOrg, orgType, illness);

            if (evaluatorTeam is null)
                return NotFound();

            _evaluatorTeamsService.Delete(id, idEvaluatorOrg, orgType, illness);

            return NoContent();
        }
    }
}
