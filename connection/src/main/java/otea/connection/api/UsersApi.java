package otea.connection.api;

import com.google.gson.JsonObject;


import java.util.List;

import cli.user.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsersApi {

    /**Gets all users*/
    @GET("Users/all")
    Call<List<JsonObject>> GetAll(@Header("Authorization") String Authorization);

    /**
     * Gets all organization users of an organization
     *
     * @param idOrganization - User organization identifier
     * @param orgType - User organization type
     * @param illness - User organization illness or syndrome
     * */
    @GET("Users/allByOrg")
    Call<List<JsonObject>> GetAllOrgUsersByOrganization(@Query("idOrganization") int idOrganization, @Query("orgType") String orgType, @Query("illness") String illness, @Header("Authorization") String Authorization);
    
    /**
     * Gets an user
     *
     * @param email - User email
     * */
    @GET("Users/get")
    Call<JsonObject> Get(@Query("email") String email);//, @Header("Authorization") String Authorization);

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
    Call<User> Update(@Query("email") String email, @Body User user, @Header("Authorization") String Authorization);

    /**
     * Deletes an user
     *
     * @param email - User email
     * */
    @DELETE("Users")
    Call<User> Delete(@Query("email") String email, @Header("Authorization") String Authorization);

    /**
     * Login method
     *
     * @param credentials - Login credentials
     * */
    @POST("Users/login")
    Call<JsonObject> Login(@Body JsonObject credentials);
}
