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
                rcvr, "1 John Dog jp@dog.com"
        );
        ListCommand list = new ListCommand(rcvr);
        DeleteCommand delete = new DeleteCommand(rcvr, "f");
        UndoCommand undo = new UndoCommand(rcvr, history);
        Command[] commands = {
                add, add2, update, undo, list, delete, list
        };

        invoker.setCommandsForExecution(commands);
        invoker.executeCommand(history);
        FileManager.storeToFile(rcvr.getDataEntries());
    }
}