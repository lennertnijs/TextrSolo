package com.Textr;

import com.Textr.File.File;
import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBufferService;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.View.ViewService;

import java.util.Arrays;

public class Initialiser {
    public static void initialise(FileService fs, FileBufferService fbs, ViewService vs, String[] args){
        loadDefaultLineSeparator();
        String[] files = handleArguments(args);
        InputHandlerRepo.initialiseInputHandlers(fbs, vs);

        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();

        for(String file: files){
            fs.initialiseFile(file);
        }
        for(File file : fs.getAllFiles()){
            fbs.initialisePassiveFileBuffer(file);
        }
        fbs.setActiveFileBuffer(0);
        vs.initialiseViewsVertical();
        vs.drawAllViews();
        vs.drawCursor();
    }

    /**
     * Loads the correct default line separator for the given system.
     *
     * @throws IllegalStateException If no matching line separator was found.
     */
    private static void loadDefaultLineSeparator(){
        Settings.defaultLineSeparator = System.lineSeparator();
        if(Settings.defaultLineSeparator == null){
            throw new IllegalStateException("This system's line separator is not supported.");
        }
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
}
