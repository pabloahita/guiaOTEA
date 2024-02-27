package gui.data;


import com.google.gson.JsonObject;

import cli.user.User;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private JsonObject data = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return data != null;
    }

    public void logout() {
        data = null;
        dataSource.logout();
    }

    private void setLoggedInUser(JsonObject data) {
        this.data = data;
    }

    public Result<JsonObject> login(String username, String password) {
        // handle login
        Result<JsonObject> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<JsonObject>) result).getData());
        }
        return result;
    }
}