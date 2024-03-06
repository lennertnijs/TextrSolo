package com.Textr.File;

import com.Textr.Settings;
import com.Textr.Util.Validator;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * A class for reading an actual .txt file's contents.
 */
public final class FileReader {

    /**
     * Private constructor. No use.
     */
    private FileReader(){
    }

    /**
     * Reads the contents of a .txt file and returns it.
     * If any non-ASCII characters are found in the file, throws an Exception.
     * @param url The uniform resource locator of the text file.
     *
     * @return The file's contents
     * @throws IllegalArgumentException If a non-ASCII character was found in the file's contents.
     */
    public static String readContents(String url){
        Validator.notNull(url, "Cannot read a null file's contents.");
        try(BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(url))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                checkForNonAscii(line);
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            return stringBuilder.toString();
        }catch(IOException e){
            throw new IllegalArgumentException("An error occurred during the reading of a File");
        }
    }

    /**
     * Checks a string of text for non-ascii characters. Throws an error if any were found.
     * @param text The text.
     */
    private static void checkForNonAscii(String text){
        for(char c : text.toCharArray()){
            boolean isNonASCII = checkForNonAsciiWithLineSeparatorSetting(c);
            if(isNonASCII){
                throw new IllegalArgumentException("A non-ASCII character was present in the File.");
            }
        }
    }

    /**
     * Checks whether a character is within ASCII characters, including the set line separator.
     * @param c The character
     * @return True if non-ascii. False otherwise.
     */
    private static boolean checkForNonAsciiWithLineSeparatorSetting(char c){
        switch(Settings.defaultLineSeparator){
            case "\r" -> {
                return (c < 32 && c != 13) || 127 <= c;
            }
            case "\n" -> {
                return (c < 32 && c != 10) || 127 <= c;
            }
            case "\r\n" -> {
                return (c < 32 && c != 10 && c != 13) || 127 <= c;
            }
        }
        throw new IllegalArgumentException();
    }
}
