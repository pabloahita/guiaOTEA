using Microsoft.Extensions.Configuration;
using OTEAServer.Models;
using System.Data.SqlClient;
using System.Security.Policy;

namespace OTEAServer.Services
{
    public class CentersService
    {
        private static IConfiguration _configuration;
        public CentersService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<Center> GetAll() {
            List<Center> centersList = new List<Center>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM CENTERS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            centersList.Add(new Center(reader.GetInt32(0),reader.GetString(1),reader.GetString(2),reader.GetInt32(3),reader.GetString(4),reader.GetInt32(5),reader.GetInt64(6)));
                        }
                    }
                }
            }
            return centersList;
        }

        public List<Center> GetAllByOrganization(int idOrganization, string orgType, string illness)
        {
            List<Center> centersList = new List<Center>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM CENTERS WHERE idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS ORDER BY idCenter";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            centersList.Add(new Center(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetInt32(3), reader.GetString(4), reader.GetInt32(5), reader.GetInt64(6)));
                        }
                    }
                }
            }
            return centersList;
        }

        public Center Get(int idOrganization, string orgType, string illness, int idCenter)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM CENTERS WHERE idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS AND idCenter=@IDCENTER";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);

                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Center(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetInt32(3), reader.GetString(4), reader.GetInt32(5), reader.GetInt64(6));
                        }
                    }
                }
            }
            return null;
        }

        public void Add(int idOrganization, string orgType, string illness, int idCenter, string centerDescription, int idAddress, long telephone)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO CENTERS (IDORGANIZATION,ORGTYPE,ILLNESS,IDCENTER,CENTERDESCRIPTION,IDADDRESS,TELEPHONE) VALUES (@IDORGANIZATION,@ORGTYPE,@ILLNESS,@IDCENTER,@CENTERDESCRIPTION,@IDADDRESS,@TELEPHONE)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    command.Parameters.AddWithValue("@IDCENTER", idCenter);
                    command.Parameters.AddWithValue("@CENTERDESCRIPTION", centerDescription);
                    command.Parameters.AddWithValue("@IDADDRESS", idAddress);
                    command.Parameters.AddWithValue("@TELEPHONE", telephone);
                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        public void Update(int idOrganization, string orgType, string illness, int idCenter, Center center)
        {
            if (center != null && Get(idOrganization,orgType,illness,idCenter)!=null && idOrganization==center.IdOrganization && orgType==center.orgType && illness==center.illness && idCenter==center.idCenter)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE CENTERS SET CENTERDESCRIPTION=@CENTERDESCRIPTION AND IDADDRESS=@IDADDRESS AND TELEPHONE=@TELEPHONE WHERE IDORGANIZATION=@ID AND ORGTYPE=@ORGTYPE AND ILLNESS=@ILLNESS AND IDCENTER=@IDCENTER";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@ID", center.IdOrganization);
                        command.Parameters.AddWithValue("@ORGTYPE", center.orgType);
                        command.Parameters.AddWithValue("@ILLNESS", center.illness);
                        command.Parameters.AddWithValue("@IDCENTER", center.idCenter);
                        command.Parameters.AddWithValue("@IDADRESS", center.idAddress);
                        command.Parameters.AddWithValue("@CENTERDESCRIPTION", center.centerDescription);
                        command.Parameters.AddWithValue("@TELEPHONE", center.telephone);
                        command.Parameters.AddWithValue("@IDADDRESS", center.idAddress);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }

        }

        public void Delete(int idOrganization, string orgType, string illness, int idCenter) {
            if (Get(idOrganization, orgType, illness, idCenter) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM CENTERS WHERE IdOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS AND idCenter=@IDCENTER";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                        command.Parameters.AddWithValue("@ORGTYPE", orgType);
                        command.Parameters.AddWithValue("@ILLNESS", illness);
                        command.Parameters.AddWithValue("@IDCENTER", idCenter);

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
