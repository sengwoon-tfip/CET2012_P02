package Receiver;

import Command.Command;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

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
    private Stack<Command> history;
    private String filepath = "src/dataStore.txt";

    /**
     * Constructs a Receiver instance, initializing data storage and loading
     * data entries from the file.
     */
    public Receiver() {
        this.dataEntries = new ArrayList<>();
        this.history = new Stack<Command>();
        this.loadFromFile();
    }

    /**
     * Sets the history stack used to track executed commands for undo.
     *
     * @param history the stack of executed commands
     */
    public void setHistory(Stack<Command> history) {
        this.history = history;
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
        if (inputs.length < 1) {
            System.out.println("Error: Not enough parameters for update.");
            return;
        }
        if (inputs.length > 3) {
            System.out.println("Error: Too many parameters for update.");
            return;
        }
        if (index < 0 || index >= this.dataEntries.size()) {
            System.out.println("Error: Index out of bounds.");
            return;
        }

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
     * Undoes the last executed command by popping it from history and calling
     * its undo method.
     */
    public void undo() {
        if (!this.history.isEmpty()) {
            Command lastCommand = this.history.pop();
            lastCommand.undo();
        } else {
            System.out.println("No previous command to undo.");
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
     * Loads entries from the data file into the data list.
     * Creates the file if it does not exist.
     */
    public void loadFromFile() {
        File file = new File(filepath);

        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println(
                            "File not found. Created new file: " + filepath
                    );
                } else {
                    System.out.println("Failed to create new file.");
                }
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                this.dataEntries.clear();
                String line;
                while ((line = br.readLine()) != null) {
                    this.dataEntries.add(line.trim());
                }
            }

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
        }
    }

    /**
     * Writes current data entries to the data file, overwriting previous
     * content.
     */
    public void storeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String line : dataEntries) {
                bw.write(line + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing file. " + e.getMessage());
        }
    }

    /**
     * Gets the list of all current data entries.
     *
     * @return the list of data entries
     */
    public ArrayList<String> getDataEntries() {
        return dataEntries;
    }
}