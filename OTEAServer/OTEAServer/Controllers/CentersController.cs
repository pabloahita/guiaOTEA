using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using OTEAServer.Models;
using OTEAServer.Services;
using System.Data.SqlClient;
using System.Security.Policy;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Centers")]
    public class CentersController : ControllerBase
    {
        private readonly ILogger<CentersController> _logger;
        private readonly CentersService _centersService;

        public CentersController(ILogger<CentersController> logger, CentersService centersService)
        {
            _logger = logger;
            _centersService = centersService;
        }

        [HttpGet]
        public IActionResult GetAll() {
            var centers = _centersService.GetAll();
            return Ok(centers);
        }

        [HttpGet("org::idOrganization={idOrganization}:orgType={orgType}:illness={illness}")]
        public IActionResult GetAllByOrganization(int idOrganization, string orgType, string illness) {
            var centers = _centersService.GetAllByOrganization(idOrganization,orgType,illness);
            return Ok(centers);
        }

        [HttpGet("get::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")]
        public ActionResult<Center> Get(int idOrganization, string orgType, string illness, int idCenter)
        {
            var center = _centersService.Get(idOrganization, orgType, illness,idCenter);
            if(center == null)
            {
                return NotFound();
            }
            return center;
        }

        [HttpPost]
        public ActionResult<Center> Create([FromBody] Center center)
        {
            _centersService.Add(center.idOrganization, center.orgType, center.illness, center.idCenter, center.centerDescription, center.idAddress, center.telephone);
            //Center center = new Center(idOrganization, orgType, illness, idCenter, centerDescription, idAddress, telephone);
            return CreatedAtAction(nameof(Get), new { idOrganization = center.idOrganization, orgType = center.orgType, illness = center.illness, idCenter = center.idCenter }, center);
        }

        [HttpPut("put::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")]
        public ActionResult<Center> Update(int idOrganization, string orgType, string illness, int idCenter, Center center)
        {
            if (idOrganization != center.idOrganization || orgType != center.orgType || illness != center.illness || idCenter != center.idCenter)
                return BadRequest();

            var existingOrganization = _centersService.Get(idOrganization, orgType, illness,idCenter);
            if (existingOrganization is null)
                return NotFound();

            _centersService.Update(idOrganization,orgType,illness,idCenter,center);

            return Ok(center);
        }

        [HttpDelete("del::idOrganization={idOrganization}:orgType={orgType}:illness={illness}:idCenter={idCenter}")]
        public ActionResult<Center> Delete(int idOrganization, string orgType, string illness, int idCenter)
        {
            var center = _centersService.Get(idOrganization, orgType, illness, idCenter);

            if (center is null)
                return NotFound();

            _centersService.Delete(idOrganization, orgType, illness,idCenter);

            return NoContent();
        }

    }

    
}

