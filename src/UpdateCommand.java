/**
 * Concrete command that performs an update operation by delegating to a
 * {@link Receiver}.
 *
 * <p>Utilises shared validation logic from {@link Utils},
 * specifically for validating the email parameter if it is provided.</p>
 */
public class UpdateCommand implements Command {

    /** Parameters required for the update operation. */
    private final String params;

    /** The receiver that actually performs the update operation. */
    private final Receiver receiver;

    /** Backup of the data before the update, used for undo. */
    private String previousData;

    /**
     * Constructs an Update command with the specified receiver and parameters.
     * If an email is provided (as the fourth element in the array), it is
     * validated using the Utils email validation method.
     *
     * @param receiver the receiver that will perform the update operation
     * @param params the parameters to be passed to the receiver's update
     *               method; if provided, the email must be the third data item
     *               and valid
     * @throws IllegalArgumentException if an email is provided and it is
     * invalid
     */
    public UpdateCommand(Receiver receiver, String params) throws InvalidInputException {
        this.receiver = receiver;
        String[] inputs = params.split(" ");
        if ((inputs.length == 0 ) || (inputs.length > 4)) {
            throw new InvalidInputException("Invalid number of parameters.");
        }
        if (inputs.length > 3) {
            if (!Utils.validate_email(inputs[3])) {
                throw new InvalidInputException("Invalid email format.");
            }
        }
        this.params = params;
    }

    /**
     * Executes the update command by invoking the {@code update} method on the
     * receiver with the stored parameters.
     * <p>
     * Also stores the original data so it can be restored if {@code undo()} is
     * called.
     * </p>
     */
    @Override
    public void execute() {
        String[] inputs = params.split(" ");
        int index = Integer.parseInt(inputs[0]) - 1;
        // Make a copy of the existing data before update
        this.previousData = receiver.getDataEntries().get(index);
        String[] newData = new String[inputs.length - 1];
        System.arraycopy(
                inputs, 1, newData, 0, inputs.length - 1
        );
        receiver.update(index, newData);
        System.out.println("Entry updated successfully.");
    }

    /**
     * Undoes the update operation by restoring the original data at the
     * specified index using the previously backed-up state.
     */
    @Override
    public void undo() {
        String[] inputs = params.split(" ");
        int index = Integer.parseInt(inputs[0]) - 1;
        receiver.update(index, this.previousData.split(" "));
        System.out.println("Undo command for update executed successfully.");
    }
}