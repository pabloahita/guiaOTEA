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
                            int idCity = reader.IsDBNull(3) ? -1 : reader.GetInt32(3);
                            int idProvince = reader.IsDBNull(4) ? -1 : reader.GetInt32(4);
                            int idRegion = reader.IsDBNull(5) ? -1 : reader.GetInt32(5);
                            addressesList.Add(new Address(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2), idCity,
                   idProvince, idRegion, reader.GetString(7), reader.GetString(8), reader.GetString(9), reader.GetString(6)));

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
                            int idCity = reader.IsDBNull(3) ? -1 : reader.GetInt32(3);
                            int idProvince = reader.IsDBNull(4) ? -1 : reader.GetInt32(4);
                            int idRegion = reader.IsDBNull(5) ? -1 : reader.GetInt32(5);
                            return new Address(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2), idCity,
                   idProvince, idRegion, reader.GetString(7), reader.GetString(8), reader.GetString(9), reader.GetString(6));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idAddress, string addressName, int zipCode, int idCity, int idProvince, int idRegion, string nameCity, string nameProvince, string nameRegion, string idCountry)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO addresses (IDADDRESS,ADDRESSNAME,ZIPCODE,IDCITY,IDPROVINCE,IDREGION,IDCOUNTRY,NAMECITY,NAMEPROVINCE,NAMEREGION) VALUES (@IDADDRESS,@ADDRESSNAME,@ZIPCODE,@IDCITY,@IDPROVINCE,@IDREGION,@IDCOUNTRY,@NAMECITY,@NAMEPROVINCE,@NAMEREGION)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@IDADDRESS", idAddress);
                    command.Parameters.AddWithValue("@ADDRESSNAME", addressName);
                    command.Parameters.AddWithValue("@ZIPCODE", zipCode);
                    if (idCity==-1)
                    {
                        command.Parameters.AddWithValue("@IDCITY", DBNull.Value);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@IDCITY", idCity);
                    }

                    if (idProvince==-1)
                    {
                        command.Parameters.AddWithValue("@IDPROVINCE", DBNull.Value);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@IDPROVINCE", idProvince);
                    }

                    if (idRegion == -1)
                    {
                        command.Parameters.AddWithValue("@IDREGION", DBNull.Value);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@IDREGION", idRegion);
                    }
                    command.Parameters.AddWithValue("@NAMECITY", nameCity);
                    command.Parameters.AddWithValue("@NAMEPROVINCE", nameProvince);
                    command.Parameters.AddWithValue("@NAMEREGION", nameRegion);
                    command.Parameters.AddWithValue("@IDCOUNTRY", idCountry);
                    // Ejecuta el command
                    command.ExecuteNonQuery();
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

                    // Crea el command SQL
                    string sql = "DELETE FROM ADDRESSES WHERE idAddress=@ID";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@ID", id);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(int idAddress, Address address)
        {
            if (address != null && Get(idAddress)!=null && idAddress == address.idAddress)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE ADDRESS SET ADDRESSNAME=@ADDRESSNAME,ZIPCODE=@ZIPCODE,IDCITY=@IDCITY,IDPROVINCE=@IDPROVINCE,IDREGION=@IDREGION,IDCOUNTRY=@IDCOUNTRY, NAMECITY=@NAMECITY,NAMEPROVINCE=@NAMEPROVINCE,NAMEREGION=@NAMEREGION WHERE IDADDRESS=@IDADDRESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDADDRESS", address.idAddress);
                        command.Parameters.AddWithValue("@ADDRESSNAME", address.addressName);
                        command.Parameters.AddWithValue("@ZIPCODE", address.zipCode); 
                        if (address.idCity == -1)
                        {
                            command.Parameters.AddWithValue("@IDCITY", DBNull.Value);
                        }
                        else
                        {
                            command.Parameters.AddWithValue("@IDCITY", address.idCity);
                        }

                        if (address.idProvince == -1)
                        {
                            command.Parameters.AddWithValue("@IDPROVINCE", DBNull.Value);
                        }
                        else
                        {
                            command.Parameters.AddWithValue("@IDPROVINCE", address.idProvince);
                        }

                        if (address.idRegion == -1)
                        {
                            command.Parameters.AddWithValue("@IDREGION", DBNull.Value);
                        }
                        else
                        {
                            command.Parameters.AddWithValue("@IDREGION", address.idRegion);
                        }
                        command.Parameters.AddWithValue("@IDCOUNTRY", address.idCountry);
                        command.Parameters.AddWithValue("@NAMECITY", address.nameCity);
                        command.Parameters.AddWithValue("@NAMEPROVINCE", address.nameProvince);
                        command.Parameters.AddWithValue("@NAMEREGION", address.nameRegion);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }
    }
}
