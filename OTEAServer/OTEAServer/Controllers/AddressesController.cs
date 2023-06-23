using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;
using System.Data;

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
            var addresses = _addressesService.GetAll();
            return Ok(addresses);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get::{id}")]
        public ActionResult<Address> Get(int id)
        {
            var address = _addressesService.Get(id);

            if (address == null)
                return NotFound();

            return address;
        }

        // POST action
        [HttpPost]
        public IActionResult Create(int idAddress, string nameStreet, int numberStreet, int floorApartment, char apartmentLetter, int zipCode, int idCity, int idProvince, int idRegion, string idCountry)
        {
            _addressesService.Add(idAddress,nameStreet,numberStreet,floorApartment,apartmentLetter,zipCode,idCity,idProvince,idRegion,idCountry);
            Address address = new Address(idAddress, nameStreet, numberStreet, floorApartment, apartmentLetter, zipCode, idCity, idProvince, idRegion, idCountry);
            return CreatedAtAction(nameof(Get), new { idAddress = address.idAddress }, address);
        }

        // PUT action
        [HttpPut("upd::{id}")]
        public IActionResult Update(int id, Address address)
        {
            // This code will update the mesa and return a result
            if (id != address.idAddress)
                return BadRequest();

            var existingAddress = _addressesService.Get(id);
            if (existingAddress is null)
                return NotFound();

            _addressesService.Update(address);

            return Ok(address);
        }

        // DELETE action
        [HttpDelete("del::{id}")]
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
