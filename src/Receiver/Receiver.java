package Receiver;

import Utils.FileManager;
import java.util.ArrayList;

/**
 * The Receiver class manages the core data store and handles all CRUD
 * operations.
 *
 * <p>It maintains a list of data entries, supports undo operations via a
 * command
 * history stack, and handles persistence by reading from and writing to a
 * file.</p>
 */
public class Receiver {
    private final ArrayList<String> dataEntries;
    private final FileManager fileManager = new FileManager();

    /**
     * Constructs a Receiver instance, initializing data storage and loading
     * data entries from the file.
     */
    public Receiver() {
        this.dataEntries = fileManager.loadFromFile();
    }

    /**
     * Adds a new entry line at the end of the data list.
     *
     * @param line the string line to be added
     */
    public void add(String line) {
        dataEntries.add(line);
    }

    /**
     * Updates the entry at the specified index with provided input fields.
     * Only non-empty input fields overwrite existing data fields.
     *
     * @param index  the zero-based index of the entry to update
     * @param inputs array of strings representing fields to update (max 3)
     */
    public void update(int index, String[] inputs) {
        String[] fields = this.dataEntries.get(index).split(" ", -1);

        if (!inputs[0].isEmpty()) {
            fields[0] = inputs[0];
        }
        if (inputs.length > 1 && !inputs[1].isEmpty()) {
            fields[1] = inputs[1];
        }
        if (inputs.length > 2 && !inputs[2].isEmpty()) {
            fields[2] = inputs[2]; // for email (could apply lowercase here)
        }

        this.dataEntries.set(index, String.join(" ", fields));
    }

    /**
     * Prints all data entries with a numbered prefix (starting at 1).
     */
    public void list() {
        for (int i = 0; i < this.dataEntries.size(); i++) {
            System.out.printf("%02d. %s\n", i + 1, this.dataEntries.get(i));
        }
    }

    /**
     * Deletes and returns the entry at the specified index.
     *
     * @param index zero-based index of the entry to delete
     * @return the deleted entry string, or empty string if invalid index
     */
    public String delete(int index) {
        if (dataEntries.isEmpty()) {
            System.out.println("No entries to delete");
            return "";
        }
        if (index < 0 || index >= this.dataEntries.size()) {
            return "";
        } else {
            return dataEntries.remove(index);
        }
    }

    /**
     * Inserts an entry at the specified index in the data list.
     *
     * @param index the zero-based position to insert the entry
     * @param entry the string entry to insert
     */
    public void insertAtIndex(int index, String entry) {
        dataEntries.add(index, entry);
    }

    /**
     * Gets the list of all current data entries.
     *
     * @return the list of data entries
     */
    public ArrayList<String> getDataEntries() {
        return dataEntries;
    }

    /**
     * Stores the current data entries to the data file,
     * overwriting previous content.
     */
    public void storeToFile() {
        fileManager.saveToFile(this.dataEntries);
    }
}