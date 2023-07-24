using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Organizations")]
    public class OrganizationsController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public OrganizationsController(DatabaseContext context)
        {
            _context = context;
        }


        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var organizations = _context.Organizations.ToList();
            return Ok(organizations);
        }


        // GET all evaluated organizations action
        [HttpGet("evaluated")]
        public IActionResult GetAllEvaluatedOrganizations()
        {
            var organizations = _context.Organizations.Where(o => o.orgType == "EVALUATED").ToList();
            return Ok(organizations);
        }

        // GET all evaluator organizations action
        [HttpGet("evaluator")]
        public IActionResult GetAllEvaluatorOrganizations()
        {
            var organizations = _context.Organizations.Where(o => o.orgType == "EVALUATOR").ToList();
            return Ok(organizations);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get::id={id}:orgType={orgType}:illness={illness}")]
        public ActionResult<Organization> Get(int id,string orgType,string illness)
        {
            var organization = _context.Organizations.FirstOrDefault(o=>o.IdOrganization==id && o.orgType==orgType && o.illness==illness);

            if (organization == null)
                return NotFound();

            return organization;
        }


        // GET by ID AND ORGTYPE action

        [HttpGet("evaluated::id={id}:illness={illness}")]
        public ActionResult<Organization> GetEvaluatedOrganizationById(int id, string illness)
        {
            return Get(id, "EVALUATED", illness);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("evaluator::id={id}:illness={illness}")]
        public ActionResult<Organization> GetEvaluatorOrganizationById(int id, string illness)
        {
            return Get(id, "EVALUATOR", illness);
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Organization organization)
        {
           _context.Organizations.Add(organization);
           _context.SaveChanges();
           return CreatedAtAction(nameof(Get), new { id = organization.IdOrganization, organizationType=organization.orgType, illness=organization.illness }, organization);
        }

        // PUT action
        [HttpPut("upd::id={id}:orgType={orgType}:illness={illness}")]
        public IActionResult Update(int id,string orgType,string illness, [FromBody] Organization organization)
        {
            // This code will update the mesa and return a result
            if (id != organization.IdOrganization || orgType!=organization.orgType || illness!=organization.illness)
                return BadRequest();

            var existingOrganization = _context.Organizations.FirstOrDefault(o => o.IdOrganization == id && o.orgType == orgType && o.illness == illness);
            if (existingOrganization is null)
                return NotFound();

            _context.Organizations.Update(organization);
            _context.SaveChanges();

            return Ok(organization);
        }

        // DELETE action
        [HttpDelete("del::id={id}:orgType={orgType}:illness={illness}")]
        public IActionResult Delete(int id, string orgType, string illness)
        {
            // This code will delete the mesa and return a result
            var organization = _context.Organizations.FirstOrDefault(o => o.IdOrganization == id && o.orgType == orgType && o.illness == illness);

            if (organization is null)
                return NotFound();

            _context.Organizations.Remove(organization);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
