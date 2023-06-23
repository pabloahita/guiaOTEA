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
                            if (illness != null) { illness = ""; }
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
        public void Add(string email, string first_Name, string last_Name, string password, string userType, long telephone, int? idOrganization, string? organizationType, string? illness)
        {
            if (idOrganization == -1){ idOrganization = null; }
            if (organizationType == "")  { organizationType = null; }
            if (illness == "") { illness = null; }
            
            string connectionString = _configuration.GetConnectionString("DefaultConnection");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                // Abre la conexión a la base de datos
                connection.Open();

                // Crea el comando SQL
                string sql = "INSERT INTO USERS (EMAIL,FIRSTNAME,LASTNAME,PASSWORD,TELEPHONE,USERTYPE,IDORGANIZATION,ORGANIZATIONTYPE,ILLNESS) VALUES (@EMAIL,@FIRSTNAME,@LASTNAME,@PASSWORD,@TELEPHONE,@USERTYPE,@IDORGANIZATION,@ORGANIZATIONTYPE,@ILLNESS)";
                using (SqlCommand comando = new SqlCommand(sql, connection))
                {
                    // Añade parámetros para evitar la inyección de SQL
                    comando.Parameters.AddWithValue("@EMAIL", email);
                    comando.Parameters.AddWithValue("@FIRSTNAME", first_Name);
                    comando.Parameters.AddWithValue("@LASTNAME", last_Name);
                    comando.Parameters.AddWithValue("@PASSWORD", password);
                    comando.Parameters.AddWithValue("@TELEPHONE", telephone);
                    comando.Parameters.AddWithValue("@USERTYPE", userType);
                    comando.Parameters.AddWithValue("@IDORGANIZATION", idOrganization);
                    comando.Parameters.AddWithValue("@ORGANIZATIONTYPE", organizationType);
                    comando.Parameters.AddWithValue("@ILLNESS", illness);

                    // Ejecuta el comando
                    comando.ExecuteNonQuery();
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

                    // Crea el comando SQL
                    string sql = "DELETE FROM USERS WHERE emailUser=@EMAIL";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@EMAIL", email);

                        // Ejecuta el comando
                        comando.ExecuteNonQuery();
                    }

                    // Cierra la conexión a la base de datos
                    connection.Close();
                }
            }
        }

        public void Update(User user)
        {
            if (user !=null && Get(user.emailUser) == user)
            {
                string connectionString = _configuration.GetConnectionString("DefaultConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    // Abre la conexión a la base de datos
                    connection.Open();

                    // Crea el comando SQL
                    string sql = "UPDATE USERS SET FIRSTNAME=@FIRSTNAME, LASTNAME=@LASTNAME, PASSWORD=@PASSWORD, TELEPHONE=@TELEPHONE, USERTYPE=@USERTYPE, IDORGANIZATION=@IDORGANIZATION, ORGANIZATIONTYPE=@ORGANIZATIONTYPE,ILLNESS=@ILLNESS WHERE EMAIL=@EMAIL";
                    using (SqlCommand comando = new SqlCommand(sql, connection))
                    {
                        // Añade parámetros para evitar la inyección de SQL
                        comando.Parameters.AddWithValue("@EMAIL", user.emailUser);
                        comando.Parameters.AddWithValue("@FIRSTNAME", user.first_name);
                        comando.Parameters.AddWithValue("@LASTNAME", user.last_name);
                        comando.Parameters.AddWithValue("@PASSWORD", user.passwordUser);
                        comando.Parameters.AddWithValue("@TELEPHONE", user.telephone);
                        comando.Parameters.AddWithValue("@USERTYPE", user.userType);
                        comando.Parameters.AddWithValue("@IDORGANIZATION", user.idOrganization);
                        comando.Parameters.AddWithValue("@ORGANIZATIONTYPE", user.organizationType);
                        comando.Parameters.AddWithValue("@ILLNESS", user.illness);

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
