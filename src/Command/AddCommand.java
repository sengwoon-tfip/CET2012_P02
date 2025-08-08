package Command;

import Utils.InvalidInputException;
import Utils.InputValidator;
import Utils.WordFormatter;
import Receiver.Receiver;

/**
 * Concrete command that performs an add operation by delegating to a
 * {@link Receiver}.
 *
 * <p>This class follows the Command design pattern, encapsulating the logic
 * for adding a new entry to the receiver's data store. It uses shared
 * validation utilities from {@link InputValidator} to ensure the email format
 * is valid, and utility methods from {@link WordFormatter} to normalise the
 * casing of input parameters.</p>
 */
public class AddCommand implements Command {

    /** The receiver that actually performs the add operation. */
    private final Receiver receiver;

    /**
     * Parameters required for the add operation, space-separated.
     * <p>
     * This string is updated in {@link #execute()} after normalising the
     * casing of the first two parameters.
     * </p>
     */
    private String params;

    /**
     * Constructs an {@code AddCommand} with the specified receiver and
     * parameters.
     *
     * <p>Expects exactly three space-separated values in {@code params}, with
     * the third being a valid email address. Email validation is handled using
     * {@link InputValidator#validate_email(String)}.</p>
     *
     * @param receiver the receiver responsible for executing the add operation
     * @param params space-separated string containing exactly three values:
     *               <ul>
     *                 <li>First value: typically a name or identifier</li>
     *                 <li>Second value: an attribute such as a role or title</li>
     *                 <li>Third value: a valid email address</li>
     *               </ul>
     * @throws InvalidInputException if the number of parameters is not three or
     *                               if the email format is invalid
     */
    public AddCommand(Receiver receiver, String params) {
        this.receiver = receiver;
        this.params = params;
    }

    /**
     * Executes the add operation by validating the inputs, normalising the
     * casing of the first two parameters using {@link WordFormatter#capitalise(
     * String)}, joining them back into a string, and invoking the
     * {@code add} method on the receiver.
     *
     * <p>The parameters string is split by spaces, expecting exactly three
     * elements: two words (such as name and role/title) and an email. The first
     * and second elements are capitalised (first letter uppercase, rest
     * lowercase) while the third is validated as a proper email format.</p>
     *
     * <p>If validation fails, an {@link InvalidInputException} is thrown.</p>
     *
     * <p><b>Note:</b> The {@code params} field is updated with the capitalised
     * and re-joined string before being passed to the receiver.</p>
     *
     * @throws InvalidInputException if the input is malformed or contains an
     *                               invalid email
     */
    @Override
    public void execute() {
        if (params == null) {
            throw new InvalidInputException(
                    "Error: Input cannot be null."
            );
        }
        String[] inputs = params.split(" ");
        if (inputs.length != 3) {
            throw new InvalidInputException(
                    "Error: Addition not successful: invalid number of parameters.");
        }
        if (!InputValidator.validate_email(inputs[2])) {
            throw new InvalidInputException(
                    "Error: Addition not successful: invalid email format.");
        }
        inputs[0] = WordFormatter.capitalise(inputs[0]);
        inputs[1] = WordFormatter.capitalise(inputs[1]);
        params = String.join(" ", inputs);
        this.receiver.add(params);
        System.out.println("Add");
    }

    /**
     * Undoes the add operation by removing the most recently added entry from
     * the receiver's data store.
     *
     * <p>This assumes that the add operation appends the entry to the end of
     * the data list and that no other entries have been added since. It should
     * only be called immediately after {@link #execute()}.</p>
     */
    @Override
    public void undo() {
        receiver.delete(receiver.getDataEntries().size() - 1);
        System.out.println("Undo");
    }

    /**
     * Indicates that this command supports undo.
     *
     * @return {@code true}, since the add operation can be reverted
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}