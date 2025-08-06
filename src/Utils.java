import java.util.regex.Pattern;

public class Utils {

    // Regex pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^" +
                    // Local part
                    // Must not start with . or -
                    "(?![.-])" +
                    // Allowed chars and structure
                    "(?:[A-Za-z0-9_]|(?<![.-])[.-](?![.-]))+" +
                    // Must not end with . or -
                    "(?<![.-])" +
                    "@" +
                    // Domain name
                    "(?![.-])" +
                    // domain name (yyyy)
                    "(?:[A-Za-z0-9]|(?<![.-])[.-](?![.-]))+" +
                    "(?<![.-])" +
                    "\\." +
                    // Domain suffix
                    // domain extension (xxx)
                    "[a-z]{2,3}" +
                    "$"
    );

    /**
     * Validates an email address according to the company rules.
     *
     * @param email the email string to validate
     * @return true if the email is valid; false otherwise
     */
    public static boolean validate_email(String email) {
        if (email == null || email.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
}