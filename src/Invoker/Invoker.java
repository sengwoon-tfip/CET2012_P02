package Invoker;

import Command.Command;
import Helper.InvalidInputException;

import java.util.Stack;

public class Invoker {
    private Command[] cmdToExecute;

    /**
     * Stores array of Command objects to be executed.
     *
     * @param commands Array of commands to be executed
     */
    public void setCommandsForExecution(Command[] commands) {
        this.cmdToExecute = commands;
    }

    /**
     * Executes each Command object and push the executed command to history
     * stack.
     *
     * @param history Stores stack of command executed previously
     */
    public void executeCommand(Stack<Command> history) {
        for (Command cmd : cmdToExecute) {
            try {
                cmd.execute();
                if (cmd.isUndoable()) {
                    history.push(cmd);
                }
            }
            catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}