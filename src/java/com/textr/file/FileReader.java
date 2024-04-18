package com.textr.file;

import com.textr.Settings;

import java.io.*;
import java.util.Objects;

/**
 * A class for reading a file's contents.
 */
public final class FileReader {

    /**
     * Private constructor. No use.
     */
    private FileReader(){
    }

    /**
     * Reads the contents of a file and returns it.
     * @param file  The File to read from.
     *
     * @return The file's contents as a String.
     * @throws IllegalArgumentException If a non-ASCII character was found.
     * @throws IllegalArgumentException If a non-supported line break was found.
     */
    public static String readContents(File file){
        Objects.requireNonNull(file, "File is null.");
        try(FileInputStream f = new FileInputStream(file)){
            DataInputStream data = new DataInputStream(f);
            StringBuilder stringBuilder = new StringBuilder();
            int b;
            while((b = data.read()) != -1){
                checkForNonAscii((char) b);
                stringBuilder.append((char)b);
            }
            String s = stringBuilder.toString();
            validateLineBreaks(s);
            return s.trim();
        }catch(IOException e){
            throw new IllegalArgumentException("An error occurred during the reading of a File");
        }
    }

    /**
     * Checks whether the given character is supported.
     * @throws IllegalArgumentException If the character is not supported.
     */
    private static void checkForNonAscii(char c){
        if(c < 32 && c != 10 && c != 13 || 127 <= c)
            throw new IllegalArgumentException("An unsupported character was present in the File.");
    }

    /**
     * Checks whether the given string only contains line breaks of the type set in the Settings.
     * @param s The string. Cannot be null.
     *
     * @throws IllegalStateException If the line break is fully unsupported.
     */
    private static void validateLineBreaks(String s){
        switch(Settings.defaultLineSeparator){
            case "\r\n" -> validateCRLFOnly(s);
            case "\n" -> validateLFOnly(s);
            default -> throw new IllegalStateException("An unsupported line break was found.");
        }
    }

    /**
     * Validates that the only line breaks in the string are CRLF. (\r\n)
     * @param s The string. Cannot be null.
     *
     * @throws IllegalArgumentException If any other line breaks are found. (\r or \n)
     */
    private static void validateCRLFOnly(String s){
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == '\n')
                throw new IllegalArgumentException("An unsupported line break was found.");
            if(c == '\r'){
                if(i == s.length() - 1)
                    throw new IllegalArgumentException("An unsupported line break was found.");
                if(s.charAt(i + 1) != '\n')
                    throw new IllegalArgumentException("An unsupported line break was found.");
                i += 1;
            }
        }
    }

    /**
     * Validates that the only line breaks in the string are LF. (\n)
     * @param s The string. Cannot be null.
     *
     * @throws IllegalStateException If any \r were found.
     */
    private static void validateLFOnly(String s) {
        if(s.contains("\r"))
            throw new IllegalArgumentException("An unsupported line break was found.");
    }
}
