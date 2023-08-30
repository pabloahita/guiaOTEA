using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Countries")]
    public class CountriesController : ControllerBase
    {
        private readonly DatabaseContext _context;

        public CountriesController(DatabaseContext context)
        {
            _context = context;
        }

        // GET all by country action
        [HttpGet("all")]
        public IActionResult GetAll([FromQuery] string language)
        {
            /*var countries = _context.Countries.ToList();
            return Ok(countries);*/

            IQueryable<Country> query = _context.Countries.AsQueryable();

            switch(language){
                case "es": query=query.OrderBy(c=>c.nameSpanish);break;
                case "fr": query=query.OrderBy(c=>c.nameFrench);break;
                /*case "eu": query=query.OrderBy(c=>c.nameBasque);break; Poner cuando se implementen más idiomas en la base de datos
                case "ca": query=query.OrderBy(c=>c.nameCatalan);break;
                case "gl": query=query.OrderBy(c=>c.nameGalician);break;
                case "pt": query=query.OrderBy(c=>c.namePortuguese);break;
                case "de": query=query.OrderBy(c=>c.nameDeustch);break;
                case "it": query=query.OrderBy(c=>c.nameItalian);break;
                case "nl": query=query.OrderBy(c=>c.nameDutch);break;*/
                default: query=query.OrderBy(c=>c.nameEnglish);break;
            }

            var list=query.ToList();
            List<string> firstCountries=new List<string>();
            switch(language){
                case "es": firstCountries=new List<string>{"ESP","AND","USA","ARG","BOL","CHL","COL","CRI","CUB","ECU","SLV","GUA","GNQ","HND","MEX","NIC","PAN","PRY","PER","PRI","DOM","URY","VEN"};break;
                case "fr": firstCountries=new List<string>{"FRA","MCO","BEL","CHE","LUX","CAN","MAR","DZA"};break;
                /*case "eu": query=query.OrderBy(c=>c.nameBasque);break; Poner cuando se implementen más idiomas en la base de datos
                case "ca": query=query.OrderBy(c=>c.nameCatalan);break;
                case "gl": query=query.OrderBy(c=>c.nameGalician);break;
                case "pt": query=query.OrderBy(c=>c.namePortuguese);break;
                case "de": query=query.OrderBy(c=>c.nameDeustch);break;
                case "it": query=query.OrderBy(c=>c.nameItalian);break;
                case "nl": query=query.OrderBy(c=>c.nameDutch);break;*/
                default: firstCountries=new List<string>{"USA","GBR","AUS","CAN","GIB","IND","IRL","NZL","ZAF"};break;
            }

            var sortedCountries = query.ToList();
            sortedCountries.Sort((c1, c2) =>
            {
                var indexC1 = firstCountries.IndexOf(c1.idCountry);
                var indexC2 = firstCountries.IndexOf(c2.idCountry);

                if (indexC1 != -1 && indexC2 != -1)
                    return indexC1.CompareTo(indexC2);
                else if (indexC1 != -1)
                    return -1;
                else if (indexC2 != -1)
                    return 1;

                return 0;
            });
            return Ok(sortedCountries);

        }

        // GET COUNTRY by IDS action

        [HttpGet("get")]
        public ActionResult<Country> Get([FromQuery] string idCountry)
        {
            var country = _context.Countries.FirstOrDefault(c=>c.idCountry == idCountry);

            if (country == null)
                return NotFound();

            return country;
        }
    }
}
