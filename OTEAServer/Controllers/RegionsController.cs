using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Text.Json;
using static Microsoft.EntityFrameworkCore.DbLoggerCategory;
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
        public IActionResult GetAllByCountry([FromQuery] string idCountry, [FromQuery] string language)
        {
            try
            {
                IQueryable<Region> query = _context.Regions.Where(r => r.idCountry == idCountry).AsQueryable();

                switch (language)
                {
                    case "es":
                        query = query.OrderBy(r => r.nameSpanish); break;
                    case "fr":
                        query = query.OrderBy(r => r.nameFrench); break;
                    case "eu":
                        query = query.OrderBy(r => r.nameBasque); break;
                    case "ca":
                        query = query.OrderBy(r => r.nameCatalan); break;
                    case "gl":
                        query = query.OrderBy(r => r.nameGalician); break;
                    case "pt":
                        query = query.OrderBy(r => r.namePortuguese); break;
                    case "de":
                        query = query.OrderBy(r => r.nameGerman); break;
                    case "it":
                        query = query.OrderBy(r => r.nameItalian); break;
                    case "nl":
                        query = query.OrderBy(r => r.nameDutch); break;
                    default:
                        query = query.OrderBy(r => r.nameEnglish); break;
                }
                var regions = query.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (Region region in regions)
                {
                    String rg = "{\"idRegion\":\"" + region.idRegion + "\"," +
                            "\"nameSpanish\":\"" + region.nameSpanish + "\"," +
                            "\"nameEnglish\":\"" + region.nameEnglish + "\"," +
                            "\"nameFrench\":\"" + region.nameFrench + "\"," +
                            "\"nameBasque\":\"" + region.nameBasque + "\"," +
                            "\"nameCatalan\":\"" + region.nameCatalan + "\"," +
                            "\"nameDutch\":\"" + region.nameDutch + "\"," +
                            "\"nameGalician\":\"" + region.nameGalician + "\"," +
                            "\"nameGerman\":\"" + region.nameGerman + "\"," +
                            "\"nameItalian\":\"" + region.nameItalian + "\"," +
                            "\"namePortuguese\":\"" + region.namePortuguese + "\"}";
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
