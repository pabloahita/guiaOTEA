package gui.data;

import com.google.gson.Gson;

import cli.user.User;
import connection.ConnectionToDatabase;
import gui.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


    /**HTTP Connection*/
    String url= ConnectionToDatabase.getUrlHTTP();
    public Result<User> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            HttpURLConnection connection= (HttpURLConnection) new URL(url+"?action=login?username="+username+"?password"+password).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // lee la respuesta de la solicitud GET como una cadena JSON
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String json = in.readLine();

                // analiza la cadena JSON en un objeto User usando la biblioteca Gson
                Gson gson = new Gson();
                User user = gson.fromJson(json, User.class);

                // cierra la conexión y el lector
                in.close();
                connection.disconnect();
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new IOException("Email or password wrong", new Exception()));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}