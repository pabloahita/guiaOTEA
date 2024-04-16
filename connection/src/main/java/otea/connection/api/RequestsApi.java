package otea.connection.api;

import java.util.List;

import cli.user.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * API for requests operations
 *
 * @author Pablo Ahíta del Barrio
 * @version 1
 * */
public interface RequestsApi {

    /**Gets all requests*/
    @GET("Requests/all")
    Call<List<Request>> GetAll();

    /**
     * Gets a request
     *
     * @param email - Email of the register request
     * */
    @GET("Requests/get")
    Call<Request> Get(@Query("email") String email);

    /**
     * Create a request
     *
     * @param request - Request
     * */
    @POST("Requests")
    Call<Request> Create(@Body Request request);

    /**
     * Updates a request
     *
     * @param email - Email of the register request
     * @param request - Request
     * */
    @PUT("Requests")
    Call<Request> Update(@Query("email") String email,@Body Request request);

    /**
     * Deletes a request
     *
     * @param email - Email of the register request
     * */
    @DELETE("Requests")
    Call<Request> Delete(@Query("email") String email);
}
