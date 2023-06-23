package gui.data;


import cli.user.User;
import gui.data.model.PasswordIncorrectException;
import gui.data.model.UnknownUserException;
import otea.connection.caller.Caller;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


    Caller caller;
    public Result<User> login(String username, String password) {

        caller=new Caller();
        try {
            //LoggedInUser user=new LoggedInUser(username,password);
            //Codificar contraseña
            User user=caller.obtainUserForLogin(username,password);
            if(user==null){
                User aux=caller.obtainUser(username);
                if(aux!=null && password.equals(aux.getPasswordUser())){
                    return new Result.Error(new PasswordIncorrectException("The password is wrong, please put the correct one"));
                }
                else if(aux==null){
                    return new Result.Error(new UnknownUserException("The user doesn't exists in our database. Please, sign up and try to login again"));
                }
            }
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}