package gui.data;


import gui.data.model.LoggedInUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


    /**HTTP Connection*/

    public Result<LoggedInUser> login(String username, String password) {

        try {
            LoggedInUser user=new LoggedInUser(username,password);
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}