package otea.connection;

import java.util.List;

import cli.user.OrganizationUser;
import cli.user.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsersApi {

    // GET all action
    @GET("Users")
    Call<List<User>> GetAll();
    //GET all by user type
    @GET("Users/userType={userType}")
    Call<List<User>> GetAllByType(@Path("userType") String userType);

    //GET all organization users by organization type
    @GET("Users/idOrganization={idOrganization}:orgType={orgType}")
    Call<List<OrganizationUser>> GetAllOrgUsersByOrganization(@Path("idOrganization") int idOrganization, @Path("orgType") String orgType);
    
    // GET by EMAIL action
    @GET("Users/email={email}")
    Call<User> Get(@Path("email") String email);

    // GET by EMAIL and USER TYPE action

    @GET("Users/email={email}:userType={userType}")
    Call<User> GetByType(String email, String userType);

    // GET by EMAIL and ORGANIZATION action

    @GET("Users/email={email}:idOrganization={idOrganization}:orgType={orgType}")
    Call<OrganizationUser> GetOrgUserByOrganization(@Path("email") String email,@Path("idOrganization") int idOrganization,@Path("orgType") String orgType);

    // POST action
    @POST("Users")
    Call<User> Create(String email, String first_Name, String last_Name, String password, String userType, int telephone, int idOrganization, String organizationType, String illness);

    // PUT action
    @PUT("Users/{email}")
    Call<User> Update(@Path("email") String email,@Body User user);

    // DELETE action
    @DELETE("Users/{email}")
    Call<User> Delete(@Path("email") String email);
}
