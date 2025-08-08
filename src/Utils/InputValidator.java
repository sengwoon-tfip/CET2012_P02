package Utils;

import java.util.regex.Pattern;

/**
 * Utility class containing helper methods for input validation.
 *
 * <p>This class currently provides email validation logic that checks whether
 * a given string conforms to a specific format based on company rules.</p>
 */
public class InputValidator {

    /** Regex pattern used for validating email addresses. */
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
    private static final Pattern STRING_PATTERN = Pattern.compile(
            "[a-zA-Z0-9_]{1,}"
    );
    /**
     * Validates an email address against a custom pattern.
     *
     * <p>The email must:
     * <ul>
     *   <li>Not start or end with '.' or '-'</li>
     *   <li>Contain only alphanumeric characters, '.', '-', and '_'</li>
     *   <li>Have a valid domain and suffix (e.g. example.com)</li>
     * </ul>
     *
     * @param email the email address to validate
     * @return {@code true} if the email matches the pattern;
     *         {@code false} otherwise
     */
    public static boolean validate_email(String email) {
        if (email.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean validate_string(String string) {
        if (string.isEmpty()) return false;
        return STRING_PATTERN.matcher(string).matches();
    }
}