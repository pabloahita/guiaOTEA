using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Organizations")]
    public class OrganizationsController : ControllerBase
    {
        private readonly ILogger<OrganizationsController> _logger;
        private readonly OrganizationsService _organizationsService;

        public OrganizationsController(ILogger<OrganizationsController> logger, OrganizationsService organizationsService)
        {
            _logger = logger;
            _organizationsService = organizationsService;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var organizations = _organizationsService.GetAll();
            return Ok(organizations);
        }


        // GET all evaluated organizations action
        [HttpGet("evaluated")]
        public IActionResult GetAllEvaluatedOrganizations()
        {
            var organizations = _organizationsService.GetAllEvaluatedOrganizations();
            return Ok(organizations);
        }

        // GET all evaluator organizations action
        [HttpGet("evaluator")]
        public IActionResult GetAllEvaluatorOrganizations()
        {
            var organizations = _organizationsService.GetAllEvaluatorOrganizations();
            return Ok(organizations);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("id={id}:orgType={orgType}:illness={illness}")]
        public ActionResult<Organization> Get(int id,string orgType,string illness)
        {
            var organization = _organizationsService.Get(id,orgType,illness);

            if (organization == null)
                return NotFound();

            return organization;
        }


        // GET by ID AND ORGTYPE action

        [HttpGet("evaluated::id={id}:illness={illness}")]
        public ActionResult<Organization> GetEvaluatedOrganizationById(int id, string illness)
        {
            var organization = _organizationsService.GetEvaluatedOrganizationById(id,illness);

            if (organization == null)
                return NotFound();

            return organization;
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("evaluator::id={id}:illness={illness}")]
        public ActionResult<Organization> GetEvaluatorOrganizationById(int id, string illness)
        {
            var organization = _organizationsService.GetEvaluatorOrganizationById(id, illness);

            if (organization == null)
                return NotFound();

            return organization;
        }

        // POST action
        [HttpPost]
        public IActionResult Create(int id, string orgType, string illness, string name, int idAddress, string email, int telephone, string information, string emailOrgPrincipal, string emailOrgConsultant)
        {
            _organizationsService.Add(id, orgType, illness, name, idAddress, email, telephone, information, emailOrgPrincipal, emailOrgConsultant);
            Organization organization = new Organization(id, orgType, illness, name, idAddress, email, telephone, information, emailOrgPrincipal, emailOrgConsultant);
            return CreatedAtAction(nameof(Get), new { id = organization.Id, type=organization.OrgType }, organization);
        }

        // PUT action
        [HttpPut("id={id}:orgType={orgType}:illness={illness}")]
        public IActionResult Update(int id,string orgType,string illness, Organization organization)
        {
            // This code will update the mesa and return a result
            if (id != organization.Id || orgType!=organization.OrgType || illness!=organization.Illness)
                return BadRequest();

            var existingOrganization = _organizationsService.Get(id, orgType, illness);
            if (existingOrganization is null)
                return NotFound();

            _organizationsService.Update(organization);

            return NoContent();
        }

        // DELETE action
        [HttpDelete("id={id}:orgType={orgType}:illness={illness}")]
        public IActionResult Delete(int id, string orgType, string illness)
        {
            // This code will delete the mesa and return a result
            var organization = _organizationsService.Get(id,orgType, illness);

            if (organization is null)
                return NotFound();

            _organizationsService.Delete(id,orgType, illness);

            return NoContent();
        }
    }
}
