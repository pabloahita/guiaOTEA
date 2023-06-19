using Microsoft.Extensions.Configuration;
using OTEAServer.Models;
using System.Data.SqlClient;
using System.Diagnostics.Metrics;
using System.Net;
using System.Reflection.Emit;

namespace OTEAServer.Services
{
    public class AddressesService
    {

        private static IConfiguration _configuration;
        public AddressesService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        //Operaciones GET

        public List<Address> GetAll()
        {
            List<Address> addressesList = new List<Address>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM ADDRESSES";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            addressesList.Add(new Address(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2), reader.GetInt32(3),
                   reader.GetString(4)[0], reader.GetInt32(5), reader.GetString(6), reader.GetString(7), reader.GetString(8), reader.GetString(9)));
                        }
                    }
                }
            }
            return addressesList;

        }

        public Address? Get(int id)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM ADDRESSES WHERE idAddress=@ID";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@ID", id);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Address(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2), reader.GetInt32(3),
                   reader.GetString(4)[0], reader.GetInt32(5), reader.GetString(6), reader.GetString(7), reader.GetString(8), reader.GetString(9));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idAddress, string nameStreet, int numberStreet, int floorApartment, char apartmentLetter, int zipCode, string city, string province, string region, string country)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el comando SQL
                string sql = "INSERT INTO ADDRESSES (IDADDRESS,NAMESTREET,NUMBERSTREET,FLOORAPARTMENT,APARTMENTLETTER,ZIPCODE,CITY,PROVINCE,REGION,COUNTRY) VALUES (@IDADDRESS,@NAMESTREET,@NUMBERSTREET,@FLOORAPARTMENT,@APARTMENTLETTER,@ZIPCODE,@CITY,@PROVINCE,@REGION,@COUNTRY)";
                using (SqlCommand comando = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    comando.Parameters.AddWithValue("@IDADDRESS", idAddress);
                    comando.Parameters.AddWithValue("@NAMESTREET", nameStreet);
                    comando.Parameters.AddWithValue("@NUMBERSTREET", numberStreet);
                    comando.Parameters.AddWithValue("@FLOORAPARTMENT", floorApartment);
                    comando.Parameters.AddWithValue("@APARTMENTLETTER", apartmentLetter);
                    comando.Parameters.AddWithValue("@ZIPCODE", zipCode);
                    comando.Parameters.AddWithValue("@CITY", city);
                    comando.Parameters.AddWithValue("@PROVINCE", province);
                    comando.Parameters.AddWithValue("@REGION", region);
                    comando.Parameters.AddWithValue("@COUNTRY", country);
                    // Ejecuta el comando
                    comando.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(int id)
        {
            if (Get(id) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el comando SQL
                    string sql = "DELETE FROM ADDRESSES WHERE idAddress=@ID";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@ID", id);

                        // Ejecuta el comando
                        comando.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(Address address)
        {
            if (address != null && Get(address.IdAddress) == address)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el comando SQL
                    string sql = "UPDATE ADDRESS SET NAMESTREET=@NAMESTREET,NUMBERSTREET=@NUMBERSTREET,FLOORAPARTMENT=@FLOORAPARTMENT,APARTMENTLETTER=@APARTMENTLETTER,ZIPCODE=@ZIPCODE,CITY=@CITY,PROVINCE=@PROVINCE,REGION=@REGION,COUNTRY=@COUNTRY WHERE IDADDRESS=@IDADDRESS";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@IDADDRESS", address.IdAddress);
                        comando.Parameters.AddWithValue("@NAMESTREET", address.NameStreet);
                        comando.Parameters.AddWithValue("@NUMBERSTREET", address.NumberStreet);
                        comando.Parameters.AddWithValue("@FLOORAPARTMENT", address.FloorApartment);
                        comando.Parameters.AddWithValue("@APARTMENTLETTER", address.ApartmentLetter);
                        comando.Parameters.AddWithValue("@ZIPCODE", address.ZipCode);
                        comando.Parameters.AddWithValue("@CITY", address.City);
                        comando.Parameters.AddWithValue("@PROVINCE", address.Province);
                        comando.Parameters.AddWithValue("@REGION", address.Region);
                        comando.Parameters.AddWithValue("@COUNTRY", address.Country);

                        // Ejecuta el comando
                        comando.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }
    }
}
