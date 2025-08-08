package Command;

import Utils.InvalidInputException;
import Utils.InputValidator;
import Receiver.Receiver;

/**
 * Concrete command that performs an add operation by delegating to a
 * {@link Receiver}.
 *
 * <p>This class follows the Command design pattern, encapsulating the logic
 * for adding a new entry to the receiver's data store. It uses shared
 * validation utilities from {@link InputValidator} to ensure the email format is valid.
 * </p>
 */
public class AddCommand implements Command {

    /** The receiver that actually performs the add operation. */
    private final Receiver receiver;

    /** Parameters required for the add operation, space-separated. */
    private final String params;

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
     *                 <li>Second value: an attribute such as a role or title
     *                 </li>
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
     * Executes the add operation by invoking the {@code add} method on the
     * receiver.
     *
     * <p>Validates the input format and email before delegating the operation
     * to the receiver. If validation fails, an {@link InvalidInputException}
     * is thrown.</p>
     *
     * @throws InvalidInputException if the input is malformed or contains an
     * invalid email
     */
    @Override
    public void execute() {
        String[] inputs = params.split(" ");
        if (inputs.length != 3) {
            throw new InvalidInputException("Error: Addition not successful: " +
                    "invalid number of parameters.");
        }
        if ((!InputValidator.validate_email(inputs[2]) && !InputValidator.validate_email_string(inputs[2]))) {
            throw new InvalidInputException("Error: Addition not successful: " +
                    "invalid email format.");
        }
        this.receiver.add(params);
        System.out.println("Add");
    }

    /**
     * Undoes the add operation by removing the most recently added entry
     * from the receiver's data store.
     *
     * <p>This assumes that the add operation appends the entry to the end
     * of the data list and that no other entries have been added since.
     * It should only be called immediately after {@link #execute()}.</p>
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