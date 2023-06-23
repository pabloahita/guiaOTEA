using Microsoft.Extensions.Configuration;
using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class CitiesService
    {

        private static IConfiguration _configuration;
        public CitiesService(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        public List<City> GetAllByProvince(int idProvince, int idRegion, string idCountry)
        {
            List<City> citiesList = new List<City>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM CITIES WHERE idProvince=@IDPROVINCE AND idRegion=@IDREGION AND idCountry=@IDCOUNTRY ORDER BY cityName";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDPROVINCE", idProvince);
                    command.Parameters.AddWithValue("@IDREGION", idRegion);
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            citiesList.Add(new City(reader.GetInt32(2), idProvince, idRegion, idCountry, reader.GetString(3)));
                        }
                    }
                }
            }
            return citiesList;
        }

        public City? Get(int idCity, int idProvince, int idRegion, string idCountry)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM CITIES WHERE idCity=@IDCITY AND idProvince=@IDPROVINCE AND idRegion=@IDREGION AND idCountry=@IDCOUNTRY";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDCITY", idCity);
                    command.Parameters.AddWithValue("@IDPROVINCE", idProvince);
                    command.Parameters.AddWithValue("@IDREGION", idRegion);
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new City(idCity,idProvince,idRegion,idCountry,reader.GetString(3));
                        }
                    }
                }
            }
            return null;
        }
    }
}
