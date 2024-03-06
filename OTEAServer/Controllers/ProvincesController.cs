using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for provinces operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Provinces")]
    public class ProvincesController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public ProvincesController(DatabaseContext context)
        {
            _context=context;
        }

        /// <summary>
        /// Method that obtains all provinces of a region
        /// </summary>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <returns>Provinces list</returns>
        [HttpGet("allByRegion")]
        public IActionResult GetAllByRegion([FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            try
            {
                var provinces = _context.Provinces.Where(p => p.idRegion == idRegion && p.idCountry == idCountry).ToList();
                return Ok(provinces);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains a province from the database
        /// </summary>
        /// <param name="idProvince">Province identifier</param>
        /// <param name="idRegion">Region identifier</param>
        /// <param name="idCountry">Country identifier</param>
        /// <returns></returns>
        [HttpGet("get")]
        public ActionResult<Province> Get([FromQuery] int idProvince, [FromQuery] int idRegion, [FromQuery] string idCountry)
        {
            try
            {
                var province = _context.Provinces.FirstOrDefault(p => p.idProvince == idProvince && p.idRegion == idRegion && p.idCountry == idCountry);

                if (province == null)
                    return NotFound();

                return province;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

       
    }
}
