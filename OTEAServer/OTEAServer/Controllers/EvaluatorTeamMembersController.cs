using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("EvaluatorTeamMembers")]
    public class EvaluatorTeamMembersController : ControllerBase
    {
        private readonly ILogger<EvaluatorTeamMembersController> _logger;
        private readonly EvaluatorTeamMembersService _evaluatorTeamMembersService;

        public EvaluatorTeamMembersController(ILogger<EvaluatorTeamMembersController> logger, EvaluatorTeamMembersService evaluatorTeamsService)
        {
            _logger = logger;
            _evaluatorTeamMembersService = evaluatorTeamsService;
        }
        [HttpGet]
        public IActionResult GetAll() {
            var evaluatorTeamMembers = _evaluatorTeamMembersService.GetAll();
            return Ok(evaluatorTeamMembers);
        }

        [HttpGet("evalTeam::idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
            var evaluatorTeamMembers = _evaluatorTeamMembersService.GetAllByEvaluatorTeam(idEvaluatorTeam,idEvaluatorOrganization,orgType,illness);
            return Ok(evaluatorTeamMembers);
        }

        [HttpGet("get::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeamMember> Get(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
            var evaluatorTeamMember = _evaluatorTeamMembersService.Get(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness);

            if (evaluatorTeamMember == null)
                return NotFound();

            return evaluatorTeamMember;
        }

        [HttpPost]
        public ActionResult<EvaluatorTeamMember> Create([FromBody] EvaluatorTeamMember evaluatorTeamMember) {
            _evaluatorTeamMembersService.Add(evaluatorTeamMember.emailUser, evaluatorTeamMember.idEvaluatorTeam, evaluatorTeamMember.idEvaluatorOrganization, evaluatorTeamMember.orgType, evaluatorTeamMember.illness);
            //EvaluatorTeamMember evaluatorTeamMember = new EvaluatorTeamMember(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness);
            return CreatedAtAction(nameof(Get), new { emailUser = evaluatorTeamMember.emailUser, idEvaluatorTeam = evaluatorTeamMember.idEvaluatorTeam, idEvaluatorOrganization = evaluatorTeamMember.idEvaluatorOrganization, orgType = evaluatorTeamMember.orgType, illness = evaluatorTeamMember.illness }, evaluatorTeamMember);
        }

        [HttpPut("upd::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeamMember> Update(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness, [FromBody] EvaluatorTeamMember evaluatorTeamMember) {
            // This code will update the evaluator team and return a result
            if (emailUser != evaluatorTeamMember.emailUser || idEvaluatorTeam != evaluatorTeamMember.idEvaluatorTeam || idEvaluatorOrganization != evaluatorTeamMember.idEvaluatorOrganization || orgType != evaluatorTeamMember.orgType || illness != evaluatorTeamMember.illness)
                return BadRequest();

            var existingEvaluatorTeam = _evaluatorTeamMembersService.Get(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness);
            if (existingEvaluatorTeam is null)
                return NotFound();

            _evaluatorTeamMembersService.Update(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness, evaluatorTeamMember);

            return Ok(evaluatorTeamMember);
        }

        [HttpDelete("del::emailUser={emailUser}:idEvaluatorTeam={idEvaluatorTeam}:idEvaluatorOrganization={idEvaluatorOrganization}:orgType={orgType}:illness={illness}")]
        public ActionResult<EvaluatorTeamMember> Delete(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness) {
            // This code will delete the evaluator team member and return a result
            var evaluatorTeamMember = _evaluatorTeamMembersService.Get(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness);

            if (evaluatorTeamMember is null)
                return NotFound();

            _evaluatorTeamMembersService.Delete(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness);

            return NoContent();
        }
    }
}
