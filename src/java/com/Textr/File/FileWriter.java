package com.Textr.File;

import com.Textr.Validator.Validator;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class is responsible for writing data to a URL
 */
public final class FileWriter {

    /**
     * Private constructor, has no use.
     */
    private FileWriter(){
        // No actions
    }

    /**
     * Writes data to a file, using the given URL as location. This overwrites the file with the given data.
     *
     * @param data          The String to write.
     * @param fileLocation  The String holding the file location.
     * @throws IOException when the file cannot be created or opened, or an error happens during writing.
     */
    public static void writeToFile(String data, String fileLocation) throws IOException {
        // Parameter checks
        Validator.notNull(fileLocation, "File location to write to may not be null.");
        Validator.notNull(data, "Data to write may not be null.");

        // Writing to file
        BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(fileLocation)); // Potential IOException
        writer.write(data); // Potential IOException
    }
}
