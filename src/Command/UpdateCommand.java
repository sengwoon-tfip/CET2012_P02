package Command;

import Utils.InvalidInputException;
import Utils.InputValidator;
import Receiver.Receiver;
import Utils.WordFormatter;

/**
 * Concrete command that performs an update operation by delegating to a
 * {@link Receiver}.
 *
 * <p>This command follows the Command design pattern and encapsulates the
 * logic for updating an existing entry. It optionally validates a provided
 * email address using {@link InputValidator}. The command supports undo
 * functionality by storing the previous state before execution.</p>
 */
public class UpdateCommand implements Command {

    /** The receiver that actually performs the update operation. */
    private final Receiver receiver;

    /** Parameters required for the update operation, space-separated. */
    private final String params;

    /**
     * Backup of the data before the update, used for undo functionality.
     * This stores the original entry at the update index.
     */
    private String previousData;

    /**
     * Constructs an {@code UpdateCommand} with the specified receiver and
     * parameters.
     *
     * <p>If four parameters are provided, the fourth is assumed to be an email
     * address and is validated using
     * {@link InputValidator#validate_email(String)}.</p>
     *
     * @param receiver the receiver responsible for executing the update
     * @param params a space-separated string of update parameters:
     *               <ul>
     *                 <li>First element: index (1-based) of the entry to
     *                 update</li>
     *                 <li>Following elements: new data values</li>
     *                 <li>Optional fourth element: email which must be valid if
     *                     present</li>
     *               </ul>
     * @throws InvalidInputException if an email is present but invalid
     */
    public UpdateCommand(Receiver receiver, String params)
    throws InvalidInputException {
        this.receiver = receiver;
        this.params = params;
    }

    /**
     * Executes the update operation.
     *
     * <p>This method splits the parameters, checks the count (must be between
     * 1 and 4), validates the email (if present), then retrieves and saves
     * the original entry to permit undo.</p>
     *
     * <p>The update data (excluding index) has the first two elements
     * capitalised (first letter uppercase, rest lowercase) via
     * {@link WordFormatter#capitalise(String)}.</p>
     *
     * <p>The update is then delegated to the receiver using the parsed index
     * and updated data elements.</p>
     *
     * @throws InvalidInputException if the number of parameters is invalid or
     *                               if the email (when provided) is in an
     *                               invalid format
     * @throws NumberFormatException if the first parameter is not a valid
     *                               integer index
     */
    @Override
    public void execute() {
        String[] inputs = params.split(" ");
        if (inputs.length <= 1) {
            throw new InvalidInputException(
                "Error: Not enough parameters for update."
            );
        }
        if (inputs.length > 4) {
            throw new InvalidInputException(
                "Error: Too many parameters for update."
            );
        }
        if (inputs.length == 4 && !InputValidator.validate_email(inputs[3])) {
            throw new InvalidInputException(
                "Invalid email format for update command."
            );
        }

        int index = Integer.parseInt(inputs[0]) - 1;

        if (index < 0 || index >= receiver.getDataEntries().size()) {
            throw new InvalidInputException(
                "Error: Index out of bounds."
            );
        }

        this.previousData = receiver.getDataEntries().get(index);

        // Extract new data components (excluding index)
        String[] newData = new String[inputs.length - 1];
        System.arraycopy(
                inputs, 1, newData, 0, inputs.length - 1
        );

        // Capitalise first two new data elements if available
        newData[0] = WordFormatter.capitalise(newData[0]);
        if (newData.length > 1) {
            newData[1] = WordFormatter.capitalise(newData[1]);
        }

        receiver.update(index, newData);
        System.out.println("Update");
    }

    /**
     * Undoes the update operation by restoring the previous data at the
     * specified index.
     *
     * <p>Relies on the backup taken during {@link #execute()} to revert the
     * changes.</p>
     *
     * <p>This method parses the original index from {@code params} and invokes
     * an update on the receiver with the previously saved data split into
     * components.</p>
     */
    @Override
    public void undo() {
        String[] inputs = params.split(" ");
        int index = Integer.parseInt(inputs[0]) - 1;
        receiver.update(index, this.previousData.split(" "));
        System.out.println("Undo");
    }

    /**
     * Indicates that this command supports undo.
     *
     * @return {@code true}, since the update operation can be reverted
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}