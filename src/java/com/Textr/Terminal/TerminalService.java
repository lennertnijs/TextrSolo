package com.Textr.Terminal;

import com.Textr.View.Dimension2D;
import com.Textr.View.Position;
import io.github.btj.termios.Terminal;

import java.io.IOException;

public class TerminalService {

    private TerminalService(){
    }

    public static Dimension2D getTerminalArea(){
        Terminal.reportTextAreaSize();
        try{
            int b = Terminal.readByte();
            while (b != ';'){
                b = Terminal.readByte();
            }
            b = Terminal.readByte();
            int height = 0;
            while(b != ';'){
                if ('0' <= b && b <= '9') {
                    height *= 10;
                    height += b - '0';
                }
                b = Terminal.readByte();
            }

            int width = 0;
            while(b != 't'){
                b = Terminal.readByte();
                if ('0' <= b && b <= '9') {
                    width *= 10;
                    width += b - '0';
                }
            }
            return Dimension2D.builder().width(width).height(height).build();
        }catch(IOException e){
            throw new IllegalStateException("Something went wrong while reading the terminal dimensions.");
        }
    }

    public static void enterRawInputMode(){
        Terminal.enterRawInputMode();
    }

    public static void leaveRawInputMode(){
        Terminal.leaveRawInputMode();
    }

    public static void moveCursor(int row, int column){
        if(row <= 0 || column <= 0){
            throw new IllegalArgumentException("The system is 1-based. All indices must be strictly positive");
        }
        Terminal.moveCursor(column, row, "empty");
    }

    public static void clearScreen(){
        Terminal.clearScreen();
    }

    public static void printText(Position position, String text){
        Terminal.printText(position.getY(), position.getX(), text);
    }

    public static void printText(int x, int y, String text){
        Terminal.printText(y, x , text);
    }

    public static int readByte(){
        try{
            return Terminal.readByte();
        }catch(IOException e){
            return -1;
        }
    }
}
