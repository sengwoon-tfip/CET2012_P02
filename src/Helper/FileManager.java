package Helper;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    /**
     * File path to read and store data for long term storage
     */
    private static final String filepath = "src/dataStore.txt";

    /**
     * Loads entries from the data file into the data list.
     * Creates the file if it does not exist.
     */
    public static ArrayList<String> loadFromFile() {
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
                return null;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                ArrayList<String> dataEntries = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    dataEntries.add(line.trim());
                }
                return dataEntries;
            }

        } catch (IOException e) {
            System.out.println("Error handling file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes current data entries to the data file, overwriting previous
     * content.
     */
    public static void storeToFile(ArrayList<String> dataEntries) {
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
}
