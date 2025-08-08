package Client;

import Command.*;
import Utils.InvalidInputException;
import Invoker.Invoker;
import Receiver.Receiver;

import java.util.Stack;

public class Client {
    public static void main(String[] args) throws InvalidInputException {
        Invoker invoker = new Invoker();
        Receiver rcvr = new Receiver();
        Stack<Command> history = new Stack<Command>();

//        AddCommand add = new AddCommand(
//                rcvr, "John Pork jo@pig.com"
//        );
//        AddCommand add2 = new AddCommand(
//                rcvr, "Tom Cat tc@cat.com"
//        );
//        UpdateCommand update = new UpdateCommand(
//                rcvr, "1 John Dog jp@dog.com hhh"
//        );
//        ListCommand list = new ListCommand(rcvr);
//        DeleteCommand delete = new DeleteCommand(rcvr, "1");
//        UndoCommand undo = new UndoCommand(rcvr, history);
        Command[] commands = {
//                add, add2, list, update, undo, list, delete, undo, undo
                new AddCommand(rcvr, "NAME1 name2 email@email.com"),    // valid
                new AddCommand(rcvr, "^!@#$ name4 email09_"),           // valid
                new AddCommand(rcvr, "name1 name2 _email@email.com"),   // valid
                new AddCommand(rcvr, "name1 name2 email_@email.com"),   // valid
                new AddCommand(rcvr, "name1 name2 email__@email.com"),  // valid
                new AddCommand(rcvr, "name1 name2 e__mail@email.com"),  // valid
                new AddCommand(rcvr, "name1 name2 EMAIL@EMAIL.com"),    // valid
                new AddCommand(rcvr, "name1 name2 EMAIL@E.MAIL.com"),   // valid
//                new DeleteCommand(rcvr, "1.2"),   // valid
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
//                new UndoCommand(rcvr, history),
                new ListCommand(rcvr),
                new UpdateCommand(rcvr, "10.6 jean luc jean.luc@domain.com"),
                new UpdateCommand(rcvr, "2 aLiCe McDoNaLd alice@domain.com"),
                new ListCommand(rcvr),
                new ListCommand(rcvr)

        };

        invoker.setCommandsForExecution(commands);
        invoker.executeCommand(history);
        rcvr.storeToFile();
    }
}