/**
 * Concrete command that performs an update operation by delegating to a
 * {@link Receiver}.
 *
 * <p>Extends {@link AbstractCommand} to utilise shared validation logic,
 * specifically for validating the email parameter if it is provided.</p>
 */
public class UpdateCommand extends AbstractCommand implements Command {

    /** Parameters required for the update operation. */
    private final String[] params;

    /** The receiver that actually performs the update operation. */
    private final Receiver receiver;

    /**
     * Constructs an Update command with the specified receiver and parameters.
     * If an email is provided (as the fourth element in the array), it is
     * validated using the inherited email validation method.
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
            if (!AbstractCommand.validate_email(params[3])) {
                throw new IllegalArgumentException("Invalid email.");
            }
        }
        this.params = params;
    }

    /**
     * Executes the update command by invoking the {@code update} method on the
     * receiver with the stored parameters.
     */
    @Override
    public void execute() {
        this.receiver.update(params);
    }
}