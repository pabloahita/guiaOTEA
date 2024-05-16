package misc;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldChecker {

    public static boolean isACorrectPhone(String phone){
        try {
            PhoneNumberUtil phoneNumberUtil=PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phone, null);
            return phoneNumberUtil.isValidNumber(number);
        } catch (Exception e) {
            System.out.println("Error al analizar el número de teléfono: " + e.getMessage());
        }
        return false;
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

    public static boolean isPrecharged(String idCountry){
        return idCountry.equals("ESP") || idCountry.equals("AND") || idCountry.equals("USA") || idCountry.equals("ARG") || idCountry.equals("BOL")
                || idCountry.equals("CHL") || idCountry.equals("COL") || idCountry.equals("CRI") || idCountry.equals("CUB") || idCountry.equals("DOM") || idCountry.equals("ECU") ||
                idCountry.equals("SLV") || idCountry.equals("GUA") || idCountry.equals("PRT") || idCountry.equals("HND") || idCountry.equals("MEX") || idCountry.equals("NIC") ||
                idCountry.equals("PAN") || idCountry.equals("PRY") || idCountry.equals("PER") || idCountry.equals("PRI") || idCountry.equals("URY") || idCountry.equals("VEN");
    }


}
