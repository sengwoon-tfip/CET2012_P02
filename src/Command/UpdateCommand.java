package Command;

import Utils.InvalidInputException;
import Utils.Utils;
import Receiver.Receiver;

/**
 * Concrete command that performs an update operation by delegating to a
 * {@link Receiver}.
 *
 * <p>This command follows the Command design pattern and encapsulates
 * the logic for updating an existing entry. It uses {@link Utils}
 * for optional email validation and supports undo functionality
 * by storing the previous state before execution.</p>
 */
public class UpdateCommand implements Command {

    /** Parameters required for the update operation. */
    private final String params;

    /** The receiver that actually performs the update operation. */
    private final Receiver receiver;

    /** Backup of the data before the update, used for undo. */
    private String previousData;

    /**
     * Constructs an {@code UpdateCommand} with the specified receiver and
     * parameters.
     *
     * <p>If four parameters are provided, the fourth is assumed to be an email
     * address and is validated using {@link Utils#validate_email(String)}.</p>
     *
     * @param receiver the receiver responsible for executing the update
     * @param params a space-separated string of update parameters:
     *               <ul>
     *                 <li>First element: index of the entry to update (1-based)
     *                 </li>
     *                 <li>Following elements: new data values</li>
     *                 <li>Optional fourth element: email, which must be valid
     *                 if present</li>
     *               </ul>
     * @throws InvalidInputException if the email is present but invalid
     */
    public UpdateCommand(Receiver receiver, String params)
    throws InvalidInputException {
        this.receiver = receiver;
        this.params = params;
    }

    /**
     * Executes the update operation.
     *
     * <p>Parses the parameters, validates the email if present, and performs
     * the update on the receiver. Backs up the original data to allow undoing.
     * </p>
     *
     * @throws InvalidInputException if the number of parameters is invalid
     *                               or the email (if present) is not in a
     *                               valid format
     */
    @Override
    public void execute() {
        String[] inputs = params.split(" ");
        if ((inputs.length == 0) || (inputs.length > 4)) {
            throw new InvalidInputException("Update command provided with " +
                    "invalid number of parameters.");
        }

        if (inputs.length == 4 && !Utils.validate_email(inputs[3])) {
            throw new InvalidInputException("Invalid email format for update " +
                    "command.");
        }

        int index = Integer.parseInt(inputs[0]) - 1;
        this.previousData = receiver.getDataEntries().get(index);

        String[] newData = new String[inputs.length - 1];
        System.arraycopy(
                inputs, 1, newData, 0, inputs.length - 1
        );

        receiver.update(index, newData);
        System.out.println("Entry updated successfully.");
    }

    /**
     * Undoes the update operation by restoring the previous data at the
     * specified index.
     *
     * <p>Relies on the backup taken during {@link #execute()} to revert the
     * changes.</p>
     */
    @Override
    public void undo() {
        String[] inputs = params.split(" ");
        int index = Integer.parseInt(inputs[0]) - 1;
        receiver.update(index, this.previousData.split(" "));
        System.out.println("Undo command for update executed successfully.");
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