package yankov.tsvetilian.watchit.Utilities;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidEmail(CharSequence email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidServer(CharSequence server) {
        return Patterns.WEB_URL.matcher(server).matches();
    }

    public static boolean isValidPassword(CharSequence password) {
        return password.length() >= 6;
    }

    public static boolean isValidName(CharSequence name) {
        return name.length() >= 4 && name.length() <= 20;
    }
}
