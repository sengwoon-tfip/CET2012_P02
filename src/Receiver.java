import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Receiver {
    private final ArrayList<String> dataEntries;
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
     * Method to add a line composed of multiple string values joined by spaces
     * at the end of the data array.
     *
     * @param values Array of string values to be joined and added as one line
     */
    public void add(String[] values) {
        String line = String.join(" ", values);
        dataEntries.add(line);
    }

    /**
     * Updates an existing employee entry at the given index using the provided
     * parameters.
     *
     * @param params Array of strings in the format:
     *               [index, data1?, data2?, data3?]
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void update(String[] params) {
        if (params.length < 2) {
            System.out.println("Error: Not enough parameters for update.");
            return;
        }
        if (params.length > 4) {
            System.out.println("Error: Too many parameters for update.");
            return;
        }

        // Convert 1-based to 0-based index
        int index = Integer.parseInt(params[0]) - 1;

        if (index < 0 || index >= this.dataEntries.size()) {
            System.out.println("Error: Index out of bounds.");
            return;
        }

        // Split entry into fields
        String[] fields = this.dataEntries.get(index).split(" ", -1);
        // Assume three fields: Name, Dept, Email
        // Pad to three fields if missing parts
        fields = Arrays.copyOf(fields, 3);

        // Update only provided fields (if not empty)
        if (!params[1].isEmpty()) {
            fields[0] = params[1];
        }
        if (params.length > 2 && !params[2].isEmpty()) {
            fields[1] = params[2];
        }
        if (params.length > 3 && !params[3].isEmpty()) {
            fields[2] = params[3].toLowerCase(); // For email, in lowercase
        }

        // Join back and update the entry
        this.dataEntries.set(index, String.join(",", fields));
        System.out.println("Entry updated successfully.");
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
        if (dataEntries.isEmpty()) {
            System.out.println("No entries to delete");
            return "";
        } else {
            if (index >= dataEntries.size()) {
                System.out.println("Index out of bounds");
                return "";
            } else {
                return dataEntries.remove(index);
            }
        }
    }

    /**
     * Retrieves last executed command to carry out corresponding undo operation.
     */
    public void undo() {
        if (dataEntries != null) {
            Command lastCommand = this.history.pop();
            lastCommand.undo();
            // remove undo command from history
            this.history.pop();
        }
    }

    /**
     * Method to add a line back into the data array at the specified index.
     *
     * @param index Index at which the entry should be inserted
     * @param entry String value of the entry to insert
     */
    public void insertAtIndex(int index, String entry) {
        dataEntries.add(index, entry);
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
