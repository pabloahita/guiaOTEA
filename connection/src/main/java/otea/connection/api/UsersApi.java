package otea.connection.api;

import java.util.List;

import cli.user.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsersApi {

    // GET all action
    @GET("Users/all")
    Call<List<User>> GetAll();
    //GET all by user type
    @GET("Users/allByType")
    Call<List<User>> GetAllByType(@Query("userType") String userType);

    //GET all organization users by organization type
    @GET("Users/allByOrg")
    Call<List<User>> GetAllOrgUsersByOrganization(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness);
    
    // GET by EMAIL action
    @GET("Users/get")
    Call<User> Get(@Query("email") String email);

    // GET by EMAIL and USER TYPE action

    @GET("Users/type")
    Call<User> GetByType(@Query("email") String email, @Query("userType")String userType);

    // GET by EMAIL and ORGANIZATION action

    @GET("Users/org")
    Call<User> GetOrgUserByOrganization(@Query("email") String email,@Query("idOrganization") int idOrganization,@Query("orgType") String orgType,@Query("illness") String illness);

    // POST action
    @POST("Users")
    Call<User> Create(@Body User user);

    // PUT action
    @PUT("Users")
    Call<User> Update(@Query("email") String email, @Body User user);

    // DELETE action
    @DELETE("Users")
    Call<User> Delete(@Query("email") String email);

    @GET("Users/login")
    Call<User> GetForLogin(@Query("email") String email, @Query("password") String password);
}
