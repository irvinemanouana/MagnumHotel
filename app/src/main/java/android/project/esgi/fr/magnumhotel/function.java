package android.project.esgi.fr.magnumhotel;

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

}
