package com.Textr;

/**
 * Stores any global app settings.
 */
public class Settings {

    public static DefaultLineSeparator defaultLineSeparator = null;

    /**
     * Loads the correct default line separator for the given system.
     *
     * @throws IllegalStateException If no matching line separator was found.
     */
    public static void loadDefaultLineSeparator(){
        switch (System.lineSeparator()) {
            case ("\r") -> defaultLineSeparator = DefaultLineSeparator.CR;
            case ("\n") -> defaultLineSeparator = DefaultLineSeparator.LF;
            case ("\r\n") -> defaultLineSeparator = DefaultLineSeparator.CRLF;
        }
        if(defaultLineSeparator == null){
            throw new IllegalStateException("This system's line separator is not supported.");
        }
    }
}
