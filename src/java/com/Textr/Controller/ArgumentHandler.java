package com.Textr.Controller;

import com.Textr.DefaultLineSeparator;
import com.Textr.Settings;

import java.util.Arrays;

public class ArgumentHandler {

    private ArgumentHandler(){
    }

    public static String[] handleArguments(String[] args){
        loadDefaultSettings();
        if(args.length == 0){
            throw new IllegalArgumentException("No files were provided.");
        }
        String[] files = Arrays.copyOfRange(args, 0, args.length);
        if(args[0].contains("--")){
            overWriteDefaultLineSeparator(args[0]);
            files = Arrays.copyOfRange(args, 1, args.length);
        }
        return files;
    }

    private static void loadDefaultSettings(){
        Settings.loadDefaultLineSeparator();
    }

    private static void overWriteDefaultLineSeparator(String input){
        String str = input.replace("--", "");
        switch (str) {
            case "lf" -> Settings.defaultLineSeparator = DefaultLineSeparator.LF;
            case "cr" -> Settings.defaultLineSeparator = DefaultLineSeparator.CR;
            case "crlf" -> Settings.defaultLineSeparator = DefaultLineSeparator.CRLF;
        }
    }
}
