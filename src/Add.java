/**
 * Concrete command that performs an add operation by delegating to a
 * {@link Receiver}.
 *
 * <p>Extends {@link AbstractCommand} to utilise shared validation logic,
 * specifically for validating the email parameter.</p>
 */
public class Add extends AbstractCommand implements Command {

    /** Parameters required for the add operation. */
    private final String[] params;

    /** The receiver that actually performs the add operation. */
    private final Receiver receiver;

    /**
     * Constructs an Add command with the specified receiver and parameters.
     * Validates the email at index 2 of the params array using the inherited
     * validation method.
     *
     * @param receiver the receiver that will perform the add operation
     * @param params the parameters to be passed to the receiver's add method;
     *               expects exactly 3 elements
     * @throws IllegalArgumentException if the email parameter (params[2]) is
     *                                  invalid
     */
    public Add(Receiver receiver, String[] params) {
        this.receiver = receiver;
        if (!AbstractCommand.validate_email(params[2])) {
            throw new IllegalArgumentException("Invalid email.");
        }
        this.params = params;
    }

    /**
     * Executes the add command by invoking the {@code Add} method on the
     * receiver with the stored parameters.
     */
    @Override
    public void execute() {
        this.receiver.Add(params);
    }
}
