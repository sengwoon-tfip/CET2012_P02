package Client;

import Command.*;
import Utils.InvalidInputException;
import Utils.FileManager;
import Invoker.Invoker;
import Receiver.Receiver;

import java.util.Stack;

public class Client {
    public static void main(String[] args) throws InvalidInputException {
        Invoker invoker = new Invoker();
        Receiver rcvr = new Receiver();
        Stack<Command> history = new Stack<Command>();

        AddCommand add = new AddCommand(
                rcvr, "John Pork jo@pig.com"
        );
        AddCommand add2 = new AddCommand(
                rcvr, "Tom Cat tc@cat.com"
        );
        UpdateCommand update = new UpdateCommand(
                rcvr, "1 John Dog jp@dog.com hhh"
        );
        ListCommand list = new ListCommand(rcvr);
        DeleteCommand delete = new DeleteCommand(rcvr, "1");
        UndoCommand undo = new UndoCommand(history);
        Command[] commands = {
                add, add2, list, update, undo, list, delete, undo, undo
        };

        invoker.setCommandsForExecution(commands);
        invoker.executeCommand(history);
        FileManager.storeToFile(rcvr.getDataEntries());
    }
}