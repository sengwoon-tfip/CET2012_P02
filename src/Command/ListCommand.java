package Command;

import Receiver.Receiver;

/**
 * Concrete command that displays all entries from the receiver's data store.
 *
 * <p>This command follows the Command design pattern and encapsulates the
 * logic for listing current data. It is not undoable.</p>
 */
public class ListCommand implements Command {

    /** The receiver responsible for listing the stored entries. */
    private final Receiver receiver;

    /**
     * Constructs a {@code ListCommand} with the specified receiver.
     *
     * @param receiver the receiver that provides the list operation
     */
    public ListCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the list operation by calling the receiver's list method.
     *
     * <p>Displays all entries currently stored in the data collection.</p>
     */
    @Override
    public void execute() {
        this.receiver.list();
    }

    /**
     * Informs that the list command cannot be undone.
     *
     * <p>Calling this method simply prints a message indicating
     * that undo is not supported.</p>
     */
    @Override
    public void undo() {
        System.out.println("List command cannot be undone.");
    }

    /**
     * Indicates that this command does not support undo.
     *
     * @return {@code false}, since list is a non-destructive operation
     */
    @Override
    public boolean isUndoable() {
        return false;
    }
}