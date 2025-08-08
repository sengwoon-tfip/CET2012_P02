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
                rcvr, "First_name Last_name Email"
        );
        ListCommand list = new ListCommand(rcvr);
        AddCommand add2 = new AddCommand(
                rcvr, "John Doe simple@example.com"
        );
        AddCommand add3 = new AddCommand(
                rcvr, "Hanna Moon tetter.tots@potatoesarelife.com"
        );
        AddCommand add4 = new AddCommand(
                rcvr, "Ah Boon green-tea@teaforlife.com"
        );
        UpdateCommand update = new UpdateCommand(
                rcvr, "3 Adam"
        );
        UpdateCommand update2 = new UpdateCommand(
                rcvr, "1 blue bell ice-cream@alaskaFields.org"
        );
        DeleteCommand delete = new DeleteCommand(rcvr, "1");
        UndoCommand undo = new UndoCommand(rcvr, history);
        Command[] commands = {
                add, add2, add3, add4, list,
                update, list, update2, list,
                delete, list,
                undo, list
        };

        invoker.setCommandsForExecution(commands);
        invoker.executeCommand(history);
        FileManager.storeToFile(rcvr.getDataEntries());
    }
}