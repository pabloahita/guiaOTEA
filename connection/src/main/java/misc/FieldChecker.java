package misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldChecker {

    public static boolean isASpanishNumber(String inputText){
        return inputText.length()==9 && (inputText.startsWith("6") || inputText.startsWith("9"));
    }

    public static boolean isAForeignNumber(String telephone){
        return telephone.startsWith("00") && telephone.length()>=8;
    }

    public static boolean emailHasCorrectFormat(String email){
        Pattern patronEmail=Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher= patronEmail.matcher(email);
        return matcher.matches();
    }

    public static boolean passwordHasCorrectFormat(String email){
        Pattern patronPassword=Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$&%#.!\"^¨_:;/+><()=¿?¡!-]).{6,}$");
        Matcher matcher= patronPassword.matcher(email);
        return matcher.matches();
    }


}
