package Command;

import java.util.Stack;
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

    /** The receiver that actually performs the add operation. */
    private final Receiver receiver;

    /** The stack to contain the history of executed (undoable) commands*/
    private final Stack<Command> history;

    /**
     * Constructs an {@code UndoCommand} with the sperrcified receiver.
     *
     * @param history the stack to contain the history of executed commands
     */
    public UndoCommand(Receiver receiver, Stack<Command> history) {
        this.receiver = receiver;
        this.history = history;
    }

    /**
     * Executes the undo operation.
     *
     * <p>This method undoes the last executed command by popping it from
     * the command history and executing the previous command.</p>
     */
    @Override
    public void execute() {
        if (!this.history.isEmpty()) {
            Command lastCommand = this.history.pop();
            lastCommand.undo();
        } else {
            System.out.println("No previous command to undo.");
        }
    }

    /**
     * Informs that the undo command itself cannot be undone.
     *
     * <p>Calling this method simply prints a message indicating that
     * undoing an undo is not supported.</p>
     */
    @Override
    public void undo() {
//        System.out.println("Undo command cannot be undone.");
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