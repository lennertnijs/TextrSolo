package com.Textr.File;

import com.Textr.Input.InputHandlerRepo;
import com.Textr.Settings;
import com.Textr.Util.Validator;

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
    }

    /**
     * Writes data to a file, using the given URL as location. This overwrites the file with the given data.
     *
     * @param data          The String to write.
     * @param fileLocation  The String holding the file location.
     * @throws IOException when the file cannot be created or opened, or an error happens during writing.
     */
    public static void write(String data, String fileLocation){
        Validator.notNull(fileLocation, "File location to write to may not be null.");
        Validator.notNull(data, "Data to write may not be null.");
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(fileLocation))) {
            writer.write(data.replace(System.lineSeparator(), Settings.defaultLineSeparator));
        }catch(IOException e){
            InputHandlerRepo.setAnythingInputHandler();
        }
    }
}
