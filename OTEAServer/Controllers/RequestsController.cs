using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for requests operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Requests")]
    public class RequestsController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Class controller
        /// </summary>
        /// <param name="context">Database context</param>
        public RequestsController(DatabaseContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Method that obtains all requests
        /// </summary>
        /// <returns>Request list</returns>
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            try
            {
                var requests = _context.Requests.ToList();
                return Ok(requests);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains a request from the database
        /// </summary>
        /// <param name="email">Email of the register request</param>
        /// <returns>Request if success, null if not</returns>
        [HttpGet("get")]
        public ActionResult<Request> Get([FromQuery] string email)
        {
            try
            {
                var request = _context.Requests.FirstOrDefault(r => r.email == email);

                if (request == null)
                    return NotFound();

                return request;
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends a request to the database
        /// </summary>
        /// <param name="request">Request</param>
        /// <returns>Request if success, null if not</returns>
        [HttpPost]
        public IActionResult Create([FromBody] Request request)
        {
            try
            {
                _context.Requests.Add(request);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { email = request.email }, request);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that updates a request
        /// </summary>
        /// <param name="email">Email of the register request</param>
        /// <param name="request">Request</param>
        /// <returns>Request if success, null if not</returns>
        [HttpPut]
        public IActionResult Update([FromQuery] string email, [FromBody] Request request){
            try
            {
                if (email != request.email)
                    return BadRequest();
                var existingRequest = _context.Requests.FirstOrDefault(r => r.email == email);
                if (existingRequest is null)
                    return NotFound();

                existingRequest.email = email;
                existingRequest.statusReq = request.statusReq;
                existingRequest.tempPassword = request.tempPassword;
                existingRequest.idOrganization = request.idOrganization;
                existingRequest.orgType = request.orgType;
                existingRequest.illness = request.illness;
                existingRequest.userType = request.userType;
                _context.SaveChanges();

                return Ok(existingRequest);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes a request
        /// </summary>
        /// <param name="email">Email of the register request</param>
        /// <returns>Request if success, null if not</returns>
        [HttpDelete]
        public IActionResult Delete([FromQuery] string email)
        {
            try
            {
                var existingRequest = _context.Requests.FirstOrDefault(r => r.email == email);
                if (existingRequest is null)
                    return NotFound();

                _context.Requests.Remove(existingRequest);
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