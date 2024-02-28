package gui.data;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cli.user.User;
import gui.data.model.PasswordIncorrectException;
import gui.data.model.UnknownUserException;
import misc.PasswordCodifier;
import otea.connection.ConnectionClient;
import otea.connection.controller.UsersController;
import session.Session;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<JsonObject> login(String username, String password) {

        try {
            JsonObject credentials=new JsonObject();
            credentials.addProperty("email",username);
            credentials.addProperty("password",PasswordCodifier.codify(password));
            JsonObject response = UsersController.getInstance().Login(credentials);
            return new Result.Success<>(response);
        } catch (Exception e) {
            if(e.getMessage().equals("Unauthorized")){
                return new Result.Error(new PasswordIncorrectException("The password is wrong, please put the correct one"));
            }
            else if(e.getMessage().equals("Bad request")){
                return new Result.Error(new UnknownUserException("The user doesn't exists in our database. Please, sign up and try to login again"));
            }
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }


}