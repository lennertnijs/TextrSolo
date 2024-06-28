package com.textr;

import com.textr.terminal.TerminalService;
import com.textr.view.ViewService;

import java.util.Arrays;

public class Initialiser {


    public static void initialise(ViewService viewService, String[] args, TerminalService terminal){
        loadDefaultLineSeparator();
        String[] filePaths = handleArguments(args);
        terminal.enterRawInputMode();
        terminal.clearScreen();
        if(filePaths.length > terminal.getTerminalArea().getHeight() / 2){
            throw new IllegalArgumentException("Too many input files.");
        }
        viewService.initialiseViews(filePaths);
        viewService.drawAll();
    }


    private static String[] handleArguments(String[] args){
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


    private static void overWriteDefaultLineSeparator(String input){
        String str = input.replace("--", "");
        switch (str) {
            case "lf" -> Settings.defaultLineSeparator = "\n";
            case "crlf" -> Settings.defaultLineSeparator = "\r\n";
        }
    }

    /**
     * Loads the correct default line separator for the given system.
     *
     * @throws IllegalStateException If no matching line separator was found.
     */
    private static void loadDefaultLineSeparator(){
        validateLineSeparator(System.lineSeparator());
        Settings.defaultLineSeparator = System.lineSeparator();
    }

    private static void validateLineSeparator(String lineSeparator){
        if(lineSeparator.equals("\n") || lineSeparator.equals("\r\n")){
            return;
        }
        throw new IllegalStateException("This line separator is not supported.");
    }
}
