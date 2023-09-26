using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Ambits")]
    public class AmbitsController:ControllerBase
    {
        private readonly DatabaseContext _context;

        public AmbitsController(DatabaseContext context)
        {
            _context = context;
        }

        // GET all action
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            var ambits = _context.Ambits.ToList();
            return Ok(ambits);
        }

        // GET by ID AND ORGTYPE action

        [HttpGet("get")]
        public ActionResult<Ambit> Get([FromQuery] int id)
        {
            var ambit = _context.Ambits.FirstOrDefault(a => a.idAmbit==id);

            if (ambit == null)
                return NotFound();

            return ambit;
        }

        // POST action
        [HttpPost]
        public IActionResult Create([FromBody] Ambit ambit)
        {
            _context.Ambits.Add(ambit);
            _context.SaveChanges();
            return CreatedAtAction(nameof(Get), new { idAmbit = ambit.idAmbit }, ambit);
        }


        // PUT action
        [HttpPut]
        public IActionResult Update([FromQuery] int idAmbit, [FromBody] Ambit ambit)
        {
            // This code will update the mesa and return a result
            if (idAmbit != ambit.idAmbit)
                return BadRequest();

            var existingAmbit = _context.Ambits.FirstOrDefault(a => a.idAmbit == idAmbit);
            if (existingAmbit is null)
                return NotFound();


            existingAmbit.idAmbit = ambit.idAmbit;
            existingAmbit.descriptionEnglish = ambit.descriptionEnglish;
            existingAmbit.descriptionSpanish = ambit.descriptionSpanish;
            existingAmbit.descriptionFrench = ambit.descriptionFrench;
            existingAmbit.descriptionBasque = ambit.descriptionBasque;
            existingAmbit.descriptionCatalan = ambit.descriptionCatalan;
            existingAmbit.descriptionDutch = ambit.descriptionDutch;
            existingAmbit.descriptionGalician = ambit.descriptionGalician;
            existingAmbit.descriptionGerman = ambit.descriptionGerman;
            existingAmbit.descriptionItalian = ambit.descriptionItalian;
            existingAmbit.descriptionPortuguese = ambit.descriptionPortuguese;
            _context.SaveChanges();

            return Ok(existingAmbit);
        }

        // DELETE action
        [HttpDelete]
        public IActionResult Delete([FromQuery] int id)
        {
            // This code will delete the ambit and return a result
            var ambit = _context.Ambits.FirstOrDefault(a => a.idAmbit == id);

            if (ambit is null)
                return NotFound();

            _context.Ambits.Remove(ambit);
            _context.SaveChanges();

            return NoContent();
        }
    }
}
