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
        public IActionResult Create([FromBody] Address address)
        {
            _addressesService.Add(address.idAddress, address.addressName, address.zipCode, address.idCity, address.idProvince, address.idRegion, address.nameCity, address.nameProvince, address.nameRegion, address.idCountry);

            return CreatedAtAction(nameof(Get), new { idAddress = address.idAddress }, address);
        }


        // PUT action
        [HttpPut("upd::{id}")]
        public IActionResult Update(int idAddress, Address address)
        {
            // This code will update the mesa and return a result
            if (idAddress != address.idAddress)
                return BadRequest();

            var existingAddress = _addressesService.Get(idAddress);
            if (existingAddress is null)
                return NotFound();

            _addressesService.Update(idAddress,address);

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
