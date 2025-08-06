import java.util.regex.Pattern;

public class Utils {

    // Regex pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?![.-])" +                   // Local part must not start with . or -
                    "(?!.*[.-]{2})" +              // Local part must not have consecutive . or -
                    "(?!.*[.-]$)" +                // Local part must not end with . or -
                    "[A-Za-z0-9._-]+" +            // Local part: allowed characters
                    "@" +                          // Separator
                    "(?![.-])" +                   // Domain must not start with . or -
                    "(?!.*[.-]{2})" +              // Domain must not have consecutive . or -
                    "(?!.*[.-]\\.)" +              // First domain part must not end with . or -
                    "[A-Za-z0-9.-]+" +             // First domain part characters
                    "\\." +                        // Dot before TLD
                    "[a-z]{2,3}$"                  // TLD: only lowercase a-z, length 2-3
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