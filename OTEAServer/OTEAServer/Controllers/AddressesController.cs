using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Addresses")]
    public class AddressesController : ControllerBase
    {
        private readonly ILogger<AddressesController> _logger;
        private readonly AddressesService _addressesService;

        public AddressesController(ILogger<AddressesController> logger, AddressesService addressesService)
        {
            _logger = logger;
            _addressesService = addressesService;
        }
        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var organizations = _addressesService.GetAll();
            return Ok(organizations);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("{id}")]
        public ActionResult<Address> Get(int id)
        {
            var address = _addressesService.Get(id);

            if (address == null)
                return NotFound();

            return address;
        }

        // POST action
        [HttpPost]
        public IActionResult Create(int idAddress, string nameStreet, int numberStreet, int floorApartment, char apartmentLetter, int zipCode, string city, string province, string region, string country)
        {
            _addressesService.Add(idAddress,nameStreet,numberStreet,floorApartment,apartmentLetter,zipCode,city,province,region,country);
            Address address = new Address(idAddress, nameStreet, numberStreet, floorApartment, apartmentLetter, zipCode, city, province, region, country);
            return CreatedAtAction(nameof(Get), new { idAddress = address.IdAddress }, address);
        }

        // PUT action
        [HttpPut("{id}")]
        public IActionResult Update(int id, Address address)
        {
            // This code will update the mesa and return a result
            if (id != address.IdAddress)
                return BadRequest();

            var existingAddress = _addressesService.Get(id);
            if (existingAddress is null)
                return NotFound();

            _addressesService.Update(address);

            return NoContent();
        }

        // DELETE action
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            // This code will delete the mesa and return a result
            var address = _addressesService.Get(id);

            if (address is null)
                return NotFound();

            _addressesService.Delete(id);

            return NoContent();
        }
    }
}
