using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using OTEAServer.Misc;
using OTEAServer.Models;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace OTEAServer.Controllers
{
    /// <summary>
    /// Controller class for users operations
    /// Author: Pablo Ahíta del Barrio
    /// Version: 1
    /// </summary>
    [ApiController]
    [Route("Users")]
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
        [HttpGet("all")]
        public IActionResult GetAll()
        {
            try
            {
                var users = _context.Users.ToList();
                return Ok(users);
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
        public IActionResult GetAllOrgUsersByOrganization([FromQuery] int? idOrganization, [FromQuery] string? orgType, [FromQuery] string? illness)
        {
            try
            {
                var users = _context.Users.Where(u => u.idOrganization == idOrganization && u.orgType == orgType && u.illness == illness).ToList();
                return Ok(users);
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
        public ActionResult<User> Get([FromQuery] string email)
        {
            try
            {
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

                if (user == null)
                    return NotFound();

                return user;
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
        public ActionResult<User> Login([FromQuery] string email,[FromQuery] string password)
        {
            try
            {
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email && u.passwordUser == password);

                if (user == null)
                {//Finds user in database
                    user = _context.Users.FirstOrDefault(u => u.emailUser == email);
                    if (user == null) { return NotFound(); }
                    else
                    {
                        if (user.emailUser != email)
                        {
                            return BadRequest();
                        }
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
                        new Claim(ClaimTypes.Name, user.emailUser)
                    }),
                    Expires = DateTime.UtcNow.AddDays(7),
                    SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
                };
                var token = tokenHandler.CreateToken(tokenDescriptor);
                _sessionConfig.token = tokenHandler.WriteToken(token);
                return Ok(user);
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
        [Authorize]
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
        [Authorize]
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
                existingUser.passwordUser = user.passwordUser;
                existingUser.telephone = user.telephone;
                existingUser.idOrganization = user.idOrganization;
                existingUser.orgType = user.orgType;
                existingUser.illness = user.illness;
                existingUser.profilePhoto = user.profilePhoto;
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
        [Authorize]
        public IActionResult Delete([FromQuery] string email)
        {
            try
            {
                var user = _context.Users.FirstOrDefault(u => u.emailUser == email);

                if (user is null)
                    return NotFound();

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
