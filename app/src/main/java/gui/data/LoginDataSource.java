package gui.data;


import cli.user.User;
import gui.data.model.PasswordIncorrectException;
import gui.data.model.UnknownUserException;
import misc.PasswordCodifier;
import otea.connection.controller.UsersController;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(String username, String password) {

        try {
            //LoggedInUser user=new LoggedInUser(username,password);
            //Codificar contraseña
            String nuevaPassword= PasswordCodifier.codify(password);
            User user= UsersController.getInstance().GetForLogin(username,nuevaPassword);
            if(user==null){
                User aux= UsersController.getInstance().Get(username);
                if(aux!=null){
                    return new Result.Error(new PasswordIncorrectException("The password is wrong, please put the correct one"));
                }
                else{
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