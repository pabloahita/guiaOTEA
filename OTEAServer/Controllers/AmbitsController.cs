using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for ambit operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>


    [ApiController]
    [Route("Ambits")]
    public class AmbitsController:ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context"></param>
        public AmbitsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        ///  Method that obtains from the database all the ambits
        /// </summary>
        /// <returns>All ambits</returns>
        [HttpGet("all")]
        [Authorize]
        public IActionResult GetAll([FromHeader] string Authorization)
        {
            try
            {
                var ambits = _context.Ambits.ToList();
                return Ok(ambits);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains from the database a ambit using its identifier
        /// </summary>
        /// <param name="id">Ambit identifier</param>
        /// <returns>The ambit if exists, null if not.</returns>

        [HttpGet("get")]
        [Authorize]
        public ActionResult<Ambit> Get([FromQuery] int id, [FromHeader] string Authorization)
        {
            try
            {
                var ambit = _context.Ambits.FirstOrDefault(a => a.idAmbit == id);

                if (ambit == null)
                    return NotFound();

                return ambit;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a new ambit to the database
        /// </summary>
        /// <param name="ambit">Ambit to append</param>
        /// <returns>Ambit append, null if not</returns>
        [HttpPost]
        [Authorize(Policy = "Administrator")]
        public IActionResult Create([FromBody] Ambit ambit, [FromHeader] string Authorization)
        {
            try
            {
                _context.Ambits.Add(ambit);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { idAmbit = ambit.idAmbit }, ambit);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="idAmbit">Ambit identifier</param>
        /// <param name="ambit">New ambit</param>
        /// <returns>Ambit if was updated, null if not</returns>
        [HttpPut]
        [Authorize(Policy = "Administrator")]
        public IActionResult Update([FromQuery] int idAmbit, [FromBody] Ambit ambit, [FromHeader] string Authorization)
        {
            try
            {
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
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes the ambit using its identifier
        /// </summary>
        /// <param name="id">Ambit identifier</param>
        /// <returns>Deleted ambit if deletion was succesful, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Administrator")]
        public IActionResult Delete([FromQuery] int id, [FromHeader] string Authorization)
        {
            try
            {
                var ambit = _context.Ambits.FirstOrDefault(a => a.idAmbit == id);

                if (ambit is null)
                    return NotFound();

                _context.Ambits.Remove(ambit);
                _context.SaveChanges();

                return NoContent();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
