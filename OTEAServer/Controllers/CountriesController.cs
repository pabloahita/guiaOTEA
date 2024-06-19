using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Text.Json;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller for countries operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("Countries")]
    public class CountriesController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public CountriesController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all the countries in language order
        /// </summary>
        /// <param name="language">App language (english, spanish, french, basque, catalan, dutch, galician, german, italian and portuguese)</param>
        /// <returns>Country list</returns>
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] string language)
        {
            try
            {
                IQueryable<Country> query = _context.Countries.AsQueryable();

                switch (language)
                {
                    case "es":
                        query = query.OrderByDescending(c => c.idCountry == "ESP")
                                    .ThenByDescending(c => c.idCountry == "AND")
                                    .ThenByDescending(c => c.idCountry == "ARG")
                                    .ThenByDescending(c => c.idCountry == "BOL")
                                    .ThenByDescending(c => c.idCountry == "CHL")
                                    .ThenByDescending(c => c.idCountry == "COL")
                                    .ThenByDescending(c => c.idCountry == "CRI")
                                    .ThenByDescending(c => c.idCountry == "CUB")
                                    .ThenByDescending(c => c.idCountry == "ECU")
                                    .ThenByDescending(c => c.idCountry == "SLV")
                                    .ThenByDescending(c => c.idCountry == "GUA")
                                    .ThenByDescending(c => c.idCountry == "GNQ")
                                    .ThenByDescending(c => c.idCountry == "HND")
                                    .ThenByDescending(c => c.idCountry == "MEX")
                                    .ThenByDescending(c => c.idCountry == "NIC")
                                    .ThenByDescending(c => c.idCountry == "PAN")
                                    .ThenByDescending(c => c.idCountry == "PRY")
                                    .ThenByDescending(c => c.idCountry == "PER")
                                    .ThenByDescending(c => c.idCountry == "PRI")
                                    .ThenByDescending(c => c.idCountry == "DOM")
                                    .ThenByDescending(c => c.idCountry == "URY")
                                    .ThenByDescending(c => c.idCountry == "VEN")
                                    .ThenBy(c => c.nameSpanish); break;
                    case "fr":
                        query = query.OrderByDescending(c => c.idCountry == "FRA")
                                    .ThenByDescending(c => c.idCountry == "MCO")
                                    .ThenByDescending(c => c.idCountry == "BEL")
                                    .ThenByDescending(c => c.idCountry == "CHE")
                                    .ThenByDescending(c => c.idCountry == "LUX")
                                    .ThenByDescending(c => c.idCountry == "CAN")
                                    .ThenByDescending(c => c.idCountry == "MAR")
                                    .ThenByDescending(c => c.idCountry == "DZA")
                                    .ThenBy(c => c.nameFrench); break;
                    case "eu":
                        query = query.OrderByDescending(c => c.idCountry == "ESP")
                                    .ThenByDescending(c => c.idCountry == "FRA")
                                    .ThenByDescending(c => c.idCountry == "AND")
                                    .ThenByDescending(c => c.idCountry == "ARG")
                                    .ThenByDescending(c => c.idCountry == "BOL")
                                    .ThenByDescending(c => c.idCountry == "CHL")
                                    .ThenByDescending(c => c.idCountry == "COL")
                                    .ThenByDescending(c => c.idCountry == "CRI")
                                    .ThenByDescending(c => c.idCountry == "CUB")
                                    .ThenByDescending(c => c.idCountry == "ECU")
                                    .ThenByDescending(c => c.idCountry == "SLV")
                                    .ThenByDescending(c => c.idCountry == "GUA")
                                    .ThenByDescending(c => c.idCountry == "GNQ")
                                    .ThenByDescending(c => c.idCountry == "HND")
                                    .ThenByDescending(c => c.idCountry == "MEX")
                                    .ThenByDescending(c => c.idCountry == "NIC")
                                    .ThenByDescending(c => c.idCountry == "PAN")
                                    .ThenByDescending(c => c.idCountry == "PRY")
                                    .ThenByDescending(c => c.idCountry == "PER")
                                    .ThenByDescending(c => c.idCountry == "PRI")
                                    .ThenByDescending(c => c.idCountry == "DOM")
                                    .ThenByDescending(c => c.idCountry == "URY")
                                    .ThenByDescending(c => c.idCountry == "VEN")
                                    .ThenBy(c => c.nameBasque); break;
                    case "ca":
                        query = query.OrderByDescending(c => c.idCountry == "ESP")
                                    .ThenByDescending(c => c.idCountry == "AND")
                                    .ThenByDescending(c => c.idCountry == "ARG")
                                    .ThenByDescending(c => c.idCountry == "BOL")
                                    .ThenByDescending(c => c.idCountry == "CHL")
                                    .ThenByDescending(c => c.idCountry == "COL")
                                    .ThenByDescending(c => c.idCountry == "CRI")
                                    .ThenByDescending(c => c.idCountry == "CUB")
                                    .ThenByDescending(c => c.idCountry == "ECU")
                                    .ThenByDescending(c => c.idCountry == "SLV")
                                    .ThenByDescending(c => c.idCountry == "GUA")
                                    .ThenByDescending(c => c.idCountry == "GNQ")
                                    .ThenByDescending(c => c.idCountry == "HND")
                                    .ThenByDescending(c => c.idCountry == "MEX")
                                    .ThenByDescending(c => c.idCountry == "NIC")
                                    .ThenByDescending(c => c.idCountry == "PAN")
                                    .ThenByDescending(c => c.idCountry == "PRY")
                                    .ThenByDescending(c => c.idCountry == "PER")
                                    .ThenByDescending(c => c.idCountry == "PRI")
                                    .ThenByDescending(c => c.idCountry == "DOM")
                                    .ThenByDescending(c => c.idCountry == "URY")
                                    .ThenByDescending(c => c.idCountry == "VEN")
                                    .ThenBy(c => c.nameCatalan); break;
                    case "gl":
                        query = query.OrderByDescending(c => c.idCountry == "ESP")
                                    .ThenByDescending(c => c.idCountry == "AND")
                                    .ThenByDescending(c => c.idCountry == "ARG")
                                    .ThenByDescending(c => c.idCountry == "BOL")
                                    .ThenByDescending(c => c.idCountry == "CHL")
                                    .ThenByDescending(c => c.idCountry == "COL")
                                    .ThenByDescending(c => c.idCountry == "CRI")
                                    .ThenByDescending(c => c.idCountry == "CUB")
                                    .ThenByDescending(c => c.idCountry == "ECU")
                                    .ThenByDescending(c => c.idCountry == "SLV")
                                    .ThenByDescending(c => c.idCountry == "GUA")
                                    .ThenByDescending(c => c.idCountry == "GNQ")
                                    .ThenByDescending(c => c.idCountry == "HND")
                                    .ThenByDescending(c => c.idCountry == "MEX")
                                    .ThenByDescending(c => c.idCountry == "NIC")
                                    .ThenByDescending(c => c.idCountry == "PAN")
                                    .ThenByDescending(c => c.idCountry == "PRY")
                                    .ThenByDescending(c => c.idCountry == "PER")
                                    .ThenByDescending(c => c.idCountry == "PRI")
                                    .ThenByDescending(c => c.idCountry == "DOM")
                                    .ThenByDescending(c => c.idCountry == "URY")
                                    .ThenByDescending(c => c.idCountry == "VEN")
                                    .ThenBy(c => c.nameGalician); break;
                    case "pt":
                        query = query.OrderByDescending(c => c.idCountry == "PRT")
                                    .ThenByDescending(c => c.idCountry == "BRA")
                                    .ThenByDescending(c => c.idCountry == "GNB")
                                    .ThenByDescending(c => c.idCountry == "MOZ")
                                    .ThenByDescending(c => c.idCountry == "STP")
                                    .ThenByDescending(c => c.idCountry == "TLS")
                                    .ThenBy(c => c.namePortuguese); break;
                    case "de":
                        query = query.OrderByDescending(c => c.idCountry == "DEU")
                                    .ThenByDescending(c => c.idCountry == "AUT")
                                    .ThenByDescending(c => c.idCountry == "BEL")
                                    .ThenByDescending(c => c.idCountry == "LUX")
                                    .ThenByDescending(c => c.idCountry == "LIE")
                                    .ThenByDescending(c => c.idCountry == "CHE")
                                    .ThenBy(c => c.nameGerman); break;
                    case "it":
                        query = query.OrderByDescending(c => c.idCountry == "ITA")
                                    .ThenByDescending(c => c.idCountry == "CHE")
                                    .ThenByDescending(c => c.idCountry == "SMR")
                                    .ThenByDescending(c => c.idCountry == "VAT")
                                    .ThenBy(c => c.nameItalian); break;
                    case "nl":
                        query = query.OrderByDescending(c => c.idCountry == "NLD")
                                    .ThenByDescending(c => c.idCountry == "BEL")
                                    .ThenByDescending(c => c.idCountry == "FRA")
                                    .ThenByDescending(c => c.idCountry == "ABW")
                                    .ThenByDescending(c => c.idCountry == "SUR")
                                    .ThenBy(c => c.nameDutch); break;
                    default:
                        query = query.OrderByDescending(c => c.idCountry == "USA")
                                    .ThenByDescending(c => c.idCountry == "GBR")
                                    .ThenByDescending(c => c.idCountry == "AUS")
                                    .ThenByDescending(c => c.idCountry == "CAN")
                                    .ThenByDescending(c => c.idCountry == "GIB")
                                    .ThenByDescending(c => c.idCountry == "IOT")
                                    .ThenByDescending(c => c.idCountry == "IND")
                                    .ThenByDescending(c => c.idCountry == "IRL")
                                    .ThenByDescending(c => c.idCountry == "NZF")
                                    .ThenByDescending(c => c.idCountry == "ZAF")
                                    .ThenBy(c => c.nameEnglish); break;
                }

                var countries = query.ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (Country country in countries)
                {
                    String rg = "{\"idCountry\":\"" + country.idCountry + "\"," +
                            "\"nameSpanish\":\"" + country.nameSpanish + "\"," +
                            "\"nameEnglish\":\"" + country.nameEnglish + "\"," +
                            "\"nameFrench\":\"" + country.nameFrench + "\"," +
                            "\"nameBasque\":\"" + country.nameBasque + "\"," +
                            "\"nameCatalan\":\"" + country.nameCatalan + "\"," +
                            "\"nameDutch\":\"" + country.nameDutch + "\"," +
                            "\"nameGalician\":\"" + country.nameGalician + "\"," +
                            "\"nameGerman\":\"" + country.nameGerman + "\"," +
                            "\"nameItalian\":\"" + country.nameItalian + "\"," +
                            "\"namePortuguese\":\"" + country.namePortuguese + "\"," +
                            "\"phone_code\":\"" + country.phone_code + "\"," +
                            "\"flag\":\"" + country.flag + "\"}";
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
        /// Method that obtains the country from the database
        /// </summary>
        /// <param name="idCountry">Country identifier</param>
        /// <returns>Country if exists, null if not</returns>

        [HttpGet("get")]
        public ActionResult<Country> Get([FromQuery] string idCountry)
        {
            try
            {
                var country = _context.Countries.FirstOrDefault(c => c.idCountry == idCountry);

                if (country == null)
                    return NotFound();

                return country;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
