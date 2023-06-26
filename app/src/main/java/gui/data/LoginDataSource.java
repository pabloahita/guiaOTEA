package gui.data;


import cli.user.User;
import gui.data.model.PasswordIncorrectException;
import gui.data.model.UnknownUserException;
import otea.connection.caller.UsersCaller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<User> login(String username, String password) {

        try {
            //LoggedInUser user=new LoggedInUser(username,password);
            //Codificar contraseña
            String nuevaPassword=codificar(password);
            User user= UsersCaller.getInstance().obtainUserForLogin(username,nuevaPassword);
            if(user==null){
                User aux=UsersCaller.getInstance().obtainUser(username);
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

    public String codificar(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}