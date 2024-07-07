using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using System.Net.Http.Headers;
using WebApplication1;

namespace GuiaOTEAAdmin.Pages
{

    public class ActiveUsersManagementModel : PageModel
    {
        private readonly Session _session;

        public ActiveUsersManagementModel()
        {
        }

        public List<UserSession> Users { get; set; }
        public async Task<IActionResult> OnGetAsync()
        {

            HttpClient _httpClient = new HttpClient();


            Session session = Session.Instance;

            string[] token = session.getToken().Split(" ");

            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(token[0], token[1]);

            var response = await _httpClient.GetAsync("https://guiaotea.azurewebsites.net/Users/allActiveUsers");
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
            HttpClient _httpClient = new HttpClient();
            string[] token = Session.Instance.getToken().Split(" ");
            _httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue(token[0], token[1]);
            var response = await _httpClient.DeleteAsync($"https://guiaotea.azurewebsites.net/Users?email={emailUser}");
            if (response.IsSuccessStatusCode)
            {
                // Actualizar la lista de usuarios después de aceptar la solicitud
                return RedirectToPage("/ActiveUsersManagement");
            }
            else
            {
                // Manejar errores aquí
            }

            return Page();

        }
    }
}
