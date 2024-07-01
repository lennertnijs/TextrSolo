package com.textr.file;

import com.textr.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is responsible for writing data to a file.
 */
public final class FileWriter implements IFileWriter{

    /**
     * Private constructor, has no use.
     */
    public FileWriter(){
    }

    /**
     * Writes the string to the given file.
     * This overwrites the old contents of the file.
     *
     * @param s  The string to write.
     * @param file  The file to write to.
     */
    public void write(String s, File file) throws IOException {
        Objects.requireNonNull(s, "The text to write is null.");
        Objects.requireNonNull(file, "File is null.");
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            writer.write(s.replace("\n", Settings.defaultLineSeparator));
        }
    }
}
