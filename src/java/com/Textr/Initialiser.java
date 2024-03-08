package com.Textr;

import com.Textr.File.FileService;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

import java.util.Arrays;

public class Initialiser {


    public static void initialise(FileService fileService, ViewService viewService, String[] args){
        loadDefaultLineSeparator();
        String[] filePaths = handleArguments(args);
        InputHandlerRepo.initialiseInputHandlers(viewService);

        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();

        for(String file: filePaths){
            fileService.initialiseFile(file);
        }

        viewService.initialiseViewsForAllFiles();
        viewService.drawAllViews();
        viewService.drawCursor();
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
            case "cr" -> Settings.defaultLineSeparator = "\r";
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
        if(lineSeparator.equals("\n") || lineSeparator.equals("\r") || lineSeparator.equals("\r\n")){
            return;
        }
        throw new IllegalStateException("This line separator is not supported.");
    }
}
