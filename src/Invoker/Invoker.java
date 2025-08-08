package Invoker;

import Command.Command;
import Utils.InvalidInputException;

import java.util.Stack;

/**
 * Invoker class that manages and executes commands.
 *
 * <p>This class holds an array of commands to be executed and maintains the
 * history of undoable commands for potential rollback.</p>
 */
public class Invoker {

    /** Array of Command objects scheduled for execution. */
    private Command[] cmdToExecute;

    /**
     * Sets the commands to be executed.
     *
     * @param commands an array of Command objects to execute
     */
    public void setCommandsForExecution(Command[] commands) {
        this.cmdToExecute = commands;
    }

    /**
     * Executes each command in the array and pushes undoable commands to
     * the history stack.
     *
     * <p>If a command throws an {@link InvalidInputException}, the error
     * message is printed and execution continues with the next command.</p>
     *
     * @param history a stack to store successfully executed undoable commands
     */
    public void executeCommand(Stack<Command> history) {
        if (history == null) {
            throw new InvalidInputException(
                    "Error: Input cannot be null."
            );
        }
        for (Command cmd : cmdToExecute) {
            try {
                cmd.execute();
                if (cmd.isUndoable()) {
                    history.push(cmd);
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}