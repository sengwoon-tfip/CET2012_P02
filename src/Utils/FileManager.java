package Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for loading and storing data entries using NIO.
 */
public class FileManager {

    /**
     * File path to read and store data for long-term storage.
     */
    private final Path filepath = Paths.get("src/dataStore.txt");

    /**
     * Loads entries from the data file into a list.
     * If the file doesn't exist, creates it.
     *
     * @return an ArrayList of trimmed strings representing each data line,
     *         or null if an error occurred
     */
    public ArrayList<String> loadFromFile() {
        try {
            if (Files.notExists(filepath)) {
                Files.createFile(filepath);
                System.out.println(
                        "File not found. Created new file: " + filepath
                );
            }

            List<String> lines = Files.readAllLines(filepath);
            ArrayList<String> dataEntries = new ArrayList<>();

            for (String line : lines) {
                dataEntries.add(line.trim());
            }

            return dataEntries;

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Stores the current data entries to the data file,
     * overwriting previous content.
     *
     * @param dataEntries list of strings to write to file
     */
    public void saveToFile(ArrayList<String> dataEntries) {
        try {
            Files.write(
                    filepath,
                    dataEntries,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.CREATE
            );
        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}