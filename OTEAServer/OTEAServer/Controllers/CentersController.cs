using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data.SqlClient;
using System.Security.Policy;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Centers")]
    public class CentersController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public CentersController(DatabaseContext context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult GetAll() {
            var centers = _context.Centers.ToList();
            return Ok(centers);
        }

        [HttpGet("org::idOrganization={idOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByOrganization(int idOrganization, string orgType, string illness) {
            var centers = _context.Centers.Where(c=>c.idOrganization== idOrganization && c.orgType==orgType && c.illness==illness).ToList();
            return Ok(centers);
        }

        [HttpGet("get::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")]
        public ActionResult<Center> Get(int idOrganization, string orgType, string illness, int idCenter)
        {
            var center = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);
            if(center == null)
            {
                return NotFound();
            }
            return center;
        }

        [HttpPost]
        public ActionResult<Center> Create([FromBody] Center center)
        {
            _context.Centers.Add(center);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { idOrganization = center.idOrganization, orgType = center.orgType, illness = center.illness, idCenter = center.idCenter }, center);
        }

        [HttpPut("put::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")]
        public ActionResult<Center> Update(int idOrganization, string orgType, string illness, int idCenter, Center center)
        {
            if (idOrganization != center.idOrganization || orgType != center.orgType || illness != center.illness || idCenter != center.idCenter)
                return BadRequest();

            var existingCenter = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);
            if (existingCenter is null)
                return NotFound();

            existingCenter.idOrganization = idOrganization;
            existingCenter.orgType = orgType;
            existingCenter.illness = illness;
            existingCenter.idCenter = idCenter;
            existingCenter.centerDescription=center.centerDescription;
            existingCenter.idAddress = center.idAddress;
            existingCenter.telephone = center.telephone;
            existingCenter.email=center.email;

            return Ok(existingCenter);
        }

        [HttpDelete("del::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")]
        public ActionResult<Center> Delete(int idOrganization, string orgType, string illness, int idCenter)
        {
            var center = _context.Centers.FirstOrDefault(c => c.idOrganization == idOrganization && c.orgType == orgType && c.illness == illness && c.idCenter == idCenter);

            if (center is null)
                return NotFound();

            _context.Centers.Remove(center);
            _context.SaveChanges();
            return NoContent();
        }

    }

    
}

