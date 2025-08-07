package Command;

import Receiver.Receiver;

/**
 * Concrete command that triggers the undo operation on the receiver.
 *
 * <p>This command follows the Command design pattern. It instructs the
 * receiver to revert the most recent command that supports undo.</p>
 *
 * <p>Note: The undo command itself cannot be undone.</p>
 */
public class UndoCommand implements Command {

    /** The receiver that manages and performs undo operations. */
    private final Receiver receiver;

    /**
     * Constructs an {@code UndoCommand} with the specified receiver.
     *
     * @param receiver the receiver that will perform the undo action
     */
    public UndoCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Executes the undo operation via the receiver.
     *
     * <p>This method delegates the undo behavior to the receiver,
     * which should maintain a command history to support undo.</p>
     */
    @Override
    public void execute() {
        this.receiver.undo();
    }

    /**
     * Informs that the undo command itself cannot be undone.
     *
     * <p>Calling this method simply prints a message indicating that
     * undoing an undo is not supported.</p>
     */
    @Override
    public void undo() {
        System.out.println("Undo command cannot be undone.");
    }

    /**
     * Indicates that this command is not undoable.
     *
     * @return {@code false}, since an undo command cannot be reverted
     */
    @Override
    public boolean isUndoable() {
        return false;
    }
}