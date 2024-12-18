using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.IdentityModel.Tokens.Jwt;
using System.Net;
using System.Security.Claims;
using System.Text;
using System.Text.Json;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for users operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Users")]
    [Authorize]
    public class UsersController : ControllerBase
    {
        /// <summary>
        /// Database context
        /// </summary>
        private readonly DatabaseContext _context;

        /// <summary>
        /// Session Configuration
        /// </summary>
        private readonly SessionConfig _sessionConfig;

        /// <summary>
        /// Class constructor
        /// </summary>
        /// <param name="context">Database context</param>
        public UsersController(DatabaseContext context, SessionConfig sessionConfig)
        {
            _context = context;
            _sessionConfig = sessionConfig;
        }

        /// <summary>
        /// Method that obtains all the users
        /// </summary>
        /// <returns>User list</returns>
        [HttpGet("allRequests")]
        [Authorize(Policy = "Administrator")]
        public IActionResult GetAllRequests([FromHeader] string Authorization)
        {
            try
            {
                var users = _context.Users.Where(u=>u.isActive==-1).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var user in users)
                {
                    String usr = "{\"emailUser\":\"" + user.emailUser + "\"," +
                    "\"userType\":\"" + user.userType + "\"," +
                        "\"first_name\":\"" + user.first_name + "\"," +
                        "\"last_name\":\"" + user.last_name + "\"," +
                        "\"telephone\":\"" + user.telephone + "\"," +
                        "\"idOrganization\":" + user.idOrganization + "," +
                        "\"orgType\":\"" + user.orgType + "\"," +
                        "\"illness\":\"" + user.illness + "\"," +
                        "\"profilePhoto\":\"" + user.profilePhoto + "\"," +
                        "\"isActive\":\"" + user.isActive + "\"}";
                    result.Add(JsonDocument.Parse(usr));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("allActiveUsers")]
        [Authorize(Policy = "Administrator")]
        public IActionResult GetAllActiveUsers([FromHeader] string Authorization)
        {
            try
            {
                var users = _context.Users.Where(u => u.isActive == 1 && u.userType == "ORGANIZATION").ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var user in users)
                {
                    String usr = "{\"emailUser\":\"" + user.emailUser + "\"," +
                    "\"userType\":\"" + user.userType + "\"," +
                        "\"first_name\":\"" + user.first_name + "\"," +
                        "\"last_name\":\"" + user.last_name + "\"," +
                        "\"telephone\":\"" + user.telephone + "\"," +
                        "\"idOrganization\":" + user.idOrganization + "," +
                        "\"orgType\":\"" + user.orgType + "\"," +
                        "\"illness\":\"" + user.illness + "\"," +
                        "\"profilePhoto\":\"" + user.profilePhoto + "\"," +
                        "\"isActive\":\"" + user.isActive + "\"}";
                    result.Add(JsonDocument.Parse(usr));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }




        /// <summary>
        /// Method that obtains all the users
        /// </summary>
        /// <returns>User list</returns>
        [HttpGet("all")]
        public IActionResult GetAll([FromHeader] string Authorization)
        {
            try
            {
                var users = _context.Users.Where(u=>u.isActive==1).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach(var user in users) {
                    String usr = "{\"emailUser\":\"" + user.emailUser + "\"," +
                    "\"userType\":\"" + user.userType + "\"," +
                        "\"first_name\":\"" + user.first_name + "\"," +
                        "\"last_name\":\"" + user.last_name + "\"," +
                        "\"telephone\":\"" + user.telephone + "\"," +
                        "\"idOrganization\":" + user.idOrganization + "," +
                        "\"orgType\":\"" + user.orgType + "\"," +
                        "\"illness\":\"" + user.illness + "\"," +
                        "\"profilePhoto\":\"" + user.profilePhoto + "\"," +
                        "\"isActive\":\"" + user.isActive + "\"}";
                    result.Add(JsonDocument.Parse(usr));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }


        /// <summary>
        /// Method that obtains all organization users of an organization
        /// </summary>
        /// <param name="idOrganization">User organization identifier</param>
        /// <param name="orgType">User organization type</param>
        /// <param name="illness">User organization illness or syndrome</param>
        /// <returns>User list</returns>
        [HttpGet("allByOrg")]
        public IActionResult GetAllOrgUsersByOrganization([FromQuery] int? idOrganization, [FromQuery] string? orgType, [FromQuery] string? illness, [FromHeader] string Authorization)
        {
            try
            {
                var users = _context.Users.Where(u => u.idOrganization == idOrganization && u.orgType == orgType && u.illness == illness && u.isActive == 1).ToList();
                List<JsonDocument> result = new List<JsonDocument>();
                foreach (var user in users)
                {
                    String usr = "{\"emailUser\":\"" + user.emailUser + "\"," +
                    "\"userType\":\"" + user.userType + "\"," +
                        "\"first_name\":\"" + user.first_name + "\"," +
                        "\"last_name\":\"" + user.last_name + "\"," +
                        "\"telephone\":\"" + user.telephone + "\"," +
                        "\"idOrganization\":" + user.idOrganization + "," +
                        "\"orgType\":\"" + user.orgType + "\"," +
                        "\"illness\":\"" + user.illness + "\"," +
                        "\"profilePhoto\":\"" + user.profilePhoto + "\"," +
                        "\"isActive\":\"" + user.isActive + "\"}";
                    result.Add(JsonDocument.Parse(usr));
                }
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains an user from database
        /// </summary>
        /// <param name="email">User email</param>
        /// <returns>User if success, null if not</returns>
        [HttpGet("get")]
        [AllowAnonymous]
        public ActionResult<JsonDocument> Get([FromQuery] string email)
        {
            try
            {
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

                if (user == null)
                    return NotFound();
                String usr = "{\"emailUser\":\"" + user.emailUser + "\"," +
                    "\"userType\":\"" + user.userType + "\"," +
                        "\"first_name\":\"" + user.first_name + "\"," +
                        "\"last_name\":\"" + user.last_name + "\"," +
                        "\"telephone\":\"" + user.telephone + "\"," +
                        "\"idOrganization\":" + user.idOrganization + "," +
                        "\"orgType\":\"" + user.orgType + "\"," +
                        "\"illness\":\"" + user.illness + "\"," +
                        "\"profilePhoto\":\"" + user.profilePhoto + "\"," +
                        "\"isActive\":\"" + user.isActive + "\"}";
                return JsonDocument.Parse(usr);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("getDirector")]
        [Authorize]
        public ActionResult<JsonDocument> GetDirector([FromQuery] int idOrganization, [FromQuery] string organizationType, [FromQuery] string illness, [FromHeader] string Authorization)
        {
            try
            {
                var user = _context.Users.FirstOrDefault(u => u.idOrganization==idOrganization && u.orgType==organizationType && u.illness==illness && u.userType=="DIRECTOR");

                if (user == null)
                    return NotFound();
                String usr = "{\"emailUser\":\"" + user.emailUser + "\"," +
                    "\"userType\":\"" + user.userType + "\"," +
                        "\"first_name\":\"" + user.first_name + "\"," +
                        "\"last_name\":\"" + user.last_name + "\"," +
                        "\"telephone\":\"" + user.telephone + "\"," +
                        "\"idOrganization\":" + user.idOrganization + "," +
                        "\"orgType\":\"" + user.orgType + "\"," +
                        "\"illness\":\"" + user.illness + "\"," +
                        "\"profilePhoto\":\"" + user.profilePhoto + "\"," +
                        "\"isActive\":\"" + user.isActive + "\"}";
                return JsonDocument.Parse(usr);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains the login
        /// </summary>
        /// <param name="email">User login</param>
        /// <param name="password">User password</param>
        /// <returns>Login if credentials are true, null if not</returns>
        [HttpPost("login")]
        [AllowAnonymous]
        public ActionResult Login([FromBody] JsonDocument credentials)
        {
            try
            {
                string email=credentials.RootElement.GetProperty("email").ToString();
                string password = credentials.RootElement.GetProperty("password").ToString();
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.passwordUser == password && u.isActive == 1);

                if (user == null)
                {//Finds user in database
                    user = _context.Users.FirstOrDefault(u => u.emailUser == email);
                    if (user == null) { return NotFound(); }
                    else
                    {
                        if (user.passwordUser != password)
                        {
                            return Unauthorized();
                        }
                    }
                }


                var tokenHandler = new JwtSecurityTokenHandler();
                var key = Encoding.ASCII.GetBytes(_sessionConfig.secret);
                var tokenDescriptor = new SecurityTokenDescriptor
                {
                    Subject = new ClaimsIdentity(new Claim[]
                    {
                        new Claim(ClaimTypes.Name, user.emailUser),
                        new Claim(ClaimTypes.Role, user.userType)
                    }),
                    Expires = DateTime.UtcNow.AddHours(12),
                    SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
                };
                var token = tokenHandler.CreateToken(tokenDescriptor);
                string sessionToken = tokenHandler.WriteToken(token);
                var usr = new
                {
                    emailUser = email,
                    userType = user.userType,
                    first_name = user.first_name,
                    last_name = user.last_name,
                    telephone = user.telephone,
                    idOrganization = user.idOrganization,
                    orgType = user.orgType,
                    illness = user.illness,
                    profilePhoto = user.profilePhoto,
                    isActive = user.isActive
                };
                var organization= _context.Organizations.FirstOrDefault(o => o.idOrganization == user.idOrganization && o.orgType == user.orgType && o.illness == user.illness);
                if (organization == null) {
                    return BadRequest();
                }
                var org = new {
                    idOrganization = organization.idOrganization,
                    orgType = organization.orgType,
                    illness = organization.illness,
                    nameOrg = organization.nameOrg,
                    idAddress = organization.idAddress,
                    email = organization.email,
                    telephone = organization.telephone,
                    informationSpanish = organization.informationSpanish,
                    informationEnglish = organization.informationEnglish,
                    informationFrench = organization.informationFrench,
                    informationBasque = organization.informationBasque,
                    informationCatalan = organization.informationCatalan,
                    informationDutch = organization.informationDutch,
                    informationGalician = organization.informationGalician,
                    informationGerman = organization.informationGerman,
                    informationItalian = organization.informationItalian,
                    informationPortuguese = organization.informationPortuguese,
                    profilePhoto = organization.profilePhoto
                };
                var response = new
                {
                    user = usr,
                    organization = org,
                    token = "Bearer "+sessionToken

                };
                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that obtains the login
        /// </summary>
        /// <param name="email">User login</param>
        /// <param name="password">User password</param>
        /// <returns>Login if credentials are true, null if not</returns>
        [HttpPost("loginAdmin")]
        [AllowAnonymous]
        public ActionResult LoginAdmin([FromBody] JsonDocument credentials)
        {
            try
            {
                string email = credentials.RootElement.GetProperty("email").ToString();
                string password = credentials.RootElement.GetProperty("password").ToString();
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.passwordUser == password && u.userType=="ADMIN" && u.isActive == 1);

                if (user == null)
                {//Finds user in database
                    user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.userType == "ADMIN" && u.isActive == 1);
                    if (user == null) { return NotFound(); }
                    else
                    {
                        if (user.passwordUser != password)
                        {
                            return Unauthorized();
                        }
                    }
                }


                var tokenHandler = new JwtSecurityTokenHandler();
                var key = Encoding.ASCII.GetBytes(_sessionConfig.secret);
                var tokenDescriptor = new SecurityTokenDescriptor
                {
                    Subject = new ClaimsIdentity(new Claim[]
                    {
                        new Claim(ClaimTypes.Name, user.emailUser),
                        new Claim(ClaimTypes.Role, user.userType)
                    }),
                    Expires = DateTime.UtcNow.AddHours(12),
                    SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
                };
                var token = tokenHandler.CreateToken(tokenDescriptor);
                string sessionToken = tokenHandler.WriteToken(token);
                var usr = new
                {
                    emailUser = email,
                    userType = user.userType,
                    first_name = user.first_name,
                    last_name = user.last_name,
                    telephone = user.telephone,
                    idOrganization = user.idOrganization,
                    orgType = user.orgType,
                    illness = user.illness,
                    profilePhoto = user.profilePhoto,
                    isActive = user.isActive
                };
                var organization = _context.Organizations.FirstOrDefault(o => o.idOrganization == user.idOrganization && o.orgType == user.orgType && o.illness == user.illness);
                if (organization == null)
                {
                    return BadRequest();
                }
                var org = new
                {
                    idOrganization = organization.idOrganization,
                    orgType = organization.orgType,
                    illness = organization.illness,
                    nameOrg = organization.nameOrg,
                    idAddress = organization.idAddress,
                    email = organization.email,
                    telephone = organization.telephone,
                    informationSpanish = organization.informationSpanish,
                    informationEnglish = organization.informationEnglish,
                    informationFrench = organization.informationFrench,
                    informationBasque = organization.informationBasque,
                    informationCatalan = organization.informationCatalan,
                    informationDutch = organization.informationDutch,
                    informationGalician = organization.informationGalician,
                    informationGerman = organization.informationGerman,
                    informationItalian = organization.informationItalian,
                    informationPortuguese = organization.informationPortuguese,
                    profilePhoto = organization.profilePhoto
                };
                var response = new
                {
                    user = usr,
                    organization = org,
                    token = "Bearer " + sessionToken

                };
                return Ok(response);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that appends an user to the database
        /// </summary>
        /// <param name="user">User</param>
        /// <returns>User if success, null if not</returns>
        [HttpPost]
        [AllowAnonymous]
        public IActionResult Create([FromBody] User user)
        {
            try
            {
                _context.Users.Add(user);
                _context.SaveChanges();
                return CreatedAtAction(nameof(Get), new { email = user.emailUser }, user);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that updates an user
        /// </summary>
        /// <param name="email">User email</param>
        /// <param name="user">User</param>
        /// <returns>User if success, null if not</returns>
        [HttpPut]
        [AllowAnonymous]
        public IActionResult Update([FromQuery] string email, [FromBody] User user)
        {
            try
            {
                if (email != user.emailUser)
                    return BadRequest();

                var existingUser = _context.Users.FirstOrDefault(u => u.emailUser == email);
                if (existingUser is null)
                    return NotFound();

                existingUser.emailUser = email;
                existingUser.userType = user.userType;
                existingUser.first_name = user.first_name;
                existingUser.last_name = user.last_name;
                if (!user.passwordUser.IsNullOrEmpty()) {
                    existingUser.passwordUser = user.passwordUser;
                }
                existingUser.telephone = user.telephone;
                existingUser.idOrganization = user.idOrganization;
                existingUser.orgType = user.orgType;
                existingUser.illness = user.illness;
                existingUser.profilePhoto = user.profilePhoto;
                existingUser.isActive = user.isActive;
                _context.SaveChanges();

                return Ok(existingUser);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        /// <summary>
        /// Method that deletes an user
        /// </summary>
        /// <param name="email">User email</param>
        /// <returns>User if success, null if not</returns>
        [HttpDelete]
        [Authorize(Policy = "Administrator")]
        public IActionResult Delete([FromQuery] string email, [FromHeader] string Authorization)
        {
            try
            {
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

                if (user is null)
                    return NotFound();

                if (user.isActive == 1)
                {
                    var director = _context.Users.FirstOrDefault(u => u.idOrganization == user.idOrganization && u.orgType == user.orgType && u.illness == user.illness && u.userType == "DIRECTOR");
                    if (director is null)
                        return NotFound();
                    var evaluatorTeams = _context.EvaluatorTeams.Where(e => e.emailProfessional == email || e.emailResponsible == email);
                    if (evaluatorTeams.Count() > 0) {
                        foreach (var evaluatorTeam in evaluatorTeams) {
                            if (evaluatorTeam.emailProfessional == email) {
                                evaluatorTeam.emailProfessional = director.emailUser;
                            }
                            if (evaluatorTeam.emailResponsible == email)
                            {
                                evaluatorTeam.emailResponsible = director.emailUser;
                            }
                        }
                    }
                    _context.SaveChanges();
                }
                

                _context.Users.Remove(user);
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
