package Utils;

/**
 * Utility class for formatting words.
 * <p>
 * Provides methods to modify the capitalization of single words.
 * </p>
 */
public class WordFormatter {

    /**
     * Capitalises the first letter of the given word and converts the rest of
     * the letters to lowercase.
     * <p>
     * If the input string is empty, it returns the string unchanged.
     * </p>
     *
     * @param word the input string to capitalise; should represent a single
     *             word
     * @return the word with the first letter capitalised and the rest in
     * lowercase, or the original string if it is empty
     */
    public static String capitalise(String word) {
        return word.isEmpty()
                ? word
                : word.substring(0, 1).toUpperCase()
                + word.substring(1).toLowerCase();
    }
}