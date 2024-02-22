package com.Textr;

import com.Textr.Controller.FileController;
import com.Textr.FileModel.FileService;
import com.Textr.FileModel.Rectangle;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final FileService fileService = new FileService();
        final FileController fileController = new FileController(fileService);

        fileController.loadFiles(args);
        Terminal.enterRawInputMode();
        Terminal.clearScreen();
        Rectangle rect = textAreaSize();
        Terminal.printText(15, 15, String.format("%s x %s", rect.getWidth(), rect.getHeight()));
        Terminal.leaveRawInputMode();
    }

    private static Rectangle textAreaSize() throws IOException {
        Terminal.reportTextAreaSize();
        int b = Terminal.readByte();
        while (b != ';'){
            b = Terminal.readByte();
        }
        b = Terminal.readByte();
        int height = 0;
        while(b != ';'){
            b = Terminal.readByte();
            if ('0' <= b && b <= '9') {
                height *= 10;
                height += b - '0';
            }
        }

        int width = 0;
        while(b != 't'){
            b = Terminal.readByte();
            if ('0' <= b && b <= '9') {
                width *= 10;
                width += b - '0';
            }
        }
        return Rectangle.builder().width(width).height(height).build();
    }
}