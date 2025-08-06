/**
 * Concrete command that performs an add operation by delegating to a {@link Receiver}.
 *
 * <p>Utilises shared validation logic from {@link Utils},
 * specifically for validating the email parameter.</p>
 */
public class AddCommand implements Command {

    /** Parameters required for the add operation. */
    private final String params;

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
    public AddCommand(Receiver receiver, String params) throws InvalidInputException {
        this.receiver = receiver;
        String[] inputs = params.split(" ");
        if (inputs.length != 3) {
            throw new InvalidInputException("Invalid number of parameters.");
        }
        if (!Utils.validate_email(inputs[2])) {
            throw new InvalidInputException("Invalid email format.");
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
        System.out.println("Added line " + params + ".");
    }

    /**
     * Reverts the last add operation by removing the most recently added entry
     * from the receiver's data store.
     *
     * <p>This effectively undoes the effect of the {@link #execute()} method,
     * assuming that the corresponding {@code add} operation appended a new
     * entry at the end of the data store.</p>
     */
    @Override
    public void undo() {
        receiver.delete(receiver.getDataEntries().size() - 1);
        System.out.println("Undo command for add executed successfully.");
    }
}