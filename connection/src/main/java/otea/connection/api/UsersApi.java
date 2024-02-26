package otea.connection.api;

import org.json.JSONObject;

import java.util.List;

import cli.user.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsersApi {

    /**Gets all users*/
    @GET("Users/all")
    Call<List<User>> GetAll();

    /**
     * Gets all organization users of an organization
     *
     * @param idOrganization - User organization identifier
     * @param orgType - User organization type
     * @param illness - User organization illness or syndrome
     * */
    @GET("Users/allByOrg")
    Call<List<User>> GetAllOrgUsersByOrganization(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness);
    
    /**
     * Gets an user
     *
     * @param email - User email
     * */
    @GET("Users/get")
    Call<User> Get(@Query("email") String email);

    /**
     * Creates an user
     *
     * @param user - User
     * */
    @POST("Users")
    Call<User> Create(@Body User user);

    /**
     * Updates an user
     *
     * @param email - User email
     * @param user - User
     * */
    @PUT("Users")
    Call<User> Update(@Query("email") String email, @Body User user);

    /**
     * Deletes an user
     *
     * @param email - User email
     * */
    @DELETE("Users")
    Call<User> Delete(@Query("email") String email);

    /**
     * Login method
     *
     * @param email - User email
     * @param password - User password
     * */
    @POST("Users/login")
    Call<JSONObject> Login(@Query("email") String email, @Query("password") String password);
}
