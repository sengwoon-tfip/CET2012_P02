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

public class Receiver {
    private final ArrayList<String> dataEntries;
    private Stack<Command> history;
    private String filepath = "src/dataStore.txt";

    public Receiver() {
        this.dataEntries = new ArrayList<>();
        this.history = new Stack<Command>();
        this.loadFromFile();
    }

    public void setHistory(Stack<Command> history) {
        this.history = history;
    }

    /**
     * Method to add a line composed of multiple string values joined by spaces
     * at the end of the data array.
     *
     * @param line Line to be added
     */
    public void add(String line) {
        dataEntries.add(line);
    }

    /**
     * Updates an existing employee entry at the given index using the provided
     * parameters.
     *
     * @param inputs Array of strings in the format:
     *               [index, data1?, data2?, data3?]
     * @throws IndexOutOfBoundsException if the index is invalid
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

        // Split entry into fields
        String[] fields = this.dataEntries.get(index).split(" ", -1);

        // Update only provided fields (if not empty)
        if (!inputs[0].isEmpty()) {
            fields[0] = inputs[0];
        }
        if (inputs.length > 1 && !inputs[1].isEmpty()) {
            fields[1] = inputs[1];
        }
        if (inputs.length > 2 && !inputs[2].isEmpty()) {
            fields[2] = inputs[2]; // For email, in lowercase
        }

        // Join back and update the entry
        this.dataEntries.set(index, String.join(" ", fields));
    }

    /**
     * Lists out the entries within dataEntries array list.
     */
    public void list() {
        for (int i = 0; i < this.dataEntries.size(); i++) {
            System.out.printf("%02d. %s\n", i+1, this.dataEntries.get(i));
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
        }
        if (index < 0 || index >= this.dataEntries.size()) {
            System.out.println("Error: Index out of bounds");
            return "";
        } else {
            return dataEntries.remove(index);
        }
    }

    /**
     * Retrieves last executed command to carry out corresponding undo operation.
     */
    public void undo() {
        if (!this.history.isEmpty()) {
            // remove undo command from history
            this.history.pop();
            Command lastCommand = this.history.pop();
            lastCommand.undo();
        } else {
            System.out.println("No previous command to undo.");
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

    public void loadFromFile() {
        File file = new File(filepath);

        try {
            if (!file.exists()) {
                // Create the file and leave dataEntries empty
                if (file.createNewFile()) {
                    System.out.println("File not found. Created new file: " +
                            filepath);
                } else {
                    System.out.println("Failed to create new file.");
                }
                return; // skip reading — dataEntries stays empty
            }

            // File exists, read its contents
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                this.dataEntries.clear(); // Start fresh
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
     * Stores the modified data entries and overwrites previous dataStore file
     */
    public void storeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String line : dataEntries) {
                bw.write(line + "\n");
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
