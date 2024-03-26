package com.textr.file;

import com.textr.input.InputHandlerRepo;
import com.textr.Settings;
import com.textr.util.Validator;

import java.io.BufferedWriter;
import java.io.File;
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
     * Writes data to the given File. This overwrites the file with the given data.
     *
     * @param data  The String to write.
     * @param path  The File to write to.
     */
    public static void write(String data, File path){
        Validator.notNull(path, "File location to write to may not be null.");
        Validator.notNull(data, "Data to write may not be null.");
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path))) {
            writer.write(data.replace(System.lineSeparator(), Settings.defaultLineSeparator));
        }catch(IOException e){
            InputHandlerRepo.setAnythingInputHandler();
        }
    }
}
