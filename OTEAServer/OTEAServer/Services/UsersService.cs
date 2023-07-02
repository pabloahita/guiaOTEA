using OTEAServer.Models;
using System.Data.SqlClient;
using System.Security.Policy;

namespace OTEAServer.Services
{
    public class UsersService
    {
        private static IConfiguration _configuration;
        public UsersService(IConfiguration configuration)
        {
            _configuration = configuration;
        }


        //Operaciones GET

        public List<User> GetAll()
        {
            List<User> usersList = new List<User>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int idOrganization = reader.IsDBNull(6) ? -1 : reader.GetInt32(6);
                            string organizationType = reader.IsDBNull(7) ? "" : reader.GetString(7);
                            string illness = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            usersList.Add(new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), idOrganization,organizationType,illness));
                        }
                    }
                }
            }
            return usersList;

        }
        public List<User> GetAllByType(string userType)
        {
            List<User> usersList = new List<User>();

            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS WHERE userType=@USERTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@USERTYPE", userType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int idOrganization = reader.IsDBNull(6) ? -1 : reader.GetInt32(6);
                            string organizationType = reader.IsDBNull(7) ? "" : reader.GetString(7);
                            string illness = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            usersList.Add(new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), idOrganization, organizationType, illness));
                        }
                    }
                }
            }
            return usersList;

        }

        public List<User> GetAllOrgUsersByOrganization(int idOrganization, string orgType)
        {
            List<User> usersList=new List<User>();
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS WHERE userType=@USERTYPE AND idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ORGTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@USERTYPE", "ORGANIZATION");
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", "AUTISM");
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            usersList.Add(new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), reader.GetInt32(6), reader.GetString(7), reader.GetString(8)));
                        }
                    }
                }
            }
            return usersList;

        }



        public User? Get(string email)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS WHERE emailUser=@EMAIL";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EMAIL", email);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            int idOrganization = reader.IsDBNull(6) ? -1 : reader.GetInt32(6);
                            string organizationType = reader.IsDBNull(7) ? "" : reader.GetString(7);
                            string illness = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            return new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), idOrganization, organizationType, illness);
                        }
                    }
                }
            }
            return null;
        }

        public User? GetForLogin(string email, string password)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS WHERE emailUser=@EMAIL AND passwordUser=@PASSWORD";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EMAIL", email);
                    command.Parameters.AddWithValue("@PASSWORD", password);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            int idOrganization = reader.IsDBNull(6) ? -1 : reader.GetInt32(6);
                            string organizationType = reader.IsDBNull(7) ? "" : reader.GetString(7);
                            string illness = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            return new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), idOrganization, organizationType, illness);
                        }
                    }
                }
            }
            return null;
        }
        public User? GetByType(string email,string userType)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS WHERE emailUser=@EMAIL AND userType=@USERTYPE";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EMAIL", email);
                    command.Parameters.AddWithValue("@USERTYPE", userType);
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            int idOrganization = reader.IsDBNull(6) ? -1 : reader.GetInt32(6);
                            string organizationType = reader.IsDBNull(7) ? "" : reader.GetString(7);
                            string illness = reader.IsDBNull(8) ? "" : reader.GetString(8);
                            return new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), idOrganization, organizationType, illness);
                        }
                    }
                }
            }
            return null;
        }

        public User? GetOrgUserByOrganization(string email, int idOrganization, string orgType)
        {
            string connectionString = _configuration.GetConnectionString("DefaultConnection");
            string query = "SELECT * FROM USERS WHERE emailUser=@EMAIL AND userType=@USERTYPE AND idOrganization=@IDORGANIZATION AND orgType=@ORGTYPE AND illness=@ILLNESS";

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                connection.Open();

                using (SqlCommand command = new SqlCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@EMAIL", email);
                    command.Parameters.AddWithValue("@USERTYPE", "ORGANIZATION");
                    command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    command.Parameters.AddWithValue("@ORGTYPE", orgType);
                    command.Parameters.AddWithValue("@ILLNESS", "AUTISM");
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            return new User(reader.GetString(0), reader.GetString(1), reader.GetString(3), reader.GetString(4), reader.GetString(2), reader.GetInt64(5), reader.GetInt32(6), reader.GetString(7), reader.GetString(8));
                        }
                    }
                }
            }
            return null;
        }


        //Operación POST
        public void Add(string emailUser, string first_name, string last_name, string passwordUser, string userType, long telephone, int idOrganization, string organizationType, string illness)
        {
            
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el command SQL
                string sql = "INSERT INTO USERS (emailUser, userType, passwordUser, first_name, last_name, telephone, idOrganization, organizationType, illness) VALUES (@EMAIL,@USERTYPE,@PASSWORD,@FIRSTNAME,@LASTNAME,@TELEPHONE,@IDORGANIZATION,@ORGANIZATIONTYPE,@ILLNESS)";
                using (SqlCommand command = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    command.Parameters.AddWithValue("@EMAIL", emailUser);
                    command.Parameters.AddWithValue("@FIRSTNAME", first_name);
                    command.Parameters.AddWithValue("@LASTNAME", last_name);
                    command.Parameters.AddWithValue("@PASSWORD", passwordUser);
                    command.Parameters.AddWithValue("@TELEPHONE", telephone);
                    command.Parameters.AddWithValue("@USERTYPE", userType);
                    if (idOrganization!=-1)
                    {
                        command.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@IDORGANIZATION", DBNull.Value);
                    }

                    // Establecer valor nulo para organizationType si es null o cadena vacía
                    if (organizationType!="-")
                    {
                        command.Parameters.AddWithValue("@ORGANIZATIONTYPE", organizationType);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@ORGANIZATIONTYPE", DBNull.Value);
                    }

                    // Establecer valor nulo para illness si es null o cadena vacía
                    if (illness!="-")
                    {
                        command.Parameters.AddWithValue("@ILLNESS", illness);
                    }
                    else
                    {
                        command.Parameters.AddWithValue("@ILLNESS", DBNull.Value);
                    }


                    // Ejecuta el command
                    command.ExecuteNonQuery();
                }

                // Cierra la conexión a la base de datos
                connection.Close();
            }
        }

        //Operación delete

        public void Delete(string email)
        {
            if (Get(email) != null)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "DELETE FROM USERS WHERE emailUser=@EMAIL";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EMAIL", email);

                        // Ejecuta el command
                        command.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(string email,User user)
        {
            if (user !=null && email == user.emailUser)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el command SQL
                    string sql = "UPDATE USERS SET FIRSTNAME=@FIRSTNAME, LASTNAME=@LASTNAME, PASSWORDUSER=@PASSWORD, TELEPHONE=@TELEPHONE, USERTYPE=@USERTYPE, IDORGANIZATION=@IDORGANIZATION, ORGANIZATIONTYPE=@ORGANIZATIONTYPE,ILLNESS=@ILLNESS WHERE EMAILUSER=@EMAIL";
                    using (SqlCommand command = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        command.Parameters.AddWithValue("@EMAIL", user.emailUser);
                        command.Parameters.AddWithValue("@FIRSTNAME", user.first_name);
                        command.Parameters.AddWithValue("@LASTNAME", user.last_name);
                        command.Parameters.AddWithValue("@PASSWORD", user.passwordUser);
                        command.Parameters.AddWithValue("@TELEPHONE", user.telephone);
                        command.Parameters.AddWithValue("@USERTYPE", user.userType);
                        command.Parameters.AddWithValue("@IDORGANIZATION", user.idOrganization);
                        command.Parameters.AddWithValue("@ORGANIZATIONTYPE", user.organizationType);
                        command.Parameters.AddWithValue("@ILLNESS", user.illness);

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
