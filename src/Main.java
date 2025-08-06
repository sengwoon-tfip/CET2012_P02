import java.util.Stack;

public class Main {
    public static void main(String[] args) throws InvalidInputException {
        Invoker invoker = new Invoker();
        Receiver rcvr = new Receiver();
        AddCommand add = new AddCommand(
                rcvr, "John Pork jo@pig.com"
        );
        AddCommand add2 = new AddCommand(
                rcvr, "Tom Cat tc@cat.com"
        );
        UpdateCommand update = new UpdateCommand(
                rcvr, "1 John Dog jp.@dog.com"
        );
        ListCommand list = new ListCommand(rcvr);
        DeleteCommand delete = new DeleteCommand(rcvr, 1);
        UndoCommand undo = new UndoCommand(rcvr);
        Command[] commands = {
                add, undo, add2, update, delete, list
        };

        Stack<Command> history = new Stack<Command>();
        invoker.setCommandsForExecution(commands);
        rcvr.setHistory(history);
        invoker.executeCommand(history);
        rcvr.storeToFile();
    }
}
