using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Linq.Expressions;
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

        private class RegionDto
        {
            public int IdRegion { get; set; }
            public string Name { get; set; }
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

                Expression<Func<Region, RegionDto>> selector = r => new RegionDto
                {
                    IdRegion = r.idRegion,
                    Name = r.nameEnglish // Default value
                };
                switch (language)
                {
                    case "es":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameSpanish
                        };
                        query = query.OrderBy(r => r.nameSpanish); break;
                    case "fr":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameFrench
                        };
                        query = query.OrderBy(r => r.nameFrench); break;
                    case "eu":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameBasque
                        };
                        query = query.OrderBy(r => r.nameBasque); break;
                    case "ca":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameCatalan
                        };
                        query = query.OrderBy(r => r.nameCatalan); break;
                    case "gl":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameGalician
                        };
                        query = query.OrderBy(r => r.nameGalician); break;
                    case "pt":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.namePortuguese
                        };
                        query = query.OrderBy(r => r.namePortuguese); break;
                    case "de":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameGerman
                        };
                        query = query.OrderBy(r => r.nameGerman); break;
                    case "it":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameItalian
                        };
                        query = query.OrderBy(r => r.nameItalian); break;
                    case "nl":
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameDutch
                        };
                        query = query.OrderBy(r => r.nameDutch); break;
                    default:
                        selector = r => new RegionDto
                        {
                            IdRegion = r.idRegion,
                            Name = r.nameEnglish // Default value
                        };
                        query = query.OrderBy(r => r.nameEnglish); break;
                }
                var regions = query.Select(selector).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (RegionDto region in regions)
                {
                    String rg = "{\"idRegion\":\"" + region.IdRegion + "\"," +
                            "\"name\":\"" + region.Name + "\"}";
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
