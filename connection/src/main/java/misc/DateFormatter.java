package misc;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatter {

    public static long formatDate(String date){

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date parsedDate = null;
        try {
            parsedDate = inputFormat.parse(date);
            String formattedDate = outputFormat.format(parsedDate);
            Date outputDate = outputFormat.parse(formattedDate);
            return outputDate.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String timeStampToStrDate(long timestamp){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date(timestamp));
    }

    public static long formaUtilDateToTimestamp(Date date){
        return date.getTime();
    }
}
