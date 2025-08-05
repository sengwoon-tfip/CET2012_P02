import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Receiver {
    private ArrayList<String> dataEntries;
    private Stack<Command> history;
    public Receiver(ArrayList<String> dataEntries) {
        this.dataEntries = dataEntries;
    }

    /**
     * Sets the command history stack in case of undo operations.
     *
     * @param history Initialize history variable with command stacks
     */
    public void setHistory(Stack<Command> history) {
        this.history = history;
    }

    /**
     * Lists out the entries within dataEntries array list.
     */
    public void list() {
        for (int i = 1; i <= this.dataEntries.size(); i++) {
            System.out.println(i + ". " + this.dataEntries.get(i));
        }
    }

    /**
     * Removes the entry in the provided array list index and returns the removed
     * entry as string.
     *
     * @param index Position of entry to be deleted
     * @return Returns the deleted string for storage
     */
    public String delete(int index) {
        if (!dataEntries.isEmpty()) {
            return dataEntries.remove(index);
        }
        else {
            System.out.println("No entries to delete");
            return "";
        }
    }

    /**
     * Retrieves last executed command to carry out corresponding undo operation.
     */
    public void undo() {
        if (dataEntries != null) {
            Command lastCommand = this.history.pop();
            lastCommand.undo();
        }
    }

    /**
     * Method to add deleted line back into the data array based on index
     *
     * @param index Index at which the entry was deleted
     * @param deletedLine String value of entry that was deleted
     */
    public void indexAdd (int index, String deletedLine) {
        dataEntries.add(index, deletedLine);
    }

    /**
     * Stores the modified data entries and overwrites previous dataStore file
     */
    public void storeToFile() {
        String filename = "dataStore.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : dataEntries) {
                bw.write(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found." + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing file." + e.getMessage());
        }
    }

    /**
     * Getter for data entry array list to main loop
     *
     * @return Returns data entry array list
     */
    public ArrayList<String> getDataEntries() {
        return dataEntries;
    }
}
