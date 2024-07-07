using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;

namespace WebApplication1.Pages
{
    public class RegisterRequestsManagementModel : PageModel
    {

        private readonly Session _session;

        public RegisterRequestsManagementModel()
        {
        }

        public List<UserSession> Users { get; set; }
        
        public async Task<IActionResult> OnGetAsync()
        {

            HttpClient _httpClient = new HttpClient();


            Session session= Session.Instance;

            string[] token=session.getToken().Split(" ");

            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(token[0], token[1]);

            var response = await _httpClient.GetAsync("https://guiaotea.azurewebsites.net/Users/allRequests");
            if (response.IsSuccessStatusCode)
            {
                var jsonResponse = await response.Content.ReadAsStringAsync();
                Users = JsonConvert.DeserializeObject<List<UserSession>>(jsonResponse);
            }
            else
            {
                // Manejar errores aquí
                Users = new List<UserSession>();
            }

            Session.Instance.setUserList(Users);
            return Page();
        }

        public async Task<IActionResult> OnPostAcceptAsync(string emailUser)
        {
            Users = Session.Instance.getUserList();
            var userSession = Users.FirstOrDefault(u => u.emailUser == emailUser);
            if (userSession is null) {
                return NotFound();
            }
            User user = new User(emailUser, userSession.userType, userSession.first_name, userSession.last_name, "", userSession.telephone, userSession.idOrganization, userSession.orgType, userSession.illness, userSession.profilePhoto, 0);
            HttpClient _httpClient = new HttpClient();
            var options = new JsonSerializerOptions
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                WriteIndented = true
            };

            string userJson = System.Text.Json.JsonSerializer.Serialize(user,options);
            var response = await _httpClient.PutAsync($"https://guiaotea.azurewebsites.net/Users?email={emailUser}", new StringContent(userJson, Encoding.UTF8, "application/json"));
            if (response.IsSuccessStatusCode)
            {
                // Actualizar la lista de usuarios después de aceptar la solicitud
                return RedirectToPage("/RegisterRequestsManagement");
            }
            else
            {
                // Manejar errores aquí
            }

            return Page();
        }

        public async Task<IActionResult> OnPostRejectAsync(string emailUser)
        {
            HttpClient _httpClient = new HttpClient();
            string[] token = Session.Instance.getToken().Split(" ");
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(token[0], token[1]);
            var response = await _httpClient.DeleteAsync($"https://guiaotea.azurewebsites.net/Users?email={emailUser}");
            if (response.IsSuccessStatusCode)
            {
                // Actualizar la lista de usuarios después de aceptar la solicitud
                return RedirectToPage("/RegisterRequestsManagement");
            }
            else
            {
                // Manejar errores aquí
            }

            return Page();

        }
    }

}
