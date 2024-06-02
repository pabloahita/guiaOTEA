using Azure.Core;
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.Text.Json;

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
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var request in requests)
                {
                    String rg = "{\"email\":\"" + request.email + "\"," +
                                "\"statusReq\":\"" + request.statusReq + "\"," +
                                "\"idOrganization\":\"" + request.idOrganization + "\"," +
                                "\"orgType\":\"" + request.orgType + "\"," +
                                "\"illness\":\"" + request.illness + "\"," +
                                "\"userType\":\"" + request.userType + "\"}";

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
        /// Method that obtains a request from the database
        /// </summary>
        /// <param name="email">Email of the register request</param>
        /// <returns>Request if success, null if not</returns>
        [HttpGet("get")]
        public ActionResult<JsonDocument> Get([FromQuery] string email)
        {
            try
            {
                var request = _context.Requests.FirstOrDefault(r => r.email == email);

                if (request == null)
                    return NotFound();

                String rg = "{\"email\":\"" + request.email + "\"," +
                                "\"statusReq\":\"" + request.statusReq + "\"," +
                                "\"idOrganization\":\"" + request.idOrganization + "\"," +
                                "\"orgType\":\"" + request.orgType + "\"," +
                                "\"illness\":\"" + request.illness + "\"," +
                                "\"userType\":\"" + request.userType + "\"}";
                return JsonDocument.Parse(rg);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPost("goToUserReg")]
        public ActionResult goToUserReg([FromBody] JsonDocument credentials)
        {
            try
            {
                string email = credentials.RootElement.GetProperty("email").ToString();
                string password = credentials.RootElement.GetProperty("password").ToString();
                var request = _context.Requests.FirstOrDefault(u => u.email == email && u.tempPassword == password);

                if (request == null)
                {//Finds user in database
                    request = _context.Requests.FirstOrDefault(u => u.email == email);
                    if (request == null) { return NotFound(); }
                    else
                    {
                        if (request.tempPassword != password)
                        {
                            return Unauthorized();
                        }
                    }
                }
                String rg = "{\"email\":\"" + request.email + "\"," +
                                "\"statusReq\":\"" + request.statusReq + "\"," +
                                "\"idOrganization\":\"" + request.idOrganization + "\"," +
                                "\"orgType\":\"" + request.orgType + "\"," +
                                "\"illness\":\"" + request.illness + "\"," +
                                "\"userType\":\"" + request.userType + "\"}";
                return Ok(JsonDocument.Parse(rg));

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
        public IActionResult Create([FromBody] Models.Request request)
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
        public IActionResult Update([FromQuery] string email, [FromBody] Models.Request request){
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