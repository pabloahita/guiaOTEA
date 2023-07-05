package otea.connection.api;

import java.util.List;

import cli.user.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsersApi {

    // GET all action
    @GET("Users")
    Call<List<User>> GetAll();
    //GET all by user type
    @GET("Users/getAllByType::userType={userType}")
    Call<List<User>> GetAllByType(@Path("userType") String userType);

    //GET all organization users by organization type
    @GET("Users/getAllByOrg::idOrganization={idOrganization}:orgType={orgType}:illness={illness}")
    Call<List<User>> GetAllOrgUsersByOrganization(@Path("idOrganization") int idOrganization, @Path("orgType") String orgType, @Path("illness") String illness);
    
    // GET by EMAIL action
    @GET("Users/get::email={email}")
    Call<User> Get(@Path("email") String email);

    // GET by EMAIL and USER TYPE action

    @GET("Users/getByType::email={email}:userType={userType}")
    Call<User> GetByType(String email, String userType);

    // GET by EMAIL and ORGANIZATION action

    @GET("Users/getByOrg::email={email}:idOrganization={idOrganization}:orgType={orgType}:illness={illness}")
    Call<User> GetOrgUserByOrganization(@Path("email") String email,@Path("idOrganization") int idOrganization,@Path("orgType") String orgType,@Path("illness") String illness);

    // POST action
    @POST("Users")
    Call<User> Create(@Body User user);

    // PUT action
    @PUT("Users/upd::email={email}")
    Call<User> Update(@Path("email") String email, @Body User user);

    // DELETE action
    @DELETE("Users/del::email={email}")
    Call<User> Delete(@Path("email") String email);

    @GET("Users/login::email={email}:password={password}")
    Call<User> GetForLogin(@Path("email") String email, @Path("password") String password);
}
