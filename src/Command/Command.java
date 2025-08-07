package Command;

/**
 * Represents a command that can be executed and optionally undone.
 *
 * <p>This interface follows the Command design pattern,
 * where implementing classes encapsulate all the information needed
 * to perform an action or trigger an event. This allows for flexible
 * handling of operations such as queuing, logging, or undo/redo functionality.</p>
 */
public interface Command {

    /**
     * Executes the command.
     *
     * <p>Implementing classes should define the specific action that occurs
     * when this method is called. This method represents the primary behavior
     * associated with the command.</p>
     */
    void execute();

    /**
     * Undoes the effects of the most recent execution of this command.
     *
     * <p>Implementing classes should define the specific logic required
     * to revert the changes made during {@link #execute()}. This method
     * should only be called if {@link #isUndoable()} returns {@code true}.</p>
     */
    void undo();

    /**
     * Indicates whether this command supports undoing its operation.
     *
     * @return {@code true} if the command can be undone via {@link #undo()},
     *         {@code false} otherwise
     */
    boolean isUndoable();
}