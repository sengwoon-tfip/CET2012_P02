/**
 * Concrete command that performs an add operation by delegating to a {@link Receiver}.
 *
 * <p>Utilises shared validation logic from {@link Utils},
 * specifically for validating the email parameter.</p>
 */
public class AddCommand implements Command {

    /** Parameters required for the add operation. */
    private final String[] params;

    /** The receiver that actually performs the add operation. */
    private final Receiver receiver;

    /**
     * Constructs an Add command with the specified receiver and parameters.
     * Validates the email at index 2 of the params array using {@link Utils}.
     *
     * @param receiver the receiver that will perform the add operation
     * @param params the parameters to be passed to the receiver's add method;
     *               expects exactly 3 elements
     * @throws IllegalArgumentException if the email parameter (params[2]) is invalid
     */
    public AddCommand(Receiver receiver, String[] params) {
        this.receiver = receiver;
        if (!Utils.validate_email(params[2])) {
            throw new IllegalArgumentException("Invalid email.");
        }
        this.params = params;
    }

    /**
     * Executes the add command by invoking the {@code add} method on the
     * receiver with the stored parameters.
     */
    @Override
    public void execute() {
        this.receiver.add(params);
    }
}