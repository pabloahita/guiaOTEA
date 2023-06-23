using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using OTEAServer.Services;

namespace OTEAServer.Controllers
{
    [ApiController]
    [Route("Users")]
    public class UsersController : ControllerBase
    {
        private readonly ILogger<UsersController> _logger;
        private readonly UsersService _usersService;

        public UsersController(ILogger<UsersController> logger, UsersService usersService)
        {
            _logger = logger;
            _usersService = usersService;
        }

        
        // GET all action
        [HttpGet]
        public IActionResult GetAll()
        {
            var users = _usersService.GetAll();
            return Ok(users);
        }


        //GET all by user type
        [HttpGet("getAllByType::userType={userType}")]
        public IActionResult GetAllByType(string userType)
        {
            var users = _usersService.GetAllByType(userType);
            return Ok(users);
        }

        //GET all organization users by organization type
        [HttpGet("getAllByOrg::idOrganization={idOrganization}:orgType={orgType}")]
        public IActionResult GetAllOrgUsersByOrganization(int idOrganization, string orgType)
        {
            var users = _usersService.GetAllOrgUsersByOrganization(idOrganization,orgType);
            return Ok(users);
        }

        // GET by EMAIL action

        [HttpGet("get::email={email}")]
        public ActionResult<User> Get(string email)
        {
            var user = _usersService.Get(email);

            if (user == null)
                return NotFound();

            return user;
        }

        //GET for LOGIN
        [HttpGet("login::email={email}:password={password}")]
        public ActionResult<User> GetForLogin(string email,string password)
        {
            var user = _usersService.GetForLogin(email,password);

            if (user == null)
                return NotFound();

            return user;
        }

        // GET by EMAIL and USER TYPE action

        [HttpGet("getByType::email={email}:userType={userType}")]
        public ActionResult<User> GetByType(string email, string userType)
        {
            var user = _usersService.GetByType(email,userType);

            if (user == null)
                return NotFound();

            return user;
        }

        // GET by EMAIL and ORGANIZATION action

        [HttpGet("getByOrg::email={email}:idOrganization={idOrganization}:orgType={orgType}")]
        public ActionResult<User> GetOrgUserByOrganization(string email, int idOrganization, string orgType)
        {
            var user = _usersService.GetOrgUserByOrganization(email,idOrganization,orgType);

            if (user == null)
                return NotFound();

            return user;
        }

        // POST action
        [HttpPost]
        public IActionResult Create(string email, string first_Name, string last_Name, string password, string userType, long telephone, int? idOrganization, string? organizationType, string? illness)
        {
            _usersService.Add(email,first_Name,last_Name,password,userType,telephone,idOrganization,organizationType,illness);
            User user = new User(email, first_Name, last_Name, password, userType,telephone, idOrganization, organizationType, illness);
            return CreatedAtAction(nameof(Get), new { email = user.emailUser}, user);
        }

        // PUT action
        [HttpPut("upd::email={email}")]
        public IActionResult Update(string email, User user)
        {
            // This code will update the mesa and return a result
            if (email != user.emailUser)
                return BadRequest();

            var existingUser = _usersService.Get(email);
            if (existingUser is null)
                return NotFound();

            _usersService.Update(user);

            return Ok(user);
        }

        // DELETE action
        [HttpDelete("del::email={email}")]
        public IActionResult Delete(String email)
        {
            // This code will delete the mesa and return a result
            var user = _usersService.Get(email);

            if (user is null)
                return NotFound();

            _usersService.Delete(email);

            return NoContent();
        }
    }
}
