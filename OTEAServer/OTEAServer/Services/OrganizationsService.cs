using OTEAServer.Models;
using System.Data.SqlClient;
using System.Net;
using System.Xml.Linq;

namespace OTEAServer.Services
{
    public class OrganizationsService
    {
        private static IConfiguration _configuration;
        public OrganizationsService(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        public List<Organization> GetAll()
        {
            List<Organization> orgsList = new List<Organization>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM ORGANIZATIONS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            orgsList.Add(new Organization(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetString(5), reader.GetInt64(6), reader.GetString(7), reader.GetString(8), reader.GetString(9)));
                        }
                    }
                }
            }
            return orgsList;

        }

        public List<Organization> GetAllByType(string orgType) {
            List<Organization> orgsList = new List<Organization>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM ORGANIZATIONS WHERE orgType=@ORGTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            orgsList.Add(new Organization(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetString(5), reader.GetInt64(6), reader.GetString(7), reader.GetString(8), reader.GetString(9)));
                        }
                    }
                }
            }
            return orgsList;
        }

        public List<Organization> GetAllEvaluatedOrganizations() => GetAllByType("EVALUATED");


        public List<Organization> GetAllEvaluatorOrganizations() => GetAllByType("EVALUATOR");

        public Organization Get(int id, string orgType, string illness)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM ORGANIZATIONS WHERE orgType=@ORGTYPE AND idOrganization=@ID AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ID", id);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new Organization(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetString(5), reader.GetInt64(6), reader.GetString(7), reader.GetString(8), reader.GetString(9));
                        }
                    }
                }
            }
            return null;

        }

        public Organization GetEvaluatedOrganizationById(int id, string illness) => Get(id, "EVALUATED", illness);

        public Organization GetEvaluatorOrganizationById(int id, string illness) => Get(id, "EVALUATOR", illness);


        public void Add(int id, string orgType, string illness, string name, int idAddress, string email, long telephone, string information, string emailOrgPrincipal, string emailOrgConsultant)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el comando SQL
                string sql = "INSERT INTO ORGANIZATIONS (ID,ORGTYPE,ILLNESS,NAME,IDADDRESS,EMAIL,TELEPHONE,INFORMATION,EMAILORGPRINCIPAL,EMAILORGCONSULTANT) VALUES (@ID,@ORGTYPE,@ILLNESS,@NAME,@IDADDRESS,@EMAIL,@TELEPHONE,@INFORMATION,@EMAILORGPRINCIPAL,@EMAILORGCONSULTANT)";
                using (SqlCommand comando = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    comando.Parameters.AddWithValue("@ID", id);
                    comando.Parameters.AddWithValue("@ORGTYPE", orgType);
                    comando.Parameters.AddWithValue("@ILLNESS", illness);
                    comando.Parameters.AddWithValue("@NAME", name);
                    comando.Parameters.AddWithValue("@IDADRESS", idAddress);
                    comando.Parameters.AddWithValue("@EMAIL", email);
                    comando.Parameters.AddWithValue("@TELEPHONE", telephone);
                    comando.Parameters.AddWithValue("@INFORMATION", information);
                    comando.Parameters.AddWithValue("@EMAILORGPRINCIPAL", emailOrgPrincipal);
                    comando.Parameters.AddWithValue("@EMAILORGCONSULTANT", emailOrgConsultant);

                    // Ejecuta el comando
                    comando.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(int id, string orgType, string illness)
        {
            if (Get(id, orgType,illness) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el comando SQL
                    string sql = "DELETE FROM ORGANIZATIONS WHERE idOrganization=@ID AND orgType=@ORGTYPE AND illness=@ILLNESS";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@ID", id);
                        comando.Parameters.AddWithValue("@ORGTYPE", orgType);
                        comando.Parameters.AddWithValue("@ILLNESS", illness);
                        // Ejecuta el comando
                        comando.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(Organization organization)
        {
            if (organization != null && Get(organization.IdOrganization, organization.orgType, organization.illness) == organization)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el comando SQL
                    string sql = "UPDATE ORGANIZATIONS SET NAME=@NAME,IDADDRESS=@IDADDRESS,EMAIL=@EMAIL,TELEPHONE=@TELEPHONE,INFORMATION=@INFORMATION,EMAILORGPRINCIPAL=@EMAILORGPRINCIPAL,EMAILORGCONSULTANT=@EMAILORGCONSULTANT WHERE ID=@ID AND ORGTYPE=@ORGTYPE AND ILLNESS=@ILLNESS";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@ID", organization.IdOrganization);
                        comando.Parameters.AddWithValue("@ORGTYPE", organization.orgType);
                        comando.Parameters.AddWithValue("@ILLNESS", organization.illness);
                        comando.Parameters.AddWithValue("@NAME", organization.nameOrg);
                        comando.Parameters.AddWithValue("@IDADRESS", organization.idAddress);
                        comando.Parameters.AddWithValue("@EMAIL", organization.email);
                        comando.Parameters.AddWithValue("@TELEPHONE", organization.telephone);
                        comando.Parameters.AddWithValue("@INFORMATION", organization.information);
                        comando.Parameters.AddWithValue("@EMAILORGPRINCIPAL", organization.emailOrgPrincipal);
                        comando.Parameters.AddWithValue("@EMAILORGCONSULTANT", organization.emailOrgConsultant);

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
