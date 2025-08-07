package Command;



/**
 * Represents a command that can be executed.
 *
 * <p>This interface follows the Command design pattern,
 * where implementing classes encapsulate all the information needed
 * to perform an action or trigger an event.</p>
 */
public interface Command {

    /**
     * Executes the command.
     * <p>Implementing classes should define
     * the specific action that occurs when this method is called.</p>
     */
    void execute();

    /**
     * Undo current command.
     * <p> Implementing classes need to define specific undo
     * operations to be carried out </p>
     */
    void undo();

    boolean isUndoable();
}
