using Microsoft.Extensions.Configuration;
using OTEAServer.Models;
using System.Data.SqlClient;

namespace OTEAServer.Services
{
    public class EvaluatorTeamMembersService
    {


        private static IConfiguration _configuration;
        public EvaluatorTeamMembersService(IConfiguration configuration)
        {
            _configuration = configuration;
        }


        //Operaciones GET

        public List<EvaluatorTeamMember> GetAll()
        {
            List<EvaluatorTeamMember> evaluatorTeamList = new List<EvaluatorTeamMember>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVALUATORTEAMMEMBERS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            evaluatorTeamList.Add(new EvaluatorTeamMember(reader.GetString(0),reader.GetInt32(1),reader.GetInt32(2),reader.GetString(3),reader.GetString(4)));
                        }
                    }
                }
            }
            return evaluatorTeamList;

        }

        public List<EvaluatorTeamMember> GetAllByEvaluatorTeam(int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness)
        {
            List<EvaluatorTeamMember> evaluatorTeamList = new List<EvaluatorTeamMember>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVALUATORTEAMMEMBERS WHERE idEvaluatorTeam=@IDEVALUATORTEAM AND idEvaluatorOrganization=@IDEVALUATORORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                    command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", idEvaluatorOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            evaluatorTeamList.Add(new EvaluatorTeamMember(reader.GetString(0), reader.GetInt32(1), reader.GetInt32(2), reader.GetString(3), reader.GetString(4)));
                        }
                    }
                }
            }
            return evaluatorTeamList;
        }

        public EvaluatorTeamMember Get(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness)
        {

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM EVALUATORTEAMMEMBERS WHERE emailUser=@EMAILUSER AND idEvaluatorTeam=@IDEVALUATORTEAM AND idEvaluatorOrganization=@IDEVALUATORORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EMAILUSER", emailUser);
                    command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                    command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", idEvaluatorOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new EvaluatorTeamMember(reader.GetString(0), reader.GetInt32(1), reader.GetInt32(2), reader.GetString(3), reader.GetString(4));
                        }
                    }
                }
            }
            return null;
        }

        //Operación POST
        public void Add(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO EVALUATORTEAMMEMBERS (EMAILUSER,IDEVALUATORTEAM,IDEVALUATORORGANIZATION,ORGTYPE,ILLNESS) VALUES (@EMAILUSER,@IDEVALUATORTEAM,@IDEVALUATORORGANIZATION,@ORGTYPE,@ILLNESS)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@EMAILUSER",emailUser);
                    command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                    command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", idEvaluatorOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", illness);

                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness)
        {
            if (Get(emailUser,idEvaluatorTeam, idEvaluatorOrganization, orgType, illness) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM EVALUATORTEAMS WHERE emailUser=@EMAILUSER AND idEvaluatorTeam=@IDEVALUATORTEAM AND idEvaluatorOrganization=@IDEVALUATORORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EMAILUSER", emailUser);
                        command.Parameters.AddWithValue("@IDEVALUATORTEAM", idEvaluatorTeam);
                        command.Parameters.AddWithValue("@IDEVALUATORORGANIZATION", idEvaluatorOrganization);
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

        public void Update(string emailUser, int idEvaluatorTeam, int idEvaluatorOrganization, string orgType, string illness, EvaluatorTeamMember evaluatorTeamMember)
        {
            if (evaluatorTeamMember != null && Get(emailUser, idEvaluatorTeam, idEvaluatorOrganization, orgType, illness) != null && emailUser==evaluatorTeamMember.emailUser && idEvaluatorTeam==evaluatorTeamMember.idEvaluatorTeam && idEvaluatorOrganization==evaluatorTeamMember.idEvaluatorOrganization && orgType==evaluatorTeamMember.orgType && illness==evaluatorTeamMember.illness)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE EVALUATORTEAMMEMBERSS SET EMAILUSER=@NEWEMAILUSER, IDEVALUATORTEAM=@NEWIDEVALUATORTEAM, IDEVALUATORORGANIZATION=@NEWIDEVALUATORORGANIZATION, ORGTYPE=@NEWORGTYPE, ILLNESS=@NEWILLNESS WHERE EMAILUSER=@OLDEMAILUSER AND IDEVALUATORTEAM=@OLDIDEVALUATORTEAM AND IDEVALUATORORGANIZATION=@OLDIDEVALUATORORGANIZATION AND ORGTYPE=@OLDORGTYPE AND ILLNESS=@OLDILLNESS";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@NEWEMAILUSER", evaluatorTeamMember.emailUser);
                        command.Parameters.AddWithValue("@NEWIDEVALUATORTEAM", evaluatorTeamMember.idEvaluatorTeam);
                        command.Parameters.AddWithValue("@NEWIDEVALUATORORGANIZATION", evaluatorTeamMember.idEvaluatorOrganization);
                        command.Parameters.AddWithValue("@NEWORGTYPE", evaluatorTeamMember.orgType);
                        command.Parameters.AddWithValue("@NEWILLNESS", evaluatorTeamMember.illness); 
                        command.Parameters.AddWithValue("@OLDEMAILUSER", emailUser);
                        command.Parameters.AddWithValue("@OLDIDEVALUATORTEAM", idEvaluatorTeam);
                        command.Parameters.AddWithValue("@OLDIDEVALUATORORGANIZATION", idEvaluatorOrganization);
                        command.Parameters.AddWithValue("@OLDORGTYPE", orgType);
                        command.Parameters.AddWithValue("@OLDILLNESS", illness);

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
