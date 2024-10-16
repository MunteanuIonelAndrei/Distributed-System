package ManagementComunication.communicationCSV.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadDeviceFromFile {

    public static void main(String[] args) {
        String filePath = "path/to/your/file.txt"; // Replace with your file's path
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // Ensure the line is long enough to prevent StringIndexOutOfBoundsException
                if (line.length() > 8) {
                    int startIndex = 8; // Start reading from the 9th character (index 8)
                    int endIndex = line.indexOf('"', startIndex); // Find the next " character
                    if (endIndex != -1) {
                        String data = line.substring(startIndex, endIndex);
                        System.out.println("Found data: " + data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
