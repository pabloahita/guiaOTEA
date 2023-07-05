
using Microsoft.AspNetCore.Mvc;
using OTEAServer.Models;
using System.Data.SqlClient;
namespace OTEAServer.Services
{
    public class OrganizationsService
    {
        private static IConfiguration _configuration;
        public OrganizationsService([FromServices] IConfiguration configuration)
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
                            string emailOrgPrincipal = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            orgsList.Add(new Organization(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetString(5), reader.GetInt64(6), reader.GetString(7), emailOrgPrincipal));
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
                            string emailOrgPrincipal = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            orgsList.Add(new Organization(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetString(5), reader.GetInt64(6), reader.GetString(7), emailOrgPrincipal));
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
            string query = "SELECT * FROM ORGANIZATIONS WHERE orgType=@ORGTYPE AND IdOrganization=@ID AND illness=@ILLNESS";

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
                            string emailOrgPrincipal = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            return new Organization(reader.GetInt32(0), reader.GetString(1), reader.GetString(2), reader.GetString(3), reader.GetInt32(4), reader.GetString(5), reader.GetInt64(6), reader.GetString(7), emailOrgPrincipal);
                        }
                    }
                }
            }
            return null;

        }

        public Organization GetEvaluatedOrganizationById(int id, string illness) => Get(id, "EVALUATED", illness);

        public Organization GetEvaluatorOrganizationById(int id, string illness) => Get(id, "EVALUATOR", illness);


        public void Add(int id, string orgType, string illness, string name, int idAddress, string email, long telephone, string information, string emailOrgPrincipal)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO ORGANIZATIONS (IDORGANIZATION,ORGTYPE,ILLNESS,NAMEORG,IDADDRESS,EMAIL,TELEPHONE,INFORMATION,EMAILORGPRINCIPAL) VALUES (@ID,@ORGTYPE,@ILLNESS,@NAME,@IDADDRESS,@EMAIL,@TELEPHONE,@INFORMATION,@EMAILORGPRINCIPAL)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@ID", id);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    command.Parameters.AddWithValue("@NAME", name);
                    command.Parameters.AddWithValue("@IDADDRESS", idAddress);
                    command.Parameters.AddWithValue("@EMAIL", email);
                    command.Parameters.AddWithValue("@TELEPHONE", telephone);
                    command.Parameters.AddWithValue("@INFORMATION", information);
                    if (string.IsNullOrEmpty(emailOrgPrincipal))
                    {
                        command.Parameters.AddWithValue("@EMAILORGPRINCIPAL", DBNull.Value);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@EMAILORGPRINCIPAL", emailOrgPrincipal);
                    }

                    // Ejecuta el command
                    command.ExecuteNonQuery();
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

                    // Crea el command SQL
                    string sql = "DELETE FROM ORGANIZATIONS WHERE IdOrganization=@ID AND orgType=@ORGTYPE AND illness=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@ID", id);
                        command.Parameters.AddWithValue("@ORGTYPE", orgType);
                        command.Parameters.AddWithValue("@ILLNESS", illness);
                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(int id, string orgType, string illness, Organization organization)
        {
      
            if (organization != null && id==organization.IdOrganization && orgType==organization.orgType && illness==organization.illness)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE ORGANIZATIONS SET NAMEORG=@NAME,IDADDRESS=@IDADDRESS,EMAIL=@EMAIL,TELEPHONE=@TELEPHONE,INFORMATION=@INFORMATION,EMAILORGPRINCIPAL=@EMAILORGPRINCIPAL WHERE IDORGANIZATION=@ID AND ORGTYPE=@ORGTYPE AND ILLNESS=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@ID", organization.IdOrganization);
                        command.Parameters.AddWithValue("@ORGTYPE", organization.orgType);
                        command.Parameters.AddWithValue("@ILLNESS", organization.illness);
                        command.Parameters.AddWithValue("@NAME", organization.nameOrg);
                        command.Parameters.AddWithValue("@IDADDRESS", organization.idAddress);
                        command.Parameters.AddWithValue("@EMAIL", organization.email);
                        command.Parameters.AddWithValue("@TELEPHONE", organization.telephone);
                        command.Parameters.AddWithValue("@INFORMATION", organization.information);
                        if (string.IsNullOrEmpty(organization.emailOrgPrincipal))
                        {
                            command.Parameters.AddWithValue("@EMAILORGPRINCIPAL", DBNull.Value);
                        }
                        else
                        {
                            command.Parameters.AddWithValue("@EMAILORGPRINCIPAL", organization.emailOrgPrincipal);
                        }



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
