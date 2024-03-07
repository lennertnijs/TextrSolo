package com.Textr;

import com.Textr.File.File;
import com.Textr.File.FileService;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.FileBufferCreator;
import com.Textr.Input.InputHandlerRepo;
import com.Textr.Terminal.TerminalService;
import com.Textr.Util.Point;
import com.Textr.View.Dimension2D;
import com.Textr.View.View;
import com.Textr.View.ViewCreator;
import com.Textr.View.ViewService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Initialiser {
    public static void initialise(FileService fs, ViewService vs, String[] args){
        loadDefaultLineSeparator();
        String[] files = handleArguments(args);
        InputHandlerRepo.initialiseInputHandlers(vs);

        TerminalService.enterRawInputMode();
        TerminalService.clearScreen();

        for(String file: files){
            fs.initialiseFile(file);
        }
        List<View> views = new ArrayList<>();
        for(File file : fs.getAllFiles()){
            FileBuffer buffer = FileBufferCreator.create(file);
            View view = ViewCreator.create(buffer, Point.create(5,5), Dimension2D.create(5,5));
            views.add(view);
        }
        vs.storeViews(views);
        vs.generateViews();
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
