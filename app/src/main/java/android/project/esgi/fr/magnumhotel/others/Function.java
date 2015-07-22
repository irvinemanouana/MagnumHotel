package android.project.esgi.fr.magnumhotel.others;

import android.content.Context;
import android.project.esgi.fr.magnumhotel.R;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sylvain on 14/07/15.
 */
public class Function {

    /**
     * Vérifie la conformité de l'email
     * @param email l'adresse mail à vérifier
     * @return vrai si l'email est conforme sinon faux
     */
    public static boolean isEmailAddress(String email){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }

    /**
     * Vérifie si un champs contient seulement des lettres
     * @param text la chaine à verifier
     * @return vrai si c'est conforme sinon faux
     */
    public static boolean isString(String text){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher matcher = p.matcher(text);
        return matcher.matches();
    }

    /**
     * Transforme une date(String) en date(Date)
     * @param date une date(String)
     * @return une date(Date)
     */
    public static Date stringToDate(String date){

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date convertedDate = null;
            try {
                convertedDate = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return convertedDate;
    }

    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    /**
     * Convertie une simple date en date complete (1 janvier 2015)
     * @param date une date(Date)
     * @return une date complete (Date)
     */
    public static Date dateToFullDate(Date date) {

        // Date sous le format complet (1 janvier 2015)
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);

        String newDate = dateFormat.format(date);
        try {
            date = dateFormat.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String differenceDate(Date date1, Date date2, Context context){

        String dateDisplay;

        long diffDays = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        if(diffDays > 7){
            long diffWeeks = (date2.getTime() - date1.getTime()) /   ((1000 * 60 * 60) * (24 * 7));
            if(diffDays > 8)dateDisplay = diffWeeks +" "+ context.getResources().getString(R.string.weeks);
            else dateDisplay = diffWeeks +" "+ context.getResources().getString(R.string.week);
        }else{
            if(diffDays > 1)dateDisplay = diffDays +" "+context.getResources().getString(R.string.days);
            else dateDisplay = diffDays +" "+context.getResources().getString(R.string.day);
        }

        return dateDisplay;
    }

}
