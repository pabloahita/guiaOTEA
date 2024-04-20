using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for regions operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Regions")]
    public class RegionsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public RegionsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all regions of a country
        /// </summary>
        /// <param name="idCountry">Country identifier</param>
        /// <returns>Regions list</returns>
        [HttpGet("allByCountry")]
        public IActionResult GetAllByCountry([FromQuery] string idCountry)
        {
            try
            {
                var regions = _context.Regions.Where(r => r.idCountry == idCountry).ToList();
                return Ok(regions);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains a region from the database
        /// </summary>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <returns></returns>
        [HttpGet("get")]
        public ActionResult<Region> Get([FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            try
            {
                var region = _context.Regions.FirstOrDefault(r => r.idRegion == idRegion && r.idCountry == idCountry);

                if (region == null)
                    return NotFound();

                return region;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
