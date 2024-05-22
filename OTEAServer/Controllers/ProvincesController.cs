using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Text.Json;

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
        public IActionResult GetAllByRegion([FromQuery] int idRegion, [FromQuery] string idCountry, [FromQuery] string language)
        {
            try
            {
                IQueryable<Province> query = _context.Provinces
                    .Where(p => p.idRegion == idRegion && p.idCountry == idCountry)
                    .AsQueryable();

                switch (language)
                {
                    case "es":
                        query = query.OrderBy(p => p.nameSpanish); break;
                    case "fr":
                        query = query.OrderBy(p => p.nameFrench); break;
                    case "eu":
                        query = query.OrderBy(p => p.nameBasque); break;
                    case "ca":
                        query = query.OrderBy(p => p.nameCatalan); break;
                    case "gl":
                        query = query.OrderBy(p => p.nameGalician); break;
                    case "pt":
                        query = query.OrderBy(p => p.namePortuguese); break;
                    case "de":
                        query = query.OrderBy(p => p.nameGerman); break;
                    case "it":
                        query = query.OrderBy(p => p.nameItalian); break;
                    case "nl":
                        query = query.OrderBy(p => p.nameDutch); break;
                    default:
                        query = query.OrderBy(p => p.nameEnglish); break;
                }
                var provinces=query.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (Province province in provinces)
                {
                    String rg = "{\"idProvince\":\"" + province.idProvince + "\"," +
                            "\"nameSpanish\":\"" + province.nameSpanish + "\"," +
                            "\"nameEnglish\":\"" + province.nameEnglish + "\"," +
                            "\"nameFrench\":\"" + province.nameFrench + "\"," +
                            "\"nameBasque\":\"" + province.nameBasque + "\"," +
                            "\"nameCatalan\":\"" + province.nameCatalan + "\"," +
                            "\"nameDutch\":\"" + province.nameDutch + "\"," +
                            "\"nameGalician\":\"" + province.nameGalician + "\"," +
                            "\"nameGerman\":\"" + province.nameGerman + "\"," +
                            "\"nameItalian\":\"" + province.nameItalian + "\"," +
                            "\"namePortuguese\":\"" + province.namePortuguese + "\"}";
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
