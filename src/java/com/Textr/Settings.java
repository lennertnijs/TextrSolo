package com.Textr;

public class Settings {

    public static DefaultLineSeparator defaultLineSeparator = null;

    public static void loadDefaultLineSeparator(){
        switch (System.lineSeparator()) {
            case ("\r") -> defaultLineSeparator = DefaultLineSeparator.CR;
            case ("\n") -> defaultLineSeparator = DefaultLineSeparator.LF;
            case ("\r\n") -> defaultLineSeparator = DefaultLineSeparator.CRLF;
        }
    }
}
