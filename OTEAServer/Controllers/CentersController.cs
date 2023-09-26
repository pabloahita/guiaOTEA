using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Data.SqlClient;
using System.Net;
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

        [HttpGet("all")]
        public IActionResult GetAll() {
            var centers = _context.Centers.ToList();
            return Ok(centers);
        }

        [HttpGet("org")]
        public IActionResult GetAllByOrganization([FromQuery] int idOrganization,[FromQuery] string orgType,[FromQuery] string illness) {
            var centers = _context.Centers.Where(c=>c.idOrganization== idOrganization && c.orgType==orgType && c.illness==illness).ToList();
            return Ok(centers);
        }

        [HttpGet("get")]
        public ActionResult<Center> Get([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness,[FromQuery] int idCenter)
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

        [HttpPut]
        public ActionResult<Center> Update([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int idCenter,[FromBody] Center center)
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
            existingCenter.descriptionSpanish = center.descriptionSpanish;
            existingCenter.descriptionEnglish = center.descriptionEnglish;
            existingCenter.descriptionFrench = center.descriptionFrench;
            existingCenter.descriptionBasque = center.descriptionBasque;
            existingCenter.descriptionCatalan = center.descriptionCatalan;
            existingCenter.descriptionDutch = center.descriptionDutch;
            existingCenter.descriptionGalician = center.descriptionGalician;
            existingCenter.descriptionGerman = center.descriptionGerman;
            existingCenter.descriptionItalian = center.descriptionItalian;
            existingCenter.descriptionPortuguese = center.descriptionPortuguese;
            existingCenter.telephone = center.telephone;
            existingCenter.idAddress = center.idAddress;
            existingCenter.email = center.email;
            _context.SaveChanges();

            return Ok(existingCenter);
        }

        [HttpDelete]
        public ActionResult<Center> Delete([FromQuery] int idOrganization, [FromQuery] string orgType, [FromQuery] string illness, [FromQuery] int idCenter)
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

