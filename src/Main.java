import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        String filename = "dataStore.txt";
        ArrayList<String> dataEntries = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            dataEntries = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                dataEntries.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found." + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file." + e.getMessage());
        }

        Invoker invoker = new Invoker();
        Receiver rcvr = new Receiver(dataEntries);
        ListCommand list = new ListCommand(rcvr);
        DeleteCommand delete = new DeleteCommand(rcvr, 3);
        UndoCommand undo = new UndoCommand(rcvr);
        Command[] commands = {add, update, delete, undo, list};
        invoker.setCommandsForExecution(commands);

        Stack<Command> history = new Stack<>();
        invoker.executeCommand(history);
        rcvr.setHistory(history);
        dataEntries = rcvr.getDataEntries();
        rcvr.storeToFile();
    }
}
