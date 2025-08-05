/**
 * Concrete command that performs an update operation by delegating to a
 * {@link Receiver}.
 *
 * <p>Utilises shared validation logic from {@link Utils},
 * specifically for validating the email parameter if it is provided.</p>
 */
public class UpdateCommand implements Command {

    /** Parameters required for the update operation. */
    private final String[] params;

    /** The receiver that actually performs the update operation. */
    private final Receiver receiver;

    /** Backup of the data before the update, used for undo. */
    private String[] previousData;

    /**
     * Constructs an Update command with the specified receiver and parameters.
     * If an email is provided (as the fourth element in the array), it is
     * validated using the Utils email validation method.
     *
     * @param receiver the receiver that will perform the update operation
     * @param params the parameters to be passed to the receiver's update
     *               method; if provided, the email must be at index 3 and valid
     * @throws IllegalArgumentException if an email is provided
     *                                  (params.length > 3) and it is invalid
     */
    public UpdateCommand(Receiver receiver, String[] params) {
        this.receiver = receiver;
        if (params.length > 3) {
            if (!Utils.validate_email(params[3])) {
                throw new IllegalArgumentException("Invalid email.");
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
        int index = Integer.parseInt(params[0]) - 1;

        // Make a deep copy of the existing data before update
        previousData = receiver.getDataEntries().get(index).clone();

        receiver.update(params);
    }

    /**
     * Undoes the update operation by restoring the original data at the
     * specified index using the previously backed-up state.
     */
    @Override
    public void undo() {
        int index = Integer.parseInt(params[0]) - 1;

        // Convert previousData into a params-style update for receiver
        String[] undoParams = new String[4];
        undoParams[0] = String.valueOf(index + 1); // index as 1-based string
        System.arraycopy(
                previousData, 0, undoParams, 1, 3)
        ;

        receiver.update(undoParams);
    }
}