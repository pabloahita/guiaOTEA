using Microsoft.Extensions.Configuration;
using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class EvaluatorTeamsService
    {
        private static IConfiguration _configuration;
        public EvaluatorTeamsService(IConfiguration configuration)
        {
            _configuration = configuration;
        }


        //Operaciones GET

        public List<EvaluatorTeam> GetAll()
        {
            List<EvaluatorTeam> evaluatorTeamList = new List<EvaluatorTeam>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVALUATORTEAMS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            evaluatorTeamList.Add(new EvaluatorTeam(reader.GetInt32(0), reader.GetDateTime(1), reader.GetInt32(2), reader.GetString(3), reader.GetString(4), reader.GetString(5), reader.GetString(6), reader.GetString(7), reader.GetString(8), reader.GetString(9)));
                        }
                    }
                }
            }
            return evaluatorTeamList;

        }

        public List<EvaluatorTeam> GetAllByOrganization(int id, string orgType, string illness)
        {
            List<EvaluatorTeam> evaluatorTeamList = new List<EvaluatorTeam>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVALUATORTEAMS WHERE idOrganization=@ID AND orgType=@ORGTYPE AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@ID", id);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            evaluatorTeamList.Add(new EvaluatorTeam(reader.GetInt32(0), reader.GetDateTime(1), reader.GetInt32(2), reader.GetString(3), reader.GetString(4), reader.GetString(5), reader.GetString(6), reader.GetString(7), reader.GetString(8), reader.GetString(9)));
                        }
                    }
                }
            }
            return evaluatorTeamList;
        }

        public EvaluatorTeam Get(int idEvaluatorTeam, int idOrganization, string orgType, string illness)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVALUATORTEAMS WHERE idEvaluatorTeam=@IDEVALUATORTEAM AND idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            new EvaluatorTeam(reader.GetInt32(0), reader.GetDateTime(1), reader.GetInt32(2), reader.GetString(3), reader.GetString(4), reader.GetString(5), reader.GetString(6), reader.GetString(7), reader.GetString(8), reader.GetString(9));
                        }
                    }
                }
            }
            return null;
        }

        //Operación POST
        public void Add(int id, DateTime creation_date, int idOrganization, string orgType, string illness, string emailConsultant, string emailProfessional, string emailResponsible, string patient_name, string relative_name)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO EVALUATORTEAMS (ID,CREATIONDATE,IDORGANIZATION,ORGTYPE,ILLNESS,EMAILCONSULTANT,EMAILPROFESSIONAL,EMAILRESPONSIBLE) VALUES (@ID,@CREATIONDATE,@IDORGANIZATION,@ORGTYPE,@ILLNESS,@EMAILCONSULTANT,@EMAILPROFESSIONAL,@EMAILRESPONSIBLE)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@ID", id);
                    command.Parameters.AddWithValue("@CREATIONDATE", creation_date);
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGANIZATIONTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    command.Parameters.AddWithValue("@EMAILCONSULTANT", emailConsultant);
                    command.Parameters.AddWithValue("@EMAILPROFESSIONAL", emailProfessional);
                    command.Parameters.AddWithValue("@EMAILRESPONSIBLE", emailResponsible);

                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(int idEvaluatorTeam, int idOrganization, string orgType, string illness)
        {
            if (Get(idEvaluatorTeam,idOrganization,orgType,illness) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM EVALUATORTEAMS WHERE idEvaluatorTeam=@IDEVALUATORTEAM AND idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                        command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
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

        public void Update(EvaluatorTeam evaluatorTeam)
        {
            if (evaluatorTeam != null && Get(evaluatorTeam.idEvaluatorTeam,evaluatorTeam.idOrganization,evaluatorTeam.orgType,evaluatorTeam.illness) == evaluatorTeam)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE EVALUATORTEAMS SET CREATIONDATE=@CREATIONDATE, EMAILCONSULTANT=@EMAILCONSULTANT, EMAILPROFESSIONAL=@EMAILPROFESSIONAL, EMAILRESPONSIBLE=@EMAILRESPONSIBLE WHERE idEvaluatorTeam=@IDEVALUATORTEAM AND idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@ID", evaluatorTeam.idEvaluatorTeam);
                        command.Parameters.AddWithValue("@CREATIONDATE", evaluatorTeam.creationDate);
                        command.Parameters.AddWithValue("@IDORGANIZATION", evaluatorTeam.idOrganization);
                        command.Parameters.AddWithValue("@ORGANIZATIONTYPE", evaluatorTeam.orgType);
                        command.Parameters.AddWithValue("@ILLNESS", evaluatorTeam.illness);
                        command.Parameters.AddWithValue("@EMAILCONSULTANT", evaluatorTeam.emailConsultant);
                        command.Parameters.AddWithValue("@EMAILPROFESSIONAL", evaluatorTeam.emailProfessional);
                        command.Parameters.AddWithValue("@EMAILRESPONSIBLE", evaluatorTeam.emailResponsible);

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
